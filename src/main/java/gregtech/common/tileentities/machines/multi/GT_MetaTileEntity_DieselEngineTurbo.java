package gregtech.common.tileentities.machines.multi;

import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;

public class GT_MetaTileEntity_DieselEngineTurbo extends GT_MetaTileEntity_DieselEngine {
    public GT_MetaTileEntity_DieselEngineTurbo(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    public GT_MetaTileEntity_DieselEngineTurbo(String aName) {
        super(aName);
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Large Diesel Engine",
                "Size: 3x3x4",
                "Controller (front centered)",
                "3x3x4 of Stable Titanium Casing (hollow, Min 24!)",
                "2x Titanium Pipe Casing Block inside the Hollow Casing",
                "1x Input Hatch (one of the Casings)",
                "1x Maintenance Hatch (one of the Casings)",
                "1x Muffler Hatch (top middle back)",
                "1x Dynamo Hatch (back centered)",
                "Engine Intake Casings not obstructed (only air blocks)"};
    }

    @Override
    public boolean requiresTurboHatch() {
        return true;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_DieselEngineTurbo(this.mName);
    }

    @Override
    public String[] getInfoData() {
        return new String[]{
                "Large Diesel Engine",
                "Current Output: " + mEUt + " EU/t",

                "Efficiency: " + (float) mEfficiency / 100 + "%",
                "EfficiencyRaw: " + mEfficiency //For testing only, needs to be removed
        };
    }
}
