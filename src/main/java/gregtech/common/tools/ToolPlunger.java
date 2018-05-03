package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public class ToolPlunger extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 4;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.0F;
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "").appendSibling(entity.getDisplayName()).appendText(TextFormatting.WHITE + " has been chopped by " + TextFormatting.GREEN).appendSibling(player.getDisplayName());
//    }
}
