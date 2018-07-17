package gregtech.common.cable;

import gregtech.common.pipelike.Insulation;
import gregtech.common.pipelike.WireProperties;
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
