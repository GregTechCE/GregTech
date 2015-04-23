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
/* 11:   */ import gregtech.api.util.GT_Utility;
/* 12:   */ import net.minecraft.block.Block;
/* 13:   */ import net.minecraft.init.Blocks;
/* 14:   */ import net.minecraft.init.Items;
/* 15:   */ import net.minecraft.item.ItemStack;
/* 16:   */ 
/* 17:   */ public class ProcessingStone
/* 18:   */   implements IOreRecipeRegistrator
/* 19:   */ {
/* 20:   */   public ProcessingStone()
/* 21:   */   {
/* 22:22 */     OrePrefixes.stone.add(this);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 26:   */   {
/* 27:27 */     Block aBlock = GT_Utility.getBlockFromStack(aStack);
/* 28:28 */     switch (aMaterial.ordinal())
/* 29:   */     {
/* 30:   */     case 1: 
/* 31:30 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(3L, new Object[] { aStack }), new ItemStack(Blocks.redstone_torch, 2), Materials.Redstone.getMolten(144L), new ItemStack(Items.repeater, 1), 100, 4);
/* 32:31 */       break;
/* 33:   */     case 2: 
/* 34:33 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), new ItemStack(Blocks.sand, 1, 0), null, 10, false);
/* 35:34 */       break;
/* 36:   */     case 3: 
/* 37:36 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustImpure, Materials.Endstone, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Tungsten, 1L), 5, false);
/* 38:37 */       break;
/* 39:   */     case 4: 
/* 40:39 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustImpure, Materials.Netherrack, 1L), GT_OreDictUnificator.get(OrePrefixes.nugget, Materials.Gold, 1L), 5, false);
/* 41:40 */       break;
/* 42:   */     case 5: 
/* 43:42 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.Circuit_Integrated.getWithDamage(0L, 1L, new Object[0]), new ItemStack(Blocks.nether_brick_fence, 1), 100, 4);
/* 44:43 */       break;
/* 45:   */     case 6: 
/* 46:45 */       if (aBlock != Blocks.air) {
/* 47:45 */         aBlock.setResistance(20.0F);
/* 48:   */       }
/* 49:46 */       GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 2L), GT_Utility.copyAmount(5L, new Object[] { aStack }), Materials.Glass.getMolten(72L), GT_ModHandler.getModItem("Forestry", "thermionicTubes", 4L, 6), 64, 32);
/* 50:47 */       GT_Values.RA.addAssemblerRecipe(GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherStar, 1L), GT_Utility.copyAmount(3L, new Object[] { aStack }), Materials.Glass.getMolten(720L), new ItemStack(Blocks.beacon, 1, 0), 32, 16);
/* 51:48 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), ItemList.IC2_Compressed_Coal_Ball.get(8L, new Object[0]), ItemList.IC2_Compressed_Coal_Chunk.get(1L, new Object[0]), 400, 4);
/* 52:49 */       GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.gem, Materials.EnderEye, 1L), new ItemStack(Blocks.ender_chest, 1), 400, 4);
/* 53:50 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_ModHandler.getModItem("Railcraft", "cube.crushed.obsidian", 1L, GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L)), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 10, true);
/* 54:51 */       GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), null, 200, 32);
/* 55:52 */       break;
/* 56:   */     case 7: 
/* 57:54 */       GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), null, 100, 32);
/* 58:55 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L));
/* 59:56 */       break;
/* 60:   */     case 8: 
/* 61:   */     case 9: 
/* 62:   */     case 10: 
/* 63:   */     case 11: 
/* 64:58 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, aMaterial, 1L), 10, false);
/* 65:59 */       break;
/* 66:   */     case 12: 
/* 67:61 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 2L), new ItemStack(Items.flint, 1), 50, false);
/* 68:62 */       break;
/* 69:   */     case 13: 
/* 70:64 */       GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), null, 200, 32);
/* 71:65 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Thorium, 1L), 1, false);
/* 72:66 */       break;
/* 73:   */     case 14: 
/* 74:68 */       GT_Values.RA.addCutterRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.plate, aMaterial, 1L), null, 200, 32);
/* 75:69 */       GT_ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1L, new Object[] { aStack }), GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Uranium, 1L), 1, false);
/* 76:   */     }
/* 77:   */   }
/* 78:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingStone
 * JD-Core Version:    0.7.0.1
 */