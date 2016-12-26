package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.ToolDictNames;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingPipe implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingPipe() {
        OrePrefixes.pipeLarge.add(this);
        OrePrefixes.pipeMedium.add(this);
        OrePrefixes.pipeSmall.add(this);
        OrePrefixes.pipeRestrictiveHuge.add(this);
        OrePrefixes.pipeRestrictiveLarge.add(this);
        OrePrefixes.pipeRestrictiveMedium.add(this);
        OrePrefixes.pipeRestrictiveSmall.add(this);
        OrePrefixes.pipeRestrictiveTiny.add(this);
    }

    @Override
    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        switch (aPrefix) {
            case pipeLarge:
            case pipeMedium:
            case pipeSmall:
                if ((!aMaterial.contains(SubTag.NO_WORKING)) && ((aMaterial.contains(SubTag.WOOD)) || (!aMaterial.contains(SubTag.NO_SMASHING)))) {
                    if (!(aMaterial == Materials.Redstone || aMaterial == Materials.Glowstone)) {
                        long aAmount = aPrefix == OrePrefixes.pipeLarge ? 1L : aPrefix == OrePrefixes.pipeMedium ? 2L : 6L;
                        String aRecipeString1 = aPrefix == OrePrefixes.pipeLarge ? "PHP" : aPrefix == OrePrefixes.pipeMedium ? "PPP" : "PWP";
                        String aRecipeString2 = aPrefix == OrePrefixes.pipeLarge || aPrefix == OrePrefixes.pipeSmall ? "P P" : "W H";
                        String aRecipeString3 = aPrefix == OrePrefixes.pipeLarge ? "PWP" : aPrefix == OrePrefixes.pipeMedium ? "PPP" : "PHP";
                        GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(aAmount, new Object[]{aStack}), GT_ModHandler.RecipeBits.MIRRORED | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{aRecipeString1, aRecipeString2, aRecipeString3, 'P', aMaterial == Materials.Wood ? OrePrefixes.plank.get(aMaterial) : OrePrefixes.plate.get(aMaterial), 'H', aMaterial.contains(SubTag.WOOD) ? ToolDictNames.craftingToolSoftHammer : ToolDictNames.craftingToolHardHammer, 'W', aMaterial.contains(SubTag.WOOD) ? ToolDictNames.craftingToolSaw : ToolDictNames.craftingToolWrench});
                    }
                }
                break;
            case pipeRestrictiveHuge:
            case pipeRestrictiveLarge:
            case pipeRestrictiveMedium:
            case pipeRestrictiveSmall:
            case pipeRestrictiveTiny:
                gregtech.api.enums.GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(aOreDictName.replaceFirst("Restrictive", ""), null, 1L, false, true), GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Steel, aPrefix.mSecondaryMaterial.mAmount / OrePrefixes.ring.mMaterialAmount), GT_Utility.copyAmount(1L, new Object[]{aStack}), (int) (aPrefix.mSecondaryMaterial.mAmount * 400L / OrePrefixes.ring.mMaterialAmount), 4);
                break;
        }
    }
}