package gregtech.integration.jei.multiblock;

import com.google.common.collect.Maps;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class MultiblockInfoPage {

    private Map<MultiblockAbility, List<Tuple<String, TextFormatting>>> abilityTooltips;

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

    /**
     * Gets the Map containing all tooltips for MultiblockAbilities. Will generate the map if it does not exist
     * @return - The Map containing Tooltips and Formatting for specific MultiblockAbilities
     */
    public Map<MultiblockAbility, List<Tuple<String, TextFormatting>>> getAbilityTooltipMap() {
        // Generate on first use
        if (this.abilityTooltips == null) {
            this.abilityTooltips = Maps.newHashMap();
            generateAbilityTooltips();
        }
        return this.abilityTooltips;
    }

    /**
     * Contains the default tooltips that will be applied to all MultiblockAbilities
     */
    protected void generateAbilityTooltips() {
        addAbilityTooltip(MultiblockAbility.EXPORT_ITEMS, new Tuple<>("gregtech.multiblock.preview.any_hatch", TextFormatting.GREEN));
        addAbilityTooltip(MultiblockAbility.IMPORT_ITEMS, new Tuple<>("gregtech.multiblock.preview.any_hatch", TextFormatting.GREEN));
        addAbilityTooltip(MultiblockAbility.EXPORT_FLUIDS, new Tuple<>("gregtech.multiblock.preview.any_hatch", TextFormatting.GREEN));
        addAbilityTooltip(MultiblockAbility.IMPORT_FLUIDS, new Tuple<>("gregtech.multiblock.preview.any_hatch", TextFormatting.GREEN));
    }

    /**
     * A Helper method for adding tooltips to MultiblockAbilities. Can be called if {@link MultiblockInfoPage#generateAbilityTooltips()} is not overridden
     * Will add tooltips to MultiblockAbilities with existing tooltips
     * @param ability - The MultiblockAbility to add a Tooltip too
     * @param tooltip - The tooltip String and TextFormatting to be added
     */
    protected void addAbilityTooltip(MultiblockAbility ability, Tuple<String, TextFormatting> tooltip) {

        List<Tuple<String, TextFormatting>> tooltipList = this.abilityTooltips.getOrDefault(ability, null);

        if(tooltipList == null) {
            List<Tuple<String, TextFormatting>> tooltipToAdd = new ArrayList<>();
            tooltipToAdd.add(tooltip);
            this.abilityTooltips.put(ability, tooltipToAdd);
        }
        else {
            tooltipList.add(tooltip);
            this.abilityTooltips.put(ability, tooltipList);

        }
    }
}
