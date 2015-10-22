package gregtech.common.items.behaviors;

import gregtech.api.GregTech_API;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Behaviour_Crowbar
        extends Behaviour_None {
    private final int mVanillaCosts;
    private final int mEUCosts;

    public Behaviour_Crowbar(int aVanillaCosts, int aEUCosts) {
        this.mVanillaCosts = aVanillaCosts;
        this.mEUCosts = aEUCosts;
    }

    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if (aWorld.isRemote) {
            return false;
        }
        if (GT_ModHandler.getModItem("Railcraft", "fluid.creosote.bucket", 1L) != null) {
            return false;
        }
        Block aBlock = aWorld.getBlock(aX, aY, aZ);
        if (aBlock == null) {
            return false;
        }
        byte aMeta = (byte) aWorld.getBlockMetadata(aX, aY, aZ);
        if (aBlock == Blocks.rail) {
            if (GT_ModHandler.damageOrDechargeItem(aStack, this.mVanillaCosts, this.mEUCosts, aPlayer)) {
                aWorld.isRemote = true;
                aWorld.setBlock(aX, aY, aZ, aBlock, (aMeta + 1) % 10, 0);
                aWorld.isRemote = false;
                GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(0)), 1.0F, -1.0F, aX, aY, aZ);
            }
            return true;
        }
        if ((aBlock == Blocks.detector_rail) || (aBlock == Blocks.activator_rail) || (aBlock == Blocks.golden_rail)) {
            if (GT_ModHandler.damageOrDechargeItem(aStack, this.mVanillaCosts, this.mEUCosts, aPlayer)) {
                aWorld.isRemote = true;
                aWorld.setBlock(aX, aY, aZ, aBlock, aMeta / 8 * 8 + (aMeta % 8 + 1) % 6, 0);
                aWorld.isRemote = false;
                GT_Utility.sendSoundToPlayers(aWorld, (String) GregTech_API.sSoundList.get(Integer.valueOf(0)), 1.0F, -1.0F, aX, aY, aZ);
            }
            return true;
        }
        return false;
    }
}
