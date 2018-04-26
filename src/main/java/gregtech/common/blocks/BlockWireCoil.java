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

        //TODO proper temperature values @Exidex
        CUPRONICKEL("cupronickel", 1800),
        KANTHAL("kanthal", 2500),
        NICHROME("nichrome", 2900),
        TUNGSTENSTEEL("tungstensteel", 3500),
        HSS_G("hss_g", 4000),
        NAQUADAH("naquadah", 4700),
        NAQUADAH_ALLOY("naquadah_alloy", 5000),
        SUPERCONDUCTOR("superconductor", 5300),
        FUSION_COIL("fusion_coil", 5700);

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
