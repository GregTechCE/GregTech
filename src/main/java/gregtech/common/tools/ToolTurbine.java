package gregtech.common.tools;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public abstract class ToolTurbine extends ToolBase {

    public abstract float getBaseDamage(ItemStack stack);

    @Override
    public IIconContainer getIcon(boolean isToolHead, ItemStack stack) {
        return isToolhead ? getTurbineIcon() : null;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(player.getDisplayName())
                .appendText(TextFormatting.WHITE + " put " + TextFormatting.RED)
                .appendSibling(player.getDisplayName())
                .appendText(TextFormatting.WHITE + " head into turbine");
    }

    public abstract IIconContainer getTurbineIcon();

    public abstract float getSpeedMultiplier(ItemStack stack);

    public abstract float getMaxDurabilityMultiplier(ItemStack stack);
}
