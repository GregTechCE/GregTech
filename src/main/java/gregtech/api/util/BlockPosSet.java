package gregtech.api.util;

import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import net.minecraft.util.math.BlockPos;

public class BlockPosSet extends BlockPosCollection {
    private TLongSet set;

    public BlockPosSet() {
        this.set = new TLongHashSet(32);
    }

    public void add(BlockPos pos) {
        this.set.add(blockPosToLong(pos));
    }

    public BlockPos remove(BlockPos pos) {
        this.set.remove(blockPosToLong(pos));
        return pos;
    }

    public boolean contains(BlockPos pos) {
        return this.set.contains(blockPosToLong(pos));
    }

    public void clear() {
        this.set.clear();
    }
}
