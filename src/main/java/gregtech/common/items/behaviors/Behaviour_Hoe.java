package gregtech.common.items.behaviors;

import cpw.mods.fml.common.eventhandler.Event;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import java.util.List;

public class Behaviour_Hoe
        extends Behaviour_None {
    private final int mCosts;
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.hoe", "Can till Dirt");

    public Behaviour_Hoe(int aCosts) {
        this.mCosts = aCosts;
    }

    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        if (!aPlayer.canPlayerEdit(aX, aY, aZ, aSide, aStack)) {
            return false;
        }
        UseHoeEvent event = new UseHoeEvent(aPlayer, aStack, aWorld, aX, aY, aZ);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return false;
        }
        if (event.getResult() == Event.Result.ALLOW) {
            if (!aPlayer.capabilities.isCreativeMode) {
                ((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts);
            }
            return true;
        }
        Block aBlock = aWorld.getBlock(aX, aY, aZ);
        if ((aSide != 0) && (GT_Utility.isBlockAir(aWorld, aX, aY + 1, aZ)) && ((aBlock == Blocks.grass) || (aBlock == Blocks.dirt))) {
            aWorld.playSoundEffect(aX + 0.5F, aY + 0.5F, aZ + 0.5F, Blocks.farmland.stepSound.getStepResourcePath(), (Blocks.farmland.stepSound.getVolume() + 1.0F) / 2.0F, Blocks.farmland.stepSound.getPitch() * 0.8F);
            if (aWorld.isRemote) {
                return true;
            }
            aWorld.setBlock(aX, aY, aZ, Blocks.farmland);
            if (!aPlayer.capabilities.isCreativeMode) {
                ((GT_MetaGenerated_Tool) aItem).doDamage(aStack, this.mCosts);
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
