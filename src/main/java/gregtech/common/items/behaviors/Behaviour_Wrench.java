package gregtech.common.items.behaviors;

import gregtech.api.GregTech_API;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import ic2.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;
import java.util.List;

public class Behaviour_Wrench
        extends Behaviour_None {
    private final int mCosts;
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.wrench", "Rotates Blocks on Rightclick");

    public Behaviour_Wrench(int aCosts) {
        this.mCosts = aCosts;
    }

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if (aWorld.isRemote) {
            return false;
        }
        Block aBlock = aWorld.getBlock(aX, aY, aZ);
        if (aBlock == null) {
            return false;
        }
        byte aMeta = (byte) aWorld.getBlockMetadata(aX, aY, aZ);
        byte aTargetSide = GT_Utility.determineWrenchingSide((byte) aSide, hitX, hitY, hitZ);
        TileEntity aTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        try {
            if (((aTileEntity instanceof IWrenchable))) {
                if (((IWrenchable) aTileEntity).wrenchCanSetFacing(aPlayer, aTargetSide)) {
                    if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                        ((IWrenchable) aTileEntity).setFacing((short) aTargetSide);
                        GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
                    }
                    return true;
                }
                if (((IWrenchable) aTileEntity).wrenchCanRemove(aPlayer)) {
                    int tDamage = ((IWrenchable) aTileEntity).getWrenchDropRate() < 1.0F ? 10 : 3;
                    if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, tDamage * this.mCosts))) {
                        ItemStack tOutput = ((IWrenchable) aTileEntity).getWrenchDrop(aPlayer);
                        for (ItemStack tStack : aBlock.getDrops(aWorld, aX, aY, aZ, aMeta, 0)) {
                            if (tOutput == null) {
                                aWorld.spawnEntityInWorld(new EntityItem(aWorld, aX + 0.5D, aY + 0.5D, aZ + 0.5D, tStack));
                            } else {
                                aWorld.spawnEntityInWorld(new EntityItem(aWorld, aX + 0.5D, aY + 0.5D, aZ + 0.5D, tOutput));
                                tOutput = null;
                            }
                        }
                        aWorld.setBlockToAir(aX, aY, aZ);
                        GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
                    }
                    return true;
                }
                return true;
            }
        } catch (Throwable e) {
        }
        if ((aBlock == Blocks.log) || (aBlock == Blocks.log2) || (aBlock == Blocks.hay_block)) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (aMeta + 4) % 12, 3);
                GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
            }
            return true;
        }
        if ((aBlock == Blocks.powered_repeater) || (aBlock == Blocks.unpowered_repeater)) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.setBlockMetadataWithNotify(aX, aY, aZ, aMeta / 4 * 4 + (aMeta % 4 + 1) % 4, 3);
                GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
            }
            return true;
        }
        if ((aBlock == Blocks.powered_comparator) || (aBlock == Blocks.unpowered_comparator)) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.setBlockMetadataWithNotify(aX, aY, aZ, aMeta / 4 * 4 + (aMeta % 4 + 1) % 4, 3);
                GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
            }
            return true;
        }
        if ((aBlock == Blocks.crafting_table) || (aBlock == Blocks.bookshelf)) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.spawnEntityInWorld(new EntityItem(aWorld, aX + 0.5D, aY + 0.5D, aZ + 0.5D, new ItemStack(aBlock, 1, aMeta)));
                aWorld.setBlockToAir(aX, aY, aZ);
                GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
            }
            return true;
        }
        if (aMeta == aTargetSide) {
            if ((aBlock == Blocks.pumpkin) || (aBlock == Blocks.lit_pumpkin) || (aBlock == Blocks.piston) || (aBlock == Blocks.sticky_piston) || (aBlock == Blocks.dispenser) || (aBlock == Blocks.dropper) || (aBlock == Blocks.furnace) || (aBlock == Blocks.lit_furnace) || (aBlock == Blocks.chest) || (aBlock == Blocks.trapped_chest) || (aBlock == Blocks.ender_chest) || (aBlock == Blocks.hopper)) {
                if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                    aWorld.spawnEntityInWorld(new EntityItem(aWorld, aX + 0.5D, aY + 0.5D, aZ + 0.5D, new ItemStack(aBlock, 1, 0)));
                    aWorld.setBlockToAir(aX, aY, aZ);
                    GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
                }
                return true;
            }
        } else {
            if ((aBlock == Blocks.piston) || (aBlock == Blocks.sticky_piston) || (aBlock == Blocks.dispenser) || (aBlock == Blocks.dropper)) {
                if ((aMeta < 6) && ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts)))) {
                    aWorld.setBlockMetadataWithNotify(aX, aY, aZ, aTargetSide, 3);
                    GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
                }
                return true;
            }
            if ((aBlock == Blocks.pumpkin) || (aBlock == Blocks.lit_pumpkin) || (aBlock == Blocks.furnace) || (aBlock == Blocks.lit_furnace) || (aBlock == Blocks.chest) || (aBlock == Blocks.ender_chest) || (aBlock == Blocks.trapped_chest)) {
                if ((aTargetSide > 1) && ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts)))) {
                    aWorld.setBlockMetadataWithNotify(aX, aY, aZ, aTargetSide, 3);
                    GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
                }
                return true;
            }
            if (aBlock == Blocks.hopper) {
                if ((aTargetSide != 1) && ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts)))) {
                    aWorld.setBlockMetadataWithNotify(aX, aY, aZ, aTargetSide, 3);
                    GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
                }
                return true;
            }
        }
        if ((Arrays.asList(aBlock.getValidRotations(aWorld, aX, aY, aZ)).contains(ForgeDirection.getOrientation(aTargetSide))) &&
                ((aPlayer.capabilities.isCreativeMode) || (!GT_ModHandler.isElectricItem(aStack)) || (GT_ModHandler.canUseElectricItem(aStack, this.mCosts))) &&
                (aBlock.rotateBlock(aWorld, aX, aY, aZ, ForgeDirection.getOrientation(aTargetSide)))) {
            if (!aPlayer.capabilities.isCreativeMode) {
                ((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts);
            }
            GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(100)), 1.0F, -1.0F, aX, aY, aZ);
        }
        return false;
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        return aList;
    }
}
