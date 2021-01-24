package gregtech.common.items.behaviors;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import gregtech.api.GTValues;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.items.metaitem.stats.IEnchantabilityHelper;
import gregtech.api.items.metaitem.stats.IItemUseManager;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.material.Materials;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

public class NanoSaberBehavior extends ToggleEnergyConsumerBehavior implements IEnchantabilityHelper, IItemUseManager {

    private static final ResourceLocation OVERRIDE_KEY_LOCATION = new ResourceLocation(GTValues.MODID, "nano_saber_active");
    protected static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A288-9C13A33DB5CF");
    protected static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4288-B01B-BCCE9785ACA3");
    protected static final UUID REACH_DISTANCE_MODIFIER = UUID.fromString("FA233E1C-4180-4288-B01B-BCCEFF45ACA3");

    private final float baseAttackDamage;
    private final float additionalAttackDamage;

    public NanoSaberBehavior(float baseAttackDamage, float additionalAttackDamage, int energyUsagePerTick) {
        super(energyUsagePerTick);
        this.baseAttackDamage = baseAttackDamage;
        this.additionalAttackDamage = additionalAttackDamage;
    }

    @Override
    public EnumAction getUseAction(ItemStack stack) {
        return EnumAction.NONE;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onAddedToItem(MetaValueItem metaValueItem) {
        metaValueItem.getMetaItem().addPropertyOverride(OVERRIDE_KEY_LOCATION, (stack, worldIn, entityIn) -> isItemActive(stack) ? 1.0f : 0.0f);
    }

    @Override
    public void removeReequipCompareTags(NBTTagCompound tagCompound) {
        tagCompound.removeTag("Animation");
    }

    @Override
    public boolean canStartUsing(ItemStack stack, EntityPlayer player, EnumHand hand) {
        return isItemActive(stack) && hand == EnumHand.MAIN_HAND;
    }

    @Override
    public void onItemUseStart(ItemStack stack, EntityPlayer player) {
        NBTTagCompound tagCompound = stack.getOrCreateSubCompound("Animation");
        tagCompound.setFloat("Old", 10.0f);
        tagCompound.setFloat("Cur", 10.0f);
        tagCompound.setFloat("Nxt", 10.0f);
    }

    @Override
    public void onItemUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (!isItemActive(stack)) {
            player.stopActiveHand();
            return;
        }
        NBTTagCompound tagCompound = stack.getOrCreateSubCompound("Animation");
        float currentAnim = tagCompound.getFloat("Cur");
        float nextAnim = tagCompound.getFloat("Nxt");
        tagCompound.setFloat("Old", currentAnim);
        if (Math.abs(currentAnim - nextAnim) > 0.01f) {
            //move ourselves towards target position
            float deltaSign = Math.signum(nextAnim - currentAnim);
            float delta = Math.min(40.0f, Math.abs(nextAnim - currentAnim));
            tagCompound.setFloat("Cur", currentAnim + delta * deltaSign);
        } else {
            //move sword back to the initial position
            tagCompound.setFloat("Nxt", 10.0f);
        }
    }

    public static void playBlockAnimation(EntityPlayer player, ItemStack itemStack) {
        NBTTagCompound tagCompound = itemStack.getOrCreateSubCompound("Animation");
        tagCompound.setFloat("Nxt", 30.0f + player.world.rand.nextInt(7) * -20.0f);
        player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 2.0f);
    }

    @Override
    public void onPlayerStoppedItemUsing(ItemStack stack, EntityPlayer player, int timeLeft) {
        stack.removeSubCompound("Animation");
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        HashMultimap<String, AttributeModifier> modifiers = HashMultimap.create();
        if(slot == EntityEquipmentSlot.MAINHAND) {
            float attackDamage = baseAttackDamage + (isItemActive(stack) ? additionalAttackDamage : 0.0f);
            modifiers.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon Modifier", -2.0, 0));
            modifiers.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon Modifier", attackDamage, 0));
            modifiers.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(REACH_DISTANCE_MODIFIER, "Weapon Modifier", 3f, 0));
        }
        return modifiers;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return ToolMetaItem.getMaterialEnchantability(Materials.Platinum);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment != Enchantments.UNBREAKING &&
            enchantment != Enchantments.MENDING &&
            enchantment.type.canEnchantItem(Items.IRON_SWORD);
    }
}
