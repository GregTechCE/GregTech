package gregtech.common.tools;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public abstract class GT_Tool_Turbine extends GT_Tool {
    public abstract float getBaseDamage();

    @Override
    public boolean isMinableBlock(Block aBlock, byte aMetaData) {
        return false;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? getTurbineIcon() : null;
    }

    @Override
    public short[] getRGBa(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? GT_MetaGenerated_Tool.getPrimaryMaterial(aStack).mRGBa : null;
    }

    public IChatComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new ChatComponentText(EnumChatFormatting.GREEN + aPlayer.getCommandSenderName() + EnumChatFormatting.WHITE + " put " + EnumChatFormatting.RED +
                aEntity.getCommandSenderName() + "s" + EnumChatFormatting.WHITE + " head into a turbine");
    }

    public abstract IIconContainer getTurbineIcon();

    public abstract float getSpeedMultiplier();

    public abstract float getMaxDurabilityMultiplier();

    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }
}
