package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.common.items.behaviors.Behaviour_Plunger_Fluid;
import gregtech.common.items.behaviors.Behaviour_Plunger_Item;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ToolPlunger extends ToolBase {

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.25F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 0.25F;
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
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(0);
    }

    @Override
    public boolean isCrowbar(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isGrafter(ItemStack stack) {
        return false;
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(101);
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock, ItemStack stack) {
        return false;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        return 0;
    }

    @Override
    public float getNormalDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase attacker) {
        return 0;
    }

    @Override
    public float getMagicDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase player) {
        return 0;
    }

    @Override
    public float getAttackSpeed(ItemStack stack) {
        return 0;
    }

//    @Override
//    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
//        return aIsToolHead ? Textures.ItemIcons.PLUNGER : null;
//    }

    @Override
    public int getColor(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).materialRGB : ToolMetaItem.getSecondaryMaterial(aStack).materialRGB;
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem aItem, int aID) {
        aItem.addStats(new Behaviour_Plunger_Item(getToolDamagePerDropConversion()));
        aItem.addStats(new Behaviour_Plunger_Fluid(getToolDamagePerDropConversion()));
    }

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 0;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 0;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 0;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 0;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 0;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.RED + "")
                .appendSibling(aEntity.getDisplayName())
                .appendText(TextFormatting.WHITE + " got stuck trying to escape through a Pipe while fighting " + TextFormatting.GREEN)
                .appendSibling(aPlayer.getDisplayName());
    }
}
