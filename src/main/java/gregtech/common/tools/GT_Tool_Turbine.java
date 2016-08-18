package gregtech.common.tools;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.items.GT_MetaGenerated_Tool;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public abstract class GT_Tool_Turbine extends GT_Tool {

    public abstract float getBaseDamage();

    @Override
    public boolean isMinableBlock(IBlockState aBlock) {
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

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(aPlayer.getDisplayName())
                .appendText(TextFormatting.WHITE + " put " + TextFormatting.RED)
                .appendSibling(aPlayer.getDisplayName())
                .appendText(TextFormatting.WHITE + " head into turbine");
    }

    public abstract IIconContainer getTurbineIcon();

    public abstract float getSpeedMultiplier();

    public abstract float getMaxDurabilityMultiplier();

    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
    }
}
