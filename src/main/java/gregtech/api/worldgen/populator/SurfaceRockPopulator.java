package gregtech.api.worldgen.populator;

import com.google.gson.JsonObject;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.worldgen.config.OreConfigUtils;
import gregtech.api.worldgen.config.OreDepositDefinition;
import gregtech.api.worldgen.generator.GridEntryInfo;
import gregtech.common.MetaFluids;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.surfacerock.TileEntitySurfaceRock;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.*;

public class SurfaceRockPopulator implements VeinChunkPopulator {

    private Material material;

    public SurfaceRockPopulator() {
    }

    public SurfaceRockPopulator(Material material) {
        this.material = material;
    }

    @Override
    public void loadFromConfig(JsonObject object) {
        DustMaterial material = OreConfigUtils.getMaterialByName(object.get("material").getAsString());
        if (!(material instanceof DustMaterial))
            throw new IllegalArgumentException("Only dust materials are supported for surface rocks");
        this.material = material;
    }

    @Override
    public void initializeForVein(OreDepositDefinition definition) {
    }

    private Set<Material> findUndergroundMaterials(Collection<IBlockState> generatedBlocks) {
        HashSet<Material> result = new HashSet<>();
        for (IBlockState blockState : generatedBlocks) {
            Material resultMaterial;
            if (blockState.getBlock() instanceof IFluidBlock || blockState.getBlock() instanceof BlockLiquid) {
                Fluid fluid = FluidRegistry.lookupFluidForBlock(blockState.getBlock());
                resultMaterial = fluid == null ? null : MetaFluids.getMaterialFromFluid(fluid);
            } else {
                ItemStack itemStack = new ItemStack(blockState.getBlock(), 1, blockState.getBlock().damageDropped(blockState));
                UnificationEntry entry = OreDictUnifier.getUnificationEntry(itemStack);
                resultMaterial = entry == null ? null : entry.material;
            }
            if (resultMaterial != null) {
                result.add(resultMaterial);
            }
        }
        return result;
    }

    private void setStoneBlock(World world, BlockPos blockPos, Collection<Material> undergroundMaterials) {
        world.setBlockState(blockPos, MetaBlocks.SURFACE_ROCK_NEW.getDefaultState());
        TileEntitySurfaceRock tileEntity = (TileEntitySurfaceRock) world.getTileEntity(blockPos);
        tileEntity.setData(material, undergroundMaterials);
    }

    @Override
    public void populateChunk(World world, int chunkX, int chunkZ, Random random, OreDepositDefinition definition, GridEntryInfo gridEntryInfo) {
        int stonesCount = random.nextInt(2);
        if (world.getWorldType() != WorldType.FLAT && stonesCount > 0) {
            Set<Material> undergroundMaterials = findUndergroundMaterials(gridEntryInfo.getGeneratedBlocks(definition, chunkX, chunkZ));
            for (int i = 0; i < stonesCount; i++) {
                int randomX = chunkX * 16 + random.nextInt(16);
                int randomZ = chunkZ * 16 + random.nextInt(16);
                BlockPos topBlockPos = new BlockPos(randomX, 0, randomZ);
                topBlockPos = world.getTopSolidOrLiquidBlock(topBlockPos).down();
                IBlockState blockState = world.getBlockState(topBlockPos);
                if (blockState.getBlockFaceShape(world, topBlockPos, EnumFacing.UP) != BlockFaceShape.SOLID ||
                    !blockState.isOpaqueCube() || !blockState.isFullBlock())
                    continue;
                BlockPos surfaceRockPos = topBlockPos.up();
                setStoneBlock(world, surfaceRockPos, undergroundMaterials);
            }
        }
    }
}
