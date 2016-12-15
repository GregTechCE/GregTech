package gregtech.api.items;


import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.item.ItemStack;

public class GT_CoolantCellIC_Item extends GT_CoolantCell_Item implements IReactorComponent {
    public GT_CoolantCellIC_Item(String aUnlocalized, String aEnglish, int aMaxStore) {
        super(aUnlocalized, aEnglish, aMaxStore);
    }

    public void processChamber(IReactor aReactor, ItemStack aStack, int x, int y, boolean aHeatRun) {
    }

    public boolean acceptUraniumPulse(IReactor aReactor, ItemStack aStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean aHeatRun) {
        return false;
    }

    public boolean canStoreHeat(IReactor aReactor, ItemStack aStack, int x, int y) {
        if (aReactor.isFluidCooled() && (getControlTagOfStack(aStack)) != 0) {
            return false;
        }
        return true;
    }

    public int getMaxHeat(IReactor aReactor, ItemStack aStack, int x, int y) {
        return this.heatStorage;
    }

    public int getCurrentHeat(IReactor aReactor, ItemStack aStack, int x, int y) {
        return getHeatOfStack(aStack);
    }

    public float influenceExplosion(IReactor aReactor, ItemStack aStack) {
        return 1.0F + this.heatStorage / 30000.0F;
    }

    public int alterHeat(IReactor aReactor, ItemStack aStack, int x, int y, int aHeat) {
        int tHeat = getHeatOfStack(aStack);
        if ((tHeat == 0) && (getControlTagOfStack(aStack) != 0)) {
            setControlTagOfStack(aStack, 0);
        }
        tHeat += aHeat;
        if (tHeat > this.heatStorage) {
            aReactor.setItemAt(x, y, (ItemStack) null);
            aHeat = this.heatStorage - tHeat + 1;
        } else {
            if (tHeat < 0) {
                aHeat = tHeat;
                tHeat = 0;
            } else {
                aHeat = 0;
            }
            if ((tHeat > 0) && (getControlTagOfStack(aStack) == 0) && (!aReactor.isFluidCooled())) {
                setControlTagOfStack(aStack, 1);
            }
            setHeatForStack(aStack, tHeat);
        }
        return aHeat;
    }

    @Override
    public void processChamber(ItemStack itemStack, IReactor iReactor, int i, int i1, boolean b) {
        processChamber(iReactor, itemStack, i, i1, b);
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack itemStack, IReactor iReactor, ItemStack itemStack1, int i, int i1, int i2, int i3, boolean b) {
        return acceptUraniumPulse(iReactor, itemStack, itemStack1, i, i1, i2, i3, b);
    }

    @Override
    public boolean canStoreHeat(ItemStack itemStack, IReactor iReactor, int i, int i1) {
        return canStoreHeat(iReactor, itemStack, i, i1);
    }

    @Override
    public int getMaxHeat(ItemStack itemStack, IReactor iReactor, int i, int i1) {
        return getMaxHeat(iReactor, itemStack, i, i1);
    }

    @Override
    public int getCurrentHeat(ItemStack itemStack, IReactor iReactor, int i, int i1) {
        return getCurrentHeat(iReactor, itemStack, i, i1);
    }

    @Override
    public int alterHeat(ItemStack itemStack, IReactor iReactor, int i, int i1, int i2) {
        return alterHeat(iReactor, itemStack, i, i1, i2);
    }

    @Override
    public float influenceExplosion(ItemStack itemStack, IReactor iReactor) {
        return influenceExplosion(iReactor, itemStack);
    }

    @Override
    public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
        return true;
    }
}
