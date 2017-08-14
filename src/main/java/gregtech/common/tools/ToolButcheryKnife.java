package gregtech.common.tools;

import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ToolButcheryKnife extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 200;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 100;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 100;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 400;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 0;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity) {
        return aOriginalHurtResistance * 2;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0.1F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return null;
    }

    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public boolean isMiningTool() {
        return false;
    }

    @Override
    public boolean isCrowbar(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isGrafter(ItemStack stack) {
        return false;
    }

    @Override
    public int[] getEnchantmentLevels(ItemStack aStack) {
        return new int[]{(2 + ToolMetaItem.getPrimaryMaterial(aStack).toolQuality) / 2};
    }

//    @Override
//    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
//        return aIsToolHead ? Textures.ItemIcons.BUTCHERYKNIFE : null;
//    }

    @Override
    public int getColor(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).materialRGB : ToolMetaItem.getSecondaryMaterial(aStack).materialRGB;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(aPlayer.getDisplayName())
                .appendText(TextFormatting.WHITE + " has butchered " + TextFormatting.RED)
                .appendSibling(aEntity.getDisplayName());
    }

    @Override
    public boolean isMinableBlock(IBlockState blockState, ItemStack stack) {
        return false;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        return 0;
    }

    @Override
    public float getNormalDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase attacker) {
        return 0;
    }

    @Override
    public float getMagicDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase player) {
        return 0;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        return 0;
    }
}
