package gregtech.common.tools;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.items.behaviors.SoftHammerBehaviour;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ToolSoftHammer extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0.1F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 8.0F;
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

    @Override
    public boolean hasMaterialHandle() {
        return true;
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
        item.addStats(new SoftHammerBehaviour(100));
    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.RED + "")
//                .appendSibling(entity.getDisplayName())
//                .appendText(TextFormatting.WHITE + " was stopped working by " + TextFormatting.GREEN)
//                .appendSibling(player.getDisplayName());
//    }
}
