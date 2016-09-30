package gregtech.jei;

import mezz.jei.api.IJeiHelpers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class JEI_Compat {


    public static void hideItem(ItemStack stack) {
        if(Loader.isModLoaded("JustEnoughItems")) {
            try {
                hideItemUnsafe(stack);
            } catch (Exception exception) {
                System.out.println("Can't blacklist " + stack + " in JEI!");
                exception.printStackTrace();
            }
        }
    }

    public static void hideItemUnsafe(ItemStack stack) {
        IJeiHelpers helpers = JEI_GT_Plugin.getJeiHelpers();
        if(helpers != null) {
            helpers.getItemBlacklist().addItemToBlacklist(stack);
        }
    }

}
