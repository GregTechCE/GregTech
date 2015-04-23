/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  4:   */ import gregtech.api.util.GT_CoverBehavior;
/*  5:   */ import net.minecraft.world.World;
/*  6:   */ import net.minecraft.world.biome.BiomeGenBase;
/*  7:   */ 
/*  8:   */ public class GT_Cover_SolarPanel
/*  9:   */   extends GT_CoverBehavior
/* 10:   */ {
/* 11:   */   private final int mVoltage;
/* 12:   */   
/* 13:   */   public GT_Cover_SolarPanel(int aVoltage)
/* 14:   */   {
/* 15:11 */     this.mVoltage = aVoltage;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 19:   */   {
/* 20:16 */     if (aTimer % 100L == 0L) {
/* 21:17 */       if ((aSide != 1) || (aTileEntity.getWorld().isThundering()))
/* 22:   */       {
/* 23:18 */         aCoverVariable = 0;
/* 24:   */       }
/* 25:   */       else
/* 26:   */       {
/* 27:20 */         boolean bRain = (aTileEntity.getWorld().isRaining()) && (aTileEntity.getBiome().rainfall > 0.0F);
/* 28:21 */         aCoverVariable = ((!bRain) || (aTileEntity.getWorld().skylightSubtracted < 4)) && (aTileEntity.getSkyAtSide(aSide)) ? 1 : (bRain) || (!aTileEntity.getWorld().isDaytime()) ? 2 : 0;
/* 29:   */       }
/* 30:   */     }
/* 31:24 */     if ((aCoverVariable == 1) || ((aCoverVariable == 2) && (aTimer % 8L == 0L))) {
/* 32:24 */       aTileEntity.injectEnergyUnits((byte)6, this.mVoltage, 1L);
/* 33:   */     }
/* 34:25 */     return aCoverVariable;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* 38:   */   {
/* 39:30 */     return 1;
/* 40:   */   }
/* 41:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_SolarPanel
 * JD-Core Version:    0.7.0.1
 */