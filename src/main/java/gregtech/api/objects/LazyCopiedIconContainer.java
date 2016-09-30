package gregtech.api.objects;

import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class LazyCopiedIconContainer implements IIconContainer {

    private final String mSpriteName;
    private TextureAtlasSprite lazyComputedSprite;

    public LazyCopiedIconContainer(String mSpriteName) {
        this.mSpriteName = mSpriteName;
    }

    @Override
    public TextureAtlasSprite getIcon() {
        if(lazyComputedSprite == null) {
            lazyComputedSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(mSpriteName);
        }
        return lazyComputedSprite;
    }

    @Override
    public TextureAtlasSprite getOverlayIcon() {
        return null;
    }
}
