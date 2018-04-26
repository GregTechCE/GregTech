package gregtech.loaders.postload;

import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import gregtech.common.items.MetaItems;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class MachineRecipeLoader implements Runnable {

    @Override
    public void run() {
        shapingRecipes();
    }
    private void shapingRecipes(){
        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(31, OreDictUnifier.get(OrePrefix.ingot, Materials.Iron)))
            .notConsumable(MetaItems.SHAPE_MOLD_ANVIL)
            .outputs(new ItemStack(Blocks.ANVIL, 1, 0))
            .duration(31 * 512)
            .EUt(4 * 16)
            .buildAndRegister();

        RecipeMaps.ALLOY_SMELTER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(31, OreDictUnifier.get(OrePrefix.ingot, Materials.WroughtIron)))
            .notConsumable(MetaItems.SHAPE_MOLD_ANVIL)
            .outputs(new ItemStack(Blocks.ANVIL, 1, 0))
            .duration(31 * 512)
            .EUt(4 * 16)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(Materials.RedAlloy + "_t_cable01", OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.wireGtSingle, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.plate, Materials.Paper));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
            .outputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.RedAlloy))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.cableGtSingle, Materials.RedAlloy))
            .outputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtSingle, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(Materials.RedAlloy + "_t_cable02", OreDictUnifier.get(OrePrefix.cableGtDouble, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.wireGtDouble, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.plate, Materials.Paper));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
            .outputs(OreDictUnifier.get(OrePrefix.cableGtDouble, Materials.RedAlloy))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.cableGtDouble, Materials.RedAlloy))
            .outputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtDouble, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(Materials.RedAlloy + "_t_cable04", OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.wireGtQuadruple, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtQuadruple, Materials.RedAlloy)),OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2))
            .outputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.RedAlloy))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.cableGtQuadruple, Materials.RedAlloy))
            .outputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtOctal, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 2))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(Materials.RedAlloy + "_t_cable08", OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.wireGtOctal, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtOctal, Materials.RedAlloy)),OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3))
            .outputs(OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.RedAlloy))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.cableGtOctal, Materials.RedAlloy))
            .outputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtOctal, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        ModHandler.addShapelessRecipe(Materials.RedAlloy + "_t_cable12", OreDictUnifier.get(OrePrefix.cableGtTwelve, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.wireGtTwelve, Materials.RedAlloy),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper),
            new UnificationEntry(OrePrefix.plate, Materials.Paper));

        RecipeMaps.PACKER_RECIPES.recipeBuilder()
            .inputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtTwelve, Materials.RedAlloy)),OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4))
            .outputs(OreDictUnifier.get(OrePrefix.cableGtTwelve, Materials.RedAlloy))
            .duration(100)
            .EUt(8)
            .buildAndRegister();

        RecipeMaps.UNPACKER_RECIPES.recipeBuilder()
            .inputs(OreDictUnifier.get(OrePrefix.cableGtTwelve, Materials.RedAlloy))
            .outputs(GTUtility.copyAmount(1, OreDictUnifier.get(OrePrefix.wireGtTwelve, Materials.RedAlloy)), OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 4))
            .duration(100)
            .EUt(8)
            .buildAndRegister();
    }

}
