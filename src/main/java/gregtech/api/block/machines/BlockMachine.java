package gregtech.api.block.machines;

import codechicken.lib.render.item.CCRenderItem;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.ICustomHighlightBlock;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.render.MetaTileEntityRenderer;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("deprecation")
public class BlockMachine<T extends MetaTileEntity> extends Block implements ITileEntityProvider, ICustomHighlightBlock {

    public static final Map<BlockMachine<?>, ItemBlock> MACHINES = new HashMap<>();

    public final String name;
    public final Class<T> metaTileEntityClass;
    private final T sampleMetaTileEntity;
    private List<AxisAlignedBB> boundingBoxes;

    private int damageForScrewdriverClick = 40;
    private int damageForWrenchClick = 60;

    public static <T extends MetaTileEntity> BlockMachine<T> registerMetaTileEntity(String name, Class<T> metaTileEntityClass) {
        TileEntity.register(name, metaTileEntityClass);
        BlockMachine<T> blockMachine = new BlockMachine<>(name, metaTileEntityClass);
        blockMachine.setRegistryName("machine." + name);
        ItemBlock itemBlock = new ItemBlock(blockMachine);
        itemBlock.setRegistryName(blockMachine.getRegistryName());
        MACHINES.put(blockMachine, itemBlock);
        try {
            Method initMethod = metaTileEntityClass.getMethod("init", BlockMachine.class);
            initMethod.invoke(blockMachine.getSampleMetaTileEntity(), blockMachine);
        } catch (ReflectiveOperationException exception) {
            GTLog.logger.error("Failed to call init() on meta tile entity class {}", metaTileEntityClass, exception);
        }
        return blockMachine;
    }

    public BlockMachine(String name, Class<T> metaTileEntityClass) {
        super(Material.IRON);
        this.name = name;
        this.metaTileEntityClass = metaTileEntityClass;
        this.sampleMetaTileEntity = createNewTileEntity(null, 0);
        setCreativeTab(GregTechAPI.TAB_GREGTECH);
        this.boundingBoxes = Collections.singletonList(FULL_BLOCK_AABB);
    }

    public T getSampleMetaTileEntity() {
        return sampleMetaTileEntity;
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("gregtech.machine.%s.name", name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        String unlocalizedDesc = String.format("gregtech.machine.%s.desc", name);
        if(!I18n.hasKey(unlocalizedDesc)) return;
        String[] descriptionParts = I18n.format(unlocalizedDesc).split("/n");
        tooltip.addAll(Arrays.asList(descriptionParts));
        sampleMetaTileEntity.addInformation(stack, player, tooltip, advanced.isAdvanced());
    }

    public void setDamageForScrewdriverClick(int damageForScrewdriverClick) {
        this.damageForScrewdriverClick = damageForScrewdriverClick;
    }

    public void setDamageForWrenchClick(int damageForWrenchClick) {
        this.damageForWrenchClick = damageForWrenchClick;
    }

    public void setBoundingBox(AxisAlignedBB... boundingBoxes) {
        this.boundingBoxes = Arrays.asList(boundingBoxes);
    }

    @Override
    //just to be accessible from MetaTileEntity.init
    public Block setSoundType(SoundType sound) {
        return super.setSoundType(sound);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        for(AxisAlignedBB axisAlignedBB : boundingBoxes) {
            AxisAlignedBB offsetBox = axisAlignedBB.offset(pos);
            if (entityBox.intersects(offsetBox)) {
                collidingBoxes.add(offsetBox);
            }
        }
    }

    @Override
    public Collection<AxisAlignedBB> getSelectedBoundingBoxes(IBlockAccess world, BlockPos blockPos, IBlockState blockState) {
        ArrayList<AxisAlignedBB> selection = new ArrayList<>(boundingBoxes.size());
        for(AxisAlignedBB axisAlignedBB : boundingBoxes) {
            selection.add(axisAlignedBB.offset(blockPos));
        }
        return selection;
    }

    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        for(AxisAlignedBB boundingBox : boundingBoxes) {
            Vec3d vec3d = start.subtract(pos.getX(), pos.getY(), pos.getZ());
            Vec3d vec3d1 = end.subtract(pos.getX(), pos.getY(), pos.getZ());
            RayTraceResult raytraceresult = boundingBox.calculateIntercept(vec3d, vec3d1);
            if(raytraceresult != null) {
                return new RayTraceResult(raytraceresult.hitVec.addVector(pos.getX(), pos.getY(), pos.getZ()), raytraceresult.sideHit, pos);
            }
        }
        return null;
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        MetaTileEntity metaTileEntity = (MetaTileEntity) world.getTileEntity(pos);
        if(metaTileEntity == null ||
            !metaTileEntity.isValidFrontFacing(axis) ||
            metaTileEntity.getFrontFacing() == axis)
            return false;
        metaTileEntity.setFrontFacing(axis);
        return true;
    }

