package gregtech.common.inventory;

import gregtech.api.util.ItemStackKey;

public interface IItemInfo {

    int getTotalItemAmount();

    ItemStackKey getItemStackKey();
}
