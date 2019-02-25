package gregtech.api.pipenet.tile;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.metatileentity.SyncedTileEntityBase;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.IPipeType;
import gregtech.api.unification.material.type.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class TileEntityPipeBase<PipeType extends Enum<PipeType> & IPipeType<NodeDataType>, NodeDataType> extends SyncedTileEntityBase implements IPipeTile<PipeType, NodeDataType> {

    protected int blockedConnections = 0;
    protected int insulationColor = DEFAULT_INSULATION_COLOR;
    protected final PipeCoverableImplementation coverableImplementation = new PipeCoverableImplementation(this);
    private NodeDataType cachedNodeData;
    private Material pipeMaterial;
    private PipeType pipeType = getPipeTypeClass().getEnumConstants()[0];
    private boolean isBeingConverted;

    public TileEntityPipeBase() {
    }

    public boolean isBeingConverted() {
        return isBeingConverted;
    }

    public void setBeingConverted(boolean beingConverted) {
        isBeingConverted = beingConverted;
    }

    public void setPipeType(PipeType pipeType) {
        this.pipeType = pipeType;
    }

    @Override
    public void setPipeMaterial(Material pipeMaterial) {
        this.pipeMaterial = pipeMaterial;
    }

    @Override
    public void transferDataFrom(IPipeTile<PipeType, NodeDataType> tileEntity) {
        this.pipeType = tileEntity.getPipeType();
        this.pipeMaterial = tileEntity.getPipeMaterial();
        this.blockedConnections = tileEntity.getBlockedConnections();
        this.insulationColor = tileEntity.getInsulationColor();
        tileEntity.getCoverableImplementation().transferDataTo(coverableImplementation);
    }

    public abstract Class<PipeType> getPipeTypeClass();

    @Override
    public abstract boolean supportsTicking();

    @Override
    public World getPipeWorld() {
        return getWorld();
    }

    @Override
    public BlockPos getPipePos() {
        return getPos();
    }

    @Override
    public PipeCoverableImplementation getCoverableImplementation() {
        return coverableImplementation;
    }

    @Override
    public boolean canPlaceCoverOnSide(EnumFacing side) {
        return true;
    }

    @Override
    public IPipeTile<PipeType, NodeDataType> setSupportsTicking() {
        if(supportsTicking()) {
            return this;
        }
        //create new tickable tile entity, transfer data, and replace it
        IPipeTile<PipeType, NodeDataType> newTile = getPipeBlock().createNewTileEntity(true);
        newTile.transferDataFrom(this);
        getWorld().setTileEntity(getPos(), (TileEntity) newTile);
        return newTile;
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
            writeCustomData(-1, buffer -> buffer.writeInt(insulationColor));
            markDirty();
        }
    }

    public void setConnectionBlocked(EnumFacing side, boolean blocked) {
        if(blocked) {
            this.blockedConnections |= 1 << side.getIndex();
        } else {
            this.blockedConnections &= ~(1 << side.getIndex());
        }
        if(!getWorld().isRemote) {
            getPipeBlock().getWorldPipeNet(getWorld()).updateBlockedConnections(getPos(), side, blocked);
            writeCustomData(-2, buffer -> buffer.writeInt(blockedConnections));
            markDirty();
        }
    }

    @Override
    public PipeType getPipeType() {
        return pipeType;
    }

    @Override
    public Material getPipeMaterial() {
        return pipeMaterial;
    }

    @Override
    public NodeDataType getNodeData() {
        if(cachedNodeData == null) {
            this.cachedNodeData = getPipeBlock().createProperties(this);
        }
        return cachedNodeData;
    }

    private int getCableMark() {
        return insulationColor == DEFAULT_INSULATION_COLOR ? 0 : insulationColor;
    }

    public <T> T getCapabilityInternal(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == GregtechCapabilities.CAPABILITY_COVERABLE) {
            return GregtechCapabilities.CAPABILITY_COVERABLE.cast(getCoverableImplementation());
        }
        return super.getCapability(capability, facing);
    }

    @Nullable
    @Override
    public final <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        boolean isCoverable = capability == GregtechCapabilities.CAPABILITY_COVERABLE;
        CoverBehavior coverBehavior = facing == null ? null : coverableImplementation.getCoverAtSide(facing);
        T defaultValue = getCapabilityInternal(capability, facing);
        if(coverBehavior != null && !isCoverable) {
            return coverBehavior.getCapability(capability, defaultValue);
        }
        return defaultValue;
    }

    @Override
    public final boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return getCapability(capability, facing) != null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("PipeType", pipeType.ordinal());
        compound.setString("PipeMaterial", pipeMaterial.toString());
        compound.setInteger("BlockedConnections", blockedConnections);
        compound.setInteger("InsulationColor", insulationColor);
        this.coverableImplementation.writeToNBT(compound);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.pipeType = getPipeTypeClass().getEnumConstants()[compound.getInteger("PipeType")];
        this.pipeMaterial = Material.MATERIAL_REGISTRY.getObject(compound.getString("PipeMaterial"));
        this.blockedConnections = compound.getInteger("BlockedConnections");
        this.insulationColor = compound.getInteger("InsulationColor");
        this.coverableImplementation.readFromNBT(compound);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        buf.writeVarInt(pipeType.ordinal());
        buf.writeVarInt(Material.MATERIAL_REGISTRY.getIDForObject(pipeMaterial));
        buf.writeVarInt(blockedConnections);
        buf.writeInt(insulationColor);
        this.coverableImplementation.writeInitialSyncData(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        this.pipeType = getPipeTypeClass().getEnumConstants()[buf.readVarInt()];
        this.pipeMaterial = Material.MATERIAL_REGISTRY.getObjectById(buf.readVarInt());
        this.blockedConnections = buf.readVarInt();
        this.insulationColor = buf.readInt();
        this.coverableImplementation.readInitialSyncData(buf);
    }

    @Override
    public void receiveCustomData(int discriminator, PacketBuffer buf) {
        if(discriminator == -1) {
            this.insulationColor = buf.readInt();
            scheduleChunkForRenderUpdate();
        } else if(discriminator == -2) {
            this.blockedConnections = buf.readVarInt();
            scheduleChunkForRenderUpdate();
        } else if(discriminator == -3) {
            this.coverableImplementation.readCustomData(buf.readVarInt(), buf);
        }
    }

    @Override
    public void writeCoverCustomData(int id, Consumer<PacketBuffer> writer) {
        writeCustomData(-3, buffer -> {
            buffer.writeVarInt(id);
            writer.accept(buffer);
        });
    }

    @Override
    public void scheduleChunkForRenderUpdate() {
        BlockPos pos = getPos();
        getWorld().markBlockRangeForRenderUpdate(
            pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1,
            pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    @Override
    public void notifyBlockUpdate() {
        getWorld().notifyNeighborsOfStateChange(getPos(), getBlockType(), true);
        getPipeBlock().updateActiveNodeStatus(getWorld(), getPos());
    }

    @Override
    public void markAsDirty() {
        markDirty();
    }

    @Override
    public boolean isValidTile() {
        return !isInvalid();
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }
}
