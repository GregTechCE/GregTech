package gregtech.common.cable;

import gregtech.api.GTValues;
import gregtech.common.cable.tile.TileEntityCable;
import mcmultipart.api.container.IMultipartContainer;
import mcmultipart.api.multipart.IMultipart;
import mcmultipart.api.ref.MCMPCapabilities;
import mcmultipart.api.slot.EnumCenterSlot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;

public class RoutePath {

    public BlockPos destination;
    public HashMap<BlockPos, WireProperties> path = new HashMap<>();
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
            newPath.totalLoss += wireProperties.lossPerBlock;
        }
        return newPath;
    }

    public boolean burnCablesInPath(World world, long voltage, long amperage) {
        if(minVoltage >= voltage && minAmperage >= amperage)
            return false;
        for(BlockPos blockPos : path.keySet()) {
            WireProperties wireProperties = path.get(blockPos);
            if(voltage > wireProperties.voltage || amperage > wireProperties.amperage) {
                TileEntity tileEntity = world.getTileEntity(blockPos);
                if(!world.isRemote) {
                    ((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE,
                        blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5,
                        5 + world.rand.nextInt(3), 0.0, 0.0, 0.0, 0.1);
                }
                if(tileEntity instanceof TileEntityCable) {
                    world.setBlockToAir(blockPos);
                    world.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
                } else if(Loader.isModLoaded(GTValues.MODID_MCMP)) {
                    IMultipartContainer container = tileEntity.getCapability(MCMPCapabilities.MULTIPART_CONTAINER, null);
                    IMultipart partInfoInSlot = container == null ? null : container.getPart(EnumCenterSlot.CENTER).orElse(null);
                    if(partInfoInSlot instanceof BlockCable) {
                        container.removePart(EnumCenterSlot.CENTER);
                    }
                }
            }
        }
        return true;
    }

}
