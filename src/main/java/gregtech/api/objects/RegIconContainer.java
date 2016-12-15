package gregtech.api.objects;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RegIconContainer implements IIconContainer, Runnable {

    private final String path;

    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite atlasSprite;

    public RegIconContainer(String path) {
        this.path = path;
        GregTech_API.sGTBlockIconload.add(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void run() {
        ResourceLocation resourceLocation = new ResourceLocation(path);
        TextureMap map = GregTech_API.sBlockIcons;

        atlasSprite = map.getTextureExtry(resourceLocation.toString());
        if(atlasSprite == null) {
            atlasSprite = map.registerSprite(resourceLocation);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon() {
        return atlasSprite;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getOverlayIcon() {
        return null;
    }

}
