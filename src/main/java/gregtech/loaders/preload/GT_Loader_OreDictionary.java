package gregtech.loaders.preload;

import gregtech.api.GregTech_API;
import gregtech.api.items.ItemList;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.items.OreDictNames;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import gregtech.api.util.GT_ModHandler;
import ic2.core.block.BlockTexGlass;
import ic2.core.block.wiring.CableType;
import ic2.core.item.block.ItemCable;
import ic2.core.item.type.CraftingItemType;
import ic2.core.item.type.IngotResourceType;
import ic2.core.ref.BlockName;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class GT_Loader_OreDictionary
        implements Runnable {
    public void run() {
        GTLog.out.println("GT_Mod: Register OreDict Entries of Non-GT-Items.");
        OreDictionaryUnifier.set(OrePrefix.cell, Materials.Empty, ItemList.Cell_Empty.get(1));
        OreDictionaryUnifier.set(OrePrefix.cell, Materials.Lava, ItemList.Cell_Lava.get(1));
        OreDictionaryUnifier.set(OrePrefix.cell, Materials.Lava, GT_ModHandler.getIC2Item(ItemName.fluid_cell, FluidRegistry.LAVA.getName(), 1));
        OreDictionaryUnifier.set(OrePrefix.cell, Materials.Water, ItemList.Cell_Water.get(1));
        OreDictionaryUnifier.set(OrePrefix.cell, Materials.Water, GT_ModHandler.getIC2Item(ItemName.fluid_cell, FluidRegistry.WATER.getName(), 1));
        OreDictionaryUnifier.set(OrePrefix.cell, Materials.Creosote, GT_ModHandler.getModItem("Railcraft", "fluid.creosote.cell", 1));


        OreDictionaryUnifier.set(OrePrefix.cell, Materials.UUMatter, GT_ModHandler.getIC2Item(ItemName.fluid_cell, FluidName.uu_matter, 1));
        OreDictionaryUnifier.set(OrePrefix.cell, Materials.ConstructionFoam, GT_ModHandler.getIC2Item(ItemName.fluid_cell, FluidName.construction_foam, 1));

        OreDictionaryUnifier.set(OrePrefix.bucket, Materials.Empty, new ItemStack(Items.BUCKET, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.bucket, Materials.Water, new ItemStack(Items.WATER_BUCKET, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.bucket, Materials.Lava, new ItemStack(Items.LAVA_BUCKET, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.bucket, Materials.Milk, new ItemStack(Items.MILK_BUCKET, 1, 0));

        OreDictionaryUnifier.set(OrePrefix.bottle, Materials.Empty, new ItemStack(Items.GLASS_BOTTLE, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.bottle, Materials.Water, new ItemStack(Items.POTIONITEM, 1, 0));

        OreDictionaryUnifier.set(OrePrefix.plateAlloy, Materials.Iridium, GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.iridium, 1));
        OreDictionaryUnifier.set(OrePrefix.plateAlloy, Materials.Advanced, GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.alloy, 1));
        OreDictionaryUnifier.set(OrePrefix.plateAlloy, Materials.Carbon, GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_plate, 1));

        OreDictionaryUnifier.set(OrePrefix.ore, Materials.Coal, new ItemStack(Blocks.COAL_ORE, 1));
        OreDictionaryUnifier.set(OrePrefix.ore, Materials.Iron, new ItemStack(Blocks.IRON_ORE, 1));
        OreDictionaryUnifier.set(OrePrefix.ore, Materials.Lapis, new ItemStack(Blocks.LAPIS_ORE, 1));
        OreDictionaryUnifier.set(OrePrefix.ore, Materials.Redstone, new ItemStack(Blocks.REDSTONE_ORE, 1));
        OreDictionaryUnifier.set(OrePrefix.ore, Materials.Redstone, new ItemStack(Blocks.LIT_REDSTONE_ORE, 1));
        OreDictionaryUnifier.set(OrePrefix.ore, Materials.Gold, new ItemStack(Blocks.GOLD_ORE, 1));
        OreDictionaryUnifier.set(OrePrefix.ore, Materials.Diamond, new ItemStack(Blocks.DIAMOND_ORE, 1));
        OreDictionaryUnifier.set(OrePrefix.ore, Materials.Emerald, new ItemStack(Blocks.EMERALD_ORE, 1));
        OreDictionaryUnifier.set(OrePrefix.ore, Materials.NetherQuartz, new ItemStack(Blocks.QUARTZ_ORE, 1));
        OreDictionaryUnifier.set(OrePrefix.ingot, Materials.Copper, GT_ModHandler.getIC2Item(ItemName.ingot, IngotResourceType.copper, 1));
        OreDictionaryUnifier.set(OrePrefix.ingot, Materials.Tin, GT_ModHandler.getIC2Item(ItemName.ingot, IngotResourceType.tin, 1));
        OreDictionaryUnifier.set(OrePrefix.ingot, Materials.Lead, GT_ModHandler.getIC2Item(ItemName.ingot, IngotResourceType.lead, 1));
        OreDictionaryUnifier.set(OrePrefix.ingot, Materials.Bronze, GT_ModHandler.getIC2Item(ItemName.ingot, IngotResourceType.bronze, 1));
        OreDictionaryUnifier.set(OrePrefix.ingot, Materials.Silver, GT_ModHandler.getIC2Item(ItemName.ingot, IngotResourceType.silver, 1));
        OreDictionaryUnifier.set(OrePrefix.gem, Materials.Lapis, new ItemStack(Items.DYE, 1, 4));
        OreDictionaryUnifier.set(OrePrefix.gem, Materials.EnderEye, new ItemStack(Items.ENDER_EYE, 1));
        OreDictionaryUnifier.set(OrePrefix.gem, Materials.EnderPearl, new ItemStack(Items.ENDER_PEARL, 1));
        OreDictionaryUnifier.set(OrePrefix.gem, Materials.Diamond, new ItemStack(Items.DIAMOND, 1));
        OreDictionaryUnifier.set(OrePrefix.gem, Materials.Emerald, new ItemStack(Items.EMERALD, 1));
        OreDictionaryUnifier.set(OrePrefix.gem, Materials.Coal, new ItemStack(Items.COAL, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.gem, Materials.Charcoal, new ItemStack(Items.COAL, 1, 1));
        OreDictionaryUnifier.set(OrePrefix.gem, Materials.NetherQuartz, new ItemStack(Items.QUARTZ, 1));
        OreDictionaryUnifier.set(OrePrefix.gem, Materials.NetherStar, new ItemStack(Items.NETHER_STAR, 1));
        OreDictionaryUnifier.set(OrePrefix.nugget, Materials.Gold, new ItemStack(Items.GOLD_NUGGET, 1));
        OreDictionaryUnifier.set(OrePrefix.ingot, Materials.Gold, new ItemStack(Items.GOLD_INGOT, 1));
        OreDictionaryUnifier.set(OrePrefix.ingot, Materials.Iron, new ItemStack(Items.IRON_INGOT, 1));
        OreDictionaryUnifier.set(OrePrefix.plate, Materials.Paper, new ItemStack(Items.PAPER, 1));
        OreDictionaryUnifier.set(OrePrefix.dust, Materials.Sugar, new ItemStack(Items.SUGAR, 1));
        OreDictionaryUnifier.set(OrePrefix.dust, Materials.Bone, ItemList.Dye_Bonemeal.get(1));
        OreDictionaryUnifier.set(OrePrefix.stick, Materials.Wood, new ItemStack(Items.STICK, 1));
        OreDictionaryUnifier.set(OrePrefix.dust, Materials.Redstone, new ItemStack(Items.REDSTONE, 1));
        OreDictionaryUnifier.set(OrePrefix.dust, Materials.Gunpowder, new ItemStack(Items.GUNPOWDER, 1));
        OreDictionaryUnifier.set(OrePrefix.dust, Materials.Glowstone, new ItemStack(Items.GLOWSTONE_DUST, 1));
        OreDictionaryUnifier.set(OrePrefix.dust, Materials.Blaze, new ItemStack(Items.BLAZE_POWDER, 1));
        OreDictionaryUnifier.set(OrePrefix.stick, Materials.Blaze, new ItemStack(Items.BLAZE_ROD, 1));
        OreDictionaryUnifier.set(OrePrefix.block, Materials.Iron, new ItemStack(Blocks.IRON_BLOCK, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.block, Materials.Gold, new ItemStack(Blocks.GOLD_BLOCK, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.block, Materials.Diamond, new ItemStack(Blocks.DIAMOND_BLOCK, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.block, Materials.Emerald, new ItemStack(Blocks.EMERALD_BLOCK, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.block, Materials.Lapis, new ItemStack(Blocks.LAPIS_BLOCK, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.block, Materials.Coal, new ItemStack(Blocks.COAL_BLOCK, 1, 0));
        OreDictionaryUnifier.set(OrePrefix.block, Materials.Redstone, new ItemStack(Blocks.REDSTONE_BLOCK, 1, 0));
        if (Blocks.ENDER_CHEST != null) {
            OreDictionaryUnifier.registerOre(OreDictNames.enderChest, new ItemStack(Blocks.ENDER_CHEST, 1));
        }
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.GraniteBlack, new ItemStack(Blocks.STONE, 1, 1));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.GraniteBlack, new ItemStack(Blocks.STONE, 1, 2));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Andesite, new ItemStack(Blocks.STONE, 1, 5));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Andesite, new ItemStack(Blocks.STONE, 1, 6));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Diorite, new ItemStack(Blocks.STONE, 1, 3));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Diorite, new ItemStack(Blocks.STONE, 1, 4));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingAnvil, new ItemStack(Blocks.ANVIL, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingAnvil, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.anvil", 1, 0));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingIndustrialDiamond, ItemList.IC2_Industrial_Diamond.get(1));
        OreDictionaryUnifier.registerOre(OrePrefix.dust, Materials.Wood, GT_ModHandler.getModItem("ThermalExpansion", "sawdust", 1));
        OreDictionaryUnifier.registerOre(OrePrefix.glass, Materials.Reinforced, GT_ModHandler.getIC2Item(BlockName.glass, BlockTexGlass.GlassType.reinforced, 1));
        OreDictionaryUnifier.registerOre(OrePrefix.glass, Materials.Reinforced, GT_ModHandler.getModItem("ThermalExpansion", "glassHardened", 1));

        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Basalt, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1, 6));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Marble, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.cube", 1, 7));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Basalt, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.brick.abyssal", 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Marble, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.brick.quarried", 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Obsidian, new ItemStack(Blocks.OBSIDIAN, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stoneMossy, new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stoneCobble, new ItemStack(Blocks.MOSSY_COBBLESTONE, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stoneCobble, new ItemStack(Blocks.COBBLESTONE, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stoneSmooth, new ItemStack(Blocks.STONE, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stoneBricks, new ItemStack(Blocks.STONEBRICK, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stoneMossy, new ItemStack(Blocks.STONEBRICK, 1, 1));
        OreDictionaryUnifier.registerOre(OrePrefix.stoneCracked, new ItemStack(Blocks.STONEBRICK, 1, 2));
        OreDictionaryUnifier.registerOre(OrePrefix.stoneChiseled, new ItemStack(Blocks.STONEBRICK, 1, 3));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Sand, new ItemStack(Blocks.SANDSTONE, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Netherrack, new ItemStack(Blocks.NETHERRACK, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.NetherBrick, new ItemStack(Blocks.NETHER_BRICK, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.stone, Materials.Endstone, new ItemStack(Blocks.END_STONE, 1, 32767));

        OreDictionaryUnifier.registerOre("paperResearchFragment", GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 1, 9));
        OreDictionaryUnifier.registerOre("itemCertusQuartz", GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 1));
        OreDictionaryUnifier.registerOre("itemCertusQuartz", GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 10));
        OreDictionaryUnifier.registerOre("itemNetherQuartz", GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 11));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingQuartz, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingQuartz, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 10));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingQuartz, GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 11));
        OreDictionaryUnifier.registerOre("cropLemon", ItemList.FR_Lemon.get(1));
        OreDictionaryUnifier.registerOre("cropCoffee", ItemList.IC2_CoffeeBeans.get(1));
        OreDictionaryUnifier.registerOre("cropPotato", ItemList.Food_Raw_Potato.get(1));
        OreDictionaryUnifier.registerOre("calclavia:BATTERY", GT_ModHandler.getIC2Item(ItemName.re_battery, 1));
        OreDictionaryUnifier.registerOre("calclavia:BATTERY", GT_ModHandler.getIC2Item(ItemName.re_battery, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.battery, Materials.Basic, GT_ModHandler.getIC2Item(ItemName.re_battery, 1));
        OreDictionaryUnifier.registerOre(OrePrefix.battery, Materials.Basic, GT_ModHandler.getIC2Item(ItemName.re_battery, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.battery, Materials.Advanced, GT_ModHandler.getIC2Item(ItemName.advanced_re_battery, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.battery, Materials.Elite, GT_ModHandler.getIC2Item(ItemName.energy_crystal, 1, 32767));
        OreDictionaryUnifier.registerOre(OrePrefix.battery, Materials.Master, GT_ModHandler.getIC2Item(ItemName.lapotron_crystal, 1, 32767));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingWireCopper, ItemCable.getCable(CableType.copper, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingWireGold, ItemCable.getCable(CableType.gold, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingWireIron, ItemCable.getCable(CableType.iron, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingWireTin, ItemCable.getCable(CableType.tin, 1));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingRedstoneTorch, new ItemStack(Blocks.REDSTONE_TORCH, 1, 32767));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingRedstoneTorch, new ItemStack(Blocks.UNLIT_REDSTONE_TORCH, 1, 32767));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingWorkBench, new ItemStack(Blocks.CRAFTING_TABLE, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingWorkBench, new ItemStack(GregTech_API.sBlockMachines, 1, 16));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingPiston, new ItemStack(Blocks.PISTON, 1, 32767));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingPiston, new ItemStack(Blocks.STICKY_PISTON, 1, 32767));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingSafe, new ItemStack(GregTech_API.sBlockMachines, 1, 45));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingSafe, GT_ModHandler.getIC2TEItem(TeBlock.personal_chest, 1));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingChest, new ItemStack(Blocks.CHEST, 1, 32767));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingChest, new ItemStack(Blocks.TRAPPED_CHEST, 1, 32767));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingFurnace, new ItemStack(Blocks.FURNACE, 1, 32767));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingFurnace, new ItemStack(Blocks.LIT_FURNACE, 1, 32767));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingPump, GT_ModHandler.getIC2TEItem(TeBlock.pump, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingElectromagnet, GT_ModHandler.getIC2TEItem(TeBlock.magnetizer, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingTeleporter, GT_ModHandler.getIC2TEItem(TeBlock.teleporter, 1));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingMacerator, GT_ModHandler.getIC2TEItem(TeBlock.macerator, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingMacerator, new ItemStack(GregTech_API.sBlockMachines, 1, 50));


        OreDictionaryUnifier.registerOre(OreDictNames.craftingExtractor, GT_ModHandler.getIC2TEItem(TeBlock.extractor, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingExtractor, new ItemStack(GregTech_API.sBlockMachines, 1, 51));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingCompressor, GT_ModHandler.getIC2TEItem(TeBlock.compressor, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingCompressor, new ItemStack(GregTech_API.sBlockMachines, 1, 52));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingRecycler, GT_ModHandler.getIC2TEItem(TeBlock.recycler, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingRecycler, new ItemStack(GregTech_API.sBlockMachines, 1, 53));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingIronFurnace, GT_ModHandler.getIC2TEItem(TeBlock.iron_furnace, 1));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingCentrifuge, new ItemStack(GregTech_API.sBlockMachines, 1, 62));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingInductionFurnace, GT_ModHandler.getIC2TEItem(TeBlock.induction_furnace, 1));


        OreDictionaryUnifier.registerOre(OreDictNames.craftingElectricFurnace, GT_ModHandler.getIC2TEItem(TeBlock.electric_furnace, 1));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingElectricFurnace, new ItemStack(GregTech_API.sBlockMachines, 1, 54));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingGenerator, GT_ModHandler.getIC2TEItem(TeBlock.generator, 1));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingGeothermalGenerator, GT_ModHandler.getIC2TEItem(TeBlock.geo_generator, 1));

        OreDictionaryUnifier.registerOre(OreDictNames.craftingFeather, new ItemStack(Items.FEATHER, 1, 32767));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingFeather, GT_ModHandler.getModItem("TwilightForest", "item.tfFeather", 1, 32767));

        OreDictionaryUnifier.registerOre("itemWheat", new ItemStack(Items.WHEAT, 1, 32767));
        OreDictionaryUnifier.registerOre("paperEmpty", new ItemStack(Items.PAPER, 1, 32767));
        OreDictionaryUnifier.registerOre("paperMap", new ItemStack(Items.MAP, 1, 32767));
        OreDictionaryUnifier.registerOre("paperMap", new ItemStack(Items.FILLED_MAP, 1, 32767));
        OreDictionaryUnifier.registerOre("bookEmpty", new ItemStack(Items.BOOK, 1, 32767));
        OreDictionaryUnifier.registerOre("bookWritable", new ItemStack(Items.WRITABLE_BOOK, 1, 32767));
        OreDictionaryUnifier.registerOre("bookWritten", new ItemStack(Items.WRITTEN_BOOK, 1, 32767));
        OreDictionaryUnifier.registerOre("bookEnchanted", new ItemStack(Items.ENCHANTED_BOOK, 1, 32767));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingBook, new ItemStack(Items.BOOK, 1, 32767));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingBook, new ItemStack(Items.WRITABLE_BOOK, 1, 32767));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingBook, new ItemStack(Items.WRITTEN_BOOK, 1, 32767));
        OreDictionaryUnifier.registerOre(OreDictNames.craftingBook, new ItemStack(Items.ENCHANTED_BOOK, 1, 32767));

        OreDictionaryUnifier.registerOre(OrePrefix.circuit, Materials.Basic, GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.circuit, 1));
        OreDictionaryUnifier.registerOre(OrePrefix.circuit, Materials.Advanced, GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.advanced_circuit, 1));
    }
}
