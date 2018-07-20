package gregtech.integration.theoneprobe.element;

import com.google.common.collect.Lists;
import mcjty.theoneprobe.api.IProgressStyle;
import mcjty.theoneprobe.api.TextStyleClass;
import mcjty.theoneprobe.apiimpl.elements.ElementProgress;
import mcjty.theoneprobe.config.Config;
import mcjty.theoneprobe.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static gregtech.integration.theoneprobe.element.ElementTextAdvanced.SUBENDLOC;
import static gregtech.integration.theoneprobe.element.ElementTextAdvanced.SUBSTARTLOC;
import static mcjty.theoneprobe.api.IProbeInfo.ENDLOC;
import static mcjty.theoneprobe.api.IProbeInfo.STARTLOC;

public class ElementRenders {

    public static void render(IProgressStyle style, Number current, Number max, String format1, String format2, String infixWithFormat, int x, int y, int w, int h) {
        RenderHelper.drawThickBeveledBox(x, y, x + w, y + h, 1, style.getBorderColor(), style.getBorderColor(), style.getBackgroundColor());
        if (current.doubleValue() > 0 && max.doubleValue() > 0) {
            // Determine the progress bar width, but limit it to the size of the element (minus 2).
            int dx = (int) Math.min((current.doubleValue() * (w - 2) / max.doubleValue()), w - 2);

            if (style.getFilledColor() == style.getAlternatefilledColor()) {
                if (dx > 0) {
                    RenderHelper.drawThickBeveledBox(x + 1, y + 1, x + dx + 1, y + h - 1, 1, style.getFilledColor(), style.getFilledColor(), style.getFilledColor());
                }
            } else {
                for (int xx = x + 1; xx <= x + dx + 1; xx++) {
                    int color = (xx & 1) == 0 ? style.getFilledColor() : style.getAlternatefilledColor();
                    RenderHelper.drawVerticalLine(xx, y + 1, y + h - 1, color);
                }
            }
        }

        if (style.isShowText()) {
            if (!"".equals(format1) || !"".equals(format2)) {
                String num1, num2;
                if (!"".equals(format1)) {
                    num1 = new DecimalFormat(format1).format(current);
                } else {
                    num1 = ElementProgress.format(current.longValue(), style.getNumberFormat(), "");
                }
                if (!"".equals(format2)) {
                    num2 = new DecimalFormat(format2).format(max);
                } else {
                    num2 = ElementProgress.format(max.longValue(), style.getNumberFormat(), "");
                }
                RenderHelper.renderText(Minecraft.getMinecraft(), x + 3, y + 2, style.getPrefix() + String.format(stylifyStringAdvanced(infixWithFormat), num1, num2) + style.getSuffix());
            } else {
                RenderHelper.renderText(Minecraft.getMinecraft(), x + 3, y + 2, style.getPrefix() + ElementProgress.format(current.longValue(), style.getNumberFormat(), style.getSuffix()));
            }
        }
    }

    public static void renderTextAdvanced(String text, int x, int y) {
        RenderHelper.renderText(Minecraft.getMinecraft(), x, y, stylifyStringAdvanced(text));
    }

    public static int getWidthTextAdvanced(String text) {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(stylifyStringAdvanced(text));
    }

    private static String stylifyStringAdvanced(String text) {
        while (text.contains(STARTLOC) && text.contains(ENDLOC)) {
            int start = text.indexOf(STARTLOC);
            int end = text.indexOf(ENDLOC);
            if (start < end) {
                // Translation is needed
                String left = text.substring(0, start);
                String middle = text.substring(start + 2, end);
                List<String> replacements = Lists.newArrayList();
                while (middle.contains(SUBSTARTLOC) && middle.contains(SUBENDLOC)) {
                    int subStart = middle.indexOf(SUBSTARTLOC);
                    int subEnd = middle.indexOf(SUBENDLOC);
                    String subLeft = middle.substring(0, subStart);
                    replacements.add(middle.substring(subStart + 2, subEnd));
                    String subRight = middle.substring(subEnd + 2);
                    middle = subLeft + subRight;
                }
                middle = I18n.format(middle, replacements.toArray()).trim();
                String right = text.substring(end+2);
                text = left + middle + right;
            } else {
                break;
            }
        }
        if (text.contains("{=")) {
            Set<TextStyleClass> stylesNeedingContext = EnumSet.noneOf(TextStyleClass.class);
            TextStyleClass context = null;
            for (TextStyleClass styleClass : Config.textStyleClasses.keySet()) {
                if (text.contains(styleClass.toString())) {
                    String replacement = Config.getTextStyle(styleClass);
                    if ("context".equals(replacement)) {
                        stylesNeedingContext.add(styleClass);
                    } else if (context == null) {
                        context = styleClass;
                        text = StringUtils.replace(text, styleClass.toString(), replacement);
                    } else {
                        text = StringUtils.replace(text, styleClass.toString(), replacement);
                    }
                }
            }
            if (context != null) {
                for (TextStyleClass styleClass : stylesNeedingContext) {
                    String replacement = Config.getTextStyle(context);
                    text = StringUtils.replace(text, styleClass.toString(), replacement);
                }
            }
        }
        return text;
    }
}
