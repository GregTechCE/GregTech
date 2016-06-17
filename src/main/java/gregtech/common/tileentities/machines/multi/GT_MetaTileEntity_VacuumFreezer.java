package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class GT_MetaTileEntity_VacuumFreezer
        extends GT_MetaTileEntity_MultiBlockBase {
    public GT_MetaTileEntity_VacuumFreezer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_VacuumFreezer(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_VacuumFreezer(this.mName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Vacuum Freezer",
                "Super cools hot ingots and cells",
                "Size(WxHxD): 3x3x3 (Hollow), Controller (Front centered)",
                "1x Input Bus (Any casing)",
                "1x Output Bus (Any casing)",
                "1x Maintenance Hatch (Any casing)",
                "1x Energy Hatch (Any casing)",
                "Frost Proof Casings for the rest (16 at least!)"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[17], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_VACUUM_FREEZER)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[17]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "VacuumFreezer.png");
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_Recipe.GT_Recipe_Map.sVacuumRecipes;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean checkRecipe(ItemStack aStack) {
        ArrayList<ItemStack> tInputList = getStoredInputs();
        for (ItemStack tInput : tInputList) {
            long tVoltage = getMaxInputVoltage();
            byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));

            GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sVacuumRecipes.findRecipe(getBaseMetaTileEntity(), false, gregtech.api.enums.GT_Values.V[tTier], null, new ItemStack[]{tInput});
            if (tRecipe != null) {
                if (tRecipe.isRecipeInputEqual(true, null, new ItemStack[]{tInput})) {
                    this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
                    this.mEfficiencyIncrease = 10000;
                    if (tRecipe.mEUt <= 16) {
                        this.mEUt = (tRecipe.mEUt * (1 << tTier - 1) * (1 << tTier - 1));
                        this.mMaxProgresstime = (tRecipe.mDuration / (1 << tTier - 1));
                    } else {
                        this.mEUt = tRecipe.mEUt;
                        this.mMaxProgresstime = tRecipe.mDuration;
                        while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)]) {
                            this.mEUt *= 4;
                            this.mMaxProgresstime /= 2;
                        }
                    }
                    if (this.mEUt > 0) {
                        this.mEUt = (-this.mEUt);
                    }
                    this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
                    this.mOutputItems = new ItemStack[]{tRecipe.getOutput(0)};
                    updateSlots();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        if (!aBaseMetaTileEntity.getAirOffset(xDir, 0, zDir)) {
            return false;
        }
        int tAmount = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int h = -1; h < 2; h++) {
                    if ((h != 0) || (((xDir + i != 0) || (zDir + j != 0)) && ((i != 0) || (j != 0)))) {
                        IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, h, zDir + j);
                        if ((!addMaintenanceToMachineList(tTileEntity, 17)) && (!addInputToMachineList(tTileEntity, 17)) && (!addOutputToMachineList(tTileEntity, 17)) && (!addEnergyInputToMachineList(tTileEntity, 17))) {
                            if (aBaseMetaTileEntity.getBlockOffset(xDir + i, h, zDir + j) != GregTech_API.sBlockCasings2) {
                                return false;
                            }
                            if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, h, zDir + j) != 1) {
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

    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
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
}