package gregtech.loaders.misc;

import cpw.mods.fml.common.Loader;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.core.genetics.alleles.AlleleHelper;
import forestry.core.genetics.alleles.EnumAllele;
import gregtech.GT_Mod;
import gregtech.common.items.ItemComb;

public class GT_Bees {

    public static ItemComb combs;

    public GT_Bees() {
        if (Loader.isModLoaded("Forestry") && GT_Mod.gregtechproxy.mGTBees) {
            combs = new ItemComb();
            combs.initCombsRecipes();
            GT_BeeDefinition.initBees();            
        }
    }
}
