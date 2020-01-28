package gregtech.common.blocks;

import gregtech.api.unification.material.type.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MetalCasingItemBlock extends ItemBlock {

    private BlockMetalCasing metalCasingBlock;

    public MetalCasingItemBlock(BlockMetalCasing block) {
        super(block);
        this.metalCasingBlock = block;
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @SuppressWarnings("deprecation")
    public IBlockState getBlockState(ItemStack stack) {
        return metalCasingBlock.getStateFromMeta(getMetadata(stack.getItemDamage()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack stack) {
        Material material = metalCasingBlock.getMetalCasingMaterial();
        return I18n.format(String.format("tile.metal_casing.%s.name", material.getUnlocalizedName()));
    }


}
