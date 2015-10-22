package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;

public class GT_MetaTileEntity_LargeBoiler_Steel
        extends GT_MetaTileEntity_LargeBoiler {
    public GT_MetaTileEntity_LargeBoiler_Steel(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_LargeBoiler_Steel(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_LargeBoiler_Steel(this.mName);
    }

    public Block getCasingBlock() {
        return GregTech_API.sBlockCasings2;
    }

    public byte getCasingMeta() {
        return 0;
    }

    public byte getCasingTextureIndex() {
        return 16;
    }

    public Block getPipeBlock() {
        return GregTech_API.sBlockCasings2;
    }

    public byte getPipeMeta() {
        return 13;
    }

    public Block getFireboxBlock() {
        return GregTech_API.sBlockCasings3;
    }

    public byte getFireboxMeta() {
        return 14;
    }

    public byte getFireboxTextureIndex() {
        return 46;
    }

    public int getEUt() {
        return 600;
    }

    public int getEfficiencyIncrease() {
        return 12;
    }

    @Override
    int runtimeBoost(int mTime) {
        return mTime * 150 / 100;
    }
}
