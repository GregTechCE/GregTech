package gregtech.api.enums.material.builder;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import gregtech.api.enums.Element;
import gregtech.api.enums.SubTag;
import gregtech.api.enums.material.Material;
import gregtech.api.enums.material.MaterialIconSet;
import gregtech.api.enums.material.types.MarkerMaterial;
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
    protected ArrayList<Material> oreReRegistrations = new ArrayList<>();
    protected int metaItemSubId = -1;

    public MaterialBuilder(String name, String defaultLocalizedName) {
        this.name = name;
        this.defaultLocalizedName = defaultLocalizedName;
    }

    /**
     * Assigns a meta item sub id to material
     * Makes meta-items generate for this material if desired
     * @param metaItemSubId meta item sub id
     * @return this
     */
    public Self metaItemSubId(int metaItemSubId) {
        this.metaItemSubId = metaItemSubId;
        return (Self) this;
    }

    /**
     * Assigns material to a group of marker material
     * @param markerMaterial marker
     * @return this
     */
    public Self reRegisterInto(MarkerMaterial markerMaterial) {
        this.oreReRegistrations.add(markerMaterial);
        return (Self) this;
    }

    /**
     * Enables generation of centrifuge recipes like:
     * X This Material Dust + N Cells = material components dusts/in cells
     * @return this
     */
    public Self enableDecompositionByCentrifuging() {
        this.materialGenerationBits |= Material.MatFlags.GENERATE_DECOMPOSITION_RECIPES;
        this.materialGenerationBits |= Material.MatFlags.DECOMPOSITION_BY_CENTRIFUGING;
        return (Self) this;
    }

    /**
     * Enables generation of electrolyzer recipes like:
     * X This Material Dust + N Cells = material components dusts/in cells
     * @return this
     */
    public Self enableDecompositionByElectrolyzing() {
        this.materialGenerationBits |= Material.MatFlags.GENERATE_DECOMPOSITION_RECIPES;
        this.materialGenerationBits |= Material.MatFlags.DECOMPOSITION_BY_ELECTROLYZING;
        return (Self) this;
    }

    /**
     * Marks this material as magical.
     * Doesn't have any actual effect on material
     * @return this
     */
    public Self markMagical() {
        this.subTags.add(Material.MatFlags.MAGICAL);
        return (Self) this;
    }

    /**
     * Marks material as explosive.
     * Used in some recipes generation
     * @return this
     */
    public Self markExplosive() {
        this.subTags.add(Material.MatFlags.EXPLOSIVE);
        return (Self) this;
    }

    /**
     * Marks material as totally unburnable (cannot catch fire at all)
     * Used in some recipes generation
     * @return this
     */
    public Self markUnburnable() {
        Preconditions.checkState(!subTags.contains(Material.MatFlags.FLAMMABLE), "Cannot mark flammable material as unburnable!");
        this.subTags.add(Material.MatFlags.UNBURNABLE);
        return (Self) this;
    }

    /**
     * Marks material as flammable (can catch fire from source)
     * Used in some recipes generation
     * @return this
     */
    public Self markFlammable() {
        Preconditions.checkState(!subTags.contains(Material.MatFlags.UNBURNABLE), "Cannot mark unburnable material as flammable!");
        this.subTags.add(Material.MatFlags.FLAMMABLE);
        return (Self) this;
    }

    /**
     * Marks material as burning itself (can ignite things itself)
     * Used in some recipes generation
     * @return this
     */
    public Self markBurning() {
        Preconditions.checkState(!subTags.contains(Material.MatFlags.UNBURNABLE), "Cannot mark unburnable material as burning!");
        this.subTags.add(Material.MatFlags.FLAMMABLE);
        this.subTags.add(Material.MatFlags.BURNING);
        return (Self) this;
    }

    /**
     * Disables unification for this material
     * @return this
     */
    public Self disableUnification() {
        this.subTags.add(Material.MatFlags.NO_UNIFICATION);
        return (Self) this;
    }

    /**
     * Disables recycling for this material
     * @return this
     */
    public Self disableRecycling() {
        this.subTags.add(Material.MatFlags.NO_RECYCLING);
        return (Self) this;
    }

    /**
     * Sets a color of a material
     * @param color RGB color (0xRRGGBB)
     * @return this
     */
    public Self color(int color) {
        this.rgbColor = color;
        return (Self) this;
    }

    /**
     * Sets a density multiplier for material
     * Affects cost of replicating material trough Replicator (and total material's mass)
     * @param densityMultiplier density multiplier
     * @return this
     */
    public Self densityMultiplier(float densityMultiplier) {
        this.densityMultiplier = densityMultiplier;
        return (Self) this;
    }

    /**
     * Specifies direct element from which this material consist
     * Not compatible with compounds(...) under this method
     * @param element direct element
     * @return this
     */
    public Self directElement(Element element) {
        Preconditions.checkNotNull(element);
        Preconditions.checkState(materialCompounds.isEmpty(), "Cannot specify direct element for compound material!");
        this.directElement = element;
        return (Self) this;
    }

    /**
     * Specifies sub-materials (components) of this material
     * Not compatible with directElement(Element) above
     * @param compounds material components
     * @return this
     */
    public Self compounds(MaterialStack... compounds) {
        Preconditions.checkNotNull(compounds);
        Preconditions.checkArgument(compounds.length > 0, "Cannot add empty compounds array!");
        Preconditions.checkState(directElement == null, "Cannot specify compounds for direct element material!");
        this.materialCompounds = Lists.newArrayList(compounds);
        return (Self) this;
    }

    /**
     * Builds actual material instance
     * Registration is done by buildAndRegister(), only build, not register.
     * @return built material
     */
    protected abstract T build();

    /**
     * Builds and registers material
     * @return material
     */
    public final T buildAndRegister() {
        T material = build();
        if(metaItemSubId > 0) {
            Material.MATERIAL_REGISTRY.register(metaItemSubId, name, material);
        } else Material.MATERIAL_REGISTRY.putObject(name, material);
        return material;
    }

}
