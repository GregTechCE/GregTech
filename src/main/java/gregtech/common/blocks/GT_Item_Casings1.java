package gregtech.common.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class GT_Item_Casings1
        extends GT_Item_Casings_Abstract {
    public GT_Item_Casings1(Block par1) {
        super(par1);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        int tMeta = getDamage(aStack);
        if (tMeta >= 12 && tMeta <= 14) {
            aList.add(EnumChatFormatting.ITALIC + this.mCoilOverheated1Tooltip);
            aList.add(EnumChatFormatting.ITALIC + this.mCoilOverheated2Tooltip);
        }
    }
}
