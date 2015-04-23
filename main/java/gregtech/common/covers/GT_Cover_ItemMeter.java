/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.util.GT_CoverBehavior;
/*  5:   */ import gregtech.api.util.GT_Utility;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.item.ItemStack;
/*  8:   */ import net.minecraftforge.fluids.Fluid;
/*  9:   */ 
/* 10:   */ public class GT_Cover_ItemMeter
/* 11:   */   extends GT_CoverBehavior
/* 12:   */ {
/* 13:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 14:   */   {
/* 15:   */     int[] tSlots;
/* 17:14 */     if (aCoverVariable < 2) {
/* 18:15 */       tSlots = aTileEntity.getAccessibleSlotsFromSide(aSide);
/* 19:   */     } else {
/* 20:17 */       tSlots = new int[] { aCoverVariable - 2 };
/* 21:   */     }
/* 22:19 */     int tAll = 0;int tFull = 0;
/* 23:20 */     for (int i : tSlots) {
/* 24:21 */       if ((i > 0) && (i < aTileEntity.getSizeInventory()))
/* 25:   */       {
/* 26:22 */         tAll += 64;
/* 27:23 */         ItemStack tStack = aTileEntity.getStackInSlot(i);
/* 28:24 */         if (tStack != null) {
/* 29:25 */           tFull += tStack.stackSize * 64 / tStack.getMaxStackSize();
/* 30:   */         }
/* 31:   */       }
/* 32:   */     }
/* 33:29 */     tAll /= 14;
/* 34:30 */     if (tAll > 0) {
/* 35:31 */       aTileEntity.setOutputRedstoneSignal(aSide, aCoverVariable != 1 ? 0 : tFull > 0 ? (byte)(tFull / tAll + 1) : (byte)(15 - (tFull > 0 ? tFull / tAll + 1 : 0)));
/* 36:   */     } else {
/* 37:33 */       aTileEntity.setOutputRedstoneSignal(aSide, (byte)(aCoverVariable != 1 ? 0 : 15));
/* 38:   */     }
/* 39:35 */     return aCoverVariable;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 43:   */   {
/* 44:40 */     aCoverVariable = (aCoverVariable + 1) % (2 + aTileEntity.getSizeInventory());
/* 45:41 */     if (aCoverVariable == 0) {
/* 46:41 */       GT_Utility.sendChatToPlayer(aPlayer, "Normal");
/* 47:42 */     } else if (aCoverVariable == 1) {
/* 48:42 */       GT_Utility.sendChatToPlayer(aPlayer, "Inverted");
/* 49:   */     } else {
/* 50:43 */       GT_Utility.sendChatToPlayer(aPlayer, "Slot: " + (aCoverVariable - 2));
/* 51:   */     }
/* 52:44 */     return aCoverVariable;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 56:   */   {
/* 57:49 */     return true;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 61:   */   {
/* 62:54 */     return true;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 66:   */   {
/* 67:59 */     return true;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 71:   */   {
/* 72:64 */     return true;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 76:   */   {
/* 77:69 */     return true;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 81:   */   {
/* 82:74 */     return true;
/* 83:   */   }
/* 84:   */   
/* 85:   */   public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 86:   */   {
/* 87:79 */     return true;
/* 88:   */   }
/* 89:   */   
/* 90:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 91:   */   {
/* 92:84 */     return 5;
/* 93:   */   }
/* 94:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_ItemMeter
 * JD-Core Version:    0.7.0.1
 */