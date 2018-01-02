package gregtech.common.blocks;

import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreItemBlock extends ItemBlock {

    private BlockOre block;

    public OreItemBlock(BlockOre block) {
        super(block);
        this.block = block;
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @SuppressWarnings("deprecation")
    protected IBlockState getBlockState(ItemStack stack) {
        return block.getStateFromMeta(getMetadata(stack.getItemDamage()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        IBlockState blockState = getBlockState(stack);
        boolean small = blockState.getValue(BlockOre.SMALL);
        if (small) {
            return OrePrefix.oreSmall.getLocalNameForItem(block.material);
        } else {
            StoneType stoneType = blockState.getValue(BlockOre.STONE_TYPE);
            return stoneType.processingPrefix.getLocalNameForItem(block.material);
        }
    }

}
