package gregtech.common.blocks;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.items.IDebugableBlock;
import gregtech.api.interfaces.ITexture;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.capability.ICoverable;
import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.items.GenericBlock;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.TickableTileEntityBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GTLog;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.itemblocks.ItemMachines;
import gregtech.common.blocks.materials.MaterialMachines;
import gregtech.common.render.GT_Renderer_Block;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockMachines extends Block implements IDebugableBlock, ITileEntityProvider {

    public BlockMachines() {
        super("blockmachines", ItemMachines.class, MaterialMachines.INSTANCE);
        GregTech_API.registerMachineBlock(this.getBlockState().getValidStates());
        setHardness(1.0F);
        setResistance(10.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(GregTech_API.TAB_GREGTECH);
        this.isBlockContainer = true;
    }

    public String getHarvestTool(IBlockState blockState) {
        switch (blockState.getValue(METADATA) / 4) {
            case 0:
                return "wrench";
            case 1:
                return "wrench";
            case 2:
                return "cutter";
            case 3:
                return "axe";
        }
        return "wrench";
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return state.getValue(METADATA) % 4;
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if ((tileEntity instanceof TickableTileEntityBase)) {
            ((TickableTileEntityBase) tileEntity).onAdjacentBlockChange(neighbor.getX(), neighbor.getY(), neighbor.getZ());
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if(GregTech_API.isMachineBlock(state)) {
            GregTech_API.causeMachineUpdate(worldIn, pos);
        }
    }

    @Override
    public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return 0;
    }

    @Override
    public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return (GregTech_API.sMachineFlammable) && (world.getBlockState(pos).getValue(METADATA) == 0) ? 100 : 0;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return (GregTech_API.sMachineFlammable) && (world.getBlockState(pos).getValue(METADATA) == 0);
    }

    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
        return (GregTech_API.sMachineFlammable) && (world.getBlockState(pos).getValue(METADATA) == 0);
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return false;
    }

    /**
     * NB: Vanilla redstone behavior is that wires will connect to redstone emitters but not consumers.
     * Thus we only say we can connect if we are an emitter. (This is usually delegated to a cover behavior.)
     *
     */
    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (side == null ) {
            return false;
        }
        IGregTechTileEntity gregTechTileEntity = getGregTile(world, pos);
        return gregTechTileEntity != null && gregTechTileEntity.canOutputRedstone((byte) side.getOpposite().getIndex());
    }

    @Override
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return false;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return createNewTileEntity(world, state.getValue(METADATA));
    }

    public IGregTechTileEntity getGregTile(IBlockAccess world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if(tileEntity instanceof IGregTechTileEntity) {
            IGregTechTileEntity gregTechTile = (IGregTechTileEntity) tileEntity;
            if(gregTechTile.getMetaTileEntity() != null) return gregTechTile;
        }
        return null;
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        return tileEntity != null && tileEntity.receiveClientEvent(id, param);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn) {
        IGregTechTileEntity greg = getGregTile(worldIn, pos);
        if(greg != null) {
            greg.addCollisionBoxesToList(worldIn, pos.getX(), pos.getY(), pos.getZ(), entityBox, collidingBoxes, entityIn);
            return;
        }
        super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
        IGregTechTileEntity greg = getGregTile(worldIn, pos);
        if(greg != null) {
            return greg.getCollisionBoundingBoxFromPool(worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return super.getCollisionBoundingBox(blockState, worldIn, pos);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        IGregTechTileEntity greg = getGregTile(worldIn, pos);
        if(greg != null) {
            greg.onEntityCollidedWithBlock(worldIn, pos.getX(), pos.getY(), pos.getZ(), entityIn);
        }
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        IGregTechTileEntity greg = getGregTile(world, pos);
        if(greg != null) {
            ArrayList<ItemStack> drops = greg.getDrops();
            if(!drops.isEmpty()) return drops.get(0);
        }
        return null;
    }



    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap iconRegister) {
        if (GregTech_API.sPostloadFinished) {
            GTLog.out.println("GT_Mod: Registering MetaTileEntity specific Textures");
            for (IMetaTileEntity tMetaTileEntity : GregTech_API.METATILEENTITIES) {
                try {
                    if (tMetaTileEntity != null) {
                        tMetaTileEntity.registerIcons(iconRegister);
                    }
                } catch (Throwable e) {
                    e.printStackTrace(GTLog.err);
                }
            }
        }
    }

    @Override
    public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World worldIn, BlockPos pos) {
        IGregTechTileEntity gregTile = getGregTile(worldIn, pos);
        if(gregTile != null && gregTile instanceof BaseMetaTileEntity) {
            BaseMetaTileEntity metaTile = (BaseMetaTileEntity) gregTile;
            if(metaTile.privateAccess() && !metaTile.getOwnerName().equals(player.getName())) {
                return - 1.0F;
            }
        }

        return super.getPlayerRelativeBlockHardness(state, player, worldIn, pos);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(worldIn, pos);
        if (gregTechTileEntity == null) {
            return false;
        }
        if (playerIn.isSneaking()) {
            ItemStack handItem = playerIn.inventory.getCurrentItem();
            return handItem != null && GT_Utility.isStackInList(handItem, GregTech_API.sScrewdriverList);
        }
        if (gregTechTileEntity.getTimer() < 50L) {
            return false;
        }
        if (gregTechTileEntity.isUseableByPlayer(playerIn)) {
            return gregTechTileEntity.onRightclick(playerIn, (byte) side.getIndex(), hitX, hitY, hitZ, EnumHand.MAIN_HAND);
        }
        return false;
    }



    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(worldIn, pos);
        if(gregTechTileEntity != null) {
            gregTechTileEntity.onLeftclick(playerIn);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleSprite(IBlockAccess worldObj, BlockPos pos, EnumFacing side) {
        IGregTechTileEntity tileEntity = getGregTile(worldObj, pos);
        if(tileEntity != null) {
            IMetaTileEntity metaTileEntity = tileEntity.getMetaTileEntity();
            if(metaTileEntity instanceof GT_MetaTileEntity_TieredMachineBlock) {
                return Textures.BlockIcons.MACHINECASINGS_TOP[((GT_MetaTileEntity_TieredMachineBlock) metaTileEntity).mTier].getIcon();
            } else {
                return Textures.BlockIcons.MACHINECASINGS_TOP[1].getIcon();
            }

        }
        return null;
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(world, pos);
        if(gregTechTileEntity != null && gregTechTileEntity instanceof BaseMetaTileEntity) {
            ((BaseMetaTileEntity) gregTechTileEntity).doEnergyExplosion();
        }
        super.onBlockExploded(world, pos, explosion);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack) {
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);

        if(te instanceof IGregTechTileEntity) {
            List<ItemStack> itemstacks = getDrops((IGregTechTileEntity) te);
            for(ItemStack itemStack : itemstacks) {
                spawnAsEntity(worldIn, pos, itemStack);
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Collections.emptyList();
    }

    public List<ItemStack> getDrops(IGregTechTileEntity gregTechTileEntity) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        for (int i = 0; i < gregTechTileEntity.getSizeInventory(); i++) {
            ItemStack stack = gregTechTileEntity.getStackInSlot(i);
            if ((stack != null) && (stack.stackSize > 0) && (gregTechTileEntity.isValidSlot(i))) {
                drops.add(stack);
                gregTechTileEntity.setInventorySlotContents(i, null);
            }
        }
        drops.addAll(gregTechTileEntity.getDrops());
        return drops;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        IGregTechTileEntity gregTechTileEntity = (IGregTechTileEntity) worldIn.getTileEntity(pos);
        if(gregTechTileEntity != null) {
            return gregTechTileEntity.getComparatorValue((byte) 0);
        }
        return 0;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(blockAccess, pos);
        if(gregTechTileEntity != null) {
            return gregTechTileEntity.getOutputRedstoneSignal((byte) side.getOpposite().getIndex());
        }
        return 0;
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(blockAccess, pos);
        if(gregTechTileEntity != null) {
            return gregTechTileEntity.getStrongOutputRedstoneSignal((byte) side.getOpposite().getIndex());
        }
        return 0;
    }


    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if(!worldIn.isRemote) {
            IGregTechTileEntity gregTechTileEntity = getGregTile(worldIn, pos);
            if(gregTechTileEntity instanceof BaseMetaTileEntity && GregTech_API.sMachineNonWrenchExplosions)
                ((BaseMetaTileEntity) gregTechTileEntity).doEnergyExplosion();
        }
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (base_state.getValue(METADATA) == 0) {
            return true;
        }
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity != null) {
            if ((tileEntity instanceof BaseMetaTileEntity)) {
                return true;
            }
            if (((tileEntity instanceof BaseMetaPipeEntity)) && ((((BaseMetaPipeEntity) tileEntity).mConnections & 0xFFFFFFC0) != 0)) {
                return true;
            }
            if (((tileEntity instanceof ICoverable)) && (((ICoverable) tileEntity).getCoverIDAtSide((byte) side.getIndex()) != 0)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(world, pos);
        if(gregTechTileEntity != null) {
            return gregTechTileEntity.getLightOpacity();
        }
        return state.getValue(METADATA) == 0 ? 255 : 0;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(world, pos);
        if(gregTechTileEntity instanceof BaseMetaTileEntity) {
            return ((BaseMetaTileEntity) gregTechTileEntity).getLightValue();
        }
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        if (meta < 4) {
            return GregTech_API.constructBaseMetaTileEntity();
        }
        return new BaseMetaPipeEntity();
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(world, pos);
        if(gregTechTileEntity != null) {
            return gregTechTileEntity.getBlastResistance((byte) 6);
        }
        return 10.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++) {
            if (GregTech_API.METATILEENTITIES[i] != null) {
                list.add(new ItemStack(item, 1, i));
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(worldIn, pos);
        if(gregTechTileEntity != null) {
            if(placer == null) {
                gregTechTileEntity.setFrontFacing((byte) 1);
            } else {
                int var7 = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
                int var8 = Math.round(placer.rotationPitch);
                if ((var8 >= 65) && (gregTechTileEntity.isValidFacing((byte) 1))) {
                    gregTechTileEntity.setFrontFacing((byte) 1);
                } else if ((var8 <= -65) && (gregTechTileEntity.isValidFacing((byte) 0))) {
                    gregTechTileEntity.setFrontFacing((byte) 0);
                } else {
                    switch (var7) {
                        case 0:
                            gregTechTileEntity.setFrontFacing((byte) 2);
                            break;
                        case 1:
                            gregTechTileEntity.setFrontFacing((byte) 5);
                            break;
                        case 2:
                            gregTechTileEntity.setFrontFacing((byte) 3);
                            break;
                        case 3:
                            gregTechTileEntity.setFrontFacing((byte) 4);
                    }
                }
            }
        }
    }

    @Override
    public ArrayList<String> getDebugInfo(EntityPlayer placer, int x, int y, int z, int logLevel) {
        TileEntity tileEntity = placer.worldObj.getTileEntity(new BlockPos(x, y, z));
        if ((tileEntity instanceof BaseMetaTileEntity)) {
            return ((BaseMetaTileEntity) tileEntity).getDebugInfo(placer, logLevel);
        }
        if ((tileEntity instanceof BaseMetaPipeEntity)) {
            return ((BaseMetaPipeEntity) tileEntity).getDebugInfo(placer, logLevel);
        }
        return null;
    }

    @Override
    public boolean recolorBlock(World world, BlockPos blockPos, EnumFacing side, EnumDyeColor colorDye) {
        int color = colorDye.getMetadata();
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if ((tileEntity instanceof IGregTechTileEntity)) {
            if (((IGregTechTileEntity) tileEntity).getColorization() == (byte) ((~color) & 0xF)) {
                return false;
            }
            ((IGregTechTileEntity) tileEntity).setColorization((byte) ((~color) & 0xF));
            return true;
        }
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return GT_Renderer_Block.INSTANCE.renderType;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}