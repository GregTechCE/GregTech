package gregtech.api.metatileentity.implementations;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Recipe.GT_Recipe_Map;
import gregtech.common.GT_Pollution;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;

import static gregtech.api.enums.GT_Values.V;

public abstract class GT_MetaTileEntity_BasicGenerator extends GT_MetaTileEntity_BasicTank {
    public GT_MetaTileEntity_BasicGenerator(int aID, String aName, String aNameRegional, int aTier, String aDescription, ITexture... aTextures) {
        super(aID, aName, aNameRegional, aTier, 3, aDescription, aTextures);
    }

    public GT_MetaTileEntity_BasicGenerator(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, 3, aDescription, aTextures);
    }

    @Override
    public ITexture[][][] getTextureSet(ITexture[] aTextures) {
        ITexture[][][] rTextures = new ITexture[10][17][];
        for (byte i = -1; i < 16; i++) {
            rTextures[0][i + 1] = getFront(i);
            rTextures[1][i + 1] = getBack(i);
            rTextures[2][i + 1] = getBottom(i);
            rTextures[3][i + 1] = getTop(i);
            rTextures[4][i + 1] = getSides(i);
            rTextures[5][i + 1] = getFrontActive(i);
            rTextures[6][i + 1] = getBackActive(i);
            rTextures[7][i + 1] = getBottomActive(i);
            rTextures[8][i + 1] = getTopActive(i);
            rTextures[9][i + 1] = getSidesActive(i);
        }
        return rTextures;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        return mTextures[(aActive ? 5 : 0) + (aSide == aFacing ? 0 : aSide == GT_Utility.getOppositeSide(aFacing) ? 1 : aSide == 0 ? 2 : aSide == 1 ? 3 : 4)][aColorIndex + 1];
    }

    @Override
    public String[] getDescription() {
        return new String[]{mDescription, "Fuel Efficiency: " + getEfficiency() + "%"};
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) return true;
        aBaseMetaTileEntity.openGUI(aPlayer);
        return true;
    }

    public ITexture[] getFront(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getBack(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getBottom(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getTop(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getSides(byte aColor) {
        return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColor + 1]};
    }

    public ITexture[] getFrontActive(byte aColor) {
        return getFront(aColor);
    }

    public ITexture[] getBackActive(byte aColor) {
        return getBack(aColor);
    }

    public ITexture[] getBottomActive(byte aColor) {
        return getBottom(aColor);
    }

    public ITexture[] getTopActive(byte aColor) {
        return getTop(aColor);
    }

    public ITexture[] getSidesActive(byte aColor) {
        return getSides(aColor);
    }

    @Override
    public boolean isFacingValid(byte aSide) {
        return aSide > 1;
    }

    @Override
    public boolean isSimpleMachine() {
        return false;
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        return aIndex < 2;
    }

    @Override
    public boolean isEnetOutput() {
        return true;
    }

    @Override
    public boolean isOutputFacing(byte aSide) {
        return true;
    }

    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public long maxEUOutput() {
        return getBaseMetaTileEntity().isAllowedToWork() ? V[mTier] : 0;
    }

    @Override
    public long maxEUStore() {
        return Math.max(getEUVar(), V[mTier] * 40 + getMinimumStoredEU());
    }

    @Override
    public boolean doesFillContainers() {
        return getBaseMetaTileEntity().isAllowedToWork();
    }

    @Override
    public boolean doesEmptyContainers() {
        return getBaseMetaTileEntity().isAllowedToWork();
    }

    @Override
    public boolean canTankBeFilled() {
        return getBaseMetaTileEntity().isAllowedToWork();
    }

    @Override
    public boolean canTankBeEmptied() {
        return getBaseMetaTileEntity().isAllowedToWork();
    }

    @Override
    public boolean displaysItemStack() {
        return true;
    }

    @Override
    public boolean displaysStackSize() {
        return false;
    }

    @Override
    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return getFuelValue(aFluid) > 0;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.isAllowedToWork() && aTick % 10 == 0) {
        	long tProducedEU = 0;
            if (mFluid == null) {
                if (aBaseMetaTileEntity.getUniversalEnergyStored() < maxEUOutput() + getMinimumStoredEU()) {
                    mInventory[getStackDisplaySlot()] = null;
                } else {
                    if (mInventory[getStackDisplaySlot()] == null)
                        mInventory[getStackDisplaySlot()] = new ItemStack(Blocks.fire, 1);
                    mInventory[getStackDisplaySlot()].setStackDisplayName("Generating: " + (aBaseMetaTileEntity.getUniversalEnergyStored() - getMinimumStoredEU()) + " EU");
                }
            } else {
                int tFuelValue = getFuelValue(mFluid), tConsumed = consumedFluidPerOperation(mFluid);
                if (tFuelValue > 0 && tConsumed > 0 && mFluid.amount > tConsumed) {
                    long tFluidAmountToUse = Math.min(mFluid.amount / tConsumed, (maxEUStore() - aBaseMetaTileEntity.getUniversalEnergyStored()) / tFuelValue);
                    if (tFluidAmountToUse > 0 && aBaseMetaTileEntity.increaseStoredEnergyUnits(tFluidAmountToUse * tFuelValue, true)) {
                        tProducedEU = tFluidAmountToUse * tFuelValue;
                        mFluid.amount -= tFluidAmountToUse * tConsumed;
                    }
                }
            }
            if (mInventory[getInputSlot()] != null && aBaseMetaTileEntity.getUniversalEnergyStored() < (maxEUOutput() * 20 + getMinimumStoredEU()) && GT_Utility.getFluidForFilledItem(mInventory[getInputSlot()], true) == null) {
                int tFuelValue = getFuelValue(mInventory[getInputSlot()]);
                if (tFuelValue > 0) {
                    ItemStack tEmptyContainer = getEmptyContainer(mInventory[getInputSlot()]);
                    if (aBaseMetaTileEntity.addStackToSlot(getOutputSlot(), tEmptyContainer)) {
                        aBaseMetaTileEntity.increaseStoredEnergyUnits(tFuelValue, true);
                        aBaseMetaTileEntity.decrStackSize(getInputSlot(), 1);
                        tProducedEU = tFuelValue;
                    }
                }
            }
            if(tProducedEU>0&&getPollution()>0){            	
            	GT_Pollution.addPollution(new ChunkPosition(this.getBaseMetaTileEntity().getXCoord(), this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord()), 
            			(int) ((tProducedEU * getPollution()/(500*mTier))+1));                
            }
        }

        if (aBaseMetaTileEntity.isServerSide())
            aBaseMetaTileEntity.setActive(aBaseMetaTileEntity.isAllowedToWork() && aBaseMetaTileEntity.getUniversalEnergyStored() >= maxEUOutput() + getMinimumStoredEU());
    }
    
    public abstract int getPollution();

    public abstract GT_Recipe_Map getRecipes();

    public abstract int getEfficiency();

    public int consumedFluidPerOperation(FluidStack aLiquid) {
        return 1;
    }

    public int getFuelValue(FluidStack aLiquid) {
        if (aLiquid == null || getRecipes() == null) return 0;
        FluidStack tLiquid;
        Collection<GT_Recipe> tRecipeList = getRecipes().mRecipeList;
        if (tRecipeList != null) for (GT_Recipe tFuel : tRecipeList)
            if ((tLiquid = GT_Utility.getFluidForFilledItem(tFuel.getRepresentativeInput(0), true)) != null)
                if (aLiquid.isFluidEqual(tLiquid))
                    return (int) (((long) tFuel.mSpecialValue * getEfficiency() * consumedFluidPerOperation(tLiquid)) / 100);
        return 0;
    }

    public int getFuelValue(ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack) || getRecipes() == null) return 0;
        GT_Recipe tFuel = getRecipes().findRecipe(getBaseMetaTileEntity(), false, Long.MAX_VALUE, null, aStack);
        if (tFuel != null) return (int) ((tFuel.mSpecialValue * 1000L * getEfficiency()) / 100);
        return 0;
    }

    public ItemStack getEmptyContainer(ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack) || getRecipes() == null) return null;
        GT_Recipe tFuel = getRecipes().findRecipe(getBaseMetaTileEntity(), false, Long.MAX_VALUE, null, aStack);
        if (tFuel != null) return GT_Utility.copy(tFuel.getOutput(0));
        return GT_Utility.getContainerItem(aStack, true);
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack) && (getFuelValue(aStack) > 0 || getFuelValue(GT_Utility.getFluidForFilledItem(aStack, true)) > 0);
    }

    @Override
    public int getCapacity() {
        return 16000;
    }

    @Override
    public int getTankPressure() {
        return -100;
    }
}
