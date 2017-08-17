package gregtech.common.tools;

import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.List;

public class ToolSaw extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 200;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.75F;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        ItemStack stack = harvester.getHeldItem(EnumHand.MAIN_HAND);
        if (blockState.getMaterial() == Material.LEAVES && blockState.getBlock() instanceof IShearable) {
            IShearable shearable = (IShearable) blockState.getBlock();
            if (shearable.isShearable(stack, harvester.worldObj, blockPos)) {
                List<ItemStack> tDrops = shearable.onSheared(stack, harvester.worldObj, blockPos, aFortune);
                drops.clear();
                drops.addAll(tDrops);
                aEvent.setDropChance(1.0F);
            }
            harvester.worldObj.setBlockToAir(blockPos);
        } else if ((blockState.getMaterial() == Material.ICE ||
                blockState.getMaterial() == Material.PACKED_ICE)
                && drops.isEmpty()) {
            drops.add(getBlockStack(blockState));
            harvester.worldObj.setBlockToAir(blockPos);
            aEvent.setDropChance(1.0F);
            return 1;
        }
        return 0;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tTool = block.getBlock().getHarvestTool(block);
        return ((tTool != null) && ((tTool.equals("axe")) || (tTool.equals("saw")))) ||
                (block.getMaterial() == Material.LEAVES) ||
                (block.getMaterial() == Material.VINE) ||
                (block.getMaterial() == Material.WOOD) ||
                (block.getMaterial() == Material.CACTUS) ||
                (block.getMaterial() == Material.ICE) ||
                (block.getMaterial() == Material.PACKED_ICE);
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadSaw.mTextureIndex] : Textures.ItemIcons.HANDLE_SAW;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(player.getDisplayName())
                .appendText(TextFormatting.WHITE + " was getting cut down " + TextFormatting.RED)
                .appendSibling(entity.getDisplayName());
    }
}
