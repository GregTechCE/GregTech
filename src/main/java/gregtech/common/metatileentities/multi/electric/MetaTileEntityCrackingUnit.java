package gregtech.common.metatileentities.multi.electric;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.multiblock.BlockPattern;
import gregtech.api.multiblock.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.render.ICubeRenderer;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.Materials;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil.CoilType;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class MetaTileEntityCrackingUnit extends RecipeMapMultiblockController {

    private static final MultiblockAbility<?>[] ALLOWED_ABILITIES = {
        MultiblockAbility.IMPORT_FLUIDS, MultiblockAbility.EXPORT_FLUIDS,
        MultiblockAbility.INPUT_ENERGY
    };

    public MetaTileEntityCrackingUnit(String metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.CRACKING_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityCrackingUnit(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
            .aisle("HCHCH", "HCHCH", "HCHCH")
            .aisle("HCHCH", "H###H", "HCHCH")
            .aisle("HCHCH", "HCOCH", "HCHCH")
            .where('O', selfPredicate())
            .where('H', statePredicate(getCasingState()).or(abilityPartPredicate(ALLOWED_ABILITIES)))
            .where('#', blockPredicate(Blocks.AIR))
            .where('C', statePredicate(getCoilState()))
            .build();
    }

    @Override
    public ICubeRenderer getBaseTexture() { return Textures.CLEAN_STAINLESS_STEEL_CASING; }

    protected IBlockState getCoilState() { return MetaBlocks.WIRE_COIL.getState(CoilType.CUPRONICKEL);}

    protected IBlockState getCasingState() {  return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN); }
}
