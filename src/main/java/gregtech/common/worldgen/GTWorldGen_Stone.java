package gregtech.common.worldgen;

import java.util.HashSet;
import java.util.Random;
import java.util.function.Predicate;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.ore.StoneType;
import gregtech.api.unification.ore.StoneTypes;
import gregtech.api.util.GTWorldGen;
import gregtech.api.util.XSTR;
import gregtech.common.blocks.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class GTWorldGen_Stone extends GTWorldGen {

    public final int minY, maxY, minSize, maxSize, probability, amount;
    public final IBlockState stone;
    public final StoneType stoneType;
    public final boolean air;

    /**
     * @param name          Name of this stone generator
     * @param enabled       Set true to enable this generator
     * @param minY          Minimum height where the center of the stone will occurs 
     * @param maxY          Maximum height where the center of the stone will occurs; Must > minY
     * @param minSize       Minimum radius of the stone
     * @param maxSize       Maximum radius of the stone; Must > minSize
     * @param probability   Inverse of the probability that the stone will be generated in each chunk
     * @param amount        Maximum amount that the stone will generate in each chunk
     * @param stoneType     Type of the stone block
     * @param air           Whether the stone block can be generated in the air
     */
    public GTWorldGen_Stone(String name, boolean enabled, int minY, int maxY, int minSize, int maxSize, int probability, int amount, StoneType stoneType, boolean air, String[] dimWhiteList, String[] biomeWhiteList) {
        this(name, enabled, 1024, minY, maxY, minSize, maxSize, probability, amount, stoneType, air, dimWhiteList, biomeWhiteList);
    }

    protected GTWorldGen_Stone(String name, boolean enabled, int sortingWeight, int minY, int maxY, int minSize, int maxSize, int probability, int amount, StoneType stoneType, boolean air, String[] dimWhiteList, String[] biomeWhiteList) {
        super(name, enabled, sortingWeight, GregTechAPI.worldgenList, dimWhiteList, biomeWhiteList);
        this.minY = minY;
        this.maxY = maxY;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.probability = probability;
        this.amount = amount;
        this.stone = stoneType.stone.get();
        this.stoneType = stoneType;
        this.air = air;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, Biome biome, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (!this.isGenerationAllowed(world, biome) || this.probability <= 0)
            return;
        for (int i = 0; i < this.amount; i++) {
            if (random.nextInt(this.probability) == 0) {
                this.generate(random, chunkX, chunkZ, world, true,
                        pos -> this.air || !world.isAirBlock(pos),
                        (pos, r, rnd) -> {
                    IBlockState blockState = world.getBlockState(pos);
                    Block block = blockState.getBlock();
                    if (this.stoneType != StoneTypes._NULL && block instanceof BlockOre) {
                        if (blockState.getValue(((BlockOre) block).STONE_TYPE) != this.stoneType) {
                            getOreBlock(((BlockOre) block).material, this.stoneType, blockState.getValue((BlockOre.SMALL)))
                            .ifPresent(ore -> world.setBlockState(pos, ore, 18));
                        }
                        return;
                    }
                    StoneType stoneType;
                    if ((this.air && blockState.getBlock().isAir(blockState, world, pos))
                            || ((stoneType = StoneType.computeStoneType(blockState)) != StoneTypes._NULL && stoneType.harvestTool.equals("pickaxe"))) {
                        world.setBlockState(pos, this.stone);
                    }
                });
            }
        }
    }

    protected final void generate(Random random, int chunkX, int chunkZ, World world, boolean shortcut, Predicate<BlockPos> allowGenerateAtCenter, ActionAtPos action) {
        int size = this.minSize + random.nextInt(this.maxSize - this.minSize);
        int step = size >> 3;
        int centerX = (chunkX << 4) + random.nextInt(16);
        int centerY = this.minY + random.nextInt(this.maxY - this.minY);
        int centerZ = (chunkZ << 4) + random.nextInt(16);
        if (!allowGenerateAtCenter.test(new BlockPos(centerX, centerY, centerZ)))
            return;
        float phi = 6.2831855f * random.nextFloat();
        float cosPhi = MathHelper.cos(phi);
        float sinPhi = MathHelper.sin(phi);
        //float theta = (float) Math.acos(.5f * random.nextFloat());
        float cosTheta = .5f * random.nextFloat();
        float sinTheta = MathHelper.sqrt(1.0f - cosTheta * cosTheta);
        float val08 = .5235988f * (1.0f + random.nextFloat());
        float semiAxisA = size * MathHelper.cos(val08);
        float semiAxisB = size * MathHelper.sin(val08);
        float semiAxisC = size * (random.nextFloat() * .5f + .5f) * .5f;
        float maxRange = Math.max(semiAxisA, Math.max(semiAxisB, semiAxisC)) + step;
        float val17 = sinTheta * cosPhi;
        float val18 = cosTheta * cosPhi;
        float val19 = sinTheta * sinPhi;
        float val20 = cosTheta * sinPhi;
        int minX = MathHelper.floor(centerX + .5f - maxRange);
        int maxX = MathHelper.ceil(centerX + .5f + maxRange) + 1;
        int minZ = MathHelper.floor(centerZ + .5f - maxRange);
        int maxZ = MathHelper.ceil(centerZ + .5f + maxRange) + 1;
        int minY = MathHelper.floor(centerY + .5f - maxRange);
        int maxY = MathHelper.ceil(centerY + .5f + maxRange) + 1;
        float[] scales = new float[(step << 1) + 1];
        float[][] vals1 = new float[8][(step << 1) + 1];
        for (int i = 0; i < scales.length; i++) {
            scales[i] = random.nextFloat();
            float var = 6.2831855f * random.nextFloat();
            vals1[0][i] = .8660254f + .1339746f * random.nextFloat();
            vals1[1][i] = MathHelper.sqrt(1.0f - vals1[0][i] * vals1[0][i]);
            vals1[2][i] = MathHelper.cos(var);
            vals1[3][i] = MathHelper.sin(var);
            vals1[4][i] = vals1[0][i] * vals1[2][i];
            vals1[5][i] = vals1[0][i] * vals1[3][i];
            vals1[6][i] = vals1[1][i] * vals1[2][i];
            vals1[7][i] = vals1[1][i] * vals1[3][i];
        }
        
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        Random rnd = new XSTR(WorldGenerator.getRandomSeed(world, centerX, centerZ));
        for (int x =minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {
                for (int y = minY; y < maxY; y++) {
                    float var03 = x - centerX;
                    float var04 = z - centerZ;
                    float var05 = y - centerY;
                    float var06 = var03 * val17 + var04 * val18 + var05 * sinPhi;
                    float var07 = var04 * sinTheta - var03 * cosTheta;
                    float var08 = var05 * cosPhi - var03 * val19 - var04 * val20;
                    float r = 2.0f;
                    for (int i = 0; i < scales.length; i++) {
                        float var10 = var06 + (i << 1) - (step << 1);
                        float var11 = var10 * vals1[4][i] - var08 * vals1[6][i] + var07 * vals1[3][i];
                        float var12 = var07 * vals1[2][i] - var10 * vals1[5][i] + var08 * vals1[7][i];
                        float var13 = var10 * vals1[1][i] + var08 * vals1[0][i];
                        float var14 = scales[i] == 0.0f ? 0 : normSquare(var11 / semiAxisA / scales[i], var12 / semiAxisB / scales[i], var13 / semiAxisC / scales[i]);
                        if (var14 < r) r = var14;
                        if (shortcut && r <= 1.0f) {
                            action.generate(pos.setPos(x, y, z), r, rnd);
                            break;
                        }
                    }
                    if (!shortcut && r <= 1.0f) {
                        action.generate(pos.setPos(x, y, z), r, rnd);
                    }
                }
            }
        }
        
        HashSet<ChunkPos> chunkCoords = new HashSet<>();
        for (int i = (minX & ~0xF); i <= maxX; i += 16) {
            for (int j = (minZ & ~0xF); j <= maxZ; j += 16) {
                chunkCoords.add(new ChunkPos(i, j));
            }
        }
        chunkCoords/*.parallelStream()*/.forEach(chunkCoord -> {
            int x0 = Math.max(chunkCoord.x, minX);
            int x1 = Math.min(chunkCoord.x + 16, maxX);
            int y0 = Math.max(chunkCoord.z, minZ);
            int y1 = Math.min(chunkCoord.z + 16, maxZ);
            
        });
    }

    @FunctionalInterface
    protected interface ActionAtPos {
        /**
         * @param pos       Position of the block
         * @param r         Square of the relative "distance" metric of the block to the center area of the stone.
         *                  Ranged between 0.0f to 1.0f
         * @param random A local {@link Random}}
         */
        public void generate(BlockPos pos, float r, Random random);
    }

    public static float normSquare(float... x) {
        float r = 0;
        for (float xi : x) r += xi * xi;
        return r;
    }

    @Override
    public String toString() {
        return "stone." + this.name;
    }

}
