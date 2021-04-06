package gregtech.api.pipenet.tile;

import gnu.trove.map.TIntIntMap;
import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.ModularUI;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.block.IPipeType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import java.util.function.Consumer;

import codechicken.lib.raytracer.CuboidRayTraceResult;

public interface IPipeTile<PipeType extends Enum<PipeType> & IPipeType<NodeDataType>, NodeDataType> extends IUIHolder {

    int DEFAULT_INSULATION_COLOR = 0x777777;

    World getPipeWorld();

    BlockPos getPipePos();

    default long getTickTimer() {
        return getPipeWorld().getWorldTime();
    }

    BlockPipe<PipeType, NodeDataType, ?> getPipeBlock();

    void transferDataFrom(IPipeTile<PipeType, NodeDataType> sourceTile);

    int getInsulationColor();

    void setInsulationColor(int newInsulationColor);

    int getBlockedConnections();

    TIntIntMap getBlockedConnectionsMap();

    boolean isConnectionBlocked(AttachmentType type, EnumFacing side);

    void setConnectionBlocked(AttachmentType type, EnumFacing side, boolean isBlocked);

    PipeType getPipeType();

    NodeDataType getNodeData();

    PipeCoverableImplementation getCoverableImplementation();

    boolean supportsTicking();

    IPipeTile<PipeType, NodeDataType> setSupportsTicking();

    boolean canPlaceCoverOnSide(EnumFacing side);

    <T> T getCapability(Capability<T> capability, EnumFacing side);

    <T> T getCapabilityInternal(Capability<T> capability, EnumFacing side);

    void notifyBlockUpdate();

    void writeCoverCustomData(int id, Consumer<PacketBuffer> writer);

    void markAsDirty();

    boolean isValidTile();

    void scheduleChunkForRenderUpdate();

    // Review: Introduced generic UI handling for pipes.
    default boolean isValid() {
        return isValidTile();
    }

    default boolean isRemote() {
        return getPipeWorld().isRemote;
    }

    default ModularUI createUI(EntityPlayer entityPlayer) {
        throw new RuntimeException("No UI defined for " + this);
    }

    default boolean onRightClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        return false;
    }

}
