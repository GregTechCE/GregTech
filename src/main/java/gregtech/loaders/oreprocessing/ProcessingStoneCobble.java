package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingStoneCobble implements IOreRegistrationHandler {
    public ProcessingStoneCobble() {
        OrePrefix.stoneCobble.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, aStack), OreDictionaryUnifier.get(OrePrefix.stick, Materials.Wood, 1L), new ItemStack(Blocks.LEVER, 1), 400, 1);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, aStack), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), new ItemStack(Blocks.FURNACE, 1), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(7L, aStack), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Redstone, 1L), new ItemStack(Blocks.DROPPER, 1), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(7L, aStack), new ItemStack(Items.BOW, 1, 0), Materials.Redstone.getMolten(144L), new ItemStack(Blocks.DISPENSER, 1), 400, 4);
    }
}
