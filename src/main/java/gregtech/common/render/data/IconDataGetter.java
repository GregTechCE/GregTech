package gregtech.common.render.data;

import net.minecraft.client.renderer.texture.TextureMap;

public interface IconDataGetter {

    IIconData makeIconData(Object... iconData);
    TextureMap getMap();

}
