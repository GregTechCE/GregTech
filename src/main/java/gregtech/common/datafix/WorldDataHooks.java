package gregtech.common.datafix;

import gregtech.api.GTValues;
import gregtech.api.util.GTLog;
import gregtech.api.util.Version;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WorldDataHooks {

    private static final String MAP_STORAGE_NAME = "gregtech_data";
    private static final String KEY_FALLBACK_DATA_VERSION = "FallbackDataVersion";

    private static final Version V1_10_5 = new Version(1, 10, 5); // granite was added in 1.10.5
    private static final Version V1_12_2 = new Version(1, 12, 2); // meta block id alloc was changed in 1.12.2

    public static int gtFallbackVersion = -1;

    public static void onWorldLoad(SaveHandler saveHandler, NBTTagCompound levelTag) {
        gtFallbackVersion = -2;

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

}
