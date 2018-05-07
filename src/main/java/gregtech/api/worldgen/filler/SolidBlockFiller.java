package gregtech.api.worldgen.filler;

import net.minecraft.block.state.IBlockState;

import java.util.Random;

public class SolidBlockFiller implements IBlockFiller {

    private IBlockState[] blockStates;

    public SolidBlockFiller(IBlockState... blockStates) {
        this.blockStates = blockStates;
    }

    @Override
    public IBlockState getStateForGeneration(Random gridRandom, int maxDistance, int centerX, int centerY, int centerZ, int x, int y, int z) {
        return blockStates[gridRandom.nextInt(blockStates.length)];
    }
}
