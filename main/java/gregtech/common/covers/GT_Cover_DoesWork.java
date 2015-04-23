/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.interfaces.tileentity.IMachineProgress;
/*  5:   */ import gregtech.api.util.GT_CoverBehavior;
/*  6:   */ import gregtech.api.util.GT_Utility;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraftforge.fluids.Fluid;
/*  9:   */ 
/* 10:   */ public class GT_Cover_DoesWork
/* 11:   */   extends GT_CoverBehavior
/* 12:   */ {
/* 13:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 14:   */   {
/* 15:13 */     if ((aTileEntity instanceof IMachineProgress))
/* 16:   */     {
/* 17:14 */       if (aCoverVariable < 2)
/* 18:   */       {
/* 19:15 */         int tScale = ((IMachineProgress)aTileEntity).getMaxProgress() / 15;
/* 20:16 */         if ((tScale > 0) && (((IMachineProgress)aTileEntity).hasThingsToDo())) {
/* 21:17 */           aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable % 2 == 0 ? (byte)(((IMachineProgress)aTileEntity).getProgress() / tScale) : (byte)(15 - ((IMachineProgress)aTileEntity).getProgress() / tScale));
/* 22:   */         } else {
/* 23:19 */           aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable % 2 == 0 ? 0 : 15));
/* 24:   */         }
/* 25:   */       }
/* 26:   */       else
/* 27:   */       {
/* 28:22 */         aTileEntity.setOutputRedstoneSignal(aSide, (byte)((aCoverVariable % 2 == 0 ? 1 : 0) != (((IMachineProgress)aTileEntity).getMaxProgress() == 0 ? 1 : 0) ? 0 : 15));
/* 29:   */       }
/* 30:   */     }
/* 31:   */     else {
/* 32:25 */       aTileEntity.setOutputRedstoneSignal(aSide, (byte)0);
/* 33:   */     }
/* 34:27 */     return aCoverVariable;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 38:   */   {
/* 39:32 */     aCoverVariable = (aCoverVariable + 1) % 4;
/* 40:33 */     if (aCoverVariable == 0) {
/* 41:33 */       GT_Utility.sendChatToPlayer(aPlayer, "Normal");
/* 42:   */     }
/* 43:34 */     if (aCoverVariable == 1) {
/* 44:34 */       GT_Utility.sendChatToPlayer(aPlayer, "Inverted");
/* 45:   */     }
/* 46:35 */     if (aCoverVariable == 2) {
/* 47:35 */       GT_Utility.sendChatToPlayer(aPlayer, "Ready to work");
/* 48:   */     }
/* 49:36 */     if (aCoverVariable == 3) {
/* 50:36 */       GT_Utility.sendChatToPlayer(aPlayer, "Not ready to work");
/* 51:   */     }
/* 52:37 */     return aCoverVariable;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 56:   */   {
/* 57:42 */     return true;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 61:   */   {
/* 62:47 */     return true;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 66:   */   {
/* 67:52 */     return true;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 71:   */   {
/* 72:57 */     return true;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 76:   */   {
/* 77:62 */     return true;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 81:   */   {
/* 82:67 */     return true;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 86:   */   {
/* 87:72 */     return true;
/* 88:   */   }
/* 89:   */   
/* 90:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 91:   */   {
/* 92:77 */     return 5;
/* 93:   */   }
/* 94:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_DoesWork
 * JD-Core Version:    0.7.0.1
 */