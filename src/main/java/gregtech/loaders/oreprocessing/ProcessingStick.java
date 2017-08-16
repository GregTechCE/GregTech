package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingStick implements IOreRegistrationHandler {
    public ProcessingStick() {
        OrePrefix.stick.addProcessingHandler(this);
        OrePrefix.stickLong.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (uEntry.orePrefix == OrePrefix.stick) {
            if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                GT_Values.RA.addLatheRecipe(uEntry.material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE) ? OreDictionaryUnifier.get(OrePrefix.gem, uEntry.material, 1) : OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1L), OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 1L), OreDictionaryUnifier.get(OrePrefix.dustSmall, uEntry.material.mMacerateInto, 2L), (int) Math.max(uEntry.material.getMass() * 5L, 1L), 16);
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.bolt, uEntry.material, 4L), null, (int) Math.max(uEntry.material.getMass() * 2L, 1L), 4);
                if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material)) {
                    ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 2), GT_Proxy.tBits, new Object[]{"s", "X", Character.valueOf('X'), OrePrefix.stickLong.get(uEntry.material)});
                    ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"f ", " X", Character.valueOf('X'), OrePrefix.ingot.get(uEntry.material)});
                }
            }
            if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                GT_Values.RA.addForgeHammerRecipe(GT_Utility.copyAmount(2L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.stickLong, uEntry.material, 1L), (int) Math.max(uEntry.material.getMass(), 1L), 16);
            }
        } else if (uEntry.orePrefix == OrePrefix.stickLong) {
            if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.stick, uEntry.material, 2), null, (int) Math.max(uEntry.material.getMass(), 1L), 4);
                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                    ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stickLong, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefix.gemFlawless.get(uEntry.material)});
                    ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stickLong, uEntry.material, 2), GT_Proxy.tBits, new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefix.gemExquisite.get(uEntry.material)});
                }
            }
            if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[]{stack}), OreDictionaryUnifier.get(OrePrefix.spring, uEntry.material, 1), 200, 16);
                if (uEntry.material.mUnificatable && (uEntry.material.mMaterialInto == uEntry.material)) {
                    ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stickLong, uEntry.material, 1), GT_Proxy.tBits, new Object[]{"ShS", Character.valueOf('S'), OrePrefix.stick.get(uEntry.material)});
                }
            }
        }
    }
}
