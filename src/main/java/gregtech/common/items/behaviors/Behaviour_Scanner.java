package gregtech.common.items.behaviors;

import gregtech.api.GregTechAPI;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Behaviour_Scanner extends Behaviour_None {

    public static final IItemBehaviour<GT_MetaBase_Item> INSTANCE = new Behaviour_Scanner();
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.scanning", "Can scan Blocks in World");

    @Override
    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (((aPlayer instanceof EntityPlayerMP)) && (aItem.canUse(aStack, 20000.0D))) {
            ArrayList<String> tList = new ArrayList<>();
            if (aItem.use(aStack, GT_Utility.getCoordinateScan(tList, aPlayer, aWorld, 1, pos, side, hitX, hitY, hitZ), aPlayer)) {
                for (String aTList : tList) {
                    GT_Utility.sendChatToPlayer(aPlayer, aTList);
                }
            }
            GT_Utility.doSoundAtClient(GregTechAPI.sSoundList.get(108), 1, 1.0F, pos);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        return aList;
    }
}
