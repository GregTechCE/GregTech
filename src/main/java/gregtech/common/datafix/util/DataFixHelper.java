package gregtech.common.datafix.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class DataFixHelper {

    public static void rewriteCompoundTags(NBTTagCompound tag, CompoundRewriter rewriter) {
        for (String key : tag.getKeySet()) {
            NBTBase childTag = tag.getTag(key);
            switch (childTag.getId()) {
                case Constants.NBT.TAG_LIST:
                    rewriteCompoundTags((NBTTagList) childTag, rewriter);
                    break;
                case Constants.NBT.TAG_COMPOUND:
                    NBTTagCompound childTagCompound = (NBTTagCompound) childTag;
                    rewriteCompoundTags(childTagCompound, rewriter);
                    childTagCompound = rewriter.rewrite(childTagCompound);
                    if (childTagCompound != null) {
                        tag.setTag(key, childTagCompound);
                    }
                    break;
            }
        }
    }

    public static void rewriteCompoundTags(NBTTagList tag, CompoundRewriter rewriter) {
        for (int i = 0; i < tag.tagCount(); i++) {
            NBTBase childTag = tag.get(i);
            switch (childTag.getId()) {
                case Constants.NBT.TAG_LIST:
                    rewriteCompoundTags((NBTTagList) childTag, rewriter);
                    break;
                case Constants.NBT.TAG_COMPOUND:
                    NBTTagCompound childTagCompound = (NBTTagCompound) childTag;
                    rewriteCompoundTags(childTagCompound, rewriter);
                    childTagCompound = rewriter.rewrite(childTagCompound);
                    if (childTagCompound != null) {
                        tag.set(i, childTagCompound);
                    }
                    break;
            }
        }
    }

    @FunctionalInterface
    public interface CompoundRewriter {

        @Nullable
        NBTTagCompound rewrite(NBTTagCompound tag);

    }

    public static void rewriteBlocks(NBTTagCompound chunkSectionTag, BlockRewriter rewriter) {
        byte[] blockIds = chunkSectionTag.getByteArray("Blocks");
        NibbleArray blockData = new NibbleArray(chunkSectionTag.getByteArray("Data"));
        NibbleArray extendedIds = chunkSectionTag.hasKey("Add", Constants.NBT.TAG_BYTE_ARRAY)
                ? new NibbleArray(chunkSectionTag.getByteArray("Add")) : null;
        for (int i = 0; i < 4096; ++i) {
            int x = i & 0x0F, y = i >> 8 & 0x0F, z = i >> 4 & 0x0F;
            int id = extendedIds == null ? (blockIds[i] & 0xFF)
                    : ((blockIds[i] & 0xFF) | (extendedIds.get(x, y, z) << 8));
            RemappedBlock remapped = rewriter.rewrite(id, (short) blockData.get(x, y, z));
            if (remapped != null) {
                blockIds[i] = (byte) (remapped.id & 0xFF);
                int idExt = (remapped.id >> 8) & 0x0F;
                if (idExt != 0) {
                    if (extendedIds == null) {
                        extendedIds = new NibbleArray();
                    }
                    extendedIds.set(x, y, z, idExt);
                }
                blockData.set(x, y, z, remapped.data & 0x0F);
            }
        }
        if (extendedIds != null) {
            chunkSectionTag.setByteArray("Add", extendedIds.getData());
        }
    }

    @FunctionalInterface
    public interface BlockRewriter {

        @Nullable
        RemappedBlock rewrite(int id, short data);

    }

}
