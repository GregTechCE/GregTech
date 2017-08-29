package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingAll implements IOreRegistrationHandler {
    public ProcessingAll() {
        for (OrePrefix tPrefix : OrePrefix.values()) {
            tPrefix.addProcessingHandler(this);
        }
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {

        //TODO -----------------
        if (aOreDictName.startsWith("slabWood")) {
            GTValues.RA.addChemicalBathRecipe(GTUtility.copyAmount(3L, new Object[]{stack}), Materials.Creosote.getFluid(1000L), ItemList.RC_Tie_Wood.get(1L, new Object[0]), null, null, null, 200, 4);
        } else if (aOreDictName.equals("foodCheese")) {
            GTValues.RA.addSlicerRecipe(stack, ItemList.Shape_Slicer_Flat.get(0), ItemList.Food_Sliced_Cheese.get(4), 64, 4);
            OreDictUnifier.addItemData(stack, new ItemMaterialInfo(Materials.Cheese, 3628800, new MaterialStack[0]));
        } else if (aOreDictName.equals("foodDough")) {
            ModHandler.removeFurnaceSmelting(stack);
            GTValues.RA.addBenderRecipe(GTUtility.copyAmount(1, stack), ItemList.Food_Flat_Dough.get(1), 16, 4);

            GTValues.RA.addMixerRecipe(stack, OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), null, null, null, null, ItemList.Food_Dough_Sugar.get(2), 32, 8);
            GTValues.RA.addMixerRecipe(stack, OreDictUnifier.get(OrePrefix.dust, Materials.Cocoa, 1), null, null, null, null, ItemList.Food_Dough_Chocolate.get(2), 32, 8);
            GTValues.RA.addMixerRecipe(stack, OreDictUnifier.get(OrePrefix.dust, Materials.Chocolate, 1), null, null, null, null, ItemList.Food_Dough_Chocolate.get(2), 32, 8);

            GTValues.RA.addFormingPressRecipe(GTUtility.copyAmount(1, stack), ItemList.Shape_Mold_Bun.get(0), ItemList.Food_Raw_Bun.get(1), 128, 4);
            GTValues.RA.addFormingPressRecipe(GTUtility.copyAmount(2, stack), ItemList.Shape_Mold_Bread.get(0), ItemList.Food_Raw_Bread.get(1), 256, 4);
            GTValues.RA.addFormingPressRecipe(GTUtility.copyAmount(3, stack), ItemList.Shape_Mold_Baguette.get(0), ItemList.Food_Raw_Baguette.get(1), 384, 4);
        } else if (aOreDictName.equals("craftingLensBlue")) {
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.block, Materials.Iron, 1), GTUtility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 13), 2000, 1920);
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.block, Materials.WroughtIron, 1), GTUtility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 13), 2000, 1920);
            GTValues.RA.addLaserEngraverRecipe(ItemList.IC2_LapotronCrystal.getWildcard(1), GTUtility.copyAmount(0, stack), ItemList.Circuit_Parts_Crystal_Chip_Master.get(3), 256, 480);
        } else if (aOreDictName.equals("craftingLensYellow")) {
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.block, Materials.Iron, 1), GTUtility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 14), 2000, 1920);
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.block, Materials.WroughtIron, 1), GTUtility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 14), 2000, 1920);
        } else if (aOreDictName.equals("craftingLensCyan")) {
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.block, Materials.Iron, 1), GTUtility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 15), 2000, 1920);
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.block, Materials.WroughtIron, 1), GTUtility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 15), 2000, 1920);
        } else if (aOreDictName.equals("craftingLensRed")) {
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Redstone, 1), GTUtility.copyAmount(0, stack), ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 0), 50, 120);

            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.foil, Materials.Copper, 1), GTUtility.copyAmount(0, stack), ItemList.Circuit_Parts_Wiring_Basic.get(1), 64, 30);
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.foil, Materials.AnnealedCopper, 1), GTUtility.copyAmount(0, stack), ItemList.Circuit_Parts_Wiring_Basic.get(1), 64, 30);
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.foil, Materials.Gold, 1), GTUtility.copyAmount(0, stack), ItemList.Circuit_Parts_Wiring_Advanced.get(1), 64, 120);
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.foil, Materials.Electrum, 1), GTUtility.copyAmount(0, stack), ItemList.Circuit_Parts_Wiring_Advanced.get(1), 64, 120);
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.foil, Materials.Platinum, 1), GTUtility.copyAmount(0, stack), ItemList.Circuit_Parts_Wiring_Elite.get(1), 64, 480);
        } else if (aOreDictName.equals("craftingLensGreen")) {
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Olivine, 1), GTUtility.copyAmount(0, stack), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1), 256, 480);
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.plate, Materials.Emerald, 1), GTUtility.copyAmount(0, stack), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1), 256, 480);
        } else if (aOreDictName.equals("craftingLensWhite")) {
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.block, Materials.Iron, 1), GTUtility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 19), 2000, 1920);
            GTValues.RA.addLaserEngraverRecipe(OreDictUnifier.get(OrePrefix.block, Materials.WroughtIron, 1), GTUtility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 19), 2000, 1920);

            GTValues.RA.addLaserEngraverRecipe(new ItemStack(Blocks.SANDSTONE, 1, 2), GTUtility.copyAmount(0, stack), new ItemStack(Blocks.SANDSTONE, 1, 1), 50, 16);
            GTValues.RA.addLaserEngraverRecipe(new ItemStack(Blocks.STONE, 1, 0), GTUtility.copyAmount(0, stack), new ItemStack(Blocks.STONEBRICK, 1, 3), 50, 16);
            GTValues.RA.addLaserEngraverRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), GTUtility.copyAmount(0, stack), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), 50, 16);
            GTValues.RA.addLaserEngraverRecipe(ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartz", 1), GTUtility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzChiseled", 1L), 50, 16);
        } else if (oreName.equals("beansCocoa")) {
            ModHandler.addPulverisationRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.dust, Materials.Cocoa, 1));
        }
    }
}
