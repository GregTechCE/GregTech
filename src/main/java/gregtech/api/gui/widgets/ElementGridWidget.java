package gregtech.api.gui.widgets;

import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.ElementGrid2D;
import gregtech.api.util.Position;
import gregtech.api.util.Size;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.util.*;

public abstract class ElementGridWidget<T> extends Widget {

    protected final ElementGrid2D<T> serverSideGrid;
    protected final ElementGrid2D<T> replicatedGrid = new ElementGrid2D<>();

    protected Size elementDisplaySize = new Size(16, 16);
    protected Size elementSpacing = new Size(4, 4);

    public ElementGridWidget(int posX, int posY, ElementGrid2D<T> grid) {
        super(new Position(posX, posY), Size.ZERO);
        this.serverSideGrid = grid;
    }

    public ElementGridWidget<T> setDisplayInfo(Size elementSize, Size elementSpacing) {
        this.elementDisplaySize = elementSize;
        this.elementSpacing = elementSpacing;
        return this;
    }

    protected Position getElementPositionUnderMouse(int mouseX, int mouseY) {
        if (isMouseOverElement(mouseX, mouseY)) {
            Position widgetPosition = getPosition();
            int relativeMouseX = mouseX - widgetPosition.x;
            int relativeMouseY = mouseY - widgetPosition.y;

            int slotSizeHorizontal = elementDisplaySize.width + elementSpacing.width;
            int slotSizeVertical = elementDisplaySize.height + elementSpacing.height;

            int hoveredSlotX = relativeMouseX / slotSizeHorizontal;
            int hoveredSlotY = relativeMouseY / slotSizeVertical;

            //Make sure we didn't hit spacing between elements, and if we did, simply return null
            int mousePosXRelativeToSlot = relativeMouseX % slotSizeHorizontal;
            int mousePosYRelativeToSlot = relativeMouseY % slotSizeVertical;

            if (mousePosXRelativeToSlot >= elementDisplaySize.width ||
                mousePosYRelativeToSlot >= elementDisplaySize.height) {
                return null;
            }

            return new Position(hoveredSlotX, hoveredSlotY);
        }
        return null;
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY, IRenderContext context) {
        Position widgetPosition = getPosition();

        for (int i = 0; i < serverSideGrid.getGridSizeY(); i++) {
            int elementPosY = widgetPosition.y + (elementDisplaySize.height + elementSpacing.height) * i;

            for (int j = 0; j < serverSideGrid.getGridSizeX(); j++) {
                int elementPosX = widgetPosition.x + (elementDisplaySize.width + elementSpacing.width) * j;
                T elementAtPos = replicatedGrid.getElement(j, i);

                if (elementAtPos != null) {
                    drawElement(elementPosX, elementPosY, new Position(j, i), elementAtPos);
                }
            }
        }
    }

    protected abstract void drawElement(int drawPosX, int drawPosY, Position elementPos, T elementAtPos);

    @Override
    public void drawInForeground(int mouseX, int mouseY) {
        super.drawInForeground(mouseX, mouseY);

        Position elementUnderMouse = getElementPositionUnderMouse(mouseX, mouseY);
        if (elementUnderMouse != null) {
            T elementInSlot = replicatedGrid.getElement(elementUnderMouse.x, elementUnderMouse.y);

            if (elementInSlot != null) {
                drawElementHovered(mouseX, mouseY, elementUnderMouse, elementInSlot);
            }
        }
    }

