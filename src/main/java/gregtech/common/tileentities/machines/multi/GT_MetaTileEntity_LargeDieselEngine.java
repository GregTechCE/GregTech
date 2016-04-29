package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collection;

public class GT_MetaTileEntity_LargeDieselEngine extends GT_MetaTileEntity_MultiBlockBase {
    boolean firstRun = true;
    boolean hasLubricant = false;
    boolean hasCoolant = false;
    boolean hasOxygen = false;

    public GT_MetaTileEntity_LargeDieselEngine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }
    public GT_MetaTileEntity_LargeDieselEngine(String aName) {
        super(aName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Large Diesel Engine",
                "Size: 3x3x3",
                "Controller (front centered)",
                "3x3x3 of Stable Titanium Casing (hollow, Min 24!)",
                "1x Titanium Pipe Casing Block inside the Hollow Casing",
                "3x Input Hatch (one of the Casings)",
                "1x Maintenance Hatch (one of the Casings)",
                "1x Muffler Hatch (one of the Casings)",
                "1x Dynamo Hatch (back centered)"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_LARGE_BOILER)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[50]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "LargeDieselEngine.png");
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean checkRecipe(ItemStack aStack) {
        ArrayList<FluidStack> tFluids = getStoredFluids();
        Collection<GT_Recipe> tRecipeList = GT_Recipe.GT_Recipe_Map.sDieselFuels.mRecipeList;
        hasLubricant = false;
        hasCoolant = false;
        hasOxygen = false;

        if(tFluids.size() > 0 && tRecipeList != null) {

            for (FluidStack hatchFluid1 : tFluids) { //Loops through hatches
                for(GT_Recipe aFuel : tRecipeList) { //Loops through diesel fuel recipes
                    FluidStack tLiquid;
                    if ((tLiquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null) {
                        if (hatchFluid1.isFluidEqual(tLiquid)) {
                            for (FluidStack hatchFluid2 : tFluids) {
                                if(hatchFluid2.isFluidEqual(Materials.Lubricant.getFluid(1L))) hasLubricant = true;
                                if(hatchFluid2.isFluidEqual(FluidRegistry.getFluidStack("ic2coolant", 1))) hasCoolant = true;
                                if(hatchFluid2.isFluidEqual(Materials.Oxygen.getGas(1L))) {
                                    if(hatchFluid2.amount >= aFuel.mSpecialValue / 10) {
                                        hasOxygen = true;
                                    }
                                }
                            }

                            System.out.println("Fuel Value: "+aFuel.mSpecialValue);

                            //Deplete Oxygen
                            if(hasOxygen) depleteInput(Materials.Oxygen.getGas(aFuel.mSpecialValue / 10));

                            //Deplete coolant every IRL hour
                            //Create Maintenance issues every hour if there is no coolant
                            if(hasCoolant) {
                                int amount = hasOxygen ? 3 ^ (mEfficiency / 10000) : (mEfficiency / 10000);
                                if(firstRun) {
                                    depleteInput(FluidRegistry.getFluidStack("ic2coolant", amount));
                                } else if(mRuntime % 720 == 0) {
                                    depleteInput(FluidRegistry.getFluidStack("ic2coolant", amount));
                                }
                            } else {
                                doRandomMaintenanceDamage();
                            }

                            //Deplete Lubricant every 12096 ticks or 10.08 minutes (Every 1000L should last an IRL week)
                            if(hasLubricant) {
                                if(firstRun) {
                                    depleteInput(Materials.Lubricant.getFluid(1L));
                                } else if(mRuntime % 12096 == 0) {
                                    depleteInput(Materials.Lubricant.getFluid(1L));
                                }
                            } else {
                                //Negative Effect?
                            }

                            tLiquid.amount = 1;
                            depleteInput(tLiquid);

                            mProgresstime = 1;
                            mMaxProgresstime = 1;
                            mEfficiencyIncrease = 10;

                            int baseEUt = 768;
                            mEUt = (baseEUt + (aFuel.mSpecialValue * 10) * mEfficiency / 10000);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (mProgresstime > 0 && firstRun) {
            firstRun = false;
            try {
                //Implement Achievement
                //GT_Mod.instance.achievements.issueAchievement(aBaseMetaTileEntity.getWorld().getPlayerEntityByName(aBaseMetaTileEntity.getOwnerName()), "extremepressure");
            } catch (Exception e) {
            }
        }
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        if (aBaseMetaTileEntity.getBlockOffset(xDir, 0, zDir) != getPipeBlock()) {
            return false;
        }
        if (aBaseMetaTileEntity.getMetaIDOffset(xDir, 0, zDir) != getPipeMeta()) {
            return false;
        }
        int tAmount = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int h = -1; h < 2; h++) {
                    if ((h != 0) || (((xDir + i != 0) || (zDir + j != 0)) && ((i != 0) || (j != 0)))) {
                        IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
                        if ((!addMaintenanceToMachineList(tTileEntity, 50)) && (!addMufflerToMachineList(tTileEntity, 50)) && (!addInputToMachineList(tTileEntity, 50)) && (!addInputToMachineList(tTileEntity, 50)) && (!addInputToMachineList(tTileEntity, 50)) && (!addInputToMachineList(tTileEntity, 50)) && (!addOutputToMachineList(tTileEntity, 50)) && (!addDynamoToMachineList(tTileEntity, 50))) {
                            Block tBlock = aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j);
                            byte tMeta = aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j);
                            if (((tBlock != GregTech_API.sBlockCasings4) || (tMeta != 2))) {
                                return false;
                            }
                            tAmount++;
                        }
                    }
                }
            }
        }
        return tAmount >= 16;
    }

    public Block getCasingBlock() {
        return GregTech_API.sBlockCasings4;
    }

    public byte getCasingMeta() {
        return 2;
    }

    public byte getCasingTextureIndex() {
        return 50;
    }

    public Block getPipeBlock() {
        return GregTech_API.sBlockCasings2;
    }

    public byte getPipeMeta() {
        return 14;
    }

    public int getMaxEfficiency(ItemStack aStack) {
        return hasOxygen ? 14500 : 10000;
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
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

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_LargeDieselEngine(this.mName);
    }

    @Override
    public String[] getInfoData() {
        return new String[]{
                "Large Diesel Engine",
                "Efficiency: " + (float) mEfficiency / 100 + "%",
                "EfficiencyRaw: " + mEfficiency,
                "Current Output: " + mEUt + " EU/t"
        };
    }
}
