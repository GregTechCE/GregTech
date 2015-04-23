/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.util.GT_CoverBehavior;
/*  5:   */ import gregtech.api.util.GT_Utility;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraftforge.fluids.Fluid;
/*  8:   */ 
/*  9:   */ public class GT_Cover_Shutter
/* 10:   */   extends GT_CoverBehavior
/* 11:   */ {
/* 12:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 13:   */   {
/* 14:13 */     return aCoverVariable;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 18:   */   {
/* 19:18 */     aCoverVariable = (aCoverVariable + 1) % 4;
/* 20:19 */     if (aCoverVariable == 0) {
/* 21:19 */       GT_Utility.sendChatToPlayer(aPlayer, "Open if work enabled");
/* 22:   */     }
/* 23:20 */     if (aCoverVariable == 1) {
/* 24:20 */       GT_Utility.sendChatToPlayer(aPlayer, "Open if work disabled");
/* 25:   */     }
/* 26:21 */     if (aCoverVariable == 2) {
/* 27:21 */       GT_Utility.sendChatToPlayer(aPlayer, "Only Output allowed");
/* 28:   */     }
/* 29:22 */     if (aCoverVariable == 3) {
/* 30:22 */       GT_Utility.sendChatToPlayer(aPlayer, "Only Input allowed");
/* 31:   */     }
/* 32:23 */     return aCoverVariable;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 36:   */   {
/* 37:28 */     return aCoverVariable % 2 == 0;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 41:   */   {
/* 42:33 */     return aCoverVariable % 2 == 0;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 46:   */   {
/* 47:38 */     return aCoverVariable % 2 == 0;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 51:   */   {
/* 52:43 */     return aCoverVariable % 2 == 0;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 56:   */   {
/* 57:48 */     return aCoverVariable % 2 == 0;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 61:   */   {
/* 62:53 */     return aCoverVariable % 2 == 0;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 66:   */   {
/* 67:58 */     return aCoverVariable % 2 == 0;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 71:   */   {
/* 72:63 */     return aCoverVariable % 2 == 0;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 76:   */   {
/* 77:68 */     return true;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 81:   */   {
/* 82:73 */     return 0;
/* 83:   */   }
/* 84:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Shutter
 * JD-Core Version:    0.7.0.1
 */