package gregtech.api.pipenet.tile;

import gregtech.api.block.BlockStateTileEntity;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.IPipeType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TileEntityPipeBase<PipeType extends Enum<PipeType> & IPipeType<NodeDataType>, NodeDataType> extends BlockStateTileEntity implements IPipeTile<PipeType, NodeDataType> {

    private int blockedConnections = 0;
    private int insulationColor = DEFAULT_INSULATION_COLOR;

    public TileEntityPipeBase() {
    }

    @Override
    public void transferDataFrom(IPipeTile<PipeType, NodeDataType> tileEntity) {
        this.blockedConnections = tileEntity.getBlockedConnections();
        this.insulationColor = tileEntity.getInsulationColor();
    }

    @Override
    public World getPipeWorld() {
        return getWorld();
    }

    @Override
    public BlockPos getPipePos() {
        return getPos();
    }

    @Override
    public BlockPipe<PipeType, NodeDataType, ?> getPipeBlock() {
        //noinspection unchecked
        return (BlockPipe<PipeType, NodeDataType, ?>) getBlockState().getBlock();
    }

    @Override
    public int getBlockedConnections() {
        return blockedConnections;
    }

    @Override
    public int getInsulationColor() {
        return insulationColor;
    }

    @Override
    public void setInsulationColor(int insulationColor) {
        this.insulationColor = insulationColor;
        if(!getWorld().isRemote) {
            getPipeBlock().getWorldPipeNet(getWorld()).updateMark(getPos(), getCableMark());
            updateClientState();
            markDirty();
        }
    }

    public void setBlockedConnection(EnumFacing side, boolean blocked) {
        if(blocked) {
            this.blockedConnections |= 1 << side.getIndex();
        } else {
            this.blockedConnections &= ~(1 << side.getIndex());
        }
        if(!getWorld().isRemote) {
            getPipeBlock().getWorldPipeNet(getWorld()).updateBlockedConnections(getPos(), side, blocked);
        }
    }

    @Override
    public PipeType getPipeType() {
        return getBlockState().getValue(getPipeBlock().pipeVariantProperty);
    }

    @Override
    public NodeDataType getNodeData() {
        return getPipeBlock().getProperties(getPipeType());
    }

    private int getCableMark() {
        return insulationColor == DEFAULT_INSULATION_COLOR ? 0 : insulationColor;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.blockedConnections = compound.getInteger("BlockedConnections");
        this.insulationColor = compound.getInteger("InsulationColor");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BlockedConnections", blockedConnections);
        compound.setInteger("InsulationColor", insulationColor);
        return compound;
    }

    ///////////////////////////////// SYNCING SHIT /////////////////////////

    public void updateClientState() {
        IBlockState blockState = getBlockState();
        //send only tile entity sync packet
        getWorld().notifyBlockUpdate(getPos(), blockState, blockState, 0b00010);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        handleUpdateTag(pkt.getNbtCompound());
        getWorld().markBlockRangeForRenderUpdate(getPos().add(-1, -1, -1), getPos().add(1, 1, 1));
    }

}
