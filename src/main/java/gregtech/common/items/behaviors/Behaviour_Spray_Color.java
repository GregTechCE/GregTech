package gregtech.common.items.behaviors;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Behaviour_Spray_Color
        extends Behaviour_None {
    private final ItemStack mEmpty;
    private final ItemStack mUsed;
    private final ItemStack mFull;
    private final long mUses;
    private final byte mColor;
    private final Collection<Block> mAllowedVanillaBlocks = Arrays.asList(new Block[]{Blocks.glass, Blocks.glass_pane, Blocks.stained_glass, Blocks.stained_glass_pane, Blocks.carpet, Blocks.hardened_clay, ItemList.TE_Rockwool.getBlock()});
    private final String mTooltip;
    private final String mTooltipUses = GT_LanguageManager.addStringLocalization("gt.behaviour.paintspray.uses", "Remaining Uses:");
    private final String mTooltipUnstackable = GT_LanguageManager.addStringLocalization("gt.behaviour.unstackable", "Not usable when stacked!");

    public Behaviour_Spray_Color(ItemStack aEmpty, ItemStack aUsed, ItemStack aFull, long aUses, int aColor) {
        this.mEmpty = aEmpty;
        this.mUsed = aUsed;
        this.mFull = aFull;
        this.mUses = aUses;
        this.mColor = ((byte) aColor);
        this.mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.paintspray." + this.mColor + ".tooltip", "Can Color things in " + Dyes.get(this.mColor).mName);
    }

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if ((aWorld.isRemote) || (aStack.stackSize != 1)) {
            return false;
        }
        boolean rOutput = false;
        if (!aPlayer.canPlayerEdit(aX, aY, aZ, aSide, aStack)) {
            return false;
        }
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null) {
            tNBT = new NBTTagCompound();
        }
        long tUses = tNBT.getLong("GT.RemainingPaint");
        if (GT_Utility.areStacksEqual(aStack, this.mFull, true)) {
            aStack.func_150996_a(this.mUsed.getItem());
            Items.feather.setDamage(aStack, Items.feather.getDamage(this.mUsed));
            tUses = this.mUses;
        }
        if ((GT_Utility.areStacksEqual(aStack, this.mUsed, true)) && (colorize(aWorld, aX, aY, aZ, aSide))) {
            GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(102)), 1.0F, 1.0F, aX, aY, aZ);
            if (!aPlayer.capabilities.isCreativeMode) {
                tUses -= 1L;
            }
            rOutput = true;
        }
        tNBT.removeTag("GT.RemainingPaint");
        if (tUses > 0L) {
            tNBT.setLong("GT.RemainingPaint", tUses);
        }
        if (tNBT.hasNoTags()) {
            aStack.setTagCompound(null);
        } else {
            aStack.setTagCompound(tNBT);
        }
        if (tUses <= 0L) {
            if (this.mEmpty == null) {
                aStack.stackSize -= 1;
            } else {
                aStack.func_150996_a(this.mEmpty.getItem());
                Items.feather.setDamage(aStack, Items.feather.getDamage(this.mEmpty));
            }
        }
        return rOutput;
    }

    private boolean colorize(World aWorld, int aX, int aY, int aZ, int aSide) {
        Block aBlock = aWorld.getBlock(aX, aY, aZ);
        if ((aBlock != Blocks.air) && ((this.mAllowedVanillaBlocks.contains(aBlock)) || ((aBlock instanceof BlockColored)))) {
            if (aBlock == Blocks.hardened_clay) {
                aWorld.setBlock(aX, aY, aZ, Blocks.stained_hardened_clay, (this.mColor ^ 0xFFFFFFFF) & 0xF, 3);
                return true;
            }
            if (aBlock == Blocks.glass_pane) {
                aWorld.setBlock(aX, aY, aZ, Blocks.stained_glass_pane, (this.mColor ^ 0xFFFFFFFF) & 0xF, 3);
                return true;
            }
            if (aBlock == Blocks.glass) {
                aWorld.setBlock(aX, aY, aZ, Blocks.stained_glass, (this.mColor ^ 0xFFFFFFFF) & 0xF, 3);
                return true;
            }
            if (aWorld.getBlockMetadata(aX, aY, aZ) == ((this.mColor ^ 0xFFFFFFFF) & 0xF)) {
                return false;
            }
            aWorld.setBlockMetadataWithNotify(aX, aY, aZ, (this.mColor ^ 0xFFFFFFFF) & 0xF, 3);
            return true;
        }
        return aBlock.recolourBlock(aWorld, aX, aY, aZ, ForgeDirection.getOrientation(aSide), (this.mColor ^ 0xFFFFFFFF) & 0xF);
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        NBTTagCompound tNBT = aStack.getTagCompound();
        long tRemainingPaint = tNBT == null ? 0L : GT_Utility.areStacksEqual(aStack, this.mFull, true) ? this.mUses : tNBT.getLong("GT.RemainingPaint");
        aList.add(this.mTooltipUses + " " + tRemainingPaint);
        aList.add(this.mTooltipUnstackable);
        return aList;
    }
}
