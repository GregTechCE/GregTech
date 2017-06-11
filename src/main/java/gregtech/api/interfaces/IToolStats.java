package gregtech.api.interfaces;

import gregtech.api.items.toolitem.ToolMetaItem;
import ic2.api.tile.IWrenchable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

/**
 * The Stats for GT Tools. Not including any Material Modifiers.
 * <p/>
 * And this is supposed to not have any ItemStack Parameters as these are generic Stats.
 */
public interface IToolStats {

    /**
     * Called when aPlayer crafts this Tool
     */
    void onToolCrafted(ItemStack stack, EntityPlayer player);

    /**
     * Called when this gets added to a Tool Item
     */
    void onStatsAddedToTool(ToolMetaItem item, int ID);

    /**
     * @return Damage the Tool receives when breaking a Block. 100 is one Damage Point (or 100 EU).
     */
    int getToolDamagePerBlockBreak();

    /**
     * @return Damage the Tool receives when converting the drops of a Block. 100 is one Damage Point (or 100 EU).
     */
    int getToolDamagePerDropConversion();

    /**
     * @return Damage the Tool receives when being used as Container Item. 100 is one use, however it is usually 8 times more than normal.
     */
    int getToolDamagePerContainerCraft();

    /**
     * @return Damage the Tool receives when being used as Weapon, 200 is the normal Value, 100 for actual Weapons.
     */
    int getToolDamagePerEntityAttack();

    /**
     * @return Basic Quality of the Tool, 0 is normal. If increased, it will increase the general quality of all Tools of this Type. Decreasing is also possible.
     */
    int getBaseQuality();

    /**
     * @return The Damage Bonus for this Type of Tool against Mobs. 1.0F is normal punch.
     */
    float getBaseDamage();

    /**
     * @return This gets the Hurt Resistance time for Entities getting hit. (always does 1 as minimum)
     */
    int getHurtResistanceTime(int originalHurtResistance, Entity entity);

    /**
     * @return This is a multiplier for the Tool Speed. 1.0F = no special Speed.
     */
    float getSpeedMultiplier();

    /**
     * @return This is a multiplier for the Tool Durability. 1.0F = no special Durability.
     */
    float getMaxDurabilityMultiplier();

    DamageSource getDamageSource(EntityLivingBase player, Entity entity);

    ResourceLocation getMiningSound();

    ResourceLocation getCraftingSound();

    ResourceLocation getEntityHitSound();

    ResourceLocation getBreakingSound();

    ImmutablePair<Enchantment, Integer>[] getEnchantments(ItemStack stack);

    /**
     * @return If this Tool can be used as an RC Crowbar.
     */
    boolean isCrowbar();

    /**
     * @return If this Tool can be used as an FR Grafter.
     */
    boolean isGrafter();

    /**
     * @return If this Tool can be used as an BC Wrench.
     */
    boolean isWrench();

    /**
     * aBlock.getHarvestTool(aMetaData) can return the following Values for example.
     * "axe", "pickaxe", "sword", "shovel", "hoe", "grafter", "saw", "wrench", "crowbar", "file", "hammer", "plow", "plunger", "scoop", "screwdriver", "sense", "scythe", "softhammer", "cutter", "plasmatorch"
     *
     * @return If this is a minable Block. Tool Quality checks (like Diamond Tier or something) are separate from this check.
     */
    boolean isMinableBlock(IBlockState block);

    /**
     * This lets you modify the Drop List, when this type of Tool has been used.
     *
     */
    int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops);

    /**
     * @return Returns a broken Version of the Item.
     */
    ItemStack getBrokenItem(ItemStack stack);

    /**
     * @return the Damage actually done to the Mob.
     */
    float getNormalDamageAgainstEntity(float originalDamage, Entity entity, ItemStack stack, EntityPlayer player);

    /**
     * @return the Damage actually done to the Mob.
     */
    float getMagicDamageAgainstEntity(float originalDamage, Entity entity, ItemStack stack, EntityPlayer player);

    int getColor(boolean isToolHead, ItemStack stack);

}