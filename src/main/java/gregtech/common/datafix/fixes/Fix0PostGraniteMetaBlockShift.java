package gregtech.common.datafix.fixes;

import gregtech.common.datafix.fixes.metablockid.MetaBlockIdFixHelper;
import gregtech.common.datafix.fixes.metablockid.PreGraniteMetaBlockIdFixer;
import gregtech.common.datafix.fixes.metablockid.WorldDataHooks;
import gregtech.common.datafix.util.RemappedBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class Fix0PostGraniteMetaBlockShift implements IFixableData {

    @Override
    public int getFixVersion() {
        return 0;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        if (!WorldDataHooks.isFixerAvailable()) {
            return compound;
        }

        int index = MetaBlockIdFixHelper.getCompressedIndexFromResLoc(compound.getString("id"));
        if (index != -1) {
            RemappedBlock remapped = ((PreGraniteMetaBlockIdFixer) WorldDataHooks.getMetaBlockIdFixer())
                    .remapCompressedPreGraniteToPost(index, compound.getShort("Damage"));
            if (remapped != null) {
                compound.setString("id", MetaBlockIdFixHelper.COMP_RESLOC_PREF + remapped.id);
                compound.setShort("Damage", remapped.data);
            }
        }
        return compound;
    }

}
