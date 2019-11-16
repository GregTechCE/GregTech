package gregtech.api.gui;

import net.minecraft.inventory.Slot;

public interface IRenderContext {

    void drawSlotContents(Slot slot);

    void renderSlotOverlay(Slot slot);

    void setHoveredSlot(Slot hoveredSlot);
}
