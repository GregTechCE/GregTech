package gregtech.api.worldgen.filler;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.config.OreConfigUtils;
import net.minecraft.block.state.IBlockState;

import java.util.Random;
import java.util.function.Function;

public class SimpleBlockFiller implements IBlockFiller {

    private Function<IBlockState, IBlockState> blockStateFiller;

    public SimpleBlockFiller() {
    }

    public SimpleBlockFiller(Function<IBlockState, IBlockState> blockStateFiller) {
        this.blockStateFiller = blockStateFiller;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        this.blockStateFiller = OreConfigUtils.createBlockStateFiller(object.get("value"));
    }

    @Override
    public IBlockState getStateForGeneration(Random gridRandom, IBlockState currentState, int x, int y, int z) {
        return blockStateFiller.apply(currentState);
    }

}
