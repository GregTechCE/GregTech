package gregtech.common.items;

import gregtech.api.GregTech_API;
import gregtech.api.items.GT_Generic_Item;
import gregtech.api.items.GT_RadioactiveCellIC_Item;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.item.ItemStack;

public class GT_NeutronReflector_Item
        extends GT_Generic_Item
        implements IReactorComponent {
    public GT_NeutronReflector_Item(String aUnlocalized, String aEnglish, int aMaxDamage) {
        super(aUnlocalized, aEnglish, "Undestructable");
        this.setMaxStackSize(64);
        this.setMaxDamage(aMaxDamage);
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack yourStack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        if (!heatrun) {
            if (!GregTech_API.mIC2Classic&&(pulsingStack.getItem() instanceof ic2.core.item.reactor.ItemReactorMOX)) {
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

    @Override
    public boolean canStoreHeat(ItemStack aStack, IReactor reactor, int x, int y) {
        return false;
    }

    @Override
    public int getMaxHeat(ItemStack aStack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int getCurrentHeat(ItemStack aStack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public float influenceExplosion(ItemStack aStack, IReactor reactor) {
        return -1.0F;
    }

    @Override
    public int alterHeat(ItemStack aStack, IReactor reactor, int x, int y, int aHeat) {
        return aHeat;
    }

    @Override
    public void processChamber(ItemStack aStack, IReactor reactor, int x, int y, boolean aHeatRun) {
    }

    @Override
    public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
        return true;
    }
}
