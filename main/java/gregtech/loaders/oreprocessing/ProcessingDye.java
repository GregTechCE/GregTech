/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Dyes;
/*  4:   */ import gregtech.api.enums.GT_Values;
/*  5:   */ import gregtech.api.enums.Materials;
/*  6:   */ import gregtech.api.enums.OrePrefixes;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.interfaces.internal.IGT_RecipeAdder;
/*  9:   */ import gregtech.api.util.GT_ModHandler;
/* 10:   */ import gregtech.api.util.GT_OreDictUnificator;
/* 11:   */ import gregtech.api.util.GT_Utility;
/* 12:   */ import net.minecraft.init.Blocks;
/* 13:   */ import net.minecraft.item.ItemStack;
/* 14:   */ import net.minecraftforge.fluids.FluidRegistry;
/* 15:   */ 
/* 16:   */ public class ProcessingDye
/* 17:   */   implements IOreRecipeRegistrator
/* 18:   */ {
/* 19:   */   public ProcessingDye()
/* 20:   */   {
/* 21:20 */     OrePrefixes.dye.add(this);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 25:   */   {
/* 26:25 */     Dyes aDye = Dyes.get(aOreDictName);
/* 27:26 */     if ((aDye.mIndex >= 0) && (aDye.mIndex < 16) && 
/* 28:27 */       (GT_Utility.getContainerItem(aStack, true) == null))
/* 29:   */     {
/* 30:28 */       GT_ModHandler.addAlloySmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 8L), GT_Utility.copyAmount(1L, new Object[] { aStack }), new ItemStack(Blocks.stained_glass, 8, 15 - aDye.mIndex), 200, 8, false);
/* 31:29 */       GT_ModHandler.addAlloySmelterRecipe(new ItemStack(Blocks.glass, 8, 32767), GT_Utility.copyAmount(1L, new Object[] { aStack }), new ItemStack(Blocks.stained_glass, 8, 15 - aDye.mIndex), 200, 8, false);
/* 32:30 */       GT_Values.RA.addMixerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, null, null, Materials.Water.getFluid(144L), FluidRegistry.getFluidStack("dye.watermixed." + aDye.name().toLowerCase(), 144), null, 16, 4);
/* 33:31 */       GT_Values.RA.addMixerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), null, null, null, GT_ModHandler.getDistilledWater(144L), FluidRegistry.getFluidStack("dye.watermixed." + aDye.name().toLowerCase(), 144), null, 16, 4);
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingDye
 * JD-Core Version:    0.7.0.1
 */