package gregtech.common.datafix.fixes;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.datafix.util.RemappedBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

import javax.annotation.Nullable;

public class Fix0PostGraniteMetaBlockShift implements IFixableData {

    private static final String COMP_BLOCK_PREFIX = GTValues.MODID + ":compressed_";
    private static final int COMP_BLOCK_PREFIX_LEN = COMP_BLOCK_PREFIX.length();
    private static final int GRANITE_ID = Material.MATERIAL_REGISTRY.getIDForObject(Materials.Granite);

    @Override
    public int getFixVersion() {
        return 0;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        String regName = compound.getString("id");
        if (regName.startsWith(COMP_BLOCK_PREFIX)) {
            RemappedBlock remapped = remap(
                    Integer.parseInt(regName.substring(COMP_BLOCK_PREFIX_LEN)), compound.getShort("Damage"));
            if (remapped != null) {
                compound.setString("id", COMP_BLOCK_PREFIX + remapped.id);
                compound.setShort("Damage", remapped.data);
            }
        }
        return compound;
    }

    @Nullable
    static RemappedBlock remap(int index, int data) {
        int matId = MetaBlocks.COMPRESSED_OLD.get(index)[data];
        if (matId >= GRANITE_ID) {
            if (data == 15) {
                return new RemappedBlock(index + 1, (short) 0);
            } else {
                return new RemappedBlock(index, (short) (data + 1));
            }
        }
        return null;
    }

}
