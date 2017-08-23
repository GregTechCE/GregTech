package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Locale;

public class ProcessingDye implements IOreRegistrationHandler {
    public ProcessingDye() {
        OrePrefix.dye.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        Dyes aDye = Dyes.get(aOreDictName);
        if ((aDye.mIndex >= 0) && (aDye.mIndex < 16) && (GT_Utility.getContainerItem(stack, true) == null)) {
            ModHandler.addAlloySmelterRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Glass, 8), GT_Utility.copyAmount(1, stack), new ItemStack(Blocks.STAINED_GLASS, 8, 15 - aDye.mIndex), 200, 8, false);
            ModHandler.addAlloySmelterRecipe(new ItemStack(Blocks.GLASS, 8, 32767), GT_Utility.copyAmount(1, stack), new ItemStack(Blocks.STAINED_GLASS, 8, 15 - aDye.mIndex), 200, 8, false);
            GTValues.RA.addMixerRecipe(GT_Utility.copyAmount(1, stack), null, null, null, Materials.Water.getFluid(216), FluidRegistry.getFluidStack("dye.watermixed." + aDye.name().toLowerCase(Locale.ENGLISH), 192), null, 16, 4);
            GTValues.RA.addMixerRecipe(GT_Utility.copyAmount(1, stack), null, null, null, ModHandler.getDistilledWater(288), FluidRegistry.getFluidStack("dye.watermixed." + aDye.name().toLowerCase(Locale.ENGLISH), 216), null, 16, 4);
            GTValues.RA.addChemicalRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Salt, 2), Materials.SulfuricAcid.getFluid(432), FluidRegistry.getFluidStack("dye.chemical." + aDye.name().toLowerCase(Locale.ENGLISH), 288), GTValues.NI, 600, 48);
        }
    }
}
