package gregtech.common.tileentities.machines;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull_NonElectric;
import gregtech.api.objects.GT_RenderedTexture;

public class GT_MetaTileEntity_BasicHull_SteelBricks
        extends GT_MetaTileEntity_BasicHull_NonElectric {
    public GT_MetaTileEntity_BasicHull_SteelBricks(int aID, String aName, String aNameRegional, int aTier, String aDescription) {
        super(aID, aName, aNameRegional, aTier, aDescription);
    }

    public GT_MetaTileEntity_BasicHull_SteelBricks(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_BasicHull_SteelBricks(this.mName, this.mTier, this.mDescription, this.mTextures);
    }

    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[3][17][];
        for (byte i = -1; i < 16; i = (byte) (i + 1)) {
            ITexture[] tmp0 = {new GT_RenderedTexture(Textures.BlockIcons.MACHINE_STEELBRICKS_BOTTOM, Dyes.getModulation(i, Dyes._NULL.mRGBa))};
            rTextures[0][(i + 1)] = tmp0;
            ITexture[] tmp1 = {new GT_RenderedTexture(Textures.BlockIcons.MACHINE_STEELBRICKS_TOP, Dyes.getModulation(i, Dyes._NULL.mRGBa))};
            rTextures[1][(i + 1)] = tmp1;
            ITexture[] tmp2 = {new GT_RenderedTexture(Textures.BlockIcons.MACHINE_STEELBRICKS_SIDE, Dyes.getModulation(i, Dyes._NULL.mRGBa))};
            rTextures[2][(i + 1)] = tmp2;
        }
        return rTextures;
    }
}
