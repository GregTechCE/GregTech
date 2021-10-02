package gregtech.common.tools;

import codechicken.lib.raytracer.RayTracer;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.TaskScheduler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.common.util.FakePlayer;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ToolUtility {

    public static EnumFacing getSideHit(World world, BlockPos blockPos, EntityPlayer harvester) {
        RayTraceResult result = RayTracer.retraceBlock(world, harvester, blockPos);
        return result == null ? harvester.getHorizontalFacing() : result.sideHit;
    }

    public static boolean applyTimberAxe(ItemStack itemStack, World world, BlockPos blockPos, EntityPlayer player) {
        if (player instanceof FakePlayer) {
            return false;
        }
        IBlockState blockState = world.getBlockState(blockPos);
        if(TreeChopTask.isLogBlock(blockState) == 1) {
            if(!world.isRemote) {
                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                if (playerMP.getCooldownTracker().hasCooldown(itemStack.getItem()))
                    return false;
                TreeChopTask treeChopTask = new TreeChopTask(blockPos, world, playerMP, itemStack);
                TaskScheduler.scheduleTask(world, treeChopTask);
            }
            return true;
        }
        return false;
    }

    public static boolean applyShearBehavior(ItemStack itemStack, BlockPos pos, EntityPlayer player) {
        Block block = player.world.getBlockState(pos).getBlock();
        if (block instanceof IShearable) {
            IShearable target = (IShearable) block;
            if (target.isShearable(itemStack, player.world, pos)) {
                int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, itemStack);
                List<ItemStack> drops = target.onSheared(itemStack, player.world, pos, fortuneLevel);
                dropListOfItems(player.world, pos, drops);
                         player.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
                return true;
            }
        }
        return false;
    }

    public static boolean applyHarvestBehavior(BlockPos pos, EntityPlayer player) {
        IBlockState blockState = player.world.getBlockState(pos);
        Block block = blockState.getBlock();
        if(block instanceof BlockCrops) {
            BlockCrops blockCrops = (BlockCrops) block;
            if(blockCrops.isMaxAge(blockState)) {
                @SuppressWarnings("deprecation")
                List<ItemStack> drops = blockCrops.getDrops(player.world, pos, blockState, 0);
                dropListOfItems(player.world, pos, drops);
                player.world.setBlockState(pos, blockCrops.withAge(0));
                return true;
            }
        }
        return false;
    }

    private static void dropListOfItems(World world, BlockPos pos, List<ItemStack> drops) {
        Random rand = new Random();
        for (ItemStack stack : drops) {
            float f = 0.7F;
            double offX = (rand.nextFloat() * f) + (1.0F - f) * 0.5D;
            double offY = (rand.nextFloat() * f) + (1.0F - f) * 0.5D;
            double offZ = (rand.nextFloat() * f) + (1.0F - f) * 0.5D;
            EntityItem entityItem = new EntityItem(world, pos.getX() + offX, pos.getY() + offY, pos.getZ() + offZ, stack);
            entityItem.setDefaultPickupDelay();
            world.spawnEntity(entityItem);
        }
    }

    public static void applyHammerDrops(Random random, IBlockState blockState, List<ItemStack> drops, int fortuneLevel, EntityPlayer player) {
        ItemStack itemStack = new ItemStack(blockState.getBlock(), 1, blockState.getBlock().getMetaFromState(blockState));
        Recipe recipe = RecipeMaps.FORGE_HAMMER_RECIPES.findRecipe(Long.MAX_VALUE, Collections.singletonList(itemStack), Collections.emptyList(), 0);
        if (recipe != null && !recipe.getOutputs().isEmpty()) {
            drops.clear();
            for (ItemStack outputStack : recipe.getResultItemOutputs(Integer.MAX_VALUE, random, 0)) {
                outputStack = outputStack.copy();
                if (!(player instanceof FakePlayer) && OreDictUnifier.getPrefix(outputStack) == OrePrefix.crushed) {
                    int growAmount = Math.round(outputStack.getCount() * random.nextFloat());
                    if (fortuneLevel > 0) {
                        int i = Math.max(0, random.nextInt(fortuneLevel + 2) - 1);
                        growAmount += outputStack.getCount() * i;
                    }
                    outputStack.grow(growAmount);
                }
                drops.add(outputStack);
            }
        }
    }


}
