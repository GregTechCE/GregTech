package gregtech.common.tools;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.IToolStats;
import gregtech.common.items.MetaTool;
import gregtech.common.items.behaviors.Behaviour_Crowbar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.Iterator;

public class ToolCrowbar extends ToolBase {

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

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(0);
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(0);
    }

    @Override
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(0);
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(0);
    }

    @Override
    public boolean isCrowbar(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        if (block.getMaterial() == Material.CIRCUITS) {
            return true;
        }
        String tTool = block.getBlock().getHarvestTool(block);
        if ((tTool == null) || (tTool.equals(""))) {
            for (Iterator i$ = MetaTool.INSTANCE.mToolStats.values().iterator(); i$.hasNext(); i$.next()) {
                if (((i$ instanceof ToolCrowbar)) && (!((IToolStats) i$).isMinableBlock(block, stack))) {
                    return false;
                }
            }
            return true;
        }
        return tTool.equals("crowbar");
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.CROWBAR : null;
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
        item.addStats(new Behaviour_Crowbar(1, 1000));
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(player.getDisplayName())
                .appendText(TextFormatting.WHITE + " was removed " + TextFormatting.RED)
                .appendSibling(entity.getDisplayName());
    }
}
