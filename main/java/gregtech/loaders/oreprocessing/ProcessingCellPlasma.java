/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  8:   */ import gregtech.api.util.GT_ModHandler;
/*  9:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 10:   */ import gregtech.api.util.GT_Utility;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class ProcessingCellPlasma
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingCellPlasma()
/* 17:   */   {
/* 18:15 */     OrePrefixes.cellPlasma.add(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 22:   */   {
/* 23:20 */     if (aMaterial == Materials.Empty)
/* 24:   */     {
/* 25:21 */       GT_ModHandler.removeRecipeByOutput(aStack);
/* 26:   */     }
/* 27:   */     else
/* 28:   */     {
/* 29:23 */       GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_Utility.getFluidForFilledItem(aStack, true) == null ? GT_Utility.getContainerItem(aStack, true) : null, (int)Math.max(1024L, 1024L * aMaterial.getMass()), 4);
/* 30:24 */       GT_Values.RA.addVacuumFreezerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.cell, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 2L, 1L));
/* 31:   */     }
/* 32:   */   }
/* 33:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingCellPlasma
 * JD-Core Version:    0.7.0.1
 */