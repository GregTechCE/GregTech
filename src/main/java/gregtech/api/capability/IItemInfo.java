package gregtech.api.capability;

import gregtech.api.util.ItemStackKey;

/**
 * Information about items in a storage network
 */
public interface IItemInfo {

    int getTotalItemAmount();

    ItemStackKey getItemStackKey();
}