package gregtech.api.enums;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.objects.CachedIconContainer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;

public enum MaterialType {

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

    MaterialType() {
        if(FMLCommonHandler.instance().getSide().isClient()) {
            initTextures();
        }
    }

    @SideOnly(Side.CLIENT)
    private EnumMap<MaterialTypeTexture, IIconContainer> textures;

    @SideOnly(Side.CLIENT)
    public IIconContainer getTexture(MaterialTypeTexture texture) {
        return textures.get(texture);
    }

    @SideOnly(Side.CLIENT)
    private void initTextures() {
        textures = new EnumMap<>(MaterialTypeTexture.class);
        for(MaterialTypeTexture texture : MaterialTypeTexture.values()) {
            textures.put(texture, CachedIconContainer.createWithOverlay(texture.getTexturePath(this)));
        }
    }

}
