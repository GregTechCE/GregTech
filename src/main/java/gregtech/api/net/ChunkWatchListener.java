package gregtech.api.net;

import gregtech.api.capability.internal.ICustomDataTile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

class ChunkWatchListener {

    @SubscribeEvent
    public void onChunkWatch(ChunkWatchEvent.Watch event) {
        ChunkPos chunkPos = event.getChunk();
        EntityPlayerMP player = event.getPlayer();
        Chunk chunk = player.world.getChunkFromChunkCoords(chunkPos.x, chunkPos.z);
        for (TileEntity tileEntity : chunk.getTileEntityMap().values()) {
            if(tileEntity instanceof ICustomDataTile) {
                ((ICustomDataTile) tileEntity).writeInitialSyncData(player);
            }
        }
    }

}