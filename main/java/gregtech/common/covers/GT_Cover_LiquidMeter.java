/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.util.GT_CoverBehavior;
/*  5:   */ import gregtech.api.util.GT_Utility;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraftforge.common.util.ForgeDirection;
/*  8:   */ import net.minecraftforge.fluids.Fluid;
/*  9:   */ import net.minecraftforge.fluids.FluidStack;
/* 10:   */ import net.minecraftforge.fluids.FluidTankInfo;
/* 11:   */ import net.minecraftforge.fluids.IFluidHandler;
/* 12:   */ 
/* 13:   */ public class GT_Cover_LiquidMeter
/* 14:   */   extends GT_CoverBehavior
/* 15:   */ {
/* 16:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 17:   */   {
/* 18:16 */     if ((aTileEntity instanceof IFluidHandler))
/* 19:   */     {
/* 20:17 */       FluidTankInfo[] tTanks = ((IFluidHandler)aTileEntity).getTankInfo(ForgeDirection.UNKNOWN);
/* 21:18 */       long tAll = 0L;long tFull = 0L;
/* 22:19 */       if (tTanks != null) {
/* 23:19 */         for (FluidTankInfo tTank : tTanks) {
/* 24:19 */           if (tTank != null)
/* 25:   */           {
/* 26:20 */             tAll += tTank.capacity;
/* 27:21 */             FluidStack tLiquid = tTank.fluid;
/* 28:22 */             if (tLiquid != null) {
/* 29:23 */               tFull += tLiquid.amount;
/* 30:   */             }
/* 31:   */           }
/* 32:   */         }
/* 33:   */       }
/* 34:26 */       tAll /= 14L;
/* 35:27 */       if (tAll > 0L) {
/* 36:28 */         aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable == 0 ? 0 : tFull > 0L ? (byte)(int)(tFull / tAll + 1L) : (byte)(int)(15L - (tFull > 0L ? tFull / tAll + 1L : 0L)));
/* 37:   */       } else {
/* 38:30 */         aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable == 0 ? 0 : 15));
/* 39:   */       }
/* 40:   */     }
/* 41:   */     else
/* 42:   */     {
/* 43:33 */       aTileEntity.setOutputRedstoneSignal(aSide, (byte)0);
/* 44:   */     }
/* 45:35 */     return aCoverVariable;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 49:   */   {
/* 50:40 */     if (aCoverVariable == 0) {
/* 51:41 */       GT_Utility.sendChatToPlayer(aPlayer, "Inverted");
/* 52:   */     } else {
/* 53:43 */       GT_Utility.sendChatToPlayer(aPlayer, "Normal");
/* 54:   */     }
/* 55:44 */     return aCoverVariable == 0 ? 1 : 0;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 59:   */   {
/* 60:49 */     return true;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 64:   */   {
/* 65:54 */     return true;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 69:   */   {
/* 70:59 */     return true;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 74:   */   {
/* 75:64 */     return true;
/* 76:   */   }
/* 77:   */   
/* 78:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 79:   */   {
/* 80:69 */     return true;
/* 81:   */   }
/* 82:   */   
/* 83:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 84:   */   {
/* 85:74 */     return true;
/* 86:   */   }
/* 87:   */   
/* 88:   */   public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 89:   */   {
/* 90:79 */     return true;
/* 91:   */   }
/* 92:   */   
/* 93:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 94:   */   {
/* 95:84 */     return 5;
/* 96:   */   }
/* 97:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_LiquidMeter
 * JD-Core Version:    0.7.0.1
 */