package gregtech.integration.jei.utils.render;

import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ScaledRenderer<T> implements IIngredientRenderer<T> {
    private final double scale;
    private final IIngredientRenderer<T> delegate;

    public ScaledRenderer(double scale, IIngredientRenderer<T> delegate) {
        this.scale = scale;
        this.delegate = delegate;
    }

    @Override
    public void render(Minecraft minecraft, int xPosition, int yPosition, @Nullable T ingredient) {
        if(ingredient != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(xPosition, yPosition, 0);
            GlStateManager.scale(scale, scale, 1);
            delegate.render(minecraft, 0, 0, ingredient);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public List<String> getTooltip(Minecraft minecraft, T ingredient, ITooltipFlag tooltipFlag) {
        return delegate.getTooltip(minecraft, ingredient, tooltipFlag);
    }

    @Override
    public FontRenderer getFontRenderer(Minecraft minecraft, T ingredient) {
        return delegate.getFontRenderer(minecraft, ingredient);
    }
}
