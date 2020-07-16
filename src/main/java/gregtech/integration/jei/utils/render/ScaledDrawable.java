package gregtech.integration.jei.utils.render;

import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import javax.annotation.Nonnull;

public class ScaledDrawable implements IDrawable {
    private final double scale;
    private final IDrawable delegate;

    public ScaledDrawable(double scale, IDrawable delegate) {
        this.scale = scale;
        this.delegate = delegate;
    }

    @Override
    public int getWidth() {
        return (int) (scale * delegate.getWidth());
    }

    @Override
    public int getHeight() {
        return (int) (scale * delegate.getHeight());
    }

    @Override
    public void draw(@Nonnull Minecraft minecraft, int xOffset, int yOffset) {
        GlStateManager.pushMatrix();
        if (xOffset * yOffset != 0)
            GlStateManager.translate(xOffset, yOffset, 0);
        GlStateManager.scale(scale, scale, 1);
        delegate.draw(minecraft);
        GlStateManager.popMatrix();
    }
}
