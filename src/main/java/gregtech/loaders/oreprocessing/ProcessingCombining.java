package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingCombining {

    private ProcessingCombining() {}

    public static void register() {

        OrePrefix.nugget.addProcessingHandler((entry, modName, itemStack) -> {
            ItemStack stack = itemStack.asItemStack();
            if(entry.material instanceof MetalMaterial) {
                ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, entry.material);
                ModHandler.addShapelessRecipe(GTUtility.copyAmount(9, stack), ingotStack);
                ModHandler.addShapedRecipe(ingotStack, "XXX", "XXX", "XXX", 'X', stack);
            } else if(entry.material instanceof GemMaterial) { //sometimes happens because of other mods
                ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, entry.material);
                ModHandler.addShapelessRecipe(GTUtility.copyAmount(9, stack), gemStack);
                ModHandler.addShapedRecipe(gemStack, "XXX", "XXX", "XXX", stack);
            }
        });

        OrePrefix.dustSmall.addProcessingHandler((entry, modName, itemStack) -> {
            if (entry.material instanceof DustMaterial) {
                ItemStack stack = itemStack.asItemStack();
                ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, entry.material);
                ModHandler.addShapedRecipe(GTUtility.copyAmount(4, stack), "##", "#X", 'X', dustStack);
                ModHandler.addShapedRecipe(dustStack, "XX", "XX", stack);
            }
        });

        OrePrefix.dustTiny.addProcessingHandler((entry, modName, itemStack) -> {
            if (entry.material instanceof DustMaterial) {
                ItemStack stack = itemStack.asItemStack();
                ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, entry.material);
                ModHandler.addShapedRecipe(GTUtility.copyAmount(9, stack), "X", 'X', dustStack);
                ModHandler.addShapedRecipe(dustStack, "XXX", "XXX", "XXX", stack);
            }
        });

    }

}
