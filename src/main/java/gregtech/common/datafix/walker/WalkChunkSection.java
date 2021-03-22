package gregtech.common.datafix.walker;

import gregtech.common.datafix.GregTechFixType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraftforge.common.util.Constants;

public class WalkChunkSection implements IDataWalker {

    @Override
    public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
        if (compound.hasKey("Level", Constants.NBT.TAG_COMPOUND)) {
            NBTTagCompound levelTag = compound.getCompoundTag("Level");
            if (levelTag.hasKey("Sections", Constants.NBT.TAG_LIST)) {
                NBTTagList sectionListTag = levelTag.getTagList("Sections", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < sectionListTag.tagCount(); i++) {
                    sectionListTag.set(i, fixer.process(
                            GregTechFixType.CHUNK_SECTION, sectionListTag.getCompoundTagAt(i), versionIn));
                }
            }
        }
        return compound;
    }

}
