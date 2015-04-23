/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_Utility;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ 
/* 12:   */ public class ProcessingToolHeadArrow
/* 13:   */   implements IOreRecipeRegistrator
/* 14:   */ {
/* 15:   */   public ProcessingToolHeadArrow()
/* 16:   */   {
/* 17:15 */     OrePrefixes.toolHeadArrow.add(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 21:   */   {
/* 22:20 */     if (aMaterial.mStandardMoltenFluid != null) {
/* 23:20 */       GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Arrow.get(0L, new Object[0]), aMaterial.getMolten(36L), GT_Utility.copyAmount(1L, new Object[] { aStack }), 16, 4);
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingToolHeadArrow
 * JD-Core Version:    0.7.0.1
 */