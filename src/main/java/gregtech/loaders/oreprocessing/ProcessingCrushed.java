package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

public class ProcessingCrushed implements IOreRegistrationHandler {

	public void register() {
		OrePrefix.clump.addProcessingHandler(this);
		OrePrefix.shard.addProcessingHandler(this);
		OrePrefix.crushed.addProcessingHandler(this);
		OrePrefix.dirtyGravel.addProcessingHandler(this);
	}

	public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
		ItemStack stack = simpleStack.asItemStack();
		if (entry.material instanceof SolidMaterial) {
            SolidMaterial material = (SolidMaterial) entry.material;
            ItemStack impureDustStack = OreDictUnifier.get(OrePrefix.dustImpure, material.macerateInto);
            ItemStack dustStack = OreDictUnifier.get(OrePrefix.dust, material.macerateInto);
            DustMaterial byproductMaterial = GTUtility.selectItemInList(0, material.macerateInto, material.oreByProducts);

            //fallback for dirtyGravel, shard & clump
            if (impureDustStack == null) {
                impureDustStack = dustStack;
            }

            RecipeMap.HAMMER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(impureDustStack)
                    .duration(10)
                    .EUt(16)
                    .buildAndRegister();

            RecipeMap.MACERATOR_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .outputs(impureDustStack)
                    .duration(100)
                    .EUt(24)
                    .chancedOutput(OreDictUnifier.get(OrePrefix.dust, byproductMaterial), 1000);

            RecipeMap.ORE_WASHER_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .fluidInputs(ModHandler.getWater(1000))
                    .outputs(OreDictUnifier.get(entry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, material),
                            OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial),
                            OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
                    .buildAndRegister();

            RecipeMap.THERMAL_CENTRIFUGE_RECIPES.recipeBuilder()
                    .inputs(stack)
                    .duration((int) entry.material.getMass() * 20)
                    .outputs(OreDictUnifier.get(entry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedCentrifuged : OrePrefix.dust, entry.material),
                            OreDictUnifier.get(OrePrefix.dustTiny, byproductMaterial),
                            OreDictUnifier.get(OrePrefix.dust, Materials.Stone))
                    .buildAndRegister();

            if(material.washedIn != null) {
                DustMaterial washingByproduct = GTUtility.selectItemInList(3, material.macerateInto, material.oreByProducts);

                RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
                        .inputs(stack)
                        .fluidInputs(material.washedIn.getFluid(1000))
                        .outputs(OreDictUnifier.get(entry.orePrefix == OrePrefix.crushed ? OrePrefix.crushedPurified : OrePrefix.dustPure, entry.material))
                        .chancedOutput(OreDictUnifier.get(OrePrefix.dust, washingByproduct), 7000)
                        .chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Stone), 4000)
                        .duration(800)
                        .EUt(8)
                        .buildAndRegister();
            }
        }
	}

}
