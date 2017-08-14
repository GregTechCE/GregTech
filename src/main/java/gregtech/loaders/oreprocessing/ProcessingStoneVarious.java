package gregtech.loaders.oreprocessing;

import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ProcessingStoneVarious implements IOreRegistrationHandler {
    public ProcessingStoneVarious() {
        OrePrefix.stone.addProcessingHandler(this);
        OrePrefix.stoneCobble.addProcessingHandler(this);
        OrePrefix.stoneBricks.addProcessingHandler(this);
        OrePrefix.stoneChiseled.addProcessingHandler(this);
        OrePrefix.stoneCracked.addProcessingHandler(this);
        OrePrefix.stoneMossy.addProcessingHandler(this);
        OrePrefix.stoneMossyBricks.addProcessingHandler(this);
        OrePrefix.stoneSmooth.addProcessingHandler(this);
    }

    public void registerOre(UnificationEntry uEntry, String modName, SimpleItemStack simpleStack) {
        if (uEntry.orePrefix == OrePrefix.stoneSmooth) {
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(1L, aStack), ItemList.Circuit_Integrated.getWithDamage(0L, 1L), new ItemStack(Blocks.STONE_BUTTON, 1), 100, 4);
            GT_Values.RA.addAssemblerRecipe(GT_Utility.copyAmount(2L, aStack), ItemList.Circuit_Integrated.getWithDamage(0L, 2L), new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1), 200, 4);
        }
    }
}
