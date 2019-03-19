package gregtech.api.items.armor;

import gregtech.api.items.armor.ArmorMetaItem.ArmorMetaValueItem;
import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ArmorMetaItem extends MetaItem<ArmorMetaValueItem> implements ISpecialArmor {

    public ArmorMetaItem(short metaItemOffset) {
        super(metaItemOffset);
    }

    @Override
    protected ArmorMetaValueItem constructMetaValueItem(short metaValue, String unlocalizedName) {
        return new ArmorMetaValueItem(metaValue, unlocalizedName);
    }

    @Override
    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        ArmorMetaValueItem valueItem = getItem(stack);
        return valueItem != null && valueItem.getEquipmentSlot() == armorType;
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        ArmorMetaValueItem valueItem = getItem(stack);
        return valueItem == null ? null : valueItem.getEquipmentSlot();
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
    }

    @Override
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
        super.renderHelmetOverlay(stack, player, resolution, partialTicks);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, @Nonnull ItemStack armor, DamageSource source, double damage, int slot) {
        return null;
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot) {
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {

    }

    public class ArmorMetaValueItem extends MetaItem<?>.MetaValueItem {

        private EntityEquipmentSlot equipmentSlot;

        public ArmorMetaValueItem(int metaValue, String unlocalizedName) {
            super(metaValue, unlocalizedName);
        }

        public EntityEquipmentSlot getEquipmentSlot() {
            return equipmentSlot;
        }

        public ArmorMetaValueItem setEquipmentSlot(EntityEquipmentSlot equipmentSlot) {
            this.equipmentSlot = equipmentSlot;
            return this;
        }
    }

}
