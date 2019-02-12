package gregtech.api.worldgen.filler;

import com.google.gson.JsonObject;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.state.IBlockState;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.List;

@ZenClass("mods.gregtech.ore.filler.BlockFiller")
@ZenRegister
public abstract class BlockFiller {

    public abstract void loadFromConfig(JsonObject object);

    public abstract IBlockState apply(@Nullable IBlockState currentState, int x, int y, int z);

    public abstract List<FillerEntry> getAllPossibleStates();

    @ZenMethod("apply")
    public crafttweaker.api.block.IBlockState ctGetStateForGeneration(crafttweaker.api.block.IBlockState currentState, int x, int y, int z) {
        IBlockState mcBlockState = CraftTweakerMC.getBlockState(currentState);
        return CraftTweakerMC.getBlockState(apply(mcBlockState, x, y, z));
    }

}
