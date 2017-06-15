package gregtech.common.blocks.itemblocks;

import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemConcretes extends ItemStonesAbstract {
    private final String runFasterToolTip = GT_LanguageManager.addStringLocalization("gt.runfastertooltip", "You can walk faster on this Block");

    public ItemConcretes(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(this.runFasterToolTip);
    }
}
