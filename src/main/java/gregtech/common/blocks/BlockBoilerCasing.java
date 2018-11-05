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

public class BlockBoilerCasing extends VariantBlock<BlockBoilerCasing.BoilerCasingType> {

    public BlockBoilerCasing() {
        super(Material.IRON);
        setUnlocalizedName("boiler_casing");
        setHardness(5.0f);
        setResistance(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel("wrench", 2);
        setDefaultState(getState(BoilerCasingType.BRONZE_PIPE));
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
        return false;
    }

    //TODO cleanup in next major update
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        IBlockState blockState = ((VariantItemBlock<BoilerCasingType, BlockBoilerCasing>) stack.getItem()).getBlockState(stack);
        BoilerCasingType casingType = getState(blockState);
        if(casingType.name.startsWith("legacy_")) {
            tooltip.add(I18n.format("tile.boiler_casing.legacy"));
        }
    }

    //TODO cleanup in next major update
    @SuppressWarnings("DeprecatedIsStillUsed")
    public enum BoilerCasingType implements IStringSerializable {

        BRONZE_PIPE("bronze_pipe"),
        STEEL_PIPE("steel_pipe"),
        TITANIUM_PIPE("titanium_pipe"),
        TUNGSTENSTEEL_PIPE("tungstensteel_pipe"),

        //TODO cleanup in next major update
        @Deprecated __LEGACY_BRONZE_FIREBOX("legacy_bronze_firebox"),
        @Deprecated __LEGACY_STEEL_FIREBOX("legacy_steel_firebox"),
        @Deprecated __LEGACY_TITANIUM_FIREBOX("legacy_titanium_firebox"),
        @Deprecated __LEGACY_TUNGSTENSTEEL_FIREBOX("legacy_tungstensteel_firebox");

        private final String name;

        BoilerCasingType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

    }

}
