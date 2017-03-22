package gregtech.api.world;

import gregtech.api.GregTech_API;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.List;
import java.util.Random;

public abstract class GT_Worldgen {

    public final String mWorldGenName;
    public final boolean mEnabled;

    public GT_Worldgen(String aName, List aList, boolean aDefault) {
        mWorldGenName = aName;
        mEnabled = GregTech_API.sWorldgenFile.get("worldgen", mWorldGenName, aDefault);
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

    /**
     * @param aWorld         The World Object
     * @param aRandom        The Random Generator to use
     * @param aBiome         The Name of the Biome (always != null)
     * @param aDimensionType The Type of Worldgeneration to add. -1 = Nether, 0 = Overworld, +1 = End
     * @param aChunkX        xCoord of the Chunk
     * @param aChunkZ        zCoord of the Chunk
     * @return if the Worldgeneration has been successfully completed
     */
    public boolean executeCavegen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkGenerator aChunkGenerator, IChunkProvider aChunkProvider) {
        return false;
    }

    public boolean isDimensionAllowed(World aWorld, int aDimensionType, boolean nether, boolean overworld, boolean end, boolean moon, boolean mars) {
        /*String aDimName = aWorld.getProviderName();
        Boolean tAllowed = mDimensionMap.get(aDimName);
        if (tAllowed == null) {
            boolean tValue = GregTech_API.sWorldgenFile.get("worldgen.dimensions." + mWorldGenName, aDimName, ((aDimensionType == -1) && nether) || ((aDimensionType == 0) && overworld) || ((aDimensionType == 1) && end));
            mDimensionMap.put(aDimName, tValue);
            return tValue;
        }
        return tAllowed;*/
        return (aDimensionType == 0 && overworld) || (aDimensionType == -1 && nether) || (aDimensionType == 1 && end) || (aDimensionType == -28 && moon) || (aDimensionType == -29 && mars);
    }

    public boolean isDimensionAllowed(World aWorld, int aDimensionType, int exceptedDimension) {
        /*String aDimName = aWorld.getProviderName();
        Boolean tAllowed = mDimensionMap.get(aDimName);
        if (tAllowed == null) {
            boolean tValue = GregTech_API.sWorldgenFile.get("worldgen.dimensions." + mWorldGenName, aDimName, aDimensionType == exceptedDimension);
            mDimensionMap.put(aDimName, tValue);
            return tValue;
        }
        return tAllowed;*/
        return aDimensionType == exceptedDimension;
    }

    public boolean isGenerationAllowed(World aWorld, BlockPos blockPos) {
        IBlockState blockState = aWorld.getBlockState(blockPos);
        return GT_Worldgen_Constants.ANY.apply(blockState);
    }

}