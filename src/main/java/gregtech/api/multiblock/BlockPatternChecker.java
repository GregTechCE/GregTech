package gregtech.api.multiblock;

import codechicken.lib.vec.Vector3;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;

public class BlockPatternChecker {
    
    public static BlockPos getPatternErrorPos(MultiblockControllerBase controllerBase) {
        BlockPattern structurePattern = controllerBase.getStructurePattern();
        if(structurePattern != null && structurePattern.checkPatternAt(controllerBase.getWorld(), controllerBase.getPos(), controllerBase.getFrontFacing().getOpposite()) == null) {
            return new BlockPos(structurePattern.getBlockPos());
        }
        return null;
    }

    public static PatternMatchContext checkPatternAt(MultiblockControllerBase controllerBase) {
        BlockPattern structurePattern = controllerBase.getStructurePattern();
        return structurePattern == null ? null : structurePattern.checkPatternAt(controllerBase.getWorld(), controllerBase.getPos(), controllerBase.getFrontFacing().getOpposite());
    }

    public static EnumFacing getSpin(MultiblockControllerBase controllerBase) {
        return EnumFacing.NORTH;
    }

    public static BlockPos getActualPos(EnumFacing ref, EnumFacing facing, EnumFacing spin, int x, int y, int z) {
        Vector3 vector3 = new Vector3(x, y, z);
        double degree = Math.PI/2 * (spin == EnumFacing.EAST? 1: spin == EnumFacing.SOUTH? 2: spin == EnumFacing.WEST? -1:0);
        if (ref != facing) {
            if (facing.getAxis() != EnumFacing.Axis.Y) {
                vector3.rotate(Math.PI/2 * ((4 + facing.getHorizontalIndex() - ref.getHorizontalIndex()) % 4), new Vector3(0, -1, 0));
            } else {
                vector3.rotate(-Math.PI/2 * facing.getFrontOffsetY(), new Vector3(-ref.rotateY().getFrontOffsetX(), 0, -ref.rotateY().getFrontOffsetZ()));
                degree = facing.getFrontOffsetY() * Math.PI/2 * ((4 + spin.getHorizontalIndex() - (facing.getFrontOffsetY() > 0 ? ref.getOpposite() : ref).getHorizontalIndex()) % 4);
            }
        }
        vector3.rotate(degree, new Vector3(-facing.getFrontOffsetX(), -facing.getFrontOffsetY(), -facing.getFrontOffsetZ()));
        return new BlockPos(Math.round(vector3.x), Math.round(vector3.y), Math.round(vector3.z));
    }

    public static EnumFacing getActualFrontFacing(EnumFacing ref, EnumFacing facing, EnumFacing spin, EnumFacing frontFacing) {
        BlockPos pos = getActualPos(ref, facing, spin, frontFacing.getFrontOffsetX(), frontFacing.getFrontOffsetY(), frontFacing.getFrontOffsetZ());
        return pos.getX() < 0 ? EnumFacing.WEST : pos.getX() > 0 ? EnumFacing.EAST
                : pos.getY() < 0 ? EnumFacing.DOWN : pos.getY() > 0 ? EnumFacing.UP
                : pos.getZ() < 0 ? EnumFacing.NORTH : EnumFacing.SOUTH;
    }

}
