package gregtech.common.tileentities.machines.basic;

import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.GT_Block_Ores;
import gregtech.common.blocks.GT_TileEntity_Ores;
import ic2.core.Ic2Items;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class GT_MetaTileEntity_SeismicProspector extends GT_MetaTileEntity_BasicMachine {

    boolean ready = false;

    public GT_MetaTileEntity_SeismicProspector(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "Place, activate with explosives(8 Dynamite, 1 Glyceryl, 4 TNT or 2 ITNT), use Data Stick", 1, 1, "Default.png", "", new ITexture[]{new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SIDE_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_TOP_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_ROCK_BREAKER_ACTIVE), new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_BOTTOM_ROCK_BREAKER)});
    }

    public GT_MetaTileEntity_SeismicProspector(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_SeismicProspector(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        if (aBaseMetaTileEntity.isServerSide()) {
            ItemStack aStack = aPlayer.getCurrentEquippedItem();
            if (!ready && (aStack != null) && (
            		(aStack.getItem() == Item.getItemFromBlock(Blocks.tnt) && aStack.stackSize > 3 ) || 
            		(aStack.getItem() == Ic2Items.industrialTnt.getItem()  && aStack.stackSize > 1 ) ||
            		(aStack.getItem() == Ic2Items.dynamite.getItem()  	   && aStack.stackSize > 7 ) ||
            		(GT_OreDictUnificator.getItemData(aStack).mMaterial.mMaterial == Materials.Glyceryl  && aStack.stackSize > 0 )
            		) ) {
                if ((!aPlayer.capabilities.isCreativeMode) && (aStack.stackSize != 111)) {
                	if(aStack.getItem() == Item.getItemFromBlock(Blocks.tnt)){
                    aStack.stackSize -= 4;
                	}else if(aStack.getItem() == Ic2Items.industrialTnt.getItem()){
                    aStack.stackSize -= 2;
                    }else if(aStack.getItem() == Ic2Items.dynamite.getItem()){
                    aStack.stackSize -= 8;
                    }else{
                    aStack.stackSize -= 1;
                    }
                }
                this.ready = true;
                this.mMaxProgresstime = 200;
            } else if (ready && mMaxProgresstime == 0 && aStack != null && aStack.stackSize == 1 && aStack.getItem() == ItemList.Tool_DataStick.getItem()) {
                this.ready = false;
                GT_Utility.ItemNBT.setBookTitle(aPlayer.getCurrentEquippedItem(), "Raw Prospection Data");
                List<String> tStringList = new ArrayList<String>();
                for (int i = this.getBaseMetaTileEntity().getYCoord(); i > 0; i--) {
                    for (int f = -2; f < 3; f++) {
                        for (int g = -2; g < 3; g++) {
                            Block tBlock = this.getBaseMetaTileEntity().getBlockOffset(f, -i, g);
                            if ((tBlock instanceof GT_Block_Ores)) {
                                TileEntity tTileEntity = getBaseMetaTileEntity().getWorld().getTileEntity(getBaseMetaTileEntity().getXCoord() + f, getBaseMetaTileEntity().getYCoord() + (-i), getBaseMetaTileEntity().getZCoord() + g);
                                if ((tTileEntity instanceof GT_TileEntity_Ores)) {
                                	if(((GT_TileEntity_Ores) tTileEntity).mMetaData < 16000){
                                    Materials tMaterial = GregTech_API.sGeneratedMaterials[(((GT_TileEntity_Ores) tTileEntity).mMetaData % 1000)];
                                    if ((tMaterial != null) && (tMaterial != Materials._NULL)) {
                                        if (!tStringList.contains(tMaterial.mDefaultLocalName)) {
                                            tStringList.add(tMaterial.mDefaultLocalName);
                                        }
                                    }
                                  }
                                }
                            } else {
                                int tMetaID = getBaseMetaTileEntity().getWorld().getBlockMetadata(getBaseMetaTileEntity().getXCoord() + f, getBaseMetaTileEntity().getYCoord() + (-i), getBaseMetaTileEntity().getZCoord() + g);
                                ItemData tAssotiation = GT_OreDictUnificator.getAssociation(new ItemStack(tBlock, 1, tMetaID));
                                if ((tAssotiation != null) && (tAssotiation.mPrefix.toString().startsWith("ore"))) {
                                    if (!tStringList.contains(tAssotiation.mMaterial.mMaterial.mDefaultLocalName)) {
                                        tStringList.add(tAssotiation.mMaterial.mMaterial.mDefaultLocalName);
                                    }
                                }
                            }
                        }
                    }
                }
                if(tStringList.size()<1){tStringList.add("No Ores found.");}
                FluidStack tFluid = GT_Utility.getUndergroundOil(getBaseMetaTileEntity().getWorld(), getBaseMetaTileEntity().getXCoord(), getBaseMetaTileEntity().getZCoord());
                String[] tStringArray = new String[tStringList.size()];
                {
                    for (int i = 0; i < tStringArray.length; i++) {
                        tStringArray[i] = tStringList.get(i);
                    }
                }
                GT_Utility.ItemNBT.setProspectionData(aPlayer.getCurrentEquippedItem(), this.getBaseMetaTileEntity().getXCoord(), this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord(), this.getBaseMetaTileEntity().getWorld().provider.dimensionId, tFluid, tStringArray);
            }
        }

        return true;
    }

}
