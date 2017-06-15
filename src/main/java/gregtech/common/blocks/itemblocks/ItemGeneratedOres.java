package gregtech.common.blocks.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemGeneratedOres extends ItemBlock {

    public ItemGeneratedOres(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return block.getUnlocalizedName() + "." + stack.getItemDamage();
    }

    @Override
    public int getMetadata(int damage) {
        return Math.max(0, Math.min(16, damage)); //checks to prevent outofbounds
    }

}
