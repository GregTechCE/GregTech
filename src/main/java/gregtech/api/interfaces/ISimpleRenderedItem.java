package gregtech.api.interfaces;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * Implement this on items that require "simple" (1.7.10-alike) rendering with layered icons
 * Also supports coloring directly, without implementing IItemColor for item
 */
public interface ISimpleRenderedItem {

    /**
     * Registers icons in items TextureMap
     * Call {@link TextureMap#registerSprite(ResourceLocation)} to obtain {@link TextureAtlasSprite} instance
     */
    void registerIcons(TextureMap textureMap);

    /**
     * @return icon for ItemStack in given render pass.
     */
    TextureAtlasSprite getIcon(ItemStack itemStack, int renderPass);

    /**
     * @return amount of render passes needed for this ItemStack
     * 1 is equal to 1 pass, when 0 means no rendering at all.
     */
    default int getRenderPasses(ItemStack itemStack) {
        return 1;
    }

    /**
     * @return RGB color for ItemStack rendering.
     * Default color modifier is absolute white (i.e has no effect)
     */
    default int getColor(ItemStack itemStack) {
        return 0xFFFFFF;
    }

    /**
     * @return true if item is handheld, false otherwise
     */
    default boolean isHandheld(ItemStack itemStack) {
        return false;
    }

}
