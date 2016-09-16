package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import net.minecraft.item.ItemStack;

public class ProcessingBolt implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingBolt() {
        OrePrefixes.bolt.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {

    }
}
