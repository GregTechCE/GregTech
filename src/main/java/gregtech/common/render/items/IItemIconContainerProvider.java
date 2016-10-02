package gregtech.common.render.items;

import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Simple multilayer implementation of {@link IItemIconProvider} for {@link IIconContainer} icons
 *
 * @see IIconContainer
 * @see IItemIconProvider
 */
@SideOnly(Side.CLIENT)
public interface IItemIconContainerProvider extends IItemIconProvider {

    @Override
    @SideOnly(Side.CLIENT)
    default TextureAtlasSprite getIcon(ItemStack stack, int pass) {
        IIconContainer iconContainer = getIconContainer(stack);
        if(iconContainer != null) {
            switch (pass) {
                case 0:
                    return iconContainer.getIcon();
                case 1:
                    return iconContainer.getOverlayIcon();
            }
        }
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    default int getRenderPasses(ItemStack stack) {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    IIconContainer getIconContainer(ItemStack itemStack);

}
