/*  1:   */ package gregtech.loaders.load;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.GT_Values;
/*  4:   */ import gregtech.api.enums.Materials;
/*  5:   */ import gregtech.api.enums.OrePrefixes;
/*  6:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  7:   */ import gregtech.api.util.GT_Log;
/*  8:   */ import gregtech.api.util.GT_ModHandler;
/*  9:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 10:   */ import gregtech.api.util.GT_Recipe;
/* 11:   */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
/* 12:   */ import gregtech.api.util.GT_Recipe.GT_Recipe_Map_Fuel;
/* 13:   */ import java.io.PrintStream;
/* 14:   */ import net.minecraft.init.Blocks;
/* 15:   */ import net.minecraft.init.Items;
/* 16:   */ import net.minecraft.item.ItemStack;
/* 17:   */ 
/* 18:   */ public class GT_FuelLoader
/* 19:   */   implements Runnable
/* 20:   */ {
/* 21:   */   public void run()
/* 22:   */   {
/* 23:16 */     GT_Log.out.println("GT_Mod: Initializing various Fuels.");
/* 24:17 */     new GT_Recipe(new ItemStack(Items.lava_bucket), new ItemStack(Blocks.obsidian), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Electrum, 1L), 30, 2);
/* 25:   */     
/* 26:19 */     GT_Recipe.GT_Recipe_Map.sSmallNaquadahReactorFuels.addRecipe(true, new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.NaquadahEnriched, 1L) }, new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Naquadah, 1L) }, null, null, null, 0, 0, 25000);
/* 27:20 */     GT_Recipe.GT_Recipe_Map.sLargeNaquadahReactorFuels.addRecipe(true, new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NaquadahEnriched, 1L) }, new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Naquadah, 1L) }, null, null, null, 0, 0, 200000);
/* 28:21 */     GT_Recipe.GT_Recipe_Map.sFluidNaquadahReactorFuels.addRecipe(true, new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahEnriched, 1L) }, new ItemStack[] { GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Empty, 1L) }, null, null, null, 0, 0, 200000);
/* 29:   */     
/* 30:23 */     GT_Values.RA.addFuel(GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 1L, 4), null, 4, 5);
/* 31:24 */     GT_Values.RA.addFuel(new ItemStack(Items.experience_bottle, 1), null, 10, 5);
/* 32:25 */     GT_Values.RA.addFuel(new ItemStack(Items.ghast_tear, 1), null, 50, 5);
/* 33:26 */     GT_Values.RA.addFuel(new ItemStack(Blocks.beacon, 1), null, Materials.NetherStar.mFuelPower * 2, Materials.NetherStar.mFuelType);
/* 34:   */   }
/* 35:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.load.GT_FuelLoader
 * JD-Core Version:    0.7.0.1
 */