package gregtech.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockMachineCasing extends VariantBlock<BlockMachineCasing.MachineCasingType> {

    public BlockMachineCasing() {
        super(Material.IRON);
        setTranslationKey("machine_casing");
        setHardness(4.0f);
        setResistance(8.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(MachineCasingType.ULV));
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
        return false;
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
        MAX("maximum_voltage"),
        BRONZE_HULL("bronze_hull"),
        BRONZE_BRICKS_HULL("bronze_bricks_hull"),
        STEEL_HULL("steel_hull"),
        STEEL_BRICKS_HULL("steel_bricks_hull");

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
