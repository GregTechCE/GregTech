package gregtech.common.blocks;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.api.world.GT_Worldgen_Constants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GT_TileEntity_Ores extends TileEntity {

    private static final NBTTagCompound EMPTY_TAG = new NBTTagCompound();

    static {
        EMPTY_TAG.setInteger("y", -1);
    }

    public short mMetaData = 0;
    public boolean mNatural = false;

    private boolean mBlocked = true;

    public static boolean isBlocked(World worldObj, BlockPos pos) {
        return isOpaque(worldObj, pos.up()) &&
                isOpaque(worldObj, pos.down()) &&
                isOpaque(worldObj, pos.east()) &&
                isOpaque(worldObj, pos.west()) &&
                isOpaque(worldObj, pos.south()) &&
                isOpaque(worldObj, pos.north());
    }

    public void updateBlocked() {
        this.mBlocked = isBlocked(worldObj, getPos());
    }

    public boolean isBlocked() {
        return mBlocked;
    }

    private static boolean isOpaque(World worldObj, BlockPos pos) {
        return worldObj.getBlockState(pos).isOpaqueCube();
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        updateBlocked();
        if(mBlocked) {
            return EMPTY_TAG;
        }
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return tagCompound;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return null;
    }

    public void broadcastPacketIfNeeded() {
        updateBlocked();
        if(!mBlocked) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            writeNBTInternal(tagCompound);
            SPacketUpdateTileEntity packet = new SPacketUpdateTileEntity(getPos(), 0, tagCompound);
            if (packet != null) {
                AxisAlignedBB box = new AxisAlignedBB(pos, pos).expand(256, 256, 256);
                List<EntityPlayerMP> players = worldObj.getEntitiesWithinAABB(EntityPlayerMP.class, box);
                for (EntityPlayerMP entityPlayer : players) {
                    entityPlayer.connection.sendPacket(packet);
                }
            }
        }
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        readNBTInternal(tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readNBTInternal(pkt.getNbtCompound());
        causeChunkUpdate();
    }

    @Override
    public void readFromNBT(NBTTagCompound aNBT) {
        super.readFromNBT(aNBT);
        readNBTInternal(aNBT);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound aNBT) {
        super.writeToNBT(aNBT);
        writeNBTInternal(aNBT);
        return aNBT;
    }

    public void writeNBTInternal(NBTTagCompound tagCompound) {
        tagCompound.setShort("m", this.mMetaData);
        tagCompound.setBoolean("n", this.mNatural);
    }

    public void readNBTInternal(NBTTagCompound tagCompound) {
        this.mMetaData = tagCompound.getShort("m");
        this.mNatural = tagCompound.getBoolean("n");
    }

    @SideOnly(Side.CLIENT)
    public void causeChunkUpdate() {
        int minX = pos.getX();
        int minY = pos.getY() - 1;
        int minZ = pos.getZ();
        int maxX = pos.getX();
        int maxY = pos.getY() + 1;
        int maxZ = pos.getZ();
        Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static int getHarvestData(GT_Block_Ores_Abstract aBlock, int aMetaData) {
        Materials aMaterial = GregTech_API.sGeneratedMaterials[(aMetaData % 1000)];
        if(aMaterial != null) {
            boolean gravelAlike = aBlock.isGravelAlike(aMetaData);
            int toolQuality = Math.min(7, aMaterial.mToolQuality);
            return gravelAlike ? 8 + toolQuality : toolQuality;
        }
        return 0;
    }


    public static boolean setOreBlock(World aWorld, BlockPos blockPos, int aMetaData, boolean isSmallOre) {
        return setOreBlock(aWorld, blockPos.getX(), blockPos.getY(), blockPos.getZ(), aMetaData, isSmallOre, false);
    }

    public static boolean setOreBlock(World aWorld, int aX, int aY, int aZ, int aMetaData, boolean isSmallOre, boolean air) {
        BlockPos blockPos = new BlockPos(aX, aY, aZ);
        IBlockState blockState = aWorld.getBlockState(blockPos);

        Block tBlock = blockState.getBlock();
        int BlockMeta = tBlock.getMetaFromState(blockState);

        GT_Block_Ores_Abstract tOreBlock = (GT_Block_Ores_Abstract) GregTech_API.sBlockOres1;
        aMetaData += isSmallOre ? 16000 : 0;

        if(air && tBlock == Blocks.AIR) {
            aMetaData += 0;
        } else if(tBlock == Blocks.STONE) {
            if(BlockMeta == 0) {
                aMetaData += 0;
            } else if(BlockMeta == 1) {
                aMetaData += 7000;
            } else if(BlockMeta == 3) {
                aMetaData += 8000;
            } else if(BlockMeta == 5) {
                aMetaData += 9000;
            } else {
                return false;
            }
        } else if (tBlock == Blocks.NETHERRACK) {
            aMetaData += 1000;
        } else if (tBlock == Blocks.END_STONE) {
            aMetaData += 2000;
        } else if (tBlock == Blocks.SAND || tBlock == Blocks.SANDSTONE) {
            aMetaData += 11000;
        } else if (tBlock == Blocks.GRAVEL) {
            aMetaData += 10000;
        } else if (tBlock == GregTech_API.sBlockGranites && BlockMeta == 0) {
            aMetaData += 3000;
        } else if (tBlock == GregTech_API.sBlockGranites && BlockMeta == 8) {
            aMetaData += 4000;
        } else if (tBlock == GregTech_API.sBlockStones && BlockMeta == 0) {
            aMetaData += 5000;
        } else if (tBlock == GregTech_API.sBlockStones && BlockMeta == 8) {
            aMetaData += 6000;
        } else {
            return false;
        }

        aWorld.setBlockState(blockPos, tOreBlock.getStateFromMeta(getHarvestData(tOreBlock, aMetaData)));

        GT_TileEntity_Ores tTileEntity = (GT_TileEntity_Ores) aWorld.getTileEntity(blockPos);
        tTileEntity.mMetaData = ((short) aMetaData);
        tTileEntity.mNatural = true;

        return true;
    }

    public void overrideOreBlockMaterial(Block aOverridingStoneBlock, byte aOverridingStoneMeta) {
        /*IBlockState blockState = worldObj.getBlockState(getPos());
        GT_Block_Ores_Abstract tOresBlock = (GT_Block_Ores_Abstract) blockState.getBlock();

        this.mMetaData = ((short) (int) (this.mMetaData % 1000L + this.mMetaData / 16000L * 16000L));
        if (aOverridingStoneBlock.isReplaceableOreGen(blockState, this.worldObj, getPos(), state -> state.getBlock() == Blocks.NETHERRACK)) {
            this.mMetaData = ((short) (this.mMetaData + 1000));
        } else if (aOverridingStoneBlock.isReplaceableOreGen(blockState, this.worldObj, getPos(), state -> state.getBlock() == Blocks.END_STONE)) {
            this.mMetaData = ((short) (this.mMetaData + 2000));
        } else if (aOverridingStoneBlock.isReplaceableOreGen(blockState, this.worldObj, getPos(), state -> state.getBlock() == GregTech_API.sBlockGranites)) {
            if (aOverridingStoneBlock == GregTech_API.sBlockGranites) {
                if (aOverridingStoneMeta < 8) {
                    this.mMetaData = ((short) (this.mMetaData + 3000));
                } else {
                    this.mMetaData = ((short) (this.mMetaData + 4000));
                }
            } else {
                this.mMetaData = ((short) (this.mMetaData + 3000));
            }
        } else {
            if (aOverridingStoneBlock == GregTech_API.sBlockStones) {
                if (aOverridingStoneMeta < 8) {
                    this.mMetaData = ((short) (this.mMetaData + 5000));
                } else {
                    this.mMetaData = ((short) (this.mMetaData + 6000));
                }
            } else {
                this.mMetaData = ((short) (this.mMetaData + 5000));
            }
        }
        this.worldObj.setBlockState(
                getPos(),
                tOresBlock.getStateFromMeta(
                getHarvestData(tOresBlock, this.mMetaData)), 0);*/
    }

    /*public void convertOreBlock(World aWorld, int aX, int aY, int aZ) {
        short aMeta = ((short) (int) (this.mMetaData % 1000 + (this.mMetaData / 16000 * 16000)));

        aWorld.setBlockState(new BlockPos(aX, aY, aZ), GregTech_API.sBlockOres1.getStateFromMeta(getHarvestData(aMeta)));
        TileEntity tTileEntity = aWorld.getTileEntity(new BlockPos(aX, aY, aZ));
        if (tTileEntity instanceof GT_TileEntity_Ores) {
            ((GT_TileEntity_Ores) tTileEntity).mMetaData = aMeta;
        }
    }*/

    public ArrayList<ItemStack> getDrops(Block aDroppedOre, int aFortune) {
        ArrayList<ItemStack> rList = new ArrayList<>();
        if (this.mMetaData <= 0) {
            rList.add(new ItemStack(Blocks.COBBLESTONE, 1, 0));
            return rList;
        }
        if (this.mMetaData < 16000) {
            rList.add(new ItemStack(aDroppedOre, 1, this.mMetaData));
            return rList;
        }
        Materials aMaterial = GregTech_API.sGeneratedMaterials[(this.mMetaData % 1000)];
        if (!this.mNatural) {
            aFortune = 0;
        }
        if (aMaterial != null) {
            Random tRandom = new Random(getPos().hashCode());
            ArrayList<ItemStack> tSelector = new ArrayList<>();


            ItemStack tStack = GT_OreDictUnificator.get(OrePrefixes.gemExquisite, aMaterial, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 1L);
            if (tStack != null) {
                for (int i = 0; i < 1; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.gemFlawless, aMaterial, GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L), 1L);
            if (tStack != null) {
                for (int i = 0; i < 2; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.gem, aMaterial, 1L);
            if (tStack != null) {
                for (int i = 0; i < 12; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.gemFlawed, aMaterial, GT_OreDictUnificator.get(OrePrefixes.crushed, aMaterial, 1L), 1L);
            if (tStack != null) {
                for (int i = 0; i < 5; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.crushed, aMaterial, 1L);
            if (tStack != null) {
                for (int i = 0; i < 10; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.gemChipped, aMaterial, GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L), 1L);
            if (tStack != null) {
                for (int i = 0; i < 5; i++) {
                    tSelector.add(tStack);
                }
            }
            tStack = GT_OreDictUnificator.get(OrePrefixes.dustImpure, aMaterial, 1L);
            if (tStack != null) {
                for (int i = 0; i < 10; i++) {
                    tSelector.add(tStack);
                }
            }
            if (tSelector.size() > 0) {
                int i = 0;
                for (int j = Math.max(1, aMaterial.mOreMultiplier + (aFortune > 0 ? tRandom.nextInt(1 + aFortune * aMaterial.mOreMultiplier) : 0) / 2); i < j; i++) {
                    rList.add(GT_Utility.copyAmount(1L, tSelector.get(tRandom.nextInt(tSelector.size()))));
                }
            }
            if (tRandom.nextInt(3 + aFortune) > 1) {
                Materials dustMat = ((GT_Block_Ores_Abstract) aDroppedOre).getDroppedDusts()[this.mMetaData / 1000 % 16];
                if (dustMat != null) rList.add(GT_OreDictUnificator.get(tRandom.nextInt(3) > 0 ? OrePrefixes.dustImpure : OrePrefixes.dust, dustMat, 1L));
            }
        }
        return rList;
    }

}
