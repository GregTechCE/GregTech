package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingClearDust implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.dust.addProcessingHandler(this);
    }

    @Override
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack itemStack) {
        if(entry.material instanceof DustMaterial) {
            DustMaterial material = (DustMaterial) entry.material;
            ItemStack stack = itemStack.asItemStack();

            if (material instanceof GemMaterial) {
                ItemStack gemStack = OreDictUnifier.get(OrePrefix.gem, material);
                if(material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE)) {
                    RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .fluidInputs(Materials.Water.getFluid(200))
                            .chancedOutput(gemStack, 7000)
                            .duration(2000)
                            .EUt(24)
                            .buildAndRegister();
                    RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(1, stack))
                            .fluidInputs(ModHandler.getDistilledWater(200))
                            .chancedOutput(gemStack, 9000)
                            .duration(1500)
                            .EUt(24)
                            .buildAndRegister();
                } else if(!material.hasFlag(Material.MatFlags.EXPLOSIVE | DustMaterial.MatFlags.NO_SMASHING)) {
                    RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(4, stack))
                            .outputs(GTUtility.copyAmount(3, gemStack))
                            .explosivesAmount(4)
                            .buildAndRegister();
                }
            } else if(material instanceof MetalMaterial &&
                    !material.hasFlag(Material.MatFlags.FLAMMABLE | DustMaterial.MatFlags.NO_SMELTING)) {
                MetalMaterial metalMaterial = (MetalMaterial) material;
                ItemStack ingotStack = OreDictUnifier.get(OrePrefix.ingot, metalMaterial);
                if(metalMaterial.blastFurnaceTemperature > 0) {
                    ModHandler.removeFurnaceSmelting(ingotStack);
                    RecipeMap.BLAST_RECIPES.recipeBuilder()
                            .inputs(stack)
                            .outputs(ingotStack)
                            .duration((int) (entry.material.getMass() * metalMaterial.blastFurnaceTemperature / 40L))
                            .EUt(120)
                            .blastFurnaceTemp(metalMaterial.blastFurnaceTemperature)
                            .buildAndRegister();
                } else {
                    ModHandler.addSmeltingRecipe(stack, ingotStack);
                }
            } else { //solid or dust material without known solid item form
                if(material.hasFlag(DustMaterial.MatFlags.GENERATE_PLATE) &&
                        !material.hasFlag(Material.MatFlags.EXPLOSIVE | DustMaterial.MatFlags.NO_SMASHING)) {
                    RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
                            .inputs(stack)
                            .outputs(OreDictUnifier.get(OrePrefix.plate, material))
                            .buildAndRegister();
                }
            }



        }
    }

}