    @Nullable
    @Override
    public EnumFacing[] getValidRotations(World world, BlockPos pos) {
        MetaTileEntity metaTileEntity = (MetaTileEntity) world.getTileEntity(pos);
        if(metaTileEntity == null) return null;
        return Arrays.stream(EnumFacing.VALUES)
            .filter(metaTileEntity::isValidFrontFacing)
            .toArray(EnumFacing[]::new);
    }

    @Override
    public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {
        MetaTileEntity metaTileEntity = (MetaTileEntity) world.getTileEntity(pos);
        if(metaTileEntity == null ||
            metaTileEntity.getPaintingColor() == color.colorValue)
            return false;
        metaTileEntity.setPaintingColor(color.colorValue);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        MetaTileEntity metaTileEntity = (MetaTileEntity) worldIn.getTileEntity(pos);
        if(metaTileEntity == null) return;
        //always call initFromItemStackData with non-null tag, and empty if itemstack doesn't have one
        metaTileEntity.initFromItemStackData(stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound());
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        MetaTileEntity metaTileEntity = (MetaTileEntity) worldIn.getTileEntity(pos);
        if(metaTileEntity != null) {
            NonNullList<ItemStack> inventoryContents = NonNullList.create();
            metaTileEntity.clearMachineInventory(inventoryContents);
            for(ItemStack itemStack : inventoryContents) {
                Block.spawnAsEntity(worldIn, pos, itemStack);
            }
            metaTileEntity.onRemoval();
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        MetaTileEntity metaTileEntity = tileEntities.get() == null ? (MetaTileEntity) world.getTileEntity(pos) : tileEntities.get();
        if(metaTileEntity == null) return;

        ItemStack itemStack = new ItemStack(Item.getItemFromBlock(this));
        NBTTagCompound tagCompound = new NBTTagCompound();
        metaTileEntity.writeItemStackData(tagCompound);
        //only set item tag if it's not empty, so newly created items will stack with dismantled
        if(!tagCompound.hasNoTags())
            itemStack.setTagCompound(tagCompound);
        drops.add(itemStack);
        metaTileEntity.getDrops(drops, harvesters.get());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        MetaTileEntity metaTileEntity = (MetaTileEntity) worldIn.getTileEntity(pos);
        if(metaTileEntity == null) return false;
        ItemStack itemInHand = playerIn.getHeldItem(hand);
        if(!itemInHand.isEmpty()) {
            SimpleItemStack simpleItemStack = new SimpleItemStack(itemInHand);
            if(GregTechAPI.screwdriverList.contains(simpleItemStack)) {
                if(GTUtility.doDamageItem(itemInHand, damageForScrewdriverClick, true) &&
                    metaTileEntity.onScrewdriverClick(playerIn, hand, facing, hitX, hitY, hitZ)) {
                    GTUtility.doDamageItem(itemInHand, damageForScrewdriverClick, false);
                    return true;
                } else return false;
            } else if(GregTechAPI.wrenchList.contains(simpleItemStack)) {
                if(GTUtility.doDamageItem(itemInHand, damageForWrenchClick, true) &&
                    metaTileEntity.onWrenchClick(playerIn, hand, facing, hitX, hitY, hitZ)) {
                    GTUtility.doDamageItem(itemInHand, damageForWrenchClick, false);
                    return true;
                } else return false;
            }
        }
        return metaTileEntity.onRightClick(playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        MetaTileEntity metaTileEntity = (MetaTileEntity) worldIn.getTileEntity(pos);
        if(metaTileEntity == null) return;
        metaTileEntity.onLeftClick(playerIn);
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        MetaTileEntity metaTileEntity = (MetaTileEntity) world.getTileEntity(pos);
        return metaTileEntity != null && metaTileEntity.canConnectRedstone(side);
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        MetaTileEntity metaTileEntity = (MetaTileEntity) blockAccess.getTileEntity(pos);
        return metaTileEntity == null ? 0 : metaTileEntity.getOutputRedstoneSignal(side);
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        MetaTileEntity metaTileEntity = (MetaTileEntity) worldIn.getTileEntity(pos);
        return metaTileEntity == null ? 0 : metaTileEntity.getComparatorValue();
    }

    protected ThreadLocal<MetaTileEntity> tileEntities = new ThreadLocal<>();

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        tileEntities.set((MetaTileEntity) te);
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        tileEntities.set(null);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public T createNewTileEntity(@Nullable World worldIn, int meta) {
        try {
            return (T) metaTileEntityClass.newInstance();
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException("Failed to create meta tile entity from class " + metaTileEntityClass.getName(), exception);
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return MetaTileEntityRenderer.BLOCK_RENDER_TYPE;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
