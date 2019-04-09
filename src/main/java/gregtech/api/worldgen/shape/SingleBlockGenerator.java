package gregtech.api.worldgen.shape;

import com.google.gson.JsonObject;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.worldgen.config.OreConfigUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.Vec3i;
import org.apache.commons.lang3.ArrayUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

import java.util.Random;

@ZenClass("mods.gregtech.ore.generator.SingleBlockGenerator")
@ZenRegister
public class SingleBlockGenerator extends ShapeGenerator {

    private int minBlocksCount;
    private int maxBlocksCount;

    public SingleBlockGenerator() {
    }


    public SingleBlockGenerator(int minBlocksCount, int maxBlocksCount) {
        this.minBlocksCount = minBlocksCount;
        this.maxBlocksCount = maxBlocksCount;
    }

    @ZenGetter("minBlocksCount")
    public int getMinBlocksCount() {
        return minBlocksCount;
    }

    @ZenGetter("maxBlocksCount")
    public int getMaxBlocksCount() {
        return maxBlocksCount;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        int[] blocksCount = OreConfigUtils.getIntRange(object.get("blocks_count"));
        this.minBlocksCount = blocksCount[0];
        this.maxBlocksCount = blocksCount[1];
    }

    @Override
    public Vec3i getMaxSize() {
        return new Vec3i(maxBlocksCount, maxBlocksCount, maxBlocksCount);
    }

    @Override
    public void generate(Random gridRandom, IBlockGeneratorAccess relativeBlockAccess) {
        MutableBlockPos relativePos = new MutableBlockPos();
        int blocksCount = minBlocksCount == maxBlocksCount ? maxBlocksCount : minBlocksCount + gridRandom.nextInt(maxBlocksCount - minBlocksCount);
        EnumFacing prevDirection = null;
        for (int i = 0; i < blocksCount; i++) {
            EnumFacing[] allowedFacings = ArrayUtils.removeElement(EnumFacing.VALUES, prevDirection);
            prevDirection = allowedFacings[gridRandom.nextInt(allowedFacings.length)];
            relativePos.offset(prevDirection);
            relativeBlockAccess.generateBlock(relativePos.getX(), relativePos.getY(), relativePos.getZ());
        }
    }
}
