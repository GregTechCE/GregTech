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
/* 12:   */ public class ProcessingSlab
/* 13:   */   implements IOreRecipeRegistrator
/* 14:   */ {
/* 15:   */   public ProcessingSlab()
/* 16:   */   {
/* 17:14 */     OrePrefixes.slab.add(this);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 21:   */   {
/* 22:19 */     if (aOreDictName.startsWith("slabWood")) {
/* 23:20 */       GT_Values.RA.addChemicalBathRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), Materials.Creosote.getFluid(1000L), ItemList.RC_Tie_Wood.get(1L, new Object[0]), null, null, null, 200, 4);
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingSlab
 * JD-Core Version:    0.7.0.1
 */