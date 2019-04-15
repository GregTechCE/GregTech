package gregtech.api.util;

import gnu.trove.queue.TLongQueue;
import net.minecraft.util.math.BlockPos;

/**
 * This queue takes BlockPos and store them as long
 */
public class BlockPosQueue extends BlockPosCollection {

    private long[] data;
    private int k1, k2;
    private static final int INITIAL_SIZE = 32;
    private TLongQueue queue;

    public BlockPosQueue() {
        super();
        this.data = new long[INITIAL_SIZE];
        this.k1 = 0;
        this.k2 = 0;
    }

    public void add(BlockPos pos) {
        if(pos == null)
            return;

        tryExpandSize();
        data[(k2 ++) % data.length] = blockPosToLong(pos);
    }

    @Override
    public BlockPos remove(BlockPos pos) {
        return poll();
    }

    public BlockPos poll() {
        if(!isEmpty()) {
            BlockPos pos = longToBlockPos(data[(k1 ++) % data.length]);
            tryReduceSize();
            return pos;
        }

        return null;
    }

    public boolean contains(BlockPos pos) {
        if(pos == null)
            return false;

        long temp = blockPosToLong(pos);
        if(!isEmpty()) {
            for(int i = k1; i <= k2; i ++)
                if(data[i % data.length] == temp)
                    return true;
        }

        return false;
    }

    public boolean isEmpty() {
        return k2 <= k1;
    }

    public void clear() {
        this.data = new long[INITIAL_SIZE];
        this.k1 = 0;
        this.k2 = 0;
    }

    private void tryExpandSize() {
        if(k2 - k1 >= data.length) {
            long[] temp = new long[data.length * 2];
            for(int i = k1; i < k2; i ++)
                temp[i - k1] = data[i % data.length];

            data = temp;
            k1 = 0;
            k2 -= k1;
        }
    }

    private void tryReduceSize() {
        if(k2 - k1 < data.length / 2 && data.length > INITIAL_SIZE) {
            long[] temp = new long[data.length / 2];
            for(int i = k1; i < k2; i ++) {
                temp[i - k1] = data[i % data.length];
            }

            data = temp;
            k1 = 0;
            k2 -= k1;
        }
    }
}
