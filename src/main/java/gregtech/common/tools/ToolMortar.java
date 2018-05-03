package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolMortar extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 5;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.0F;
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "")
//                .appendSibling(entity.getDisplayName())
//                .appendText(TextFormatting.WHITE + " was grounded by " + TextFormatting.GREEN)
//                .appendSibling(player.getDisplayName());
//    }
}
