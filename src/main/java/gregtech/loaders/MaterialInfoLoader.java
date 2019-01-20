package gregtech.loaders;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.M;
import static gregtech.api.GTValues.W;

public class MaterialInfoLoader {
    
    public static void init() {
        OreDictUnifier.registerOre(new ItemStack(Items.IRON_DOOR, 1), new ItemMaterialInfo(new MaterialStack(Materials.Iron, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Items.ACACIA_DOOR, 1), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Items.BIRCH_DOOR, 1), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Items.JUNGLE_DOOR, 1), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Items.OAK_DOOR, 1), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Items.SPRUCE_DOOR, 1), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Items.DARK_OAK_DOOR, 1), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M * 2)));

        OreDictUnifier.registerOre(new ItemStack(Blocks.QUARTZ_BLOCK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.NetherQuartz, 14515200L)));
        OreDictUnifier.registerOre(new ItemStack(Items.WHEAT, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wheat, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.HAY_BLOCK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wheat, 32659200L)));
        OreDictUnifier.registerOre(new ItemStack(Items.SNOWBALL, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Water, 907200L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.SNOW, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Water, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.GLOWSTONE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Glowstone, 14515200L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_LAMP, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Glowstone, 14515200L), new MaterialStack(Materials.Redstone, OrePrefix.dust.materialAmount * 4L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.ICE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Ice, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.PACKED_ICE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Ice, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Items.CLAY_BALL, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Clay, 1814400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.CLAY, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Clay, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.HARDENED_CLAY, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Clay, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Clay, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.BRICK_BLOCK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Clay, M)));
        OreDictUnifier.registerOre(new ItemStack(Items.BOOK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L)));
        OreDictUnifier.registerOre(new ItemStack(Items.WRITTEN_BOOK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L)));
        OreDictUnifier.registerOre(new ItemStack(Items.WRITABLE_BOOK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L)));
        OreDictUnifier.registerOre(new ItemStack(Items.ENCHANTED_BOOK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L)));
        OreDictUnifier.registerOre(new ItemStack(Items.GOLDEN_APPLE, 1, 1), new ItemMaterialInfo(new MaterialStack(Materials.Gold, OrePrefix.block.materialAmount * 8L)));
        OreDictUnifier.registerOre(new ItemStack(Items.GOLDEN_APPLE, 1, 0), new ItemMaterialInfo(new MaterialStack(Materials.Gold, OrePrefix.ingot.materialAmount * 8L)));
        OreDictUnifier.registerOre(new ItemStack(Items.SPECKLED_MELON, 1, 0), new ItemMaterialInfo(new MaterialStack(Materials.Gold, OrePrefix.nugget.materialAmount * 8L)));
        OreDictUnifier.registerOre(new ItemStack(Items.MINECART, 1), new ItemMaterialInfo(new MaterialStack(Materials.Iron, 18144000L)));
        OreDictUnifier.registerOre(new ItemStack(Items.CAULDRON, 1), new ItemMaterialInfo(new MaterialStack(Materials.Iron, 25401600L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.IRON_BARS, 8, W), new ItemMaterialInfo(new MaterialStack(Materials.Iron, 10886400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Gold, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Iron, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.ANVIL, 1, 0), new ItemMaterialInfo(new MaterialStack(Materials.Iron, M * 31)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.ANVIL, 1, 1), new ItemMaterialInfo(new MaterialStack(Materials.Iron, M * 22)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.ANVIL, 1, 2), new ItemMaterialInfo(new MaterialStack(Materials.Iron, M * 13)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.HOPPER, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Iron, 18144000L), new MaterialStack(Materials.Wood, 29030400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.TRIPWIRE_HOOK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Iron, OrePrefix.ring.materialAmount * 2L), new MaterialStack(Materials.Wood, M)));
        OreDictUnifier.registerOre(new ItemStack(Items.GLASS_BOTTLE), new ItemMaterialInfo(new MaterialStack(Materials.Glass, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STAINED_GLASS, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Glass, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.GLASS, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Glass, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Glass, 1360800L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.GLASS_PANE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Glass, 1360800L)));
        OreDictUnifier.registerOre(new ItemStack(Items.CLOCK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Gold, 14515200L), new MaterialStack(Materials.Redstone, M)));
        OreDictUnifier.registerOre(new ItemStack(Items.COMPASS, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Iron, 14515200L), new MaterialStack(Materials.Redstone, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.BEACON, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.NetherStar, M), new MaterialStack(Materials.Obsidian, 10886400L), new MaterialStack(Materials.Glass, 18144000L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.ENCHANTING_TABLE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Diamond, M * 2), new MaterialStack(Materials.Obsidian, 14515200L), new MaterialStack(Materials.Paper, 10886400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.ENDER_CHEST, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.EnderEye, M), new MaterialStack(Materials.Obsidian, 29030400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.BOOKSHELF, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Paper, 32659200L), new MaterialStack(Materials.Wood, 21772800L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.LEVER, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, M), new MaterialStack(Materials.Wood, 1814400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.ICE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Ice, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.PACKED_ICE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Ice, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.SNOW, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Water, M)));
        OreDictUnifier.registerOre(new ItemStack(Items.SNOWBALL, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Water, 907200L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.SNOW_LAYER, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Water, -1)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE_SLAB, 1, 0), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 1814400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE_SLAB, 1, 8), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 1814400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE_SLAB, 1, 2), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 1814400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE_SLAB, 1, 10), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 1814400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE_SLAB, 1, 3), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 1814400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE_SLAB, 1, 11), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 1814400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE_SLAB, 1, 5), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 1814400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE_SLAB, 1, 13), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 1814400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.FURNACE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 29030400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, M)));

        OreDictUnifier.registerOre(new ItemStack(Blocks.COBBLESTONE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE_BUTTON, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.LADDER, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.WOODEN_BUTTON, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.OAK_FENCE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 5443200L)));
        OreDictUnifier.registerOre(new ItemStack(Items.BOWL, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M)));
        OreDictUnifier.registerOre(new ItemStack(Items.SIGN, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, M * 2)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.CHEST, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 29030400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.TRAPPED_CHEST, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 32659200L), new MaterialStack(Materials.Iron, OrePrefix.ring.materialAmount * 2L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_TORCH, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 1814400L), new MaterialStack(Materials.Redstone, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.NOTEBLOCK, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 29030400L), new MaterialStack(Materials.Redstone, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.JUKEBOX, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 29030400L), new MaterialStack(Materials.Diamond, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.CRAFTING_TABLE, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 14515200L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.PISTON, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 14515200L), new MaterialStack(Materials.Wood, 10886400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.STICKY_PISTON, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 14515200L), new MaterialStack(Materials.Wood, 10886400L)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.DISPENSER, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 25401600L), new MaterialStack(Materials.Redstone, M)));
        OreDictUnifier.registerOre(new ItemStack(Blocks.DROPPER, 1, W), new ItemMaterialInfo(new MaterialStack(Materials.Stone, 25401600L), new MaterialStack(Materials.Redstone, M)));
    }
}