/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.interfaces.tileentity.IMachineProgress;
/*  5:   */ import gregtech.api.util.GT_CoverBehavior;
/*  6:   */ import gregtech.api.util.GT_Utility;
/*  7:   */ import net.minecraft.entity.player.EntityPlayer;
/*  8:   */ import net.minecraftforge.fluids.Fluid;
/*  9:   */ 
/* 10:   */ public class GT_Cover_ControlsWork
/* 11:   */   extends GT_CoverBehavior
/* 12:   */ {
/* 13:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 14:   */   {
    if(aTileEntity instanceof IMachineProgress)
    {
        if((aInputRedstone > 0) == (aCoverVariable == 0) && aCoverVariable != 2)
            ((IMachineProgress)aTileEntity).enableWorking();
        else
            ((IMachineProgress)aTileEntity).disableWorking();
        ((IMachineProgress)aTileEntity).setWorkDataValue(aInputRedstone);
    }
    return aCoverVariable;
}
/* 30:   */   
/* 31:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 32:   */   {
/* 33:22 */     return true;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 37:   */   {
/* 38:27 */     return true;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 42:   */   {
/* 43:32 */     return true;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 47:   */   {
/* 48:37 */     return true;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 52:   */   {
/* 53:42 */     return true;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 57:   */   {
/* 58:47 */     return true;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public boolean onCoverRemoval(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, boolean aForced)
/* 62:   */   {
/* 63:52 */     if ((aTileEntity instanceof IMachineProgress))
/* 64:   */     {
/* 65:53 */       ((IMachineProgress)aTileEntity).enableWorking();
/* 66:54 */       ((IMachineProgress)aTileEntity).setWorkDataValue((byte)0);
/* 67:   */     }
/* 68:56 */     return true;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 72:   */   {
/* 73:61 */     aCoverVariable = (aCoverVariable + 1) % 3;
/* 74:62 */     if (aCoverVariable == 0) {
/* 75:62 */       GT_Utility.sendChatToPlayer(aPlayer, "Normal");
/* 76:   */     }
/* 77:63 */     if (aCoverVariable == 1) {
/* 78:63 */       GT_Utility.sendChatToPlayer(aPlayer, "Inverted");
/* 79:   */     }
/* 80:64 */     if (aCoverVariable == 2) {
/* 81:64 */       GT_Utility.sendChatToPlayer(aPlayer, "No Work at all");
/* 82:   */     }
/* 83:65 */     return aCoverVariable;
/* 84:   */   }
/* 85:   */   
/* 86:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 87:   */   {
/* 88:70 */     return 1;
/* 89:   */   }
/* 90:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_ControlsWork
 * JD-Core Version:    0.7.0.1
 */