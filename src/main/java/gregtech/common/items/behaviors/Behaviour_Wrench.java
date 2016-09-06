package gregtech.common.items.behaviors;

import gregtech.api.GregTech_API;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import ic2.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class Behaviour_Wrench
        extends Behaviour_None {
    private final int mCosts;
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.wrench", "Rotates Blocks on Rightclick");

    public Behaviour_Wrench(int aCosts) {
        this.mCosts = aCosts;
    }

    @Override
    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos blockPos, EnumFacing aSide, float hitX, float hitY, float hitZ, EnumHand hand) {
        if(!aWorld.isRemote && !aWorld.isAirBlock(blockPos)) {
            TileEntity tileEntity = aWorld.getTileEntity(blockPos);
            if(tileEntity instanceof IWrenchable) {
                IWrenchable wrenchable = (IWrenchable) tileEntity;
                if(aPlayer.isSneaking()) {
                    if (wrenchable.wrenchCanRemove(aWorld, blockPos, aPlayer)) {
                        List<ItemStack> wrenchDrops = wrenchable.getWrenchDrops(aWorld, blockPos, aWorld.getBlockState(blockPos), tileEntity, aPlayer, 0);
                        for(ItemStack wrenchDrop : wrenchDrops) {
                            if(!aPlayer.inventory.addItemStackToInventory(wrenchDrop)) {
                                Block.spawnAsEntity(aWorld, blockPos, wrenchDrop);
                            }
                        }
                        aWorld.setBlockToAir(blockPos);
                        aWorld.removeTileEntity(blockPos);
                        ((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts);
                        GT_Utility.sendSoundToPlayers(aWorld, GregTech_API.sSoundList.get(100), 1.0F, -1.0F, blockPos);
                        return true;
                    }
                } else {
                    if(wrenchable.getFacing(aWorld, blockPos) != aSide) {
                        if(wrenchable.setFacing(aWorld, blockPos, aSide, aPlayer)) {
                            ((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts);
                            GT_Utility.sendSoundToPlayers(aWorld, GregTech_API.sSoundList.get(100), 1.0F, -1.0F, blockPos);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        return aList;
    }

}
