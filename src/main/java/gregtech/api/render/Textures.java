package gregtech.api.render;

import codechicken.lib.texture.TextureUtils.IIconRegister;
import gregtech.api.GTValues;
import gregtech.api.util.GTLog;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.render.OrientedOverlayRenderer.OverlayFace.*;

public class Textures {

    public static List<IIconRegister> iconRegisters = new ArrayList<>();

    public static SimpleSidedCubeRenderer STEAM_CASING_BRONZE = new SimpleSidedCubeRenderer("casings/steam/bronze");
    public static SimpleSidedCubeRenderer STEAM_CASING_STEEL = new SimpleSidedCubeRenderer("casings/steam/steel");
    public static SimpleSidedCubeRenderer STEAM_BRICKED_CASING_BRONZE = new SimpleSidedCubeRenderer("casings/steam/bricked_bronze");
    public static SimpleSidedCubeRenderer STEAM_BRICKED_CASING_STEEL = new SimpleSidedCubeRenderer("casings/steam/bricked_steel");
    public static SimpleSidedCubeRenderer[] VOLTAGE_CASINGS = new SimpleSidedCubeRenderer[GTValues.V.length];

    public static SimpleCubeRenderer BRONZE_PLATED_BRICKS = new SimpleCubeRenderer("casings/solid/machine_bronze_plated_bricks");
    public static SimpleCubeRenderer HEAT_PROOF_CASING = new SimpleCubeRenderer("casings/solid/machine_casing_heatproof");
    public static SimpleCubeRenderer FROST_PROOF_CASING = new SimpleCubeRenderer("casings/solid/machine_casing_frost_proof");
    public static SimpleCubeRenderer SOLID_STEEL_CASING = new SimpleCubeRenderer("casings/solid/machine_casing_solid_steel");
    public static SimpleCubeRenderer CLEAN_STAINLESS_STEEL_CASING = new SimpleCubeRenderer("casings/solid/machine_casing_clean_stainless_steel");
    public static SimpleCubeRenderer STABLE_TITANIUM_CASING = new SimpleCubeRenderer("casings/solid/machine_casing_stable_titanium");
    public static SimpleCubeRenderer ROBUST_TUNGSTENSTEEL_CASING = new SimpleCubeRenderer("casings/solid/machine_casing_robust_tungstensteel");

    public static SimpleCubeRenderer BRONZE_FIREBOX = new SimpleCubeRenderer("casings/firebox/machine_casing_firebox_bronze");
    public static SimpleCubeRenderer BRONZE_FIREBOX_ACTIVE = new SimpleCubeRenderer("casings/firebox/machine_casing_firebox_bronze_active");
    public static SimpleCubeRenderer STEEL_FIREBOX = new SimpleCubeRenderer("casings/firebox/machine_casing_firebox_steel");
    public static SimpleCubeRenderer STEEL_FIREBOX_ACTIVE = new SimpleCubeRenderer("casings/firebox/machine_casing_firebox_steel_active");
    public static SimpleCubeRenderer TITANIUM_FIREBOX = new SimpleCubeRenderer("casings/firebox/machine_casing_firebox_titanium");
    public static SimpleCubeRenderer TITANIUM_FIREBOX_ACTIVE = new SimpleCubeRenderer("casings/firebox/machine_casing_firebox_titanium_active");
    public static SimpleCubeRenderer TUNGSTENSTEEL_FIREBOX = new SimpleCubeRenderer("casings/firebox/machine_casing_firebox_tungstensteel");
    public static SimpleCubeRenderer TUNGSTENSTEEL_FIREBOX_ACTIVE = new SimpleCubeRenderer("casings/firebox/machine_casing_firebox_tungstensteel_active");

    public static OrientedOverlayRenderer COAL_BOILER_OVERLAY = new OrientedOverlayRenderer("generators/boiler/coal", FRONT);
    public static OrientedOverlayRenderer LAVA_BOILER_OVERLAY = new OrientedOverlayRenderer("generators/boiler/lava", FRONT);
    public static OrientedOverlayRenderer SOLAR_BOILER_OVERLAY = new OrientedOverlayRenderer("generators/boiler/solar", TOP);

    public static OrientedOverlayRenderer PRIMITIVE_BLAST_FURNACE_OVERLAY = new OrientedOverlayRenderer("machines/primitive_blast_furnace", FRONT);
    public static OrientedOverlayRenderer MULTIBLOCK_WORKABLE_OVERLAY = new OrientedOverlayRenderer("machines/multiblock_workable", FRONT);
    public static LargeTurbineRenderer LARGE_TURBINE_ROTOR_RENDERER = new LargeTurbineRenderer();

