/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.interfaces.tileentity.IMachineProgress;
/*  5:   */ import gregtech.api.util.GT_CoverBehavior;
/*  6:   */ import gregtech.api.util.GT_Utility;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraft.tileentity.TileEntity;
/*  9:   */ import net.minecraftforge.fluids.Fluid;
/* 10:   */ 
/* 11:   */ public class GT_Cover_Arm
/* 12:   */   extends GT_CoverBehavior
/* 13:   */ {
/* 14:   */   public final int mTickRate;
/* 15:   */   
/* 16:   */   public GT_Cover_Arm(int aTickRate)
/* 17:   */   {
/* 18:16 */     this.mTickRate = aTickRate;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 22:   */   {
/* 23:21 */     if ((aCoverVariable == 0) || (((aTileEntity instanceof IMachineProgress)) && (!((IMachineProgress)aTileEntity).isAllowedToWork()))) {
/* 24:21 */       return aCoverVariable;
/* 25:   */     }
/* 26:22 */     TileEntity tTileEntity = aTileEntity.getTileEntityAtSide(aSide);
/* 27:23 */     aTileEntity.decreaseStoredEnergyUnits(1L, true);
/* 28:24 */     if (aTileEntity.getUniversalEnergyCapacity() >= 128L)
/* 29:   */     {
/* 30:25 */       if (aTileEntity.isUniversalEnergyStored(256L)) {
/* 31:26 */         aTileEntity.decreaseStoredEnergyUnits(4 * GT_Utility.moveOneItemStackIntoSlot(aCoverVariable > 0 ? aTileEntity : tTileEntity, aCoverVariable > 0 ? tTileEntity : aTileEntity, aCoverVariable > 0 ? aSide : GT_Utility.getOppositeSide(aSide), Math.abs(aCoverVariable) - 1, null, false, (byte)64, (byte)1, (byte)64, (byte)1), true);
/* 32:   */       }
/* 33:   */     }
/* 34:   */     else {
/* 35:29 */       GT_Utility.moveOneItemStackIntoSlot(aCoverVariable > 0 ? aTileEntity : tTileEntity, aCoverVariable > 0 ? tTileEntity : aTileEntity, aCoverVariable > 0 ? aSide : GT_Utility.getOppositeSide(aSide), Math.abs(aCoverVariable) - 1, null, false, (byte)64, (byte)1, (byte)64, (byte)1);
/* 36:   */     }
/* 37:31 */     return aCoverVariable;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 41:   */   {
/* 42:36 */     if (GT_Utility.getClickedFacingCoords(aSide, aX, aY, aZ)[0] >= 0.5F) {
/* 43:36 */       aCoverVariable += 16;
/* 44:   */     } else {
/* 45:36 */       aCoverVariable -= 16;
/* 46:   */     }
/* 47:37 */     GT_Utility.sendChatToPlayer(aPlayer, (aCoverVariable > 0 ? "Puts out into adjacent Slot #" : "Grabs in for own Slot #") + (Math.abs(aCoverVariable) - 1));
/* 48:38 */     return aCoverVariable;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 52:   */   {
/* 53:43 */     if (GT_Utility.getClickedFacingCoords(aSide, aX, aY, aZ)[0] >= 0.5F) {
/* 54:43 */       aCoverVariable++;
/* 55:   */     } else {
/* 56:43 */       aCoverVariable--;
/* 57:   */     }
/* 58:44 */     GT_Utility.sendChatToPlayer(aPlayer, (aCoverVariable > 0 ? "Puts out into adjacent Slot #" : "Grabs in for own Slot #") + (Math.abs(aCoverVariable) - 1));
/* 59:45 */     aTileEntity.setCoverDataAtSide(aSide, aCoverVariable);
/* 60:46 */     return true;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 64:   */   {
/* 65:51 */     return true;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 69:   */   {
/* 70:56 */     return true;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 74:   */   {
/* 75:61 */     return true;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 79:   */   {
/* 80:66 */     return true;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 84:   */   {
/* 85:71 */     return true;
/* 86:   */   }
/* 87:   */   
/* 88:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 89:   */   {
/* 90:76 */     return true;
/* 91:   */   }
/* 92:   */   
/* 93:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 94:   */   {
/* 95:81 */     return true;
/* 96:   */   }
/* 97:   */   
/* 98:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 99:   */   {
/* :0:86 */     return true;
/* :1:   */   }
/* :2:   */   
/* :3:   */   public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* :4:   */   {
/* :5:91 */     return true;
/* :6:   */   }
/* :7:   */   
/* :8:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* :9:   */   {
/* ;0:96 */     return this.mTickRate;
/* ;1:   */   }
/* ;2:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Arm
 * JD-Core Version:    0.7.0.1
 */