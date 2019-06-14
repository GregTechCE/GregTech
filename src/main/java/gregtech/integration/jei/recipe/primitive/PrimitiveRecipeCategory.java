package gregtech.integration.jei.recipe.primitive;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import gregtech.api.GTValues;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public abstract class PrimitiveRecipeCategory <T, W extends IRecipeWrapper> implements IRecipeCategory<W>, IRecipeWrapperFactory<T>{

	public String uniqueName;
	public String localizedName;
	protected IDrawable background;
	private final Class<T> recipeClass;

	public PrimitiveRecipeCategory(String uniqueName, String localKey, IDrawable background, Class<T> recipeClass)
	{
		this.uniqueName = uniqueName;
		this.localizedName = I18n.format(localKey);
		this.background = background;
		this.recipeClass = recipeClass;
	}

	@Nullable
	@Override
	public IDrawable getIcon()
	{
		return null;
	}

	@Override
	public String getUid()
	{
		return getModName()+":"+uniqueName;
	}

	@Override
	public String getTitle()
	{
		return localizedName;
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft)
	{
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		return Collections.emptyList();
	}

	public Class<T> getRecipeClass()
	{
		return this.recipeClass;
	}

	public String getRecipeCategoryUid()
	{
		return getModName()+":"+uniqueName;
	}
	
	public boolean isRecipeValid(T recipe)
	{
		return true;
	}

	@Override
	public String getModName()
	{
		return GTValues.MODID;
	}
}
