package gregtech.api.enchants;

import net.minecraft.enchantment.Enchantment;

public class EnchantmentData {

    public final Enchantment enchantment;
    public final int level;

    public EnchantmentData(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnchantmentData that = (EnchantmentData) o;

        if (level != that.level) return false;
        return enchantment.equals(that.enchantment);
    }

    @Override
    public int hashCode() {
        int result = enchantment.hashCode();
        result = 31 * result + level;
        return result;
    }

}
