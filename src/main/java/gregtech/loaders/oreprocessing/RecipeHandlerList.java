package gregtech.loaders.oreprocessing;

public class RecipeHandlerList {

    public static void register() {
        MaterialRecipeHandler.register();
        OreRecipeHandler.register();
        PartsRecipeHandler.register();
        WireRecipeHandler.register();
        PipeRecipeHandler.register();
        ToolRecipeHandler.register();
        PolarizingRecipeHandler.register();
        DecompositionRecipeHandler.register();
        RecyclingRecipeHandler.register();
    }

}
