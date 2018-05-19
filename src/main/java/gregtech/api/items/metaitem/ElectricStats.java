package gregtech.api.items.metaitem;

import gregtech.api.items.metaitem.stats.IElectricStats;

public class ElectricStats implements IElectricStats {

    public static final ElectricStats EMPTY = new ElectricStats(0, 0, false, false);

    public final long maxCharge;
    public final int tier;

    public final boolean chargeable;
    public final boolean dischargeable;

    public static ElectricStats electricItem(long maxCharge, long tier) {
        return new ElectricStats(maxCharge, tier, true, false);
    }

    public ElectricStats(long maxCharge, long tier, boolean chargeable, boolean dischargeable) {
        this.maxCharge = maxCharge;
        this.tier = (int) tier;
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
    public long getMaxCharge() {
        return maxCharge;
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public boolean isChargeable() {
        return chargeable;
    }

    @Override
    public boolean isDischargeable() {
        return dischargeable;
    }
}
