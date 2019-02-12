package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import net.minecraft.network.PacketBuffer;

import java.util.function.DoubleSupplier;

public class ProgressWidget extends Widget {

    public enum MoveType {
        VERTICAL,
        HORIZONTAL
    }

    public final DoubleSupplier progressSupplier;
    private final int x, y, width, height;

    private MoveType moveType;
    private TextureArea emptyBarArea;
    private TextureArea filledBarArea;

    private double lastProgressValue;

    public ProgressWidget(DoubleSupplier progressSupplier, int x, int y, int width, int height) {
        super();
        this.progressSupplier = progressSupplier;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public ProgressWidget(DoubleSupplier progressSupplier, int x, int y, int width, int height, TextureArea fullImage, MoveType moveType) {
        super();
        this.progressSupplier = progressSupplier;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.emptyBarArea = fullImage.getSubArea(0.0, 0.0, 1.0, 0.5);
        this.filledBarArea = fullImage.getSubArea(0.0, 0.5, 1.0, 0.5);
        this.moveType = moveType;
    }

    public ProgressWidget setProgressBar(TextureArea emptyBarArea, TextureArea filledBarArea, MoveType moveType) {
        this.emptyBarArea = emptyBarArea;
        this.filledBarArea = filledBarArea;
        this.moveType = moveType;
        return this;
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY) {
        if(emptyBarArea != null) {
            emptyBarArea.draw(x, y, width, height);
        }
        if(filledBarArea != null) {
            //fuck this precision-dependent things, they are so fucking annoying
            if(moveType == MoveType.HORIZONTAL) {
                filledBarArea.drawSubArea(x, y, (int) (width * lastProgressValue), height,
                    0.0, 0.0, ((int) (width * lastProgressValue)) / (width * 1.0), 1.0);
            } else if(moveType == MoveType.VERTICAL) {
                int progressValueScaled = (int) (height * lastProgressValue);
                filledBarArea.drawSubArea(x, y + height - progressValueScaled, width, progressValueScaled,
                    0.0, 1.0 - (progressValueScaled / (height * 1.0)),
                    1.0, (progressValueScaled / (height * 1.0)));
            }
        }
    }

    @Override
    public void detectAndSendChanges() {
        double actualValue = progressSupplier.getAsDouble();
        //todo check if given epsilon is enough for long recipes
        if(Math.abs(actualValue - lastProgressValue) > 0.005) {
            this.lastProgressValue = actualValue;
            writeUpdateInfo(0, buffer -> buffer.writeDouble(actualValue));
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        if(id == 0) {
            this.lastProgressValue = buffer.readDouble();
        }
    }
}
