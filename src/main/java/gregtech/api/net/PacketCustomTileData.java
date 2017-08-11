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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PacketCustomTileData that = (PacketCustomTileData) o;

        if (!tileEntityPos.equals(that.tileEntityPos)) return false;
        return payload.equals(that.payload);
    }

    @Override
    public int hashCode() {
        int result = tileEntityPos.hashCode();
        result = 31 * result + payload.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PacketCustomTileData{" +
                "tileEntityPos=" + tileEntityPos +
                ", payload=" + payload.hashCode() +
                '}';
    }

}
