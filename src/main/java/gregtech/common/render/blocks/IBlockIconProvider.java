package gregtech.common.render.blocks;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Implement on simple blocks which have only 1 sprite on each side
 * For simple behavior just implement on block class and override {@link IBlockIconProvider#getIcon(EnumFacing, int)}
 * If you care about {@link BlockPos} or @{@link ItemStack}, you can override getIcon based on them
 *
 * @see gregtech.common.render.IIconRegister
 * @see TextureAtlasSprite
 * @see IBlockIconProvider
 */
public interface IBlockIconProvider {

    @SideOnly(Side.CLIENT)
    TextureAtlasSprite getIcon(EnumFacing aSide, int aDamage);

    @SideOnly(Side.CLIENT)
    default TextureAtlasSprite getIcon(IBlockAccess world, BlockPos pos, EnumFacing aSide, int metadata) {
        return getIcon(aSide, metadata);
    }

    @SideOnly(Side.CLIENT)
    default TextureAtlasSprite getIcon(EntityPlayer player, ItemStack itemStack, EnumFacing aSide) {
        return getIcon(aSide, itemStack.getItemDamage());
    }

}
