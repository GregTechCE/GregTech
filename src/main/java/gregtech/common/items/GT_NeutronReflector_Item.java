package gregtech.common.items;

import gregtech.api.items.GT_Generic_Item;
import gregtech.api.items.GT_RadioactiveCellIC_Item;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.ItemReactorMOX;
import net.minecraft.item.ItemStack;

public class GT_NeutronReflector_Item
        extends GT_Generic_Item
        implements IReactorComponent {
    public GT_NeutronReflector_Item(String aUnlocalized, String aEnglish, int aMaxDamage) {
        super(aUnlocalized, aEnglish, "Undestructable");
        this.setMaxStackSize(64);
        this.setMaxDamage(aMaxDamage);
    }

    public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        if (!heatrun) {
            if ((pulsingStack.getItem() instanceof ItemReactorMOX)) {
                float breedereffectiveness = reactor.getHeat() / reactor.getMaxHeat();
                float ReaktorOutput = 4.0F * breedereffectiveness + 1.0F;
                reactor.addOutput(ReaktorOutput);
            } else {
                float tEnergy = 1.0f;
                if (pulsingStack.getItem() instanceof GT_RadioactiveCellIC_Item) {
                    tEnergy = (float) ((GT_RadioactiveCellIC_Item) pulsingStack.getItem()).sEnergy;
                }
                reactor.addOutput(tEnergy);
            }
        }
        return true;
    }

    public boolean canStoreHeat(IReactor aReactor, ItemStack aStack, int x, int y) {
        return false;
    }

    public int getMaxHeat(IReactor aReactor, ItemStack aStack, int x, int y) {
        return 0;
    }

    public int getCurrentHeat(IReactor aReactor, ItemStack aStack, int x, int y) {
        return 0;
    }

    public float influenceExplosion(IReactor aReactor, ItemStack aStack) {
        return -1.0F;
    }

    public int alterHeat(IReactor aReactor, ItemStack aStack, int x, int y, int aHeat) {
        return aHeat;
    }

    public void processChamber(IReactor aReactor, ItemStack aStack, int x, int y, boolean aHeatRun) {
    }
}
