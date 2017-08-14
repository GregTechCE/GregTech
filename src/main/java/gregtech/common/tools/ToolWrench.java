package gregtech.common.tools;

import gregtech.api.GregTech_API;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.unification.material.Materials;
import gregtech.common.items.behaviors.Behaviour_Wrench;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class ToolWrench extends ToolBase {

    public static final List<String> mEffectiveList = Arrays.asList(EntityIronGolem.class.getName(), "EntityTowerGuardian");

    @Override
    public float getNormalDamageAgainstEntity(float aOriginalDamage, Entity aEntity, ItemStack aStack, EntityPlayer aPlayer) {
        String tName = aEntity.getClass().getName();
        tName = tName.substring(tName.lastIndexOf('.') + 1);
        return (mEffectiveList.contains(tName)) || (tName.contains("Golem")) ? aOriginalDamage * 2.0F : aOriginalDamage;
    }

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 50;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 100;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 800;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 200;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 0;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public int getHurtResistanceTime(int aOriginalHurtResistance, Entity aEntity) {
        return aOriginalHurtResistance * 2;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 1.0F;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return GregTech_API.sSoundList.get(100);
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return null;
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
        return GregTech_API.sSoundList.get(100);
    }

    @Override
    public boolean isCrowbar() {
        return false;
    }

    @Override
    public boolean isWrench() {
        return true;
    }

    @Override
    public boolean isMinableBlock(IBlockState aBlock, ItemStack stack) {
        String tTool = aBlock.getBlock().getHarvestTool(aBlock);
        return ((tTool != null) && (tTool.equals("wrench"))) ||
                (aBlock.getMaterial() == Material.PISTON) ||
                (aBlock == Blocks.HOPPER) ||
                (aBlock == Blocks.DISPENSER) ||
                (aBlock == Blocks.DROPPER) ||
                aBlock.getMaterial() == Material.IRON;
    }

    @Override
    public int convertBlockDrops(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        return 0;
    }

    @Override
    public ItemStack getBrokenItem(ItemStack aStack) {
        return null;
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
//        return aIsToolHead ? Textures.ItemIcons.WRENCH : null;
//    }

    @Override
    public int getColor(boolean aIsToolHead, ItemStack aStack) {
        return aIsToolHead ? ToolMetaItem.getPrimaryMaterial(aStack).materialRGB : Materials.Iron.materialRGB;
    }

    @Override
    public void onStatsAddedToTool(MetaItem.MetaValueItem aItem, int aID) {
        aItem.addStats(new Behaviour_Wrench(100));
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase aPlayer, EntityLivingBase aEntity) {
        return new TextComponentString(TextFormatting.GREEN + "")
                .appendSibling(aPlayer.getDisplayName())
                .appendText(TextFormatting.WHITE + " wrenched " + TextFormatting.RED)
                .appendSibling(aEntity.getDisplayName())
                .appendText(TextFormatting.WHITE + " into the parts");
    }

}