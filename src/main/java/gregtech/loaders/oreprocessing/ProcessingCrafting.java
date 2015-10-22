package gregtech.loaders.oreprocessing;

import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingCrafting implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingCrafting() {
        OrePrefixes.crafting.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aOreDictName.equals(OreDictNames.craftingQuartz.toString())) {
            GT_Values.RA.addAssemblerRecipe(new ItemStack(Blocks.redstone_torch, 3, 32767), GT_Utility.copyAmount(1L, new Object[]{aStack}), Materials.Concrete.getMolten(144L), new ItemStack(net.minecraft.init.Items.comparator, 1, 0), 800, 1);
        } else if (aOreDictName.equals(OreDictNames.craftingWireCopper.toString())) {
            GT_Values.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1L, new Object[0]), GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_ModHandler.getIC2Item("frequencyTransmitter", 1L), 800, 1);
        } else if (aOreDictName.equals(OreDictNames.craftingWireTin.toString())) {
            GT_Values.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1L, new Object[0]), GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_ModHandler.getIC2Item("frequencyTransmitter", 1L), 800, 1);
        } else if (aOreDictName.equals(OreDictNames.craftingLensBlue.toString())) {
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 13), 2000, 1920);
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 13), 2000, 1920);
            GT_Values.RA.addLaserEngraverRecipe(ItemList.IC2_LapotronCrystal.getWildcard(1L, new Object[0]), GT_Utility.copyAmount(0L, new Object[]{aStack}), ItemList.Circuit_Parts_Crystal_Chip_Master.get(3L, new Object[0]), 256, 480);
        } else if (aOreDictName.equals(OreDictNames.craftingLensYellow.toString())) {
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 14), 2000, 1920);
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 14), 2000, 1920);
        } else if (aOreDictName.equals(OreDictNames.craftingLensCyan.toString())) {
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 15), 2000, 1920);
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 15), 2000, 1920);
        } else if (aOreDictName.equals(OreDictNames.craftingLensRed.toString())) {
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Redstone, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1L, 0), 50, 120);

            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Copper, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), ItemList.Circuit_Parts_Wiring_Basic.get(1L, new Object[0]), 64, 30);
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.AnnealedCopper, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), ItemList.Circuit_Parts_Wiring_Basic.get(1L, new Object[0]), 64, 30);
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Gold, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), ItemList.Circuit_Parts_Wiring_Advanced.get(1L, new Object[0]), 64, 120);
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Electrum, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), ItemList.Circuit_Parts_Wiring_Advanced.get(1L, new Object[0]), 64, 120);
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Platinum, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), ItemList.Circuit_Parts_Wiring_Elite.get(1L, new Object[0]), 64, 480);
        } else if (aOreDictName.equals(OreDictNames.craftingLensGreen.toString())) {
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Olivine, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L, new Object[0]), 256, 480);
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Emerald, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L, new Object[0]), 256, 480);
        } else if (aOreDictName.equals(OreDictNames.craftingLensWhite.toString())) {
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 19), 2000, 1920);
            GT_Values.RA.addLaserEngraverRecipe(GT_OreDictUnificator.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 19), 2000, 1920);

            GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.sandstone, 1, 2), GT_Utility.copyAmount(0L, new Object[]{aStack}), new ItemStack(Blocks.sandstone, 1, 1), 50, 16);
            GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.stone, 1, 0), GT_Utility.copyAmount(0L, new Object[]{aStack}), new ItemStack(Blocks.stonebrick, 1, 3), 50, 16);
            GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.quartz_block, 1, 0), GT_Utility.copyAmount(0L, new Object[]{aStack}), new ItemStack(Blocks.quartz_block, 1, 1), 50, 16);
            GT_Values.RA.addLaserEngraverRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartz", 1L), GT_Utility.copyAmount(0L, new Object[]{aStack}), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzChiseled", 1L), 50, 16);
        }
    }
}
