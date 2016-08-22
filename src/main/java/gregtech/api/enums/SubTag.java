package gregtech.api.enums;

import gregtech.api.interfaces.ICondition;
import gregtech.api.interfaces.ISubTagContainer;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Just a simple Class to be able to add special Tags for Materials.
 * <p/>
 * The Tags should be added in preload and before I do my own preload to the Materials.
 * In order to make yourself a new SubTag, just create one new instance of SubTag using getNewSubTag
 * and use that one instance on all Materials you want to add those Tags to.
 * <p/>
 * You should look at this File whenever you update, maybe there are some new Tags you could use.
 * <p/>
 * -------------------------------------------------------------------------------------------------
 * <p/>
 * Some SubTags are used for other things than Materials too. It is useful when I need an easy way to declare Stuff in Items.
 */
public final class SubTag implements ICondition<ISubTagContainer> {
    public static final HashMap<String, SubTag> sSubTags = new HashMap<String, SubTag>();
    private static long sSubtagID = 0;
    public final long mSubtagID;
    public final String mName;
    /**
     * Add this to your Material if you want to have its Ore Calcite heated in a Blast Furnace for more output. Already listed are:
     * Iron, Pyrite, PigIron, DeepIron, ShadowIron, WroughtIron and MeteoricIron.
     */
    public static final SubTag BLASTFURNACE_CALCITE_DOUBLE = getNewSubTag("BLASTFURNACE_CALCITE_DOUBLE"), BLASTFURNACE_CALCITE_TRIPLE = getNewSubTag("BLASTFURNACE_CALCITE_TRIPLE");
    /**
     * Materials which are outputting less in an Induction Smelter. Already listed are:
     * Pyrite, Tetrahedrite, Sphalerite, Cinnabar
     */
    public static final SubTag INDUCTIONSMELTING_LOW_OUTPUT = getNewSubTag("INDUCTIONSMELTING_LOW_OUTPUT");
    /**
     * Add this to your Material if you want to have its Ore Sodium Persulfate washed. Already listed are:
     * Zinc, Nickel, Copper, Cobalt, Cobaltite and Tetrahedrite.
     */
    public static final SubTag WASHING_SODIUMPERSULFATE = getNewSubTag("WASHING_SODIUMPERSULFATE");
    /**
     * Add this to your Material if you want to have its Ore Mercury washed. Already listed are:
     * Gold, Silver, Osmium, Mithril, Platinum, Midasium, Cooperite and AstralSilver.
     */
    public static final SubTag WASHING_MERCURY = getNewSubTag("WASHING_MERCURY");
    /**
     * Add this to your Material if you want to have its Ore electromagnetically separated to give Gold.
     */
    public static final SubTag ELECTROMAGNETIC_SEPERATION_GOLD = getNewSubTag("ELECTROMAGNETIC_SEPERATION_GOLD");
    /**
     * Add this to your Material if you want to have its Ore electromagnetically separated to give Iron.
     */
    public static final SubTag ELECTROMAGNETIC_SEPERATION_IRON = getNewSubTag("ELECTROMAGNETIC_SEPERATION_IRON");
    /**
     * Add this to your Material if you want to have its Ore electromagnetically separated to give Neodymium.
     */
    public static final SubTag ELECTROMAGNETIC_SEPERATION_NEODYMIUM = getNewSubTag("ELECTROMAGNETIC_SEPERATION_NEODYMIUM");
    /**
     * Add this to your Material if you want to have its Ore giving Cinnabar Crystals on Pulverization. Already listed are:
     * Redstone
     */
    public static final SubTag PULVERIZING_CINNABAR = getNewSubTag("PULVERIZING_CINNABAR");
    /**
     * This Material cannot be worked by any other means, than smashing or smelting. This is used for coated Materials.
     */
    public static final SubTag NO_WORKING = getNewSubTag("NO_WORKING");
    /**
     * This Material cannot be used for regular Metal working techniques since it is not possible to bend it. Already listed are:
     * Rubber, Plastic, Paper, Wood, Stone
     */
    public static final SubTag NO_SMASHING = getNewSubTag("NO_SMASHING");
    /**
     * This Material cannot be unificated
     */
    public static final SubTag NO_UNIFICATION = getNewSubTag("NO_UNIFICATION");
    /**
     * This Material cannot be used in any Recycler. Already listed are:
     * Stone, Glass, Water
     */
    public static final SubTag NO_RECYCLING = getNewSubTag("NO_RECYCLING");
    /**
     * This Material cannot be used in any Furnace alike Structure. Already listed are:
     * Paper, Wood, Gunpowder, Stone
     */
    public static final SubTag NO_SMELTING = getNewSubTag("NO_SMELTING");
    /**
     * This Material can be molten into a Fluid
     */
    public static final SubTag SMELTING_TO_FLUID = getNewSubTag("SMELTING_TO_FLUID");
    /**
     * This Ore should be molten directly into a Gem of this Material, if the Ingot is missing. Already listed are:
     * Cinnabar
     */
    public static final SubTag SMELTING_TO_GEM = getNewSubTag("SMELTING_TO_GEM");
    /**
     * If this Material is some kind of Wood
     */
    public static final SubTag WOOD = getNewSubTag("WOOD");
    /**
     * If this Material is some kind of Food (or edible at all)
     */
    public static final SubTag FOOD = getNewSubTag("FOOD");
    /**
     * If this Material is some kind of Stone
     */
    public static final SubTag STONE = getNewSubTag("STONE");
    /**
     * If this Material is some kind of Pearl
     */
    public static final SubTag PEARL = getNewSubTag("PEARL");
    /**
     * If this Material is some kind of Quartz
     */
    public static final SubTag QUARTZ = getNewSubTag("QUARTZ");
    /**
     * If this Material is Crystallisable
     */
    public static final SubTag CRYSTALLISABLE = getNewSubTag("CRYSTALLISABLE");
    /**
     * If this Material is some kind of Crystal
     */
    public static final SubTag CRYSTAL = getNewSubTag("CRYSTAL");
    /**
     * If this Material is some kind of Magical
     */
    public static final SubTag MAGICAL = getNewSubTag("MAGICAL");
    /**
     * If this Material is some kind of Metal
     */
    public static final SubTag METAL = getNewSubTag("METAL");
    /**
     * If this Material is some kind of Paper
     */
    public static final SubTag PAPER = getNewSubTag("PAPER");
    /**
     * If this Material is having a constantly burning Aura
     */
    public static final SubTag BURNING = getNewSubTag("BURNING");
    /**
     * If this Material is some kind of flammable
     */
    public static final SubTag FLAMMABLE = getNewSubTag("FLAMMABLE");
    /**
     * If this Material is not burnable at all
     */
    public static final SubTag UNBURNABLE = getNewSubTag("UNBURNABLE");
    /**
     * If this Material is some kind of explosive
     */
    public static final SubTag EXPLOSIVE = getNewSubTag("EXPLOSIVE");
    /**
     * If this Material is bouncy
     */
    public static final SubTag BOUNCY = getNewSubTag("BOUNCY");
    /**
     * If this Material is invisible
     */
    public static final SubTag INVISIBLE = getNewSubTag("INVISIBLE");
    /**
     * If this Material is transparent
     */
    public static final SubTag TRANSPARENT = getNewSubTag("TRANSPARENT");
    /**
     * If this Material has a Color
     */
    public static final SubTag HAS_COLOR = getNewSubTag("HAS_COLOR");
    /**
     * If this Material is stretchable
     */
    public static final SubTag STRETCHY = getNewSubTag("STRETCHY");
    /**
     * If this Material is grindable with a simple Mortar
     */
    public static final SubTag MORTAR_GRINDABLE = getNewSubTag("MORTAR_GRINDABLE");
    /**
     * If this Material is usable for Soldering
     */
    public static final SubTag SOLDERING_MATERIAL = getNewSubTag("SOLDERING_MATERIAL");
    /**
     * If this Material is has extra Costs for Soldering, requires the Tag "SOLDERING_MATERIAL" too
     */
    public static final SubTag SOLDERING_MATERIAL_BAD = getNewSubTag("SOLDERING_MATERIAL_BAD");
    /**
     * If this Material is has a discount for Soldering, requires the Tag "SOLDERING_MATERIAL" too
     */
    public static final SubTag SOLDERING_MATERIAL_GOOD = getNewSubTag("SOLDERING_MATERIAL_GOOD");
    /**
     * Energy Tag for Electricity
     * Primary = Voltage
     * Secondary = Amperage
     */
    public static final SubTag ENERGY_ELECTRICITY = getNewSubTag("ENERGY_ELECTRICITY");
    /**
     * Energy Tag for Rotating Power
     * Primary = Speed
     * Secondary = Power
     */
    public static final SubTag ENERGY_ROTATIONAL = getNewSubTag("ENERGY_ROTATIONAL");
    /**
     * Energy Tag for Steam Power
     * Primary = Steam per Tick
     * Secondary = unused (always 1)
     */
    public static final SubTag ENERGY_STEAM = getNewSubTag("ENERGY_STEAM");
    /**
     * Energy Tag for Air Pressure Power
     * Primary = Pressure
     * Secondary = unused (always 1)
     */
    public static final SubTag ENERGY_AIR = getNewSubTag("ENERGY_AIR");
    /**
     * Energy Tag for Heat
     * Primary = Temperature
     * Secondary = unused (always 1)
     */
    public static final SubTag ENERGY_HEAT = getNewSubTag("ENERGY_HEAT");
    /**
     * Energy Tag for RedstoneFlux
     * Primary = unused (always 1)
     * Secondary = RF
     */
    public static final SubTag ENERGY_REDSTONE_FLUX = getNewSubTag("ENERGY_REDSTONE_FLUX");
    /**
     * Projectile Tag for Arrows
     */
    public static final SubTag PROJECTILE_ARROW = getNewSubTag("PROJECTILE_ARROW");
    public final Collection<ISubTagContainer> mRelevantTaggedItems = new HashSet<ISubTagContainer>(1);

    private SubTag(String aName) {
        mSubtagID = sSubtagID++;
        mName = aName;
        sSubTags.put(aName, this);
    }

    public static SubTag getNewSubTag(String aName) {
        for (SubTag tSubTag : sSubTags.values()) if (tSubTag.mName.equals(aName)) return tSubTag;
        return new SubTag(aName);
    }

    @Override
    public String toString() {
        return mName;
    }

    public SubTag addContainerToList(ISubTagContainer... aContainers) {
        if (aContainers != null) for (ISubTagContainer aContainer : aContainers)
            if (aContainer != null && !mRelevantTaggedItems.contains(aContainer)) mRelevantTaggedItems.add(aContainer);
        return this;
    }

    public SubTag addTo(ISubTagContainer... aContainers) {
        if (aContainers != null)
            for (ISubTagContainer aContainer : aContainers) if (aContainer != null) aContainer.add(this);
        return this;
    }

    @Override
    public boolean isTrue(ISubTagContainer aObject) {
        return aObject.contains(this);
    }
}