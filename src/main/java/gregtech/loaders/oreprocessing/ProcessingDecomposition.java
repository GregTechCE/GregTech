package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public class ProcessingDecomposition implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.cell.addProcessingHandler(this);
        OrePrefix.dust.addProcessingHandler(this);
    }

    @Override
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack itemStack) {
        if(entry.material instanceof FluidMaterial) {
            FluidMaterial material = (FluidMaterial) entry.material;
            if(!material.materialComponents.isEmpty() &&
                    (material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING) ||
                    material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_CENTRIFUGING))) {

                //compute inputs
                ArrayList<ItemStack> inputs = new ArrayList<>();
                ArrayList<FluidStack> fluidInputs = new ArrayList<>();
                int totalInputAmount = 0;
                for(MaterialStack component : material.materialComponents) {
                    totalInputAmount += component.amount;
                    if(component.material instanceof DustMaterial) {
                        inputs.add(OreDictionaryUnifier.get(OrePrefix.dust, component.material, (int) component.amount));
                    } else if(component.material instanceof FluidMaterial) {
                        FluidMaterial componentMaterial = (FluidMaterial) component.material;
                        fluidInputs.add(componentMaterial.getFluid((int) (GTValues.L * component.amount)));
                    }
                }

                //generate builder
                RecipeBuilder<Recipe, RecipeBuilder.DefaultRecipeBuilder> builder;
                if(material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING)) {
                    builder = RecipeMap.ELECTROLYZER_RECIPES.recipeBuilder()
                            .duration((int) material.getProtons() * totalInputAmount * 8)
                            .EUt(Math.min(4, material.materialComponents.size()) * 30);
                } else {
                    builder = RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
                            .duration((int) material.getMass() * totalInputAmount * 2)
                            .EUt(30);
                }

                //finish builder
                if(entry.orePrefix == OrePrefix.dust) {
                    builder.inputs(itemStack.asItemStack(totalInputAmount));
                } else {
                    builder.fluidInputs(material.getFluid(GTValues.L * totalInputAmount));
                }

                //register recipe
                builder.buildAndRegister();
            }

        }
    }

}
