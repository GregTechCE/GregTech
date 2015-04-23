/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
/*  5:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ 
/*  8:   */ public class ProcessingLeaves
/*  9:   */   implements IOreRecipeRegistrator
/* 10:   */ {
/* 11:   */   public ProcessingLeaves()
/* 12:   */   {
/* 13:11 */     OrePrefixes.treeLeaves.add(this);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {}
/* 17:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingLeaves
 * JD-Core Version:    0.7.0.1
 */