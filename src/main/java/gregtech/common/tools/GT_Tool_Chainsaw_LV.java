package gregtech.common.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.world.BlockEvent;

public class GT_Tool_Chainsaw_LV
        extends GT_Tool_Saw {
    public int getToolDamagePerBlockBreak() {
        return 50;
    }

    public int getToolDamagePerDropConversion() {
        return 100;
    }

    public int getToolDamagePerContainerCraft() {
        return 800;
    }

    public int getToolDamagePerEntityAttack() {
        return 200;
    }

    public int getBaseQuality() {
        return 0;
    }

    public float getBaseDamage() {
        return 3.0F;
    }

    public float getSpeedMultiplier() {
        return 2.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    public String getCraftingSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(104));
    }

    public String getEntityHitSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(105));
    }

    public String getBreakingSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(0));
    }

    public String getMiningSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(104));
    }

    public boolean canBlock() {
        return false;
    }

    public boolean isChainsaw(){
    	return true;
    }
    
    public boolean isWeapon() {
        return true;
    }

    public void onToolCrafted(ItemStack aStack, EntityPlayer aPlayer) {
        super.onToolCrafted(aStack, aPlayer);
        try {
            GT_Mod.instance.achievements.issueAchievement(aPlayer, "brrrr");
            GT_Mod.instance.achievements.issueAchievement(aPlayer, "buildChainsaw");
        } catch (Exception e) {
        }
    }
    @Override
    public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent) {
        int rAmount = 0;
        if ((aBlock.getMaterial() == Material.leaves) && ((aBlock instanceof IShearable))) {
            aPlayer.worldObj.setBlock(aX, aY, aZ, aBlock, aMetaData, 0);
            if (((IShearable) aBlock).isShearable(aStack, aPlayer.worldObj, aX, aY, aZ)) {
                ArrayList<ItemStack> tDrops = ((IShearable) aBlock).onSheared(aStack, aPlayer.worldObj, aX, aY, aZ, aFortune);
                aDrops.clear();
//                aDrops.addAll(tDrops);
//                aEvent.dropChance = 1.0F;
//                for (ItemStack stack : tDrops)
//                {
//                Random itemRand = new Random();
//                  float f = 0.7F;
//                  double d = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
//                  double d1 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
//                  double d2 = itemRand.nextFloat() * f + (1.0F - f) * 0.5D;
//                  EntityItem entityitem = new EntityItem(aPlayer.worldObj, aX + d, aY + d1, aZ + d2, stack);
//                  entityitem.delayBeforeCanPickup = 10;
//                  aPlayer.worldObj.spawnEntityInWorld(entityitem);
//                }
//                aPlayer.addStat(net.minecraft.stats.StatList.mineBlockStatArray[Block.getIdFromBlock(aBlock)], 1);                
            }
            aPlayer.worldObj.setBlock(aX, aY, aZ, Blocks.air, 0, 0);
        } else 
        	if (((aBlock.getMaterial() == Material.ice) || (aBlock.getMaterial() == Material.packedIce)) && (aDrops.isEmpty())) {
            aDrops.add(new ItemStack(aBlock, 1, aMetaData));
            aPlayer.worldObj.setBlockToAir(aX, aY, aZ);
            aEvent.dropChance = 1.0F;
            return 1;
        }
        if ((GregTech_API.sTimber) && (!aPlayer.isSneaking()) && (OrePrefixes.log.contains(new ItemStack(aBlock, 1, aMetaData)))) {
            int tY = aY + 1;
            for (int tH = aPlayer.worldObj.getHeight(); tY < tH; tY++) {
                if ((aPlayer.worldObj.getBlock(aX, tY, aZ) != aBlock) || (!aPlayer.worldObj.func_147480_a(aX, tY, aZ, true))) {
                    break;
                }
                rAmount++;
            }
        }
        return rAmount;
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadChainsaw.mTextureIndex] : Textures.ItemIcons.POWER_UNIT_LV;
    }

    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " was massacred by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
    }
}
