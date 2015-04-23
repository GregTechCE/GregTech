/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.util.GT_CoverBehavior;
/*  5:   */ import net.minecraft.entity.player.EntityPlayer;
/*  6:   */ import net.minecraftforge.fluids.Fluid;
/*  7:   */ 
/*  8:   */ public class GT_Cover_Screen
/*  9:   */   extends GT_CoverBehavior
/* 10:   */ {
/* 11:   */   public float getBlastProofLevel(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 12:   */   {
/* 13:11 */     return 20.0F;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean letsRedstoneGoIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 17:   */   {
/* 18:16 */     return false;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean letsRedstoneGoOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 22:   */   {
/* 23:21 */     return false;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 27:   */   {
/* 28:26 */     return false;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 32:   */   {
/* 33:31 */     return false;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 37:   */   {
/* 38:36 */     return false;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 42:   */   {
/* 43:41 */     return false;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 47:   */   {
/* 48:46 */     return false;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity)
/* 52:   */   {
/* 53:51 */     return false;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public boolean isGUIClickable(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 57:   */   {
/* 58:56 */     return true;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 62:   */   {
/* 63:61 */     return false;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 67:   */   {
/* 68:66 */     return false;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public boolean onCoverRemoval(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, boolean aForced)
/* 72:   */   {
/* 73:71 */     return true;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 77:   */   {
/* 78:76 */     return 0;
/* 79:   */   }
/* 80:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Screen
 * JD-Core Version:    0.7.0.1
 */