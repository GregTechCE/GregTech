package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.items.ItemList;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import ic2.core.ref.ItemName;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingCrafting implements IOreRegistrationHandler {
    public ProcessingCrafting() {
        OrePrefixes.crafting.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        switch (aOreDictName) {
            case "craftingQuartz":
                GT_Values.RA.addAssemblerRecipe(new ItemStack(Blocks.REDSTONE_TORCH, 3, 32767), GT_Utility.copyAmount(1L, aStack), Materials.Concrete.getMolten(144L), new ItemStack(Items.COMPARATOR, 1, 0), 800, 1);
                break;
            case "craftingWireCopper":
                GT_Values.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1L), GT_Utility.copyAmount(1L, aStack), GT_ModHandler.getIC2Item(ItemName.frequency_transmitter, 1), 800, 1);
                break;
            case "craftingWireTin":
                GT_Values.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1L), GT_Utility.copyAmount(1L, aStack), GT_ModHandler.getIC2Item(ItemName.frequency_transmitter, 1), 800, 1);
                break;
            case "craftingLensBlue":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, aStack), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 13), 2000, 1920);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, aStack), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 13), 2000, 1920);
                GT_Values.RA.addLaserEngraverRecipe(ItemList.IC2_LapotronCrystal.getWildcard(1L), GT_Utility.copyAmount(0L, aStack), ItemList.Circuit_Parts_Crystal_Chip_Master.get(3L), 256, 480);
                break;
            case "craftingLensYellow":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, aStack), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 14), 2000, 1920);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, aStack), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 14), 2000, 1920);
                break;
            case "craftingLensCyan":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, aStack), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 15), 2000, 1920);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, aStack), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 15), 2000, 1920);
                break;
            case "craftingLensRed":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.plate, Materials.Redstone, 1L), GT_Utility.copyAmount(0L, aStack), GT_ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1L, 0), 50, 120);

                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.foil, Materials.Copper, 1L), GT_Utility.copyAmount(0L, aStack), ItemList.Circuit_Parts_Wiring_Basic.get(1L), 64, 30);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.foil, Materials.AnnealedCopper, 1L), GT_Utility.copyAmount(0L, aStack), ItemList.Circuit_Parts_Wiring_Basic.get(1L), 64, 30);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.foil, Materials.Gold, 1L), GT_Utility.copyAmount(0L, aStack), ItemList.Circuit_Parts_Wiring_Advanced.get(1L), 64, 120);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.foil, Materials.Electrum, 1L), GT_Utility.copyAmount(0L, aStack), ItemList.Circuit_Parts_Wiring_Advanced.get(1L), 64, 120);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.foil, Materials.Platinum, 1L), GT_Utility.copyAmount(0L, aStack), ItemList.Circuit_Parts_Wiring_Elite.get(1L), 64, 480);
                break;
            case "craftingLensGreen":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.plate, Materials.Olivine, 1L), GT_Utility.copyAmount(0L, aStack), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L), 256, 480);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.plate, Materials.Emerald, 1L), GT_Utility.copyAmount(0L, aStack), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1L), 256, 480);
                break;
            case "craftingLensWhite":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.block, Materials.Iron, 1L), GT_Utility.copyAmount(0L, aStack), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 19), 2000, 1920);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefixes.block, Materials.WroughtIron, 1L), GT_Utility.copyAmount(0L, aStack), GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 19), 2000, 1920);

                GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.SANDSTONE, 1, 2), GT_Utility.copyAmount(0L, aStack), new ItemStack(Blocks.SANDSTONE, 1, 1), 50, 16);
                GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.STONE, 1, 0), GT_Utility.copyAmount(0L, aStack), new ItemStack(Blocks.STONEBRICK, 1, 3), 50, 16);
                GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), GT_Utility.copyAmount(0L, aStack), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), 50, 16);
                GT_Values.RA.addLaserEngraverRecipe(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartz", 1L), GT_Utility.copyAmount(0L, aStack), GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzChiseled", 1L), 50, 16);
                break;
        }
    }
}
