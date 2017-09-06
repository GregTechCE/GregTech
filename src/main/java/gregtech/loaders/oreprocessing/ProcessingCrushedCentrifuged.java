package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingCrushedCentrifuged implements IOreRegistrationHandler {

    private ProcessingCrushedCentrifuged() {}

    public static void register() {
        OrePrefix.crushedCentrifuged.addProcessingHandler(new ProcessingCrushedCentrifuged());
    }

    @Override
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack itemStack) {
        if (entry.material instanceof SolidMaterial) {
            ItemStack stack = itemStack.asItemStack();
            SolidMaterial solidMaterial = (SolidMaterial) entry.material;
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, solidMaterial);
            ItemStack byproductStack = OreDictUnifier.get(OrePrefix.dust, GTUtility.selectItemInList(2,
                    solidMaterial, solidMaterial.oreByProducts, DustMaterial.class), 1);

            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(dustStack)
                    .duration(10)
                    .EUt(16)
                    .buildAndRegister();

            RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(dustStack)
                    .chancedOutput(byproductStack, 1000)
                    .buildAndRegister();
        }
    }

}
