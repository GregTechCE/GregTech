package gregtech.common.tools;

import net.minecraft.item.ItemStack;

public abstract class ToolTurbine extends ToolBase {

    public abstract float getBaseDamage(ItemStack stack);

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.GREEN + "")
//                .appendSibling(player.getDisplayName())
//                .appendText(TextFormatting.WHITE + " put " + TextFormatting.RED)
//                .appendSibling(player.getDisplayName())
//                .appendText(TextFormatting.WHITE + " head into turbine");
//    }

    public abstract float getSpeedMultiplier(ItemStack stack);

    public abstract float getMaxDurabilityMultiplier(ItemStack stack);
}
