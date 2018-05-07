package gregtech.api.worldgen.filler;

import com.google.gson.JsonObject;
import net.minecraft.block.state.IBlockState;

import java.util.Random;

public class SolidBlockFiller implements IBlockFiller {

    private IBlockState[] blockStates;

    public SolidBlockFiller(IBlockState... blockStates) {
        this.blockStates = blockStates;
    }

    @Override
    public void loadFromConfig(JsonObject object) {

    }

    @Override
    public IBlockState getStateForGeneration(Random gridRandom, IBlockState currentState, int maxDistance, int centerX, int centerY, int centerZ, int x, int y, int z) {
        return null;
    }

    public IBlockState getStateForGeneration(Random gridRandom, int maxDistance, int centerX, int centerY, int centerZ, int x, int y, int z) {
        return blockStates[gridRandom.nextInt(blockStates.length)];
    }
}
