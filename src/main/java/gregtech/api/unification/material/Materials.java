package gregtech.api.unification.material;

import gregtech.api.GTValues;
import gregtech.api.unification.Element;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.init.Enchantments;

import static com.google.common.collect.ImmutableList.of;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.FluidMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.GemMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.IngotMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.Material.MatFlags.*;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.*;
import static gregtech.api.unification.material.MaterialIconSet.*;

@SuppressWarnings("WeakerAccess")
public class Materials {

    public static void register() {
        MarkerMaterials.register();
    }

    private static final long STD_SOLID = GENERATE_PLATE | GENERATE_ROD | GENERATE_BOLT_SCREW | GENERATE_LONG_ROD;
    private static final long STD_GEM = GENERATE_ORE | STD_SOLID | GENERATE_LENSE;
    private static final long STD_METAL = GENERATE_PLATE;
    private static final long EXT_METAL = STD_METAL | GENERATE_ROD | GENERATE_BOLT_SCREW | GENERATE_LONG_ROD;
    private static final long EXT2_METAL = EXT_METAL | GENERATE_GEAR | GENERATE_FOIL | GENERATE_FINE_WIRE;

    public static final MarkerMaterial _NULL = new MarkerMaterial("_null");

    /**
     * Direct Elements
     */
    public static IngotMaterial Aluminium = new IngotMaterial(1, "aluminium", 0x80C8F0, DULL, 2, of(), EXT2_METAL | GENERATE_SMALL_GEAR | GENERATE_ORE | GENERATE_RING | GENERATE_FRAME, Element.Al, 10.0F, 2.0f, 128, 1700);
    public static IngotMaterial Americium = new IngotMaterial(2, "americium", 0xC8C8C8, METALLIC, 3, of(), STD_METAL | GENERATE_ROD | GENERATE_LONG_ROD, Element.Am);
    public static IngotMaterial Antimony = new IngotMaterial(3, "antimony", 0xDCDCC8, SHINY, 2, of(), EXT_METAL | MORTAR_GRINDABLE, Element.Sb);
    public static FluidMaterial Argon = new FluidMaterial(4, "argon", 0xBBBB00, FLUID, of(), STATE_GAS | GENERATE_PLASMA, Element.Ar);
    public static DustMaterial Arsenic = new DustMaterial(5, "arsenic", 0xDDDDDD, SAND, 2, of(), 0, Element.As);
    public static IngotMaterial Barium = new IngotMaterial(6, "barium", 0xFFFFFF, SHINY, 2, of(), 0, Element.Ba);
    public static IngotMaterial Beryllium = new IngotMaterial(7, "beryllium", 0x64B464, METALLIC, 2, of(), STD_METAL | GENERATE_ORE, Element.Be);
    public static IngotMaterial Bismuth = new IngotMaterial(8, "bismuth", 0x64A0A0, METALLIC, 1, of(), GENERATE_ORE, Element.Bi);
    public static DustMaterial Boron = new DustMaterial(9, "boron", 0xD2F0D2, SAND, 2, of(), 0, Element.B);
    public static IngotMaterial Caesium = new IngotMaterial(10, "caesium", 0xFFFFFC, DULL, 2, of(), 0, Element.Cs);
    public static IngotMaterial Calcium = new IngotMaterial(11, "calcium", 0xDDDDAA, METALLIC, 2, of(), 0, Element.Ca);
    public static IngotMaterial Carbon = new IngotMaterial(12, "carbon", 0x333333, DULL, 2, of(), 0, Element.C);
    public static IngotMaterial Cadmium = new IngotMaterial(13, "cadmium", 0x505060, SHINY, 2, of(), 0, Element.Cd);
    public static IngotMaterial Cerium = new IngotMaterial(14, "cerium", 0xEEEEEE, METALLIC, 2, of(), 0, Element.Ce, 1068);
    public static FluidMaterial Chlorine = new FluidMaterial(15, "chlorine", 0xEEEECC, GAS, of(), STATE_GAS, Element.Cl);
    public static IngotMaterial Chrome = new IngotMaterial(16, "chrome", 0xFFAAAB, SHINY, 3, of(), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR, Element.Cr, 12.0f, 3.0f, 512, 1700);
    public static IngotMaterial Cobalt = new IngotMaterial(17, "cobalt", 0x2929BC, METALLIC, 2, of(), GENERATE_ORE | STD_METAL, Element.Co, 10.0F, 3.0f, 256);
    public static IngotMaterial Copper = new IngotMaterial(18, "copper", 0xFF8000, SHINY, 1, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_DENSE, Element.Cu);
    public static FluidMaterial Deuterium = new FluidMaterial(19, "deuterium", 0xEEEE00, FLUID, of(), STATE_GAS, Element.D);
    public static IngotMaterial Dysprosium = new IngotMaterial(20, "dysprosium", 0xFFFFEE, SHINY, 2, of(), 0, Element.Dy, 1680);
    public static IngotMaterial Erbium = new IngotMaterial(21, "erbium", 0xEEEEEE, METALLIC, 2, of(), STD_METAL, Element.Er, 1802);
    public static IngotMaterial Europium = new IngotMaterial(22, "europium", 0xFFFFFF, METALLIC, 2, of(), STD_METAL | GENERATE_ROD, Element.Eu, 1099);
    public static FluidMaterial Fluorine = new FluidMaterial(23, "fluorine", 0xFFFFAA, GAS, of(), STATE_GAS, Element.F).setFluidTemperature(253);
    public static IngotMaterial Gadolinium = new IngotMaterial(24, "gadolinium", 0xDDDDFF, METALLIC, 2, of(), 0, Element.Gd, 1585);
    public static IngotMaterial Gallium = new IngotMaterial(25, "gallium", 0xEEEEFF, SHINY, 2, of(), GENERATE_PLATE, Element.Ga);
    public static IngotMaterial Gold = new IngotMaterial(26, "gold", 0xFFFF00, SHINY, 2, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE | EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES, Element.Au);
    public static IngotMaterial Holmium = new IngotMaterial(27, "holmium", 0xFFFFFF, METALLIC, 2, of(), 0, Element.Ho, 1734);
    public static FluidMaterial Hydrogen = new FluidMaterial(28, "hydrogen", 0x00FFAA, GAS, of(), STATE_GAS, Element.H);
    public static FluidMaterial Helium = new FluidMaterial(29, "helium", 0xDDDD00, GAS, of(), STATE_GAS | GENERATE_PLASMA, Element.He);
    public static FluidMaterial Helium3 = new FluidMaterial(30, "helium3", 0xDDDD00, GAS, of(), STATE_GAS, Element.He_3);
    public static IngotMaterial Indium = new IngotMaterial(31, "indium", 0x6600BB, METALLIC, 2, of(), 0, Element.In);
    public static IngotMaterial Iridium = new IngotMaterial(32, "iridium", 0xFFFFFF, DULL, 3, of(), GENERATE_ORE | EXT2_METAL | GENERATE_ORE | GENERATE_RING | GENERATE_ROTOR, Element.Ir, 7.0F, 3.0f, 2560, 2719);
    public static IngotMaterial Iron = new IngotMaterial(33, "iron", 0xAAAAAA, METALLIC, 2, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_DENSE | GENERATE_FRAME | GENERATE_LONG_ROD | GENERATE_PLASMA | EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES, Element.Fe, 7.0F, 2.5f, 256);
    public static IngotMaterial Lanthanum = new IngotMaterial(34, "lanthanum", 0xFFFFFF, METALLIC, 2, of(), 0, Element.La, 1193);
    public static IngotMaterial Lead = new IngotMaterial(35, "lead", 0x8C648C, DULL, 1, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_DENSE, Element.Pb);
    public static IngotMaterial Lithium = new IngotMaterial(36, "lithium", 0xCBCBCB, DULL, 2, of(), STD_METAL | GENERATE_ORE, Element.Li);
    public static IngotMaterial Lutetium = new IngotMaterial(37, "lutetium", 0xFFFFFF, METALLIC, 2, of(), 0, Element.Lu, 1925);
    public static IngotMaterial Magnesium = new IngotMaterial(38, "magnesium", 0xFFBBBB, METALLIC, 2, of(), 0, Element.Mg);
    public static IngotMaterial Manganese = new IngotMaterial(39, "manganese", 0xEEEEEE, DULL, 2, of(), GENERATE_FOIL, Element.Mn, 7.0F, 2.0f, 512);
    public static FluidMaterial Mercury = new FluidMaterial(40, "mercury", 0xFFDDDD, FLUID, of(), SMELT_INTO_FLUID, Element.Hg);
    public static IngotMaterial Molybdenum = new IngotMaterial(41, "molybdenum", 0xAAAADD, DULL, 2, of(), GENERATE_ORE, Element.Mo, 7.0F, 2.0f, 512);
    public static IngotMaterial Neodymium = new IngotMaterial(42, "neodymium", 0x777777, METALLIC, 2, of(), STD_METAL | GENERATE_ROD | GENERATE_ORE, Element.Nd, 7.0F, 2.0f, 512, 1297);
    public static IngotMaterial Darmstadtium = new IngotMaterial(43, "darmstadtium", 0xAAAAAA, METALLIC, 6, of(), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD | GENERATE_FRAME, Element.Ds, 24.0F, 6.0f, 155360);
    public static IngotMaterial Nickel = new IngotMaterial(44, "nickel", 0xAAAAFF, METALLIC, 2, of(), STD_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_PLASMA, Element.Ni);
    public static IngotMaterial Niobium = new IngotMaterial(45, "niobium", 0x9486AA, METALLIC, 2, of(), STD_METAL | GENERATE_ORE, Element.Nb, 2750);
    public static FluidMaterial Nitrogen = new FluidMaterial(46, "nitrogen", 0x7090AF, FLUID, of(), STATE_GAS | GENERATE_PLASMA, Element.N);
    public static IngotMaterial Osmium = new IngotMaterial(47, "osmium", 0x5050FF, METALLIC, 4, of(), GENERATE_ORE | EXT2_METAL | GENERATE_RING | GENERATE_ROTOR, Element.Os, 16.0F, 4.0f, 1280, 3306);
    public static FluidMaterial Oxygen = new FluidMaterial(48, "oxygen", 0x90AAEE, FLUID, of(), STATE_GAS | GENERATE_PLASMA, Element.O);
    public static IngotMaterial Palladium = new IngotMaterial(49, "palladium", 0xCED0DD, METALLIC, 2, of(), EXT2_METAL | GENERATE_ORE | GENERATE_FLUID_BLOCK, Element.Pd, 8.0f, 2.0f, 512, 1228);
    public static DustMaterial Phosphorus = new DustMaterial(50, "phosphorus", 0xC8C800, SAND, 2, of(), 0, Element.P);
    public static IngotMaterial Platinum = new IngotMaterial(51, "platinum", 0xFFFF99, SHINY, 2, of(), EXT2_METAL | GENERATE_ORE | GENERATE_FLUID_BLOCK, Element.Pt);
    public static IngotMaterial Plutonium = new IngotMaterial(52, "plutonium", 0xF03232, METALLIC, 3, of(), EXT_METAL, Element.Pu);
    public static IngotMaterial Plutonium241 = new IngotMaterial(53, "plutonium241", 0xFA4646, SHINY, 3, of(), EXT_METAL, Element.Pu_241);
    public static IngotMaterial Potassium = new IngotMaterial(54, "potassium", 0xCECECE, METALLIC, 1, of(), EXT_METAL, Element.K);
    public static IngotMaterial Praseodymium = new IngotMaterial(55, "praseodymium", 0xCECECE, METALLIC, 2, of(), EXT_METAL, Element.Pr, 1208);
    public static IngotMaterial Promethium = new IngotMaterial(56, "promethium", 0xFFFFFF, METALLIC, 2, of(), EXT_METAL, Element.Pm, 1315);
    public static FluidMaterial Radon = new FluidMaterial(57, "radon", 0xFF00FF, FLUID, of(), STATE_GAS, Element.Rn);
    public static IngotMaterial Rubidium = new IngotMaterial(58, "rubidium", 0xF01E1E, METALLIC, 2, of(), STD_METAL, Element.Rb);
    public static IngotMaterial Samarium = new IngotMaterial(59, "samarium", 0xFFFFFF, METALLIC, 2, of(), STD_METAL, Element.Sm, 1345);
    public static IngotMaterial Scandium = new IngotMaterial(60, "scandium", 0xFFFFFF, METALLIC, 2, of(), STD_METAL, Element.Sc, 1814);
    public static IngotMaterial Silicon = new IngotMaterial(61, "silicon", 0x3C3C50, METALLIC, 2, of(), STD_METAL | GENERATE_FOIL, Element.Si, 1687);
    public static IngotMaterial Silver = new IngotMaterial(62, "silver", 0xDCDCFF, SHINY, 2, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE, Element.Ag);
    public static IngotMaterial Sodium = new IngotMaterial(63, "sodium", 0x000096, METALLIC, 2, of(), STD_METAL, Element.Na);
    public static IngotMaterial Strontium = new IngotMaterial(64, "strontium", 0xC8C896, METALLIC, 2, of(), STD_METAL, Element.Sr);
    public static DustMaterial Sulfur = new DustMaterial(65, "sulfur", 0xC8C800, SAND, 2, of(), NO_SMASHING | NO_SMELTING | FLAMMABLE | GENERATE_ORE, Element.S);
    public static IngotMaterial Tantalum = new IngotMaterial(66, "tantalum", 0xFFFFFF, METALLIC, 2, of(), STD_METAL, Element.Ta);
    public static IngotMaterial Tellurium = new IngotMaterial(67, "tellurium", 0xFFFFFF, METALLIC, 2, of(), STD_METAL, Element.Te);
    public static IngotMaterial Terbium = new IngotMaterial(68, "terbium", 0xFFFFFF, METALLIC, 2, of(), STD_METAL, Element.Tb, 1629);
    public static IngotMaterial Thorium = new IngotMaterial(69, "thorium", 0x001E00, SHINY, 2, of(), STD_METAL | GENERATE_ORE, Element.Th, 6.0F, 2.0f, 512);
    public static IngotMaterial Thulium = new IngotMaterial(70, "thulium", 0xFFFFFF, METALLIC, 2, of(), STD_METAL, Element.Tm, 1818);
    public static IngotMaterial Tin = new IngotMaterial(71, "tin", 0xDCDCDC, DULL, 1, of(), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_ROTOR | GENERATE_ORE, Element.Sn);
    public static IngotMaterial Titanium = new IngotMaterial(72, "titanium", 0xDCA0F0, METALLIC, 3, of(), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD | GENERATE_SPRING | GENERATE_FRAME | GENERATE_DENSE, Element.Ti, 7.0F, 3.0f, 1600, 1941);
    public static FluidMaterial Tritium = new FluidMaterial(73, "tritium", 0xFF0000, METALLIC, of(), STATE_GAS, Element.T);
    public static IngotMaterial Tungsten = new IngotMaterial(74, "tungsten", 0x323232, METALLIC, 3, of(), EXT2_METAL, Element.W, 7.0F, 3.0f, 2560, 3000);
    public static IngotMaterial Uranium = new IngotMaterial(75, "uranium", 0x32F032, METALLIC, 3, of(), STD_METAL | GENERATE_ORE, Element.U, 6.0F, 3.0f, 512);
    public static IngotMaterial Uranium235 = new IngotMaterial(76, "uranium235", 0x46FA46, SHINY, 3, of(), STD_METAL | GENERATE_ORE | GENERATE_ROD, Element.U_235, 6.0F, 3.0f, 512);
    public static IngotMaterial Vanadium = new IngotMaterial(77, "vanadium", 0x323232, METALLIC, 2, of(), STD_METAL, Element.V, 2183);
    public static IngotMaterial Ytterbium = new IngotMaterial(353, "ytterbium", 0xFFFFFF, METALLIC, 2, of(), STD_METAL, Element.Yb, 1097);
    public static IngotMaterial Yttrium = new IngotMaterial(78, "yttrium", 0xDCFADC, METALLIC, 2, of(), STD_METAL, Element.Y, 1799);
    public static IngotMaterial Zinc = new IngotMaterial(79, "zinc", 0xFAF0F0, METALLIC, 1, of(), STD_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_FOIL, Element.Zn);

