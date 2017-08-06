package gregtech.api.enchants;

import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.material.Materials;
import gregtech.api.util.GT_Config;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class EnchantmentEnderDamage extends EnchantmentDamage {

    public static EnchantmentEnderDamage INSTANCE;

    public EnchantmentEnderDamage() {
        super(Rarity.UNCOMMON, 2);
        Materials.Silver.setEnchantmentForTools(this, 2);
        Materials.Mercury.setEnchantmentForTools(this, 3);
        Materials.Electrum.setEnchantmentForTools(this, 3);
        Materials.SterlingSilver.setEnchantmentForTools(this, 4);
        Materials.AstralSilver.setEnchantmentForTools(this, 5);
        REGISTRY.register(GT_Config.addIDConfig(ConfigCategories.IDs.enchantments, "Disjunction", 15),
                new ResourceLocation(GT_Values.MOD_ID, "disjunction"), this);
        INSTANCE = this;
    }

    @Override
    public int getMinEnchantability(int aLevel) {
        return 5 + (aLevel - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int aLevel) {
        return this.getMinEnchantability(aLevel) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public void onEntityDamaged(EntityLivingBase hurtEntity, Entity damagingEntity, int level) {
        if ((hurtEntity instanceof EntityEnderman || hurtEntity instanceof EntityDragon || (hurtEntity.getClass().getName().contains(".") && hurtEntity.getClass().getName().substring(hurtEntity.getClass().getName().lastIndexOf(".")).contains("Ender")))) {
            // Weakness causes Endermen to not be able to teleport with GT being installed.
            hurtEntity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, level * 200, Math.max(1, (5 * level) / 7)));
            // They also get Poisoned. If you have this Enchant on an Arrow, you can kill the Ender Dragon easier.
            hurtEntity.addPotionEffect(new PotionEffect(MobEffects.POISON, level * 200, Math.max(1, (5 * level) / 7)));
        }
    }

    @Override
    public String getName() {
        return "enchantment.damage.endermen";
    }

}