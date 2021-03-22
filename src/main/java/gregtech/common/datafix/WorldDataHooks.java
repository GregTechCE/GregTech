package gregtech.common.datafix;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gregtech.api.GTValues;
import gregtech.api.util.GTLog;
import gregtech.api.util.Version;
import net.minecraft.block.Block;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class WorldDataHooks {

    private static final String MAP_STORAGE_NAME = "gregtech_data";
    private static final String KEY_FALLBACK_DATA_VERSION = "FallbackDataVersion";

    private static final Version V1_10_5 = new Version(1, 10, 5); // granite was added in 1.10.5
    private static final Version V1_12_2 = new Version(1, 12, 2); // meta block id alloc was changed in 1.12.2

    private static int gtFallbackVersion = -1;
    private static final TIntIntMap oldIdMapCompressed = new TIntIntHashMap(32, 0.8F, -1, -1);
    private static final TIntIntMap oldIdMapCompressedInv = new TIntIntHashMap(32, 0.8F, -1, -1);
    private static final TIntIntMap newIdMapCompressed = new TIntIntHashMap(64, 1F, -1, -1);
    private static final TIntIntMap oldIdMapSurfaceRock = new TIntIntHashMap(4, 0.8F, -1, -1);
    private static final TIntIntMap newIdMapSurfaceRock = new TIntIntHashMap(64, 1F, -1, -1);

    public static void onWorldLoad(SaveHandler saveHandler, NBTTagCompound levelTag) {
        gtFallbackVersion = -2;
        oldIdMapCompressed.clear();
        oldIdMapCompressedInv.clear();
        newIdMapCompressed.clear();
        oldIdMapSurfaceRock.clear();
        newIdMapSurfaceRock.clear();

        // read from gt world-saved data
        File mapStorageFile = saveHandler.getMapFileFromName(MAP_STORAGE_NAME);
        NBTTagCompound wsdDataTag = null;
        if (mapStorageFile.exists()) {
            try (FileInputStream mapStorageIn = new FileInputStream(mapStorageFile)) {
                wsdDataTag = CompressedStreamTools.readCompressed(mapStorageIn).getCompoundTag("data");
                if (wsdDataTag.hasKey(KEY_FALLBACK_DATA_VERSION, Constants.NBT.TAG_INT)) {
                    gtFallbackVersion = wsdDataTag.getInteger(KEY_FALLBACK_DATA_VERSION);
                    GTLog.logger.info("Using fallback data version {} from WSD", gtFallbackVersion);
                }
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read GregTech world-saved data!", e);
            }
        }

        // that failing, infer from previously-saved map version
        if (gtFallbackVersion < -1 && levelTag.hasKey("FML", Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound fmlTag = levelTag.getCompoundTag("FML");
            if (fmlTag.hasKey("ModList", Constants.NBT.TAG_LIST)) {
                NBTTagList modListTag = fmlTag.getTagList("ModList", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < modListTag.tagCount(); i++) {
                    NBTTagCompound modEntryTag = modListTag.getCompoundTagAt(i);
                    if (modEntryTag.getString("ModId").equals(GTValues.MODID)) {
                        Version version = Version.parse(modEntryTag.getString("ModVersion"));
                        if (version.compareTo(V1_10_5) < 0) {
                            gtFallbackVersion = -1;
                        } else if (version.compareTo(V1_12_2) < 0) {
                            gtFallbackVersion = 0;
                        } else {
                            gtFallbackVersion = 1;
                        }
                        GTLog.logger.info("Using fallback data version {} from previous GregTech version {}",
                                gtFallbackVersion, version);
                    }
                }
            }
        }

        // that failing, assume world has never been played with gt before, so we're already up-to-date
        if (gtFallbackVersion < -1) {
            gtFallbackVersion = 1;
            GTLog.logger.info("Using fallback data version 1 by default");
        }

        // write the calculated version to world-saved data
        if (wsdDataTag == null) {
            wsdDataTag = new NBTTagCompound();
        }
        wsdDataTag.setInteger(KEY_FALLBACK_DATA_VERSION, gtFallbackVersion);
        NBTTagCompound wsdTag = new NBTTagCompound();
        wsdTag.setTag("data", wsdDataTag);
        try (FileOutputStream mapStorageOut = new FileOutputStream(mapStorageFile)) {
            CompressedStreamTools.writeCompressed(wsdTag, mapStorageOut);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write GregTech world-saved data!", e);
        }
    }

    public static int getFallbackModVersion(String modId) {
        return modId.equals(GTValues.MODID) ? gtFallbackVersion : -1;
    }

    public static void addOldCompressedId(int id, int index) {
        oldIdMapCompressed.put(id, index);
        oldIdMapCompressedInv.put(index, id);
    }

    public static int getOldCompressedIndex(int id) {
        return oldIdMapCompressed.get(id);
    }

    public static int getOldCompressedId(int index) {
        return oldIdMapCompressedInv.get(index);
    }

    public static int getNewCompressedId(int index) {
        int id = newIdMapCompressed.get(index);
        if (id == -1) {
            id = Block.getIdFromBlock(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(
                    new ResourceLocation(GTValues.MODID, "meta_block_compressed_" + index))));
            newIdMapCompressed.put(index, id);
        }
        return id;
    }

    public static void addOldSurfaceRockId(int id, int index) {
        oldIdMapSurfaceRock.put(id, index);
    }

    public static int getOldSurfaceRockIndex(int id) {
        return oldIdMapSurfaceRock.get(id);
    }

    public static int getNewSurfaceRockId(int index) {
        int id = newIdMapSurfaceRock.get(index);
        if (id == -1) {
            id = Block.getIdFromBlock(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(
                    new ResourceLocation(GTValues.MODID, "meta_block_surface_rock_" + index))));
            newIdMapSurfaceRock.put(index, id);
        }
        return id;
    }

}
