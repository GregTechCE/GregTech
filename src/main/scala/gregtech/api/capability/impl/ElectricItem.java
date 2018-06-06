package gregtech.api.capability.impl;

import gregtech.api.GTValues;
import gregtech.api.capability.IElectricItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants.NBT;

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

    public void setMaxChargeOverride(long maxCharge) {
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        itemStack.getTagCompound().setLong("MaxCharge", maxCharge);
    }

    @Override
    public long getMaxCharge() {
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if(tagCompound == null)
            return maxCharge;
        if(tagCompound.hasKey("MaxCharge", NBT.TAG_LONG))
            return tagCompound.getLong("MaxCharge");
        return maxCharge;
    }

    protected long getCharge() {
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if(tagCompound == null)
            return 0;
        if(tagCompound.getBoolean("Infinite"))
            return getMaxCharge();
        return tagCompound.getLong("Charge");
    }

    @Override
    public boolean canProvideChargeExternally() {
        return this.dischargeable;
    }

    @Override
    public long charge(long amount, int chargerTier, boolean ignoreTransferLimit, boolean simulate) {
        if ((chargeable || amount == Long.MAX_VALUE) && (chargerTier == Integer.MAX_VALUE || tier >= chargerTier) && getMaxCharge() > 0) {
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
    public long discharge(long amount, int chargerTier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        if ((dischargeable || !externally || amount == Long.MAX_VALUE) && (chargerTier == Integer.MAX_VALUE || chargerTier >= tier) && getMaxCharge() > 0) {
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
    public boolean canUse(long amount) {
        return getMaxCharge() > 0L && getCharge() >= amount;
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
        if (getMaxCharge() > 0 && chargeable && getCharge() != getMaxCharge()) {
            entity.getEquipmentAndArmor().forEach(otherStack -> {
                IElectricItem capability = otherStack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
                if(capability != null && capability.canProvideChargeExternally()) {
                    setChange(getCharge() + capability.discharge(getMaxCharge() - getCharge(), getTier(), false, true, false));
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
