package gregtech.api.interfaces;

import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.world.BlockEvent;

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
    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer);

    /**
     * Called when this gets added to a Tool Item
     */
    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID);

    /**
     * @return Damage the Tool receives when breaking a Block. 100 is one Damage Point (or 100 EU).
     */
    public int getToolDamagePerBlockBreak();

    /**
     * @return Damage the Tool receives when converting the drops of a Block. 100 is one Damage Point (or 100 EU).
     */
    public int getToolDamagePerDropConversion();

    /**
     * @return Damage the Tool receives when being used as Container Item. 100 is one use, however it is usually 8 times more than normal.
     */
    public int getToolDamagePerContainerCraft();

    /**
     * @return Damage the Tool receives when being used as Weapon, 200 is the normal Value, 100 for actual Weapons.
     */
    public int getToolDamagePerEntityAttack();

    /**
     * @return Basic Quality of the Tool, 0 is normal. If increased, it will increase the general quality of all Tools of this Type. Decreasing is also possible.
     */
    public int getBaseQuality();

    /**
     * @return The Damage Bonus for this Type of Tool against Mobs. 1.0F is normal punch.
     */
    public float getBaseDamage();

    /**
     * @return This gets the Hurt Resistance time for Entities getting hit. (always does 1 as minimum)
     */
    public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity);

    /**
     * @return This is a multiplier for the Tool Speed. 1.0F = no special Speed.
     */
    public float getSpeedMultiplier();

    /**
     * @return This is a multiplier for the Tool Speed. 1.0F = no special Durability.
     */
    public float getMaxDurabilityMultiplier();

    public DamageSource getDamageSource(EntityLivingBase aPlayer, Entity aEntity);

    public String getMiningSound();

    public String getCraftingSound();

    public String getEntityHitSound();

    public String getBreakingSound();

    public Enchantment[] getEnchantments(ItemStack aStack);

    public int[] getEnchantmentLevels(ItemStack aStack);

    /**
     * @return If this Tool can be used for blocking Damage like a Sword.
     */
    public boolean canBlock();

    /**
     * @return If this Tool can be used as an RC Crowbar.
     */
    public boolean isCrowbar();

    /**
     * @return If this Tool can be used as an FR Grafter.
     */
    public boolean isGrafter();

    public boolean isChainsaw();
    /**
     * @return If this Tool can be used as an BC Wrench.
     */
    public boolean isWrench();
    
    /**
     * @return If this Tool can be used as Weapon i.e. if that is the main purpose.
     */
    public boolean isWeapon();

    /**
     * @return If this Tool is a Ranged Weapon. Return false at isWeapon unless you have a Blade attached to your Bow/Gun or something
     */
    public boolean isRangedWeapon();

    /**
     * @return If this Tool can be used as Weapon i.e. if that is the main purpose.
     */
    public boolean isMiningTool();

    /**
     * aBlock.getHarvestTool(aMetaData) can return the following Values for example.
     * "axe", "pickaxe", "sword", "shovel", "hoe", "grafter", "saw", "wrench", "crowbar", "file", "hammer", "plow", "plunger", "scoop", "screwdriver", "sense", "scythe", "softhammer", "cutter", "plasmatorch"
     *
     * @return If this is a minable Block. Tool Quality checks (like Diamond Tier or something) are separate from this check.
     */
    public boolean isMinableBlock(Block aBlock, byte aMetaData);

    /**
     * This lets you modify the Drop List, when this type of Tool has been used.
     *
     * @return the Amount of modified Items.
     */
    public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent);

    /**
     * @return Returns a broken Version of the Item.
     */
    public ItemStack getBrokenItem(ItemStack aStack);

    /**
     * @return the Damage actually done to the Mob.
     */
    public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer);

    /**
     * @return the Damage actually done to the Mob.
     */
    public float getMagicDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer);

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack);

    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack);
}