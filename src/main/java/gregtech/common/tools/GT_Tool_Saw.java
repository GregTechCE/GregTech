package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.world.BlockEvent;

import java.util.ArrayList;
import java.util.List;

public class GT_Tool_Saw
        extends GT_Tool {
    public int getToolDamagePerBlockBreak() {
        return 50;
    }

    public int getToolDamagePerDropConversion() {
        return 100;
    }

    public int getToolDamagePerContainerCraft() {
        return 200;
    }

    public int getToolDamagePerEntityAttack() {
        return 200;
    }

    public int getBaseQuality() {
        return 0;
    }

    public float getBaseDamage() {
        return 1.75F;
    }

    public float getSpeedMultiplier() {
        return 1.0F;
    }

    public float getMaxDurabilityMultiplier() {
        return 1.0F;
    }

    public String getCraftingSound() {
        return null;
    }

    public String getEntityHitSound() {
        return null;
    }

    public String getBreakingSound() {
        return (String) GregTech_API.sSoundList.get(Integer.valueOf(0));
    }

    public String getMiningSound() {
        return null;
    }

    public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent) {
        if ((aBlock.getMaterial() == Material.leaves) && ((aBlock instanceof IShearable))) {
            aPlayer.worldObj.setBlock(aX, aY, aZ, aBlock, aMetaData, 0);
            if (((IShearable) aBlock).isShearable(aStack, aPlayer.worldObj, aX, aY, aZ)) {
                ArrayList<ItemStack> tDrops = ((IShearable) aBlock).onSheared(aStack, aPlayer.worldObj, aX, aY, aZ, aFortune);
                aDrops.clear();
                aDrops.addAll(tDrops);
                aEvent.dropChance = 1.0F;
            }
            aPlayer.worldObj.setBlock(aX, aY, aZ, Blocks.air, 0, 0);
        } else if (((aBlock.getMaterial() == Material.ice) || (aBlock.getMaterial() == Material.packedIce)) && (aDrops.isEmpty())) {
            aDrops.add(new ItemStack(aBlock, 1, aMetaData));
            aPlayer.worldObj.setBlockToAir(aX, aY, aZ);
            aEvent.dropChance = 1.0F;
            return 1;
        }
        return 0;
    }

    public boolean isMinableBlock(Block aBlock, byte aMetaData) {
        String tTool = aBlock.getHarvestTool(aMetaData);
        return ((tTool != null) && ((tTool.equals("axe")) || (tTool.equals("saw")))) || (aBlock.getMaterial() == Material.leaves) || (aBlock.getMaterial() == Material.vine) || (aBlock.getMaterial() == Material.wood) || (aBlock.getMaterial() == Material.cactus) || (aBlock.getMaterial() == Material.ice) || (aBlock.getMaterial() == Material.packedIce);
    }

    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadSaw.mTextureIndex] : Textures.ItemIcons.HANDLE_SAW;
    }

    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
    }

    public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new ChatComponentText(EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE + " was getting cut down by " + EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE);
    }
}
