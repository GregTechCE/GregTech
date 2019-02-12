package gregtech.api.items.toolitem;

import gregtech.api.enchants.EnchantmentData;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
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

/**
 * The Stats for GT Tools. Not including any Material Modifiers.
 */
public interface IToolStats {

    /**
     * Called when aPlayer crafts this Tool
     */
    default void onToolCrafted(ItemStack stack, EntityPlayer player) {
    }

    /**
     * Called when this gets added to a Tool Item
     */
    default void onStatsAddedToTool(MetaValueItem metaValueItem) {
    }

    /**
     * @return Damage the Tool receives when breaking a Block. 100 is one Damage Point (or 100 EU).
     */
    int getToolDamagePerBlockBreak(ItemStack stack);

    /**
     * @return Damage the Tool receives when converting the drops of a Block. 100 is one Damage Point (or 100 EU).
     */
    default int getToolDamagePerDropConversion(ItemStack stack) {
        return 0; //we are not converting anything by default
    }

    /**
     * @return Damage the Tool receives when being used as Container Item. 100 is one use, however it is usually 8 times more than normal.
     */
    int getToolDamagePerContainerCraft(ItemStack stack);

    /**
     * @return Damage the Tool receives when being used as Weapon, 200 is the normal Value, 100 for actual Weapons.
     */
    int getToolDamagePerEntityAttack(ItemStack stack);

    /**
     * @return Basic Quality of the Tool, 0 is normal. If increased, it will increase the general quality of all Tools of this Type. Decreasing is also possible.
     */
    default int getBaseQuality(ItemStack stack) {
        return 0;
    }

    /**
     * @return The Damage Bonus for this Type of Tool against Mobs. 1.0F is normal punch.
     */
    default float getBaseDamage(ItemStack stack) {
        return 1.0f;
    }

    /**
     * @return This is a multiplier for the Tool Speed. 1.0F = no special Speed.
     */
    default float getDigSpeedMultiplier(ItemStack stack) {
        return 1.0f;
    }

    /**
     * @return This is a multiplier for the Tool Durability. 1.0F = no special Durability.
     */
    default float getMaxDurabilityMultiplier(ItemStack stack) {
        return 1.0f;
    }

    ResourceLocation getUseSound(ItemStack stack);

    default List<EnchantmentData> getEnchantments(ItemStack stack) {
        return Collections.emptyList();
    }

    boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment);

    /**
     * block.getHarvestTool(metaData) can return the following Values for example.
     * "axe", "pickaxe", "sword", "shovel", "hoe", "grafter", "saw", "wrench", "crowbar", "file", "hammer", "plow", "plunger", "scoop", "screwdriver", "sense", "scythe", "softhammer", "cutter", "plasmatorch"
     *
     * @return If this is a minable Block. Tool Quality checks (like Diamond Tier or something) are separate from this check.
     */
    boolean isMinableBlock(IBlockState block, ItemStack stack);

    /**
     * @return If this Tool can be used as an RC Crowbar.
     */
    default boolean isCrowbar(ItemStack stack) {
        return false;
    }

    /**
     * @return If this Tool can be used as an FR Grafter.
     */
    default boolean isGrafter(ItemStack stack) {
        return false;
    }

    /**
     * @return true to allow recursive calling of convertBlockDrops on this tool
     * this is useful when you have tool that breaks multiple block area in unusual way and need to break it
     * and ALSO convert it's drops. In such cases, recursive parameter of convertBlockDrops will be set to true
     */
    default boolean allowRecursiveConversion() {
        return false;
    }

    /**
     * This lets you modify the Drop List, when this type of Tool has been used.
     * Allows special behaviors like timber axe, leaves cutting, etc
     */
    default int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops, boolean recursive) {
        return 0; //do not convert anything by default
    }

    /**
     * Whether to show basic attributes in addInformation (i.e mining speed and attack damage) call for this tool type.
     * Returning false will hide these attributes from displaying in tool tooltip.
     */
    default boolean showBasicAttributes() {
        return true;
    }

    default void addInformation(ItemStack stack, List<String> lines, boolean isAdvanced) {
    }

    /**
     * @return the Damage actually done to the Mob.
     */
    default float getNormalDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase attacker) {
        return 0.0f;
    }

    /**
     * @return the Damage actually done to the Mob.
     */
    default float getMagicDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase player) {
        return 0.0f;
    }

    /**
     * @return attack speed of weapon
     */
    default float getAttackSpeed(ItemStack stack) {
        return -2.8f;
    }

    default int getColor(ItemStack stack, int tintIndex) {
        SolidMaterial primaryMaterial = ToolMetaItem.getToolMaterial(stack);
        return tintIndex % 2 == 1 ? primaryMaterial.materialRGB : 0xFFFFFF;
    }
}