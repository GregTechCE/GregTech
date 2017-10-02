package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ToolSword extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 200;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 100;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 100;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && tool.equals("sword") ||
            block.getMaterial() == Material.LEAVES ||
            block.getMaterial() == Material.GOURD ||
            block.getMaterial() == Material.VINE ||
            block.getMaterial() == Material.WEB ||
            block.getMaterial() == Material.CLOTH ||
            block.getMaterial() == Material.CARPET ||
            block.getMaterial() == Material.PLANTS ||
            block.getMaterial() == Material.CACTUS ||
            block.getMaterial() == Material.CAKE ||
            block.getMaterial() == Material.TNT ||
            block.getMaterial() == Material.SPONGE;
    }

    @Override
    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
        super.onToolCrafted(stack, player);
//        player.addStat(AchievementList.BUILD_SWORD);
    }
}
