package gregtech.api;

import gregtech.api.block.machines.BlockMachine;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.util.BaseCreativeTab;
import gregtech.api.util.GTControlledRegistry;
import gregtech.api.util.IBlockOre;
import gregtech.common.items.MetaItems;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GregTechAPI {

    public static BlockMachine MACHINE;

    public static final BaseCreativeTab TAB_GREGTECH =
        new BaseCreativeTab(GTValues.MODID + ".main", () -> MetaItems.BATTERY_HULL_HV.getStackForm(), true);
    public static final BaseCreativeTab TAB_GREGTECH_MATERIALS =
        new BaseCreativeTab(GTValues.MODID + ".materials", () -> OreDictUnifier.get(OrePrefix.ingot, Materials.Aluminium), true);
    public static final BaseCreativeTab TAB_GREGTECH_ORES =
        new BaseCreativeTab(GTValues.MODID + ".ores", () -> MetaItems.JACKHAMMER.getStackForm(), true);

    public static final GTControlledRegistry<ResourceLocation, MetaTileEntity> META_TILE_ENTITY_REGISTRY = new GTControlledRegistry<>(Short.MAX_VALUE);

    public static <T extends MetaTileEntity> T registerMetaTileEntity(int id, T sampleMetaTileEntity) {
        META_TILE_ENTITY_REGISTRY.register(id, sampleMetaTileEntity.metaTileEntityId, sampleMetaTileEntity);
        return sampleMetaTileEntity;
    }

    /**
     * Registered tool items list
     * Damageable and electric items are accepted.
     */
    public static final HashSet<SimpleItemStack> crowbarList = new HashSet<>(),
            screwdriverList = new HashSet<>(),
            wrenchList = new HashSet<>(),
            softHammerList = new HashSet<>(),
            hardHammerList = new HashSet<>(),
            solderingToolList = new HashSet<>(),
            solderingMetalList = new HashSet<>();

    /**
     * The List of Hazmat Armors
     */
    public static final HashSet<SimpleItemStack> gasHazmatList = new HashSet<>(),
            bioHazmatList = new HashSet<>(),
            frostHazmatList = new HashSet<>(),
            heatHazmatList = new HashSet<>(),
            radioHazmatList = new HashSet<>(),
            electroHazmatList = new HashSet<>();

    /**
     * The List of Dimensions, which are Whitelisted for the Teleporter. This list should not contain other Planets.
     * Mystcraft Dimensions and other Dimensional Things should be allowed.
     * Mystcraft and Twilight Forest are automatically considered a Dimension, without being in this List.
     */
    public static final Collection<Integer> dimensionalList = new HashSet<>();

    /**
     * Table of all ore blocks
     */
    public static final Map<DustMaterial, Map<StoneType, IBlockOre>> oreBlockTable = new HashMap<>(); 

}