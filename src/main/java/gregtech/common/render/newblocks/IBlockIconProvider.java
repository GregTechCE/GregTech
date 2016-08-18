package gregtech.common.render.newblocks;

import gregtech.api.items.GT_Generic_Block;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IBlockIconProvider {

    public TextureAtlasSprite getIcon(EnumFacing aSide, int aDamage);

    default TextureAtlasSprite getIcon(IBlockAccess world, BlockPos pos, EnumFacing aSide) {
        return getIcon(aSide, world.getBlockState(pos).getValue(GT_Generic_Block.METADATA));
    }

    default TextureAtlasSprite getIcon(ItemStack itemStack, EnumFacing aSide) {
        return getIcon(aSide, itemStack.getItemDamage());
    }

}
