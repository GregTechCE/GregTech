package gregtech.api.block.machines;

import codechicken.lib.block.property.unlisted.UnlistedIntegerProperty;
import codechicken.lib.block.property.unlisted.UnlistedStringProperty;
import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.Cuboid6;
import com.google.common.collect.Lists;
import gregtech.api.GregTechAPI;
import gregtech.api.block.BlockCustomParticle;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.tool.IScrewdriverItem;
import gregtech.api.capability.tool.IWrenchItem;
import gregtech.api.cover.CoverBehavior;
import gregtech.api.cover.ICoverable;
import gregtech.api.cover.IFacadeCover;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.render.MetaTileEntityRenderer;
import gregtech.common.tools.DamageValues;
import gregtech.api.render.IBlockAppearance;
import gregtech.integration.ctm.IFacadeWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("deprecation")
public class BlockMachine extends BlockCustomParticle implements ITileEntityProvider, IFacadeWrapper, IBlockAppearance {

    private static final List<IndexedCuboid6> EMPTY_COLLISION_BOX = Lists.newArrayList(new IndexedCuboid6(null, Cuboid6.full));
    private static final IUnlistedProperty<String> HARVEST_TOOL = new UnlistedStringProperty("harvest_tool");
    private static final IUnlistedProperty<Integer> HARVEST_LEVEL = new UnlistedIntegerProperty("harvest_level");
    //used for rendering purposes of non-opaque machines like chests and tanks
    public static final PropertyBool OPAQUE = PropertyBool.create("opaque");

    public BlockMachine() {
        super(Material.IRON);
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        setSoundType(SoundType.METAL);
        setHardness(6.0f);
        setResistance(6.0f);
        setTranslationKey("unnamed");
        setHarvestLevel("wrench", 1);
        setDefaultState(getDefaultState().withProperty(OPAQUE, true));
    }

    @Nullable
    @Override
    public String getHarvestTool(IBlockState state) {
        return ((IExtendedBlockState) state).getValue(HARVEST_TOOL);
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        Integer value = ((IExtendedBlockState) state).getValue(HARVEST_LEVEL);
        return value == null ? 0 : value; //safety check for mods who don't handle state properly
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return state.getValue(OPAQUE);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(worldIn, pos);
        if (metaTileEntity == null)
            return state;

        return ((IExtendedBlockState) state)
            .withProperty(HARVEST_TOOL, metaTileEntity.getHarvestTool())
            .withProperty(HARVEST_LEVEL, metaTileEntity.getHarvestLevel());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{OPAQUE}, new IUnlistedProperty[]{HARVEST_TOOL, HARVEST_LEVEL});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(OPAQUE, meta % 2 == 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(OPAQUE) ? 0 : 1;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
        return false;
    }

    public static MetaTileEntity getMetaTileEntity(IBlockAccess blockAccess, BlockPos pos) {
        TileEntity holder = blockAccess.getTileEntity(pos);
        return holder instanceof MetaTileEntityHolder ? ((MetaTileEntityHolder) holder).getMetaTileEntity() : null;
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(worldIn, pos);
        return metaTileEntity == null ? 1.0f : metaTileEntity.getBlockHardness();
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(world, pos);
        return metaTileEntity == null ? 1.0f : metaTileEntity.getBlockResistance();
    }

