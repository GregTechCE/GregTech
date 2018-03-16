package gregtech.api.gui.widgets;

import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.Widget;
import gregtech.api.gui.resources.TextureArea;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Predicate;

public class ImageWidget<T extends IUIHolder> extends Widget<T> {

    protected TextureArea area;
    protected final int xPosition;
    protected final int yPosition;
    private int width;
    private int height;

    private ResourceLocation imageLocation;

    private Predicate<T> predicate;

    public ImageWidget(int xPosition, int yPosition, int width, int height) {
        super(SLOT_DRAW_PRIORITY - 100);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
    }

    public ImageWidget<T> setImage(TextureArea area) {
        this.area = area;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInBackground(float partialTicks, int mouseX, int mouseY) {
        if (area == null || predicate != null && !predicate.test(this.gui.holder)) {
            return;
        }
        area.draw(xPosition, yPosition, width, height);
    }

}

