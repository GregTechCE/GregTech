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

public class GT_MetaTileEntity_SolidNaquadahReactor
        extends GT_MetaTileEntity_BasicGenerator {
    public int mEfficiency;

    public GT_MetaTileEntity_SolidNaquadahReactor(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, "Requires Enriched Naquadah Bolts", new ITexture[0]);
        onConfigLoad();
    }

    public GT_MetaTileEntity_SolidNaquadahReactor(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
        onConfigLoad();
    }

    public boolean isOutputFacing(byte aSide) {
        return (aSide > 1) && (aSide != getBaseMetaTileEntity().getFrontFacing()) && (aSide != getBaseMetaTileEntity().getBackFacing());
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_SolidNaquadahReactor(this.mName, this.mTier, this.mDescription, this.mTextures);
    }

    public GT_Recipe.GT_Recipe_Map getRecipes() {
        return GT_Recipe.GT_Recipe_Map.sSmallNaquadahReactorFuels;
    }

    public int getCapacity() {
        return 0;
    }

    public int getEfficiency() {
        return mEfficiency;
    }

    public void onConfigLoad() {
        this.mEfficiency = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "SolidNaquadah.efficiency.tier." + this.mTier, 80);
    }

    public ITexture[] getFront(byte aColor) {
        return new ITexture[]{super.getFront(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.NAQUADAH_REACTOR_SOLID_FRONT)};
    }

    public ITexture[] getBack(byte aColor) {
        return new ITexture[]{super.getBack(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.NAQUADAH_REACTOR_SOLID_BACK)};
    }

    public ITexture[] getBottom(byte aColor) {
        return new ITexture[]{super.getBottom(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.NAQUADAH_REACTOR_SOLID_BOTTOM)};
    }

    public ITexture[] getTop(byte aColor) {
        return new ITexture[]{super.getTop(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.NAQUADAH_REACTOR_SOLID_TOP)};
    }

    public ITexture[] getSides(byte aColor) {
        return new ITexture[]{super.getSides(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.NAQUADAH_REACTOR_SOLID_SIDE)};
    }

    public ITexture[] getFrontActive(byte aColor) {
        return new ITexture[]{super.getFrontActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.NAQUADAH_REACTOR_SOLID_FRONT_ACTIVE)};
    }

    public ITexture[] getBackActive(byte aColor) {
        return new ITexture[]{super.getBackActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.NAQUADAH_REACTOR_SOLID_BACK_ACTIVE)};
    }

    public ITexture[] getBottomActive(byte aColor) {
        return new ITexture[]{super.getBottomActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.NAQUADAH_REACTOR_SOLID_BOTTOM_ACTIVE)};
    }

    public ITexture[] getTopActive(byte aColor) {
        return new ITexture[]{super.getTopActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.NAQUADAH_REACTOR_SOLID_TOP_ACTIVE)};
    }

    public ITexture[] getSidesActive(byte aColor) {
        return new ITexture[]{super.getSidesActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.NAQUADAH_REACTOR_SOLID_SIDE_ACTIVE)};
    }

	@Override
	public int getPollution() {
		return 0;
	}
}
