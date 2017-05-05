package gregtech.common.blocks.itemblocks;

import gregtech.api.GregTech_API;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class GT_Item_Stones_Abstract extends ItemBlock {

    private final String mNoMobsToolTip = GT_LanguageManager.addStringLocalization("gt.nomobspawnsonthisblock", "Mobs cannot Spawn on this Block");

    public GT_Item_Stones_Abstract(Block par1) {
        super(par1);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return GT_LanguageManager.getTranslation(getUnlocalizedName(stack) + ".name");
    }

    @Override
    public String getUnlocalizedName(ItemStack aStack) {
        return this.block.getUnlocalizedName() + "." + getDamage(aStack);
    }

    @Override
    public int getMetadata(int aMeta) {
        return aMeta;
    }

    @Override
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List<String> aList, boolean aF3_H) {
        super.addInformation(aStack, aPlayer, aList, aF3_H);
        if (aStack.getItemDamage() % 8 >= 3) {
            aList.add(this.mNoMobsToolTip);
        }
    }
}
