package gregtech.api.net;

import codechicken.lib.vec.Vector3;
import gregtech.api.net.NetworkHandler.Packet;
import net.minecraft.util.math.BlockPos;

public class PacketBlockParticle implements Packet {

    public final BlockPos blockPos;
    public final Vector3 entityPos;
    public final int particlesAmount;

    public PacketBlockParticle(BlockPos blockPos, Vector3 entityPos, int particlesAmount) {
        this.blockPos = blockPos;
        this.entityPos = entityPos;
        this.particlesAmount = particlesAmount;
    }
}
