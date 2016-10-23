package gregtech.common.render.data;

import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class DefaultDataGetter implements IconDataGetter {

    private TextureMap textureMap;

    public DefaultDataGetter(TextureMap textureMap) {
        this.textureMap = textureMap;
    }

    @Override
    public IIconData makeIconData(Object... iconPaths) {
        //register sprites and replacing strings and resource locations with them
        Object[] parsedData = new Object[iconPaths.length];
        for(int i = 0; i < iconPaths.length; i++) {
            Object object = iconPaths[i];
            if(object instanceof String) {
                String path = (String) object;
                TextureAtlasSprite atlasSprite = textureMap.registerSprite(new ResourceLocation(path));
                parsedData[i] = atlasSprite;
            } else if(object instanceof ResourceLocation) {
                ResourceLocation path = (ResourceLocation) object;
                TextureAtlasSprite atlasSprite = textureMap.registerSprite(path);
                parsedData[i] = atlasSprite;
            } else {
                parsedData[i] = object;
            }
        }
        return new DefaultQuadData(parsedData);
    }

    @Override
    public TextureMap getMap() {
        return textureMap;
    }

}
