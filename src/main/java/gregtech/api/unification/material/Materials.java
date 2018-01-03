package gregtech.api.unification.material;

import gregtech.api.unification.Element;
import gregtech.api.unification.material.type.*;
import gregtech.api.unification.stack.MaterialStack;
import net.minecraft.init.Enchantments;

import static com.google.common.collect.ImmutableList.of;
import static gregtech.api.unification.material.type.DustMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.FluidMaterial.MatFlags.GENERATE_PLASMA;
import static gregtech.api.unification.material.type.FluidMaterial.MatFlags.STATE_GAS;
import static gregtech.api.unification.material.type.GemMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.Material.MatFlags.*;
import static gregtech.api.unification.material.type.MetalMaterial.MatFlags.*;
import static gregtech.api.unification.material.type.SolidMaterial.MatFlags.*;

@SuppressWarnings("WeakerAccess")
public class Materials {

    private static final long STD_SOLID = GENERATE_PLATE | GENERATE_ROD | GENERATE_BOLT_SCREW;
    private static final long STD_GEM = GENERATE_ORE | STD_SOLID | GENERATE_LENSE;
    private static final long STD_METAL = GENERATE_PLATE;
    private static final long EXT_METAL = STD_METAL | GENERATE_ROD | GENERATE_BOLT_SCREW;
    private static final long EXT2_METAL = EXT_METAL | GENERATE_GEAR | GENERATE_FOIL | GENERATE_FINE_WIRE;

