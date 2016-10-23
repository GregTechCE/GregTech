package gregtech.common.render.items;

import gregtech.common.render.data.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Implement on items which need either simple or advanced rendering
 * If you need simple behavior, implement {@link IItemIconProvider#getIcon(ItemStack, int)}
 *
 * If you need multilayer, you can override {@link IItemIconProvider#getRenderPasses(ItemStack)}
 * If you need handheld item, return true in {@link IItemIconProvider#isHandheld(ItemStack)}
 *
 * @see IIconRegister
 * @see TextureAtlasSprite
 * @see IItemIconContainerProvider
 */
public interface IItemIconProvider {

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite getIcon(ItemStack stack, int pass);

    /**
     * Returns amount of additional render passes needed for item
     * @param stack item stack
     * @return 0 if dont need multilayer rendering, amount of layers otherwise
     */
    @SideOnly(Side.CLIENT)
    default int getRenderPasses(ItemStack stack) {
        return 0;
    }

    /**
     * @param stack item stack
     * @return true in item is handheld
     */
    @SideOnly(Side.CLIENT)
    default boolean isHandheld(ItemStack stack) {
        return false;
    }

}
