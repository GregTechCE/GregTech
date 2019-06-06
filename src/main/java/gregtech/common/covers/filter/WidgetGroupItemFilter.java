package gregtech.common.covers.filter;

import gregtech.api.gui.widgets.AbstractWidgetGroup;
import net.minecraft.network.PacketBuffer;

import java.util.function.Supplier;

public class WidgetGroupItemFilter extends AbstractWidgetGroup {

    private final int yPosition;
    private Supplier<AbstractItemFilter> itemFilterSupplier;
    private AbstractItemFilter itemFilter;
    private int maxStackSize = 1;

    public WidgetGroupItemFilter(int yPosition, Supplier<AbstractItemFilter> itemFilterSupplier) {
        this.yPosition = yPosition;
        this.itemFilterSupplier = itemFilterSupplier;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        AbstractItemFilter newItemFilter = itemFilterSupplier.get();
        if (itemFilter != newItemFilter) {
            clearAllWidgets();
            this.itemFilter = newItemFilter;
            if (itemFilter != null) {
                this.itemFilter.initUI(yPosition, this::addWidget);
            }
            writeUpdateInfo(2, buffer -> {
                if (itemFilter != null) {
                    buffer.writeBoolean(true);
                    int filterId = FilterTypeRegistry.getIdForItemFilter(itemFilter);
                    buffer.writeVarInt(filterId);
                } else {
                    buffer.writeBoolean(false);
                }
            });
        }
        int newMaxStackSize = 1;
        if(itemFilter instanceof ISlottedItemFilter) {
            newMaxStackSize = ((ISlottedItemFilter) itemFilter).getMaxStackSize();
        }
        if (maxStackSize != newMaxStackSize) {
            this.maxStackSize = newMaxStackSize;
            writeUpdateInfo(3, buffer -> buffer.writeVarInt(maxStackSize));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if(id == 2) {
            clearAllWidgets();
            if(buffer.readBoolean()) {
                int filterId = buffer.readVarInt();
                this.itemFilter = FilterTypeRegistry.createItemFilterById(filterId);
                this.itemFilter.initUI(yPosition, this::addWidget);
                if(itemFilter instanceof ISlottedItemFilter) {
                    ((ISlottedItemFilter) itemFilter).setMaxStackSize(maxStackSize);
                }
            }
        } else if(id == 3) {
            this.maxStackSize = buffer.readVarInt();
            if(itemFilter instanceof ISlottedItemFilter) {
                ((ISlottedItemFilter) itemFilter).setMaxStackSize(maxStackSize);
            }
        }
    }
}
