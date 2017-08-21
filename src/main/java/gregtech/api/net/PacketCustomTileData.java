package gregtech.api.net;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

public class PacketCustomTileData implements NetworkHandler.Packet {

    public final BlockPos tileEntityPos;
    public final PacketBuffer payload;

    public PacketCustomTileData(BlockPos tileEntityPos, PacketBuffer payload) {
        this.tileEntityPos = tileEntityPos;
        this.payload = payload;
    }

}