    public static OrientedOverlayRenderer ALLOY_SMELTER_OVERLAY = new OrientedOverlayRenderer("machines/alloy_smelter", FRONT);
    public static OrientedOverlayRenderer FURNACE_OVERLAY = new OrientedOverlayRenderer("machines/furnace", FRONT);
    public static OrientedOverlayRenderer ELECTRIC_FURNACE_OVERLAY = new OrientedOverlayRenderer("machines/electric_furnace", FRONT);
    public static OrientedOverlayRenderer EXTRACTOR_OVERLAY = new OrientedOverlayRenderer("machines/extractor", FRONT, TOP, SIDE);
    public static OrientedOverlayRenderer COMPRESSOR_OVERLAY = new OrientedOverlayRenderer("machines/compressor", FRONT, TOP, SIDE);
    public static OrientedOverlayRenderer HAMMER_OVERLAY = new OrientedOverlayRenderer("machines/forge_hammer", FRONT);
    public static OrientedOverlayRenderer MACERATOR_OVERLAY = new OrientedOverlayRenderer("machines/macerator", FRONT, TOP);
    public static OrientedOverlayRenderer AMPLIFAB_OVERLAY = new OrientedOverlayRenderer("machines/amplifab", FRONT);
    public static OrientedOverlayRenderer ARC_FURNACE_OVERLAY = new OrientedOverlayRenderer("machines/arc_furnace", FRONT, BOTTOM, SIDE);
    public static OrientedOverlayRenderer ASSEMBLER_OVERLAY = new OrientedOverlayRenderer("machines/assembler", FRONT, TOP);
    public static OrientedOverlayRenderer AUTOCLAVE_OVERLAY = new OrientedOverlayRenderer("machines/autoclave", FRONT, SIDE, TOP);
    public static OrientedOverlayRenderer BENDER_OVERLAY = new OrientedOverlayRenderer("machines/bender", FRONT);
    public static OrientedOverlayRenderer BREWERY_OVERLAY = new OrientedOverlayRenderer("machines/brewery", FRONT, SIDE);
    public static OrientedOverlayRenderer CANNER_OVERLAY = new OrientedOverlayRenderer("machines/canner", FRONT);
    public static OrientedOverlayRenderer CENTRIFUGE_OVERLAY = new OrientedOverlayRenderer("machines/centrifuge", FRONT, SIDE, TOP);
    public static OrientedOverlayRenderer CHEMICAL_BATH_OVERLAY = new OrientedOverlayRenderer("machines/chemical_bath", FRONT, SIDE);
    public static OrientedOverlayRenderer CHEMICAL_REACTOR_OVERLAY = new OrientedOverlayRenderer("machines/chemical_reactor", FRONT);
    public static OrientedOverlayRenderer CUTTER_OVERLAY = new OrientedOverlayRenderer("machines/cutter", FRONT);
    public static OrientedOverlayRenderer DISTILLERY_OVERLAY = new OrientedOverlayRenderer("machines/distillery", FRONT, SIDE);
    public static OrientedOverlayRenderer ELECTROLYZER_OVERLAY = new OrientedOverlayRenderer("machines/electrolyzer", FRONT, SIDE);
    public static OrientedOverlayRenderer ELECTROMAGNETIC_SEPARATOR_OVERLAY = new OrientedOverlayRenderer("machines/electromagnetic_separator", FRONT, TOP);
    public static OrientedOverlayRenderer EXTRUDER_OVERLAY = new OrientedOverlayRenderer("machines/extruder", FRONT, TOP);
    public static OrientedOverlayRenderer FERMENTER_OVERLAY = new OrientedOverlayRenderer("machines/fermenter", FRONT, SIDE);
    public static OrientedOverlayRenderer FLUID_CANNER_OVERLAY = new OrientedOverlayRenderer("machines/fluid_canner", FRONT, SIDE);
    public static OrientedOverlayRenderer FLUID_EXTRACTOR_OVERLAY = new OrientedOverlayRenderer("machines/fluid_extractor", FRONT, SIDE, TOP);
    public static OrientedOverlayRenderer FLUID_HEATER_OVERLAY = new OrientedOverlayRenderer("machines/fluid_heater", FRONT, SIDE, TOP);
    public static OrientedOverlayRenderer FLUID_SOLIDIFIER_OVERLAY = new OrientedOverlayRenderer("machines/fluid_solidifier", FRONT);
    public static OrientedOverlayRenderer FORGE_HAMMER_OVERLAY = new OrientedOverlayRenderer("machines/forge_hammer", FRONT);
    public static OrientedOverlayRenderer FORMING_PRESS_OVERLAY = new OrientedOverlayRenderer("machines/press", FRONT, SIDE, TOP);
    public static OrientedOverlayRenderer LATHE_OVERLAY = new OrientedOverlayRenderer("machines/lathe", FRONT);
    public static OrientedOverlayRenderer MICROWAVE_OVERLAY = new OrientedOverlayRenderer("machines/microwave", FRONT);
    public static OrientedOverlayRenderer MIXER_OVERLAY = new OrientedOverlayRenderer("machines/mixer", FRONT, SIDE, TOP);
    public static OrientedOverlayRenderer ORE_WASHER_OVERLAY = new OrientedOverlayRenderer("machines/ore_washer", FRONT, SIDE);
    public static OrientedOverlayRenderer PACKER_OVERLAY = new OrientedOverlayRenderer("machines/packer", FRONT);
    public static OrientedOverlayRenderer UNPACKER_OVERLAY = new OrientedOverlayRenderer("machines/unpacker", FRONT);
    public static OrientedOverlayRenderer PLASMA_ARC_FURNACE_OVERLAY = new OrientedOverlayRenderer("machines/plasma_arc_furnace", BOTTOM, FRONT, SIDE);
    public static OrientedOverlayRenderer POLARIZER_OVERLAY = new OrientedOverlayRenderer("machines/polarizer", FRONT, TOP);
    public static OrientedOverlayRenderer LASER_ENGRAVER_OVERLAY = new OrientedOverlayRenderer("machines/laser_engraver", FRONT);
    public static OrientedOverlayRenderer PRINTER_OVERLAY = new OrientedOverlayRenderer("machines/printer", FRONT);
    public static OrientedOverlayRenderer SIFTER_OVERLAY = new OrientedOverlayRenderer("machines/sifter", FRONT, TOP);
    public static OrientedOverlayRenderer THERMAL_CENTRIFUGE_OVERLAY = new OrientedOverlayRenderer("machines/thermal_centrifuge", FRONT);
    public static OrientedOverlayRenderer WIREMILL_OVERLAY = new OrientedOverlayRenderer("machines/wiremill", FRONT, TOP);

