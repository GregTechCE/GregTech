package gregtech.integration.theoneprobe.provider;

import codechicken.multipart.TileMultipart;
import gregtech.api.GTValues;
import gregtech.api.pipenet.Node;
import gregtech.api.pipenet.PipeNet;
import gregtech.api.pipenet.block.BlockPipe;
import gregtech.api.pipenet.tile.IPipeTile;
import gregtech.common.ConfigHolder;
import gregtech.common.multipart.FluidPipeActiveMultiPart;
import gregtech.common.pipelike.fluidpipe.BlockFluidPipe;
import gregtech.common.pipelike.fluidpipe.tile.TileEntityFluidPipeActive;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import java.util.Map;

public class DebugPipeNetInfoProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return "gregtech:debug_pipe_net_provider";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        if(mode == ProbeMode.DEBUG && ConfigHolder.debug) {
            TileEntity tileEntity = world.getTileEntity(data.getPos());
            IPipeTile<?, ?> pipeTile = tileEntity == null ? null : getAnyPipeTile(tileEntity);
            if(pipeTile != null) {
                BlockPipe<?, ?, ?> blockPipe = pipeTile.getPipeBlock();
                PipeNet<?> pipeNet = blockPipe.getWorldPipeNet(world).getNetFromPos(data.getPos());
                if(pipeNet != null) {
                    probeInfo.text("Net: " + pipeNet.hashCode());
                    probeInfo.text("Node Info: ");
                    StringBuilder builder = new StringBuilder();
                    Map<BlockPos, ? extends Node<?>> nodeMap = pipeNet.getAllNodes();
                    Node<?> node = nodeMap.get(data.getPos());
                    builder.append("{").append("active: ").append(node.isActive)
                        .append(", mark: ").append(node.mark)
                        .append(", blocked: ").append(node.blockedConnections).append("}");
                    probeInfo.text(builder.toString());
                }
                if(blockPipe instanceof BlockFluidPipe) {
                    if(pipeTile instanceof TileEntityFluidPipeActive) {
                        probeInfo.text("tile active: " + ((TileEntityFluidPipeActive) pipeTile).isActive());
                    } else if(Loader.isModLoaded(GTValues.MODID_FMP) && pipeTile instanceof FluidPipeActiveMultiPart) {
                        probeInfo.text("tile active: " + ((FluidPipeActiveMultiPart) pipeTile).isActivePart());
                    }
                }
            }
        }
    }

    private IPipeTile<?, ?> getAnyPipeTile(TileEntity tileEntity) {
        if(tileEntity instanceof IPipeTile) {
            return (IPipeTile<?, ?>) tileEntity;
        } else if(Loader.isModLoaded(GTValues.MODID_FMP) &&
            tileEntity instanceof TileMultipart) {
            return (IPipeTile<?, ?>) ((TileMultipart) tileEntity).jPartList().stream()
                .filter(part -> part instanceof IPipeTile)
                .findFirst().orElse(null);
        }
        return null;
    }
}
