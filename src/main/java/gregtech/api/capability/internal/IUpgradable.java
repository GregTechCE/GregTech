package gregtech.api.capability.internal;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public interface IUpgradable {

    enum UpgradeType {
        MUFFLER,
        LOCK
    }

    boolean isUpgradable(UpgradeType upgradeType);

    boolean addUpgrade(UpgradeType upgradeType);

    int getInstalledUpgrades(UpgradeType upgradeType);

}
