package gregtech.common.tools;

import codechicken.lib.raytracer.RayTracer;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ToolUtility {

    public static EnumFacing getSideHit(World world, BlockPos blockPos, EntityPlayer harvester) {
        RayTraceResult result = RayTracer.retraceBlock(world, harvester, blockPos);
        return result == null ? harvester.getHorizontalFacing() : result.sideHit;
    }

    public static int applyTimberAxe(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer harvester, List<ItemStack> drops) {
        if (harvester.isSneaking() ||
            !GTUtility.isBlockOrePrefixed(world, blockPos, blockState, OrePrefix.log, drops))
            return 0; //do not try to convert while shift-clicking or non-log blocks
        MutableBlockPos mutableBlockPos = new MutableBlockPos(blockPos);
        int destroyedAmount = 0;
        while (true) {
            mutableBlockPos.move(EnumFacing.UP);
            IBlockState targetState = world.getBlockState(mutableBlockPos);
            if (targetState != blockState ||
                !world.isBlockModifiable(harvester, mutableBlockPos) ||
                !((EntityPlayerMP) harvester).interactionManager.tryHarvestBlock(mutableBlockPos))
                return destroyedAmount;
            destroyedAmount++;
        }
    }

    public static int applyShearable(World world, BlockPos blockPos, IBlockState blockState, List<ItemStack> drops, EntityPlayer harvester) {
        if (blockState.getBlock() instanceof IShearable) {
            IShearable shearable = (IShearable) blockState.getBlock();
            ItemStack selfStack = harvester.getHeldItem(EnumHand.MAIN_HAND);
            //because fucking minecraft removes block before that
            world.setBlockState(blockPos, blockState, 0);
            try {
                if (shearable.isShearable(selfStack, world, blockPos)) {
                    drops.clear();
                    int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, selfStack);
                    drops.addAll(shearable.onSheared(selfStack, world, blockPos, fortuneLevel));
                    return drops.size();
                }
            } finally {
                //also make sure that we removed block
                world.setBlockToAir(blockPos);
            }
        } else if (blockState.getMaterial() == Material.LEAVES) {
            int stackMetadata = blockState.getBlock().getMetaFromState(blockState);
            ItemStack dropStack = new ItemStack(blockState.getBlock(), 1, stackMetadata);
            if (!dropStack.isEmpty()) {
                //do not set damage for non-subtype items
                if (!dropStack.getItem().getHasSubtypes())
                    dropStack.setItemDamage(0);
                //only add drop stack if actual block has item form
                drops.clear();
                drops.add(dropStack);
                return 1;
            }
        }
        return 0;
    }

    public static int applyMultiBreak(World world, BlockPos blockPos, EntityPlayer harvester, ToolBase self, int size) {
        int conversions = 0;
        ItemStack selfStack = harvester.getHeldItem(EnumHand.MAIN_HAND);
        for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
                for (int k = -size; k <= size; k++) {
                    if (i == 0 && j == 0 && k == 0)
                        continue;
                    BlockPos block = blockPos.add(i, j, k);
                    if (!self.isMinableBlock(world.getBlockState(block), selfStack) ||
                        !world.canMineBlockBody(harvester, block) ||
                        !((EntityPlayerMP) harvester).interactionManager.tryHarvestBlock(block))
                        continue;
                    conversions++;
                }
            }
        }
        return conversions;
    }

    public static int applyHammerDrops(Random random, IBlockState blockState, List<ItemStack> drops, int fortuneLevel) {
        ItemStack itemStack = new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState));
        Recipe recipe = RecipeMaps.FORGE_HAMMER_RECIPES.findRecipe(Long.MAX_VALUE, Collections.singletonList(itemStack), Collections.emptyList());

        if (recipe != null && !recipe.getOutputs().isEmpty()) {
            drops.clear();
            for (ItemStack outputStack : recipe.getResultItemOutputs(random, 1)) {
                outputStack = outputStack.copy();
                if (OreDictUnifier.getPrefix(outputStack) == OrePrefix.crushed) {
                    int growAmount = Math.round(outputStack.getCount() * random.nextFloat());
                    if (fortuneLevel > 0) {
                        int i = Math.max(0, random.nextInt(fortuneLevel + 2) - 1);
                        growAmount += outputStack.getCount() * i;
                    }
                    outputStack.grow(growAmount);
                }
                drops.add(outputStack);
            }
            return 1;
        }
        return 0;
    }


}
