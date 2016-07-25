package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class GT_MetaTileEntity_OilDrill extends GT_MetaTileEntity_MultiBlockBase {

    private boolean completedCycle = false;

    public GT_MetaTileEntity_OilDrill(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_OilDrill(String aName) {
        super(aName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Oil Drilling Rig",
                "Size(WxHxD): 3x7x3", "Controller (Front middle at bottom)",
                "3x1x3 Base of Solid Steel Casings",
                "1x3x1 Solid Steel Casing pillar (Center of base)",
                "1x3x1 Steel Frame Boxes (Each Steel pillar side and on top)",
                "1x Output Hatch (One of base casings)",
                "1x Maintenance Hatch (One of base casings)",
                "1x Energy Hatch (One of base casings)"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_OIL_DRILL_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_OIL_DRILL)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "DrillingRig.png");
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        if (mInventory[1] == null || (mInventory[1].isItemEqual(GT_ModHandler.getIC2Item("miningPipe", 1L)) && mInventory[1].stackSize < mInventory[1].getMaxStackSize())) {
            ArrayList<ItemStack> tItems = getStoredInputs();
            for (ItemStack tStack : tItems) {
                if (tStack.isItemEqual(GT_ModHandler.getIC2Item("miningPipe", 1L))) {
                    if (tStack.stackSize < 2) {
                        tStack = null;
                    } else {
                        tStack.stackSize--;
                    }

                }
                if (mInventory[1] == null) {
                    mInventory[1] = GT_ModHandler.getIC2Item("miningPipe", 1L);
                } else {
                    mInventory[1].stackSize++;
                }
            }
        }
        FluidStack tFluid = GT_Utility.getUndergroundOil(getBaseMetaTileEntity().getWorld(), getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getZCoord());
        if (tFluid == null) {
            return false;
        }
        if (getBaseMetaTileEntity().getBlockOffset(ForgeDirection.getOrientation(getBaseMetaTileEntity().getBackFacing()).offsetX, getYOfPumpHead() - 1 - getBaseMetaTileEntity().getYCoord(), ForgeDirection.getOrientation(getBaseMetaTileEntity().getBackFacing()).offsetZ) != Blocks.bedrock) {
            if (completedCycle) {
                moveOneDown();
            }
            tFluid = null;
            if (mEnergyHatches.size() > 0 && mEnergyHatches.get(0).getEUVar() > (512 + getMaxInputVoltage() * 4))
                completedCycle = true;
        } else if (tFluid.amount < 5000) {
            return false;
        } else {
            tFluid.amount = tFluid.amount / 5000;
        }
        long tVoltage = getMaxInputVoltage();
        byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;
        int tEU = 24;
        int tDuration = 160;
        if (tEU <= 16) {
            this.mEUt = (tEU * (1 << tTier - 1) * (1 << tTier - 1));
            this.mMaxProgresstime = (tDuration / (1 << tTier - 1));
        } else {
            this.mEUt = tEU;
            this.mMaxProgresstime = tDuration;
            while (this.mEUt <= gregtech.api.enums.GT_Values.V[(tTier - 1)]) {
                this.mEUt *= 4;
                this.mMaxProgresstime /= 2;
            }
        }
        if (this.mEUt > 0) {
            this.mEUt = (-this.mEUt);
        }
        this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
        this.mOutputFluids = new FluidStack[]{tFluid};
        return true;
    }

    private boolean moveOneDown() {
        if ((this.mInventory[1] == null) || (this.mInventory[1].stackSize < 1)
                || (!GT_Utility.areStacksEqual(this.mInventory[1], GT_ModHandler.getIC2Item("miningPipe", 1L)))) {
            return false;
        }
        int xDir = ForgeDirection.getOrientation(getBaseMetaTileEntity().getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(getBaseMetaTileEntity().getBackFacing()).offsetZ;
        int yHead = getYOfPumpHead();
        if (yHead <= 0) {
            return false;
        }
        if (getBaseMetaTileEntity().getBlock(getBaseMetaTileEntity().getXCoord() + xDir, yHead - 1, getBaseMetaTileEntity().getZCoord() + zDir) == Blocks.bedrock) {
            return false;
        }
        if (!(getBaseMetaTileEntity().getWorld().setBlock(getBaseMetaTileEntity().getXCoord() + xDir, yHead - 1, getBaseMetaTileEntity().getZCoord() + zDir, GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipeTip", 1L))))) {
            return false;
        }
        if (yHead != getBaseMetaTileEntity().getYCoord()) {
            getBaseMetaTileEntity().getWorld().setBlock(getBaseMetaTileEntity().getXCoord() + xDir, yHead, getBaseMetaTileEntity().getZCoord() + zDir, GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipe", 1L)));
        }
        getBaseMetaTileEntity().decrStackSize(1, 1);
        return true;
    }

    private int getYOfPumpHead() {
        int xDir = ForgeDirection.getOrientation(getBaseMetaTileEntity().getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(getBaseMetaTileEntity().getBackFacing()).offsetZ;
        int y = getBaseMetaTileEntity().getYCoord() - 1;
        while (getBaseMetaTileEntity().getBlock(getBaseMetaTileEntity().getXCoord() + xDir, y, getBaseMetaTileEntity().getZCoord() + zDir) == GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipe", 1L))) {
            y--;
        }
        if (y == getBaseMetaTileEntity().getYCoord() - 1) {
            if (getBaseMetaTileEntity().getBlock(getBaseMetaTileEntity().getXCoord() + xDir, y, getBaseMetaTileEntity().getZCoord() + zDir) != GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipeTip", 1L))) {
                return y + 1;
            }
        } else if (getBaseMetaTileEntity().getBlock(getBaseMetaTileEntity().getXCoord() + xDir, y, getBaseMetaTileEntity().getZCoord() + zDir) != GT_Utility
                .getBlockFromStack(GT_ModHandler.getIC2Item("miningPipeTip", 1L)) && this.mInventory[1] != null && this.mInventory[1].stackSize > 0 && GT_Utility.areStacksEqual(this.mInventory[1], GT_ModHandler.getIC2Item("miningPipe", 1L))) {
            getBaseMetaTileEntity().getWorld().setBlock(getBaseMetaTileEntity().getXCoord() + xDir, y, getBaseMetaTileEntity().getZCoord() + zDir,
                    GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item("miningPipeTip", 1L)));
            getBaseMetaTileEntity().decrStackSize(0, 1);
        }
        return y;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetX;
        int zDir = ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()).offsetZ;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if ((xDir + i != 0) || (zDir + j != 0)) {
                    IGregTechTileEntity tTileEntity = aBaseMetaTileEntity.getIGregTechTileEntityOffset(xDir + i, 0, zDir + j);
                    if ((!addMaintenanceToMachineList(tTileEntity, 16)) && (!addInputToMachineList(tTileEntity, 16)) && (!addOutputToMachineList(tTileEntity, 16)) && (!addEnergyInputToMachineList(tTileEntity, 16))) {
                        if (aBaseMetaTileEntity.getBlockOffset(xDir + i, 0, zDir + j) != GregTech_API.sBlockCasings2) {
                            return false;
                        }
                        if (aBaseMetaTileEntity.getMetaIDOffset(xDir + i, 0, zDir + j) != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        for (int y = 1; y < 4; y++) {
            if (aBaseMetaTileEntity.getBlockOffset(xDir, y, zDir) != GregTech_API.sBlockCasings2) {
                return false;
            }
            if (aBaseMetaTileEntity.getBlockOffset(xDir + 1, y, zDir) != GregTech_API.sBlockMachines) {
                return false;
            }
            if (aBaseMetaTileEntity.getBlockOffset(xDir - 1, y, zDir) != GregTech_API.sBlockMachines) {
                return false;
            }
            if (aBaseMetaTileEntity.getBlockOffset(xDir, y, zDir + 1) != GregTech_API.sBlockMachines) {
                return false;
            }
            if (aBaseMetaTileEntity.getBlockOffset(xDir, y, zDir - 1) != GregTech_API.sBlockMachines) {
                return false;
            }
            if (aBaseMetaTileEntity.getBlockOffset(xDir, y + 3, zDir) != GregTech_API.sBlockMachines) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    @Override
    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    @Override
    public int getAmountOfOutputs() {
        return 0;
    }

    @Override
    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_OilDrill(this.mName);
    }

}