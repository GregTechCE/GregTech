/*  1:   */ package gregtech.common;
/*  2:   */ 
/*  3:   */ import net.minecraft.entity.player.EntityPlayer;
/*  4:   */ import net.minecraft.item.ItemStack;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ 
/*  7:   */ public class GT_Server
/*  8:   */   extends GT_Proxy
/*  9:   */ {
/* 10:   */   public boolean isServerSide()
/* 11:   */   {
/* 12:10 */     return true;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean isClientSide()
/* 16:   */   {
/* 17:11 */     return false;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean isBukkitSide()
/* 21:   */   {
/* 22:12 */     return false;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void doSonictronSound(ItemStack aStack, World aWorld, double aX, double aY, double aZ) {}
/* 26:   */   
/* 27:   */   public int addArmor(String aPrefix)
/* 28:   */   {
/* 29:14 */     return 0;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public EntityPlayer getThePlayer()
/* 33:   */   {
/* 34:15 */     return null;
/* 35:   */   }
/* 36:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.GT_Server
 * JD-Core Version:    0.7.0.1
 */