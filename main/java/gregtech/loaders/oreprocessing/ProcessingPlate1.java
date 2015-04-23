/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
/*  4:   */ import gregtech.api.enums.ConfigCategories.Recipes;
/*  5:   */ import gregtech.api.enums.GT_Values;
/*  6:   */ import gregtech.api.enums.ItemList;
/*  7:   */ import gregtech.api.enums.Materials;
/*  8:   */ import gregtech.api.enums.OrePrefixes;
/*  9:   */ import gregtech.api.enums.SubTag;
import gregtech.api.enums.Textures;
/* 10:   */ import gregtech.api.enums.Textures.BlockIcons;
/* 11:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/* 12:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/* 13:   */ import gregtech.api.objects.GT_CopiedBlockTexture;
/* 14:   */ import gregtech.api.objects.GT_RenderedTexture;
/* 15:   */ import gregtech.api.util.GT_Config;
/* 16:   */ import gregtech.api.util.GT_ModHandler;
/* 17:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 18:   */ import gregtech.api.util.GT_Utility;
/* 19:   */ import net.minecraft.init.Blocks;
/* 20:   */ import net.minecraft.init.Items;
/* 21:   */ import net.minecraft.item.ItemStack;
/* 22:   */ 
/* 23:   */ public class ProcessingPlate1
/* 24:   */   implements IOreRecipeRegistrator
/* 25:   */ {
/* 26:   */   public ProcessingPlate1()
/* 27:   */   {
/* 28:21 */     OrePrefixes.plate.add(this);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 32:   */   {
/* 33:26 */     GT_ModHandler.removeRecipeByOutput(aStack);
/* 34:27 */     GT_ModHandler.removeRecipe(new ItemStack[] { aStack });
/* 35:   */     
/* 36:29 */     GT_Values.RA.addBoxingRecipe(GT_Utility.copyAmount(16L, new Object[] { aStack }), ItemList.Crate_Empty.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.crateGtPlate, aMaterial, 1L), 100, 8);
/* 37:30 */     GT_Values.RA.addUnboxingRecipe(GT_OreDictUnificator.get(OrePrefixes.crateGtPlate, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 16L), ItemList.Crate_Empty.get(1L, new Object[0]), 800, 1);
/* 38:32 */     if (aMaterial.mStandardMoltenFluid != null) {
/* 39:32 */       GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Plate.get(0L, new Object[0]), aMaterial.getMolten(144L), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), 32, 8);
/* 40:   */     }
/* 41:34 */     switch (aMaterial.ordinal())
/* 42:   */     {
/* 43:   */     case 1: 
/* 44:35 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.iron_block, 1, 0), null); break;
/* 45:   */     case 2: 
/* 46:36 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.gold_block, 1, 0), null); break;
/* 47:   */     case 3: 
/* 48:37 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.diamond_block, 1, 0), null); break;
/* 49:   */     case 4: 
/* 50:38 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.emerald_block, 1, 0), null); break;
/* 51:   */     case 5: 
/* 52:39 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.lapis_block, 1, 0), null); break;
/* 53:   */     case 6: 
/* 54:40 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.coal_block, 1, 0), null); break;
/* 55:   */     case 7: 
/* 56:41 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.redstone_block, 1, 0), null); break;
/* 57:   */     case 8: 
/* 58:42 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.glowstone, 1, 0), null); break;
/* 59:   */     case 9: 
/* 60:43 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.quartz_block, 1, 0), null); break;
/* 61:   */     case 10: 
/* 62:44 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.obsidian, 1, 0), null); break;
/* 63:   */     case 11: 
/* 64:45 */       GregTech_API.registerCover(aStack, new GT_CopiedBlockTexture(Blocks.stone, 1, 0), null); break;
/* 65:   */     case 12: 
/* 66:46 */       GregTech_API.registerCover(aStack, new GT_RenderedTexture(Textures.BlockIcons.GRANITE_BLACK_SMOOTH), null); break;
/* 67:   */     case 13: 
/* 68:47 */       GregTech_API.registerCover(aStack, new GT_RenderedTexture(Textures.BlockIcons.GRANITE_RED_SMOOTH), null); break;
/* 69:   */     case 14: 
/* 70:48 */       GregTech_API.registerCover(aStack, new GT_RenderedTexture(Textures.BlockIcons.CONCRETE_LIGHT_SMOOTH), null); break;
/* 71:   */     default: 
/* 72:49 */       GregTech_API.registerCover(aStack, new GT_RenderedTexture(aMaterial.mIconSet.mTextures[71], aMaterial.mRGBa, false), null);
/* 73:   */     }
/* 74:52 */     if (aMaterial.mFuelPower > 0) {
/* 75:52 */       GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, aMaterial.mFuelPower, aMaterial.mFuelType);
/* 76:   */     }
/* 77:53 */     GT_Utility.removeSimpleIC2MachineRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_ModHandler.getCompressorRecipeList(), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L));
/* 78:54 */     GT_ModHandler.addCompressionRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.compressed, aMaterial, 1L));
/* 79:55 */     if (!aMaterial.contains(SubTag.NO_WORKING)) {
/* 80:55 */       GT_Values.RA.addLatheRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.lens, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, aMaterial, 1L), (int)Math.max(aMaterial.getMass() / 2L, 1L), 16);
/* 81:   */     }
/* 82:57 */     if (aMaterial == Materials.Paper) {
/* 83:57 */       GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, aStack, true) ? 2L : 3L, new Object[] { aStack }), new Object[] { "XXX", Character.valueOf('X'), new ItemStack(Items.reeds, 1, 32767) });
/* 84:   */     }
/* 85:58 */     if (!aMaterial.contains(SubTag.NO_SMASHING))
/* 86:   */     {
/* 87:59 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.foil, aMaterial, 4L), (int)Math.max(aMaterial.getMass() * 1L, 1L), 24);
/* 88:60 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDouble, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 2L, 1L), 96);
/* 89:61 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 3L, 1L), 96);
/* 90:62 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(4L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 4L, 1L), 96);
/* 91:63 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(5L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 5L, 1L), 96);
/* 92:64 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 9L, 1L), 96);
/* 93:   */     }
/* 94:   */   }
/* 95:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingPlate1
 * JD-Core Version:    0.7.0.1
 */