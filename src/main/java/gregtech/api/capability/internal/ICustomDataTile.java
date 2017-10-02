package gregtech.api.capability.internal;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;

public interface ICustomDataTile {

    void writeInitialSyncData(EntityPlayerMP player);

    void receiveCustomData(PacketBuffer buf);
}
