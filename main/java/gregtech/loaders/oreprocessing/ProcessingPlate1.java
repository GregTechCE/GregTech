/*    */ package gregtech.loaders.oreprocessing;
/*    */ 
/*    */ import gregtech.api.GregTech_API;
/*    */ import gregtech.api.enums.GT_Values;
/*    */ import gregtech.api.enums.ItemList;
/*    */ import gregtech.api.enums.Materials;
/*    */ import gregtech.api.enums.OrePrefixes;
/*    */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*    */ import gregtech.api.objects.GT_CopiedBlockTexture;
/*    */ import gregtech.api.objects.GT_RenderedTexture;
/*    */ import gregtech.api.util.GT_ModHandler;
/*    */ import gregtech.api.util.GT_OreDictUnificator;
/*    */ import gregtech.api.util.GT_Utility;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ProcessingPlate1 implements gregtech.api.interfaces.IOreRecipeRegistrator
/*    */ {
/*    */   public ProcessingPlate1()
/*    */   {
/* 21 */     OrePrefixes.plate.add(this);
/*    */   }
/*    */   
/*    */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/*    */   {
/* 26 */     GT_ModHandler.removeRecipeByOutput(aStack);
/* 27 */     GT_ModHandler.removeRecipe(new ItemStack[] { aStack });
/*    */     
/* 29 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, new Object[] { aStack }), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtPlate, aMaterial, 1L), 100, 8);
/* 30 */     GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtPlate, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
/*    */     
/* 32 */     if (aMaterial.mStandardMoltenFluid != null) { GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Plate.get(0L, new Object[0]), aMaterial.getMolten(144L), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), 32, 8);
/*    */     }
/* 34 */     switch (aMaterial) {
/* 35 */     case Iron:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.iron_block, 1, 0), null); break;
/* 36 */     case Gold:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.gold_block, 1, 0), null); break;
/* 37 */     case Diamond:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.diamond_block, 1, 0), null); break;
/* 38 */     case Emerald:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.emerald_block, 1, 0), null); break;
/* 39 */     case Lapis:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.lapis_block, 1, 0), null); break;
/* 40 */     case Coal:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.coal_block, 1, 0), null); break;
/* 41 */     case Redstone:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.redstone_block, 1, 0), null); break;
/* 42 */     case Glowstone:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.glowstone, 1, 0), null); break;
/* 43 */     case NetherQuartz:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.quartz_block, 1, 0), null); break;
/* 44 */     case Obsidian:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.obsidian, 1, 0), null); break;
/* 45 */     case Stone:  GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.stone, 1, 0), null); break;
/* 46 */     case GraniteBlack:  GregTech_API.registerCover(aStack, new GT_RenderedTexture(gregtech.api.enums.Textures.BlockIcons.GRANITE_BLACK_SMOOTH), null); break;
/* 47 */     case GraniteRed:  GregTech_API.registerCover(aStack, new GT_RenderedTexture(gregtech.api.enums.Textures.BlockIcons.GRANITE_RED_SMOOTH), null); break;
/* 48 */     case Concrete:  GregTech_API.registerCover(aStack, new GT_RenderedTexture(gregtech.api.enums.Textures.BlockIcons.CONCRETE_LIGHT_SMOOTH), null); break;
/* 49 */     default:  GregTech_API.registerCover(aStack, new GT_RenderedTexture(aMaterial.mIconSet.mTextures[71], aMaterial.mRGBa, false), null);
/*    */     }
/*    */     
/* 52 */     if (aMaterial.mFuelPower > 0) GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, aMaterial.mFuelPower, aMaterial.mFuelType);
/* 53 */     GT_Utility.removeSimpleIC2MachineRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_ModHandler.getCompressorRecipeList(), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L));
/* 54 */     GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.compressed, aMaterial, 1L));
/* 55 */     if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_WORKING)) { GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.lens, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 1L), (int)Math.max(aMaterial.getMass() / 2L, 1L), 16);
/*    */     }
/* 57 */     if (aMaterial == Materials.Paper) GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GregTech_API.sRecipeFile.get(gregtech.api.enums.ConfigCategories.Recipes.harderrecipes, aStack, true) ? 2L : 3L, new Object[] { aStack }), new Object[] { "XXX", Character.valueOf('X'), new ItemStack(net.minecraft.init.Items.reeds, 1, 32767) });
/* 58 */     if (!aMaterial.contains(gregtech.api.enums.SubTag.NO_SMASHING)) {
/* 59 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.foil, aMaterial, 4L), (int)Math.max(aMaterial.getMass() * 1L, 1L), 24);
/* 60 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 2L, 1L), 96);
/* 61 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 3L, 1L), 96);
/* 62 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 4L, 1L), 96);
/* 63 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 5L, 1L), 96);
/* 64 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 9L, 1L), 96);
/*    */     }
/*    */   }
/*    */ }


/* Location:              F:\Torrent\minecraft\jdgui test\gregtech_1.7.10-5.07.07-dev.jar!\gregtech\loaders\oreprocessing\ProcessingPlate1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */