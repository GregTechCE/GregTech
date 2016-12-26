package gregtech.common.tileentities.generators;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicGenerator;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_SteamTurbine extends GT_MetaTileEntity_BasicGenerator {

    public int mEfficiency;

    public GT_MetaTileEntity_SteamTurbine(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, "Requires Steam to run", new ITexture[0]);
        onConfigLoad();
    }

    public GT_MetaTileEntity_SteamTurbine(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
        onConfigLoad();
    }

    public boolean isOutputFacing(byte aSide) {
        return aSide == getBaseMetaTileEntity().getFrontFacing();
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_SteamTurbine(this.mName, this.mTier, this.mDescription, this.mTextures);
    }

    public GT_Recipe.GT_Recipe_Map getRecipes() {
        return null;
    }

    @Override
    public String[] getDescription() {
        return new String[]{mDescription, "Fuel Efficiency: " + (600 / getEfficiency()) + "%"};
    }

    public int getCapacity() {
        return 24000 * this.mTier;
    }

    public void onConfigLoad() {
        this.mEfficiency = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "SteamTurbine.efficiency.tier." + this.mTier, 6 + this.mTier);
    }

    public int getEfficiency() {
        return this.mEfficiency;
    }

    public int getFuelValue(FluidStack aLiquid) {
    	String fluidName = aLiquid.getFluid().getUnlocalizedName(aLiquid);
        return GT_ModHandler.isSteam(aLiquid) || fluidName.equals("fluid.steam") || fluidName.equals("ic2.fluidSteam") || fluidName.equals("fluid.mfr.steam.still.name") ? 3 : 0;
    }

    public int consumedFluidPerOperation(FluidStack aLiquid) {
        return this.mEfficiency;
    }

    public ITexture[] getFront(byte aColor) {
        return new ITexture[]{super.getFront(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_FRONT),
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
    }

    public ITexture[] getBack(byte aColor) {
        return new ITexture[]{super.getBack(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_BACK)};
    }

    public ITexture[] getBottom(byte aColor) {
        return new ITexture[]{super.getBottom(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_BOTTOM)};
    }

    public ITexture[] getTop(byte aColor) {
        return new ITexture[]{super.getTop(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_TOP)};
    }

    public ITexture[] getSides(byte aColor) {
        return new ITexture[]{super.getSides(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_SIDE)};
    }

    public ITexture[] getFrontActive(byte aColor) {
        return new ITexture[]{super.getFrontActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_FRONT_ACTIVE),
                Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
    }

    public ITexture[] getBackActive(byte aColor) {
        return new ITexture[]{super.getBackActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_BACK_ACTIVE)};
    }

    public ITexture[] getBottomActive(byte aColor) {
        return new ITexture[]{super.getBottomActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_BOTTOM_ACTIVE)};
    }

    public ITexture[] getTopActive(byte aColor) {
        return new ITexture[]{super.getTopActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_TOP_ACTIVE)};
    }

    public ITexture[] getSidesActive(byte aColor) {
        return new ITexture[]{super.getSidesActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.STEAM_TURBINE_SIDE_ACTIVE)};
    }

	@Override
	public int getPollution() {
		return 0;
	}
	
    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
    		if(aFluid.getFluid().getUnlocalizedName(aFluid).equals("ic2.fluidSuperheatedSteam")){
                aFluid.amount=0;
                aFluid = null;
                return false;
            }
        return super.isFluidInputAllowed(aFluid);
    }
}
