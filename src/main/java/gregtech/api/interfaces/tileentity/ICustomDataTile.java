package gregtech.api.interfaces.tileentity;

import net.minecraft.network.PacketBuffer;

public interface ICustomDataTile {

    void receiveCustomData(PacketBuffer buf);

}
