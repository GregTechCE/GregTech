package gregtech.common.tileentities.machines.multi;

import gregtech.GT_Mod;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public abstract class GT_MetaTileEntity_LargeBoiler
        extends GT_MetaTileEntity_MultiBlockBase {
    private boolean firstRun = true;

    public GT_MetaTileEntity_LargeBoiler(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_LargeBoiler(String aName) {
        super(aName);
    }

    public String[] getDescription() {
        return new String[]{"Controller Block for the Large Boiler", "Size: 3x3x5", "Controller (front middle in Fireboxes)", "3x3 of Fire Boxes (bottom Layer, Min 3!)", "3x3x4 of Casing (above Fireboxes, hollow, Min 24!)", "3 Pipe Casing Blocks inside the Hollow Casing", "1x Input (one of Fireboxes)", "1x Maintenance Hatch (one of Fireboxes)", "1x Muffler Hatch (one of Fireboxes)", "1x Fluid Output (one of Main Casing)","Produces "+getEUt()*40+"L/sec steam","Runs "+(runtimeBoost(20)/20f)+" sec per coal of fuel","Refined liquid fuels have 1/4 efficiency"};
    }

    public abstract Block getCasingBlock();

    public abstract byte getCasingMeta();

    public abstract byte getCasingTextureIndex();

    public abstract Block getPipeBlock();

    public abstract byte getPipeMeta();

    public abstract Block getFireboxBlock();

    public abstract byte getFireboxMeta();

    public abstract byte getFireboxTextureIndex();

    public abstract int getEUt();

    public abstract int getEfficiencyIncrease();

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[getCasingTextureIndex()], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[getCasingTextureIndex()]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeBoiler.png");
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean checkRecipe(ItemStack aStack) {
        for (GT_Recipe tRecipe : GT_Recipe.GT_Recipe_Map.sDieselFuels.mRecipeList) {
            FluidStack tFluid = GT_Utility.getFluidForFilledItem(tRecipe.getRepresentativeInput(0), true);
            if ((tFluid != null) && (tRecipe.mSpecialValue > 1)) {
                tFluid.amount = 1000;
                if (depleteInput(tFluid)) {
                    this.mMaxProgresstime = (runtimeBoost(tRecipe.mSpecialValue / 2));
                    this.mEUt = getEUt();
                    this.mEfficiencyIncrease = (this.mMaxProgresstime * getEfficiencyIncrease() * 4);
                    return true;
                }
            }
        }
        for (GT_Recipe tRecipe : GT_Recipe.GT_Recipe_Map.sDenseLiquidFuels.mRecipeList) {
            FluidStack tFluid = GT_Utility.getFluidForFilledItem(tRecipe.getRepresentativeInput(0), true);
            if (tFluid != null) {
                tFluid.amount = 1000;
                if (depleteInput(tFluid)) {
                    this.mMaxProgresstime = Math.max(1, runtimeBoost(tRecipe.mSpecialValue * 2));
                    this.mEUt = getEUt();
                    this.mEfficiencyIncrease = (this.mMaxProgresstime * getEfficiencyIncrease());
                    return true;
                }
            }
        }
        ArrayList<ItemStack> tInputList = getStoredInputs();
        if (!tInputList.isEmpty()) {
            for (ItemStack tInput : tInputList) {
                if ((GT_Utility.getFluidForFilledItem(tInput, true) == null) && ((this.mMaxProgresstime = runtimeBoost(GT_ModHandler.getFuelValue(tInput) / 80)) > 0)) {
                    this.mEUt = getEUt();
                    this.mEfficiencyIncrease = (this.mMaxProgresstime * getEfficiencyIncrease());
                    this.mOutputItems = new ItemStack[]{GT_Utility.getContainerItem(tInput, true)};
                    tInput.stackSize -= 1;
                    updateSlots();
                    return true;
                }
            }
        }
        this.mMaxProgresstime = 0;
        this.mEUt = 0;
        return false;
    }

    abstract int runtimeBoost(int mTime);

    public boolean onRunningTick(ItemStack aStack) {
        if (this.mEUt > 0) {
            int tGeneratedEU = (int) (this.mEUt * 2L * this.mEfficiency / 10000L);
            if (tGeneratedEU > 0) {
                long amount = (tGeneratedEU + 160) / 160;
                if (depleteInput(Materials.Water.getFluid(amount)) || depleteInput(GT_ModHandler.getDistilledWater(amount))) {
                    addOutput(GT_ModHandler.getSteam(tGeneratedEU));
                } else {
                    explodeMultiblock();
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (mProgresstime > 0 && firstRun) {
            firstRun = false;
            try {
                GT_Mod.instance.achievements.issueAchievement(aBaseMetaTileEntity.getWorld().getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), "extremepressure");
            } catch (Exception e) {
            }
        }
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }


    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;

        int tCasingAmount = 0;
        int tFireboxAmount = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((i != 0) || (j != 0)) {
                    for (int k = 1; k <= 4; k++) {
                        if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, k, zDir + j), getCasingTextureIndex())) {
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + i, k, zDir + j) != getCasingBlock()) {
                                return false;
                            }
                            if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, k, zDir + j) != getCasingMeta()) {
                                return false;
                            }
                            tCasingAmount++;
                        }
                    }
                } else {
                    for (int k = 1; k <= 3; k++) {
                        if (aBaseMetaTileEntity.getBlockOffset(xDir + i, k, zDir + j) != getPipeBlock()) {
                            return false;
                        }
                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, k, zDir + j) != getPipeMeta()) {
                            return false;
                        }
                    }
                    if (!addOutputToMachineList(aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 4, zDir + j), getCasingTextureIndex())) {
                        if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 4, zDir + j) != getCasingBlock()) {
                            return false;
                        }
                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 4, zDir + j) != getCasingMeta()) {
                            return false;
                        }
                        tCasingAmount++;
                    }
                }
            }
        }
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((xDir + i != 0) || (zDir + j != 0)) {
                    IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j);
                    if ((!addMaintenanceToMachineList(tTileEntity, getFireboxTextureIndex())) && (!addInputToMachineList(tTileEntity, getFireboxTextureIndex())) && (!addMufflerToMachineList(tTileEntity, getFireboxTextureIndex()))) {
                        if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != getFireboxBlock()) {
                            return false;
                        }
                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != getFireboxMeta()) {
                            return false;
                        }
                        tFireboxAmount++;
                    }
                }
            }
        }
        return (tCasingAmount >= 24) && (tFireboxAmount >= 3);
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 10;
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public int getAmountOfOutputs() {
        return 1;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }
}
