package gregtech.api.util;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class LocalisationUtils {

    public static String format(String p_format_0_, Object... p_format_1_) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            return net.minecraft.util.text.translation.I18n.translateToLocalFormatted(p_format_0_, p_format_1_);
        } else {
            return net.minecraft.client.resources.I18n.format(p_format_0_, p_format_1_);
        }
    }   
    
    public static String translateToLocal(String p_format_0_) {
        return net.minecraft.util.text.translation.I18n.translateToLocal(p_format_0_);
    }

    public static boolean hasKey(String p_hasKey_0_) {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            return net.minecraft.util.text.translation.I18n.canTranslate(p_hasKey_0_);
        } else {
            return net.minecraft.client.resources.I18n.hasKey(p_hasKey_0_);
        }
    }

}
