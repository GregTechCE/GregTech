package gregtech.api.multiblock;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class BlockWorldState {

    private World world;
    private BlockPos pos;
    private IBlockState state;
    private TileEntity tileEntity;
    private boolean tileEntityInitialized;
    private PatternMatchContext matchContext;
    private PatternMatchContext layerContext;

    public static IPatternCenterPredicate wrap(Predicate<BlockWorldState> predicate) {
        return predicate::test;
    }

    public void update(World worldIn, BlockPos posIn, PatternMatchContext matchContext, PatternMatchContext layerContext) {
        this.world = worldIn;
        this.pos = posIn;
        this.state = null;
        this.tileEntity = null;
        this.tileEntityInitialized = false;
        this.matchContext = matchContext;
        this.layerContext = layerContext;
    }

    public PatternMatchContext getMatchContext() {
        return matchContext;
    }

    public PatternMatchContext getLayerContext() {
        return layerContext;
    }

    public IBlockState getBlockState() {
        if (this.state == null) {
            this.state = this.world.getBlockState(this.pos);
        }

        return this.state;
    }

    @Nullable
    public TileEntity getTileEntity() {
        if (this.tileEntity == null && !this.tileEntityInitialized) {
            this.tileEntity = this.world.getTileEntity(this.pos);
            this.tileEntityInitialized = true;
        }

        return this.tileEntity;
    }

    public BlockPos getPos() {
        return this.pos.toImmutable();
    }

    public IBlockState getOffsetState(EnumFacing face) {
        if (pos instanceof MutableBlockPos) {
            ((MutableBlockPos) pos).move(face);
            IBlockState blockState = world.getBlockState(pos);
            ((MutableBlockPos) pos).move(face.getOpposite());
            return blockState;
        }
        return world.getBlockState(this.pos.offset(face));
    }

    public World getWorld() {
        return world;
    }
}
