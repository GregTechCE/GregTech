package gregtech.api.objects;

import gregtech.api.GregTech_API;
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
            lazyComputedSprite = GregTech_API.sBlockIcons.getAtlasSprite(mSpriteName);
            System.out.println("Lazy-initialized sprite " + lazyComputedSprite.getIconName() + " " + this);
        }
        return lazyComputedSprite;
    }

    @Override
    public TextureAtlasSprite getOverlayIcon() {
        return null;
    }


}
