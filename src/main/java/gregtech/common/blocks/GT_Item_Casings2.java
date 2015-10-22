package gregtech.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GT_Item_Casings2
        extends GT_Item_Casings_Abstract {
    public GT_Item_Casings2(Block par1) {
        super(par1);
    }

    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        switch (getDamage(aStack)) {
            case 8:
                aList.add(this.mBlastProofTooltip);
        }
    }
}
