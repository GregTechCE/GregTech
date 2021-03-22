package gregtech.common.datafix.fixes;

import gregtech.common.datafix.WorldDataHooks;
import gregtech.common.datafix.util.DataFixHelper;
import gregtech.common.datafix.util.RemappedBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

public class Fix1MetaBlockIdSystemInWorld implements IFixableData {

    @Override
    public int getFixVersion() {
        return 1;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        DataFixHelper.rewriteBlocks(compound, (id, data) -> {
            int index = WorldDataHooks.getOldCompressedIndex(id);
            if (index != -1) {
                RemappedBlock remapped = Fix1MetaBlockIdSystem.remapCompressed(index, data);
                return new RemappedBlock(WorldDataHooks.getNewCompressedId(remapped.id), remapped.data);
            }
            index = WorldDataHooks.getOldSurfaceRockIndex(id);
            if (index != -1) {
                RemappedBlock remapped = Fix1MetaBlockIdSystem.remapSurfaceRock(index, data);
                return new RemappedBlock(WorldDataHooks.getNewSurfaceRockId(remapped.id), remapped.data);
            }
            return null;
        });
        return compound;
    }

}
