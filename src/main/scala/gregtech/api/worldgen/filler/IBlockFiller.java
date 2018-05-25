package gregtech.api.worldgen.filler;

import com.google.gson.JsonObject;
import net.minecraft.block.state.IBlockState;

public interface IBlockFiller {

    void loadFromConfig(JsonObject object);

    IBlockState getStateForGeneration(IBlockState currentState, int x, int y, int z);

}
