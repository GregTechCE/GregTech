package gregtech.api.net;

import gregtech.api.net.NetworkHandler.Packet;
import net.minecraft.network.PacketBuffer;

public class PacketPipeNetUpdate implements Packet {
    public final String pipeNetName;
    public final PacketBuffer updateData;
    public final long uid;

    public PacketPipeNetUpdate(String pipeNetName, long uid, PacketBuffer updateData) {
        this.pipeNetName = pipeNetName;
        this.uid = uid;
        this.updateData = updateData;
    }
}
