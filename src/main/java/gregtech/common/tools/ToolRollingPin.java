package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolRollingPin extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 100;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.0F;
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "")
//                .appendSibling(entity.getDisplayName())
//                .appendText(TextFormatting.WHITE + " got flattened by " + TextFormatting.GREEN)
//                .appendSibling(player.getDisplayName());
//    }
}
