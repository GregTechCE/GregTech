package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ProcessingStoneCobble implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingStoneCobble() {
        OrePrefixes.stoneCobble.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Wood, 1L), new ItemStack(Blocks.lever, 1), 400, 1);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(8L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 8L, new Object[0]), new ItemStack(Blocks.furnace, 1), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(7L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Redstone, 1L), new ItemStack(Blocks.dropper, 1), 400, 4);
        GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(7L, new Object[]{aStack}), new ItemStack(Items.bow, 1, 0), Materials.Redstone.getMolten(144L), new ItemStack(Blocks.dispenser, 1), 400, 4);
    }
}
