package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.GT_Block_GeneratedOres;
import ic2.core.block.machine.BlockMiningPipe;
import ic2.core.ref.BlockName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class GT_MetaTileEntity_AdvMiner2 extends GT_MetaTileEntity_MultiBlockBase {

    private static final ItemStack mining_pipe_item = GT_ModHandler.getIC2Item(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe, 1);
    private static final IBlockState mining_pipe = GT_ModHandler.getIC2BlockState(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe);
    private static final IBlockState mining_pipe_tip = GT_ModHandler.getIC2BlockState(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.tip);


    private final ArrayList<BlockPos> mMineList = new ArrayList();

    public GT_MetaTileEntity_AdvMiner2(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_AdvMiner2(String aName) {
        super(aName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller Block for the Advanced Miner II",
                "Size(WxHxD): 3x7x3, Controller (Front middle bottom)",
                "3x1x3 Base of Solid Steel Casings",
                "1x3x1 Solid Steel Casing pillar (Center of base)",
                "1x3x1 Steel Frame Boxes (Each Steel pillar side and on top)",
                "1x Input Hatch (Any bottom layer casing)",
                "1x Output Bus (Any bottom layer casing)",
                "1x Maintenance Hatch (Any bottom layer casing)",
                "1x MV+ Energy Hatch (Any bottom layer casing)"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_ADVMINER2_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_ADVMINER2)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[16]};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "AdvMiner2.png");
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        if (mInventory[1] == null || (mInventory[1].isItemEqual(mining_pipe_item) && mInventory[1].stackSize < mInventory[1].getMaxStackSize())) {
            ArrayList<ItemStack> tItems = getStoredInputs();
            for (ItemStack tStack : tItems) {
                if (tStack.isItemEqual(mining_pipe_item)) {
                    tStack.stackSize--;
                    if (tStack.stackSize < 1) {
                    }
                    if (mInventory[1] == null) {
                        mInventory[1] = mining_pipe_item;
                    } else {
                        mInventory[1].stackSize++;
                    }
                }

            }
        }
        if (mInputHatches == null || mInputHatches.get(0).mFluid == null || mInputHatches.get(0).mFluid.getFluid() != ItemList.sDrillingFluid) {
            return false;
        }
        FluidStack tFluid = mInputHatches.get(0).mFluid.copy();
        if (tFluid == null) {
            return false;
        }
        if (tFluid.amount < 100) {
            return false;
        }
        tFluid.amount = 100;
        depleteInput(tFluid);
        long tVoltage = getMaxInputVoltage();
        if (getBaseMetaTileEntity().getRandomNumber(20) == 0) {
            if (mMineList.isEmpty()) {
                int yLevel = getYOfPumpHead();
                for (int i = -48; i < 49; i++) {
                    for (int f = -48; f < 49; f++) {
                        Block tBlock = getBaseMetaTileEntity().getBlockOffset(i, yLevel - getBaseMetaTileEntity().getYCoord(), f);
                        int tMetaID = getBaseMetaTileEntity().getMetaIDOffset(i, yLevel - getBaseMetaTileEntity().getYCoord(), f);
                        if (tBlock instanceof GT_Block_GeneratedOres) {
                            if (tBlock instanceof GT_Block_GeneratedOres && !mMineList.contains(new BlockPos(i, yLevel - getBaseMetaTileEntity().getYCoord(), f))) {
                                mMineList.add(new BlockPos(i, yLevel - getBaseMetaTileEntity().getYCoord(), f));
                            }
                        } else {
                            ItemData tAssotiation = GT_OreDictUnificator.getAssociation(new ItemStack(tBlock, 1, tMetaID));
                            if ((tAssotiation != null) && (tAssotiation.mPrefix.toString().startsWith("ore"))) {
                                BlockPos cp = new BlockPos(i, yLevel - getBaseMetaTileEntity().getYCoord(), f);
                                if (!mMineList.contains(cp)) {
                                    mMineList.add(cp);
                                }
                            }
                        }
                    }
                }
            }
            if (mMineList.isEmpty()) {
                if(getBaseMetaTileEntity().getBlockOffset(EnumFacing.VALUES[getBaseMetaTileEntity().getBackFacing()].getFrontOffsetX(), getYOfPumpHead() - 1 - getBaseMetaTileEntity().getYCoord(), EnumFacing.VALUES[getBaseMetaTileEntity().getBackFacing()].getFrontOffsetZ()) != Blocks.BEDROCK){
                    if (mEnergyHatches.size() > 0 && mEnergyHatches.get(0).getEUVar() > (512 + getMaxInputVoltage() * 4)) {
                        moveOneDown();
                    }
                }else{
                    stopMachine();
                    return false;
                }
            }
            List<ItemStack> tDrops = new ArrayList();
            Block tMineBlock = null;
            BlockPos mle = null;
            while ((tMineBlock==null || tMineBlock == Blocks.AIR) && !mMineList.isEmpty()) {
                mle = mMineList.get(0);
                mMineList.remove(0);
                tMineBlock = getBaseMetaTileEntity().getBlockOffset(mle.getX(), mle.getY(), mle.getZ());
            }

            if (tMineBlock!=null && tMineBlock!=Blocks.AIR) {
                BlockPos pos = new BlockPos(mle.getX() + getBaseMetaTileEntity().getXCoord(),
                                            mle.getY() + getBaseMetaTileEntity().getYCoord(),
                                            mle.getZ() + getBaseMetaTileEntity().getZCoord());


                IBlockState blockState = getBaseMetaTileEntity().getWorldObj().getBlockState(pos);
                boolean silkTouch = tMineBlock.canSilkHarvest(getBaseMetaTileEntity().getWorldObj(), pos, blockState, null);
                if (silkTouch){
                    ItemStack IS = new ItemStack(tMineBlock);
                    IS.setItemDamage(blockState.getBlock().getMetaFromState(blockState));
                    IS.stackSize=1;
                    tDrops.add(IS);
                }
                else{
                    tDrops = tMineBlock.getDrops(getBaseMetaTileEntity().getWorldObj(), pos, blockState, 1);
                }

                getBaseMetaTileEntity().getWorldObj().setBlockToAir(pos);
                if (!tDrops.isEmpty()) {
                    ItemData tData = GT_OreDictUnificator.getItemData(tDrops.get(0).copy());
                    if (tData.mPrefix != OrePrefixes.crushed && tData.mMaterial.mMaterial != Materials.Oilsands) {

                        GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.findRecipe(getBaseMetaTileEntity(), false, tVoltage, null, tDrops.get(0).copy());
                        if (tRecipe != null) {
                            this.mOutputItems = new ItemStack[tRecipe.mOutputs.length];
                            for (int g = 0; g < mOutputItems.length; g++) {
                                mOutputItems[g] = tRecipe.mOutputs[g].copy();
                                if (getBaseMetaTileEntity().getRandomNumber(10000) < tRecipe.getOutputChance(g)) {
                                    mOutputItems[g].stackSize *= getBaseMetaTileEntity().getRandomNumber(4) + 1;
                                }
                            }
                        } else {
                            this.mOutputItems = new ItemStack[tDrops.size()];
                            for (int g = 0; g < mOutputItems.length; g++) {
                                mOutputItems[g] = tDrops.get(g).copy();
                            }
                        }
                    } else {
                        this.mOutputItems = null;
                        ItemStack[] tStack = new ItemStack[tDrops.size()];
                        for (int j = 0; j < tDrops.size(); j++) {
                            tStack[j] = tDrops.get(j).copy();
                            tStack[j].stackSize = tStack[j].stackSize * (getBaseMetaTileEntity().getRandomNumber(4) + 1);
                        }
                        mOutputItems = tStack;
                    }
                }

            }
        }

        byte tTier = (byte) Math.max(1, GT_Utility.getTier(tVoltage));
        this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;
        int tEU = 48;
        int tDuration = 24;
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
        return true;
    }

    private boolean moveOneDown() {
        if ((this.mInventory[1] == null) || (this.mInventory[1].stackSize < 1)
                || (!GT_Utility.areStacksEqual(this.mInventory[1], mining_pipe_item))) {
            stopMachine();
            return false;
        }
        int xDir = EnumFacing.VALUES[getBaseMetaTileEntity().getBackFacing()].getFrontOffsetX();
        int zDir = EnumFacing.VALUES[getBaseMetaTileEntity().getBackFacing()].getFrontOffsetZ();
        int yHead = getYOfPumpHead();
        if (yHead <= 0) {
            stopMachine();
            return false;
        }
        if (getBaseMetaTileEntity().getBlock(getBaseMetaTileEntity().getXCoord() + xDir, yHead - 1, getBaseMetaTileEntity().getZCoord() + zDir) == Blocks.BEDROCK) {
            stopMachine();
            return false;
        }
        if (!(getBaseMetaTileEntity().getWorldObj().setBlockState(
                new BlockPos(getBaseMetaTileEntity().getXCoord() + xDir, yHead - 1, getBaseMetaTileEntity().getZCoord() + zDir), mining_pipe_tip))) {
            stopMachine();
            return false;
        }
        if (yHead != getBaseMetaTileEntity().getYCoord()) {
            getBaseMetaTileEntity().getWorldObj().setBlockState(
                    new BlockPos(getBaseMetaTileEntity().getXCoord() + xDir, yHead, getBaseMetaTileEntity().getZCoord() + zDir), mining_pipe);
        }
        getBaseMetaTileEntity().decrStackSize(1, 1);
        return true;
    }

    private int getYOfPumpHead() {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(getBaseMetaTileEntity().getWorldPos());
        pos.move(EnumFacing.VALUES[getBaseMetaTileEntity().getBackFacing()]);
        pos.move(EnumFacing.DOWN);

        while (getBaseMetaTileEntity().getBlockState(pos).equals(mining_pipe)) {
            pos.move(EnumFacing.DOWN);
        }
        if (pos.getY() == getBaseMetaTileEntity().getYCoord() - 1) {
            if (!getBaseMetaTileEntity().getBlockState(pos).equals(mining_pipe_tip)) {
                return pos.getY() + 1;
            }
        } else if (!getBaseMetaTileEntity().getBlockState(pos).equals(mining_pipe_tip)
                && this.mInventory[1] != null
                && this.mInventory[1].stackSize > 0
                && GT_Utility.areStacksEqual(this.mInventory[1], mining_pipe_item)) {
            getBaseMetaTileEntity().getWorldObj().setBlockState(pos, mining_pipe_tip);
            getBaseMetaTileEntity().decrStackSize(0, 1);
        }
        return pos.getY();
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        int xDir = EnumFacing.VALUES[aBaseMetaTileEntity.getBackFacing()].getFrontOffsetX();
        int zDir = EnumFacing.VALUES[aBaseMetaTileEntity.getBackFacing()].getFrontOffsetZ();
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
        return new GT_MetaTileEntity_AdvMiner2(this.mName);
    }

}