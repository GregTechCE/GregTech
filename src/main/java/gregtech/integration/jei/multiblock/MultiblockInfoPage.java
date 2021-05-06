package gregtech.integration.jei.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiblockInfoPage {

    public abstract MultiblockControllerBase getController();

    public abstract List<MultiblockShapeInfo> getMatchingShapes();

    public abstract String[] getDescription();

    public float getDefaultZoom() {
        return 1.0f;
    }

    public List<String> informationText() {
        String tiltText = I18n.format("gregtech.multiblock.preview.tilt");
        String zoomText = I18n.format("gregtech.multiblock.preview.zoom");
        String panText = I18n.format("gregtech.multiblock.preview.pan");

        List<String> informationText = new ArrayList<>();
        informationText.add(tiltText);
        informationText.add(panText);
        informationText.add(zoomText);

        return informationText;
    }
}