    private List<IndexedCuboid6> getCollisionBox(IBlockAccess blockAccess, BlockPos pos) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(blockAccess, pos);
        if (metaTileEntity == null)
            return EMPTY_COLLISION_BOX;
        ArrayList<IndexedCuboid6> collisionList = new ArrayList<>();
        metaTileEntity.addCollisionBoundingBox(collisionList);
        metaTileEntity.addCoverCollisionBoundingBox(collisionList);
        return collisionList;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(world, pos);
        if (metaTileEntity == null)
            return ItemStack.EMPTY;
        if (target instanceof CuboidRayTraceResult) {
            return metaTileEntity.getPickItem((CuboidRayTraceResult) target, player);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        for (Cuboid6 axisAlignedBB : getCollisionBox(worldIn, pos)) {
            AxisAlignedBB offsetBox = axisAlignedBB.aabb().offset(pos);
            if (offsetBox.intersects(entityBox)) collidingBoxes.add(offsetBox);
        }
    }

    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        return RayTracer.rayTraceCuboidsClosest(start, end, pos, getCollisionBox(worldIn, pos));
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(world, pos);
        if (metaTileEntity == null ||
            !metaTileEntity.isValidFrontFacing(axis) ||
            metaTileEntity.getFrontFacing() == axis ||
            !metaTileEntity.hasFrontFacing())
            return false;
        metaTileEntity.setFrontFacing(axis);
        return true;
    }

    @Nullable
    @Override
    public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(world, pos);
        if (metaTileEntity == null || !metaTileEntity.hasFrontFacing()) return null;
        return Arrays.stream(EnumFacing.VALUES)
            .filter(metaTileEntity::isValidFrontFacing)
            .toArray(EnumFacing[]::new);
    }

    @Override
    public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(world, pos);
        if (metaTileEntity == null ||
            metaTileEntity.getPaintingColor() == color.colorValue)
            return false;
        metaTileEntity.setPaintingColor(color.colorValue);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        MetaTileEntityHolder holder = (MetaTileEntityHolder) worldIn.getTileEntity(pos);
        MetaTileEntity sampleMetaTileEntity = GregTechAPI.META_TILE_ENTITY_REGISTRY.getObjectById(stack.getItemDamage());
        if (holder != null && sampleMetaTileEntity != null) {
            MetaTileEntity metaTileEntity = holder.setMetaTileEntity(sampleMetaTileEntity);
            if (stack.hasTagCompound()) {
                metaTileEntity.initFromItemStackData(stack.getTagCompound());
            }
            EnumFacing placeFacing = placer.getHorizontalFacing().getOpposite();
            if (metaTileEntity.isValidFrontFacing(placeFacing)) {
                metaTileEntity.setFrontFacing(placeFacing);
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(worldIn, pos);
        if (metaTileEntity != null) {
            if (!metaTileEntity.keepsInventory()) {
                NonNullList<ItemStack> inventoryContents = NonNullList.create();
                metaTileEntity.clearMachineInventory(inventoryContents);
                for (ItemStack itemStack : inventoryContents) {
                    Block.spawnAsEntity(worldIn, pos, itemStack);
                }
            }
            metaTileEntity.dropAllCovers();
            metaTileEntity.onRemoval();

            tileEntities.set(metaTileEntity);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        MetaTileEntity metaTileEntity = tileEntities.get() == null ? getMetaTileEntity(world, pos) : tileEntities.get();
        if (metaTileEntity == null) return;
        if (!metaTileEntity.shouldDropWhenDestroyed())
            return;
        ItemStack itemStack = metaTileEntity.getStackForm();
        NBTTagCompound tagCompound = new NBTTagCompound();
        metaTileEntity.writeItemStackData(tagCompound);
        //only set item tag if it's not empty, so newly created items will stack with dismantled
        if (!tagCompound.isEmpty())
            itemStack.setTagCompound(tagCompound);
        drops.add(itemStack);
        metaTileEntity.getDrops(drops, harvesters.get());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(worldIn, pos);
        CuboidRayTraceResult rayTraceResult = (CuboidRayTraceResult) RayTracer.retraceBlock(worldIn, playerIn, pos);
        ItemStack itemStack = playerIn.getHeldItem(hand);
        if (metaTileEntity == null || rayTraceResult == null) {
            return false;
        }

        if (itemStack.hasCapability(GregtechCapabilities.CAPABILITY_SCREWDRIVER, null)) {
            IScrewdriverItem screwdriver = itemStack.getCapability(GregtechCapabilities.CAPABILITY_SCREWDRIVER, null);

            if (screwdriver.damageItem(DamageValues.DAMAGE_FOR_SCREWDRIVER, true) &&
                metaTileEntity.onCoverScrewdriverClick(playerIn, hand, rayTraceResult)) {
                screwdriver.damageItem(DamageValues.DAMAGE_FOR_SCREWDRIVER, false);
                return true;
            }
            return false;
        }

        if (itemStack.hasCapability(GregtechCapabilities.CAPABILITY_WRENCH, null)) {
            IWrenchItem wrenchItem = itemStack.getCapability(GregtechCapabilities.CAPABILITY_WRENCH, null);
            EnumFacing wrenchDirection = ICoverable.determineGridSideHit(rayTraceResult);

            if (wrenchItem.damageItem(DamageValues.DAMAGE_FOR_WRENCH, true) &&
                metaTileEntity.onWrenchClick(playerIn, hand, wrenchDirection, rayTraceResult)) {
                wrenchItem.damageItem(DamageValues.DAMAGE_FOR_WRENCH, false);
                return true;
            }
            return false;
        }

        return metaTileEntity.onCoverRightClick(playerIn, hand, rayTraceResult);
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(worldIn, pos);
        if (metaTileEntity == null) return;
        CuboidRayTraceResult rayTraceResult = (CuboidRayTraceResult) RayTracer.retraceBlock(worldIn, playerIn, pos);
        if (rayTraceResult != null) {
            metaTileEntity.onCoverLeftClick(playerIn, rayTraceResult);
        }
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(world, pos);
        return metaTileEntity != null && metaTileEntity.canConnectRedstone(side == null ? null : side.getOpposite());
    }

    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        // The check in World::getRedstonePower in the vanilla code base is reversed. Setting this to false will
        // actually cause getWeakPower to be called, rather than prevent it.
        return false;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(blockAccess, pos);
        return metaTileEntity == null ? 0 : metaTileEntity.getOutputRedstoneSignal(side == null ? null : side.getOpposite());
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(worldIn, pos);
        if (metaTileEntity != null) {
            metaTileEntity.updateInputRedstoneSignals();
            metaTileEntity.onNeighborChanged();
        }
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(worldIn, pos);
        return metaTileEntity == null ? 0 : metaTileEntity.getComparatorValue();
    }

    protected ThreadLocal<MetaTileEntity> tileEntities = new ThreadLocal<>();

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        tileEntities.set(te == null ? tileEntities.get() : ((MetaTileEntityHolder) te).getMetaTileEntity());
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        tileEntities.set(null);
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public MetaTileEntityHolder createNewTileEntity(@Nullable World worldIn, int meta) {
        return new MetaTileEntityHolder();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return MetaTileEntityRenderer.BLOCK_RENDER_TYPE;
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return state.getValue(OPAQUE);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return state.getValue(OPAQUE);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(worldIn, pos);
        return metaTileEntity == null ? BlockFaceShape.SOLID : metaTileEntity.getCoverFaceShape(face);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        //why mc is so fucking retarded to call this method on fucking NEIGHBOUR BLOCKS!
        MetaTileEntity metaTileEntity = getMetaTileEntity(world, pos);
        return metaTileEntity == null ? 0 : metaTileEntity.getLightValue();
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        //why mc is so fucking retarded to call this method on fucking NEIGHBOUR BLOCKS!
        MetaTileEntity metaTileEntity = getMetaTileEntity(world, pos);
        return metaTileEntity == null ? 0 : metaTileEntity.getLightOpacity();
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (MetaTileEntity metaTileEntity : GregTechAPI.META_TILE_ENTITY_REGISTRY) {
            metaTileEntity.getSubItems(tab, items);
        }
    }

    @Nonnull
    @Override
    public IBlockState getFacade(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable EnumFacing side, @Nonnull BlockPos otherPos) {
        return getFacade(world, pos, side);
    }

    @Nonnull
    @Override
    public IBlockState getFacade(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, EnumFacing side) {
        MetaTileEntity metaTileEntity = getMetaTileEntity(world, pos);
        if (metaTileEntity != null && side != null) {
            CoverBehavior coverBehavior = metaTileEntity.getCoverAtSide(side);
            if (coverBehavior instanceof IFacadeCover) {
                return ((IFacadeCover) coverBehavior).getVisualState();
            }
        }
        return world.getBlockState(pos);
    }

    @Nonnull
    @Override
    public IBlockState getVisualState(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing side) {
        return getFacade(world, pos, side);
    }

    @Override
    public boolean supportsVisualConnections() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected Pair<TextureAtlasSprite, Integer> getParticleTexture(World world, BlockPos blockPos) {
        return MetaTileEntityRenderer.INSTANCE.getParticleTexture(world, blockPos);
    }
}
