package gregtech.api.worldgen.shape;

import com.google.gson.JsonObject;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.block.IBlockState;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import gregtech.api.GTValues;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Method;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Random;

@ZenClass("mods.gregtech.ore.generator.ShapeGenerator")
@ZenRegister
public abstract class ShapeGenerator {

    /**
     * Loads shape generator configuration from the config
     */
    public abstract void loadFromConfig(JsonObject object);

    public abstract Vec3i getMaxSize();

    /**
     * Generates shape with the given generator access
     *
     * @param gridRandom          random instance to use for generation
     * @param relativeBlockAccess block access
     */
    public abstract void generate(Random gridRandom, IBlockGeneratorAccess relativeBlockAccess);

    @ZenMethod
    @Method(modid = GTValues.MODID_CT)
    public void generate(long randomSeed, IWorld world, IBlockPos centerPos, IBlockState blockState) {
        World mcWorld = CraftTweakerMC.getWorld(world);
        net.minecraft.block.state.IBlockState mcBlockState = CraftTweakerMC.getBlockState(blockState);
        BlockPos blockPos = CraftTweakerMC.getBlockPos(centerPos);
        generate(new Random(randomSeed), (x, y, z) -> mcWorld.setBlockState(blockPos, mcBlockState));
    }
}
