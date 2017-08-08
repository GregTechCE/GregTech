package gregtech.api.interfaces.metaitem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public interface IFoodStats extends IMetaItemStats {

    int getFoodLevel(ItemStack itemStack, @Nullable EntityPlayer player);

    float getSaturation(ItemStack itemStack, @Nullable EntityPlayer player);

    boolean alwaysEdible(ItemStack itemStack, @Nullable EntityPlayer player);

    EnumAction getFoodAction(ItemStack itemStack);

    void onEaten(ItemStack itemStack, EntityPlayer player);

    void addInformation(ItemStack itemStack, List<String> lines);

}