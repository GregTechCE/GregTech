package gregtech.api.items.metaitem;

import gregtech.api.GTValues;
import gregtech.api.items.metaitem.stats.IElectricStats;
import gregtech.api.recipes.ModHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

public class ElectricStats implements IElectricStats {

    public static final ElectricStats EMPTY = new ElectricStats(0, 0, false, false);

    public final long maxCharge;
    public final int tier;

    public final boolean chargeable;
    public final boolean dischargeable;

    public ElectricStats(long maxCharge, int tier, boolean chargeable, boolean dischargeable) {
        this.maxCharge = maxCharge;
        this.tier = tier;
        this.chargeable = chargeable;
        this.dischargeable = dischargeable;
    }

    public ElectricStats(long maxCharge, int tier, boolean chargeable) {
        this(maxCharge, tier, chargeable, true);
    }

    public ElectricStats(long maxCharge, int tier) {
        this(maxCharge, tier, true);
    }

    @Override
    public long charge(ItemStack stack, long amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
        int myTier = getTier(stack);
        if((chargeable || amount == Integer.MAX_VALUE) && (tier == Integer.MAX_VALUE || myTier >= tier) && maxCharge > 0) {
            long energyStored = getCharge(stack);
            long canReceive = maxCharge - energyStored;
            if(!ignoreTransferLimit) {
                amount = Math.min(amount, GTValues.V[myTier]);
            }
            long charged = amount > canReceive ? canReceive : amount;
            if(!simulate) {
                setCharge(stack, energyStored + charged);
            }
            return charged;
        }
        return 0;
    }

    @Override
    public long discharge(ItemStack stack, long amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        int myTier = getTier(stack);
        if((dischargeable || !externally || amount == Integer.MAX_VALUE) && (tier == Integer.MAX_VALUE || tier >= myTier) && maxCharge > 0) {
            long energyStored = getCharge(stack);
            if(!ignoreTransferLimit) {
                amount = Math.min(amount, GTValues.V[myTier]);
            }
            long discharged = amount > energyStored ? energyStored : amount;
            if(!simulate) {
                setCharge(stack, energyStored - discharged);
            }
            return discharged;
        }
        return 0;
    }

    private void setCharge(ItemStack stack, long charge) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound tagCompound = stack.getTagCompound();
        tagCompound.setLong("GT.Charge", charge);
    }

    @Override
    public long getCharge(ItemStack stack) {
        if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("GT.Charge", Constants.NBT.TAG_INT))
            return 0;
        return stack.getTagCompound().getInteger("GT.Charge");
    }

    @Override
    public long getMaxCharge(ItemStack stack) {
        return maxCharge;
    }

    @Override
    public boolean canUse(ItemStack stack, long amount) {
        return maxCharge > 0 && getCharge(stack) >= amount;
    }

    @Override
    public boolean use(ItemStack stack, long amount, EntityLivingBase entity) {
        if(canUse(stack, amount)) {
            discharge(stack, amount, getTier(stack), true, false, false);
            return true;
        }
        return false;
    }

    @Override
    public void chargeFromArmor(ItemStack stack, EntityLivingBase entity) {
        if(maxCharge > 0 && chargeable && getCharge(stack) != maxCharge) {
            int myTier = getTier(stack);
            entity.getEquipmentAndArmor().forEach(otherStack -> {
//                if(ModHandler.isElectricItem(stack, myTier)) { // TODO ELECTRIC ITEMS
//                    long currentCharge = getCharge(stack);
//                    setCharge(stack, currentCharge + ElectricItem.manager.discharge(stack, maxCharge - currentCharge, myTier, false, true, false));
//                }
            });
        }
    }

    @Override
    public String getToolTip(ItemStack stack) {
        return null;
    }

    @Override
    public int getTier(ItemStack stack) {
        return tier;
    }


}
