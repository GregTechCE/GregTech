package gregtech.common.datafix.fixes.metablockid;

import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.datafix.GregTechDataFixers;
import gregtech.common.datafix.util.RemappedBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nullable;
import java.util.List;

public class PostGraniteMetaBlockIdFixer implements MetaBlockIdFixer {

    protected static final String KEY_REMAP_CACHE_COMPRESSED = "RemapCacheCompressed";
    protected static final String KEY_REMAP_CACHE_SURF_ROCK = "RemapCacheSurfaceRock";

    @Nullable
    private static List<int[]> tableOldAllocCompressed = null;
    @Nullable
    private static List<int[]> tableOldAllocSurfRock = null;

    protected static List<int[]> getTableOldAllocCompressed() {
        if (tableOldAllocCompressed == null) {
            tableOldAllocCompressed = MetaBlockIdFixHelper.collectOldMetaBlockAlloc(
                    mat -> mat instanceof DustMaterial && !OrePrefix.block.isIgnored(mat));
        }
        return tableOldAllocCompressed;
    }

    protected static List<int[]> getTableOldAllocSurfRock() {
        if (tableOldAllocSurfRock == null) {
            tableOldAllocSurfRock = MetaBlockIdFixHelper.collectOldMetaBlockAlloc(
                    mat -> mat instanceof IngotMaterial && mat.hasFlag(DustMaterial.MatFlags.GENERATE_ORE));
        }
        return tableOldAllocSurfRock;
    }

    private final MetaBlockIdRemapCache remapCacheCompressed;
    private final MetaBlockIdRemapCache remapCacheSurfRock;

    public PostGraniteMetaBlockIdFixer(MetaBlockIdRemapCache remapCacheCompressed,
                                       MetaBlockIdRemapCache remapCacheSurfRock) {
        this.remapCacheCompressed = remapCacheCompressed;
        this.remapCacheSurfRock = remapCacheSurfRock;
    }

    public static PostGraniteMetaBlockIdFixer generate(NBTTagCompound fmlTag) {
        NBTTagList blockRegistryTag = MetaBlockIdFixHelper.getBlockRegistryTag(fmlTag);
        if (blockRegistryTag == null) {
            throw new IllegalStateException("Block registry is not serialized in level data!");
        }
        MetaBlockIdRemapCache[] remapCaches = MetaBlockIdRemapCache.generate(blockRegistryTag,
                new MetaBlockIdRemapCache.Spec(
                        MetaBlockIdFixHelper.COMP_NAME_PREF_NEW, MetaBlockIdFixHelper::getCompressedIndexFromResLoc, false),
                new MetaBlockIdRemapCache.Spec(
                        MetaBlockIdFixHelper.SURF_ROCK_NAME_PREF_NEW, MetaBlockIdFixHelper::getSurfRockIndexFromResLoc, false));
        return new PostGraniteMetaBlockIdFixer(remapCaches[0], remapCaches[1]);
    }

    @Override
    public int getFallbackDataVersion() {
        return GregTechDataFixers.V0_POST_GRANITE;
    }

    public MetaBlockIdRemapCache getRemapCacheCompressed() {
        return remapCacheCompressed;
    }

    public MetaBlockIdRemapCache getRemapCacheSurfRock() {
        return remapCacheSurfRock;
    }

    public RemappedBlock remapCompressedPostGraniteToNew(int index, int data) {
        int matId = getTableOldAllocCompressed().get(index)[data];
        return new RemappedBlock(matId / 16, (short) (matId % 16));
    }

    public RemappedBlock remapSurfRockToNew(int index, int data) {
        int matId = getTableOldAllocSurfRock().get(index)[data];
        return new RemappedBlock(matId / 16, (short) (matId % 16));
    }

    @Override
    public NBTTagCompound serialize() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(MetaBlockIdFixHelper.KEY_FALLBACK_VERSION, 0);
        tag.setTag(KEY_REMAP_CACHE_COMPRESSED, remapCacheCompressed.serialize());
        tag.setTag(KEY_REMAP_CACHE_SURF_ROCK, remapCacheSurfRock.serialize());
        return tag;
    }

    public static PostGraniteMetaBlockIdFixer deserialize(NBTTagCompound tag) {
        return new PostGraniteMetaBlockIdFixer(
                MetaBlockIdRemapCache.deserialize(
                        MetaBlockIdFixHelper.COMP_NAME_PREF_NEW, tag.getCompoundTag(KEY_REMAP_CACHE_COMPRESSED)),
                MetaBlockIdRemapCache.deserialize(
                        MetaBlockIdFixHelper.SURF_ROCK_NAME_PREF_NEW, tag.getCompoundTag(KEY_REMAP_CACHE_SURF_ROCK)));
    }

}
