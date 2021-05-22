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

        return new ArrayList<String>() {{
            add(I18n.format("gregtech.multiblock.preview.tilt"));
            add(I18n.format("gregtech.multiblock.preview.zoom"));
            add(I18n.format("gregtech.multiblock.preview.pan"));
            add(I18n.format("gregtech.multiblock.preview.move"));
            add(I18n.format("gregtech.multiblock.preview.reset"));
        }};
    }
}
