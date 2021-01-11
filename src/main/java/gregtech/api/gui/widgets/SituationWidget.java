package gregtech.api.gui.widgets;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.IRenderContext;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.situation.Situation;
import gregtech.api.situation.SituationTypes;
import gregtech.api.util.Position;
import gregtech.api.util.Size;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.function.Supplier;

public class SituationWidget extends Widget {

    private final Supplier<Situation> currentSituationSupplier;
    private Situation currentSituation;
    protected String tooltipHoverString;
    protected int currentId;

    protected TextureArea area;

    public <T extends Situation> SituationWidget(int xPosition, int yPosition, int width, int height, Supplier<Situation> getSituation) {
        super(new Position(xPosition, yPosition), new Size(width, height));
        this.currentSituationSupplier = getSituation;
        this.currentSituation = getSituation.get();
        setTooltipHoverString();
        setImage();
    }

    public void setTooltipHoverString() {
        if (this.currentSituation != null) {
            this.tooltipHoverString = I18n.format(this.currentSituation.situationLocaleName);
        } else {
            this.tooltipHoverString = null;
        }
    }

    public SituationWidget setImage() {
        if (this.currentSituation == null) {
            this.area = null;
            return this;
        }
        SituationTypes iconTextures = this.currentSituation.situationTypes;
        switch (iconTextures) {
            case IDLE:
                this.area = GuiTextures.STATUS_IDLING;
                break;
            case WORKING:
                this.area = GuiTextures.STATUS_WORKING;
                break;
            case WARNING:
                this.area = GuiTextures.STATUS_WARNING;
                break;
            case ERROR:
                this.area = GuiTextures.STATUS_ERROR;
                break;
            default:
                this.area = null;
        }
        return this;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (currentSituationSupplier.get() != null) {
            if (currentSituationSupplier.get().id != currentId) {
                this.currentId = currentSituationSupplier.get().id;
                writeUpdateInfo(1, buf -> buf.writeVarInt(currentId));
            }
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if (id == 1) {
            this.currentId = buffer.readVarInt();
            this.currentSituation = Situation.getSituationFromId(currentId);
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
        if (area == null) return;
        Position position = getPosition();
        Size size = getSize();
        area.draw(position.x, position.y, size.width, size.height);
    }
}
