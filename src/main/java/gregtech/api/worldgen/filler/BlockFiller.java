package gregtech.api.worldgen.filler;

import com.google.gson.JsonObject;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.state.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

@ZenClass("mods.gregtech.ore.filter.BlockFiller")
@ZenRegister
public abstract class BlockFiller {

    public abstract void loadFromConfig(JsonObject object);

    public abstract IBlockState getStateForGeneration(@Nullable IBlockState currentState, int x, int y, int z);

    public abstract List<Function<IBlockState, IBlockState>> getAllPossibleStates();

    @ZenMethod("getStateForGeneration")
    public crafttweaker.api.block.IBlockState ctGetStateForGeneration(crafttweaker.api.block.IBlockState currentState, int x, int y, int z) {
        IBlockState mcBlockState = CraftTweakerMC.getBlockState(currentState);
        return CraftTweakerMC.getBlockState(getStateForGeneration(mcBlockState, x, y, z));
    }

}
