package gregtech.common.blocks;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.ITexturedTileEntity;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.api.world.GT_Worldgen_Ore_Normal;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

public class GT_TileEntity_Ores extends TileEntity implements ITexturedTileEntity {
    public short mMetaData = 0;
    public boolean mNatural = false;
    public boolean mBlocked = true;

    public static byte getHarvestData(short aMetaData) {
        Materials aMaterial = GregTech_API.sGeneratedMaterials[(aMetaData % 1000)];
        byte tByte = aMaterial == null ? 0 : (byte) Math.max((aMetaData % 16000 / 1000 == 3) || (aMetaData % 16000 / 1000 == 4) ? 3 : 0, Math.min(7, aMaterial.mToolQuality - (aMetaData < 16000 ? 0 : 1)));
        if(GT_Mod.gregtechproxy.mChangeHarvestLevels ){
            tByte = aMaterial == null ? 0 : (byte) Math.max((aMetaData % 16000 / 1000 == 3) || (aMetaData % 16000 / 1000 == 4) ? GT_Mod.gregtechproxy.mGraniteHavestLevel : 0, Math.min(GT_Mod.gregtechproxy.mMaxHarvestLevel, GT_Mod.gregtechproxy.mHarvestLevel[aMaterial.mMetaItemSubID] - (aMetaData < 16000 ? 0 : 1)));
        }
        return tByte;
    }

    public static boolean setOreBlock(World aWorld, int aX, int aY, int aZ, int aMetaData, boolean isSmallOre) {
        return setOreBlock(aWorld, aX, aY, aZ, aMetaData, isSmallOre, false);
    }

