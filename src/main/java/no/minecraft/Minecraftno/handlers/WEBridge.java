package no.minecraft.Minecraftno.handlers;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import no.minecraft.Minecraftno.Minecraftno;
import no.minecraft.Minecraftno.handlers.enums.BlockLogReason;
import no.minecraft.Minecraftno.handlers.player.MessageTask;
import org.bukkit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class WEBridge {
    private Minecraftno plugin;
    private WorldEditPlugin wePlugin;
    private static ArrayList<Material> nonBlocks = new ArrayList<>(Arrays.asList(
            Material.AIR, Material.GRASS_BLOCK, Material.DIRT, Material.PODZOL, Material.COARSE_DIRT, Material.BEDROCK,
            Material.WATER, Material.LAVA, Material.SAND, Material.RED_SAND, Material.GRAVEL, Material.BROWN_MUSHROOM,
            Material.RED_MUSHROOM, Material.FIRE, Material.WHEAT, Material.FARMLAND, Material.SUGAR_CANE, Material.CARROTS,
            Material.POTATO)
    );

    public WEBridge(Minecraftno plugin) {
        this.plugin = plugin;
        // Add all the beds & saplings to the nonBlocks list.
        nonBlocks.addAll(Arrays.stream(Material.values()).filter(m -> (m.name().endsWith("_BED") || m.name().endsWith("_SAPLING"))).collect(Collectors.toList()));
    }

    public boolean initialise() {
        this.wePlugin = (WorldEditPlugin) this.plugin.getServer().getPluginManager().getPlugin("WorldEdit");
        if (this.wePlugin == null) {
            this.plugin.getLogger().severe("Could not find WorldEdit!");
            return false;
        }

        return true;
    }

    public boolean isEnabled() {
        return this.wePlugin != null;
    }

    public WorldEditPlugin getWePlugin() {
        return this.wePlugin;
    }

    public void setArea(Region sel, int playerID, int newOwnerID, int changeId, Set<Material> materials) {
        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, new AreaProtecter(sel, playerID, newOwnerID, changeId, materials, plugin));
    }

    protected class AreaProtecter implements Runnable {
        protected Connection conn;
        protected BlockVector3 maxPoint;
        protected BlockVector3 minPoint;
        protected int playerID;
        protected World worldName;
        protected int newOwnerID;
        protected int changeId;
        protected Set<Material> materials;

        public AreaProtecter(Region sel, int playerID, int newOwnerID, int changeId, Set<Material> materials, Minecraftno instance) {
            this.conn = instance.getConnection();
            this.maxPoint = sel.getMaximumPoint();
            this.minPoint = sel.getMinimumPoint();
            this.playerID = playerID;
            this.worldName = BukkitAdapter.asBukkitWorld(sel.getWorld()).getWorld();
            this.newOwnerID = newOwnerID;
            this.changeId = changeId;
            this.materials = materials;
        }

        @Override
        public void run() {
            PreparedStatement ps = null;
            PreparedStatement logPS = null;

            try {
                if (this.newOwnerID != 0) {
                    ps = this.conn.prepareStatement("REPLACE INTO `blocks` (`x`, `y`, `z`, `world`, `player`) " + "VALUES (?, ?, ?, ?, ?)");
                    ps.setInt(5, this.newOwnerID);
                } else {
                    if (this.changeId != 0) {
                        ps = this.conn.prepareStatement("DELETE FROM blocks WHERE x=? AND y=? AND z=? AND world=? AND player=?");
                        ps.setInt(5, this.changeId);
                    } else {
                        ps = this.conn.prepareStatement("DELETE FROM blocks WHERE x=? AND y=? AND z=? AND world=?");
                    }
                }
                ps.setString(4, worldName.getName());

                logPS = this.conn.prepareStatement("INSERT INTO `blocklog` (`id`, `userid`, `action`, `x`, `y`, `z`, `world`, `material`, `time`) " + "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, UNIX_TIMESTAMP());");
                logPS.setInt(1, playerID);

                if (this.newOwnerID != 0) {
                    logPS.setString(2, BlockLogReason.CHANGEOWNER.toString());
                } else {
                    logPS.setString(2, BlockLogReason.UNPROTECTED.toString());
                }
                logPS.setString(6, this.worldName.getName());

                runOnSelection(this.minPoint, this.maxPoint, this.worldName, ps, logPS, materials);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                    if (logPS != null) {
                        logPS.close();
                    }
                    if (conn != null) {
                        //conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new MessageTask(playerID, ChatColor.GREEN + "Ferdig med å sette/fjerne blokkbeskyttelse", plugin));
        }
    }

    private static boolean runOnSelection(BlockVector3 min, BlockVector3 max, World worldname, PreparedStatement ps, PreparedStatement ps2, Set<Material> materials) throws SQLException {
        int fromX = min.getBlockX();
        int toX = max.getBlockX();

        int fromY = min.getBlockY();
        int toY = max.getBlockY();

        int fromZ = min.getBlockZ();
        int toZ = max.getBlockZ();

        for (int x = fromX; x <= toX; ++x) {
            for (int y = fromY; y <= toY; ++y) {
                for (int z = fromZ; z <= toZ; ++z) {
                    Location loc = new Location(worldname, x, y, z);

                    if (materials == null) {
                        if (!nonBlocks.contains(Bukkit.getServer().getWorld(worldname.getName()).getBlockAt(loc).getType())) {
                            ps.setInt(1, x);
                            ps.setInt(2, y);
                            ps.setInt(3, z);
                            ps.executeUpdate();

                            ps2.setInt(3, x);
                            ps2.setInt(4, y);
                            ps2.setInt(5, z);
                            ps2.setString(7, loc.getBlock().getType().name());
                            ps2.executeUpdate();
                        }
                    } else {
                        for (Material mat : materials) {
                            if (Bukkit.getServer().getWorld(worldname.getName()).getBlockAt(loc).getType() == mat) {
                                ps.setInt(1, x);
                                ps.setInt(2, y);
                                ps.setInt(3, z);
                                ps.executeUpdate();

                                ps2.setInt(3, x);
                                ps2.setInt(4, y);
                                ps2.setInt(5, z);
                                ps2.setString(7, mat.name()); // Using 'mat' instead of loc.getBlock().getType() to not call getBlockAt again.
                                ps2.executeUpdate();
                            }
                        }
                    }
                }
            }
        }

        return true;
    }
}
