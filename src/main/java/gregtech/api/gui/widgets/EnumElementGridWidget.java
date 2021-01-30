package gregtech.api.gui.widgets;

import com.google.common.base.Preconditions;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.ElementGrid2D;
import gregtech.api.util.Position;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.util.*;

public class EnumElementGridWidget<E extends Enum<E>> extends ElementGridWidget<E> {

    private final List<E> boundElements = new ArrayList<>();
    private final Map<E, BoundElementInfo> displayInfo = new HashMap<>();
    protected final Class<E> enumClass;

    //Cached to avoid allocation of new arrays for serialization of every element
    private final E[] cachedEnumConstants;

    private boolean allowElementChange = true;

    public EnumElementGridWidget(int posX, int posY, Class<E> enumClass, ElementGrid2D<E> grid) {
        super(posX, posY, grid);
        Preconditions.checkArgument(enumClass.isEnum(), "enumClass.isEnum()");
        this.enumClass = enumClass;
        this.cachedEnumConstants = this.enumClass.getEnumConstants();
    }

    public EnumElementGridWidget<E> bindElementInfo(E element, String tooltipText, TextureArea texture) {
        this.boundElements.add(element);
        this.displayInfo.put(element, new BoundElementInfo(tooltipText, texture));
        return this;
    }

    public EnumElementGridWidget<E> setAllowElementChange(boolean allowElementChange) {
        this.allowElementChange = allowElementChange;
        return this;
    }

    @Override
    protected void drawElement(int drawPosX, int drawPosY, Position elementPos, E elementAtPos) {
        if (displayInfo.containsKey(elementAtPos)) {
            BoundElementInfo elementInfo = displayInfo.get(elementAtPos);
            elementInfo.texture.draw(drawPosX, drawPosY, elementDisplaySize.width, elementDisplaySize.height);
        }
    }

    @Override
    protected void drawElementHovered(int mouseX, int mouseY, Position elementPos, E elementInSlot) {
        if (displayInfo.containsKey(elementInSlot)) {
            BoundElementInfo elementInfo = displayInfo.get(elementInSlot);
            List<String> translatedTooltipText = Arrays.asList(I18n.format(elementInfo.tooltipText).split("/n"));
            drawHoveringText(ItemStack.EMPTY, translatedTooltipText, -1, mouseX, mouseY);
        }
    }

    @Override
    protected boolean handleElementClicked(int mouseX, int mouseY, Position elementPos, E clickedElement) {
        //Clicking cycles through possible element values
        if (allowElementChange && displayInfo.containsKey(clickedElement)) {
            int elementIndex = boundElements.indexOf(clickedElement);
            int nextElementIndex = (elementIndex + 1) % boundElements.size();
            E newElement = boundElements.get(nextElementIndex);

            writeClientAction(1, buf -> {
                buf.writeVarInt(elementPos.x);
                buf.writeVarInt(elementPos.y);
                serializeElement(buf, newElement);
            });

            playButtonClickSound();
            return true;
        }

        return false;
    }

    @Override
    public void handleClientAction(int id, PacketBuffer buffer) {
        super.handleClientAction(id, buffer);

        //User requested element change
        if (id == 1) {
            if (allowElementChange) {
                int elementPosX = buffer.readVarInt();
                int elementPosY = buffer.readVarInt();
                E serializedElement = deserializeElement(buffer);

                Preconditions.checkArgument(serverSideGrid.isElementPositionValid(elementPosX, elementPosY), "Invalid position received from client");
                Preconditions.checkArgument(displayInfo.containsKey(serializedElement), "Unknown element received from client");

                //Well, if element is registered and has a valid index, we can update it in the master grid
                serverSideGrid.setElement(elementPosX, elementPosY, serializedElement);
            }
        }
    }

    @Override
    protected void serializeElement(PacketBuffer buf, E element) {
        buf.writeVarInt(element.ordinal());
    }

    @Override
    protected E deserializeElement(PacketBuffer buf) {
        int elementOrdinal = buf.readVarInt();
        Preconditions.checkElementIndex(elementOrdinal, cachedEnumConstants.length, "elementOrdinal out of cachedEnumConstants.length");
        return cachedEnumConstants[elementOrdinal];
    }

    private static class BoundElementInfo {
        public final String tooltipText;
        public final TextureArea texture;

        public BoundElementInfo(String tooltipText, TextureArea texture) {
            this.tooltipText = tooltipText;
            this.texture = texture;
        }
    }
}
