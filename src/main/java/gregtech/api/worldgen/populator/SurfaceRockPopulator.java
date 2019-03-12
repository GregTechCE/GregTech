package gregtech.api.worldgen.populator;

import com.google.gson.JsonObject;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.DustMaterial.MatFlags;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.worldgen.config.OreConfigUtils;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.generator.GridEntryInfo;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.surfacerock.BlockSurfaceRock;
import gregtech.common.blocks.surfacerock.BlockSurfaceRockFlooded;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

import java.util.Random;

public class SurfaceRockPopulator implements VeinChunkPopulator {

    private IngotMaterial material;

    public SurfaceRockPopulator() {
    }

    public SurfaceRockPopulator(IngotMaterial material) {
        this.material = material;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        DustMaterial material = OreConfigUtils.getMaterialByName(object.get("material").getAsString());
        if(!(material instanceof IngotMaterial))
            throw new IllegalArgumentException("Only metal materials are supported for surface rocks");
        if(!material.hasFlag(MatFlags.GENERATE_ORE))
            throw new IllegalArgumentException("Only materials with ore can have surface rocks");
        this.material = (IngotMaterial) material;
    }

    @Override
    public void initializeForVein(OreDepositDefinition definition) {
    }

    @Override
    public void populateChunk(World world, int chunkX, int chunkZ, Random random, OreDepositDefinition definition, GridEntryInfo gridEntryInfo) {
        if(world.getWorldType() != WorldType.FLAT) {
            int stonesCount = random.nextInt(2);
            for (int i = 0; i < stonesCount; i++) {
                int randomX = chunkX * 16 + random.nextInt(16);
                int randomZ = chunkZ * 16 + random.nextInt(16);
                BlockPos topBlockPos = new BlockPos(randomX, 0, randomZ);
                topBlockPos = world.getTopSolidOrLiquidBlock(topBlockPos).down();
                IBlockState blockState = world.getBlockState(topBlockPos);
                if (blockState.getBlockFaceShape(world, topBlockPos, EnumFacing.UP) != BlockFaceShape.SOLID ||
                    !blockState.isOpaqueCube() || !blockState.isFullBlock())
                    continue;
                BlockPos surfaceRockPos = topBlockPos.up();
                IBlockState blockStateReplaced = world.getBlockState(surfaceRockPos);

                boolean isFloodedBlock = blockStateReplaced.getMaterial() == Material.WATER;
                IBlockState stoneBlockState;
                if (!isFloodedBlock) {
                    BlockSurfaceRock blockSurfaceRock = MetaBlocks.SURFACE_ROCKS.get(material);
                    stoneBlockState = blockSurfaceRock.getDefaultState().withProperty(blockSurfaceRock.materialProperty, material);
                } else {
                    BlockSurfaceRockFlooded blockSurfaceRock = MetaBlocks.FLOODED_SURFACE_ROCKS.get(material);
                    stoneBlockState = blockSurfaceRock.getDefaultState().withProperty(blockSurfaceRock.materialProperty, material);
                }
                world.setBlockState(surfaceRockPos, stoneBlockState, 16);
            }
        }
    }
}
