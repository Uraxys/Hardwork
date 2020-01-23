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

public class UnprotectAreaCommand extends MinecraftnoCommand {

    private final WEBridge weBridge;
    private final UserHandler userHandler;

    public UnprotectAreaCommand(Minecraftno instance) {
        super(instance);
        setAccessLevel(3);
        this.weBridge = instance.getWeBridge();
        this.userHandler = instance.getUserHandler();
    }

    @Override
    public final boolean onPlayerCommand(Player player, Command command, String label, String[] args) {
        Region sel;
        try {
            sel = this.weBridge.getWePlugin().getSession(player).getSelection(BukkitAdapter.adapt(player.getLocation().getWorld()));
        } catch (IncompleteRegionException ignore) {
            player.sendMessage(getErrorChatColor() + "Du har ikke valgt et område.");
            return true;
        }
        if (sel == null) {
            player.sendMessage(getErrorChatColor() + "Du har ikke valgt et område.");
            return true;
        }
        if (sel.getArea() > 100000) {
            player.sendMessage(getErrorChatColor() + "Kan ikke fjerne beskyttelse på mer enn 100000 blokker om gangen. " +
                "Utvalget var på: " + getVarChatColor() + sel.getArea());
            return true;
        }
        String changeId = UserHandler.SERVER_USERNAME;
        if (args.length > 0) {
            changeId = args[0];
            int exists = this.userHandler.getUserId(changeId);
            if (exists == -1) {
                player.sendMessage(getErrorChatColor() + "Spilleren eksisterer ikke!");
                return true;
            }
            if (args.length > 1) {
                Set<Material> ids = getId(args[1]);
                if (ids == null) {
                    player.sendMessage(getErrorChatColor() + "'" + args[1] + "' er ikke et tall!");
                    return false;
                }
                for (Material mat : ids) {
                    if (mat == Material.AIR) {
                        player.sendMessage(getErrorChatColor() + "Luft kan ikke bli beskyttet.");
                        return false;
                    }
                }
                this.weBridge.setArea(sel, this.userHandler.getUserId(player), 0, this.userHandler.getUserId(changeId), ids);
                return true;
            }
            this.weBridge.setArea(sel, this.userHandler.getUserId(player), 0, this.userHandler.getUserId(changeId), null);
            return true;
        } else {
            player.sendMessage(getOkChatColor() + "Prøver å fjerne beskyttelse i område: " + getVarChatColor() + sel.getMinimumPoint().toString() + "  " + sel.getMaximumPoint().toString());

            this.weBridge.setArea(sel, this.userHandler.getUserId(player), 0, 0, null);
            return true;
        }
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
