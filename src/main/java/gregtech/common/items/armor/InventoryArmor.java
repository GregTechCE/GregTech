package gregtech.common.items.armor;

import gregtech.api.util.GT_Utility;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryArmor implements IInventory {

	public ItemStack[] parts;
	public ItemStack parent;
//	float[] def = new float[32];
	public int maxCharge;
	public int charge;
	public ArmorData data;

	public InventoryArmor(Class<ModularArmor_Item> class1, ItemStack currentEquippedItem) {
		this.parts = new ItemStack[16];
		this.parent = currentEquippedItem;
		setUID(false);
		readFromNBT(currentEquippedItem.getTagCompound());
//		for (int i = 0; i < def.length; i++) {
//			def[i] = 0.0f;
//		}
		if(currentEquippedItem.getItem() instanceof ModularArmor_Item){
			data = ((ModularArmor_Item)currentEquippedItem.getItem()).data;
		}
		
	}

	@Override
	public int getSizeInventory() {
		return this.parts.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return parts[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (parts[i] == null) {
			return null;
		}

		ItemStack product;
		if (parts[i].stackSize <= j) {
			product = parts[i];
			parts[i] = null;
//			def = ArmorCalculation.calculateArmor(parts);
			data.calculateArmor(parts);
			return product;
		} else {
			product = parts[i].splitStack(j);
			if (parts[i].stackSize == 0) {
				parts[i] = null;
			}
//			def = ArmorCalculation.calculateArmor(parts);
			data.calculateArmor(parts);
			return product;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (parts[slot] == null) {
			return null;
		}
		ItemStack toReturn = parts[slot];
		parts[slot] = null;
		return toReturn;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		parts[i] = itemstack;
//		def = ArmorCalculation.calculateArmor(parts);
		data.calculateArmor(parts);
	}

	@Override
	public String getInventoryName() {
		return "container.armor";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}

	public void onGuiSaved(EntityPlayer entityplayer) {
		parent = findParentInInventory(entityplayer);
		if (parent != null) {
			save();
		}
	}

	public void save() {
		NBTTagCompound nbt = parent.getTagCompound();
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}
		writeToNBT(nbt);
		ModularArmor_Item tmp = (ModularArmor_Item) parent.getItem();
		tmp.data.calculateArmor(parts);
		parent.setTagCompound(nbt);
	}

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < parts.length; i++) {
			if (parts[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				parts[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);
	}

	public ItemStack findParentInInventory(EntityPlayer player) {
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			if (isIdenticalItem(stack, parent)) {
				return stack;
			}
		}
		return parent;
	}

	public static boolean isIdenticalItem(ItemStack lhs, ItemStack rhs) {
		if (lhs == null || rhs == null) {
			return false;
		}

		if (lhs.getItem() != rhs.getItem()) {
			return false;
		}

		if (lhs.getItemDamage() != OreDictionary.WILDCARD_VALUE) {
			if (lhs.getItemDamage() != rhs.getItemDamage()) {
				return false;
			}
		}

		return ItemStack.areItemStackTagsEqual(lhs, rhs);
	}

	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt == null) {
			return;
		}
		if (nbt.hasKey("Items")) {
			NBTTagList nbttaglist = nbt.getTagList("Items", 10);
			parts = new ItemStack[getSizeInventory()];
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if (byte0 >= 0 && byte0 < parts.length) {
					parts[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
					//parts[12]= UT.Fluids.display(UT.Fluids.water(1234), true);
				}
			}
		}

	}

	protected void setUID(boolean override) {
		if (parent.getTagCompound() == null) {
			parent.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound nbt = parent.getTagCompound();
		if (override || !nbt.hasKey("UID")) {
			nbt.setInteger("UID", new Random().nextInt());
		}
	}

	public static int getOccupiedSlotCount(ItemStack itemStack) {
		NBTTagCompound nbt = itemStack.getTagCompound();
		if (nbt == null) {
			return 0;
		}

		int count = 0;
		if (nbt.hasKey("Items")) {
			NBTTagList nbttaglist = nbt.getTagList("Items", 10);
			for (int i = 0; i < nbttaglist.tagCount(); i++) {
				NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
				ItemStack itemStack1 = ItemStack.loadItemStackFromNBT(nbttagcompound1);
				if (itemStack1 != null && itemStack1.stackSize > 0) {
					count++;
				}
			}
		}
		return count;
	}

}
