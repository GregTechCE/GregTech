package gregtech.common.tileentities.generators;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicGenerator;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import net.minecraft.item.ItemStack;

public class GT_MetaTileEntity_DieselGenerator
        extends GT_MetaTileEntity_BasicGenerator {

    public int mEfficiency;

    public GT_MetaTileEntity_DieselGenerator(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, "Requires liquid Fuel", new ITexture[0]);
        onConfigLoad();
    }

    public GT_MetaTileEntity_DieselGenerator(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aDescription, aTextures);
        onConfigLoad();
    }

    public boolean isOutputFacing(byte aSide) {
        return aSide == getBaseMetaTileEntity().getFrontFacing();
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_DieselGenerator(this.mName, this.mTier, this.mDescription, this.mTextures);
    }

    public GT_Recipe.GT_Recipe_Map getRecipes() {
        return GT_Recipe.GT_Recipe_Map.sDieselFuels;
    }

    public int getCapacity() {
        return 16000;
    }

    public void onConfigLoad() {
        this.mEfficiency = GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "DieselGenerator.efficiency.tier." + this.mTier, (100 - this.mTier * 5));
    }

    public int getEfficiency() {
        return this.mEfficiency;
    }

    public int getFuelValue(ItemStack aStack) {
        int rValue = Math.max(GT_ModHandler.getFuelCanValue(aStack) * 6 / 5, super.getFuelValue(aStack));
        if (ItemList.Fuel_Can_Plastic_Filled.isStackEqual(aStack, false, true)) {
            rValue = Math.max(rValue, GameRegistry.getFuelValue(aStack) * 3);
        }
        return rValue;
    }

    public ITexture[] getFront(byte aColor) {
        return new ITexture[]{super.getFront(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_FRONT), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
    }

    public ITexture[] getBack(byte aColor) {
        return new ITexture[]{super.getBack(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_BACK)};
    }

    public ITexture[] getBottom(byte aColor) {
        return new ITexture[]{super.getBottom(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_BOTTOM)};
    }

    public ITexture[] getTop(byte aColor) {
        return new ITexture[]{super.getTop(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_TOP)};
    }

    public ITexture[] getSides(byte aColor) {
        return new ITexture[]{super.getSides(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_SIDE)};
    }

    public ITexture[] getFrontActive(byte aColor) {
        return new ITexture[]{super.getFrontActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_FRONT_ACTIVE), Textures.BlockIcons.OVERLAYS_ENERGY_OUT[this.mTier]};
    }

    public ITexture[] getBackActive(byte aColor) {
        return new ITexture[]{super.getBackActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_BACK_ACTIVE)};
    }

    public ITexture[] getBottomActive(byte aColor) {
        return new ITexture[]{super.getBottomActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_BOTTOM_ACTIVE)};
    }

    public ITexture[] getTopActive(byte aColor) {
        return new ITexture[]{super.getTopActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_TOP_ACTIVE)};
    }

    public ITexture[] getSidesActive(byte aColor) {
        return new ITexture[]{super.getSidesActive(aColor)[0], new GT_RenderedTexture(Textures.BlockIcons.DIESEL_GENERATOR_SIDE_ACTIVE)};
    }

	@Override
	public int getPollution() {
		return 10;
	}
}
