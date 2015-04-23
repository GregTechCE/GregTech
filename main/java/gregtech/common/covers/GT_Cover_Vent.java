/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.interfaces.tileentity.IMachineProgress;
/*  5:   */ import gregtech.api.util.GT_CoverBehavior;
/*  6:   */ import gregtech.api.util.GT_Utility;
/*  7:   */ 
/*  8:   */ public class GT_Cover_Vent
/*  9:   */   extends GT_CoverBehavior
/* 10:   */ {
/* 11:   */   private final int mEfficiency;
/* 12:   */   
/* 13:   */   public GT_Cover_Vent(int aEfficiency)
/* 14:   */   {
/* 15:13 */     this.mEfficiency = aEfficiency;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 19:   */   {
/* 20:18 */     if ((aTileEntity instanceof IMachineProgress))
/* 21:   */     {
/* 22:19 */       if ((((IMachineProgress)aTileEntity).hasThingsToDo()) && (aCoverVariable != ((IMachineProgress)aTileEntity).getProgress()) && 
/* 23:20 */         (!GT_Utility.hasBlockHitBox(aTileEntity.getWorld(), aTileEntity.getOffsetX(aSide, 1), aTileEntity.getOffsetY(aSide, 1), aTileEntity.getOffsetZ(aSide, 1)))) {
/* 24:21 */         ((IMachineProgress)aTileEntity).increaseProgress(this.mEfficiency);
/* 25:   */       }
/* 26:24 */       return ((IMachineProgress)aTileEntity).getProgress();
/* 27:   */     }
/* 28:26 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 32:   */   {
/* 33:31 */     return true;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 37:   */   {
/* 38:36 */     return 60;
/* 39:   */   }
/* 40:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Vent
 * JD-Core Version:    0.7.0.1
 */