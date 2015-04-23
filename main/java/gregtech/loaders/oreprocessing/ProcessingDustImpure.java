/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.enums.SubTag;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_ModHandler;
/* 10:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 11:   */ import gregtech.api.util.GT_Utility;
/* 12:   */ import net.minecraft.item.ItemStack;
/* 13:   */ import net.minecraftforge.fluids.FluidStack;
/* 14:   */ 
/* 15:   */ public class ProcessingDustImpure
/* 16:   */   implements IOreRecipeRegistrator
/* 17:   */ {
/* 18:   */   public ProcessingDustImpure()
/* 19:   */   {
/* 20:17 */     OrePrefixes.dustPure.add(this);
/* 21:18 */     OrePrefixes.dustImpure.add(this);
/* 22:19 */     OrePrefixes.dustRefined.add(this);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 26:   */   {
/* 27:24 */     Materials tByProduct = (Materials)GT_Utility.selectItemInList(aPrefix == OrePrefixes.dustRefined ? 2 : aPrefix == OrePrefixes.dustPure ? 1 : 0, aMaterial, aMaterial.mOreByProducts);
/* 28:26 */     if (aPrefix == OrePrefixes.dustPure)
/* 29:   */     {
/* 30:27 */       if (aMaterial.contains(SubTag.ELECTROMAGNETIC_SEPERATION_GOLD)) {
/* 31:27 */         GT_Values.RA.addElectromagneticSeparatorRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Gold, 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Gold, 1L), new int[] { 10000, 4000, 2000 }, 400, 24);
/* 32:   */       }
/* 33:28 */       if (aMaterial.contains(SubTag.ELECTROMAGNETIC_SEPERATION_IRON)) {
/* 34:28 */         GT_Values.RA.addElectromagneticSeparatorRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Iron, 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Iron, 1L), new int[] { 10000, 4000, 2000 }, 400, 24);
/* 35:   */       }
/* 36:29 */       if (aMaterial.contains(SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM)) {
/* 37:29 */         GT_Values.RA.addElectromagneticSeparatorRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Neodymium, 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Neodymium, 1L), new int[] { 10000, 4000, 2000 }, 400, 24);
/* 38:   */       }
/* 39:   */     }
/* 40:32 */     if (aMaterial.contains(SubTag.CRYSTALLISABLE))
/* 41:   */     {
/* 42:33 */       GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), Materials.Water.getFluid(200L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 9000, 2000, 24);
/* 43:34 */       GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_ModHandler.getDistilledWater(200L), GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 9500, 1500, 24);
/* 44:   */     }
/* 45:37 */     ItemStack tStack = GT_OreDictUnificator.get(OrePrefixes.dustTiny, tByProduct, GT_OreDictUnificator.get(OrePrefixes.nugget, tByProduct, 1L), 1L);
/* 46:38 */     if (tStack == null)
/* 47:   */     {
/* 48:39 */       tStack = GT_OreDictUnificator.get(OrePrefixes.dustSmall, tByProduct, 1L);
/* 49:40 */       if (tStack == null)
/* 50:   */       {
/* 51:41 */         tStack = GT_OreDictUnificator.get(OrePrefixes.dust, tByProduct, GT_OreDictUnificator.get(OrePrefixes.gem, tByProduct, 1L), 1L);
/* 52:42 */         if (tStack == null)
/* 53:   */         {
/* 54:43 */           tStack = GT_OreDictUnificator.get(OrePrefixes.cell, tByProduct, 1L);
/* 55:44 */           if (tStack == null)
/* 56:   */           {
/* 57:45 */             GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), 0, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), null, null, null, null, null, (int)Math.max(1L, aMaterial.getMass()));
/* 58:   */           }
/* 59:   */           else
/* 60:   */           {
/* 61:47 */             FluidStack tFluid = GT_Utility.getFluidForFilledItem(tStack, true);
/* 62:48 */             if (tFluid == null)
/* 63:   */             {
/* 64:49 */               GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), 1, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 9L), tStack, null, null, null, null, (int)Math.max(1L, aMaterial.getMass() * 72L));
/* 65:   */             }
/* 66:   */             else
/* 67:   */             {
/* 68:51 */               tFluid.amount /= 10;
/* 69:52 */               GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, null, tFluid, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), null, null, null, null, null, null, (int)Math.max(1L, aMaterial.getMass() * 8L), 5);
/* 70:   */             }
/* 71:   */           }
/* 72:   */         }
/* 73:   */         else
/* 74:   */         {
/* 75:56 */           GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(9L, new Object[] { aStack }), 0, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 9L), tStack, null, null, null, null, (int)Math.max(1L, aMaterial.getMass() * 72L));
/* 76:   */         }
/* 77:   */       }
/* 78:   */       else
/* 79:   */       {
/* 80:59 */         GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), 0, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 2L), tStack, null, null, null, null, (int)Math.max(1L, aMaterial.getMass() * 16L));
/* 81:   */       }
/* 82:   */     }
/* 83:   */     else
/* 84:   */     {
/* 85:62 */       GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), 0, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), tStack, null, null, null, null, (int)Math.max(1L, aMaterial.getMass() * 8L));
/* 86:   */     }
/* 87:   */   }
/* 88:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingDustImpure
 * JD-Core Version:    0.7.0.1
 */