/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.enums.SubTag;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 10:   */ import gregtech.api.util.GT_Utility;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class ProcessingIngot3
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingIngot3()
/* 17:   */   {
/* 18:15 */     OrePrefixes.ingotTriple.add(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 22:   */   {
/* 23:20 */     if (!aMaterial.contains(SubTag.NO_SMASHING))
/* 24:   */     {
/* 25:21 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateTriple, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 1L, 1L), 96);
/* 26:22 */       GT_Values.RA.addBenderRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plateDense, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 3L, 1L), 96);
/* 27:   */     }
/* 28:   */   }
/* 29:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingIngot3
 * JD-Core Version:    0.7.0.1
 */