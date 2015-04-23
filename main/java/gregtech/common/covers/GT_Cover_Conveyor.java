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
/* 11:   */ public class GT_Cover_Conveyor
/* 12:   */   extends GT_CoverBehavior
/* 13:   */ {
/* 14:   */   public final int mTickRate;
/* 15:   */   
/* 16:   */   public GT_Cover_Conveyor(int aTickRate)
/* 17:   */   {
/* 18:16 */     this.mTickRate = aTickRate;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 22:   */   {
/* 23:21 */     if ((aCoverVariable % 6 > 1) && ((aTileEntity instanceof IMachineProgress))) {
/* 24:21 */       if (((IMachineProgress)aTileEntity).isAllowedToWork() != aCoverVariable % 6 < 4) {
/* 25:21 */         return aCoverVariable;
/* 26:   */       }
/* 27:   */     }
/* 28:22 */     TileEntity tTileEntity = aTileEntity.getTileEntityAtSide(aSide);
/* 29:23 */     aTileEntity.decreaseStoredEnergyUnits(1L, true);
/* 30:24 */     if (((aCoverVariable % 2 != 1) || (aSide != 1)) && ((aCoverVariable % 2 != 0) || (aSide != 0)) && (aTileEntity.getUniversalEnergyCapacity() >= 128L))
/* 31:   */     {
/* 32:25 */       if (aTileEntity.isUniversalEnergyStored(256L)) {
/* 33:26 */         aTileEntity.decreaseStoredEnergyUnits(4 * GT_Utility.moveOneItemStack(aCoverVariable % 2 == 0 ? aTileEntity : tTileEntity, aCoverVariable % 2 != 0 ? aTileEntity : tTileEntity, aCoverVariable % 2 != 0 ? GT_Utility.getOppositeSide(aSide) : aSide, aCoverVariable % 2 == 0 ? GT_Utility.getOppositeSide(aSide) : aSide, null, false, (byte)64, (byte)1, (byte)64, (byte)1), true);
/* 34:   */       }
/* 35:   */     }
/* 36:   */     else {
/* 37:29 */       GT_Utility.moveOneItemStack(aCoverVariable % 2 == 0 ? aTileEntity : tTileEntity, aCoverVariable % 2 != 0 ? aTileEntity : tTileEntity, aCoverVariable % 2 != 0 ? GT_Utility.getOppositeSide(aSide) : aSide, aCoverVariable % 2 == 0 ? GT_Utility.getOppositeSide(aSide) : aSide, null, false, (byte)64, (byte)1, (byte)64, (byte)1);
/* 38:   */     }
/* 39:31 */     return aCoverVariable;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 43:   */   {
/* 44:36 */     aCoverVariable = (aCoverVariable + 1) % 12;
/* 45:37 */     if (aCoverVariable == 0) {
/* 46:37 */       GT_Utility.sendChatToPlayer(aPlayer, "Export");
/* 47:   */     }
/* 48:38 */     if (aCoverVariable == 1) {
/* 49:38 */       GT_Utility.sendChatToPlayer(aPlayer, "Import");
/* 50:   */     }
/* 51:39 */     if (aCoverVariable == 2) {
/* 52:39 */       GT_Utility.sendChatToPlayer(aPlayer, "Export (conditional)");
/* 53:   */     }
/* 54:40 */     if (aCoverVariable == 3) {
/* 55:40 */       GT_Utility.sendChatToPlayer(aPlayer, "Import (conditional)");
/* 56:   */     }
/* 57:41 */     if (aCoverVariable == 4) {
/* 58:41 */       GT_Utility.sendChatToPlayer(aPlayer, "Export (invert cond)");
/* 59:   */     }
/* 60:42 */     if (aCoverVariable == 5) {
/* 61:42 */       GT_Utility.sendChatToPlayer(aPlayer, "Import (invert cond)");
/* 62:   */     }
/* 63:43 */     if (aCoverVariable == 6) {
/* 64:43 */       GT_Utility.sendChatToPlayer(aPlayer, "Export allow Input");
/* 65:   */     }
/* 66:44 */     if (aCoverVariable == 7) {
/* 67:44 */       GT_Utility.sendChatToPlayer(aPlayer, "Import allow Output");
/* 68:   */     }
/* 69:45 */     if (aCoverVariable == 8) {
/* 70:45 */       GT_Utility.sendChatToPlayer(aPlayer, "Export allow Input (conditional)");
/* 71:   */     }
/* 72:46 */     if (aCoverVariable == 9) {
/* 73:46 */       GT_Utility.sendChatToPlayer(aPlayer, "Import allow Output (conditional)");
/* 74:   */     }
/* 75:47 */     if (aCoverVariable == 10) {
/* 76:47 */       GT_Utility.sendChatToPlayer(aPlayer, "Export allow Input (invert cond)");
/* 77:   */     }
/* 78:48 */     if (aCoverVariable == 11) {
/* 79:48 */       GT_Utility.sendChatToPlayer(aPlayer, "Import allow Output (invert cond)");
/* 80:   */     }
/* 81:49 */     return aCoverVariable;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 85:   */   {
/* 86:54 */     return true;
/* 87:   */   }
/* 88:   */   
/* 89:   */   public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 90:   */   {
/* 91:59 */     return true;
/* 92:   */   }
/* 93:   */   
/* 94:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 95:   */   {
/* 96:64 */     return true;
/* 97:   */   }
/* 98:   */   
/* 99:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* :0:   */   {
/* :1:69 */     return true;
/* :2:   */   }
/* :3:   */   
/* :4:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* :5:   */   {
/* :6:74 */     return true;
/* :7:   */   }
/* :8:   */   
/* :9:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* ;0:   */   {
/* ;1:79 */     return true;
/* ;2:   */   }
/* ;3:   */   
/* ;4:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* ;5:   */   {
/* ;6:84 */     return (aCoverVariable >= 6) || (aCoverVariable % 2 != 0);
/* ;7:   */   }
/* ;8:   */   
/* ;9:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* <0:   */   {
/* <1:89 */     return (aCoverVariable >= 6) || (aCoverVariable % 2 == 0);
/* <2:   */   }
/* <3:   */   
/* <4:   */   public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* <5:   */   {
/* <6:94 */     return true;
/* <7:   */   }
/* <8:   */   
/* <9:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* =0:   */   {
/* =1:99 */     return this.mTickRate;
/* =2:   */   }
/* =3:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Conveyor
 * JD-Core Version:    0.7.0.1
 */