package gregtech.common.tileentities.machines.multi;

import gregtech.api.GregTech_API;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;

import java.util.ArrayList;

public class GT_MetaTileEntity_Charcoal_Pit extends GT_MetaTileEntity_MultiBlockBase {

    private boolean running = false;
    private boolean p1, p2, p3, p4, p5, p6;

    public GT_MetaTileEntity_Charcoal_Pit(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GT_MetaTileEntity_Charcoal_Pit(String aName) {
        super(aName);
    }

    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_Charcoal_Pit(this.mName);
    }

    public String[] getDescription() {
        return new String[]{
                "Controller for the Charcoal Pit",
                "Converts Logs into Brittle Charcoal blocks",
                "Max Size(WxHxD): 11x6x11, Controller (Top layer, centered)",
                "11x1x11 of Bricks (Bottom layer only)",
                "11x5x11 of Logs (Above bottom Brick layer)",
                "Only grass/dirt can touch Log blocks",
                "No air between logs allowed"};
    }

    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == 1) {
            return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10], new GT_RenderedTexture(aActive ? Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER)};
        }
        return new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[10]};
    }

    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return null;
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isClientSide()) return true;

        return true;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public boolean isFacingValid(byte aFacing) {
        return aFacing > 1;
    }

    public boolean checkRecipe(ItemStack aStack) {
        if (!checkRecursiveBlocks()) {
            return false;
        }

        if (mEfficiency == 0) {
            this.mEfficiency = 10000;
            this.mEfficiencyIncrease = 10000;
            this.mMaxProgresstime = Math.max(1, this.mMaxProgresstime);
            return true;
        } else {
            this.mEfficiency = 0;
            this.mEfficiencyIncrease = 0;
            this.mMaxProgresstime = 0;
        }
        return false;
    }

    private boolean checkRecursiveBlocks() {
        ArrayList<ChunkPosition> tList1 = new ArrayList();
        ArrayList<ChunkPosition> tList2 = new ArrayList();

        Block tBlock = this.getBaseMetaTileEntity().getBlockOffset(0, -1, 0);
        if (!isWoodLog(tBlock)) {
            return false;
        } else {
            tList2.add(new ChunkPosition(0, -1, 0));
        }
        while (!tList2.isEmpty()) {
            ChunkPosition tPos = (ChunkPosition) tList2.get(0);
            tList2.remove(0);
            if (!checkAllBlockSides(tPos.chunkPosX, tPos.chunkPosY, tPos.chunkPosZ, tList1, tList2)) {
                return false;
            }
        }
        if (running) {
            for (ChunkPosition tPos : tList1) {
                if (isWoodLog(this.getBaseMetaTileEntity().getBlockOffset(tPos.chunkPosX, tPos.chunkPosY, tPos.chunkPosZ)))
                    this.getBaseMetaTileEntity().getWorld().setBlock(this.getBaseMetaTileEntity().getXCoord() + tPos.chunkPosX, this.getBaseMetaTileEntity().getYCoord() + tPos.chunkPosY, this.getBaseMetaTileEntity().getZCoord() + tPos.chunkPosZ, GregTech_API.sBlockReinforced, 4, 3);
            }
            running = false;
            return false;
        } else {
            this.mMaxProgresstime = (int) Math.sqrt(tList1.size() * 240000);
        }
        running = true;
        return true;
    }

    private boolean checkAllBlockSides(int aX, int aY, int aZ, ArrayList<ChunkPosition> aList1, ArrayList<ChunkPosition> aList2) {
        p1 = false;
        p2 = false;
        p3 = false;
        p4 = false;
        p5 = false;
        p6 = false;
        Block tBlock = this.getBaseMetaTileEntity().getBlockOffset(aX + 1, aY, aZ);
        if (aX + 1 < 6 && (isWoodLog(tBlock))) {
            if (!aList1.contains(new ChunkPosition(aX + 1, aY, aZ)) && (!aList2.contains(new ChunkPosition(aX + 1, aY, aZ))))
                p1 = true;
        } else if (!(tBlock == Blocks.dirt || tBlock == Blocks.grass)) {
            return false;
        }

        tBlock = this.getBaseMetaTileEntity().getBlockOffset(aX - 1, aY, aZ);
        if (aX - 1 > -6 && (isWoodLog(tBlock))) {
            if (!aList1.contains(new ChunkPosition(aX - 1, aY, aZ)) && (!aList2.contains(new ChunkPosition(aX - 1, aY, aZ))))
                p2 = true;
        } else if (!(tBlock == Blocks.dirt || tBlock == Blocks.grass)) {
            return false;
        }

        tBlock = this.getBaseMetaTileEntity().getBlockOffset(aX, aY + 1, aZ);
        if (aY + 1 < 1 && (isWoodLog(tBlock))) {
            if (!aList1.contains(new ChunkPosition(aX, aY + 1, aZ)) && (!aList2.contains(new ChunkPosition(aX, aY + 1, aZ))))
                p3 = true;
        } else if (!(tBlock == Blocks.dirt || tBlock == Blocks.grass || (aX == 0 && aY == -1 && aZ == 0 && tBlock == GregTech_API.sBlockMachines))) {
            return false;
        }

        tBlock = this.getBaseMetaTileEntity().getBlockOffset(aX, aY - 1, aZ);
        if (aY - 1 > -6 && (isWoodLog(tBlock))) {
            if (!aList1.contains(new ChunkPosition(aX, aY - 1, aZ)) && (!aList2.contains(new ChunkPosition(aX, aY - 1, aZ))))
                p4 = true;
        } else if (tBlock != Blocks.brick_block) {
            return false;
        }

        tBlock = this.getBaseMetaTileEntity().getBlockOffset(aX, aY, aZ + 1);
        if (aZ + 1 < 6 && (isWoodLog(tBlock))) {
            if (!aList1.contains(new ChunkPosition(aX, aY, aZ + 1)) && (!aList2.contains(new ChunkPosition(aX, aY, aZ + 1))))
                p5 = true;
        } else if (!(tBlock == Blocks.dirt || tBlock == Blocks.grass)) {
            return false;
        }

        tBlock = this.getBaseMetaTileEntity().getBlockOffset(aX, aY, aZ - 1);
        if (aZ - 1 > -6 && (isWoodLog(tBlock))) {
            if (!aList1.contains(new ChunkPosition(aX, aY, aZ - 1)) && (!aList2.contains(new ChunkPosition(aX, aY, aZ - 1))))
                p6 = true;
        } else if (!(tBlock == Blocks.dirt || tBlock == Blocks.grass)) {
            return false;
        }
        aList1.add(new ChunkPosition(aX, aY, aZ));
        if (p1) aList2.add(new ChunkPosition(aX + 1, aY, aZ));
        if (p2) aList2.add(new ChunkPosition(aX - 1, aY, aZ));
        if (p3) aList2.add(new ChunkPosition(aX, aY + 1, aZ));
        if (p4) aList2.add(new ChunkPosition(aX, aY - 1, aZ));
        if (p5) aList2.add(new ChunkPosition(aX, aY, aZ + 1));
        if (p6) aList2.add(new ChunkPosition(aX, aY, aZ - 1));
        return true;
    }

    public boolean isWoodLog(Block log){
        String tTool = log.getHarvestTool(0);
        return  OrePrefixes.log.contains(new ItemStack(log, 1))&& ((tTool != null) && (tTool.equals("axe"))) || (log.getMaterial() == Material.wood);
    }

    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        mWrench = true;
        mScrewdriver = true;
        mSoftHammer = true;
        mHardHammer = true;
        mSolderingTool = true;
        mCrowbar = true;
        return true;
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