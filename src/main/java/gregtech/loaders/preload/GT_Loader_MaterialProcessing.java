package gregtech.loaders.preload;

import gregtech.api.util.GT_Log;
import gregtech.loaders.materialprocessing.ProcessingConfig;

public class GT_Loader_MaterialProcessing implements Runnable {
    public void run() {
        GT_Log.out.println("GT_Mod: Register Material processing.");
        new ProcessingConfig();
    }
}
