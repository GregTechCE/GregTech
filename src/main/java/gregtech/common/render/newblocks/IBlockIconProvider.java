package gregtech.common.render.newblocks;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
