/*   1:    */ package gregtech.common.covers;
/*   2:    */ 
/*   3:    */ import gregtech.api.interfaces.tileentity.ICoverable;
/*   4:    */ import gregtech.api.util.GT_CoverBehavior;
/*   5:    */ import gregtech.api.util.GT_Utility;
/*   6:    */ import net.minecraft.entity.player.EntityPlayer;
/*   7:    */ import net.minecraftforge.fluids.Fluid;
/*   8:    */ 
/*   9:    */ public class GT_Cover_EUMeter
/*  10:    */   extends GT_CoverBehavior
/*  11:    */ {
/*  12:    */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/*  13:    */   {
/*  14: 12 */     long tScale = 0L;
/*  15: 13 */     if (aCoverVariable < 2)
/*  16:    */     {
/*  17: 14 */       tScale = aTileEntity.getUniversalEnergyCapacity() / 15L;
/*  18: 15 */       if (tScale > 0L) {
/*  19: 16 */         aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(int)(aTileEntity.getUniversalEnergyStored() / tScale) : (byte)(int)(15L - aTileEntity.getUniversalEnergyStored() / tScale));
/*  20:    */       } else {
/*  21: 18 */         aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
/*  22:    */       }
/*  23:    */     }
/*  24: 20 */     else if (aCoverVariable < 4)
/*  25:    */     {
/*  26: 21 */       tScale = aTileEntity.getEUCapacity() / 15L;
/*  27: 22 */       if (tScale > 0L) {
/*  28: 23 */         aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(int)(aTileEntity.getStoredEU() / tScale) : (byte)(int)(15L - aTileEntity.getStoredEU() / tScale));
/*  29:    */       } else {
/*  30: 25 */         aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
/*  31:    */       }
/*  32:    */     }
/*  33: 27 */     else if (aCoverVariable < 6)
/*  34:    */     {
/*  35: 28 */       tScale = aTileEntity.getSteamCapacity() / 15L;
/*  36: 29 */       if (tScale > 0L) {
/*  37: 30 */         aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(int)(aTileEntity.getStoredSteam() / tScale) : (byte)(int)(15L - aTileEntity.getStoredSteam() / tScale));
/*  38:    */       } else {
/*  39: 32 */         aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
/*  40:    */       }
/*  41:    */     }
/*  42: 34 */     else if (aCoverVariable < 8)
/*  43:    */     {
/*  44: 35 */       tScale = aTileEntity.getInputVoltage() * aTileEntity.getInputAmperage() / 15L;
/*  45: 36 */       if (tScale > 0L) {
/*  46: 37 */         aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(int)(aTileEntity.getAverageElectricInput() / tScale) : (byte)(int)(15L - aTileEntity.getAverageElectricInput() / tScale));
/*  47:    */       } else {
/*  48: 39 */         aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
/*  49:    */       }
/*  50:    */     }
/*  51: 41 */     else if (aCoverVariable < 10)
/*  52:    */     {
/*  53: 42 */       tScale = aTileEntity.getOutputVoltage() * aTileEntity.getOutputAmperage() / 15L;
/*  54: 43 */       if (tScale > 0L) {
/*  55: 44 */         aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(int)(aTileEntity.getAverageElectricOutput() / tScale) : (byte)(int)(15L - aTileEntity.getAverageElectricOutput() / tScale));
/*  56:    */       } else {
/*  57: 46 */         aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
/*  58:    */       }
/*  59:    */     }
/*  60: 49 */     return aCoverVariable;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/*  64:    */   {
/*  65: 54 */     aCoverVariable = (aCoverVariable + 1) % 10;
/*  66: 55 */     if (aCoverVariable == 0) {
/*  67: 55 */       GT_Utility.sendChatToPlayer(aPlayer, "Normal Universal Storage");
/*  68:    */     }
/*  69: 56 */     if (aCoverVariable == 1) {
/*  70: 56 */       GT_Utility.sendChatToPlayer(aPlayer, "Inverted Universal Storage");
/*  71:    */     }
/*  72: 57 */     if (aCoverVariable == 2) {
/*  73: 57 */       GT_Utility.sendChatToPlayer(aPlayer, "Normal Electricity Storage");
/*  74:    */     }
/*  75: 58 */     if (aCoverVariable == 3) {
/*  76: 58 */       GT_Utility.sendChatToPlayer(aPlayer, "Inverted Electricity Storage");
/*  77:    */     }
/*  78: 59 */     if (aCoverVariable == 4) {
/*  79: 59 */       GT_Utility.sendChatToPlayer(aPlayer, "Normal Steam Storage");
/*  80:    */     }
/*  81: 60 */     if (aCoverVariable == 5) {
/*  82: 60 */       GT_Utility.sendChatToPlayer(aPlayer, "Inverted Steam Storage");
/*  83:    */     }
/*  84: 61 */     if (aCoverVariable == 6) {
/*  85: 61 */       GT_Utility.sendChatToPlayer(aPlayer, "Normal Average Electric Input");
/*  86:    */     }
/*  87: 62 */     if (aCoverVariable == 7) {
/*  88: 62 */       GT_Utility.sendChatToPlayer(aPlayer, "Inverted Average Electric Input");
/*  89:    */     }
/*  90: 63 */     if (aCoverVariable == 8) {
/*  91: 63 */       GT_Utility.sendChatToPlayer(aPlayer, "Normal Average Electric Output");
/*  92:    */     }
/*  93: 64 */     if (aCoverVariable == 9) {
/*  94: 64 */       GT_Utility.sendChatToPlayer(aPlayer, "Inverted Average Electric Output");
/*  95:    */     }
/*  96: 65 */     return aCoverVariable;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 100:    */   {
/* 101: 70 */     return true;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 105:    */   {
/* 106: 75 */     return true;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 110:    */   {
/* 111: 80 */     return true;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 115:    */   {
/* 116: 85 */     return true;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 120:    */   {
/* 121: 90 */     return true;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 125:    */   {
/* 126: 95 */     return true;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 130:    */   {
/* 131:100 */     return true;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 135:    */   {
/* 136:105 */     return 5;
/* 137:    */   }
/* 138:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_EUMeter
 * JD-Core Version:    0.7.0.1
 */