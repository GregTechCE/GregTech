package gregtech.api.creativetabs;

import net.minecraft.creativetab.CreativeTabs;

public abstract class BaseCreativeTab extends CreativeTabs {

    private final boolean hasSearchBar;

    public BaseCreativeTab(String TabName) {
        this(TabName, false);
    }

    public BaseCreativeTab(String TabName, boolean HasSearchBar) {
        super(TabName);
        this.hasSearchBar = HasSearchBar;

        if (hasSearchBar)
            setBackgroundImageName("item_search.png");
    }

    @Override
    public boolean hasSearchBar() {
        return hasSearchBar;
    }
}
