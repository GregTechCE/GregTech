package gregtech.loaders.postload;

import gregtech.api.recipes.ModHandler;
import gregtech.api.util.GTLog;
import net.minecraft.init.Blocks;

public class GT_MinableRegistrator
        implements Runnable {
    public void run() {
        GTLog.out.println("GT_Mod: Adding Blocks to the Miners Valuable List.");
        ModHandler.addValuableOre(Blocks.GLOWSTONE, 0, 1);
        ModHandler.addValuableOre(Blocks.SOUL_SAND, 0, 1);
    }
}
