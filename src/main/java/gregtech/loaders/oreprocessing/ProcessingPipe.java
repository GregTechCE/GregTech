package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingPipe implements IOreRegistrationHandler {
    public ProcessingPipe() {
        OrePrefix.pipeLarge.addProcessingHandler(this);
        OrePrefix.pipeMedium.addProcessingHandler(this);
        OrePrefix.pipeSmall.addProcessingHandler(this);
        OrePrefix.pipeRestrictiveHuge.addProcessingHandler(this);
        OrePrefix.pipeRestrictiveLarge.addProcessingHandler(this);
        OrePrefix.pipeRestrictiveMedium.addProcessingHandler(this);
        OrePrefix.pipeRestrictiveSmall.addProcessingHandler(this);
        OrePrefix.pipeRestrictiveTiny.addProcessingHandler(this);
    }

    @Override
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        switch (uEntry.orePrefix) {
            case pipeLarge:
            case pipeMedium:
            case pipeSmall:
                if ((!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) && ((uEntry.material.contains(SubTag.WOOD)) || (!uEntry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)))) {
                    if (!(uEntry.material == Materials.Redstone || uEntry.material == Materials.Glowstone)) {
                        int aAmount = uEntry.orePrefix == OrePrefix.pipeLarge ? 1 : uEntry.orePrefix == OrePrefix.pipeMedium ? 2 : 6;
                        String aRecipeString1 = uEntry.orePrefix == OrePrefix.pipeLarge ? "PHP" : uEntry.orePrefix == OrePrefix.pipeMedium ? "PPP" : "PWP";
                        String aRecipeString2 = uEntry.orePrefix == OrePrefix.pipeLarge || uEntry.orePrefix == OrePrefix.pipeSmall ? "P P" : "W H";
                        String aRecipeString3 = uEntry.orePrefix == OrePrefix.pipeLarge ? "PWP" : uEntry.orePrefix == OrePrefix.pipeMedium ? "PPP" : "PHP";
                        ModHandler.addCraftingRecipe(GTUtility.copyAmount(aAmount, stack), ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{aRecipeString1, aRecipeString2, aRecipeString3, 'P', uEntry.material == Materials.Wood ? OrePrefix.plank.get(uEntry.material) : OrePrefix.plate.get(uEntry.material), 'H', uEntry.material.contains(SubTag.WOOD) ? ToolDictNames.craftingToolSoftHammer : ToolDictNames.craftingToolHardHammer, 'W', uEntry.material.contains(SubTag.WOOD) ? ToolDictNames.craftingToolSaw : ToolDictNames.craftingToolWrench});
                    }
                }
                break;
            case pipeRestrictiveHuge:
            case pipeRestrictiveLarge:
            case pipeRestrictiveMedium:
            case pipeRestrictiveSmall:
            case pipeRestrictiveTiny:
                GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(aOreDictName.replaceFirst("Restrictive", ""), null, 1L, false, true), OreDictionaryUnifier.get(OrePrefix.ring, Materials.Steel, uEntry.orePrefix.mSecondaryMaterial.mAmount / OrePrefix.ring.mMaterialAmount), GTUtility.copyAmount(1, stack), (int) (uEntry.orePrefix.mSecondaryMaterial.mAmount * 400L / OrePrefix.ring.mMaterialAmount), 4);
                break;
        }
    }
}