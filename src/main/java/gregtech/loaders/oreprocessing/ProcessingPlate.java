package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.CommonProxy;
import gregtech.common.items.MetaItems;
import ic2.core.block.BlockTexGlass;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMASHING;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_WORKING;

public class ProcessingPlate implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.plate.addProcessingHandler(this);
        OrePrefix.plateDouble.addProcessingHandler(this);
        OrePrefix.plateTriple.addProcessingHandler(this);
        OrePrefix.plateQuadruple.addProcessingHandler(this);
        OrePrefix.plateQuintuple.addProcessingHandler(this);
        OrePrefix.plateDense.addProcessingHandler(this);
        OrePrefix.plateAlloy.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        boolean noSmashing = entry.material.hasFlag(NO_SMASHING);
        boolean noWorking = entry.material.hasFlag(NO_WORKING);
        long materialMass = entry.material.getMass();

        switch (entry.orePrefix) {
            case plate:
                ModHandler.removeRecipe(stack);

                if (entry.material instanceof FluidMaterial) {
                    RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                            .notConsumable(MetaItems.SHAPE_MOLD_PLATE)
                            .fluidInputs(((FluidMaterial) entry.material).getFluid(L))
                            .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material))
                            .duration(32)
                            .EUt(8)
                            .buildAndRegister();
                }

                GTValues.RA.addImplosionRecipe(GTUtility.copyAmount(2, stack), 2, OreDictUnifier.get(OrePrefix.compressed, entry.material, 1L), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 1L));

                if (entry.material == Materials.Paper)
                    ModHandler.addCraftingRecipe(GTUtility.copyAmount(GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, stack, true) ? 2 : 3, stack), "XXX", 'X', new ItemStack(net.minecraft.init.Items.REEDS, 1, 32767));

                if ((entry.material.mUnificatable) && (entry.material.mMaterialInto == entry.material)) {
                    if (!noSmashing && GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammerplating, entry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 1), CommonProxy.tBits, new Object[]{"h", "X", "X", 'X', OrePrefix.ingot.get(entry.material)});
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 1), CommonProxy.tBits, new Object[]{"H", "X", 'H', ToolDictNames.craftingToolForgeHammer, 'X', OrePrefix.ingot.get(entry.material)});
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 1), CommonProxy.tBits, new Object[]{"h", "X", 'X', OrePrefix.gem.get(entry.material)});
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 1), CommonProxy.tBits, new Object[]{"H", "X", 'H', ToolDictNames.craftingToolForgeHammer, 'X', OrePrefix.gem.get(entry.material)});
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 1), CommonProxy.tBits, new Object[]{"h", "X", 'X', OrePrefix.ingotDouble.get(entry.material)});
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 2), CommonProxy.tBits, new Object[]{"H", "X", 'H', ToolDictNames.craftingToolForgeHammer, 'X', OrePrefix.ingotDouble.get(entry.material)});
                    }
                    if ((entry.material.contains(SubTag.MORTAR_GRINDABLE)) && (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, entry.material.toString(), true)))
                        ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, entry.material, 1), CommonProxy.tBits, new Object[]{"X", "m", 'X', OrePrefix.plate.get(entry.material)});
                }
                break;
            case plateDouble:
                ModHandler.removeRecipeByOutput(stack);
                GregTechAPI.registerCover(stack, new GT_RenderedTexture(entry.material.mIconSet.mTextures[72], entry.material.materialRGB), null);
                if (!noSmashing) {
                    GTValues.RA.addBenderRecipe(GTUtility.copyAmount(2, stack), OreDictUnifier.get(OrePrefix.plateQuadruple, entry.material, 1L), (int) Math.max(materialMass * 2L, 1L), 96);
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammerdoubleplate, OrePrefix.plate.get(entry.material).toString(), true)) {
                        Object aPlateStack = OrePrefix.plate.get(entry.material);
                        ModHandler.addCraftingRecipe(GTUtility.copyAmount(1, stack), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"I", "B", "h", 'I', aPlateStack, 'B', aPlateStack});
                        ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1, stack), ToolDictNames.craftingToolForgeHammer, aPlateStack, aPlateStack);
                    }
                    GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 2L), GTUtility.copyAmount(1L, stack), (int) Math.max(materialMass * 2L, 1L), 96);
                } else {
                        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), Materials.Glue.getFluid(10L), GTUtility.copyAmount(1L, stack), 64, 8);
                    }
                break;
            case plateTriple:
                ModHandler.removeRecipeByOutput(stack);
                GregTechAPI.registerCover(stack, new GT_RenderedTexture(entry.material.mIconSet.mTextures[73], entry.material.materialRGB), null);
                if (!noSmashing) {
                    GTValues.RA.addBenderRecipe(GTUtility.copyAmount(3L, stack), OreDictUnifier.get(OrePrefix.plateDense, entry.material, 1L), (int) Math.max(entry.materialMass * 3L, 1L), 96);
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammertripleplate, OrePrefix.plate.get(entry.material).toString(), true)) {
                        Object aPlateStack = OrePrefix.plate.get(entry.material);
                        ModHandler.addCraftingRecipe(GTUtility.copyAmount(1L, stack), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | gregtech.api.util.ModHandler.RecipeBits.BUFFERED, new Object[]{"I", "B", "h", 'I', OrePrefix.plateDouble.get(entry.material), 'B', aPlateStack});
                        ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1L, stack), ToolDictNames.craftingToolForgeHammer, aPlateStack, aPlateStack, aPlateStack);
                    } 
                    GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 3L), GTUtility.copyAmount(1L, stack), (int) Math.max(entry.materialMass * 3L, 1L), 96);
                }else {
                        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 3L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L), Materials.Glue.getFluid(20L), GTUtility.copyAmount(1L, stack), 96, 8);
                    }
                break;
            case plateQuadruple:
                ModHandler.removeRecipeByOutput(stack);
                GregTechAPI.registerCover(stack, new GT_RenderedTexture(entry.material.mIconSet.mTextures[74], entry.material.mRGBa), null);
                if (!noWorking)
                    GTValues.RA.addCNCRecipe(GTUtility.copyAmount(1L, stack), OreDictUnifier.get(OrePrefix.gearGt, entry.material, 1L), (int) Math.max(materialMass * 2L, 1L), 32);
                if (!noSmashing) {
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammerquadrupleplate, OrePrefix.plate.get(entry.material).toString(), true)) {
                        Object aPlateStack = OrePrefix.plate.get(entry.material);
                        ModHandler.addCraftingRecipe(GTUtility.copyAmount(1L, stack), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"I", "B", "h", 'I', OrePrefix.plateTriple.get(entry.material), 'B', aPlateStack});
                        ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1L, stack), ToolDictNames.craftingToolForgeHammer, aPlateStack, aPlateStack, aPlateStack, aPlateStack);
                    }
                    GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 4L), GTUtility.copyAmount(1L, stack), (int) Math.max(materialMass * 4L, 1L), 96);
                } else {
                        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 4L), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.Glue.getFluid(30L), GTUtility.copyAmount(1L, stack), 128, 8);
                    }
                break;
            case plateQuintuple:
                ModHandler.removeRecipeByOutput(stack);
                GregTechAPI.registerCover(stack, new GT_RenderedTexture(entry.material.mIconSet.mTextures[75], entry.material.mRGBa), null);
                if (!noSmashing) {
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammerquintupleplate, OrePrefix.plate.get(entry.material).toString(), true)) {
                        Object aPlateStack = OrePrefix.plate.get(entry.material);
                        ModHandler.addCraftingRecipe(GTUtility.copyAmount(1L, stack), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"I", "B", "h", 'I', OrePrefix.plateQuadruple.get(entry.material), 'B', aPlateStack});
                        ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1L, stack), ToolDictNames.craftingToolForgeHammer, aPlateStack, aPlateStack, aPlateStack, aPlateStack, aPlateStack);
                    }
                    GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 5L), GTUtility.copyAmount(1L, stack), (int) Math.max(materialMass * 5L, 1L), 96);
                } else {
                        GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 5L), ItemList.Circuit_Integrated.getWithDamage(0L, 5L), Materials.Glue.getFluid(40L), GTUtility.copyAmount(1L, stack), 160, 8);
                    }
                break;
            case plateDense:
                ModHandler.removeRecipeByOutput(stack);
                GregTechAPI.registerCover(stack, new GT_RenderedTexture(entry.material.mIconSet.mTextures[76], entry.material.materialRGB), null);
                if (!noSmashing) {
                    GTValues.RA.addBenderRecipe(OreDictUnifier.get(OrePrefix.plate, entry.material, 9L), GTUtility.copyAmount(1, stack), (int) Math.max(materialMass * 9L, 1L), 96);
                }
                break;
            case plateAlloy:
                switch (aOreDictName) {
                    case "plateAlloyCarbon":
                        GTValues.RA.addAssemblerRecipe(ModHandler.getIC2TEItem(TeBlock.generator, 1), GTUtility.copyAmount(4, stack), ModHandler.getIC2TEItem(TeBlock.wind_generator, 1), 6400, 8);
                    case "plateAlloyAdvanced":
                        ModHandler.addAlloySmelterRecipe(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.GLASS, 3, 32767), ModHandler.getIC2Item(BlockName.glass, BlockTexGlass.GlassType.reinforced, 4), 400, 4, false);
                        ModHandler.addAlloySmelterRecipe(GTUtility.copyAmount(1, stack), OreDictUnifier.get(OrePrefix.dust, Materials.Glass, 3L), ModHandler.getIC2Item(BlockName.glass, BlockTexGlass.GlassType.reinforced, 4), 400, 4, false);
                    case "plateAlloyIridium":
                        ModHandler.removeRecipeByOutput(stack);
                }
                break;
        }
    }
}
