package gregtech.api.items.armor;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public interface IArmorLogic {

    default void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
    }

    default void renderHelmetOverlay(ItemStack itemStack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
    }

    ArmorProperties getArmorProperties(EntityLivingBase player, ItemStack itemStack, DamageSource source, double damage, EntityEquipmentSlot slot);

    int getArmorDisplay(EntityPlayer player, ItemStack itemStack, EntityEquipmentSlot slot);

    void damageArmor(EntityLivingBase entity, ItemStack itemStack, DamageSource source, int damage, EntityEquipmentSlot slot);
}
