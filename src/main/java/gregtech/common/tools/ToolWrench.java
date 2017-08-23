package gregtech.common.tools;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.common.items.behaviors.Behaviour_Wrench;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;

public class ToolWrench extends ToolBase {

    public static final List<String> mEffectiveList = Arrays.asList(EntityIronGolem.class.getName(), "EntityTowerGuardian");

    @Override
    public float getNormalDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase attacker) {
        String tName = entity.getClass().getName();
        tName = tName.substring(tName.lastIndexOf('.') + 1);
        return (mEffectiveList.contains(tName)) || (tName.contains("Golem")) ? 2.0F : 1.0F;
    }

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
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(100);
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return GregTechAPI.sSoundList.get(100);
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        String tTool = block.getBlock().getHarvestTool(block);
        return ((tTool != null) && (tTool.equals("wrench"))) ||
                (block.getMaterial() == Material.PISTON) ||
                (block == Blocks.HOPPER) ||
                (block == Blocks.DISPENSER) ||
                (block == Blocks.DROPPER) ||
                block.getMaterial() == Material.IRON;
    }

    @Override
    public IIconContainer getIcon(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? Textures.ItemIcons.WRENCH : null;
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem item, int ID) {
        item.addStats(new Behaviour_Wrench(100));
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase player, EntityLivingBase entity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(player.getDisplayName())
                .appendText(TextFormatting.WHITE + " wrenched " + TextFormatting.RED)
                .appendSibling(entity.getDisplayName())
                .appendText(TextFormatting.WHITE + " into the parts");
    }
}