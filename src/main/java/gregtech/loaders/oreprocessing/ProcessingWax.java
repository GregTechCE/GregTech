package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class ProcessingWax implements IOreRegistrationHandler {
    public ProcessingWax() {
        OrePrefix.wax.add(this);
    }

    public void registerOre(OrePrefix aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aOreDictName.equals("waxMagical"))
            GT_Values.RA.addFuel(GT_Utility.copyAmount(1L, new Object[]{aStack}), null, 6, 5);
    }
}
