package gregtech.api.enums;

public enum ConfigCategories {
    news,
    general,
    machineconfig,
    specialunificationtargets;

    public enum IDs {
        crops,
        enchantments;
    }

    public enum Materials {
        heatdamage,
        oreprocessingoutputmultiplier,
        blastfurnacerequirements,
        blastinductionsmelter,;
    }

    public enum Recipes {
        researches,
        harderrecipes,
        gregtechrecipes,
        disabledrecipes,
        recipereplacements,
        storageblockcrafting,
        storageblockdecrafting;
    }

    public enum Machines {
        smelting,
        squeezer,
        liquidtransposer,
        liquidtransposerfilling,
        liquidtransposeremptying,
        extractor,
        sawmill,
        compression,
        thermalcentrifuge,
        orewashing,
        inductionsmelter,
        rcblastfurnace,
        scrapboxdrops,
        massfabamplifier,
        maceration,
        rockcrushing,
        pulverization;
    }

    public enum Fuels {
        boilerfuels;
    }

    public enum Tools {
        mortar,
        hammerplating,
        hammermultiingot,
        hammerdoubleplate,
        hammertripleplate,
        hammerquadrupleplate,
        hammerquintupleplate;
    }
}