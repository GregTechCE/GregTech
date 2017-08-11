package gregtech.api.items.metaitem.stats;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public interface IItemUseManager extends IMetaItemStats {

    boolean canStartUsing(ItemStack stack, EntityPlayer player);

    void onItemUseStart(ItemStack stack, EntityPlayer player);

    EnumAction getUseAction(ItemStack stack);

    int getMaxItemUseDuration(ItemStack stack);

    void onItemUsingTick(ItemStack stack, EntityPlayer player, int count);

    void onPlayerStoppedItemUsing(ItemStack stack, EntityPlayer player, int timeLeft);

    ItemStack onItemUseFinish(ItemStack stack, EntityPlayer player);

}
