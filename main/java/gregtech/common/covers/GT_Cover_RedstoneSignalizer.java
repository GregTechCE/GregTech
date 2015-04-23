/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.interfaces.tileentity.IMachineProgress;
/*  5:   */ import gregtech.api.util.GT_CoverBehavior;
/*  6:   */ import gregtech.api.util.GT_Utility;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraftforge.fluids.Fluid;
/*  9:   */ 
/* 10:   */ public class GT_Cover_RedstoneSignalizer
/* 11:   */   extends GT_CoverBehavior
/* 12:   */ {
/* 13:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 14:   */   {
/* 15:13 */     aCoverVariable = (aCoverVariable + 1) % 48;
/* 16:14 */     if (aCoverVariable / 16 == 0) {
/* 17:15 */       GT_Utility.sendChatToPlayer(aPlayer, "Signal = " + (aCoverVariable & 0xF));
/* 18:16 */     } else if (aCoverVariable / 16 == 1) {
/* 19:17 */       GT_Utility.sendChatToPlayer(aPlayer, "Conditional Signal = " + (aCoverVariable & 0xF));
/* 20:18 */     } else if (aCoverVariable / 16 == 2) {
/* 21:19 */       GT_Utility.sendChatToPlayer(aPlayer, "Inverted Conditional Signal = " + (aCoverVariable & 0xF));
/* 22:   */     }
/* 23:20 */     return aCoverVariable;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 27:   */   {
/* 28:25 */     return true;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 32:   */   {
/* 33:30 */     return true;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 37:   */   {
/* 38:35 */     return true;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 42:   */   {
/* 43:40 */     return true;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 47:   */   {
/* 48:45 */     return true;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 52:   */   {
/* 53:50 */     return true;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 57:   */   {
/* 58:55 */     return true;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public byte getRedstoneInput(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 62:   */   {
/* 63:60 */     if (aCoverVariable < 16) {
/* 64:60 */       return (byte)(aCoverVariable & 0xF);
/* 65:   */     }
/* 66:61 */     if ((aTileEntity instanceof IMachineProgress))
/* 67:   */     {
/* 68:62 */       if (((IMachineProgress)aTileEntity).isAllowedToWork())
/* 69:   */       {
/* 70:63 */         if (aCoverVariable / 16 == 1) {
/* 71:64 */           return (byte)(aCoverVariable & 0xF);
/* 72:   */         }
/* 73:   */       }
/* 74:67 */       else if (aCoverVariable / 16 == 2) {
/* 75:68 */         return (byte)(aCoverVariable & 0xF);
/* 76:   */       }
/* 77:71 */       return 0;
/* 78:   */     }
/* 79:73 */     return (byte)(aCoverVariable & 0xF);
/* 80:   */   }
/* 81:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_RedstoneSignalizer
 * JD-Core Version:    0.7.0.1
 */