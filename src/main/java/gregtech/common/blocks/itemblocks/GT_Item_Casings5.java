package gregtech.common.blocks.itemblocks;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GT_Item_Casings5 extends GT_Item_Casings_Abstract {

    public GT_Item_Casings5(Block block) {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        switch (getDamage(stack)) {
            case 0:
                tooltip.add(this.mCoil01Tooltip);
                break;
            case 1:
                tooltip.add(this.mCoil02Tooltip);
                break;
            case 2:
                tooltip.add(this.mCoil03Tooltip);
                break;
            case 3:
                tooltip.add(this.mCoil04Tooltip);
                break;
            case 4:
                tooltip.add(this.mCoil05Tooltip);
                break;
            case 5:
                tooltip.add(this.mCoil06Tooltip);
                break;
            case 6:
                tooltip.add(this.mCoil07Tooltip);
                break;
        }
    }
}
