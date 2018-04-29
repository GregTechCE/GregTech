package gregtech.api.capability.impl;

import gregtech.api.GTValues;
import gregtech.api.capability.IElectricItem;
import gregtech.api.recipes.ModHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ElectricItem implements IElectricItem, ICapabilityProvider {

    protected ItemStack itemStack;

    protected final long maxCharge;
    protected final int tier;

    protected final boolean chargeable;
    protected final boolean dischargeable;

    public ElectricItem(ItemStack itemStack, long maxCharge, int tier, boolean chargeable, boolean dischargeable) {
        this.itemStack = itemStack;
        this.maxCharge = maxCharge;
        this.tier = tier;
        this.chargeable = chargeable;
        this.dischargeable = dischargeable;
    }

    protected void setChange(long change) {
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        itemStack.getTagCompound().setLong("Charge", change);
    }

    protected long getCharge() {
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if(tagCompound == null)
            return 0;
        if(tagCompound.getBoolean("Infinite"))
            return maxCharge;
        return tagCompound.getLong("Charge");
    }

    @Override
    public boolean canProvideChargeExternally() {
        return this.dischargeable;
    }

    @Override
    public long charge(long amount, int chargerTier, boolean ignoreTransferLimit, boolean simulate) {
        if ((chargeable || amount == Long.MAX_VALUE) && (chargerTier == Integer.MAX_VALUE || tier >= chargerTier) && maxCharge > 0) {
            long canReceive = maxCharge - getCharge();
            if (!ignoreTransferLimit) {
                amount = Math.min(amount, GTValues.V[tier]);
            }
            long charged = amount > canReceive ? canReceive : amount;
            if (!simulate) {
                setChange(getCharge() + charged);
            }
            return charged;
        }
        return 0;
    }

    @Override
    public long discharge(long amount, int dischargerTier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        if ((dischargeable || !externally || amount == Long.MAX_VALUE) && (dischargerTier == Integer.MAX_VALUE || dischargerTier >= tier) && maxCharge > 0) {
            if (!ignoreTransferLimit) {
                amount = Math.min(amount, GTValues.V[tier]);
            }
            long charge = getCharge();
            long discharged = amount > charge ? charge : amount;
            if (!simulate) {
                setChange(charge - discharged);
            }
            return discharged;
        }
        return 0;
    }

    @Override
    public long getMaxCharge() {
        return maxCharge;
    }

    @Override
    public boolean canUse(long amount) {
        return maxCharge > 0 && getCharge() >= amount;
    }

    @Override
    public boolean use(long amount, EntityLivingBase entity) {
        if (canUse(amount)) {
            discharge(amount, tier, true, false, false);
            return true;
        }
        return false;
    }

    @Override
    public void chargeFromArmor(EntityLivingBase entity) {
        if (maxCharge > 0 && chargeable && getCharge() != maxCharge) {
            entity.getEquipmentAndArmor().forEach(otherStack -> {
                if (ModHandler.isElectricItem(otherStack, tier)) {
                    IElectricItem capability = otherStack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
                    setChange(getCharge() + capability.discharge(maxCharge - getCharge(), tier, false, true, false));
                }
            });
        }
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == IElectricItem.CAPABILITY_ELECTRIC_ITEM;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == IElectricItem.CAPABILITY_ELECTRIC_ITEM ? (T) this : null;
    }
}
