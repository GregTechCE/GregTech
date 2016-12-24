package gregtech.loaders.oreprocessing;

import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.RegIconContainer;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;

public class ProcessingPlate1 implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingPlate1() {
        OrePrefixes.plate.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_ModHandler.removeRecipeByOutput(aStack);
        GT_ModHandler.removeRecipe(aStack);

        GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, aStack), ItemList.Crate_Empty.get(1L), GT_OreDictUnificator.get(OrePrefixes.crateGtPlate, aMaterial, 1L), 100, 8);
        GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtPlate, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 16L), ItemList.Crate_Empty.get(1L), 800, 1);

        if (aMaterial.mStandardMoltenFluid != null) {
            GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Plate.get(0L), aMaterial.getMolten(144L), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), 32, 8);
        }
        switch (aMaterial) {
            case Iron:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/iron_block")), null);
                break;
            case Gold:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/gold_block")), null);
                break;
            case Diamond:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/diamond_block")), null);
                break;
            case Emerald:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/emerald_block")), null);
                break;
            case Lapis:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/lapis_block")), null);
                break;
            case Coal:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/coal_block")), null);
                break;
            case Redstone:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/redstone_block")), null);
                break;
            case Glowstone:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/glowstone")), null);
                break;
            case NetherQuartz:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/quartz_block_side")), null);
                break;
            case Obsidian:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/obsidian")), null);
                break;
            case Stone:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/stone")), null);
                break;
            case GraniteBlack:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(gregtech.api.enums.Textures.BlockIcons.GRANITE_BLACK_SMOOTH), null);
                break;
            case GraniteRed:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(gregtech.api.enums.Textures.BlockIcons.GRANITE_RED_SMOOTH), null);
                break;
            case Andesite:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/stone_andesite")), null);
                break;
            case Diorite:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(new RegIconContainer("blocks/stone_diorite")), null);
                break;
            case Concrete:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(gregtech.api.enums.Textures.BlockIcons.CONCRETE_LIGHT_SMOOTH), null);
                break;
            default:
                GregTech_API.registerCover(aStack, new GT_RenderedTexture(aMaterial.mIconSet.mTextures[71], aMaterial.mRGBa), null);
        }

        if (aMaterial.mFuelPower > 0)
            GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, aStack), null, aMaterial.mFuelPower, aMaterial.mFuelType);
        GT_Utility.removeSimpleIC2MachineRecipe(GT_Utility.copyAmount(9L, aStack), Recipes.compressor.getRecipes(), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L));
        GT_Values.RA.addImplosionRecipe(GT_Utility.copyAmount(2L, aStack), 2, GT_OreDictUnificator.get(OrePrefixes.compressed, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.DarkAsh, 1L));
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_WORKING)) {
            GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, aStack), GT_OreDictUnificator.get(OrePrefixes.lens, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 1L), (int) Math.max(aMaterial.getMass() / 2L, 1L), 16);
        }
        if (aMaterial == Materials.Paper)
            GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GregTech_API.sRecipeFile.get(gregtech.api.enums.ConfigCategories.Recipes.harderrecipes, aStack, true) ? 2L : 3L, aStack), new Object[]{"XXX", Character.valueOf('X'), new ItemStack(net.minecraft.init.Items.REEDS, 1, 32767)});
        if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, aStack), GT_OreDictUnificator.get(OrePrefixes.foil, aMaterial, 4L), (int) Math.max(aMaterial.getMass() * 1L, 1L), 24);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, aStack), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int) Math.max(aMaterial.getMass() * 2L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, aStack), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int) Math.max(aMaterial.getMass() * 3L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, aStack), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int) Math.max(aMaterial.getMass() * 4L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, aStack), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, aMaterial, 1L), (int) Math.max(aMaterial.getMass() * 5L, 1L), 96);
            GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, aStack), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int) Math.max(aMaterial.getMass() * 9L, 1L), 96);
        }
    }
}
