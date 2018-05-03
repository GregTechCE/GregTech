package gregtech.common.tools;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.IToolStats;
import gregtech.common.items.MetaTool;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Iterator;

public class ToolCrowbar extends ToolBase {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 1;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTechAPI.soundList.get(0);
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return GregTechAPI.soundList.get(0);
    }

    @Override
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return GregTechAPI.soundList.get(0);
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTechAPI.soundList.get(0);
    }

    @Override
    public boolean isCrowbar(ItemStack stack) {
        return true;
    }

//    @Override
//    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
//        if (block.getMaterial() == Material.CIRCUITS) {
//            return true;
//        }
//        String tool = block.getBlock().getHarvestTool(block);
//        if (tool == null || tool.equals("")) {
//            for (Iterator i$ = MetaTool.INSTANCE.mToolStats.values().iterator(); i$.hasNext(); i$.next()) {
//                if (i$ instanceof ToolCrowbar && !((IToolStats) i$).isMinableBlock(block, stack)) {
//                    return false;
//                }
//            }
//            return true;
//        }
//        return tool.equals("crowbar");
//    }

//    @Override
//    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
//        item.addStats(new CrowbarBehaviour(1, 1000));
//    }

//    @Override
//    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
//        return new TextComponentString(TextFormatting.GREEN + "")
//                .appendSibling(player.getDisplayName())
//                .appendText(TextFormatting.WHITE + " was removed " + TextFormatting.RED)
//                .appendSibling(entity.getDisplayName());
//    }
}
