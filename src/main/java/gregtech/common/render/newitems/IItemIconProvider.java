package gregtech.common.render.newitems;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IItemIconProvider {

    TextureAtlasSprite getIcon(ItemStack stack, int pass);

    default int getRenderPasses(ItemStack stack) {
        return 0;
    }

}
