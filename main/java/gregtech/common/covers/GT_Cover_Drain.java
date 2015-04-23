/*  1:   */ package gregtech.common.covers;
/*  2:   */ 
/*  3:   */ import gregtech.api.enums.Materials;
/*  4:   */ import gregtech.api.interfaces.tileentity.ICoverable;
/*  5:   */ import gregtech.api.interfaces.tileentity.IMachineProgress;
/*  6:   */ import gregtech.api.util.GT_CoverBehavior;
/*  7:   */ import gregtech.api.util.GT_Utility;
/*  8:   */ import net.minecraft.block.Block;
/*  9:   */ import net.minecraft.entity.player.EntityPlayer;
/* 10:   */ import net.minecraft.init.Blocks;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ import net.minecraft.world.biome.BiomeGenBase;
/* 13:   */ import net.minecraftforge.common.util.ForgeDirection;
/* 14:   */ import net.minecraftforge.fluids.Fluid;
/* 15:   */ import net.minecraftforge.fluids.FluidStack;
/* 16:   */ import net.minecraftforge.fluids.IFluidBlock;
/* 17:   */ import net.minecraftforge.fluids.IFluidHandler;
/* 18:   */ 
/* 19:   */ public class GT_Cover_Drain
/* 20:   */   extends GT_CoverBehavior
/* 21:   */ {
/* 22:   */   public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer)
/* 23:   */   {
/* 24:20 */     if ((aCoverVariable % 3 > 1) && ((aTileEntity instanceof IMachineProgress))) {
/* 25:20 */       if (((IMachineProgress)aTileEntity).isAllowedToWork() != aCoverVariable % 3 < 2) {
/* 26:20 */         return aCoverVariable;
/* 27:   */       }
/* 28:   */     }
/* 29:21 */     if (aSide != 6)
/* 30:   */     {
/* 31:22 */       Block tBlock = aTileEntity.getBlockAtSide(aSide);
/* 32:23 */       if ((aCoverVariable < 3) && ((aTileEntity instanceof IFluidHandler)))
/* 33:   */       {
/* 34:24 */         if ((aSide == 1) && 
/* 35:25 */           (aTileEntity.getWorld().isRaining()) && 
/* 36:26 */           (aTileEntity.getWorld().getPrecipitationHeight(aTileEntity.getXCoord(), aTileEntity.getZCoord()) - 2 < aTileEntity.getYCoord()))
/* 37:   */         {
/* 38:27 */           int tAmount = (int)(aTileEntity.getBiome().rainfall * 10.0F);
/* 39:28 */           if (tAmount > 0) {
/* 40:29 */             ((IFluidHandler)aTileEntity).fill(ForgeDirection.getOrientation(aSide), Materials.Water.getFluid(aTileEntity.getWorld().isThundering() ? tAmount * 2 : tAmount), true);
/* 41:   */           }
/* 42:   */         }
/* 43:34 */         FluidStack tLiquid = null;
/* 44:35 */         if (tBlock != null)
/* 45:   */         {
/* 46:36 */           if (((tBlock == Blocks.water) || (tBlock == Blocks.flowing_water)) && (aTileEntity.getMetaIDAtSide(aSide) == 0)) {
/* 47:37 */             tLiquid = Materials.Water.getFluid(1000L);
/* 48:38 */           } else if (((tBlock == Blocks.lava) || (tBlock == Blocks.flowing_lava)) && (aTileEntity.getMetaIDAtSide(aSide) == 0)) {
/* 49:39 */             tLiquid = Materials.Lava.getFluid(1000L);
/* 50:40 */           } else if ((tBlock instanceof IFluidBlock)) {
/* 51:41 */             tLiquid = ((IFluidBlock)tBlock).drain(aTileEntity.getWorld(), aTileEntity.getOffsetX(aSide, 1), aTileEntity.getOffsetY(aSide, 1), aTileEntity.getOffsetZ(aSide, 1), false);
/* 52:   */           }
/* 53:43 */           if ((tLiquid != null) && (tLiquid.getFluid() != null) && ((aSide > 1) || ((aSide == 0) && (tLiquid.getFluid().getDensity() <= 0)) || ((aSide == 1) && (tLiquid.getFluid().getDensity() >= 0))) && 
/* 54:44 */             (((IFluidHandler)aTileEntity).fill(ForgeDirection.getOrientation(aSide), tLiquid, false) == tLiquid.amount))
/* 55:   */           {
/* 56:45 */             ((IFluidHandler)aTileEntity).fill(ForgeDirection.getOrientation(aSide), tLiquid, true);
/* 57:46 */             aTileEntity.getWorld().setBlockToAir(aTileEntity.getXCoord() + ForgeDirection.getOrientation(aSide).offsetX, aTileEntity.getYCoord() + ForgeDirection.getOrientation(aSide).offsetY, aTileEntity.getZCoord() + ForgeDirection.getOrientation(aSide).offsetZ);
/* 58:   */           }
/* 59:   */         }
/* 60:   */       }
/* 61:51 */       if ((aCoverVariable >= 3) && (tBlock != null) && (
/* 62:52 */         (tBlock == Blocks.lava) || (tBlock == Blocks.flowing_lava) || (tBlock == Blocks.water) || (tBlock == Blocks.flowing_water) || ((tBlock instanceof IFluidBlock)))) {
/* 63:53 */         aTileEntity.getWorld().setBlock(aTileEntity.getOffsetX(aSide, 1), aTileEntity.getOffsetY(aSide, 1), aTileEntity.getOffsetZ(aSide, 1), Blocks.air, 0, 0);
/* 64:   */       }
/* 65:   */     }
/* 66:57 */     return aCoverVariable;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ)
/* 70:   */   {
/* 71:62 */     aCoverVariable = (aCoverVariable + 1) % 6;
/* 72:63 */     if (aCoverVariable == 0) {
/* 73:63 */       GT_Utility.sendChatToPlayer(aPlayer, "Import");
/* 74:   */     }
/* 75:64 */     if (aCoverVariable == 1) {
/* 76:64 */       GT_Utility.sendChatToPlayer(aPlayer, "Import (conditional)");
/* 77:   */     }
/* 78:65 */     if (aCoverVariable == 2) {
/* 79:65 */       GT_Utility.sendChatToPlayer(aPlayer, "Import (invert cond)");
/* 80:   */     }
/* 81:66 */     if (aCoverVariable == 3) {
/* 82:66 */       GT_Utility.sendChatToPlayer(aPlayer, "Keep Liquids Away");
/* 83:   */     }
/* 84:67 */     if (aCoverVariable == 4) {
/* 85:67 */       GT_Utility.sendChatToPlayer(aPlayer, "Keep Liquids Away (conditional)");
/* 86:   */     }
/* 87:68 */     if (aCoverVariable == 5) {
/* 88:68 */       GT_Utility.sendChatToPlayer(aPlayer, "Keep Liquids Away (invert cond)");
/* 89:   */     }
/* 90:69 */     return aCoverVariable;
/* 91:   */   }
/* 92:   */   
/* 93:   */   public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity)
/* 94:   */   {
/* 95:74 */     if ((aCoverVariable > 1) && ((aTileEntity instanceof IMachineProgress))) {}
/* 96:74 */     return ((IMachineProgress)aTileEntity).isAllowedToWork() == aCoverVariable < 2;
/* 97:   */   }
/* 98:   */   
/* 99:   */   public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* :0:   */   {
/* :1:79 */     return true;
/* :2:   */   }
/* :3:   */   
/* :4:   */   public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity)
/* :5:   */   {
/* :6:84 */     return aCoverVariable < 3 ? 50 : 1;
/* :7:   */   }
/* :8:   */ }


/* Location:           F:\Torrent\minecraft\jd-gui-0.3.6.windows\gregtech_1.7.10-5.07.07-dev.jar
 * Qualified Name:     gregtech.common.covers.GT_Cover_Drain
 * JD-Core Version:    0.7.0.1
 */