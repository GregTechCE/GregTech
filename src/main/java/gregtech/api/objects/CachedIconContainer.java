package gregtech.api.objects;

import codechicken.lib.texture.TextureUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;

public class CachedIconContainer implements IIconContainer, TextureUtils.IIconRegister {

    private static HashMap<List<ResourceLocation>, CachedIconContainer> containerCache = new HashMap<>();

    public static CachedIconContainer create(ResourceLocation iconLocation, ResourceLocation overlayLocation) {
        Preconditions.checkNotNull(iconLocation, "Icon location cannot be null");
        List<ResourceLocation> paths = overlayLocation == null ? ImmutableList.of(iconLocation) : ImmutableList.of(iconLocation, overlayLocation);
        if(containerCache.containsKey(paths)) {
            return containerCache.get(paths);
        }
        CachedIconContainer iconContainer = new CachedIconContainer(paths);
        TextureUtils.addIconRegister(iconContainer);
        containerCache.put(paths, iconContainer);
        return iconContainer;
    }

    public static CachedIconContainer create(ResourceLocation iconLocation) {
        return create(iconLocation, null);
    }

    public static CachedIconContainer createWithOverlay(ResourceLocation iconLocation) {
        return create(iconLocation, new ResourceLocation(iconLocation.getResourceDomain(), iconLocation.getResourcePath() + "_OVERLAY"));
    }

    private List<ResourceLocation> resourceLocations;
    @SideOnly(Side.CLIENT)
    private TextureAtlasSprite[] resolvedSprites;

    private CachedIconContainer(List<ResourceLocation> resourceLocations) {
        this.resourceLocations = resourceLocations;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon() {
        return resolvedSprites[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getOverlayIcon() {
        return resolvedSprites.length > 1 ? resolvedSprites[2] : null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {
        resolvedSprites = new TextureAtlasSprite[resourceLocations.size()];
        for(int i = 0; i < resourceLocations.size(); i++) {
            resolvedSprites[i] = textureMap.registerSprite(resourceLocations.get(i));
        }
    }

}
