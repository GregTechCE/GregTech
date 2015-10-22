package gregtech.common;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import java.io.File;

public class GT_DummyWorld
        extends World {
    public GT_IteratorRandom mRandom = new GT_IteratorRandom();
    public ItemStack mLastSetBlock = null;

    public GT_DummyWorld(ISaveHandler par1iSaveHandler, String par2Str, WorldProvider par3WorldProvider, WorldSettings par4WorldSettings, Profiler par5Profiler) {
        super(par1iSaveHandler, par2Str, par4WorldSettings, par3WorldProvider, par5Profiler);
        this.rand = this.mRandom;
    }

    public GT_DummyWorld() {
        this(new ISaveHandler() {
                 public void saveWorldInfoWithPlayer(WorldInfo var1, NBTTagCompound var2) {
                 }

                 public void saveWorldInfo(WorldInfo var1) {
                 }

                 public WorldInfo loadWorldInfo() {
                     return null;
                 }

                 public IPlayerFileData getSaveHandler() {
                     return null;
                 }

                 public File getMapFileFromName(String var1) {
                     return null;
                 }

                 public IChunkLoader getChunkLoader(WorldProvider var1) {
                     return null;
                 }

                 public void flush() {
                 }

                 public void checkSessionLock() {
                 }

                 public String getWorldDirectoryName() {
                     return null;
                 }

                 public File getWorldDirectory() {
                     return null;
                 }
             }, "DUMMY_DIMENSION", null,
//				new WorldProvider(),
//    
//
//
//      new WorldSettings(new WorldInfo(new NBTTagCompound()))
//      {
//        public String getDimensionName()
//        {
//          return "DUMMY_DIMENSION";
//        }
//      }
                new WorldSettings(new WorldInfo(new NBTTagCompound())), new Profiler());
    }

    protected IChunkProvider createChunkProvider() {
        return null;
    }

    public Entity getEntityByID(int aEntityID) {
        return null;
    }

    public boolean setBlock(int aX, int aY, int aZ, Block aBlock, int aMeta, int aFlags) {
        this.mLastSetBlock = new ItemStack(aBlock, 1, aMeta);
        return true;
    }

    public float getSunBrightnessFactor(float p_72967_1_) {
        return 1.0F;
    }

    public BiomeGenBase getBiomeGenForCoords(int aX, int aZ) {
        if ((aX >= 16) && (aZ >= 16) && (aX < 32) && (aZ < 32)) {
            return BiomeGenBase.plains;
        }
        return BiomeGenBase.ocean;
    }

    public int getFullBlockLightValue(int aX, int aY, int aZ) {
        return 10;
    }

    public Block getBlock(int aX, int aY, int aZ) {
        if ((aX >= 16) && (aZ >= 16) && (aX < 32) && (aZ < 32)) {
            return aY == 64 ? Blocks.grass : Blocks.air;
        }
        return Blocks.air;
    }

    public int getBlockMetadata(int aX, int aY, int aZ) {
        return 0;
    }

    public boolean canBlockSeeTheSky(int aX, int aY, int aZ) {
        if ((aX >= 16) && (aZ >= 16) && (aX < 32) && (aZ < 32)) {
            return aY > 64;
        }
        return true;
    }

    protected int func_152379_p() {
        return 0;
    }
}
