package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.damagesources.GT_DamageSources;
import gregtech.api.interfaces.IToolStats;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public abstract class GT_Tool
        implements IToolStats {
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

    public int getToolDamagePerBlockBreak() {
        return 100;
    }

    public int getToolDamagePerDropConversion() {
        return 100;
    }

    public int getToolDamagePerContainerCraft() {
        return 800;
    }

    public int getToolDamagePerEntityAttack() {
        return 200;
    }

    public float getSpeedMultiplier() {
        return 1.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity) {
        return aOriginalHurtResistance;
    }

    public String getMiningSound() {
        return null;
    }

    public String getCraftingSound() {
        return null;
    }

    public String getEntityHitSound() {
        return null;
    }

    public String getBreakingSound() {
        return GregTech_API.sSoundList.get(0);
    }

    public int getBaseQuality() {
        return 0;
    }

    public boolean isCrowbar() {
        return false;
    }

    public boolean isGrafter() {
        return false;
    }
    
    public boolean isChainsaw(){
    	return false;
    }
    
    public boolean isWrench() {
        return false;
    }

    public boolean isWeapon() {
        return false;
    }

    public boolean isRangedWeapon() {
        return false;
    }

    public boolean isMiningTool() {
        return true;
    }

    public DamageSource getDamageSource(EntityLivingBase aPlayer, Entity aEntity) {
        return GT_DamageSources.getCombatDamage((aPlayer instanceof EntityPlayer) ? "player" : "mob", aPlayer, (aEntity instanceof EntityLivingBase) ? getDeathMessage(aPlayer, (EntityLivingBase) aEntity) : null);
    }

    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new EntityDamageSource((aPlayer instanceof EntityPlayer) ? "player" : "mob", aPlayer).getDeathMessage(aEntity);
    }

    public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, IBlockState aBlock, BlockPos blockPos, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent) {
        return 0;
    }

    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }

    public Enchantment[] getEnchantments(ItemStack aStack) {
        return ZERO_ENCHANTMENTS;
    }

    public int[] getEnchantmentLevels(ItemStack aStack) {
        return ZERO_ENCHANTMENT_LEVELS;
    }

    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        aPlayer.addStat(AchievementList.OPEN_INVENTORY);
        aPlayer.addStat(AchievementList.MINE_WOOD);
        aPlayer.addStat(AchievementList.BUILD_WORK_BENCH);
    }

    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
    }

    public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer) {
        return aOriginalDamage;
    }

    public float getMagicDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer) {
        return aOriginalDamage;
    }
}
