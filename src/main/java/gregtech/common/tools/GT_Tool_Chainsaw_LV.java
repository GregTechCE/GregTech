package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.items.IIconContainer;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.ore.OrePrefixes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;
import java.util.Random;

public class GT_Tool_Chainsaw_LV extends GT_Tool_Saw {

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
        return 800;
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
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(104);
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(105);
    }

    @Override
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(0);
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(104);
    }

    @Override
    public boolean isChainsaw(){
    	return true;
    }
    
    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        GT_Mod.achievements.issueAchievement(aPlayer, "brrrr");
        GT_Mod.achievements.issueAchievement(aPlayer, "buildChainsaw");
    }
    
    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        int rAmount = 0;
        if ((blockState.getMaterial() == Material.LEAVES) && blockState.getBlock() instanceof IShearable) {
            IShearable shearable = (IShearable) blockState.getBlock();
            if (shearable.isShearable(aStack, harvester.worldObj, blockPos)) {
                List<ItemStack> tDrops = shearable.onSheared(aStack, harvester.worldObj, blockPos, aFortune);
                drops.clear();
                drops.addAll(tDrops);
                aEvent.setDropChance(1.0F);
                for (ItemStack stack : tDrops) {
                    Random itemRand = new Random();
                    float f = 0.7F;
                    double d = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d1 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d2 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(harvester.worldObj,
                            blockPos.getX() + d,
                            blockPos.getY() + d1,
                            blockPos.getZ() + d2, stack);
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
        if (GregTech_API.sTimber && !harvester.isSneaking() &&
                OrePrefixes.log.contains(getBlockStack(blockState))) {
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
    public int getColor(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mRGBa : ToolMetaItem.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(aPlayer.getDisplayName())
                .appendText(TextFormatting.WHITE + " was massacred " + TextFormatting.RED)
                .appendSibling(aEntity.getDisplayName());
    }
    
}
