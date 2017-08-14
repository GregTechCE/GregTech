package gregtech.loaders.oreprocessing;

import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;

public class ProcessingAll implements IOreRegistrationHandler {
    public ProcessingAll() {
        for (OrePrefix tPrefix : OrePrefix.values()) {
            tPrefix.addProcessingHandler(this);
        }
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (((stack.getItem() instanceof net.minecraft.item.ItemBlock)) && (uEntry.orePrefix.defaultStackSize < stack.getItem().getItemStackLimit(stack))) {
            stack.getItem().setMaxStackSize(uEntry.orePrefix.defaultStackSize);
        }
    }
}
