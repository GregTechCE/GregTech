package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingSand implements IOreRegistrationHandler {
    public ProcessingSand() {
        OrePrefix.sand.add(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        if (aOreDictName.equals("sandCracked")) {
            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(16L, stack), -1, ModHandler.getFuelCan(25000), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Saltpeter, 8), null, null, null, new ItemStack(Blocks.SAND, 10, 1), 2500);
            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(16L, stack), -1, ModHandler.getFuelCan(25000), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Saltpeter, 8), null, null, null, new ItemStack(Blocks.SAND, 10), 2500);
        } else if (aOreDictName.equals("sandOil")) {
            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(2L, stack), 1, OreDictionaryUnifier.get(OrePrefix.cell, Materials.Oil, 1), new ItemStack(Blocks.SAND, 1, 1), null, null, null, null, 1000);
            GT_Values.RA.addCentrifugeRecipe(GT_Utility.copyAmount(2L, stack), 1, OreDictionaryUnifier.get(OrePrefix.cell, Materials.Oil, 1), new ItemStack(Blocks.SAND, 1, 0), null, null, null, null, 1000);
        }
    }
}
