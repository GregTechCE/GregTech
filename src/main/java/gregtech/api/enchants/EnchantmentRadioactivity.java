package gregtech.api.enchants;

import gregtech.api.enums.ConfigCategories;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.util.GT_Config;
import gregtech.api.util.GT_Utility;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentRadioactivity extends EnchantmentDamage {

    public static EnchantmentRadioactivity INSTANCE;

    public EnchantmentRadioactivity() {
        super(Rarity.VERY_RARE, 0);
        Materials.Plutonium.setEnchantmentForTools(this, 1).setEnchantmentForArmors(this, 1);
        Materials.Uranium235.setEnchantmentForTools(this, 2).setEnchantmentForArmors(this, 2);
        Materials.Plutonium241.setEnchantmentForTools(this, 3).setEnchantmentForArmors(this, 3);
        Materials.NaquadahEnriched.setEnchantmentForTools(this, 4).setEnchantmentForArmors(this, 4);
        Materials.Naquadria.setEnchantmentForTools(this, 5).setEnchantmentForArmors(this, 5);
        REGISTRY.register(GT_Config.addIDConfig(ConfigCategories.IDs.enchantments, "Radioactivity", 14),
                new ResourceLocation(GT_Values.MOD_ID, "radioactivity"), this);
        INSTANCE = this;
    }

    @Override
    public int getMinEnchantability(int aLevel) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxEnchantability(int aLevel) {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public boolean canApply(ItemStack par1ItemStack) {
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

    @Override
    public String getName() {
        return "enchantment.damage.radioactivity";
    }

}