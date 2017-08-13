package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.List;

public class GT_Tool_Saw extends GT_Tool {

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
        return 200;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 200;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 0;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.75F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 1.0F;
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
        return GregTech_API.sSoundList.get(0);
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
    public ResourceLocation getMiningSound(ItemStack stack) {
        return null;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        if (blockState.getMaterial() == Material.LEAVES && blockState.getBlock() instanceof IShearable) {
            IShearable shearable = (IShearable) blockState.getBlock();
            if (shearable.isShearable(aStack, harvester.worldObj, blockPos)) {
                List<ItemStack> tDrops = shearable.onSheared(aStack, harvester.worldObj, blockPos, aFortune);
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
    public boolean isMinableBlock(IBlockState aBlock, ItemStack stack) {
        String tTool = aBlock.getBlock().getHarvestTool(aBlock);
        return ((tTool != null) && ((tTool.equals("axe")) || (tTool.equals("saw")))) ||
                (aBlock.getMaterial() == Material.LEAVES) ||
                (aBlock.getMaterial() == Material.VINE) ||
                (aBlock.getMaterial() == Material.WOOD) ||
                (aBlock.getMaterial() == Material.CACTUS) ||
                (aBlock.getMaterial() == Material.ICE) ||
                (aBlock.getMaterial() == Material.PACKED_ICE);
    }

    @Override
    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
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

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefix.toolHeadSaw.mTextureIndex] : Textures.ItemIcons.HANDLE_SAW;
    }

    @Override
    public int getColor(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).materialRGB : ToolMetaItem.getSecondaryMaterial(aStack).materialRGB;
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem aItem, int aID) {
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(aPlayer.getDisplayName())
                .appendText(TextFormatting.WHITE + " was getting cut down " + TextFormatting.RED)
                .appendSibling(aEntity.getDisplayName());
    }


}
