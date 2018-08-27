package gregtech.api.pipenet.tile;

import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.IPipeType;
import gregtech.common.pipelike.cable.Insulation;
import gregtech.common.pipelike.cable.WireProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPipeTile<PipeType extends Enum<PipeType> & IPipeType<NodeDataType>, NodeDataType> {

    int DEFAULT_INSULATION_COLOR = 0x777777;

    World getPipeWorld();

    BlockPos getPipePos();

    BlockPipe<PipeType, NodeDataType, ?> getPipeBlock();

    void transferDataFrom(IPipeTile<PipeType, NodeDataType> sourceTile);

    int getInsulationColor();

    void setInsulationColor(int newInsulationColor);

    int getBlockedConnections();

    PipeType getPipeType();

    NodeDataType getNodeData();

}
