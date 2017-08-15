package gregtech.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

public class BlockWireCoil extends VariantBlock<BlockWireCoil.CoilType> {

    public BlockWireCoil() {
        super(Material.IRON);
        setHardness(6.0f);
        setResistance(5.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
    }

    public enum CoilType implements IStringSerializable {

        CUPRONICKEL("cupronickel"),
        KANTHAL("kanthal"),
        NICHROME("nichrome"),
        TUNGSTENSTEEL("tungstensteel"),
        HSS_G("hss_g"),
        NAQUADAH("naquadah"),
        NAQUADAH_ALLOY("naquadah_alloy"),
        SUPERCONDUCTOR("superconductor"),
        FUSION("fusion");

        private final String name;

        CoilType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

    }

}
