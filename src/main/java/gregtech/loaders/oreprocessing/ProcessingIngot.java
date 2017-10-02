package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;

import static gregtech.api.GTValues.L;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.MORTAR_GRINDABLE;

public class ProcessingIngot implements IOreRegistrationHandler {

    private ProcessingIngot() {}

    public static void register() {
        OrePrefix.ingot.addProcessingHandler(new ProcessingIngot());

        //simple ingotHot processing
        OrePrefix.ingotHot.addProcessingHandler((entry, modName, stack) -> {
           if(entry.material instanceof MetalMaterial) {
               RecipeMap.VACUUM_RECIPES.recipeBuilder()
                       .inputs(stack.asItemStack())
                       .outputs(OreDictUnifier.get(OrePrefix.ingot, entry.material))
                       .duration(20)
                       .buildAndRegister();
           }
        });

    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        if(entry.material instanceof MetalMaterial) {
            MetalMaterial material = (MetalMaterial) entry.material;
            ItemStack stack = simpleStack.asItemStack();

            if (material.hasFlag(SolidMaterial.MatFlags.MORTAR_GRINDABLE)) {
                ModHandler.addShapelessRecipe("dust_f_ingot_" + material, OreDictUnifier.get(OrePrefix.dust, material), "m", stack);
            }

//            RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
//                    .notConsumable(MetaItems.SHAPE_MOLD_INGOT)
//                    .fluidInputs(((FluidMaterial) entry.material).getFluid(L)) // TODO MATERIAL FLUIDS
//                    .outputs(OreDictUnifier.get(OrePrefix.ingot, entry.material))
//                    .duration(20)
//                    .EUt(8)
//                    .buildAndRegister();

            if(material.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE)) {
                if(!material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    ItemStack plateStack = OreDictUnifier.get(OrePrefix.plate, material);
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                            .inputs(stack)
                            .outputs(plateStack)
                            .duration((int) (material.getMass()))
                            .circuitMeta(0)
                            .EUt(24)
                            .buildAndRegister();
                    RecipeMap.HAMMER_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(2, stack))
                            .outputs(plateStack)
                            .EUt(16)
                            .duration((int) (material.getMass() / 2L))
                            .buildAndRegister();

                    ModHandler.addShapedRecipe("plate_" + entry.material, plateStack, "h##", "I##", "I##", 'I', stack);

                    if(material.hasFlag(MetalMaterial.MatFlags.GENERATE_DENSE)) {
                        RecipeMap.BENDER_RECIPES.recipeBuilder()
                                .inputs(GTUtility.copyAmount(9, stack))
                                .outputs(OreDictUnifier.get(OrePrefix.plateDense, material))
                                .EUt(96)
                                .duration((int) (material.getMass() * 9));
                    }

                }


            }
        }
    }

}
