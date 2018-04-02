package gregtech.api.gui;

import gregtech.api.GTValues;
import gregtech.api.gui.resources.TextureArea;
import net.minecraft.util.ResourceLocation;

public class GuiTextures {

    //BASE TEXTURES
    public static final TextureArea BACKGROUND = TextureArea.fullImage("textures/gui/base/background.png");
    public static final TextureArea SLOT = TextureArea.fullImage("textures/gui/base/slot.png");
    public static final TextureArea FLUID_SLOT = TextureArea.fullImage("textures/gui/base/fluid_slot.png");

    //FLUID & ITEM OUTPUT BUTTONS
    public static final TextureArea BUTTON_FLUID_OUTPUT = TextureArea.fullImage("textures/gui/base/button_fluid_output.png");
    public static final TextureArea BUTTON_ITEM_OUTPUT = TextureArea.fullImage("textures/gui/base/button_item_output.png");

    //INDICATORS
    public static final TextureArea INDICATOR_NO_ENERGY = TextureArea.fullImage("textures/gui/base/indicator_no_energy.png");

    //SLOT OVERLAYS
    public static final TextureArea CHARGER_OVERLAY = TextureArea.fullImage("textures/gui/base/charger_slot_overlay.png");
    public static final TextureArea INT_CIRCUIT_OVERLAY = TextureArea.fullImage("textures/gui/base/int_circuit_overlay.png");

}
