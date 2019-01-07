package gregtech.api.gui;

public interface SizeProvider {

    int getScreenWidth();
    int getScreenHeight();

    int getWidth();
    int getHeight();

    default int getGuiLeft() {
        return (getScreenWidth() - getWidth()) / 2;
    }

    default int getGuiTop() {
        return (getScreenHeight() - getHeight()) / 2;
    }
}
