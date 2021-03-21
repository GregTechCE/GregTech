package gregtech.common.datafix.fixes;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IFixableData;

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
            int index = Integer.parseInt(regName.substring(COMP_BLOCK_PREFIX_LEN));
            short damage = compound.getShort("Damage");
            int matId = MetaBlocks.COMPRESSED_OLD.get(index)[damage];
            if (matId >= GRANITE_ID) {
                if (damage == 15) {
                    damage = 0;
                    ++index;
                } else {
                    ++damage;
                }
                compound.setString("id", COMP_BLOCK_PREFIX + index);
                compound.setShort("Damage", damage);
            }
        }
        return compound;
    }

}
