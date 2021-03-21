package gregtech.common.datafix;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ListIterator;

public class DataFixHelper {

    public static void rewriteCompoundTags(NBTTagCompound tag, CompoundRewriter rewriter) {
        for (String key : tag.getKeySet()) { // may want to use reflection to grab the entry set for efficiency?
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
        ListIterator<NBTBase> iter = tag.tagList.listIterator();
        while (iter.hasNext()) {
            NBTBase childTag = iter.next();
            switch (childTag.getId()) {
                case Constants.NBT.TAG_LIST:
                    rewriteCompoundTags((NBTTagList) childTag, rewriter);
                    break;
                case Constants.NBT.TAG_COMPOUND:
                    NBTTagCompound childTagCompound = (NBTTagCompound) childTag;
                    rewriteCompoundTags(childTagCompound, rewriter);
                    childTagCompound = rewriter.rewrite(childTagCompound);
                    if (childTagCompound != null) {
                        iter.set(childTagCompound);
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

}
