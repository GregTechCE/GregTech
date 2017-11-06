package gregtech.loaders.postload;

import gregtech.GregTechMod;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.*;
import ic2.api.recipe.ILiquidHeatExchangerManager.HeatExchangeProperty;
import ic2.api.recipe.Recipes;
import ic2.core.block.BlockIC2Fence;
import ic2.core.block.type.ResourceBlock;
import ic2.core.block.wiring.CableType;
import ic2.core.item.block.ItemCable;
import ic2.core.item.type.CraftingItemType;
import ic2.core.item.type.CropResItemType;
import ic2.core.item.type.NuclearResourceType;
import ic2.core.ref.BlockName;
import ic2.core.ref.FluidName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import static gregtech.api.GTValues.W;

public class GT_MachineRecipeLoader implements Runnable {

    @Override
    public void run() {
    }

/*
        private final MaterialStack[][] mAlloySmelterList = {
            {new MaterialStack(Materials.Tetrahedrite, 3L), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.Bronze, 3L)},
            {new MaterialStack(Materials.Tetrahedrite, 3L), new MaterialStack(Materials.Zinc, 1), new MaterialStack(Materials.Brass, 3L)},
            {new MaterialStack(Materials.Copper, 3L), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.Bronze, 4L)},
            {new MaterialStack(Materials.Copper, 3L), new MaterialStack(Materials.Zinc, 1), new MaterialStack(Materials.Brass, 4L)},
            {new MaterialStack(Materials.Copper, 1), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Cupronickel, 2L)},
            {new MaterialStack(Materials.Copper, 1), new MaterialStack(Materials.Redstone, 4L), new MaterialStack(Materials.RedAlloy, 1)},
            {new MaterialStack(Materials.AnnealedCopper, 3L), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.Bronze, 4L)},
            {new MaterialStack(Materials.AnnealedCopper, 3L), new MaterialStack(Materials.Zinc, 1), new MaterialStack(Materials.Brass, 4L)},
            {new MaterialStack(Materials.AnnealedCopper, 1), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Cupronickel, 2L)},
            {new MaterialStack(Materials.AnnealedCopper, 1), new MaterialStack(Materials.Redstone, 4L), new MaterialStack(Materials.RedAlloy, 1)},
            {new MaterialStack(Materials.Iron, 1), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.TinAlloy, 2L)},
            {new MaterialStack(Materials.WroughtIron, 1), new MaterialStack(Materials.Tin, 1), new MaterialStack(Materials.TinAlloy, 2L)},
            {new MaterialStack(Materials.Iron, 2L), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Invar, 3L)},
            {new MaterialStack(Materials.WroughtIron, 2L), new MaterialStack(Materials.Nickel, 1), new MaterialStack(Materials.Invar, 3L)},
            {new MaterialStack(Materials.Tin, 9L), new MaterialStack(Materials.Antimony, 1), new MaterialStack(Materials.SolderingAlloy, 10L)},
            {new MaterialStack(Materials.Lead, 4L), new MaterialStack(Materials.Antimony, 1), new MaterialStack(Materials.BatteryAlloy, 5L)},
            {new MaterialStack(Materials.Gold, 1), new MaterialStack(Materials.Silver, 1), new MaterialStack(Materials.Electrum, 2L)},
            {new MaterialStack(Materials.Magnesium, 1), new MaterialStack(Materials.Aluminium, 2L), new MaterialStack(Materials.Magnalium, 3L)}
    };
    private final static String aTextAE = "appliedenergistics2"; private final static String aTextAEMM = "item.ItemMultiMaterial"; private final static String aTextForestry = "Forestry";
    private final static String aTextEBXL = "ExtrabiomesXL"; private final static String aTextTCGTPage = "gt.research.page.1.";

    public void run() {
        GTLog.logger.info("Adding non-OreDict Machine Recipes.");

        GTValues.RA.addFluidExtractionRecipe(new ItemStack(Items.WHEAT_SEEDS, 1, W), GTValues.NI, Materials.SeedOil.getFluid(5L), 10000, 32, 2);
        GTValues.RA.addFluidExtractionRecipe(new ItemStack(Items.MELON_SEEDS, 1, W), GTValues.NI, Materials.SeedOil.getFluid(3L), 10000, 32, 2);
        GTValues.RA.addFluidExtractionRecipe(new ItemStack(Items.PUMPKIN_SEEDS, 1, W), GTValues.NI, Materials.SeedOil.getFluid(6L), 10000, 32, 2);

        GTValues.RA.addArcFurnaceRecipe(ItemList.Block_BronzePlate.get(1, new Object[]{}), new ItemStack[]{ OreDictUnifier.get(OrePrefix.ingot,Materials.Bronze,4), OreDictUnifier.get(OrePrefix.dust,Materials.Stone,1)}, null, 160, 96);
        GTValues.RA.addArcFurnaceRecipe(ItemList.Block_IridiumTungstensteel.get(1, new Object[]{}), new ItemStack[]{ OreDictUnifier.get(OrePrefix.ingot,Materials.Bronze,4), OreDictUnifier.get(OrePrefix.dust,Materials.Stone,1)}, null, 160, 96);
        GTValues.RA.addArcFurnaceRecipe(ItemList.Block_TungstenSteelReinforced.get(1), new ItemStack[]{ OreDictUnifier.get(OrePrefix.ingot,Materials.TungstenSteel,2), OreDictUnifier.get(OrePrefix.dust,Materials.Concrete,1)}, null, 160, 96);

        //Temporary until circuit overhaul
        GTValues.RA.addAlloySmelterRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Rubber, 2), OreDictUnifier.get(OrePrefix.wireGt01, Materials.Copper, 1), OreDictUnifier.get(OrePrefix.cableGt01, Materials.Copper, 1), 100, 16);

        GTValues.RA.addPrinterRecipe(OreDictUnifier.get(OrePrefix.plateDouble, Materials.Paper, 1), FluidRegistry.getFluidStack("squidink", 36), GTValues.NI, ItemList.Paper_Punch_Card_Empty.get(1), 100, 2);
        GTValues.RA.addPrinterRecipe(ItemList.Paper_Punch_Card_Empty.get(1), FluidRegistry.getFluidStack("squidink", 36), ItemList.Tool_DataStick.getWithName(0L, "With Punch Card Data"), ItemList.Paper_Punch_Card_Encoded.get(1), 100, 2);
        GTValues.RA.addPrinterRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3L), FluidRegistry.getFluidStack("squidink", 144), ItemList.Tool_DataStick.getWithName(0L, "With Scanned Book Data"), ItemList.Paper_Printed_Pages.get(1), 400, 2);
        GTValues.RA.addPrinterRecipe(new ItemStack(Items.MAP, 1, 32767), FluidRegistry.getFluidStack("squidink", 144), ItemList.Tool_DataStick.getWithName(0L, "With Scanned Map Data"), new ItemStack(Items.FILLED_MAP, 1, 0), 400, 2);
        GTValues.RA.addPrinterRecipe(new ItemStack(Items.BOOK, 1, 32767), FluidRegistry.getFluidStack("squidink", 144), GTValues.NI, GTUtility.getWrittenBook("Manual_Printer", ItemList.Book_Written_01.get(1)), 400, 2);
        for (OrePrefix tPrefix : Arrays.asList(new OrePrefix[]{OrePrefix.dust, OrePrefix.dustSmall, OrePrefix.dustTiny})) {
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.EnderPearl, 1), OreDictUnifier.get(tPrefix, Materials.Blaze, 1), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.EnderEye, 1 * tPrefix.mMaterialAmount), (int) (100L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Gold, 1), OreDictUnifier.get(tPrefix, Materials.Silver, 1), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.Electrum, 2L * tPrefix.mMaterialAmount), (int) (200L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Iron, 2L), OreDictUnifier.get(tPrefix, Materials.Nickel, 1), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.Invar, 3L * tPrefix.mMaterialAmount), (int) (300L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Iron, 4L), OreDictUnifier.get(tPrefix, Materials.Invar, 3L), OreDictUnifier.get(tPrefix, Materials.Manganese, 1), OreDictUnifier.get(tPrefix, Materials.Chrome, 1), GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.StainlessSteel, 9L * tPrefix.mMaterialAmount), (int) (900L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Iron, 1), OreDictUnifier.get(tPrefix, Materials.Aluminium, 1), OreDictUnifier.get(tPrefix, Materials.Chrome, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.Kanthal, 3L * tPrefix.mMaterialAmount), (int) (300L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Copper, 3L), OreDictUnifier.get(tPrefix, Materials.Barium, 2L), OreDictUnifier.get(tPrefix, Materials.Yttrium, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.YttriumBariumCuprate, 6L * tPrefix.mMaterialAmount), (int) (600L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Copper, 3L), OreDictUnifier.get(tPrefix, Materials.Zinc, 1), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.Brass, 4L * tPrefix.mMaterialAmount), (int) (400L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Copper, 3L), OreDictUnifier.get(tPrefix, Materials.Tin, 1), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.Bronze, 4L * tPrefix.mMaterialAmount), (int) (400L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Copper, 1), OreDictUnifier.get(tPrefix, Materials.Nickel, 1), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.Cupronickel, 2L * tPrefix.mMaterialAmount), (int) (200L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Copper, 1), OreDictUnifier.get(tPrefix, Materials.Gold, 4L), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.RoseGold, 5L * tPrefix.mMaterialAmount), (int) (500L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Copper, 1), OreDictUnifier.get(tPrefix, Materials.Silver, 4L), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.SterlingSilver, 5L * tPrefix.mMaterialAmount), (int) (500L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Copper, 3L), OreDictUnifier.get(tPrefix, Materials.Electrum, 2L), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.BlackBronze, 5L * tPrefix.mMaterialAmount), (int) (500L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Bismuth, 1), OreDictUnifier.get(tPrefix, Materials.Brass, 4L), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.BismuthBronze, 5L * tPrefix.mMaterialAmount), (int) (500L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.BlackBronze, 1), OreDictUnifier.get(tPrefix, Materials.Nickel, 1), OreDictUnifier.get(tPrefix, Materials.Steel, 3L), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.BlackSteel, 5L * tPrefix.mMaterialAmount), (int) (500L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.SterlingSilver, 1), OreDictUnifier.get(tPrefix, Materials.BismuthBronze, 1), OreDictUnifier.get(tPrefix, Materials.BlackSteel, 4L), OreDictUnifier.get(tPrefix, Materials.Steel, 2L), GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.RedSteel, 8L * tPrefix.mMaterialAmount), (int) (800L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.RoseGold, 1), OreDictUnifier.get(tPrefix, Materials.Brass, 1), OreDictUnifier.get(tPrefix, Materials.BlackSteel, 4L), OreDictUnifier.get(tPrefix, Materials.Steel, 2L), GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.BlueSteel, 8L * tPrefix.mMaterialAmount), (int) (800L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Cobalt, 5L), OreDictUnifier.get(tPrefix, Materials.Chrome, 2L), OreDictUnifier.get(tPrefix, Materials.Nickel, 1), OreDictUnifier.get(tPrefix, Materials.Molybdenum, 1), GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.Ultimet, 9L * tPrefix.mMaterialAmount), (int) (900L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Brass, 7L), OreDictUnifier.get(tPrefix, Materials.Aluminium, 1), OreDictUnifier.get(tPrefix, Materials.Cobalt, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.CobaltBrass, 9L * tPrefix.mMaterialAmount), (int) (900L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Saltpeter, 2L), OreDictUnifier.get(tPrefix, Materials.Sulfur, 1), OreDictUnifier.get(tPrefix, Materials.Coal, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.Gunpowder, 4L * tPrefix.mMaterialAmount), (int) (400L * tPrefix.mMaterialAmount / 3628800L), 8);
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(tPrefix, Materials.Saltpeter, 2L), OreDictUnifier.get(tPrefix, Materials.Sulfur, 1), OreDictUnifier.get(tPrefix, Materials.Charcoal, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.getDust(Materials.Gunpowder, 3L * tPrefix.mMaterialAmount), (int) (300L * tPrefix.mMaterialAmount / 3628800L), 8);
        }
        GTValues.RA.addMixerRecipe(new ItemStack(Items.ROTTEN_FLESH, 1, 0), new ItemStack(Items.FERMENTED_SPIDER_EYE, 1, 0), ItemList.IC2_Scrap.get(1), OreDictUnifier.get(OrePrefix.dust, Materials.MeatRaw, 1), FluidRegistry.getFluidStack("potion.purpledrink", 750), FluidRegistry.getFluidStack("sludge", 1000), ItemList.Food_Chum.get(4L), 128, 24);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Wheat, 1), GTValues.NI, GTValues.NI, GTValues.NI, Materials.Water.getFluid(1000L), GTValues.NF, ItemList.Food_Dough.get(2L), 32, 8);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Chili, 1), ItemList.Food_PotatoChips.get(1), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, ItemList.Food_ChiliChips.get(1), 32, 8);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Clay, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Stone, 3L), GTValues.NI, GTValues.NI, Materials.Water.getFluid(500L), Materials.Concrete.getMolten(576L), GTValues.NI, 20, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Redstone, 5L), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Ruby, 4L), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, ItemList.IC2_Energium_Dust.get(1), 100, 8);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 5L), OreDictUnifier.get(OrePrefix.dust, Materials.Ruby, 4L), GTValues.NI, GTValues.NI, GTValues.NF, GTValues.NF, ItemList.IC2_Energium_Dust.get(9L), 900, 8);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), new ItemStack(Blocks.BROWN_MUSHROOM, 1), new ItemStack(Items.SPIDER_EYE, 1), GTValues.NI, GTValues.NF, GTValues.NF, new ItemStack(Items.FERMENTED_SPIDER_EYE, 1), 100, 8);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.dust, Materials.LiveRoot, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dust, Materials.IronWood, 2L), 100, 8);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 9L), OreDictUnifier.get(OrePrefix.dust, Materials.LiveRoot, 9L), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dust, Materials.IronWood, 18L), 900, 8);
        GTValues.RA.addMixerRecipe(GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), OreDictUnifier.get(OrePrefix.gem, Materials.NetherQuartz, 1), GTValues.NI, Materials.Water.getFluid(500L), GTValues.NF, OreDictUnifier.get(OrePrefix.gem, Materials.Fluix, 2L), 20, 16);
        GTValues.RA.addMixerRecipe(GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), OreDictUnifier.get(OrePrefix.gem, Materials.NetherQuartz, 1), GTValues.NI, GT_ModHandler.getDistilledWater(500L), GTValues.NF, OreDictUnifier.get(OrePrefix.gem, Materials.Fluix, 2L), 20, 16);
        GTValues.RA.addMixerRecipe(ItemList.IC2_Fertilizer.get(1), new ItemStack(Blocks.DIRT, 8, 32767), GTValues.NI, GTValues.NI, Materials.Water.getFluid(1000L), GTValues.NF, GT_ModHandler.getModItem(aTextForestry, "soil", 8, 0), 64, 16);
        GTValues.RA.addMixerRecipe(ItemList.FR_Fertilizer.get(1), new ItemStack(Blocks.DIRT, 8, 32767), GTValues.NI, GTValues.NI, Materials.Water.getFluid(1000L), GTValues.NF, GT_ModHandler.getModItem(aTextForestry, "soil", 8, 0), 64, 16);
        GTValues.RA.addMixerRecipe(ItemList.FR_Compost.get(1), new ItemStack(Blocks.DIRT, 8, 32767), GTValues.NI, GTValues.NI, Materials.Water.getFluid(1000L), GTValues.NF, GT_ModHandler.getModItem(aTextForestry, "soil", 8, 0), 64, 16);
        GTValues.RA.addMixerRecipe(ItemList.FR_Mulch.get(1), new ItemStack(Blocks.DIRT, 8, 32767), GTValues.NI, GTValues.NI, Materials.Water.getFluid(1000L), GTValues.NF, GT_ModHandler.getModItem(aTextForestry, "soil", 9, 0), 64, 16);
        GTValues.RA.addMixerRecipe(new ItemStack(Blocks.SAND, 1, 32767), new ItemStack(Blocks.DIRT, 1, 32767), GTValues.NI, GTValues.NI, Materials.Water.getFluid(250L), GTValues.NF, GT_ModHandler.getModItem(aTextForestry, "soil", 2, 1), 16, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.LightFuel, 5L), OreDictUnifier.get(OrePrefix.cell, Materials.HeavyFuel, 1), null, null, null, null, OreDictUnifier.get(OrePrefix.cell, Materials.Fuel, 6L), 16, 16);
        GTValues.RA.addMixerRecipe(ItemList.Cell_Water.get(5L), OreDictUnifier.get(OrePrefix.dust, Materials.Stone, 1), null, null, Materials.Lubricant.getFluid(20), new FluidStack(ItemList.sDrillingFluid, 5000), ItemList.Cell_Empty.get(5), 64, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Lapis, 1), null, null, null, Materials.Water.getFluid(125), FluidRegistry.getFluidStack("ic2coolant", 125), null, 256, 48);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Lapis, 1), null, null, null, GT_ModHandler.getDistilledWater(1000), FluidRegistry.getFluidStack("ic2coolant", 1000), null, 256, 48);

        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.Creosote.getFluid(1000), null, ItemList.SFMixture.get(8), 1600, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Lithium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.Creosote.getFluid(1000), null, ItemList.SFMixture.get(8), 1600, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Caesium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.Creosote.getFluid(1000), null, ItemList.SFMixture.get(12), 1600, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.Lubricant.getFluid(300), null, ItemList.SFMixture.get(8), 1200, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Lithium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.Lubricant.getFluid(300), null, ItemList.SFMixture.get(8), 1200, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Caesium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.Lubricant.getFluid(300), null, ItemList.SFMixture.get(12), 1200, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.Glue.getFluid(333), null, ItemList.SFMixture.get(8), 800, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Lithium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.Glue.getFluid(333), null, ItemList.SFMixture.get(8), 800, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Caesium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.Glue.getFluid(333), null, ItemList.SFMixture.get(12), 800, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.McGuffium239.getFluid(10), null, ItemList.SFMixture.get(64), 400, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Lithium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.McGuffium239.getFluid(10), null, ItemList.SFMixture.get(64), 400, 16);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Caesium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 4L), null, Materials.McGuffium239.getFluid(10), null, ItemList.SFMixture.get(64), 400, 16);

        GTValues.RA.addMixerRecipe(ItemList.SFMixture.get(2, new Object[]{}), OreDictUnifier.get(OrePrefix.dustTiny, Materials.EnderEye, 1L), null, null, Materials.Mercury.getFluid(50), null, ItemList.MSFMixture.get(2, new Object[]{}), 100, 64);
        GTValues.RA.addMixerRecipe(ItemList.SFMixture.get(2, new Object[]{}), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Blaze, 1L), null, null, Materials.Mercury.getFluid(50), null, ItemList.MSFMixture.get(2, new Object[]{}), 100, 64);

        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Lignite,1), ItemList.MSFMixture.get(6, new Object[]{}),  OreDictUnifier.get(OrePrefix.dustTiny, Materials.Diamond, 1L), null, Materials.NitroFuel.getFluid(1000), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Charcoal,1), ItemList.MSFMixture.get(4, new Object[]{}), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Diamond, 1L), null, Materials.NitroFuel.getFluid(800), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Coal,1), ItemList.MSFMixture.get(2, new Object[]{}),     OreDictUnifier.get(OrePrefix.dustTiny, Materials.Diamond, 1L), null, Materials.NitroFuel.getFluid(500), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Lignite,1), ItemList.MSFMixture.get(6, new Object[]{}),  OreDictUnifier.get(OrePrefix.dustTiny, Materials.Emerald, 1L), null, Materials.NitroFuel.getFluid(1000), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Charcoal,1), ItemList.MSFMixture.get(4, new Object[]{}), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Emerald, 1L), null, Materials.NitroFuel.getFluid(800), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Coal,1), ItemList.MSFMixture.get(2, new Object[]{}),     OreDictUnifier.get(OrePrefix.dustTiny, Materials.Emerald, 1L), null, Materials.NitroFuel.getFluid(500), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Lignite,1), ItemList.MSFMixture.get(6, new Object[]{}),  OreDictUnifier.get(OrePrefix.dustTiny, Materials.Sapphire, 1L), null, Materials.NitroFuel.getFluid(1000), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Charcoal,1), ItemList.MSFMixture.get(4, new Object[]{}), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Sapphire, 1L), null, Materials.NitroFuel.getFluid(800), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Coal,1), ItemList.MSFMixture.get(2, new Object[]{}),     OreDictUnifier.get(OrePrefix.dustTiny, Materials.Sapphire, 1L), null, Materials.NitroFuel.getFluid(500), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Lignite,1), ItemList.MSFMixture.get(6, new Object[]{}),  OreDictUnifier.get(OrePrefix.dustTiny, Materials.GreenSapphire, 1L), null, Materials.NitroFuel.getFluid(1000), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Charcoal,1), ItemList.MSFMixture.get(4, new Object[]{}), OreDictUnifier.get(OrePrefix.dustTiny, Materials.GreenSapphire, 1L), null, Materials.NitroFuel.getFluid(800), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Coal,1), ItemList.MSFMixture.get(2, new Object[]{}),     OreDictUnifier.get(OrePrefix.dustTiny, Materials.GreenSapphire, 1L), null, Materials.NitroFuel.getFluid(500), null, ItemList.Block_MSSFUEL.get(1, new Object[]{}), 120, 96);

        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Lignite,1), ItemList.SFMixture.get(6), null, null, Materials.NitroFuel.getFluid(1000), null, ItemList.Block_SSFUEL.get(1), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Charcoal,1), ItemList.SFMixture.get(4), null, null, Materials.NitroFuel.getFluid(800), null, ItemList.Block_SSFUEL.get(1), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Coal,1), ItemList.SFMixture.get(2), null, null, Materials.NitroFuel.getFluid(500), null, ItemList.Block_SSFUEL.get(1), 120, 96);

        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Lignite,1), ItemList.SFMixture.get(6), null, null, Materials.HeavyFuel.getFluid(1500), null, ItemList.Block_SSFUEL.get(1), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Charcoal,1), ItemList.SFMixture.get(4), null, null, Materials.HeavyFuel.getFluid(1200), null, ItemList.Block_SSFUEL.get(1), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Coal,1), ItemList.SFMixture.get(2), null, null, Materials.HeavyFuel.getFluid(750), null, ItemList.Block_SSFUEL.get(1), 120, 96);

        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Lignite,1), ItemList.SFMixture.get(6), null, null, Materials.LPG.getFluid(1500), null, ItemList.Block_SSFUEL.get(1), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Charcoal,1), ItemList.SFMixture.get(4), null, null, Materials.LPG.getFluid(1200), null, ItemList.Block_SSFUEL.get(1), 120, 96);
        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.block,Materials.Coal,1), ItemList.SFMixture.get(2), null, null, Materials.LPG.getFluid(750), null, ItemList.Block_SSFUEL.get(1), 120, 96);

//        if(Loader.isModLoaded("Railcraft")){
//        GTValues.RA.addMixerRecipe(EnumCube.COKE_BLOCK.getItem(), ItemList.SFMixture.get(1), null, null, Materials.NitroFuel.getFluid(250), null, ItemList.Block_SSFUEL.get(1), 120, 96);
//        GTValues.RA.addMixerRecipe(EnumCube.COKE_BLOCK.getItem(), ItemList.SFMixture.get(1), null, null, Materials.HeavyFuel.getFluid(375), null, ItemList.Block_SSFUEL.get(1), 120, 96);
//        GTValues.RA.addMixerRecipe(EnumCube.COKE_BLOCK.getItem(), ItemList.SFMixture.get(1), null, null, Materials.LPG.getFluid(375), null, ItemList.Block_SSFUEL.get(1), 120, 96);
//        if(Loader.isModLoaded("Thaumcraft")){
//        GTValues.RA.addMixerRecipe(EnumCube.COKE_BLOCK.getItem(), ItemList.MSFMixture.get(1), GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 4), null, Materials.NitroFuel.getFluid(250), null, ItemList.Block_MSSFUEL.get(1), 120, 96);
//        GTValues.RA.addMixerRecipe(EnumCube.COKE_BLOCK.getItem(), ItemList.MSFMixture.get(1), GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 4), null, Materials.HeavyFuel.getFluid(375), null, ItemList.Block_MSSFUEL.get(1), 120, 96);
//        GTValues.RA.addMixerRecipe(EnumCube.COKE_BLOCK.getItem(), ItemList.MSFMixture.get(1), GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 4), null, Materials.LPG.getFluid(375), null, ItemList.Block_MSSFUEL.get(1), 120, 96);
//        }}
        GTValues.RA.addExtruderRecipe(ItemList.FR_Wax.get(1), ItemList.Shape_Extruder_Cell.get(0L), ItemList.FR_WaxCapsule.get(1), 64, 16);
        GTValues.RA.addExtruderRecipe(ItemList.FR_RefractoryWax.get(1), ItemList.Shape_Extruder_Cell.get(0L), ItemList.FR_RefractoryCapsule.get(1), 128, 16);

        GTValues.RA.addFluidCannerRecipe(ItemList.Battery_Hull_LV.get(1), ItemList.IC2_ReBattery.get(1), Materials.Redstone.getMolten(288L), GTValues.NF);
        GTValues.RA.addFluidCannerRecipe(ItemList.Battery_Hull_LV.get(1), ItemList.Battery_SU_LV_Mercury.getWithCharge(1, Integer.MAX_VALUE), Materials.Mercury.getFluid(1000L), GTValues.NF);
        GTValues.RA.addFluidCannerRecipe(ItemList.Battery_Hull_MV.get(1), ItemList.Battery_SU_MV_Mercury.getWithCharge(1, Integer.MAX_VALUE), Materials.Mercury.getFluid(4000L), GTValues.NF);
        GTValues.RA.addFluidCannerRecipe(ItemList.Battery_Hull_HV.get(1), ItemList.Battery_SU_HV_Mercury.getWithCharge(1, Integer.MAX_VALUE), Materials.Mercury.getFluid(16000L), GTValues.NF);
        GTValues.RA.addFluidCannerRecipe(ItemList.Battery_Hull_LV.get(1), ItemList.Battery_SU_LV_SulfuricAcid.getWithCharge(1, Integer.MAX_VALUE), Materials.SulfuricAcid.getFluid(1000L), GTValues.NF);
        GTValues.RA.addFluidCannerRecipe(ItemList.Battery_Hull_MV.get(1), ItemList.Battery_SU_MV_SulfuricAcid.getWithCharge(1, Integer.MAX_VALUE), Materials.SulfuricAcid.getFluid(4000L), GTValues.NF);
        GTValues.RA.addFluidCannerRecipe(ItemList.Battery_Hull_HV.get(1), ItemList.Battery_SU_HV_SulfuricAcid.getWithCharge(1, Integer.MAX_VALUE), Materials.SulfuricAcid.getFluid(16000L), GTValues.NF);
        GTValues.RA.addFluidCannerRecipe(ItemList.TF_Vial_FieryTears.get(1), ItemList.Bottle_Empty.get(1), GTValues.NF, Materials.FierySteel.getFluid(250L));

        Materials tMaterial = Materials.Iron;
        if (tMaterial.mStandardMoltenFluid != null) {
            GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Iron.get(1), 16, 8);
        }
        tMaterial = Materials.WroughtIron;
        if (tMaterial.mStandardMoltenFluid != null) {
            GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Iron.get(1), 16, 8);
        }
        tMaterial = Materials.Gold;
        if (tMaterial.mStandardMoltenFluid != null) {
            GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Gold.get(1), 16, 8);
        }
        tMaterial = Materials.Bronze;
        if (tMaterial.mStandardMoltenFluid != null) {
            GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Bronze.get(1), 16, 8);
        }
        tMaterial = Materials.Copper;
        if (tMaterial.mStandardMoltenFluid != null) {
            GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Copper.get(1), 16, 8);
        }
        tMaterial = Materials.AnnealedCopper;
        if (tMaterial.mStandardMoltenFluid != null) {
            GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Copper.get(1), 16, 8);
        }
        tMaterial = Materials.Tin;
        if (tMaterial.mStandardMoltenFluid != null) {
            GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Tin.get(1), 16, 8);
        }
        tMaterial = Materials.Lead;
        if (tMaterial.mStandardMoltenFluid != null) {
            GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Lead.get(1), 16, 8);
        }
        tMaterial = Materials.Steel;
        if (tMaterial.mStandardMoltenFluid != null) {
            GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Casing.get(0L), tMaterial.getMolten(72L), ItemList.IC2_Item_Casing_Steel.get(1), 16, 8);
        }
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L, new Object[0]), Materials.Mercury.getFluid(1000L), GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 1, 3), 128, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L), Materials.Mercury.getFluid(1000L), OreDictUnifier.get(OrePrefix.gem, Materials.Mercury, 1), 128, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L), Materials.Water.getFluid(250L), new ItemStack(Items.SNOWBALL, 1, 0), 128, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L), GT_ModHandler.getDistilledWater(250L), new ItemStack(Items.SNOWBALL, 1, 0), 128, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), Materials.Water.getFluid(1000L), new ItemStack(Blocks.SNOW, 1, 0), 512, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), GT_ModHandler.getDistilledWater(1000L), new ItemStack(Blocks.SNOW, 1, 0), 512, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), Materials.Lava.getFluid(1000L), new ItemStack(Blocks.OBSIDIAN, 1, 0), 1024, 16);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), Materials.Concrete.getMolten(144L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 8), 12, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), Materials.Glowstone.getMolten(576L), new ItemStack(Blocks.GLOWSTONE, 1, 0), 12, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L), Materials.Glass.getMolten(144L), new ItemStack(Blocks.GLASS, 1, 0), 12, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Plate.get(0L), Materials.Glass.getMolten(144L), OreDictUnifier.get(OrePrefix.plate, Materials.Glass, 1), 12, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Bottle.get(0L), Materials.Glass.getMolten(144L), ItemList.Bottle_Empty.get(1), 12, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Cylinder.get(0L), Materials.Milk.getFluid(250L), ItemList.Food_Cheese.get(1), 1024, 4);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Cylinder.get(0L), Materials.Cheese.getMolten(144L), ItemList.Food_Cheese.get(1), 64, 8);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Anvil.get(0L), Materials.Iron.getMolten(4464L), new ItemStack(Blocks.ANVIL, 1, 0), 128, 16);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Anvil.get(0L), Materials.WroughtIron.getMolten(4464L), new ItemStack(Blocks.ANVIL, 1, 0), 128, 16);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Anvil.get(0L), Materials.Steel.getMolten(4464L), GT_ModHandler.getModItem("Railcraft", "tile.railcraft.anvil", 1, 0), 128, 16);

        GTValues.RA.addChemicalBathRecipe(ItemList.Food_Raw_Fries.get(1), Materials.FryingOilHot.getFluid(10L), ItemList.Food_Fries.get(1), GTValues.NI, GTValues.NI, null, 16, 4);
        GTValues.RA.addChemicalBathRecipe(GT_ModHandler.getIC2Item(BlockName.te, TeBlock.itnt, 1), Materials.Glue.getFluid(10L), GT_ModHandler.getIC2Item(ItemName.dynamite_sticky, 1), GTValues.NI, GTValues.NI, null, 16, 4);
        GTValues.RA.addChemicalRecipe(new ItemStack(Items.PAPER,1), new ItemStack(Items.STRING,1), Materials.Glyceryl.getFluid(150), GTValues.NF, GT_ModHandler.getIC2Item(ItemName.dynamite, 1), 160, 4);
        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.frameGt, Materials.Steel, 1), Materials.Concrete.getMolten(144L), GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reinforced_stone, 1), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Coal, 1), Materials.Water.getFluid(125L), OreDictUnifier.get(OrePrefix.dust, Materials.HydratedCoal, 1), GTValues.NI, GTValues.NI, null, 12, 4);
        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 1), Materials.Water.getFluid(100L), new ItemStack(Items.PAPER, 1, 0), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Paper, 1), Materials.Water.getFluid(100L), new ItemStack(Items.PAPER, 1, 0), GTValues.NI, GTValues.NI, null, 100, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Items.REEDS, 1, 32767), Materials.Water.getFluid(100L), new ItemStack(Items.PAPER, 1, 0), GTValues.NI, GTValues.NI, null, 100, 8);
        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Coal, 1), GT_ModHandler.getDistilledWater(125L), OreDictUnifier.get(OrePrefix.dust, Materials.HydratedCoal, 1), GTValues.NI, GTValues.NI, null, 12, 4);
        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 1), GT_ModHandler.getDistilledWater(100L), new ItemStack(Items.PAPER, 1, 0), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Paper, 1), GT_ModHandler.getDistilledWater(100L), new ItemStack(Items.PAPER, 1, 0), GTValues.NI, GTValues.NI, null, 100, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Items.REEDS, 1, 32767), GT_ModHandler.getDistilledWater(100L), new ItemStack(Items.PAPER, 1, 0), GTValues.NI, GTValues.NI, null, 100, 8);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 1), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 2), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 3), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 4), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 5), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 6), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 7), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 8), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 9), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 10), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 11), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 12), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 13), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 14), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 15), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.WOOL, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 1), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 2), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 3), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 4), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 5), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 6), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 7), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 8), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 9), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 10), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 11), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 12), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 13), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 14), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.CARPET, 1, 15), Materials.Chlorine.getGas(25L), new ItemStack(Blocks.CARPET, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 32767), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.HARDENED_CLAY, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.STAINED_GLASS, 1, 32767), Materials.Chlorine.getGas(50L), new ItemStack(Blocks.GLASS, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 32767), Materials.Chlorine.getGas(20L), new ItemStack(Blocks.GLASS_PANE, 1, 0), GTValues.NI, GTValues.NI, null, 400, 2);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 8), Materials.Water.getFluid(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 0), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 9), Materials.Water.getFluid(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 1), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 10), Materials.Water.getFluid(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 2), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 11), Materials.Water.getFluid(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 3), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 12), Materials.Water.getFluid(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 4), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 13), Materials.Water.getFluid(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 5), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 14), Materials.Water.getFluid(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 6), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 15), Materials.Water.getFluid(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 7), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 8), GT_ModHandler.getDistilledWater(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 0), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 9), GT_ModHandler.getDistilledWater(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 1), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 10), GT_ModHandler.getDistilledWater(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 2), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 11), GT_ModHandler.getDistilledWater(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 3), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 12), GT_ModHandler.getDistilledWater(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 4), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 13), GT_ModHandler.getDistilledWater(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 5), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 14), GT_ModHandler.getDistilledWater(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 6), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addChemicalBathRecipe(new ItemStack(GregTechAPI.sBlockConcretes, 1, 15), GT_ModHandler.getDistilledWater(250L), new ItemStack(GregTechAPI.sBlockConcretes, 1, 7), GTValues.NI, GTValues.NI, null, 200, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.frameGt, Materials.BlackSteel, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Plastic, 1), Materials.Concrete.getMolten(144L), ItemList.Block_Plascrete.get(1), 200, 48);
        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.frameGt, Materials.TungstenSteel, 1), Materials.Concrete.getMolten(144L), ItemList.Block_TungstenSteelReinforced.get(1), GTValues.NI, GTValues.NI, null, 200, 4);


        for (int j = 0; j < Dyes.dyeRed.getSizeOfFluidList(); j++) {
            GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.RedAlloy, 1), Dyes.dyeRed.getFluidDye(j, 72L), GT_ModHandler.getModItem("BuildCraft|Transport", "pipeWire", 4, 0), GTValues.NI, GTValues.NI, null, 32, 16);
        }
        for (int j = 0; j < Dyes.dyeBlue.getSizeOfFluidList(); j++) {
            GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.RedAlloy, 1), Dyes.dyeBlue.getFluidDye(j, 72L), GT_ModHandler.getModItem("BuildCraft|Transport", "pipeWire", 4, 1), GTValues.NI, GTValues.NI, null, 32, 16);
        }
        for (int j = 0; j < Dyes.dyeGreen.getSizeOfFluidList(); j++) {
            GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.RedAlloy, 1), Dyes.dyeGreen.getFluidDye(j, 72L), GT_ModHandler.getModItem("BuildCraft|Transport", "pipeWire", 4, 2), GTValues.NI, GTValues.NI, null, 32, 16);
        }
        for (int j = 0; j < Dyes.dyeYellow.getSizeOfFluidList(); j++) {
            GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.RedAlloy, 1), Dyes.dyeYellow.getFluidDye(j, 72L), GT_ModHandler.getModItem("BuildCraft|Transport", "pipeWire", 4, 3), GTValues.NI, GTValues.NI, null, 32, 16);
        }
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            for (int j = 0; j < Dyes.VALUES[i].getSizeOfFluidList(); j++) {
                if (i != 15) {
                    GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.WOOL, 1, 0), Dyes.VALUES[i].getFluidDye(j, 72L), new ItemStack(Blocks.WOOL, 1, 15 - i), GTValues.NI, GTValues.NI, null, 64, 2);
                }
                GTValues.RA.addAssemblerRecipe(new ItemStack(Items.STRING, 3), ItemList.Circuit_Integrated.getWithDamage(0L, 3L), Dyes.VALUES[i].getFluidDye(j, 24L), new ItemStack(Blocks.CARPET, 2, 15 - i), 128, 5);
                GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.GLASS, 1, 0), Dyes.VALUES[i].getFluidDye(j, 18L), new ItemStack(Blocks.STAINED_GLASS, 1, 15 - i), GTValues.NI, GTValues.NI, null, 64, 2);
                GTValues.RA.addChemicalBathRecipe(new ItemStack(Blocks.HARDENED_CLAY, 1, 0), Dyes.VALUES[i].getFluidDye(j, 18L), new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 15 - i), GTValues.NI, GTValues.NI, null, 64, 2);
            }
        }
        GTValues.RA.addFluidExtractionRecipe(ItemList.Dye_SquidInk.get(1), GTValues.NI, FluidRegistry.getFluidStack("squidink", 144), 10000, 128, 4);
        GTValues.RA.addFluidExtractionRecipe(ItemList.Dye_Indigo.get(1), GTValues.NI, FluidRegistry.getFluidStack("indigo", 144), 10000, 128, 4);
        GTValues.RA.addFluidExtractionRecipe(ItemList.Crop_Drop_Indigo.get(1), GTValues.NI, FluidRegistry.getFluidStack("indigo", 144), 10000, 128, 4);
        GTValues.RA.addFluidExtractionRecipe(ItemList.Crop_Drop_MilkWart.get(1), OreDictUnifier.get(OrePrefix.dust, Materials.Milk, 1), GT_ModHandler.getMilk(150L), 1000, 128, 4);
        GTValues.RA.addFluidExtractionRecipe(ItemList.Crop_Drop_OilBerry.get(1), GTValues.NI, Materials.Oil.getFluid(100L), 10000, 128, 4);
        GTValues.RA.addFluidExtractionRecipe(ItemList.Crop_Drop_UUMBerry.get(1), GTValues.NI, Materials.UUMatter.getFluid(4L), 10000, 128, 4);
        GTValues.RA.addFluidExtractionRecipe(ItemList.Crop_Drop_UUABerry.get(1), GTValues.NI, Materials.UUAmplifier.getFluid(4L), 10000, 128, 4);
        GTValues.RA.addFluidExtractionRecipe(new ItemStack(Items.FISH, 1, 0), GTValues.NI, Materials.FishOil.getFluid(4L), 10000, 16, 4);
        GTValues.RA.addFluidExtractionRecipe(new ItemStack(Items.FISH, 1, 1), GTValues.NI, Materials.FishOil.getFluid(6L), 10000, 16, 4);
        GTValues.RA.addFluidExtractionRecipe(new ItemStack(Items.FISH, 1, 2), GTValues.NI, Materials.FishOil.getFluid(7L), 10000, 16, 4);
        GTValues.RA.addFluidExtractionRecipe(new ItemStack(Items.FISH, 1, 3), GTValues.NI, Materials.FishOil.getFluid(3L), 10000, 16, 4);
        GTValues.RA.addFluidExtractionRecipe(new ItemStack(Items.COAL, 1, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 1), Materials.Creosote.getFluid(100L), 1000, 128, 4);
        GTValues.RA.addFluidExtractionRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 1), ItemList.IC2_Plantball.get(1), Materials.Creosote.getFluid(5L), 100, 16, 4);
        GTValues.RA.addFluidExtractionRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.HydratedCoal, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Coal, 1), Materials.Water.getFluid(100L), 10000, 32, 4);
        GTValues.RA.addFluidExtractionRecipe(GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 1, 3), GTValues.NI, Materials.Mercury.getFluid(1000L), 10000, 128, 4);
        GTValues.RA.addFluidExtractionRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.Mercury, 1), GTValues.NI, Materials.Mercury.getFluid(1000L), 10000, 128, 4);
        GTValues.RA.addFluidExtractionRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Monazite, 1), GTValues.NI, Materials.Helium.getFluid(200L), 10000, 64, 64);

        GTValues.RA.addFluidSmelterRecipe(new ItemStack(Items.SNOWBALL, 1, 0), GTValues.NI, Materials.Water.getFluid(250L), 10000, 32, 4);
        GTValues.RA.addFluidSmelterRecipe(new ItemStack(Blocks.SNOW, 1, 0), GTValues.NI, Materials.Water.getFluid(1000L), 10000, 128, 4);
        GTValues.RA.addFluidSmelterRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Ice, 1), GTValues.NI, Materials.Ice.getSolid(1000L), 10000, 128, 4);
        GTValues.RA.addFluidSmelterRecipe(GT_ModHandler.getModItem(aTextForestry, "phosphor", 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphorus, 1), Materials.Lava.getFluid(800L), 1000, 256, 128);

        GTValues.RA.addAutoclaveRecipe(ItemList.IC2_Energium_Dust.get(9L), Materials.Water.getFluid(1000L), ItemList.IC2_EnergyCrystal.get(1), 10000, 500, 256);
        GTValues.RA.addAutoclaveRecipe(GT_ModHandler.getModItem(aTextAE, "item.ItemCrystalSeed", 1, 0), Materials.Water.getFluid(200L), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 10), 10000, 2000, 24);
        GTValues.RA.addAutoclaveRecipe(GT_ModHandler.getModItem(aTextAE, "item.ItemCrystalSeed", 1, 600), Materials.Water.getFluid(200L), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 11), 10000, 2000, 24);
        GTValues.RA.addAutoclaveRecipe(GT_ModHandler.getModItem(aTextAE, "item.ItemCrystalSeed", 1, 1200), Materials.Water.getFluid(200L), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 12), 10000, 2000, 24);
        GTValues.RA.addAutoclaveRecipe(ItemList.IC2_Energium_Dust.get(9L), GT_ModHandler.getDistilledWater(1000L), ItemList.IC2_EnergyCrystal.get(1), 10000, 250, 256);
        GTValues.RA.addAutoclaveRecipe(GT_ModHandler.getModItem(aTextAE, "item.ItemCrystalSeed", 1, 0), GT_ModHandler.getDistilledWater(200L), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 10), 10000, 1000, 24);
        GTValues.RA.addAutoclaveRecipe(GT_ModHandler.getModItem(aTextAE, "item.ItemCrystalSeed", 1, 600), GT_ModHandler.getDistilledWater(200L), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 11), 10000, 1000, 24);
        GTValues.RA.addAutoclaveRecipe(GT_ModHandler.getModItem(aTextAE, "item.ItemCrystalSeed", 1, 1200), GT_ModHandler.getDistilledWater(200L), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 12), 10000, 1000, 24);
        GTValues.RA.addAutoclaveRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 16), Materials.Palladium.getMolten(4), GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_fibre, 8), 9000, 600, 5);
        GTValues.RA.addAutoclaveRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 16), Materials.Platinum.getMolten(4), GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_fibre, 8), 5000, 600, 5);
        GTValues.RA.addAutoclaveRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 16), Materials.Lutetium.getMolten(4), GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_fibre, 8), 3333, 600, 5);
        GTValues.RA.addAutoclaveRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.NetherStar, 1), Materials.UUMatter.getFluid(576), OreDictUnifier.get(OrePrefix.gem, Materials.NetherStar, 1), 3333, 72000, 480);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.EnderPearl, 1), OreDictUnifier.get(OrePrefix.circuit, Materials.Basic, 4), Materials.Osmium.getMolten(288), ItemList.Field_Generator_LV.get(1), 1800, 30);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.EnderEye, 1), OreDictUnifier.get(OrePrefix.circuit, Materials.Good, 4), Materials.Osmium.getMolten(576), ItemList.Field_Generator_MV.get(1), 1800, 120);
        GTValues.RA.addAssemblerRecipe(ItemList.QuantumEye.get(1), OreDictUnifier.get(OrePrefix.circuit, Materials.Advanced, 4), Materials.Osmium.getMolten(1152), ItemList.Field_Generator_HV.get(1), 1800, 480);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.NetherStar, 1), OreDictUnifier.get(OrePrefix.circuit, Materials.Elite, 4), Materials.Osmium.getMolten(2304), ItemList.Field_Generator_EV.get(1), 1800, 1920);
        GTValues.RA.addAssemblerRecipe(ItemList.QuantumStar.get(1), OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 4), Materials.Osmium.getMolten(4608), ItemList.Field_Generator_IV.get(1), 1800, 7680);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireFine, Materials.Steel, 64), OreDictUnifier.get(OrePrefix.foil, Materials.Zinc, 16), null, ItemList.Component_Filter.get(1), 1600, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Graphite, 8), OreDictUnifier.get(OrePrefix.foil, Materials.Silicon, 1), Materials.Glue.getFluid(250L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Graphene, 1), 480, 240);

        GTValues.RA.addCentrifugeRecipe(ItemList.Cell_Empty.get(1), null, Materials.Air.getGas(10000), Materials.Nitrogen.getGas(3900), OreDictUnifier.get(OrePrefix.cell,Materials.Oxygen,1), null, null, null, null, null, null, 1600, 8);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell,Materials.NitrogenDioxide,4), OreDictUnifier.get(OrePrefix.cell,Materials.Oxygen,1), Materials.Water.getFluid(2000), new FluidStack(ItemList.sNitricAcid,4000), ItemList.Cell_Empty.get(5), 950, 30);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.crushedPurified,Materials.Pentlandite,1), null, new FluidStack(ItemList.sNitricAcid,8000), new FluidStack(ItemList.sNickelSulfate,9000), OreDictUnifier.get(OrePrefix.dustTiny, Materials.PlatinumGroupSludge, 1), 50, 30);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.crushedPurified,Materials.Chalcopyrite,1), null, new FluidStack(ItemList.sNitricAcid,8000), new FluidStack(ItemList.sBlueVitriol,9000), OreDictUnifier.get(OrePrefix.dustTiny, Materials.PlatinumGroupSludge, 1), 50, 30);
        GTValues.RA.addElectrolyzerRecipe(ItemList.Cell_Empty.get(1), null, new FluidStack(ItemList.sBlueVitriol,9000), Materials.SulfuricAcid.getFluid(8000), OreDictUnifier.get(OrePrefix.dust,Materials.Copper,1), OreDictUnifier.get(OrePrefix.cell,Materials.Oxygen,1), null, null, null, null, null, 900, 30);
        GTValues.RA.addElectrolyzerRecipe(ItemList.Cell_Empty.get(1), null, new FluidStack(ItemList.sNickelSulfate,9000), Materials.SulfuricAcid.getFluid(8000), OreDictUnifier.get(OrePrefix.dust,Materials.Nickel,1), OreDictUnifier.get(OrePrefix.cell,Materials.Oxygen,1), null, null, null, null, null, 900, 30);
        GTValues.RA.addCentrifugeRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.PlatinumGroupSludge, 1), null, null, null, OreDictUnifier.get(OrePrefix.dust,Materials.SiliconDioxide,1), OreDictUnifier.get(OrePrefix.dustTiny,Materials.Gold,1), OreDictUnifier.get(OrePrefix.dustTiny,Materials.Platinum,1), OreDictUnifier.get(OrePrefix.dustTiny,Materials.Palladium,1), OreDictUnifier.get(OrePrefix.dustTiny,Materials.Iridium,1), OreDictUnifier.get(OrePrefix.dustTiny,Materials.Osmium,1), new int[]{10000,10000,10000,8000,6000,6000}, 900, 30);

        GTValues.RA.addSlicerRecipe(ItemList.Food_Dough_Chocolate.get(1), ItemList.Shape_Slicer_Flat.get(0L), ItemList.Food_Raw_Cookie.get(4L), 128, 4);
        GTValues.RA.addSlicerRecipe(ItemList.Food_Baked_Bun.get(1), ItemList.Shape_Slicer_Flat.get(0L), ItemList.Food_Sliced_Bun.get(2L), 128, 4);
        GTValues.RA.addSlicerRecipe(ItemList.Food_Baked_Bread.get(1), ItemList.Shape_Slicer_Flat.get(0L), ItemList.Food_Sliced_Bread.get(2L), 128, 4);
        GTValues.RA.addSlicerRecipe(ItemList.Food_Baked_Baguette.get(1), ItemList.Shape_Slicer_Flat.get(0L), ItemList.Food_Sliced_Baguette.get(2L), 128, 4);

        EntityPlayer
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Silicon, 1), OreDictUnifier.get(OrePrefix.plate, Materials.Plastic, 1), ItemList.Empty_Board_Basic.get(1), 32, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Silicon, 2L), OreDictUnifier.get(OrePrefix.plate, Materials.Polytetrafluoroethylene, 1), ItemList.Empty_Board_Elite.get(1), 32, 256);

        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 0), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 1), 100, 120);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 0), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 1), 100, 120);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Gold, 1), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 0), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 2), 200, 120);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Diamond, 1), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 0), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 3), 100, 480);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl, 1), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 0), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 2, 4), 200, 120);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.NetherQuartz, 1), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 0), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 5), 300, 120);
        GTValues.RA.addFormingPressRecipe(new ItemStack(Items.COMPARATOR, 1, 32767), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 0), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 6), 300, 120);
        GTValues.RA.addFormingPressRecipe(GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 10), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 0, 13), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 16), 200, 16);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.CertusQuartz, 1), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 0, 13), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 16), 200, 16);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Diamond, 1), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 0, 14), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 17), 200, 16);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Gold, 1), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 0, 15), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 18), 200, 16);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Silicon, 1), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 0, 19), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 20), 200, 16);
        GTValues.RA.addFormingPressRecipe(ItemList.Empty_Board_Basic.get(1), ItemList.Circuit_Parts_Wiring_Basic.get(4L), ItemList.Circuit_Board_Basic.get(1), 32, 16);
        GTValues.RA.addFormingPressRecipe(ItemList.Empty_Board_Basic.get(1), ItemList.Circuit_Parts_Wiring_Advanced.get(4L), ItemList.Circuit_Board_Advanced.get(1), 32, 64);
        GTValues.RA.addFormingPressRecipe(ItemList.Empty_Board_Elite.get(1), ItemList.Circuit_Parts_Wiring_Elite.get(4L), ItemList.Circuit_Board_Elite.get(1), 32, 256);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Lapis, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Glowstone, 1), ItemList.Circuit_Parts_Advanced.get(2L), 32, 64);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Lazurite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Glowstone, 1), ItemList.Circuit_Parts_Advanced.get(2L), 32, 64);
        GTValues.RA.addFormingPressRecipe(ItemList.Food_Dough_Sugar.get(4L), ItemList.Shape_Mold_Cylinder.get(0L), ItemList.Food_Raw_Cake.get(1), 384, 4);
        GTValues.RA.addFormingPressRecipe(new ItemStack(Blocks.GLASS, 1, 32767), ItemList.Shape_Mold_Arrow.get(0L), ItemList.Arrow_Head_Glass_Emtpy.get(1), 64, 4);
        for (Materials tMat : Materials.values()) {
            if ((tMat.mStandardMoltenFluid != null) && (tMat.contains(SubTag.SOLDERING_MATERIAL))) {
                int tMultiplier = tMat.contains(SubTag.SOLDERING_MATERIAL_GOOD) ? 1 : tMat.contains(SubTag.SOLDERING_MATERIAL_BAD) ? 4 : 2;

                GTValues.RA.addAssemblerRecipe(ItemList.IC2_Item_Casing_Steel.get(1), OreDictUnifier.get(OrePrefix.wireGt01, Materials.RedAlloy, 2L), tMat.getMolten(144L * tMultiplier / 8L), ItemList.Circuit_Primitive.get(1), 16, 8);
                GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Plastic, 1), OreDictUnifier.get(OrePrefix.wireGt01, Materials.RedAlloy, 1), tMat.getMolten(144L * tMultiplier / 8L), ItemList.Circuit_Primitive.get(1), 16, 8);
                GTValues.RA.addAssemblerRecipe(ItemList.Circuit_Board_Basic.get(1), ItemList.Circuit_Primitive.get(2L), tMat.getMolten(144L * tMultiplier / 4L), ItemList.Circuit_Basic.get(1), 32, 16);
                GTValues.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1), ItemList.Circuit_Primitive.get(2L), tMat.getMolten(144L * tMultiplier / 4L), ItemList.Circuit_Good.get(1), 32, 16);
                GTValues.RA.addAssemblerRecipe(ItemList.Circuit_Board_Advanced.get(1), ItemList.Circuit_Parts_Advanced.get(2L), tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Advanced.get(1), 32, 64);
                GTValues.RA.addAssemblerRecipe(ItemList.Circuit_Board_Advanced.get(1), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1), tMat.getMolten(144L * tMultiplier / 2L), ItemList.Circuit_Data.get(1), 32, 64);
                GTValues.RA.addAssemblerRecipe(ItemList.Circuit_Board_Elite.get(1), ItemList.Circuit_Data.get(3L), tMat.getMolten(144L * tMultiplier / 1), ItemList.Circuit_Elite.get(1), 32, 256);
                GTValues.RA.addAssemblerRecipe(ItemList.Circuit_Board_Elite.get(1), ItemList.Circuit_Parts_Crystal_Chip_Master.get(3L), tMat.getMolten(144L * tMultiplier / 1), ItemList.Circuit_Master.get(1), 32, 256);
                GTValues.RA.addAssemblerRecipe(ItemList.Circuit_Data.get(1), OreDictUnifier.get(OrePrefix.plate, Materials.Plastic, 2L), tMat.getMolten(144L * tMultiplier / 2L), ItemList.Tool_DataStick.get(1), 128, 64);
                for (ItemStack tPlate : new ItemStack[]{OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), OreDictUnifier.get(OrePrefix.plate, Materials.Aluminium, 1)}) {
                    GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.LEVER, 1, 32767), tPlate, tMat.getMolten(144L * tMultiplier / 2L), ItemList.Cover_Controller.get(1), 800, 16);
                    GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.REDSTONE_TORCH, 1, 32767), tPlate, tMat.getMolten(144L * tMultiplier / 2L), ItemList.Cover_ActivityDetector.get(1), 800, 16);
                    GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1, 32767), tPlate, tMat.getMolten(144L * tMultiplier / 2L), ItemList.Cover_FluidDetector.get(1), 800, 16);
                    GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 1, 32767), tPlate, tMat.getMolten(144L * tMultiplier / 2L), ItemList.Cover_ItemDetector.get(1), 800, 16);
                    GTValues.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item(ItemName.meter, 1), tPlate, tMat.getMolten(144L * tMultiplier / 2L), ItemList.Cover_EnergyDetector.get(1), 800, 16);
                }
            }
        }
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.REDSTONE_TORCH, 2, 32767), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), Materials.Concrete.getMolten(144L), new ItemStack(Items.REPEATER, 1, 0), 800, 1);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Items.LEATHER, 1, 32767), new ItemStack(Items.LEAD, 1, 32767), Materials.Glue.getFluid(50L), new ItemStack(Items.NAME_TAG, 1, 0), 100, 8);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 8L), new ItemStack(Items.COMPASS, 1, 32767), GTValues.NF, new ItemStack(Items.MAP, 1, 0), 100, 8);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Tantalum, 1), OreDictUnifier.get(OrePrefix.plate, Materials.Manganese, 1), Materials.Plastic.getMolten(144L), ItemList.Battery_RE_ULV_Tantalum.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Circuit_Elite.get(2L), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(18L), GTValues.NF, ItemList.Tool_DataOrb.get(1), 512, 256);
        GTValues.RA.addAssemblerRecipe(ItemList.Circuit_Master.get(2L), ItemList.Circuit_Parts_Crystal_Chip_Master.get(18L), GTValues.NF, ItemList.Energy_LapotronicOrb.get(1), 512, 1024);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Europium, 4L), ItemList.Energy_LapotronicOrb.get(8L), GTValues.NF, ItemList.Energy_LapotronicOrb2.get(1), 2048, 4096);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Neutronium, 16), ItemList.Energy_LapotronicOrb2.get(8L), GTValues.NF, ItemList.ZPM2.get(1), 32768, 4096);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem("TwilightForest", "item.charmOfLife1", 4, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), GTValues.NF, GT_ModHandler.getModItem("TwilightForest", "item.charmOfLife2", 1, 0), 100, 8);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem("TwilightForest", "item.charmOfKeeping1", 4, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), GTValues.NF, GT_ModHandler.getModItem("TwilightForest", "item.charmOfKeeping2", 1, 0), 100, 8);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem("TwilightForest", "item.charmOfKeeping2", 4, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), GTValues.NF, GT_ModHandler.getModItem("TwilightForest", "item.charmOfKeeping3", 1, 0), 100, 8);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem("TwilightForest", "item.charmOfLife2", 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 1), GTValues.NF, GT_ModHandler.getModItem("TwilightForest", "item.charmOfLife1", 4, 0), 100, 8);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem("TwilightForest", "item.charmOfKeeping2", 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 1), GTValues.NF, GT_ModHandler.getModItem("TwilightForest", "item.charmOfKeeping1", 4, 0), 100, 8);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem("TwilightForest", "item.charmOfKeeping3", 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 1), GTValues.NF, GT_ModHandler.getModItem("TwilightForest", "item.charmOfKeeping2", 4, 0), 100, 8);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 16), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 20), Materials.Redstone.getMolten(144), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 23), 64, 32);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 17), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 20), Materials.Redstone.getMolten(144), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 24), 64, 32);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 18), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 20), Materials.Redstone.getMolten(144), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 22), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.CertusQuartz, 1), new ItemStack(Blocks.SAND, 1, 32767), GTValues.NF, GT_ModHandler.getModItem(aTextAE, "item.ItemCrystalSeed", 2, 0), 64, 8);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.NetherQuartz, 1), new ItemStack(Blocks.SAND, 1, 32767), GTValues.NF, GT_ModHandler.getModItem(aTextAE, "item.ItemCrystalSeed", 2, 600), 64, 8);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Fluix, 1), new ItemStack(Blocks.SAND, 1, 32767), GTValues.NF, GT_ModHandler.getModItem(aTextAE, "item.ItemCrystalSeed", 2, 1200), 64, 8);
        GTValues.RA.addAssemblerRecipe(ItemList.FR_Wax.get(6L), new ItemStack(Items.STRING, 1, 32767), Materials.Water.getFluid(600L), GT_ModHandler.getModItem(aTextForestry, "candle", 24, 0), 64, 8);
        GTValues.RA.addAssemblerRecipe(ItemList.FR_Wax.get(2L), ItemList.FR_Silk.get(1), Materials.Water.getFluid(200L), GT_ModHandler.getModItem(aTextForestry, "candle", 8, 0), 16, 8);
        GTValues.RA.addAssemblerRecipe(ItemList.FR_Silk.get(9L), ItemList.Circuit_Integrated.getWithDamage(0L, 9L), Materials.Water.getFluid(500L), GT_ModHandler.getModItem(aTextForestry, "craftingMaterial", 1, 3), 64, 8);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem(aTextForestry, "propolis", 5, 2), ItemList.Circuit_Integrated.getWithDamage(0L, 5L), GTValues.NF, GT_ModHandler.getModItem(aTextForestry, "craftingMaterial", 1, 1), 16, 8);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem(aTextForestry, "sturdyMachine", 1, 0), OreDictUnifier.get(OrePrefix.gem, Materials.Diamond, 4L), Materials.Water.getFluid(5000L), ItemList.FR_Casing_Hardened.get(1), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Bronze, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), GTValues.NF, ItemList.FR_Casing_Sturdy.get(1), 32, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Tin, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 6L), Materials.Water.getFluid(1000L), GT_ModHandler.getModItem(aTextForestry, "chipsets", 1, 0), 16, 8);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Bronze, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 6L), Materials.Water.getFluid(1000L), GT_ModHandler.getModItem(aTextForestry, "chipsets", 1, 1), 32, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 6L), Materials.Water.getFluid(1000L), GT_ModHandler.getModItem(aTextForestry, "chipsets", 1, 2), 48, 24);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 6L), Materials.Water.getFluid(1000L), GT_ModHandler.getModItem(aTextForestry, "chipsets", 1, 2), 48, 24);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Gold, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 6L), Materials.Water.getFluid(1000L), GT_ModHandler.getModItem(aTextForestry, "chipsets", 1, 3), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), new ItemStack(Blocks.WOOL, 1, 32767), Materials.Creosote.getFluid(1000L), new ItemStack(Blocks.TORCH, 6, 0), 400, 1);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getModItem(aTextForestry, "craftingMaterial", 5, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 5L), GTValues.NF, OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl, 1), 64, 8);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.PISTON, 1, 32767), new ItemStack(Items.SLIME_BALL, 1, 32767), GTValues.NF, new ItemStack(Blocks.STICKY_PISTON, 1, 0), 100, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.PISTON, 1, 32767), ItemList.IC2_Resin.get(1), GTValues.NF, new ItemStack(Blocks.STICKY_PISTON, 1, 0), 100, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.PISTON, 1, 32767), ItemList.Circuit_Integrated.getWithDamage(0L, 1), Materials.Glue.getFluid(100L), new ItemStack(Blocks.STICKY_PISTON, 1, 0), 100, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Rubber, 3L), GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_mesh, 3), Materials.Glue.getFluid(300L), ItemList.Duct_Tape.get(1), 100, 64);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3L), new ItemStack(Items.LEATHER, 1, 32767), Materials.Glue.getFluid(20L), new ItemStack(Items.BOOK, 1, 0), 32, 8);
        GTValues.RA.addAssemblerRecipe(ItemList.Paper_Printed_Pages.get(1), new ItemStack(Items.LEATHER, 1, 32767), Materials.Glue.getFluid(20L), new ItemStack(Items.WRITTEN_BOOK, 1, 0), 32, 8);
        GTValues.RA.addAssemblerRecipe(ItemList.IC2_Item_Casing_Tin.get(4L), new ItemStack(Blocks.GLASS_PANE, 1, 32767), GTValues.NF, ItemList.Cell_Universal_Fluid.get(1), 128, 8);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Baked_Cake.get(1), new ItemStack(Items.EGG, 1, 0), Materials.Milk.getFluid(3000L), new ItemStack(Items.CAKE, 1, 0), 100, 8);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Bun.get(2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), GTValues.NF, ItemList.Food_Sliced_Buns.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Bread.get(2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), GTValues.NF, ItemList.Food_Sliced_Breads.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Baguette.get(2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), GTValues.NF, ItemList.Food_Sliced_Baguettes.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Buns.get(1), ItemList.Circuit_Integrated.getWithDamage(0L, 1), GTValues.NF, ItemList.Food_Sliced_Bun.get(2L), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Breads.get(1), ItemList.Circuit_Integrated.getWithDamage(0L, 1), GTValues.NF, ItemList.Food_Sliced_Bread.get(2L), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Baguettes.get(1), ItemList.Circuit_Integrated.getWithDamage(0L, 1), GTValues.NF, ItemList.Food_Sliced_Baguette.get(2L), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Bun.get(2L), OreDictUnifier.get(OrePrefix.dust, Materials.MeatCooked, 1), GTValues.NF, ItemList.Food_Burger_Meat.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Buns.get(1), OreDictUnifier.get(OrePrefix.dust, Materials.MeatCooked, 1), GTValues.NF, ItemList.Food_Burger_Meat.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Bun.get(2L), ItemList.Food_Chum.get(1), GTValues.NF, ItemList.Food_Burger_Chum.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Buns.get(1), ItemList.Food_Chum.get(1), GTValues.NF, ItemList.Food_Burger_Chum.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Bun.get(2L), ItemList.Food_Sliced_Cheese.get(3L), GTValues.NF, ItemList.Food_Burger_Cheese.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Sliced_Buns.get(1), ItemList.Food_Sliced_Cheese.get(3L), GTValues.NF, ItemList.Food_Burger_Cheese.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Flat_Dough.get(1), OreDictUnifier.get(OrePrefix.dust, Materials.MeatCooked, 1), GTValues.NF, ItemList.Food_Raw_Pizza_Meat.get(1), 100, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.Food_Flat_Dough.get(1), ItemList.Food_Sliced_Cheese.get(3L), GTValues.NF, ItemList.Food_Raw_Pizza_Cheese.get(1), 100, 4);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 0), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.ingot, Materials.AnnealedCopper, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 0), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.ingot, Materials.Tin, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 1), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.ingot, Materials.Bronze, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 2), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 3), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 3), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.ingot, Materials.Gold, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 4), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.gem, Materials.Diamond, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 5), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.dust, Materials.Blaze, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 7), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.ingot, Materials.Rubber, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 8), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.gem, Materials.Emerald, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 9), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.gem, Materials.Apatite, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 10), 64, 32);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.gem, Materials.Lapis, 5L), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 4, 11), 64, 32);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Aluminium, 2L), new ItemStack(Items.IRON_DOOR, 1), ItemList.Cover_Shutter.get(2L, new Object[0]), 800, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 2L), new ItemStack(Items.IRON_DOOR, 1), ItemList.Cover_Shutter.get(2L, new Object[0]), 800, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 2L), new ItemStack(Items.IRON_DOOR, 1), ItemList.Cover_Shutter.get(2L, new Object[0]), 800, 16);

        GTValues.RA.addUniversalDistillationRecipe(Materials.OilLight.getFluid(150), new FluidStack[]{Materials.SulfuricGas.getGas(240), Materials.SulfuricNaphtha.getFluid(30), Materials.SulfuricLightFuel.getFluid(20), Materials.SulfuricHeavyFuel.getFluid(10)}, null, 32, 64);
        GTValues.RA.addUniversalDistillationRecipe(Materials.OilMedium.getFluid(100), new FluidStack[]{Materials.SulfuricGas.getGas(60), Materials.SulfuricNaphtha.getFluid(20), Materials.SulfuricLightFuel.getFluid(50), Materials.SulfuricHeavyFuel.getFluid(15)}, null, 32, 64);
        GTValues.RA.addUniversalDistillationRecipe(Materials.Oil.getFluid(50L), new FluidStack[]{Materials.SulfuricGas.getGas(60), Materials.SulfuricNaphtha.getFluid(20), Materials.SulfuricLightFuel.getFluid(50), Materials.SulfuricHeavyFuel.getFluid(15)}, null, 32, 64);

        if (FluidRegistry.getFluid("oilgc") != null) {
            GTValues.RA.addUniversalDistillationRecipe(new FluidStack(FluidRegistry.getFluid("oilgc"), 50), new FluidStack[]{Materials.SulfuricGas.getGas(60), Materials.SulfuricNaphtha.getFluid(20), Materials.SulfuricLightFuel.getFluid(50), Materials.SulfuricHeavyFuel.getFluid(15)}, null, 32, 64);
        }
        GTValues.RA.addUniversalDistillationRecipe(Materials.OilHeavy.getFluid(100), new FluidStack[]{Materials.SulfuricGas.getGas(60), Materials.SulfuricNaphtha.getFluid(15), Materials.SulfuricLightFuel.getFluid(45), Materials.SulfuricHeavyFuel.getFluid(250)}, null, 32, 192);
        GTValues.RA.addUniversalDistillationRecipe(Materials.CrackedLightFuel.getFluid(100), new FluidStack[]{Materials.Gas.getGas(240), Materials.Naphtha.getFluid(30), Materials.HeavyFuel.getFluid(10), new FluidStack(ItemList.sToluene,10)}, null, 16, 64);
        GTValues.RA.addUniversalDistillationRecipe(Materials.CrackedHeavyFuel.getFluid(100), new FluidStack[]{Materials.Gas.getGas(80), Materials.Naphtha.getFluid(10), Materials.LightFuel.getFluid(40), new FluidStack(ItemList.sToluene,30), Materials.Lubricant.getFluid(5)}, OreDictUnifier.get(OrePrefix.dustTiny, Materials.HydratedCoal, 1), 16, 64);

        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1), Materials.HeavyFuel.getFluid(10L), new FluidStack(ItemList.sToluene,4), 16, 24, false);
        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1), new FluidStack(ItemList.sToluene,30), Materials.LightFuel.getFluid(30L), 16, 24, false);
        GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Ball.get(0L), new FluidStack(ItemList.sToluene,100), ItemList.GelledToluene.get(1), 100, 16);
        GTValues.RA.addChemicalRecipe(ItemList.GelledToluene.get(4), GTValues.NI, Materials.SulfuricAcid.getFluid(250), GTValues.NF, new ItemStack(Blocks.TNT,1), 200, 24);

        GTValues.RA.addChemicalRecipe(new ItemStack(Items.SUGAR), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Plastic, 1), new FluidStack(ItemList.sToluene, 133), GTValues.NF, ItemList.GelledToluene.get(2), 140, 192);

        GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.cell,Materials.SulfuricAcid, 1), null, null, null, new FluidStack(ItemList.sNitricAcid,1000), new FluidStack(ItemList.sNitrationMixture,2000), ItemList.Cell_Empty.get(1), 500, 2);
        GTValues.RA.addChemicalRecipe(ItemList.GelledToluene.get(4), GTValues.NI, new FluidStack(ItemList.sNitrationMixture,250), GTValues.NF, GT_ModHandler.getIC2Item(BlockName.te, TeBlock.itnt, 1), 80, 480);

        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Hydrogen, 1), ItemList.Cell_Empty.get(1), Materials.NatruralGas.getGas(16000), Materials.Gas.getGas(16000), OreDictUnifier.get(OrePrefix.cell, Materials.HydricSulfide, 2L), 160);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Hydrogen, 1), ItemList.Cell_Empty.get(1), Materials.SulfuricGas.getGas(16000), Materials.Gas.getGas(16000), OreDictUnifier.get(OrePrefix.cell, Materials.HydricSulfide, 2L), 160);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Hydrogen, 1), ItemList.Cell_Empty.get(1), Materials.SulfuricNaphtha.getFluid(7000), Materials.Naphtha.getFluid(7000), OreDictUnifier.get(OrePrefix.cell, Materials.HydricSulfide, 2L), 160);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Hydrogen, 1), ItemList.Cell_Empty.get(1), Materials.SulfuricLightFuel.getFluid(6000), Materials.LightFuel.getFluid(6000), OreDictUnifier.get(OrePrefix.cell, Materials.HydricSulfide, 2L), 160);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Hydrogen, 1), ItemList.Cell_Empty.get(1), Materials.SulfuricHeavyFuel.getFluid(4000), Materials.HeavyFuel.getFluid(4000), OreDictUnifier.get(OrePrefix.cell, Materials.HydricSulfide, 2L), 160);

        GTValues.RA.addCentrifugeRecipe(ItemList.Cell_Empty.get(4), null, Materials.Gas.getGas(8000), Materials.Methane.getGas(4000), OreDictUnifier.get(OrePrefix.cell, Materials.LPG, 4), null, null, null, null, null, new int[]{10000}, 200, 5);

        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.HydricSulfide, 2L), ItemList.Cell_Water.get(2), null, Materials.SulfuricAcid.getFluid(3000), ItemList.Cell_Empty.get(4), 320);
        GTValues.RA.addChemicalRecipe(ItemList.Cell_Water.get(2, new Object[0]),                               null, new FluidStack(ItemList.sHydricSulfur, 2000), Materials.SulfuricAcid.getFluid(3000), ItemList.Cell_Empty.get(2, new Object[0]), 320);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.HydricSulfide, 2L), null, Materials.Water.getFluid(2000),         Materials.SulfuricAcid.getFluid(3000), ItemList.Cell_Empty.get(2), 320);


        GTValues.RA.addChemicalRecipe(ItemList.Cell_Air.get(2), null, Materials.Naphtha.getFluid(288), Materials.Plastic.getMolten(144), ItemList.Cell_Empty.get(2), 640);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Titanium, 1), OreDictUnifier.get(OrePrefix.cell, Materials.Oxygen, 16L), Materials.Naphtha.getFluid(1296), Materials.Plastic.getMolten(1296), ItemList.Cell_Empty.get(16), 640);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Saltpeter, 1), null, Materials.Naphtha.getFluid(576), Materials.Polycaprolactam.getMolten(1296), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Potassium, 1), 640);
        GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Polycaprolactam, 1), new ItemStack(Items.STRING, 32), 80, 48);

        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 1), OreDictUnifier.get(OrePrefix.cell, Materials.Chlorine, 1), Materials.LPG.getFluid(432), new FluidStack(ItemList.sEpichlorhydrin, 432), ItemList.Cell_Empty.get(1), 480, 30);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Naphtha, 3L), OreDictUnifier.get(OrePrefix.cell, Materials.Fluorine, 1), new FluidStack(ItemList.sEpichlorhydrin, 432), Materials.Polytetrafluoroethylene.getMolten(432), ItemList.Cell_Empty.get(4), 240, 256);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Silicon, 1), null, new FluidStack(ItemList.sEpichlorhydrin, 144), Materials.Silicone.getMolten(144), null, 240, 96);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Naphtha, 3L), OreDictUnifier.get(OrePrefix.cell, Materials.NitrogenDioxide, 1), new FluidStack(ItemList.sEpichlorhydrin, 144), Materials.Epoxid.getMolten(288), ItemList.Cell_Empty.get(4), 240, 30);

        GTValues.RA.addCrackingRecipe(Materials.LightFuel.getFluid(128), Materials.CrackedLightFuel.getFluid(192), 16, 320);
        GTValues.RA.addCrackingRecipe(Materials.HeavyFuel.getFluid(128), Materials.CrackedHeavyFuel.getFluid(192), 16, 320);

        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.Creosote.getFluid(3L), Materials.Lubricant.getFluid(1), 16, 24, false);
        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.SeedOil.getFluid(4L), Materials.Lubricant.getFluid(1), 16, 24, false);
        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.FishOil.getFluid(3L), Materials.Lubricant.getFluid(1), 16, 24, false);
        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1), Materials.Biomass.getFluid(40L), Materials.Ethanol.getFluid(12L), 16, 24, false);
        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 5L), Materials.Biomass.getFluid(40L), Materials.Water.getFluid(12L), 16, 24, false);
        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 5L), Materials.Water.getFluid(5L), GT_ModHandler.getDistilledWater(4L), 16, 8, false);
        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1), FluidRegistry.getFluidStack("potion.potatojuice", 2), FluidRegistry.getFluidStack("potion.vodka", 1), 16, 16, true);
        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1), FluidRegistry.getFluidStack("potion.lemonade", 2), FluidRegistry.getFluidStack("potion.alcopops", 1), 16, 16, true);

        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.OilLight.getFluid(300L), Materials.Oil.getFluid(100L), 16, 24, false);
        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.OilMedium.getFluid(200L), Materials.Oil.getFluid(100L), 16, 24, false);
        GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.OilHeavy.getFluid(100L), Materials.Oil.getFluid(100L), 16, 24, false);

        GTValues.RA.addFluidHeaterRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1), Materials.Water.getFluid(6L), Materials.Water.getGas(960L), 30, 32);
        GTValues.RA.addFluidHeaterRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1), GT_ModHandler.getDistilledWater(6L), Materials.Water.getGas(960L), 30, 32);
        GTValues.RA.addFluidHeaterRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1), Materials.SeedOil.getFluid(16L), Materials.FryingOilHot.getFluid(16L), 16, 32);
        GTValues.RA.addFluidHeaterRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1), Materials.FishOil.getFluid(16L), Materials.FryingOilHot.getFluid(16L), 16, 32);

        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Talc, 1), FluidRegistry.getFluid("oil"), FluidRegistry.getFluid("lubricant"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Soapstone, 1), FluidRegistry.getFluid("oil"), FluidRegistry.getFluid("lubricant"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), FluidRegistry.getFluid("oil"), FluidRegistry.getFluid("lubricant"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Talc, 1), FluidRegistry.getFluid("creosote"), FluidRegistry.getFluid("lubricant"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Soapstone, 1), FluidRegistry.getFluid("creosote"), FluidRegistry.getFluid("lubricant"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), FluidRegistry.getFluid("creosote"), FluidRegistry.getFluid("lubricant"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Talc, 1), FluidRegistry.getFluid("seedoil"), FluidRegistry.getFluid("lubricant"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Soapstone, 1), FluidRegistry.getFluid("seedoil"), FluidRegistry.getFluid("lubricant"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), FluidRegistry.getFluid("seedoil"), FluidRegistry.getFluid("lubricant"), false);
        for (Fluid tFluid : new Fluid[]{FluidRegistry.WATER, GT_ModHandler.getDistilledWater(1).getFluid()}) {
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Milk, 1), tFluid, FluidRegistry.getFluid("milk"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Wheat, 1), tFluid, FluidRegistry.getFluid("potion.wheatyjuice"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Potassium, 1), tFluid, FluidRegistry.getFluid("potion.mineralwater"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), tFluid, FluidRegistry.getFluid("potion.mineralwater"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcium, 1), tFluid, FluidRegistry.getFluid("potion.mineralwater"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Magnesium, 1), tFluid, FluidRegistry.getFluid("potion.mineralwater"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Salt, 1), tFluid, FluidRegistry.getFluid("potion.saltywater"), true);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.RockSalt, 1), tFluid, FluidRegistry.getFluid("potion.saltywater"), true);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Glowstone, 1), tFluid, FluidRegistry.getFluid("potion.thick"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), tFluid, FluidRegistry.getFluid("potion.mundane"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), tFluid, FluidRegistry.getFluid("potion.mundane"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Blaze, 1), tFluid, FluidRegistry.getFluid("potion.mundane"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.MAGMA_CREAM, 1, 0), tFluid, FluidRegistry.getFluid("potion.mundane"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE, 1, 0), tFluid, FluidRegistry.getFluid("potion.mundane"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.SPIDER_EYE, 1, 0), tFluid, FluidRegistry.getFluid("potion.mundane"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.SPECKLED_MELON, 1, 0), tFluid, FluidRegistry.getFluid("potion.mundane"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.GHAST_TEAR, 1, 0), tFluid, FluidRegistry.getFluid("potion.mundane"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.NETHER_WART, 1, 0), tFluid, FluidRegistry.getFluid("potion.awkward"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Blocks.RED_MUSHROOM, 1, 0), tFluid, FluidRegistry.getFluid("potion.poison"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.FISH, 1, 3), tFluid, FluidRegistry.getFluid("potion.poison.strong"), true);
            GTValues.RA.addBrewingRecipe(ItemList.IC2_Grin_Powder.get(1), tFluid, FluidRegistry.getFluid("potion.poison.strong"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.REEDS, 1, 0), tFluid, FluidRegistry.getFluid("potion.reedwater"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.APPLE, 1, 0), tFluid, FluidRegistry.getFluid("potion.applejuice"), false);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 0), tFluid, FluidRegistry.getFluid("potion.goldenapplejuice"), true);
            GTValues.RA.addBrewingRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 1), tFluid, FluidRegistry.getFluid("potion.idunsapplejuice"), true);
            GTValues.RA.addBrewingRecipe(ItemList.IC2_Hops.get(1), tFluid, FluidRegistry.getFluid("potion.hopsjuice"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Coffee, 1), tFluid, FluidRegistry.getFluid("potion.darkcoffee"), false);
            GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Chili, 1), tFluid, FluidRegistry.getFluid("potion.chillysauce"), false);

            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(2L), 200);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphor, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(3L), 300);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphate, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(2L), 200);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 3L), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(1), 100);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.DarkAsh, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(1), 100);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(3L), 300);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphor, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(4L), 400);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphate, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(3L), 300);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 3L), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(2L), 200);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.DarkAsh, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(2L), 200);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Apatite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(3L), 300);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Apatite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphor, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(4L), 400);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Apatite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphate, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(3L), 300);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Apatite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 3L), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(2L), 200);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Apatite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.DarkAsh, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(2L), 200);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Glauconite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(3L), 300);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Glauconite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphor, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(4L), 400);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Glauconite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphate, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(3L), 300);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Glauconite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 3L), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(2L), 200);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Glauconite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.DarkAsh, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(2L), 200);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.GlauconiteSand, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(3L), 300);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.GlauconiteSand, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphor, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(4L), 400);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.GlauconiteSand, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphate, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(3L), 300);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.GlauconiteSand, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 3L), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(2L), 200);
            GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.GlauconiteSand, 1), OreDictUnifier.get(OrePrefix.dust, Materials.DarkAsh, 1), new FluidStack(tFluid, 1000), GTValues.NF, ItemList.IC2_Fertilizer.get(2L), 200);
        }
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Chili, 1), FluidRegistry.getFluid("potion.chillysauce"), FluidRegistry.getFluid("potion.hotsauce"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Chili, 1), FluidRegistry.getFluid("potion.hotsauce"), FluidRegistry.getFluid("potion.diabolosauce"), true);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Chili, 1), FluidRegistry.getFluid("potion.diabolosauce"), FluidRegistry.getFluid("potion.diablosauce"), true);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Coffee, 1), FluidRegistry.getFluid("milk"), FluidRegistry.getFluid("potion.coffee"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Cocoa, 1), FluidRegistry.getFluid("milk"), FluidRegistry.getFluid("potion.darkchocolatemilk"), false);
        GTValues.RA.addBrewingRecipe(ItemList.IC2_Hops.get(1), FluidRegistry.getFluid("potion.wheatyjuice"), FluidRegistry.getFluid("potion.wheatyhopsjuice"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Wheat, 1), FluidRegistry.getFluid("potion.hopsjuice"), FluidRegistry.getFluid("potion.wheatyhopsjuice"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), FluidRegistry.getFluid("potion.tea"), FluidRegistry.getFluid("potion.sweettea"), true);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), FluidRegistry.getFluid("potion.coffee"), FluidRegistry.getFluid("potion.cafeaulait"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), FluidRegistry.getFluid("potion.cafeaulait"), FluidRegistry.getFluid("potion.laitaucafe"), true);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), FluidRegistry.getFluid("potion.lemonjuice"), FluidRegistry.getFluid("potion.lemonade"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), FluidRegistry.getFluid("potion.darkcoffee"), FluidRegistry.getFluid("potion.darkcafeaulait"), true);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), FluidRegistry.getFluid("potion.darkchocolatemilk"), FluidRegistry.getFluid("potion.chocolatemilk"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Ice, 1), FluidRegistry.getFluid("potion.tea"), FluidRegistry.getFluid("potion.icetea"), false);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Gunpowder, 1), FluidRegistry.getFluid("potion.lemonade"), FluidRegistry.getFluid("potion.cavejohnsonsgrenadejuice"), true);
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), FluidRegistry.getFluid("potion.mundane"), FluidRegistry.getFluid("potion.purpledrink"), true);
        GTValues.RA.addBrewingRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE, 1, 0), FluidRegistry.getFluid("potion.mundane"), FluidRegistry.getFluid("potion.weakness"), false);
        GTValues.RA.addBrewingRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE, 1, 0), FluidRegistry.getFluid("potion.thick"), FluidRegistry.getFluid("potion.weakness"), false);

        addPotionRecipes("waterbreathing", new ItemStack(Items.FISH, 1, 3));
        addPotionRecipes("fireresistance", new ItemStack(Items.MAGMA_CREAM, 1, 0));
        addPotionRecipes("nightvision", new ItemStack(Items.GOLDEN_CARROT, 1, 0));
        addPotionRecipes("weakness", new ItemStack(Items.FERMENTED_SPIDER_EYE, 1, 0));
        addPotionRecipes("poison", new ItemStack(Items.SPIDER_EYE, 1, 0));
        addPotionRecipes("health", new ItemStack(Items.SPECKLED_MELON, 1, 0));
        addPotionRecipes("regen", new ItemStack(Items.GHAST_TEAR, 1, 0));
        addPotionRecipes("speed", OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1L));
        addPotionRecipes("strength", OreDictUnifier.get(OrePrefix.dust, Materials.Blaze, 1L));

        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("milk", 50), FluidRegistry.getFluidStack("potion.mundane", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.lemonjuice", 50), FluidRegistry.getFluidStack("potion.limoncello", 25), 1024, true);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.applejuice", 50), FluidRegistry.getFluidStack("potion.cider", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.goldenapplejuice", 50), FluidRegistry.getFluidStack("potion.goldencider", 25), 1024, true);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.idunsapplejuice", 50), FluidRegistry.getFluidStack("potion.notchesbrew", 25), 1024, true);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.reedwater", 50), FluidRegistry.getFluidStack("potion.rum", 25), 1024, true);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.rum", 50), FluidRegistry.getFluidStack("potion.piratebrew", 10), 2048, true);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.grapejuice", 50), FluidRegistry.getFluidStack("potion.wine", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.wine", 50), FluidRegistry.getFluidStack("potion.vinegar", 10), 2048, true);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.wheatyjuice", 50), FluidRegistry.getFluidStack("potion.scotch", 25), 1024, true);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.scotch", 50), FluidRegistry.getFluidStack("potion.glenmckenner", 10), 2048, true);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.wheatyhopsjuice", 50), FluidRegistry.getFluidStack("potion.beer", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.hopsjuice", 50), FluidRegistry.getFluidStack("potion.darkbeer", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.darkbeer", 50), FluidRegistry.getFluidStack("potion.dragonblood", 10), 2048, true);

        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.awkward", 50), FluidRegistry.getFluidStack("potion.weakness", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.mundane", 50), FluidRegistry.getFluidStack("potion.weakness", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.thick", 50), FluidRegistry.getFluidStack("potion.weakness", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.poison", 50), FluidRegistry.getFluidStack("potion.damage", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.health", 50), FluidRegistry.getFluidStack("potion.damage", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.waterbreathing", 50), FluidRegistry.getFluidStack("potion.damage", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.nightvision", 50), FluidRegistry.getFluidStack("potion.invisibility", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.fireresistance", 50), FluidRegistry.getFluidStack("potion.slowness", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.speed", 50), FluidRegistry.getFluidStack("potion.slowness", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.strength", 50), FluidRegistry.getFluidStack("potion.weakness", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.regen", 50), FluidRegistry.getFluidStack("potion.poison", 25), 1024, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.poison.strong", 50), FluidRegistry.getFluidStack("potion.damage.strong", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.health.strong", 50), FluidRegistry.getFluidStack("potion.damage.strong", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.speed.strong", 50), FluidRegistry.getFluidStack("potion.slowness.strong", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.strength.strong", 50), FluidRegistry.getFluidStack("potion.weakness.strong", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.nightvision.long", 50), FluidRegistry.getFluidStack("potion.invisibility.long", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.regen.strong", 50), FluidRegistry.getFluidStack("potion.poison.strong", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.poison.long", 50), FluidRegistry.getFluidStack("potion.damage.long", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.waterbreathing.long", 50), FluidRegistry.getFluidStack("potion.damage.long", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.fireresistance.long", 50), FluidRegistry.getFluidStack("potion.slowness.long", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.speed.long", 50), FluidRegistry.getFluidStack("potion.slowness.long", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.strength.long", 50), FluidRegistry.getFluidStack("potion.weakness.long", 10), 2048, false);
        GTValues.RA.addFermentingRecipe(FluidRegistry.getFluidStack("potion.regen.long", 50), FluidRegistry.getFluidStack("potion.poison.long", 10), 2048, false);

        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_PotatoChips.get(1), ItemList.Food_PotatoChips.get(1));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Potato_On_Stick.get(1), ItemList.Food_Potato_On_Stick_Roasted.get(1));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Bun.get(1), ItemList.Food_Baked_Bun.get(1));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Bread.get(1), ItemList.Food_Baked_Bread.get(1));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Baguette.get(1), ItemList.Food_Baked_Baguette.get(1));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Pizza_Veggie.get(1), ItemList.Food_Baked_Pizza_Veggie.get(1));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Pizza_Cheese.get(1), ItemList.Food_Baked_Pizza_Cheese.get(1));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Pizza_Meat.get(1), ItemList.Food_Baked_Pizza_Meat.get(1));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Baguette.get(1), ItemList.Food_Baked_Baguette.get(1));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Cake.get(1), ItemList.Food_Baked_Cake.get(1));
        GT_ModHandler.addSmeltingRecipe(ItemList.Food_Raw_Cookie.get(1), new ItemStack(Items.COOKIE, 1));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(Items.SLIME_BALL, 1), ItemList.IC2_Resin.get(1));

        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.BOOKSHELF, 1, 32767), new ItemStack(Items.BOOK, 3, 0));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Items.SLIME_BALL, 1), OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 2L));
        GT_ModHandler.addExtractionRecipe(ItemList.IC2_Resin.get(1), OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 3L));
        GT_ModHandler.addExtractionRecipe(GT_ModHandler.getIC2Item(BlockName.sapling, 1), OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 1));
        GT_ModHandler.addExtractionRecipe(GT_ModHandler.getIC2Item(BlockName.leaves, 16), OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 1));
        GT_ModHandler.addExtractionRecipe(ItemList.Cell_Air.get(1), ItemList.Cell_Empty.get(1));
        if (Loader.isModLoaded(aTextEBXL)) {
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "waterplant1", 1, 0), new ItemStack(Items.DYE, 4, 2));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "vines", 1, 0), new ItemStack(Items.DYE, 4, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 11), new ItemStack(Items.DYE, 4, 11));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 10), new ItemStack(Items.DYE, 4, 5));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 9), new ItemStack(Items.DYE, 4, 14));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 8), new ItemStack(Items.DYE, 4, 14));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 7), new ItemStack(Items.DYE, 4, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 6), new ItemStack(Items.DYE, 4, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 5), new ItemStack(Items.DYE, 4, 11));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 0), new ItemStack(Items.DYE, 4, 9));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 4), new ItemStack(Items.DYE, 4, 11));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 3), new ItemStack(Items.DYE, 4, 13));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower1", 1, 3), new ItemStack(Items.DYE, 4, 5));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 2), new ItemStack(Items.DYE, 4, 5));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower1", 1, 1), new ItemStack(Items.DYE, 4, 12));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 15), new ItemStack(Items.DYE, 4, 11));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 14), new ItemStack(Items.DYE, 4, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 13), new ItemStack(Items.DYE, 4, 9));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 12), new ItemStack(Items.DYE, 4, 14));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 11), new ItemStack(Items.DYE, 4, 7));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower1", 1, 7), new ItemStack(Items.DYE, 4, 7));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower1", 1, 2), new ItemStack(Items.DYE, 4, 11));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 13), new ItemStack(Items.DYE, 4, 6));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 6), new ItemStack(Items.DYE, 4, 12));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 5), new ItemStack(Items.DYE, 4, 10));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 2), new ItemStack(Items.DYE, 4, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 1), new ItemStack(Items.DYE, 4, 9));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 0), new ItemStack(Items.DYE, 4, 13));

            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 7), GT_ModHandler.getModItem(aTextEBXL, "extrabiomes.dye", 1, 0));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1, 1), GT_ModHandler.getModItem(aTextEBXL, "extrabiomes.dye", 1, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower3", 1,12), GT_ModHandler.getModItem(aTextEBXL, "extrabiomes.dye", 1, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 4), GT_ModHandler.getModItem(aTextEBXL, "extrabiomes.dye", 1, 1));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower1", 1, 6), GT_ModHandler.getModItem(aTextEBXL, "extrabiomes.dye", 1, 2));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 8), GT_ModHandler.getModItem(aTextEBXL, "extrabiomes.dye", 1, 3));
            GT_ModHandler.addExtractionRecipe(GT_ModHandler.getModItem(aTextEBXL, "flower2", 1, 3), GT_ModHandler.getModItem(aTextEBXL, "extrabiomes.dye", 1, 3));

            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_1", 4, 0), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_1", 4, 1), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_1", 4, 2), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_1", 4, 3), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_1", 4, 4), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_1", 4, 5), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_1", 4, 6), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_1", 4, 7), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_2", 4, 0), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_2", 4, 1), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_2", 4, 2), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_2", 4, 3), ItemList.IC2_Plantball.get(1));
            GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextEBXL, "saplings_2", 4, 4), ItemList.IC2_Plantball.get(1));

        }
        GT_ModHandler.addCompressionRecipe(ItemList.IC2_Compressed_Coal_Chunk.get(1), ItemList.IC2_Industrial_Diamond.get(1));
        GT_ModHandler.addCompressionRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Uranium, 1), GT_ModHandler.getIC2Item(ItemName.nuclear, NuclearResourceType.uranium_238, 1));
        GT_ModHandler.addCompressionRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Uranium235, 1), GT_ModHandler.getIC2Item(ItemName.nuclear, NuclearResourceType.uranium_235, 1));
        GT_ModHandler.addCompressionRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Plutonium, 1), GT_ModHandler.getIC2Item(ItemName.nuclear, NuclearResourceType.plutonium, 1));
        GT_ModHandler.addCompressionRecipe(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Uranium235, 1), GT_ModHandler.getIC2Item(ItemName.nuclear, NuclearResourceType.small_uranium_235, 1));
        GT_ModHandler.addCompressionRecipe(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Plutonium, 1), GT_ModHandler.getIC2Item(ItemName.nuclear, NuclearResourceType.small_plutonium, 1));
        GT_ModHandler.addCompressionRecipe(new ItemStack(Blocks.ICE, 2, 32767), new ItemStack(Blocks.PACKED_ICE, 1, 0));
        GT_ModHandler.addCompressionRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Ice, 1), new ItemStack(Blocks.ICE, 1, 0));
        GT_ModHandler.addCompressionRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.CertusQuartz, 4L), GT_ModHandler.getModItem(aTextAE, "tile.BlockQuartz", 1));
        GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextAE, aTextAEMM, 8, 10), GT_ModHandler.getModItem(aTextAE, "tile.BlockQuartz", 1));
        GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextAE, aTextAEMM, 8, 11), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0));
        GT_ModHandler.addCompressionRecipe(GT_ModHandler.getModItem(aTextAE, aTextAEMM, 8, 12), GT_ModHandler.getModItem(aTextAE, "tile.BlockFluix", 1));
        GT_ModHandler.addCompressionRecipe(new ItemStack(Items.QUARTZ, 4, 0), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0));
        GT_ModHandler.addCompressionRecipe(new ItemStack(Items.WHEAT, 9, 0), new ItemStack(Blocks.HAY_BLOCK, 1, 0));
        GT_ModHandler.addCompressionRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Glowstone, 4L), new ItemStack(Blocks.GLOWSTONE, 1));
        GTValues.RA.addCutterRecipe(OreDictUnifier.get(OrePrefix.block, Materials.Graphite, 1), OreDictUnifier.get(OrePrefix.ingot, Materials.Graphite, 9L), GTValues.NI, 500, 48);
        GT_ModHandler.removeFurnaceSmelting(OreDictUnifier.get(OrePrefix.ore, Materials.Graphite, 1));
        GT_ModHandler.addSmeltingRecipe(OreDictUnifier.get(OrePrefix.ore, Materials.Graphite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Graphite, 1));
        GT_ModHandler.removeFurnaceSmelting(OreDictUnifier.get(OrePrefix.oreBlackgranite, Materials.Graphite, 1));
        GT_ModHandler.addSmeltingRecipe(OreDictUnifier.get(OrePrefix.oreBlackgranite, Materials.Graphite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Graphite, 1));
        GT_ModHandler.removeFurnaceSmelting(OreDictUnifier.get(OrePrefix.oreEndstone, Materials.Graphite, 1));
        GT_ModHandler.addSmeltingRecipe(OreDictUnifier.get(OrePrefix.oreEndstone, Materials.Graphite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Graphite, 1));
        GT_ModHandler.removeFurnaceSmelting(OreDictUnifier.get(OrePrefix.oreNetherrack, Materials.Graphite, 1));
        GT_ModHandler.addSmeltingRecipe(OreDictUnifier.get(OrePrefix.oreNetherrack, Materials.Graphite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Graphite, 1));
        GT_ModHandler.removeFurnaceSmelting(OreDictUnifier.get(OrePrefix.oreRedgranite, Materials.Graphite, 1));
        GT_ModHandler.addSmeltingRecipe(OreDictUnifier.get(OrePrefix.oreRedgranite, Materials.Graphite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Graphite, 1));

        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getModItem(aTextAE, "tile.BlockSkyStone", 1, 32767), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 45), GTValues.NI, 0, false);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getModItem(aTextAE, "tile.BlockSkyChest", 1, 32767), GT_ModHandler.getModItem(aTextAE, aTextAEMM, 8, 45), GTValues.NI, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.BLAZE_ROD, 1), new ItemStack(Items.BLAZE_POWDER, 3), new ItemStack(Items.BLAZE_POWDER, 1), 50, false);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getModItem("Railcraft", "cube.crushed.obsidian", 1), OreDictUnifier.get(OrePrefix.dust, Materials.Obsidian, 1), GTValues.NI, 0, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.FLINT, 1, 32767), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Flint, 4L), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Flint, 1), 40, true);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.RED_MUSHROOM, 1, 32767), ItemList.IC2_Grin_Powder.get(1));
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.ITEM_FRAME, 1, 32767), new ItemStack(Items.LEATHER, 1), OreDictUnifier.getDust(Materials.Wood, OrePrefix.stick.mMaterialAmount * 4L), 95, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.BOW, 1, 0), new ItemStack(Items.STRING, 3), OreDictUnifier.getDust(Materials.Wood, OrePrefix.stick.mMaterialAmount * 3L), 95, false);

        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0), new ItemStack(Blocks.STONEBRICK, 1, 2), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.STONE, 1, 0), new ItemStack(Blocks.COBBLESTONE, 1, 0), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.COBBLESTONE, 1, 0), new ItemStack(Blocks.GRAVEL, 1, 0), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.SANDSTONE, 1, 32767), new ItemStack(Blocks.SAND, 1, 0), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.ICE, 1, 0), OreDictUnifier.get(OrePrefix.dust, Materials.Ice, 1), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.PACKED_ICE, 1, 0), OreDictUnifier.get(OrePrefix.dust, Materials.Ice, 2L), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.HARDENED_CLAY, 1, 0), OreDictUnifier.get(OrePrefix.dust, Materials.Clay, 1), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, 32767), OreDictUnifier.get(OrePrefix.dust, Materials.Clay, 1), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.BRICK_BLOCK, 1, 0), new ItemStack(Items.BRICK, 3, 0), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.NETHER_BRICK, 1, 0), new ItemStack(Items.NETHERBRICK, 3, 0), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.STAINED_GLASS, 1, 32767), OreDictUnifier.get(OrePrefix.dust, Materials.Glass, 1), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.GLASS, 1, 32767), OreDictUnifier.get(OrePrefix.dust, Materials.Glass, 1), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.STAINED_GLASS_PANE, 1, 32767), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Glass, 3L), 16, 10);
        GTValues.RA.addForgeHammerRecipe(new ItemStack(Blocks.GLASS_PANE, 1, 32767), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Glass, 3L), 16, 10);

        GTValues.RA.addForgeHammerRecipe(GT_ModHandler.getModItem("HardcoreEnderExpansion", "endium_ore", 1), OreDictUnifier.get(OrePrefix.crushed, Materials.Endium, 1), 16, 10);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getModItem("HardcoreEnderExpansion", "endium_ore", 1), OreDictUnifier.get(OrePrefix.crushed, Materials.Endium, 2), OreDictUnifier.get(OrePrefix.dust, Materials.Endstone, 1), 50, GTValues.NI, 0, true);
        OreDictUnifier.set(OrePrefix.ingot, Materials.Endium, GT_ModHandler.getModItem("HardcoreEnderExpansion", "endium_ingot", 1), true, true);

        GTValues.RA.addAmplifier(ItemList.IC2_Scrap.get(9L), 180, 1);
        GTValues.RA.addAmplifier(ItemList.IC2_Scrapbox.get(1), 180, 1);

        GTValues.RA.addBoxingRecipe(ItemList.IC2_Scrap.get(9L), ItemList.Schematic_3by3.get(0L), ItemList.IC2_Scrapbox.get(1), 16, 1);
        GTValues.RA.addBoxingRecipe(ItemList.Food_Fries.get(1), OreDictUnifier.get(OrePrefix.plateDouble, Materials.Paper, 1), ItemList.Food_Packaged_Fries.get(1), 64, 16);
        GTValues.RA.addBoxingRecipe(ItemList.Food_PotatoChips.get(1), OreDictUnifier.get(OrePrefix.foil, Materials.Aluminium, 1), ItemList.Food_Packaged_PotatoChips.get(1), 64, 16);
        GTValues.RA.addBoxingRecipe(ItemList.Food_ChiliChips.get(1), OreDictUnifier.get(OrePrefix.foil, Materials.Aluminium, 1), ItemList.Food_Packaged_ChiliChips.get(1), 64, 16);

        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel, 3L), OreDictUnifier.get(OrePrefix.ingot, Materials.Silicon, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.ElectricalSteel, 4L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 2L), (int) Math.max(Materials.ElectricalSteel.getMass() / 40L, 1) * Materials.ElectricalSteel.mBlastFurnaceTemp, 120, Materials.ElectricalSteel.mBlastFurnaceTemp);

        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Gold, 1L), OreDictUnifier.get(OrePrefix.dust, Materials.Glowstone, 1L), Materials.Redstone.getMolten(144), GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.EnergeticAlloy, 1L), null, Materials.EnergeticAlloy.mBlastFurnaceTemp / 10, 120, Materials.EnergeticAlloy.mBlastFurnaceTemp);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.EnergeticAlloy, 1L), OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl, 1L), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.PhasedGold, 1L), null, Materials.VibrantAlloy.mBlastFurnaceTemp / 10, 480, Materials.VibrantAlloy.mBlastFurnaceTemp);
        GTValues.RA.addAlloySmelterRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1L), OreDictUnifier.get(OrePrefix.ingot, Materials.Silicon, 1L), OreDictUnifier.get(OrePrefix.ingot, Materials.RedstoneAlloy, 1L), 400, 24);
        GTValues.RA.addAlloySmelterRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1L), OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 1L), OreDictUnifier.get(OrePrefix.ingot, Materials.ConductiveIron, 1L), 400, 24);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 1L), OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl, 1L), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.PhasedIron, 1L), null, Materials.PulsatingIron.mBlastFurnaceTemp / 10, 120, Materials.PulsatingIron.mBlastFurnaceTemp);
        GTValues.RA.addAlloySmelterRecipe(new ItemStack(Blocks.SOUL_SAND), OreDictUnifier.get(OrePrefix.ingot, Materials.Gold, 1L), OreDictUnifier.get(OrePrefix.ingot, Materials.Soularium, 1L), 400, 24);

        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Tungsten, 1), OreDictUnifier.get(OrePrefix.ingot, Materials.Steel, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.ingotHot, Materials.TungstenSteel, 2L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1), (int) Math.max(Materials.TungstenSteel.getMass() / 80L, 1) * Materials.TungstenSteel.mBlastFurnaceTemp, 480, Materials.TungstenSteel.mBlastFurnaceTemp);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Tungsten, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.ingotHot, Materials.TungstenCarbide, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Ash, 2L), (int) Math.max(Materials.TungstenCarbide.getMass() / 40L, 1) * Materials.TungstenCarbide.mBlastFurnaceTemp, 480, Materials.TungstenCarbide.mBlastFurnaceTemp);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Vanadium, 3L), OreDictUnifier.get(OrePrefix.ingot, Materials.Gallium, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.ingotHot, Materials.VanadiumGallium, 4L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 2L), (int) Math.max(Materials.VanadiumGallium.getMass() / 40L, 1) * Materials.VanadiumGallium.mBlastFurnaceTemp, 480, Materials.VanadiumGallium.mBlastFurnaceTemp);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Niobium, 1), OreDictUnifier.get(OrePrefix.ingot, Materials.Titanium, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.ingotHot, Materials.NiobiumTitanium, 2L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1), (int) Math.max(Materials.NiobiumTitanium.getMass() / 80L, 1) * Materials.NiobiumTitanium.mBlastFurnaceTemp, 480, Materials.NiobiumTitanium.mBlastFurnaceTemp);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Nickel, 4L), OreDictUnifier.get(OrePrefix.ingot, Materials.Chrome, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.ingotHot, Materials.Nichrome, 5L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 2L), (int) Math.max(Materials.Nichrome.getMass() / 32L, 1) * Materials.Nichrome.mBlastFurnaceTemp, 480, Materials.Nichrome.mBlastFurnaceTemp);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Ruby, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 3L), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 1), 400, 100, 1200);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.Ruby, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 3L), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 1), 320, 100, 1200);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.GreenSapphire, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 3L), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 1), 400, 100, 1200);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.GreenSapphire, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 3L), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 1), 320, 100, 1200);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sapphire, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 3L), GTValues.NI, 400, 100, 1200);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.Sapphire, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.Aluminium, 3L), GTValues.NI, 320, 100, 1200);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Ilmenite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.WroughtIron, 4L), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Rutile, 4L), 800, 500, 1700);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.Ilmenite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.WroughtIron, 4L), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Rutile, 4L), 800, 500, 1700);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Magnesium, 2L), GTValues.NI, Materials.Titaniumtetrachloride.getFluid(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.ingotHot, Materials.Titanium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Magnesiumchloride, 2L), 800, 480, Materials.Titanium.mBlastFurnaceTemp + 200);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Galena, 1), GTValues.NI, Materials.Oxygen.getGas(2000L), GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.Silver, 4L), OreDictUnifier.get(OrePrefix.nugget, Materials.Lead, 4L), 400, 500, 1500);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.Galena, 1), GTValues.NI, Materials.Oxygen.getGas(2000L), GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.Silver, 5L), OreDictUnifier.get(OrePrefix.nugget, Materials.Lead, 5L), 320, 500, 1500);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Magnetite, 1), GTValues.NI, Materials.Oxygen.getGas(2000L), GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.WroughtIron, 4L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1), 400, 500, 1000);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.Magnetite, 1), GTValues.NI, Materials.Oxygen.getGas(2000L), GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.WroughtIron, 5L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Ash, 1), 320, 500, 1000);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 1), GTValues.NI, Materials.Oxygen.getGas(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1), 500, 120, 1000);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.PigIron, 1), GTValues.NI, Materials.Oxygen.getGas(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1), 100, 120, 1000);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron, 1), GTValues.NI, Materials.Oxygen.getGas(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1), 100, 120, 1000);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.ShadowIron, 1), GTValues.NI, Materials.Oxygen.getGas(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.ShadowSteel, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1), 500, 120, 1100);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.MeteoricIron, 1), GTValues.NI, Materials.Oxygen.getGas(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.MeteoricSteel, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.DarkAsh, 1), 500, 120, 1200);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Copper, 1), GTValues.NI, Materials.Oxygen.getGas(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.AnnealedCopper, 1), GTValues.NI, 500, 120, 1200);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 1), GTValues.NI, Materials.Oxygen.getGas(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.AnnealedCopper, 1), GTValues.NI, 500, 120, 1200);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.ElectricalSteel, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Obsidian, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.ingot, Materials.DarkSteel, 1), GTValues.NI, 4000, 120, 2000);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Iridium, 3L), OreDictUnifier.get(OrePrefix.ingot, Materials.Osmium, 1), Materials.Helium.getGas(1000), null, OreDictUnifier.get(OrePrefix.ingotHot, Materials.Osmiridium, 4L), null, 500, 1920, 2900);
        GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Naquadah, 1), OreDictUnifier.get(OrePrefix.ingot, Materials.Osmiridium, 1), Materials.Argon.getGas(1000), null, OreDictUnifier.get(OrePrefix.ingotHot, Materials.NaquadahAlloy, 2L), null, 500, 30720, Materials.NaquadahAlloy.mBlastFurnaceTemp);

        if (!GregTechAPI.mIC2Classic) {
            GTValues.RA.addCannerRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Lithium, 1), GT_ModHandler.getIC2Item(ItemName.lithium_fuel_rod, 1, 1), null, 16, 64);
            //TODO GTValues.RA.addFluidExtractionRecipe(GT_ModHandler.getIC2Item("TritiumCell", 1), GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, 1), Materials.Tritium.getGas(32), 10000, 16, 64);
            GTValues.RA.addCannerRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Thorium, 3), ItemList.ThoriumCell_1.get(1), null, 30, 16);
            GTValues.RA.addCannerRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, 1), OreDictUnifier.get(OrePrefix.dust, Materials.NaquadahEnriched, 3), ItemList.NaquadahCell_1.get(1L, new Object[0]), null, 30, 16);
            GTValues.RA.addCannerRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, 1), GT_ModHandler.getIC2Item(ItemName.uranium_fuel_rod, 1), ItemList.Uraniumcell_1.get(1), null, 30, 16);
            GTValues.RA.addCannerRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, 1), GT_ModHandler.getIC2Item(ItemName.mox_fuel_rod, 1), ItemList.Moxcell_1.get(1), null, 30, 16);
        }

        GTValues.RA.addFusionReactorRecipe(Materials.Lithium.getMolten(16), Materials.Tungsten.getMolten(16), Materials.Iridium.getMolten(16), 32, 32768, 300000000);
        GTValues.RA.addFusionReactorRecipe(Materials.Deuterium.getGas(125), Materials.Tritium.getGas(125), Materials.Helium.getPlasma(125), 16, 4096, 40000000);  //Mark 1 Cheap //
        GTValues.RA.addFusionReactorRecipe(Materials.Deuterium.getGas(125), Materials.Helium_3.getGas(125), Materials.Helium.getPlasma(125), 16, 2048, 60000000); //Mark 1 Expensive //
        GTValues.RA.addFusionReactorRecipe(Materials.Carbon.getMolten(125), Materials.Helium_3.getGas(125), Materials.Oxygen.getPlasma(125), 32, 4096, 80000000); //Mark 1 Expensive //
        GTValues.RA.addFusionReactorRecipe(Materials.Aluminium.getMolten(16), Materials.Lithium.getMolten(16), Materials.Sulfur.getPlasma(125), 32, 10240, 240000000); //Mark 2 Cheap
        GTValues.RA.addFusionReactorRecipe(Materials.Beryllium.getMolten(16), Materials.Deuterium.getGas(375), Materials.Nitrogen.getPlasma(175), 16, 16384, 180000000); //Mark 2 Expensive //
        GTValues.RA.addFusionReactorRecipe(Materials.Silicon.getMolten(16), Materials.Magnesium.getMolten(16), Materials.Iron.getPlasma(125), 32, 8192, 360000000); //Mark 3 Cheap //
        GTValues.RA.addFusionReactorRecipe(Materials.Potassium.getMolten(16), Materials.Fluorine.getGas(125), Materials.Nickel.getPlasma(125), 16, 32768, 480000000); //Mark 3 Expensive //
        GTValues.RA.addFusionReactorRecipe(Materials.Beryllium.getMolten(16), Materials.Tungsten.getMolten(16), Materials.Platinum.getMolten(16), 32, 32768, 150000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Neodymium.getMolten(16), Materials.Hydrogen.getGas(48), Materials.Europium.getMolten(16), 64, 24576, 150000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Lutetium.getMolten(16), Materials.Chrome.getMolten(16), Materials.Americium.getMolten(16), 96, 49152, 200000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Plutonium.getMolten(16), Materials.Thorium.getMolten(16), Materials.Naquadah.getMolten(16), 64, 32768, 300000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Americium.getMolten(16), Materials.Naquadria.getMolten(16), Materials.Neutronium.getMolten(1), 1200, 98304, 600000000); //

        GTValues.RA.addFusionReactorRecipe(Materials.Tungsten.getMolten(16), Materials.Helium.getGas(16), Materials.Osmium.getMolten(16), 64, 24578, 150000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Manganese.getMolten(16), Materials.Hydrogen.getGas(16), Materials.Iron.getMolten(16), 64, 8192, 120000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Mercury.getFluid(16), Materials.Magnesium.getMolten(16), Materials.Uranium.getMolten(16), 64, 49152, 240000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Gold.getMolten(16), Materials.Aluminium.getMolten(16), Materials.Uranium.getMolten(16), 64, 49152, 240000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Uranium.getMolten(16), Materials.Helium.getGas(16), Materials.Plutonium.getMolten(16), 128, 49152, 480000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Vanadium.getMolten(16), Materials.Hydrogen.getGas(125), Materials.Chrome.getMolten(16), 64, 24576, 140000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Gallium.getMolten(16), Materials.Radon.getGas(125), Materials.Duranium.getMolten(16), 64, 16384, 140000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Titanium.getMolten(16), Materials.Duranium.getMolten(125), Materials.Tritanium.getMolten(16), 64, 32768, 200000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.Gold.getMolten(16), Materials.Mercury.getFluid(16), Materials.Radon.getGas(125), 64, 32768, 200000000); //
        GTValues.RA.addFusionReactorRecipe(Materials.NaquadahEnriched.getMolten(15), Materials.Radon.getGas(125), Materials.Naquadria.getMolten(3), 64, 49152, 400000000); //

        GT_ModHandler.removeRecipeByOutput(ItemList.IC2_Fertilizer.get(1));
        GTValues.RA.addImplosionRecipe(ItemList.IC2_Compressed_Coal_Chunk.get(1), 8, ItemList.IC2_Industrial_Diamond.get(1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 4L));
        GTValues.RA.addImplosionRecipe(ItemList.Ingot_IridiumAlloy.get(1), 8, OreDictUnifier.get(OrePrefix.plateAlloy, Materials.Iridium, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 4L));

        if (Loader.isModLoaded("GalacticraftMars")) {
            GT_ModHandler.addCraftingRecipe(ItemList.Ingot_Heavy1.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"BhB", "CAS", "B B", 'B', OrePrefix.bolt.get(Materials.StainlessSteel), 'C', OrePrefix.compressed.get(Materials.Bronze), 'A', OrePrefix.compressed.get(Materials.Aluminium), 'S', OrePrefix.compressed.get(Materials.Steel)});
            GT_ModHandler.addCraftingRecipe(ItemList.Ingot_Heavy2.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" BB", "hPC", " BB", 'B', OrePrefix.bolt.get(Materials.Tungsten), 'C', OrePrefix.compressed.get(Materials.MeteoricIron), 'P', GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 1L)});
            GT_ModHandler.addCraftingRecipe(ItemList.Ingot_Heavy3.get(1L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" BB", "hPC", " BB", 'B', OrePrefix.bolt.get(Materials.TungstenSteel), 'C', OrePrefix.compressed.get(Materials.Desh), 'P', GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1L, 3)});

            GTValues.RA.addImplosionRecipe(ItemList.Ingot_Heavy1.get(1), 8, GT_ModHandler.getModItem("GalacticraftCore", "item.heavyPlating", 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.StainlessSteel, 2L));
            GTValues.RA.addImplosionRecipe(ItemList.Ingot_Heavy2.get(1), 8, GT_ModHandler.getModItem("GalacticraftMars", "item.null", 1, 3), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Tungsten, 2L));
            GTValues.RA.addImplosionRecipe(ItemList.Ingot_Heavy3.get(1), 8, GT_ModHandler.getModItem("GalacticraftMars", "item.itemBasicAsteroids", 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.TungstenSteel, 2L));

            GTValues.RA.addCentrifugeRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.moonBlock", 1L, 5), null, null, Materials.Helium_3.getGas(33), new ItemStack(Blocks.SAND,1), OreDictUnifier.get(OrePrefix.dust, Materials.Aluminium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Calcite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Magnesium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Rutile, 1), new int[]{5000,400,400,100,100,100}, 400, 8);
            GTValues.RA.addPulveriserRecipe(GT_ModHandler.getModItem("GalacticraftCore", "tile.moonBlock", 1L, 4), new ItemStack[]{GT_ModHandler.getModItem("GalacticraftCore", "tile.moonBlock", 1L, 5)}, null, 400, 2);
            GTValues.RA.addFluidExtractionRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.mars", 1L, 9), new ItemStack(Blocks.STONE, 1), Materials.Iron.getMolten(50), 10000, 250, 16);
            GTValues.RA.addPulveriserRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.asteroidsBlock", 1L, 1), new ItemStack[]{GT_ModHandler.getModItem("GalacticraftMars", "tile.asteroidsBlock", 1L, 0)}, null, 400, 2);
            GTValues.RA.addPulveriserRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.asteroidsBlock", 1L, 2), new ItemStack[]{GT_ModHandler.getModItem("GalacticraftMars", "tile.asteroidsBlock", 1L, 0)}, null, 400, 2);
            GTValues.RA.addCentrifugeRecipe(GT_ModHandler.getModItem("GalacticraftMars", "tile.asteroidsBlock", 1L, 0), null, null, Materials.Nitrogen.getGas(33), new ItemStack(Blocks.SAND,1), OreDictUnifier.get(OrePrefix.dust, Materials.Aluminium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Nickel, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Gallium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Platinum, 1), new int[]{5000,400,400,100,100,100}, 400, 8);
        }

        GTValues.RA.addFluidExtractionRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Quartzite, 1), null, Materials.Glass.getMolten(72), 10000, 600, 28);//(OreDictUnifier.get(OrePrefix.cell, Materials.SiliconDioxide,1), OreDictUnifier.get(OrePrefix.dust,Materials.SiliconDioxide,2L),OreDictUnifier.get(OrePrefix.cell, Materials.Glass,1)/** GTUtility.fillFluidContainer(Materials.Glass.getMolten(1000), ItemList.Cell_Empty.get(1, new Object[0]), true, true)**//*, 600, 16);

        GTValues.RA.addDistillationTowerRecipe(Materials.Creosote.getFluid(24L), new FluidStack[]{Materials.Lubricant.getFluid(12L)}, null, 16, 96);
        GTValues.RA.addDistillationTowerRecipe(Materials.SeedOil.getFluid(32L), new FluidStack[]{Materials.Lubricant.getFluid(12L)}, null, 16, 96);
        GTValues.RA.addDistillationTowerRecipe(Materials.FishOil.getFluid(24L), new FluidStack[]{Materials.Lubricant.getFluid(12L)}, null, 16, 96);
        GTValues.RA.addDistillationTowerRecipe(Materials.Biomass.getFluid(150L), new FluidStack[]{Materials.Ethanol.getFluid(60L), Materials.Water.getFluid(60L)}, OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 1), 25, 64);
        GTValues.RA.addDistillationTowerRecipe(Materials.Water.getFluid(288L), new FluidStack[]{GT_ModHandler.getDistilledWater(260L)}, null, 16, 64);
        if (!GregTechAPI.mIC2Classic) {
            GTValues.RA.addDistillationTowerRecipe(new FluidStack(FluidName.biomass.getInstance(), 250), new FluidStack[]{new FluidStack(FluidRegistry.getFluid("ic2biogas"), 8000), Materials.Water.getFluid(125L)}, ItemList.IC2_Fertilizer.get(1), 250, 480);
            //GTValues.RA.addFuel(GT_ModHandler.getIC2Item(ItemName.fluid_cell, FluidName.biogas, 1), null, 32, 1); TODO Custom GT Biogas Cell

            GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 1), new FluidStack(FluidRegistry.getFluid("ic2biomass"), 1), new FluidStack(FluidRegistry.getFluid("ic2biogas"), 32), 40, 16, false);
            GTValues.RA.addDistilleryRecipe(ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new FluidStack(FluidRegistry.getFluid("ic2biomass"), 4), Materials.Water.getFluid(2), 80, 30, false);
        }
        GTValues.RA.addFuel(new ItemStack(Items.GOLDEN_APPLE,1,1), new ItemStack(Items.APPLE, 1), 6400, 5);

        GTValues.RA.addFuel(GT_ModHandler.getModItem("Thaumcraft", "ItemShard", 1, 6), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ForbiddenMagic", "GluttonyShard", 1), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ForbiddenMagic", "FMResource", 1, 3), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ForbiddenMagic", "NetherShard", 1), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ForbiddenMagic", "NetherShard", 1, 1), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ForbiddenMagic", "NetherShard", 1, 2), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ForbiddenMagic", "NetherShard", 1, 3), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ForbiddenMagic", "NetherShard", 1, 4), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ForbiddenMagic", "NetherShard", 1, 5), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ForbiddenMagic", "NetherShard", 1, 6), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("TaintedMagic", "WarpedShard", 1), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("TaintedMagic", "FluxShard", 1), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("TaintedMagic", "EldritchShard", 1), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ThaumicTinkerer", "kamiResource", 1, 6), null, 720, 5);
        GTValues.RA.addFuel(GT_ModHandler.getModItem("ThaumicTinkerer", "kamiResource", 1, 7), null, 720, 5);

        GTValues.RA.addElectrolyzerRecipe(GTValues.NI, ItemList.Cell_Empty.get(1), Materials.Water.getFluid(3000L), Materials.Hydrogen.getGas(2000L), OreDictUnifier.get(OrePrefix.cell, Materials.Oxygen, 1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 1500, 30);
        GTValues.RA.addElectrolyzerRecipe(GTValues.NI, ItemList.Cell_Empty.get(1), GT_ModHandler.getDistilledWater(3000L), Materials.Hydrogen.getGas(2000L), OreDictUnifier.get(OrePrefix.cell, Materials.Oxygen, 1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 1500, 30);

        //GTValues.RA.addElectrolyzerRecipe(GT_ModHandler.getIC2Item(ItemName.fluid_cell, FluidName.heavy_water, 3), 0, OreDictUnifier.get(OrePrefix.cell, Materials.Hydrogen, 2L), OreDictUnifier.get(OrePrefix.cell, Materials.Oxygen, 1), GTValues.NI, GTValues.NI, GTValues.NI, null, 30, 30); TODO GT Heavy Water Cell
        GTValues.RA.addElectrolyzerRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Water, 1), 0, GT_ModHandler.getIC2Item(ItemName.fluid_cell, FluidName.heavy_water, 1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, 490, 30);
        GTValues.RA.addElectrolyzerRecipe(ItemList.Dye_Bonemeal.get(3L), 0, OreDictUnifier.get(OrePrefix.dust, Materials.Calcium, 1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, 98, 26);
        GTValues.RA.addElectrolyzerRecipe(new ItemStack(Blocks.SAND, 8), 0, OreDictUnifier.get(OrePrefix.dust, Materials.SiliconDioxide, 1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, 500, 25);
        GTValues.RA.addElectrolyzerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Tungstate, 7L), GTValues.NI, Materials.Hydrogen.getGas(7000L), Materials.Oxygen.getGas(4000L), OreDictUnifier.get(OrePrefix.dust, Materials.Tungsten, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Lithium, 2L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, new int[]{10000, 10000, 0, 0, 0, 0}, 120, 1920);
        GTValues.RA.addElectrolyzerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Scheelite, 7L), GTValues.NI, Materials.Hydrogen.getGas(7000L), Materials.Oxygen.getGas(4000L), OreDictUnifier.get(OrePrefix.dust, Materials.Tungsten, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Calcium, 2L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, new int[]{10000, 10000, 0, 0, 0, 0}, 120, 1920);
        //GTValues.RA.addElectrolyzerRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.CarbonDioxide, 4), GTValues.NI, GTValues.NF,GTValues.NF,									   OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 3),   OreDictUnifier.get(OrePrefix.cell, Materials.Oxygen, 1), ItemList.Cell_Empty.get(3, new Object[0]), GTValues.NI, GTValues.NI, GTValues.NI,new int[]{10000,10000,10000,0,0,0}, 180, 60);

        GTValues.RA.addElectrolyzerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Graphite, 1), 0, OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 4), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, 100, 64);

        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.NetherQuartz, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), Materials.Water.getFluid(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.gem, Materials.NetherQuartz, 3L), 500);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.CertusQuartz, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), Materials.Water.getFluid(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.gem, Materials.CertusQuartz, 3L), 500);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Quartzite, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), Materials.Water.getFluid(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.gem, Materials.Quartzite, 3L), 500);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.NetherQuartz, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), GT_ModHandler.getDistilledWater(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.gem, Materials.NetherQuartz, 3L), 500);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.CertusQuartz, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), GT_ModHandler.getDistilledWater(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.gem, Materials.CertusQuartz, 3L), 500);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Quartzite, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), GT_ModHandler.getDistilledWater(1000L), GTValues.NF, OreDictUnifier.get(OrePrefix.gem, Materials.Quartzite, 3L), 500);

        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Uraninite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Aluminium, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dust, Materials.Uranium, 1), 1000);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Uraninite, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Magnesium, 1), GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dust, Materials.Uranium, 1), 1000);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Calcium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 1), Materials.Oxygen.getGas(3000L), GTValues.NF, OreDictUnifier.get(OrePrefix.dust, Materials.Calcite, 5L), 500);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 1), GTValues.NI, Materials.Hydrogen.getGas(4000L), Materials.Methane.getGas(5000L), GTValues.NI, 3500);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 1), Materials.Water.getFluid(2000L), Materials.SulfuricAcid.getFluid(3000L), GTValues.NI, 1150);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), Materials.Oxygen.getGas(4000L), Materials.SodiumPersulfate.getFluid(6000L), GTValues.NI, 8000);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Nitrogen, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 1), Materials.Water.getFluid(2000L), Materials.Glyceryl.getFluid(4000L), ItemList.Cell_Empty.get(1), 2700);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Fuel, 1), GTValues.NI, Materials.Glyceryl.getFluid(250L), Materials.NitroFuel.getFluid(1000L), ItemList.Cell_Empty.get(1), 250);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Glyceryl, 1), GTValues.NI, Materials.Fuel.getFluid(4000L), Materials.NitroFuel.getFluid(4000L), ItemList.Cell_Empty.get(1), 1000);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.LightFuel, 1), GTValues.NI, Materials.Glyceryl.getFluid(250L), Materials.NitroFuel.getFluid(1250L), ItemList.Cell_Empty.get(1), 250);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Glyceryl, 1), GTValues.NI, Materials.LightFuel.getFluid(4000L), Materials.NitroFuel.getFluid(5000L), ItemList.Cell_Empty.get(1), 1000);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Nitrogen, 1), GTValues.NI, Materials.Oxygen.getGas(2000L), Materials.NitrogenDioxide.getGas(3000L), ItemList.Cell_Empty.get(1), 1250);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Oxygen, 2L), GTValues.NI, Materials.Nitrogen.getGas(1000L), Materials.NitrogenDioxide.getGas(3000L), ItemList.Cell_Empty.get(2L), 1250);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Oxygen, 1), GTValues.NI, Materials.Hydrogen.getGas(2000L), GT_ModHandler.getDistilledWater(3000L), ItemList.Cell_Empty.get(1), 10);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Hydrogen, 1), GTValues.NI, Materials.Oxygen.getGas(500L), GT_ModHandler.getDistilledWater(1500L), ItemList.Cell_Empty.get(1), 5);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Tin, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Saltpeter, 1), Materials.Glass.getMolten(864L), GTValues.NF, GT_ModHandler.getModItem("Railcraft", "tile.railcraft.glass", 6), 50);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Rutile, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 3L), Materials.Chlorine.getGas(2000L), Materials.Titaniumtetrachloride.getFluid(1000L), OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 1), 500, 480);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sodium, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Magnesiumchloride, 2L), GTValues.NF, Materials.Chlorine.getGas(1500L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Magnesium, 6L), 300, 240);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 9L), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), GTValues.NF, Materials.Rubber.getMolten(1296L), GTValues.NI, 600, 16);

        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.NitrogenDioxide, 1L), OreDictUnifier.get(OrePrefix.cell, Materials.Hydrogen, 3L), Materials.Air.getGas(500), new FluidStack(ItemList.sRocketFuel,3000), ItemList.Cell_Water.get(4, new Object[0]), 1000, 388);

        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold, 8L), new ItemStack(Items.MELON, 1, 32767), new ItemStack(Items.SPECKLED_MELON, 1, 0), 50);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.nugget, Materials.Gold, 8L), new ItemStack(Items.CARROT, 1, 32767), new ItemStack(Items.GOLDEN_CARROT, 1, 0), 50);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Gold, 8L), new ItemStack(Items.APPLE, 1, 32767), new ItemStack(Items.GOLDEN_APPLE, 1, 0), 50);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.block, Materials.Gold, 8L), new ItemStack(Items.APPLE, 1, 32767), new ItemStack(Items.GOLDEN_APPLE, 1, 1), 50);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Blaze, 1), OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl, 1), OreDictUnifier.get(OrePrefix.gem, Materials.EnderEye, 1), 50);
        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Blaze, 1), new ItemStack(Items.SLIME_BALL, 1, 32767), new ItemStack(Items.MAGMA_CREAM, 1, 0), 50);

        GTValues.RA.addChemicalRecipe(OreDictUnifier.get(OrePrefix.ingot,Materials.Plutonium, 6), null, null, Materials.Radon.getGas(100), OreDictUnifier.get(OrePrefix.dust,Materials.Plutonium, 6), 12000, 8);
        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.EnderEye, 1), Materials.Radon.getGas(250), ItemList.QuantumEye.get(1), null, null, null, 480, 384);
        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.NetherStar, 1), Materials.Radon.getGas(1250), ItemList.QuantumStar.get(1), null, null, null, 1920, 384);
        GTValues.RA.addAutoclaveRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.NetherStar, 1), Materials.Neutronium.getMolten(288), ItemList.Gravistar.get(1), 10000, 480, 7680);

        GTValues.RA.addBenderRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), OreDictUnifier.get(OrePrefix.plateAlloy, Materials.Advanced, 1), 100, 8);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Aluminium, 6L), ItemList.RC_Rail_Standard.get(2L), 200, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Iron, 6L), ItemList.RC_Rail_Standard.get(4L), 400, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.WroughtIron, 6L), ItemList.RC_Rail_Standard.get(5L), 400, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Bronze, 6L), ItemList.RC_Rail_Standard.get(3L), 300, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Steel, 6L), ItemList.RC_Rail_Standard.get(8L), 800, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.StainlessSteel, 6L), ItemList.RC_Rail_Standard.get(12L), 1200, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Titanium, 6L), ItemList.RC_Rail_Standard.get(16L), 1600, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.TungstenSteel, 6L), ItemList.RC_Rail_Reinforced.get(24L), 2400, 30);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Aluminium, 12L), ItemList.RC_Rebar.get(4L), 200, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Iron, 12L), ItemList.RC_Rebar.get(8L), 400, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.WroughtIron, 12L), ItemList.RC_Rebar.get(10L), 400, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Bronze, 12L), ItemList.RC_Rebar.get(8L), 400, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Steel, 12L), ItemList.RC_Rebar.get(16L), 800, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.StainlessSteel, 12L), ItemList.RC_Rebar.get(24L), 1200, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Titanium, 12L), ItemList.RC_Rebar.get(32L), 1600, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.TungstenSteel, 12L), ItemList.RC_Rebar.get(48L), 2400, 15);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Tin, 12L), ItemList.Cell_Empty.get(6L), 1200, 8);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 12L), new ItemStack(Items.BUCKET, 4, 0), 800, 4);
        GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 12L), new ItemStack(Items.BUCKET, 4, 0), 800, 4);
        GTValues.RA.addBenderRecipe(ItemList.IC2_Item_Casing_Iron.get(2L), GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.fuel_rod, 1), 100, 8);
        GTValues.RA.addBenderRecipe(ItemList.IC2_Item_Casing_Tin.get(1), ItemList.IC2_Food_Can_Empty.get(1), 100, 8);
        GTValues.RA.addPulveriserRecipe(OreDictUnifier.get(OrePrefix.block, Materials.Marble, 1), new ItemStack[]{OreDictUnifier.get(OrePrefix.dust, Materials.Marble, 1)}, null, 160, 4);

        GTValues.RA.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_He_1.getWildcard(1), ItemList.Reactor_Coolant_He_1.get(1), 600);
        GTValues.RA.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_He_3.getWildcard(1), ItemList.Reactor_Coolant_He_3.get(1), 1800);
        GTValues.RA.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_He_6.getWildcard(1), ItemList.Reactor_Coolant_He_6.get(1), 3600);
        GTValues.RA.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_NaK_1.getWildcard(1), ItemList.Reactor_Coolant_NaK_1.get(1), 600);
        GTValues.RA.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_NaK_3.getWildcard(1), ItemList.Reactor_Coolant_NaK_3.get(1), 1800);
        GTValues.RA.addVacuumFreezerRecipe(ItemList.Reactor_Coolant_NaK_6.getWildcard(1), ItemList.Reactor_Coolant_NaK_6.get(1), 3600);
        GTValues.RA.addVacuumFreezerRecipe(OreDictUnifier.get(OrePrefix.cell, Materials.Water, 1), OreDictUnifier.get(OrePrefix.cell, Materials.Ice, 1), 50);
        GTValues.RA.addVacuumFreezerRecipe(ItemList.Cell_Air.get(1L), OreDictUnifier.get(OrePrefix.cell, Materials.LiquidAir, 1), 25);

        GTValues.RA.addAlloySmelterRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Lead, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Obsidian, 2L), ItemList.TE_Hardened_Glass.get(2L), 200, 16);
        GTValues.RA.addAlloySmelterRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Lead, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Obsidian, 2L), ItemList.TE_Hardened_Glass.get(2L), 200, 16);
        GTValues.RA.addAlloySmelterRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 3L), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.ingot, Materials.Rubber, 1), 200, 8);

        GTValues.RA.addCutterRecipe(GT_ModHandler.getModItem("BuildCraft|Transport", "item.buildcraftPipe.pipestructurecobblestone", 1, 0), GT_ModHandler.getModItem("BuildCraft|Transport", "pipePlug", 8, 0), GTValues.NI, 32, 16);
        for (int i = 0; i < 16; i++) {
            GTValues.RA.addCutterRecipe(new ItemStack(Blocks.STAINED_GLASS, 3, i), new ItemStack(Blocks.STAINED_GLASS_PANE, 8, i), GTValues.NI, 50, 8);
        }
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.GLASS, 3, 0), new ItemStack(Blocks.GLASS_PANE, 8, 0), GTValues.NI, 50, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.STONE, 1, 0), new ItemStack(Blocks.STONE_SLAB, 2, 0), GTValues.NI, 25, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.SANDSTONE, 1, 0), new ItemStack(Blocks.STONE_SLAB, 2, 1), GTValues.NI, 25, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.COBBLESTONE, 1, 0), new ItemStack(Blocks.STONE_SLAB, 2, 3), GTValues.NI, 25, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.BRICK_BLOCK, 1, 0), new ItemStack(Blocks.STONE_SLAB, 2, 4), GTValues.NI, 25, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0), new ItemStack(Blocks.STONE_SLAB, 2, 5), GTValues.NI, 25, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.NETHER_BRICK, 1, 0), new ItemStack(Blocks.STONE_SLAB, 2, 6), GTValues.NI, 25, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 32767), new ItemStack(Blocks.STONE_SLAB, 2, 7), GTValues.NI, 25, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.GLOWSTONE, 1, 0), OreDictUnifier.get(OrePrefix.plate, Materials.Glowstone, 4L), GTValues.NI, 100, 16);

        GTValues.RA.addCutterRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1), ItemList.IC2_Item_Casing_Iron.get(2L), GTValues.NI, 50, 16);
        GTValues.RA.addCutterRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), ItemList.IC2_Item_Casing_Iron.get(2L), GTValues.NI, 50, 16);
        GTValues.RA.addCutterRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Gold, 1), ItemList.IC2_Item_Casing_Gold.get(2L), GTValues.NI, 50, 16);
        GTValues.RA.addCutterRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Bronze, 1), ItemList.IC2_Item_Casing_Bronze.get(2L), GTValues.NI, 50, 16);
        GTValues.RA.addCutterRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Copper, 1), ItemList.IC2_Item_Casing_Copper.get(2L), GTValues.NI, 50, 16);
        GTValues.RA.addCutterRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.AnnealedCopper, 1), ItemList.IC2_Item_Casing_Copper.get(2L), GTValues.NI, 50, 16);
        GTValues.RA.addCutterRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Tin, 1), ItemList.IC2_Item_Casing_Tin.get(2L), GTValues.NI, 50, 16);
        GTValues.RA.addCutterRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Lead, 1), ItemList.IC2_Item_Casing_Lead.get(2L), GTValues.NI, 50, 16);
        GTValues.RA.addCutterRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Steel, 1), ItemList.IC2_Item_Casing_Steel.get(2L), GTValues.NI, 50, 16);
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            GTValues.RA.addCutterRecipe(new ItemStack(Blocks.WOOL, 1, i), new ItemStack(Blocks.CARPET, 2, i), GTValues.NI, 50, 8);
        }
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.WOODEN_SLAB, 1, 0), ItemList.Plank_Oak.get(2L), GTValues.NI, 50, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.WOODEN_SLAB, 1, 1), ItemList.Plank_Spruce.get(2L), GTValues.NI, 50, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.WOODEN_SLAB, 1, 2), ItemList.Plank_Birch.get(2L), GTValues.NI, 50, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.WOODEN_SLAB, 1, 3), ItemList.Plank_Jungle.get(2L), GTValues.NI, 50, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.WOODEN_SLAB, 1, 4), ItemList.Plank_Acacia.get(2L), GTValues.NI, 50, 8);
        GTValues.RA.addCutterRecipe(new ItemStack(Blocks.WOODEN_SLAB, 1, 5), ItemList.Plank_DarkOak.get(2L), GTValues.NI, 50, 8);

        //TODO FORESTRY PLANK COVERS

//                TODO FIX ITEM HIDING?
//                for(int g=0;g<16;g++){
//                        API.hideItem(new ItemStack(GT_MetaGenerated_Item_03.INSTANCE,1,g));
//                }

        GTValues.RA.addLatheRecipe(new ItemStack(Blocks.WOODEN_SLAB, 1, W), new ItemStack(Items.BOWL,1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 1), 50, 8);
        GTValues.RA.addLatheRecipe(GT_ModHandler.getModItem(aTextForestry, "slabs", 1, W), new ItemStack(Items.BOWL,1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 1), 50, 8);
        GTValues.RA.addLatheRecipe(GT_ModHandler.getModItem(aTextEBXL, "woodslab", 1, W), new ItemStack(Items.BOWL,1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 1), 50, 8);

        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Cupronickel, 1), ItemList.Shape_Mold_Credit.get(0L), ItemList.Credit_Greg_Cupronickel.get(4L), 100, 16);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Brass, 1), ItemList.Shape_Mold_Credit.get(0L), ItemList.Coin_Doge.get(4L), 100, 16);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1), ItemList.Shape_Mold_Credit.get(0L), ItemList.Credit_Iron.get(4L), 100, 16);
        GTValues.RA.addFormingPressRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), ItemList.Shape_Mold_Credit.get(0L), ItemList.Credit_Iron.get(4L), 100, 16);

        if (!GregTechMod.gregtechproxy.mDisableIC2Cables) {
            GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Copper, 1), GT_ModHandler.getIC2Item(ItemName.cable, CableType.copper, 3), 100, 2);
            GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.AnnealedCopper, 1), GT_ModHandler.getIC2Item(ItemName.cable, CableType.copper, 3), 100, 2);
            GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Tin, 1), GT_ModHandler.getIC2Item(ItemName.cable, CableType.tin, 4), 150, 1);
            GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1), GT_ModHandler.getIC2Item(ItemName.cable, CableType.iron, 6), 200, 2);
            GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), GT_ModHandler.getIC2Item(ItemName.cable, CableType.iron, 6), 200, 2);
            GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Gold, 1), GT_ModHandler.getIC2Item(ItemName.cable, CableType.gold, 6), 200, 1);
        }
        GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Graphene, 1), OreDictUnifier.get(OrePrefix.wireGt01, Materials.Graphene, 1), 400, 2);
        if (!GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "torchesFromCoal", false)) {
            GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), new ItemStack(Items.COAL, 1, 32767), new ItemStack(Blocks.TORCH, 4), 400, 1);
        }
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Gold, 2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 1), 200, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1), 200, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 6L), ItemList.Circuit_Integrated.getWithDamage(0L, 6L), new ItemStack(Items.IRON_DOOR, 1), 600, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 7L), ItemList.Circuit_Integrated.getWithDamage(0L, 7L), new ItemStack(Items.CAULDRON, 1), 700, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Iron, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 1), GT_ModHandler.getIC2Item(BlockName.fence, BlockIC2Fence.IC2FenceType.iron, 1), 100, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Iron, 3L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new ItemStack(Blocks.IRON_BARS, 4), 300, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 1), 200, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 6L), ItemList.Circuit_Integrated.getWithDamage(0L, 6L), new ItemStack(Items.IRON_DOOR, 1), 600, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 7L), ItemList.Circuit_Integrated.getWithDamage(0L, 7L), new ItemStack(Items.CAULDRON, 1), 700, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.WroughtIron, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 1), GT_ModHandler.getIC2Item(BlockName.fence, BlockIC2Fence.IC2FenceType.iron, 1), 100, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.WroughtIron, 3L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new ItemStack(Blocks.IRON_BARS, 4), 300, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 3L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L), new ItemStack(Blocks.OAK_FENCE, 1), 300, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), OreDictUnifier.get(OrePrefix.ring, Materials.Iron, 2L), new ItemStack(Blocks.TRIPWIRE_HOOK, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), OreDictUnifier.get(OrePrefix.ring, Materials.WroughtIron, 2L), new ItemStack(Blocks.TRIPWIRE_HOOK, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 3L), new ItemStack(Items.STRING, 3, 32767), new ItemStack(Items.BOW, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 3L), ItemList.Component_Minecart_Wheels_Iron.get(2L), new ItemStack(Items.MINECART, 1), 500, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 3L), ItemList.Component_Minecart_Wheels_Iron.get(2L), new ItemStack(Items.MINECART, 1), 400, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Steel, 3L), ItemList.Component_Minecart_Wheels_Steel.get(2L), new ItemStack(Items.MINECART, 1), 300, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.ring, Materials.Iron, 2L), ItemList.Component_Minecart_Wheels_Iron.get(1), 500, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.WroughtIron, 1), OreDictUnifier.get(OrePrefix.ring, Materials.WroughtIron, 2L), ItemList.Component_Minecart_Wheels_Iron.get(1), 400, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.ring, Materials.Steel, 2L), ItemList.Component_Minecart_Wheels_Steel.get(1), 300, 2);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Items.MINECART, 1), new ItemStack(Blocks.HOPPER, 1, 32767), new ItemStack(Items.HOPPER_MINECART, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Items.MINECART, 1), new ItemStack(Blocks.TNT, 1, 32767), new ItemStack(Items.TNT_MINECART, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Items.MINECART, 1), new ItemStack(Blocks.CHEST, 1, 32767), new ItemStack(Items.CHEST_MINECART, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Items.MINECART, 1), new ItemStack(Blocks.TRAPPED_CHEST, 1, 32767), new ItemStack(Items.CHEST_MINECART, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Items.MINECART, 1), new ItemStack(Blocks.FURNACE, 1, 32767), new ItemStack(Items.FURNACE_MINECART, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.TRIPWIRE_HOOK, 1), new ItemStack(Blocks.CHEST, 1, 32767), new ItemStack(Blocks.TRAPPED_CHEST, 1), 200, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.STONE, 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new ItemStack(Blocks.STONEBRICK, 1, 0), 50, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.SANDSTONE, 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 1), new ItemStack(Blocks.SANDSTONE, 1, 2), 50, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.SANDSTONE, 1, 1), ItemList.Circuit_Integrated.getWithDamage(0L, 1), new ItemStack(Blocks.SANDSTONE, 1, 0), 50, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.SANDSTONE, 1, 2), ItemList.Circuit_Integrated.getWithDamage(0L, 1), new ItemStack(Blocks.SANDSTONE, 1, 0), 50, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1), 25, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_ULV.get(1), 25, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Steel, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_LV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Aluminium, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_MV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_HV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Titanium, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_EV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_IV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Chrome, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_LuV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iridium, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_ZPM.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Osmium, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_UV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Neutronium, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_MAX.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Invar, 6L), OreDictUnifier.get(OrePrefix.frameGt, Materials.Invar, 1), ItemList.Casing_HeatProof.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt02, Materials.Cupronickel, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_Cupronickel.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt02, Materials.Kanthal, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_Kanthal.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt02, Materials.Nichrome, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_Nichrome.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt02, Materials.TungstenSteel, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_TungstenSteel.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt02, Materials.HSSG, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_HSSG.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt02, Materials.Naquadah, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_Naquadah.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt02, Materials.NaquadahAlloy, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_NaquadahAlloy.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt02, Materials.Superconductor, 8L), ItemList.Circuit_Integrated.getWithDamage(0L, 8L), ItemList.Casing_Coil_Superconductor.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Steel, 6L), OreDictUnifier.get(OrePrefix.frameGt, Materials.Steel, 1), ItemList.Casing_SolidSteel.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Aluminium, 6L), OreDictUnifier.get(OrePrefix.frameGt, Materials.Aluminium, 1), ItemList.Casing_FrostProof.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel, 6L), OreDictUnifier.get(OrePrefix.frameGt, Materials.TungstenSteel, 1), ItemList.Casing_RobustTungstenSteel.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel, 6L), OreDictUnifier.get(OrePrefix.frameGt, Materials.StainlessSteel, 1), ItemList.Casing_CleanStainlessSteel.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Titanium, 6L), OreDictUnifier.get(OrePrefix.frameGt, Materials.Titanium, 1), ItemList.Casing_StableTitanium.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel, 6L), ItemList.Casing_LuV.get(1), ItemList.Casing_Fusion.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Magnalium, 6L), OreDictUnifier.get(OrePrefix.frameGt, Materials.BlueSteel, 1), ItemList.Casing_Turbine.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.StainlessSteel, 6L), ItemList.Casing_Turbine.get(1), ItemList.Casing_Turbine1.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Titanium, 6L), ItemList.Casing_Turbine.get(1), ItemList.Casing_Turbine2.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.TungstenSteel, 6L), ItemList.Casing_Turbine.get(1), ItemList.Casing_Turbine3.get(1), 50, 16);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.Lead, 2L), ItemList.Casing_ULV.get(1), Materials.Plastic.getMolten(288), ItemList.Hull_ULV.get(1), 25, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.Tin, 2L), ItemList.Casing_LV.get(1), Materials.Plastic.getMolten(288), ItemList.Hull_LV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.Copper, 2L), ItemList.Casing_MV.get(1), Materials.Plastic.getMolten(288), ItemList.Hull_MV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.AnnealedCopper, 2L), ItemList.Casing_MV.get(1), Materials.Plastic.getMolten(288), ItemList.Hull_MV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.Gold, 2L), ItemList.Casing_HV.get(1), Materials.Plastic.getMolten(288), ItemList.Hull_HV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.Aluminium, 2L), ItemList.Casing_EV.get(1), Materials.Plastic.getMolten(288), ItemList.Hull_EV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.Tungsten, 2L), ItemList.Casing_IV.get(1), Materials.Plastic.getMolten(288), ItemList.Hull_IV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.VanadiumGallium, 2L), ItemList.Casing_LuV.get(1), Materials.Plastic.getMolten(288), ItemList.Hull_LuV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.Naquadah, 2L), ItemList.Casing_ZPM.get(1), Materials.Polytetrafluoroethylene.getMolten(288), ItemList.Hull_ZPM.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt04, Materials.NaquadahAlloy, 2L), ItemList.Casing_UV.get(1), Materials.Polytetrafluoroethylene.getMolten(288), ItemList.Hull_UV.get(1), 50, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 2L), ItemList.Casing_MAX.get(1), Materials.Polytetrafluoroethylene.getMolten(288), ItemList.Hull_MAX.get(1), 50, 16);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.Tin, 1), OreDictUnifier.get(OrePrefix.plate, Materials.BatteryAlloy, 1), Materials.Plastic.getMolten(144), ItemList.Battery_Hull_LV.get(1), 800, 1);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.Copper, 2L), OreDictUnifier.get(OrePrefix.plate, Materials.BatteryAlloy, 3L), Materials.Plastic.getMolten(432), ItemList.Battery_Hull_MV.get(1), 1600, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.AnnealedCopper, 2L), OreDictUnifier.get(OrePrefix.plate, Materials.BatteryAlloy, 3L), Materials.Plastic.getMolten(432), ItemList.Battery_Hull_MV.get(1), 1600, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.cableGt01, Materials.Gold, 4L), OreDictUnifier.get(OrePrefix.plate, Materials.BatteryAlloy, 9L), Materials.Plastic.getMolten(1296), ItemList.Battery_Hull_HV.get(1), 3200, 4);

        GTValues.RA.addAssemblerRecipe(new ItemStack(Items.STRING, 4, 32767), new ItemStack(Items.SLIME_BALL, 1, 32767), new ItemStack(Items.LEAD, 2), 200, 2);
        GTValues.RA.addAssemblerRecipe(ItemList.IC2_Compressed_Coal_Ball.get(8L), new ItemStack(Blocks.BRICK_BLOCK, 1), ItemList.IC2_Compressed_Coal_Chunk.get(1), 400, 4);

        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getIC2TEItem(TeBlock.water_generator, 2), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), GT_ModHandler.getIC2TEItem(TeBlock.generator, 1), 6400, 8);
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item(ItemName.batpack, 1, 32767), ItemList.Circuit_Integrated.getWithDamage(0L, 1), ItemList.IC2_ReBattery.get(6L), 800, 4);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.STONE_SLAB, 3, 0), ItemList.RC_Rebar.get(1), ItemList.RC_Tie_Stone.get(1), 128, 8);
        GTValues.RA.addAssemblerRecipe(new ItemStack(Blocks.STONE_SLAB, 3, 7), ItemList.RC_Rebar.get(1), ItemList.RC_Tie_Stone.get(1), 128, 8);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.Copper, 9L), OreDictUnifier.get(OrePrefix.plate, Materials.Lead, 2L), GTValues.NF, ItemList.RC_ShuntingWire.get(4L), 1600, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.AnnealedCopper, 9L), OreDictUnifier.get(OrePrefix.plate, Materials.Lead, 2L), GTValues.NF, ItemList.RC_ShuntingWire.get(4L), 1600, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel, 3L), OreDictUnifier.get(OrePrefix.plate, Materials.Gold, 3L), Materials.Blaze.getMolten(432L), ItemList.RC_Rail_HS.get(8L), 400, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.RC_Rail_Standard.get(3L), OreDictUnifier.get(OrePrefix.plate, Materials.Gold, 3L), Materials.Redstone.getMolten(432L), ItemList.RC_Rail_Adv.get(8L), 400, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.RC_Rail_Standard.get(1), OreDictUnifier.get(OrePrefix.wireGt01, Materials.Copper, 1), ItemList.RC_Rail_Electric.get(1), 50, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.RC_Rail_Standard.get(1), OreDictUnifier.get(OrePrefix.wireGt01, Materials.AnnealedCopper, 1), ItemList.RC_Rail_Electric.get(1), 50, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.RC_Tie_Wood.get(6L), OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1), ItemList.RC_Rail_Wooden.get(6L), 400, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.RC_Tie_Wood.get(6L), OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), ItemList.RC_Rail_Wooden.get(6L), 400, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.RC_Tie_Wood.get(4L), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), ItemList.RC_Bed_Wood.get(1), 200, 4);
        GTValues.RA.addAssemblerRecipe(ItemList.RC_Tie_Stone.get(4L), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), ItemList.RC_Bed_Stone.get(1), 200, 4);
        for (ItemStack tRail : new ItemStack[]{ItemList.RC_Rail_Standard.get(6L), ItemList.RC_Rail_Adv.get(6L), ItemList.RC_Rail_Reinforced.get(6L), ItemList.RC_Rail_Electric.get(6L), ItemList.RC_Rail_HS.get(6L), ItemList.RC_Rail_Wooden.get(6L)}) {
            for (ItemStack tBed : new ItemStack[]{ItemList.RC_Bed_Wood.get(1), ItemList.RC_Bed_Stone.get(1)}) {
                GTValues.RA.addAssemblerRecipe(tBed, tRail, GT_ModHandler.getRecipeOutput(tRail, GTValues.NI, tRail, tRail, tBed, tRail, tRail, GTValues.NI, tRail), 400, 4);
                GTValues.RA.addAssemblerRecipe(tBed, tRail, Materials.Redstone.getMolten(144L), GT_ModHandler.getRecipeOutput(tRail, GTValues.NI, tRail, tRail, tBed, tRail, tRail, OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), tRail), 400, 4);
                GTValues.RA.addAssemblerRecipe(tBed, tRail, Materials.Redstone.getMolten(288L), GT_ModHandler.getRecipeOutput(tRail, OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), tRail, tRail, tBed, tRail, tRail, OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), tRail), 400, 4);
            }
        }
        GTValues.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_fibre, 2), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_mesh, 1), 800, 2);

        GTValues.RA.addAssemblerRecipe(ItemList.NC_SensorCard.getWildcard(1), ItemList.Circuit_Integrated.getWithDamage(0L, 1), ItemList.Circuit_Basic.get(3L), 1600, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Aluminium, 4L), GT_ModHandler.getIC2TEItem(TeBlock.generator, 1), GT_ModHandler.getIC2TEItem(TeBlock.water_generator, 2), 6400, 8);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 5L), new ItemStack(Blocks.CHEST, 1, 32767), new ItemStack(Blocks.HOPPER), 800, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 5L), new ItemStack(Blocks.TRAPPED_CHEST, 1, 32767), new ItemStack(Blocks.HOPPER), 800, 2);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 5L), new ItemStack(Blocks.CHEST, 1, 32767), new ItemStack(Blocks.HOPPER), 800, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 5L), new ItemStack(Blocks.TRAPPED_CHEST, 1, 32767), new ItemStack(Blocks.HOPPER), 800, 2);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Magnalium, 2L), GT_ModHandler.getIC2TEItem(TeBlock.generator, 1), GT_ModHandler.getIC2TEItem(TeBlock.water_generator, 1), 6400, 8);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl, 1), new ItemStack(Items.BLAZE_POWDER, 1, 0), new ItemStack(Items.ENDER_EYE, 1, 0), 400, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.EnderPearl, 6L), new ItemStack(Items.BLAZE_ROD, 1, 0), new ItemStack(Items.ENDER_EYE, 6, 0), 2500, 2);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.gear, Materials.CobaltBrass, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Diamond, 1), ItemList.Component_Sawblade_Diamond.get(1), 1600, 2);
//        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Flint, 5L), new ItemStack(Blocks.tnt, 3, 32767), GT_ModHandler.getIC2Item("industrialTnt", 5L), 800, 2);
//        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Gunpowder, 4L), new ItemStack(Blocks.SAND, 4, 32767), new ItemStack(Blocks.tnt, 1), 400, 1);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 4L), OreDictUnifier.get(OrePrefix.dust, Materials.Glowstone, 4L), new ItemStack(Blocks.REDSTONE_LAMP, 1), 400, 1);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), new ItemStack(Blocks.REDSTONE_TORCH, 1), 400, 1);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 4L), new ItemStack(Items.COMPASS, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), OreDictUnifier.get(OrePrefix.plate, Materials.WroughtIron, 4L), new ItemStack(Items.COMPASS, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), OreDictUnifier.get(OrePrefix.plate, Materials.Gold, 4L), new ItemStack(Items.CLOCK, 1), 400, 4);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), new ItemStack(Blocks.TORCH, 2), 400, 1);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Phosphor, 1), new ItemStack(Blocks.TORCH, 6), 400, 1);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), ItemList.IC2_Resin.get(1), new ItemStack(Blocks.TORCH, 6), 400, 1);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Coal, 8L), new ItemStack(Items.FLINT, 1), ItemList.IC2_Compressed_Coal_Ball.get(1), 400, 4);
        if (!GregTechMod.gregtechproxy.mDisableIC2Cables) {
            GTValues.RA.addAssemblerRecipe(ItemCable.getCable(CableType.tin, 0), OreDictUnifier.get(OrePrefix.ingot, Materials.Rubber, 1), ItemCable.getCable(CableType.tin, 1), 100, 2);
            GTValues.RA.addAssemblerRecipe(ItemCable.getCable(CableType.copper, 0), OreDictUnifier.get(OrePrefix.ingot, Materials.Rubber, 1), ItemCable.getCable(CableType.copper, 1), 100, 2);
            GTValues.RA.addAssemblerRecipe(ItemCable.getCable(CableType.gold, 0), OreDictUnifier.get(OrePrefix.ingot, Materials.Rubber, 2L), ItemCable.getCable(CableType.gold, 1), 200, 2);
            GTValues.RA.addAssemblerRecipe(ItemCable.getCable(CableType.iron, 0), OreDictUnifier.get(OrePrefix.ingot, Materials.Rubber, 3L), ItemCable.getCable(CableType.iron, 1), 300, 2);
        }
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, Materials.Wood, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), new ItemStack(Items.WOODEN_SWORD, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, Materials.Stone, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), new ItemStack(Items.STONE_SWORD, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), new ItemStack(Items.IRON_SWORD, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), new ItemStack(Items.GOLDEN_SWORD, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, Materials.Diamond, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), new ItemStack(Items.DIAMOND_SWORD, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, Materials.Bronze, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), ItemList.Tool_Sword_Bronze.getUndamaged(1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1), ItemList.Tool_Sword_Steel.getUndamaged(1), 100, 16);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, Materials.Wood, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.WOODEN_PICKAXE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, Materials.Stone, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.STONE_PICKAXE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.IRON_PICKAXE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.GOLDEN_PICKAXE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, Materials.Diamond, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.DIAMOND_PICKAXE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, Materials.Bronze, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), ItemList.Tool_Pickaxe_Bronze.getUndamaged(1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), ItemList.Tool_Pickaxe_Steel.getUndamaged(1), 100, 16);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, Materials.Wood, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.WOODEN_SHOVEL, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, Materials.Stone, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.STONE_SHOVEL, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.IRON_SHOVEL, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.GOLDEN_SHOVEL, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, Materials.Diamond, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.DIAMOND_SHOVEL, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, Materials.Bronze, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), ItemList.Tool_Shovel_Bronze.getUndamaged(1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), ItemList.Tool_Shovel_Steel.getUndamaged(1), 100, 16);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, Materials.Wood, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.WOODEN_AXE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, Materials.Stone, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.STONE_AXE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.IRON_AXE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.GOLDEN_AXE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, Materials.Diamond, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.DIAMOND_AXE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, Materials.Bronze, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), ItemList.Tool_Axe_Bronze.getUndamaged(1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), ItemList.Tool_Axe_Steel.getUndamaged(1), 100, 16);

        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, Materials.Wood, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.WOODEN_HOE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, Materials.Stone, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.STONE_HOE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.IRON_HOE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.GOLDEN_HOE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, Materials.Diamond, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), new ItemStack(Items.DIAMOND_HOE, 1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, Materials.Bronze, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), ItemList.Tool_Hoe_Bronze.getUndamaged(1), 100, 16);
        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, Materials.Steel, 1), OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 2L), ItemList.Tool_Hoe_Steel.getUndamaged(1), 100, 16);

        GT_ModHandler.removeRecipe(new ItemStack(Items.LAVA_BUCKET), ItemList.Cell_Empty.get(1));
        GT_ModHandler.removeRecipe(new ItemStack(Items.WATER_BUCKET), ItemList.Cell_Empty.get(1));

        GT_ModHandler.removeFurnaceSmelting(ItemList.IC2_Resin.get(1));

        if (!GregTechAPI.mIC2Classic)
            GTValues.RA.addPyrolyseRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.bio_chaff, 4), Materials.Water.getFluid(4000), 1, null, new FluidStack(FluidRegistry.getFluid("ic2biomass"), 5000), 900, 10);
//        if (Loader.isModLoaded("Railcraft")) { TODO
//            GTValues.RA.addPyrolyseRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.Coal, 16), null, 1, RailcraftToolItems.getCoalCoke(16), Materials.Creosote.getFluid(8000), 640, 64);
//            GTValues.RA.addPyrolyseRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.Coal, 16), Materials.Nitrogen.getGas(1000), 2, RailcraftToolItems.getCoalCoke(16), Materials.Creosote.getFluid(8000), 320, 96);
//            GTValues.RA.addPyrolyseRecipe(OreDictUnifier.get(OrePrefix.block, Materials.Coal, 8), null, 1, EnumCube.COKE_BLOCK.getItem(8), Materials.Creosote.getFluid(32000), 2560, 64);
//            GTValues.RA.addPyrolyseRecipe(OreDictUnifier.get(OrePrefix.block, Materials.Coal, 8), Materials.Nitrogen.getGas(1000), 2, EnumCube.COKE_BLOCK.getItem(8), Materials.Creosote.getFluid(32000), 1280, 96);
//        }

        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 1), GTValues.NI, GTValues.NF, Materials.Methane.getGas(9216L), new ItemStack(Items.GOLD_INGOT, 64), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 9216, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), new ItemStack(Items.GOLD_INGOT, 7), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 9216, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.GOLDEN_CARROT, 1, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), new ItemStack(Items.GOLD_NUGGET, 6), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 9216, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.SPECKLED_MELON, 1, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), new ItemStack(Items.GOLD_NUGGET, 6), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 9216, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.MUSHROOM_STEW, 16, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), new ItemStack(Items.BOWL, 16, 0), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.APPLE, 32, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.BREAD, 64, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.PORKCHOP, 12, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.COOKED_PORKCHOP, 16, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.BEEF, 12, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.COOKED_BEEF, 16, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.FISH, 12, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.COOKED_FISH, 16, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.CHICKEN, 12, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.COOKED_CHICKEN, 16, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.MELON, 64, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Blocks.PUMPKIN, 16, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.ROTTEN_FLESH, 16, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.SPIDER_EYE, 32, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.CARROT, 16, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(ItemList.Food_Raw_Potato.get(16L), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(ItemList.Food_Poisonous_Potato.get(12L), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(ItemList.Food_Baked_Potato.get(24L), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.COOKIE, 64, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.CAKE, 8, 0), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Blocks.BROWN_MUSHROOM, 12, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Blocks.RED_MUSHROOM_BLOCK, 12, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Blocks.BROWN_MUSHROOM, 32, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Blocks.RED_MUSHROOM, 32, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.NETHER_WART, 32, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(GT_ModHandler.getIC2Item(ItemName.crop_res, CropResItemType.weed, 16), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(GT_ModHandler.getModItem("TwilightForest", "item.meefRaw", 12, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(GT_ModHandler.getModItem("TwilightForest", "item.meefSteak", 16, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(GT_ModHandler.getModItem("TwilightForest", "item.venisonRaw", 12, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);
        GTValues.RA.addCentrifugeRecipe(GT_ModHandler.getModItem("TwilightForest", "item.venisonCooked", 16, 32767), GTValues.NI, GTValues.NF, Materials.Methane.getGas(1152L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 4608, 5);

        GTValues.RA.addCentrifugeRecipe(new ItemStack(Blocks.SAND, 1, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Diamond, 1), new ItemStack(Blocks.SAND, 1), GTValues.NI, GTValues.NI, GTValues.NI, new int[]{5000, 100, 5000}, 50, 30);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Blocks.DIRT, 1, 32767), GTValues.NI, GTValues.NF, GTValues.NF, ItemList.IC2_Plantball.get(1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Clay, 1), new ItemStack(Blocks.SAND, 1), GTValues.NI, GTValues.NI, GTValues.NI, new int[]{1250, 5000, 5000}, 250, 30);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Blocks.GRASS, 1, 32767), GTValues.NI, GTValues.NF, GTValues.NF, ItemList.IC2_Plantball.get(1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Clay, 1), new ItemStack(Blocks.SAND, 1), GTValues.NI, GTValues.NI, GTValues.NI, new int[]{2500, 5000, 5000}, 250, 30);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Blocks.MYCELIUM, 1, 32767), GTValues.NI, GTValues.NF, GTValues.NF, new ItemStack(Blocks.BROWN_MUSHROOM, 1), new ItemStack(Blocks.RED_MUSHROOM, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Clay, 1), new ItemStack(Blocks.SAND, 1), GTValues.NI, GTValues.NI, new int[]{2500, 2500, 5000, 5000}, 650, 30);
        GTValues.RA.addCentrifugeRecipe(ItemList.IC2_Resin.get(1), GTValues.NI, GTValues.NF, Materials.Glue.getFluid(100L), OreDictUnifier.get(OrePrefix.dust, Materials.RawRubber, 3L), ItemList.IC2_Plantball.get(1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, new int[]{10000, 5000}, 300, 5);
        GTValues.RA.addCentrifugeRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.DarkAsh, 2L), 0, OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 1), ItemList.TE_Slag.get(1, OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 1)), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, 250);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Items.MAGMA_CREAM, 1), 0, new ItemStack(Items.BLAZE_POWDER, 1), new ItemStack(Items.SLIME_BALL, 1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, 500);
        GTValues.RA.addCentrifugeRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Uranium, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dustTiny, Materials.Uranium235, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Plutonium, 1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, new int[]{2000, 200}, 800, 320);
        GTValues.RA.addCentrifugeRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Plutonium, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dustTiny, Materials.Plutonium241, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Uranium, 1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, new int[]{2000, 3000}, 1600, 320);
        GTValues.RA.addCentrifugeRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Naquadah, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dustTiny, Materials.NaquadahEnriched, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Naquadria, 1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, new int[]{5000, 1000}, 3200, 320);
        GTValues.RA.addCentrifugeRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.NaquadahEnriched, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dustSmall, Materials.Naquadria, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Naquadah, 1), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, new int[]{2000, 3000}, 6400, 640);
        GTValues.RA.addCentrifugeRecipe(GTValues.NI, GTValues.NI, Materials.Hydrogen.getGas(160L), Materials.Deuterium.getGas(40L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 160, 20);
        GTValues.RA.addCentrifugeRecipe(GTValues.NI, GTValues.NI, Materials.Deuterium.getGas(160L), Materials.Tritium.getGas(40L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 160, 80);
        GTValues.RA.addCentrifugeRecipe(GTValues.NI, GTValues.NI, Materials.Helium.getGas(80L), Materials.Helium_3.getGas(5L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 160, 80);
        GTValues.RA.addCentrifugeRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Glowstone, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dustSmall, Materials.Redstone, 2L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Gold, 2L), GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, null, 488, 80);
        GTValues.RA.addCentrifugeRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Endstone, 1), GTValues.NI, GTValues.NF, Materials.Helium.getGas(120L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Tungstate, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Platinum, 1), new ItemStack(Blocks.SAND, 1), GTValues.NI, GTValues.NI, GTValues.NI, new int[]{1250, 625, 9000, 0, 0, 0}, 320, 20);
        GTValues.RA.addCentrifugeRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Netherrack, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dustTiny, Materials.Redstone, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Sulfur, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Coal, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Gold, 1), GTValues.NI, GTValues.NI, new int[]{5625, 9900, 5625, 625, 0, 0}, 160, 20);
        GTValues.RA.addCentrifugeRecipe(new ItemStack(Blocks.SOUL_SAND, 1), GTValues.NI, GTValues.NF, Materials.Oil.getFluid(80L), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Saltpeter, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.Coal, 1), new ItemStack(Blocks.SAND, 1), GTValues.NI, GTValues.NI, GTValues.NI, new int[]{8000, 2000, 9000, 0, 0, 0}, 200, 80);
        GTValues.RA.addCentrifugeRecipe(GTValues.NI, GTValues.NI, Materials.Lava.getFluid(100L), GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.Copper, 1), OreDictUnifier.get(OrePrefix.nugget, Materials.Tin, 1), OreDictUnifier.get(OrePrefix.nugget, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.nugget, Materials.Silver, 1), OreDictUnifier.get(OrePrefix.nugget, Materials.Tantalum, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Tungstate, 1), new int[]{2000, 1000, 250, 250, 250, 250}, 80, 80);
        GTValues.RA.addCentrifugeRecipe(GTValues.NI, GTValues.NI, FluidRegistry.getFluidStack("ic2pahoehoelava", 100), GTValues.NF, OreDictUnifier.get(OrePrefix.nugget, Materials.Copper, 1), OreDictUnifier.get(OrePrefix.nugget, Materials.Tin, 1), OreDictUnifier.get(OrePrefix.nugget, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.nugget, Materials.Silver, 1), OreDictUnifier.get(OrePrefix.nugget, Materials.Tantalum, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Tungstate, 1), new int[]{2000, 1000, 250, 250, 250, 250}, 40, 80);

        GTValues.RA.addCentrifugeRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.RareEarth, 1), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dustSmall, Materials.Neodymium, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Yttrium, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Lanthanum, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Cerium, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Cadmium, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Caesium, 1), new int[]{2500, 2500, 2500, 2500, 2500, 2500}, 64, 20);
        GTValues.RA.addCentrifugeRecipe(GT_ModHandler.getModItem(aTextAE, aTextAEMM, 1, 45), GTValues.NI, GTValues.NF, GTValues.NF, OreDictUnifier.get(OrePrefix.dustSmall, Materials.BasalticMineralSand, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Olivine, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Obsidian, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Basalt, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.Flint, 1), OreDictUnifier.get(OrePrefix.dustSmall, Materials.RareEarth, 1), new int[]{2000, 2000, 2000, 2000, 2000, 2000}, 64, 20);

        run2();

        GTUtility.removeSimpleIC2MachineRecipe(new ItemStack(Blocks.COBBLESTONE), Recipes.macerator.getRecipes(), OreDictUnifier.get(OrePrefix.dust, Materials.Stone, 1));
        GTUtility.removeSimpleIC2MachineRecipe(OreDictUnifier.get(OrePrefix.gem, Materials.Lapis, 1), Recipes.macerator.getRecipes(), ItemList.IC2_Plantball.get(1));
        GTUtility.removeSimpleIC2MachineRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1), Recipes.macerator.getRecipes(), ItemList.IC2_Plantball.get(1));
        GTUtility.removeSimpleIC2MachineRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Glowstone, 1), Recipes.macerator.getRecipes(), ItemList.IC2_Plantball.get(1));

        if(GregTechAPI.mMagneticraft && GregTechMod.gregtechproxy.mMagneticraftRecipes){
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("Magneticraft", "item.ingotCarbide", 8));
            GTValues.RA.addAlloySmelterRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Coal, 8), OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron, 1), GT_ModHandler.getModItem("Magneticraft", "item.ingotCarbide", 1), 600, 24);
            GTValues.RA.addBlastRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Coal, 8), OreDictUnifier.get(OrePrefix.ingot, Materials.TungstenCarbide, 1), null, null, GT_ModHandler.getModItem("Magneticraft", "item.ingotCarbide", 8), null, 100, 120, 2600);
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.chunks", 1, 4));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.pebbles", 1, 4));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.rubble", 1, 4));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.chunks", 1, 13));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.pebbles", 1, 13));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.rubble", 1, 13));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.chunks", 1, 15));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.pebbles", 1, 15));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.rubble", 1, 15));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.chunks", 1, 16));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.pebbles", 1, 16));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.rubble", 1, 16));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.chunks", 1, 21));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.pebbles", 1, 21));
            GT_ModHandler.removeFurnaceSmelting(GT_ModHandler.getModItem("Magneticraft", "item.rubble", 1, 21));
        }

        for (MaterialStack[] tMats : this.mAlloySmelterList) {
            ItemStack tDust1 = OreDictUnifier.get(OrePrefix.dust, tMats[0].mMaterial, tMats[0].mAmount);
            ItemStack tDust2 = OreDictUnifier.get(OrePrefix.dust, tMats[1].mMaterial, tMats[1].mAmount);
            ItemStack tIngot1 = OreDictUnifier.get(OrePrefix.ingot, tMats[0].mMaterial, tMats[0].mAmount);
            ItemStack tIngot2 = OreDictUnifier.get(OrePrefix.ingot, tMats[1].mMaterial, tMats[1].mAmount);
            ItemStack tOutputIngot = OreDictUnifier.get(OrePrefix.ingot, tMats[2].mMaterial, tMats[2].mAmount);
            if (tOutputIngot != GTValues.NI) {
                GT_ModHandler.addAlloySmelterRecipe(tIngot1, tDust2, tOutputIngot, (int) tMats[2].mAmount * 50, 16, false);
                GT_ModHandler.addAlloySmelterRecipe(tIngot1, tIngot2, tOutputIngot, (int) tMats[2].mAmount * 50, 16, false);
                GT_ModHandler.addAlloySmelterRecipe(tDust1, tIngot2, tOutputIngot, (int) tMats[2].mAmount * 50, 16, false);
                GT_ModHandler.addAlloySmelterRecipe(tDust1, tDust2, tOutputIngot, (int) tMats[2].mAmount * 50, 16, false);
            }
        }

        if (!GregTechAPI.mIC2Classic) {
            try {
                Map<String, HeatExchangeProperty> tLiqExchange = ic2.api.recipe.Recipes.liquidCooldownManager.getHeatExchangeProperties();
                Iterator<Map.Entry<String, HeatExchangeProperty>> tIterator = tLiqExchange.entrySet().iterator();
                while (tIterator.hasNext()) {
                    Map.Entry<String, HeatExchangeProperty> tEntry = tIterator.next();
                    if (tEntry.getKey().equals("ic2hotcoolant")) {
                        tIterator.remove();
                        Recipes.liquidCooldownManager.addFluid("ic2hotcoolant", "ic2coolant", 100);
                    }
                }
            } catch (Throwable e) {}

            try {
                Map<String, HeatExchangeProperty> tLiqExchange = ic2.api.recipe.Recipes.liquidHeatupManager.getHeatExchangeProperties();
                Iterator<Map.Entry<String, HeatExchangeProperty>> tIterator = tLiqExchange.entrySet().iterator();
                while (tIterator.hasNext()) {
                    Map.Entry<String, HeatExchangeProperty> tEntry = tIterator.next();
                    if (tEntry.getKey().equals("ic2coolant")) {
                        tIterator.remove();
                        Recipes.liquidHeatupManager.addFluid("ic2coolant", "ic2hotcoolant", 100);
                    }
                }
            } catch (Throwable e) {}
        }

        GTUtility.removeSimpleIC2MachineRecipe(ItemList.Crop_Drop_BobsYerUncleRanks.get(1), Recipes.extractor.getRecipes(), null);
        GTUtility.removeSimpleIC2MachineRecipe(ItemList.Crop_Drop_Ferru.get(1), Recipes.extractor.getRecipes(), null);
        GTUtility.removeSimpleIC2MachineRecipe(ItemList.Crop_Drop_Aurelia.get(1), Recipes.extractor.getRecipes(), null);

        ItemStack tCrop;
        // Metals Line
        tCrop = ItemList.Crop_Drop_Coppon.get(1, new Object[0]);
        addProcess(tCrop, Materials.Copper, 100, true);
        addProcess(tCrop, Materials.Tetrahedrite, 100, false);
        addProcess(tCrop, Materials.Chalcopyrite, 100, false);
        addProcess(tCrop, Materials.Malachite, 100, false);
        addProcess(tCrop, Materials.Pyrite, 100, false);
        addProcess(tCrop, Materials.Stibnite, 100, false);
        tCrop = ItemList.Crop_Drop_Tine.get(1, new Object[0]);
        addProcess(tCrop, Materials.Tin, 100, true);
        addProcess(tCrop, Materials.Cassiterite, 100, false);
        tCrop = ItemList.Crop_Drop_Plumbilia.get(1, new Object[0]);
        addProcess(tCrop, Materials.Lead, 100, true);
        addProcess(tCrop, Materials.Galena, 100, false);
        tCrop = ItemList.Crop_Drop_Ferru.get(1, new Object[0]);
        addProcess(tCrop, Materials.Iron, 100, true);
        addProcess(tCrop, Materials.Magnetite, 100, false);
        addProcess(tCrop, Materials.BrownLimonite, 100, false);
        addProcess(tCrop, Materials.YellowLimonite, 100, false);
        addProcess(tCrop, Materials.VanadiumMagnetite, 100, false);
        addProcess(tCrop, Materials.BandedIron, 100, false);
        addProcess(tCrop, Materials.Pyrite, 100, false);
        addProcess(tCrop, Materials.MeteoricIron, 100, false);
        tCrop = ItemList.Crop_Drop_Nickel.get(1, new Object[0]);
        addProcess(tCrop, Materials.Nickel, 100, true);
        addProcess(tCrop, Materials.Garnierite, 100, false);
        addProcess(tCrop, Materials.Pentlandite, 100, false);
        addProcess(tCrop, Materials.Cobaltite, 100, false);
        addProcess(tCrop, Materials.Wulfenite, 100, false);
        addProcess(tCrop, Materials.Powellite, 100, false);
        tCrop = ItemList.Crop_Drop_Zinc.get(1, new Object[0]);
        addProcess(tCrop, Materials.Zinc, 100, true);
        addProcess(tCrop, Materials.Sphalerite, 100, false);
        addProcess(tCrop, Materials.Sulfur, 100, false);
        tCrop = ItemList.Crop_Drop_Argentia.get(1, new Object[0]);
        addProcess(tCrop, Materials.Silver, 100, true);
        addProcess(tCrop, Materials.Galena, 100, false);
        tCrop = ItemList.Crop_Drop_Aurelia.get(1, new Object[0]);
        addProcess(tCrop, Materials.Gold, 100, true);
        addProcess(tCrop, Materials.Magnetite, Materials.Gold, 100, false);

        // Rare Metals Line
        tCrop = ItemList.Crop_Drop_Bauxite.get(1, new Object[0]);
        addProcess(tCrop,Materials.Aluminium,60, true);
        addProcess(tCrop,Materials.Bauxite,100, false);
        tCrop = ItemList.Crop_Drop_Manganese.get(1, new Object[0]);
        addProcess(tCrop,Materials.Manganese,30, true);
        addProcess(tCrop,Materials.Grossular,100, false);
        addProcess(tCrop,Materials.Spessartine,100, false);
        addProcess(tCrop,Materials.Pyrolusite,100, false);
        addProcess(tCrop,Materials.Tantalite,100, false);
        tCrop = ItemList.Crop_Drop_Ilmenite.get(1, new Object[0]);
        addProcess(tCrop,Materials.Titanium,100, true);
        addProcess(tCrop,Materials.Ilmenite,100, false);
        addProcess(tCrop,Materials.Bauxite,100, false);
        tCrop = ItemList.Crop_Drop_Scheelite.get(1, new Object[0]);
        addProcess(tCrop,Materials.Scheelite,100, true);
        addProcess(tCrop,Materials.Tungstate,100, false);
        addProcess(tCrop,Materials.Lithium,100, false);
        tCrop = ItemList.Crop_Drop_Platinum.get(1, new Object[0]);
        addProcess(tCrop,Materials.Platinum,40, true);
        addProcess(tCrop,Materials.Cooperite,40, false);
        addProcess(tCrop,Materials.Palladium,40, false);
        addProcess(tCrop, Materials.Neodymium, 100, false);
        addProcess(tCrop, Materials.Bastnasite, 100, false);
        tCrop = ItemList.Crop_Drop_Iridium.get(1, new Object[0]);
        addProcess(tCrop,Materials.Iridium,20, true);
        tCrop = ItemList.Crop_Drop_Osmium.get(1, new Object[0]);
        addProcess(tCrop,Materials.Osmium,20, true);

        // Radioactive Line
        tCrop = ItemList.Crop_Drop_Pitchblende.get(1, new Object[0]);
        addProcess(tCrop,Materials.Pitchblende,50, true);
        tCrop = ItemList.Crop_Drop_Uraninite.get(1, new Object[0]);
        addProcess(tCrop,Materials.Uraninite,50, false);
        addProcess(tCrop,Materials.Uranium,50, true);
        addProcess(tCrop,Materials.Pitchblende,50, false);
        addProcess(tCrop,Materials.Uranium235,50, false);
        tCrop = ItemList.Crop_Drop_Thorium.get(1, new Object[0]);
        addProcess(tCrop,Materials.Thorium,50, true);
        tCrop = ItemList.Crop_Drop_Naquadah.get(1, new Object[0]);
        addProcess(tCrop,Materials.Naquadah,10, true);
        addProcess(tCrop,Materials.NaquadahEnriched,10, false);
        addProcess(tCrop,Materials.Naquadria,10, false);

        //Gem Line
        tCrop = ItemList.Crop_Drop_BobsYerUncleRanks.get(1, new Object[0]);
        addProcess(tCrop, Materials.Emerald, 100, true);
        addProcess(tCrop, Materials.Beryllium, 100, false);

    }

    public void addProcess(ItemStack tCrop, Materials aMaterial, int chance, boolean aMainOutput) {
        if(tCrop==null||aMaterial==null|| OreDictUnifier.get(OrePrefix.crushed, aMaterial,1)==null)return;
        if (GregTechMod.gregtechproxy.mNerfedCrops) {
            GTValues.RA.addChemicalRecipe(GTUtility.copyAmount(9, tCrop), OreDictUnifier.get(OrePrefix.crushed, aMaterial, 1), Materials.Water.getFluid(1000), aMaterial.mOreByProducts.isEmpty() ? null : aMaterial.mOreByProducts.get(0).getMolten(144), OreDictUnifier.get(OrePrefix.crushedPurified, aMaterial, 4), 96, 24);
            GTValues.RA.addAutoclaveRecipe(GTUtility.copyAmount(16, tCrop), Materials.UUMatter.getFluid(Math.max(1, ((aMaterial.getMass()+9)/10))), OreDictUnifier.get(OrePrefix.crushedPurified, aMaterial, 1), 10000, (int) (aMaterial.getMass() * 128), 384);
        } else {
            if (aMainOutput) GT_ModHandler.addExtractionRecipe(tCrop, OreDictUnifier.get(OrePrefix.dustTiny, aMaterial, 1));
        }
    }

    public void addProcess(ItemStack tCrop, Materials aMaterial, int chance){
        if(tCrop==null||aMaterial==null|| OreDictUnifier.get(OrePrefix.crushed, aMaterial,1)==null)return;
        if (GregTechMod.gregtechproxy.mNerfedCrops) {
            GTValues.RA.addChemicalRecipe(GTUtility.copyAmount(9, tCrop), OreDictUnifier.get(OrePrefix.crushed, aMaterial, 1), Materials.Water.getFluid(1000), aMaterial.mOreByProducts.isEmpty() ? null : aMaterial.mOreByProducts.get(0).getMolten(144), OreDictUnifier.get(OrePrefix.crushedPurified, aMaterial, 4), 96, 24);
            GTValues.RA.addAutoclaveRecipe(GTUtility.copyAmount(16, tCrop), Materials.UUMatter.getFluid(Math.max(1, ((aMaterial.getMass()+9)/10))), OreDictUnifier.get(OrePrefix.crushedPurified, aMaterial, 1), 10000, (int) (aMaterial.getMass() * 128), 384);
        } else {
            GT_ModHandler.addExtractionRecipe(tCrop, OreDictUnifier.get(OrePrefix.dustTiny, aMaterial, 1));
        }
    }

    public void addProcess(ItemStack tCrop, Materials aMaterial, Materials aMaterialOut, int chance, boolean aMainOutput){
        if(tCrop==null||aMaterial==null|| OreDictUnifier.get(OrePrefix.crushed, aMaterial,1)==null)return;
        if (GregTechMod.gregtechproxy.mNerfedCrops) {
            GTValues.RA.addChemicalRecipe(GTUtility.copyAmount(9, tCrop), OreDictUnifier.get(OrePrefix.crushed, aMaterial, 1), Materials.Water.getFluid(1000), aMaterialOut.mOreByProducts.isEmpty() ? null : aMaterialOut.mOreByProducts.get(0).getMolten(144), OreDictUnifier.get(OrePrefix.crushedPurified, aMaterialOut, 4), 96, 24);
            GTValues.RA.addAutoclaveRecipe(GTUtility.copyAmount(16, tCrop), Materials.UUMatter.getFluid(Math.max(1, ((aMaterial.getMass()+9)/10))), OreDictUnifier.get(OrePrefix.crushedPurified, aMaterial, 1), 10000, (int) (aMaterial.getMass() * 128), 384);
        } else {
            if (aMainOutput) GT_ModHandler.addExtractionRecipe(tCrop, OreDictUnifier.get(OrePrefix.dustTiny, aMaterial, 1));
        }
    }

    public void addProcess(ItemStack tCrop, Materials aMaterial, Materials aMaterialOut, int chance){
        if(tCrop==null||aMaterial==null|| OreDictUnifier.get(OrePrefix.crushed, aMaterial,1)==null)return;
        if (GregTechMod.gregtechproxy.mNerfedCrops) {
            GTValues.RA.addChemicalRecipe(GTUtility.copyAmount(9, tCrop), OreDictUnifier.get(OrePrefix.crushed, aMaterial, 1), Materials.Water.getFluid(1000), aMaterialOut.mOreByProducts.isEmpty() ? null : aMaterialOut.mOreByProducts.get(0).getMolten(144), OreDictUnifier.get(OrePrefix.crushedPurified, aMaterialOut, 4), 96, 24);
            GTValues.RA.addAutoclaveRecipe(GTUtility.copyAmount(16, tCrop), Materials.UUMatter.getFluid(Math.max(1, ((aMaterial.getMass()+9)/10))), OreDictUnifier.get(OrePrefix.crushedPurified, aMaterial, 1), 10000, (int) (aMaterial.getMass() * 128), 384);
        } else {
            GT_ModHandler.addExtractionRecipe(tCrop, OreDictUnifier.get(OrePrefix.dustTiny, aMaterial, 1));
        }
    }

    private void run2(){
        GTValues.RA.addAssemblylineRecipe(ItemList.Electric_Motor_IV.get(1, new Object(){}),144000,new ItemStack[]{
                OreDictUnifier.get(OrePrefix.stickLong, Materials.NeodymiumMagnetic, 1),
                OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSG, 2L),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64L),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64L),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64L),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.AnnealedCopper, 64L),
                OreDictUnifier.get(OrePrefix.cableGt01, Materials.YttriumBariumCuprate, 2L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(144),
                Materials.Lubricant.getFluid(250)}, ItemList.Electric_Motor_LuV.get(1), 600, 6000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Electric_Motor_LuV.get(1, new Object(){}),144000,new ItemStack[]{
                OreDictUnifier.get(OrePrefix.stickLong, Materials.NeodymiumMagnetic, 2L),
                OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSE, 4L),
                OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4L),
                OreDictUnifier.get(OrePrefix.round, Materials.HSSE, 16L),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64L),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64L),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64L),
                OreDictUnifier.get(OrePrefix.wireFine, Materials.Platinum, 64L),
                OreDictUnifier.get(OrePrefix.cableGt04, Materials.VanadiumGallium, 2L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(288),
                Materials.Lubricant.getFluid(750)}, ItemList.Electric_Motor_ZPM.get(1), 600, 24000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Electric_Motor_ZPM.get(1, new Object(){}),288000,new ItemStack[]{
                OreDictUnifier.get(OrePrefix.block, Materials.NeodymiumMagnetic, 1),
                OreDictUnifier.get(OrePrefix.stickLong, Materials.Neutronium, 4L),
                OreDictUnifier.get(OrePrefix.ring, Materials.Neutronium, 4L),
                OreDictUnifier.get(OrePrefix.round, Materials.Neutronium, 16),
                OreDictUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 64L),
                OreDictUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 64L),
                OreDictUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 64L),
                OreDictUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 64L),
                OreDictUnifier.get(OrePrefix.cableGt04, Materials.NiobiumTitanium, 2L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(1296),
                Materials.Lubricant.getFluid(2000)}, ItemList.Electric_Motor_UV.get(1), 600, 100000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Electric_Pump_IV.get(1, new Object(){}),144000,new ItemStack[]{
                ItemList.Electric_Motor_LuV.get(1, new Object(){}),
                OreDictUnifier.get(OrePrefix.pipeSmall, Materials.Ultimate, 2L),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 2L),
                OreDictUnifier.get(OrePrefix.screw, Materials.HSSG, 8L),
                OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 4L),
                OreDictUnifier.get(OrePrefix.rotor, Materials.HSSG, 2L),
                OreDictUnifier.get(OrePrefix.cableGt01, Materials.YttriumBariumCuprate, 2L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(144),
                Materials.Lubricant.getFluid(250)}, ItemList.Electric_Pump_LuV.get(1), 600, 6000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Electric_Pump_LuV.get(1, new Object(){}),144000,new ItemStack[]{
                ItemList.Electric_Motor_ZPM.get(1, new Object(){}),
                OreDictUnifier.get(OrePrefix.pipeMedium, Materials.Ultimate, 2L),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 2L),
                OreDictUnifier.get(OrePrefix.screw, Materials.HSSE, 8L),
                OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 16L),
                OreDictUnifier.get(OrePrefix.rotor, Materials.HSSE, 2L),
                OreDictUnifier.get(OrePrefix.cableGt04, Materials.VanadiumGallium, 2L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(288),
                Materials.Lubricant.getFluid(750)}, ItemList.Electric_Pump_ZPM.get(1), 600, 24000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Electric_Pump_ZPM.get(1, new Object(){}),288000,new ItemStack[]{
                ItemList.Electric_Motor_UV.get(1, new Object(){}),
                OreDictUnifier.get(OrePrefix.pipeLarge, Materials.Ultimate, 2L),
                OreDictUnifier.get(OrePrefix.plate, Materials.Neutronium, 2L),
                OreDictUnifier.get(OrePrefix.screw, Materials.Neutronium, 8L),
                OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 16L),
                OreDictUnifier.get(OrePrefix.rotor, Materials.Neutronium, 2L),
                OreDictUnifier.get(OrePrefix.cableGt04, Materials.NiobiumTitanium, 2L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(1296),
                Materials.Lubricant.getFluid(2000)}, ItemList.Electric_Pump_UV.get(1), 600, 100000);

//        Conveyor

        GTValues.RA.addAssemblylineRecipe(ItemList.Conveyor_Module_IV.get(1, new Object(){}),144000,new ItemStack[]{
                ItemList.Electric_Motor_LuV.get(2, new Object(){}),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 2L),
                OreDictUnifier.get(OrePrefix.ring, Materials.HSSG, 4L),
                OreDictUnifier.get(OrePrefix.round, Materials.HSSG, 32L),
                OreDictUnifier.get(OrePrefix.cableGt01, Materials.YttriumBariumCuprate, 2L)}, new FluidStack[]{
                Materials.Rubber.getMolten(1440),
                Materials.Lubricant.getFluid(250)}, ItemList.Conveyor_Module_LuV.get(1), 600, 6000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Conveyor_Module_LuV.get(1, new Object(){}),144000,new ItemStack[]{
                ItemList.Electric_Motor_ZPM.get(2, new Object(){}),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 2L),
                OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4L),
                OreDictUnifier.get(OrePrefix.round, Materials.HSSE, 32L),
                OreDictUnifier.get(OrePrefix.cableGt04, Materials.VanadiumGallium, 2L)}, new FluidStack[]{
                Materials.Rubber.getMolten(2880),
                Materials.Lubricant.getFluid(750)}, ItemList.Conveyor_Module_ZPM.get(1), 600, 24000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Conveyor_Module_ZPM.get(1, new Object(){}),288000,new ItemStack[]{
                ItemList.Electric_Motor_UV.get(2, new Object(){}),
                OreDictUnifier.get(OrePrefix.plate, Materials.Neutronium, 2L),
                OreDictUnifier.get(OrePrefix.ring, Materials.Neutronium, 4L),
                OreDictUnifier.get(OrePrefix.round, Materials.Neutronium, 32L),
                OreDictUnifier.get(OrePrefix.cableGt04, Materials.NiobiumTitanium, 2L)}, new FluidStack[]{
                Materials.Rubber.getMolten(2880),
                Materials.Lubricant.getFluid(2000)}, ItemList.Conveyor_Module_UV.get(1), 600, 100000);

//        Piston

        GTValues.RA.addAssemblylineRecipe(ItemList.Electric_Piston_IV.get(1, new Object(){}),144000,new ItemStack[]{
                ItemList.Electric_Motor_LuV.get(1, new Object(){}),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 6L),
                OreDictUnifier.get(OrePrefix.ring, Materials.HSSG, 4L),
                OreDictUnifier.get(OrePrefix.round, Materials.HSSG, 32L),
                OreDictUnifier.get(OrePrefix.stick, Materials.HSSG, 4L),
                OreDictUnifier.get(OrePrefix.gear, Materials.HSSG, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSG, 2L),
                OreDictUnifier.get(OrePrefix.cableGt01, Materials.YttriumBariumCuprate, 4L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(144),
                Materials.Lubricant.getFluid(250)}, ItemList.Electric_Piston_LuV.get(1), 600, 6000);


        GTValues.RA.addAssemblylineRecipe(ItemList.Electric_Piston_LuV.get(1, new Object(){}),144000,new ItemStack[]{
                ItemList.Electric_Motor_ZPM.get(1, new Object(){}),
                OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 6L),
                OreDictUnifier.get(OrePrefix.ring, Materials.HSSE, 4L),
                OreDictUnifier.get(OrePrefix.round, Materials.HSSE, 32L),
                OreDictUnifier.get(OrePrefix.stick, Materials.HSSE, 4L),
                OreDictUnifier.get(OrePrefix.gear, Materials.HSSE, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSE, 2L),
                OreDictUnifier.get(OrePrefix.cableGt04, Materials.VanadiumGallium, 4L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(288),
                Materials.Lubricant.getFluid(750)}, ItemList.Electric_Piston_ZPM.get(1), 600, 24000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Electric_Piston_ZPM.get(1, new Object(){}),288000,new ItemStack[]{
                ItemList.Electric_Motor_UV.get(1, new Object(){}),
                OreDictUnifier.get(OrePrefix.plate, Materials.Neutronium, 6L),
                OreDictUnifier.get(OrePrefix.ring, Materials.Neutronium, 4L),
                OreDictUnifier.get(OrePrefix.round, Materials.Neutronium, 32L),
                OreDictUnifier.get(OrePrefix.stick, Materials.Neutronium, 4L),
                OreDictUnifier.get(OrePrefix.gear, Materials.Neutronium, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.Neutronium, 2L),
                OreDictUnifier.get(OrePrefix.cableGt04, Materials.NiobiumTitanium, 4L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(1296),
                Materials.Lubricant.getFluid(2000)}, ItemList.Electric_Piston_UV.get(1), 600, 100000);

//        RobotArm


        GTValues.RA.addAssemblylineRecipe(ItemList.Robot_Arm_IV.get(1, new Object(){}),144000,new ItemStack[]{
                OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSG, 4L),
                OreDictUnifier.get(OrePrefix.gear, Materials.HSSG, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSG, 3L),
                ItemList.Electric_Motor_LuV.get(2, new Object(){}),
                ItemList.Electric_Piston_LuV.get(1, new Object(){}),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 2L),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Elite, 2L),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Advanced, 6L),
                OreDictUnifier.get(OrePrefix.cableGt01, Materials.YttriumBariumCuprate, 6L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(576),
                Materials.Lubricant.getFluid(250)}, ItemList.Robot_Arm_LuV.get(1), 600, 6000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Robot_Arm_LuV.get(1, new Object(){}),144000,new ItemStack[]{
                OreDictUnifier.get(OrePrefix.stickLong, Materials.HSSE, 4L),
                OreDictUnifier.get(OrePrefix.gear, Materials.HSSE, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.HSSE, 3L),
                ItemList.Electric_Motor_ZPM.get(2, new Object(){}),
                ItemList.Electric_Piston_ZPM.get(1, new Object(){}),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 4L),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Elite, 4L),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Advanced, 12L),
                OreDictUnifier.get(OrePrefix.cableGt04, Materials.VanadiumGallium, 6L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(1152),
                Materials.Lubricant.getFluid(750)}, ItemList.Robot_Arm_ZPM.get(1), 600, 24000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Robot_Arm_ZPM.get(1, new Object(){}),288000,new ItemStack[]{
                OreDictUnifier.get(OrePrefix.stickLong, Materials.Neutronium, 4L),
                OreDictUnifier.get(OrePrefix.gear, Materials.Neutronium, 1),
                OreDictUnifier.get(OrePrefix.gearSmall, Materials.Neutronium, 3L),
                ItemList.Electric_Motor_UV.get(2, new Object(){}),
                ItemList.Electric_Piston_UV.get(1, new Object(){}),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 8L),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Elite, 8L),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Advanced, 24L),
                OreDictUnifier.get(OrePrefix.cableGt04, Materials.NiobiumTitanium, 6L)}, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(2304),
                Materials.Lubricant.getFluid(2000)}, ItemList.Robot_Arm_UV.get(1), 600, 100000);


//        Emitter

        GTValues.RA.addAssemblylineRecipe(ItemList.Emitter_IV.get(1, new Object(){}),144000,new ItemStack[]{
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                        ItemList.Emitter_IV.get(1, new Object(){}),
                        ItemList.Emitter_EV.get(2, new Object(){}),
                        ItemList.Emitter_HV.get(4, new Object(){}),
                        OreDictUnifier.get(OrePrefix.circuit, Materials.Advanced, 7L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64L),
                        OreDictUnifier.get(OrePrefix.cableGt01, Materials.YttriumBariumCuprate, 7L)}, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(576)},
                ItemList.Emitter_LuV.get(1), 600, 6000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Emitter_LuV.get(1, new Object(){}),144000,new ItemStack[]{
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                        ItemList.Emitter_LuV.get(1, new Object(){}),
                        ItemList.Emitter_IV.get(2, new Object(){}),
                        ItemList.Emitter_EV.get(4, new Object(){}),
                        OreDictUnifier.get(OrePrefix.circuit, Materials.Elite, 7L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64L),
                        OreDictUnifier.get(OrePrefix.cableGt04, Materials.VanadiumGallium, 7L)}, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(576)},
                ItemList.Emitter_ZPM.get(1), 600, 24000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Emitter_ZPM.get(1, new Object(){}),288000,new ItemStack[]{
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.Neutronium, 1),
                        ItemList.Emitter_ZPM.get(1, new Object(){}),
                        ItemList.Emitter_LuV.get(2, new Object(){}),
                        ItemList.Emitter_IV.get(4, new Object(){}),
                        OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 7L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64L),
                        OreDictUnifier.get(OrePrefix.cableGt04, Materials.NiobiumTitanium, 7L)}, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(576)},
                ItemList.Emitter_UV.get(1), 600, 100000);

//        Sensor

        GTValues.RA.addAssemblylineRecipe(ItemList.Sensor_IV.get(1, new Object(){}),144000,new ItemStack[]{
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                        ItemList.Sensor_IV.get(1, new Object(){}),
                        ItemList.Sensor_EV.get(2, new Object(){}),
                        ItemList.Sensor_HV.get(4, new Object(){}),
                        OreDictUnifier.get(OrePrefix.circuit, Materials.Advanced, 7L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 64L),
                        OreDictUnifier.get(OrePrefix.cableGt01, Materials.YttriumBariumCuprate, 7L)}, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(576)},
                ItemList.Sensor_LuV.get(1), 600, 6000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Sensor_LuV.get(1, new Object(){}),144000,new ItemStack[]{
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                        ItemList.Sensor_LuV.get(1, new Object(){}),
                        ItemList.Sensor_IV.get(2, new Object(){}),
                        ItemList.Sensor_EV.get(4, new Object(){}),
                        OreDictUnifier.get(OrePrefix.circuit, Materials.Elite, 7L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 64L),
                        OreDictUnifier.get(OrePrefix.cableGt04, Materials.VanadiumGallium, 7L)}, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(576)},
                ItemList.Sensor_ZPM.get(1), 600, 24000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Sensor_ZPM.get(1, new Object(){}),288000,new ItemStack[]{
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.Neutronium, 1),
                        ItemList.Sensor_ZPM.get(1, new Object(){}),
                        ItemList.Sensor_LuV.get(2, new Object(){}),
                        ItemList.Sensor_IV.get(4, new Object(){}),
                        OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 7L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64L),
                        OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64L),
                        OreDictUnifier.get(OrePrefix.cableGt04, Materials.NiobiumTitanium, 7L)}, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(576)},
                ItemList.Sensor_UV.get(1), 600, 100000);

//        Field Generator

        GTValues.RA.addAssemblylineRecipe(ItemList.Field_Generator_IV.get(1, new Object(){}),144000,new ItemStack[]{
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSG, 1),
                        OreDictUnifier.get(OrePrefix.plate, Materials.HSSG, 6L),
                        ItemList.QuantumStar.get(1, new Object(){}),
                        ItemList.Emitter_LuV.get(4, new Object(){}),
                        OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 8L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.cableGt01, Materials.YttriumBariumCuprate, 8L)}, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(576)},
                ItemList.Field_Generator_LuV.get(1), 600, 6000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Field_Generator_LuV.get(1, new Object(){}),144000,new ItemStack[]{
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.HSSE, 1),
                        OreDictUnifier.get(OrePrefix.plate, Materials.HSSE, 6L),
                        ItemList.QuantumStar.get(4, new Object(){}),
                        ItemList.Emitter_ZPM.get(4, new Object(){}),
                        OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 16L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.cableGt04, Materials.VanadiumGallium, 8L)}, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(1152)},
                ItemList.Field_Generator_ZPM.get(1), 600, 24000);

        GTValues.RA.addAssemblylineRecipe(ItemList.Field_Generator_ZPM.get(1, new Object(){}),288000,new ItemStack[]{
                        OreDictUnifier.get(OrePrefix.frameGt, Materials.Neutronium, 1),
                        OreDictUnifier.get(OrePrefix.plate, Materials.Neutronium, 6L),
                        ItemList.Gravistar.get(1, new Object(){}),
                        ItemList.Emitter_UV.get(4, new Object(){}),
                        OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.wireFine, Materials.Osmium, 64L),
                        OreDictUnifier.get(OrePrefix.cableGt04, Materials.NiobiumTitanium, 8L)}, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2304)},
                ItemList.Field_Generator_UV.get(1), 600, 100000);

        //        Quantumsuite
        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.quantum_helmet, 1));
        GTValues.RA.addAssemblylineRecipe(GT_ModHandler.getIC2Item(ItemName.nano_helmet, 1, W), 144000, new ItemStack[]{
                GT_ModHandler.getIC2Item(ItemName.nano_helmet, 1, W),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 2),
                OreDictUnifier.get(OrePrefix.plateAlloy, Materials.Iridium, 4),
                ItemList.Energy_LapotronicOrb.get(1, new Object[]{}),
                ItemList.Sensor_IV.get(1, new Object[]{}),
                ItemList.Field_Generator_EV.get(1, new Object[]{}),
                OreDictUnifier.get(OrePrefix.screw, Materials.Tungsten, 4)
        }, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(2304),
                Materials.Titanium.getMolten(1440)
        }, GT_ModHandler.getIC2Item(ItemName.quantum_helmet, 1), 1500, 4096);

        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.quantum_chestplate, 1));
        GTValues.RA.addAssemblylineRecipe(Loader.isModLoaded("GraviSuite") ? GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W) : GT_ModHandler.getIC2Item(ItemName.nano_chestplate, 1, W), 144000, new ItemStack[]{
                Loader.isModLoaded("GraviSuite") ? GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, W) : GT_ModHandler.getIC2Item(ItemName.nano_chestplate, 1, W),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 2),
                OreDictUnifier.get(OrePrefix.plateAlloy, Materials.Iridium, 6),
                ItemList.Energy_LapotronicOrb.get(1, new Object[]{}),
                ItemList.Field_Generator_HV.get(2, new Object[]{}),
                ItemList.Electric_Motor_IV.get(2, new Object[]{}),
                OreDictUnifier.get(OrePrefix.screw, Materials.Tungsten, 4)
        }, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(2304),
                Materials.Titanium.getMolten(1440)
        }, GT_ModHandler.getIC2Item(ItemName.quantum_chestplate, 1), 1500, 4096);

        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.quantum_leggings, 1));
        GTValues.RA.addAssemblylineRecipe(GT_ModHandler.getIC2Item(ItemName.nano_leggings, 1, W), 144000, new ItemStack[]{
                GT_ModHandler.getIC2Item(ItemName.nano_leggings, 1, W),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 2),
                OreDictUnifier.get(OrePrefix.plateAlloy, Materials.Iridium, 6),
                ItemList.Energy_LapotronicOrb.get(1, new Object[]{}),
                ItemList.Field_Generator_HV.get(2, new Object[]{}),
                ItemList.Electric_Motor_IV.get(4, new Object[]{}),
                OreDictUnifier.get(OrePrefix.screw, Materials.Tungsten, 4)
        }, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(2304),
                Materials.Titanium.getMolten(1440)
        }, GT_ModHandler.getIC2Item(ItemName.quantum_leggings, 1), 1500, 4096);

        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.quantum_boots, 1));
        GTValues.RA.addAssemblylineRecipe(GT_ModHandler.getIC2Item(ItemName.nano_boots, 1, W), 144000, new ItemStack[]{
                GT_ModHandler.getIC2Item(ItemName.nano_boots, 1, W),
                OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 2),
                OreDictUnifier.get(OrePrefix.plateAlloy, Materials.Iridium, 4),
                ItemList.Energy_LapotronicOrb.get(1, new Object[]{}),
                ItemList.Field_Generator_HV.get(1, new Object[]{}),
                ItemList.Electric_Piston_IV.get(2, new Object[]{}),
                OreDictUnifier.get(OrePrefix.screw, Materials.Tungsten, 4)
        }, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(2304),
                Materials.Titanium.getMolten(1440)
        }, GT_ModHandler.getIC2Item(ItemName.quantum_boots, 1), 1500, 4096);

        if(Loader.isModLoaded("GraviSuite")){
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "graviChestPlate", 1, W));
            GTValues.RA.addAssemblylineRecipe(GT_ModHandler.getIC2Item(ItemName.quantum_chestplate, 1, W), 144000, new ItemStack[]{
                    GT_ModHandler.getIC2Item(ItemName.quantum_chestplate, 1, W),
                    GT_ModHandler.getModItem("GraviSuite", "ultimateLappack", 1, W),
                    OreDictUnifier.get(OrePrefix.circuit, Materials.Ultimate, 2),
                    OreDictUnifier.get(OrePrefix.plate, Materials.Duranium, 6),
                    ItemList.Energy_LapotronicOrb2.get(1, new Object[]{}),
                    ItemList.Field_Generator_IV.get(2, new Object[]{}),
                    ItemList.Electric_Motor_ZPM.get(2, new Object[]{}),
                    OreDictUnifier.get(OrePrefix.wireGt02, Materials.Superconductor, 32),
                    OreDictUnifier.get(OrePrefix.screw, Materials.Duranium, 4)
            }, new FluidStack[]{
                    Materials.SolderingAlloy.getMolten(2304),
                    Materials.Tritanium.getMolten(1440)
            }, GT_ModHandler.getModItem("GraviSuite", "graviChestPlate", 1, 27), 1500, 16388);
        }
    }

    public void addPotionRecipes(String aName,ItemStack aItem){
        //normal
        GTValues.RA.addBrewingRecipe(aItem, FluidRegistry.getFluid("potion.awkward"), FluidRegistry.getFluid("potion."+aName), false);
        //strong
        GTValues.RA.addBrewingRecipe(aItem, FluidRegistry.getFluid("potion.thick"), FluidRegistry.getFluid("potion."+aName+".strong"), false);
        //long
        GTValues.RA.addBrewingRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1L), FluidRegistry.getFluid("potion."+aName), FluidRegistry.getFluid("potion."+aName+".long"), false);
        //splash
        if(!(FluidRegistry.getFluid("potion."+aName)==null||FluidRegistry.getFluid("potion."+aName+".splash")==null))
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Gunpowder, 1L), null, null, null, new FluidStack(FluidRegistry.getFluid("potion."+aName),750), new FluidStack(FluidRegistry.getFluid("potion."+aName+".splash"),750), null, 200, 24);
        //splash strong
        if(!(FluidRegistry.getFluid("potion."+aName+".strong")==null||FluidRegistry.getFluid("potion."+aName+".strong.splash")==null))
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Gunpowder, 1L), null, null, null, new FluidStack(FluidRegistry.getFluid("potion."+aName+".strong"),750), new FluidStack(FluidRegistry.getFluid("potion."+aName+".strong.splash"),750), null, 200, 24);
        //splash long
        if(!(FluidRegistry.getFluid("potion."+aName+".long")==null||FluidRegistry.getFluid("potion."+aName+".long.splash")==null))
            GTValues.RA.addMixerRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Gunpowder, 1L), null, null, null, new FluidStack(FluidRegistry.getFluid("potion."+aName+".long"),750), new FluidStack(FluidRegistry.getFluid("potion."+aName+".long.splash"),750), null, 200, 24);
    }
    */
}