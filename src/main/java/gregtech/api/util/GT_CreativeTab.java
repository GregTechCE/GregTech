package gregtech.api.util;

import gregtech.api.enums.ItemList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GT_CreativeTab extends CreativeTabs {
    public GT_CreativeTab(String aName, String aLocalName) {
        super("GregTech." + aName);
        GT_LanguageManager.addStringLocalization("itemGroup.GregTech." + aName, aLocalName);
    }

    @Override
    public ItemStack getIconItemStack() {
        return ItemList.Tool_Cheat.get(1);
    }

    @Override
    public Item getTabIconItem() {
        return Items.GOLD_INGOT;
    }
}