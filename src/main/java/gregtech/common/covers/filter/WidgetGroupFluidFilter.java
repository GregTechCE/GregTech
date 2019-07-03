package gregtech.common.covers.filter;

import gregtech.api.gui.widgets.AbstractWidgetGroup;
import net.minecraft.network.PacketBuffer;

import java.util.function.Supplier;

public class WidgetGroupFluidFilter extends AbstractWidgetGroup {

    private final int yPosition;
    private Supplier<FluidFilter> itemFilterSupplier;
    private FluidFilter itemFilter;

    public WidgetGroupFluidFilter(int yPosition, Supplier<FluidFilter> itemFilterSupplier) {
        this.yPosition = yPosition;
        this.itemFilterSupplier = itemFilterSupplier;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        FluidFilter newItemFilter = itemFilterSupplier.get();
        if(itemFilter != newItemFilter) {
            clearAllWidgets();
            this.itemFilter = newItemFilter;
            if(itemFilter != null) {
                this.itemFilter.initUI(yPosition, this::addWidget);
            }
            writeUpdateInfo(2, buffer -> {
                if(itemFilter != null) {
                    buffer.writeBoolean(true);
                    int filterId = FilterTypeRegistry.getIdForFluidFilter(itemFilter);
                    buffer.writeVarInt(filterId);
                } else {
                    buffer.writeBoolean(false);
                }
            });
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if(id == 2) {
            clearAllWidgets();
            if(buffer.readBoolean()) {
                int filterId = buffer.readVarInt();
                this.itemFilter = FilterTypeRegistry.createFluidFilterById(filterId);
                this.itemFilter.initUI(yPosition, this::addWidget);
            }
        }
    }
}
