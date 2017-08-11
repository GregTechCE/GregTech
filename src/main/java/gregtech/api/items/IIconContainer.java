package gregtech.api.items;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IIconContainer {

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite getIcon();

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite getOverlayIcon();

}
