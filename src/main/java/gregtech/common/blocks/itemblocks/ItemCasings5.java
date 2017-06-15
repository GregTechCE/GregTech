package gregtech.common.blocks.itemblocks;

import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemCasings5 extends ItemCasingsAbstract {

    public ItemCasings5(Block block) {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        switch (getDamage(stack)) {
            case 0:
                tooltip.add(this.coil01Tooltip);
                break;
            case 1:
                tooltip.add(this.coil02Tooltip);
                break;
            case 2:
                tooltip.add(this.coil03Tooltip);
                break;
            case 3:
                tooltip.add(this.coil04Tooltip);
                break;
            case 4:
                tooltip.add(this.coil05Tooltip);
                break;
            case 5:
                tooltip.add(this.coil06Tooltip);
                break;
            case 6:
                tooltip.add(this.coil07Tooltip);
                break;
        }
    }
}
