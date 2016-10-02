package gregtech.common.render;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Implement on blocks and items to access texture map and allow icon registration
 * You can register you own sprite using:
 *
 * TextureAtlasSprite icon = textureMap.registerSprite(ResourceLocation)
 *
 * @see TextureMap
 * @see net.minecraft.client.renderer.texture.TextureAtlasSprite
 * @see gregtech.common.render.items.IItemIconProvider
 */
public interface IIconRegister {

    @SideOnly(Side.CLIENT)
    void registerIcons(TextureMap textureMap);

}
