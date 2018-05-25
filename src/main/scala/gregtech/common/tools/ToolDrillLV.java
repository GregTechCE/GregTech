package gregtech.common.tools;

import gregtech.api.GregTechAPI;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ToolDrillLV extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getDigSpeedMultiplier(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public boolean hasMaterialHandle() {
        return true;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTechAPI.soundList.get(106);
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return GregTechAPI.soundList.get(106);
    }

    @Override
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return GregTechAPI.soundList.get(106);
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTechAPI.soundList.get(106);
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return tool != null && (tool.equals("pickaxe") || tool.equals("shovel")) ||
            block.getMaterial() == Material.ROCK ||
            block.getMaterial() == Material.IRON ||
            block.getMaterial() == Material.ANVIL ||
            block.getMaterial() == Material.SAND ||
            block.getMaterial() == Material.GRASS ||
            block.getMaterial() == Material.GROUND ||
            block.getMaterial() == Material.SNOW ||
            block.getMaterial() == Material.CLAY ||
            block.getMaterial() == Material.GLASS;
    }

}
