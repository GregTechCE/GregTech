package gregtech.integration.jei;

import gregtech.api.gui.GuiTextures;
import gregtech.api.worldgen.config.WorldGenRegistry;
import gregtech.integration.jei.recipe.primitive.PrimitiveRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.Map;

public class GTOreCategory extends PrimitiveRecipeCategory<GTOreInfo, GTOreInfo> {

    protected final IDrawable slot;
    protected String veinName;
    protected int minHeight;
    protected int maxHeight;
    protected int outputCount;
    protected int weight;
    protected int[] dimensionIDs;
    protected final int FONT_HEIGHT = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
    protected final Map<String, Integer> namedDimensions = WorldGenRegistry.getNamedDimensions();
    private final int NUM_OF_SLOTS = 5;
    private final int SLOT_WIDTH = 18;
    private final int SLOT_HEIGHT = 18;

    public GTOreCategory(IGuiHelper guiHelper) {
        super("ore_spawn_location",
            "ore.spawnlocation.name",
            guiHelper.createBlankDrawable(176, 166),
            guiHelper);

        this.slot = guiHelper.createDrawable(GuiTextures.SLOT.imageLocation, 0, 0, 18, 18, 18, 18);
    }


    @Override
    public void setRecipe(IRecipeLayout recipeLayout, GTOreInfo recipeWrapper, IIngredients ingredients) {

        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        int baseYPos = 19;

        //The ore selected from JEI
        itemStackGroup.init(0, true, 22, baseYPos);
        //The Surface Identifier
        itemStackGroup.init(1, true, 22, 73);


        for(int i = 0; i < recipeWrapper.getOutputCount(); i++) {
            int yPos = baseYPos + (i / NUM_OF_SLOTS) * SLOT_HEIGHT;
            int xPos = 70 + (i % NUM_OF_SLOTS) * SLOT_WIDTH;

            itemStackGroup.init(i + 2, false, xPos, yPos);
        }

        itemStackGroup.addTooltipCallback(recipeWrapper::addTooltip);
        itemStackGroup.set(ingredients);
        veinName = recipeWrapper.getVeinName();
        minHeight = recipeWrapper.getMinHeight();
        maxHeight = recipeWrapper.getMaxHeight();
        outputCount = recipeWrapper.getOutputCount();
        weight = recipeWrapper.getWeight();
        dimensionIDs = recipeWrapper.getDimensionIDs();
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(GTOreInfo recipe) {
        return recipe;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {

        int baseXPos = 70;
        int baseYPos = 19;
        int dimDisplayPos = 70;
        int dimDisplayLength;
        String dimName;
        String fullDimName;

        //Selected Ore
        this.slot.draw(minecraft, 22, baseYPos);
        //Surface Identifier
        this.slot.draw(minecraft, 22, 73);

        int yPos = 0;
        for(int i = 0; i < outputCount; i++) {
            yPos = baseYPos + (i / NUM_OF_SLOTS) * SLOT_HEIGHT;
            int xPos = baseXPos + (i % NUM_OF_SLOTS) * SLOT_WIDTH;

            this.slot.draw(minecraft, xPos, yPos);
        }

        baseYPos = yPos; //has to be set to position of last rendered slot for later use

        drawVeinName(minecraft.fontRenderer);

        //Begin Drawing information, depending on how many rows of ore outputs were created
        //Give room for 5 lines of 5 ores each, so 25 unique ores in the vein
        if(baseYPos > 73) {
            minecraft.fontRenderer.drawString("Spawn Range: " + minHeight + "-" + maxHeight, 70, baseYPos + 1, 0x111111);
        }
        else {
            minecraft.fontRenderer.drawString("Spawn Range: " + minHeight + "-" + maxHeight, 70, 73, 0x111111);
            //Update the position at which the spawn information ends
            baseYPos = 73;
        }

        //Create the Weight
        minecraft.fontRenderer.drawString("Vein Weight: " + weight, 70, baseYPos + FONT_HEIGHT, 0x111111);

        //Create the Dimensions
        minecraft.fontRenderer.drawString("Dimensions: ", 70, baseYPos + (2 * FONT_HEIGHT), 0x111111);

        //Will attempt to write dimension IDs in a single line, separated by commas. If the list is so long such that it
        //would run off the end of the page, the list is continued on a new line.
        for(int i = 0; i < dimensionIDs.length; i++) {

            //If the dimension name is included, append it to the dimension number
            if(namedDimensions.containsValue(dimensionIDs[i])) {
                int finalI = i;
                dimName = namedDimensions.entrySet().stream()
                                            .filter(entry -> dimensionIDs[finalI] == entry.getValue())
                                            .map(Map.Entry::getKey)
                                            .findFirst().get();
                fullDimName = i == dimensionIDs.length - 1 ?
                    dimensionIDs[i] + " (" + dimName + ")" :
                    dimensionIDs[i] + " (" + dimName + "),";
            }
            //If the dimension name is not included, just add the dimension number
            else {

                fullDimName = i == dimensionIDs.length - 1 ?
                    Integer.toString(dimensionIDs[i]) :
                    dimensionIDs[i] + ",";
            }

            //Find the length of the dimension name string
            dimDisplayLength = minecraft.fontRenderer.getStringWidth(fullDimName);

            //If the length of the string would go off the edge of screen, instead increment the y position
            if(dimDisplayLength > (176 - dimDisplayPos)) {
                baseYPos = baseYPos + FONT_HEIGHT;
                dimDisplayPos = 70;
            }

            minecraft.fontRenderer.drawString(fullDimName, dimDisplayPos, baseYPos + (3 * FONT_HEIGHT), 0x111111);

            //Increment the dimension name display position
            dimDisplayPos = dimDisplayPos + dimDisplayLength;
        }


        //Label the Surface Identifier
        minecraft.fontRenderer.drawSplitString("SurfaceMaterial", 15, 92, 42, 0x111111);

    }

    private void drawVeinName(final FontRenderer fontRenderer) {
        final int maxVeinNameLength = 176;

        String veinNameToDraw = veinName;

        //Account for really long names
        if (fontRenderer.getStringWidth(veinNameToDraw) > maxVeinNameLength) {
            veinNameToDraw = fontRenderer.trimStringToWidth(veinName, maxVeinNameLength - 3, false) + "...";
        }

        //Ensure that the vein name is centered
        int startPosition = (maxVeinNameLength - fontRenderer.getStringWidth(veinNameToDraw)) / 2;

        fontRenderer.drawString(veinNameToDraw, startPosition, 1, 0x111111);
    }
}
