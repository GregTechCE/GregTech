package gregtech.api.worldgen.filler;

import com.google.gson.JsonObject;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.gregtech.ore.filler.BlockFiller")
@ZenRegister
public abstract class BlockFiller {

    public abstract void loadFromConfig(JsonObject object);

    public abstract IBlockState apply(IBlockState currentState, IBlockAccess blockAccess, BlockPos blockPos, int relativeX, int relativeY, int relativeZ);

    public abstract List<FillerEntry> getAllPossibleStates();

    @ZenMethod("apply")
    public crafttweaker.api.block.IBlockState ctGetStateForGeneration(crafttweaker.api.block.IBlockState currentState, crafttweaker.api.world.IBlockAccess blockAccess, crafttweaker.api.world.IBlockPos blockPos, int relativeX, int relativeY, int relativeZ) {
        IBlockState mcBlockState = CraftTweakerMC.getBlockState(currentState);
        return CraftTweakerMC.getBlockState(apply(mcBlockState, (IBlockAccess) blockAccess.getInternal(), (BlockPos) blockPos.getInternal(), relativeX, relativeY, relativeZ));
    }

}
