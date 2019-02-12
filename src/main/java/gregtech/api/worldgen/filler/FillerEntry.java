package gregtech.api.worldgen.filler;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ZenClass("mods.gregtech.ore.filler.")
public interface FillerEntry {

    IBlockState apply(IBlockState source);

    List<FillerEntry> getSubEntries();

    Collection<IBlockState> getPossibleResults();

    static FillerEntry createSimpleFiller(IBlockState blockState) {
        List<IBlockState> possibleResults = ImmutableList.of(blockState);
        return new FillerEntry() {
            @Override
            public IBlockState apply(IBlockState source) {
                return blockState;
            }

            @Override
            public List<FillerEntry> getSubEntries() {
                return Collections.emptyList();
            }

            @Override
            public List<IBlockState> getPossibleResults() {
                return possibleResults;
            }
        };
    }
}
