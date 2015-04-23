/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.util.GT_CoverBehavior;
/*  5:   */ import gregtech.api.util.GT_Utility;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraftforge.fluids.Fluid;
/*  8:   */ 
/*  9:   */ public class GT_Cover_RedstoneConductor
/* 10:   */   extends GT_CoverBehavior
/* 11:   */ {
/* 12:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 13:   */   {
/* 14:12 */     if (aCoverVariable == 0) {
/* 15:12 */       aTileEntity.setOutputRedstoneSignal(aSide, aTileEntity.getStrongestRedstone());
/* 16:13 */     } else if (aCoverVariable < 7) {
/* 17:13 */       aTileEntity.setOutputRedstoneSignal(aSide, aTileEntity.getInternalInputRedstoneSignal((byte)(aCoverVariable - 1)));
/* 18:   */     }
/* 19:15 */     return aCoverVariable;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 23:   */   {
/* 24:20 */     aCoverVariable = (aCoverVariable + 1) % 7;
/* 25:21 */     switch (aCoverVariable)
/* 26:   */     {
/* 27:   */     case 0: 
/* 28:22 */       GT_Utility.sendChatToPlayer(aPlayer, "Conducts strongest Input"); break;
/* 29:   */     case 1: 
/* 30:23 */       GT_Utility.sendChatToPlayer(aPlayer, "Conducts from bottom Input"); break;
/* 31:   */     case 2: 
/* 32:24 */       GT_Utility.sendChatToPlayer(aPlayer, "Conducts from top Input"); break;
/* 33:   */     case 3: 
/* 34:25 */       GT_Utility.sendChatToPlayer(aPlayer, "Conducts from north Input"); break;
/* 35:   */     case 4: 
/* 36:26 */       GT_Utility.sendChatToPlayer(aPlayer, "Conducts from south Input"); break;
/* 37:   */     case 5: 
/* 38:27 */       GT_Utility.sendChatToPlayer(aPlayer, "Conducts from west Input"); break;
/* 39:   */     case 6: 
/* 40:28 */       GT_Utility.sendChatToPlayer(aPlayer, "Conducts from east Input");
/* 41:   */     }
/* 42:30 */     return aCoverVariable;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 46:   */   {
/* 47:35 */     return true;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 51:   */   {
/* 52:40 */     return true;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 56:   */   {
/* 57:45 */     return true;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 61:   */   {
/* 62:50 */     return true;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 66:   */   {
/* 67:55 */     return true;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 71:   */   {
/* 72:60 */     return true;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 76:   */   {
/* 77:65 */     return true;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 81:   */   {
/* 82:70 */     return 1;
/* 83:   */   }
/* 84:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_RedstoneConductor
 * JD-Core Version:    0.7.0.1
 */