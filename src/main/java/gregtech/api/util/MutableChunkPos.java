package gregtech.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class MutableChunkPos extends ChunkPos {
    int x, z;

    public MutableChunkPos() {
        super(0, 0);
    }

    public MutableChunkPos(int x, int z) {
        this();
        this.x = x;
        this.z = z;
    }

    public MutableChunkPos(BlockPos pos) {
        this();
        x = pos.getX() >> 4;
        z = pos.getZ() >> 4;
    }

    public void setPos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public void setPos(BlockPos pos) {
        x = pos.getX() >> 4;
        z = pos.getZ() >> 4;
    }

    ChunkPos toImmutable() {
        return new ChunkPos(x, z);
    }

    @Override
    public int hashCode() {
        int i = 1664525 * x + 1013904223;
        int j = 1664525 * (z ^ -559038737) + 1013904223;
        return i ^ j;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        else if (!(obj instanceof ChunkPos)) return false;
        else {
            ChunkPos chunkpos = (ChunkPos) obj;
            return this.x == chunkpos.x && this.z == chunkpos.z;
        }
    }

    @Override
    public double getDistanceSq(Entity entityIn) {
        double d0 = (double) (x * 16 + 8);
        double d1 = (double) (z * 16 + 8);
        double d2 = d0 - entityIn.posX;
        double d3 = d1 - entityIn.posZ;
        return d2 * d2 + d3 * d3;
    }

    @Override
    public int getXStart() {
        return x << 4;
    }

    @Override
    public int getZStart() {
        return z << 4;
    }

    @Override
    public int getXEnd() {
        return (x << 4) + 15;
    }

    @Override
    public int getZEnd() {
        return (this.z << 4) + 15;
    }

    @Override
    public BlockPos getBlock(int x, int y, int z) {
        return new BlockPos((x << 4) + x, y, (z << 4) + z);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + z + "]";
    }
}
