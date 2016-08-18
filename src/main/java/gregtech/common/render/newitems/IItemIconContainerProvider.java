package gregtech.common.render.newitems;

import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IItemIconContainerProvider extends IItemIconProvider {

    @Override
    default TextureAtlasSprite getIcon(ItemStack stack, int pass) {
        switch (pass) {
            case 0:
                return getIconContainer(stack).getIcon();
            case 1:
                return getIconContainer(stack).getOverlayIcon();
        }
        return null;
    }

    @Override
    default int getRenderPasses(ItemStack stack) {
        return 1;
    }

    IIconContainer getIconContainer(ItemStack itemStack);

}
