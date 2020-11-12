package gregtech.api.gui.widgets;

import com.sun.jna.platform.win32.Guid;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.GTUtility;
import gregtech.api.util.Position;
import gregtech.api.util.Size;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.function.IntSupplier;
import java.util.function.Supplier;


public class DiagnoseWidget extends Widget {

    private final String[] issueNames;
    private IntSupplier currentIssue;
    protected String tooltipHoverString;
    protected long hoverStartTime = -1L;
    protected boolean isMouseHovered;
    protected int currentError;

    protected TextureArea area;
    private boolean isVisible = true;

    public <T extends Enum<T> & IStringSerializable> DiagnoseWidget(int xPosition, int yPosition, int width, int height, Class<T> enumClass, Supplier<T> supplier) {
        super(new Position(xPosition, yPosition), new Size(width, height));
        T[] enumConstantPool = enumClass.getEnumConstants();
        this.issueNames = GTUtility.mapToString(enumConstantPool, it -> ((IStringSerializable) it).getName());
        this.currentIssue = () -> supplier.get().ordinal();
        setImage();
    }

    public void setTooltipHoverString() {
        this.tooltipHoverString = I18n.format(issueNames[currentError]);
    }

    public DiagnoseWidget setImage() {
        if (currentError == 0) {
            this.area = GuiTextures.DIAGNOSE_IDLING;
        }
        if (currentError == 1) {
            this.area = GuiTextures.DIAGNOSE_WORKING;
        }
        else if (currentError > 1) {
            this.area = GuiTextures.DIAGNOSE_ISSUE;
        }
        return this;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (currentIssue.getAsInt() != currentError) {
            this.currentError = currentIssue.getAsInt();
            writeUpdateInfo(1, buf -> buf.writeVarInt(currentError));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if (id == 1) {
            this.currentError = buffer.readVarInt();
            setImage();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
        boolean isHovered = isMouseOverElement(mouseX, mouseY);
        boolean wasHovered = isMouseHovered;
        if (isHovered && !wasHovered) {
            this.isMouseHovered = true;
            this.hoverStartTime = System.currentTimeMillis();
        } else if (!isHovered && wasHovered) {
            this.isMouseHovered = false;
            this.hoverStartTime = 0L;
        } else if (isHovered) {
            long timeSinceHover = System.currentTimeMillis() - hoverStartTime;
            setTooltipHoverString();
            if (timeSinceHover > 1000L && tooltipHoverString != null) {
                drawHoveringText(ItemStack.EMPTY, Collections.singletonList(tooltipHoverString), 300, mouseX, mouseY);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int mouseX, int mouseY, IRenderContext context) {
        if (!this.isVisible || area == null) return;
        Position position = getPosition();
        Size size = getSize();
        area.draw(position.x, position.y, size.width, size.height);
    }
}
