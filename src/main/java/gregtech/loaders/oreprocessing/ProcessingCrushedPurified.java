package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingCrushedPurified  {

    private ProcessingCrushedPurified() {}

    //public static void register() {
       // OrePrefix.crushedPurified.addProcessingHandler(new ProcessingCrushedPurified());
   // }

   // @Override
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack itemStack) {
        if (entry.material instanceof SolidMaterial) {
            ItemStack crushedPurifiedStack = itemStack.asItemStack();
            SolidMaterial solidMaterial = (SolidMaterial) entry.material;
            ItemStack crushedCentrifugedStack = OreDictUnifier.get(OrePrefix.crushedCentrifuged, solidMaterial);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, solidMaterial);
            ItemStack byproductStack = OreDictUnifier.get(OrePrefix.dustTiny, GTUtility.selectItemInList(1, solidMaterial, solidMaterial.oreByProducts, DustMaterial.class));

            if (!crushedCentrifugedStack.isEmpty()) {
                RecipeMap.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
                        .inputs(crushedPurifiedStack)
                        .outputs(crushedCentrifugedStack, byproductStack)
                        .duration((int) (entry.material.getMass() * 20))
                        .EUt(60)
                        .buildAndRegister();
            } else {
                RecipeMap.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
                        .inputs(crushedPurifiedStack)
                        .outputs(dustStack, byproductStack)
                        .duration((int) (entry.material.getMass() * 20))
                        .EUt(60)
                        .buildAndRegister();
            }


            if (entry.material instanceof GemMaterial) {
                ItemStack exquisiteStack = OreDictUnifier.get(OrePrefix.gemExquisite, entry.material);
                ItemStack flawlessStack = OreDictUnifier.get(OrePrefix.gemFlawless, entry.material);
                ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, entry.material);
                ItemStack flawedStack = OreDictUnifier.get(OrePrefix.gemFlawed, entry.material);
                ItemStack chippedStack = OreDictUnifier.get(OrePrefix.gemChipped, entry.material);

                if (entry.material.hasFlag(GemMaterial.MatFlags.HIGH_SIFTER_OUTPUT)) {
                    RecipeMap.SIFTER_RECIPES.recipeBuilder()
                            .inputs(crushedPurifiedStack)
                            .chancedOutput(exquisiteStack, 300)
                            .chancedOutput(flawlessStack, 1200)
                            .chancedOutput(gemStack, 4500)
                            .chancedOutput(flawedStack, 1400)
                            .chancedOutput(chippedStack, 2800)
                            .chancedOutput(dustStack, 3500)
                            .duration(800)
                            .EUt(16)
                            .buildAndRegister();
                } else {
                    RecipeMap.SIFTER_RECIPES.recipeBuilder()
                            .inputs(crushedPurifiedStack)
                            .chancedOutput(exquisiteStack, 100)
                            .chancedOutput(flawlessStack, 400)
                            .chancedOutput(gemStack, 1500)
                            .chancedOutput(flawedStack, 2000)
                            .chancedOutput(chippedStack, 4000)
                            .chancedOutput(dustStack, 5000)
                            .duration(800)
                            .EUt(16)
                            .buildAndRegister();
                }
            }
        }
    }
}
