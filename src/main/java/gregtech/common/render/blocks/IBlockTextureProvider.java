package gregtech.common.render.blocks;

import gregtech.api.interfaces.ITexture;
import gregtech.common.render.data.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Implement on blocks which care about {@link BlockPos} or {@link ItemStack} AND
 * have custom rendering behaviour defined in {@link ITexture}s
 *
 * @see ITexture
 * @see IIconRegister
 * @see IBlockIconProvider
 */
public interface IBlockTextureProvider {


    @SideOnly(Side.CLIENT)
    ITexture[] getTexture(World world, BlockPos blockPos, IExtendedBlockState blockState, EnumFacing side);

    @SideOnly(Side.CLIENT)
    ITexture[] getItemblockTexture(EntityPlayer player, ItemStack itemStack, EnumFacing side);

}
