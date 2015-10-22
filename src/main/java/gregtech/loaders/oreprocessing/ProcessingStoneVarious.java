package gregtech.loaders.oreprocessing;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingStoneVarious implements gregtech.api.interfaces.IOreRecipeRegistrator {
    public ProcessingStoneVarious() {
        OrePrefixes.stone.add(this);
        OrePrefixes.stoneCobble.add(this);
        OrePrefixes.stoneBricks.add(this);
        OrePrefixes.stoneChiseled.add(this);
        OrePrefixes.stoneCracked.add(this);
        OrePrefixes.stoneMossy.add(this);
        OrePrefixes.stoneMossyBricks.add(this);
        OrePrefixes.stoneSmooth.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, gregtech.api.enums.Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aPrefix == OrePrefixes.stoneSmooth) {
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 1L, new Object[0]), new ItemStack(Blocks.stone_button, 1), 100, 4);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(2L, new Object[]{aStack}), ItemList.Circuit_Integrated.getWithDamage(0L, 2L, new Object[0]), new ItemStack(Blocks.stone_pressure_plate, 1), 200, 4);
        }
    }
}