    public static MarkerMaterial _NULL = new MarkerMaterial("_null");
    /**
     * Direct Elements
     */
    public static MetalMaterial Aluminium = new MetalMaterial(1, "aluminium", 0x45AAAA, MaterialIconSet.DULL, 2, of(), EXT2_METAL | GENERATE_SMALL_GEAR | GENERATE_ORE | GENERATE_RING, Element.Al, 10.0F, 128, 1700);
    public static MetalMaterial Americium = new MetalMaterial(2, "americium", 0xDDDDDD, MaterialIconSet.METALLIC, 3, of(), STD_METAL | GENERATE_ROD | GENERATE_LONG_ROD, Element.Am);
    public static MetalMaterial Antimony = new MetalMaterial(3, "antimony", 0xCCCCDD, MaterialIconSet.SHINY, 2, of(), EXT_METAL | MORTAR_GRINDABLE, Element.Sb);
    public static FluidMaterial Argon = new FluidMaterial(4, "argon", 0xBBBB00, MaterialIconSet.FLUID, of(), STATE_GAS | GENERATE_PLASMA, Element.Ar);
    public static DustMaterial Arsenic = new DustMaterial(5, "arsenic", 0xFFFFFF, MaterialIconSet.SAND, 2, of(), 0, Element.As);
    public static MetalMaterial Barium = new MetalMaterial(6, "barium", 0xFFFFFF, MaterialIconSet.SHINY, 2, of(), 0, Element.Ba);
    public static MetalMaterial Beryllium = new MetalMaterial(7, "beryllium", 0x66BB66, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_ORE, Element.Be, 14.0F, 2, 64);
    public static MetalMaterial Bismuth = new MetalMaterial(8, "bismuth", 0x008080, MaterialIconSet.METALLIC, 1, of(), GENERATE_ORE, Element.Bi, 6.0F, 1, 64);
    public static DustMaterial Boron = new DustMaterial(9, "boron", 0xFCFCFC, MaterialIconSet.SAND, 2, of(), 0, Element.B);
    public static MetalMaterial Caesium = new MetalMaterial(10, "caesium", 0xFFFFFC, MaterialIconSet.DULL, 2, of(), 0, Element.Cs);
    public static MetalMaterial Calcium = new MetalMaterial(11, "calcium", 0xDDDDAA, MaterialIconSet.METALLIC, 2, of(), 0, Element.Ca);
    public static MetalMaterial Carbon = new MetalMaterial(12, "carbon", 0x555555, MaterialIconSet.DULL, 2, of(), 0, Element.C, 1.0F, 2, 64);
    public static MetalMaterial Cadmium = new MetalMaterial(13, "cadmium", 0x505060, MaterialIconSet.SHINY, 2, of(), 0, Element.Ca);
    public static MetalMaterial Cerium = new MetalMaterial(14, "cerium", 0xEEEEEE, MaterialIconSet.METALLIC, 2, of(), 0, Element.Ce, 1068);
    public static FluidMaterial Chlorine = new FluidMaterial(15, "chlorine", 0xEEEECC, MaterialIconSet.GAS, of(), STATE_GAS, Element.Cl);
    public static MetalMaterial Chrome = new MetalMaterial(16, "chrome", 0xFFAAAB, MaterialIconSet.SHINY, 3, of(), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR, Element.Cr, 11.0F, 256, 1700);
    public static MetalMaterial Cobalt = new MetalMaterial(17, "cobalt", 0xAAAAFF, MaterialIconSet.METALLIC, 3, of(), STD_METAL | GENERATE_ORE, Element.Co, 8.0F, 3, 512);
    public static MetalMaterial Copper = new MetalMaterial(18, "copper", 0xFF8000, MaterialIconSet.SHINY, 1, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_DENSE, Element.Cu);
    public static FluidMaterial Deuterium = new FluidMaterial(19, "deuterium", 0xEEEE00, MaterialIconSet.FLUID, of(), STATE_GAS | GENERATE_PLASMA, Element.D);
    public static MetalMaterial Dysprosium = new MetalMaterial(20, "dysprosium", 0xFFFFEE, MaterialIconSet.SHINY, 2, of(), 0, Element.Dy, 1680);
    public static MetalMaterial Erbium = new MetalMaterial(21, "erbium", 0xEEEEEE, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Er, 1802);
    public static MetalMaterial Europium = new MetalMaterial(22, "europium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_ROD, Element.Eu, 1099);
    public static FluidMaterial Fluorine = new FluidMaterial(23, "fluorine", 0xFFFFAA, MaterialIconSet.GAS, of(), STATE_GAS, Element.F);
    public static MetalMaterial Gadolinium = new MetalMaterial(24, "gadolinium", 0xDDDDFF, MaterialIconSet.METALLIC, 2, of(), 0, Element.Gd, 1585);
    public static MetalMaterial Gallium = new MetalMaterial(25, "gallium", 0xEEEEFF, MaterialIconSet.SHINY, 2, of(), GENERATE_PLATE, Element.Ga, 1.0F, 2, 64);
    public static MetalMaterial Gold = new MetalMaterial(26, "gold", 0xFFFF00, MaterialIconSet.SHINY, 2, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE, Element.Au, 12.0F, 2, 64);
    public static MetalMaterial Holmium = new MetalMaterial(27, "holmium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), 0, Element.Ho, 1734);
    public static FluidMaterial Hydrogen = new FluidMaterial(28, "hydrogen", 0x00FFAA, MaterialIconSet.GAS, of(), STATE_GAS, Element.H);
    public static FluidMaterial Helium = new FluidMaterial(29, "helium", 0xDDDD00, MaterialIconSet.GAS, of(), STATE_GAS, Element.He);
    public static FluidMaterial Helium3 = new FluidMaterial(30, "helium3", 0xDDDD00, MaterialIconSet.GAS, of(), STATE_GAS, Element.He_3);
    public static MetalMaterial Indium = new MetalMaterial(31, "indium", 0x6600BB, MaterialIconSet.METALLIC, 2, of(), 0, Element.In);
    public static MetalMaterial Iridium = new MetalMaterial(32, "iridium", 0xFFFFFF, MaterialIconSet.DULL, 3, of(), GENERATE_ORE | EXT2_METAL | GENERATE_ORE | GENERATE_RING | GENERATE_ROTOR, Element.Ir, 6.0F, 2560, 2719);
    public static MetalMaterial Iron = new MetalMaterial(33, "iron", 0xAAAAAA, MaterialIconSet.METALLIC, 2, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_DENSE, Element.Fe, 6.0F, 2, 256);
    public static MetalMaterial Lanthanum = new MetalMaterial(34, "lanthanum", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), 0, Element.La, 1193);
    public static MetalMaterial Lead = new MetalMaterial(35, "lead", 0x770077, MaterialIconSet.DULL, 1, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_DENSE, Element.Pb, 8.0F, 1, 64);
    public static MetalMaterial Lithium = new MetalMaterial(36, "lithium", 0xCBCBCB, MaterialIconSet.DULL, 2, of(), STD_METAL | GENERATE_ORE, Element.Li);
    public static MetalMaterial Lutetium = new MetalMaterial(37, "lutetium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), 0, Element.Lu, 1925);
    public static MetalMaterial Magnesium = new MetalMaterial(38, "magnesium", 0xFFBBBB, MaterialIconSet.METALLIC, 2, of(), 0, Element.Mg);
    public static MetalMaterial Manganese = new MetalMaterial(39, "manganese", 0xEEEEEE, MaterialIconSet.DULL, 2, of(), 0, Element.Mn, 7.0F, 2, 512);
    public static FluidMaterial Mercury = new FluidMaterial(40, "mercury", 0xFFDDDD, MaterialIconSet.FLUID, of(), SMELT_INTO_FLUID, Element.Hg);
    public static MetalMaterial Molybdenum = new MetalMaterial(41, "molybdenum", 0xAAAADD, MaterialIconSet.DULL, 2, of(), 0, Element.Mo, 7.0F, 512, 2);
    public static MetalMaterial Neodymium = new MetalMaterial(42, "neodymium", 0x999999, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_ROD, Element.Nd, 7.0F, 512, 1297);
    public static MetalMaterial Darmstadtium = new MetalMaterial(43, "darmstadtium", 0xAAAAAA, MaterialIconSet.METALLIC, 6, of(), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD | GENERATE_ROUNDS, Element.Ds, 24.0F, 6, 655360);
    public static MetalMaterial Nickel = new MetalMaterial(44, "nickel", 0xAAAAFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_ORE | MORTAR_GRINDABLE, Element.Ni, 6.0F, 2, 64);
    public static MetalMaterial Niobium = new MetalMaterial(45, "niobium", 0x9486AA, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_ORE, Element.Nb, 2750);
    public static FluidMaterial Nitrogen = new FluidMaterial(46, "nitrogen", 0x7090AF, MaterialIconSet.FLUID, of(), STATE_GAS, Element.N);
    public static MetalMaterial Osmium = new MetalMaterial(47, "osmium", 0x5050FF, MaterialIconSet.METALLIC, 4, of(), GENERATE_ORE | EXT2_METAL | GENERATE_RING | GENERATE_ROTOR, Element.Os, 16.0F, 1280, 3306);
    public static FluidMaterial Oxygen = new FluidMaterial(48, "oxygen", 0x90AAEE, MaterialIconSet.FLUID, of(), STATE_GAS, Element.O);
    public static MetalMaterial Palladium = new MetalMaterial(49, "palladium", 0xCED0DD, MaterialIconSet.METALLIC, 2, of(), EXT2_METAL | GENERATE_ORE, Element.Pd, 8.0f, 512, 1228);
    public static DustMaterial Phosphorus = new DustMaterial(50, "phosphorus", 0xC8C800, MaterialIconSet.SAND, 2, of(), 0, Element.P);
    public static MetalMaterial Platinum = new MetalMaterial(51, "platinum", 0xFFFF99, MaterialIconSet.SHINY, 2, of(), EXT2_METAL | GENERATE_ORE, Element.Pt, 12.0F, 2, 64);
    public static MetalMaterial Plutonium = new MetalMaterial(52, "plutonium", 0xF03232, MaterialIconSet.METALLIC, 3, of(), EXT_METAL, Element.Pu, 6.0F, 3, 512);
    public static MetalMaterial Plutonium241 = new MetalMaterial(53, "plutonium241", 0xFA4646, MaterialIconSet.SHINY, 3, of(), EXT_METAL, Element.Pu_241, 6.0F, 3, 512);
    public static MetalMaterial Potassium = new MetalMaterial(54, "potassium", 0xCECECE, MaterialIconSet.METALLIC, 1, of(), EXT_METAL, Element.K);
    public static MetalMaterial Praseodymium = new MetalMaterial(55, "praseodymium", 0xCECECE, MaterialIconSet.METALLIC, 2, of(), EXT_METAL, Element.Pr, 1208);
    public static MetalMaterial Promethium = new MetalMaterial(56, "promethium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), EXT_METAL, Element.Pm, 1315);
    public static FluidMaterial Radon = new FluidMaterial(57, "radon", 0xFF00FF, MaterialIconSet.FLUID, of(), STATE_GAS | GENERATE_PLASMA, Element.Rn);
    public static MetalMaterial Rubidium = new MetalMaterial(58, "rubidium", 0xF01E1E, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Rb);
    public static MetalMaterial Samarium = new MetalMaterial(59, "samarium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Sm, 1345);
    public static MetalMaterial Scandium = new MetalMaterial(60, "scandium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Sc, 1814);
    public static MetalMaterial Silicon = new MetalMaterial(61, "silicon", 0x3C3C50, MaterialIconSet.METALLIC, 2, of(), STD_METAL | GENERATE_FOIL, Element.Si, 1687);
    public static MetalMaterial Silver = new MetalMaterial(62, "silver", 0xDCDCFF, MaterialIconSet.SHINY, 2, of(), EXT2_METAL | GENERATE_ORE | MORTAR_GRINDABLE, Element.Ag, 10.0F, 2, 64);
    public static MetalMaterial Sodium = new MetalMaterial(63, "sodium", 0x000096, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Na);
    public static MetalMaterial Strontium = new MetalMaterial(64, "strontium", 0xC8C896, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Sr);
    public static DustMaterial Sulfur = new DustMaterial(65, "sulfur", 0xC8C800, MaterialIconSet.SAND, 2, of(), NO_SMASHING |NO_SMELTING |FLAMMABLE, Element.S);
    public static MetalMaterial Tantalum = new MetalMaterial(66, "tantalum", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Ta);
    public static MetalMaterial Tellurium = new MetalMaterial(67, "tellurium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Te);
    public static MetalMaterial Terbium = new MetalMaterial(68, "terbium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Tb, 1629);
    public static MetalMaterial Thorium = new MetalMaterial(69, "thorium", 0x001E00, MaterialIconSet.SHINY, 2, of(), STD_METAL | GENERATE_ORE, Element.Th, 6.0F, 2, 512);
    public static MetalMaterial Thulium = new MetalMaterial(70, "thulium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Tm, 1818);
    public static MetalMaterial Tin = new MetalMaterial(71, "tin", 0xDCDCDC, MaterialIconSet.DULL, 1, of(), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_ROTOR, Element.Sn, 505);
    public static MetalMaterial Titanium = new MetalMaterial(72, "titanium", 0xDCA0F0, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD, Element.Ti, 7.0F, 1600, 1941);
    public static FluidMaterial Tritium = new FluidMaterial(73, "tritium", 0xFF0000, MaterialIconSet.METALLIC, of(), STATE_GAS, Element.T);
    public static MetalMaterial Tungsten = new MetalMaterial(74, "tungsten", 0x323232, MaterialIconSet.METALLIC, 3, of(), EXT2_METAL, Element.W, 7.0F, 2560, 3000);
    public static MetalMaterial Uranium = new MetalMaterial(75, "uranium", 0x32F032, MaterialIconSet.METALLIC, 3, of(), STD_METAL | GENERATE_ORE, Element.U, 6.0F, 3, 512);
    public static MetalMaterial Uranium235 = new MetalMaterial(76, "uranium235", 0x46FA46, MaterialIconSet.SHINY, 3, of(), STD_METAL | GENERATE_ORE | GENERATE_ROD, Element.U_235, 6.0F, 3, 512);
    public static MetalMaterial Vanadium = new MetalMaterial(77, "vanadium", 0x323232, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.V, 2183);
    public static MetalMaterial Ytterbium = new MetalMaterial(77, "ytterbium", 0xFFFFFF, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Yb, 1097);
    public static MetalMaterial Yttrium = new MetalMaterial(78, "yttrium", 0xDCFADC, MaterialIconSet.METALLIC, 2, of(), STD_METAL, Element.Y, 1799);
    public static MetalMaterial Zinc = new MetalMaterial(79, "zinc", 0xFAF0F0, MaterialIconSet.METALLIC, 1, of(), STD_METAL | GENERATE_ORE | MORTAR_GRINDABLE | GENERATE_FOIL, Element.Zn);

    /**
     * Not possible to determine exact Components
     */
    public static DustMaterial ConstructionFoam = new DustMaterial(313, "construction_foam", 0x808080, MaterialIconSet.ROUGH, 2, of(), NO_SMASHING | EXPLOSIVE | NO_SMELTING);
    public static FluidMaterial BioFuel = new FluidMaterial(314, "bio_fuel", 0xFF8000, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Biomass = new FluidMaterial(315, "biomass", 0x00FF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Creosote = new FluidMaterial(316, "creosote", 0x804000, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Ethanol = new FluidMaterial(317, "ethanol", 0xFF8000, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Fuel = new FluidMaterial(318, "fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Glue = new FluidMaterial(319, "glue", 0xC8C400, MaterialIconSet.FLUID, of(), 0);
    public static DustMaterial Gunpowder = new DustMaterial(320, "gunpowder", 0x808080, MaterialIconSet.SAND, 0, of(), FLAMMABLE | EXPLOSIVE | NO_SMELTING | NO_SMASHING);
    public static FluidMaterial Lubricant = new FluidMaterial(321, "lubricant", 0xFFC400, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial McGuffium239 = new FluidMaterial(322, "mc_guffium239", 0xC83296, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Oil = new FluidMaterial(323, "oil", 0x0A0A0A, MaterialIconSet.FLUID, of(), 0);
    public static DustMaterial Oilsands = new DustMaterial(324, "oilsands", 0x0A0A0A, MaterialIconSet.NONE, 1, of(), GENERATE_ORE);
    public static DustMaterial Paper = new DustMaterial(325, "paper", 0xFFFFFF, MaterialIconSet.PAPER, 0, of(), GENERATE_PLATE | FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE | GENERATE_DENSE);
    public static DustMaterial RareEarth = new DustMaterial(326, "rare_earth", 0x808064, MaterialIconSet.ROUGH, 0, of(), 0);
    public static FluidMaterial SeedOil = new FluidMaterial(327, "seed_oil", 0xC4FF00, MaterialIconSet.FLUID, of(), 0);
    public static DustMaterial Stone = new DustMaterial(328, "stone", 0xCDCDCD, MaterialIconSet.ROUGH, 1, of(), MORTAR_GRINDABLE | GENERATE_GEAR | GENERATE_PLATE | NO_SMASHING | NO_RECYCLING);
    public static FluidMaterial Lava = new FluidMaterial(329, "lava", 0xFF4000, MaterialIconSet.FLUID, of(), 0);
    public static DustMaterial Glowstone = new DustMaterial(330, "glowstone", 0xFFFF00, MaterialIconSet.SHINY, 1, of(), NO_SMASHING | SMELT_INTO_FLUID);
    public static GemMaterial NetherStar = new GemMaterial(331, "nether_star", 0xFFFFFF, MaterialIconSet.NETHERSTAR, 4, of(), STD_SOLID | GENERATE_LENSE | NO_SMASHING | NO_SMELTING, null, 1.0F, 5120);
    public static DustMaterial Endstone = new DustMaterial(332, "endstone", 0xFFFFFF, MaterialIconSet.DULL, 1, of(), NO_SMASHING);
    public static DustMaterial Netherrack = new DustMaterial(333, "netherrack", 0xC80000, MaterialIconSet.ROUGH, 1, of(), NO_SMASHING | FLAMMABLE);

    /**
     * First Degree Compounds
     */
    public static FluidMaterial Methane = new FluidMaterial(80, "methane", 0xFFFFFF, MaterialIconSet.FLUID, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 4)), 0);
    public static FluidMaterial CarbonDioxide = new FluidMaterial(81, "carbon_dioxide", 0xA9D0F5, MaterialIconSet.FLUID, of(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 2)), GENERATE_PLASMA);
    public static FluidMaterial NobleGases = new FluidMaterial(82, "noble_gases", 0xA9D0F5, MaterialIconSet.FLUID, of(new MaterialStack(CarbonDioxide, 21), new MaterialStack(Helium, 9), new MaterialStack(Methane, 3), new MaterialStack(Deuterium, 1)), GENERATE_PLASMA);
    public static FluidMaterial Air = new FluidMaterial(83, "air", 0xA9D0F5, MaterialIconSet.FLUID, of(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1), new MaterialStack(NobleGases, 1)), GENERATE_PLASMA);
    public static FluidMaterial LiquidAir = new FluidMaterial(84, "liquid_air", 0xA9D0F5, MaterialIconSet.FLUID, of(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1), new MaterialStack(NobleGases, 1)), GENERATE_PLASMA);
    public static GemMaterial Almandine = new GemMaterial(85, "almandine", 0xFF0000, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(Aluminium, 2), new MaterialStack(Iron, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Andradite = new DustMaterial(86, "andradite", 0x967800, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(Calcium, 3), new MaterialStack(Iron, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), 0);
    public static MetalMaterial AnnealedCopper = new MetalMaterial(87, "annealed_copper", 0xFF7814, MaterialIconSet.SHINY, 2, of(new MaterialStack(Copper, 1)), EXT2_METAL | MORTAR_GRINDABLE);
    public static DustMaterial Asbestos = new DustMaterial(88, "asbestos", 0xE6E6E6, MaterialIconSet.SAND, 1, of(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9)), 0);
    public static DustMaterial Ash = new DustMaterial(89, "ash", 0x969696, MaterialIconSet.SAND, 1, of(new MaterialStack(Carbon, 1)), 0);
    public static MetalMaterial BandedIron = new MetalMaterial(90, "banded_iron", 0x915A5A, MaterialIconSet.DULL, 2, of(new MaterialStack(Iron, 2), new MaterialStack(Oxygen, 3)), STD_METAL | GENERATE_ORE);
    public static MetalMaterial BatteryAlloy = new MetalMaterial(91, "battery_alloy", 0x9C7CA0, MaterialIconSet.DULL, 1, of(new MaterialStack(Lead, 4), new MaterialStack(Antimony, 1)), EXT_METAL);
    public static GemMaterial BlueTopaz = new GemMaterial(92, "blue_topaz", 0x0000FF, MaterialIconSet.GEM_HORIZONTAL, 3, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)), GENERATE_LENSE | GENERATE_ORE | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.0F, 256);
    public static DustMaterial Bone = new DustMaterial(93, "bone", 0xFFFFFF, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Calcium, 1)), 0);
    public static MetalMaterial Brass = new MetalMaterial(94, "brass", 0xFFB400, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING, 7.0F, 96);
    public static MetalMaterial Bronze = new MetalMaterial(95, "bronze", 0xFF8000, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Tin, 1), new MaterialStack(Copper, 3)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_ROTOR, 6.0F, 192);
    public static DustMaterial BrownLimonite = new DustMaterial(96, "brown_limonite", 0xC86400, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    public static DustMaterial Calcite = new DustMaterial(97, "calcite", 0xFAE6DC, MaterialIconSet.DULL, 1, of(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static DustMaterial Cassiterite = new DustMaterial(98, "cassiterite", 0xDCDCDC, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    public static DustMaterial CassiteriteSand = new DustMaterial(99, "cassiterite_sand", 0xDCDCDC, MaterialIconSet.SAND, 1, of(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    public static DustMaterial Chalcopyrite = new DustMaterial(100, "chalcopyrite", 0xA07828, MaterialIconSet.DULL, 1, of(new MaterialStack(Copper, 1), new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static RoughMaterial Charcoal = new RoughMaterial(101, "charcoal", 0x644646, MaterialIconSet.FINE, 1, of(new MaterialStack(Carbon, 1)), FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE);
    public static MetalMaterial Chromite = new MetalMaterial(102, "chromite", 0x23140F, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Chrome, 2), new MaterialStack(Oxygen, 4)), GENERATE_ORE, null, 1700);
    public static MetalMaterial ChromiumDioxide  = new MetalMaterial(103, "chromium_dioxide", 0xE6C8C8, MaterialIconSet.DULL, 3, of(new MaterialStack(Chrome, 1), new MaterialStack(Oxygen, 2)), EXT_METAL, null, 11.0F, 256, 650);
    public static RoughMaterial Cinnabar = new RoughMaterial(103, "cinnabar", 0x960000, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Mercury, 1), new MaterialStack(Sulfur, 1)), GENERATE_ORE);
    public static FluidMaterial Water = new FluidMaterial(104, "water", 0x0000FF, MaterialIconSet.FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_RECYCLING);
    public static RoughMaterial Clay = new RoughMaterial(105, "clay", 0xC8C8DC, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Sodium, 2), new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Water, 6)), MORTAR_GRINDABLE);
    public static GemMaterial Coal = new GemMaterial(106, "coal", 0x464646, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Carbon, 1)), GENERATE_ORE | FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE);
    public static DustMaterial Cobaltite = new DustMaterial(107, "cobaltite", 0x5050FA, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Cobalt, 1), new MaterialStack(Arsenic, 1), new MaterialStack(Sulfur, 1)), GENERATE_ORE);
    public static DustMaterial Cooperite = new DustMaterial(108, "cooperite", 0xFFFFC8, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Platinum, 3), new MaterialStack(Nickel, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Palladium, 1)), GENERATE_ORE);
    public static MetalMaterial Cupronickel = new MetalMaterial(109, "cupronickel", 0xE39680, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Copper, 1), new MaterialStack(Nickel, 1)), EXT_METAL,  6.0F, 64);
    public static DustMaterial DarkAsh = new DustMaterial(110, "dark_ash", 0x323232, MaterialIconSet.SAND, 1, of(new MaterialStack(Carbon, 1)), 0);
    public static GemMaterial Diamond = new GemMaterial(111, "diamond", 0xC8FFFF, MaterialIconSet.DIAMOND, 3, of(new MaterialStack(Carbon, 1)), GENERATE_ROD | GENERATE_BOLT_SCREW | GENERATE_LENSE | GENERATE_GEAR | NO_SMASHING | NO_SMELTING | FLAMMABLE | HIGH_SIFTER_OUTPUT, 8.0F, 1280);
    public static MetalMaterial Electrum = new MetalMaterial(112, "electrum", 0xFFFF64, MaterialIconSet.SHINY, 2, of(new MaterialStack(Silver, 1), new MaterialStack(Gold, 1)), EXT2_METAL | MORTAR_GRINDABLE, 12.0F, 64);
    public static GemMaterial Emerald = new GemMaterial(113, "emerald", 0x50FF50, MaterialIconSet.EMERALD, 2, of(new MaterialStack(Beryllium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 6), new MaterialStack(Oxygen, 18)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.0F, 256);
    public static DustMaterial Galena = new DustMaterial(114, "galena", 0x643C64, MaterialIconSet.ROUGH, 3, of(new MaterialStack(Lead, 3), new MaterialStack(Silver, 3), new MaterialStack(Sulfur, 2)), GENERATE_ORE);
    public static DustMaterial Garnierite = new DustMaterial(115, "garnierite", 0x32C846, MaterialIconSet.ROUGH, 3, of(new MaterialStack(Nickel, 1), new MaterialStack(Oxygen, 1)), GENERATE_ORE);
    public static FluidMaterial Glyceryl = new FluidMaterial(116, "glyceryl", 0x009696, MaterialIconSet.FLUID, of(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 5), new MaterialStack(Nitrogen, 3), new MaterialStack(Oxygen, 9)), FLAMMABLE | EXPLOSIVE | NO_SMELTING | NO_SMASHING);
    public static GemMaterial GreenSapphire = new GemMaterial(117, "green_sapphire", 0x64C882, MaterialIconSet.GEM_HORIZONTAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.0F, 256);
    public static DustMaterial Grossular = new DustMaterial(118, "grossular", 0xC86400, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(Calcium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static FluidMaterial HolyWater = new FluidMaterial(119, "holy_water", 0x0000FF, MaterialIconSet.FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_RECYCLING);
    public static DustMaterial Ice = new DustMaterial(120, "ice", 0xC8C8FF, MaterialIconSet.ROUGH, 0, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_SMASHING | NO_RECYCLING);
    public static DustMaterial Ilmenite = new DustMaterial(121, "ilmenite", 0x463732, MaterialIconSet.ROUGH, 3, of(new MaterialStack(Iron, 1), new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static GemMaterial Rutile = new GemMaterial(122, "rutile", 0xD40D5C, MaterialIconSet.GEM_HORIZONTAL, 2, of(new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 2)), 0);
    public static DustMaterial Bauxite = new DustMaterial(123, "bauxite", 0xC86400, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Rutile, 2), new MaterialStack(Aluminium, 16), new MaterialStack(Hydrogen, 10), new MaterialStack(Oxygen, 11)), GENERATE_ORE);
    public static FluidMaterial Titaniumtetrachloride = new FluidMaterial(124, "titaniumtetrachloride", 0xD40D5C, MaterialIconSet.FLUID, of(new MaterialStack(Titanium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Chlorine, 2)), 0);
    public static DustMaterial Magnesiumchloride = new DustMaterial(125, "magnesiumchloride", 0xD40D5C, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Chlorine, 2)), 0);
    public static MetalMaterial Invar = new MetalMaterial(126, "invar", 0xB4B478, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 2), new MaterialStack(Nickel, 1)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING, 6.0F, 256);
    public static MetalMaterial Kanthal = new MetalMaterial(127, "kanthal", 0xC2D2DF, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Chrome, 1)), EXT_METAL, null, 6.0F, 64, 1800);
    public static GemMaterial Lazurite = new GemMaterial(128, "lazurite", 0x6478FF, MaterialIconSet.LAPIS, 1, of(new MaterialStack(Aluminium, 6), new MaterialStack(Silicon, 6), new MaterialStack(Calcium, 8), new MaterialStack(Sodium, 8)), GENERATE_PLATE | GENERATE_ORE | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | GENERATE_ROD);
    public static MetalMaterial Magnalium = new MetalMaterial(129, "magnalium", 0xC8BEFF, MaterialIconSet.DULL, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Aluminium, 2)), EXT2_METAL | GENERATE_LONG_ROD, 6.0F, 256);
    public static DustMaterial Magnesite = new DustMaterial(130, "magnesite", 0xFAFAB4, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static DustMaterial Magnetite = new DustMaterial(131, "magnetite", 0x1E1E1E, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 3), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial Molybdenite = new DustMaterial(132, "molybdenite", 0x191919, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Molybdenum, 1), new MaterialStack(Sulfur, 2)), GENERATE_ORE);
    public static MetalMaterial Nichrome = new MetalMaterial(133, "nichrome", 0xCDCEF6, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Nickel, 4), new MaterialStack(Chrome, 1)), EXT_METAL, null, 6.0F, 64, 2700);
    public static MetalMaterial NiobiumNitride = new MetalMaterial(134, "niobium_nitride", 0x1D291D, MaterialIconSet.DULL, 2, of(new MaterialStack(Niobium, 1), new MaterialStack(Nitrogen, 1)), EXT_METAL, null, 2573);
    public static MetalMaterial NiobiumTitanium = new MetalMaterial(135, "niobium_titanium", 0x1D1D29, MaterialIconSet.DULL, 2, of(new MaterialStack(Niobium, 1), new MaterialStack(Titanium, 1)), EXT2_METAL, null, 4500);
    public static FluidMaterial NitroCarbon = new FluidMaterial(136, "nitro_carbon", 0x004B64, MaterialIconSet.FLUID, of(new MaterialStack(Nitrogen, 1), new MaterialStack(Carbon, 1)), 0);
    public static FluidMaterial NitrogenDioxide = new FluidMaterial(137, "nitrogen_dioxide", 0x64AFFF, MaterialIconSet.FLUID, of(new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 2)), 0);
    public static DustMaterial Obsidian = new DustMaterial(138, "obsidian", 0x503264, MaterialIconSet.DULL, 3, of(new MaterialStack(Magnesium, 1), new MaterialStack(Iron, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 8)), NO_SMASHING);
    public static DustMaterial Phosphate = new DustMaterial(139, "phosphate", 0xFFFF00, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Phosphorus, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | FLAMMABLE | EXPLOSIVE);
    public static MetalMaterial PigIron = new MetalMaterial(140, "pig_iron", 0xC8B4B4, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 1)), EXT_METAL | GENERATE_RING, 6.0F, 384);
    public static DustMaterial Plastic = new DustMaterial(141, "plastic", 0xC8C8C8, MaterialIconSet.DULL, 1, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2)), GENERATE_PLATE | FLAMMABLE | NO_SMASHING);
    public static MetalMaterial Epoxid = new MetalMaterial(142, "epoxid", 0xC88C14, MaterialIconSet.DULL, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 1)), EXT2_METAL, 3.0F, 32);
    public static DustMaterial Silicone = new DustMaterial(143, "silicone", 0xDCDCDC, MaterialIconSet.DULL, 1, of(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 1)), GENERATE_PLATE | FLAMMABLE | NO_SMASHING);
    public static DustMaterial Polycaprolactam = new DustMaterial(144, "polycaprolactam", 0x323232, MaterialIconSet.DULL, 1, of(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 11), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 1)), GENERATE_PLATE);
    public static DustMaterial Polytetrafluoroethylene = new DustMaterial(145, "polytetrafluoroethylene", 0x646464, MaterialIconSet.DULL, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Fluorine, 4)), GENERATE_PLATE);
    public static DustMaterial Powellite = new DustMaterial(146, "powellite", 0xFFFF00, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Calcium, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial Pumice = new DustMaterial(147, "pumice", 0xE6B9B9, MaterialIconSet.PAPER, 2, of(new MaterialStack(Stone, 1)), 0);
    public static DustMaterial Pyrite = new DustMaterial(148, "pyrite", 0x967828, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial Pyrolusite = new DustMaterial(149, "pyrolusite", 0x9696AA, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Manganese, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE);
    public static DustMaterial Pyrope = new DustMaterial(150, "pyrope", 0x783264, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial RockSalt = new DustMaterial(151, "rock_salt", 0xF0C8C8, MaterialIconSet.FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Chlorine, 1)), GENERATE_ORE | NO_SMASHING);
    public static DustMaterial Rubber = new DustMaterial(152, "rubber", 0x151515, MaterialIconSet.ROUGH, 0, of(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), GENERATE_PLATE | GENERATE_GEAR | FLAMMABLE | NO_SMASHING | GENERATE_RING);
    public static DustMaterial RawRubber = new DustMaterial(153, "raw_rubber", 0xCCC789, MaterialIconSet.SAND, 0, of(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), 0);
    public static GemMaterial Ruby = new GemMaterial(154, "ruby", 0xFF6464, MaterialIconSet.RUBY, 2, of(new MaterialStack(Chrome, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.0F, 256);
    public static DustMaterial Salt = new DustMaterial(155, "salt", 0xFFFFFF, MaterialIconSet.SAND, 1, of(new MaterialStack(Sodium, 1), new MaterialStack(Chlorine, 1)), GENERATE_ORE | NO_SMASHING);
    public static DustMaterial Saltpeter = new DustMaterial(156, "saltpeter", 0xE6E6E6, MaterialIconSet.FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE | NO_SMASHING |NO_SMELTING |FLAMMABLE);
    public static GemMaterial Sapphire = new GemMaterial(157, "sapphire", 0x6464C8, MaterialIconSet.GEM_VERTICAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, null, 7.0F, 256);
    public static DustMaterial Scheelite = new DustMaterial(158, "scheelite", 0xC88C14, MaterialIconSet.DULL, 3, of(new MaterialStack(Tungsten, 1), new MaterialStack(Calcium, 2), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial SiliconDioxide = new DustMaterial(159, "silicon_dioxide", 0xC8C8C8, MaterialIconSet.QUARTZ, 1, of(new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 2)), NO_SMASHING | NO_SMELTING | CRYSTALLISABLE);
    public static RoughMaterial Snow = new RoughMaterial(160, "snow", 0xFFFFFF, MaterialIconSet.FINE, 0, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_SMASHING | NO_RECYCLING);
    public static GemMaterial Sodalite = new GemMaterial(161, "sodalite", 0x1414FF, MaterialIconSet.LAPIS, 1, of(new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Sodium, 4), new MaterialStack(Chlorine, 1)), GENERATE_ORE | GENERATE_PLATE | GENERATE_ROD | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE | GENERATE_ROD);
    public static FluidMaterial SodiumPersulfate = new FluidMaterial(162, "sodium_persulfate", 0xFFFFFF, MaterialIconSet.FLUID, of(new MaterialStack(Sodium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), 0);
    public static FluidMaterial SodiumSulfide = new FluidMaterial(163, "sodium_sulfide", 0xFFFFFF, MaterialIconSet.FLUID, of(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1)), 0);
    public static FluidMaterial HydricSulfide = new FluidMaterial(164, "hydric_sulfide", 0xFFFFFF, MaterialIconSet.FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1)), 0);
    public static FluidMaterial Steam = new FluidMaterial(346, "steam", 0xFFFFFF, MaterialIconSet.GAS, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), NO_RECYCLING);

    public static FluidMaterial OilHeavy = new FluidMaterial(165, "oil_heavy", 0x0A0A0A, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial OilMedium = new FluidMaterial(166, "oil_medium", 0x0A0A0A, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial OilLight = new FluidMaterial(167, "oil_light", 0x0A0A0A, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial NaturalGas = new FluidMaterial(168, "natural_gas", 0xFFFFFF, MaterialIconSet.FLUID, of(), STATE_GAS);
    public static FluidMaterial SulfuricGas = new FluidMaterial(169, "sulfuric_gas", 0xFFFFFF, MaterialIconSet.FLUID, of(), STATE_GAS);
    public static FluidMaterial Gas = new FluidMaterial(170, "gas", 0xFFFFFF, MaterialIconSet.FLUID, of(), STATE_GAS);
    public static FluidMaterial SulfuricNaphtha = new FluidMaterial(171, "sulfuric_naphtha", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial SulfuricLightFuel = new FluidMaterial(172, "sulfuric_ligh_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial SulfuricHeavyFuel = new FluidMaterial(173, "sulfuric_heavy_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Naphtha = new FluidMaterial(174, "naphtha", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial LightFuel = new FluidMaterial(175, "light_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial HeavyFuel = new FluidMaterial(176, "heavy_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial LPG = new FluidMaterial(177, "lpg", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial CrackedLightFuel = new FluidMaterial(178, "cracked_light_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial CrackedHeavyFuel = new FluidMaterial(179, "cracked_heavy_fuel", 0xFFFF00, MaterialIconSet.FLUID, of(), 0);

    public static MetalMaterial SolderingAlloy = new MetalMaterial(180, "soldering_alloy", 0xDCDCE6, MaterialIconSet.DULL, 1, of(new MaterialStack(Tin, 9), new MaterialStack(Antimony, 1)), EXT_METAL | GENERATE_FINE_WIRE, null, 400);
    public static DustMaterial Spessartine = new DustMaterial(181, "spessartine", 0xFF6464, MaterialIconSet.GEM_VERTICAL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Manganese, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Sphalerite = new DustMaterial(182, "sphalerite", 0xFFFFFF, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Zinc, 1), new MaterialStack(Sulfur, 1)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static MetalMaterial StainlessSteel = new MetalMaterial(183, "stainless_steel", 0xC8C8DC, MaterialIconSet.SHINY, 2, of(new MaterialStack(Iron, 6), new MaterialStack(Chrome, 1), new MaterialStack(Manganese, 1), new MaterialStack(Nickel, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR, null, 7.0F, 480, 1700);
    public static MetalMaterial Steel = new MetalMaterial(184, "steel", 0x808080, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 50), new MaterialStack(Carbon, 1)), EXT2_METAL | MORTAR_GRINDABLE  | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR, null, 6.0F, 512, 1000);
    public static DustMaterial Stibnite = new DustMaterial(185, "stibnite", 0x464646, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Antimony, 2), new MaterialStack(Sulfur, 3)), GENERATE_ORE);
    public static FluidMaterial SulfuricAcid = new FluidMaterial(186, "sulfuric_acid", 0xFF8000, MaterialIconSet.FLUID, of(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), 0);
    public static GemMaterial Tanzanite = new GemMaterial(187, "tanzanite", 0x4000C8, MaterialIconSet.GEM_VERTICAL, 2, of(new MaterialStack(Calcium, 2), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 13)), EXT_METAL | GENERATE_ORE | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, null, 7.0F, 256);
    public static DustMaterial Tetrahedrite = new DustMaterial(188, "tetrahedrite", 0xC82000, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Copper, 3), new MaterialStack(Antimony, 1), new MaterialStack(Sulfur, 3), new MaterialStack(Iron, 1)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static MetalMaterial TinAlloy = new MetalMaterial(189, "tin_alloy", 0xC8C8C8, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Tin, 1), new MaterialStack(Iron, 1)), EXT2_METAL, null, 6.5F, 2, 96);
    public static GemMaterial Topaz = new GemMaterial(190, "topaz", 0xFF8000, MaterialIconSet.GEM_HORIZONTAL, 3, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, null, 7.0F, 256);
    public static MetalMaterial Tungstate = new MetalMaterial(191, "tungstate", 0x373223, MaterialIconSet.DULL, 3, of(new MaterialStack(Tungsten, 1), new MaterialStack(Lithium, 2), new MaterialStack(Oxygen, 4)), GENERATE_ORE, null, 2500);
    public static MetalMaterial Ultimet = new MetalMaterial(192, "ultimet", 0xB4B4E6, MaterialIconSet.SHINY, 4, of(new MaterialStack(Cobalt, 5), new MaterialStack(Chrome, 2), new MaterialStack(Nickel, 1), new MaterialStack(Molybdenum, 1)), EXT2_METAL, null, 9.0F, 2048, 2700);
    public static DustMaterial Uraninite = new DustMaterial(193, "uraninite", 0x232323, MaterialIconSet.ROUGH, 3, of(new MaterialStack(Uranium, 1), new MaterialStack(Oxygen, 2)), 0);
    public static DustMaterial Uvarovite = new DustMaterial(194, "uvarovite", 0xB4FFB4, MaterialIconSet.GEM_VERTICAL, 2, of(new MaterialStack(Calcium, 3), new MaterialStack(Chrome, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)), 0);
    public static MetalMaterial VanadiumGallium = new MetalMaterial(195, "vanadium_gallium", 0x80808C, MaterialIconSet.SHINY, 2, of(new MaterialStack(Vanadium, 3), new MaterialStack(Gallium, 1)), STD_METAL | GENERATE_FOIL | GENERATE_ROD, null, 4500);
    public static DustMaterial Wood = new DustMaterial(196, "wood", 0x643200, MaterialIconSet.WOOD, 0, of(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 1), new MaterialStack(Hydrogen, 1)), STD_SOLID | FLAMMABLE | NO_SMELTING | NO_SMASHING | GENERATE_GEAR);
    public static MetalMaterial WroughtIron = new MetalMaterial(197, "wrought_iron", 0xC8B4B4, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 1)), EXT2_METAL | MORTAR_GRINDABLE | GENERATE_RING | GENERATE_LONG_ROD, null, 6.0F, 2, 384);
    public static DustMaterial Wulfenite = new DustMaterial(198, "wulfenite", 0xFF8000, MaterialIconSet.DULL, 3, of(new MaterialStack(Lead, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial YellowLimonite = new DustMaterial(199, "yellow_limonite", 0xC8C800, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Iron, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 2)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static MetalMaterial YttriumBariumCuprate = new MetalMaterial(200, "yttrium_barium_cuprate", 0x504046, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Yttrium, 1), new MaterialStack(Barium, 2), new MaterialStack(Copper, 3), new MaterialStack(Oxygen, 7)), EXT_METAL | GENERATE_FOIL, null, 4500);
    public static GemMaterial NetherQuartz = new GemMaterial(201, "nether_quartz", 0xE6D2D2, MaterialIconSet.QUARTZ, 1, of(), STD_SOLID | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE, null, 1.0F, 32);
    public static GemMaterial CertusQuartz = new GemMaterial(202, "certus_quartz", 0xD2D2E6, MaterialIconSet.QUARTZ, 1, of(), STD_SOLID | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE, null, 5.0F, 32);
    public static GemMaterial Quartzite = new GemMaterial(203, "quartzite", 0xD2E6D2, MaterialIconSet.QUARTZ, 1, of(), NO_SMASHING | NO_SMELTING | CRYSTALLISABLE);
    public static DustMaterial Graphite = new DustMaterial(204, "graphite", 0x808080, MaterialIconSet.DULL, 2, of(), GENERATE_ORE | NO_SMASHING |NO_SMELTING |FLAMMABLE);
    public static DustMaterial Graphene = new DustMaterial(205, "graphene", 0x808080, MaterialIconSet.SHINY, 2, of(), 0);
    public static GemMaterial Jasper = new GemMaterial(206, "jasper", 0xC85050, MaterialIconSet.EMERALD, 2, of(), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT);
    public static MetalMaterial Osmiridium = new MetalMaterial(207, "osmiridium", 0x6464FF, MaterialIconSet.METALLIC, 3, of(new MaterialStack(Iridium, 3), new MaterialStack(Osmium, 1)), EXT2_METAL, null,7.0F, 1600, 2500);

    /**
     * Second Degree Compounds
     */
    public static DustMaterial WoodSealed = new DustMaterial(208, "wood_sealed", 0x502800, MaterialIconSet.WOOD, 0, of(new MaterialStack(Wood, 1)), STD_SOLID | FLAMMABLE | NO_SMELTING | NO_SMASHING | NO_WORKING);
    public static GemMaterial Glass = new GemMaterial(209, "glass", 0xFFFFFF, MaterialIconSet.GLASS, 0, of(new MaterialStack(SiliconDioxide, 1)), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_RECYCLING | SMELT_INTO_FLUID);
    public static DustMaterial Perlite = new DustMaterial(210, "perlite", 0x1E141E, MaterialIconSet.DULL, 1, of(new MaterialStack(Obsidian, 2), new MaterialStack(Water, 1)), 0);
    public static DustMaterial Borax = new DustMaterial(210, "borax", 0xFFFFFF, MaterialIconSet.SAND, 1, of(new MaterialStack(Sodium, 2), new MaterialStack(Boron, 4), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 7)), 0);
    public static RoughMaterial Lignite = new RoughMaterial(211, "lignite", 0x644646, MaterialIconSet.LIGNITE, 0, of(new MaterialStack(Carbon, 2), new MaterialStack(Water, 4), new MaterialStack(DarkAsh, 1)), GENERATE_ORE | FLAMMABLE | NO_SMELTING | NO_SMASHING | MORTAR_GRINDABLE);
    public static GemMaterial Olivine = new GemMaterial(212, "olivine", 0x96FF96, MaterialIconSet.RUBY, 2, of(new MaterialStack(Magnesium, 2), new MaterialStack(Iron, 1), new MaterialStack(SiliconDioxide, 2)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.0F, 256);
    public static GemMaterial Opal = new GemMaterial(213, "opal", 0x0000FF, MaterialIconSet.OPAL, 2, of(new MaterialStack(SiliconDioxide, 1)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.0F, 256);
    public static GemMaterial Amethyst = new GemMaterial(214, "amethyst", 0xD232D2, MaterialIconSet.RUBY, 3, of(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Iron, 1)), STD_GEM | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, 7.0F, 256);
    public static DustMaterial Redstone = new DustMaterial(215, "redstone", 0xC80000, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Silicon, 1), new MaterialStack(Pyrite, 5), new MaterialStack(Ruby, 1), new MaterialStack(Mercury, 3)), GENERATE_PLATE | GENERATE_ORE | NO_SMASHING | SMELT_INTO_FLUID);
    public static GemMaterial Lapis = new GemMaterial(216, "lapis", 0x4646DC, MaterialIconSet.LAPIS, 1, of(new MaterialStack(Lazurite, 12), new MaterialStack(Sodalite, 2), new MaterialStack(Pyrite, 1), new MaterialStack(Calcite, 1)), STD_GEM | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE);
    public static DustMaterial Blaze = new DustMaterial(217, "blaze", 0xFFC800, MaterialIconSet.POWDER, 1, of(new MaterialStack(DarkAsh, 1), new MaterialStack(Sulfur, 1)), NO_SMELTING | SMELT_INTO_FLUID | MORTAR_GRINDABLE | BURNING);
    public static GemMaterial EnderPearl = new GemMaterial(218, "ender_pearl", 0x6CDCC8, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(Beryllium, 1), new MaterialStack(Potassium, 4), new MaterialStack(Nitrogen, 5)), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_SMELTING, null, 1.0F, 16);
    public static GemMaterial EnderEye = new GemMaterial(219, "ender_eye", 0x66FF66, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(EnderPearl, 1), new MaterialStack(Blaze, 1)), GENERATE_PLATE | GENERATE_LENSE | NO_SMASHING | NO_SMELTING, null, 1.0F, 16);
    public static DustMaterial Flint = new DustMaterial(220, "flint", 0x002040, MaterialIconSet.FLINT, 1, of(new MaterialStack(SiliconDioxide, 1)), NO_SMASHING | MORTAR_GRINDABLE);
    public static DustMaterial Diatomite = new DustMaterial(221, "diatomite", 0xE1E1E1, MaterialIconSet.DULL, 1, of(new MaterialStack(Flint, 8), new MaterialStack(BandedIron, 1), new MaterialStack(Sapphire, 1)), 0);
    public static DustMaterial VolcanicAsh = new DustMaterial(222, "volcanic_ash", 0x3C3232, MaterialIconSet.SAND, 0, of(new MaterialStack(Flint, 6), new MaterialStack(Iron, 1), new MaterialStack(Magnesium, 1)), 0);
    public static RoughMaterial Niter = new RoughMaterial(223, "niter", 0xFFC8C8, MaterialIconSet.FLINT, 1, of(new MaterialStack(Saltpeter, 1)), NO_SMASHING | NO_SMELTING);
    public static DustMaterial Pyrotheum = new DustMaterial(224, "pyrotheum", 0xFF8000, MaterialIconSet.FIERY, 1, of(new MaterialStack(Coal, 1), new MaterialStack(Redstone, 1), new MaterialStack(Blaze, 1)), 0);
    public static DustMaterial HydratedCoal = new DustMaterial(225, "hydrated_coal", 0x464664, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Coal, 8), new MaterialStack(Water, 1)), 0);
    public static GemMaterial Apatite = new GemMaterial(226, "apatite", 0xC8C8FF, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(Calcium, 5), new MaterialStack(Phosphate, 3), new MaterialStack(Chlorine, 1)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE);
    public static MetalMaterial SterlingSilver = new MetalMaterial(227, "sterling_silver", 0xFADCE1, MaterialIconSet.SHINY, 2, of(new MaterialStack(Copper, 1), new MaterialStack(Silver, 4)), EXT2_METAL, null, 13.0F, 128, 1700);
    public static MetalMaterial RoseGold = new MetalMaterial(228, "rose_gold", 0xFFE61E, MaterialIconSet.SHINY, 2, of(new MaterialStack(Copper, 1), new MaterialStack(Gold, 4)), EXT2_METAL, null, 14.0F, 128, 1600);
    public static MetalMaterial BlackBronze = new MetalMaterial(229, "black_bronze", 0x64327D, MaterialIconSet.DULL, 2, of(new MaterialStack(Gold, 1), new MaterialStack(Silver, 1), new MaterialStack(Copper, 3)), EXT2_METAL, null, 12.0F, 256, 2000);
    public static MetalMaterial BismuthBronze = new MetalMaterial(230, "bismuth_bronze", 0x647D7D, MaterialIconSet.DULL, 2, of(new MaterialStack(Bismuth, 1), new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)), EXT2_METAL, null, 8.0F, 256, 1100);
    public static MetalMaterial BlackSteel = new MetalMaterial(231, "black_steel", 0x646464, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Nickel, 1), new MaterialStack(BlackBronze, 1), new MaterialStack(Steel, 3)), EXT_METAL, null, 6.5F, 768, 1200);
    public static MetalMaterial RedSteel = new MetalMaterial(232, "red_steel", 0x8C6464, MaterialIconSet.METALLIC, 2, of(new MaterialStack(SterlingSilver, 1), new MaterialStack(BismuthBronze, 1), new MaterialStack(Steel, 2), new MaterialStack(BlackSteel, 4)), EXT_METAL, null, 7.0F, 896, 1300);
    public static MetalMaterial BlueSteel = new MetalMaterial(233, "blue_steel", 0x64648C, MaterialIconSet.METALLIC, 2, of(new MaterialStack(RoseGold, 1), new MaterialStack(Brass, 1), new MaterialStack(Steel, 2), new MaterialStack(BlackSteel, 4)), EXT_METAL, null, 7.5F, 1024, 1400);
    public static MetalMaterial DamascusSteel = new MetalMaterial(234, "damascus_steel", 0x6E6E6E, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Steel, 1)), EXT_METAL, null, 8.0F, 1280, 1500);
    public static MetalMaterial TungstenSteel = new MetalMaterial(235, "tungsten_steel", 0x6464A0, MaterialIconSet.METALLIC, 4, of(new MaterialStack(Steel, 1), new MaterialStack(Tungsten, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD, null, 8.0F, 2560, 3000);
    public static FluidMaterial NitroFuel = new FluidMaterial(236, "nitro_fuel", 0xC8FF00, MaterialIconSet.FLUID, of(new MaterialStack(Glyceryl, 1), new MaterialStack(Fuel, 4)), FLAMMABLE | EXPLOSIVE | NO_SMELTING | NO_SMASHING);
    public static MetalMaterial RedAlloy = new MetalMaterial(237, "red_alloy", 0xC80000, MaterialIconSet.DULL, 0, of(new MaterialStack(Copper, 1), new MaterialStack(Redstone, 4)), GENERATE_PLATE | GENERATE_FINE_WIRE);
    public static MetalMaterial CobaltBrass = new MetalMaterial(238, "cobalt_brass", 0xB4B4A0, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Brass, 7), new MaterialStack(Aluminium, 1), new MaterialStack(Cobalt, 1)), EXT2_METAL, null, 8.0F, 2, 256);
    public static RoughMaterial Phosphor = new RoughMaterial(239, "phosphor", 0xFFFF00, MaterialIconSet.FLINT, 2, of(new MaterialStack(Calcium, 3), new MaterialStack(Phosphate, 2)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | FLAMMABLE | EXPLOSIVE);
    public static DustMaterial Basalt = new DustMaterial(240, "basalt", 0x1E1414, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Olivine, 1), new MaterialStack(Calcite, 3), new MaterialStack(Flint, 8), new MaterialStack(DarkAsh, 4)), NO_SMASHING);
    public static DustMaterial Andesite = new DustMaterial(241, "andesite", 0xBEBEBE, MaterialIconSet.ROUGH, 2, of(), NO_SMASHING);
    public static DustMaterial Diorite = new DustMaterial(242, "diorite", 0xFFFFFF, MaterialIconSet.ROUGH, 2, of(), NO_SMASHING);
    public static GemMaterial GarnetRed = new GemMaterial(243, "garnet_red", 0xC85050, MaterialIconSet.RUBY, 2, of(new MaterialStack(Pyrope, 3), new MaterialStack(Almandine, 5), new MaterialStack(Spessartine, 8)), STD_SOLID | GENERATE_LENSE | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, null, 7.0F, 128);
    public static GemMaterial GarnetYellow = new GemMaterial(244, "garnet_yellow", 0xC8C850, MaterialIconSet.RUBY, 2, of(new MaterialStack(Andradite, 5), new MaterialStack(Grossular, 8), new MaterialStack(Uvarovite, 3)), STD_SOLID | GENERATE_LENSE | NO_SMASHING | NO_SMELTING | HIGH_SIFTER_OUTPUT, null, 7.0F, 128);
    public static DustMaterial Marble = new DustMaterial(245, "marble", 0xC8C8C8, MaterialIconSet.FINE, 1, of(new MaterialStack(Magnesium, 1), new MaterialStack(Calcite, 7)), NO_SMASHING);
    public static DustMaterial Sugar = new DustMaterial(246, "sugar", 0xFAFAFA, MaterialIconSet.SAND, 1, of(new MaterialStack(Carbon, 2), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 25)), 0);
    public static GemMaterial Vinteum = new GemMaterial(247, "vinteum", 0x64C8FF, MaterialIconSet.EMERALD, 3, of(), STD_GEM | NO_SMASHING | NO_SMELTING, 10.0F, 128);
    public static DustMaterial Redrock = new DustMaterial(248, "redrock", 0xFF5032, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Calcite, 2), new MaterialStack(Flint, 1), new MaterialStack(Clay, 1)), NO_SMASHING);
    public static DustMaterial PotassiumFeldspar = new DustMaterial(249, "potassium_feldspar", 0x782828, MaterialIconSet.FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 8)), 0);
    public static DustMaterial Biotite = new DustMaterial(250, "biotite", 0x141E14, MaterialIconSet.METALLIC, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 3), new MaterialStack(Aluminium, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 10)), 0);
    public static DustMaterial GraniteBlack = new DustMaterial(251, "granite_black", 0x0A0A0A, MaterialIconSet.ROUGH, 3, of(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Biotite, 1)), NO_SMASHING);
    public static DustMaterial GraniteRed = new DustMaterial(252, "granite_red", 0xFF0080, MaterialIconSet.ROUGH, 3, of(new MaterialStack(Aluminium, 2), new MaterialStack(PotassiumFeldspar, 1), new MaterialStack(Oxygen, 3)), NO_SMASHING);
    public static DustMaterial Chrysotile = new DustMaterial(253, "chrysotile", 0x6E8C6E, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Asbestos, 1)), 0);
    public static DustMaterial Realgar = new DustMaterial(254, "realgar", 0x8C6464, MaterialIconSet.DULL, 2, of(new MaterialStack(Arsenic, 4), new MaterialStack(Sulfur, 4)), 0);
    public static DustMaterial VanadiumMagnetite = new DustMaterial(255, "vanadium_magnetite", 0x23233C, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Magnetite, 1), new MaterialStack(Vanadium, 1)), GENERATE_ORE);
    public static DustMaterial BasalticMineralSand = new DustMaterial(266, "basaltic_mineral_sand", 0x283228, MaterialIconSet.SAND, 1, of(new MaterialStack(Magnetite, 1), new MaterialStack(Basalt, 1)), INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial GraniticMineralSand = new DustMaterial(267, "granitic_mineral_sand", 0x283C3C, MaterialIconSet.SAND, 1, of(new MaterialStack(Magnetite, 1), new MaterialStack(GraniteBlack, 1)), INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial GarnetSand = new DustMaterial(268, "garnet_sand", 0xC86400, MaterialIconSet.SAND, 1, of(new MaterialStack(GarnetRed, 1), new MaterialStack(GarnetYellow, 1)), 0);
    public static DustMaterial QuartzSand = new DustMaterial(269, "quartz_sand", 0xC8C8C8, MaterialIconSet.SAND, 1, of(new MaterialStack(CertusQuartz, 1), new MaterialStack(Quartzite, 1)), 0);
    public static DustMaterial Bastnasite = new DustMaterial(270, "bastnasite", 0xC86E2D, MaterialIconSet.FINE, 2, of(new MaterialStack(Cerium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Fluorine, 1), new MaterialStack(Oxygen, 3)), GENERATE_ORE);
    public static DustMaterial Pentlandite = new DustMaterial(271, "pentlandite", 0xA59605, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Nickel, 9), new MaterialStack(Sulfur, 8)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial Spodumene = new DustMaterial(272, "spodumene", 0xBEAAAA, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 6)), GENERATE_ORE);
    public static DustMaterial Pollucite = new DustMaterial(273, "pollucite", 0xF0D2D2, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Caesium, 2), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 4), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 12)), 0);
    public static DustMaterial Tantalite = new DustMaterial(274, "tantalite", 0x915028, MaterialIconSet.METALLIC, 3, of(new MaterialStack(Manganese, 1), new MaterialStack(Tantalum, 2), new MaterialStack(Oxygen, 6)), GENERATE_ORE);
    public static DustMaterial Lepidolite = new DustMaterial(274, "lepidolite", 0xF0328C, MaterialIconSet.FINE, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Lithium, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10)), GENERATE_ORE);
    public static DustMaterial Glauconite = new DustMaterial(275, "glauconite", 0x82B43C, MaterialIconSet.DULL, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 2), new MaterialStack(Aluminium, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial GlauconiteSand = new DustMaterial(276, "glauconite_sand", 0x82B43C, MaterialIconSet.SAND, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 2), new MaterialStack(Aluminium, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)), 0);
    public static DustMaterial Vermiculite = new DustMaterial(277, "vermiculite", 0xC8B40F, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Iron, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Water, 4), new MaterialStack(Oxygen, 12)), 0);
    public static DustMaterial Bentonite = new DustMaterial(278, "bentonite", 0xF5D7D2, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Sodium, 1), new MaterialStack(Magnesium, 6), new MaterialStack(Silicon, 12), new MaterialStack(Hydrogen, 6), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 36)), GENERATE_ORE);
    public static DustMaterial FullersEarth = new DustMaterial(279, "fullers_earth", 0xA0A078, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Magnesium, 1), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 4), new MaterialStack(Oxygen, 11)), 0);
    public static DustMaterial Pitchblende = new DustMaterial(280, "pitchblende", 0xC8D200, MaterialIconSet.ROUGH, 3, of(new MaterialStack(Uraninite, 3), new MaterialStack(Thorium, 1), new MaterialStack(Lead, 1)), GENERATE_ORE);
    public static GemMaterial Monazite = new GemMaterial(281, "monazite", 0x324632, MaterialIconSet.GEM_VERTICAL, 1, of(new MaterialStack(RareEarth, 1), new MaterialStack(Phosphate, 1)), GENERATE_ORE | NO_SMASHING | NO_SMELTING | CRYSTALLISABLE);
    public static DustMaterial Malachite = new DustMaterial(282, "malachite", 0x055F05, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Copper, 2), new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 5)), GENERATE_ORE | INDUCTION_SMELTING_LOW_OUTPUT);
    public static DustMaterial Mirabilite = new DustMaterial(283, "mirabilite", 0xF0FAD2, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 4)), 0);
    public static DustMaterial Mica = new DustMaterial(284, "mica", 0xC3C3CD, MaterialIconSet.FINE, 1, of(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10)), 0);
    public static DustMaterial Trona = new DustMaterial(285, "trona", 0x87875F, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Sodium, 3), new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 6)), 0);
    public static DustMaterial Barite = new DustMaterial(286, "barite", 0xE6EBFF, MaterialIconSet.DULL, 2, of(new MaterialStack(Barium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)), GENERATE_ORE);
    public static DustMaterial Gypsum = new DustMaterial(287, "gypsum", 0xE6E6FA, MaterialIconSet.FINE, 1, of(new MaterialStack(Calcium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 4)), 0);
    public static DustMaterial Alunite = new DustMaterial(288, "alunite", 0xE1B441, MaterialIconSet.METALLIC, 2, of(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 14)), 0);
    public static DustMaterial Dolomite = new DustMaterial(289, "dolomite", 0xE1CDCD, MaterialIconSet.FLINT, 1, of(new MaterialStack(Calcium, 1), new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Oxygen, 6)), 0);
    public static DustMaterial Wollastonite = new DustMaterial(290, "wollastonite", 0xF0F0F0, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Calcium, 1), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 3)), 0);
    public static DustMaterial Zeolite = new DustMaterial(291, "zeolite", 0xF0E6E6, MaterialIconSet.ROUGH, 2, of(new MaterialStack(Sodium, 1), new MaterialStack(Calcium, 4), new MaterialStack(Silicon, 27), new MaterialStack(Aluminium, 9), new MaterialStack(Water, 28), new MaterialStack(Oxygen, 72)), 0);
    public static DustMaterial Kyanite = new DustMaterial(292, "kyanite", 0x6E6EFA, MaterialIconSet.FLINT, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 5)), 0);
    public static DustMaterial Kaolinite = new DustMaterial(293, "kaolinite", 0xF5EBEB, MaterialIconSet.DULL, 2, of(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9)), 0);
    public static DustMaterial Talc = new DustMaterial(294, "talc", 0x5AB45A, MaterialIconSet.FINE, 2, of(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Soapstone = new DustMaterial(295, "soapstone", 0x5F915F, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12)), GENERATE_ORE);
    public static DustMaterial Concrete = new DustMaterial(296, "concrete", 0x646464, MaterialIconSet.ROUGH, 1, of(new MaterialStack(Stone, 1)), NO_SMASHING | SMELT_INTO_FLUID);
    public static MetalMaterial IronMagnetic = new MetalMaterial(297, "iron_magnetic", 0xC8C8C8, MaterialIconSet.MAGNETIC, 2, of(new MaterialStack(Iron, 1)), EXT2_METAL | MORTAR_GRINDABLE, null, 6.0F, 2, 256);
    public static MetalMaterial SteelMagnetic = new MetalMaterial(298, "steel_magnetic", 0x808080, MaterialIconSet.MAGNETIC, 2, of(new MaterialStack(Steel, 1)), EXT2_METAL | MORTAR_GRINDABLE, null, 6.0F, 512, 1000);
    public static MetalMaterial NeodymiumMagnetic = new MetalMaterial(299, "neodymium_magnetic", 0x646464, MaterialIconSet.MAGNETIC, 2, of(new MaterialStack(Neodymium, 1)), EXT2_METAL | GENERATE_LONG_ROD, null, 7.0F, 512, 1297);
    public static MetalMaterial TungstenCarbide = new MetalMaterial(300, "tungsten_carbide", 0x330066, MaterialIconSet.METALLIC, 4, of(new MaterialStack(Tungsten, 1)), EXT2_METAL, null, 14.0F, 1280, 2460);
    public static MetalMaterial VanadiumSteel = new MetalMaterial(301, "vanadium_steel", 0xC0C0C0, MaterialIconSet.METALLIC, 3, of(new MaterialStack(Vanadium, 1), new MaterialStack(Chrome, 1), new MaterialStack(Steel, 7)), EXT2_METAL, null, 3.0F, 1920, 1453);
    public static MetalMaterial HSSG = new MetalMaterial(302, "hssg", 0x999900, MaterialIconSet.METALLIC, 3, of(new MaterialStack(TungstenSteel, 5), new MaterialStack(Chrome, 1), new MaterialStack(Molybdenum, 2), new MaterialStack(Vanadium, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD | GENERATE_ROUNDS, null, 10.0F, 4000, 4500);
    public static MetalMaterial HSSE = new MetalMaterial(303, "hsse", 0x336600, MaterialIconSet.METALLIC, 4, of(new MaterialStack(HSSG, 6), new MaterialStack(Cobalt, 1), new MaterialStack(Manganese, 1), new MaterialStack(Silicon, 1)), EXT2_METAL | GENERATE_RING | GENERATE_ROTOR | GENERATE_SMALL_GEAR | GENERATE_LONG_ROD | GENERATE_ROUNDS, null, 10.0F, 5120, 5400);
    public static MetalMaterial HSSS = new MetalMaterial(304, "hsss", 0x660033, MaterialIconSet.METALLIC, 4, of(new MaterialStack(HSSG, 6), new MaterialStack(Iridium, 2), new MaterialStack(Osmium, 1)), EXT2_METAL, null, 14.0F, 3000, 5400);

    /**
     * Clear matter materials
     */
    public static FluidMaterial UUAmplifier = new FluidMaterial(305, "uuamplifier", 0xAA00AA, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial UUMatter = new FluidMaterial(306, "uumatter", 0x770077, MaterialIconSet.FLUID, of(), 0);

    /**
     * Stargate materials
     */
    public static MetalMaterial Naquadah = new MetalMaterial(307, "naquadah", 0x323232, MaterialIconSet.METALLIC, 4, of(), EXT_METAL, null, 6.0F, 1280, 5400);
    public static MetalMaterial NaquadahAlloy = new MetalMaterial(308, "naquadah_alloy", 0x282828, MaterialIconSet.METALLIC, 5, of(new MaterialStack(Naquadah, 1), new MaterialStack(Osmiridium, 1)), EXT2_METAL, null, 8.0F, 5120, 7200);
    public static MetalMaterial NaquadahEnriched = new MetalMaterial(309, "naquadah_enriched", 0x282828, MaterialIconSet.METALLIC, 4, of(), EXT_METAL, null, 6.0F, 1280, 4500);
    public static MetalMaterial Naquadria = new MetalMaterial(310, "naquadria", 0x1E1E1E, MaterialIconSet.SHINY, 3, of(), EXT_METAL, null, 1.0F, 512, 9000);
    public static MetalMaterial Tritanium = new MetalMaterial(311, "tritanium", 0xFFFFFF, MaterialIconSet.METALLIC, 6, of(), EXT_METAL, null, 20.0F, 6, 10240);
    public static MetalMaterial Duranium = new MetalMaterial(312, "duranium", 0xFFFFFF, MaterialIconSet.METALLIC, 5, of(), EXT_METAL, null, 16.0F, 5, 5120);

    /**
     * Actual food
     */
    public static DustMaterial Cheese = new DustMaterial(334,  "cheese", 0xFFFF00, MaterialIconSet.ROUGH, 0, of(), 0);
    public static DustMaterial Chili = new DustMaterial(335, "chili", 0xC80000, MaterialIconSet.FINE, 0, of(), 0);
    public static DustMaterial Chocolate = new DustMaterial(336, "chocolate", 0xBE5F00, MaterialIconSet.ROUGH, 0, of(), 0);
    public static DustMaterial MeatRaw = new DustMaterial(337, "meat_raw", 0xFFC8C8, MaterialIconSet.ROUGH, 0, of(), NO_SMASHING);
    public static DustMaterial MeatCooked = new DustMaterial(338, "meat_cooked", 0x963C14, MaterialIconSet.ROUGH, 0, of(), NO_SMASHING | NO_SMELTING);
    public static FluidMaterial Milk = new FluidMaterial(339, "milk", 0xFEFEFE, MaterialIconSet.FINE, of(), 0);
    public static FluidMaterial FryingOilHot = new FluidMaterial(340, "frying_oil_hot", 0xC8C400, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial Honey = new FluidMaterial(341, "honey", 0xD2C800, MaterialIconSet.FLUID, of(), 0);
    public static FluidMaterial FishOil = new FluidMaterial(342, "fish_oil", 0xFFC400, MaterialIconSet.FLUID, of(), 0);
    public static DustMaterial Cocoa = new DustMaterial(343, "cocoa", 0xBE5F00, MaterialIconSet.ROUGH, 0, of(), 0);
    public static DustMaterial Coffee = new DustMaterial(345, "coffee", 0x964B00, MaterialIconSet.FINE, 0, of(), 0);
    public static DustMaterial Wheat = new DustMaterial(345, "wheat", 0xFFFFC4, MaterialIconSet.POWDER, 0, of(), 0);


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
            dustMaterial.add(BLAST_FURNACE_CALCITE_DOUBLE);
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Iron, PigIron, WroughtIron, BrownLimonite}) {
            dustMaterial.add(BLAST_FURNACE_CALCITE_TRIPLE);
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Gold, Silver, Osmium, Platinum, Cooperite}) {
            dustMaterial.washedIn = Mercury;
        }
        for (DustMaterial dustMaterial : new DustMaterial[]{Zinc, Nickel, Copper, Cobalt, Cobaltite, Tetrahedrite}) {
            dustMaterial.washedIn = SodiumPersulfate;
        }

        Neodymium.magneticMaterial = NeodymiumMagnetic;
        Steel.magneticMaterial = SteelMagnetic;
        Iron.magneticMaterial = IronMagnetic;

        NeodymiumMagnetic.setSmeltingInto(Neodymium).setArcSmeltingInto(Neodymium).setMaceratingInto(Neodymium);
        SteelMagnetic.setSmeltingInto(Steel).setArcSmeltingInto(Steel).setMaceratingInto(Steel);
        Iron.setSmeltingInto(Iron).setArcSmeltingInto(WroughtIron).setMaceratingInto(Iron);
        PigIron.setSmeltingInto(Iron).setArcSmeltingInto(WroughtIron).setMaceratingInto(Iron);
        WroughtIron.setSmeltingInto(Iron).setArcSmeltingInto(WroughtIron).setMaceratingInto(Iron);
        IronMagnetic.setSmeltingInto(Iron).setArcSmeltingInto(WroughtIron).setMaceratingInto(Iron);
        Copper.setSmeltingInto(Copper).setArcSmeltingInto(AnnealedCopper).setMaceratingInto(Copper);
        AnnealedCopper.setSmeltingInto(Copper).setArcSmeltingInto(AnnealedCopper).setMaceratingInto(Copper);

        Tetrahedrite.setDirectSmelting(Copper);
        Chalcopyrite.setDirectSmelting(Copper);
        Malachite.setDirectSmelting(Copper);
        Pentlandite.setDirectSmelting(Nickel);
        Sphalerite.setDirectSmelting(Zinc);
        Pyrite.setDirectSmelting(Iron);
        BasalticMineralSand.setDirectSmelting(Iron);
        GraniticMineralSand.setDirectSmelting(Iron);
        YellowLimonite.setDirectSmelting(Iron);
        BrownLimonite.setDirectSmelting(Iron);
        BandedIron.setDirectSmelting(Iron);
        Cassiterite.setDirectSmelting(Tin);
        CassiteriteSand.setDirectSmelting(Tin);
        Chromite.setDirectSmelting(Chrome);
        Garnierite.setDirectSmelting(Nickel);
        Cobaltite.setDirectSmelting(Cobalt);
        Stibnite.setDirectSmelting(Antimony);
        Cooperite.setDirectSmelting(Platinum);
        Pyrolusite.setDirectSmelting(Manganese);
        Magnesite.setDirectSmelting(Magnesium);
        Molybdenite.setDirectSmelting(Molybdenum);

        Salt.setOreMultiplier(2).setSmeltingMultiplier(2);
        RockSalt.setOreMultiplier(2).setSmeltingMultiplier(2);
        Scheelite.setOreMultiplier(2).setSmeltingMultiplier(2);
        Tungstate.setOreMultiplier(2).setSmeltingMultiplier(2);
        Cassiterite.setOreMultiplier(2).setSmeltingMultiplier(2);
        CassiteriteSand.setOreMultiplier(2).setSmeltingMultiplier(2);
        NetherQuartz.setOreMultiplier(2).setSmeltingMultiplier(2);
        CertusQuartz.setOreMultiplier(2).setSmeltingMultiplier(2);
        Phosphor.setOreMultiplier(3).setSmeltingMultiplier(3);
        Saltpeter.setOreMultiplier(4).setSmeltingMultiplier(4);
        Apatite.setOreMultiplier(4).setSmeltingMultiplier(4).setByProductMultiplier(2);
        Redstone.setOreMultiplier(5).setSmeltingMultiplier(5);
        Glowstone.setOreMultiplier(5).setSmeltingMultiplier(5);
        Lapis.setOreMultiplier(6).setSmeltingMultiplier(6).setByProductMultiplier(4);
        Sodalite.setOreMultiplier(6).setSmeltingMultiplier(6).setByProductMultiplier(4);
        Lazurite.setOreMultiplier(6).setSmeltingMultiplier(6).setByProductMultiplier(4);
        Monazite.setOreMultiplier(8).setSmeltingMultiplier(8).setByProductMultiplier(2);

        Vinteum.addEnchantmentForTools(Enchantments.FORTUNE, 1);

        EnderPearl.addEnchantmentForTools(Enchantments.SILK_TOUCH, 1);
        NetherStar.addEnchantmentForTools(Enchantments.SILK_TOUCH, 1);

        BlackBronze.addEnchantmentForTools(Enchantments.SMITE, 2);
        Gold.addEnchantmentForTools(Enchantments.SMITE, 3);
        RoseGold.addEnchantmentForTools(Enchantments.SMITE, 4);
        Platinum.addEnchantmentForTools(Enchantments.SMITE, 5);

        Lead.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 2);
        Nickel.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 2);
        Invar.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 3);
        Antimony.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 3);
        BatteryAlloy.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 4);
        Bismuth.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 4);
        BismuthBronze.addEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 5);

        Iron.addEnchantmentForTools(Enchantments.SHARPNESS, 1);
        Bronze.addEnchantmentForTools(Enchantments.SHARPNESS, 1);
        Brass.addEnchantmentForTools(Enchantments.SHARPNESS, 2);
        Steel.addEnchantmentForTools(Enchantments.SHARPNESS, 2);
        WroughtIron.addEnchantmentForTools(Enchantments.SHARPNESS, 2);
        StainlessSteel.addEnchantmentForTools(Enchantments.SHARPNESS, 3);
        BlackSteel.addEnchantmentForTools(Enchantments.SHARPNESS, 4);
        RedSteel.addEnchantmentForTools(Enchantments.SHARPNESS, 4);
        BlueSteel.addEnchantmentForTools(Enchantments.SHARPNESS, 5);
        DamascusSteel.addEnchantmentForTools(Enchantments.SHARPNESS, 5);
        TungstenCarbide.addEnchantmentForTools(Enchantments.SHARPNESS, 5);
        HSSE.addEnchantmentForTools(Enchantments.SHARPNESS, 5);
        HSSG.addEnchantmentForTools(Enchantments.SHARPNESS, 4);
        HSSS.addEnchantmentForTools(Enchantments.SHARPNESS, 5);

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
        Olivine.addOreByProducts(Pyrope, Magnesium);
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
        //Endstone.addOreByProducts(Helium3); //FIXME
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
        Cassiterite.addOreByProducts(Tin);
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
        Salt.addOreByProducts(RockSalt);
        RockSalt.addOreByProducts(Salt);
        Andesite.addOreByProducts(Basalt);
        Diorite.addOreByProducts(NetherQuartz);
    }
}
