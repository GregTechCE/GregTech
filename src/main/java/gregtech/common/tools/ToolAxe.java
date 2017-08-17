package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ToolAxe extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 100;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 100;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tTool = block.getBlock().getHarvestTool(block);
        return "axe".equals(tTool) || (block.getMaterial() == Material.WOOD);
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        int rAmount = 0;
        if (GregTech_API.sTimber && !harvester.isSneaking() && OrePrefix.log.contains(getBlockStack(blockState))) {
            int tY = blockPos.getY() + 1;
            for (int tH = harvester.worldObj.getHeight(); tY < tH; tY++) {
                BlockPos block = new BlockPos(blockPos.getX(), tY, blockPos.getZ());
                if (!isStateEqual(harvester.worldObj.getBlockState(block), blockState) ||
                        !harvester.worldObj.destroyBlock(block, true)) break;
                rAmount++;
            }
        }
        return rAmount;
    }

    @Override
    public IIconContainer getIcon(boolean isToolHead, ItemStack stack) {
        return isToolHead ? ToolMetaItem.getPrimaryMaterial(stack).mIconSet.mTextures[OrePrefixes.toolHeadAxe.mTextureIndex] : ToolMetaItem.getSecondaryMaterial(stack).mIconSet.mTextures[OrePrefixes.stick.mTextureIndex];
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.RED + "").appendSibling(entity.getDisplayName()).appendText(TextFormatting.WHITE + " has been chopped by " + TextFormatting.GREEN).appendSibling(player.getDisplayName());
    }
}
