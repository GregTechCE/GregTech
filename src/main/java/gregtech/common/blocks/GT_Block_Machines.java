package gregtech.common.blocks;

import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.IDebugableBlock;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_Utility;
import gregtech.common.render.IIconRegister;
import gregtech.common.render.newblocks.ITextureBlockIconProvider;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public class GT_Block_Machines extends GT_Generic_Block implements IDebugableBlock, ITileEntityProvider, IIconRegister, ITextureBlockIconProvider {

    public static ThreadLocal<IGregTechTileEntity> mTemporaryTileEntity = new ThreadLocal<>();

    public GT_Block_Machines() {
        super(GT_Item_Machines.class, "gt.blockmachines", new GT_Material_Machines());
        GregTech_API.registerMachineBlock(this, -1);
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
    public int getHarvestLevel(IBlockState aMeta) {
        return aMeta.getValue(METADATA) % 4;
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntity tTileEntity = world.getTileEntity(pos);
        if ((tTileEntity instanceof BaseTileEntity)) {
            ((BaseTileEntity) tTileEntity).onAdjacentBlockChange(neighbor.getX(), neighbor.getY(), neighbor.getZ());
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        if(GregTech_API.isMachineBlock(this, state.getValue(METADATA))) {
            GregTech_API.causeMachineUpdate(worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
    }

    @Override
    public String getUnlocalizedName() {
        return "gt.blockmachines";
    }

    @Override
    public String getLocalizedName() {
        return GT_LanguageManager.TRANSLATION.translateKey(getUnlocalizedName() + ".name");
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

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return true;
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
    public ITexture[] getTexture(World world, BlockPos blockPos, IExtendedBlockState blockState, EnumFacing side) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(world, blockPos);
        if(gregTechTileEntity != null) {
            return gregTechTileEntity.getTexture(this, (byte) side.getIndex());
        }
        return new ITexture[0];
    }

    @Override
    public ITexture[] getItemblockTexture(EntityPlayer player, ItemStack itemStack, EnumFacing side) {
        int tDamage = itemStack.getItemDamage();
        if(GregTech_API.METATILEENTITIES.length > tDamage) {
            return GregTech_API.METATILEENTITIES[tDamage].getTexture(null, (byte) side.getIndex(), (byte) 0, (byte) 0, false, false);
        }
        return new ITexture[0];
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        TileEntity tTileEntity = worldIn.getTileEntity(pos);
        return tTileEntity != null && tTileEntity.receiveClientEvent(id, param);
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
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap aIconRegister) {
        if (GregTech_API.sPostloadFinished) {
            GT_Log.out.println("GT_Mod: Setting up Icon Register for Blocks");

            GT_Log.out.println("GT_Mod: Registering MetaTileEntity specific Textures");
            for (IMetaTileEntity tMetaTileEntity : GregTech_API.METATILEENTITIES) {
                try {
                    if (tMetaTileEntity != null) {
                        tMetaTileEntity.registerIcons(aIconRegister);
                    }
                } catch (Throwable e) {
                    e.printStackTrace(GT_Log.err);
                }
            }

            GT_Log.out.println("GT_Mod: Starting Block Icon Load Phase");
            System.out.println("GT_Mod: Starting Block Icon Load Phase");
            for (Runnable tRunnable : GregTech_API.sGTBlockIconload) {
                try {
                    tRunnable.run();
                } catch (Throwable e) {
                    e.printStackTrace(GT_Log.err);
                }
            }
            GT_Log.out.println("GT_Mod: Finished Block Icon Load Phase");
            System.out.println("GT_Mod: Finished Block Icon Load Phase");
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
        if(gregTechTileEntity == null) {
            return false;
        }
        if(playerIn.isSneaking()) {
            ItemStack handItem = playerIn.getHeldItem(hand);
            if(handItem != null && GT_Utility.isStackInList(handItem, GregTech_API.sScrewdriverList)) {
                return true;
            }
            return false;
        }
        if(gregTechTileEntity.getTimer() < 50L) {
            return false;
        }
        if(!worldIn.isRemote && gregTechTileEntity.isUseableByPlayer(playerIn))
            return true;
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
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(world, pos);
        if(gregTechTileEntity != null && gregTechTileEntity instanceof BaseMetaTileEntity) {
            ((BaseMetaTileEntity) gregTechTileEntity).doEnergyExplosion();
        }
        super.onBlockExploded(world, pos, explosion);
    }

    @Override
    public void breakBlock(World aWorld, BlockPos pos, IBlockState blockState) {
        GregTech_API.causeMachineUpdate(aWorld, pos.getX(), pos.getY(), pos.getZ());
        TileEntity tTileEntity = aWorld.getTileEntity(pos);
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            IGregTechTileEntity tGregTechTileEntity = (IGregTechTileEntity) tTileEntity;
            Random tRandom = new Random();
            mTemporaryTileEntity.set(tGregTechTileEntity);
            for (int i = 0; i < tGregTechTileEntity.getSizeInventory(); i++) {
                ItemStack tItem = tGregTechTileEntity.getStackInSlot(i);
                if ((tItem != null) && (tItem.stackSize > 0) && (tGregTechTileEntity.isValidSlot(i))) {
                    EntityItem tItemEntity = new EntityItem(aWorld,
                            pos.getX() + tRandom.nextFloat() * 0.8F + 0.1F,
                            pos.getY() + tRandom.nextFloat() * 0.8F + 0.1F,
                            pos.getZ() + tRandom.nextFloat() * 0.8F + 0.1F,
                            new ItemStack(tItem.getItem(), tItem.stackSize, tItem.getItemDamage()));
                    if (tItem.hasTagCompound()) {
                        tItemEntity.getEntityItem().setTagCompound((NBTTagCompound) tItem.getTagCompound().copy());
                    }
                    tItemEntity.motionX = (tRandom.nextGaussian() * 0.0500000007450581D);
                    tItemEntity.motionY = (tRandom.nextGaussian() * 0.0500000007450581D + 0.2000000029802322D);
                    tItemEntity.motionZ = (tRandom.nextGaussian() * 0.0500000007450581D);
                    aWorld.spawnEntityInWorld(tItemEntity);
                    tItem.stackSize = 0;
                    tGregTechTileEntity.setInventorySlotContents(i, null);
                }
            }
        }
        super.breakBlock(aWorld, pos, blockState);
        aWorld.removeTileEntity(pos);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(world, pos);
        if(gregTechTileEntity != null) {
            return gregTechTileEntity.getDrops();
        }
        if(mTemporaryTileEntity.get() != null) {
            IGregTechTileEntity tempTile = mTemporaryTileEntity.get();
            mTemporaryTileEntity.remove();
            return tempTile.getDrops();
        }
        return Collections.emptyList();
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
            gregTechTileEntity.getOutputRedstoneSignal((byte) side.getIndex());
        }
        return 0;
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IGregTechTileEntity gregTechTileEntity = getGregTile(blockAccess, pos);
        if(gregTechTileEntity != null) {
            gregTechTileEntity.getStrongOutputRedstoneSignal((byte) side.getIndex());
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
        TileEntity tTileEntity = world.getTileEntity(pos);
        if (tTileEntity != null) {
            if ((tTileEntity instanceof BaseMetaTileEntity)) {
                return true;
            }
            if (((tTileEntity instanceof BaseMetaPipeEntity)) && ((((BaseMetaPipeEntity) tTileEntity).mConnections & 0xFFFFFFC0) != 0)) {
                return true;
            }
            if (((tTileEntity instanceof ICoverable)) && (((ICoverable) tTileEntity).getCoverIDAtSide((byte) side.getIndex()) != 0)) {
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
    public TileEntity createNewTileEntity(World aWorld, int aMeta) {
        if (aMeta < 4) {
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

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List) {
        for (int i = 1; i < GregTech_API.METATILEENTITIES.length; i++) {
            if (GregTech_API.METATILEENTITIES[i] != null) {
                par3List.add(new ItemStack(par1, 1, i));
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
    public ArrayList<String> getDebugInfo(EntityPlayer placer, int aX, int aY, int aZ, int aLogLevel) {
        TileEntity tTileEntity = placer.worldObj.getTileEntity(new BlockPos(aX, aY, aZ));
        if ((tTileEntity instanceof BaseMetaTileEntity)) {
            return ((BaseMetaTileEntity) tTileEntity).getDebugInfo(placer, aLogLevel);
        }
        if ((tTileEntity instanceof BaseMetaPipeEntity)) {
            return ((BaseMetaPipeEntity) tTileEntity).getDebugInfo(placer, aLogLevel);
        }
        return null;
    }

    public boolean recolourBlock(World aWorld, BlockPos blockPos, EnumFacing aSide, int aColor) {
        TileEntity tTileEntity = aWorld.getTileEntity(blockPos);
        if ((tTileEntity instanceof IGregTechTileEntity)) {
            if (((IGregTechTileEntity) tTileEntity).getColorization() == (byte) ((~aColor) & 0xF)) {
                return false;
            }
            ((IGregTechTileEntity) tTileEntity).setColorization((byte) ((~aColor) & 0xF));
            return true;
        }
        return false;
    }
}
