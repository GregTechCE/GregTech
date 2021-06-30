package gregtech.common.datafix.fixes.metablockid;

import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gregtech.api.GTValues;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class MetaBlockIdRemapCache {

    private static final String KEY_ID_MAPPING = "IdMapping";
    private static final int SER_MASK_ID = 0xFFFFFFC0, SER_MASK_INDEX = 0x0000003F;

    private final String newNamePrefix;
    private final TIntIntMap idToIndex, indexToId;
    // gregtech registry IDs are capped at 999, so we can't have more than 63 * 16 = 1008 entries
    private final int[] newIdCache = new int[63];

    private MetaBlockIdRemapCache(String newNamePrefix, TIntIntMap idToIndex, TIntIntMap indexToId) {
        this.newNamePrefix = newNamePrefix;
        this.idToIndex = idToIndex;
        this.indexToId = indexToId;
        Arrays.fill(newIdCache, -1);
    }

    public int getOldIndex(int id) {
        return idToIndex.get(id);
    }

    public int getOldId(int index) {
        return indexToId.get(index);
    }

    public int getNewId(int index) {
        int id = newIdCache[index];
        if (id != -1) {
            return id;
        }
        newIdCache[index] = Block.getIdFromBlock(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(
                new ResourceLocation(GTValues.MODID, newNamePrefix + index))));

        return newIdCache[index];
    }

    public NBTTagCompound serialize() {
        NBTTagCompound tag = new NBTTagCompound();

        // only need to serialize the old id/index mappings, since newNamePrefix is a constant
        int[] idMapSer = new int[idToIndex.size()];
        int entryNdx = 0;
        TIntIntIterator iter = idToIndex.iterator();
        while (iter.hasNext()) {
            iter.advance();
            // 26-bit block ID, 6-bit meta block index
            idMapSer[entryNdx++] = ((iter.key() << 6) & SER_MASK_ID) | (iter.value() & SER_MASK_INDEX);
        }
        tag.setIntArray(KEY_ID_MAPPING, idMapSer);

        return tag;
    }

    public static MetaBlockIdRemapCache deserialize(String newNamePrefix, NBTTagCompound tag) {
        int[] idMapSer = tag.getIntArray(KEY_ID_MAPPING);
        TIntIntMap idToIndex = new TIntIntHashMap(idMapSer.length, 1.1F, -1, -1);
        TIntIntMap indexToId = new TIntIntHashMap(idMapSer.length, 1.1F, -1, -1);
        for (int entrySer : idMapSer) {
            // 26-bit block ID, 6-bit meta block index
            int id = (entrySer & SER_MASK_ID) >>> 6;
            int index = entrySer & SER_MASK_INDEX;
            idToIndex.put(id, index);
            indexToId.put(index, id);
        }
        return new MetaBlockIdRemapCache(newNamePrefix, idToIndex, indexToId);
    }

    public static MetaBlockIdRemapCache[] generate(NBTTagList regEntryListTag, Spec... metaBlockSpecs) {
        // array for collecting id/index mappings
        MappingCollection[] mappings = new MappingCollection[metaBlockSpecs.length];
        for (int specNdx = 0; specNdx < metaBlockSpecs.length; specNdx++) {
            mappings[specNdx] = new MappingCollection();
        }

        // iterate registry and populate mappings
        int maxBlockId = -1;
        for (int i = 0; i < regEntryListTag.tagCount(); i++) {
            NBTTagCompound regEntryTag = regEntryListTag.getCompoundTagAt(i);
            int id = regEntryTag.getInteger("V");
            if (id > maxBlockId) {
                maxBlockId = id;
            }
            for (int specNdx = 0; specNdx < metaBlockSpecs.length; specNdx++) {
                int index = metaBlockSpecs[specNdx].indexParser.applyAsInt(regEntryTag.getString("K"));
                if (index != -1) {
                    MappingCollection mapping = mappings[specNdx];
                    mapping.idToIndex.put(id, index);
                    mapping.indexToId.put(index, id);
                    if (index > mapping.maxIndex) {
                        mapping.maxIndex = index;
                    }
                    break; // any one block can only be part of one meta block
                }
            }
        }

        // add dummy entry for the max meta block index + 1, in case the addition of an ID overflows existing indices
        // necessary for mapping compressed blocks from pre-granite to post, for example
        for (int specNdx = 0; specNdx < metaBlockSpecs.length; specNdx++) {
            if (metaBlockSpecs[specNdx].addAdditionalIndex) {
                ++maxBlockId;
                MappingCollection mapping = mappings[specNdx];
                mapping.idToIndex.put(maxBlockId, mapping.maxIndex + 1);
                mapping.indexToId.put(mapping.maxIndex + 1, maxBlockId);
            }
        }

        // bake mappings into MetaBlockIdRemapCache instances
        MetaBlockIdRemapCache[] result = new MetaBlockIdRemapCache[mappings.length];
        for (int specNdx = 0; specNdx < metaBlockSpecs.length; specNdx++) {
            result[specNdx] = mappings[specNdx].bake(metaBlockSpecs[specNdx].newNamePrefix);
        }
        return result;
    }

    public static class Spec {

        final String newNamePrefix;
        final ToIntFunction<String> indexParser;
        final boolean addAdditionalIndex;

        public Spec(String newNamePrefix, ToIntFunction<String> indexParser, boolean addAdditionalIndex) {
            this.newNamePrefix = newNamePrefix;
            this.indexParser = indexParser;
            this.addAdditionalIndex = addAdditionalIndex;
        }

    }

    private static class MappingCollection {

        final TIntIntMap idToIndex = new TIntIntHashMap(16, 0.8F, -1, -1);
        final TIntIntMap indexToId = new TIntIntHashMap(16, 0.8F, -1, -1);
        int maxIndex = -1;

        MetaBlockIdRemapCache bake(String newNamePrefix) {
            return new MetaBlockIdRemapCache(newNamePrefix, idToIndex, indexToId);
        }

    }

}
