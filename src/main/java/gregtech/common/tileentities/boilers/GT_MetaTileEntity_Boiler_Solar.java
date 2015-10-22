package gregtech.common.tileentities.boilers;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.common.gui.GT_Container_Boiler;
import gregtech.common.gui.GT_GUIContainer_Boiler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class GT_MetaTileEntity_Boiler_Solar
        extends GT_MetaTileEntity_Boiler {
    public GT_MetaTileEntity_Boiler_Solar(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional, "Steam Power by the Sun", new ITexture[0]);
    }

    public GT_MetaTileEntity_Boiler_Solar(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
    }

    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[4][17][];
        for (byte i = -1; i < 16; i = (byte) (i + 1)) {
            ITexture[] tmp0 = {new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEBRICKS_BOTTOM, Dyes.getModulation(i, Dyes._NULL.mRGBa))};
            rTextures[0][(i + 1)] = tmp0;
            ITexture[] tmp1 = {new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEBRICKS_TOP, Dyes.getModulation(i, Dyes._NULL.mRGBa)), new GT_RenderedTexture(Textures.BlockIcons.BOILER_SOLAR)};
            rTextures[1][(i + 1)] = tmp1;
            ITexture[] tmp2 = {new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEBRICKS_SIDE, Dyes.getModulation(i, Dyes._NULL.mRGBa))};
            rTextures[2][(i + 1)] = tmp2;
            ITexture[] tmp3 = {new GT_RenderedTexture(Textures.BlockIcons.MACHINE_BRONZEBRICKS_SIDE, Dyes.getModulation(i, Dyes._NULL.mRGBa)), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE)};
            rTextures[3][(i + 1)] = tmp3;
        }
        return rTextures;
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return mTextures[aSide >= 2 ? ((byte) (aSide != aFacing ? 2 : 3)) : aSide][aColorIndex + 1];
    }

    public int maxProgresstime() {
        return 500;
    }

    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_Boiler(aPlayerInventory, aBaseMetaTileEntity, 16000);
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_Boiler(aPlayerInventory, aBaseMetaTileEntity, "SolarBoiler.png", 16000);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Boiler_Solar(this.mName, this.mTier, this.mDescription, this.mTextures);
    }

    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if ((aBaseMetaTileEntity.isServerSide()) && (aTick > 20L)) {
            if (this.mTemperature <= 20) {
                this.mTemperature = 20;
                this.mLossTimer = 0;
            }
            if (++this.mLossTimer > 45) {
                this.mTemperature -= 1;
                this.mLossTimer = 0;
            }
            if (this.mSteam != null) {
                byte i = aBaseMetaTileEntity.getFrontFacing();
                IFluidHandler tTileEntity = aBaseMetaTileEntity.getITankContainerAtSide(i);
                if (tTileEntity != null) {
                    FluidStack tDrained = aBaseMetaTileEntity.drain(ForgeDirection.getOrientation(i), Math.max(1, this.mSteam.amount / 2), false);
                    if (tDrained != null) {
                        int tFilledAmount = tTileEntity.fill(ForgeDirection.getOrientation(i).getOpposite(), tDrained, false);
                        if (tFilledAmount > 0) {
                            tTileEntity.fill(ForgeDirection.getOrientation(i).getOpposite(), aBaseMetaTileEntity.drain(ForgeDirection.getOrientation(i), tFilledAmount, true), true);
                        }
                    }
                }
            }
            if (aTick % 25L == 0L) {
                if (this.mTemperature > 100) {
                    if ((this.mFluid == null) || (!GT_ModHandler.isWater(this.mFluid)) || (this.mFluid.amount <= 0)) {
                        this.mHadNoWater = true;
                    } else {
                        if (this.mHadNoWater) {
                            aBaseMetaTileEntity.doExplosion(2048L);
                            return;
                        }
                        this.mFluid.amount -= 1;
                        if (this.mSteam == null) {
                            this.mSteam = GT_ModHandler.getSteam(150L);
                        } else if (GT_ModHandler.isSteam(this.mSteam)) {
                            this.mSteam.amount += 150;
                        } else {
                            this.mSteam = GT_ModHandler.getSteam(150L);
                        }
                    }
                } else {
                    this.mHadNoWater = false;
                }
            }
            if ((this.mSteam != null) &&
                    (this.mSteam.amount > 16000)) {
                sendSound((byte) 1);
                this.mSteam.amount = 12000;
            }
            if ((this.mProcessingEnergy <= 0) && (aBaseMetaTileEntity.isAllowedToWork()) && (aTick % 256L == 0L) && (!aBaseMetaTileEntity.getWorld().isThundering())) {
                boolean bRain = aBaseMetaTileEntity.getWorld().isRaining() && aBaseMetaTileEntity.getBiome().rainfall > 0.0F;
                mProcessingEnergy += bRain && aBaseMetaTileEntity.getWorld().skylightSubtracted >= 4 || !aBaseMetaTileEntity.getSkyAtSide((byte) 1) ? 0 : !bRain && aBaseMetaTileEntity.getWorld().isDaytime() ? 8 : 1;
            }
            if ((this.mTemperature < 500) && (this.mProcessingEnergy > 0) && (aTick % 12L == 0L)) {
                this.mProcessingEnergy -= 1;
                this.mTemperature += 1;
            }
            aBaseMetaTileEntity.setActive(this.mProcessingEnergy > 0);
        }
    }
}
