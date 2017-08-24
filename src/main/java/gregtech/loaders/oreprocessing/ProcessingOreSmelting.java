package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingOreSmelting implements IOreRegistrationHandler {

    public ProcessingOreSmelting() {
        OrePrefix.crushed.addProcessingHandler(this);
        OrePrefix.crushedPurified.addProcessingHandler(this);
        OrePrefix.crushedCentrifuged.addProcessingHandler(this);
        OrePrefix.dustImpure.addProcessingHandler(this);
        OrePrefix.dustPure.addProcessingHandler(this);
        OrePrefix.dustRefined.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        ModHandler.removeFurnaceSmelting(stack);
        if (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMELTING)) {
            if ((uEntry.material.mBlastFurnaceRequired) || (uEntry.material.mDirectSmelting.mBlastFurnaceRequired)) {
                GTValues.RA.addBlastRecipe(GTUtility.copyAmount(1, stack), null, null, null, uEntry.material.mBlastFurnaceTemp > 1750 ? OreDictionaryUnifier.get(OrePrefix.ingotHot, uEntry.material, OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1), 1) : OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1), null, (int) Math.max(uEntry.material.getMass() / 4L, 1L) * uEntry.material.mBlastFurnaceTemp, 120, uEntry.material.mBlastFurnaceTemp);
                if (uEntry.material.mBlastFurnaceTemp <= 1000)
                    ModHandler.addRCBlastFurnaceRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material, 1), uEntry.material.mBlastFurnaceTemp * 2);
            } else {
                switch (uEntry.orePrefix) {
                    case crushed:
                    case crushedPurified:
                    case crushedCentrifuged:
                        ItemStack tStack = OreDictionaryUnifier.get(OrePrefix.nugget, uEntry.material.mDirectSmelting, uEntry.material.mDirectSmelting == uEntry.material ? 10L : 3L);
                        if (tStack == null) {
                            tStack = OreDictionaryUnifier.get(uEntry.material.contains(SubTag.SMELTING_TO_GEM) ? OrePrefix.gem : OrePrefix.ingot, uEntry.material.mDirectSmelting, 1L);
                        }
                        if ((tStack == null) && (!uEntry.material.contains(SubTag.SMELTING_TO_GEM))) {
                            tStack = OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mDirectSmelting, 1L);
                        }
                        ModHandler.addSmeltingRecipe(stack, tStack);
                        break;
                    default:
                        ModHandler.addSmeltingRecipe(stack, OreDictionaryUnifier.get(OrePrefix.ingot, uEntry.material.mDirectSmelting, 1L));
                }
            }
        }
    }
}
