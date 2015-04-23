/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_Utility;
/* 10:   */ import net.minecraft.init.Blocks;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class ProcessingStoneVarious
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingStoneVarious()
/* 17:   */   {
/* 18:15 */     OrePrefixes.stone.add(this);
/* 19:16 */     OrePrefixes.stoneCobble.add(this);
/* 20:17 */     OrePrefixes.stoneBricks.add(this);
/* 21:18 */     OrePrefixes.stoneChiseled.add(this);
/* 22:19 */     OrePrefixes.stoneCracked.add(this);
/* 23:20 */     OrePrefixes.stoneMossy.add(this);
/* 24:21 */     OrePrefixes.stoneMossyBricks.add(this);
/* 25:22 */     OrePrefixes.stoneSmooth.add(this);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 29:   */   {
/* 30:27 */     if (aPrefix == OrePrefixes.stoneSmooth)
/* 31:   */     {
/* 32:28 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 1L, new Object[0]), new ItemStack(Blocks.stone_button, 1), 100, 4);
/* 33:29 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(2L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), new ItemStack(Blocks.stone_pressure_plate, 1), 200, 4);
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingStoneVarious
 * JD-Core Version:    0.7.0.1
 */