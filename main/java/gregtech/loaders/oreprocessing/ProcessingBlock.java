/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
/*  4:   */ import gregtech.api.enums.ConfigCategories.Recipes;
/*  5:   */ import gregtech.api.enums.GT_Values;
/*  6:   */ import gregtech.api.enums.ItemList;
/*  7:   */ import gregtech.api.enums.Materials;
/*  8:   */ import gregtech.api.enums.OrePrefixes;
/*  9:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/* 10:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/* 11:   */ import gregtech.api.util.GT_Config;
/* 12:   */ import gregtech.api.util.GT_ModHandler;
/* 13:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 14:   */ import gregtech.api.util.GT_Utility;

/* 15:   */ import java.io.PrintStream;

/* 16:   */ import net.minecraft.item.ItemStack;
/* 17:   */ 
/* 18:   */ public class ProcessingBlock
/* 19:   */   implements IOreRecipeRegistrator
/* 20:   */ {
/* 21:   */   public ProcessingBlock()
/* 22:   */   {
/* 23:19 */     OrePrefixes.block.add(this);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 27:   */   {
/* 28:24 */     GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 9L), null, (int)Math.max(aMaterial.getMass() * 10L, 1L), 30);
/* 29:   */     
/* 30:26 */     ItemStack tStack1 = GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L);ItemStack tStack2 = GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L);ItemStack tStack3 = GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L);
/* 31:   */     
/* 32:28 */     GT_ModHandler.removeRecipe(new ItemStack[] { GT_Utility.copyAmount(1L, new Object[] { aStack }) });
/* 33:30 */     if (tStack1 != null) {
/* 34:30 */       GT_ModHandler.removeRecipe(new ItemStack[] { tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1, tStack1 });
/* 35:   */     }
/* 36:31 */     if (tStack2 != null) {
/* 37:31 */       GT_ModHandler.removeRecipe(new ItemStack[] { tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2, tStack2 });
/* 38:   */     }
/* 39:32 */     if (tStack3 != null) {
/* 40:32 */       GT_ModHandler.removeRecipe(new ItemStack[] { tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3, tStack3 });
/* 41:   */     }
/* 42:34 */     if (aMaterial.mStandardMoltenFluid != null) {
/* 43:34 */       GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Block.get(0L, new Object[0]), aMaterial.getMolten(1296L), GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L), 288, 8);
/* 44:   */     }
/* 45:36 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.storageblockcrafting, OrePrefixes.block.get(aMaterial).toString(), false))
/* 46:   */     {
/* 47:37 */       if ((tStack1 == null) && (tStack2 == null) && (tStack3 != null)) {
/* 48:37 */         GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L), new Object[] { "XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefixes.dust.get(aMaterial) });
/* 49:   */       }
/* 50:38 */       if (tStack2 != null) {
/* 51:38 */         GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L), new Object[] { "XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefixes.gem.get(aMaterial) });
/* 52:   */       }
/* 53:39 */       if (tStack1 != null) {
/* 54:39 */         GT_ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.block, aMaterial, 1L), new Object[] { "XXX", "XXX", "XXX", Character.valueOf('X'), OrePrefixes.ingot.get(aMaterial) });
/* 55:   */       }
/* 56:   */     }
/* 57:42 */     if (tStack1 != null) {
/* 58:42 */       tStack1.stackSize = 9;
/* 59:   */     }
/* 60:43 */     if (tStack2 != null) {
/* 61:43 */       tStack2.stackSize = 9;
/* 62:   */     }
/* 63:44 */     if (tStack3 != null) {
/* 64:44 */       tStack3.stackSize = 9;
/* 65:   */     }
/* 66:46 */     GT_Values.RA.addForgeHammerRecipe(aStack, tStack2, 100, 24);
/* 67:48 */     if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.storageblockdecrafting, OrePrefixes.block.get(aMaterial).toString(), tStack2 != null))
/* 68:   */     {
/* 69:49 */       if (tStack3 != null) {
/* 70:49 */         GT_ModHandler.addShapelessCraftingRecipe(tStack3, new Object[] { OrePrefixes.block.get(aMaterial) });
/* 71:   */       }
/* 72:50 */       if (tStack2 != null) {
/* 73:50 */         GT_ModHandler.addShapelessCraftingRecipe(tStack2, new Object[] { OrePrefixes.block.get(aMaterial) });
/* 74:   */       }
/* 75:51 */       if (tStack1 != null) {
/* 76:51 */         GT_ModHandler.addShapelessCraftingRecipe(tStack1, new Object[] { OrePrefixes.block.get(aMaterial) });
/* 77:   */       }
/* 78:   */     }
/* 79:54 */     switch (aMaterial.ordinal())
/* 80:   */     {
/* 81:   */     case 1: 
/* 82:56 */       System.err.println("'blockQuickSilver'?, In which Ice Desert can you actually place this as a solid Block?");
/* 83:57 */       break;
/* 84:   */     case 2: 
/* 85:   */     case 3: 
/* 86:59 */       GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Rod.get(0L, new Object[0]), ItemList.IC2_ShaftIron.get(1L, new Object[0]), 640, 120);
/* 87:60 */       GT_Values.RA.addAssemblerRecipe(ItemList.IC2_Compressed_Coal_Ball.get(8L, new Object[0]), GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.IC2_Compressed_Coal_Chunk.get(1L, new Object[0]), 400, 4);
/* 88:61 */       break;
/* 89:   */     case 4: 
/* 90:63 */       GT_Values.RA.addExtruderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Shape_Extruder_Rod.get(0L, new Object[0]), ItemList.IC2_ShaftSteel.get(1L, new Object[0]), 1280, 120);
/* 91:64 */       GT_Values.RA.addAssemblerRecipe(ItemList.IC2_Compressed_Coal_Ball.get(8L, new Object[0]), GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.IC2_Compressed_Coal_Chunk.get(1L, new Object[0]), 400, 4);
/* 92:   */     }
/* 93:   */   }
/* 94:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingBlock
 * JD-Core Version:    0.7.0.1
 */