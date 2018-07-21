package gregtech.common.tools;

import gregtech.api.enchants.EnchantmentData;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.items.toolitem.IToolStats;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.material.type.SolidMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public abstract class ToolBase implements IToolStats {

    @Override
    public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 8;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 2;
    }

    @Override
    public float getDigSpeedMultiplier(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public ResourceLocation getUseSound(ItemStack stack) {
        return null;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 0;
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
    public List<EnchantmentData> getEnchantments(ItemStack stack) {
        return Collections.emptyList();
    }

    @Override
    public boolean hasMaterialHandle() {
        return false;
    }

    @Override
    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
    }

    @Override
    public void onStatsAddedToTool(MetaValueItem item) {
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
        return 1.0F;
    }

    @Override
    public int getColor(boolean isToolHead, ItemStack stack) {
        SolidMaterial primaryMaterial = ToolMetaItem.getPrimaryMaterial(stack);
        SolidMaterial handleMaterial = ToolMetaItem.getHandleMaterial(stack);
        return isToolHead
            ? primaryMaterial != null ? primaryMaterial.materialRGB : 0xFFFFFF
            : handleMaterial != null ? handleMaterial.materialRGB : 0xFFFFFF;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack, String tool) {
        return false;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops, boolean recursive) {
        return 0;
    }
}
