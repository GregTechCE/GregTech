/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_ModHandler;
/* 10:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class ProcessingGear
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingGear()
/* 17:   */   {
/* 18:16 */     OrePrefixes.gearGt.add(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 22:   */   {
/* 23:21 */     GT_ModHandler.removeRecipeByOutput(aStack);
/* 24:22 */     if (aMaterial.mStandardMoltenFluid != null) {
/* 25:22 */       GT_Values.RA.addFluidSolidifierRecipe(ItemList.Shape_Mold_Gear.get(0L, new Object[0]), aMaterial.getMolten(576L), GT_OreDictUnificator.get(aPrefix, aMaterial, 1L), 128, 8);
/* 26:   */     }
/* 27:   */   }
/* 28:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingGear
 * JD-Core Version:    0.7.0.1
 */