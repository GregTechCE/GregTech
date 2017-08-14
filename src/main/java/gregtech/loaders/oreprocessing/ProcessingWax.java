package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingWax implements IOreRegistrationHandler {
    public ProcessingWax() {
        OrePrefix.wax.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (aOreDictName.equals("waxMagical"))
            GT_Values.RA.addFuel(GT_Utility.copyAmount(1, stack), null, 6, 5);
    }
}
