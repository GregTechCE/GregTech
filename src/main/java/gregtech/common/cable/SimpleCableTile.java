package gregtech.common.cable;

import gregtech.common.cable.tile.TileEntityCable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//Used in onBlockAdded when tile is not initialized yet
public class SimpleCableTile implements ICableTile {

    private final Insulation insulation;
    private final WireProperties properties;

    public SimpleCableTile(Insulation insulation, WireProperties properties) {
        this.insulation = insulation;
        this.properties = properties;
    }

    @Override
    public World getCableWorld() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BlockPos getCablePos() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getInsulationColor() {
        return TileEntityCable.DEFAULT_INSULATION_COLOR;
    }

    @Override
    public int getBlockedConnections() {
        return 0;
    }

    @Override
    public Insulation getInsulation() {
        return insulation;
    }

    @Override
    public WireProperties getWireProperties() {
        return properties;
    }
}
