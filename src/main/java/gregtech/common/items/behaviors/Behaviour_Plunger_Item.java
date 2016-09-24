package gregtech.common.items.behaviors;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.metatileentity.IMetaTileEntityItemPipe;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Behaviour_Plunger_Item
        extends Behaviour_None {
    private final int mCosts;
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.plunger.item", "Clears Items from Pipes");

    public Behaviour_Plunger_Item(int aCosts) {
        this.mCosts = aCosts;
    }

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if (aWorld.isRemote) {
            return false;
        }
        TileEntity aTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((aTileEntity instanceof IGregTechTileEntity)) {
            IMetaTileEntity tMetaTileEntity = ((IGregTechTileEntity) aTileEntity).getMetaTileEntity();
            if ((tMetaTileEntity instanceof IMetaTileEntityItemPipe)) {
                for (Object tTileEntity : GT_Utility.sortMapByValuesAcending(IMetaTileEntityItemPipe.Util.scanPipes((IMetaTileEntityItemPipe) tMetaTileEntity, new ConcurrentHashMap(), 0L, false, true)).keySet())

                {
                    int i = 0;
                    for (int j = ((IMetaTileEntityItemPipe) tTileEntity).getSizeInventory(); i < j; i++) {
                        if (((IMetaTileEntityItemPipe) tTileEntity).isValidSlot(i)) {
                            if ((((IMetaTileEntityItemPipe) tTileEntity).getStackInSlot(i) != null) && (
                                    (aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts)))) {
                                ItemStack tStack = ((IMetaTileEntityItemPipe) tTileEntity).decrStackSize(i, 64);
                                if (tStack != null) {
                                    EntityItem tEntity = new EntityItem(aWorld, ((IGregTechTileEntity) aTileEntity).getOffsetX((byte) aSide, 1) + 0.5D, ((IGregTechTileEntity) aTileEntity).getOffsetY((byte) aSide, 1) + 0.5D, ((IGregTechTileEntity) aTileEntity).getOffsetZ((byte) aSide, 1) + 0.5D, tStack);
                                    tEntity.motionX = 0.0D;
                                    tEntity.motionY = 0.0D;
                                    tEntity.motionZ = 0.0D;
                                    aWorld.spawnEntityInWorld(tEntity);
                                    GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        return aList;
    }
}
