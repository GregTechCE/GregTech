package gregtech.common.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerElectricArmor1 extends ContainerBasicArmor {

	public ContainerElectricArmor1(EntityPlayer player, InventoryArmor aInvArmor) {
		super(player, aInvArmor);
	}

	public void addSlots(InventoryPlayer aInventoryPlayer) {
		addSlotToContainer(new Slot(mInvArmor, 0, 118, 6));
		addSlotToContainer(new Slot(mInvArmor, 1, 136, 6));
		addSlotToContainer(new Slot(mInvArmor, 2, 154, 6));
		addSlotToContainer(new Slot(mInvArmor, 3, 118, 24));
		addSlotToContainer(new Slot(mInvArmor, 4, 136, 24));
		addSlotToContainer(new Slot(mInvArmor, 5, 154, 24));
		addSlotToContainer(new Slot(mInvArmor, 6, 118, 42));
		addSlotToContainer(new Slot(mInvArmor, 7, 136, 42));
		addSlotToContainer(new Slot(mInvArmor, 8, 154, 42));
		addSlotToContainer(new Slot(mInvArmor, 9, 118, 60));
		addSlotToContainer(new Slot(mInvArmor, 10, 136, 60));
		addSlotToContainer(new Slot(mInvArmor, 11, 154, 60));
		
		addSlotToContainer(new SlotFluid(mInvArmor,12,94,32));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(aInventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			ItemStack stackInSlot = aInventoryPlayer.getStackInSlot(i);
		    if(isIdenticalItem(mInvArmor.parent,stackInSlot)){
				addSlotToContainer(new SlotLocked(aInventoryPlayer,i,8+i*18,142));
			}else{
			addSlotToContainer(new Slot(aInventoryPlayer, i, 8 + i * 18, 142));}
		}
	}

	public int getSlotCount() {
		return 12;
	}

	public int getShiftClickSlotCount() {
		return 12;
	}
	
}
