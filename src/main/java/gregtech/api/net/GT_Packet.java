package gregtech.api.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public abstract class GT_Packet implements IMessage {

    public GT_Packet() {}

    @Override
    public final void fromBytes(ByteBuf buf) {
        decode(buf);
    }

    @Override
    public final void toBytes(ByteBuf buf) {
        encode(buf);
    }

    /**
     * @return encoded byte Stream
     */
    public abstract void encode(ByteBuf buf);

    /**
     * @return encoded byte Stream
     */
    public abstract void decode(ByteBuf buf);

    public abstract void process(World world);
}