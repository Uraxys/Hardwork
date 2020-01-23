package no.minecraft.Minecraftno.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import no.minecraft.Minecraftno.Minecraftno;
import no.minecraft.Minecraftno.handlers.WEBridge;
import no.minecraft.Minecraftno.handlers.player.UserHandler;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class SetAreaBPCommand extends MinecraftnoCommand {
    private final WEBridge weBridge;
    private final UserHandler userHandler;
    private final static int maxSelection = 500000;

    public SetAreaBPCommand(Minecraftno instance) {
        super(instance);
        setAccessLevel(3);
        this.weBridge = instance.getWeBridge();
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public final boolean onPlayerCommand(Player player, Command command, String label, String[] args) {
        Region region;
        try {
            region = this.weBridge.getWePlugin().getSession(player).getSelection(BukkitAdapter.adapt(player.getLocation().getWorld()));
        } catch (IncompleteRegionException ignore) {
            player.sendMessage(getErrorChatColor() + "Du har ikke valgt et område.");
            return true;
        }
        if (region == null) {
            player.sendMessage(getErrorChatColor() + "Du har ikke valgt et område.");
            return true;
        }
        if (region.getArea() > maxSelection) {
            player.sendMessage(getErrorChatColor() + "Kan ikke beskytte mer enn " + maxSelection + " blokker om gangen. Du prøvde å beskytte: " + getVarChatColor() + region.getArea());
            return true;
        }
        player.sendMessage(getOkChatColor() + "Prøver å beskytte område: " + getVarChatColor() + region.getMaximumPoint().toString() + "  " + region.getMinimumPoint().toString());
        // printSelection(sel,player);
        String newOwner = UserHandler.SERVER_USERNAME;
        if (args.length > 0) {
            newOwner = args[0];
            int exists = this.userHandler.getUserId(newOwner);
            if (exists == -1) {
                player.sendMessage(getErrorChatColor() + "Spilleren eksisterer ikke!");
                return true;
            }
            if (args.length > 1) {
                Set<Material> materials = getId(args[1]);
                if (materials == null) {
                    player.sendMessage(getErrorChatColor() + "'" + args[1] + "' er ikke et tall!");
                    return false;
                }
                for (Material m : materials) {
                    if (m == Material.AIR) {
                        player.sendMessage(getErrorChatColor() + "Luft kan ikke bli beskyttet.");
                        return false;
                    }
                }
                this.weBridge.setArea(region, this.userHandler.getUserId(player), this.userHandler.getUserId(newOwner), 0, materials);
                return true;
            }
        }
        this.weBridge.setArea(region, this.userHandler.getUserId(player), this.userHandler.getUserId(newOwner), 0, null);
        return true;
    }

    public Set<Material> getId(String list) {
        String[] items = list.split(",");
        Set<Material> mats = new HashSet<>();
        for (String id : items) {
            Material mat = Material.getMaterial(id.toUpperCase());
            if (mat == null) continue;
            mats.add(mat);
        }
        return mats;
    }
}
