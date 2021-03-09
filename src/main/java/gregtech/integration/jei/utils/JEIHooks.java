package gregtech.integration.jei.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import gregtech.api.util.GTUtility;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * Class used for code injection into JEI.
 * Not intended for normal use within GTCE.
 */
public class JEIHooks {

    /**
     * This method is NOT intended to be called by GTCE.
     * Do NOT use this method for any reason.
     */
    public static void addFluidTooltip(List<String> tooltip, Object ingredient) {
        if (ingredient instanceof FluidStack) {
            String formula = GTUtility.getFluidFormula((FluidStack) ingredient);
            if (formula != null && !formula.isEmpty()) {
                tooltip.add(1, ChatFormatting.GRAY + formula);
            }
        }
    }
}
