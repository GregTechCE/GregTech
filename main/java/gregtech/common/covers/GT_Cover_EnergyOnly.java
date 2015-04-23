/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.interfaces.tileentity.IMachineProgress;
/*  5:   */ import gregtech.api.util.GT_CoverBehavior;
/*  6:   */ import gregtech.api.util.GT_Utility;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraftforge.fluids.Fluid;
/*  9:   */ 
/* 10:   */ public class GT_Cover_EnergyOnly
/* 11:   */   extends GT_CoverBehavior
/* 12:   */ {
/* 13:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 14:   */   {
/* 15:13 */     aCoverVariable = (aCoverVariable + 1) % 3;
/* 16:14 */     if (aCoverVariable == 0) {
/* 17:14 */       GT_Utility.sendChatToPlayer(aPlayer, "Allow");
/* 18:   */     }
/* 19:15 */     if (aCoverVariable == 1) {
/* 20:15 */       GT_Utility.sendChatToPlayer(aPlayer, "Allow (conditional)");
/* 21:   */     }
/* 22:16 */     if (aCoverVariable == 2) {
/* 23:16 */       GT_Utility.sendChatToPlayer(aPlayer, "Disallow (conditional)");
/* 24:   */     }
/* 25:17 */     return aCoverVariable;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public float getBlastProofLevel(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 29:   */   {
/* 30:22 */     return 20.0F;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 34:   */   {
/* 35:27 */     return false;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 39:   */   {
/* 40:32 */     return false;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 44:   */   {
/* 45:37 */     if ((aCoverVariable > 1) && ((aTileEntity instanceof IMachineProgress))) {
/* 46:37 */       if (((IMachineProgress)aTileEntity).isAllowedToWork() != aCoverVariable < 2) {
/* 47:37 */         return false;
/* 48:   */       }
/* 49:   */     }
/* 50:38 */     return true;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 54:   */   {
/* 55:43 */     if ((aCoverVariable > 1) && ((aTileEntity instanceof IMachineProgress))) {
/* 56:43 */       if (((IMachineProgress)aTileEntity).isAllowedToWork() != aCoverVariable < 2) {
/* 57:43 */         return false;
/* 58:   */       }
/* 59:   */     }
/* 60:44 */     return true;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 64:   */   {
/* 65:49 */     return false;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 69:   */   {
/* 70:54 */     return false;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 74:   */   {
/* 75:59 */     return false;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 79:   */   {
/* 80:64 */     return false;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public boolean isGUIClickable(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 84:   */   {
/* 85:69 */     return false;
/* 86:   */   }
/* 87:   */   
/* 88:   */   public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 89:   */   {
/* 90:74 */     return false;
/* 91:   */   }
/* 92:   */   
/* 93:   */   public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 94:   */   {
/* 95:79 */     return false;
/* 96:   */   }
/* 97:   */   
/* 98:   */   public boolean onCoverRemoval(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, boolean aForced)
/* 99:   */   {
/* :0:84 */     return true;
/* :1:   */   }
/* :2:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_EnergyOnly
 * JD-Core Version:    0.7.0.1
 */