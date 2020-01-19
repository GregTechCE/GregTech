package gregtech.common.gui.widget;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.Widget;
import gregtech.api.util.Position;
import gregtech.api.util.Size;
import gregtech.common.inventory.IItemInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class ItemListSlotWidget extends Widget {

    private final ItemListGridWidget gridWidget;
    private final int index;

    ItemListSlotWidget(int x, int y, ItemListGridWidget gridWidget, int index) {
        super(new Position(x, y), new Size(18, 18));
        this.gridWidget = gridWidget;
        this.index = index;
    }

    public String formatItemAmount(int itemAmount) {
        return Integer.toString(itemAmount);
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY, IRenderContext context) {
        super.drawInBackground(mouseX, mouseY, context);
        Minecraft minecraft = Minecraft.getMinecraft();
        RenderItem renderItem = minecraft.getRenderItem();
        Position position = getPosition();
        GuiTextures.SLOT.draw(position.x, position.y, 18, 18);
        IItemInfo itemInfo = gridWidget.getItemInfoAt(index);
        if (itemInfo != null) {
            ItemStack itemStack = itemInfo.getItemStackKey().getItemStackRaw();
            int stackX = position.x + 1;
            int stackY = position.y + 1;
            renderItem.renderItemAndEffectIntoGUI(itemStack, stackX, stackY);
            String text = formatItemAmount(itemInfo.getTotalItemAmount());
            renderItem.renderItemOverlayIntoGUI(minecraft.fontRenderer, itemStack, stackX, stackY, text);
        }
    }

    @Override
    public void drawInForeground(int mouseX, int mouseY) {
        super.drawInForeground(mouseX, mouseY);
        IItemInfo itemInfo = gridWidget.getItemInfoAt(index);
        if (itemInfo != null) {
            ItemStack itemStack = itemInfo.getItemStackKey().getItemStackRaw();
            List<String> tooltip = getItemToolTip(itemStack);
            int totalItemStored = itemInfo.getTotalItemAmount();
            String itemStoredText = I18n.format("gregtech.item_list.item_stored", totalItemStored);
            tooltip.add(TextFormatting.GRAY + itemStoredText);
            drawHoveringText(itemStack, tooltip, -1, mouseX, mouseY);
        }
    }
}
