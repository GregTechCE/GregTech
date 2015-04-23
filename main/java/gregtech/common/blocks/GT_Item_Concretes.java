/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import gregtech.api.util.GT_LanguageManager;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.block.Block;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ 
/*  9:   */ public class GT_Item_Concretes
/* 10:   */   extends GT_Item_Stones_Abstract
/* 11:   */ {
/* 12:   */   public GT_Item_Concretes(Block par1)
/* 13:   */   {
/* 14:13 */     super(par1);
/* 15:   */   }
/* 16:   */   
/* 17:16 */   private final String mRunFasterToolTip = GT_LanguageManager.addStringLocalization("gt.runfastertooltip", "You can walk faster on this Block");
/* 18:   */   
/* 19:   */   public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H)
/* 20:   */   {
/* 21:20 */     super.addInformation(aStack, aPlayer, aList, aF3_H);
/* 22:21 */     aList.add(this.mRunFasterToolTip);
/* 23:   */   }
/* 24:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Item_Concretes
 * JD-Core Version:    0.7.0.1
 */