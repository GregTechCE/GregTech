package gregtech.common.blocks.surfacerock;

import gregtech.api.items.toolitem.IScannableBlock;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockSurfaceRockNew extends BlockSurfaceRock implements ITileEntityProvider, IScannableBlock {
    protected ThreadLocal<TileEntitySurfaceRock> tileEntities = new ThreadLocal<>();

    public BlockSurfaceRockNew() {
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    public List<ITextComponent> getMagnifyResults(IBlockAccess world, BlockPos pos, IBlockState blockState, EntityPlayer player) {
        TileEntitySurfaceRock tileEntity = getTileEntity(world, pos);
        if (tileEntity == null) {
            return Collections.emptyList();
        }
        List<Material> materials = tileEntity.getUndergroundMaterials();
        ITextComponent materialComponent = new TextComponentTranslation(tileEntity.getMaterial().getUnlocalizedName());
        materialComponent.getStyle().setColor(TextFormatting.GREEN);
        ITextComponent baseComponent = new TextComponentString("");
        ITextComponent separator = new TextComponentString(", ");
        separator.getStyle().setColor(TextFormatting.GRAY);
        for (int i = 0; i < materials.size(); i++) {
            ITextComponent extraComponent = new TextComponentTranslation(materials.get(i).getUnlocalizedName());
            extraComponent.getStyle().setColor(TextFormatting.YELLOW);
            baseComponent.appendSibling(extraComponent);
            if (i + 1 != materials.size()) baseComponent.appendSibling(separator);
        }
        ArrayList<ITextComponent> result = new ArrayList<>();
        result.add(new TextComponentTranslation("gregtech.block.surface_rock.material", materialComponent));
        result.add(new TextComponentTranslation("gregtech.block.surface_rock.underground_materials", baseComponent));
        return result;
    }

    @Override
    public Material getStoneMaterial(IBlockAccess blockAccess, BlockPos pos, IBlockState blockState) {
        TileEntitySurfaceRock surfaceRockTileEntity = getTileEntity(blockAccess, pos);
        if (surfaceRockTileEntity == null) {
            // This can be null during Harvest event, in which case we've (hackily) stashed a tile entity via harvestBlock()
            surfaceRockTileEntity = tileEntities.get();
        }

        if (surfaceRockTileEntity != null) {
            return surfaceRockTileEntity.getMaterial();
        }
        return Materials.Aluminium;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntitySurfaceRock surfaceRockTileEntity = getTileEntity(worldIn, pos);
        if (surfaceRockTileEntity != null) {
            tileEntities.set(surfaceRockTileEntity);
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        tileEntities.set(te == null ? tileEntities.get() : (TileEntitySurfaceRock) te);
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        tileEntities.set(null);
    }

    public static TileEntitySurfaceRock getTileEntity(IBlockAccess world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileEntitySurfaceRock)
            return (TileEntitySurfaceRock) tileEntity;
        return null;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySurfaceRock();
    }
}
