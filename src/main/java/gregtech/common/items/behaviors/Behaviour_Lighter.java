package gregtech.common.items.behaviors;

import codechicken.lib.math.MathHelper;
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
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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

    public boolean onLeftClickEntity(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, Entity aEntity) {
        if ((aPlayer.worldObj.isRemote) || (aStack.stackSize != 1)) {
            return false;
        }
        boolean rOutput = false;
        if ((aEntity instanceof EntityCreeper)) {
            prepare(aStack);
            long tFuelAmount = GT_Utility.ItemNBT.getLighterFuel(aStack);
            if (GT_Utility.areStacksEqual(aStack, this.mUsedLighter, true)) {
                GT_Utility.sendSoundToPlayers(aPlayer.worldObj, (String) GregTech_API.sSoundList.get(Integer.valueOf(6)), 1.0F, 1.0F, MathHelper.floor_double(aEntity.posX), MathHelper.floor_double(aEntity.posY), MathHelper.floor_double(aEntity.posZ));
                ((EntityCreeper) aEntity).func_146079_cb();
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

    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        return false;
    }

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if ((aWorld.isRemote) || (aStack.stackSize != 1)) {
            return false;
        }
        boolean rOutput = false;

        ForgeDirection tDirection = ForgeDirection.getOrientation(aSide);
        aX += tDirection.offsetX;
        aY += tDirection.offsetY;
        aZ += tDirection.offsetZ;
        if ((!GT_Utility.isBlockAir(aWorld, aX, aY, aZ)) || (!aPlayer.canPlayerEdit(aX, aY, aZ, aSide, aStack))) {
            return false;
        }
        prepare(aStack);
        long tFuelAmount = GT_Utility.ItemNBT.getLighterFuel(aStack);
        if (GT_Utility.areStacksEqual(aStack, this.mUsedLighter, true)) {
            GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(6)), 1.0F, 1.0F, aX, aY, aZ);
            aWorld.setBlock(aX, aY, aZ, Blocks.fire);
            if (!aPlayer.capabilities.isCreativeMode) {
                tFuelAmount -= 1L;
            }
            rOutput = true;
        }
        GT_Utility.ItemNBT.setLighterFuel(aStack, tFuelAmount);
        if (tFuelAmount <= 0L) {
            useUp(aStack);
        }
        return rOutput;
    }

    private void prepare(ItemStack aStack) {
        if (GT_Utility.areStacksEqual(aStack, this.mFullLighter, true)) {
            aStack.func_150996_a(this.mUsedLighter.getItem());
            Items.feather.setDamage(aStack, Items.feather.getDamage(this.mUsedLighter));
            GT_Utility.ItemNBT.setLighterFuel(aStack, this.mFuelAmount);
        }
    }

    private void useUp(ItemStack aStack) {
        if (this.mEmptyLighter == null) {
            aStack.stackSize -= 1;
        } else {
            aStack.func_150996_a(this.mEmptyLighter.getItem());
            Items.feather.setDamage(aStack, Items.feather.getDamage(this.mEmptyLighter));
        }
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        NBTTagCompound tNBT = aStack.getTagCompound();
        long tFuelAmount = tNBT == null ? 0L : GT_Utility.areStacksEqual(aStack, this.mFullLighter, true) ? this.mFuelAmount : tNBT.getLong("GT.LighterFuel");
        aList.add(this.mTooltipUses + " " + tFuelAmount);
        aList.add(this.mTooltipUnstackable);
        return aList;
    }
}
