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
        int baseXPos = 70;
        int baseYPos = 19;
        int counter = 1;

        //The ore selected from JEI
        itemStackGroup.init(0, true, 22, baseYPos);
        //The Surface Identifier
        itemStackGroup.init(1, true, 22, 73);


        for(int i = 0; i < recipeWrapper.getOutputCount(); i++) {
            int temp = counter - 1;
            itemStackGroup.init(i + 2, false, baseXPos + (temp * 18), baseYPos);
            //Only Span 5 slots in the X direction
            if((baseXPos + (counter * 18)) == (baseXPos + (5 * 18))) {
                //Increment the Y display
                baseYPos = baseYPos + 18;
                counter = 0;
            }

            counter++;
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
        int counter = 1;
        int endPos;
        int dimDisplayPos = 70;
        int dimDisplayLength;
        String dimName;

        //Selected Ore
        this.slot.draw(minecraft, 22, baseYPos);
        //Surface Identifier
        this.slot.draw(minecraft, 22, 73);

        for(int i = 0; i < outputCount; i++) {
            int temp = counter - 1;
            this.slot.draw(minecraft, baseXPos + (temp * 18), baseYPos);
            //Only Span 5 slots in the X direction
            if((baseXPos + (counter * 18)) == (baseXPos + (5 * 18))) {
                //Increment the Y display
                baseYPos = baseYPos + 18;
                counter = 0;
            }

            counter++;
        }

        //Draw the Vein Name
        int veinNameLength = minecraft.fontRenderer.getStringWidth(veinName);
        int startPosition = (176 - veinNameLength)/2;
        if(startPosition < 0) {
            startPosition = 0;
        }

        //Account for really long names
        if(veinNameLength > 176) {
            String newVeinName = minecraft.fontRenderer.trimStringToWidth(veinName, 176, false);
            newVeinName = newVeinName.substring(0, newVeinName.length() - 4) + "...";

            minecraft.fontRenderer.drawString(newVeinName, startPosition, 1, 0x111111);
        }
        else {
            minecraft.fontRenderer.drawString(veinName, startPosition, 1, 0x111111);
        }

        //Begin Drawing information, depending on how many rows of ore outputs were created
        //Give room for 5 lines of 5 ores each, so 25 unique ores in the vein
        if(baseYPos > 109) { //109 is starting pos of 19 + (5 * 18)
            minecraft.fontRenderer.drawString("Spawn Range: " + minHeight + "-" + maxHeight, 70, baseYPos + 1, 0x111111);
            endPos = baseYPos + 1;
        }
        else {
            if(baseYPos > 73) {
                minecraft.fontRenderer.drawString("Spawn Range: " + minHeight + "-" + maxHeight, 70, baseYPos + 1, 0x111111);
                endPos = baseYPos + 1;
            }
            else {
                minecraft.fontRenderer.drawString("Spawn Range: " + minHeight + "-" + maxHeight, 70, 73, 0x111111);
                endPos = 73;
            }
        }

        //Create the Weight
        minecraft.fontRenderer.drawString("Vein Weight: " + weight, 70, endPos + FONT_HEIGHT, 0x111111);

        //Create the Dimensions
        minecraft.fontRenderer.drawString("Dimensions: ", 70, endPos + 1 + (2 * FONT_HEIGHT), 0x111111);
        for(int i = 0; i < dimensionIDs.length; i++) {

            if(namedDimensions.containsValue(dimensionIDs[i])) {
                int finalI = i;
                dimName = namedDimensions.entrySet().stream()
                                            .filter(entry -> dimensionIDs[finalI] == entry.getValue())
                                            .map(Map.Entry::getKey)
                                            .findFirst().get();
                String fullName = i == dimensionIDs.length -1 ?
                    dimensionIDs[i] + " (" + dimName + ")" :
                    dimensionIDs[i] + " (" + dimName + "),";

                dimDisplayLength = minecraft.fontRenderer.getStringWidth(fullName);

                //Check if the name is too long to fit, and drop down a line if it is
                if(dimDisplayLength > 176 - dimDisplayPos) {
                    endPos = endPos + FONT_HEIGHT;
                    dimDisplayPos = 70;
                }

                minecraft.fontRenderer.drawString(fullName, dimDisplayPos, endPos + 1 + (3 * FONT_HEIGHT), 0x111111);

            }
            else {

                dimName = i == dimensionIDs.length - 1 ?
                    Integer.toString(dimensionIDs[i]) :
                    dimensionIDs[i] + ",";

                dimDisplayLength = minecraft.fontRenderer.getStringWidth(dimName);

                if(dimDisplayLength > (176 - dimDisplayPos)) {
                    endPos = endPos + FONT_HEIGHT;
                    dimDisplayPos = 70;
                }

                minecraft.fontRenderer.drawString(dimName, dimDisplayPos, endPos + (3 * FONT_HEIGHT), 0x111111);
            }
            dimDisplayPos = dimDisplayPos + dimDisplayLength;
        }


        //Label the Surface Identifier
        minecraft.fontRenderer.drawSplitString("SurfaceMaterial", 15, 92, 42, 0x111111);

    }
}
