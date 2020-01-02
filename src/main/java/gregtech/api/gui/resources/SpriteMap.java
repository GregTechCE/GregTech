package gregtech.api.gui.resources;

import com.google.common.base.Preconditions;
import net.minecraft.util.ResourceLocation;

public class SpriteMap {

    private final ResourceLocation imageLocation;
    private final int spritesPerRow;

    public SpriteMap(ResourceLocation imageLocation, int spritesPerRow) {
        this.imageLocation = imageLocation;
        this.spritesPerRow = spritesPerRow;
    }

    public TextureArea getSprite(int x, int y) {
        Preconditions.checkElementIndex(x, spritesPerRow, "Invalid sprite x!");
        Preconditions.checkElementIndex(y, spritesPerRow, "Invalid sprite y!");
        double spriteUnit = 1 / (spritesPerRow * 1.0);
        return new TextureArea(imageLocation, spriteUnit * x, spriteUnit * y, spriteUnit, spriteUnit);
    }
}
