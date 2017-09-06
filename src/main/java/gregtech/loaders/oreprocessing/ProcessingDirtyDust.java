package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingDirtyDust implements IOreRegistrationHandler {

    private ProcessingDirtyDust() {}

    public void register() {
        ProcessingDirtyDust processing = new ProcessingDirtyDust();
        OrePrefix.dustImpure.addProcessingHandler(processing);
        OrePrefix.dustPure.addProcessingHandler(processing);
        OrePrefix.dustRefined.addProcessingHandler(processing);
    }

    @Override
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack itemStack) {
        if(entry.material instanceof DustMaterial) {
            DustMaterial material = (DustMaterial) entry.material;
            ItemStack stack = itemStack.asItemStack();
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material);

            if(entry.orePrefix == OrePrefix.dustPure && material.separatedOnto != null) {
                ItemStack separatedStack = OreDictUnifier.get(OrePrefix.dustSmall, material.separatedOnto);
                RecipeMap.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .outputs(dustStack)
                        .chancedOutput(separatedStack, 4000)
                        .duration((int) material.separatedOnto.getMass())
                        .EUt(24)
                        .buildAndRegister();
            }

            int byProductIndex;
            if(entry.orePrefix == OrePrefix.dustRefined) {
                byProductIndex = 2;
            } else if(entry.orePrefix == OrePrefix.dustPure) {
                byProductIndex = 1;
            } else byProductIndex = 0;
            FluidMaterial byproduct = GTUtility.selectItemInList(byProductIndex, material, material.oreByProducts, FluidMaterial.class);

            RecipeBuilder builder = RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(dustStack)
                    .duration((int) (material.getMass() * 4))
                    .EUt(24);

            if(byproduct instanceof DustMaterial) {
                builder.outputs(OreDictUnifier.get(OrePrefix.dustTiny, byproduct));
            } else {
                builder.fluidOutputs(byproduct.getFluid(GTValues.L / 9));
            }

            builder.buildAndRegister();
        }
    }

}
