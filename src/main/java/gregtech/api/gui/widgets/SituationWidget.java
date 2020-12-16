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
    private boolean isVisible = true;

    public <T extends Situation> SituationWidget(int xPosition, int yPosition, int width, int height, Supplier <Situation> getSituation) {
        super(new Position(xPosition, yPosition), new Size(width, height));
        this.currentSituationSupplier = getSituation;
        this.currentSituation = getSituation.get();
        setTooltipHoverString();
        setImage();
    }

    public void setTooltipHoverString() {
        this.tooltipHoverString = I18n.format(this.currentSituation.situationLocaleName);
    }

    public SituationWidget setImage() {
        SituationTypes iconTextures = this.currentSituation.situationTypes;
        switch (iconTextures) {
            case IDLE:
                this.area = GuiTextures.STATUS_IDLING;
            case WORKING:
                this.area = GuiTextures.STATUS_WORKING;
            case WARNING:
                this.area = GuiTextures.STATUS_WARNING;
            case ERROR:
                this.area = GuiTextures.STATUS_ERROR;
            default:
                this.area = null;
        }
        return this;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (currentSituationSupplier.get().id != currentId) {
            this.currentId = currentSituationSupplier.get().id;
            writeUpdateInfo(1, buf -> buf.writeVarInt(currentId));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if (id == 1) {
            this.currentId = buffer.readVarInt();
            this.currentSituation = Situation.getSituationFromId(this.currentId);
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
