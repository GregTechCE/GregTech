package gregtech.api.enums.material.builder;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.material.Material;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.objects.MaterialStack;

import java.util.ArrayList;

public abstract class MaterialBuilder<T extends Material, Self extends MaterialBuilder<T, Self>> {

    protected final String name;
    protected final String defaultLocalizedName;
    protected MaterialIconSet iconSet = MaterialIconSet.NONE;
    protected int rgbColor = 0xFFFFFF;
    protected ArrayList<SubTag> subTags = new ArrayList<>();
    protected int materialGenerationBits = 0;
    protected float densityMultiplier = 1.0f;
    protected Element directElement = null;
    protected ArrayList<MaterialStack> materialCompounds = new ArrayList<>();
    protected int metaItemSubId = -1;

    public MaterialBuilder(String name, String defaultLocalizedName) {
        this.name = name;
        this.defaultLocalizedName = defaultLocalizedName;
    }

    public Self metaItemSubId(int metaItemSubId) {
        this.metaItemSubId = metaItemSubId;
        return (Self) this;
    }

    public Self enableDecompositionByCentrifuging() {
        this.materialGenerationBits |= Material.MatFlags.GENERATE_DECOMPOSITION_RECIPES;
        this.materialGenerationBits |= Material.MatFlags.DECOMPOSITION_BY_CENTRIFUGING;
        return (Self) this;
    }

    public Self enableDecompositionByElectrolyzing() {
        this.materialGenerationBits |= Material.MatFlags.GENERATE_DECOMPOSITION_RECIPES;
        this.materialGenerationBits |= Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING;
        return (Self) this;
    }

    public Self markMagical() {
        this.subTags.add(Material.MatFlags.MAGICAL);
        return (Self) this;
    }

    public Self markExplosive() {
        this.subTags.add(Material.MatFlags.EXPLOSIVE);
        return (Self) this;
    }

    public Self markUnburnable() {
        Preconditions.checkState(!subTags.contains(Material.MatFlags.FLAMMABLE), "Cannot mark flammable material as unburnable!");
        this.subTags.add(Material.MatFlags.UNBURNABLE);
        return (Self) this;
    }

    public Self markFlammable() {
        Preconditions.checkState(!subTags.contains(Material.MatFlags.UNBURNABLE), "Cannot mark unburnable material as flammable!");
        this.subTags.add(Material.MatFlags.FLAMMABLE);
        return (Self) this;
    }

    public Self markBurning() {
        Preconditions.checkState(!subTags.contains(Material.MatFlags.UNBURNABLE), "Cannot mark unburnable material as burning!");
        this.subTags.add(Material.MatFlags.FLAMMABLE);
        this.subTags.add(Material.MatFlags.BURNING);
        return (Self) this;
    }

    public Self disableUnification() {
        this.subTags.add(Material.MatFlags.NO_UNIFICATION);
        return (Self) this;
    }

    public Self disableRecycling() {
        this.subTags.add(Material.MatFlags.NO_RECYCLING);
        return (Self) this;
    }

    public Self color(int color) {
        this.rgbColor = color;
        return (Self) this;
    }

    public Self densityMultiplier(float densityMultiplier) {
        this.densityMultiplier = densityMultiplier;
        return (Self) this;
    }

    public Self directElement(Element element) {
        Preconditions.checkNotNull(element);
        Preconditions.checkState(materialCompounds.isEmpty(), "Cannot specify direct element for compound material!");
        this.directElement = element;
        return (Self) this;
    }

    public Self compounds(MaterialStack... compounds) {
        Preconditions.checkNotNull(compounds);
        Preconditions.checkArgument(compounds.length > 0, "Cannot add empty compounds array!");
        Preconditions.checkState(directElement == null, "Cannot specify compounds for direct element material!");
        this.materialCompounds = Lists.newArrayList(compounds);
        return (Self) this;
    }

    protected abstract T build();

    public final T buildAndRegister() {
        T material = build();
        if(metaItemSubId > 0) {
            Material.MATERIAL_REGISTRY.register(metaItemSubId, name, material);
        } else Material.MATERIAL_REGISTRY.putObject(name, material);
    }

}
