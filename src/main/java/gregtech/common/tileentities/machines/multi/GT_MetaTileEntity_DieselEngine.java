package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;

public class GT_MetaTileEntity_DieselEngine extends GT_MetaTileEntity_MultiBlockBase {
    protected int fuelConsumption = 0;
    protected int fuelValue = 0;
    protected int fuelRemaining = 0;
    protected boolean boostEu = false;

    public GT_MetaTileEntity_DieselEngine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    public GT_MetaTileEntity_DieselEngine(String aName) {
        super(aName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Large Diesel Engine",
                "Size(WxHxD): 3x3x4, Controller (front centered)",
                "3x3x4 of Stable Titanium Casing (hollow, Min 24!)",
                "2x Titanium Gear Box Casing inside the Hollow Casing",
                "1x Input Hatch (one of the Casings)",
                "1x Maintenance Hatch (one of the Casings)",
                "1x Muffler Hatch (top middle back)",
                "1x Dynamo Hatch (back centered)",
                "Engine Intake Casings not obstructed (only air blocks)"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_DIESEL_ENGINE)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50]};
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return getMaxEfficiency(aStack) > 0;
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeDieselEngine.png");
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        ArrayList<FluidStack> tFluids = getStoredFluids();
        Collection<GT_Recipe> tRecipeList = GT_Recipe.GT_Recipe_Map.sDieselFuels.mRecipeList;

        if(tFluids.size() > 0 && tRecipeList != null) { //Does input hatch have a diesel fuel?
            for (FluidStack hatchFluid1 : tFluids) { //Loops through hatches
                for(GT_Recipe aFuel : tRecipeList) { //Loops through diesel fuel recipes
                    FluidStack tLiquid;
                    if ((tLiquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null) { //Create fluidstack from current recipe
                        if (hatchFluid1.isFluidEqual(tLiquid)) { //Has a diesel fluid
                            fuelConsumption = tLiquid.amount = boostEu ? (4096 / aFuel.mSpecialValue) : (2048 / aFuel.mSpecialValue); //Calc fuel consumption
                            if(depleteInput(tLiquid)) { //Deplete that amount
                                boostEu = depleteInput(Materials.Oxygen.getGas(2L));

                                if(tFluids.contains(Materials.Lubricant.getFluid(1L))) { //Has lubricant?
                                    //Deplete Lubricant. 1000L should = 1 hour of runtime (if baseEU = 2048)
                                    if(mRuntime % 72 == 0 || mRuntime == 0) depleteInput(Materials.Lubricant.getFluid(boostEu ? 2 : 1));
                                } else return false;

                                fuelValue = aFuel.mSpecialValue;
                                fuelRemaining = hatchFluid1.amount; //Record available fuel
                                this.mEUt = mEfficiency < 2000 ? 0 : (int) (2048 * ((float) mEfficiency / 10000)); //Output 0 if startup is less than 20%
                                this.mProgresstime = 1;
                                this.mMaxProgresstime = 1;
                                this.mEfficiencyIncrease = 15;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        this.mEUt = 0;
        this.mEfficiency = 0;
        return false;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        byte tSide = getBaseMetaTileEntity().getBackFacing();
        int tX = getBaseMetaTileEntity().getXCoord();
        int tY = getBaseMetaTileEntity().getYCoord();
        int tZ = getBaseMetaTileEntity().getZCoord();

        if(getBaseMetaTileEntity().getBlockAtSideAndDistance(tSide, 1) != getGearboxBlock() && getBaseMetaTileEntity().getBlockAtSideAndDistance(tSide, 2) != getGearboxBlock()) {
            return false;
        }
        if(getBaseMetaTileEntity().getMetaIDAtSideAndDistance(tSide, 1) != getGearboxMeta() && getBaseMetaTileEntity().getMetaIDAtSideAndDistance(tSide, 2) != getGearboxMeta()) {
            return false;
        }
        for (byte i = -1; i < 2; i = (byte) (i + 1)) {
            for (byte j = -1; j < 2; j = (byte) (j + 1)) {
                if ((i != 0) || (j != 0)) {
                    for (byte k = 0; k < 4; k = (byte) (k + 1)) {
                        Block frontAir = getBaseMetaTileEntity().getBlock(tX - (tSide == 5 ? 1 : tSide == 4 ? -1 : i), tY + j, tZ - (tSide == 2 ? -1 : tSide == 3 ? 1 : i));
                        if(!(frontAir.getUnlocalizedName().equalsIgnoreCase("tile.air") || frontAir.getUnlocalizedName().equalsIgnoreCase("tile.railcraft.residual.heat"))) {
                            return false; //Fail if vent blocks are obstructed
                        }
                        if (((i == 0) || (j == 0)) && ((k == 1) || (k == 2))) {
                            if (getBaseMetaTileEntity().getBlock(tX + (tSide == 5 ? k : tSide == 4 ? -k : i), tY + j, tZ + (tSide == 2 ? -k : tSide == 3 ? k : i)) == getCasingBlock() && getBaseMetaTileEntity().getMetaID(tX + (tSide == 5 ? k : tSide == 4 ? -k : i), tY + j, tZ + (tSide == 2 ? -k : tSide == 3 ? k : i)) == getCasingMeta()) {
                            } else if (!addMufflerToMachineList(getBaseMetaTileEntity().getIGregTechTileEntity(tX + (tSide == 5 ? 2 : tSide == 4 ? -2 : 0), tY + 1, tZ + (tSide == 3 ? 2 : tSide == 2 ? -2 : 0)), getCasingTextureIndex())) {
                                return false; //Fail if no muffler top middle back
                            } else if (!addToMachineList(getBaseMetaTileEntity().getIGregTechTileEntity(tX + (tSide == 5 ? k : tSide == 4 ? -k : i), tY + j, tZ + (tSide == 2 ? -k : tSide == 3 ? k : i)))) {
                                return false;
                            }
                        } else if (k == 0) {
                          if(!(getBaseMetaTileEntity().getBlock(tX + (tSide == 5 ? k : tSide == 4 ? -k : i), tY + j, tZ + (tSide == 2 ? -k : tSide == 3 ? k : i)) == getIntakeBlock() && getBaseMetaTileEntity().getMetaID(tX + (tSide == 5 ? k : tSide == 4 ? -k : i), tY + j, tZ + (tSide == 2 ? -k : tSide == 3 ? k : i)) == getIntakeMeta())) {
                              return false;
                          }
                        } else if (getBaseMetaTileEntity().getBlock(tX + (tSide == 5 ? k : tSide == 4 ? -k : i), tY + j, tZ + (tSide == 2 ? -k : tSide == 3 ? k : i)) == getCasingBlock() && getBaseMetaTileEntity().getMetaID(tX + (tSide == 5 ? k : tSide == 4 ? -k : i), tY + j, tZ + (tSide == 2 ? -k : tSide == 3 ? k : i)) == getCasingMeta()) {
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        this.mDynamoHatches.clear();
        IGregTechTileEntity tTileEntity = getBaseMetaTileEntity().getIGregTechTileEntityAtSideAndDistance(getBaseMetaTileEntity().getBackFacing(), 3);
        if ((tTileEntity != null) && (tTileEntity.getMetaTileEntity() != null)) {
            if ((tTileEntity.getMetaTileEntity() instanceof GT_MetaTileEntity_Hatch_Dynamo)) {
                this.mDynamoHatches.add((GT_MetaTileEntity_Hatch_Dynamo) tTileEntity.getMetaTileEntity());
                ((GT_MetaTileEntity_Hatch) tTileEntity.getMetaTileEntity()).mMachineBlock = getCasingTextureIndex();
            } else {
                return false;
            }
        }
        return true;
    }

    public Block getCasingBlock() {
        return GregTech_API.sBlockCasings4;
    }

    public byte getCasingMeta() {
        return 2;
    }

    public Block getIntakeBlock() {
        return GregTech_API.sBlockCasings4;
    }

    public byte getIntakeMeta() {
        return 13;
    }

    public Block getGearboxBlock() {
        return GregTech_API.sBlockCasings2;
    }

    public byte getGearboxMeta() {
        return 4;
    }

    public byte getCasingTextureIndex() {
        return 50;
    }

    private boolean addToMachineList(IGregTechTileEntity tTileEntity) {
        return ((addMaintenanceToMachineList(tTileEntity, getCasingTextureIndex())) || (addInputToMachineList(tTileEntity, getCasingTextureIndex())) || (addOutputToMachineList(tTileEntity, getCasingTextureIndex())) || (addMufflerToMachineList(tTileEntity, getCasingTextureIndex())));
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_DieselEngine(this.mName);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 1;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return boostEu ? 30000 : 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    public int getAmountOfOutputs() {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return true;
    }

    @Override
    public String[] getInfoData() {
        return new String[]{
            "Diesel Engine",
            "Current Output: "+mEUt+" EU/t",
            "Fuel Consumption: "+fuelConsumption+"L/t",
            "Fuel Value: "+fuelValue+" EU/L",
            "Fuel Remaining: "+fuelRemaining+" Litres",
            "Current Efficiency: "+(mEfficiency/100)+"%"};
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }
}
