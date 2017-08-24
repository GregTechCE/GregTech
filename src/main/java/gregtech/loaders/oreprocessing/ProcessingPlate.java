package gregtech.loaders.oreprocessing;

import gregtech.api.ConfigCategories;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.items.ItemList;
import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.CommonProxy;
import ic2.api.recipe.Recipes;
import ic2.core.block.BlockTexGlass;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingPlate implements IOreRegistrationHandler {
    public ProcessingPlate() {
        OrePrefix.plate.add(this);
        OrePrefix.plateDouble.add(this);
        OrePrefix.plateTriple.add(this);
        OrePrefix.plateQuadruple.add(this);
        OrePrefix.plateQuintuple.add(this);
        OrePrefix.plateDense.add(this);
        OrePrefix.plateAlloy.add(this);
    }
    
    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        boolean aNoSmashing = uEntry.material.contains(SubTag.NO_SMASHING);
        boolean aNoWorking = uEntry.material.contains(SubTag.NO_WORKING);
        long materialMass = uEntry.material.getMass();
        
        switch (uEntry.orePrefix) {
            case plate:
                ModHandler.removeRecipeByOutput(stack);
                ModHandler.removeRecipe(stack);

                if (uEntry.material.mStandardMoltenFluid != null) {
                    GTValues.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Plate.get(0L), uEntry.material.getMolten(144L), OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1L), 32, 8);
                }
                switch (uEntry.material.toString()) {
                    case "Iron":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/iron_block")), null);
                        break;
                    case "Gold":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/gold_block")), null);
                        break;
                    case "Diamond":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/diamond_block")), null);
                        break;
                    case "Emerald":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/emerald_block")), null);
                        break;
                    case "Lapis":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/lapis_block")), null);
                        break;
                    case "Coal":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/coal_block")), null);
                        break;
                    case "Redstone":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/redstone_block")), null);
                        break;
                    case "Glowstone":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/glowstone")), null);
                        break;
                    case "NetherQuartz":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/quartz_block_side")), null);
                        break;
                    case "Obsidian":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/obsidian")), null);
                        break;
                    case "Stone":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/stone")), null);
                        break;
                    case "GraniteBlack":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(Textures.GRANITE_BLACK_SMOOTH), null);
                        break;
                    case "GraniteRed":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(Textures.BlockIcons.GRANITE_RED_SMOOTH), null);
                        break;
                    case "Andesite":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/stone_andesite")), null);
                        break;
                    case "Diorite":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(new RegIconContainer("blocks/stone_diorite")), null);
                        break;
                    case "Concrete":
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(Textures.BlockIcons.CONCRETE_LIGHT_SMOOTH), null);
                        break;
                    default:
                        GregTechAPI.registerCover(stack, new GT_RenderedTexture(uEntry.material.mIconSet.mTextures[71], uEntry.material.materialRGB), null);
                }

                if (uEntry.material.mFuelPower > 0)
                    GTValues.RA.addFuel(GTUtility.copyAmount(1, stack), null, uEntry.material.mFuelPower, uEntry.material.mFuelType);
                GTUtility.removeSimpleIC2MachineRecipe(GTUtility.copyAmount(9, stack), Recipes.compressor.getRecipes(), OreDictionaryUnifier.get(OrePrefix.plateDense, uEntry.material, 1L));
                GTValues.RA.addImplosionRecipe(GTUtility.copyAmount(2, stack), 2, OreDictionaryUnifier.get(OrePrefix.compressed, uEntry.material, 1L), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 1L));
                if (uEntry.material == Materials.Paper)
                    ModHandler.addCraftingRecipe(GTUtility.copyAmount(GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, stack, true) ? 2 : 3, stack), "XXX", 'X', new ItemStack(net.minecraft.init.Items.REEDS, 1, 32767));

                if ((uEntry.material.mUnificatable) && (uEntry.material.mMaterialInto == uEntry.material)) {
                    if (!aNoSmashing && GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammerplating, uEntry.material.toString(), true)) {
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1), CommonProxy.tBits, new Object[]{"h", "X", "X", 'X', OrePrefix.ingot.get(uEntry.material)});
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1), CommonProxy.tBits, new Object[]{"H", "X", 'H', ToolDictNames.craftingToolForgeHammer, 'X', OrePrefix.ingot.get(uEntry.material)});
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1), CommonProxy.tBits, new Object[]{"h", "X", 'X', OrePrefix.gem.get(uEntry.material)});
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1), CommonProxy.tBits, new Object[]{"H", "X", 'H', ToolDictNames.craftingToolForgeHammer, 'X', OrePrefix.gem.get(uEntry.material)});
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 1), CommonProxy.tBits, new Object[]{"h", "X", 'X', OrePrefix.ingotDouble.get(uEntry.material)});
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 2), CommonProxy.tBits, new Object[]{"H", "X", 'H', ToolDictNames.craftingToolForgeHammer, 'X', OrePrefix.ingotDouble.get(uEntry.material)});
                    }
                    if ((uEntry.material.contains(SubTag.MORTAR_GRINDABLE)) && (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, uEntry.material.toString(), true)))
                        ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, uEntry.material, 1), CommonProxy.tBits, new Object[]{"X", "m", 'X', OrePrefix.plate.get(uEntry.material)});
                }
                break;
            case plateDouble:
                ModHandler.removeRecipeByOutput(stack);
                GregTechAPI.registerCover(stack, new GT_RenderedTexture(uEntry.material.mIconSet.mTextures[72], uEntry.material.materialRGB), null);
                if (!aNoSmashing) {
                    GTValues.RA.addBenderRecipe(GTUtility.copyAmount(2, stack), OreDictionaryUnifier.get(OrePrefix.plateQuadruple, uEntry.material, 1L), (int) Math.max(materialMass * 2L, 1L), 96);
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammerdoubleplate, OrePrefix.plate.get(uEntry.material).toString(), true)) {
                        Object aPlateStack = OrePrefix.plate.get(uEntry.material);
                        ModHandler.addCraftingRecipe(GTUtility.copyAmount(1, stack), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"I", "B", "h", 'I', aPlateStack, 'B', aPlateStack});
                        ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1, stack), ToolDictNames.craftingToolForgeHammer, aPlateStack, aPlateStack);
                    }
                    GTValues.RA.addBenderRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 2L), GTUtility.copyAmount(1L, stack), (int) Math.max(materialMass * 2L, 1L), 96);
                } else {
                        GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 2L), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), Materials.Glue.getFluid(10L), GTUtility.copyAmount(1L, stack), 64, 8);
                    }
                break;
            case plateTriple:
                ModHandler.removeRecipeByOutput(stack);
                GregTechAPI.registerCover(stack, new GT_RenderedTexture(uEntry.material.mIconSet.mTextures[73], uEntry.material.materialRGB), null);
                if (!aNoSmashing) {
                    GTValues.RA.addBenderRecipe(GTUtility.copyAmount(3L, stack), OreDictionaryUnifier.get(OrePrefix.plateDense, uEntry.material, 1L), (int) Math.max(uEntry.materialMass * 3L, 1L), 96);
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammertripleplate, OrePrefix.plate.get(uEntry.material).toString(), true)) {
                        Object aPlateStack = OrePrefix.plate.get(uEntry.material);
                        ModHandler.addCraftingRecipe(GTUtility.copyAmount(1L, stack), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | gregtech.api.util.ModHandler.RecipeBits.BUFFERED, new Object[]{"I", "B", "h", 'I', OrePrefix.plateDouble.get(uEntry.material), 'B', aPlateStack});
                        ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1L, stack), ToolDictNames.craftingToolForgeHammer, aPlateStack, aPlateStack, aPlateStack);
                    } 
                    GTValues.RA.addBenderRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 3L), GTUtility.copyAmount(1L, stack), (int) Math.max(uEntry.materialMass * 3L, 1L), 96);
                }else {
                        GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 3L), ItemList.Circuit_Integrated.getWithDamage(0L, 3L), Materials.Glue.getFluid(20L), GTUtility.copyAmount(1L, stack), 96, 8);
                    }
                break;
            case plateQuadruple:
                ModHandler.removeRecipeByOutput(stack);
                GregTechAPI.registerCover(stack, new GT_RenderedTexture(uEntry.material.mIconSet.mTextures[74], uEntry.material.mRGBa), null);
                if (!aNoWorking)
                    GTValues.RA.addCNCRecipe(GTUtility.copyAmount(1L, stack), OreDictionaryUnifier.get(OrePrefix.gearGt, uEntry.material, 1L), (int) Math.max(materialMass * 2L, 1L), 32);
                if (!aNoSmashing) {
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammerquadrupleplate, OrePrefix.plate.get(uEntry.material).toString(), true)) {
                        Object aPlateStack = OrePrefix.plate.get(uEntry.material);
                        ModHandler.addCraftingRecipe(GTUtility.copyAmount(1L, stack), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"I", "B", "h", 'I', OrePrefix.plateTriple.get(uEntry.material), 'B', aPlateStack});
                        ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1L, stack), ToolDictNames.craftingToolForgeHammer, aPlateStack, aPlateStack, aPlateStack, aPlateStack);
                    }
                    GTValues.RA.addBenderRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 4L), GTUtility.copyAmount(1L, stack), (int) Math.max(materialMass * 4L, 1L), 96);
                } else {
                        GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 4L), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), Materials.Glue.getFluid(30L), GTUtility.copyAmount(1L, stack), 128, 8);
                    }
                break;
            case plateQuintuple:
                ModHandler.removeRecipeByOutput(stack);
                GregTechAPI.registerCover(stack, new GT_RenderedTexture(uEntry.material.mIconSet.mTextures[75], uEntry.material.mRGBa), null);
                if (!aNoSmashing) {
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammerquintupleplate, OrePrefix.plate.get(uEntry.material).toString(), true)) {
                        Object aPlateStack = OrePrefix.plate.get(uEntry.material);
                        ModHandler.addCraftingRecipe(GTUtility.copyAmount(1L, stack), ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | ModHandler.RecipeBits.BUFFERED, new Object[]{"I", "B", "h", 'I', OrePrefix.plateQuadruple.get(uEntry.material), 'B', aPlateStack});
                        ModHandler.addShapelessCraftingRecipe(GTUtility.copyAmount(1L, stack), ToolDictNames.craftingToolForgeHammer, aPlateStack, aPlateStack, aPlateStack, aPlateStack, aPlateStack);
                    }
                    GTValues.RA.addBenderRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 5L), GTUtility.copyAmount(1L, stack), (int) Math.max(materialMass * 5L, 1L), 96);
                } else {
                        GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 5L), ItemList.Circuit_Integrated.getWithDamage(0L, 5L), Materials.Glue.getFluid(40L), GTUtility.copyAmount(1L, stack), 160, 8);
                    }
                break;
            case plateDense:
                ModHandler.removeRecipeByOutput(stack);
                GregTechAPI.registerCover(stack, new GT_RenderedTexture(uEntry.material.mIconSet.mTextures[76], uEntry.material.materialRGB), null);
                if (!aNoSmashing) {
                    GTValues.RA.addBenderRecipe(OreDictionaryUnifier.get(OrePrefix.plate, uEntry.material, 9L), GTUtility.copyAmount(1, stack), (int) Math.max(materialMass * 9L, 1L), 96);
                }
                break;
            case plateAlloy:
                switch (aOreDictName) {
                    case "plateAlloyCarbon":
                        GTValues.RA.addAssemblerRecipe(ModHandler.getIC2TEItem(TeBlock.generator, 1), GTUtility.copyAmount(4, stack), ModHandler.getIC2TEItem(TeBlock.wind_generator, 1), 6400, 8);
                    case "plateAlloyAdvanced":
                        ModHandler.addAlloySmelterRecipe(GTUtility.copyAmount(1, stack), new ItemStack(Blocks.GLASS, 3, 32767), ModHandler.getIC2Item(BlockName.glass, BlockTexGlass.GlassType.reinforced, 4), 400, 4, false);
                        ModHandler.addAlloySmelterRecipe(GTUtility.copyAmount(1, stack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Glass, 3L), ModHandler.getIC2Item(BlockName.glass, BlockTexGlass.GlassType.reinforced, 4), 400, 4, false);
                    case "plateAlloyIridium":
                        ModHandler.removeRecipeByOutput(stack);
                }
                break;
        }
    }
}
