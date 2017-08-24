package gregtech.common.items.behaviors;

import gregtech.api.util.GT_LanguageManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class Behaviour_SoftHammer
        extends Behaviour_None {
    private final int mCosts;
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.softhammer", "Activates and Deactivates Machines");

    public Behaviour_SoftHammer(int aCosts) {
        this.mCosts = aCosts;
    }

    @Override
    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (aWorld.isRemote || aWorld.isAirBlock(blockPos)) {
            return false;
        }
        /*IBlockState blockState = aWorld.getBlockState(blockPos);
        byte aMeta = (byte) aWorld.getBlockMetadata(aX, aY, aZ);
        if (blockState.getBlock() == Blocks.LIT_REDSTONE_LAMP) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.setBlockState(blockPos, Blocks.REDSTONE_LAMP.getDefaultState());
                GTUtility.sendSoundToPlayers(aWorld, GregTechAPI.sSoundList.get(101), 1.0F, -1.0F, blockPos);
            }
            return true;
        }
        if (blockState.getBlock() == Blocks.REDSTONE_LAMP) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.setBlockState(blockPos, Blocks.LIT_REDSTONE_LAMP.getDefaultState());
                GTUtility.sendSoundToPlayers(aWorld, GregTechAPI.sSoundList.get(101), 1.0F, -1.0F, blockPos);
            }
            return true;
        }
        if (aBlock == Blocks.golden_rail) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.isRemote = true;
                aWorld.setBlock(aX, aY, aZ, aBlock, (aMeta + 8) % 16, 0);
                aWorld.isRemote = false;
                GTUtility.sendSoundToPlayers(aWorld, (String) GregTechAPI.sSoundList.get(101), 1.0F, -1.0F, aX, aY, aZ);
            }
            return true;
        }
        if (blockState.getBlock() instanceof BlockRailBase) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                BlockRailBase railBase = (BlockRailBase) blockState.getBlock();
                IProperty<BlockRailBase.EnumRailDirection> directionProperty = railBase.getShapeProperty();
                int rotational = blockState.getValue(directionProperty).ordinal();
                blockState = blockState.withProperty(directionProperty,
                        BlockRailBase.EnumRailDirection.values()[
                        rotational == 9 ? 0 : rotational + 1]);
                GTUtility.sendSoundToPlayers(aWorld, GregTechAPI.sSoundList.get(101), 1.0F, -1.0F, blockPos);
            }
            return true;
        }
        if ((aBlock == Blocks.log) || (aBlock == Blocks.log2) || (aBlock == Blocks.hay_block)) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (aMeta + 4) % 12, 3);
            }
            return true;
        }
        if ((aBlock == Blocks.piston) || (aBlock == Blocks.sticky_piston) || (aBlock == Blocks.dispenser) || (aBlock == Blocks.dropper)) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (aMeta + 1) % 6, 3);
                GTUtility.sendSoundToPlayers(aWorld, (String) GregTechAPI.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
            }
            return true;
        }
        if ((aBlock == Blocks.pumpkin) || (aBlock == Blocks.lit_pumpkin) || (aBlock == Blocks.furnace) || (aBlock == Blocks.lit_furnace) || (aBlock == Blocks.chest) || (aBlock == Blocks.trapped_chest)) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (aMeta - 1) % 4 + 2, 3);
                GTUtility.sendSoundToPlayers(aWorld, (String) GregTechAPI.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
            }
            return true;
        }
        if (aBlock == Blocks.hopper) {
            if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (aMeta + 1) % 6 == 1 ? (aMeta + 1) % 6 : 2, 3);
                GTUtility.sendSoundToPlayers(aWorld, (String) GregTechAPI.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
            }
            return true;
        }*/
        return false;
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        return aList;
    }
}
