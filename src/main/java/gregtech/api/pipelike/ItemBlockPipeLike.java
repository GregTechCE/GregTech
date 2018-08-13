package gregtech.api.pipelike;

import codechicken.lib.vec.Vector3;
import gregtech.api.GTValues;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockPipeLike<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty, C> extends ItemBlock {

    private BlockPipeLike<Q, P, C> blockPipeLike;

    public ItemBlockPipeLike(BlockPipeLike<Q, P, C> block) {
        super(block);
        blockPipeLike = block;
        setHasSubtypes(true);
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
        if (Loader.isModLoaded(GTValues.MODID_FMP)) {
            return blockPipeLike.factory.canPlaceBlockOnSide(worldIn, pos, side, player, stack, blockPipeLike);
        }
        return super.canPlaceBlockOnSide(worldIn, pos, side, player, stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (Loader.isModLoaded(GTValues.MODID_FMP)) {
            if (EnumActionResult.SUCCESS == blockPipeLike.factory.tryPlaceMultipart(player, worldIn, pos, player.getHeldItem(hand), facing, new Vector3(hitX, hitY, hitZ), blockPipeLike))
                return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        Q baseProperty = blockPipeLike.getBaseProperties(stack);
        return baseProperty == null ? super.getItemStackDisplayName(stack) : blockPipeLike.factory.getDisplayName(baseProperty.getOrePrefix(), blockPipeLike.material);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        Q baseProperty = blockPipeLike.getBaseProperties(stack);
        if (baseProperty != null) {
            P actualProperty = blockPipeLike.getActualProperty(baseProperty);
            actualProperty.addInformation(tooltip);
        }
    }

}
