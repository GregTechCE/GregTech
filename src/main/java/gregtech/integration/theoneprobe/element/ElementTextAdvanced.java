package gregtech.integration.theoneprobe.element;

import gregtech.integration.theoneprobe.TheOneProbeCompatibility;
import io.netty.buffer.ByteBuf;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.network.NetworkTools;

import static gregtech.integration.theoneprobe.element.ElementRenders.*;

/**
 * Advanced version of {@link mcjty.theoneprobe.apiimpl.elements.ElementText}
 * Use "{*key{%replacement1%}{%replacement2%}...{%replacementN%}*}"
 * for those translations with placeholders.
 */
public class ElementTextAdvanced implements IElement {

    public static final String SUBSTARTLOC = "{%";
    public static final String SUBENDLOC = "%}";

    private final String text;

    public ElementTextAdvanced(String text) {
        this.text = text;
    }

    public ElementTextAdvanced(ByteBuf buf) {
        text = NetworkTools.readStringUTF8(buf);
    }


    @Override
    public void render(int x, int y) {
        renderTextAdvanced(text, x, y);
    }

    @Override
    public int getWidth() {
        return getStringWidth(stylifyStringAdvanced(text));
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NetworkTools.writeStringUTF8(buf, text);
    }

    @Override
    public int getID() {
        return TheOneProbeCompatibility.ELEMENT_TEXT_ADVANCED;
    }
}
