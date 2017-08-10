package gregtech.api.interfaces.tileentity;

import gregtech.api.material.type.Material;

public interface IUpgradableMachine extends IMachineProgress {

    int UPGRADE_TYPE_LOCK = Material.MatFlags.createFlag(0);
    int UPGRADE_TYPE_MUFFLER = Material.MatFlags.createFlag(1);

    boolean isUpgradable(int upgradeBit);

    boolean addUpgrade(int upgradeBit);

    boolean hasUpgrade(int upgradeBit);

}
