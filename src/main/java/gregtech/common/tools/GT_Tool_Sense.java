package gregtech.common.tools;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.common.items.behaviors.Behaviour_Sense;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class GT_Tool_Sense
        extends GT_Tool {
    private ThreadLocal<Object> sIsHarvestingRightNow = new ThreadLocal();

    public float getBaseDamage() {
        return 3.0F;
    }

    public boolean isMinableBlock(Block aBlock, byte aMetaData) {
        String tTool = aBlock.getHarvestTool(aMetaData);
        return ((tTool != null) && ((tTool.equals("sense")) || (tTool.equals("scythe")))) || (aBlock.getMaterial() == Material.plants) || (aBlock.getMaterial() == Material.leaves);
    }

    public int convertBlockDrops(List<ItemStack> aDrops, ItemStack aStack, EntityPlayer aPlayer, Block aBlock, int aX, int aY, int aZ, byte aMetaData, int aFortune, boolean aSilkTouch, BlockEvent.HarvestDropsEvent aEvent) {
        int rConversions = 0;
        if ((this.sIsHarvestingRightNow.get() == null) && ((aPlayer instanceof EntityPlayerMP))) {
            this.sIsHarvestingRightNow.set(this);
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    for (int k = -1; k < 2; k++) {
                        if (((i != 0) || (j != 0) || (k != 0)) && (aStack.getItem().getDigSpeed(aStack, aPlayer.worldObj.getBlock(aX + i, aY + j, aZ + k), aPlayer.worldObj.getBlockMetadata(aX + i, aY + j, aZ + k)) > 0.0F) && (((EntityPlayerMP) aPlayer).theItemInWorldManager.tryHarvestBlock(aX + i, aY + j, aZ + k))) {
                            rConversions++;
                        }
                    }
                }
            }
            this.sIsHarvestingRightNow.set(null);
        }
        return rConversions;
    }

    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.toolHeadSense.mTextureIndex] : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mIconSet.mTextures[gregtech.api.enums.OrePrefixes.stick.mTextureIndex];
    }

    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : GT_MetaGenerated_Tool.getSecondaryMaterial(aStack).mRGBa;
    }

    public void onStatsAddedToTool(GT_MetaGenerated_Tool aItem, int aID) {
        aItem.addItemBehavior(aID, new Behaviour_Sense(getToolDamagePerBlockBreak()));
    }

    public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new ChatComponentText(EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + " has taken the Soul of " + EnumChatFormatting.RED + aEntity.getCommandSenderName() + EnumChatFormatting.WHITE);
    }
}
