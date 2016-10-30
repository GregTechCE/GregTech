package gregtech.common.items.armor.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.google.common.base.Charsets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class FluidSync2 /**implements IPacket**/ {
	String playerName;

//	@Override
	public byte getPacketID() {
		return 1;
	}

	public FluidSync2(String player) {
		this.playerName = player;
	}

//	@Override
	public ByteArrayDataOutput encode() {
		ByteArrayDataOutput rOut = ByteStreams.newDataOutput(4);
		rOut.writeUTF(playerName);
		return rOut;
	}

//	@Override
//	public IPacket decode(ByteArrayDataInput aData) {
//		return new FluidSync2(aData.readUTF());
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
//						ItemStack tmp2 = tmp.inventory.getItemStack();
//						ItemStack tmp3 = UT.Fluids.getContainerForFilledItem(tmp2, true);
//						if (tmp2.stackSize <= 1) {
//							tmp2 = null;
//						} else {
//							tmp2.stackSize--;
//						}
//						tmp.inventory.setItemStack(tmp2);
//						if(tmp3!=null){
//						tmp3.stackSize=1;	
//						tmp.inventory.addItemStackToInventory(tmp3);}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//
//			}
//		}
//	}

}
