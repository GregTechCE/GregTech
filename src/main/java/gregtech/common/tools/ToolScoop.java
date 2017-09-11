package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ToolScoop extends ToolBase {

    public static Material beeHiveMaterial;

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 200;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && tool.equals("scoop") ||
                block.getMaterial() == beeHiveMaterial;
    }

//    @Override
//    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
//        item.addStats(new Behaviour_Scoop(200));
//    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "")
//                .appendSibling(entity.getDisplayName())
//                .appendText(TextFormatting.WHITE + " got scooped by " + TextFormatting.GREEN)
//                .appendSibling(player.getDisplayName());
//    }
}
