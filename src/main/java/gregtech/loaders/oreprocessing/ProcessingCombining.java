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
                if (!ingotStack.isEmpty()) {
                    ModHandler.addShapelessRecipe("ingot_" + entry.material, GTUtility.copyAmount(9, stack), ingotStack);
                    ModHandler.addShapedRecipe("nugget_" + entry.material, ingotStack, "XXX", "XXX", "XXX", 'X', stack);
                }
            } else if(entry.material instanceof GemMaterial) { //sometimes happens because of other mods
                ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, entry.material);
                if (!gemStack.isEmpty()) {
                    ModHandler.addShapelessRecipe("gem_" + entry.material, GTUtility.copyAmount(9, stack), gemStack);
                    ModHandler.addShapedRecipe("nugget_" + entry.material, gemStack, "XXX", "XXX", "XXX", stack);
                }
            }
        });

        OrePrefix.dustSmall.addProcessingHandler((entry, modName, itemStack) -> {
            if (entry.material instanceof DustMaterial) {
                ItemStack stack = itemStack.asItemStack();
                ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, entry.material);
                ModHandler.addShapedRecipe("sdust_t_dust_" + entry.material, GTUtility.copyAmount(4, stack), "##", "#X", 'X', dustStack);
                ModHandler.addShapedRecipe("dust_t_sdust_" + entry.material, dustStack, "XX", "XX", stack);
            }
        });

        OrePrefix.dustTiny.addProcessingHandler((entry, modName, itemStack) -> {
            if (entry.material instanceof DustMaterial) {
                ItemStack stack = itemStack.asItemStack();
                ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, entry.material);
                ModHandler.addShapedRecipe("tdust_t_dust_" + entry.material, GTUtility.copyAmount(9, stack), "X", 'X', dustStack);
                ModHandler.addShapedRecipe("dust_t_tdust_" + entry.material, dustStack, "XXX", "XXX", "XXX", stack);
            }
        });

    }

}
