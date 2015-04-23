/*  1:   */ package gregtech.common.blocks;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.block.Block;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraft.item.ItemStack;
/*  7:   */ 
/*  8:   */ public class GT_Item_Casings1
/*  9:   */   extends GT_Item_Casings_Abstract
/* 10:   */ {
/* 11:   */   public GT_Item_Casings1(Block par1)
/* 12:   */   {
/* 13:11 */     super(par1);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H)
/* 17:   */   {
/* 18:16 */     super.addInformation(aStack, aPlayer, aList, aF3_H);
/* 19:17 */     switch (getDamage(aStack))
/* 20:   */     {
/* 21:   */     case 12: 
/* 22:18 */       aList.add(this.mCoil01Tooltip); break;
/* 23:   */     case 13: 
/* 24:19 */       aList.add(this.mCoil02Tooltip); break;
/* 25:   */     case 14: 
/* 26:20 */       aList.add(this.mCoil03Tooltip);
/* 27:   */     }
/* 28:   */   }
/* 29:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.blocks.GT_Item_Casings1
 * JD-Core Version:    0.7.0.1
 */