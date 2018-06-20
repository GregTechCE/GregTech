package gregtech.api.items.toolitem;

import gregtech.api.enchants.EnchantmentData;
import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * The Stats for GT Tools. Not including any Material Modifiers.
 */
public interface IToolStats {

    /**
     * Called when aPlayer crafts this Tool
     */
    void onToolCrafted(ItemStack stack, EntityPlayer player);

    /**
     * Called when this gets added to a Tool Item
     */
    void onStatsAddedToTool(MetaItem.MetaValueItem metaValueItem, int ID);

    /**
     * @return Damage the Tool receives when breaking a Block. 100 is one Damage Point (or 100 EU).
     */
    int getToolDamagePerBlockBreak(ItemStack stack);

    /**
     * @return Damage the Tool receives when converting the drops of a Block. 100 is one Damage Point (or 100 EU).
     */
    int getToolDamagePerDropConversion(ItemStack stack);

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
    int getBaseQuality(ItemStack stack);

    /**
     * @return The Damage Bonus for this Type of Tool against Mobs. 1.0F is normal punch.
     */
    float getBaseDamage(ItemStack stack);

    /**
     * @return This is a multiplier for the Tool Speed. 1.0F = no special Speed.
     */
    float getDigSpeedMultiplier(ItemStack stack);

    /**
     * @return This is a multiplier for the Tool Durability. 1.0F = no special Durability.
     */
    float getMaxDurabilityMultiplier(ItemStack stack);

    ResourceLocation getUseSound(ItemStack stack);

    List<EnchantmentData> getEnchantments(ItemStack stack);

    boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment);

    boolean hasMaterialHandle();

    /**
     * @return If this Tool can be used as an RC Crowbar.
     */
    boolean isCrowbar(ItemStack stack);

    /**
     * @return If this Tool can be used as an FR Grafter.
     */
    boolean isGrafter(ItemStack stack);

    /**
     * block.getHarvestTool(metaData) can return the following Values for example.
     * "axe", "pickaxe", "sword", "shovel", "hoe", "grafter", "saw", "wrench", "crowbar", "file", "hammer", "plow", "plunger", "scoop", "screwdriver", "sense", "scythe", "softhammer", "cutter", "plasmatorch"
     *
     * @return If this is a minable Block. Tool Quality checks (like Diamond Tier or something) are separate from this check.
     */
    boolean isMinableBlock(IBlockState block, ItemStack stack);

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
    int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops, boolean recursive);

    /**
     * @return the Damage actually done to the Mob.
     */
    float getNormalDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase attacker);

    /**
     * @return the Damage actually done to the Mob.
     */
    float getMagicDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase player);

    /**
     * @return attack speed of weapon
     */
    float getAttackSpeed(ItemStack stack);

    int getColor(boolean isToolHead, ItemStack stack);
}