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
    public void receiveCustomData(PacketBuffer buf) {
        int dataId = buf.readInt();
        switch (dataId) {
            case 0:
                IMetaTileEntityFactory factory = GregTechAPI.METATILEENTITY_REGISTRY.getObjectById(buf.readShort());
                this.setMetaTileEntity(factory.constructMetaTileEntity());
                this.metaTileEntity.receiveInitialData(buf);
                break;
            default:
                if (metaTileEntity != null) {
                    metaTileEntity.receiveCustomData(dataId, buf);
                }
        }
    }

    public void writeCustomData(int dataId, Consumer<PacketBuffer> dataWriter) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeInt(dataId);
        dataWriter.accept(packetBuffer);
        if (!world.isRemote) {
            WorldServer worldServer = (WorldServer) world;
            PlayerChunkMapEntry entry = worldServer.getPlayerChunkMap().getEntry(pos.getX() >> 4, pos.getZ() >> 4);
            if (entry != null) {
                for (EntityPlayerMP player : entry.players) {
                    NetworkHandler.channel.sendTo(NetworkHandler.packet2proxy(new PacketCustomTileData(pos, packetBuffer)), player);
                }
            }
        } else {
            //it is error
            GTLog.logger.warn("Attempted to call writeCustomData on client side!", new IllegalArgumentException());
        }
    }

    @Override
    public void writeInitialSyncData(EntityPlayerMP player) {
        if (metaTileEntity != null) {
            PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
            packetBuffer.writeInt(0);
            writeInitialSyncData(packetBuffer);
            NetworkHandler.channel.sendTo(NetworkHandler.packet2proxy(new PacketCustomTileData(pos, packetBuffer)), player);
        }
    }

    private void writeInitialSyncData(PacketBuffer packetBuffer) {
        if (metaTileEntity != null) {
            packetBuffer.writeShort(GregTechAPI.METATILEENTITY_REGISTRY.getIDForObject(metaTileEntity.factory));
            metaTileEntity.writeInitialData(packetBuffer);
        }
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