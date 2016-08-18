package gregtech.common;

import gregtech.common.tools.GT_Tool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;
import java.io.File;

public class GT_DummyWorld extends World {

    public static WorldSettings DUMMY_SETTINGS =
            new WorldSettings(1L, GameType.SURVIVAL, true, false, WorldType.DEFAULT);

    public static WorldInfo DUMMY_INFO = new WorldInfo(DUMMY_SETTINGS, "DUMMY_DIMENSION");

    public GT_IteratorRandom mRandom = new GT_IteratorRandom();
    public ItemStack mLastSetBlock = null;

    public GT_DummyWorld(ISaveHandler par1iSaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, Profiler par5Profiler) {
        super(par1iSaveHandler, par1iSaveHandler.loadWorldInfo(), par3WorldProvider, par5Profiler, false);
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
                     return null;
                 }

                 @Override
                 public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {}

                 @Override
                 public void saveWorldInfo(WorldInfo worldInformation) {}

                 @Override
                 public IPlayerFileData getPlayerNBTManager() {
                     return null;
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
             }, DUMMY_INFO.getWorldName(), null, DUMMY_SETTINGS, new Profiler());
    }

    protected IChunkProvider createChunkProvider() {
        return null;
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
        this.mLastSetBlock = GT_Tool.getBlockStack(newState);
        return true;
    }

    @Override
    public float getSunBrightness(float par1) {
        return 1.0F;
    }

    @Override
    public Biome getBiomeForCoordsBody(BlockPos pos) {
        return getBiomeGenForCoords(pos);
    }

    @Override
    public Biome getBiomeGenForCoords(BlockPos pos) {
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
