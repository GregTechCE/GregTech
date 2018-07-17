package gregtech.common.pipelike;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.worldobject.PipeNet;
import gregtech.api.worldobject.WorldPipeNet;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Map;

public class EnergyNet extends PipeNet<Insulation, WireProperties, IEnergyContainer> {

    private Map<BlockPos, Integer> amperage = Maps.newHashMap();
    private Collection<BlockPos> burntBlock = Sets.newHashSet();

    public EnergyNet(WorldPipeNet worldNet) {
        super(CableFactory.INSTANCE, worldNet);
    }

    @Override
    protected void onPostTick(long tickTimer) {
        World world = worldNets.getWorld();
        burntBlock.forEach(pos -> {
            world.setBlockToAir(pos);
            world.setBlockState(pos, Blocks.FIRE.getDefaultState());
        });
        amperage.clear();
        burntBlock.clear();
    }

    @Override
    protected void serializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {

    }

    @Override
    protected void deserializeNodeData(BlockPos pos, NBTTagCompound nodeTag) {

    }

    @Override
    protected void transferNodeDataTo(Collection<BlockPos> nodeToTransfer, PipeNet<Insulation, WireProperties, IEnergyContainer> toNet) {
        for (BlockPos pos : nodeToTransfer) {
            if (amperage.containsKey(pos)) ((EnergyNet) toNet).amperage.put(pos, amperage.get(pos));
            if (burntBlock.contains(pos)) ((EnergyNet) toNet).burntBlock.add(pos);
        }
    }

    @Override
    protected void removeData(BlockPos pos) {
        amperage.remove(pos);
        burntBlock.remove(pos);
    }
}
