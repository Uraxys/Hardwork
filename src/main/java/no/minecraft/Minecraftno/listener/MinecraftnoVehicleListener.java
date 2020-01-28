package no.minecraft.Minecraftno.listener;

import no.minecraft.Minecraftno.Minecraftno;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Rail;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class MinecraftnoVehicleListener implements Listener {

    private final Minecraftno plugin;
    double maxSpeed = 0;

    public MinecraftnoVehicleListener(Minecraftno instance) {
        this.plugin = instance;
        maxSpeed = 0.65;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onVehicleDamage(VehicleDamageEvent event) {
        if (!event.getVehicle().isEmpty()) {
            if (event.getVehicle() instanceof Boat) {
                event.setCancelled(true);
            }

            if (event.getVehicle() instanceof Minecart) {
                // Not sure why there was so many if statements here before, as it was always cancelled anyway.
                event.setCancelled(true);
                event.setDamage(0D);
            }
        } else {
            if (event.getAttacker() instanceof Player && event.getVehicle().getType() == EntityType.MINECART) {
                Player p = (Player) event.getAttacker();
                p.getInventory().addItem(new ItemStack(Material.MINECART));
                event.getVehicle().remove();
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onVehicleEntityCollision(VehicleEntityCollisionEvent event) {
        // We only care about minecarts.
        if (!(event.getVehicle() instanceof Minecart))
            return;

        // We only care about collisions with living entities.
        if (!(event.getEntity() instanceof LivingEntity))
            return;

        // We ony care about non-empty carts.
        if (event.getVehicle().isEmpty())
            return;

        // No collision. Carry on!
        event.setCancelled(true);
        event.setCollisionCancelled(true);
        event.setPickupCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onVehicleBlockCollision(VehicleBlockCollisionEvent event) {
        if (event.getVehicle() instanceof Boat) {
            if ((!event.getVehicle().isEmpty()) && !(event.getBlock().getType() == Material.WATER)) {
                Player localPlayer = (Player) event.getVehicle().getPassenger();
                event.getVehicle().teleport(localPlayer.getLocation());
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onVehicleMove(VehicleMoveEvent event) {
        Vehicle vehicle = event.getVehicle();

        // Kun minecarts!
        if (!(vehicle instanceof Minecart)) { // 'vehicle instanceof StorageMinecart' isn't needed as 'StorageMinecart' extends Minecart.
            return;
        }

        Location from = event.getFrom();
        Location to = event.getTo();
        Minecart cart = (Minecart) (event.getVehicle());
        Player player = (Player) event.getVehicle().getPassenger();
        Block rail = cart.getWorld().getBlockAt(cart.getLocation());
        Block under = to.getBlock().getRelative(BlockFace.DOWN);
        Block signBlock = under.getRelative(BlockFace.DOWN);

        if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
            Material underType = under.getType();
            if (underType == Material.BLUE_WOOL) {
                Block skilt = under.getRelative(BlockFace.DOWN);
                if (skilt != null && (signBlock.getState() instanceof Sign)) {
                    Sign sign = (Sign) skilt.getState();
                    if (player != null) {
                        String message = ChatColor.GREEN + sign.getLine(0) + sign.getLine(1) + sign.getLine(2) + sign.getLine(3);
                        player.sendMessage(message);
                        return;
                    }
                }
            } else if (underType == Material.GRAY_WOOL) {
                // BREMSE 50%
                Vector speed = cart.getVelocity();
                speed.setX(speed.getX() / 4);
                speed.setZ(speed.getZ() / 4);
                cart.setVelocity(speed);
                cart.setMaxSpeed(maxSpeed / 4);
            } else if (underType == Material.LIGHT_GRAY_WOOL) {
                // BREMSE 25%
                Vector speed = cart.getVelocity();
                speed.setX(speed.getX() / 2);
                speed.setZ(speed.getZ() / 2);
                cart.setVelocity(speed);
                cart.setMaxSpeed(maxSpeed / 2);
            } else if (underType == Material.PINK_WOOL) {
                cart.eject();
                return;
            } else if (underType == Material.YELLOW_WOOL && !under.isBlockIndirectlyPowered()) {
                Vector speed = cart.getVelocity();
                speed.setX(-speed.getX());
                speed.setZ(-speed.getZ());
                cart.setVelocity(speed);
            } else if (underType == Material.RED_WOOL) {
                cart.setVelocity(cart.getVelocity().multiply(0.0));
            } else if (underType == Material.BLACK_WOOL && !under.isBlockIndirectlyPowered() && (signBlock.getState() instanceof Sign)) {
                Sign sign = (Sign) signBlock.getState();
                for (String lines : sign.getLines()) {
                    if (lines.equalsIgnoreCase("[Stasjon]")) {
                        Vector speed = new Vector();
                        speed.setX(0);
                        speed.setY(0);
                        speed.setZ(0);
                        cart.setVelocity(speed);
                        return;
                    }
                }
            } else if (underType == Material.BROWN_WOOL && (signBlock.getState() instanceof Sign)) {
                if (event.getVehicle().getType() != EntityType.MINECART_CHEST) {
                    return;
                }
                Sign signe = (Sign) signBlock.getState();
                for (String lines : signe.getLines()) {
                    if (lines.equalsIgnoreCase("[Mottak]")) {
                        Block track = to.getBlock();
                        Rail railData = (Rail) track.getBlockData();
                        if (railData.getShape() == Rail.Shape.ASCENDING_EAST || railData.getShape() == Rail.Shape.ASCENDING_WEST || railData.getShape() == Rail.Shape.EAST_WEST) {
                            Block b_l = track.getRelative(0, 0, +2);
                            Block b_r = track.getRelative(0, 0, -2);
                            if (b_l.getState() instanceof Chest) {
                                Chest chest = (Chest) b_l.getState();
                                depositAll(chest, event.getVehicle());
                            } else if (b_r.getState() instanceof Chest) {
                                Chest chest = (Chest) b_r.getState();
                                depositAll(chest, event.getVehicle());
                            }
                        } else if (railData.getShape() == Rail.Shape.ASCENDING_NORTH || railData.getShape() == Rail.Shape.ASCENDING_SOUTH || railData.getShape() == Rail.Shape.NORTH_SOUTH) {
                            Block b_l = track.getRelative(-2, 0, 0);
                            Block b_r = track.getRelative(+2, 0, 0);
                            if (b_l.getState() instanceof Chest) {
                                Chest chest = (Chest) b_l.getState();
                                depositAll(chest, event.getVehicle());
                            } else if (b_r.getState() instanceof Chest) {
                                Chest chest = (Chest) b_r.getState();
                                depositAll(chest, event.getVehicle());
                            }
                        }
                    }
                }
            } else if (underType == Material.CYAN_WOOL && (signBlock.getState() instanceof Sign)) {
                if (event.getVehicle().getType() != EntityType.MINECART_CHEST) {
                    return;
                }
                Sign signe = (Sign) signBlock.getState();
                for (String lines : signe.getLines()) {
                    if (lines.equalsIgnoreCase("[Mottak]")) {
                        ItemStack item = null;
                        try {
                            item = this.plugin.matchItem(signe.getLines()[2]);
                        } catch (Exception e) {
                            return;
                        }
                        Block track = to.getBlock();
                        Rail railData = (Rail) track.getBlockData();
                        if (railData.getShape() == Rail.Shape.ASCENDING_EAST || railData.getShape() == Rail.Shape.ASCENDING_WEST || railData.getShape() == Rail.Shape.EAST_WEST) {
                            Block b_l = track.getRelative(0, 0, +2);
                            Block b_r = track.getRelative(0, 0, -2);
                            if (b_l.getState() instanceof Chest) {
                                Chest chest = (Chest) b_l.getState();
                                depositID(chest, event.getVehicle(), item);
                            } else if (b_r.getState() instanceof Chest) {
                                Chest chest = (Chest) b_r.getState();
                                depositID(chest, event.getVehicle(), item);
                            }
                        } else if (railData.getShape() == Rail.Shape.ASCENDING_NORTH || railData.getShape() == Rail.Shape.ASCENDING_SOUTH || railData.getShape() == Rail.Shape.NORTH_SOUTH) {
                            Block b_l = track.getRelative(-2, 0, 0);
                            Block b_r = track.getRelative(+2, 0, 0);
                            if (b_l.getState() instanceof Chest) {
                                Chest chest = (Chest) b_l.getState();
                                depositID(chest, event.getVehicle(), item);
                            } else if (b_r.getState() instanceof Chest) {
                                Chest chest = (Chest) b_r.getState();
                                depositID(chest, event.getVehicle(), item);
                            }
                        }
                        return;
                    }
                }
            }
            if (!event.getVehicle().getPassengers().isEmpty() && event.getVehicle().getPassengers().get(0) instanceof Player) {
                // Not sure if I'm wrong here, but the code that was here
                // before (The commented code) didn't do anything.
                // if (rail.getData() != 0x2 || rail.getData() != 0x3) {
                //     if (rail.getData() != 0x5 || rail.getData() != 0x4) {
                //         // Speed code here.
                //     } else {
                //         cart.setMaxSpeed(0.4D);
                //     }
                // } else { }
                Vector speed = cart.getVelocity();
                speed.setX(speed.getX() * 2);
                speed.setZ(speed.getZ() * 2);
                if (speed.getX() < 0) {
                    if (speed.getX() < -this.maxSpeed) {
                        speed.setX(-this.maxSpeed);
                    }
                }
                if (speed.getX() > 0) {
                    if (speed.getX() > this.maxSpeed) {
                        speed.setX(this.maxSpeed);
                    }
                }
                if (speed.getZ() < 0) {
                    if (speed.getZ() < -this.maxSpeed) {
                        speed.setZ(-this.maxSpeed);
                    }
                }
                if (speed.getZ() > 0) {
                    if (speed.getZ() > this.maxSpeed) {
                        speed.setZ(this.maxSpeed);
                    }
                }
                cart.setMaxSpeed(this.maxSpeed);
                cart.setVelocity(speed);
            } else if (event.getVehicle() instanceof StorageMinecart) {
                // Not sure if I'm wrong here, but the code that was here
                // before (The commented code) didn't do anything.
                // if (rail.getData() != 0x2 || rail.getData() != 0x3) {
                //     if (rail.getData() != 0x5 || rail.getData() != 0x4) {
                //         // Speed code here.
                //     } else {
                //         cart.setMaxSpeed(0.4D);
                //     }
                // } else { }
                Vector speed = cart.getVelocity();
                speed.setX(speed.getX() * 2);
                speed.setZ(speed.getZ() * 2);
                if (speed.getX() < 0) {
                    if (speed.getX() < -this.maxSpeed / 2) {
                        speed.setX(-this.maxSpeed / 2);
                    }
                }
                if (speed.getX() > 0) {
                    if (speed.getX() > this.maxSpeed / 2) {
                        speed.setX(this.maxSpeed / 2);
                    }
                }
                if (speed.getZ() < 0) {
                    if (speed.getZ() < -this.maxSpeed / 2) {
                        speed.setZ(-this.maxSpeed / 2);
                    }
                }
                if (speed.getZ() > 0) {
                    if (speed.getZ() > this.maxSpeed / 2) {
                        speed.setZ(this.maxSpeed / 2);
                    }
                }
                cart.setMaxSpeed(this.maxSpeed / 2);
                cart.setVelocity(speed);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onVehicleUpdate(VehicleUpdateEvent event) {
        if (!(event.getVehicle() instanceof Minecart)) {
            return;
        }
        Minecart cart = (Minecart) (event.getVehicle());
        Block under = cart.getWorld().getBlockAt(cart.getLocation()).getRelative(BlockFace.DOWN);
        Block signBlock = under.getRelative(BlockFace.DOWN);

        if (under.getType() == Material.BLACK_WOOL && under.isBlockIndirectlyPowered() && (signBlock.getState() instanceof Sign)) {
            Sign sign = (Sign) signBlock.getState();
            org.bukkit.block.data.type.Sign signData = (org.bukkit.block.data.type.Sign) sign.getBlockData();
            for (String lines : sign.getLines()) {
                if (lines.equalsIgnoreCase("[Stasjon]")) {
                    Vector speed = new Vector();
                    speed.setX(0);
                    speed.setZ(0);
                    if (signData.getRotation() == BlockFace.NORTH) speed.setZ(0.6D);
                    else if (signData.getRotation() == BlockFace.EAST) speed.setX(-0.6D);
                    else if (signData.getRotation() == BlockFace.SOUTH) speed.setZ(-0.6D);
                    else if (signData.getRotation() == BlockFace.WEST) speed.setX(0.6D);
                    else speed.setX(0.6D);
                    cart.setVelocity(speed);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onVehicleCreate(VehicleCreateEvent event) {
        Vehicle vehicle = event.getVehicle();

        // Kun minecarts!
        if (!(vehicle instanceof Minecart)) {
            return;
        }
        Minecart minecart = (Minecart) vehicle;
        //minecart.setSlowWhenEmpty(true);
        minecart.setMaxSpeed(2.0D);
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onVehicleEnter(VehicleEnterEvent event) {
        Vehicle vehicle = event.getVehicle();
        Entity entity = event.getEntered();

        if (vehicle instanceof Horse && entity instanceof Player) {
            Player player = (Player) entity;
            Horse horsy = (Horse) vehicle;
            if (horsy.isTamed() && horsy.getOwner() != null) {
                String owner = horsy.getOwner().getName();
                if (!owner.equalsIgnoreCase(player.getName())) {
                    player.sendMessage(ChatColor.RED + "Du kan ikke ri på denne hesten.");
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onVehicleExit(VehicleExitEvent event) {
        if (event.getExited() instanceof Player) {
            Player player = (Player) event.getExited();
            Vehicle veh = event.getVehicle();
            if (veh.getType() == EntityType.MINECART || veh.getType() == EntityType.BOAT) {
                Material matReturn = Material.getMaterial(veh.getType().toString()); // Name of enums of minecart and boat are the same in Material.
                if (matReturn != null) {
                    ItemStack toReturn = new ItemStack(matReturn);
                    if (toReturn != null) {
                        if (player.getInventory().firstEmpty() != -1) {
                            player.getInventory().addItem(toReturn);
                        } else {
                            player.getWorld().dropItem(player.getLocation(), toReturn);
                        }

                        veh.remove();
                    }
                }
            }
        }
    }

    public void depositID(Chest chest, Entity e, ItemStack checkitem) {
        if (!(e instanceof StorageMinecart)) {
            return;
        }
        StorageMinecart storageMinecart = (StorageMinecart) e;
        ArrayList<ItemStack> rest = new ArrayList<ItemStack>();
        for (ItemStack item : storageMinecart.getInventory().getContents()) {
            if (item != null && item.isSimilar(checkitem)) {
                HashMap<Integer, ItemStack> Failed = chest.getInventory().addItem(item);
                for (Entry<Integer, ItemStack> entry : Failed.entrySet()) {
                    if (entry.getValue() != null) {
                        rest.add(entry.getValue());
                    }
                }
            } else {
                rest.add(item);
            }
        }
        storageMinecart.getInventory().clear();
        for (ItemStack item : rest) {
            if (item != null) {
                storageMinecart.getInventory().addItem(item);
            }
        }
    }

    public void depositAll(Chest chest, Entity e) {
        if (!(e instanceof StorageMinecart)) {
            return;
        }
        StorageMinecart storageMinecart = (StorageMinecart) e;
        ArrayList<ItemStack> rest = new ArrayList<ItemStack>();
        for (ItemStack item : storageMinecart.getInventory().getContents()) {
            if (item != null) {
                HashMap<Integer, ItemStack> Failed = chest.getInventory().addItem(item);
                for (Entry<Integer, ItemStack> entry : Failed.entrySet()) {
                    if (entry.getValue() != null) {
                        rest.add(entry.getValue());
                    }
                }
            }
        }
        storageMinecart.getInventory().clear();
        for (ItemStack item : rest) {
            if (item != null) {
                storageMinecart.getInventory().addItem(item);
            }
        }
    }
}