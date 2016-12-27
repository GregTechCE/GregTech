package gregtech.common.items.armor.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.base.Charsets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class FluidSync /**implements IPacket**/ {
	String playerName;
	int amount;
	String fluid;

//	@Override
	public byte getPacketID() {
		return 0;
	}

	public FluidSync(String player, int amount, String fluid) {
		this.playerName = player;
		this.amount = amount;
		this.fluid = fluid.toLowerCase();
	}

//	@Override
	public ByteArrayDataOutput encode() {
		ByteArrayDataOutput rOut = ByteStreams.newDataOutput(4);
		rOut.writeUTF(playerName + ";" + amount + ";" + fluid);
		return rOut;
	}

//	@Override
//	public IPacket decode(ByteArrayDataInput aData) {
//		String tmp = aData.readUTF();
//		String[] tmp2 = tmp.split(";");
//		return new FluidSync(tmp2[0], Integer.parseInt(tmp2[1]), tmp2[2].toLowerCase());
//	}
//
//	@Override
//	public void process(IBlockAccess aWorld, INetworkHandler aNetworkHandler) {
//		WorldServer[] worlds = DimensionManager.getWorlds();
//		EntityPlayer tmp;
//		for (int i = 0; i < worlds.length; i++) {
//			tmp = worlds[i].getPlayerEntityByName(playerName);
//			if (tmp != null) {
//				try {
//					if (fluid.equals("null")) {
//						tmp.openContainer.getSlot(12).putStack(null);
//					} else {
//						tmp.openContainer.getSlot(12).putStack(UT.Fluids.display(new FluidStack(FluidRegistry.getFluid(fluid), amount), true));
//					}
//					tmp.openContainer.detectAndSendChanges();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		}
//	}

}
