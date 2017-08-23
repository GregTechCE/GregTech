package gregtech.api.world;

import com.google.common.base.Predicate;
import gregtech.api.GregTechAPI;
import gregtech.common.blocks.BlockGranites;
import gregtech.common.blocks.BlockStones;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public abstract class GT_Worldgen {

    private final HashMap<String, Boolean> mDimensionMap = new HashMap<>();
    public final String mWorldGenName;
    public final boolean mEnabled;

    public GT_Worldgen(String aName, List<GT_Worldgen> aList, boolean aDefault) {
        mWorldGenName = aName;
        mEnabled = GregTechAPI.sWorldgenFile.get("worldgen", mWorldGenName, aDefault);
        if (mEnabled) aList.add(this);
    }

    /**
     * @param aWorld         The World Object
     * @param aRandom        The Random Generator to use
     * @param aBiome         The Name of the Biome (always != null)
     * @param aDimensionType The Type of Worldgeneration to add. -1 = Nether, 0 = Overworld, +1 = End
     * @param aChunkX        xCoord of the Chunk
     * @param aChunkZ        zCoord of the Chunk
     * @return if the Worldgeneration has been successfully completed
     */
    public boolean executeWorldgen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkGenerator aChunkGenerator, IChunkProvider aChunkProvider) {
        return false;
    }

    public boolean isDimensionAllowed(World aWorld, int aDimensionType, boolean nether, boolean overworld, boolean end, boolean moon, boolean mars) {
        String aDimName = aWorld.getProviderName();
        Boolean tAllowed = mDimensionMap.get(aDimName);
        if (tAllowed == null) {
            boolean tValue = GregTechAPI.sWorldgenFile.get("worldgen.dimensions." + mWorldGenName, aDimName, ((aDimensionType == -1) && nether) || ((aDimensionType == 0) && overworld) || ((aDimensionType == 1) && end));
            mDimensionMap.put(aDimName, tValue);
            return tValue;
        }
        return tAllowed;
    }

    public boolean isDimensionAllowed(World aWorld, int aDimensionType, int exceptedDimension) {
        String aDimName = aWorld.getProviderName();
        Boolean tAllowed = mDimensionMap.get(aDimName);
        if (tAllowed == null) {
            boolean tValue = GregTechAPI.sWorldgenFile.get("worldgen.dimensions." + mWorldGenName, aDimName, aDimensionType == exceptedDimension);
            mDimensionMap.put(aDimName, tValue);
            return tValue;
        }
        return tAllowed;
    }

    public boolean isGenerationAllowed(World aWorld, BlockPos blockPos) {
        IBlockState blockState = aWorld.getBlockState(blockPos);
        return ANY.apply(blockState);
    }


    public static Predicate<IBlockState> STONES = input ->
            input.getBlock() == Blocks.STONE ||
                    input.getBlock() instanceof BlockGranites ||
                    input.getBlock() instanceof BlockStones;

    public static Predicate<IBlockState> NETHERRACK = input ->
            input.getBlock() == Blocks.NETHERRACK;

    public static Predicate<IBlockState> ENDSTONE = input ->
            input.getBlock() == Blocks.END_STONE;

    public static Predicate<IBlockState> GRAVEL = input ->
            input.getBlock() == Blocks.GRAVEL;

    public static Predicate<IBlockState> SAND = input ->
            input.getBlock() == Blocks.SANDSTONE;


    public static Predicate<IBlockState> ANY = input ->
            STONES.apply(input) || NETHERRACK.apply(input) || ENDSTONE.apply(input) || GRAVEL.apply(input) || SAND.apply(input);


}