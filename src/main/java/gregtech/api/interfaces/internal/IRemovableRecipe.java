package gregtech.api.interfaces.internal;

import net.minecraft.item.crafting.IRecipe;

public interface IRemovableRecipe extends IRecipe {

    boolean isRemovable();
}
