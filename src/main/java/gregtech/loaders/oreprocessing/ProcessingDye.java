package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class ProcessingDye implements IOreRecipeRegistrator {
    public ProcessingDye() {
        OrePrefixes.dye.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        Dyes aDye = Dyes.get(aOreDictName);
        if ((aDye.mIndex >= 0) && (aDye.mIndex < 16) &&
                (GT_Utility.getContainerItem(aStack, true) == null)) {
            GT_ModHandler.addAlloySmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 8L), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.stained_glass, 8, 15 - aDye.mIndex), 200, 8, false);
            GT_ModHandler.addAlloySmelterRecipe(new ItemStack(Blocks.glass, 8, 32767), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.stained_glass, 8, 15 - aDye.mIndex), 200, 8, false);
            GT_Values.RA.addMixerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, null, null, Materials.Water.getFluid(144L), FluidRegistry.getFluidStack("dye.watermixed." + aDye.name().toLowerCase(), 144), null, 16, 4);
            GT_Values.RA.addMixerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, null, null, GT_ModHandler.getDistilledWater(144L), FluidRegistry.getFluidStack("dye.watermixed." + aDye.name().toLowerCase(), 144), null, 16, 4);
        }
    }
}