    public static boolean setOreBlock(World aWorld, int aX, int aY, int aZ, int aMetaData, boolean isSmallOre, boolean air) {
        if (!air) {
            aY = Math.min(aWorld.getActualHeight(), Math.max(aY, 1));
        }
        BlockPos blockPos = new BlockPos(aX, aY, aZ);
        IBlockState blockState = aWorld.getBlockState(blockPos);
        Block tBlock = blockState.getBlock();
        Block tOreBlock = GregTech_API.sBlockOres1;
        int BlockMeta = tBlock.getMetaFromState(blockState);
        String BlockName = tBlock.getUnlocalizedName();
        aMetaData += isSmallOre ? 16000 : 0;
        if ((aMetaData > 0) && ((tBlock != Blocks.AIR) || air)) {
            if (BlockName.equals("tile.igneousStone")) {
                if (GregTech_API.sBlockOresUb1 != null) {
                    tOreBlock = GregTech_API.sBlockOresUb1;
                    aMetaData += (BlockMeta * 1000);
                }
            } else if (BlockName.equals("tile.metamorphicStone")) {
                if (GregTech_API.sBlockOresUb2 != null) {
                    tOreBlock = GregTech_API.sBlockOresUb2;
                    aMetaData += (BlockMeta * 1000);
                }
            } else if (BlockName.equals("tile.sedimentaryStone")) {
                if (GregTech_API.sBlockOresUb3 != null) {
                    tOreBlock = GregTech_API.sBlockOresUb3;
                    aMetaData += (BlockMeta * 1000);
                }
            } else if (tBlock.isReplaceableOreGen(blockState, aWorld, blockPos, GT_Worldgen_Ore_Normal.STONE)) {
                aMetaData += 1000;
            }
            aWorld.setBlockState(blockPos, tOreBlock.getStateFromMeta(getHarvestData((short) aMetaData)), 0);
            TileEntity tTileEntity = aWorld.getTileEntity(blockPos);
            if ((tTileEntity instanceof GT_TileEntity_Ores)) {
                ((GT_TileEntity_Ores) tTileEntity).mMetaData = ((short) aMetaData);
                ((GT_TileEntity_Ores) tTileEntity).mNatural = true;
            }
            return true;
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound aNBT) {
        super.readFromNBT(aNBT);
        this.mMetaData = aNBT.getShort("m");
        this.mNatural = aNBT.getBoolean("n");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound aNBT) {
        super.writeToNBT(aNBT);
        aNBT.setShort("m", this.mMetaData);
        aNBT.setBoolean("n", this.mNatural);
        return aNBT;
    }

    public void onUpdated() {
        if ((!this.worldObj.isRemote) && (this.mBlocked)) {
            this.mBlocked = false;
            GT_Values.NW.sendPacketToAllPlayersInRange(this.worldObj, new GT_Packet_Ores(getPos(), this.mMetaData), getPos().getX(), getPos().getZ());
        }
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        if (!this.worldObj.isRemote) {
            updateBlocked();
            if (!this.mBlocked) {
                GT_Values.NW.sendPacketToAllPlayersInRange(this.worldObj, new GT_Packet_Ores(getPos(), this.mMetaData), getPos().getX(), getPos().getZ());
            }
        }
        return null;
    }

    public void updateBlocked() {
        mBlocked = !(GT_Utility.isOpaqueBlock(worldObj, getPos().up()) ||
                GT_Utility.isOpaqueBlock(worldObj, getPos().down()) ||
                GT_Utility.isOpaqueBlock(worldObj, getPos().east()) ||
                GT_Utility.isOpaqueBlock(worldObj, getPos().west()) ||
                GT_Utility.isOpaqueBlock(worldObj, getPos().south()) ||
                GT_Utility.isOpaqueBlock(worldObj, getPos().north()));
     }

    public void overrideOreBlockMaterial(Block aOverridingStoneBlock, byte aOverridingStoneMeta) {
            this.mMetaData = ((short) (int) (this.mMetaData % 1000L + this.mMetaData / 16000L * 16000L));
            if (aOverridingStoneBlock.isReplaceableOreGen(worldObj.getBlockState(getPos()), this.worldObj, getPos(), state -> state.getBlock() == Blocks.NETHERRACK)) {
                this.mMetaData = ((short) (this.mMetaData + 1000));
            } else if (aOverridingStoneBlock.isReplaceableOreGen(worldObj.getBlockState(getPos()), this.worldObj, getPos(), state -> state.getBlock() == Blocks.END_STONE)) {
                this.mMetaData = ((short) (this.mMetaData + 2000));
            } else if (aOverridingStoneBlock.isReplaceableOreGen(worldObj.getBlockState(getPos()), this.worldObj, getPos(), state -> state.getBlock() == GregTech_API.sBlockGranites)) {
                if (aOverridingStoneBlock == GregTech_API.sBlockGranites) {
                    if (aOverridingStoneMeta < 8) {
                        this.mMetaData = ((short) (this.mMetaData + 3000));
                    } else {
                        this.mMetaData = ((short) (this.mMetaData + 4000));
                    }
                } else {
                    this.mMetaData = ((short) (this.mMetaData + 3000));
                }
            } else if (aOverridingStoneBlock.isReplaceableOreGen(worldObj.getBlockState(getPos()), this.worldObj, getPos(), state -> state.getBlock() == GregTech_API.sBlockStones)) {
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
            this.worldObj.setBlockState(getPos(), getBlockType().getStateFromMeta(getHarvestData(this.mMetaData)), 0);
    }

    public void convertOreBlock(World aWorld, int aX, int aY, int aZ) {
        short aMeta = ((short) (int) (this.mMetaData % 1000 + (this.mMetaData / 16000 * 16000)));
        aWorld.setBlockState(new BlockPos(aX, aY, aZ), GregTech_API.sBlockOres1.getStateFromMeta(getHarvestData(aMeta)));
        TileEntity tTileEntity = aWorld.getTileEntity(new BlockPos(aX, aY, aZ));
        if (tTileEntity instanceof GT_TileEntity_Ores) {
            ((GT_TileEntity_Ores) tTileEntity).mMetaData = aMeta;
        }
    }

    public short getMetaData() {
        return this.mMetaData;
    }

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

    public ITexture[] getTexture(Block aBlock, byte aSide) {
        Materials aMaterial = GregTech_API.sGeneratedMaterials[(this.mMetaData % 1000)];
        if ((aMaterial != null) && (this.mMetaData < 32000)) {
            GT_RenderedTexture aIconSet = new GT_RenderedTexture(aMaterial.mIconSet.mTextures[this.mMetaData / 16000 == 0 ? OrePrefixes.ore.mTextureIndex : OrePrefixes.oreSmall.mTextureIndex], aMaterial.mRGBa);
            if (aBlock instanceof GT_Block_Ores_Abstract) {
                return new ITexture[]{((GT_Block_Ores_Abstract) aBlock).getTextureSet()[((this.mMetaData / 1000) % 16)], aIconSet};
            }
        }
        return new ITexture[]{
                new GT_RenderedTexture("minecraft:blocks/stone", null),
                new GT_RenderedTexture(gregtech.api.enums.TextureSet.SET_NONE.mTextures[OrePrefixes.ore.mTextureIndex])};
    }
}
