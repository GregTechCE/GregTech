package gregtech.api.metatileentity;

import com.google.common.base.Preconditions;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.ICustomDataTile;
import gregtech.api.gui.IUIHolder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;

import javax.annotation.Nullable;

public class MetaTileEntityHolder extends TickableTileEntityBase implements ICustomDataTile, IUIHolder {

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
        if(!getWorld().isRemote) {
            writeCustomData(-100000, buffer -> {
                buffer.writeString(metaTileEntity.metaTileEntityId);
                metaTileEntity.writeInitialSyncData(buffer);
            });
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
        super.update();
        if(metaTileEntity != null) {
            metaTileEntity.update();
        }
    }


    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        if(metaTileEntity != null) {
            buf.writeBoolean(true);
            buf.writeString(metaTileEntity.metaTileEntityId);
            metaTileEntity.writeInitialSyncData(buf);
        } else buf.writeBoolean(false);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        if(buf.readBoolean()) {
            String metaTileEntityName = buf.readString(Short.MAX_VALUE);
            setMetaTileEntity(GregTechAPI.META_TILE_ENTITY_REGISTRY.getObject(metaTileEntityName));
            this.metaTileEntity.receiveInitialSyncData(buf);
            scheduleChunkForRenderUpdate();
        }
    }

    @Override
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
}
