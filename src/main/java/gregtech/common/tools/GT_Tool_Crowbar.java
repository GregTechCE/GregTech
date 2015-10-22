package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.IToolStats;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import gregtech.common.items.behaviors.Behaviour_Crowbar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.Iterator;

public class GT_Tool_Crowbar
        extends GT_Tool {
    public int getToolDamagePerBlockBreak() {
        return 50;
    }

    public int getToolDamagePerDropConversion() {
        return 100;
    }

    public int getToolDamagePerContainerCraft() {
        return 100;
    }

    public int getToolDamagePerEntityAttack() {
        return 200;
    }

    public int getBaseQuality() {
        return 0;
    }

    public float getBaseDamage() {
        return 2.0F;
    }

    public float getSpeedMultiplier() {
        return 1.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    public String getCraftingSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(0));
    }

    public String getEntityHitSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(0));
    }

    public String getBreakingSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(0));
    }

    public String getMiningSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(0));
    }

    public boolean canBlock() {
        return true;
    }

    public boolean isCrowbar() {
        return true;
    }

    public boolean isWeapon() {
        return true;
    }

    public boolean isMinableBlock(Block aBlock, byte aMetaData) {
        if (aBlock.getMaterial() == Material.circuits) {
            return true;
        }
        String tTool = aBlock.getHarvestTool(aMetaData);
        if ((tTool == null) || (tTool.equals(""))) {
            for (Iterator i$ = GT_MetaGenerated_Tool_01.INSTANCE.mToolStats.values().iterator(); i$.hasNext(); i$.next()) {
                if (((i$ instanceof GT_Tool_Crowbar)) && (!((IToolStats) i$).isMinableBlock(aBlock, aMetaData))) {
                    return false;
                }
            }
            return true;
        }
        return tTool.equals("crowbar");
    }

    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.CROWBAR : null;
    }

    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : null;
    }

    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
        aItem.addItemBehavior(aID, new Behaviour_Crowbar(1, 1000));
    }

    public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " was removed by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
    }
}
