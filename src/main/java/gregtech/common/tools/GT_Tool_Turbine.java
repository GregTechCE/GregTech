package gregtech.common.tools;

import gregtech.api.items.toolitem.ToolMetaItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public abstract class GT_Tool_Turbine extends GT_Tool {

    public abstract float getBaseDamage(ItemStack stack);

    @Override
    public boolean isMinableBlock(IBlockState aBlock, ItemStack stack) {
        return false;
    }

//    @Override
//    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
//        return aIsToolHead ? getTurbineIcon() : null;
//    }

    @Override
    public int getColor(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).materialRGB : null;
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
