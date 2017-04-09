package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.common.items.behaviors.Behaviour_Screwdriver;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;

public class GT_Tool_Soldering_Iron extends GT_Tool {

    public static final List<String> mEffectiveList = Arrays.asList(EntityCaveSpider.class.getName(), EntitySpider.class.getName(), "EntityTFHedgeSpider", "EntityTFKingSpider", "EntityTFSwarmSpider", "EntityTFTowerBroodling");

    @Override
    public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer) {
        String tName = aEntity.getClass().getName();
        tName = tName.substring(tName.lastIndexOf('.') + 1);
        return mEffectiveList.contains(tName) ? aOriginalDamage * 2.0F : aOriginalDamage;
    }

    @Override
    public int getToolDamagePerBlockBreak() {
        return 1000;
    }

    @Override
    public int getToolDamagePerDropConversion() {
        return 500;
    }

    @Override
    public int getToolDamagePerContainerCraft() {
        return 1000;
    }

    @Override
    public int getToolDamagePerEntityAttack() {
        return 500;
    }

    @Override
    public int getBaseQuality() {
        return 0;
    }

    @Override
    public float getBaseDamage() {
        return 1.5F;
    }

    @Override
    public float getSpeedMultiplier() {
        return 1.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    @Override
    public String getCraftingSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(100));
    }

    @Override
    public String getEntityHitSound() {
        return null;
    }

    @Override
    public String getBreakingSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(0));
    }

    @Override
    public String getMiningSound() {
        return null;
    }

    @Override
    public boolean isCrowbar() {
        return false;
    }

    @Override
    public boolean isMiningTool() {
        return false;
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock) {
        return aBlock.getMaterial() == Material.CIRCUITS;
    }

    @Override
    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[49] : Textures.ItemIcons.HANDLE_SOLDERING;
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
        aItem.addItemBehavior(aID, new Behaviour_Screwdriver(1, 200));
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(aEntity.getDisplayName())
                .appendText(TextFormatting.WHITE + " got soldert by " + TextFormatting.GREEN)
                .appendSibling(aPlayer.getDisplayName());
    }

}
