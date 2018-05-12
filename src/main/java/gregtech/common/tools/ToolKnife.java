package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolKnife extends ToolSword {

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0.5F;
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "")
//                .appendSibling(player.getDisplayName())
//                .appendText(TextFormatting.WHITE + " has been murdered by " + TextFormatting.GREEN)
//                .appendSibling(player.getDisplayName());
//    }
}
