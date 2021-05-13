package gregtech.common.datafix.fixes.metablockid;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.common.datafix.GregTechDataFixers;
import gregtech.common.datafix.util.RemappedBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nullable;

public class PreGraniteMetaBlockIdFixer extends PostGraniteMetaBlockIdFixer {

    private static final int GRANITE_ID = Material.MATERIAL_REGISTRY.getIDForObject(Materials.Granite);

    public PreGraniteMetaBlockIdFixer(MetaBlockIdRemapCache remapCacheCompressed,
                                      MetaBlockIdRemapCache remapCacheSurfRock) {
        super(remapCacheCompressed, remapCacheSurfRock);
    }

    public static PreGraniteMetaBlockIdFixer generate(NBTTagCompound fmlTag) {
        NBTTagList blockRegistryTag = MetaBlockIdFixHelper.getBlockRegistryTag(fmlTag);
        if (blockRegistryTag == null) {
            throw new IllegalStateException("Block registry is not serialized in level data!");
        }
        MetaBlockIdRemapCache[] remapCaches = MetaBlockIdRemapCache.generate(blockRegistryTag,
                new MetaBlockIdRemapCache.Spec(
                        MetaBlockIdFixHelper.COMP_NAME_PREF_NEW, MetaBlockIdFixHelper::getCompressedIndexFromResLoc, true),
                new MetaBlockIdRemapCache.Spec(
                        MetaBlockIdFixHelper.SURF_ROCK_NAME_PREF_NEW, MetaBlockIdFixHelper::getSurfRockIndexFromResLoc, false));
        return new PreGraniteMetaBlockIdFixer(remapCaches[0], remapCaches[1]);
    }

    @Override
    public int getFallbackDataVersion() {
        return GregTechDataFixers.V_PRE_GRANITE;
    }

    @Nullable
    public RemappedBlock remapCompressedPreGraniteToPost(int index, int data) {
        int matId = getTableOldAllocCompressed().get(index)[data];
        if (matId >= GRANITE_ID) {
            if (data == 15) {
                return new RemappedBlock(index + 1, (short) 0);
            } else {
                return new RemappedBlock(index, (short) (data + 1));
            }
        }
        return null;
    }

    @Override
    public NBTTagCompound serialize() {
        NBTTagCompound tag = super.serialize();
        tag.setInteger(MetaBlockIdFixHelper.KEY_FALLBACK_VERSION, -1);
        return tag;
    }

    public static PreGraniteMetaBlockIdFixer deserialize(NBTTagCompound tag) {
        return new PreGraniteMetaBlockIdFixer(
                MetaBlockIdRemapCache.deserialize(
                        MetaBlockIdFixHelper.COMP_NAME_PREF_NEW, tag.getCompoundTag(KEY_REMAP_CACHE_COMPRESSED)),
                MetaBlockIdRemapCache.deserialize(
                        MetaBlockIdFixHelper.SURF_ROCK_NAME_PREF_NEW, tag.getCompoundTag(KEY_REMAP_CACHE_SURF_ROCK)));
    }

}
