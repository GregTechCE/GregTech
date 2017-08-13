package gregtech.api.util;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.ItemList;

/**
 * Experimental Class for later
 */
public class Tier {
    public static final Tier[]
            ELECTRIC = new Tier[]{
            new Tier(SubTag.ENERGY_ELECTRICITY, 0, 8, 1, 1, 1, Materials.WroughtIron, ItemList.Hull_ULV, OrePrefix.cableGt01.get(Materials.Lead), OrePrefix.cableGt04.get(Materials.Lead), OrePrefix.circuit.get(Materials.Primitive), OrePrefix.circuit.get(Materials.Basic)),
            new Tier(SubTag.ENERGY_ELECTRICITY, 1, 32, 1, 1, 1, Materials.Steel, ItemList.Hull_LV, OrePrefix.cableGt01.get(Materials.Tin), OrePrefix.cableGt04.get(Materials.Tin), OrePrefix.circuit.get(Materials.Basic), OrePrefix.circuit.get(Materials.Good)),
            new Tier(SubTag.ENERGY_ELECTRICITY, 2, 128, 1, 1, 1, Materials.Aluminium, ItemList.Hull_MV, OrePrefix.cableGt01.get(Materials.AnyCopper), OrePrefix.cableGt04.get(Materials.AnyCopper), OrePrefix.circuit.get(Materials.Good), OrePrefix.circuit.get(Materials.Advanced)),
            new Tier(SubTag.ENERGY_ELECTRICITY, 3, 512, 1, 1, 1, Materials.StainlessSteel, ItemList.Hull_HV, OrePrefix.cableGt01.get(Materials.Gold), OrePrefix.cableGt04.get(Materials.Gold), OrePrefix.circuit.get(Materials.Advanced), OrePrefix.circuit.get(Materials.Elite)),
            new Tier(SubTag.ENERGY_ELECTRICITY, 4, 2048, 1, 1, 1, Materials.Titanium, ItemList.Hull_EV, OrePrefix.cableGt01.get(Materials.Aluminium), OrePrefix.cableGt04.get(Materials.Aluminium), OrePrefix.circuit.get(Materials.Elite), OrePrefix.circuit.get(Materials.Master)),
            new Tier(SubTag.ENERGY_ELECTRICITY, 5, 8192, 1, 1, 1, Materials.TungstenSteel, ItemList.Hull_IV, OrePrefix.cableGt01.get(Materials.Platinum), OrePrefix.cableGt04.get(Materials.Platinum), OrePrefix.circuit.get(Materials.Master), OrePrefix.circuit.get(Materials.Ultimate)),
            new Tier(SubTag.ENERGY_ELECTRICITY, 6, 32768, 1, 1, 1, Materials.Chrome, ItemList.Hull_LuV, OrePrefix.cableGt01.get(Materials.NiobiumTitanium), OrePrefix.cableGt04.get(Materials.NiobiumTitanium), OrePrefix.circuit.get(Materials.Ultimate), OrePrefix.circuit.get(Materials.Ultimate)),
            new Tier(SubTag.ENERGY_ELECTRICITY, 7, 131072, 1, 1, 1, Materials.Iridium, ItemList.Hull_ZPM, OrePrefix.cableGt01.get(Materials.Naquadah), OrePrefix.cableGt04.get(Materials.Naquadah), OrePrefix.circuit.get(Materials.Ultimate), OrePrefix.circuit.get(Materials.Ultimate)),
            new Tier(SubTag.ENERGY_ELECTRICITY, 8, 524288, 1, 1, 1, Materials.Osmium, ItemList.Hull_UV, OrePrefix.wireGt04.get(Materials.NaquadahAlloy), OrePrefix.cableGt01.get(Materials.Superconductor), OrePrefix.circuit.get(Materials.Ultimate), OrePrefix.circuit.get(Materials.Ultimate)),
            new Tier(SubTag.ENERGY_ELECTRICITY, 9, Integer.MAX_VALUE, 1, 1, 1, Materials.Neutronium, ItemList.Hull_MAX, OrePrefix.wireGt01.get(Materials.Superconductor), OrePrefix.wireGt04.get(Materials.Superconductor), OrePrefix.circuit.get(Materials.Ultimate), OrePrefix.circuit.get(Materials.Ultimate)),
    }, ROTATIONAL = new Tier[]{
            new Tier(SubTag.ENERGY_ROTATIONAL, 1, 32, 1, 1, 1, Materials.Wood, OrePrefix.frameGt.get(Materials.Wood), OrePrefix.stick.get(Materials.Wood), OrePrefix.ingot.get(Materials.Wood), OrePrefix.gearGt.get(Materials.Wood), OrePrefix.gearGt.get(Materials.Stone)),
            new Tier(SubTag.ENERGY_ROTATIONAL, 1, 32, 1, 2, 2, Materials.WoodSealed, OrePrefix.frameGt.get(Materials.WoodSealed), OrePrefix.stick.get(Materials.WoodSealed), OrePrefix.ingot.get(Materials.WoodSealed), OrePrefix.gearGt.get(Materials.WoodSealed), OrePrefix.gearGt.get(Materials.Stone)),
            new Tier(SubTag.ENERGY_ROTATIONAL, 2, 128, 1, 1, 1, Materials.Stone, OrePrefix.frameGt.get(Materials.Stone), OrePrefix.stick.get(Materials.Stone), OrePrefix.ingot.get(Materials.Stone), OrePrefix.gearGt.get(Materials.Stone), OrePrefix.gearGt.get(Materials.Bronze)),
            new Tier(SubTag.ENERGY_ROTATIONAL, 2, 128, 1, 2, 2, Materials.IronWood, OrePrefix.frameGt.get(Materials.IronWood), OrePrefix.stick.get(Materials.IronWood), OrePrefix.ingot.get(Materials.IronWood), OrePrefix.gearGt.get(Materials.IronWood), OrePrefix.gearGt.get(Materials.Bronze)),
            new Tier(SubTag.ENERGY_ROTATIONAL, 3, 512, 1, 1, 1, Materials.Bronze, OrePrefix.frameGt.get(Materials.Bronze), OrePrefix.stick.get(Materials.Bronze), OrePrefix.ingot.get(Materials.Bronze), OrePrefix.gearGt.get(Materials.Bronze), OrePrefix.gearGt.get(Materials.Steel)),
            new Tier(SubTag.ENERGY_ROTATIONAL, 3, 512, 1, 2, 2, Materials.Brass, OrePrefix.frameGt.get(Materials.Brass), OrePrefix.stick.get(Materials.Brass), OrePrefix.ingot.get(Materials.Brass), OrePrefix.gearGt.get(Materials.Brass), OrePrefix.gearGt.get(Materials.Steel)),
            new Tier(SubTag.ENERGY_ROTATIONAL, 4, 2048, 1, 1, 1, Materials.Steel, OrePrefix.frameGt.get(Materials.Steel), OrePrefix.stick.get(Materials.Steel), OrePrefix.ingot.get(Materials.Steel), OrePrefix.gearGt.get(Materials.Steel), OrePrefix.gearGt.get(Materials.TungstenSteel)),
            new Tier(SubTag.ENERGY_ROTATIONAL, 4, 2048, 1, 2, 2, Materials.Titanium, OrePrefix.frameGt.get(Materials.Titanium), OrePrefix.stick.get(Materials.Titanium), OrePrefix.ingot.get(Materials.Titanium), OrePrefix.gearGt.get(Materials.Titanium), OrePrefix.gearGt.get(Materials.TungstenSteel)),
            new Tier(SubTag.ENERGY_ROTATIONAL, 5, 8192, 1, 1, 1, Materials.TungstenSteel, OrePrefix.frameGt.get(Materials.TungstenSteel), OrePrefix.stick.get(Materials.TungstenSteel), OrePrefix.ingot.get(Materials.TungstenSteel), OrePrefix.gearGt.get(Materials.TungstenSteel), OrePrefix.gearGt.get(Materials.Iridium)),
            new Tier(SubTag.ENERGY_ROTATIONAL, 6, 32768, 1, 1, 1, Materials.Iridium, OrePrefix.frameGt.get(Materials.Iridium), OrePrefix.stick.get(Materials.Iridium), OrePrefix.ingot.get(Materials.Iridium), OrePrefix.gearGt.get(Materials.Iridium), OrePrefix.gearGt.get(Materials.Neutronium)),
            new Tier(SubTag.ENERGY_ROTATIONAL, 9, Integer.MAX_VALUE, 1, 1, 1, Materials.Neutronium, OrePrefix.frameGt.get(Materials.Neutronium), OrePrefix.stick.get(Materials.Neutronium), OrePrefix.ingot.get(Materials.Neutronium), OrePrefix.gearGt.get(Materials.Neutronium), OrePrefix.gearGt.get(Materials.Neutronium)),
    }, STEAM = new Tier[]{
            new Tier(SubTag.ENERGY_STEAM, 1, 32, 1, 1, 1, Materials.Bronze, OrePrefix.frameGt.get(Materials.Bronze), OrePrefix.pipeMedium.get(Materials.Bronze), OrePrefix.pipeHuge.get(Materials.Bronze), OrePrefix.pipeMedium.get(Materials.Bronze), OrePrefix.pipeLarge.get(Materials.Bronze)),
            new Tier(SubTag.ENERGY_STEAM, 2, 128, 1, 1, 1, Materials.Steel, OrePrefix.frameGt.get(Materials.Steel), OrePrefix.pipeMedium.get(Materials.Steel), OrePrefix.pipeHuge.get(Materials.Steel), OrePrefix.pipeMedium.get(Materials.Steel), OrePrefix.pipeLarge.get(Materials.Steel)),
            new Tier(SubTag.ENERGY_STEAM, 3, 512, 1, 1, 1, Materials.Titanium, OrePrefix.frameGt.get(Materials.Titanium), OrePrefix.pipeMedium.get(Materials.Titanium), OrePrefix.pipeHuge.get(Materials.Titanium), OrePrefix.pipeMedium.get(Materials.Titanium), OrePrefix.pipeLarge.get(Materials.Titanium)),
            new Tier(SubTag.ENERGY_STEAM, 4, 2048, 1, 1, 1, Materials.TungstenSteel, OrePrefix.frameGt.get(Materials.TungstenSteel), OrePrefix.pipeMedium.get(Materials.TungstenSteel), OrePrefix.pipeHuge.get(Materials.TungstenSteel), OrePrefix.pipeMedium.get(Materials.TungstenSteel), OrePrefix.pipeLarge.get(Materials.TungstenSteel)),
            new Tier(SubTag.ENERGY_STEAM, 5, 8192, 1, 1, 1, Materials.Iridium, OrePrefix.frameGt.get(Materials.Iridium), OrePrefix.pipeMedium.get(Materials.Iridium), OrePrefix.pipeHuge.get(Materials.Iridium), OrePrefix.pipeMedium.get(Materials.Iridium), OrePrefix.pipeLarge.get(Materials.Iridium)),
            new Tier(SubTag.ENERGY_STEAM, 9, Integer.MAX_VALUE, 1, 1, 1, Materials.Neutronium, OrePrefix.frameGt.get(Materials.Neutronium), OrePrefix.pipeMedium.get(Materials.Neutronium), OrePrefix.pipeHuge.get(Materials.Neutronium), OrePrefix.pipeMedium.get(Materials.Neutronium), OrePrefix.pipeLarge.get(Materials.Neutronium)),
    };
    /**
     * Used for Crafting Recipes
     */
    public final Object mHullObject, mConductingObject, mLargerConductingObject, mManagingObject, mBetterManagingObject;
    private final SubTag mType;
    private final byte mRank;
    private final long mPrimaryValue, mSecondaryValue, mSpeedMultiplier, mEnergyCostMultiplier;
    private final Materials mMaterial;

