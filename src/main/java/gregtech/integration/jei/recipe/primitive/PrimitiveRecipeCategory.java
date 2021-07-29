package gregtech.integration.jei.recipe.primitive;

import gregtech.api.GTValues;
import gregtech.api.util.LocalisationUtils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public abstract class PrimitiveRecipeCategory<T, W extends IRecipeWrapper> implements IRecipeCategory<W>, IRecipeWrapperFactory<T> {

	public String uniqueName;
	public String localizedName;
	protected IDrawable background;

	public PrimitiveRecipeCategory(String uniqueName, String localKey, IDrawable background, IGuiHelper guiHelper) {
		this.uniqueName = uniqueName;
		this.localizedName = LocalisationUtils.format(localKey);
		this.background = background;
	}

	@Nullable
	@Override
	public IDrawable getIcon() {
		return null;
	}

	@Override
	public String getUid() {
		return getModName() + ":" + uniqueName;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		return Collections.emptyList();
	}

	@Override
	public String getModName() {
		return GTValues.MODID;
	}
}
