package gregtech.api.pipelike;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockPipeLike<Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends ITileProperty, T extends ITilePipeLike<Q, P>, C> extends ItemBlock {

    private BlockPipeLike<Q, P, T, C> blockPipeLike;

    public ItemBlockPipeLike(BlockPipeLike<Q, P, T, C> block) {
        super(block);
        blockPipeLike = block;
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        Q baseProperty = blockPipeLike.getBaseProperties(stack);
        return baseProperty == null ? super.getItemStackDisplayName(stack) : baseProperty.getOrePrefix().getLocalNameForItem(blockPipeLike.material);
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
