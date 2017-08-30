package gregtech.loaders.oreprocessing;

import com.google.common.collect.Lists;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.MarkerMaterial;
import gregtech.api.unification.ore.IOreRegistrationHandler;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GTUtility;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

import java.awt.*;
import java.util.ArrayList;

public class ProcessingLens implements IOreRegistrationHandler {

    public void register() {
        OrePrefix.lens.addProcessingHandler(this);
    }
    
    public void registerOre(UnificationEntry entry, String modName, SimpleItemStack simpleStack) {
        if(entry.material instanceof GemMaterial) {
            ItemStack stack = simpleStack.asItemStack();

            RecipeMap.LATHE_RECIPES.recipeBuilder()
                    .inputs(OreDictUnifier.get(OrePrefix.plate, entry.material))
                    .outputs(stack, OreDictUnifier.get(OrePrefix.dustSmall, entry.material))
                    .duration((int) (entry.material.getMass() / 2L))
                    .EUt(16)
                    .buildAndRegister();

            EnumDyeColor dyeColor = GTUtility.determineDyeColor(entry.material.materialRGB);
            MarkerMaterial colorMaterial = MarkerMaterials.Color.COLORS.get(dyeColor);
            OreDictUnifier.registerOre(stack, OrePrefix.craftingLens, colorMaterial);
        }
    }

}
