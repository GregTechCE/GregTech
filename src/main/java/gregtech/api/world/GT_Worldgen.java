package gregtech.api.world;

import gregtech.api.GregTech_API;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GT_Worldgen {

    public final String mWorldGenName;
    public final boolean mEnabled;
    private final Map<String, Boolean> mDimensionMap = new ConcurrentHashMap<String, Boolean>();

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
    public boolean executeWorldgen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider) {
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
    public boolean executeCavegen(World aWorld, Random aRandom, String aBiome, int aDimensionType, int aChunkX, int aChunkZ, IChunkProvider aChunkGenerator, IChunkProvider aChunkProvider) {
        return false;
    }

    public boolean isGenerationAllowed(World aWorld, int aDimensionType, int aAllowedDimensionType) {
        String aDimName = aWorld.provider.getDimensionName();
        Boolean tAllowed = mDimensionMap.get(aDimName);
        if (tAllowed == null) {
            boolean tValue = GregTech_API.sWorldgenFile.get("worldgen.dimensions." + mWorldGenName, aDimName, aDimensionType == aAllowedDimensionType);
            mDimensionMap.put(aDimName, tValue);
            return tValue;
        }
        return tAllowed;
    }
}
