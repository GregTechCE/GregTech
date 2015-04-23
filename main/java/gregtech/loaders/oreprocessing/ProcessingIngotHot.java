/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  8:   */ import gregtech.api.util.GT_OreDictUnificator;
/*  9:   */ import gregtech.api.util.GT_Utility;
/* 10:   */ import net.minecraft.item.ItemStack;
/* 11:   */ 
/* 12:   */ public class ProcessingIngotHot
/* 13:   */   implements IOreRecipeRegistrator
/* 14:   */ {
/* 15:   */   public ProcessingIngotHot()
/* 16:   */   {
/* 17:14 */     OrePrefixes.ingotHot.add(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 21:   */   {
/* 22:19 */     GT_Values.RA.addVacuumFreezerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.ingot, aMaterial, 1L), (int)Math.max(aMaterial.getMass() * 3L, 1L));
/* 23:   */   }
/* 24:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingIngotHot
 * JD-Core Version:    0.7.0.1
 */