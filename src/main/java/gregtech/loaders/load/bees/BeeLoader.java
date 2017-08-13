package gregtech.loaders.load.bees;

import gregtech.common.items.ItemComb;
import net.minecraftforge.fml.common.Loader;

public class BeeLoader implements Runnable {

    public static ItemComb combs;

    public void run() {
        if (Loader.isModLoaded("Forestry")) {
            combs = new ItemComb();
            combs.initCombsRecipes();
            BeeDefinitions.initBees();
        }
    }

}
