package gregtech.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GT_Item_Casings1
        extends GT_Item_Casings_Abstract {
    public GT_Item_Casings1(Block par1) {
        super(par1);
    }

    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        switch (getDamage(aStack)) {
            case 12:
                aList.add(this.mCoil01Tooltip);
                break;
            case 13:
                aList.add(this.mCoil02Tooltip);
                break;
            case 14:
                aList.add(this.mCoil03Tooltip);
        }
    }
}
