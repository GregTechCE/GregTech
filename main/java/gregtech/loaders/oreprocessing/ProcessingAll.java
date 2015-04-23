/*  1:   */ package gregtech.loaders.oreprocessing;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.enums.OrePrefixes;
/*  5:   */ import gregtech.api.interfaces.IOreRecipeRegistrator;
/*  6:   */ import net.minecraft.item.Item;
/*  7:   */ import net.minecraft.item.ItemBlock;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ 
/* 10:   */ public class ProcessingAll
/* 11:   */   implements IOreRecipeRegistrator
/* 12:   */ {
/* 13:   */   public ProcessingAll()
/* 14:   */   {
/* 15:12 */     for (OrePrefixes tPrefix : OrePrefixes.values()) {
/* 16:12 */       tPrefix.add(this);
/* 17:   */     }
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack)
/* 21:   */   {
/* 22:17 */     if (((aStack.getItem() instanceof ItemBlock)) && (aPrefix.mDefaultStackSize < aStack.getItem().getItemStackLimit(aStack))) {
/* 23:17 */       aStack.getItem().setMaxStackSize(aPrefix.mDefaultStackSize);
/* 24:   */     }
/* 25:   */   }
/* 26:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.loaders.oreprocessing.ProcessingAll
 * JD-Core Version:    0.7.0.1
 */