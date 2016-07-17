package gregtech.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GT_Item_Casings4
        extends GT_Item_Casings_Abstract {
    public GT_Item_Casings4(Block par1) {
        super(par1);
    }
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        switch (getDamage(aStack)) {
            case 14:
                aList.add(this.mCoil04Tooltip);
                break;
            case 15:
                aList.add(this.mCoil05Tooltip);
                break;
        }
    }
}
