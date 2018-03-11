package gregtech.api.capability;

import gregtech.api.net.NetworkHandler;
import gregtech.api.net.PacketCustomTileData;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

import java.util.function.Consumer;

public interface ICustomDataTile {

    void writeInitialSyncData(PacketBuffer buf);

    void receiveCustomData(int discriminator, PacketBuffer buffer);

    default void writeCustomData(int discriminator, Consumer<PacketBuffer> dataWriter) {
        TileEntity tileEntity = (TileEntity) this; //unsafe
        WorldServer worldServer = (WorldServer) tileEntity.getWorld();
        BlockPos pos = tileEntity.getPos();
        PlayerChunkMapEntry chunkLoaderEntry = worldServer.getPlayerChunkMap().getEntry(pos.getX() / 16, pos.getZ() / 16);
        if(chunkLoaderEntry == null) return; //nobody is watching chunk, so...
        PacketBuffer backedData = new PacketBuffer(Unpooled.buffer());
        backedData.writeBoolean(false);
        backedData.writeInt(discriminator);
        dataWriter.accept(backedData);
        PacketCustomTileData packet = new PacketCustomTileData(pos, backedData);
        for(EntityPlayerMP watcher : chunkLoaderEntry.players) {
            NetworkHandler.channel.sendTo(packet.toFMLPacket(), watcher);
        }
    }

    void receiveInitialSyncData(PacketBuffer buf);

    default void handleDataPacket(PacketBuffer payload) {
        boolean initialSync = payload.readBoolean();
        if(!initialSync) {
            int discriminator = payload.readInt();
            receiveCustomData(discriminator, payload);
        } else receiveInitialSyncData(payload);
    }

    default void onBeingWatched(EntityPlayerMP watcher) {
        TileEntity tileEntity = (TileEntity) this;
        PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeBoolean(true);
        writeInitialSyncData(buffer);
        PacketCustomTileData packet = new PacketCustomTileData(tileEntity.getPos(), buffer);
        NetworkHandler.channel.sendTo(packet.toFMLPacket(), watcher);
    }

}
