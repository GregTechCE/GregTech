package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.block.Block;

public class GT_MetaTileEntity_FusionComputer3 extends GT_MetaTileEntity_FusionComputer {

    public GT_MetaTileEntity_FusionComputer3(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, 6);
    }

    public GT_MetaTileEntity_FusionComputer3(String aName) {
        super(aName);
    }

    @Override
    public int tier() {
        return 8;
    }

    @Override
    public long maxEUStore() {
        return 640000000L * (Math.min(16, this.mEnergyHatches.size())) / 16L;
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_FusionComputer3(mName);
    }

    @Override
    public int getCasingMeta() {
        return 8;
    }

    @Override
    public Block getFusionCoil() {
        return GregTech_API.sBlockCasings4;
    }

    @Override
    public int getFusionCoilMeta() {
        return 7;
    }

    public String[] getDescription() {
        return new String[]{"A SUN DOWN ON EARTH", "Fusion Casings MK II around Fusion Coils", "2-16 Input Hatches", "1-16 Output Hatches", "1-16 Energy Hatches", "All Hatches must be UV or better", "8192EU/t and 40mio EU Cap per Energy Hatch"};
    }

    @Override
    public int tierOverclock() {
        return 4;
    }

    @Override
    public Block getCasing() {
        return GregTech_API.sBlockCasings4;
    }

    @Override
    public IIconContainer getIconOverlay() {
        return Textures.BlockIcons.OVERLAY_FUSION3;
    }
}

