package gregtech.common.tools;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;
import java.util.Random;

public class GT_Tool_Chainsaw_LV extends GT_Tool_Saw {

    @Override
    public int getToolDamagePerBlockBreak() {
        return 50;
    }

    @Override
    public int getToolDamagePerDropConversion() {
        return 100;
    }

    @Override
    public int getToolDamagePerContainerCraft() {
        return 800;
    }

    @Override
    public int getToolDamagePerEntityAttack() {
        return 200;
    }

    @Override
    public int getBaseQuality() {
        return 0;
    }

    @Override
    public float getBaseDamage() {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier() {
        return 2.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    @Override
    public String getCraftingSound() {
        return GregTech_API.sSoundList.get(104);
    }

    @Override
    public String getEntityHitSound() {
        return GregTech_API.sSoundList.get(105);
    }

    @Override
    public String getBreakingSound() {
        return GregTech_API.sSoundList.get(0);
    }

    @Override
    public String getMiningSound() {
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
    public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, IBlockState aBlock, BlockPos pos, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent) {
        int rAmount = 0;
        if ((aBlock.getMaterial() == Material.LEAVES) && aBlock.getBlock() instanceof IShearable) {
            IShearable shearable = (IShearable) aBlock.getBlock();
            if (shearable.isShearable(aStack, aPlayer.worldObj, pos)) {
                List<ItemStack> tDrops = shearable.onSheared(aStack, aPlayer.worldObj, pos, aFortune);
                aDrops.clear();
                aDrops.addAll(tDrops);
                aEvent.setDropChance(1.0F);
                for (ItemStack stack : tDrops) {
                    Random itemRand = new Random();
                    float f = 0.7F;
                    double d = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d1 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double d2 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(aPlayer.worldObj,
                            pos.getX() + d,
                            pos.getY() + d1,
                            pos.getZ() + d2, stack);
                    entityitem.setDefaultPickupDelay();
                    aPlayer.worldObj.spawnEntityInWorld(entityitem);
                }
                aPlayer.addStat(StatList.MINE_BLOCK_STATS.get(Block.getIdFromBlock(aBlock.getBlock())), 1);
            }
            aPlayer.worldObj.setBlockToAir(pos);
        } else 
        	if ((aBlock.getMaterial() == Material.ICE ||
                    aBlock.getMaterial() == Material.PACKED_ICE) &&
                    aDrops.isEmpty()) {
            aDrops.add(getBlockStack(aBlock));
            aPlayer.worldObj.setBlockToAir(pos);
            aEvent.setDropChance(1.0F);
            return 1;
        }
        if (GregTech_API.sTimber && !aPlayer.isSneaking() &&
                OrePrefixes.log.contains(getBlockStack(aBlock))) {
            for (int y = 0; y < aPlayer.worldObj.getHeight() - pos.up().getY(); y++) {
                BlockPos block = pos.up(y);
                if (!isStateEqual(aPlayer.worldObj.getBlockState(block), aBlock) ||
                        !aPlayer.worldObj.destroyBlock(block, true)) break;
                rAmount++;
            }
        }
        return rAmount;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadChainsaw.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_LV;
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(aPlayer.getDisplayName())
                .appendText(TextFormatting.WHITE + " was massacred " + TextFormatting.RED)
                .appendSibling(aEntity.getDisplayName());
    }
    
}
