package gregtech.common.items;

import gregtech.api.items.armor.ArmorMetaItem;
import gregtech.common.items.armor.ArmorLogicRebreather;

public class MetaArmor extends ArmorMetaItem<ArmorMetaItem<?>.ArmorMetaValueItem> {

    @Override
    public void registerSubItems() {
        MetaItems.REBREATHER = addItem(0, "rebreather").setArmorLogic(new ArmorLogicRebreather(16));
    }
}
