package gregtech.api.interfaces;

import gregtech.api.items.GT_MetaBase_Item;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public interface IFoodStat {
    /**
     * Warning the "aPlayer" Parameter may be null!
     */
    public int getFoodLevel(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer);

    /**
     * Warning the "aPlayer" Parameter may be null!
     */
    public float getSaturation(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer);

    /**
     * Warning the "aPlayer" Parameter may be null!
     */
    public boolean alwaysEdible(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer);

    /**
     * Warning the "aPlayer" Parameter may be null!
     */
    public boolean isRotten(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer);

    /**
     * Warning the "aPlayer" Parameter may be null!
     */
    public EnumAction getFoodAction(GT_MetaBase_Item aItem, ItemStack aStack);

    public void onEaten(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer);
}