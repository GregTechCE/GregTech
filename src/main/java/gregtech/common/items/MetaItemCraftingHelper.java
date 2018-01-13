package gregtech.common.items;

import gregtech.api.GTValues;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

public class MetaItemCraftingHelper {
    public static void init() {
        CraftingHelper.register(new ResourceLocation(GTValues.MODID, "metaitem"), this);
    }
}
