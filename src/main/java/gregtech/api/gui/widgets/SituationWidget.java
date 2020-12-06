package gregtech.api.gui.widgets;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.util.Position;
import gregtech.api.util.Size;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.function.IntSupplier;

import static gregtech.api.situation.Situation.getSituationFromId;
import static gregtech.api.situation.SituationTypes.*;

public class SituationWidget extends Widget {

    private final IntSupplier currentSituationId;
    protected String tooltipHoverString;
    protected int currentError;

    protected TextureArea area;
    private boolean isVisible = true;

    public SituationWidget(int xPosition, int yPosition, int width, int height, IntSupplier getSituationId) {
        super(new Position(xPosition, yPosition), new Size(width, height));
        this.currentSituationId = () -> getSituationId.getAsInt();
        setTooltipHoverString();
        setImage();
    }

    public void setTooltipHoverString() {
        this.tooltipHoverString = I18n.format(getSituationFromId(currentError).situationLocaleName);
    }

    public SituationWidget setImage() {
        Enum iconTextureEnum = getSituationFromId(currentError).situationTypes;
        if (iconTextureEnum.equals(ERROR)) this.area = GuiTextures.STATUS_ERROR;
        else if (iconTextureEnum.equals(WARNING)) this.area = GuiTextures.STATUS_WARNING;
        else if (iconTextureEnum.equals(WORKING)) this.area = GuiTextures.STATUS_WORKING;
        else if (iconTextureEnum.equals(IDLE)) this.area = GuiTextures.STATUS_IDLING;
        else {
            this.area = null;
        }
        return this;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (currentSituationId.getAsInt() != currentError) {
            this.currentError = currentSituationId.getAsInt();
            writeUpdateInfo(1, buf -> buf.writeVarInt(currentError));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if (id == 1) {
            this.currentError = buffer.readVarInt();
            setTooltipHoverString();
            setImage();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInForeground(int mouseX, int mouseY) {
        drawHoveringTooltip(mouseX, mouseY, tooltipHoverString);
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
