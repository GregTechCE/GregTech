package gregtech.api.util;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class LocalisationUtils {

    /**
     * @deprecated
     * This function should only be used when localisation is needed on the server.
     * Helper function that automatically calls `net.minecraft.client.resources.I18n.format` when called on client
     * or `net.minecraft.util.text.translation.I18n.translateToLocalFormatted` when called on server.
     * @param p_format_0_ passed to the underlying format function as first parameter
     * @param p_format_1_ passed to the underlying format function as second parameter
     * @return the localized string.
     */
    @Deprecated
    public static String format(String p_format_0_, Object... p_format_1_) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            return net.minecraft.util.text.translation.I18n.translateToLocalFormatted(p_format_0_, p_format_1_);
        } else {
            return net.minecraft.client.resources.I18n.format(p_format_0_, p_format_1_);
        }
    }

    /**
     * @deprecated
     * This function should only be used when localisation is needed on the server.
     * Helper function that automatically calls `net.minecraft.client.resources.I18n.hasKey` when called on client
     * or `net.minecraft.util.text.translation.I18n.canTranslate` when called on server.
     * @param p_hasKey_0_ passed to the underlying hasKey function as first parameter
     * @return the localized string.
     */
    @Deprecated
    public static boolean hasKey(String p_hasKey_0_) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            return net.minecraft.util.text.translation.I18n.canTranslate(p_hasKey_0_);
        } else {
            return net.minecraft.client.resources.I18n.hasKey(p_hasKey_0_);
        }
    }

}
