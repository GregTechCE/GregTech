package gregtech.api.interfaces.metaitem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IFoodStats extends IMetaItemStats {

    /**
     * Warning the "player" Parameter may be null!
     */
    int getFoodLevel(ItemStack itemStack, EntityPlayer player);

    /**
     * Warning the "player" Parameter may be null!
     */
    float getSaturation(ItemStack itemStack, EntityPlayer player);

    /**
     * Warning the "player" Parameter may be null!
     */
    boolean alwaysEdible(ItemStack itemStack, EntityPlayer player);

    EnumAction getFoodAction(ItemStack itemStack);

    void onEaten(ItemStack itemStack, EntityPlayer player);

    void addInformation(ItemStack itemStack, List<String> lines);

}