package gregtech.api.net;

import net.minecraft.world.IBlockAccess;

import com.google.common.io.ByteArrayDataInput;

public abstract class GT_Packet {
	public GT_Packet(boolean aIsReference) {
		//
	}
	
	/**
	 * I use constant IDs instead of Dynamic ones, since that is much more fail safe
	 * @return a Packet ID for this Class
	 */
	public abstract byte getPacketID();
	
	/**
	 * @return encoded byte Stream
	 */
	public abstract byte[] encode();
	
	/**
	 * @return encoded byte Stream
	 */
	public abstract GT_Packet decode(ByteArrayDataInput aData);
	
	public abstract void process(IBlockAccess aWorld);
}