package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ToolPickaxe extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.5F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && tool.equals("pickaxe") ||
            block.getMaterial() == Material.ROCK ||
            block.getMaterial() == Material.IRON ||
            block.getMaterial() == Material.ANVIL ||
            block.getMaterial() == Material.GLASS;
    }

    @Override
    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
        super.onToolCrafted(stack, player);
//        player.addStat(AchievementList.BUILD_PICKAXE);
//        player.addStat(AchievementList.BUILD_BETTER_PICKAXE);
//        GregTechMod.achievements.issueAchievement(player, "flintpick"); // TODO ACHIEVEMENTS/ADVANCEMENTS
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "")
//                .appendSibling(entity.getDisplayName())
//                .appendText(TextFormatting.WHITE + " got mined by " + TextFormatting.GREEN)
//                .appendSibling(player.getDisplayName());
//    }
}