    protected abstract void drawElementHovered(int mouseX, int mouseY, Position elementPos, T elementInSlot);

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int button) {
        Position elementUnderMouse = getElementPositionUnderMouse(mouseX, mouseY);
        if (elementUnderMouse != null) {
            T elementInSlot = replicatedGrid.getElement(elementUnderMouse.x, elementUnderMouse.y);

            if (elementInSlot != null) {
                return handleElementClicked(mouseX, mouseY, elementUnderMouse, elementInSlot);
            }
        }
        return false;
    }

    protected boolean handleElementClicked(int mouseX, int mouseY, Position elementPos, T clickedElement) {
        return false;
    }

    private void recomputeWidgetSize() {
        int gridSizeX = replicatedGrid.getGridSizeX();
        int gridSizeY = replicatedGrid.getGridSizeY();
        int horizontalSize = (elementDisplaySize.width + elementSpacing.width) * gridSizeX - elementSpacing.width;
        int verticalSize = (elementDisplaySize.height + elementSpacing.height) * gridSizeY - elementSpacing.height;
        setSize(new Size(Math.max(0, horizontalSize), Math.max(0, verticalSize)));
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        //Update grid size on the client if it doesn't match server's one
        if (!replicatedGrid.areGridsSizedTheSame(serverSideGrid)) {
            this.replicatedGrid.resizeGrid(serverSideGrid.getGridSizeX(), serverSideGrid.getGridSizeY());
            recomputeWidgetSize();

            //Make sure replicated grid has the same layout as the server one to avoid excessive re-sync after bulk update
            for (int i = 0; i < serverSideGrid.getGridSizeY(); i++) {
                for (int j = 0; j < serverSideGrid.getGridSizeX(); j++) {
                    T element = serverSideGrid.getElement(j, i);
                    this.replicatedGrid.setElement(j, i, element);
                }
            }

            //Serialize data required to resize grid on the client
            writeUpdateInfo(1, buf -> {
                buf.writeVarInt(serverSideGrid.getGridSizeX());
                buf.writeVarInt(serverSideGrid.getGridSizeY());

                for (int i = 0; i < serverSideGrid.getGridSizeY(); i++) {
                    for (int j = 0; j < serverSideGrid.getGridSizeX(); j++) {
                        T element = serverSideGrid.getElement(j, i);
                        serializeElement(buf, element);
                    }
                }
            });
        }

        //Collect all the positions that have been changed into the array
        ArrayList<Position> changedPositions = new ArrayList<>();

        for (int i = 0; i < serverSideGrid.getGridSizeY(); i++) {
            for (int j = 0; j < serverSideGrid.getGridSizeX(); j++) {
                T serverSideElement = serverSideGrid.getElement(j, i);
                T replicatedElement = replicatedGrid.getElement(j, i);
                if (!Objects.equals(serverSideElement, replicatedElement)) {
                    changedPositions.add(new Position(j, i));
                }
            }
        }

        //Apply individual changes to the replicated grid state
        for (Position position : changedPositions) {
            T elementAtPosition = serverSideGrid.getElement(position.x, position.y);
            replicatedGrid.setElement(position.x, position.y, elementAtPosition);
        }

        //Send updated positions to the client in bulk if we have them
        if (changedPositions.size() > 0) {
            writeUpdateInfo(2, buf -> {
                buf.writeVarInt(changedPositions.size());
                for (Position position : changedPositions) {
                    T elementAtPosition = serverSideGrid.getElement(position.x, position.y);
                    buf.writeVarInt(position.x);
                    buf.writeVarInt(position.y);
                    serializeElement(buf, elementAtPosition);
                }
            });
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);

        //Update 1 is the full contents sync, elements follow the strict order
        if (id == 1) {
            int newGridSizeX = buffer.readVarInt();
            int newGridSizeY = buffer.readVarInt();
            this.replicatedGrid.resizeGrid(newGridSizeX, newGridSizeY);
            recomputeWidgetSize();

            for (int i = 0; i < serverSideGrid.getGridSizeY(); i++) {
                for (int j = 0; j < serverSideGrid.getGridSizeX(); j++) {
                    T element = deserializeElement(buffer);
                    this.replicatedGrid.setElement(j, i, element);
                }
            }
        }

        //Update 2 is individual changes in the elements, processed one by one
        if (id == 2) {
            int changedPositionsNum = buffer.readVarInt();
            for (int i = 0; i < changedPositionsNum; i++) {
                int elementPosX = buffer.readVarInt();
                int elementPosY = buffer.readVarInt();
                T deserializedElement = deserializeElement(buffer);
                this.replicatedGrid.setElement(elementPosX, elementPosY, deserializedElement);
            }
        }
    }

    protected abstract void serializeElement(PacketBuffer buf, T element);
    protected abstract T deserializeElement(PacketBuffer buf);
}
