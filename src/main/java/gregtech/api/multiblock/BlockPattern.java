package gregtech.api.multiblock;

import com.google.common.base.Predicate;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class BlockPattern {

    private final Predicate<BlockWorldState>[][][] blockMatches;
    private final int fingerLength;
    private final int thumbLength;
    private final int palmLength;

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

    public boolean checkPatternAt(World world, BlockPos pos, EnumFacing finger, EnumFacing thumb) {
        BlockWorldState worldState = new BlockWorldState();
        MutableBlockPos blockPos = new MutableBlockPos();
        PatternMatchContext matchContext = new PatternMatchContext();
        for (int i = 0; i < this.palmLength; ++i) {
            for (int j = 0; j < this.thumbLength; ++j) {
                for (int k = 0; k < this.fingerLength; ++k) {
                    Predicate<BlockWorldState> predicate = this.blockMatches[k][j][i];
                    blockPos.setPos(pos);
                    translateOffset(blockPos, finger, thumb, i, j, k);
                    worldState.update(world, blockPos, matchContext);
                    if (!predicate.apply(worldState)) return false;
                }
            }
        }
        return true;
    }

    /**
     * Offsets the position of pos in the direction of finger and thumb facing by offset amounts, follows the right-hand
     * rule for cross products (finger, thumb, palm) @return A new BlockPos offset in the facing directions
     */
    protected static void translateOffset(MutableBlockPos pos, EnumFacing finger, EnumFacing thumb, int palmOffset, int thumbOffset, int fingerOffset) {
        if (finger != thumb && finger != thumb.getOpposite()) {
            int fingerX = finger.getFrontOffsetX();
            int fingerY = finger.getFrontOffsetY();
            int fingerZ = finger.getFrontOffsetZ();
            int thumbX = thumb.getFrontOffsetX();
            int thumbY = thumb.getFrontOffsetY();
            int thumbZ = thumb.getFrontOffsetZ();
            int crossX = fingerY * thumbZ - fingerZ * thumbY;
            int crossY = fingerZ * thumbX - fingerX * thumbZ;
            int crossZ = fingerX * thumbY - fingerY * thumbX;
            pos.setPos(pos.getX() + thumbX * -thumbOffset + crossX * palmOffset + fingerX * fingerOffset,
                pos.getY() + thumbY * -thumbOffset + crossY * palmOffset + fingerY * fingerOffset,
                pos.getZ() + thumbZ * -thumbOffset + crossZ * palmOffset + fingerZ * fingerOffset);
        } else {
            throw new IllegalArgumentException("Invalid forwards & up combination");
        }
    }

}
