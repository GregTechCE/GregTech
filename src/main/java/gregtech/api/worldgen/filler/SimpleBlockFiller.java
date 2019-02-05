package gregtech.api.worldgen.filler;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.config.FillerConfigUtils;
import net.minecraft.block.state.IBlockState;

import java.util.Collections;
import java.util.List;

public class SimpleBlockFiller extends BlockFiller {

    private FillerEntry fillerEntry;

    public SimpleBlockFiller() {
    }

    public SimpleBlockFiller(FillerEntry blockStateFiller) {
        this.fillerEntry = blockStateFiller;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        this.fillerEntry = FillerConfigUtils.createBlockStateFiller(object.get("value"));
    }

    @Override
    public IBlockState apply(IBlockState currentState, int x, int y, int z) {
        return fillerEntry.apply(currentState);
    }

    @Override
    public List<FillerEntry> getAllPossibleStates() {
        return Collections.singletonList(fillerEntry);
    }
}
