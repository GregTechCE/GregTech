package gregtech.loaders.preload;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.W;

public class OreDictionaryLoader {
    
    public static void init() {
        GTLog.logger.info("Registering OreDict entries.");

        OreDictUnifier.registerOre(new ItemStack(Items.BUCKET, 1, 0), OrePrefix.bucket, MarkerMaterials.Empty);
        OreDictUnifier.registerOre(new ItemStack(Items.WATER_BUCKET, 1, 0), OrePrefix.bucket, Materials.Water);
        OreDictUnifier.registerOre(new ItemStack(Items.LAVA_BUCKET, 1, 0), OrePrefix.bucket, Materials.Lava);
        OreDictUnifier.registerOre(new ItemStack(Items.MILK_BUCKET, 1, 0), OrePrefix.bucket, Materials.Milk);

        OreDictUnifier.registerOre(new ItemStack(Items.GLASS_BOTTLE, 1, 0), OrePrefix.bottle, MarkerMaterials.Empty);
        OreDictUnifier.registerOre(new ItemStack(Items.POTIONITEM, 1, 0), OrePrefix.bottle, Materials.Water);

        OreDictUnifier.registerOre(new ItemStack(Blocks.COAL_ORE, 1), OrePrefix.ore, Materials.Coal);
        OreDictUnifier.registerOre(new ItemStack(Blocks.IRON_ORE, 1), OrePrefix.ore, Materials.Iron);
        OreDictUnifier.registerOre(new ItemStack(Blocks.LAPIS_ORE, 1), OrePrefix.ore, Materials.Lapis);
        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_ORE, 1), OrePrefix.ore, Materials.Redstone);
        OreDictUnifier.registerOre(new ItemStack(Blocks.GOLD_ORE, 1), OrePrefix.ore, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Blocks.DIAMOND_ORE, 1), OrePrefix.ore, Materials.Diamond);
        OreDictUnifier.registerOre(new ItemStack(Blocks.EMERALD_ORE, 1), OrePrefix.ore, Materials.Emerald);
        OreDictUnifier.registerOre(new ItemStack(Blocks.QUARTZ_ORE, 1), OrePrefix.ore, Materials.NetherQuartz);
        OreDictUnifier.registerOre(new ItemStack(Items.DYE, 1, 4), OrePrefix.gem, Materials.Lapis);
        OreDictUnifier.registerOre(new ItemStack(Items.ENDER_EYE, 1), OrePrefix.gem, Materials.EnderEye);
        OreDictUnifier.registerOre(new ItemStack(Items.ENDER_PEARL, 1), OrePrefix.gem, Materials.EnderPearl);
        OreDictUnifier.registerOre(new ItemStack(Items.DIAMOND, 1), OrePrefix.gem, Materials.Diamond);
        OreDictUnifier.registerOre(new ItemStack(Items.EMERALD, 1), OrePrefix.gem, Materials.Emerald);
        OreDictUnifier.registerOre(new ItemStack(Items.COAL, 1, 0), OrePrefix.gem, Materials.Coal);
        OreDictUnifier.registerOre(new ItemStack(Items.COAL, 1, 1), OrePrefix.gem, Materials.Charcoal);
        OreDictUnifier.registerOre(new ItemStack(Items.QUARTZ, 1), OrePrefix.gem, Materials.NetherQuartz);
        OreDictUnifier.registerOre(new ItemStack(Items.NETHER_STAR, 1), OrePrefix.gem, Materials.NetherStar);
        OreDictUnifier.registerOre(new ItemStack(Items.GOLD_NUGGET, 1), OrePrefix.nugget, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Items.GOLD_INGOT, 1), OrePrefix.ingot, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Items.IRON_INGOT, 1), OrePrefix.ingot, Materials.Iron);
        OreDictUnifier.registerOre(new ItemStack(Items.PAPER, 1), OrePrefix.plate, Materials.Paper);
        OreDictUnifier.registerOre(new ItemStack(Items.SUGAR, 1), OrePrefix.dust, Materials.Sugar);
        OreDictUnifier.registerOre(new ItemStack(Items.STICK, 1), OrePrefix.stick, Materials.Wood);
        OreDictUnifier.registerOre(new ItemStack(Items.REDSTONE, 1), OrePrefix.dust, Materials.Redstone);
        OreDictUnifier.registerOre(new ItemStack(Items.GUNPOWDER, 1), OrePrefix.dust, Materials.Gunpowder);
        OreDictUnifier.registerOre(new ItemStack(Items.GLOWSTONE_DUST, 1), OrePrefix.dust, Materials.Glowstone);
        OreDictUnifier.registerOre(new ItemStack(Items.BLAZE_POWDER, 1), OrePrefix.dust, Materials.Blaze);
        OreDictUnifier.registerOre(new ItemStack(Items.BLAZE_ROD, 1), OrePrefix.stick, Materials.Blaze);
        OreDictUnifier.registerOre(new ItemStack(Blocks.IRON_BLOCK, 1, 0), OrePrefix.block, Materials.Iron);
        OreDictUnifier.registerOre(new ItemStack(Blocks.GOLD_BLOCK, 1, 0), OrePrefix.block, Materials.Gold);
        OreDictUnifier.registerOre(new ItemStack(Blocks.DIAMOND_BLOCK, 1, 0), OrePrefix.block, Materials.Diamond);
        OreDictUnifier.registerOre(new ItemStack(Blocks.EMERALD_BLOCK, 1, 0), OrePrefix.block, Materials.Emerald);
        OreDictUnifier.registerOre(new ItemStack(Blocks.LAPIS_BLOCK, 1, 0), OrePrefix.block, Materials.Lapis);
        OreDictUnifier.registerOre(new ItemStack(Blocks.COAL_BLOCK, 1, 0), OrePrefix.block, Materials.Coal);
        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_BLOCK, 1, 0), OrePrefix.block, Materials.Redstone);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 1), OrePrefix.stone, Materials.GraniteBlack);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 2), OrePrefix.stone, Materials.GraniteBlack);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 5), OrePrefix.stone, Materials.Andesite);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 6), OrePrefix.stone, Materials.Andesite);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 3), OrePrefix.stone, Materials.Diorite);
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, 4), OrePrefix.stone, Materials.Diorite);

        OreDictUnifier.registerOre(new ItemStack(Blocks.ANVIL, 1), "craftingAnvil");
        OreDictUnifier.registerOre(new ItemStack(Blocks.OBSIDIAN, 1, W), OrePrefix.stone, Materials.Obsidian);
        OreDictUnifier.registerOre(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, W), "stoneMossy");
        OreDictUnifier.registerOre(new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, W), "stoneCobble");
        OreDictUnifier.registerOre(new ItemStack(Blocks.COBBLESTONE, 1, W), "stoneCobble");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONE, 1, W), "stoneSmooth");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, W), "stoneBricks");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 1), "stoneMossy");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 2), "stoneCracked");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STONEBRICK, 1, 3), "stoneChiseled");
        OreDictUnifier.registerOre(new ItemStack(Blocks.NETHERRACK, 1, W), OrePrefix.stone, Materials.Netherrack);
        OreDictUnifier.registerOre(new ItemStack(Blocks.END_STONE, 1, W), OrePrefix.stone, Materials.Endstone);

        OreDictUnifier.registerOre(new ItemStack(Blocks.REDSTONE_TORCH, 1, W), "craftingRedstoneTorch");

        OreDictUnifier.registerOre(new ItemStack(Blocks.PISTON, 1, W), "craftingPiston");
        OreDictUnifier.registerOre(new ItemStack(Blocks.STICKY_PISTON, 1, W), "craftingPiston");

        OreDictUnifier.registerOre(new ItemStack(Blocks.CHEST, 1, W), "craftingChest");
        OreDictUnifier.registerOre(new ItemStack(Blocks.TRAPPED_CHEST, 1, W), "craftingChest");

        OreDictUnifier.registerOre(new ItemStack(Blocks.FURNACE, 1, W), "craftingFurnace");

        OreDictUnifier.registerOre(new ItemStack(Items.FEATHER, 1, W), "craftingFeather");

        OreDictUnifier.registerOre(new ItemStack(Items.WHEAT, 1, W), "itemWheat");
        OreDictUnifier.registerOre(new ItemStack(Items.PAPER, 1, W), "paperEmpty");
        OreDictUnifier.registerOre(new ItemStack(Items.MAP, 1, W), "paperMap");
        OreDictUnifier.registerOre(new ItemStack(Items.FILLED_MAP, 1, W), "paperMap");
        OreDictUnifier.registerOre(new ItemStack(Items.BOOK, 1, W), "bookEmpty");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITABLE_BOOK, 1, W), "bookWritable");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITTEN_BOOK, 1, W), "bookWritten");
        OreDictUnifier.registerOre(new ItemStack(Items.ENCHANTED_BOOK, 1, W), "bookEnchanted");
        OreDictUnifier.registerOre(new ItemStack(Items.BOOK, 1, W), "craftingBook");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITABLE_BOOK, 1, W), "craftingBook");
        OreDictUnifier.registerOre(new ItemStack(Items.WRITTEN_BOOK, 1, W), "craftingBook");
        OreDictUnifier.registerOre(new ItemStack(Items.ENCHANTED_BOOK, 1, W), "craftingBook");

    }
}
