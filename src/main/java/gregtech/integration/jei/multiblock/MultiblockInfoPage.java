package gregtech.integration.jei.multiblock;

import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import net.minecraft.client.resources.I18n;

import java.util.*;

public abstract class MultiblockInfoPage {

    public abstract MultiblockControllerBase getController();

    public abstract List<MultiblockShapeInfo> getMatchingShapes();

    public abstract String[] getDescription();

    public float getDefaultZoom() {
        return 1.0f;
    }

    public List<String> informationText() {

        return Stream.of("gregtech.multiblock.preview.tilt", "gregtech.multiblock.preview.zoom",
                "gregtech.multiblock.preview.pan", "gregtech.multiblock.preview.move", "gregtech.multiblock.preview.reset")
                .map(I18n::format)
                .collect(Collectors.toList());
    }

    public Map<MultiblockAbility, List<String>> getAbilityTooltipMap() {
        Map<MultiblockAbility, List<String>> abilityTooltipMap = new HashMap<>();
        abilityTooltipMap.put(MultiblockAbility.EXPORT_ITEMS, Collections.singletonList("gregtech.multiblock.preview.any_hatch"));
        abilityTooltipMap.put(MultiblockAbility.IMPORT_ITEMS, Collections.singletonList("gregtech.multiblock.preview.any_hatch"));
        abilityTooltipMap.put(MultiblockAbility.EXPORT_FLUIDS, Collections.singletonList("gregtech.multiblock.preview.any_hatch"));
        abilityTooltipMap.put(MultiblockAbility.IMPORT_FLUIDS, Collections.singletonList("gregtech.multiblock.preview.any_hatch"));

        return abilityTooltipMap;
    }
}
