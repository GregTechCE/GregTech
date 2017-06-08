package gregtech.api.interfaces;

import gregtech.api.items.GT_MetaBase_Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public interface IFoodStat {

    /**
     * Warning the "player" Parameter may be null!
     */
    int getFoodLevel(GT_MetaBase_Item item, ItemStack itemStack, EntityPlayer player);

    /**
     * Warning the "player" Parameter may be null!
     */
    float getSaturation(GT_MetaBase_Item item, ItemStack itemStack, EntityPlayer player);

    /**
     * Warning the "player" Parameter may be null!
     */
    boolean alwaysEdible(GT_MetaBase_Item item, ItemStack itemStack, EntityPlayer player);

    /**
     * Warning the "player" Parameter may be null!
     */
    boolean isRotten(GT_MetaBase_Item item, ItemStack itemStack, EntityPlayer player);

    /**
     * Warning the "player" Parameter may be null!
     */
    EnumAction getFoodAction(GT_MetaBase_Item item, ItemStack itemStack);

    void onEaten(GT_MetaBase_Item item, ItemStack itemStack, EntityPlayer player);

}