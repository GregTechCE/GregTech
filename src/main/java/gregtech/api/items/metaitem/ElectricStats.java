package gregtech.api.items.metaitem;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.impl.ElectricItem;
import gregtech.api.items.metaitem.stats.IItemCapabilityProvider;
import gregtech.api.items.metaitem.stats.IItemMaxStackSizeProvider;
import gregtech.api.items.metaitem.stats.IMetaItemStats;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ElectricStats implements IMetaItemStats, IItemCapabilityProvider, IItemMaxStackSizeProvider {

    public static final ElectricStats EMPTY = new ElectricStats(0, 0, false, false);

    public final long maxCharge;
    public final int tier;

    public final boolean chargeable;
    public final boolean dischargeable;

    public ElectricStats(long maxCharge, long tier, boolean chargeable, boolean dischargeable) {
        this.maxCharge = maxCharge;
        this.tier = (int) tier;
        this.chargeable = chargeable;
        this.dischargeable = dischargeable;
    }

    @Override
    public int getMaxStackSize(ItemStack itemStack, int defaultValue) {
        ElectricItem electricItem = (ElectricItem) itemStack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if(electricItem == null || electricItem.getCharge() == 0) {
            return defaultValue;
        }
        return 1;
    }

    public static ElectricStats createElectricItem(long maxCharge, long tier) {
        return new ElectricStats(maxCharge, tier, true, false);
    }

    public static ElectricStats createRechargeableBattery(long maxCharge, int tier) {
        return new ElectricStats(maxCharge, tier, true, true);
    }

    public static ElectricStats createBattery(long maxCharge, int tier, boolean rechargeable) {
        return new ElectricStats(maxCharge, tier, rechargeable, true);
    }

    @Override
    public ICapabilityProvider createProvider(ItemStack itemStack) {
        return new ElectricItem(itemStack, maxCharge, tier, chargeable, dischargeable);
    }
}
