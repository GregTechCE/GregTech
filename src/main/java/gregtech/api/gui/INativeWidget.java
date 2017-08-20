package gregtech.api.gui;

import net.minecraft.inventory.Slot;

/**
 * Native widget is widget that is based on MC native container mechanics
 * This widget logic delegates to MC slot
 */
public interface INativeWidget {

    Slot getNativeHandle();

}
