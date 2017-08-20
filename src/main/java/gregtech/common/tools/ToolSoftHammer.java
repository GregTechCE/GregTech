package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.common.items.behaviors.Behaviour_SoftHammer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

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
    public int getHurtResistanceTime(int originalHurtResistance, Entity entity) {
        return originalHurtResistance * 2;
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
        return GregTech_API.sSoundList.get(101);
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(101);
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(101);
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).mIconSet.mTextures[OrePrefixes.toolHeadMallet.mTextureIndex] : ToolMetaItem.getHandleMaterial(aStack).mIconSet.mTextures[OrePrefixes.handleMallet.mTextureIndex];
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
        item.addStats(new Behaviour_SoftHammer(100));
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(entity.getDisplayName())
                .appendText(TextFormatting.WHITE + " was stopped working by " + TextFormatting.GREEN)
                .appendSibling(player.getDisplayName());
    }
}
