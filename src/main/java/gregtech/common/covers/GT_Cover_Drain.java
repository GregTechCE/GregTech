package gregtech.common.covers;

import gregtech.api.enums.Materials;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IMachineProgress;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

public class GT_Cover_Drain
        extends GT_CoverBehavior {
    public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
        EnumFacing sideFacing = EnumFacing.VALUES[aSide];
        if ((aCoverVariable % 3 > 1) && ((aTileEntity instanceof IMachineProgress))) {
            if (((IMachineProgress) aTileEntity).isAllowedToWork() != aCoverVariable % 3 < 2) {
                return aCoverVariable;
            }
        }
        if (aSide != 6) {
            Block tBlock = aTileEntity.getBlockAtSide(aSide);
            if ((aCoverVariable < 3) && ((aTileEntity instanceof IFluidHandler))) {
                if ((aSide == 1) &&
                        (aTileEntity.getWorldObj().isRaining()) &&
                        (aTileEntity.getWorldObj().getPrecipitationHeight(aTileEntity.getWorldPos()).getY() - 2 < aTileEntity.getYCoord())) {
                    int tAmount = (int) (aTileEntity.getBiome().getRainfall() * 10.0F);
                    if (tAmount > 0) {
                        ((IFluidHandler) aTileEntity).fill(sideFacing, Materials.Water.getFluid(aTileEntity.getWorldObj().isThundering() ? tAmount * 2 : tAmount), true);
                    }
                }
                FluidStack tLiquid = null;
                if (tBlock != null) {
                    if (((tBlock == Blocks.WATER) || (tBlock == Blocks.FLOWING_WATER)) && (aTileEntity.getMetaIDAtSide(aSide) == 0)) {
                        tLiquid = Materials.Water.getFluid(1000L);
                    } else if (((tBlock == Blocks.LAVA) || (tBlock == Blocks.FLOWING_LAVA)) && (aTileEntity.getMetaIDAtSide(aSide) == 0)) {
                        tLiquid = Materials.Lava.getFluid(1000L);
                    } else if ((tBlock instanceof IFluidBlock)) {
                        tLiquid = ((IFluidBlock) tBlock).drain(aTileEntity.getWorldObj(), aTileEntity.getWorldPos().offset(sideFacing), false);
                    }
                    if ((tLiquid != null) && (tLiquid.getFluid() != null) && ((aSide > 1) || ((aSide == 0) && (tLiquid.getFluid().getDensity() <= 0)) || ((aSide == 1) && (tLiquid.getFluid().getDensity() >= 0))) &&
                            (((IFluidHandler) aTileEntity).fill(sideFacing, tLiquid, false) == tLiquid.amount)) {
                        ((IFluidHandler) aTileEntity).fill(sideFacing, tLiquid, true);
                        aTileEntity.getWorldObj().setBlockToAir(aTileEntity.getWorldPos().offset(sideFacing));
                    }
                }
            }
            if ((aCoverVariable >= 3) && (tBlock != null) && (tBlock instanceof IFluidBlock || tBlock instanceof BlockDynamicLiquid || tBlock instanceof BlockStaticLiquid)) {
                aTileEntity.getWorldObj().setBlockToAir(aTileEntity.getWorldPos().offset(sideFacing));
            }
        }
        return aCoverVariable;
    }

    public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        aCoverVariable = (aCoverVariable + (aPlayer.isSneaking()? -1 : 1)) % 6;
        if(aCoverVariable <0){aCoverVariable = 5;}
        switch(aCoverVariable) {
            case 0: GT_Utility.sendChatToPlayer(aPlayer, "Import"); break;
            case 1: GT_Utility.sendChatToPlayer(aPlayer, "Import (conditional)"); break;
            case 2: GT_Utility.sendChatToPlayer(aPlayer, "Import (invert cond)"); break;
            case 3: GT_Utility.sendChatToPlayer(aPlayer, "Keep Liquids Away"); break;
            case 4: GT_Utility.sendChatToPlayer(aPlayer, "Keep Liquids Away (conditional)"); break;
            case 5: GT_Utility.sendChatToPlayer(aPlayer, "Keep Liquids Away (invert cond)"); break;
        }
        return aCoverVariable;
    }

    public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
        return ((IMachineProgress) aTileEntity).isAllowedToWork() == aCoverVariable < 2;
    }

    public boolean alwaysLookConnected(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return true;
    }

    public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
        return aCoverVariable < 3 ? 50 : 1;
    }
}