    public static OrientedOverlayRenderer DIESEL_GENERATOR_OVERLAY = new OrientedOverlayRenderer("generators/diesel", TOP);
    public static OrientedOverlayRenderer GAS_TURBINE_OVERLAY = new OrientedOverlayRenderer("generators/gas_turbine", SIDE);
    public static OrientedOverlayRenderer STEAM_TURBINE_OVERLAY = new OrientedOverlayRenderer("generators/steam_turbine", SIDE);

    public static SimpleOverlayRenderer PIPE_OUT_OVERLAY = new SimpleOverlayRenderer("machine/overlay_pipe_out");
    public static SimpleOverlayRenderer PIPE_IN_OVERLAY = new SimpleOverlayRenderer("machine/overlay_pipe_in");
    public static SimpleOverlayRenderer FLUID_OUTPUT_OVERLAY = new SimpleOverlayRenderer("machine/overlay_fluid_output");
    public static SimpleOverlayRenderer ITEM_OUTPUT_OVERLAY = new SimpleOverlayRenderer("machine/overlay_item_output");
    public static SimpleOverlayRenderer ROTOR_HOLDER_OVERLAY = new SimpleOverlayRenderer("machine/overlay_rotor_holder");

    public static SimpleOverlayRenderer ENERGY_OUT = new SimpleOverlayRenderer("machine/overlay_energy_out");
    public static SimpleOverlayRenderer ENERGY_IN = new SimpleOverlayRenderer("machine/overlay_energy_in");
    public static SimpleOverlayRenderer ENERGY_OUT_MULTI = new SimpleOverlayRenderer("machine/overlay_energy_out_multi");
    public static SimpleOverlayRenderer ENERGY_IN_MULTI = new SimpleOverlayRenderer("machine/overlay_energy_in_multi");

    static {
        for(int i = 0; i < VOLTAGE_CASINGS.length; i++) {
            String voltageName = GTValues.VN[i].toLowerCase();
            VOLTAGE_CASINGS[i] = new SimpleSidedCubeRenderer("casings/voltage/" + voltageName);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void register(TextureMap textureMap) {
        GTLog.logger.info("Loading meta tile entity texture sprites...");
        for(IIconRegister iconRegister : iconRegisters) {
            iconRegister.registerIcons(textureMap);
        }
    }

}
