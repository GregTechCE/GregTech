package gregtech.common.tileentities.machines.basic;


import java.util.Iterator;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_1by1;
import gregtech.api.gui.GT_GUIContainer_1by1;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class GT_MetaTileEntity_CuringOven
        extends GT_MetaTileEntity_BasicMachine {

    public GT_MetaTileEntity_CuringOven(int aID, String aName, String aNameRegional, int aTier) {
        super(aID, aName, aNameRegional, aTier, 1, "Heats tools for hardening", 1, 1, "E_Oven.png", "", new ITexture[]{new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_SIDE_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_SIDE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_FRONT_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_FRONT")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_TOP_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_TOP")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_BOTTOM_ACTIVE")), new GT_RenderedTexture(new Textures.BlockIcons.CustomIcon("basicmachines/ELECTRIC_OVEN/OVERLAY_BOTTOM"))});
    }

    public GT_MetaTileEntity_CuringOven(String aName, int aTier, String aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
        super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
    }

    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GT_MetaTileEntity_CuringOven(this.mName, this.mTier, this.mDescription, this.mTextures, this.mGUIName, this.mNEIName);
    }

    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return (super.allowPutStack(aBaseMetaTileEntity, aIndex, aSide, aStack)) && (ItemList.Cell_Empty.isStackEqual(aStack));
    }

    public boolean isFluidInputAllowed(FluidStack aFluid) {
        return false;
    }
    
    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_1by1(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_1by1(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        if (aBaseMetaTileEntity.isServerSide()) {
            for (ItemStack tStack : mInventory)
                if (tStack!=null&&tStack.getItem() instanceof GT_MetaGenerated_Tool &&getBaseMetaTileEntity().getStoredEU()>0) {
                	getBaseMetaTileEntity().decreaseStoredEnergyUnits(24, true);
                    NBTTagCompound aNBT = tStack.getTagCompound();
                    if (aNBT != null) {
                	int tHeat = 300;
                	long tWorldTime = getBaseMetaTileEntity().getWorld().getTotalWorldTime();
                        aNBT = aNBT.getCompoundTag("GT.ToolStats");
                        if (aNBT != null&&aNBT.hasKey("Heat")) {
                        	tHeat = aNBT.getInteger("Heat");
                        	if(aNBT.hasKey("HeatTime")){
                        		long tHeatTime = aNBT.getLong("HeatTime");
                        		if(tWorldTime>(tHeatTime+10)){
                        			tHeat = (int) (tHeat - ((tWorldTime-tHeatTime)/10));
                        			if(tHeat<300)tHeat=300;
                        		}
                        	}
                        }
                        tHeat++;
                        if(aNBT!=null){
                        	aNBT.setInteger("Heat", tHeat);
                        	aNBT.setLong("HeatTime", tWorldTime);}
                        if(tHeat>GT_MetaGenerated_Tool.getPrimaryMaterial(tStack).mMeltingPoint){mInventory[0]=null;}
                    }
                	
                }
        }
    }
}