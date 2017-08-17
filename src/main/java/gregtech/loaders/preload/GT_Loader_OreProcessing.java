package gregtech.loaders.preload;

import gregtech.api.util.GTLog;
import gregtech.loaders.oreprocessing.*;

public class GT_Loader_OreProcessing
        implements Runnable {
    public void run() {
        GTLog.out.println("GT_Mod: Register Ore processing.");
        new ProcessingAll();
        new ProcessingBlock();
        new ProcessingBolt();
        new ProcessingCell();
        new ProcessingCircuit();
        new ProcessingCompressed();
        new ProcessingCrop();
        new ProcessingCrushedOre();
        new ProcessingCrystallized();
        new ProcessingDirty();
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
        new ProcessingTransforming();
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
    }
}