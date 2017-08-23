package gregtech.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

public class BlockMultiblockCasing extends VariantBlock<BlockMultiblockCasing.MultiblockCasingType> {

    public BlockMultiblockCasing() {
        super(Material.IRON);
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
    }

    public enum MultiblockCasingType implements IStringSerializable {

        BRONZE_BRICKS("bronze_bricks"),
        ENGINE_INTAKE_CASING("engine_intake"),
        GRATE_CASING("grate"),
        ASSEMBLER_CASING("assembler"),
        ASSEMBLING_MACHINE_CASING("assembling_machine"),
        FUSION_CASING("fusion"),
        FUSION_CASING_MK2("fusion_mk2");

        private final String name;

        MultiblockCasingType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

    }

}
