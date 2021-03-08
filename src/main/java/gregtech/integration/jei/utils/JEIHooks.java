package gregtech.integration.jei.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import gregtech.api.util.GTUtility;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class JEIHooks {

    public static void addFluidTooltip(List<String> tooltip, Object ingredient) {
        if (ingredient instanceof FluidStack) {
            String formula = GTUtility.getFluidFormula((FluidStack) ingredient);
            if (formula != null && !formula.isEmpty()) {
                tooltip.add(1, ChatFormatting.GRAY + formula);
            }
        }
    }
}
