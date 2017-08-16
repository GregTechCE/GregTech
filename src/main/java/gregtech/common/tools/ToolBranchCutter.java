package gregtech.common.tools;

import gregtech.api.recipes.ModHandler;
import gregtech.api.util.GT_Utility;
import ic2.core.ref.BlockName;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ToolBranchCutter extends ToolBase {

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.5F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0.25F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 0.25F;
    }

    @Override
    public boolean isGrafter(ItemStack stack) {
        return true;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        ItemStack stack = harvester.getHeldItem(EnumHand.MAIN_HAND);
        if (blockState.getMaterial() == Material.LEAVES) {
            aEvent.setDropChance(Math.min(1.0F, Math.max(aEvent.getDropChance(), (stack.getItem().getHarvestLevel(stack, "") + 1) * 0.2F)));
            if (blockState.getBlock() == Blocks.LEAVES) {
                drops.clear();
                if ((blockState.getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.OAK &&
                        harvester.worldObj.rand.nextInt(9) <= aFortune * 2)) {
                    drops.add(new ItemStack(Items.APPLE, 1, 0));
                } else {
                    drops.add(new ItemStack(Blocks.SAPLING, 1, blockState
                            .getValue(BlockOldLeaf.VARIANT).getMetadata()));
                }
            } else if (blockState == Blocks.LEAVES2) {
                drops.clear();
                drops.add(new ItemStack(Blocks.SAPLING, 1, blockState
                        .getValue(BlockNewLeaf.VARIANT).getMetadata()));
            } else if (blockState == GT_Utility.getBlockFromStack(ModHandler.getIC2Item(BlockName.leaves, 1))) {
                drops.clear();
                drops.add(ModHandler.getIC2Item(BlockName.sapling, 1));
            }
        }
        return 0;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tTool = block.getBlock().getHarvestTool(block);
        return ((tTool != null) && (tTool.equals("grafter"))) || (block.getMaterial() == Material.LEAVES);
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.GRAFTER : null;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(entity.getDisplayName())
                .appendText(TextFormatting.WHITE + " has been trimmed by " + TextFormatting.GREEN)
                .appendSibling(player.getDisplayName());
    }
}
