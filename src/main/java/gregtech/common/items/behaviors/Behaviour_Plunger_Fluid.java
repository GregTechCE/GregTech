package gregtech.common.items.behaviors;

import gregtech.api.GregTech_API;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

public class Behaviour_Plunger_Fluid
        extends Behaviour_None {
    private final int mCosts;
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.plunger.fluid", "Clears 1000 Liters of Fluid from Tanks");

    public Behaviour_Plunger_Fluid(int aCosts) {
        this.mCosts = aCosts;
    }

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if (aWorld.isRemote) {
            return false;
        }
        TileEntity aTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((aTileEntity instanceof IFluidHandler)) {
            for (ForgeDirection tDirection : ForgeDirection.VALID_DIRECTIONS) {
                if (((IFluidHandler) aTileEntity).drain(tDirection, 1000, false) != null) {
                    if ((aPlayer.capabilities.isCreativeMode) || (((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts))) {
                        ((IFluidHandler) aTileEntity).drain(tDirection, 1000, true);
                        GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(101)), 1.0F, -1.0F, aX, aY, aZ);
                        return true;
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
