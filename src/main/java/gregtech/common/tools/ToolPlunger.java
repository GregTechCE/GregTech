package gregtech.common.tools;

import gregtech.api.GregTechAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ToolPlunger extends ToolBase {

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.25F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 0.25F;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTechAPI.soundList.get(101);
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return GregTechAPI.soundList.get(101);
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTechAPI.soundList.get(101);
    }

//    @Override
//    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
//        item.addStats(new Behaviour_Plunger_Item(getToolDamagePerDropConversion(item.getStackForm())));
//        item.addStats(new Behaviour_Plunger_Fluid(getToolDamagePerDropConversion(item.getStackForm())));
//    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "")
//                .appendSibling(entity.getDisplayName())
//                .appendText(TextFormatting.WHITE + " got stuck trying to escape through a Pipe while fighting " + TextFormatting.GREEN)
//                .appendSibling(player.getDisplayName());
//    }
}
