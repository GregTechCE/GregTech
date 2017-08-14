package gregtech.common.tools;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ToolTurbineHuge extends ToolTurbine {
    @Override
    public float getSpeedMultiplier() {
        return 4.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier() {
        return 4.0F;
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
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 0;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 0;
    }

    @Override
    public ResourceLocation getMiningSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getCraftingSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getEntityHitSound(ItemStack stack) {
        return null;
    }

    @Override
    public ResourceLocation getBreakingSound(ItemStack stack) {
        return null;
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

    @Override
    public IIconContainer getTurbineIcon() {
        return Textures.ItemIcons.TURBINE_HUGE;
    }
}
