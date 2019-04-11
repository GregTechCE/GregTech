package gregtech.api.gui.impl;

import gregtech.api.gui.Widget;
import gregtech.api.gui.igredient.IGhostIngredientTarget;
import gregtech.api.gui.igredient.IIngredientSlot;
import mezz.jei.api.gui.IAdvancedGuiHandler;
import mezz.jei.api.gui.IGhostIngredientHandler;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ModularUIGuiHandler implements IAdvancedGuiHandler<ModularUIGui>, IGhostIngredientHandler<ModularUIGui> {

    @Override
    public Class<ModularUIGui> getGuiContainerClass() {
        return ModularUIGui.class;
    }

    @Nullable
    @Override
    public Object getIngredientUnderMouse(ModularUIGui gui, int mouseX, int mouseY) {
        Collection<Widget> widgets = gui.getModularUI().guiWidgets.values();
        for (Widget widget : widgets) {
            if (widget instanceof IIngredientSlot) {
                return ((IIngredientSlot) widget).getIngredientOverMouse(mouseX, mouseY);
            }
        }
        return null;
    }

    @Override
    public <I> List<Target<I>> getTargets(ModularUIGui gui, I ingredient, boolean doStart) {
        Collection<Widget> widgets = gui.getModularUI().guiWidgets.values();
        List<Target<I>> targets = new ArrayList<>();
        for(Widget widget : widgets) {
            if(widget instanceof IGhostIngredientTarget) {
                IGhostIngredientTarget ghostTarget = (IGhostIngredientTarget) widget;
                List<Target<?>> widgetTargets = ghostTarget.getPhantomTargets(ingredient);
                //noinspection unchecked
                targets.addAll((List<Target<I>>) (Object) widgetTargets);
            }
        }
        return targets;
    }

    @Nullable
    @Override
    public List<Rectangle> getGuiExtraAreas(ModularUIGui guiContainer) {
        return Collections.emptyList();
    }

    @Override
    public void onComplete() {
    }
}
