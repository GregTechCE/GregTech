package gregtech.common.datafix.fixes.metablockid;

import com.google.common.collect.ImmutableList;
import gregtech.api.GTValues;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.Version;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class MetaBlockIdFixHelper {

    private MetaBlockIdFixHelper() {
    }

    public static final Version V1_10_5 = new Version(1, 10, 5); // granite was added in 1.10.5
    public static final Version V1_15_0 = new Version(1, 15, 0); // meta block id alloc was changed in 1.15.0

    public static final String KEY_FALLBACK_VERSION = "FallbackVersion";

    public static final String COMP_NAME_PREF = "compressed_";
    public static final String COMP_RESLOC_PREF = GTValues.MODID + ":" + COMP_NAME_PREF;
    public static final int COMP_RESLOC_PREF_LEN = COMP_RESLOC_PREF.length();
    public static final String COMP_NAME_PREF_NEW = "meta_block_compressed_";
    public static final String COMP_RESLOC_PREF_NEW = GTValues.MODID + ":" + COMP_NAME_PREF_NEW;

    public static int getCompressedIndexFromResLoc(String resLoc) {
        if (resLoc.startsWith(COMP_RESLOC_PREF)) {
            try {
                return Integer.parseInt(resLoc.substring(COMP_RESLOC_PREF_LEN));
            } catch (NumberFormatException ignored) {
                //Left empty on purpose
            }
        }
        return -1;
    }

    public static final String SURF_ROCK_NAME_PREF = "surface_rock_";
    public static final String SURF_ROCK_RESLOC_PREF = GTValues.MODID + ":" + SURF_ROCK_NAME_PREF;
    public static final int SURF_ROCK_RESLOC_PREF_LEN = SURF_ROCK_RESLOC_PREF.length();
    public static final String SURF_ROCK_NAME_PREF_NEW = "meta_block_surface_rock_";
    public static final String SURF_ROCK_RESLOC_PREF_NEW = GTValues.MODID + ":" + SURF_ROCK_NAME_PREF_NEW;

    public static int getSurfRockIndexFromResLoc(String resLoc) {
        if (resLoc.startsWith(SURF_ROCK_RESLOC_PREF)) {
            try {
                return Integer.parseInt(resLoc.substring(SURF_ROCK_RESLOC_PREF_LEN));
            } catch (NumberFormatException ignored) {
                //Left empty on purpose
            }
        }
        return -1;
    }

    public static List<int[]> collectOldMetaBlockAlloc(Predicate<Material> filter) {
        ImmutableList.Builder<int[]> builder = ImmutableList.builder();
        int[] buffer = new int[16];
        Arrays.fill(buffer, -1);
        int bufferPtr = 0;
        for (Material material : Material.MATERIAL_REGISTRY) {
            if (filter.test(material)) {
                buffer[bufferPtr++] = Material.MATERIAL_REGISTRY.getIDForObject(material);
                if (bufferPtr >= 16) {
                    builder.add(buffer);
                    buffer = new int[16];
                    Arrays.fill(buffer, -1);
                    bufferPtr = 0;
                }
            }
        }
        if (bufferPtr > 0) {
            builder.add(buffer);
        }
        return builder.build();
    }

    @Nullable
    public static NBTTagList getBlockRegistryTag(NBTTagCompound fmlTag) {
        if (fmlTag.hasKey("Registries", Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound tag = fmlTag.getCompoundTag("Registries");
            if (tag.hasKey("minecraft:blocks", Constants.NBT.TAG_COMPOUND)) {
                tag = tag.getCompoundTag("minecraft:blocks");
                if (tag.hasKey("ids", Constants.NBT.TAG_LIST)) {
                    return tag.getTagList("ids", Constants.NBT.TAG_COMPOUND);
                }
            }
        }
        return null;
    }

}
