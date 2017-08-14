package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingGear implements IOreRegistrationHandler {
    public ProcessingGear() {
        OrePrefix.gearGt.add(this);
        OrePrefix.gearGtSmall.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (uEntry.orePrefix) {
            case gearGt:
                ModHandler.removeRecipeByOutput(stack);
                if (uEntry.material.mStandardMoltenFluid != null)
                    GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Gear.get(0), uEntry.material.getMolten(576L), OreDictionaryUnifier.get(uEntry.orePrefix, uEntry.material, 1), 128, 8);
                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING)) {
                    switch (uEntry.material.defaultLocalName) {
                        case "Wood":
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"SPS", "PsP", "SPS", Character.valueOf('P'), OrePrefix.plank.get(uEntry.material), Character.valueOf('S'), OrePrefix.stick.get(uEntry.material)});
                            break;
                        case "Stone":
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, uEntry.material, 1), GT_Proxy.tBits, "SPS", "PfP", "SPS", Character.valueOf('P'), OrePrefix.stoneSmooth, Character.valueOf('S'), new ItemStack(Blocks.STONE_BUTTON, 1, 32767));
                            break;
                        default:
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"SPS", "PwP", "SPS", Character.valueOf('P'), OrePrefix.plate.get(uEntry.material), Character.valueOf('S'), OrePrefix.stick.get(uEntry.material)});
                    }
                }
                break;
            case gearGtSmall:
                if (uEntry.material.mStandardMoltenFluid != null)
                    GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Gear_Small.get(0), uEntry.material.getMolten(144L), GT_Utility.copyAmount(1, stack), 16, 8);
                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material) && !uEntry.material.contains(SubTag.NO_WORKING)) {
                    switch (uEntry.material.defaultLocalName) {
                        case "Wood":
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"P ", " s", Character.valueOf('P'), OrePrefix.plank.get(uEntry.material)});
                            break;
                        case "Stone":
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, uEntry.material, 1), GT_Proxy.tBits, "P ", " f", Character.valueOf('P'), OrePrefix.stoneSmooth);
                            break;
                        default:
                            ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"P ", uEntry.material.contains(SubTag.WOOD) ? " s" : " h", Character.valueOf('P'), OrePrefix.plate.get(uEntry.material)});
                    }
                }
                break;
        }
    }
}
