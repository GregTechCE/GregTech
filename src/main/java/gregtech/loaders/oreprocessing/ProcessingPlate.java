package gregtech.loaders.oreprocessing;

import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.W;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.NO_SMASHING;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.MORTAR_GRINDABLE;

public class ProcessingPlate implements IOreRegistrationHandler {

    private ProcessingPlate() {}

    public static void register() {
        ProcessingPlate processing = new ProcessingPlate();
        OrePrefix.plate.addProcessingHandler(processing);
        OrePrefix.plateDense.addProcessingHandler(processing);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();
        boolean noSmashing = entry.material.hasFlag(NO_SMASHING);
        long materialMass = entry.material.getMass();

        switch (entry.orePrefix) {
            case plate:
                if (entry.material instanceof FluidMaterial) {
                    RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
                        .notConsumable(MetaItems.SHAPE_MOLD_PLATE)
                        .fluidInputs(((FluidMaterial) entry.material).getFluid(L))
                        .outputs(OreDictUnifier.get(OrePrefix.plate, entry.material))
                        .duration(32)
                        .EUt(8)
                        .buildAndRegister();
                }

                RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
                    .inputs(GTUtility.copyAmount(2, stack))
                    .explosivesAmount(2)
                    .outputs(OreDictUnifier.get(OrePrefix.compressed, entry.material), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh))
                    .buildAndRegister();

                if (entry.material == Materials.Paper)
                    ModHandler.addShapedRecipe("papar_f_scane_" + entry.material,
                        GTUtility.copyAmount(3, stack),
                        "XXX",
                        'X', new ItemStack(Items.REEDS, 1, W));

                if (!noSmashing) {
                    ModHandler.addShapedRecipe("ingot_t_plate_" + entry.material,
                        OreDictUnifier.get(OrePrefix.plate, entry.material),
                        "h",
                        "X",
                        "X",
                        'X', new UnificationEntry(OrePrefix.ingot, entry.material));

                    ModHandler.addShapedRecipe("ingot_t_plate_od_" + entry.material,
                        OreDictUnifier.get(OrePrefix.plate, entry.material),
                        "H",
                        "X",
                        'H', ToolDictNames.craftingToolForgeHammer,
                        'X', new UnificationEntry(OrePrefix.ingot, entry.material));

                    ModHandler.addShapedRecipe("gem_t_plate_" + entry.material,
                        OreDictUnifier.get(OrePrefix.plate, entry.material),
                        "h",
                        "X",
                        'X', new UnificationEntry(OrePrefix.gem, entry.material));

                    ModHandler.addShapedRecipe("gem_t_plate_od_" + entry.material,
                        OreDictUnifier.get(OrePrefix.plate, entry.material),
                        "H",
                        "X",
                        'H', ToolDictNames.craftingToolForgeHammer,
                        'X', new UnificationEntry(OrePrefix.gem, entry.material));
                }

                if (entry.material.hasFlag(MORTAR_GRINDABLE))
                    ModHandler.addShapedRecipe("plate_t_dust_" + entry.material,
                        OreDictUnifier.get(OrePrefix.dust, entry.material),
                        "X",
                        "m",
                        'X', new UnificationEntry(OrePrefix.plate, entry.material));

                break;
            case plateDense:
                if (!noSmashing) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                        .inputs(OreDictUnifier.get(OrePrefix.plate, entry.material, 9))
                        .outputs(GTUtility.copyAmount(1, stack))
                        .duration((int) Math.max(materialMass * 9L, 1L))
                        .EUt(96)
                        .buildAndRegister();

                }
                break;
        }
    }
}
