package gregtech.api.multiblock;

import net.minecraft.block.BlockColored;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Predicate;

public class BlockPattern {

    private final Predicate<BlockWorldState>[][][] blockMatches; //[z][y][x]
    private final int fingerLength; //z size
    private final int thumbLength; //y size
    private final int palmLength; //x size

    public BlockPattern(Predicate<BlockWorldState>[][][] predicatesIn) {
        this.blockMatches = predicatesIn;
        this.fingerLength = predicatesIn.length;

        if (this.fingerLength > 0) {
            this.thumbLength = predicatesIn[0].length;

            if (this.thumbLength > 0) {
                this.palmLength = predicatesIn[0][0].length;
            } else {
                this.palmLength = 0;
            }
        } else {
            this.thumbLength = 0;
            this.palmLength = 0;
        }
    }

    public int getFingerLength() {
        return this.fingerLength;
    }

    public int getThumbLength() {
        return this.thumbLength;
    }

    public int getPalmLength() {
        return this.palmLength;
    }

    public PatternMatchContext checkPatternAt(World world, double centerX, int floorY, double centerZ, EnumFacing facing) {
        int cornerX = (int) (centerX - (palmLength / 2.0));
        int cornerZ = (int) (centerZ - (fingerLength / 2.0));
        BlockWorldState worldState = new BlockWorldState();
        MutableBlockPos blockPos = new MutableBlockPos();
        PatternMatchContext matchContext = new PatternMatchContext();
        for (int x = 0; x < this.palmLength; x++) {
            for (int y = 0; y < this.thumbLength; y++) {
                for (int z = 0; z < this.fingerLength; z++) {
                    Predicate<BlockWorldState> predicate = this.blockMatches[z][y][x];
                    blockPos.setPos(x, y, z);
                    rotate(blockPos, facing, palmLength - 1, fingerLength - 1);
                    blockPos.setPos(blockPos.getX() + cornerX, blockPos.getY() + floorY, blockPos.getZ() + cornerZ);
                    //world.setBlockState(blockPos,
                    //    Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.values()[new Random(predicate.hashCode()).nextInt(15)]));
                    worldState.update(world, blockPos, matchContext);
                    if (!predicate.test(worldState)) {
                        return null;
                    }
                }
            }
        }
        return matchContext;
    }

    private static MutableBlockPos rotate(MutableBlockPos pos, EnumFacing facing, int xSize, int zSize) {
        switch (facing) {
            case NORTH: return pos;
            case SOUTH: return pos.setPos(xSize - pos.getX(), pos.getY(), zSize - pos.getZ());
            case WEST: return pos.setPos(pos.getZ(), pos.getY(), xSize - pos.getX());
            case EAST: return pos.setPos(zSize - pos.getZ(), pos.getY(), pos.getX());
            default: throw new IllegalArgumentException("Can rotate only horizontally");
        }
    }

}
