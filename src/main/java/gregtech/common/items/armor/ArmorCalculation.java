package gregtech.common.items.armor;

import cpw.mods.fml.common.Loader;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import thaumcraft.api.nodes.IRevealer;

public class ArmorCalculation {
	public static float[] calculateArmor(ItemStack[] parts) {
		return new float[32];
	}

	public static int deChargeBatterys(ItemStack[] parts, int amount) {
		int decharged = 0;
		for (int i = 0; decharged < amount && i < parts.length; i++) {
			if (GT_ModHandler.isChargerItem(parts[i])) {
				decharged = (int) (decharged + ic2.api.item.ElectricItem.manager.discharge(parts[i], amount - decharged, 10, false, false, false));
			}
		}
		return decharged;
	}

	public static FluidStack getFluid(ItemStack[] parts, int tankCap) {
		int fluidAmount;
		if (parts.length > 12 && parts[12] != null) {
			NBTTagCompound nbt = parts[12].getTagCompound();
			if (nbt != null) {
				fluidAmount = (int) nbt.getLong("mFluidDisplayAmount");
				if (fluidAmount > tankCap) {
					nbt.setLong("mFluidDisplayAmount", tankCap);
					parts[12].setTagCompound(nbt);
					fluidAmount = tankCap;
				}
				return new FluidStack(parts[12].getItemDamage(), fluidAmount);
			}

		}
		return null;
	}

	public static void useFluid(ItemStack[] parts, int usedAmount) {
		int fluidAmount;
		if (parts.length > 12 && parts[12] != null) {
			NBTTagCompound nbt = parts[12].getTagCompound();
			if (nbt != null) {
				fluidAmount = (int) nbt.getLong("mFluidDisplayAmount");
				nbt.setLong("mFluidDisplayAmount", fluidAmount - usedAmount);
				parts[12].setTagCompound(nbt);
			}
		}
	}
}
