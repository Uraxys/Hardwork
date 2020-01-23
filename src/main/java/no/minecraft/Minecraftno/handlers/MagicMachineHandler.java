package no.minecraft.Minecraftno.handlers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class MagicMachineHandler {

    public static void magicMachin(Block block) {

        Random r = new Random();
        if (block.getWorld().getName().equalsIgnoreCase("world")) {
            Block[] Undervann = {block.getRelative(BlockFace.DOWN), block.getRelative(BlockFace.NORTH), block.getRelative(BlockFace.SOUTH), block.getRelative(BlockFace.EAST), block.getRelative(BlockFace.WEST)};
            for (int i = 0; i < Undervann.length; i++) {
                //BlockProtectHandler.delete(Undervann[i]);

                // gravel + stein == clay

                if (Undervann[i].getType() == Material.STONE) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.DOWN);
                    if (Undervannunder.getType() == Material.GRAVEL) {
                        int choice = r.nextInt(4);
                        if (choice == 0) {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.CLAY);
                        } else {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.GRAVEL) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.UP);
                    if (Undervannunder.getType() == Material.STONE) {
                        int choice = r.nextInt(4);
                        if (choice == 0) {
                            Undervann[i].setType(Material.CLAY);
                            Undervannunder.setType(Material.AIR);
                        } else {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                // stein == obsidian

                if (Undervann[i].getType() == Material.STONE) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.DOWN);
                    if (Undervannunder.getType() == Material.GLOWSTONE || Undervannunder.getType() == Material.STONE) {
                        int choice = r.nextInt(4);
                        if (choice == 0) {
                            Undervann[i].setType(Material.OBSIDIAN);
                        } else {
                            Undervann[i].setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.GLOWSTONE) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.UP);
                    if (Undervannunder.getType() == Material.STONE || Undervannunder.getType() == Material.STONE) {
                        int choice = r.nextInt(4);
                        if (choice == 0) {
                            Undervannunder.setType(Material.OBSIDIAN);
                        } else {
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.DIRT) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.DOWN);
                    if (Undervannunder.getType() == Material.GLOWSTONE || Undervannunder.getType() == Material.DIRT) {
                        int choice = (r.nextInt(100) < 25) ? 1 : 0;
                        if (choice == 0) {
                            Undervann[i].setType(Material.SAND);
                        } else {
                            Undervann[i].setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.GLOWSTONE) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.UP);
                    if (Undervannunder.getType() == Material.DIRT || Undervannunder.getType() == Material.DIRT) {
                        int choice = (r.nextInt(100) < 25) ? 1 : 0;
                        if (choice == 0) {
                            Undervannunder.setType(Material.SAND);
                        } else {
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.DIRT) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.DOWN);
                    if (Undervannunder.getType() == Material.OAK_PLANKS) {
                        int choice = r.nextInt(2);
                        if (choice == 0) {
                            Location loc = Undervann[i].getLocation();
                            Undervann[i].getWorld().dropItem(loc, new ItemStack(Material.OAK_LEAVES, 1));
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        } else {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.OAK_PLANKS) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.UP);
                    if (Undervannunder.getType() == Material.DIRT) {
                        int choice = r.nextInt(2);
                        if (choice == 0) {
                            Location loc = Undervann[i].getLocation();
                            Undervann[i].getWorld().dropItem(loc, new ItemStack(Material.OAK_LEAVES, 1));
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        } else {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.STONE) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.DOWN);
                    if (Undervannunder.getType() == Material.YELLOW_WOOL) {
                        int choice = r.nextInt(3);
                        if (choice == 0) {
                            Location loc = Undervann[i].getLocation();
                            Undervann[i].getWorld().dropItem(loc, new ItemStack(Material.GLOWSTONE, 1));
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        } else {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.YELLOW_WOOL) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.UP);
                    if (Undervannunder.getType() == Material.DIORITE) {
                        int choice = r.nextInt(3);
                        if (choice == 0) {
                            Location loc = Undervann[i].getLocation();
                            Undervann[i].getWorld().dropItem(loc, new ItemStack(Material.GLOWSTONE, 1));
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        } else {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.OBSIDIAN) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.DOWN);
                    if (Undervannunder.getType() == Material.NETHERRACK) {
                        int choice = r.nextInt(5);
                        if (choice == 0) {
                            Location loc = Undervann[i].getLocation();
                            Undervann[i].getWorld().dropItem(loc, new ItemStack(Material.POPPY, 1));
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        } else {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.NETHERRACK) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.UP);
                    if (Undervannunder.getType() == Material.OBSIDIAN) {
                        int choice = r.nextInt(5);
                        if (choice == 0) {
                            Location loc = Undervann[i].getLocation();
                            Undervann[i].getWorld().dropItem(loc, new ItemStack(Material.POPPY, 1));
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        } else {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.IRON_BLOCK) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.DOWN);
                    if (Undervannunder.getType() == Material.GLOWSTONE || Undervannunder.getType() == Material.IRON_BLOCK) {
                        int choice = r.nextInt(1);
                        if (choice == 0) {
                            Location loc = Undervann[i].getLocation();
                            Undervann[i].getWorld().dropItem(loc, new ItemStack(Material.IRON_DOOR, 1));
                            Undervann[i].setType(Material.AIR);
                        } else {
                            Undervann[i].setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.GLOWSTONE) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.UP);
                    if (Undervannunder.getType() == Material.IRON_BLOCK || Undervannunder.getType() == Material.IRON_BLOCK) {
                        int choice = r.nextInt(1);
                        if (choice == 0) {
                            Location loc = Undervann[i].getLocation();
                            Undervannunder.getWorld().dropItem(loc, new ItemStack(Material.IRON_DOOR, 1));
                            Undervannunder.setType(Material.AIR);
                        } else {
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.OBSIDIAN) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.DOWN);
                    if (Undervannunder.getType() == Material.WHITE_WOOL) {
                        int choice = r.nextInt(5);
                        if (choice == 0) {
                            Location loc = Undervann[i].getLocation();
                            Undervann[i].getWorld().dropItem(loc, new ItemStack(Material.BONE, 1));
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        } else {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }

                if (Undervann[i].getType() == Material.WHITE_WOOL) {
                    Block Undervannunder = Undervann[i].getRelative(BlockFace.UP);
                    if (Undervannunder.getType() == Material.OBSIDIAN) {
                        int choice = r.nextInt(5);
                        if (choice == 0) {
                            Location loc = Undervann[i].getLocation();
                            Undervann[i].getWorld().dropItem(loc, new ItemStack(Material.BONE, 1));
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        } else {
                            Undervann[i].setType(Material.AIR);
                            Undervannunder.setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}
