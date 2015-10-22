package gregtech.api.net;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public interface IGT_NetworkHandler {
    public void sendToPlayer(GT_Packet aPacket, EntityPlayerMP aPlayer);

    public void sendToAllAround(GT_Packet aPacket, TargetPoint aPosition);

    public void sendToServer(GT_Packet aPacket);

    public void sendPacketToAllPlayersInRange(World aWorld, GT_Packet aPacket, int aX, int aZ);
}