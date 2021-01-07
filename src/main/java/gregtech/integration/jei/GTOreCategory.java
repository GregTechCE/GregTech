package gregtech.integration.jei;

import gregtech.api.gui.GuiTextures;
import gregtech.integration.jei.recipe.primitive.PrimitiveRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;

public class GTOreCategory extends PrimitiveRecipeCategory<GTOreInfo, GTOreInfo> {

    protected final IDrawable slot;
    protected String veinName;
    protected int minHeight;
    protected int maxHeight;
    protected int outputCount;
    protected int weight;
    protected int[] dimensionIDs;
    protected final int FONT_HEIGHT = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;

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
        itemStackGroup.init(0, true, 22, 29);
        //The Surface Identifier
        itemStackGroup.init(1, true, 22, 73);


        for(int i = 0; i < recipeWrapper.getOutputCount(); i++) {
            itemStackGroup.init(i + 2, false, baseXPos + (--counter * 18), baseYPos);
            //Only Span 5 slots in the X direction
            if((baseXPos + (counter * 18)) == (baseXPos + (5 * 18))) {
                //Increment the Y display
                baseYPos = baseYPos + 18;
                counter = 0;
            }

            counter++;
        }

        itemStackGroup.addTooltipCallback(recipeWrapper::addBiomeTooltip);
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
        //Selected Ore
        this.slot.draw(minecraft, 22, 29);
        //Surface Identifier
        this.slot.draw(minecraft, 22, 73);

        int baseXPos = 70;
        int baseYPos = 19;
        int counter = 1;
        int endPos;

        for(int i = 0; i < outputCount; i++) {
            this.slot.draw(minecraft, baseXPos + (--counter * 18), baseYPos);
            //Only Span 5 slots in the X direction
            if((baseXPos + (counter * 18)) == (baseXPos + (5 * 18))) {
                //Increment the Y display
                baseYPos = baseYPos + 18;
                counter = 0;
            }

            counter++;
        }

        //Draw the Vein Name
        minecraft.fontRenderer.drawString(veinName,70, 1, 0x111111);

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
        for(int i  = 0; i < dimensionIDs.length; i++) {

            //Draw with commas between the dimensions
            if(i != dimensionIDs.length - 1) {
                minecraft.fontRenderer.drawString(Integer.toString(dimensionIDs[i]) + ", ", 70, endPos + 1 + (3 * FONT_HEIGHT), 0x111111);
            }
            else {
                minecraft.fontRenderer.drawString(Integer.toString(dimensionIDs[i]), 70, endPos + 1 + (3 * FONT_HEIGHT), 0x111111);
            }
        }


        //Label the Surface Identifier
        minecraft.fontRenderer.drawString("Surface", 15, 92, 0x111111);
        minecraft.fontRenderer.drawString("Material", 15, 92 + FONT_HEIGHT, 0x111111);


    }
}
