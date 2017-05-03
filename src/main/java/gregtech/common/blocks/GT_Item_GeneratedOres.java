package gregtech.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GT_Item_GeneratedOres extends ItemBlock {

    public GT_Item_GeneratedOres(Block block) {
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
