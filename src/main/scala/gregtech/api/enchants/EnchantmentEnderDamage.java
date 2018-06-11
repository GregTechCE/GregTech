package gregtech.api.enchants;

import gregtech.api.GTValues;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

public class EnchantmentEnderDamage extends EnchantmentDamage {

    public static final EnchantmentEnderDamage INSTANCE = new EnchantmentEnderDamage();

    private EnchantmentEnderDamage() {
        super(Rarity.UNCOMMON, 2);
    }

    public void register(RegistryEvent.Register<Enchantment> event) {
        this.setRegistryName(new ResourceLocation(GTValues.MODID, "disjunction"));
        event.getRegistry().register(this);
    }

    @Override
    public int getMinEnchantability(int level) {
        return 5 + (level - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return this.getMinEnchantability(level) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void onEntityDamaged(EntityLivingBase hurtEntity, Entity damagingEntity, int level) {
        if (hurtEntity instanceof EntityEnderman || hurtEntity instanceof EntityDragon || EntityList.getEntityString(hurtEntity).toLowerCase().contains("ender")) {
            hurtEntity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, level * 200, Math.max(1, (5 * level) / 7)));
            hurtEntity.addPotionEffect(new PotionEffect(MobEffects.POISON, level * 200, Math.max(1, (5 * level) / 7)));
        }
    }

}