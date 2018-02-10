package gregtech.api.metatileentity;

import com.google.common.base.Preconditions;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.internal.ICustomDataTile;
import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.net.NetworkHandler;
import gregtech.api.net.PacketCustomTileData;
import gregtech.api.util.GTLog;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class GregtechTileEntity extends TickableTileEntityBase implements IGregTechTileEntity, ICustomDataTile {

    private MetaTileEntity metaTileEntity;

    @Nullable
    @Override
    public IMetaTileEntity getMetaTileEntity() {
        return metaTileEntity;
    }

    @Override
    public void setMetaTileEntity(IMetaTileEntity metaTileEntity) {
        Preconditions.checkArgument(metaTileEntity instanceof MetaTileEntity, "GregtechTileEntity supports only MetaTileEntity child!");
        this.metaTileEntity = (MetaTileEntity) metaTileEntity;
        this.metaTileEntity.holder = this;
        if (world != null && !world.isRemote) {
            writeCustomData(0, this::writeInitialSyncData);
            markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("MetaTileEntityId", Constants.NBT.TAG_STRING)) {
            IMetaTileEntityFactory factory = GregTechAPI.METATILEENTITY_REGISTRY.getObject(compound.getString("MetaTileEntityId"));
            this.setMetaTileEntity(factory.constructMetaTileEntity());
            NBTTagCompound metaTileEntityTag = compound.getCompoundTag("MetaTileEntity");
            metaTileEntity.loadNBTData(metaTileEntityTag);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (metaTileEntity != null) {
            compound.setString("MetaTileEntityId", GregTechAPI.METATILEENTITY_REGISTRY.getNameForObject(metaTileEntity.factory));
            NBTTagCompound metaTileEntityTag = new NBTTagCompound();
            metaTileEntity.saveNBTData(metaTileEntityTag);
            compound.setTag("MetaTileEntity", metaTileEntityTag);
        }
        return compound;
    }

    @Override
    public void onLoad() {
        if(this.metaTileEntity != null) {
            this.metaTileEntity.onFirstTick();
        }
    }

    @Override
    public void receiveCustomData(int discriminator, PacketBuffer buffer) {
        if(this.metaTileEntity != null) {
            this.metaTileEntity.receiveCustomData(discriminator, buffer);
        }
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        IMetaTileEntityFactory factory = GregTechAPI.METATILEENTITY_REGISTRY.getObjectById(buf.readShort());
        this.setMetaTileEntity(factory.constructMetaTileEntity());
        this.metaTileEntity.receiveInitialData(buf);
        this.world.markBlockRangeForRenderUpdate(getPos(), getPos());
    }

    @Override
    public void writeInitialSyncData(PacketBuffer packetBuffer) {
        packetBuffer.writeShort(GregTechAPI.METATILEENTITY_REGISTRY.getIDForObject(metaTileEntity.factory));
        metaTileEntity.writeInitialData(packetBuffer);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return (metaTileEntity != null && metaTileEntity.hasCapability(capability, facing))
            || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (metaTileEntity != null && metaTileEntity.hasCapability(capability, facing)) {
            return metaTileEntity.getCapability(capability, facing);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        super.update();
        if (metaTileEntity != null) {
            metaTileEntity.onPreTick(getTimer());
            metaTileEntity.onPostTick(getTimer());
        }
    }
}