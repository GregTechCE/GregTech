package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.GregTechAPI;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.List;
import java.util.Random;

public class ToolChainsawLV extends ToolSaw {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 800;
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
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(104);
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(105);
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(104);
    }

    @Override
    public boolean isChainsaw(ItemStack stack){
    	return true;
    }

    @Override
    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
        super.onToolCrafted(stack, player);
        GT_Mod.achievements.issueAchievement(player, "brrrr");
        GT_Mod.achievements.issueAchievement(player, "buildChainsaw");
    }
    
    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        int rAmount = 0;
        ItemStack stack = harvester.getHeldItem(EnumHand.MAIN_HAND);
        if ((blockState.getMaterial() == Material.LEAVES) && blockState.getBlock() instanceof IShearable) {
            IShearable shearable = (IShearable) blockState.getBlock();
            if (shearable.isShearable(stack, harvester.worldObj, blockPos)) {
                List<ItemStack> tDrops = shearable.onSheared(stack, harvester.worldObj, blockPos, aFortune);
                drops.clear();
                drops.addAll(tDrops);
                aEvent.setDropChance(1.0F);
                for (ItemStack dropStack : tDrops) {
                    Random itemRand = new Random();
                    float f = 0.7F;
                    double d = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d1 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d2 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(harvester.worldObj,
                            blockPos.getX() + d,
                            blockPos.getY() + d1,
                            blockPos.getZ() + d2, dropStack);
                    entityitem.setDefaultPickupDelay();
                    harvester.worldObj.spawnEntityInWorld(entityitem);
                }
                harvester.addStat(StatList.MINE_BLOCK_STATS.get(Block.getIdFromBlock(blockState.getBlock())), 1);
            }
            harvester.worldObj.setBlockToAir(blockPos);
        } else 
        	if ((blockState.getMaterial() == Material.ICE ||
                    blockState.getMaterial() == Material.PACKED_ICE) &&
                    drops.isEmpty()) {
            drops.add(getBlockStack(blockState));
            harvester.worldObj.setBlockToAir(blockPos);
            aEvent.setDropChance(1.0F);
            return 1;
        }
        if (GregTechAPI.sTimber && !harvester.isSneaking() &&
                OrePrefix.log.contains(getBlockStack(blockState))) {
            for (int y = 0; y < harvester.worldObj.getHeight() - blockPos.up().getY(); y++) {
                BlockPos block = blockPos.up(y);
                if (!isStateEqual(harvester.worldObj.getBlockState(block), blockState) ||
                        !harvester.worldObj.destroyBlock(block, true)) break;
                rAmount++;
            }
        }
        return rAmount;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadChainsaw.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_LV;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(player.getDisplayName())
                .appendText(TextFormatting.WHITE + " was massacred " + TextFormatting.RED)
                .appendSibling(entity.getDisplayName());
    }
}
