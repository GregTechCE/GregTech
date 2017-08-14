package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import ic2.core.ref.ItemName;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingCrafting implements IOreRegistrationHandler {
    public ProcessingCrafting() {
        OrePrefix.crafting.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (aOreDictName) {
            case "craftingQuartz":
                GT_Values.RA.addAssemblerRecipe(new ItemStack(Blocks.REDSTONE_TORCH, 3, 32767), GT_Utility.copyAmount(1, stack), Materials.Concrete.getMolten(144), new ItemStack(Items.COMPARATOR, 1, 0), 800, 1);
                break;
            case "craftingWireCopper":
                GT_Values.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1), GT_Utility.copyAmount(1, stack), ModHandler.getIC2Item(ItemName.frequency_transmitter, 1), 800, 1);
                break;
            case "craftingWireTin":
                GT_Values.RA.addAssemblerRecipe(ItemList.Circuit_Basic.get(1), GT_Utility.copyAmount(1, stack), ModHandler.getIC2Item(ItemName.frequency_transmitter, 1), 800, 1);
                break;
            case "craftingLensBlue":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.block, Materials.Iron, 1), GT_Utility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 13), 2000, 1920);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.block, Materials.WroughtIron, 1), GT_Utility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 13), 2000, 1920);
                GT_Values.RA.addLaserEngraverRecipe(ItemList.IC2_LapotronCrystal.getWildcard(1), GT_Utility.copyAmount(0, stack), ItemList.Circuit_Parts_Crystal_Chip_Master.get(3), 256, 480);
                break;
            case "craftingLensYellow":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.block, Materials.Iron, 1), GT_Utility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 14), 2000, 1920);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.block, Materials.WroughtIron, 1), GT_Utility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 14), 2000, 1920);
                break;
            case "craftingLensCyan":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.block, Materials.Iron, 1), GT_Utility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 15), 2000, 1920);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.block, Materials.WroughtIron, 1), GT_Utility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 15), 2000, 1920);
                break;
            case "craftingLensRed":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Redstone, 1), GT_Utility.copyAmount(0, stack), ModHandler.getModItem("BuildCraft|Silicon", "redstoneChipset", 1, 0), 50, 120);

                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.foil, Materials.Copper, 1), GT_Utility.copyAmount(0, stack), ItemList.Circuit_Parts_Wiring_Basic.get(1), 64, 30);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.foil, Materials.AnnealedCopper, 1), GT_Utility.copyAmount(0, stack), ItemList.Circuit_Parts_Wiring_Basic.get(1), 64, 30);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.foil, Materials.Gold, 1), GT_Utility.copyAmount(0, stack), ItemList.Circuit_Parts_Wiring_Advanced.get(1), 64, 120);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.foil, Materials.Electrum, 1), GT_Utility.copyAmount(0, stack), ItemList.Circuit_Parts_Wiring_Advanced.get(1), 64, 120);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.foil, Materials.Platinum, 1), GT_Utility.copyAmount(0, stack), ItemList.Circuit_Parts_Wiring_Elite.get(1), 64, 480);
                break;
            case "craftingLensGreen":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Olivine, 1), GT_Utility.copyAmount(0, stack), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1), 256, 480);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Emerald, 1), GT_Utility.copyAmount(0, stack), ItemList.Circuit_Parts_Crystal_Chip_Elite.get(1), 256, 480);
                break;
            case "craftingLensWhite":
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.block, Materials.Iron, 1), GT_Utility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 19), 2000, 1920);
                GT_Values.RA.addLaserEngraverRecipe(OreDictionaryUnifier.get(OrePrefix.block, Materials.WroughtIron, 1), GT_Utility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1, 19), 2000, 1920);

                GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.SANDSTONE, 1, 2), GT_Utility.copyAmount(0, stack), new ItemStack(Blocks.SANDSTONE, 1, 1), 50, 16);
                GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.STONE, 1, 0), GT_Utility.copyAmount(0, stack), new ItemStack(Blocks.STONEBRICK, 1, 3), 50, 16);
                GT_Values.RA.addLaserEngraverRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), GT_Utility.copyAmount(0, stack), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), 50, 16);
                GT_Values.RA.addLaserEngraverRecipe(ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartz", 1), GT_Utility.copyAmount(0, stack), ModHandler.getModItem("appliedenergistics2", "tile.BlockQuartzChiseled", 1L), 50, 16);
                break;
        }
    }
}
