package gregtech.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockWireCoil extends VariantBlock<BlockWireCoil.CoilType> {

    public BlockWireCoil() {
        super(Material.IRON);
        setUnlocalizedName("wire_coil");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(CoilType.CUPRONICKEL));
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> lines, ITooltipFlag tooltipFlag) {
        super.addInformation(itemStack, worldIn, lines, tooltipFlag);

        VariantItemBlock itemBlock = (VariantItemBlock<CoilType, BlockWireCoil>) itemStack.getItem();
        IBlockState stackState = itemBlock.getBlockState(itemStack);
        CoilType coilType = getState(stackState);

        lines.add(I18n.format("tile.wire_coil.tooltip_ebf"));
        lines.add(I18n.format("tile.wire_coil.tooltip_heat", coilType.coilTemperature));
        lines.add("");
        lines.add(I18n.format("tile.wire_coil.tooltip_smelter"));
        lines.add(I18n.format("tile.wire_coil.tooltip_level", coilType.level));
        lines.add(I18n.format("tile.wire_coil.tooltip_discount", coilType.energyDiscount));
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
        return false;
    }

    public enum CoilType implements IStringSerializable {

        CUPRONICKEL("cupronickel", 1800, 1, 1),
        KANTHAL("kanthal", 2700, 2, 1),
        NICHROME("nichrome", 3600, 4, 1),
        TUNGSTENSTEEL("tungstensteel", 4500, 8, 1),
        HSS_G("hss_g", 4700, 8, 2),
        NAQUADAH("naquadah", 5400, 16, 1),
        NAQUADAH_ALLOY("naquadah_alloy", 7200, 16, 2),
        SUPERCONDUCTOR("superconductor", 8600, 16, 4),
        FUSION_COIL("fusion_coil", 9700, 16, 8);

        private final String name;
        //electric blast furnace properties
        private final int coilTemperature;
        //multi smelter properties
        private final int level;
        private final int energyDiscount;

        CoilType(String name, int coilTemperature, int level, int energyDiscount) {
            this.name = name;
            this.coilTemperature = coilTemperature;
            this.level = level;
            this.energyDiscount = energyDiscount;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public int getCoilTemperature() {
            return coilTemperature;
        }

        public int getLevel() {
            return level;
        }

        public int getEnergyDiscount() {
            return energyDiscount;
        }
    }
}
