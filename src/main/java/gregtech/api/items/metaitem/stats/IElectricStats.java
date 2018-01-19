package gregtech.api.items.metaitem.stats;

public interface IElectricStats extends IMetaItemStats {

    long getMaxCharge();

    int getTier();

    boolean isChargeable();

    boolean isDischargeable();
}
