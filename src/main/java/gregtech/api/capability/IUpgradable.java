package gregtech.api.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IUpgradable {

    @CapabilityInject(IUpgradable.class)
    public static final Capability<IUpgradable> CAPABILITY_UPGRADEABLE = null;

    enum UpgradeType {
        MUFFLER,
        LOCK,
        TRANSFORMER
    }

    boolean isUpgradable(UpgradeType upgradeType);

    boolean addUpgrade(UpgradeType upgradeType);

    int getInstalledUpgrades(UpgradeType upgradeType);

}
