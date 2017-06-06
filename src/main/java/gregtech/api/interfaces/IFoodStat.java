package gregtech.api.interfaces;

import gregtech.api.items.GT_MetaBase_Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public interface IFoodStat {

    /**
     * Warning the "aPlayer" Parameter may be null!
     */
    int getFoodLevel(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer);

    /**
     * Warning the "aPlayer" Parameter may be null!
     */
    float getSaturation(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer);

    /**
     * Warning the "aPlayer" Parameter may be null!
     */
    boolean alwaysEdible(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer);

    /**
     * Warning the "aPlayer" Parameter may be null!
     */
    boolean isRotten(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer);

    /**
     * Warning the "aPlayer" Parameter may be null!
     */
    EnumAction getFoodAction(GT_MetaBase_Item aItem, ItemStack aStack);

    void onEaten(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer);

}