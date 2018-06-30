package gregtech.common.cable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICableTile {

    World getCableWorld();
    BlockPos getCablePos();
    int getInsulationColor();
    int getBlockedConnections();
    Insulation getInsulation();
    WireProperties getWireProperties();

}
