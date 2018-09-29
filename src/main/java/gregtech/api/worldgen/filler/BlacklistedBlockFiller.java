package gregtech.api.worldgen.filler;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.config.OreConfigUtils;
import net.minecraft.block.state.IBlockState;

import java.util.List;
import java.util.function.Function;

public class BlacklistedBlockFiller implements IBlockFiller {

    private Function<IBlockState, IBlockState> blockStateFiller;
    private List<IBlockState> blacklist;

    public BlacklistedBlockFiller(List<IBlockState> blacklist) {
        this.blacklist = blacklist;
    }

    public BlacklistedBlockFiller(List<IBlockState> blacklist, Function<IBlockState, IBlockState> blockStateFiller) {
        this.blacklist = blacklist;
        this.blockStateFiller = blockStateFiller;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        this.blockStateFiller = OreConfigUtils.createBlockStateFiller(object.get("value"));
    }

    @Override
    public IBlockState getStateForGeneration(IBlockState currentState, int x, int y, int z) {
        for (IBlockState blockState : blacklist) {
            if (blockState == currentState) {
                return currentState;
            }
        }
        return blockStateFiller.apply(currentState);
    }
}
