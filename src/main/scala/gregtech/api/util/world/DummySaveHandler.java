package gregtech.api.util.world;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nullable;
import java.io.File;

public class DummySaveHandler implements ISaveHandler, IPlayerFileData, IChunkLoader {

    @Nullable
    @Override
    public WorldInfo loadWorldInfo() {
        return null;
    }

    @Override
    public void checkSessionLock() {
    }

    @Override
    public IChunkLoader getChunkLoader(WorldProvider provider) {
        return this;
    }

    @Override
    public IPlayerFileData getPlayerNBTManager() {
        return this;
    }


    @Override
    public TemplateManager getStructureTemplateManager() {
        return new TemplateManager("", new DataFixer(0));
    }

    @Override
    public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {
    }

    @Override
    public void saveWorldInfo(WorldInfo worldInformation) {
    }

    @Override
    public File getWorldDirectory() {
        return null;
    }

    @Override
    public File getMapFileFromName(String mapName) {
        return null;
    }


    @Nullable
    @Override
    public Chunk loadChunk(World worldIn, int x, int z) {
        return null;
    }

    @Override
    public void saveChunk(World worldIn, Chunk chunkIn) {
    }

    @Override
    public void saveExtraChunkData(World worldIn, Chunk chunkIn) {
    }

    @Override
    public void chunkTick() {
    }

    @Override
    public void flush() {
    }

    @Override
    public boolean isChunkGeneratedAt(int x, int z) {
        return false;
    }

    @Override
    public void writePlayerData(EntityPlayer player) {
    }

    @Nullable
    @Override
    public NBTTagCompound readPlayerData(EntityPlayer player) {
        return null;
    }

    @Override
    public String[] getAvailablePlayerDat() {
        return new String[0];
    }
}
