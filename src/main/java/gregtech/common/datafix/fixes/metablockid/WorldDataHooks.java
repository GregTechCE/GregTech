package gregtech.common.datafix.fixes.metablockid;

import gregtech.GregTechVersion;
import gregtech.api.GTValues;
import gregtech.api.util.GTLog;
import gregtech.api.util.Version;
import gregtech.common.datafix.GregTechDataFixers;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.StartupQuery;
import net.minecraftforge.fml.common.ZipperUtil;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class WorldDataHooks {

    private static final String MAP_STORAGE_NAME = "gregtech_data";
    private static final String KEY_META_BLOCK_ID_FIXER = "MetaBlockIdFixer";

    @Nullable
    private static MetaBlockIdFixer metaBlockIdFixer = null;

    private WorldDataHooks() {
    }

    public static boolean isFixerUnavailable() {
        return metaBlockIdFixer == null;
    }

    public static MetaBlockIdFixer getMetaBlockIdFixer() {
        return Objects.requireNonNull(metaBlockIdFixer);
    }

    @SuppressWarnings("unused")
    public static void onWorldLoad(SaveHandler saveHandler, NBTTagCompound levelTag) {
        // the meta block ID fix only matters on the server side
        if (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER) {
            return;
        }

        metaBlockIdFixer = null; // we want to get our hands on one of these
        boolean firstTimeFixing = false; // is it the first time running an old world with fixers?

        File mapStorageFile = saveHandler.getMapFileFromName(MAP_STORAGE_NAME);
        NBTTagCompound wsdDataTag = null;
        boolean wsdNeedsWrite = true; // do we need to serialize the fixer instance to the WSD?

        // load fixer instance from gt world-saved data
        if (mapStorageFile.exists()) {
            try (FileInputStream mapStorageIn = new FileInputStream(mapStorageFile)) {
                wsdDataTag = CompressedStreamTools.readCompressed(mapStorageIn).getCompoundTag("data");
                if (wsdDataTag.hasKey(KEY_META_BLOCK_ID_FIXER, Constants.NBT.TAG_COMPOUND)) {
                    metaBlockIdFixer = MetaBlockIdFixer.deserialize(wsdDataTag.getCompoundTag(KEY_META_BLOCK_ID_FIXER));
                    GTLog.logger.info("Using fallback data version {} from WSD", metaBlockIdFixer.getFallbackDataVersion());
                    wsdNeedsWrite = false; // we just loaded it, so there's no need to write it back
                }
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read GregTech world-saved data!", e);
            }
        }

        // that failing, create a new one by reading the level NBT
        if (metaBlockIdFixer == null && levelTag.hasKey("FML", Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound fmlTag = levelTag.getCompoundTag("FML");
            if (fmlTag.hasKey("ModList", Constants.NBT.TAG_LIST)) {
                NBTTagList modListTag = fmlTag.getTagList("ModList", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < modListTag.tagCount(); i++) {
                    NBTTagCompound modEntryTag = modListTag.getCompoundTagAt(i);
                    if (modEntryTag.getString("ModId").equals(GTValues.MODID)) {
                        Version prevSaveVersion = Version.parse(modEntryTag.getString("ModVersion"));
                        metaBlockIdFixer = MetaBlockIdFixer.create(prevSaveVersion, fmlTag);
                        GTLog.logger.info("Using fallback data version {} from previous GregTech version {}",
                                metaBlockIdFixer.getFallbackDataVersion(), prevSaveVersion);
                        if (metaBlockIdFixer.getFallbackDataVersion() < GregTechDataFixers.V1_META_BLOCK_ID_REWORK) {
                            firstTimeFixing = true;
                        }
                    }
                }
            }
        }

        // that failing, assume world has never been played with gt before, so we're already up-to-date
        if (metaBlockIdFixer == null) {
            metaBlockIdFixer = MetaBlockIdFixer.NOOP;
            GTLog.logger.info("Using fallback data version 1 by default");
        }

        // if needed, serialize the fixer instance to world-saved data
        if (wsdNeedsWrite) {
            if (wsdDataTag == null) {
                wsdDataTag = new NBTTagCompound();
            }

            wsdDataTag.setTag(KEY_META_BLOCK_ID_FIXER, metaBlockIdFixer.serialize());
            NBTTagCompound wsdTag = new NBTTagCompound();
            wsdTag.setTag("data", wsdDataTag);
            try (FileOutputStream mapStorageOut = new FileOutputStream(mapStorageFile)) {
                CompressedStreamTools.writeCompressed(wsdTag, mapStorageOut);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to write GregTech world-saved data!", e);
            }
        }

        // prompt to create a backup if it's the first time an old world is being loaded with fixers
        if (firstTimeFixing) {
            promptWorldBackup(metaBlockIdFixer.getFallbackDataVersion());
        }
    }

    public static void promptWorldBackup(int prevDataVersion) {
        String text = "GregTech detected a required registry remapping!\n\n"
                + "Updating from before " + TextFormatting.AQUA + (prevDataVersion == -1 ? MetaBlockIdFixHelper.V1_10_5 : MetaBlockIdFixHelper.V1_15_0) + TextFormatting.RESET
                + " to " + TextFormatting.AQUA + GregTechVersion.VERSION.toString(3) + TextFormatting.RESET + ".\n"
                + "It is " + TextFormatting.UNDERLINE + "strongly" + TextFormatting.RESET + " recommended that you perform a backup. Create backup?";

        if (StartupQuery.confirm(text)) {
            try {
                GTLog.logger.info("Creating world backup before starting registry remapping...");
                ZipperUtil.backupWorld();
            } catch (IOException e) {
                GTLog.logger.error(e);
                StartupQuery.notify("Encountered an error while creating the backup!\n" +
                        "See the game log for more details.");
                StartupQuery.abort();
            }
        } else {
            String reconfirm = "No backup will be created. Proceed with the remapping without a backup?";
            if (!StartupQuery.confirm(reconfirm))
                StartupQuery.abort();
        }
    }

    @SuppressWarnings("unused")
    public static int getFallbackModVersion(String modId) {
        return metaBlockIdFixer != null && modId.equals(GTValues.MODID)
                ? metaBlockIdFixer.getFallbackDataVersion() : -1;
    }

}
