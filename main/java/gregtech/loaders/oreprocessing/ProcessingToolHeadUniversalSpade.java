/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
/*  5:   */ import gregtech.api.enums.ToolDictNames;
/*  6:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  7:   */ import gregtech.api.util.GT_ModHandler;
/*  8:   */ import gregtech.common.items.GT_MetaGenerated_Tool_01;
/*  9:   */ import net.minecraft.item.ItemStack;
/* 10:   */ 
/* 11:   */ public class ProcessingToolHeadUniversalSpade
/* 12:   */   implements IOreRecipeRegistrator
/* 13:   */ {
/* 14:   */   public ProcessingToolHeadUniversalSpade()
/* 15:   */   {
/* 16:14 */     OrePrefixes.toolHeadUniversalSpade.add(this);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 20:   */   {
/* 21:19 */     GT_ModHandler.addShapelessCraftingRecipe(GT_MetaGenerated_Tool_01.INSTANCE.getToolWithStats(32, 1, aMaterial, aMaterial, null), new Object[] { aOreDictName, OrePrefixes.stick.get(aMaterial), OrePrefixes.screw.get(aMaterial), ToolDictNames.craftingToolScrewdriver });
/* 22:   */   }
/* 23:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingToolHeadUniversalSpade
 * JD-Core Version:    0.7.0.1
 */