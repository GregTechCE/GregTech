package gregtech.loaders.oreprocessing;

import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.FluidMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static gregtech.api.unification.material.type.Material.MatFlags.DECOMPOSITION_REQUIRES_HYDROGEN;
import static gregtech.api.unification.material.type.Material.MatFlags.DISABLE_DECOMPOSITION;

public class DecompositionRecipeHandler {

    public static void runRecipeGeneration() {
        for(Material material : Material.MATERIAL_REGISTRY) {
            if(material instanceof FluidMaterial) {
                OrePrefix prefix = material instanceof DustMaterial ? OrePrefix.dust : null;
                processDecomposition(prefix, (FluidMaterial) material);
            }
        }
    }

    public static void processDecomposition(OrePrefix decomposePrefix, FluidMaterial material) {
        if (material.materialComponents.isEmpty() ||
            (!material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING) &&
                !material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_CENTRIFUGING)) ||
            //disable decomposition if explicitly disabled for this material or for one of it's components
            material.hasFlag(DISABLE_DECOMPOSITION)) return;
        DecompositionResult decompositionResult = calculateDecomposition(material);
        ArrayList<ItemStack> outputs = new ArrayList<>();
        ArrayList<FluidStack> fluidOutputs = new ArrayList<>();

        //compute outputs
        for (DecompositionEntry decompositionEntry : decompositionResult.decompositionEntries) {
            if (decompositionEntry.material instanceof DustMaterial) {
                double amount = decompositionEntry.relativeMass * decompositionResult.stackSize * GTValues.M;
                ItemStack itemStack = OreDictUnifier.getDust((DustMaterial) decompositionEntry.material, (long) amount);
                if (!itemStack.isEmpty()) outputs.add(itemStack);
            } else if (decompositionEntry.material instanceof FluidMaterial) {
                double amount = decompositionEntry.relativeMass * decompositionResult.stackSize;
                FluidMaterial componentMaterial = (FluidMaterial) decompositionEntry.material;
                FluidStack fluidStack = componentMaterial.getFluid((int) (1000 * amount));
                if (fluidStack.amount > 0) fluidOutputs.add(fluidStack);
            }
        }

        //generate builder
        RecipeBuilder builder;
        if (material.hasFlag(Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING)) {
            builder = RecipeMaps.ELECTROLYZER_RECIPES.recipeBuilder()
                .duration((int) material.getProtons() * decompositionResult.stackSize * 8)
                .EUt(getElectrolyzingVoltage(material));
        } else {
            builder = RecipeMaps.CENTRIFUGE_RECIPES.recipeBuilder()
                .duration((int) material.getAverageMass() * decompositionResult.stackSize * 2)
                .EUt(30);
        }
        builder.outputs(outputs);
        builder.fluidOutputs(fluidOutputs);

        //finish builder
        if (decomposePrefix == OrePrefix.dust) {
            builder.input(decomposePrefix, material, decompositionResult.stackSize);
        } else {
            builder.fluidInputs(material.getFluid(1000 * decompositionResult.stackSize));
        }
        if(material.hasFlag(DECOMPOSITION_REQUIRES_HYDROGEN)) {
            builder.fluidInputs(Materials.Hydrogen.getFluid(1000 * decompositionResult.stackSize));
        }

        //register recipe
        builder.buildAndRegister();
    }

    private static DecompositionResult calculateDecomposition(Material sourceMaterial) {
        List<MaterialStack> rawComponents = sourceMaterial.materialComponents;
        DecompositionEntry[] entries = new DecompositionEntry[rawComponents.size()];
        long totalMass = 0L;
        for(int i = 0; i < entries.length; i++) {
            MaterialStack materialStack = rawComponents.get(i);
            long absoluteMass = materialStack.material.getMass() * materialStack.amount;
            entries[i] = new DecompositionEntry(materialStack.material, absoluteMass);
            totalMass += absoluteMass;
        }
        for (DecompositionEntry entry : entries) {
            entry.relativeMass = entry.absoluteMass / (totalMass * 1.0);
        }
        StackSizeToDouble[] stackSizeRounding = new StackSizeToDouble[64];
        for(int stackSize = 1; stackSize <= 64; stackSize++) {
            double averageRounding = 0.0;
            for (DecompositionEntry entry : entries) {
                double itemAmount = entry.relativeMass * stackSize;
                averageRounding += Math.abs(Math.round(itemAmount) - itemAmount);
            }
            stackSizeRounding[stackSize - 1] = new StackSizeToDouble(stackSize, averageRounding / 3.0);
        }
        Arrays.sort(stackSizeRounding, Comparator.comparing(it -> it.roundingValue));
        return new DecompositionResult(stackSizeRounding[0].stackSize, entries);
    }

    private static class DecompositionResult {
        public final int stackSize;
        public DecompositionEntry[] decompositionEntries;

        public DecompositionResult(int stackSize, DecompositionEntry[] decompositionEntries) {
            this.stackSize = stackSize;
            this.decompositionEntries = decompositionEntries;
        }
    }

    private static class StackSizeToDouble {
        public final int stackSize;
        public final double roundingValue;

        public StackSizeToDouble(int stackSize, double roundingValue) {
            this.stackSize = stackSize;
            this.roundingValue = roundingValue;
        }
    }

    private static class DecompositionEntry {
        public final Material material;
        public final long absoluteMass;
        public double relativeMass;

        public DecompositionEntry(Material material, long absoluteMass) {
            this.material = material;
            this.absoluteMass = absoluteMass;
        }
    }

    //todo think something better with this
    private static int getElectrolyzingVoltage(Material material) {
        List<Material> components = material.materialComponents.stream()
            .map(s -> s.material).collect(Collectors.toList());
        //titanium or tungsten-containing materials electrolyzing requires 1920
        if(components.contains(Materials.Tungsten) ||
            components.contains(Materials.Titanium))
            return 1920; //EV voltage (tungstate and scheelite electrolyzing)
        return components.size() >= 4 ? 90 : 30;
    }
    
}
