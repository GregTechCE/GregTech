package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.damagesources.DamageSources;
import gregtech.api.enchants.EnchantmentData;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.IToolStats;
import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.List;

public abstract class ToolBase implements IToolStats {
    public static final Enchantment[] FORTUNE_ENCHANTMENT = {Enchantment.getEnchantmentByLocation("fortune")};
    public static final Enchantment[] LOOTING_ENCHANTMENT = {Enchantment.getEnchantmentByLocation("looting")};
    public static final Enchantment[] ZERO_ENCHANTMENTS = new Enchantment[0];
    public static final int[] ZERO_ENCHANTMENT_LEVELS = new int[0];

    public static ItemStack getBlockStack(IBlockState blockState) {
        return new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState));
    }

    protected static boolean isStateEqual(IBlockState state1, IBlockState state2) {
        if(state1.getBlock() != state2.getBlock())
            return false;
        if(!state1.getProperties().equals(state2.getProperties()))
            return false;
        return true;
    }

    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 100;
    }

    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 100;
    }

    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 800;
    }

    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 200;
    }

    public float getSpeedMultiplier(ItemStack stack) {
        return 1.0F;
    }

    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 1.0F;
    }

    public int getHurtResistanceTime(int originalHurtResistance, Entity entity) {
        return originalHurtResistance;
    }

    public ResourceLocation getMiningSound(ItemStack stack) {
        return null;
    }

    public ResourceLocation getCraftingSound(ItemStack stack) {
        return null;
    }

    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return null;
    }

    public ResourceLocation getBreakingSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(0);
    }

    public int getBaseQuality(ItemStack stack) {
        return 0;
    }

    public boolean isCrowbar(ItemStack stack) {
        return false;
    }

    public boolean isGrafter(ItemStack stack) {
        return false;
    }
    
    public boolean isChainsaw(ItemStack stack){
    	return false;
    }
    
    public boolean isWrench(ItemStack stack) {
        return false;
    }

    public boolean isWeapon(ItemStack stack) {
        return true;
    }

    public boolean isRangedWeapon(ItemStack stack) {
        return false;
    }

    public boolean isMiningTool(ItemStack stack) {
        return true;
    }

    public DamageSource getDamageSource(EntityLivingBase player, Entity entity) {
        return DamageSources.getCombatDamage((player instanceof EntityPlayer) ? "player" : "mob", player, (entity instanceof EntityLivingBase) ? getDeathMessage(player, (EntityLivingBase) entity) : null);
    }

    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new EntityDamageSource((player instanceof EntityPlayer) ? "player" : "mob", player).getDeathMessage(entity);
    }

    public ItemStack getBrokenItem(ItemStack stack) {
        return null;
    }

    public List<EnchantmentData> getEnchantments(ItemStack stack) {
        return new ArrayList<EnchantmentData>(Arrays.asList(ZERO_ENCHANTMENTS));
    }

    public int[] getEnchantmentLevels(ItemStack stack) {
        return ZERO_ENCHANTMENT_LEVELS;
    }

    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
        player.addStat(AchievementList.OPEN_INVENTORY);
        player.addStat(AchievementList.MINE_WOOD);
        player.addStat(AchievementList.BUILD_WORK_BENCH);
    }

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
        return isToolHead ? ToolMetaItem.getPrimaryMaterial(stack).materialRGB : ToolMetaItem.getHandleMaterial(stack).materialRGB;
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
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        return 0;
    }
}
