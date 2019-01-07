package gregtech.api.gui.widgets;

import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.tab.HorizontalTabListRenderer;
import gregtech.api.gui.widgets.tab.HorizontalTabListRenderer.HorizontalStartCorner;
import gregtech.api.gui.widgets.tab.HorizontalTabListRenderer.VerticalLocation;
import gregtech.api.gui.widgets.tab.ITabInfo;
import gregtech.api.gui.widgets.tab.TabListRenderer;
import gregtech.api.gui.widgets.tab.VerticalTabListRenderer;
import gregtech.api.gui.widgets.tab.VerticalTabListRenderer.HorizontalLocation;
import gregtech.api.gui.widgets.tab.VerticalTabListRenderer.VerticalStartCorner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.Tuple;

import java.util.*;
import java.util.function.Supplier;

public class TabGroup extends AbstractWidgetGroup {

    private List<ITabInfo> tabInfos = new ArrayList<>();
    private Map<Integer, AbstractWidgetGroup> tabWidgets = new HashMap<>();
    private int selectedTabIndex = 0;
    private TabListRenderer tabListRenderer;

    public TabGroup(TabLocation tabLocation) {
        this.tabListRenderer = tabLocation.supplier.get();
    }

    public void addTab(ITabInfo tabInfo, AbstractWidgetGroup tabWidget) {
        this.tabInfos.add(tabInfo);
        int tabIndex = tabInfos.size() - 1;
        this.tabWidgets.put(tabIndex, tabWidget);
        tabWidget.setVisible(tabIndex == selectedTabIndex);
        addWidget(tabWidget);
    }

    @Override
    public void drawInBackground(int mouseX, int mouseY) {
        super.drawInBackground(mouseX, mouseY);
        this.tabListRenderer.renderTabs(tabInfos, sizes.getWidth(), sizes.getHeight(), selectedTabIndex);
    }

    @Override
    public void drawInForeground(int mouseX, int mouseY) {
        super.drawInForeground(mouseX, mouseY);
        Tuple<ITabInfo, int[]> tabOnMouse = getTabOnMouse(mouseX, mouseY);
        if(tabOnMouse != null) {
            int[] tabSizes = tabOnMouse.getSecond();
            ITabInfo tabInfo = tabOnMouse.getFirst();
            boolean isSelected = tabInfos.get(selectedTabIndex) == tabInfo;
            tabInfo.renderHoverText(tabSizes[0], tabSizes[1], tabSizes[2], tabSizes[3], sizes.getWidth(), sizes.getHeight(), isSelected, mouseX, mouseY);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        Tuple<ITabInfo, int[]> tabOnMouse = getTabOnMouse(mouseX, mouseY);
        if(tabOnMouse != null) {
            ITabInfo tabInfo = tabOnMouse.getFirst();
            int tabIndex = tabInfos.indexOf(tabInfo);
            if(selectedTabIndex != tabIndex) {
                this.tabWidgets.get(selectedTabIndex).setVisible(false);
                this.tabWidgets.get(tabIndex).setVisible(true);
                this.selectedTabIndex = tabIndex;
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            }
        }
    }

    private Tuple<ITabInfo, int[]> getTabOnMouse(int mouseX, int mouseY) {
        for(int tabIndex = 0; tabIndex < tabInfos.size(); tabIndex++) {
            ITabInfo tabInfo = tabInfos.get(tabIndex);
            int[] tabSizes = tabListRenderer.getTabPos(tabIndex, sizes.getWidth(), sizes.getHeight());
            if(isMouseOverTab(mouseX, mouseY, tabSizes)) {
                return new Tuple<>(tabInfo, tabSizes);
            }
        }
        return null;
    }

    private static boolean isMouseOverTab(int mouseX, int mouseY, int[] tabSizes) {
        int minX = tabSizes[0];
        int minY = tabSizes[1];
        int maxX = tabSizes[0] + tabSizes[2];
        int maxY = tabSizes[1] + tabSizes[3];
        return mouseX >= minX && mouseY >= minY && mouseX < maxX && mouseY < maxY;
    }

    @Override
    public boolean isWidgetVisible(Widget widget) {
        return tabWidgets.containsKey(selectedTabIndex) && tabWidgets.get(selectedTabIndex) == widget;
    }

    public enum TabLocation {

        HORIZONTAL_TOP_LEFT(() -> new HorizontalTabListRenderer(HorizontalStartCorner.LEFT, VerticalLocation.TOP)),
        HORIZONTAL_TOP_RIGHT(() -> new HorizontalTabListRenderer(HorizontalStartCorner.RIGHT, VerticalLocation.TOP)),
        HORIZONTAL_BOTTOM_LEFT(() -> new HorizontalTabListRenderer(HorizontalStartCorner.LEFT, VerticalLocation.BOTTOM)),
        HORIZONTAL_BOTTOM_RIGHT(() -> new HorizontalTabListRenderer(HorizontalStartCorner.RIGHT, VerticalLocation.BOTTOM)),
        VERTICAL_LEFT_TOP(() -> new VerticalTabListRenderer(VerticalStartCorner.TOP, HorizontalLocation.LEFT)),
        VERTICAL_LEFT_BOTTOM(() -> new VerticalTabListRenderer(VerticalStartCorner.BOTTOM, HorizontalLocation.LEFT)),
        VERTICAL_RIGHT_TOP(() -> new VerticalTabListRenderer(VerticalStartCorner.TOP, HorizontalLocation.RIGHT)),
        VERTICAL_RIGHT_BOTTOM(() -> new VerticalTabListRenderer(VerticalStartCorner.BOTTOM, HorizontalLocation.RIGHT));

        private final Supplier<TabListRenderer> supplier;

        TabLocation(Supplier<TabListRenderer> supplier) {
            this.supplier = supplier;
        }
    }

}
