package gregtech.common.blocks.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemCasings2 extends ItemCasingsAbstract {
    public ItemCasings2(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        switch (getDamage(stack)) {
            case 8:
                tooltip.add(this.blastProofTooltip);
        }
    }
}
