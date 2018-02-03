package gregtech.common.tools;

import gregtech.api.GregTechAPI;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.enchants.EnchantmentData;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.IToolStats;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.material.type.SolidMaterial;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ToolBase implements IToolStats {

    public static final EnchantmentData[] ZERO_ENCHANTMENTS = new EnchantmentData[0];

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 100;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 100;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 800;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 200;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 1.0F;
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
        return GregTechAPI.soundList.get(0);
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

//    public DamageSource getDamageSource(EntityLivingBase player, Entity entity) {
//        return DamageSources.getCombatDamage((player instanceof EntityPlayer) ? "player" : "mob", player, (entity instanceof EntityLivingBase) ? getDeathMessage(player, (EntityLivingBase) entity) : null);
//    }
//
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new EntityDamageSource((player instanceof EntityPlayer) ? "player" : "mob", player).getDeathMessage(entity);
//    }

    @Override
    public ItemStack getBrokenItem(ItemStack stack) {
        return null;
    }

    @Override
    public List<EnchantmentData> getEnchantments(ItemStack stack) {
        return new ArrayList<>(Arrays.asList(ZERO_ENCHANTMENTS));
    }

    @Override
    public boolean hasMaterialHandle() {
        return false;
    }

    @Override
    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
//        player.addStat(AchievementList.OPEN_INVENTORY);
//        player.addStat(AchievementList.MINE_WOOD);
//        player.addStat(AchievementList.BUILD_WORK_BENCH);
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
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
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        return false;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, NonNullList<ItemStack> drops) {
        return 0;
    }
}
