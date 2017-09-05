package gregtech.loaders.preload;

import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.loaders.oreprocessing.*;
import mods.railcraft.common.items.ItemTie;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraftforge.fml.common.Loader;

public class OreProcessingLoader implements Runnable {
    public void run() {
        GTLog.out.println("GregTechMod: Register Ore processing.");
        new ProcessingAll();
        new ProcessingBlock();
        new ProcessingBolt();
        new ProcessingCell();
        new ProcessingCircuit();
        new ProcessingCompressed();
        new ProcessingCrop();
        new ProcessingCrushedOre();
        new ProcessingCrystallized();
        new ProcessingCrushed();
        new ProcessingDust();
        new ProcessingDye();
        new ProcessingFoil();
        new ProcessingFineWire();
        new ProcessingLens();
        new ProcessingShaping();
        new ProcessingGem();
        new ProcessingGear();
        new ProcessingIngot();
        new ProcessingItem();
        new ProcessingLog();
        new ProcessingPolarizing();
        new ProcessingNugget();
        new ProcessingOre();
        new ProcessingOrePoor();
        new ProcessingOreSmelting();
        new ProcessingPipe();
        new ProcessingPlank();
        new ProcessingPlate();
        new ProcessingPure();
        new ProcessingRecycling();
        new ProcessingRound();
        new ProcessingRotor();
        new ProcessingSaplings();
        new ProcessingScrew();
        new ProcessingStick();
        new ProcessingStone();
        new ProcessingToolHead();
        new ProcessingToolOther();
        new ProcessingWire();

        if (Loader.isModLoaded("railcraft")) {
            OrePrefix.slab.addProcessingHandler((entry, modName, itemStack) -> {
                if (entry.material == Materials.Wood) {
                    RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
                            .inputs(GTUtility.copyAmount(3, itemStack.asItemStack()))
                            .fluidInputs(Materials.Creosote.getFluid(1000))
                            .outputs(RailcraftItems.TIE.getStack(1, ItemTie.EnumTie.WOOD))
                            .duration(200)
                            .EUt(4)
                            .buildAndRegister();
                }
            });
        }
    }
}