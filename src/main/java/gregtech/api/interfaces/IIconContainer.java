package gregtech.api.interfaces;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IIconContainer {
    /**
     * @return A regular Icon.
     */
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon();

    /**
     * @return Icon of the Overlay (or null if there is no Icon)
     */
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getOverlayIcon();

}
