package gregtech.common.items.behaviors;

import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_LanguageManager;
import ic2.api.crops.ICropTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class Behaviour_Sense
        extends Behaviour_None {
    private final int mCosts;
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.sense", "Rightclick to harvest Crop Sticks");

    public Behaviour_Sense(int aCosts) {
        this.mCosts = aCosts;
    }

    @Override
    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (aWorld.isRemote) {
            return false;
        }
        TileEntity tTileEntity = aWorld.getTileEntity(blockPos);
        if ((tTileEntity instanceof ICropTile)) {
            for (int i = -2; i < 3; i++) {
                for (int j = -2; j < 3; j++) {
                    for (int k = -2; k < 3; k++) {
                        if ((aStack.stackSize > 0) && (((tTileEntity = aWorld.getTileEntity(blockPos.add(i, j, k))) instanceof ICropTile)) && (((ICropTile) tTileEntity).performManualHarvest()) && (!aPlayer.capabilities.isCreativeMode)) {
                            ((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts / 20);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        return aList;
    }
}
