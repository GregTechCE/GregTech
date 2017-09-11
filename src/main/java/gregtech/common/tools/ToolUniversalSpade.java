package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

public class ToolUniversalSpade extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 400;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 100;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0.75F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && (
                        tool.equals("shovel") ||
                        tool.equals("axe") ||
                        tool.equals("saw") ||
                        tool.equals("sword") ||
                        tool.equals("crowbar")) ||
                block.getMaterial() == Material.SAND ||
                block.getMaterial() == Material.GRASS ||
                block.getMaterial() == Material.GROUND ||
                block.getMaterial() == Material.SNOW ||
                block.getMaterial() == Material.CLAY ||
                block.getMaterial() == Material.CRAFTED_SNOW ||
                block.getMaterial() == Material.LEAVES ||
                block.getMaterial() == Material.VINE ||
                block.getMaterial() == Material.WOOD ||
                block.getMaterial() == Material.CACTUS ||
                block.getMaterial() == Material.CIRCUITS ||
                block.getMaterial() == Material.GOURD ||
                block.getMaterial() == Material.WEB ||
                block.getMaterial() == Material.CLOTH ||
                block.getMaterial() == Material.CARPET ||
                block.getMaterial() == Material.PLANTS ||
                block.getMaterial() == Material.CAKE ||
                block.getMaterial() == Material.TNT ||
                block.getMaterial() == Material.SPONGE;
    }

//    @Override
//    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
//        item.addStats(new Behaviour_Crowbar(2, 2000));
//    }

    @Override
    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
        super.onToolCrafted(stack, player);
        player.addStat(AchievementList.BUILD_SWORD);
//        GregTechMod.achievements.issueAchievement(player, "unitool");  // TODO ACHIEVEMENTS/ADVANCEMENTS
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "")
//                .appendSibling(entity.getDisplayName())
//                .appendText(TextFormatting.WHITE + " has been universal digged by " + TextFormatting.GREEN)
//                .appendSibling(player.getDisplayName());
//    }
}
