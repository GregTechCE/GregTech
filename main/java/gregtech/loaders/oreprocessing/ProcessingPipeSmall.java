/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
/*  5:   */ import gregtech.api.enums.SubTag;
/*  6:   */ import gregtech.api.enums.ToolDictNames;
/*  7:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  8:   */ import gregtech.api.util.GT_ModHandler;
/*  9:   */ import gregtech.api.util.GT_ModHandler.RecipeBits;
/* 10:   */ import gregtech.api.util.GT_Utility;
/* 11:   */ import net.minecraft.item.ItemStack;
/* 12:   */ 
/* 13:   */ public class ProcessingPipeSmall
/* 14:   */   implements IOreRecipeRegistrator
/* 15:   */ {
/* 16:   */   public ProcessingPipeSmall()
/* 17:   */   {
/* 18:16 */     OrePrefixes.pipeSmall.add(this);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 22:   */   {
/* 23:21 */     if ((!aMaterial.contains(SubTag.NO_WORKING)) && ((aMaterial.contains(SubTag.WOOD)) || (!aMaterial.contains(SubTag.NO_SMASHING)))) {
/* 24:22 */       GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(6L, new Object[] { aStack }), GT_ModHandler.RecipeBits.MIRRORED | GT_ModHandler.RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | GT_ModHandler.RecipeBits.BUFFERED, new Object[] { "PWP", "P P", "PHP", Character.valueOf('P'), aMaterial == Materials.Wood ? OrePrefixes.plank.get(aMaterial) : OrePrefixes.plate.get(aMaterial), Character.valueOf('H'), aMaterial.contains(SubTag.WOOD) ? ToolDictNames.craftingToolSoftHammer : ToolDictNames.craftingToolHardHammer, Character.valueOf('W'), aMaterial.contains(SubTag.WOOD) ? ToolDictNames.craftingToolSaw : ToolDictNames.craftingToolWrench });
/* 25:   */     }
/* 26:   */   }
/* 27:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingPipeSmall
 * JD-Core Version:    0.7.0.1
 */