package gregtech.common.tools;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class ToolWireCutter extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 4;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.25F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && tool.equals("cutter");
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.GREEN + "")
//                .appendSibling(player.getDisplayName())
//                .appendText(TextFormatting.WHITE + " has cut the Cable for the Life Support Machine of " + TextFormatting.RED)
//                .appendSibling(player.getDisplayName());
//    }
}
