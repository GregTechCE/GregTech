package gregtech.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

public class BlockMachineCasing extends VariantBlock<BlockMachineCasing.MachineCasingType> {

    public BlockMachineCasing() {
        super(Material.IRON);
        setHardness(5.0f);
        setResistance(4.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
    }

    public enum MachineCasingType implements IStringSerializable {

        //Voltage-tiered casings
        ULV("ultra_low_voltage"),
        LV("low_voltage"),
        MV("medium_voltage"),
        HV("high_voltage"),
        EV("extreme_voltage"),
        IV("insane_voltage"),
        LuV("ludicrous_voltage"),
        ZPM("zero_point_module"),
        UV("ultra_voltage"),
        MAX("maximum_voltage");

        private final String name;

        MachineCasingType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

    }

}
