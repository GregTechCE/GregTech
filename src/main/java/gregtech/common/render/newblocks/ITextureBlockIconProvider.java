package gregtech.common.render.newblocks;

import gregtech.api.interfaces.ITexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface ITextureBlockIconProvider {

    public ITexture[] getTexture(World world, BlockPos blockPos, IExtendedBlockState blockState, EnumFacing side);
    public ITexture[] getItemblockTexture(EntityPlayer player, ItemStack itemStack, EnumFacing side);

}
