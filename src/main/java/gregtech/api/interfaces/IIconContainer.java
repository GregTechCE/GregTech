package gregtech.api.interfaces;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public interface IIconContainer {
    /**
     * @return A regular Icon.
     */
    public TextureAtlasSprite getIcon();

    /**
     * @return Icon of the Overlay (or null if there is no Icon)
     */
    public TextureAtlasSprite getOverlayIcon();

}
