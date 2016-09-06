package gregtech.common.tools;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import ic2.core.block.Ic2Leaves;
import ic2.core.ref.BlockName;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class GT_Tool_BranchCutter extends GT_Tool {

    @Override
    public float getBaseDamage() {
        return 2.5F;
    }

    @Override
    public float getSpeedMultiplier() {
        return 0.25F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 0.25F;
    }

    @Override
    public boolean isGrafter() {
        return true;
    }

    @Override
    public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, IBlockState aBlock, BlockPos blockPos, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent) {
        if (aBlock.getMaterial() == Material.LEAVES) {
            aEvent.setDropChance(Math.min(1.0F, Math.max(aEvent.getDropChance(), (aStack.getItem().getHarvestLevel(aStack, "") + 1) * 0.2F)));
            if (aBlock.getBlock() == Blocks.LEAVES) {
                aDrops.clear();
                if ((aBlock.getValue(BlockOldLeaf.VARIANT) == BlockPlanks.EnumType.OAK &&
                        aPlayer.worldObj.rand.nextInt(9) <= aFortune * 2)) {
                    aDrops.add(new ItemStack(Items.APPLE, 1, 0));
                } else {
                    aDrops.add(new ItemStack(Blocks.SAPLING, 1, aBlock
                            .getValue(BlockOldLeaf.VARIANT).getMetadata()));
                }
            } else if (aBlock == Blocks.LEAVES2) {
                aDrops.clear();
                aDrops.add(new ItemStack(Blocks.SAPLING, 1, aBlock
                        .getValue(BlockNewLeaf.VARIANT).getMetadata()));
            } else if (aBlock == GT_Utility.getBlockFromStack(GT_ModHandler.getIC2Item(BlockName.leaves, 1))) {
                aDrops.clear();
                aDrops.add(GT_ModHandler.getIC2Item(BlockName.sapling, 1));
            }
        }
        return 0;
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock) {
        String tTool = aBlock.getBlock().getHarvestTool(aBlock);
        return ((tTool != null) && (tTool.equals("grafter"))) || (aBlock.getMaterial() == Material.LEAVES);
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.GRAFTER : null;
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(aEntity.getDisplayName())
                .appendText(TextFormatting.WHITE + " has been trimmed by " + TextFormatting.GREEN)
                .appendSibling(aPlayer.getDisplayName());
    }

}
