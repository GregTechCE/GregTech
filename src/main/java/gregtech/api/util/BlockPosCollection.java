package gregtech.api.util;

import net.minecraft.util.math.BlockPos;

public abstract class BlockPosCollection {

    private BlockPos center;

    public BlockPosCollection() {
        this.center = new BlockPos(0, 0, 0);
    }

    public void setCenter(BlockPos pos) {
        this.center = pos;
    }

    public abstract void add(BlockPos pos);

    public abstract BlockPos remove(BlockPos pos);

    public abstract boolean contains(BlockPos pos);

    protected long blockPosToLong(BlockPos pos) {
        short x = (short) (pos.getX() - center.getX());
        short y = (short) (pos.getY() - center.getY());
        short z = (short) (pos.getZ() - center.getZ());

        long m = 0x000000000000ffffL;
        long l = 0;
        l += (m & x);
        l += (m & y) << 16;
        l += (m & z) << 32;

        return l;
    }

    protected BlockPos longToBlockPos(long l) {
        short x = (short) l;
        short y = (short) (l >> 16);
        short z = (short) (l >> 32);

        return new BlockPos((int) x + center.getX(), (int) y + center.getY(), (int) z + center.getZ());
    }
}
