package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gregtech.api.GTValues.M;

public class ProcessingGem implements IOreRegistrationHandler {

    private static List<OrePrefix> ORDER = Arrays.asList(
            OrePrefix.gemChipped, OrePrefix.gemFlawed, OrePrefix.gem, OrePrefix.gemFlawless, OrePrefix.gemExquisite
    );

    public void register() {
        ORDER.forEach(p -> p.addProcessingHandler(this));
    }

    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        if(entry.material instanceof GemMaterial) {
            GemMaterial material = (GemMaterial) entry.material;
            ItemStack stack = simpleStack.asItemStack();
            long materialAmount = entry.orePrefix.materialAmount;
            ItemStack crushedStack = OreDictUnifier.getDust(material, materialAmount);

            if (material.hasFlag(SolidMaterial.MatFlags.MORTAR_GRINDABLE)) {
                ModHandler.addShapelessRecipe(crushedStack, "m", stack);
            }

            if (!material.hasFlag(DustMaterial.MatFlags.NO_SMASHING)) {
                OrePrefix prevPrefix = GTUtility.getItem(ORDER, ORDER.indexOf(entry.orePrefix) - 1, null);
                if(prevPrefix != null) {
                    ItemStack prevStack = OreDictUnifier.get(prevPrefix, material, 2);
                    ModHandler.addShapelessRecipe(prevStack, "h", stack);
                    RecipeMap.HAMMER_RECIPES.recipeBuilder()
                            .inputs(stack)
                            .outputs(prevStack)
                            .duration(20)
                            .EUt(16)
                            .buildAndRegister();
                }
            }

            if(!material.hasFlag(DustMaterial.MatFlags.NO_WORKING)) {
                if (material.hasFlag(SolidMaterial.MatFlags.GENERATE_LONG_ROD) && materialAmount >= M * 2) {
                    RecipeMap.LATHE_RECIPES.recipeBuilder()
                            .inputs(stack)
                            .outputs(OreDictUnifier.get(OrePrefix.stickLong, material, (int) (materialAmount / (M * 2))),
                                    OreDictUnifier.getDust(material, materialAmount % (M * 2)))
                            .duration((int) material.getMass())
                            .EUt(16)
                            .buildAndRegister();
                } else if (materialAmount >= M) {
                    RecipeMap.LATHE_RECIPES.recipeBuilder()
                            .inputs(stack)
                            .outputs(OreDictUnifier.get(OrePrefix.stick, material, (int) (materialAmount / M)),
                                    OreDictUnifier.getDust(material, materialAmount % M))
                            .duration((int) material.getMass())
                            .EUt(16)
                            .buildAndRegister();
                }
            }

        }
    }

}
