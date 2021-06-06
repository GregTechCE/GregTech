package gregtech.api.worldgen.populator;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.config.PredicateConfigUtils;
import gregtech.api.worldgen.generator.GridEntryInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

import java.util.Random;

public class SurfaceBlockPopulator implements VeinChunkPopulator {

    private IBlockState blockState;
    private int minIndicatorAmount;
    private int maxIndicatorAmount;

    public SurfaceBlockPopulator() {
    }

    public SurfaceBlockPopulator(IBlockState blockState) {
        this.blockState = blockState;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        this.blockState = PredicateConfigUtils.parseBlockStateDefinition(object.getAsJsonObject("block"));
        this.minIndicatorAmount = JsonUtils.getInt(object, "min_amount", 0);
        this.maxIndicatorAmount = JsonUtils.getInt(object, "max_amount", 2);
    }

    @Override
    public void initializeForVein(OreDepositDefinition definition) {
    }

    @Override
    public void populateChunk(World world, int chunkX, int chunkZ, Random random, OreDepositDefinition definition, GridEntryInfo gridEntryInfo) {
        if (world.getWorldType() != WorldType.FLAT) {
            int stonesCount = minIndicatorAmount + (minIndicatorAmount >= maxIndicatorAmount ? 0 : random.nextInt(maxIndicatorAmount - minIndicatorAmount));
            for (int i = 0; i < stonesCount; i++) {
                int randomX = chunkX * 16 + random.nextInt(16);
                int randomZ = chunkZ * 16 + random.nextInt(16);
                BlockPos topBlockPos = new BlockPos(randomX, 0, randomZ);
                topBlockPos = world.getTopSolidOrLiquidBlock(topBlockPos).down();
                IBlockState blockState = world.getBlockState(topBlockPos);
                Block blockAtPos = blockState.getBlock();
                if (blockState.getBlockFaceShape(world, topBlockPos, EnumFacing.UP) != BlockFaceShape.SOLID ||
                    !blockState.isOpaqueCube() || !blockState.isFullBlock() || !(blockAtPos.isReplaceable(world, topBlockPos) &&
                    blockState.getMaterial().isLiquid()))
                    continue;
                BlockPos surfaceRockPos = topBlockPos.up();
                world.setBlockState(surfaceRockPos, this.blockState, 16);
            }
        }
    }

    public IBlockState getBlockState() {
        return blockState;
    }
}
