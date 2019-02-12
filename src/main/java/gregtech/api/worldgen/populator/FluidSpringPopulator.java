package gregtech.api.worldgen.populator;

import com.google.gson.JsonObject;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.generator.GridEntryInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;

import java.util.Random;

public class FluidSpringPopulator implements VeinBufferPopulator {

    private IBlockState fluidState;
    private float springGenerationChance;

    public FluidSpringPopulator() {
    }

    public FluidSpringPopulator(IBlockState fluidState, float springGenerationChance) {
        this.fluidState = fluidState;
        this.springGenerationChance = springGenerationChance;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        this.springGenerationChance = object.get("chance").getAsFloat();
    }

    @Override
    public void populateBlockBuffer(OreDepositDefinition definition, Random random, GridEntryInfo gridEntryInfo, IBlockModifierAccess modifier) {
        if(random.nextFloat() <= springGenerationChance) {
            int groundLevel = gridEntryInfo.getTerrainHeight();
            int springUndergroundHeight = groundLevel - gridEntryInfo.getCenterPos(definition).getY();
            int springHeight = springUndergroundHeight + 6 + random.nextInt(3);
            for(int i = 1; i <= springHeight; i++) {
                modifier.setBlock(0, i, 0, 0);
                if(i <= springUndergroundHeight) {
                    modifier.setBlock(1, i, 0, 0);
                    modifier.setBlock(-1, i, 0, 0);
                    modifier.setBlock(0, i, 1, 0);
                    modifier.setBlock(0, i, -1, 0);
                }
            }
        }
    }

    @Override
    public IBlockState getBlockByIndex(World world, OreDepositDefinition definition, BlockPos pos, int index) {
        if(fluidState == null) {
            this.fluidState = definition.getBlockFiller().getAllPossibleStates().stream()
                .flatMap(it -> it.getPossibleResults().stream())
                .filter(it -> it.getPropertyKeys().contains(BlockFluidBase.LEVEL))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Couldn't find fluid block in a vein!"));
        }
        return fluidState.withProperty(BlockFluidBase.LEVEL, index);
    }
}
