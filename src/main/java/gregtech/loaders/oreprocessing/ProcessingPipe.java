package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.enums.SubTag;
import gregtech.api.items.ToolDictNames;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingPipe implements IOreRegistrationHandler {
    public ProcessingPipe() {
        OrePrefix.pipeLarge.add(this);
        OrePrefix.pipeMedium.add(this);
        OrePrefix.pipeSmall.add(this);
        OrePrefix.pipeRestrictiveHuge.add(this);
        OrePrefix.pipeRestrictiveLarge.add(this);
        OrePrefix.pipeRestrictiveMedium.add(this);
        OrePrefix.pipeRestrictiveSmall.add(this);
        OrePrefix.pipeRestrictiveTiny.add(this);
    }

    @Override
    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        switch (aPrefix) {
            case pipeLarge:
            case pipeMedium:
            case pipeSmall:
                if ((!aMaterial.contains(SubTag.NO_WORKING)) && ((aMaterial.contains(SubTag.WOOD)) || (!aMaterial.contains(SubTag.NO_SMASHING)))) {
                    if (!(aMaterial == Materials.Redstone || aMaterial == Materials.Glowstone)) {
                        long aAmount = aPrefix == OrePrefix.pipeLarge ? 1L : aPrefix == OrePrefix.pipeMedium ? 2L : 6L;
                        String aRecipeString1 = aPrefix == OrePrefix.pipeLarge ? "PHP" : aPrefix == OrePrefix.pipeMedium ? "PPP" : "PWP";
                        String aRecipeString2 = aPrefix == OrePrefix.pipeLarge || aPrefix == OrePrefix.pipeSmall ? "P P" : "W H";
                        String aRecipeString3 = aPrefix == OrePrefix.pipeLarge ? "PWP" : aPrefix == OrePrefix.pipeMedium ? "PPP" : "PHP";
                        GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(aAmount, new Object[]{aStack}), GT_ModHandler.RecipeBits.MIRRORED | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{aRecipeString1, aRecipeString2, aRecipeString3, 'P', aMaterial == Materials.Wood ? OrePrefix.plank.get(aMaterial) : OrePrefix.plate.get(aMaterial), 'H', aMaterial.contains(SubTag.WOOD) ? ToolDictNames.craftingToolSoftHammer : ToolDictNames.craftingToolHardHammer, 'W', aMaterial.contains(SubTag.WOOD) ? ToolDictNames.craftingToolSaw : ToolDictNames.craftingToolWrench});
                    }
                }
                break;
            case pipeRestrictiveHuge:
            case pipeRestrictiveLarge:
            case pipeRestrictiveMedium:
            case pipeRestrictiveSmall:
            case pipeRestrictiveTiny:
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(aOreDictName.replaceFirst("Restrictive", ""), null, 1L, false, true), OreDictionaryUnifier.get(OrePrefix.ring, Materials.Steel, aPrefix.mSecondaryMaterial.mAmount / OrePrefix.ring.mMaterialAmount), GT_Utility.copyAmount(1L, new Object[]{aStack}), (int) (aPrefix.mSecondaryMaterial.mAmount * 400L / OrePrefix.ring.mMaterialAmount), 4);
                break;
        }
    }
}