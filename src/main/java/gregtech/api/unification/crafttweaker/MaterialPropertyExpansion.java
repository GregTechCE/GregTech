package gregtech.api.unification.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.properties.*;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import static gregtech.api.unification.crafttweaker.CTMaterialHelpers.*;

@ZenExpansion("mods.gregtech.material.Material")
@ZenRegister
@SuppressWarnings("unused")
public class MaterialPropertyExpansion {

    // Property Checkers
    @ZenMethod
    public static boolean hasBlastTemp(Material m) {
        return m.hasProperty(PropertyKey.BLAST);
    }

    @ZenMethod
    public static boolean hasDust(Material m) {
        return m.hasProperty(PropertyKey.DUST);
    }

    @ZenMethod
    public static boolean hasFluidPipes(Material m) {
        return m.hasProperty(PropertyKey.FLUID_PIPE);
    }

    @ZenMethod
    public static boolean hasFluid(Material m) {
        return m.hasProperty(PropertyKey.FLUID);
    }

    @ZenMethod
    public static boolean hasGem(Material m) {
        return m.hasProperty(PropertyKey.GEM);
    }

    @ZenMethod
    public static boolean hasIngot(Material m) {
        return m.hasProperty(PropertyKey.INGOT);
    }

    @ZenMethod
    public static boolean hasItemPipes(Material m) {
        return m.hasProperty(PropertyKey.ITEM_PIPE);
    }

    @ZenMethod
    public static boolean hasOre(Material m) {
        return m.hasProperty(PropertyKey.ORE);
    }

    @ZenMethod
    public static boolean hasPlasma(Material m) {
        return m.hasProperty(PropertyKey.PLASMA);
    }

    @ZenMethod
    public static boolean hasTools(Material m) {
        return m.hasProperty(PropertyKey.TOOL);
    }

    @ZenMethod
    public static boolean hasWires(Material m) {
        return m.hasProperty(PropertyKey.WIRE);
    }

    // Property Setters
    @ZenMethod
    public static void addBlastTemp(Material m, int blastTemp) {
        if (checkFrozen("add blast temperature")) return;
        if (m.hasProperty(PropertyKey.BLAST)) m.getProperty(PropertyKey.BLAST).setBlastTemperature(blastTemp);
        else m.setProperty(PropertyKey.BLAST, new BlastProperty(blastTemp));
    }

    @ZenMethod
    public static void addDust(Material m, @Optional int harvestLevel, @Optional int burnTime) {
        if (checkFrozen("add a dust to a material")) return;
        if (harvestLevel == 0) harvestLevel = 2;
        if (m.hasProperty(PropertyKey.DUST)) {
            m.getProperty(PropertyKey.DUST).setHarvestLevel(harvestLevel);
            m.getProperty(PropertyKey.DUST).setBurnTime(burnTime);
        } else m.setProperty(PropertyKey.DUST, new DustProperty(harvestLevel, burnTime));
    }

    @ZenMethod
    public static void addFluidPipes(Material m, int maxFluidTemperature, int throughput, boolean gasProof) {
        if (checkFrozen("add fluid pipes to a material")) return;
        if (m.hasProperty(PropertyKey.FLUID_PIPE)) {
            m.getProperty(PropertyKey.FLUID_PIPE).maxFluidTemperature = maxFluidTemperature;
            m.getProperty(PropertyKey.FLUID_PIPE).throughput = throughput;
            m.getProperty(PropertyKey.FLUID_PIPE).gasProof = gasProof;
        } else m.setProperty(PropertyKey.FLUID_PIPE, new FluidPipeProperties(maxFluidTemperature, throughput, gasProof));
    }

    @ZenMethod
    public static void addFluid(Material m, @Optional String fluidTypeName, @Optional boolean hasBlock) {
        if (checkFrozen("add a Fluid to a material")) return;
        Material.FluidType type = validateFluidType(fluidTypeName);
        if (m.hasProperty(PropertyKey.FLUID)) {
            m.getProperty(PropertyKey.FLUID).setIsGas(type == Material.FluidType.GAS);
            m.getProperty(PropertyKey.FLUID).setHasBlock(hasBlock);
        } else m.setProperty(PropertyKey.FLUID, new FluidProperty(type == Material.FluidType.GAS, hasBlock));
    }

    @ZenMethod
    public static void addGem(Material m) {
        if (checkFrozen("add a Gem to a material")) return;
        if (!m.hasProperty(PropertyKey.GEM)) m.setProperty(PropertyKey.GEM, new GemProperty());
    }

    @ZenMethod
    public static void addIngot(Material m) {
        if (checkFrozen("add an Ingot to a material")) return;
        if (!m.hasProperty(PropertyKey.INGOT)) m.setProperty(PropertyKey.INGOT, new IngotProperty());
    }
}
