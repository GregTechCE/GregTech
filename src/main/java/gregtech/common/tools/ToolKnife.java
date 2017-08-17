package gregtech.common.tools;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ToolKnife extends ToolSword {

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 100;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0.5F;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.KNIFE : null;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(player.getDisplayName())
                .appendText(TextFormatting.WHITE + " has been murdered by " + TextFormatting.GREEN)
                .appendSibling(player.getDisplayName());
    }
}
