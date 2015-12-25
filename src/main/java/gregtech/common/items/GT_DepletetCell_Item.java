package gregtech.common.items;

import gregtech.api.items.GT_RadioactiveCellIC_Item;
import ic2.api.reactor.IReactor;
import net.minecraft.item.ItemStack;

public class GT_DepletetCell_Item extends GT_RadioactiveCellIC_Item {

    public GT_DepletetCell_Item(String aUnlocalized, String aEnglish, int aRadiation) {
        super(aUnlocalized, aEnglish, 1, 1, 0, aRadiation, 0, null, false);
    }

    public void processChamber(IReactor paramIReactor, ItemStack paramItemStack, int paramInt1, int paramInt2, boolean paramBoolean) {
    }

    public boolean acceptUraniumPulse(IReactor paramIReactor, ItemStack paramItemStack1, ItemStack paramItemStack2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean) {
        return false;
    }

    public boolean canStoreHeat(IReactor paramIReactor, ItemStack paramItemStack, int paramInt1, int paramInt2) {
        return false;
    }

    public int getMaxHeat(IReactor paramIReactor, ItemStack paramItemStack, int paramInt1, int paramInt2) {
        return 0;
    }

    public int getCurrentHeat(IReactor paramIReactor, ItemStack paramItemStack, int paramInt1, int paramInt2) {
        return 0;
    }

    public int alterHeat(IReactor paramIReactor, ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3) {
        return 0;
    }

    public float influenceExplosion(IReactor paramIReactor, ItemStack paramItemStack) {
        return 0.0F;
    }
}
