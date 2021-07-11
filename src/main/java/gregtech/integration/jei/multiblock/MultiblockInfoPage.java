package gregtech.integration.jei.multiblock;

import gregtech.api.GTValues;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.util.ItemStackHashStrategy;
import gregtech.common.metatileentities.MetaTileEntities;
import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class MultiblockInfoPage {

    private final Hash.Strategy<ItemStack> strategy = ItemStackHashStrategy.comparingAllButCount();

    private Map<ItemStack, List<Tuple<String, TextFormatting>>> abilityTooltips = new Object2ObjectOpenCustomHashMap<>(strategy);

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
    public Map<ItemStack, List<Tuple<String, TextFormatting>>> getAbilityTooltipMap() {
        // Generate on first use
        if (this.abilityTooltips.size() == 0) {
            generateAbilityTooltips();
        }
        return this.abilityTooltips;
    }

    /**
     * Contains the default tooltips that will be applied to all MultiblockAbilities
     */
    protected void generateAbilityTooltips() {

        for(int i  = 0; i < GTValues.V.length; i++) {
            addAbilityTooltip(MetaTileEntities.ITEM_EXPORT_BUS[i].getStackForm(), new Tuple<>("gregtech.multiblock.preview.any_hatch", TextFormatting.GREEN));
            addAbilityTooltip(MetaTileEntities.ITEM_IMPORT_BUS[i].getStackForm(), new Tuple<>("gregtech.multiblock.preview.any_hatch", TextFormatting.GREEN));
            addAbilityTooltip(MetaTileEntities.FLUID_EXPORT_HATCH[i].getStackForm(), new Tuple<>("gregtech.multiblock.preview.any_hatch", TextFormatting.GREEN));
            addAbilityTooltip(MetaTileEntities.FLUID_IMPORT_HATCH[i].getStackForm(), new Tuple<>("gregtech.multiblock.preview.any_hatch", TextFormatting.GREEN));
        }
    }

    /**
     * A Helper method for adding tooltips to MultiblockAbilities. Can be called if {@link MultiblockInfoPage#generateAbilityTooltips()} is not overridden
     * Will add tooltips to MultiblockAbilities with existing tooltips
     * @param itemStack - The MultiblockAbility to add a Tooltip too
     * @param tooltip - The tooltip String and TextFormatting to be added
     */
    protected void addAbilityTooltip(ItemStack itemStack, Tuple<String, TextFormatting> tooltip) {

        List<Tuple<String, TextFormatting>> tooltipList = this.abilityTooltips.getOrDefault(itemStack, null);

        if(tooltipList == null) {
            List<Tuple<String, TextFormatting>> tooltipToAdd = new ArrayList<>();
            tooltipToAdd.add(tooltip);
            this.abilityTooltips.put(itemStack, tooltipToAdd);
        }
        else {
            tooltipList.add(tooltip);
            this.abilityTooltips.put(itemStack, tooltipList);

        }
    }
}
