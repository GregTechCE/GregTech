/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.ItemList;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 10:   */ import gregtech.api.util.GT_Utility;
/* 11:   */ import net.minecraft.init.Blocks;
/* 12:   */ import net.minecraft.init.Items;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ 
/* 15:   */ public class ProcessingStoneCobble
/* 16:   */   implements IOreRecipeRegistrator
/* 17:   */ {
/* 18:   */   public ProcessingStoneCobble()
/* 19:   */   {
/* 20:18 */     OrePrefixes.stoneCobble.add(this);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 24:   */   {
/* 25:23 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 1L), new ItemStack(Blocks.lever, 1), 400, 1);
/* 26:24 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), new ItemStack(Blocks.furnace, 1), 400, 4);
/* 27:25 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(7L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L), new ItemStack(Blocks.dropper, 1), 400, 4);
/* 28:26 */     GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(7L, new Object[] { aStack }), new ItemStack(Items.bow, 1, 0), Materials.Redstone.getMolten(144L), new ItemStack(Blocks.dispenser, 1), 400, 4);
/* 29:   */   }
/* 30:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingStoneCobble
 * JD-Core Version:    0.7.0.1
 */