package gregtech.common.items.behaviors;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.eventhandler.Event;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;

public class Behaviour_Hoe
        extends Behaviour_None {
    private final int mCosts;
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.hoe", "Can till Dirt");

    public Behaviour_Hoe(int aCosts) {
        this.mCosts = aCosts;
    }

    @Override
    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (aPlayer.canPlayerEdit(blockPos, side, aStack) && !aWorld.isAirBlock(blockPos)) {
            IBlockState blockState = aWorld.getBlockState(blockPos);
            if (blockState.getBlock() == Blocks.GRASS || blockState.getBlock() == Blocks.DIRT) {
                if (blockState.getBlock() == Blocks.GRASS && aPlayer.isSneaking()) {
                    if(((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts)) {
                        if (aWorld.rand.nextInt(3) == 0) {
                            ItemStack grassSeed = ForgeHooks.getGrassSeed(aWorld.rand, 0);
                            Block.spawnAsEntity(aWorld, blockPos.up(), grassSeed);
                        }
                        aWorld.playSound(null, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        aWorld.setBlockState(blockPos, Blocks.DIRT.getDefaultState()
                                .withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
                        return true;
                    }
                } else if (blockState.getBlock() != Blocks.DIRT ||
                        blockState.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT) {
                    if(((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts)) {
                        aWorld.playSound(null, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.PLAYERS, 1.0F, 1.0F);
                        aWorld.setBlockState(blockPos, Blocks.FARMLAND.getDefaultState());
                        return true;
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
