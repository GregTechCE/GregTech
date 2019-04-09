package gregtech.api.gui.widgets;

import gregtech.api.gui.resources.TextureArea;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.BooleanSupplier;

public class ImageWidget extends AbstractPositionedRectangleWidget {

    protected TextureArea area;

    private BooleanSupplier predicate;
    private boolean isVisible = true;

    public ImageWidget(int xPosition, int yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
    }

    public ImageWidget(int xPosition, int yPosition, int width, int height, TextureArea area) {
        super(xPosition, yPosition, width, height);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.area = area;
    }

    public ImageWidget setImage(TextureArea area) {
        this.area = area;
        return this;
    }

    public ImageWidget setPredicate(BooleanSupplier predicate) {
        this.predicate = predicate;
        this.isVisible = false;
        return this;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (predicate != null && predicate.getAsBoolean() != isVisible) {
            this.isVisible = predicate.getAsBoolean();
            writeUpdateInfo(1, buf -> buf.writeBoolean(isVisible));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if (id == 1) {
            this.isVisible = buffer.readBoolean();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(int mouseX, int mouseY) {
        if (!this.isVisible || area == null) return;
        area.draw(xPosition, yPosition, width, height);
    }

}

