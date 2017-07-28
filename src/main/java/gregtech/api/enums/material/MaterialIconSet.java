package gregtech.api.enums.material;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.objects.CachedIconContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;

public enum MaterialIconSet {

    NONE,
    METALLIC,
    DULL,
    MAGNETIC,
    QUARTZ,
    DIAMOND,
    EMERALD,
    SHINY,
    SHARDS,
    ROUGH,
    FINE,
    SAND,
    FLINT,
    RUBY,
    LAPIS,
    POWDER,
    FLUID,
    GAS,
    LIGNITE,
    OPAL,
    GLASS,
    WOOD,
    FIERY,
    LEAF,
    GEM_HORIZONTAL,
    GEM_VERTICAL,
    PAPER,
    NETHERSTAR;

    @SideOnly(Side.CLIENT)
    public static void init() {
        for(MaterialIconSet iconSet : values()) {
            iconSet.initTextures();
        }
    }

    @SideOnly(Side.CLIENT)
    private EnumMap<MaterialIconType, IIconContainer> textures;

    @SideOnly(Side.CLIENT)
    public IIconContainer getTexture(MaterialIconType texture) {
        return textures.get(texture);
    }

    @SideOnly(Side.CLIENT)
    private void initTextures() {
        textures = new EnumMap<>(MaterialIconType.class);
        for(MaterialIconType texture : MaterialIconType.values()) {
            textures.put(texture, CachedIconContainer.createWithOverlay(texture.getTexturePath(this)));
        }
    }

}
