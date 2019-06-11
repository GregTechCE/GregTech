package gregtech.common.tools;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import gregtech.common.items.behaviors.ModeSwitchBehavior;
import gregtech.common.items.behaviors.ModeSwitchBehavior.ILocalizationKey;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToolJackHammer extends ToolDrillLV {

    public static final ModeSwitchBehavior<JackHammerMode> MODE_SWITCH_BEHAVIOR = new ModeSwitchBehavior<>(JackHammerMode.class);

    public enum JackHammerMode implements ILocalizationKey {
        THREE_BY_THREE("metaitem.jack_hammer.mode.three_by_three", 3, 3, 1.2f),
        VERTICAL_LINE("metaitem.jack_hammer.mode.vertical_line", 3, 1, 2.5f),
        HORIZONTAL_LINE("metaitem.jack_hammer.mode.horizontal_line", 1, 3, 2.5f),
        SINGLE_BLOCK("metaitem.jack_hammer.mode.single_block", 1, 1, 3.0f);

        private final String localizationKey;
        private final int verticalSize;
        private final int horizontalSize;
        private final float digSpeedMultiplier;

        JackHammerMode(String localizationKey, int verticalSize, int horizontalSize, float digSpeedMultiplier) {
            this.localizationKey = localizationKey;
            this.verticalSize = verticalSize;
            this.horizontalSize = horizontalSize;
            this.digSpeedMultiplier = digSpeedMultiplier;
        }

        public int getVerticalSize() {
            return verticalSize;
        }

        public int getHorizontalSize() {
            return horizontalSize;
        }

        public float getDigSpeedMultiplier() {
            return digSpeedMultiplier;
        }

        @Override
        public String getUnlocalizedName() {
            return localizationKey;
        }
    }

    @Override
    public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(Items.IRON_PICKAXE);
    }

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 4;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 4;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 32;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 8;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getDigSpeedMultiplier(ItemStack stack) {
        JackHammerMode jackHammerMode = MODE_SWITCH_BEHAVIOR.getModeFromItemStack(stack);
        return jackHammerMode.getDigSpeedMultiplier();
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 2.0F;
    }

    @Override
    public boolean canMineBlock(IBlockState block, ItemStack stack) {
        String tool = block.getBlock().getHarvestTool(block);
        return (tool != null && (tool.equals("hammer") || tool.equals("pickaxe"))) ||
            block.getMaterial() == Material.ROCK ||
            block.getMaterial() == Material.GLASS ||
            block.getMaterial() == Material.ICE ||
            block.getMaterial() == Material.PACKED_ICE;
    }

    @Override
    public void onStatsAddedToTool(MetaValueItem metaValueItem) {
        metaValueItem.addStats(MODE_SWITCH_BEHAVIOR);
    }

    @Override
    public List<BlockPos> getAOEBlocks(ItemStack itemStack, EntityPlayer player, RayTraceResult rayTraceResult) {
        if(player.isCreative()) {
            return Collections.emptyList();
        }
        ArrayList<BlockPos> result = new ArrayList<>();
        BlockPos pos = rayTraceResult.getBlockPos();
        JackHammerMode jackHammerMode = MODE_SWITCH_BEHAVIOR.getModeFromItemStack(itemStack);
        EnumFacing horizontalFacing = player.getHorizontalFacing();
        int xSizeExtend = (jackHammerMode.getHorizontalSize() - 1) / 2;
        int ySizeExtend = (jackHammerMode.getVerticalSize() - 1) / 2;
        for (int x = -xSizeExtend; x <= xSizeExtend; x++) {
            for (int y = -ySizeExtend; y <= ySizeExtend; y++) {
                //do not check center block - it's handled now
                if (x == 0 && y == 0) continue;
                BlockPos offsetPos = rotate(pos, x, y, rayTraceResult.sideHit, horizontalFacing);
                IBlockState blockState = player.world.getBlockState(offsetPos);
                if(itemStack.canHarvestBlock(blockState)) {
                    result.add(offsetPos);
                }
            }
        }
        return result;
    }

    @Override
    public void onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
        if(entity instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) entity;
            EnumFacing sideHit = ToolUtility.getSideHit(world, pos, entityPlayer);
            int damagePerBlockBreak = getToolDamagePerBlockBreak(stack);
            JackHammerMode jackHammerMode = MODE_SWITCH_BEHAVIOR.getModeFromItemStack(stack);
            EnumFacing horizontalFacing = entity.getHorizontalFacing();
            int xSizeExtend = (jackHammerMode.getHorizontalSize() - 1) / 2;
            int ySizeExtend = (jackHammerMode.getVerticalSize() - 1) / 2;
            for (int x = -xSizeExtend; x <= xSizeExtend; x++) {
                for (int y = -ySizeExtend; y <= ySizeExtend; y++) {
                    //do not check center block - it's handled now
                    if (x == 0 && y == 0) continue;
                    BlockPos offsetPos = rotate(pos, x, y, sideHit, horizontalFacing);
                    IBlockState blockState = world.getBlockState(offsetPos);
                    if(world.isBlockModifiable(entityPlayer, offsetPos) && stack.canHarvestBlock(blockState)) {
                        GTUtility.harvestBlock(world, offsetPos, entityPlayer);
                        ((ToolMetaItem) stack.getItem()).damageItem(stack, damagePerBlockBreak, false);
                    }
                }
            }
        }
    }

    @Override
    public ItemStack getBrokenStack(ItemStack stack) {
        IElectricItem electricItem = stack.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        return MetaItems.JACKHAMMER_BASE.getChargedStackWithOverride(electricItem);
    }

    private static BlockPos rotate(BlockPos origin, int x, int y, EnumFacing sideHit, EnumFacing horizontalFacing) {
        switch (sideHit.getAxis()) {
            case X: return origin.add(0, y, x);
            case Z: return origin.add(x, y, 0);
            case Y: return rotateVertical(origin, x, y, horizontalFacing);
            default: return BlockPos.ORIGIN;
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private static BlockPos rotateVertical(BlockPos origin, int x, int y, EnumFacing horizontalFacing) {
        switch (horizontalFacing.getAxis()) {
            case X: return origin.add(y, 0, x);
            case Z: return origin.add(x, 0, y);
            default: return BlockPos.ORIGIN;
        }
    }
}
