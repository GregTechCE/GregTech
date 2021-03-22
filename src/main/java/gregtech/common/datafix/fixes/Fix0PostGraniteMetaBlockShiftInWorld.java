package gregtech.common.datafix.fixes;

import gregtech.common.datafix.WorldDataHooks;
import gregtech.common.datafix.util.DataFixHelper;
import gregtech.common.datafix.util.RemappedBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraftforge.common.util.Constants;

public class Fix0PostGraniteMetaBlockShiftInWorld implements IFixableData {

    @Override
    public int getFixVersion() {
        return 0;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        DataFixHelper.rewriteBlocks(compound, (id, data) -> {
            int index = WorldDataHooks.getOldCompressedIndex(id);
            if (index == -1) {
                return null;
            }
            RemappedBlock remapped = Fix0PostGraniteMetaBlockShift.remap(index, data);
            if (remapped == null) {
                return null;
            }
            return new RemappedBlock(WorldDataHooks.getOldCompressedId(remapped.id), remapped.data);
        });
        return compound;
    }

}
