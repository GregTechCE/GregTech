/*   1:    */ package gregtech.common.covers;
/*   2:    */ 
/*   3:    */ import gregtech.api.interfaces.tileentity.ICoverable;
/*   4:    */ import gregtech.api.interfaces.tileentity.IMachineProgress;
/*   5:    */ import gregtech.api.util.GT_CoverBehavior;
/*   6:    */ import gregtech.api.util.GT_Utility;
/*   7:    */ import net.minecraft.entity.player.EntityPlayer;
/*   8:    */ import net.minecraftforge.common.util.ForgeDirection;
/*   9:    */ import net.minecraftforge.fluids.Fluid;
/*  10:    */ import net.minecraftforge.fluids.FluidStack;
/*  11:    */ import net.minecraftforge.fluids.IFluidHandler;
/*  12:    */ 
/*  13:    */ public class GT_Cover_Pump
/*  14:    */   extends GT_CoverBehavior
/*  15:    */ {
/*  16:    */   public final int mTransferRate;
/*  17:    */   
/*  18:    */   public GT_Cover_Pump(int aTransferRate)
/*  19:    */   {
/*  20: 18 */     this.mTransferRate = aTransferRate;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/*  24:    */   {
/*  25: 23 */     if ((aCoverVariable % 6 > 1) && ((aTileEntity instanceof IMachineProgress))) {
/*  26: 23 */       if (((IMachineProgress)aTileEntity).isAllowedToWork() != aCoverVariable % 6 < 4) {
/*  27: 23 */         return aCoverVariable;
/*  28:    */       }
/*  29:    */     }
/*  30: 24 */     if ((aTileEntity instanceof IFluidHandler))
/*  31:    */     {
/*  32: 25 */       IFluidHandler tTank2 = aTileEntity.getITankContainerAtSide(aSide);
/*  33: 26 */       if (tTank2 != null)
/*  34:    */       {
/*  35: 27 */         aTileEntity.decreaseStoredEnergyUnits(GT_Utility.getTier(this.mTransferRate), true);
/*  36: 28 */         IFluidHandler tTank1 = (IFluidHandler)aTileEntity;
/*  37: 29 */         if (aCoverVariable % 2 == 0)
/*  38:    */         {
/*  39: 30 */           FluidStack tLiquid = tTank1.drain(ForgeDirection.getOrientation(aSide), this.mTransferRate, false);
/*  40: 31 */           if (tLiquid != null)
/*  41:    */           {
/*  42: 32 */             tLiquid = tLiquid.copy();
/*  43: 33 */             tLiquid.amount = tTank2.fill(ForgeDirection.getOrientation(aSide).getOpposite(), tLiquid, false);
/*  44: 34 */             if (tLiquid.amount > 0) {
/*  45: 35 */               if (((aCoverVariable % 2 != 1) || (aSide != 1)) && ((aCoverVariable % 2 != 0) || (aSide != 0)) && (aTileEntity.getUniversalEnergyCapacity() >= Math.min(1, tLiquid.amount / 10)))
/*  46:    */               {
/*  47: 36 */                 if (aTileEntity.isUniversalEnergyStored(Math.min(1, tLiquid.amount / 10)))
/*  48:    */                 {
/*  49: 37 */                   aTileEntity.decreaseStoredEnergyUnits(Math.min(1, tLiquid.amount / 10), true);
/*  50: 38 */                   tTank2.fill(ForgeDirection.getOrientation(aSide).getOpposite(), tTank1.drain(ForgeDirection.getOrientation(aSide), tLiquid.amount, true), true);
/*  51:    */                 }
/*  52:    */               }
/*  53:    */               else {
/*  54: 41 */                 tTank2.fill(ForgeDirection.getOrientation(aSide).getOpposite(), tTank1.drain(ForgeDirection.getOrientation(aSide), tLiquid.amount, true), true);
/*  55:    */               }
/*  56:    */             }
/*  57:    */           }
/*  58:    */         }
/*  59:    */         else
/*  60:    */         {
/*  61: 46 */           FluidStack tLiquid = tTank2.drain(ForgeDirection.getOrientation(aSide).getOpposite(), this.mTransferRate, false);
/*  62: 47 */           if (tLiquid != null)
/*  63:    */           {
/*  64: 48 */             tLiquid = tLiquid.copy();
/*  65: 49 */             tLiquid.amount = tTank1.fill(ForgeDirection.getOrientation(aSide), tLiquid, false);
/*  66: 50 */             if (tLiquid.amount > 0) {
/*  67: 51 */               if (((aCoverVariable % 2 != 1) || (aSide != 1)) && ((aCoverVariable % 2 != 0) || (aSide != 0)) && (aTileEntity.getUniversalEnergyCapacity() >= Math.min(1, tLiquid.amount / 10)))
/*  68:    */               {
/*  69: 52 */                 if (aTileEntity.isUniversalEnergyStored(Math.min(1, tLiquid.amount / 10)))
/*  70:    */                 {
/*  71: 53 */                   aTileEntity.decreaseStoredEnergyUnits(Math.min(1, tLiquid.amount / 10), true);
/*  72: 54 */                   tTank1.fill(ForgeDirection.getOrientation(aSide), tTank2.drain(ForgeDirection.getOrientation(aSide).getOpposite(), tLiquid.amount, true), true);
/*  73:    */                 }
/*  74:    */               }
/*  75:    */               else {
/*  76: 57 */                 tTank1.fill(ForgeDirection.getOrientation(aSide), tTank2.drain(ForgeDirection.getOrientation(aSide).getOpposite(), tLiquid.amount, true), true);
/*  77:    */               }
/*  78:    */             }
/*  79:    */           }
/*  80:    */         }
/*  81:    */       }
/*  82:    */     }
/*  83: 64 */     return aCoverVariable;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/*  87:    */   {
/*  88: 69 */     aCoverVariable = (aCoverVariable + 1) % 12;
/*  89: 70 */     if (aCoverVariable == 0) {
/*  90: 70 */       GT_Utility.sendChatToPlayer(aPlayer, "Export");
/*  91:    */     }
/*  92: 71 */     if (aCoverVariable == 1) {
/*  93: 71 */       GT_Utility.sendChatToPlayer(aPlayer, "Import");
/*  94:    */     }
/*  95: 72 */     if (aCoverVariable == 2) {
/*  96: 72 */       GT_Utility.sendChatToPlayer(aPlayer, "Export (conditional)");
/*  97:    */     }
/*  98: 73 */     if (aCoverVariable == 3) {
/*  99: 73 */       GT_Utility.sendChatToPlayer(aPlayer, "Import (conditional)");
/* 100:    */     }
/* 101: 74 */     if (aCoverVariable == 4) {
/* 102: 74 */       GT_Utility.sendChatToPlayer(aPlayer, "Export (invert cond)");
/* 103:    */     }
/* 104: 75 */     if (aCoverVariable == 5) {
/* 105: 75 */       GT_Utility.sendChatToPlayer(aPlayer, "Import (invert cond)");
/* 106:    */     }
/* 107: 76 */     if (aCoverVariable == 6) {
/* 108: 76 */       GT_Utility.sendChatToPlayer(aPlayer, "Export allow Input");
/* 109:    */     }
/* 110: 77 */     if (aCoverVariable == 7) {
/* 111: 77 */       GT_Utility.sendChatToPlayer(aPlayer, "Import allow Output");
/* 112:    */     }
/* 113: 78 */     if (aCoverVariable == 8) {
/* 114: 78 */       GT_Utility.sendChatToPlayer(aPlayer, "Export allow Input (conditional)");
/* 115:    */     }
/* 116: 79 */     if (aCoverVariable == 9) {
/* 117: 79 */       GT_Utility.sendChatToPlayer(aPlayer, "Import allow Output (conditional)");
/* 118:    */     }
/* 119: 80 */     if (aCoverVariable == 10) {
/* 120: 80 */       GT_Utility.sendChatToPlayer(aPlayer, "Export allow Input (invert cond)");
/* 121:    */     }
/* 122: 81 */     if (aCoverVariable == 11) {
/* 123: 81 */       GT_Utility.sendChatToPlayer(aPlayer, "Import allow Output (invert cond)");
/* 124:    */     }
/* 125: 82 */     return aCoverVariable;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 129:    */   {
/* 130: 87 */     return true;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 134:    */   {
/* 135: 92 */     return true;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 139:    */   {
/* 140: 97 */     return true;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 144:    */   {
/* 145:102 */     return true;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 149:    */   {
/* 150:107 */     return true;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 154:    */   {
/* 155:112 */     return true;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 159:    */   {
/* 160:117 */     if ((aCoverVariable > 1) && ((aTileEntity instanceof IMachineProgress))) {
/* 161:117 */       if (((IMachineProgress)aTileEntity).isAllowedToWork() != aCoverVariable % 6 < 4) {
/* 162:117 */         return false;
/* 163:    */       }
/* 164:    */     }
/* 165:118 */     return (aCoverVariable >= 6) || (aCoverVariable % 2 != 0);
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 169:    */   {
/* 170:123 */     if ((aCoverVariable > 1) && ((aTileEntity instanceof IMachineProgress))) {
/* 171:123 */       if (((IMachineProgress)aTileEntity).isAllowedToWork() != aCoverVariable % 6 < 4) {
/* 172:123 */         return false;
/* 173:    */       }
/* 174:    */     }
/* 175:124 */     return (aCoverVariable >= 6) || (aCoverVariable % 2 == 0);
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 179:    */   {
/* 180:129 */     return true;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 184:    */   {
/* 185:134 */     return 1;
/* 186:    */   }
/* 187:    */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Pump
 * JD-Core Version:    0.7.0.1
 */