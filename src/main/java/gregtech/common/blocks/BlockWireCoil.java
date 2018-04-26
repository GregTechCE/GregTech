package gregtech.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

public class BlockWireCoil extends VariantBlock<BlockWireCoil.CoilType> {

    public BlockWireCoil() {
        super(Material.IRON);
        setUnlocalizedName("wire_coil");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
    }

    public enum CoilType implements IStringSerializable {

        CUPRONICKEL("cupronickel", 1800),
        KANTHAL("kanthal", 2700),
        NICHROME("nichrome", 3600),
        TUNGSTENSTEEL("tungstensteel", 4500),
        HSS_G("hss_g", 5400),
        NAQUADAH("naquadah", 4700),
        NAQUADAH_ALLOY("naquadah_alloy", 7200),
        SUPERCONDUCTOR("superconductor", 8600),
        FUSION_COIL("fusion_coil", 9700);

        private final String name;
        private final int coilTemperature;

        CoilType(String name, int coilTemperature) {
            this.name = name;
            this.coilTemperature = coilTemperature;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public int getCoilTemperature() {
            return coilTemperature;
        }
    }

}
