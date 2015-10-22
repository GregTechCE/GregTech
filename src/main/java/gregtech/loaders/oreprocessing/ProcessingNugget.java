package gregtech.loaders.oreprocessing;

import appeng.core.Api;
import gregtech.GT_Mod;
import gregtech.api.enums.*;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_RecipeRegistrator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingNugget implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingNugget() {
        OrePrefixes.nugget.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aMaterial == Materials.Iron)
            GT_ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.WroughtIron, 1L));
        if (!aMaterial.contains(SubTag.NO_WORKING))
            GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.round, aMaterial, 1L), null, (int) Math.max(aMaterial.getMass() / 4L, 1L), 8);
        GT_Values.RA.addAlloySmelterRecipe(GT_Utility.copyAmount(9L, new Object[]{aStack}), aMaterial.contains(SubTag.SMELTING_TO_GEM) ? ItemList.Shape_Mold_Ball.get(0L, new Object[0]) : ItemList.Shape_Mold_Ingot.get(0L, new Object[0]), GT_OreDictUnificator.get(aMaterial.contains(SubTag.SMELTING_TO_GEM) ? OrePrefixes.gem : OrePrefixes.ingot, aMaterial.mSmeltInto, 1L), 200, 2);
        if (aMaterial.mStandardMoltenFluid != null)
            GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Nugget.get(0L, new Object[0]), aMaterial.getMolten(16L), GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial, 1L), 16, 4);
        GT_RecipeRegistrator.registerReverseFluidSmelting(aStack, aMaterial, aPrefix.mMaterialAmount, null);
        GT_RecipeRegistrator.registerReverseMacerating(aStack, aMaterial, aPrefix.mMaterialAmount, null, null, null, false);
        if (GT_Mod.gregtechproxy.mAE2Integration) {
            Api.INSTANCE.registries().matterCannon().registerAmmo(GT_OreDictUnificator.get(OrePrefixes.round, aMaterial, 1L), aMaterial.getMass());
            ;
        }
    }
}
