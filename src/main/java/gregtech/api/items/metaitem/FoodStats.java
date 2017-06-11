package gregtech.api.items.metaitem;

import com.google.common.collect.Iterables;
import gregtech.api.interfaces.metaitem.IFoodStats;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import java.util.List;

/**
 * Simple {@link IFoodStats} implementation
 *
 * @see gregtech.api.items.metaitem.MetaItem
 */
public class FoodStats implements IFoodStats {

    public final int foodLevel;
    public final float saturation;
    public final boolean isDrink;
    public final boolean alwaysEdible;
    public final PotionEffect[] potionEffects;

    public FoodStats(int foodLevel, float saturation, boolean isDrink, boolean alwaysEdible, PotionEffect... potionEffects) {
        this.foodLevel = foodLevel;
        this.saturation = saturation;
        this.isDrink = isDrink;
        this.alwaysEdible = alwaysEdible;
        this.potionEffects = potionEffects;
    }

    public FoodStats(int foodLevel, float saturation, boolean isDrink) {
        this(foodLevel, saturation, isDrink, false);
    }

    public FoodStats(int foodLevel, float saturation) {
        this(foodLevel, saturation, false);
    }

    @Override
    public int getFoodLevel(ItemStack itemStack, EntityPlayer player) {
        return foodLevel;
    }

    @Override
    public float getSaturation(ItemStack itemStack, EntityPlayer player) {
        return saturation;
    }

    @Override
    public boolean alwaysEdible(ItemStack itemStack, EntityPlayer player) {
        return alwaysEdible;
    }

    @Override
    public EnumAction getFoodAction(ItemStack itemStack) {
        return isDrink ? EnumAction.DRINK : EnumAction.EAT;
    }

    @Override
    public void onEaten(ItemStack itemStack, EntityPlayer player) {
        for(PotionEffect potionEffect : potionEffects) {
            player.addPotionEffect(GT_Utility.copyPotionEffect(potionEffect));
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        if(potionEffects.length > 0) {
            GT_Utility.addPotionTooltip(Iterables.cycle(potionEffects), lines);
        }
    }

}
