package gregtech.api.worldgen.filler;

import com.google.gson.JsonObject;
import net.minecraft.block.state.IBlockState;

import java.util.Random;

public interface IBlockFiller {

    void loadFromConfig(JsonObject object);

    IBlockState getStateForGeneration(Random gridRandom, IBlockState currentState, int maxDistance, int centerX, int centerY, int centerZ, int x, int y, int z);

}
