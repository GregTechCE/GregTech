package gregtech.api.cable;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class RoutePath {

    public BlockPos destination;
    public HashMap<BlockPos, WireProperties> path;
    public int minAmperage = Integer.MAX_VALUE;
    public int minVoltage = Integer.MAX_VALUE;
    public int totalLoss;

    public RoutePath cloneAndCompute(BlockPos destination) {
        RoutePath newPath = new RoutePath();
        newPath.path = new HashMap<>(path);
        newPath.destination = destination;
        for(WireProperties wireProperties : path.values()) {
            newPath.minAmperage = Math.min(newPath.minAmperage, wireProperties.amperage);
            newPath.minVoltage = Math.min(newPath.minVoltage, wireProperties.voltage);
            newPath.totalLoss += newPath.totalLoss;
        }
        return newPath;
    }

    public boolean burnCablesInPath(World world, long voltage, long amperage) {
        if(minVoltage >= voltage && minAmperage >= amperage)
            return false;
        for(BlockPos blockPos : path.keySet()) {
            WireProperties wireProperties = path.get(blockPos);
            if(voltage > wireProperties.voltage || amperage > wireProperties.amperage) {
                world.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
            }
        }
        return true;
    }

}
