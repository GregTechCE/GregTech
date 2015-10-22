package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import net.minecraft.item.ItemStack;

public class ProcessingAll implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingAll() {
        for (OrePrefixes tPrefix : OrePrefixes.values()) tPrefix.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (((aStack.getItem() instanceof net.minecraft.item.ItemBlock)) && (aPrefix.mDefaultStackSize < aStack.getItem().getItemStackLimit(aStack)))
            aStack.getItem().setMaxStackSize(aPrefix.mDefaultStackSize);
    }
}
