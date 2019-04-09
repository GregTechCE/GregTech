package gregtech.loaders.oreprocessing;

import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.IngotMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.loaders.recipe.RecyclingRecipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class RecyclingRecipeHandler {

    private static final List<Object> CRUSHING_PREFIXES = Arrays.asList(
        OrePrefix.ingot, OrePrefix.gem, OrePrefix.stick, OrePrefix.plate, OrePrefix.plank,
        OrePrefix.ring, OrePrefix.stickLong, OrePrefix.foil, OrePrefix.bolt,
        OrePrefix.screw, OrePrefix.nugget, OrePrefix.gearSmall, OrePrefix.gear,
        OrePrefix.frameGt, OrePrefix.plateDense, OrePrefix.spring,
        OrePrefix.springSmall, OrePrefix.block, OrePrefix.wireFine,
        OrePrefix.rotor, OrePrefix.lens, OrePrefix.turbineBlade,
        (Predicate<OrePrefix>) orePrefix -> orePrefix.name().startsWith("toolHead"),
        (Predicate<OrePrefix>) orePrefix -> orePrefix.name().startsWith("gem"),
        (Predicate<OrePrefix>) orePrefix -> orePrefix.name().startsWith("cableGt"),
        (Predicate<OrePrefix>) orePrefix -> orePrefix.name().startsWith("wireGt")
    );

    private static final List<OrePrefix> IGNORE_ARC_SMELTING = Arrays.asList(
        OrePrefix.ingot, OrePrefix.gem, OrePrefix.nugget, OrePrefix.block);

    public static void register() {
        //registers universal maceration recipes for specified ore prefixes
        for (OrePrefix orePrefix : OrePrefix.values()) {
            if (CRUSHING_PREFIXES.stream().anyMatch(object -> {
                if (object instanceof OrePrefix)
                    return object == orePrefix;
                else if (object instanceof Predicate)
                    return ((Predicate<OrePrefix>) object).test(orePrefix);
                else return false;
            })) orePrefix.addProcessingHandler(DustMaterial.class, RecyclingRecipeHandler::processCrushing);
        }
    }

    public static void processCrushing(OrePrefix thingPrefix, DustMaterial material) {
        ArrayList<MaterialStack> materialStacks = new ArrayList<>();
        materialStacks.add(new MaterialStack(material, thingPrefix.getMaterialAmount(material)));
        materialStacks.addAll(thingPrefix.secondaryMaterials);
        //only ignore arc smelting for blacklisted prefixes if yielded material is the same as input material
        //if arc smelting gives different material, allow it
        boolean ignoreArcSmelting = IGNORE_ARC_SMELTING.contains(thingPrefix) && !(
            material instanceof IngotMaterial && ((IngotMaterial) material).arcSmeltInto != material);
        RecyclingRecipes.registerArcRecyclingRecipe(builder -> builder.input(thingPrefix, material), materialStacks, ignoreArcSmelting);
    }

}
