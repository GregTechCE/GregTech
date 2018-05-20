package gregtech.api.items;

import gregtech.api.gui.ModularUI;
import gregtech.api.gui.UIFactory;
import gregtech.api.items.metaitem.MetaItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * {@link UIFactory} implementation for {@link MetaItem}s
 */
public class MetaItemUIFactory extends UIFactory<HandUIWrapper> {

    public static final MetaItemUIFactory INSTANCE = new MetaItemUIFactory();

    private MetaItemUIFactory() {}

    public void init() {
        UIFactory.FACTORY_REGISTRY.register(1, "meta_item_factory", this);
    }

    @Override
    protected ModularUI createUITemplate(HandUIWrapper holder, EntityPlayer entityPlayer) {
        ItemStack stack = entityPlayer.getHeldItem(holder.hand);
        return ((MetaItem) stack.getItem()).getItem(stack).getUIManager().createUI(holder, entityPlayer, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected HandUIWrapper readHolderFromSyncData(PacketBuffer syncData) {
        return new HandUIWrapper(EnumHand.valueOf(syncData.readString(10)));
    }

    @Override
    protected void writeHolderToSyncData(PacketBuffer syncData, HandUIWrapper holder) {
        syncData.writeString(holder.hand.name());
    }

}
