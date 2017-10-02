package gregtech.loaders.oreprocessing;

import gregtech.api.recipes.ModHandler;
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

public class ProcessingStick implements IOreRegistrationHandler {

    private ProcessingStick() {}

    public static void register() {
        ProcessingStick processing = new ProcessingStick();
        OrePrefix.stick.addProcessingHandler(processing);
        OrePrefix.stickLong.addProcessingHandler(processing);
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        ItemStack stack = simpleStack.asItemStack();

        switch (entry.orePrefix) {
            case stick:
                if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {

                    if (entry.material instanceof SolidMaterial) {
                        RecipeMap.LATHE_RECIPES.recipeBuilder()
                            .inputs(entry.material.hasFlag(GemMaterial.MatFlags.CRYSTALLISABLE)
                                ? OreDictUnifier.get(OrePrefix.gem, entry.material)
                                : OreDictUnifier.get(OrePrefix.ingot, entry.material))
                            .outputs(OreDictUnifier.get(OrePrefix.stick, entry.material), OreDictUnifier.get(OrePrefix.dustSmall, ((SolidMaterial) entry.material).macerateInto, 2))
                            .duration((int) Math.max(entry.material.getMass() * 5L, 1L))
                            .EUt(16)
                            .buildAndRegister();
                    }

                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.bolt, entry.material, 4))
                        .duration((int) Math.max(entry.material.getMass() * 2L, 1L))
                        .EUt(4)
                        .buildAndRegister();

                    ModHandler.addShapedRecipe("stick_fls_" + entry.material,
                        OreDictUnifier.get(OrePrefix.stick, entry.material, 2),
                        "s",
                        "X",
                        'X', new UnificationEntry(OrePrefix.stickLong, entry.material));

                    ModHandler.addShapedRecipe("stick_" + entry.material,
                        OreDictUnifier.get(OrePrefix.stick, entry.material, 1),
                        "f ",
                        " X",
                        'X', new UnificationEntry(OrePrefix.ingot, entry.material));

                }
                if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    RecipeMap.HAMMER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(2, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.stickLong, entry.material))
                        .duration((int) Math.max(entry.material.getMass(), 1L))
                        .EUt(16)
                        .buildAndRegister();
                }
                break;
            case stickLong:
                if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                    RecipeMap.CUTTER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.stick, entry.material, 2))
                        .duration((int) Math.max(entry.material.getMass(), 1L))
                        .EUt(4)
                        .buildAndRegister();

                    ModHandler.addShapedRecipe("lstick_ffg_" + entry.material,
                        OreDictUnifier.get(OrePrefix.stickLong, entry.material, 1),
                        "sf",
                        "G ",
                        'G', new UnificationEntry(OrePrefix.gemFlawless, entry.material));

                    ModHandler.addShapedRecipe("lstick_feg_" + entry.material,
                        OreDictUnifier.get(OrePrefix.stickLong, entry.material, 2),
                        "sf",
                        "G ",
                        'G', new UnificationEntry(OrePrefix.gemExquisite, entry.material));
                }
                if (!entry.material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                    RecipeMap.BENDER_RECIPES.recipeBuilder()
                        .inputs(GTUtility.copyAmount(1, stack))
                        .outputs(OreDictUnifier.get(OrePrefix.spring, entry.material))
                        .duration(200)
                        .EUt(16)
                        .buildAndRegister();

                    ModHandler.addShapedRecipe("lstick_fs_",
                        OreDictUnifier.get(OrePrefix.stickLong, entry.material),
                        "ShS",
                        'S', new UnificationEntry(OrePrefix.stick, entry.material));
                }
                break;
        }
    }
}
