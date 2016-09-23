package gregtech.loaders.oreprocessing;

import appeng.core.Api;
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_Proxy;
import net.minecraft.item.ItemStack;

public class ProcessingRound implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingRound() {
        OrePrefixes.round.add(this);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (!aMaterial.contains(SubTag.NO_WORKING)) {
            GT_Values.RA.addLatheRecipe(GT_OreDictUnificator.get(OrePrefixes.nugget, aMaterial, 1L), GT_Utility.copyAmount(1L, new Object[]{aStack}), null, (int) Math.max(aMaterial.getMass() / 4L, 1L), 8);
            if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial))
                GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.round, aMaterial, 1L), GT_Proxy.tBits, new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefixes.nugget.get(aMaterial)});
        }
        if (GT_Mod.gregtechproxy.mAE2Integration) {
            Api.INSTANCE.registries().matterCannon().registerAmmo(GT_OreDictUnificator.get(OrePrefixes.round, aMaterial, 1L), aMaterial.getMass());
        }
    }
}