    public Tier(SubTag aType, int aRank, long aPrimaryValue, long aSecondaryValue, long aSpeedMultiplier, long aEnergyCostMultiplier, Materials aMaterial, Object aHullObject, Object aConductingObject, Object aLargerConductingObject, Object aManagingObject, Object aBetterManagingObject) {
        mType = aType;
        mRank = (byte) aRank;
        mPrimaryValue = aPrimaryValue;
        mSecondaryValue = aSecondaryValue;
        mSpeedMultiplier = aSpeedMultiplier;
        mEnergyCostMultiplier = Math.max(mSpeedMultiplier, aEnergyCostMultiplier);
        mMaterial = aMaterial;

        mHullObject = aHullObject;
        mConductingObject = aConductingObject;
        mManagingObject = aManagingObject;
        mBetterManagingObject = aBetterManagingObject;
        mLargerConductingObject = aLargerConductingObject;
    }

    public byte getRank() {
        return mRank;
    }

    public SubTag getEnergyType() {
        return mType;
    }

    public long getEnergyPrimary() {
        return mPrimaryValue;
    }

    public long getEnergySecondary() {
        return mSecondaryValue;
    }

    public long getSpeedMultiplier() {
        return mSpeedMultiplier;
    }

    public long getEnergyCostMultiplier() {
        return mEnergyCostMultiplier;
    }

    public Materials getMaterial() {
        return mMaterial;
    }
}
