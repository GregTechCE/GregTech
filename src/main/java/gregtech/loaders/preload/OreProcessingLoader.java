package gregtech.loaders.preload;

import gregtech.api.util.GTLog;
import gregtech.loaders.oreprocessing.*;

public class OreProcessingLoader implements Runnable {

    public void run() {
        GTLog.logger.info("Registering Ore processing.");
        ProcessingBlock.register();
        ProcessingBolt.register();
        ProcessingClearDust.register();
        ProcessingCombining.register();
        ProcessingCraftingLens.register();
        ProcessingCrushed.register();
        ProcessingCrushedCentrifuged.register();
        ProcessingCrushedPurified.register();
        ProcessingCrystallized.register();
        ProcessingDecomposition.register();
        ProcessingDirtyDust.register();
        ProcessingDye.register();
        ProcessingFineWire.register();
        ProcessingFoil.register();
        ProcessingGear.register();
        ProcessingGem.register();
        ProcessingIngot.register();
        ProcessingLens.register();
        ProcessingLog.register();
        ProcessingPlank.register();
        ProcessingPlate.register();
        ProcessingPolarizing.register();
        ProcessingPure.register();
        ProcessingRotor.register();
        ProcessingRound.register();
        ProcessingSaplings.register();
        ProcessingScrew.register();
        ProcessingShaping.register();
        ProcessingStick.register();
        ProcessingStone.register();
        ProcessingToolHead.register();
        ProcessingToolOther.register();
        ProcessingWire.register();

//        if (Loader.isModLoaded("railcraft")) {
//            OrePrefix.slab.addProcessingHandler((entry, modName, itemStack) -> {
//                if (entry.material == Materials.Wood) {
//                    RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
//                            .inputs(GTUtility.copyAmount(3, itemStack.asItemStack()))
//                            .fluidInputs(Materials.Creosote.getFluid(1000))
//                            .outputs(RailcraftItems.TIE.getStack(1, ItemTie.EnumTie.WOOD))
//                            .duration(200)
//                            .EUt(4)
//                            .buildAndRegister();
//                }
//            });
//        }
    }
}