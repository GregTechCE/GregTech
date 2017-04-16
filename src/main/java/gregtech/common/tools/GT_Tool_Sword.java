package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

public class GT_Tool_Sword extends GT_Tool {

    @Override
    public int getToolDamagePerBlockBreak() {
        return 200;
    }

    @Override
    public int getToolDamagePerDropConversion() {
        return 100;
    }

    @Override
    public int getToolDamagePerContainerCraft() {
        return 100;
    }

    @Override
    public int getToolDamagePerEntityAttack() {
        return 100;
    }

    @Override
    public int getBaseQuality() {
        return 0;
    }

    @Override
    public float getBaseDamage() {
        return 4.0F;
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
        return null;
    }

    @Override
    public String getEntityHitSound() {
        return null;
    }

    @Override
    public String getBreakingSound() {
        return GregTech_API.sSoundList.get(0);
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
    public boolean isWeapon() {
        return true;
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock) {
        String tTool = aBlock.getBlock().getHarvestTool(aBlock);
        return ((tTool != null) && (tTool.equals("sword"))) ||
                (aBlock.getMaterial() == Material.LEAVES) ||
                (aBlock.getMaterial() == Material.GOURD) ||
                (aBlock.getMaterial() == Material.VINE) ||
                (aBlock.getMaterial() == Material.WEB) ||
                (aBlock.getMaterial() == Material.CLOTH) ||
                (aBlock.getMaterial() == Material.CARPET) ||
                (aBlock.getMaterial() == Material.PLANTS) ||
                (aBlock.getMaterial() == Material.CACTUS) ||
                (aBlock.getMaterial() == Material.CAKE) ||
                (aBlock.getMaterial() == Material.TNT) ||
                (aBlock.getMaterial() == Material.SPONGE);
    }

    @Override
    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadSword.mTextureIndex] : Textures.ItemIcons.HANDLE_SWORD;
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return !aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        aPlayer.addStat(AchievementList.BUILD_SWORD);
    }

}
