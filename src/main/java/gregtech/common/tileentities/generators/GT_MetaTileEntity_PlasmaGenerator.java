package gregtech.common.tileentities.generators;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicGenerator;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;

public class GT_MetaTileEntity_PlasmaGenerator
        extends GT_MetaTileEntity_BasicGenerator {

    public int mEfficiency;

    public GT_MetaTileEntity_PlasmaGenerator(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, "Plasma into energy", new ITexture[0]);
        onConfigLoad();
    }

    public GT_MetaTileEntity_PlasmaGenerator(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
        onConfigLoad();
    }

    public boolean isOutputFacing(byte aSide) {
        return aSide == getBaseMetaTileEntity().getFrontFacing();
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_PlasmaGenerator(this.mName, this.mTier, this.mDescription, this.mTextures);
    }

    public GT_Recipe.GT_Recipe_Map getRecipes() {
        return GT_Recipe.GT_Recipe_Map.sPlasmaFuels;
    }

    public int getCapacity() {
        return 16000;
    }

    public void onConfigLoad() {
        this.mEfficiency = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "PlasmaGenerator.efficiency.tier." + this.mTier, (10 + (this.mTier * 10)));
    }


    public int getEfficiency() {
        return this.mEfficiency;
    }

    public ITexture[] getFront(byte aColor) {
        return new ITexture[]{super.getFront(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
    }

    public ITexture[] getBack(byte aColor) {
        return new ITexture[]{super.getBack(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS)};
    }

    public ITexture[] getBottom(byte aColor) {
        return new ITexture[]{super.getBottom(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS)};
    }

    public ITexture[] getTop(byte aColor) {
        return new ITexture[]{super.getTop(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS)};
    }

    public ITexture[] getSides(byte aColor) {
        return new ITexture[]{super.getSides(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS)};
    }

    public ITexture[] getFrontActive(byte aColor) {
        return new ITexture[]{super.getFrontActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
    }

    public ITexture[] getBackActive(byte aColor) {
        return new ITexture[]{super.getBackActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW)};
    }

    public ITexture[] getBottomActive(byte aColor) {
        return new ITexture[]{super.getBottomActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW)};
    }

    public ITexture[] getTopActive(byte aColor) {
        return new ITexture[]{super.getTopActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW)};
    }

    public ITexture[] getSidesActive(byte aColor) {
        return new ITexture[]{super.getSidesActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS_YELLOW)};
    }

	@Override
	public int getPollution() {
		return 0;
	}
}