package gregtech.api.capability.impl;

import gregtech.api.GTValues;
import gregtech.api.capability.IElectricItem;
import gregtech.api.recipes.ModHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ElectricItem implements IElectricItem, ICapabilityProvider {

    protected final long maxCharge;
    protected final int tier;

    protected final boolean chargeable;
    protected final boolean dischargeable;

    protected int charge;

    public ElectricItem(long maxCharge, int tier, boolean chargeable, boolean dischargeable) {
        this.maxCharge = maxCharge;
        this.tier = tier;
        this.chargeable = chargeable;
        this.dischargeable = dischargeable;
    }

    @Override
    public long charge(long amount, int chargerTier, boolean ignoreTransferLimit, boolean simulate) {
        if((chargeable || amount == Long.MAX_VALUE) && (chargerTier == Integer.MAX_VALUE || tier >= chargerTier) && maxCharge > 0) {
            long canReceive = maxCharge - charge;
            if(!ignoreTransferLimit) {
                amount = Math.min(amount, GTValues.V[tier]);
            }
            long charged = amount > canReceive ? canReceive : amount;
            if(!simulate) {
                charge += charged;
            }
            return charged;
        }
        return 0;
    }

    @Override
    public long discharge(long amount, int dischargerTier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        if((dischargeable || !externally || amount == Long.MAX_VALUE) && (dischargerTier == Integer.MAX_VALUE || dischargerTier >= tier) && maxCharge > 0) {
            if(!ignoreTransferLimit) {
                amount = Math.min(amount, GTValues.V[tier]);
            }
            long discharged = amount > charge ? charge : amount;
            if(!simulate) {
                charge -= discharged;
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
        return maxCharge > 0 && charge >= amount;
    }

    @Override
    public boolean use(long amount, EntityLivingBase entity) {
        if(canUse(amount)) {
            discharge(amount, tier, true, false, false);
            return true;
        }
        return false;
    }

    @Override
    public void chargeFromArmor(EntityLivingBase entity) {
        if(maxCharge > 0 && chargeable && charge != maxCharge) {
            entity.getEquipmentAndArmor().forEach(otherStack -> {
                if(ModHandler.isElectricItem(otherStack, tier)) {
                    IElectricItem capability = otherStack.getCapability(IElectricItem.CAPABILITY_ELECTRIC_ITEM, null);
                    charge += capability.discharge(maxCharge - charge, tier, false, true, false);
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
