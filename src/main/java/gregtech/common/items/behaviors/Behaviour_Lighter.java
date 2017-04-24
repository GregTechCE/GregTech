package gregtech.common.items.behaviors;

import gregtech.api.GregTech_API;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class Behaviour_Lighter
        extends Behaviour_None {
    private final ItemStack mEmptyLighter;
    private final ItemStack mUsedLighter;
    private final ItemStack mFullLighter;
    private final long mFuelAmount;
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.lighter.tooltip", "Can light things on Fire");
    private final String mTooltipUses = GT_LanguageManager.addStringLocalization("gt.behaviour.lighter.uses", "Remaining Uses:");
    private final String mTooltipUnstackable = GT_LanguageManager.addStringLocalization("gt.behaviour.unstackable", "Not usable when stacked!");

    public Behaviour_Lighter(ItemStack aEmptyLighter, ItemStack aUsedLighter, ItemStack aFullLighter, long aFuelAmount) {
        this.mFullLighter = aFullLighter;
        this.mUsedLighter = aUsedLighter;
        this.mEmptyLighter = aEmptyLighter;
        this.mFuelAmount = aFuelAmount;
    }

    @Override
    public boolean onLeftClickEntity(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity, EnumHand hand) {
        if ((aPlayer.worldObj.isRemote) || (aStack.stackSize != 1)) {
            return false;
        }
        boolean rOutput = false;
        if (aEntity instanceof EntityCreeper) {
            prepare(aStack);
            long tFuelAmount = GT_Utility.ItemNBT.getLighterFuel(aStack);
            if (GT_Utility.areStacksEqual(aStack, this.mUsedLighter, true)) {
                GT_Utility.sendSoundToPlayers(aPlayer.worldObj, GregTech_API.sSoundList.get(6), 1.0F, 1.0F, MathHelper.floor_double(aEntity.posX), MathHelper.floor_double(aEntity.posY), MathHelper.floor_double(aEntity.posZ));
                ((EntityCreeper) aEntity).ignite();
                if (!aPlayer.capabilities.isCreativeMode) {
                    tFuelAmount -= 1L;
                }
                rOutput = true;
            }
            GT_Utility.ItemNBT.setLighterFuel(aStack, tFuelAmount);
            if (tFuelAmount <= 0L) {
                useUp(aStack);
            }
        }
        return rOutput;
    }

    @Override
    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        return false;
    }

    @Override
    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (aWorld.isRemote || aStack.stackSize != 1) {
            return false;
        }

        BlockPos clickedBlock = blockPos.offset(side);
        if(!aPlayer.canPlayerEdit(clickedBlock, side, aStack)) {
            return false;
        }

        if (aWorld.isAirBlock(clickedBlock))
        {
            prepare(aStack);
            long tFuelAmount = GT_Utility.ItemNBT.getLighterFuel(aStack);

            GT_Utility.sendSoundToPlayers(aWorld, GregTech_API.sSoundList.get(6), 1.0F, 1.0F, clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ());
            aWorld.setBlockState(clickedBlock, Blocks.FIRE.getDefaultState(), 11);

            if (!aPlayer.capabilities.isCreativeMode) {
                tFuelAmount -= 1L;
            }
            GT_Utility.ItemNBT.setLighterFuel(aStack, tFuelAmount);
            if(tFuelAmount <= 0L) {
                useUp(aStack);
            }
        }

        return false;
    }

    private void prepare(ItemStack aStack) {
        if (GT_Utility.areStacksEqual(aStack, this.mFullLighter, true)) {
            aStack.setItem(this.mUsedLighter.getItem());
            Items.FEATHER.setDamage(aStack, Items.FEATHER.getDamage(this.mUsedLighter));
            GT_Utility.ItemNBT.setLighterFuel(aStack, this.mFuelAmount);
        }
    }

    private void useUp(ItemStack aStack) {
        if (this.mEmptyLighter == null) {
            aStack.stackSize -= 1;
        } else {
            aStack.setItem(this.mEmptyLighter.getItem());
            Items.FEATHER.setDamage(aStack, Items.FEATHER.getDamage(this.mEmptyLighter));
        }
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        NBTTagCompound tNBT = aStack.getTagCompound();
        long tFuelAmount = tNBT == null ? this.mFuelAmount : tNBT.getLong("GT.LighterFuel");
        aList.add(this.mTooltipUses + " " + tFuelAmount);
        aList.add(this.mTooltipUnstackable);
        return aList;
    }
}
