package gregtech.common.tools;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.common.items.behaviors.Behaviour_Sense;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ToolSense extends ToolBase {

    private ThreadLocal<Object> sIsHarvestingRightNow = new ThreadLocal();

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return null;
    }

    @Override
    public boolean isCrowbar(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isGrafter(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock, ItemStack stack) {
        String tTool = aBlock.getBlock().getHarvestTool(aBlock);
        return (tTool != null && (tTool.equals("sense") || tTool.equals("scythe"))) ||
                aBlock.getMaterial() == Material.PLANTS ||
                aBlock.getMaterial() == Material.LEAVES;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        int rConversions = 0;
        ItemStack aStack = harvester.getHeldItem(EnumHand.MAIN_HAND);
        if (this.sIsHarvestingRightNow.get() == null && !harvester.worldObj.isRemote) {
            this.sIsHarvestingRightNow.set(this);
            for (int i = -2; i < 3; i++) {
                for (int j = -2; j < 3; j++) {
                    for (int k = -2; k < 3; k++) {
                        BlockPos block = blockPos.add(i, j, k);
                        IBlockState state = harvester.worldObj.getBlockState(block);
                        if ((i != 0 || j != 0 || k != 0) && aStack.getStrVsBlock(state) > 0.0F &&
                                ((EntityPlayerMP) harvester).interactionManager.tryHarvestBlock(block))
                            rConversions++;
                    }
                }
            }
        }
        return rConversions;
    }

    @Override
    public float getNormalDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase attacker) {
        return 0;
    }

    @Override
    public float getMagicDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase player) {
        return 0;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        return 0;
    }

//    @Override
//    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
//        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadSense.mTextureIndex] : ToolMetaItem.getSecondaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.stick.mTextureIndex];
//    }

    @Override
    public int getColor(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).materialRGB : ToolMetaItem.getSecondaryMaterial(aStack).materialRGB;
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem aItem, int aID) {
        aItem.addStats(new Behaviour_Sense(getToolDamagePerBlockBreak()));
    }

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 0;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 0;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 0;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 0;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 0;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(aPlayer.getDisplayName())
                .appendText(TextFormatting.WHITE + " has taken the Soul of " + TextFormatting.RED)
                .appendSibling(aEntity.getDisplayName());
    }

}
