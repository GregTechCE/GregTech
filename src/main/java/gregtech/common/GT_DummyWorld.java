package gregtech.common;

//import gregtech.common.tools.ToolBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public class GT_DummyWorld extends World {

    public static WorldSettings DUMMY_SETTINGS =
            new WorldSettings(1L, GameType.SURVIVAL, true, false, WorldType.DEFAULT);

    public static WorldInfo DUMMY_INFO = new WorldInfo(DUMMY_SETTINGS, "DUMMY_DIMENSION");

    //public GT_IteratorRandom mRandom = new GT_IteratorRandom();
    public ItemStack mLastSetBlock = null;

    public GT_DummyWorld(ISaveHandler par1iSaveHandler, String par2Str, WorldSettings par4WorldSettings, Profiler par5Profiler) {
        super(par1iSaveHandler, par1iSaveHandler.loadWorldInfo(), new WorldProviderSurface(), par5Profiler, false);
        //this.rand = this.mRandom;
    }

    public GT_DummyWorld() {
        this(new ISaveHandler() {

                 @Override
                 public WorldInfo loadWorldInfo() {
                     return DUMMY_INFO;
                 }

                 @Override
                 public void checkSessionLock() throws MinecraftException {}

                 @Override
                 public IChunkLoader getChunkLoader(WorldProvider provider) {
                     return new IChunkLoader() {
                         @Nullable
                         @Override
                         public Chunk loadChunk(World worldIn, int x, int z) throws IOException {
                             return null;
                         }

                         @Override
                         public void saveChunk(World worldIn, Chunk chunkIn) throws MinecraftException, IOException {

                         }

                         @Override
                         public void saveExtraChunkData(World worldIn, Chunk chunkIn) throws IOException {

                         }

                         @Override
                         public void chunkTick() {

                         }

                         @Override
                         public void saveExtraData() {

                         }
                     };
                 }

                 @Override
                 public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {}

                 @Override
                 public void saveWorldInfo(WorldInfo worldInformation) {}

                 @Override
                 public IPlayerFileData getPlayerNBTManager() {
                     return new IPlayerFileData() {
                         @Override
                         public void writePlayerData(EntityPlayer player) {}

                         @Override
                         public NBTTagCompound readPlayerData(EntityPlayer player) {
                             return new NBTTagCompound();
                         }

                         @Override
                         public String[] getAvailablePlayerDat() {
                             return new String[0];
                         }
                     };
                 }

                 @Override
                 public void flush() {}

                 @Override
                 public File getWorldDirectory() {
                     return null;
                 }

                 @Override
                 public File getMapFileFromName(String mapName) {
                     return null;
                 }

                 @Override
                 public TemplateManager getStructureTemplateManager() {
                     return null;
                 }
             }, DUMMY_INFO.getWorldName(), DUMMY_SETTINGS, new Profiler());
    }

    protected IChunkProvider createChunkProvider() {
        return new IChunkProvider() {
            @Nullable
            @Override
            public Chunk getLoadedChunk(int x, int z) {
                return null;
            }

            @Override
            public Chunk provideChunk(int x, int z) {
                return null;
            }

            @Override
            public boolean unloadQueuedChunks() {
                return false;
            }

            @Override
            public String makeString() {
                return "Dummy";
            }
        };
    }

    @Override
    protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
        return false;
    }

    @Override
    public Entity getEntityByID(int aEntityID) {
        return null;
    }

    @Override
    public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
//        this.mLastSetBlock = ToolBase.getBlockStack(newState);
        return true;
    }

    @Override
    public float getSunBrightness(float par1) {
        return 1.0F;
    }

    @Override
    public Biome getBiomeForCoordsBody(BlockPos pos) {
        return getBiome(pos);
    }

    @Override
    public Biome getBiome(BlockPos pos) {
        return Biomes.PLAINS;
    }

    @Override
    public int getLight(BlockPos pos) {
        return 10;
    }

    @Override
    public int getCombinedLight(BlockPos pos, int lightValue) {
        return 10;
    }

    @Override
    public IBlockState getBlockState(BlockPos pos) {
        return Blocks.AIR.getDefaultState();
    }

    @Nullable
    @Override
    public TileEntity getTileEntity(BlockPos pos) {
        return null;
    }

    @Override
    public void setTileEntity(BlockPos pos, @Nullable TileEntity tileEntityIn) {}

    @Override
    public void removeTileEntity(BlockPos pos) {}

    @Override
    public boolean addTileEntity(TileEntity tile) {
        return true;
    }

    @Override
    public boolean canBlockSeeSky(BlockPos pos) {
        return true;
    }


}
