package gregtech.api.enchants;

import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GT_Config;
import gregtech.api.util.GT_Utility;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentRadioactivity extends EnchantmentDamage {

    public static final EnchantmentRadioactivity INSTANCE = new EnchantmentRadioactivity();

    private EnchantmentRadioactivity() {
        super(Rarity.VERY_RARE, 0);
    }

    public void registerEnchantment() {
        REGISTRY.register(
                GT_Config.addIDConfig(ConfigCategories.IDs.enchantments, "Disjunction", 15),
                new ResourceLocation(GT_Values.MODID, "disjunction"),
                this
        );
    }

    @Override
    public int getMinEnchantability(int level) {
        return 0;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canApply(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return false;
    }

    @Override
    public void onEntityDamaged(EntityLivingBase hurtEntity, Entity damagingEntity, int level) {
        GT_Utility.applyRadioactivity(hurtEntity, level, 1);
    }

}