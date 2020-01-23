package no.minecraft.Minecraftno.handlers;

import no.minecraft.Minecraftno.Minecraftno;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeHandler {
    private Minecraftno plugin;

    public RecipeHandler(Minecraftno plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
	public void registerRecipes() {
    	// Mossy brick
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.MOSSY_STONE_BRICKS, 1)).shape(" v ", "vsv", " v ").setIngredient('v', Material.VINE).setIngredient('s', Material.STONE_BRICKS));

        // Cracked brick (x6)
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.CRACKED_STONE_BRICKS, 6)).shape("ss ", "s s", " ss").setIngredient('s', Material.STONE_BRICKS));

        // Special brick (x8)
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.CHISELED_STONE_BRICKS, 8)).shape("sss", "s s", "sss").setIngredient('s', Material.STONE_BRICKS));

        // Glowstone dust (x4)
        this.plugin.getServer().addRecipe(new ShapelessRecipe(new ItemStack(Material.GLOWSTONE_DUST, 4)).addIngredient(Material.GLOWSTONE));

        // Double step
        this.plugin.getServer().addRecipe(new ShapelessRecipe(new ItemStack(Material.SMOOTH_STONE)).addIngredient(2, Material.SMOOTH_STONE_SLAB));

        // Bone (x4)
        this.plugin.getServer().addRecipe(new FurnaceRecipe(new ItemStack(Material.BONE, 4), Material.MILK_BUCKET));

        // Endstone
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.END_STONE)).shape("nsn", "scs", "nsn").setIngredient('n', Material.NETHER_BRICK).setIngredient('s', Material.SNOW_BLOCK).setIngredient('c', Material.COBBLESTONE));

        // Slimeball
        this.plugin.getServer().addRecipe(new ShapelessRecipe(new ItemStack(Material.SLIME_BALL)).addIngredient(Material.SNOWBALL).addIngredient(Material.INK_SAC));

        // Gunpowder aka sulphur
        this.plugin.getServer().addRecipe(new ShapelessRecipe(new ItemStack(Material.GUNPOWDER)).addIngredient(Material.BLAZE_ROD).addIngredient(Material.SAND));

        // Creeper skull
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.CREEPER_HEAD, 1)).shape("g g", " w ", "g g").setIngredient('g', Material.GUNPOWDER).setIngredient('w', Material.WITHER_SKELETON_SKULL));
        
        // Skeleton skull
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.SKELETON_SKULL)).shape("b b", " w ", "b b").setIngredient('b', Material.BONE).setIngredient('w', Material.WITHER_SKELETON_SKULL));
        
        // Zombie skull
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.ZOMBIE_HEAD, 1)).shape("r r", " w ", "r r").setIngredient('r', Material.ROTTEN_FLESH).setIngredient('w', Material.WITHER_SKELETON_SKULL));
        
        // Podzol
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.PODZOL, 1)).shape("gdg", "dgd", "gdg").setIngredient('g', Material.GRASS).setIngredient('d', Material.DIRT));
        
        // Red Sand
        this.plugin.getServer().addRecipe(new ShapelessRecipe(new ItemStack(Material.RED_SAND, 1)).addIngredient(Material.RED_DYE).addIngredient(Material.SAND));
        
        // Ender pearl
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.ENDER_PEARL)).shape(" b ", "btb", " b ").setIngredient('b', Material.BLAZE_ROD).setIngredient('t', Material.GHAST_TEAR));

        // Spider eye
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.SPIDER_EYE)).shape("   ", "wrw", "   ").setIngredient('w', Material.NETHER_WART).setIngredient('r', Material.ROTTEN_FLESH));

        // Glowstone
        this.plugin.getServer().addRecipe(new ShapedRecipe(new ItemStack(Material.GLOWSTONE, 8)).shape("ggg", "gjg", "ggg").setIngredient('g', Material.GLASS).setIngredient('j', Material.JACK_O_LANTERN));
    }
}
