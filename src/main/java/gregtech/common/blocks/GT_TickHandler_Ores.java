package gregtech.common.blocks;

import gregtech.api.enums.GT_Values;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class GT_TickHandler_Ores {

    public static HashMap<Integer, HashMap<ChunkCoordIntPair, ArrayList<GT_TileEntity_Ores>>> data = new HashMap<>();

    public GT_TickHandler_Ores() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void loadChunkOre(GT_TileEntity_Ores gt_tileEntity_ores) {
        getChunkData(
                gt_tileEntity_ores.getWorld().provider.getDimension(),
                new ChunkCoordIntPair(gt_tileEntity_ores.getPos()))
                .add(gt_tileEntity_ores);
    }

    public static void unloadChunkOre(GT_TileEntity_Ores gt_tileEntity_ores) {
        getChunkData(
                gt_tileEntity_ores.getWorld().provider.getDimension(),
                new ChunkCoordIntPair(gt_tileEntity_ores.getPos()))
        .remove(gt_tileEntity_ores);
    }

    public static ArrayList<GT_TileEntity_Ores> getChunkData(int dimension, ChunkCoordIntPair chunk) {
        HashMap<ChunkCoordIntPair, ArrayList<GT_TileEntity_Ores>> dimensionData = data.get(dimension);
        if(dimensionData == null) {
            dimensionData = new HashMap<>();
            data.put(dimension, dimensionData);
        }
        ArrayList<GT_TileEntity_Ores> oresData = dimensionData.get(chunk);
        if(oresData == null) {
            oresData = new ArrayList<>();
            dimensionData.put(chunk, oresData);
        }
        return oresData;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent player) {
        /*if(player.side.isServer()) {
            EntityPlayer entityPlayer = player.player;
            World world = entityPlayer.worldObj;
            if (world.getWorldTime() % 10 == 0) {
                GT_Packet_Ores packet_ores = new GT_Packet_Ores();
                ChunkCoordIntPair chunk = new ChunkCoordIntPair(new BlockPos(entityPlayer));
                for (int x = -1; x < 1; x++) {
                    for (int z = -1; z < 1; z++) {
                        ChunkCoordIntPair offset = chunk.offset(x, z);
                        ArrayList<GT_TileEntity_Ores> ores = getChunkData(world.provider.getDimension(), offset);
                        ores.removeIf(TileEntity::isInvalid);
                        for (GT_TileEntity_Ores oresTile : ores) {
                            packet_ores.addPos(oresTile);
                        }
                    }
                }
                if (!packet_ores.isEmpty()) {
                    GT_Values.NW.sendTo(packet_ores, (EntityPlayerMP) entityPlayer);
                }
            }
        }*/
    }


    public static class ChunkCoordIntPair {

        public final int coordX;
        public final int coordZ;

        public ChunkCoordIntPair(BlockPos pos) {
            this(pos.getX() >> 4, pos.getZ() >> 4);
        }

        public ChunkCoordIntPair(int coordX, int coordZ) {
            this.coordX = coordX;
            this.coordZ = coordZ;
        }

        public ChunkCoordIntPair offset(int x, int z) {
            if(x == 0 && z == 0) return this;
            return new ChunkCoordIntPair(coordX + x, coordZ + z);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ChunkCoordIntPair)) return false;

            ChunkCoordIntPair that = (ChunkCoordIntPair) o;

            if (coordX != that.coordX) return false;
            if (coordZ != that.coordZ) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = coordX;
            result = 31 * result + coordZ;
            return result;
        }

    }

}
