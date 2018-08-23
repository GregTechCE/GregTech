package gregtech.api.metatileentity;

import com.google.common.base.Preconditions;
import gregtech.api.GregTechAPI;
import gregtech.api.gui.IUIHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MetaTileEntityHolder extends TickableTileEntityBase implements IUIHolder {

    private MetaTileEntity metaTileEntity;

    public MetaTileEntity getMetaTileEntity() {
        return metaTileEntity;
    }

    /**
     * Sets this holder's current meta tile entity to copy of given one
     * Note that this method copies given meta tile entity and returns actual instance
     * so it is safe to call it on sample meta tile entities
     */
    public MetaTileEntity setMetaTileEntity(MetaTileEntity sampleMetaTileEntity) {
        Preconditions.checkNotNull(sampleMetaTileEntity, "metaTileEntity");
        this.metaTileEntity = sampleMetaTileEntity.createMetaTileEntity(this);
        this.metaTileEntity.holder = this;
        if(hasWorld() && !getWorld().isRemote) {
            writeCustomData(-100000, buffer -> {
                buffer.writeString(metaTileEntity.metaTileEntityId);
                metaTileEntity.writeInitialSyncData(buffer);
            });
            //just to update neighbours so cables and other things will work properly
            world.neighborChanged(getPos(), getBlockType(), getPos());
            markDirty();
        }
        return metaTileEntity;
    }

    public void scheduleChunkForRenderUpdate() {
        BlockPos pos = getPos();
        getWorld().markBlockRangeForRenderUpdate(
            pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1,
            pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("MetaId", NBT.TAG_STRING)) {
            String metaTileEntityId = compound.getString("MetaId");
            MetaTileEntity sampleMetaTileEntity = GregTechAPI.META_TILE_ENTITY_REGISTRY.getObject(metaTileEntityId);
            NBTTagCompound metaTileEntityData = compound.getCompoundTag("MetaTileEntity");
            if (sampleMetaTileEntity != null) {
                this.metaTileEntity = sampleMetaTileEntity.createMetaTileEntity(this);
                this.metaTileEntity.holder = this;
                this.metaTileEntity.readFromNBT(metaTileEntityData);
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if(metaTileEntity != null) {
            compound.setString("MetaId", metaTileEntity.metaTileEntityId);
            NBTTagCompound metaTileEntityData = new NBTTagCompound();
            metaTileEntity.writeToNBT(metaTileEntityData);
            compound.setTag("MetaTileEntity", metaTileEntityData);
        }
        return compound;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return (metaTileEntity != null && metaTileEntity.hasCapability(capability, facing)) ||
            super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(metaTileEntity != null && metaTileEntity.hasCapability(capability, facing))
            return metaTileEntity.getCapability(capability, facing);
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        if(metaTileEntity != null) {
            metaTileEntity.update();
        }
        //increment only after current tick, so meta tile entities will get first tick as timer == 0
        //and update their settings which depend on getTimer() % N properly
        super.update();
    }

    public void writeInitialSyncData(PacketBuffer buf) {
        if(metaTileEntity != null) {
            buf.writeBoolean(true);
            buf.writeString(metaTileEntity.metaTileEntityId);
            metaTileEntity.writeInitialSyncData(buf);
        } else buf.writeBoolean(false);
    }

    public void receiveInitialSyncData(PacketBuffer buf) {
        if(buf.readBoolean()) {
            String metaTileEntityName = buf.readString(Short.MAX_VALUE);
            setMetaTileEntity(GregTechAPI.META_TILE_ENTITY_REGISTRY.getObject(metaTileEntityName));
            this.metaTileEntity.receiveInitialSyncData(buf);
            scheduleChunkForRenderUpdate();
        }
    }

    public void receiveCustomData(int discriminator, PacketBuffer buffer) {
        if(discriminator == -100000) {
            String metaTileEntityName = buffer.readString(Short.MAX_VALUE);
            setMetaTileEntity(GregTechAPI.META_TILE_ENTITY_REGISTRY.getObject(metaTileEntityName));
            this.metaTileEntity.receiveInitialSyncData(buffer);
            scheduleChunkForRenderUpdate();
        } else if(metaTileEntity != null) {
            metaTileEntity.receiveCustomData(discriminator, buffer);
            scheduleChunkForRenderUpdate();
        }
    }

    @Override
    public boolean isValid() {
        return !super.isInvalid() && metaTileEntity != null;
    }

    @Override
    public void markAsDirty() {
        markDirty();
    }

    private static class UpdateEntry {
        private final int discriminator;
        private final byte[] updateData;

        public UpdateEntry(int discriminator, byte[] updateData) {
            this.discriminator = discriminator;
            this.updateData = updateData;
        }
    }

    private final List<UpdateEntry> updateEntries = new ArrayList<>();

    public void writeCustomData(int discriminator, Consumer<PacketBuffer> dataWriter) {
        ByteBuf backedBuffer = Unpooled.buffer();
        dataWriter.accept(new PacketBuffer(backedBuffer));
        byte[] updateData = Arrays.copyOfRange(backedBuffer.array(), 0, backedBuffer.writerIndex());
        updateEntries.add(new UpdateEntry(discriminator, updateData));
        @SuppressWarnings("deprecation")
        IBlockState blockState = getBlockType().getStateFromMeta(getBlockMetadata());
        world.notifyBlockUpdate(getPos(), blockState, blockState, 0);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound updateTag = new NBTTagCompound();
        NBTTagList tagList = new NBTTagList();
        for(UpdateEntry updateEntry : updateEntries) {
            NBTTagCompound entryTag = new NBTTagCompound();
            entryTag.setInteger("id", updateEntry.discriminator);
            entryTag.setByteArray("data", updateEntry.updateData);
            tagList.appendTag(entryTag);
        }
        updateEntries.clear();
        updateTag.setTag("data", tagList);
        return new SPacketUpdateTileEntity(getPos(), 0, updateTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound updateTag = pkt.getNbtCompound();
        NBTTagList tagList = updateTag.getTagList("data", NBT.TAG_COMPOUND);
        for(int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound entryTag = tagList.getCompoundTagAt(i);
            int discriminator = entryTag.getInteger("id");
            byte[] updateData = entryTag.getByteArray("data");
            ByteBuf backedBuffer = Unpooled.copiedBuffer(updateData);
            receiveCustomData(discriminator, new PacketBuffer(backedBuffer));
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound updateTag = super.getUpdateTag();
        updateTag.setInteger("x", getPos().getX());
        updateTag.setInteger("y", getPos().getY());
        updateTag.setInteger("z", getPos().getZ());
        ByteBuf backedBuffer = Unpooled.buffer();
        writeInitialSyncData(new PacketBuffer(backedBuffer));
        byte[] updateData = Arrays.copyOfRange(backedBuffer.array(), 0, backedBuffer.writerIndex());
        updateTag.setByteArray("data", updateData);
        return updateTag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        byte[] updateData = tag.getByteArray("data");
        ByteBuf backedBuffer = Unpooled.copiedBuffer(updateData);
        receiveInitialSyncData(new PacketBuffer(backedBuffer));
    }
}