    /**
     * First Degree Compounds
     */
    public static FluidMaterial Methane = new FluidMaterial(80, "methane", 0xFFFFFF, FLUID, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 4)), 0);
    public static FluidMaterial CarbonDioxide = new FluidMaterial(81, "carbon_dioxide", 0xA9D0F5, FLUID, of(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 2)), 0);
    public static FluidMaterial NobleGases = new FluidMaterial(82, "noble_gases", 0xA9D0F5, FLUID, of(new MaterialStack(CarbonDioxide, 25), new MaterialStack(Helium, 11), new MaterialStack(Methane, 4), new MaterialStack(Deuterium, 2), new MaterialStack(Radon, 1)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Air = new FluidMaterial(83, "air", 0xA9D0F5, FLUID, of(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1), new MaterialStack(NobleGases, 1)), STATE_GAS | DISABLE_DECOMPOSITION);
    public static FluidMaterial LiquidAir = new FluidMaterial(84, "liquid_air", 0xA9D0F5, FLUID, of(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1), new MaterialStack(NobleGases, 1)), STATE_GAS | DISABLE_DECOMPOSITION);
    public static GemMaterial Almandine = new GemMaterial(85, "almandine", 0xFF0000, GEM_VERTICAL, 1, of(new MaterialStack(Aluminium, 2), new MaterialStack(Iron, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), STD_GEM);
    public static DustMaterial Andradite = new DustMaterial(86, "andradite", 0x967800, GEM_VERTICAL, 1, of(new MaterialStack(Calcium, 3), new MaterialStack(Iron, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), 0);
    public static IngotMaterial AnnealedCopper = new IngotMaterial(87, "annealed_copper", 0xFF7814, SHINY, 2, of(new MaterialStack(Copper, 1)), EXT2_METAL | MORTAR_GRINDABLE);
    public static DustMaterial Asbestos = new DustMaterial(88, "asbestos", 0xE6E6E6, SAND, 1, of(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9)), 0);
    public static DustMaterial Ash = new DustMaterial(89, "ash", 0x969696, SAND, 1, of(new MaterialStack(Carbon, 1)), DISABLE_DECOMPOSITION);
    public static DustMaterial BandedIron = new DustMaterial(90, "banded_iron", 0x915A5A, DULL, 2, of(new MaterialStack(Iron, 2), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static IngotMaterial BatteryAlloy = new IngotMaterial(91, "battery_alloy", 0x9C7CA0, DULL, 1, of(new MaterialStack(Lead, 4), new MaterialStack(Antimony, 1)), EXT_METAL);
    public static GemMaterial BlueTopaz = new GemMaterial(92, "blue_topaz", 0x0000FF, GEM_HORIZONTAL, 3, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.0F, 3.0f, 256);
    public static DustMaterial Bone = new DustMaterial(93, "bone", 0xFFFFFF, ROUGH, 1, of(new MaterialStack(Calcium, 1)), MORTAR_GRINDABLE | EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES);
    public static IngotMaterial Brass = new IngotMaterial(94, "brass", 0xFFB400, METALLIC, 1, of(new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING, 8.0F, 3.0f, 152);
    public static IngotMaterial Bronze = new IngotMaterial(95, "bronze", 0xFF8000, DULL, 2, of(new MaterialStack(Tin, 1), new MaterialStack(Copper, 3)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_ROTOR | GENERATE_FRAME | GENERATE_LONG_ROD, 6.0F, 2.5f, 192);
    public static DustMaterial BrownLimonite = new DustMaterial(96, "brown_limonite", 0xC86400, METALLIC, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    public static DustMaterial Calcite = new DustMaterial(97, "calcite", 0xFAE6DC, DULL, 1, of(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static DustMaterial Cassiterite = new DustMaterial(98, "cassiterite", 0xDCDCDC, METALLIC, 1, of(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    public static DustMaterial CassiteriteSand = new DustMaterial(99, "cassiterite_sand", 0xDCDCDC, SAND, 1, of(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    public static DustMaterial Chalcopyrite = new DustMaterial(100, "chalcopyrite", 0xA07828, DULL, 1, of(new MaterialStack(Copper, 1), new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static GemMaterial Charcoal = new GemMaterial(101, "charcoal", 0x644646, LIGNITE, 1, of(new MaterialStack(Carbon, 1)), FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE);
    public static DustMaterial Chromite = new DustMaterial(102, "chromite", 0x23140F, METALLIC, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Chrome, 2), new MaterialStack(Oxygen, 4)), GENERATE_ORE, null);
    public static GemMaterial Cinnabar = new GemMaterial(103, "cinnabar", 0x960000, EMERALD, 1, of(new MaterialStack(Mercury, 1), new MaterialStack(Sulfur, 1)), GENERATE_ORE | CRYSTALLISABLE);
    public static FluidMaterial Water = new FluidMaterial(104, "water", 0x0000FF, FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_RECYCLING | DISABLE_DECOMPOSITION);
    public static DustMaterial Clay = new DustMaterial(105, "clay", 0xC8C8DC, ROUGH, 1, of(new MaterialStack(Sodium, 2), new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Water, 6)), MORTAR_GRINDABLE);
    public static GemMaterial Coal = new GemMaterial(106, "coal", 0x464646, LIGNITE, 1, of(new MaterialStack(Carbon, 1)), GENERATE_ORE | FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE | EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES);
    public static DustMaterial Cobaltite = new DustMaterial(107, "cobaltite", 0x5050FA, ROUGH, 1, of(new MaterialStack(Cobalt, 1), new MaterialStack(Arsenic, 1), new MaterialStack(Sulfur, 1)), GENERATE_ORE);
    public static DustMaterial Cooperite = new DustMaterial(108, "cooperite", 0xFFFFC8, METALLIC, 1, of(new MaterialStack(Platinum, 3), new MaterialStack(Nickel, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Palladium, 1)), GENERATE_ORE);
    public static IngotMaterial Cupronickel = new IngotMaterial(109, "cupronickel", 0xE39680, METALLIC, 1, of(new MaterialStack(Copper, 1), new MaterialStack(Nickel, 1)), EXT_METAL);
    public static DustMaterial DarkAsh = new DustMaterial(110, "dark_ash", 0x323232, SAND, 1, of(new MaterialStack(Carbon, 1)), DISABLE_DECOMPOSITION);
    public static GemMaterial Diamond = new GemMaterial(111, "diamond", 0xC8FFFF, DIAMOND, 3, of(new MaterialStack(Carbon, 1)), GENERATE_ROD | GENERATE_BOLT_SCREW | GENERATE_LENSE | GENERATE_GEAR | NO_SMASHING | NO_SMELTING | FLAMMABLE | HIGH_SIFTER_OUTPUT | GENERATE_ORE | DISABLE_DECOMPOSITION | EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES, 8.0F, 3.0f, 1280);
    public static IngotMaterial Electrum = new IngotMaterial(112, "electrum", 0xFFFF64, SHINY, 2, of(new MaterialStack(Silver, 1), new MaterialStack(Gold, 1)), EXT2_METAL | MORTAR_GRINDABLE);
    public static GemMaterial Emerald = new GemMaterial(113, "emerald", 0x50FF50, EMERALD, 2, of(new MaterialStack(Beryllium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 6), new MaterialStack(Oxygen, 18)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT | EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES, 10.0F, 2.0f, 368);
    public static DustMaterial Galena = new DustMaterial(114, "galena", 0x643C64, ROUGH, 3, of(new MaterialStack(Lead, 3), new MaterialStack(Silver, 3), new MaterialStack(Sulfur, 2)), GENERATE_ORE | NO_SMELTING);
    public static DustMaterial Garnierite = new DustMaterial(115, "garnierite", 0x32C846, ROUGH, 3, of(new MaterialStack(Nickel, 1), new MaterialStack(Oxygen, 1)), GENERATE_ORE);
    public static FluidMaterial Glyceryl = new FluidMaterial(116, "glyceryl", 0x009696, FLUID, of(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 5), new MaterialStack(Nitrogen, 3), new MaterialStack(Oxygen, 9)), FLAMMABLE | EXPLOSIVE | NO_SMELTING | NO_SMASHING);
    public static GemMaterial GreenSapphire = new GemMaterial(117, "green_sapphire", 0x64C882, GEM_HORIZONTAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT | GENERATE_LENSE, 8.0F, 3.0f, 368);
    public static DustMaterial Grossular = new DustMaterial(118, "grossular", 0xC86400, GEM_VERTICAL, 1, of(new MaterialStack(Calcium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static FluidMaterial DistilledWater = new FluidMaterial(119, "distilled_water", 0x0000FF, FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_RECYCLING | DISABLE_DECOMPOSITION);
    public static DustMaterial Ice = new DustMaterial(120, "ice", 0xC8C8FF, ROUGH, 0, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_SMASHING | NO_RECYCLING | SMELT_INTO_FLUID | EXCLUDE_BLOCK_CRAFTING_RECIPES | DISABLE_DECOMPOSITION);
    public static DustMaterial Ilmenite = new DustMaterial(121, "ilmenite", 0x463732, ROUGH, 3, of(new MaterialStack(Iron, 1), new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE | DISABLE_DECOMPOSITION);
    public static GemMaterial Rutile = new GemMaterial(122, "rutile", 0xD40D5C, GEM_HORIZONTAL, 2, of(new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 2)), STD_GEM | DISABLE_DECOMPOSITION);
    public static DustMaterial Bauxite = new DustMaterial(123, "bauxite", 0xC86400, ROUGH, 1, of(new MaterialStack(Rutile, 2), new MaterialStack(Aluminium, 16), new MaterialStack(Hydrogen, 10), new MaterialStack(Oxygen, 11)), GENERATE_ORE | DISABLE_DECOMPOSITION);
    public static FluidMaterial TitaniumTetrachloride = new FluidMaterial(124, "titanium_tetrachloride", 0xD40D5C, FLUID, of(new MaterialStack(Titanium, 1), new MaterialStack(Chlorine, 4)), DISABLE_DECOMPOSITION).setFluidTemperature(2200);
    public static DustMaterial MagnesiumChloride = new DustMaterial(125, "magnesium_chloride", 0xD40D5C, ROUGH, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Chlorine, 2)), 0);
    public static IngotMaterial Invar = new IngotMaterial(126, "invar", 0xB4B478, METALLIC, 2, of(new MaterialStack(Iron, 2), new MaterialStack(Nickel, 1)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_FRAME, 7.0F, 3.0f, 512);
    public static IngotMaterial Kanthal = new IngotMaterial(127, "kanthal", 0xC2D2DF, METALLIC, 2, of(new MaterialStack(Iron, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Chrome, 1)), EXT_METAL, null, 1800);
    public static GemMaterial Lazurite = new GemMaterial(128, "lazurite", 0x6478FF, LAPIS, 1, of(new MaterialStack(Aluminium, 6), new MaterialStack(Silicon, 6), new MaterialStack(Calcium, 8), new MaterialStack(Sodium, 8)), GENERATE_PLATE | GENERATE_ORE | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | GENERATE_ROD | DECOMPOSITION_BY_ELECTROLYZING);
    public static IngotMaterial Magnalium = new IngotMaterial(129, "magnalium", 0xC8BEFF, DULL, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Aluminium, 2)), EXT2_METAL | GENERATE_LONG_ROD, 6.0F, 2.0f, 256);
    public static DustMaterial Magnesite = new DustMaterial(130, "magnesite", 0xFAFAB4, METALLIC, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static DustMaterial Magnetite = new DustMaterial(131, "magnetite", 0x1E1E1E, METALLIC, 2, of(new MaterialStack(Iron, 3), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial Molybdenite = new DustMaterial(132, "molybdenite", 0x191919, METALLIC, 2, of(new MaterialStack(Molybdenum, 1), new MaterialStack(Sulfur, 2)), GENERATE_ORE);
    public static IngotMaterial Nichrome = new IngotMaterial(133, "nichrome", 0xCDCEF6, METALLIC, 2, of(new MaterialStack(Nickel, 4), new MaterialStack(Chrome, 1)), EXT_METAL, null, 2700);
    public static IngotMaterial NiobiumNitride = new IngotMaterial(134, "niobium_nitride", 0x1D291D, DULL, 2, of(new MaterialStack(Niobium, 1), new MaterialStack(Nitrogen, 1)), EXT_METAL, null, 2573);
    public static IngotMaterial NiobiumTitanium = new IngotMaterial(135, "niobium_titanium", 0x1D1D29, DULL, 2, of(new MaterialStack(Niobium, 1), new MaterialStack(Titanium, 1)), EXT2_METAL, null, 4500);
    public static FluidMaterial NitrogenDioxide = new FluidMaterial(137, "nitrogen_dioxide", 0x64AFFF, FLUID, of(new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 2)), 0);
    public static DustMaterial Obsidian = new DustMaterial(138, "obsidian", 0x503264, DULL, 3, of(new MaterialStack(Magnesium, 1), new MaterialStack(Iron, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 8)), NO_SMASHING | EXCLUDE_BLOCK_CRAFTING_RECIPES);
    public static DustMaterial Phosphate = new DustMaterial(139, "phosphate", 0xFFFF00, ROUGH, 1, of(new MaterialStack(Phosphorus, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | FLAMMABLE | EXPLOSIVE);
    public static IngotMaterial PigIron = new IngotMaterial(140, "pig_iron", 0xC8B4B4, METALLIC, 2, of(new MaterialStack(Iron, 1)), EXT_METAL | GENERATE_RING, 6.0F, 4.0f, 384);
    public static IngotMaterial Plastic = new IngotMaterial(141, "plastic", 0xC8C8C8, DULL, 1, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2)), GENERATE_PLATE | FLAMMABLE | NO_SMASHING | SMELT_INTO_FLUID | DISABLE_DECOMPOSITION);
    public static IngotMaterial Epoxid = new IngotMaterial(142, "epoxid", 0xC88C14, DULL, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 1)), EXT2_METAL | DISABLE_DECOMPOSITION);
    public static DustMaterial Silicone = new DustMaterial(143, "silicone", 0xDCDCDC, DULL, 1, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 1)), GENERATE_PLATE | FLAMMABLE | NO_SMASHING | SMELT_INTO_FLUID | DISABLE_DECOMPOSITION);
    public static IngotMaterial Polycaprolactam = new IngotMaterial(144, "polycaprolactam", 0x323232, DULL, 1, of(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 11), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 1)), GENERATE_PLATE | DISABLE_DECOMPOSITION);
    public static IngotMaterial Polytetrafluoroethylene = new IngotMaterial(145, "polytetrafluoroethylene", 0x646464, DULL, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Fluorine, 4)), GENERATE_PLATE | SMELT_INTO_FLUID | NO_WORKING | DISABLE_DECOMPOSITION);
    public static DustMaterial Powellite = new DustMaterial(146, "powellite", 0xFFFF00, ROUGH, 2, of(new MaterialStack(Calcium, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial Pyrite = new DustMaterial(148, "pyrite", 0x967828, ROUGH, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial Pyrolusite = new DustMaterial(149, "pyrolusite", 0x9696AA, ROUGH, 2, of(new MaterialStack(Manganese, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    public static DustMaterial Pyrope = new DustMaterial(150, "pyrope", 0x783264, ROUGH, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial RockSalt = new DustMaterial(151, "rock_salt", 0xF0C8C8, FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Chlorine, 1)), GENERATE_ORE | NO_SMASHING);
    public static IngotMaterial Rubber = new IngotMaterial(152, "rubber", 0x151515, ROUGH, 0, of(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), GENERATE_PLATE | GENERATE_GEAR | GENERATE_RING | FLAMMABLE | NO_SMASHING | GENERATE_RING | NO_WORKING | DISABLE_DECOMPOSITION);
    public static DustMaterial RawRubber = new DustMaterial(153, "raw_rubber", 0xCCC789, SAND, 0, of(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), DISABLE_DECOMPOSITION);
    public static GemMaterial Ruby = new GemMaterial(154, "ruby", 0xBD4949, RUBY, 2, of(new MaterialStack(Chrome, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 8.5F, 3.0f, 256);
    public static DustMaterial Salt = new DustMaterial(155, "salt", 0xFFFFFF, SAND, 1, of(new MaterialStack(Sodium, 1), new MaterialStack(Chlorine, 1)), GENERATE_ORE | NO_SMASHING);
    public static DustMaterial Saltpeter = new DustMaterial(156, "saltpeter", 0xE6E6E6, FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | FLAMMABLE);
    public static GemMaterial Sapphire = new GemMaterial(157, "sapphire", 0x6464C8, GEM_VERTICAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, null, 7.5F, 4.0f, 256);
    public static DustMaterial Scheelite = new DustMaterial(158, "scheelite", 0xC88C14, DULL, 3, of(new MaterialStack(Tungsten, 1), new MaterialStack(Calcium, 2), new MaterialStack(Oxygen, 4)), GENERATE_ORE | DECOMPOSITION_REQUIRES_HYDROGEN);
    public static DustMaterial SiliconDioxide = new DustMaterial(159, "silicon_dioxide", 0xC8C8C8, QUARTZ, 1, of(new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 2)), NO_SMASHING | NO_SMELTING | CRYSTALLISABLE);
    public static GemMaterial Sodalite = new GemMaterial(161, "sodalite", 0x1414FF, LAPIS, 1, of(new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Sodium, 4), new MaterialStack(Chlorine, 1)), GENERATE_ORE | GENERATE_PLATE | GENERATE_ROD | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | GENERATE_ROD | DECOMPOSITION_BY_ELECTROLYZING);
    public static FluidMaterial SodiumPersulfate = new FluidMaterial(162, "sodium_persulfate", 0xFFFFFF, FLUID, of(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 2), new MaterialStack(Oxygen, 8)), 0);
    public static DustMaterial SodiumSulfide = new DustMaterial(163, "sodium_sulfide", 0xAAAA00, DULL, 1, of(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1)), 0);
    public static FluidMaterial HydrogenSulfide = new FluidMaterial(164, "hydrogen_sulfide", 0xFFFFFF, FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1)), 0);
    public static FluidMaterial Steam = new FluidMaterial(346, "steam", 0xFFFFFF, GAS, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_RECYCLING | GENERATE_FLUID_BLOCK | DISABLE_DECOMPOSITION).setFluidTemperature(380);
    public static FluidMaterial Epichlorhydrin = new FluidMaterial(349, "epichlorhydrin", 0xFFFFFF, FLUID, of(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 5), new MaterialStack(Chlorine, 1), new MaterialStack(Oxygen, 1)), 0);
    public static FluidMaterial NitricAcid = new FluidMaterial(351, "nitric_acid", 0xCCCC00, FLUID, of(new MaterialStack(Hydrogen, 1), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 3)), 0);
    public static DustMaterial Brick = new DustMaterial(355, "brick", 0xB75A40, FINE, 1, of(new MaterialStack(Clay, 1)), EXCLUDE_BLOCK_CRAFTING_RECIPES | DECOMPOSITION_BY_CENTRIFUGING);
    public static DustMaterial Fireclay = new DustMaterial(356, "fireclay", 0x928073, FINE, 2, of(new MaterialStack(Clay, 1), new MaterialStack(Brick, 1)), DECOMPOSITION_BY_CENTRIFUGING);
    public static GemMaterial Coke = new GemMaterial(357, "coke", 0x666666, LIGNITE, 1, of(new MaterialStack(Carbon, 1)), FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE);


    public static IngotMaterial SolderingAlloy = new IngotMaterial(180, "soldering_alloy", 0xDCDCE6, DULL, 1, of(new MaterialStack(Tin, 9), new MaterialStack(Antimony, 1)), EXT_METAL | GENERATE_FINE_WIRE, null);
    public static DustMaterial Spessartine = new DustMaterial(181, "spessartine", 0xFF6464, GEM_VERTICAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Manganese, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Sphalerite = new DustMaterial(182, "sphalerite", 0xFFFFFF, ROUGH, 1, of(new MaterialStack(Zinc, 1), new MaterialStack(Sulfur, 1)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT | DISABLE_DECOMPOSITION);
    public static IngotMaterial StainlessSteel = new IngotMaterial(183, "stainless_steel", 0xC8C8DC, SHINY, 2, of(new MaterialStack(Iron, 6), new MaterialStack(Chrome, 1), new MaterialStack(Manganese, 1), new MaterialStack(Nickel, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_FRAME | GENERATE_LONG_ROD, null, 7.0F, 4.0f, 480, 1700);
    public static IngotMaterial Steel = new IngotMaterial(184, "steel", 0x505050, DULL, 2, of(new MaterialStack(Iron, 1)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_DENSE | DISABLE_DECOMPOSITION | GENERATE_FRAME | GENERATE_LONG_ROD, null, 6.0F, 3.0f, 512, 1000);
    public static DustMaterial Stibnite = new DustMaterial(185, "stibnite", 0x464646, ROUGH, 2, of(new MaterialStack(Antimony, 2), new MaterialStack(Sulfur, 3)), GENERATE_ORE);
    public static FluidMaterial SulfuricAcid = new FluidMaterial(186, "sulfuric_acid", 0xFF8000, FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), 0);
    public static GemMaterial Tanzanite = new GemMaterial(187, "tanzanite", 0x4000C8, GEM_VERTICAL, 2, of(new MaterialStack(Calcium, 2), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 13)), EXT_METAL | GENERATE_ORE | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, null, 7.0F, 2.0f, 256);
    public static DustMaterial Tetrahedrite = new DustMaterial(188, "tetrahedrite", 0xC82000, ROUGH, 2, of(new MaterialStack(Copper, 3), new MaterialStack(Antimony, 1), new MaterialStack(Sulfur, 3), new MaterialStack(Iron, 1)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static IngotMaterial TinAlloy = new IngotMaterial(189, "tin_alloy", 0xC8C8C8, DULL, 2, of(new MaterialStack(Tin, 1), new MaterialStack(Iron, 1)), EXT2_METAL, null);
    public static GemMaterial Topaz = new GemMaterial(190, "topaz", 0xFF8000, GEM_HORIZONTAL, 3, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, null, 7.0F, 2.0f, 256);
    public static DustMaterial Tungstate = new DustMaterial(191, "tungstate", 0x373223, DULL, 3, of(new MaterialStack(Tungsten, 1), new MaterialStack(Lithium, 2), new MaterialStack(Oxygen, 4)), GENERATE_ORE | DECOMPOSITION_REQUIRES_HYDROGEN, null);
    public static IngotMaterial Ultimet = new IngotMaterial(192, "ultimet", 0xB4B4E6, SHINY, 4, of(new MaterialStack(Cobalt, 5), new MaterialStack(Chrome, 2), new MaterialStack(Nickel, 1), new MaterialStack(Molybdenum, 1)), EXT2_METAL, null, 9.0F, 4.0f, 2048, 2700);
    public static DustMaterial Uraninite = new DustMaterial(193, "uraninite", 0x232323, ROUGH, 3, of(new MaterialStack(Uranium, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE | DISABLE_DECOMPOSITION);
    public static DustMaterial Uvarovite = new DustMaterial(194, "uvarovite", 0xB4FFB4, GEM_VERTICAL, 2, of(new MaterialStack(Calcium, 3), new MaterialStack(Chrome, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), 0);
    public static IngotMaterial VanadiumGallium = new IngotMaterial(195, "vanadium_gallium", 0x80808C, SHINY, 2, of(new MaterialStack(Vanadium, 3), new MaterialStack(Gallium, 1)), STD_METAL | GENERATE_FOIL | GENERATE_ROD, null, 4500);
    public static IngotMaterial WroughtIron = new IngotMaterial(197, "wrought_iron", 0xC8B4B4, METALLIC, 2, of(new MaterialStack(Iron, 1)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_LONG_ROD | DISABLE_DECOMPOSITION, null, 6.0F, 3.5f, 384);
    public static DustMaterial Wulfenite = new DustMaterial(198, "wulfenite", 0xFF8000, DULL, 3, of(new MaterialStack(Lead, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial YellowLimonite = new DustMaterial(199, "yellow_limonite", 0xC8C800, METALLIC, 2, of(new MaterialStack(Iron, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static IngotMaterial YttriumBariumCuprate = new IngotMaterial(200, "yttrium_barium_cuprate", 0x504046, METALLIC, 2, of(new MaterialStack(Yttrium, 1), new MaterialStack(Barium, 2), new MaterialStack(Copper, 3), new MaterialStack(Oxygen, 7)), EXT_METAL | GENERATE_FOIL | GENERATE_FINE_WIRE, null, 4500);
    public static GemMaterial NetherQuartz = new GemMaterial(201, "nether_quartz", 0xE6D2D2, QUARTZ, 1, of(), STD_SOLID | NO_SMELTING | CRYSTALLISABLE | GENERATE_ORE | EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES);
    public static GemMaterial CertusQuartz = new GemMaterial(202, "certus_quartz", 0xD2D2E6, QUARTZ, 1, of(), STD_SOLID | NO_SMELTING | CRYSTALLISABLE | GENERATE_ORE);
    public static GemMaterial Quartzite = new GemMaterial(203, "quartzite", 0xD2E6D2, QUARTZ, 1, of(), NO_SMELTING | CRYSTALLISABLE | GENERATE_ORE);
    public static IngotMaterial Graphite = new IngotMaterial(204, "graphite", 0x808080, DULL, 2, of(), GENERATE_PLATE | GENERATE_ORE | NO_SMELTING | FLAMMABLE);
    public static IngotMaterial Graphene = new IngotMaterial(205, "graphene", 0x808080, SHINY, 2, of(), GENERATE_PLATE);
    public static GemMaterial Jasper = new GemMaterial(206, "jasper", 0xC85050, EMERALD, 2, of(), STD_GEM | NO_SMELTING | HIGH_SIFTER_OUTPUT);
    public static IngotMaterial Osmiridium = new IngotMaterial(207, "osmiridium", 0x6464FF, METALLIC, 3, of(new MaterialStack(Iridium, 3), new MaterialStack(Osmium, 1)), EXT2_METAL, null, 9.0F, 3.0f, 3152, 2500);
    public static FluidMaterial NitrationMixture = new FluidMaterial(352, "nitration_mixture", 0xEEEEAA, FLUID, of(new MaterialStack(NitricAcid, 1), new MaterialStack(SulfuricAcid, 1)), DISABLE_DECOMPOSITION);
    public static DustMaterial Tenorite = new DustMaterial(358, "tenorite", 0x606060, FINE, 1, of(new MaterialStack(Copper, 1), new MaterialStack(Oxygen, 1)), GENERATE_ORE);
    public static DustMaterial Cuprite = new DustMaterial(359, "cuprite", 0x770000, RUBY, 2, of(new MaterialStack(Copper, 2), new MaterialStack(Oxygen, 1)), GENERATE_ORE);
    public static DustMaterial Bornite = new DustMaterial(360, "bornite", 0xC11800, DULL, 1, of(new MaterialStack(Copper, 5), new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 4)), GENERATE_ORE);
    public static DustMaterial Chalcocite = new DustMaterial(361, "chalcocite", 0x353535, GEM_VERTICAL, 2, of(new MaterialStack(Copper, 2), new MaterialStack(Sulfur, 1)), GENERATE_ORE);
    public static DustMaterial Enargite = new DustMaterial(362, "enargite", 0xBBBBBB, METALLIC, 2, of(new MaterialStack(Copper, 3), new MaterialStack(Arsenic, 1), new MaterialStack(Sulfur, 4)), GENERATE_ORE);
    public static DustMaterial Tennantite = new DustMaterial(363, "tennantite", 0x909090, METALLIC, 2, of(new MaterialStack(Copper, 12), new MaterialStack(Arsenic, 4), new MaterialStack(Sulfur, 13)), GENERATE_ORE);

    public static DustMaterial PhosphorousPentoxide = new DustMaterial(466, "phosphorous_pentoxide", 8158464, DULL, 1, of(new MaterialStack(Phosphorus, 4), new MaterialStack(Oxygen, 10)), 0);
    public static FluidMaterial PhosphoricAcid = new FluidMaterial(467, "phosphoric_acid", 11447824, FLUID, of(new MaterialStack(Hydrogen, 3), new MaterialStack(Phosphorus, 1), new MaterialStack(Oxygen, 4)), 0);
    public static DustMaterial SodiumHydroxide = new DustMaterial(373, "sodium_hydroxide", 6466, DULL, 1, of(new MaterialStack(Sodium, 1), new MaterialStack(Oxygen, 1), new MaterialStack(Hydrogen, 1)), 0);
    public static DustMaterial Quicklime = new DustMaterial(374, "quicklime", 8421504, SAND, 1, of(new MaterialStack(Calcium, 1), new MaterialStack(Oxygen, 1)), 0);
    public static FluidMaterial SulfurTrioxide = new FluidMaterial(376, "sulfur_trioxide", 8618781, GAS, of(new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 3)), STATE_GAS);
    public static FluidMaterial SulfurDioxide = new FluidMaterial(377, "sulfur_dioxide", 10263584, GAS, of(new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 2)), STATE_GAS);
    public static FluidMaterial CarbonMonoxde = new FluidMaterial(380, "carbon_monoxide", 1655660, GAS, of(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 1)), STATE_GAS);
    public static FluidMaterial DilutedSulfuricAcid = new FluidMaterial(381, "diluted_sulfuric_acid", 9987366, FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), DISABLE_DECOMPOSITION);
    public static DustMaterial SodiumBisulfate = new DustMaterial(382, "sodium_bisulfate", 10291, DULL, 1, of(new MaterialStack(Sodium, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), DISABLE_DECOMPOSITION);
    public static FluidMaterial HydrochloricAcid = new FluidMaterial(400, "hydrochloric_acid", 9477273, FLUID, of(new MaterialStack(Hydrogen, 1), new MaterialStack(Chlorine, 1)), 0);
    public static FluidMaterial DilutedHydrochloricAcid = new FluidMaterial(384, "diluted_hydrochloric_acid", 8160900, FLUID, of(new MaterialStack(Hydrogen, 1), new MaterialStack(Chlorine, 1)), DISABLE_DECOMPOSITION);
    public static FluidMaterial HypochlorousAcid = new FluidMaterial(385, "hypochlorous_acid", 6123637, FLUID, of(new MaterialStack(Hydrogen, 1), new MaterialStack(Chlorine, 1), new MaterialStack(Oxygen, 1)), 0);
    public static FluidMaterial Ammonia = new FluidMaterial(386, "ammonia", 4011371, GAS, of(new MaterialStack(Nitrogen, 1), new MaterialStack(Hydrogen, 3)), STATE_GAS);
    public static FluidMaterial Chloramine = new FluidMaterial(387, "chloramine", 4031340, GAS, of(new MaterialStack(Nitrogen, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(HydrochloricAcid, 1)), STATE_GAS);
    public static IngotMaterial GalliumArsenide = new IngotMaterial(410, "gallium_arsenide", 7500402, DULL, 1, of(new MaterialStack(Arsenic, 1), new MaterialStack(Gallium, 1)), DECOMPOSITION_BY_CENTRIFUGING | GENERATE_PLATE, null, 1200);
    public static DustMaterial Potash = new DustMaterial(402, "potash", 5057059, SAND, 1, of(new MaterialStack(Potassium, 2), new MaterialStack(Oxygen, 1)), 0);
    public static DustMaterial SodaAsh = new DustMaterial(403, "soda_ash", 7697800, SAND, 1, of(new MaterialStack(Sodium, 2), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)), 0);
    public static FluidMaterial NickelSulfateSolution = new FluidMaterial(412, "nickel_sulfate_water_solution", 4109888, FLUID, of(new MaterialStack(Nickel, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4), new MaterialStack(Water, 6)), 0);
    public static FluidMaterial CopperSulfateSolution = new FluidMaterial(413, "blue_vitriol_water_solution", 4761024, FLUID, of(new MaterialStack(Copper, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4), new MaterialStack(Water, 5)), 0);
    public static IngotMaterial IndiumGalliumPhosphide = new IngotMaterial(421, "indium_gallium_phosphide", 8220052, DULL, 1, of(new MaterialStack(Indium, 1), new MaterialStack(Gallium, 1), new MaterialStack(Phosphorus, 1)), DECOMPOSITION_BY_CENTRIFUGING | GENERATE_PLATE);
    public static DustMaterial FerriteMixture = new DustMaterial(423, "ferrite_mixture", 9803157, METALLIC, 1, of(new MaterialStack(Nickel, 1), new MaterialStack(Zinc, 1), new MaterialStack(Iron, 4)), DECOMPOSITION_BY_CENTRIFUGING);
    public static IngotMaterial NickelZincFerrite = new IngotMaterial(424, "nickel_zinc_ferrite", 3092271, METALLIC, 0, of(new MaterialStack(Nickel, 1), new MaterialStack(Zinc, 1), new MaterialStack(Iron, 4), new MaterialStack(Oxygen, 8)), EXT_METAL, null, 1500);
    public static FluidMaterial LeadZincSolution = new FluidMaterial(426, "lead_zinc_solution", 3213570, FLUID, of(new MaterialStack(Lead, 1), new MaterialStack(Silver, 1), new MaterialStack(Zinc, 1), new MaterialStack(Sulfur, 3), new MaterialStack(Water, 1)), DECOMPOSITION_BY_CENTRIFUGING);
    public static DustMaterial Magnesia = new DustMaterial(460, "magnesia", 8943736, SAND, 1, of(new MaterialStack(Magnesium, 1), new MaterialStack(Oxygen, 1)), 0);
    public static FluidMaterial HydrofluoricAcid = new FluidMaterial(404, "hydrofluoric_acid", 946055, FLUID, of(new MaterialStack(Hydrogen, 1), new MaterialStack(Fluorine, 1)), 0);
    public static FluidMaterial NitricOxide = new FluidMaterial(405, "nitric_oxide", 6790328, GAS, of(new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 1)), STATE_GAS);

    /**
     * Organic chemistry
     */
    public static FluidMaterial Chloroform = new FluidMaterial(383, "chloroform", 7351936, FLUID, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Chlorine, 3)), 0);
    public static FluidMaterial Cumene = new FluidMaterial(420, "cumene", 4924684, FLUID, of(new MaterialStack(Carbon, 9), new MaterialStack(Hydrogen, 12)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Tetrafluoroethylene = new FluidMaterial(427, "tetrafluoroethylene", 6776679, GAS, of(new MaterialStack(Carbon, 2), new MaterialStack(Fluorine, 4)), STATE_GAS | DISABLE_DECOMPOSITION);
    public static FluidMaterial Chloromethane = new FluidMaterial(450, "chloromethane", 10301057, GAS, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 3), new MaterialStack(Chlorine, 1)), STATE_GAS | DISABLE_DECOMPOSITION);
    public static FluidMaterial AllylChloride = new FluidMaterial(451, "allyl_chloride", 7450250, FLUID, of(new MaterialStack(Carbon, 2), new MaterialStack(Methane, 1), new MaterialStack(HydrochloricAcid, 1)), 0);
    public static FluidMaterial Isoprene = new FluidMaterial(452, "isoprene", 1907997, FLUID, of(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), 0);
    public static FluidMaterial Propane = new FluidMaterial(414, "propane", 12890952, GAS, of(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 8)), STATE_GAS);
    public static FluidMaterial Propene = new FluidMaterial(415, "propene", 12954956, GAS, of(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 6)), STATE_GAS);
    public static FluidMaterial Ethane = new FluidMaterial(416, "ethane", 10329540, GAS, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 6)), STATE_GAS);
    public static FluidMaterial Butene = new FluidMaterial(417, "butene", 10700561, GAS, of(new MaterialStack(Carbon, 4), new MaterialStack(Hydrogen, 8)), STATE_GAS);
    public static FluidMaterial Butane = new FluidMaterial(418, "butane", 9385508, GAS, of(new MaterialStack(Carbon, 4), new MaterialStack(Hydrogen, 10)), STATE_GAS);
    public static FluidMaterial CalciumAcetate = new FluidMaterial(419, "calcium_acetate", 11444113, FLUID, of(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 4), new MaterialStack(Oxygen, 4), new MaterialStack(Hydrogen, 6), new MaterialStack(Water, 1)), DISABLE_DECOMPOSITION);
    public static FluidMaterial VinylAcetate = new FluidMaterial(409, "vinyl_acetate", 13144428, FLUID, of(new MaterialStack(Carbon, 4), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 2)), DISABLE_DECOMPOSITION);
    public static IngotMaterial PolyphenyleneSulfide = new IngotMaterial(411, "polyphenylene_sulfide", 8743424, DULL, 1, of(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 4), new MaterialStack(Sulfur, 1)), DISABLE_DECOMPOSITION | EXT_METAL | GENERATE_FOIL);
    public static FluidMaterial MethylAcetate = new FluidMaterial(406, "methyl_acetate", 12427150, FLUID, of(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 2)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Ethenone = new FluidMaterial(407, "ethenone", 1776449, FLUID, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Tetranitromethane = new FluidMaterial(408, "tetranitromethane", 1715244, FLUID, of(new MaterialStack(Carbon, 1), new MaterialStack(Nitrogen, 4), new MaterialStack(Oxygen, 8)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Dimethylamine = new FluidMaterial(388, "dimethylamine", 4931417, GAS, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 7), new MaterialStack(Nitrogen, 1)), STATE_GAS | DISABLE_DECOMPOSITION);
    public static FluidMaterial Dimethylhydrazine = new FluidMaterial(389, "dimethylhidrazine", 1052748, FLUID, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 8), new MaterialStack(Nitrogen, 2)), DISABLE_DECOMPOSITION);
    public static FluidMaterial DinitrogenTetroxide = new FluidMaterial(390, "dinitrogen_tetroxide", 998766, GAS, of(new MaterialStack(Nitrogen, 2), new MaterialStack(Oxygen, 4)), STATE_GAS);
    public static IngotMaterial SiliconeRubber = new IngotMaterial(391, "silicon_rubber", 11316396, DULL, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 1), new MaterialStack(Silicon, 1)), GENERATE_PLATE | GENERATE_GEAR | GENERATE_RING | FLAMMABLE | NO_SMASHING | GENERATE_FOIL | DISABLE_DECOMPOSITION);
    public static DustMaterial Polydimethylsiloxane = new DustMaterial(392, "polydimethylsiloxane", 9211020, DULL, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 1), new MaterialStack(Silicon, 1)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Dimethyldichlorosilane = new FluidMaterial(393, "dimethyldichlorosilane", 4070471, FLUID, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 6), new MaterialStack(Chlorine, 2), new MaterialStack(Silicon, 1)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Styrene = new FluidMaterial(394, "styrene", 10722453, FLUID, of(new MaterialStack(Carbon, 8), new MaterialStack(Hydrogen, 8)), DISABLE_DECOMPOSITION);
    public static IngotMaterial Polystyrene = new IngotMaterial(395, "polystyrene", 8945785, DULL, 1, of(new MaterialStack(Carbon, 8), new MaterialStack(Hydrogen, 8)), DISABLE_DECOMPOSITION | GENERATE_FOIL);
    public static FluidMaterial Butadiene = new FluidMaterial(396, "butadiene", 11885072, GAS, of(new MaterialStack(Carbon, 4), new MaterialStack(Hydrogen, 6)), DISABLE_DECOMPOSITION);
    public static DustMaterial RawStyreneButadieneRubber = new DustMaterial(397, "raw_styrene_butadiene_rubber", 5192762, SAND, 1, of(new MaterialStack(Carbon, 8), new MaterialStack(Hydrogen, 8), new MaterialStack(Butadiene, 3)), DISABLE_DECOMPOSITION);
    public static IngotMaterial StyreneButadieneRubber = new IngotMaterial(398, "styrene_butadiene_rubber", 1906453, ROUGH, 1, of(new MaterialStack(Carbon, 8), new MaterialStack(Hydrogen, 8), new MaterialStack(Butadiene, 3)), GENERATE_PLATE | GENERATE_GEAR | GENERATE_RING | FLAMMABLE | NO_SMASHING | DISABLE_DECOMPOSITION);
    public static FluidMaterial Dichlorobenzene = new FluidMaterial(399, "dichlorobenzene", 868171, FLUID, of(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 4), new MaterialStack(Chlorine, 2)), DISABLE_DECOMPOSITION);
    public static FluidMaterial AceticAcid = new FluidMaterial(401, "acetic_acid", 10260096, FLUID, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 2)), DISABLE_DECOMPOSITION);
    public static FluidMaterial PolyvinylAcetate = new FluidMaterial(471, "polyvinyl_acetate", 13139532, FLUID, of(new MaterialStack(Carbon, 4), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 2)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Phenol = new FluidMaterial(468, "phenol", 6635559, FLUID, of(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 1)), DISABLE_DECOMPOSITION);
    public static FluidMaterial BisphenolA = new FluidMaterial(469, "bisphenol_a", 10848014, FLUID, of(new MaterialStack(Carbon, 15), new MaterialStack(Hydrogen, 16), new MaterialStack(Oxygen, 2)), DISABLE_DECOMPOSITION);
    public static IngotMaterial ReinforcedEpoxyResin = new IngotMaterial(470, "reinforced_epoxy_resin", 7491595, DULL, 1, of(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 1)), GENERATE_PLATE | DISABLE_DECOMPOSITION);
    public static IngotMaterial BorosilicateGlass = new IngotMaterial(364, "borosilicate_glass", 13424588, METALLIC, 1, of(new MaterialStack(Boron, 1), new MaterialStack(SiliconDioxide, 7)), DISABLE_DECOMPOSITION);
    public static IngotMaterial PolyvinylChloride = new IngotMaterial(965, "polyvinyl_chloride", 10069156, DULL, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 3), new MaterialStack(Chlorine, 1)), EXT_METAL | GENERATE_FOIL | DISABLE_DECOMPOSITION);
    public static FluidMaterial VinylChloride = new FluidMaterial(366, "vinyl_chloride", 11582395, GAS, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 3), new MaterialStack(Chlorine, 1)), STATE_GAS | DISABLE_DECOMPOSITION);
    public static FluidMaterial Ethylene = new FluidMaterial(367, "ethylene", 11382189, GAS, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 4)), STATE_GAS);
    public static FluidMaterial Benzene = new FluidMaterial(368, "benzene", 2039583, FLUID, of(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 6)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Acetone = new FluidMaterial(375, "acetone", 9342606, FLUID, of(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 1)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Glycerol = new FluidMaterial(378, "glycerol", 7384944, FLUID, of(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 8), new MaterialStack(Oxygen, 3)), 0);
    public static FluidMaterial Methanol = new FluidMaterial(379, "methanol", 8941584, FLUID, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 1)), 0);

    /**
     * Not possible to determine exact Components
     */
    public static FluidMaterial SaltWater = new FluidMaterial(428, "salt_water", 255, FLUID, of(new MaterialStack(Salt, 1), new MaterialStack(Water, 1)), DISABLE_DECOMPOSITION);
    public static RoughSolidMaterial Wood = new RoughSolidMaterial(196, "wood", 0x896727, WOOD, 0, of(), STD_SOLID | FLAMMABLE | NO_SMELTING | GENERATE_GEAR | GENERATE_LONG_ROD | GENERATE_FRAME, () -> OrePrefix.plank);
    public static FluidMaterial WoodGas = new FluidMaterial(370, "wood_gas", 0xB1A571, GAS, of(), STATE_GAS | DISABLE_DECOMPOSITION);
    public static FluidMaterial WoodVinegar = new FluidMaterial(371, "wood_vinegar", 0xA54B0F, FLUID, of(), 0);
    public static FluidMaterial WoodTar = new FluidMaterial(372, "wood_tar", 0x2D2118, FLUID, of(), 0);
    public static FluidMaterial CharcoalByproducts = new FluidMaterial(461, "charcoal_byproducts", 0x664027, FLUID, of(), 0);

    public static FluidMaterial Biomass = new FluidMaterial(315, "biomass", 0x00FF00, FLUID, of(), 0);
    public static FluidMaterial BioDiesel = new FluidMaterial(314, "bio_diesel", 0xC3690F, FLUID, of(), 0);
    public static FluidMaterial FermentedBiomass = new FluidMaterial(472, "fermented_biomass", 0x3F4B0D, FLUID, of(), 0);

    public static FluidMaterial Creosote = new FluidMaterial(316, "creosote", 0x804000, FLUID, of(), 0);
    public static FluidMaterial Ethanol = new FluidMaterial(317, "ethanol", 0xFF8000, FLUID, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 1)), DISABLE_DECOMPOSITION);
    public static FluidMaterial Fuel = new FluidMaterial(318, "fuel", 0xFFFF00, FLUID, of(), 0);
    public static FluidMaterial RocketFuel = new FluidMaterial(474, "rocket_fuel", 0xBDB78C, FLUID, of(), 0);
    public static FluidMaterial Glue = new FluidMaterial(319, "glue", 0xC8C400, FLUID, of(), 0);
    public static DustMaterial Gunpowder = new DustMaterial(320, "gunpowder", 0x808080, SAND, 0, of(), FLAMMABLE | EXPLOSIVE | NO_SMELTING | NO_SMASHING);
    public static FluidMaterial Lubricant = new FluidMaterial(321, "lubricant", 0xFFC400, FLUID, of(), 0);
    public static FluidMaterial McGuffium239 = new FluidMaterial(322, "mc_guffium239", 0xC83296, FLUID, of(), 0);
    public static FluidMaterial Oil = new FluidMaterial(323, "oil", 0x666666, FLUID, of(), 0);
    public static DustMaterial Oilsands = new DustMaterial(324, "oilsands", 0x0A0A0A, SAND, 1, of(new MaterialStack(Oil, 1L)), GENERATE_ORE);
    public static RoughSolidMaterial Paper = new RoughSolidMaterial(325, "paper", 0xFFFFFF, PAPER, 0, of(), GENERATE_PLATE | FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE | GENERATE_RING | EXCLUDE_PLATE_COMPRESSOR_RECIPE, () -> OrePrefix.plate);
    public static DustMaterial RareEarth = new DustMaterial(326, "rare_earth", 0x808064, ROUGH, 0, of(), 0);
    public static DustMaterial PlatinumGroupSludge = new DustMaterial(422, "platinum_group_sludge", 4864, ROUGH, 1, of(), DISABLE_DECOMPOSITION);
    public static FluidMaterial IndiumConcentrate = new FluidMaterial(425, "indium_concentrate", 205130, FLUID, of(), 0);
    public static FluidMaterial SeedOil = new FluidMaterial(327, "seed_oil", 0xC4FF00, FLUID, of(), 0);
    public static DustMaterial Stone = new DustMaterial(328, "stone", 0xCDCDCD, ROUGH, 1, of(), MORTAR_GRINDABLE | GENERATE_GEAR | GENERATE_PLATE | NO_SMASHING | NO_RECYCLING);
    public static FluidMaterial Lava = new FluidMaterial(329, "lava", 0xFF4000, FLUID, of(), 0);
    public static DustMaterial Glowstone = new DustMaterial(330, "glowstone", 0xFFFF00, SHINY, 1, of(), NO_SMASHING | SMELT_INTO_FLUID | GENERATE_PLATE | EXCLUDE_PLATE_COMPRESSOR_RECIPE);
    public static GemMaterial NetherStar = new GemMaterial(331, "nether_star", 0xFFFFFF, NETHERSTAR, 4, of(), STD_SOLID | GENERATE_LENSE | NO_SMASHING | NO_SMELTING);
    public static DustMaterial Endstone = new DustMaterial(332, "endstone", 0xFFFFFF, DULL, 1, of(), NO_SMASHING);
    public static DustMaterial Netherrack = new DustMaterial(333, "netherrack", 0xC80000, ROUGH, 1, of(), NO_SMASHING | FLAMMABLE);
    public static FluidMaterial DrillingFluid = new FluidMaterial(348, "drilling_fluid", 0xFFFFAA, FLUID, of(), 0);
    public static FluidMaterial ConstructionFoam = new FluidMaterial(347, "construction_foam", 0x333333, FLUID, of(), 0);

    /**
     * Oil refining sources & products
     */
    public static FluidMaterial HydroCrackedEthane = new FluidMaterial(429, "hydrocracked_ethane", 9868988, FLUID, of(), 0);
    public static FluidMaterial HydroCrackedEthylene = new FluidMaterial(430, "hydrocracked_ethylene", 10724256, GAS, of(), STATE_GAS);
    public static FluidMaterial HydroCrackedPropene = new FluidMaterial(431, "hydrocracked_propene", 12494144, FLUID, of(), 0);
    public static FluidMaterial HydroCrackedPropane = new FluidMaterial(432, "hydrocracked_propane", 12494144, FLUID, of(), 0);
    public static FluidMaterial HydroCrackedLightFuel = new FluidMaterial(433, "hydrocracked_light_fuel", 12037896, FLUID, of(), 0);
    public static FluidMaterial HydroCrackedButane = new FluidMaterial(434, "hydrocracked_butane", 8727576, FLUID, of(), 0);
    public static FluidMaterial HydroCrackedNaphtha = new FluidMaterial(435, "hydrocracked_naphtha", 12563976, FLUID, of(), 0);
    public static FluidMaterial HydroCrackedHeavyFuel = new FluidMaterial(436, "hydrocracked_heavy_fuel", 16776960, FLUID, of(), 0);
    public static FluidMaterial HydroCrackedGas = new FluidMaterial(437, "hydrocracked_gas", 11842740, FLUID, of(), 0);
    public static FluidMaterial HydroCrackedButene = new FluidMaterial(438, "hydrocracked_butene", 10042885, FLUID, of(), 0);
    public static FluidMaterial HydroCrackedButadiene = new FluidMaterial(439, "hydrocracked_butadiene", 11358723, FLUID, of(), 0);
    public static FluidMaterial SteamCrackedEthane = new FluidMaterial(440, "steamcracked_ethane", 9868988, FLUID, of(), 0);
    public static FluidMaterial SteamCrackedEthylene = new FluidMaterial(441, "steamcracked_ethylene", 10724256, GAS, of(), 0);
    public static FluidMaterial SteamCrackedPropene = new FluidMaterial(442, "steamcracked_propene", 12494144, FLUID, of(), 0);
    public static FluidMaterial SteamCrackedPropane = new FluidMaterial(443, "steamcracked_propane", 12494144, FLUID, of(), 0);
    public static FluidMaterial SteamCrackedButane = new FluidMaterial(444, "steamcracked_butane", 8727576, FLUID, of(), 0);
    public static FluidMaterial SteamCrackedNaphtha = new FluidMaterial(445, "steamcracked_naphtha", 12563976, FLUID, of(), 0);
    public static FluidMaterial SteamCrackedGas = new FluidMaterial(446, "steamcracked_gas", 11842740, FLUID, of(), 0);
    public static FluidMaterial SteamCrackedButene = new FluidMaterial(447, "steamcracked_butene", 10042885, FLUID, of(), 0);
    public static FluidMaterial SteamCrackedButadiene = new FluidMaterial(448, "steamcracked_butadiene", 11358723, FLUID, of(), 0);

    public static FluidMaterial OilHeavy = new FluidMaterial(165, "oil_heavy", 0x666666, FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial OilMedium = new FluidMaterial(166, "oil_medium", 0x666666, FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial OilLight = new FluidMaterial(167, "oil_light", 0x666666, FLUID, of(), GENERATE_FLUID_BLOCK);
    public static FluidMaterial NaturalGas = new FluidMaterial(168, "natural_gas", 0xFFFFFF, FLUID, of(), STATE_GAS | GENERATE_FLUID_BLOCK);
    public static FluidMaterial SulfuricGas = new FluidMaterial(169, "sulfuric_gas", 0xFFFFFF, FLUID, of(), STATE_GAS);
    public static FluidMaterial Gas = new FluidMaterial(170, "gas", 0xFFFFFF, FLUID, of(), STATE_GAS);
    public static FluidMaterial SulfuricNaphtha = new FluidMaterial(171, "sulfuric_naphtha", 0xFFFF00, FLUID, of(), 0);
    public static FluidMaterial SulfuricLightFuel = new FluidMaterial(172, "sulfuric_light_fuel", 0xFFFF00, FLUID, of(), 0);
    public static FluidMaterial SulfuricHeavyFuel = new FluidMaterial(173, "sulfuric_heavy_fuel", 0xFFFF00, FLUID, of(), 0);
    public static FluidMaterial Naphtha = new FluidMaterial(174, "naphtha", 0xFFFF00, FLUID, of(), 0);
    public static FluidMaterial LightFuel = new FluidMaterial(175, "light_fuel", 0xFFFF00, FLUID, of(), 0);
    public static FluidMaterial HeavyFuel = new FluidMaterial(176, "heavy_fuel", 0xFFFF00, FLUID, of(), 0);
    public static FluidMaterial LPG = new FluidMaterial(177, "lpg", 0xFFFF00, FLUID, of(), 0);
    public static FluidMaterial CrackedLightFuel = new FluidMaterial(464, "cracked_light_fuel", 0xFFFF00, FLUID, of(), 0);
    public static FluidMaterial CrackedHeavyFuel = new FluidMaterial(465, "cracked_heavy_fuel", 0xFFFF00, FLUID, of(), 0);
    public static FluidMaterial Toluene = new FluidMaterial(350, "toluene", 0xFFFFFF, FLUID, of(new MaterialStack(Carbon, 7), new MaterialStack(Hydrogen, 8)), DISABLE_DECOMPOSITION);

    /**
     * Second Degree Compounds
     */
    public static GemMaterial Glass = new GemMaterial(209, "glass", 0xFFFFFF, GLASS, 0, of(new MaterialStack(SiliconDioxide, 1)), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_RECYCLING | SMELT_INTO_FLUID | EXCLUDE_BLOCK_CRAFTING_RECIPES);
    public static DustMaterial Perlite = new DustMaterial(210, "perlite", 0x1E141E, DULL, 1, of(new MaterialStack(Obsidian, 2), new MaterialStack(Water, 1)), 0);
    public static DustMaterial Borax = new DustMaterial(313, "borax", 0xFFFFFF, SAND, 1, of(new MaterialStack(Sodium, 2), new MaterialStack(Boron, 4), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 7)), 0);
    public static GemMaterial Lignite = new GemMaterial(211, "lignite", 0x644646, LIGNITE, 0, of(new MaterialStack(Carbon, 2), new MaterialStack(Water, 4), new MaterialStack(DarkAsh, 1)), GENERATE_ORE | FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE);
    public static GemMaterial Olivine = new GemMaterial(212, "olivine", 0x66FF66, RUBY, 2, of(new MaterialStack(Magnesium, 2), new MaterialStack(Iron, 1), new MaterialStack(SiliconDioxide, 2)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.5F, 3.0f, 312);
    public static GemMaterial Opal = new GemMaterial(213, "opal", 0x0000FF, OPAL, 2, of(new MaterialStack(SiliconDioxide, 1)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.5F, 3.0f, 312);
    public static GemMaterial Amethyst = new GemMaterial(214, "amethyst", 0xD232D2, RUBY, 3, of(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Iron, 1)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.5F, 3.0f, 312);
    public static DustMaterial Redstone = new DustMaterial(215, "redstone", 0xC80000, ROUGH, 2, of(new MaterialStack(Silicon, 1), new MaterialStack(Pyrite, 5), new MaterialStack(Ruby, 1), new MaterialStack(Mercury, 3)), GENERATE_PLATE | GENERATE_ORE | NO_SMASHING | SMELT_INTO_FLUID | EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES);
    public static GemMaterial Lapis = new GemMaterial(216, "lapis", 0x4646DC, LAPIS, 1, of(new MaterialStack(Lazurite, 12), new MaterialStack(Sodalite, 2), new MaterialStack(Pyrite, 1), new MaterialStack(Calcite, 1)), STD_GEM | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | NO_WORKING | DECOMPOSITION_BY_ELECTROLYZING | EXCLUDE_BLOCK_CRAFTING_BY_HAND_RECIPES);
    public static DustMaterial Blaze = new DustMaterial(217, "blaze", 0xFFC800, DULL, 1, of(new MaterialStack(DarkAsh, 1), new MaterialStack(Sulfur, 1)), NO_SMELTING | SMELT_INTO_FLUID | MORTAR_GRINDABLE | BURNING);
    public static GemMaterial EnderPearl = new GemMaterial(218, "ender_pearl", 0x6CDCC8, GEM_VERTICAL, 1, of(new MaterialStack(Beryllium, 1), new MaterialStack(Potassium, 4), new MaterialStack(Nitrogen, 5)), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_SMELTING);
    public static GemMaterial EnderEye = new GemMaterial(219, "ender_eye", 0x66FF66, GEM_VERTICAL, 1, of(new MaterialStack(EnderPearl, 1), new MaterialStack(Blaze, 1)), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_SMELTING);
    public static RoughSolidMaterial Flint = new RoughSolidMaterial(220, "flint", 0x002040, FLINT, 1, of(new MaterialStack(SiliconDioxide, 1)), NO_SMASHING | MORTAR_GRINDABLE, () -> OrePrefix.gem);
    public static DustMaterial Diatomite = new DustMaterial(221, "diatomite", 0xE1E1E1, DULL, 1, of(new MaterialStack(Flint, 8), new MaterialStack(BandedIron, 1), new MaterialStack(Sapphire, 1)), 0);
    public static DustMaterial Niter = new DustMaterial(223, "niter", 0xFFC8C8, FLINT, 1, of(new MaterialStack(Saltpeter, 1)), NO_SMASHING | NO_SMELTING);
    public static DustMaterial Tantalite = new DustMaterial(224, "tantalite", 0x915028, METALLIC, 3, of(new MaterialStack(Manganese, 1), new MaterialStack(Tantalum, 2), new MaterialStack(Oxygen, 6)), GENERATE_ORE);
    public static DustMaterial HydratedCoal = new DustMaterial(225, "hydrated_coal", 0x464664, ROUGH, 1, of(new MaterialStack(Coal, 8), new MaterialStack(Water, 1)), 0);
    public static GemMaterial Apatite = new GemMaterial(226, "apatite", 0xC8C8FF, EMERALD, 1, of(new MaterialStack(Calcium, 5), new MaterialStack(Phosphate, 3), new MaterialStack(Chlorine, 1)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE);
    public static IngotMaterial SterlingSilver = new IngotMaterial(227, "sterling_silver", 0xFADCE1, SHINY, 2, of(new MaterialStack(Copper, 1), new MaterialStack(Silver, 4)), EXT2_METAL, null, 13.0F, 2.0f, 196, 1700);
    public static IngotMaterial RoseGold = new IngotMaterial(228, "rose_gold", 0xFFE61E, SHINY, 2, of(new MaterialStack(Copper, 1), new MaterialStack(Gold, 4)), EXT2_METAL, null, 14.0F, 2.0f, 152, 1600);
    public static IngotMaterial BlackBronze = new IngotMaterial(229, "black_bronze", 0x64327D, DULL, 2, of(new MaterialStack(Gold, 1), new MaterialStack(Silver, 1), new MaterialStack(Copper, 3)), EXT2_METAL, null, 12.0F, 2.0f, 256, 2000);
    public static IngotMaterial BismuthBronze = new IngotMaterial(230, "bismuth_bronze", 0x647D7D, DULL, 2, of(new MaterialStack(Bismuth, 1), new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)), EXT2_METAL, null, 8.0F, 3.0f, 256, 1100);
    public static IngotMaterial BlackSteel = new IngotMaterial(231, "black_steel", 0x646464, DULL, 2, of(new MaterialStack(Nickel, 1), new MaterialStack(BlackBronze, 1), new MaterialStack(Steel, 3)), EXT_METAL, null, 6.5F, 6.5f, 768, 1200);
    public static IngotMaterial RedSteel = new IngotMaterial(232, "red_steel", 0x8C6464, DULL, 2, of(new MaterialStack(SterlingSilver, 1), new MaterialStack(BismuthBronze, 1), new MaterialStack(Steel, 2), new MaterialStack(BlackSteel, 4)), EXT_METAL, null, 7.0F, 4.5f, 896, 1300);
    public static IngotMaterial BlueSteel = new IngotMaterial(233, "blue_steel", 0x64648C, DULL, 2, of(new MaterialStack(RoseGold, 1), new MaterialStack(Brass, 1), new MaterialStack(Steel, 2), new MaterialStack(BlackSteel, 4)), EXT_METAL | GENERATE_FRAME, null, 7.5F, 5.0f, 1024, 1400);
    public static IngotMaterial DamascusSteel = new IngotMaterial(234, "damascus_steel", 0x6E6E6E, METALLIC, 2, of(new MaterialStack(Steel, 1)), EXT_METAL, null, 8.0F, 5.0f, 1280, 1500);
    public static IngotMaterial TungstenSteel = new IngotMaterial(235, "tungsten_steel", 0x6464A0, METALLIC, 4, of(new MaterialStack(Steel, 1), new MaterialStack(Tungsten, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD | GENERATE_DENSE | GENERATE_FRAME, null, 8.0F, 4.0f, 2560, 3000);
    public static FluidMaterial NitroFuel = new FluidMaterial(236, "nitro_fuel", 0xC8FF00, FLUID, of(), FLAMMABLE | EXPLOSIVE | NO_SMELTING | NO_SMASHING);
    public static IngotMaterial RedAlloy = new IngotMaterial(237, "red_alloy", 0xC80000, DULL, 0, of(new MaterialStack(Copper, 1), new MaterialStack(Redstone, 1)), GENERATE_PLATE | GENERATE_FINE_WIRE);
    public static IngotMaterial CobaltBrass = new IngotMaterial(238, "cobalt_brass", 0xB4B4A0, METALLIC, 2, of(new MaterialStack(Brass, 7), new MaterialStack(Aluminium, 1), new MaterialStack(Cobalt, 1)), EXT2_METAL, null, 8.0F, 2.0f, 256);
    public static DustMaterial Phosphor = new DustMaterial(239, "phosphor", 0xFFFF00, FLINT, 2, of(new MaterialStack(Calcium, 3), new MaterialStack(Phosphate, 2)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | FLAMMABLE | EXPLOSIVE);
    public static DustMaterial Basalt = new DustMaterial(240, "basalt", 0x1E1414, ROUGH, 1, of(new MaterialStack(Olivine, 1), new MaterialStack(Calcite, 3), new MaterialStack(Flint, 8), new MaterialStack(DarkAsh, 4)), NO_SMASHING);
    public static DustMaterial Andesite = new DustMaterial(241, "andesite", 0xBEBEBE, ROUGH, 2, of(), NO_SMASHING);
    public static DustMaterial Diorite = new DustMaterial(242, "diorite", 0xFFFFFF, ROUGH, 2, of(), NO_SMASHING);
    public static DustMaterial Granite = new DustMaterial(449, "granite", 0xCFA18C, ROUGH, 2, of(), NO_SMASHING);
    public static GemMaterial GarnetRed = new GemMaterial(243, "garnet_red", 0xC85050, RUBY, 2, of(new MaterialStack(Pyrope, 3), new MaterialStack(Almandine, 5), new MaterialStack(Spessartine, 8)), STD_SOLID | GENERATE_LENSE | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT | GENERATE_ORE, null, 7.5F, 3.0f, 156);
    public static GemMaterial GarnetYellow = new GemMaterial(244, "garnet_yellow", 0xC8C850, RUBY, 2, of(new MaterialStack(Andradite, 5), new MaterialStack(Grossular, 8), new MaterialStack(Uvarovite, 3)), STD_SOLID | GENERATE_LENSE | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT | GENERATE_ORE, null, 7.5F, 3.0f, 156);
    public static DustMaterial Marble = new DustMaterial(245, "marble", 0xC8C8C8, FINE, 1, of(new MaterialStack(Magnesium, 1), new MaterialStack(Calcite, 7)), NO_SMASHING);
    public static DustMaterial Sugar = new DustMaterial(246, "sugar", 0xFAFAFA, SAND, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 25)), 0);
    public static GemMaterial Vinteum = new GemMaterial(247, "vinteum", 0x64C8FF, EMERALD, 3, of(), STD_GEM | NO_SMASHING | NO_SMELTING, 12.0F, 3.0f, 128);
    public static DustMaterial Redrock = new DustMaterial(248, "redrock", 0xFF5032, ROUGH, 1, of(new MaterialStack(Calcite, 2), new MaterialStack(Flint, 1), new MaterialStack(Clay, 1)), NO_SMASHING);
    public static DustMaterial PotassiumFeldspar = new DustMaterial(249, "potassium_feldspar", 0x782828, FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 8)), 0);
    public static DustMaterial Biotite = new DustMaterial(250, "biotite", 0x141E14, METALLIC, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 3), new MaterialStack(Aluminium, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 10)), 0);
    public static DustMaterial GraniteBlack = new DustMaterial(251, "granite_black", 0x0A0A0A, ROUGH, 3, of(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Biotite, 1)), NO_SMASHING);
    public static DustMaterial GraniteRed = new DustMaterial(252, "granite_red", 0xFF0080, ROUGH, 3, of(new MaterialStack(Aluminium, 2), new MaterialStack(PotassiumFeldspar, 1), new MaterialStack(Oxygen, 3)), NO_SMASHING);
    public static DustMaterial Chrysotile = new DustMaterial(253, "chrysotile", 0x6E8C6E, ROUGH, 2, of(new MaterialStack(Asbestos, 1)), 0);
    public static DustMaterial Realgar = new DustMaterial(254, "realgar", 0x8C6464, DULL, 2, of(new MaterialStack(Arsenic, 4), new MaterialStack(Sulfur, 4)), 0);
    public static DustMaterial VanadiumMagnetite = new DustMaterial(255, "vanadium_magnetite", 0x23233C, METALLIC, 2, of(new MaterialStack(Magnetite, 1), new MaterialStack(Vanadium, 1)), GENERATE_ORE);
    public static DustMaterial BasalticMineralSand = new DustMaterial(266, "basaltic_mineral_sand", 0x283228, SAND, 1, of(new MaterialStack(Magnetite, 1), new MaterialStack(Basalt, 1)), INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial GraniticMineralSand = new DustMaterial(267, "granitic_mineral_sand", 0x283C3C, SAND, 1, of(new MaterialStack(Magnetite, 1), new MaterialStack(GraniteBlack, 1)), INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial GarnetSand = new DustMaterial(268, "garnet_sand", 0xC86400, SAND, 1, of(new MaterialStack(GarnetRed, 1), new MaterialStack(GarnetYellow, 1)), 0);
    public static DustMaterial QuartzSand = new DustMaterial(269, "quartz_sand", 0xC8C8C8, SAND, 1, of(new MaterialStack(CertusQuartz, 1), new MaterialStack(Quartzite, 1)), 0);
    public static DustMaterial Bastnasite = new DustMaterial(270, "bastnasite", 0xC86E2D, FINE, 2, of(new MaterialStack(Cerium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Fluorine, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static DustMaterial Pentlandite = new DustMaterial(271, "pentlandite", 0xA59605, ROUGH, 2, of(new MaterialStack(Nickel, 9), new MaterialStack(Sulfur, 8)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial Spodumene = new DustMaterial(272, "spodumene", 0xBEAAAA, ROUGH, 2, of(new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 6)), GENERATE_ORE);
    public static DustMaterial Pollucite = new DustMaterial(273, "pollucite", 0xF0D2D2, ROUGH, 2, of(new MaterialStack(Caesium, 2), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 4), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 12)), 0);
    public static DustMaterial Lepidolite = new DustMaterial(274, "lepidolite", 0xF0328C, FINE, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Lithium, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10)), GENERATE_ORE);
    public static DustMaterial Glauconite = new DustMaterial(275, "glauconite", 0x82B43C, DULL, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 2), new MaterialStack(Aluminium, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial GlauconiteSand = new DustMaterial(276, "glauconite_sand", 0x82B43C, SAND, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 2), new MaterialStack(Aluminium, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)), 0);
    public static DustMaterial Vermiculite = new DustMaterial(277, "vermiculite", 0xC8B40F, ROUGH, 2, of(new MaterialStack(Iron, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Water, 4), new MaterialStack(Oxygen, 12)), 0);
    public static DustMaterial Bentonite = new DustMaterial(278, "bentonite", 0xF5D7D2, ROUGH, 2, of(new MaterialStack(Sodium, 1), new MaterialStack(Magnesium, 6), new MaterialStack(Silicon, 12), new MaterialStack(Hydrogen, 4), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 36)), GENERATE_ORE);
    public static DustMaterial FullersEarth = new DustMaterial(279, "fullers_earth", 0xA0A078, ROUGH, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 4), new MaterialStack(Oxygen, 11)), 0);
    public static DustMaterial Pitchblende = new DustMaterial(280, "pitchblende", 0xC8D200, ROUGH, 3, of(new MaterialStack(Uraninite, 3), new MaterialStack(Thorium, 1), new MaterialStack(Lead, 1)), GENERATE_ORE);
    public static GemMaterial Monazite = new GemMaterial(281, "monazite", 0x324632, GEM_VERTICAL, 1, of(new MaterialStack(RareEarth, 1), new MaterialStack(Phosphate, 1)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE);
    public static DustMaterial Malachite = new DustMaterial(282, "malachite", 0x055F05, ROUGH, 2, of(new MaterialStack(Copper, 2), new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 5)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial Mirabilite = new DustMaterial(283, "mirabilite", 0xF0FAD2, ROUGH, 2, of(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 4)), 0);
    public static DustMaterial Mica = new DustMaterial(284, "mica", 0xC3C3CD, FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10)), 0);
    public static DustMaterial Trona = new DustMaterial(285, "trona", 0x87875F, ROUGH, 1, of(new MaterialStack(Sodium, 3), new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 6)), 0);
    public static DustMaterial Barite = new DustMaterial(286, "barite", 0xE6EBFF, DULL, 2, of(new MaterialStack(Barium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial Gypsum = new DustMaterial(287, "gypsum", 0xE6E6FA, FINE, 1, of(new MaterialStack(Calcium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 4)), 0);
    public static DustMaterial Alunite = new DustMaterial(288, "alunite", 0xE1B441, METALLIC, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 14)), 0);
    public static DustMaterial Dolomite = new DustMaterial(289, "dolomite", 0xE1CDCD, FLINT, 1, of(new MaterialStack(Calcium, 1), new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Oxygen, 6)), 0);
    public static DustMaterial Wollastonite = new DustMaterial(290, "wollastonite", 0xF0F0F0, ROUGH, 2, of(new MaterialStack(Calcium, 1), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 3)), 0);
    public static DustMaterial Zeolite = new DustMaterial(291, "zeolite", 0xF0E6E6, ROUGH, 2, of(new MaterialStack(Sodium, 1), new MaterialStack(Calcium, 4), new MaterialStack(Silicon, 27), new MaterialStack(Aluminium, 9), new MaterialStack(Water, 28), new MaterialStack(Oxygen, 72)), DISABLE_DECOMPOSITION);
    public static DustMaterial Kyanite = new DustMaterial(292, "kyanite", 0x6E6EFA, FLINT, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 5)), 0);
    public static DustMaterial Kaolinite = new DustMaterial(293, "kaolinite", 0xF5EBEB, DULL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9)), 0);
    public static DustMaterial Talc = new DustMaterial(294, "talc", 0x5AB45A, FINE, 2, of(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Soapstone = new DustMaterial(295, "soapstone", 0x5F915F, ROUGH, 1, of(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Concrete = new DustMaterial(296, "concrete", 0x646464, ROUGH, 1, of(new MaterialStack(Stone, 1)), NO_SMASHING | SMELT_INTO_FLUID);
    public static IngotMaterial IronMagnetic = new IngotMaterial(297, "iron_magnetic", 0xC8C8C8, MAGNETIC, 2, of(new MaterialStack(Iron, 1)), EXT2_METAL | MORTAR_GRINDABLE);
    public static IngotMaterial SteelMagnetic = new IngotMaterial(298, "steel_magnetic", 0x808080, MAGNETIC, 2, of(new MaterialStack(Steel, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | MORTAR_GRINDABLE, null, 1000);
    public static IngotMaterial NeodymiumMagnetic = new IngotMaterial(299, "neodymium_magnetic", 0x646464, MAGNETIC, 2, of(new MaterialStack(Neodymium, 1)), EXT2_METAL | GENERATE_LONG_ROD, null, 1297);
    public static IngotMaterial TungstenCarbide = new IngotMaterial(300, "tungsten_carbide", 0x330066, METALLIC, 4, of(new MaterialStack(Tungsten, 1), new MaterialStack(Carbon, 1)), EXT2_METAL, null, 12.0F, 4.0f, 1280, 2460);
    public static IngotMaterial VanadiumSteel = new IngotMaterial(301, "vanadium_steel", 0xC0C0C0, METALLIC, 3, of(new MaterialStack(Vanadium, 1), new MaterialStack(Chrome, 1), new MaterialStack(Steel, 7)), EXT2_METAL, null, 7.0F, 3.0f, 1920, 1453);
    public static IngotMaterial HSSG = new IngotMaterial(302, "hssg", 0x999900, METALLIC, 3, of(new MaterialStack(TungstenSteel, 5), new MaterialStack(Chrome, 1), new MaterialStack(Molybdenum, 2), new MaterialStack(Vanadium, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD | GENERATE_FRAME, null, 10.0F, 5.5f, 4000, 4500);
    public static IngotMaterial HSSE = new IngotMaterial(303, "hsse", 0x336600, METALLIC, 4, of(new MaterialStack(HSSG, 6), new MaterialStack(Cobalt, 1), new MaterialStack(Manganese, 1), new MaterialStack(Silicon, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD | GENERATE_FRAME, null, 10.0F, 8.0f, 5120, 5400);
    public static IngotMaterial HSSS = new IngotMaterial(304, "hsss", 0x660033, METALLIC, 4, of(new MaterialStack(HSSG, 6), new MaterialStack(Iridium, 2), new MaterialStack(Osmium, 1)), EXT2_METAL | GENERATE_GEAR, null, 15.0F, 7.0f, 3000, 5400);
    /**
     * Clear matter materials
     */
    public static FluidMaterial UUAmplifier = new FluidMaterial(305, "uuamplifier", 0xAA00AA, FLUID, of(), 0);
    public static FluidMaterial UUMatter = new FluidMaterial(306, "uumatter", 0x770077, FLUID, of(), 0);

    /**
     * Stargate materials
     */
    public static IngotMaterial Naquadah = new IngotMaterial(307, "naquadah", 0x323232, METALLIC, 4, of(), EXT_METAL | GENERATE_ORE, Element.Nq, 6.0F, 4.0f, 1280, 5400);
    public static IngotMaterial NaquadahAlloy = new IngotMaterial(308, "naquadah_alloy", 0x282828, METALLIC, 5, of(new MaterialStack(Naquadah, 1), new MaterialStack(Osmiridium, 1)), EXT2_METAL, null, 8.0F, 5.0f, 5120, 7200);
    public static IngotMaterial NaquadahEnriched = new IngotMaterial(309, "naquadah_enriched", 0x282828, METALLIC, 4, of(), EXT_METAL | GENERATE_ORE, null, 6.0F, 4.0f, 1280, 4500);
    public static IngotMaterial Naquadria = new IngotMaterial(310, "naquadria", 0x1E1E1E, SHINY, 3, of(), EXT_METAL, Element.Nq, 9000);
    public static IngotMaterial Tritanium = new IngotMaterial(311, "tritanium", 0xFFFFFF, METALLIC, 6, of(), EXT_METAL, Element.Tr, 20.0F, 6.0f, 10240);
    public static IngotMaterial Duranium = new IngotMaterial(312, "duranium", 0xFFFFFF, METALLIC, 5, of(), EXT_METAL, Element.Dr, 16.0F, 5.0f, 5120);

    /**
     * Actual food
     */
    public static FluidMaterial Milk = new FluidMaterial(339, "milk", 0xFEFEFE, FINE, of(), 0);
    public static FluidMaterial Honey = new FluidMaterial(341, "honey", 0xD2C800, FLUID, of(), 0);
    public static FluidMaterial Juice = new FluidMaterial(473, "juice", 0xA8C972, FLUID, of(), 0);
    public static DustMaterial Cocoa = new DustMaterial(343, "cocoa", 0xBE5F00, ROUGH, 0, of(), 0);
    public static DustMaterial Wheat = new DustMaterial(345, "wheat", 0xFFFFC4, FINE, 0, of(), 0);

    static {
        for (DustMaterial dustMaterial : new DustMaterial[]{Bastnasite, Monazite}) {
            dustMaterial.separatedOnto = Neodymium;
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Magnetite, VanadiumMagnetite, BasalticMineralSand, GraniticMineralSand}) {
            dustMaterial.separatedOnto = Gold;
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{YellowLimonite, BrownLimonite, Pyrite, BandedIron, Nickel, Vermiculite, Glauconite, GlauconiteSand, Pentlandite, Tin, Antimony, Ilmenite, Manganese, Chrome, Chromite, Andradite}) {
            dustMaterial.separatedOnto = Iron;
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Pyrite, YellowLimonite, BasalticMineralSand, GraniticMineralSand}) {
            dustMaterial.addFlag(BLAST_FURNACE_CALCITE_DOUBLE);
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Iron, PigIron, WroughtIron, BrownLimonite}) {
            dustMaterial.addFlag(BLAST_FURNACE_CALCITE_TRIPLE);
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Gold, Silver, Osmium, Platinum, Cooperite, Chalcopyrite, Bornite}) {
            dustMaterial.washedIn = Mercury;
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Zinc, Nickel, Copper, Cobalt, Cobaltite, Tetrahedrite, Sphalerite}) {
            dustMaterial.washedIn = SodiumPersulfate;
        }

        Neodymium.magneticMaterial = NeodymiumMagnetic;
        Steel.magneticMaterial = SteelMagnetic;
        Iron.magneticMaterial = IronMagnetic;

        NeodymiumMagnetic.setSmeltingInto(Neodymium);
        NeodymiumMagnetic.setArcSmeltingInto(Neodymium);
        NeodymiumMagnetic.setMaceratingInto(Neodymium);

        SteelMagnetic.setSmeltingInto(Steel);
        IronMagnetic.setArcSmeltingInto(Steel);
        IronMagnetic.setMaceratingInto(Steel);

        IronMagnetic.setSmeltingInto(Iron);
        IronMagnetic.setArcSmeltingInto(WroughtIron);
        IronMagnetic.setMaceratingInto(Iron);

        Iron.setArcSmeltingInto(WroughtIron);
        Copper.setArcSmeltingInto(AnnealedCopper);
        Tetrahedrite.setDirectSmelting(Copper);
        Malachite.setDirectSmelting(Copper);
        Chalcopyrite.setDirectSmelting(Copper);
        Tenorite.setDirectSmelting(Copper);
        Bornite.setDirectSmelting(Copper);
        Chalcocite.setDirectSmelting(Copper);
        Cuprite.setDirectSmelting(Copper);
        Pentlandite.setDirectSmelting(Nickel);
        Sphalerite.setDirectSmelting(Zinc);
        Pyrite.setDirectSmelting(Iron);
        Magnetite.setDirectSmelting(Iron);
        YellowLimonite.setDirectSmelting(Iron);
        BrownLimonite.setDirectSmelting(Iron);
        BandedIron.setDirectSmelting(Iron);
        Cassiterite.setDirectSmelting(Tin);
        CassiteriteSand.setDirectSmelting(Tin);
        Garnierite.setDirectSmelting(Nickel);
        Cobaltite.setDirectSmelting(Cobalt);
        Stibnite.setDirectSmelting(Antimony);
        Cooperite.setDirectSmelting(Platinum);
        Pyrolusite.setDirectSmelting(Manganese);
        Magnesite.setDirectSmelting(Magnesium);
        Molybdenite.setDirectSmelting(Molybdenum);

        Salt.setOreMultiplier(3);
        RockSalt.setOreMultiplier(3);
        Lepidolite.setOreMultiplier(5);

        Spodumene.setOreMultiplier(2);
        Spessartine.setOreMultiplier(2);
        Soapstone.setOreMultiplier(3);

        Almandine.setOreMultiplier(6);
        Grossular.setOreMultiplier(6);
        Bentonite.setOreMultiplier(7);
        Pyrope.setOreMultiplier(4);

        GarnetYellow.setOreMultiplier(4);
        GarnetRed.setOreMultiplier(4);
        Olivine.setOreMultiplier(2);
        Topaz.setOreMultiplier(2);

        Bastnasite.setOreMultiplier(2);
        Tennantite.setOreMultiplier(2);
        Enargite.setOreMultiplier(2);
        Tantalite.setOreMultiplier(2);
        Tanzanite.setOreMultiplier(2);
        Pitchblende.setOreMultiplier(2);

        Scheelite.setOreMultiplier(2);
        Tungstate.setOreMultiplier(2);
        Ilmenite.setOreMultiplier(3);
        Bauxite.setOreMultiplier(3);
        Rutile.setOreMultiplier(3);

        Cassiterite.setOreMultiplier(2);
        CassiteriteSand.setOreMultiplier(2);
        NetherQuartz.setOreMultiplier(2);
        CertusQuartz.setOreMultiplier(2);
        Quartzite.setOreMultiplier(2);

        Phosphor.setOreMultiplier(3);
        Saltpeter.setOreMultiplier(4);
        Apatite.setOreMultiplier(5);
        Apatite.setByProductMultiplier(2);
        Redstone.setOreMultiplier(6);

        Lapis.setOreMultiplier(6);
        Lapis.setByProductMultiplier(4);
        Sodalite.setOreMultiplier(6);
        Sodalite.setByProductMultiplier(4);
        Lazurite.setOreMultiplier(6);
        Lazurite.setByProductMultiplier(4);
        Monazite.setOreMultiplier(8);
        Monazite.setByProductMultiplier(2);

        Coal.setBurnTime(1600); //default coal burn time in vanilla
        Charcoal.setBurnTime(1600); //default coal burn time in vanilla
        Lignite.setBurnTime(1200); //2/3 of burn time of coal
        Coke.setBurnTime(3200); //2x burn time of coal
        Wood.setBurnTime(300); //default wood burn time in vanilla

        Tenorite.addOreByProducts(Iron, Manganese, Malachite);
        Bornite.addOreByProducts(Pyrite, Cobalt, Cadmium, Gold);
        Chalcocite.addOreByProducts(Sulfur, Lead, Silver);
        Cuprite.addOreByProducts(Iron, Antimony, Malachite);
        Enargite.addOreByProducts(Pyrite, Zinc, Quartzite);
        Tennantite.addOreByProducts(Iron, Antimony, Zinc);

        Chalcopyrite.addOreByProducts(Pyrite, Cobalt, Cadmium, Gold);
        Sphalerite.addOreByProducts(GarnetYellow, Cadmium, Gallium, Zinc);
        GlauconiteSand.addOreByProducts(Sodium, Aluminium, Iron);
        Glauconite.addOreByProducts(Sodium, Aluminium, Iron);
        Vermiculite.addOreByProducts(Iron, Aluminium, Magnesium);
        FullersEarth.addOreByProducts(Aluminium, Silicon, Magnesium);
        Bentonite.addOreByProducts(Aluminium, Calcium, Magnesium);
        Uraninite.addOreByProducts(Uranium, Thorium, Uranium235);
        Pitchblende.addOreByProducts(Thorium, Uranium, Lead);
        Galena.addOreByProducts(Sulfur, Silver, Lead);
        Lapis.addOreByProducts(Lazurite, Sodalite, Pyrite);
        Pyrite.addOreByProducts(Sulfur, Phosphor, Iron);
        Copper.addOreByProducts(Cobalt, Gold, Nickel);
        Nickel.addOreByProducts(Cobalt, Platinum, Iron);
        GarnetRed.addOreByProducts(Spessartine, Pyrope, Almandine);
        GarnetYellow.addOreByProducts(Andradite, Grossular, Uvarovite);
        Cooperite.addOreByProducts(Palladium, Nickel, Iridium);
        Cinnabar.addOreByProducts(Redstone, Sulfur, Glowstone);
        Tantalite.addOreByProducts(Manganese, Niobium, Tantalum);
        Pollucite.addOreByProducts(Caesium, Aluminium, Rubidium);
        Chrysotile.addOreByProducts(Asbestos, Silicon, Magnesium);
        Asbestos.addOreByProducts(Asbestos, Silicon, Magnesium);
        Pentlandite.addOreByProducts(Iron, Sulfur, Cobalt);
        Uranium.addOreByProducts(Lead, Uranium235, Thorium);
        Scheelite.addOreByProducts(Manganese, Molybdenum, Calcium);
        Tungstate.addOreByProducts(Manganese, Silver, Lithium);
        Bauxite.addOreByProducts(Grossular, Rutile, Gallium);
        QuartzSand.addOreByProducts(CertusQuartz, Quartzite, Barite);
        Quartzite.addOreByProducts(CertusQuartz, Barite);
        CertusQuartz.addOreByProducts(Quartzite, Barite);
        Redstone.addOreByProducts(Cinnabar, RareEarth, Glowstone);
        Monazite.addOreByProducts(Thorium, Neodymium, RareEarth);
        Malachite.addOreByProducts(Copper, BrownLimonite, Calcite);
        YellowLimonite.addOreByProducts(Nickel, BrownLimonite, Cobalt);
        BrownLimonite.addOreByProducts(Malachite, YellowLimonite);
        Neodymium.addOreByProducts(Monazite, RareEarth);
        Bastnasite.addOreByProducts(Neodymium, RareEarth);
        Glowstone.addOreByProducts(Redstone, Gold);
        Zinc.addOreByProducts(Tin, Gallium);
        Tungsten.addOreByProducts(Manganese, Molybdenum);
        Diatomite.addOreByProducts(BandedIron, Sapphire);
        Iron.addOreByProducts(Nickel, Tin);
        Lepidolite.addOreByProducts(Lithium, Caesium);
        Gold.addOreByProducts(Copper, Nickel);
        Tin.addOreByProducts(Iron, Zinc);
        Antimony.addOreByProducts(Zinc, Iron);
        Silver.addOreByProducts(Lead, Sulfur);
        Lead.addOreByProducts(Silver, Sulfur);
        Thorium.addOreByProducts(Uranium, Lead);
        Plutonium.addOreByProducts(Uranium, Lead);
        Electrum.addOreByProducts(Gold, Silver);
        Bronze.addOreByProducts(Copper, Tin);
        Brass.addOreByProducts(Copper, Zinc);
        Coal.addOreByProducts(Lignite, Thorium);
        Ilmenite.addOreByProducts(Iron, Rutile);
        Manganese.addOreByProducts(Chrome, Iron);
        Sapphire.addOreByProducts(Aluminium, GreenSapphire);
        GreenSapphire.addOreByProducts(Aluminium, Sapphire);
        Platinum.addOreByProducts(Nickel, Iridium);
        Emerald.addOreByProducts(Beryllium, Aluminium);
        Olivine.addOreByProducts(Pyrope, Magnesium, Manganese);
        Chrome.addOreByProducts(Iron, Magnesium);
        Chromite.addOreByProducts(Iron, Magnesium);
        Tetrahedrite.addOreByProducts(Antimony, Zinc);
        GarnetSand.addOreByProducts(GarnetRed, GarnetYellow);
        Magnetite.addOreByProducts(Iron, Gold);
        GraniticMineralSand.addOreByProducts(GraniteBlack, Magnetite);
        BasalticMineralSand.addOreByProducts(Basalt, Magnetite);
        Basalt.addOreByProducts(Olivine, DarkAsh);
        VanadiumMagnetite.addOreByProducts(Magnetite, Vanadium);
        Lazurite.addOreByProducts(Sodalite, Lapis);
        Sodalite.addOreByProducts(Lazurite, Lapis);
        Spodumene.addOreByProducts(Aluminium, Lithium);
        Ruby.addOreByProducts(Chrome, GarnetRed);
        Phosphor.addOreByProducts(Apatite, Phosphate);
        Iridium.addOreByProducts(Platinum, Osmium);
        Pyrope.addOreByProducts(GarnetRed, Magnesium);
        Almandine.addOreByProducts(GarnetRed, Aluminium);
        Spessartine.addOreByProducts(GarnetRed, Manganese);
        Andradite.addOreByProducts(GarnetYellow, Iron);
        Grossular.addOreByProducts(GarnetYellow, Calcium);
        Uvarovite.addOreByProducts(GarnetYellow, Chrome);
        Calcite.addOreByProducts(Andradite, Malachite);
        NaquadahEnriched.addOreByProducts(Naquadah, Naquadria);
        Naquadah.addOreByProducts(NaquadahEnriched);
        Pyrolusite.addOreByProducts(Manganese);
        Molybdenite.addOreByProducts(Molybdenum);
        Stibnite.addOreByProducts(Antimony);
        Garnierite.addOreByProducts(Nickel);
        Lignite.addOreByProducts(Coal);
        Diamond.addOreByProducts(Graphite);
        Beryllium.addOreByProducts(Emerald);
        Apatite.addOreByProducts(Phosphor);
        Magnesite.addOreByProducts(Magnesium);
        NetherQuartz.addOreByProducts(Netherrack);
        PigIron.addOreByProducts(Iron);
        Steel.addOreByProducts(Iron);
        Graphite.addOreByProducts(Carbon);
        Netherrack.addOreByProducts(Sulfur);
        Flint.addOreByProducts(Obsidian);
        Cobaltite.addOreByProducts(Cobalt);
        Cobalt.addOreByProducts(Cobaltite);
        Sulfur.addOreByProducts(Sulfur);
        Saltpeter.addOreByProducts(Saltpeter);
        Endstone.addOreByProducts(Helium3);
        Osmium.addOreByProducts(Iridium);
        Magnesium.addOreByProducts(Olivine);
        Aluminium.addOreByProducts(Bauxite);
        Titanium.addOreByProducts(Almandine);
        Obsidian.addOreByProducts(Olivine);
        Ash.addOreByProducts(Carbon);
        DarkAsh.addOreByProducts(Carbon);
        Redrock.addOreByProducts(Clay);
        Marble.addOreByProducts(Calcite);
        Clay.addOreByProducts(Clay);
        Cassiterite.addOreByProducts(Tin, Bismuth);
        CassiteriteSand.addOreByProducts(Tin);
        GraniteBlack.addOreByProducts(Biotite);
        GraniteRed.addOreByProducts(PotassiumFeldspar);
        Phosphate.addOreByProducts(Phosphorus);
        Phosphorus.addOreByProducts(Phosphate);
        Tanzanite.addOreByProducts(Opal);
        Opal.addOreByProducts(Tanzanite);
        Amethyst.addOreByProducts(Amethyst);
        Topaz.addOreByProducts(BlueTopaz);
        BlueTopaz.addOreByProducts(Topaz);
        Niter.addOreByProducts(Saltpeter);
        Vinteum.addOreByProducts(Vinteum);
        Lithium.addOreByProducts(Lithium);
        Silicon.addOreByProducts(SiliconDioxide);
        Salt.addOreByProducts(RockSalt, Borax);
        RockSalt.addOreByProducts(Salt, Borax);
        Andesite.addOreByProducts(Basalt);
        Diorite.addOreByProducts(NetherQuartz);
        Lepidolite.addOreByProducts(Boron);

        Vinteum.addEnchantmentForTools(Enchantments.FORTUNE, 2);
        BlackBronze.addEnchantmentForTools(Enchantments.SMITE, 2);
        RoseGold.addEnchantmentForTools(Enchantments.SMITE, 4);
        Invar.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 3);
        BismuthBronze.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 5);

        RedAlloy.setCableProperties(GTValues.V[0], 1, 0);
        Tin.setCableProperties(GTValues.V[1], 1, 1);
        Copper.setCableProperties(GTValues.V[2], 1, 2);

        Cobalt.setCableProperties(GTValues.V[1], 2, 2);
        Lead.setCableProperties(GTValues.V[1], 2, 2);
        Tin.setCableProperties(GTValues.V[1], 1, 1);
        Zinc.setCableProperties(GTValues.V[1], 1, 1);
        SolderingAlloy.setCableProperties(GTValues.V[1], 1, 1);

        Iron.setCableProperties(GTValues.V[2], 2, 3);
        Nickel.setCableProperties(GTValues.V[2], 3, 3);
        Cupronickel.setCableProperties(GTValues.V[2], 2, 3);
        Copper.setCableProperties(GTValues.V[2], 1, 2);
        AnnealedCopper.setCableProperties(GTValues.V[2], 1, 1);

        Kanthal.setCableProperties(GTValues.V[3], 4, 3);
        Gold.setCableProperties(GTValues.V[3], 2, 2);
        Electrum.setCableProperties(GTValues.V[3], 3, 2);
        Silver.setCableProperties(GTValues.V[3], 1, 1);

        Nichrome.setCableProperties(GTValues.V[4], 4, 4);
        Steel.setCableProperties(GTValues.V[4], 2, 2);
        BlackSteel.setCableProperties(GTValues.V[4], 3, 2);
        Titanium.setCableProperties(GTValues.V[4], 4, 2);
        Aluminium.setCableProperties(GTValues.V[4], 1, 1);

        Graphene.setCableProperties(GTValues.V[5], 1, 1);
        Osmium.setCableProperties(GTValues.V[5], 4, 2);
        Platinum.setCableProperties(GTValues.V[5], 2, 1);
        Palladium.setCableProperties(GTValues.V[5], 2, 1);
        TungstenSteel.setCableProperties(GTValues.V[5], 3, 2);
        Tungsten.setCableProperties(GTValues.V[5], 2, 2);

        HSSG.setCableProperties(GTValues.V[6], 4, 2);
        NiobiumTitanium.setCableProperties(GTValues.V[6], 4, 2);
        VanadiumGallium.setCableProperties(GTValues.V[6], 4, 2);
        YttriumBariumCuprate.setCableProperties(GTValues.V[6], 4, 4);

        Naquadah.setCableProperties(GTValues.V[7], 2, 2);

        NaquadahAlloy.setCableProperties(GTValues.V[8], 2, 4);
        Duranium.setCableProperties(GTValues.V[8], 1, 8);

        Copper.setFluidPipeProperties(25, 1000, true);
        Bronze.setFluidPipeProperties(35, 2000, true);
        Steel.setFluidPipeProperties(50, 2500, true);
        StainlessSteel.setFluidPipeProperties(100, 3000, true);
        Titanium.setFluidPipeProperties(200, 5000, true);
        TungstenSteel.setFluidPipeProperties(300, 7500, true);

        Plastic.setFluidPipeProperties(200, 350, true);
        Polytetrafluoroethylene.setFluidPipeProperties(200, 600, true);
    }

}
