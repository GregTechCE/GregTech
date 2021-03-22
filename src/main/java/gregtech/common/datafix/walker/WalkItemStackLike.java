package gregtech.common.datafix.walker;

import gregtech.common.datafix.util.DataFixHelper;
import gregtech.common.datafix.GregTechFixType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraftforge.common.util.Constants;

public class WalkItemStackLike implements IDataWalker {

    @Override
    public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn) {
        DataFixHelper.rewriteCompoundTags(compound, tag -> {
            if (tag.hasKey("id", Constants.NBT.TAG_STRING) && tag.hasKey("Count", Constants.NBT.TAG_BYTE)
                    && tag.hasKey("Damage", Constants.NBT.TAG_SHORT)) {
                return fixer.process(GregTechFixType.ITEM_STACK_LIKE, tag, versionIn);
            }
            return null;
        });
        return compound;
    }

}
