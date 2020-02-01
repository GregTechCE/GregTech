package gregtech.common.blocks.surfacerock;

import gregtech.api.items.toolitem.IScannableBlock;
import gregtech.api.unification.material.type.Material;
import gregtech.common.blocks.properties.PropertyMaterial;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.List;

public class BlockSurfaceRockDeprecated extends BlockSurfaceRock implements IScannableBlock {

    public final PropertyMaterial materialProperty;

    public BlockSurfaceRockDeprecated(Material[] allowedValues) {
        this.materialProperty = PropertyMaterial.create("material", allowedValues);
        initBlockState();
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    public List<ITextComponent> getMagnifyResults(IBlockAccess world, BlockPos pos, IBlockState blockState, EntityPlayer player) {
        ArrayList<ITextComponent> result = new ArrayList<>();
        ITextComponent materialComponent = new TextComponentTranslation(getStoneMaterial(world, pos, blockState).getUnlocalizedName());
        materialComponent.getStyle().setColor(TextFormatting.GREEN);
        result.add(new TextComponentTranslation("gregtech.block.surface_rock.material", materialComponent));
        return result;
    }

    @Override
    public Material getStoneMaterial(IBlockAccess world, BlockPos pos, IBlockState blockState) {
        return blockState.getValue(materialProperty);
    }

    protected void initBlockState() {
        BlockStateContainer stateContainer = createBlockState();
        this.blockState = stateContainer;
        this.setDefaultState(stateContainer.getBaseState());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        if (materialProperty == null)
            return new BlockStateContainer(this);
        return new BlockStateContainer(this, materialProperty);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(materialProperty, materialProperty.getAllowedValues().get(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return materialProperty.getAllowedValues().indexOf(state.getValue(materialProperty));
    }
}
