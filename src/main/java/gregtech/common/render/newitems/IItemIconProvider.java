package gregtech.common.render.newitems;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemIconProvider {

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite getIcon(ItemStack stack, int pass);

    @SideOnly(Side.CLIENT)
    default int getRenderPasses(ItemStack stack) {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    default boolean isHandheld(ItemStack stack) {
        return false;
    }

}
