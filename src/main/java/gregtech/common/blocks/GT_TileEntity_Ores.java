package gregtech.common.blocks;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.ITexturedTileEntity;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.XSTR;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
        Block tBlock = aWorld.getBlock(aX, aY, aZ);
        Block tOreBlock = GregTech_API.sBlockOres1;
        int BlockMeta = aWorld.getBlockMetadata(aX, aY, aZ);
        String BlockName = tBlock.getUnlocalizedName();
        aMetaData += isSmallOre ? 16000 : 0;
        if ((aMetaData > 0) && ((tBlock != Blocks.air) || air)) {
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
            } else if (BlockName.equals("tile.moonBlock") && (BlockMeta == 3 || BlockMeta == 4)) {
                if (GregTech_API.sBlockOresGC != null) {
                    switch (BlockMeta) {
                        case 3: tOreBlock = GregTech_API.sBlockOresGC; break;
                        case 4: aMetaData += 1000; tOreBlock = GregTech_API.sBlockOresGC; break;
                    }
                }
            } else if (BlockName.equals("tile.mars") && (BlockMeta == 6 || BlockMeta == 9)) {
                if (GregTech_API.sBlockOresGC != null) {
                    switch (BlockMeta) {
                        case 6: aMetaData += 2000; tOreBlock = GregTech_API.sBlockOresGC; break;
                        case 9: aMetaData += 3000; tOreBlock = GregTech_API.sBlockOresGC; break;
                    }
                }
            } else if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.netherrack)) {
                aMetaData += 1000;
            } else if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.end_stone)) {
                aMetaData += 2000;
            } else if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, GregTech_API.sBlockGranites)) {
                if (tBlock == GregTech_API.sBlockGranites) {
                    if (aWorld.getBlockMetadata(aX, aY, aZ) < 8) {
                        aMetaData += 3000;
                    } else {
                        aMetaData += 4000;
                    }
                } else {
                    aMetaData += 3000;
                }
            } else if (tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, GregTech_API.sBlockStones)) {
                if (tBlock == GregTech_API.sBlockStones) {
                    if (aWorld.getBlockMetadata(aX, aY, aZ) < 8) {
                        aMetaData += 5000;
                    } else {
                        aMetaData += 6000;
                    }
                } else {
                    aMetaData += 5000;
                }
            } else if (!tBlock.isReplaceableOreGen(aWorld, aX, aY, aZ, Blocks.stone)) {
                return false;
            }
            aWorld.setBlock(aX, aY, aZ, tOreBlock, getHarvestData((short) aMetaData), 0);
            TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
            if ((tTileEntity instanceof GT_TileEntity_Ores)) {
                ((GT_TileEntity_Ores) tTileEntity).mMetaData = ((short) aMetaData);
                ((GT_TileEntity_Ores) tTileEntity).mNatural = true;
            }
            return true;
        }
        return false;
    }

    public void readFromNBT(NBTTagCompound aNBT) {
        super.readFromNBT(aNBT);
        this.mMetaData = aNBT.getShort("m");
        this.mNatural = aNBT.getBoolean("n");
    }

    public void writeToNBT(NBTTagCompound aNBT) {
        super.writeToNBT(aNBT);
        aNBT.setShort("m", this.mMetaData);
        aNBT.setBoolean("n", this.mNatural);
    }

    public void onUpdated() {
        if ((!this.worldObj.isRemote) && (this.mBlocked)) {
            this.mBlocked = false;
            GT_Values.NW.sendPacketToAllPlayersInRange(this.worldObj, new GT_Packet_Ores(this.xCoord, (short) this.yCoord, this.zCoord, this.mMetaData), this.xCoord, this.zCoord);
        }
    }

    public Packet getDescriptionPacket() {
        if (!this.worldObj.isRemote) {
            if ((this.mBlocked == (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord + 1, this.yCoord, this.zCoord)) && (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord - 1, this.yCoord, this.zCoord)) && (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord)) && (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord)) && (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord, this.yCoord, this.zCoord + 1)) && (GT_Utility.isOpaqueBlock(this.worldObj, this.xCoord, this.yCoord, this.zCoord - 1)) ? 1 : 0) == 0) {
                GT_Values.NW.sendPacketToAllPlayersInRange(this.worldObj, new GT_Packet_Ores(this.xCoord, (short) this.yCoord, this.zCoord, this.mMetaData), this.xCoord, this.zCoord);
            }
        }
        return null;
    }

    public void overrideOreBlockMaterial(Block aOverridingStoneBlock, byte aOverridingStoneMeta) {
            this.mMetaData = ((short) (int) (this.mMetaData % 1000L + this.mMetaData / 16000L * 16000L));
            if (aOverridingStoneBlock.isReplaceableOreGen(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.netherrack)) {
                this.mMetaData = ((short) (this.mMetaData + 1000));
            } else if (aOverridingStoneBlock.isReplaceableOreGen(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.end_stone)) {
                this.mMetaData = ((short) (this.mMetaData + 2000));
            } else if (aOverridingStoneBlock.isReplaceableOreGen(this.worldObj, this.xCoord, this.yCoord, this.zCoord, GregTech_API.sBlockGranites)) {
                if (aOverridingStoneBlock == GregTech_API.sBlockGranites) {
                    if (aOverridingStoneMeta < 8) {
                        this.mMetaData = ((short) (this.mMetaData + 3000));
                    } else {
                        this.mMetaData = ((short) (this.mMetaData + 4000));
                    }
                } else {
                    this.mMetaData = ((short) (this.mMetaData + 3000));
                }
            } else if (aOverridingStoneBlock.isReplaceableOreGen(this.worldObj, this.xCoord, this.yCoord, this.zCoord, GregTech_API.sBlockStones)) {
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
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, getHarvestData(this.mMetaData), 0);
    }

    public void convertOreBlock(World aWorld, int aX, int aY, int aZ) {
        short aMeta = ((short) (int) (this.mMetaData % 1000 + (this.mMetaData / 16000 * 16000)));
        aWorld.setBlock(aX, aY, aZ, GregTech_API.sBlockOres1);
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if (tTileEntity instanceof GT_TileEntity_Ores) {
            ((GT_TileEntity_Ores) tTileEntity).mMetaData = aMeta;
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, getHarvestData(aMeta), 0);
        }
    }

    public short getMetaData() {
        return this.mMetaData;
    }

    public boolean canUpdate() {
        return false;
    }

    public ArrayList<ItemStack> getDrops(Block aDroppedOre, int aFortune) {
        ArrayList<ItemStack> rList = new ArrayList();
        if (this.mMetaData <= 0) {
            rList.add(new ItemStack(Blocks.cobblestone, 1, 0));
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
            Random tRandom = new XSTR(this.xCoord ^ this.yCoord ^ this.zCoord);
            ArrayList<ItemStack> tSelector = new ArrayList();


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
                    rList.add(GT_Utility.copyAmount(1L, new Object[]{tSelector.get(tRandom.nextInt(tSelector.size()))}));
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
        return new ITexture[]{new GT_CopiedBlockTexture(Blocks.stone, 0, 0), new GT_RenderedTexture(gregtech.api.enums.TextureSet.SET_NONE.mTextures[OrePrefixes.ore.mTextureIndex])};
    }
}
