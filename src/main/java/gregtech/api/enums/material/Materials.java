package gregtech.api.enums.material;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.Element;
import gregtech.api.enums.material.types.DustMaterial;
import gregtech.api.enums.material.types.FluidMaterial;
import gregtech.api.enums.material.types.Material;
import gregtech.api.enums.material.types.MetalMaterial;
import net.minecraft.init.Enchantments;

import static com.google.common.collect.ImmutableList.of;
import static gregtech.api.enums.material.types.DustMaterial.MatFlags.GENERATE_PLATE;
import static gregtech.api.enums.material.types.MetalMaterial.MatFlags.GENERATE_BOLT_SCREW;
import static gregtech.api.enums.material.types.MetalMaterial.MatFlags.GENERATE_SMALL_GEAR;
import static gregtech.api.enums.material.types.SolidMaterial.MatFlags.*;
import static gregtech.api.enums.material.MaterialIconSet.*;

public class Materials {

    private static final int STD_METAL = GENERATE_PLATE;
    private static final int EXT_METAL = STD_METAL | GENERATE_ROD | GENERATE_BOLT_SCREW;

    /**
     * Direct Elements
     */
    public static MetalMaterial Aluminium = new MetalMaterial(1, "aluminium", "Aluminium", 0xCCCCFF, DULL, of(), EXT_METAL | GENERATE_SMALL_GEAR | GENERATE_GEAR, Element.Al, 0, 0, 0, 1700);
    public static MetalMaterial Americium = new MetalMaterial(2, "americium", "Americium", 0xDDDDDD, METALLIC, of(), 0, Element.Am, 8.0f, 4, 512000);
    public static MetalMaterial Antimony = new MetalMaterial(3, "antimony", "Antimony", 0xCCCCDD, SHINY, of(), EXT_METAL, Element.Sb);
    public static FluidMaterial Argon = new FluidMaterial(4, "argon", "Argon", 0xBBBB00, FLUID, of(), 0, Element.Ar);
    public static DustMaterial Arsenic = new DustMaterial(5, "arsenic", "Arsenic", 0xFFFFFF, DULL, of(), 0, Element.As);
    public static MetalMaterial Barium = new MetalMaterial(6, "barium", "Barium", 0xFFFFFF, SHINY, of(), 0, Element.Ba);
    public static MetalMaterial Beryllium = new MetalMaterial(7, "beryllium", "Beryllium", 0xAAFFAA, METALLIC, of(), STD_METAL, Element.Be, 7.2f, 2, 56000);
    public static MetalMaterial Bismuth = new MetalMaterial(8, "bismuth", "Bismuth", 0xAADDDD, METALLIC, of(), 0, Element.Bi, 5.0f, 1, 9000);
    public static DustMaterial Boron = new DustMaterial(9, "boron", "Boron", 0xFCFCFC, DULL, of(), 0, Element.B);
    public static MetalMaterial Caesium = new MetalMaterial(10, "caesium", "Caesium", 0xFFFFFC, DULL, of(), 0, Element.Cs);
    public static MetalMaterial Calcium = new MetalMaterial(11, "calcium", "Calcium", 0xDDDDAA, METALLIC, of(), 0, Element.Ca);
    public static MetalMaterial Carbon = new MetalMaterial(12, "carbon", "Carbon", 0x555555, DULL, of(), 0, Element.C);
    public static MetalMaterial Cadmium = new MetalMaterial(13, "cadmium", "Cadmium", 0x505060, SHINY, of(), 0, Element.Ca);
    public static MetalMaterial Cerium = new MetalMaterial(14, "cerium", "Cerium", 0xEEEEEE, METALLIC, of(), 0, Element.Ce);
    public static FluidMaterial Chlorine = new FluidMaterial(15, "chlorine", "Chlorine", 0xEEEECC, FLUID, of(), 0, Element.Cl);
    public static MetalMaterial Chrome = new MetalMaterial(16, "chrome", "Chrome", 0xFFAAAB, SHINY, of(), STD_METAL, Element.Cr, 8.0f, 4, 396000, 2300);
    public static MetalMaterial Cobalt = new MetalMaterial(17, "cobalt", "Cobalt", 0xAAAAFF, METALLIC, of(), STD_METAL, Element.Co, 9.0f, 3, 256000);
    public static MetalMaterial Copper = new MetalMaterial(18, "copper", "Copper", 0xFF8000, SHINY, of(), STD_METAL | GENERATE_GEAR, Element.Cu);
    public static FluidMaterial Deuterium = new FluidMaterial(19, "deuterium", "Deuterium", 0xEEEE00, FLUID, of(), 0, Element.D);
    public static MetalMaterial Dysprosium = new MetalMaterial(20, "dysprosium", "Dysprosium", 0xFFFFEE, SHINY, of(), 0, Element.Dy);

    public static Material Erbium = new Material(75, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Erbium", "Erbium", 0, 0, 1802, 1802, true, false, 4, 1, 1, Dyes._NULL, Element.Er)
    public static Material Europium = new Material(70, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Europium", "Europium", 0, 0, 1099, 1099, true, false, 4, 1, 1, Dyes._NULL, Element.Eu)
    public static Material Fluorine = new Material(14, MaterialIconSet.FLUID, 		1.0F, 0, 2, 16|32, 255, 255, 255, 127, "Fluorine", "Fluorine", 0, 0, 53, 0, false, true, 2, 1, 1, Dyes.dyeGreen, Element.F)
    public static Material Gadolinium = new Material(71, MaterialIconSet.METALLIC, 1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Gadolinium", "Gadolinium", 0, 0, 1585, 1585, true, false, 4, 1, 1, Dyes._NULL, Element.Gd)
    public static Material Gallium = new Material(37, MaterialIconSet.SHINY, 		1.0F, 64, 2, 1|2|32, 220, 220, 255, 0, "Gallium", "Gallium", 0, 0, 302, 0, false, false, 5, 1, 1, Dyes.dyeLightGray, Element.Ga)
    public static Material Gold = new Material(86, MaterialIconSet.SHINY, 		   12.0F, 64, 2, 1|2|8|32|64|128, 255, 255, 30, 0, "Gold", "Gold", 0, 0, 1337, 0, false, false, 4, 1, 1, Dyes.dyeYellow, Element.Au)
    public static Material Holmium = new Material(74, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Holmium", "Holmium", 0, 0, 1734, 1734, true, false, 4, 1, 1, Dyes._NULL, Element.Ho)
    public static Material Hydrogen = new Material(1, MaterialIconSet.FLUID, 		1.0F, 0, 2, 16|32, 0, 0, 255, 240, "Hydrogen", "Hydrogen", 1, 15, 14, 0, false, true, 2, 1, 1, Dyes.dyeBlue, Element.H)
    public static Material Helium = new Material(4, MaterialIconSet.FLUID, 		1.0F, 0, 2, 16|32, 255, 255, 0, 240, "Helium", "Helium", 0, 0, 1, 0, false, true, 5, 1, 1, Dyes.dyeYellow, Element.He)
    public static Material Helium_3 = new Material(5, MaterialIconSet.FLUID, 		1.0F, 0, 2, 16|32, 255, 255, 0, 240, "Helium_3", "Helium-3", 0, 0, 1, 0, false, true, 10, 1, 1, Dyes.dyeYellow, Element.He_3)
    public static Material Indium = new Material(56, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 64, 0, 128, 0, "Indium", "Indium", 0, 0, 429, 0, false, false, 4, 1, 1, Dyes.dyeGray, Element.In)
    public static Material Iridium = new Material(84, MaterialIconSet.DULL, 		6.0F, 2560, 3, 1|2|8|32|64|128, 240, 240, 245, 0, "Iridium", "Iridium", 0, 0, 2719, 2719, true, false, 10, 1, 1, Dyes.dyeWhite, Element.Ir)
    public static Material Iron = new Material(32, MaterialIconSet.METALLIC, 		6.0F, 256, 2, 1|2|8|32|64|128, 200, 200, 200, 0, "Iron", "Iron", 0, 0, 1811, 0, false, false, 3, 1, 1, Dyes.dyeLightGray, Element.Fe)
    public static Material Lanthanum = new Material(64, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Lanthanum", "Lanthanum", 0, 0, 1193, 1193, true, false, 4, 1, 1, Dyes._NULL, Element.La)
    public static Material Lead = new Material(89, MaterialIconSet.DULL, 			8.0F, 64, 1, 1|2|8|32|64|128, 140, 100, 140, 0, "Lead", "Lead", 0, 0, 600, 0, false, false, 3, 1, 1, Dyes.dyePurple, Element.Pb)
    public static Material Lithium = new Material(6, MaterialIconSet.DULL, 		1.0F, 0, 2, 1|2|8|32, 225, 220, 255, 0, "Lithium", "Lithium", 0, 0, 454, 0, false, false, 4, 1, 1, Dyes.dyeLightBlue, Element.Li)
    public static Material Lutetium = new Material(78, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Lutetium", "Lutetium", 0, 0, 1925, 1925, true, false, 4, 1, 1, Dyes._NULL, Element.Lu)
    public static Material Magic = new Material(-128, MaterialIconSet.SHINY, 		8.0F, 5120, 5, 1|2|4|16|32|64|128, 100, 0, 200, 0, "Magic", "Magic", 5, 32, 5000, 0, false, false, 7, 1, 1, Dyes.dyePurple, Element.Ma)
    public static Material Magnesium = new Material(18, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 200, 200, 0, "Magnesium", "Magnesium", 0, 0, 923, 0, false, false, 3, 1, 1, Dyes.dyePink, Element.Mg)
    public static Material Manganese = new Material(31, MaterialIconSet.DULL, 		7.0F, 512, 2, 1|2|8|32|64, 250, 250, 250, 0, "Manganese", "Manganese", 0, 0, 1519, 0, false, false, 3, 1, 1, Dyes.dyeWhite, Element.Mn)
    public static Material Mercury = new Material(87, MaterialIconSet.SHINY, 		1.0F, 0, 0, 16|32, 255, 220, 220, 0, "Mercury", "Mercury", 5, 32, 234, 0, false, false, 3, 1, 1, Dyes.dyeLightGray, Element.Hg)
    public static Material Molybdenum = new Material(48, MaterialIconSet.SHINY, 	7.0F, 512, 2, 1|2|8|32|64, 180, 180, 220, 0, "Molybdenum", "Molybdenum", 0, 0, 2896, 0, false, false, 1, 1, 1, Dyes.dyeBlue, Element.Mo)
    public static Material Neodymium = new Material(67, MaterialIconSet.METALLIC, 	7.0F, 512, 2, 1|2|8|32|64|128, 100, 100, 100, 0, "Neodymium", "Neodymium", 0, 0, 1297, 1297, true, false, 4, 1, 1, Dyes._NULL, Element.Nd)
    public static Material Neutronium = new Material(129, MaterialIconSet.DULL,   24.0F, 655360, 6, 1|2|32|64|128, 250, 250, 250, 0, "Neutronium", "Neutronium", 0, 0, 10000, 0, false, false, 20, 1, 1, Dyes.dyeWhite, Element.Nt)
    public static Material Nickel = new Material(34, MaterialIconSet.METALLIC, 	6.0F, 64, 2, 1|2|8|32|64|128, 200, 200, 250, 0, "Nickel", "Nickel", 0, 0, 1728, 0, false, false, 4, 1, 1, Dyes.dyeLightBlue, Element.Ni)
    public static Material Niobium = new Material(47, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 190, 180, 200, 0, "Niobium", "Niobium", 0, 0, 2750, 2750, true, false, 5, 1, 1, Dyes._NULL, Element.Nb)
    public static Material Nitrogen = new Material(12, MaterialIconSet.FLUID, 		1.0F, 0, 2, 16|32, 0, 150, 200, 240, "Nitrogen", "Nitrogen", 0, 0, 63, 0, false, true, 2, 1, 1, Dyes.dyeCyan, Element.N)
    public static Material Osmium = new Material(83, MaterialIconSet.METALLIC, 	16.0F, 1280, 4, 1|2|8|32|64|128, 50, 50, 255, 0, "Osmium", "Osmium", 0, 0, 3306, 3306, true, false, 10, 1, 1, Dyes.dyeBlue, Element.Os)
    public static Material Oxygen = new Material(13, MaterialIconSet.FLUID, 		1.0F, 0, 2, 16|32, 0, 100, 200, 240, "Oxygen", "Oxygen", 0, 0, 54, 0, false, true, 1, 1, 1, Dyes.dyeWhite, Element.O)
    public static Material Palladium = new Material(52, MaterialIconSet.SHINY, 	8.0F, 512, 2, 1|2|8|32|64|128, 128, 128, 128, 0, "Palladium", "Palladium", 0, 0, 1828, 1828, true, false, 4, 1, 1, Dyes.dyeGray, Element.Pd)
    public static Material Phosphor = new Material(21, MaterialIconSet.DULL, 		1.0F, 0, 2, 1|32, 255, 255, 0, 0, "Phosphor", "Phosphor", 0, 0, 317, 0, false, false, 2, 1, 1, Dyes.dyeYellow, Element.P)
    public static Material Platinum = new Material(85, MaterialIconSet.SHINY, 		12.0F, 64, 2, 1|2|8|32|64|128, 255, 255, 200, 0, "Platinum", "Platinum", 0, 0, 2041, 0, false, false, 6, 1, 1, Dyes.dyeOrange, Element.Pt)
    public static Material Plutonium = new Material(100, MaterialIconSet.METALLIC, 6.0F, 512, 3, 1|2|8|32|64, 240, 50, 50, 0, "Plutonium", "Plutonium 244", 0, 0, 912, 0, false, false, 6, 1, 1, Dyes.dyeLime, Element.Pu)
    public static Material Plutonium241 = new Material(101, MaterialIconSet.SHINY, 6.0F, 512, 3, 1|2|32|64, 250, 70, 70, 0, "Plutonium241", "Plutonium 241", 0, 0, 912, 0, false, false, 6, 1, 1, Dyes.dyeLime, Element.Pu_241)
    public static Material Potassium = new Material(25, MaterialIconSet.METALLIC, 	1.0F, 0, 1, 1|2|32, 250, 250, 250, 0, "Potassium", "Potassium", 0, 0, 336, 0, false, false, 2, 1, 1, Dyes.dyeWhite, Element.K)
    public static Material Praseodymium =new Material(66, MaterialIconSet.METALLIC,1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Praseodymium", "Praseodymium", 0, 0, 1208, 1208, true, false, 4, 1, 1, Dyes._NULL, Element.Pr)
    public static Material Promethium = new Material(68, MaterialIconSet.METALLIC, 1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Promethium", "Promethium", 0, 0, 1315, 1315, true, false, 4, 1, 1, Dyes._NULL, Element.Pm)
    public static Material Radon = new Material(93, MaterialIconSet.FLUID, 		1.0F, 0, 2, 16|32, 255, 0, 255, 240, "Radon", "Radon", 0, 0, 202, 0, false, true, 5, 1, 1, Dyes.dyePurple, Element.Rn)
    public static Material Rubidium = new Material(43, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 240, 30, 30, 0, "Rubidium", "Rubidium", 0, 0, 312, 0, false, false, 4, 1, 1, Dyes.dyeRed, Element.Rb)
    public static Material Samarium = new Material(69, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Samarium", "Samarium", 0, 0, 1345, 1345, true, false, 4, 1, 1, Dyes._NULL, Element.Sm)
    public static Material Scandium = new Material(27, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Scandium", "Scandium", 0, 0, 1814, 1814, true, false, 2, 1, 1, Dyes.dyeYellow, Element.Sc)
    public static Material Silicon = new Material(20, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 60, 60, 80, 0, "Silicon", "Silicon", 0, 0, 1687, 1687, true, false, 1, 1, 1, Dyes.dyeBlack, Element.Si)
    public static Material Silver = new Material(54, MaterialIconSet.SHINY, 		10.0F, 64, 2, 1|2|8|32|64|128, 220, 220, 255, 0, "Silver", "Silver", 0, 0, 1234, 0, false, false, 3, 1, 1, Dyes.dyeLightGray, Element.Ag)
    public static Material Sodium = new Material(17, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1 |32, 0, 0, 150, 0, "Sodium", "Sodium", 0, 0, 370, 0, false, false, 1, 1, 1, Dyes.dyeBlue, Element.Na)
    public static Material Strontium = new Material(44, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|32, 200, 200, 200, 0, "Strontium", "Strontium", 0, 0, 1050, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Element.Sr)
    public static Material Sulfur = new Material(22, MaterialIconSet.DULL, 		1.0F, 0, 2, 1 |8|32, 200, 200, 0, 0, "Sulfur", "Sulfur", 0, 0, 388, 0, false, false, 2, 1, 1, Dyes.dyeYellow, Element.S)
    public static Material Tantalum = new Material(80, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Tantalum", "Tantalum", 0, 0, 3290, 0, false, false, 4, 1, 1, Dyes._NULL, Element.Ta)
    public static Material Tellurium = new Material(59, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Tellurium", "Tellurium", 0, 0, 722, 0, false, false, 4, 1, 1, Dyes.dyeGray, Element.Te)
    public static Material Terbium = new Material(72, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Terbium", "Terbium", 0, 0, 1629, 1629, true, false, 4, 1, 1, Dyes._NULL, Element.Tb)
    public static Material Thorium = new Material(96, MaterialIconSet.SHINY, 		6.0F, 512, 2, 1|2|8|32|64, 0, 30, 0, 0, "Thorium", "Thorium", 0, 0, 2115, 0, false, false, 4, 1, 1, Dyes.dyeBlack, Element.Th)
    public static Material Thulium = new Material(76, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Thulium", "Thulium", 0, 0, 1818, 1818, true, false, 4, 1, 1, Dyes._NULL, Element.Tm)
    public static Material Tin = new Material(57, MaterialIconSet.DULL, 			1.0F, 0, 1, 1|2|8|32|128, 220, 220, 220, 0, "Tin", "Tin", 0, 0, 505, 505, false, false, 3, 1, 1, Dyes.dyeWhite, Element.Sn)
    public static Material Titanium = new Material(28, MaterialIconSet.METALLIC, 	7.0F, 1600, 3, 1|2|8|32|64|128, 220, 160, 240, 0, "Titanium", "Titanium", 0, 0, 1941, 1940, true, false, 5, 1, 1, Dyes.dyePurple, Element.Ti)
    public static Material Tritium = new Material(3, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 16|32, 255, 0, 0, 240, "Tritium", "Tritium", 0, 0, 14, 0, false, true, 10, 1, 1, Dyes.dyeRed, Element.T)
    public static Material Tungsten = new Material(81, MaterialIconSet.METALLIC, 	7.0F, 2560, 3, 1|2|32|64|128, 50, 50, 50, 0, "Tungsten", "Tungsten", 0, 0, 3695, 3000, true, false, 4, 1, 1, Dyes.dyeBlack, Element.W)
    public static Material Uranium = new Material(98, MaterialIconSet.METALLIC, 	6.0F, 512, 3, 1|2|8|32|64, 50, 240, 50, 0, "Uranium", "Uranium 238", 0, 0, 1405, 0, false, false, 4, 1, 1, Dyes.dyeGreen, Element.U)
    public static Material Uranium235 = new Material(97, MaterialIconSet.SHINY, 	6.0F, 512, 3, 1|2|8|32|64, 70, 250, 70, 0, "Uranium235", "Uranium 235", 0, 0, 1405, 0, false, false, 4, 1, 1, Dyes.dyeGreen, Element.U_235)
    public static Material Vanadium = new Material(29, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 50, 50, 50, 0, "Vanadium", "Vanadium", 0, 0, 2183, 2183, true, false, 2, 1, 1, Dyes.dyeBlack, Element.V)
    public static Material Ytterbium = new Material(77, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Ytterbium", "Ytterbium", 0, 0, 1097, 1097, true, false, 4, 1, 1, Dyes._NULL, Element.Yb)
    public static Material Yttrium = new Material(45, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1|2|32, 220, 250, 220, 0, "Yttrium", "Yttrium", 0, 0, 1799, 1799, true, false, 4, 1, 1, Dyes._NULL, Element.Y)
    public static Material Zinc = new Material(36, MaterialIconSet.METALLIC, 		1.0F, 0, 1, 1|2|8|32, 250, 240, 240, 0, "Zinc", "Zinc", 0, 0, 692, 0, false, false, 2, 1, 1, Dyes.dyeWhite, Element.Zn)

    /**
     * First Degree Compounds
     */
    public static Material Methane = new Material(715, MaterialIconSet.FLUID, 			1.0F, 0, 1, 16, 255, 255, 255, 0, "Methane", "Methane", 1, 45, -1, 0, false, false, 3, 1, 1, Dyes.dyeMagenta, 1)
    public static Material CarbonDioxide = new Material(497, MaterialIconSet.FLUID, 	1.0F, 0, 2, 16|32, 169, 208, 245, 240, "CarbonDioxide", "Carbon Dioxide", 0, 0, 25, 1, false, true, 1, 1, 1, Dyes.dyeLightBlue, 1)
    public static Material NobleGases = new Material(496, MaterialIconSet.FLUID, 		1.0F, 0, 2, 16|32, 169, 208, 245, 240, "NobleGases", "Noble Gases", 0, 0, 4, 0, false, true, 1, 1, 1, Dyes.dyeLightBlue, 2)
    public static Material Air = new Material(120, MaterialIconSet.FLUID, 				1.0F, 0, 2, 16|32, 169, 208, 245, 240, "Air", "Air", 0, 0, -1, 0, false, true, 1, 1, 1, Dyes.dyeLightBlue, 0)
    public static Material LiquidAir = new Material(495, MaterialIconSet.FLUID, 		1.0F, 0, 2, 16|32, 169, 208, 245, 240, "LiquidAir", "Liquid Air", 0, 0, 4, 0, false, true, 1, 1, 1, Dyes.dyeLightBlue, 2)
    public static Material Almandine = new Material(820, MaterialIconSet.ROUGH, 		1.0F, 0, 1, 1 |8 , 255, 0, 0, 0, "Almandine", "Almandine", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeRed, 1)
    public static Material Andradite = new Material(821, MaterialIconSet.ROUGH, 		1.0F, 0, 1, 1, 150, 120, 0, 0, "Andradite", "Andradite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow, 1)
    public static Material AnnealedCopper = new Material(345, MaterialIconSet.SHINY, 	1.0F, 0, 2, 1|2|128, 255, 120, 20, 0, "AnnealedCopper", "Annealed Copper", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeOrange, 2)
    public static Material Asbestos = new Material(946, MaterialIconSet.DULL, 			1.0F, 0, 1, 1, 230, 230, 230, 0, "Asbestos", "Asbestos", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 1)
    public static Material Ash = new Material(815, MaterialIconSet.DULL, 				1.0F, 0, 1, 1, 150, 150, 150, 0, "Ash", "Ashes", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, 2)
    public static Material BandedIron = new Material(917, MaterialIconSet.DULL, 		1.0F, 0, 2, 1 |8 , 145, 90, 90, 0, "BandedIron", "Banded Iron", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, 1)
    public static Material BatteryAlloy = new Material(315, MaterialIconSet.DULL, 		1.0F, 0, 1, 1|2, 156, 124, 160, 0, "BatteryAlloy", "Battery Alloy", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePurple, 2)
    public static Material BlueTopaz = new Material(513, MaterialIconSet.GEM_HORIZONTAL,7.0F, 256, 3, 1|4|8 |64, 0, 0, 255, 127, "BlueTopaz", "Blue Topaz", 0, 0, -1, 0, false, true, 3, 1, 1, Dyes.dyeBlue, 1)
    public static Material Bone = new Material(806, MaterialIconSet.DULL, 				1.0F, 0, 1, 1, 250, 250, 250, 0, "Bone", "Bone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0)
    public static Material Brass = new Material(301, MaterialIconSet.METALLIC, 		7.0F, 96, 1, 1|2|64|128, 255, 180, 0, 0, "Brass", "Brass", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, 2)
    public static Material Bronze = new Material(300, MaterialIconSet.METALLIC, 		6.0F, 192, 2, 1|2|64|128, 255, 128, 0, 0, "Bronze", "Bronze", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 2)
    public static Material BrownLimonite = new Material(930, MaterialIconSet.METALLIC, 1.0F, 0, 1, 1 |8 , 200, 100, 0, 0, "BrownLimonite", "Brown Limonite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, 2)
    public static Material Calcite = new Material(823, MaterialIconSet.DULL, 			1.0F, 0, 1, 1 |8 , 250, 230, 220, 0, "Calcite", "Calcite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 1)
    public static Material Cassiterite = new Material(824, MaterialIconSet.METALLIC, 	1.0F, 0, 1, 8 , 220, 220, 220, 0, "Cassiterite", "Cassiterite", 0, 0, -1, 0, false, false, 4, 3, 1, Dyes.dyeWhite, 1)
    public static Material CassiteriteSand = new Material(937, MaterialIconSet.SAND, 	1.0F, 0, 1, 8 , 220, 220, 220, 0, "CassiteriteSand", "Cassiterite Sand", 0, 0, -1, 0, false, false, 4, 3, 1, Dyes.dyeWhite, 1)
    public static Material Chalcopyrite = new Material(855, MaterialIconSet.DULL, 		1.0F, 0, 1, 1 |8 , 160, 120, 40, 0, "Chalcopyrite", "Chalcopyrite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, 1)
    public static Material Charcoal = new Material(536, MaterialIconSet.FINE, 			1.0F, 0, 1, 1|4, 100, 70, 70, 0, "Charcoal", "Charcoal", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 1)
    public static Material Chromite = new Material(825, MaterialIconSet.METALLIC, 		1.0F, 0, 1, 1|8, 35, 20, 15, 0, "Chromite", "Chromite", 0, 0, 1700, 1700, true, false, 6, 1, 1, Dyes.dyePink, 1)
    public static Material ChromiumDioxide  = new Material(361, MaterialIconSet.DULL, 11.0F, 256, 3, 1|2, 230, 200, 200, 0, "ChromiumDioxide", "Chromium Dioxide", 0, 0, 650, 650, false, false, 5, 3, 1, Dyes.dyePink , 1)
    public static Material Cinnabar = new Material(826, MaterialIconSet.ROUGH, 		1.0F, 0, 1, 1 |8 , 150, 0, 0, 0, "Cinnabar", "Cinnabar", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBrown, 2)
    public static Material Water = new Material(701, MaterialIconSet.FLUID, 			1.0F, 0, 0, 16, 0, 0, 255, 0, "Water", "Water", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 0)
    public static Material Clay = new Material(805, MaterialIconSet.ROUGH, 			1.0F, 0, 1, 1, 200, 200, 220, 0, "Clay", "Clay", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeLightBlue, 1)
    public static Material Coal = new Material(535, MaterialIconSet.ROUGH, 			1.0F, 0, 1, 1|4|8, 70, 70, 70, 0, "Coal", "Coal", 0, 0, -1, 0, false, false, 2, 2, 1, Dyes.dyeBlack, 1)
    public static Material Cobaltite = new Material(827, MaterialIconSet.METALLIC, 	1.0F, 0, 1, 1 |8 , 80, 80, 250, 0, "Cobaltite", "Cobaltite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlue, 1)
    public static Material Cooperite = new Material(828, MaterialIconSet.METALLIC, 	1.0F, 0, 1, 1 |8 , 255, 255, 200, 0, "Cooperite", "Sheldonite", 0, 0, -1, 0, false, false, 5, 1, 1, Dyes.dyeYellow, 2)
    public static Material Cupronickel = new Material(310, MaterialIconSet.METALLIC, 	6.0F, 64, 1, 1|2|64, 227, 150, 128, 0, "Cupronickel", "Cupronickel", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 2)
    public static Material DarkAsh = new Material(816, MaterialIconSet.DULL, 			1.0F, 0, 1, 1, 50, 50, 50, 0, "DarkAsh", "Dark Ashes", 0, 0, -1, 0, false, false, 1, 2, 1, Dyes.dyeGray, 1)
    public static Material Diamond = new Material(500, MaterialIconSet.DIAMOND, 		8.0F, 1280, 3, 1|4|8 |64|128, 200, 255, 255, 127, "Diamond", "Diamond", 0, 0, -1, 0, false, true, 5, 64, 1, Dyes.dyeWhite, 1)
    public static Material Electrum = new Material(303, MaterialIconSet.SHINY, 	   12.0F, 64, 2, 1|2|64|128, 255, 255, 100, 0, "Electrum", "Electrum", 0, 0, -1, 0, false, false, 4, 1, 1, Dyes.dyeYellow, 2)
    public static Material Emerald = new Material(501, MaterialIconSet.EMERALD, 		7.0F, 256, 2, 1|4|8 |64, 80, 255, 80, 127, "Emerald", "Emerald", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeGreen, 1)
    public static Material FreshWater = new Material(-1, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 0, 0, 255, 0, "FreshWater", "Fresh Water", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 0)
    public static Material Galena = new Material(830, MaterialIconSet.DULL, 			1.0F, 0, 3, 1 |8 , 100, 60, 100, 0, "Galena", "Galena", 0, 0, -1, 0, false, false, 4, 1, 1, Dyes.dyePurple, 1)
    public static Material Garnierite = new Material(906, MaterialIconSet.METALLIC, 	1.0F, 0, 3, 1 |8 , 50, 200, 70, 0, "Garnierite", "Garnierite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightBlue, 1)
    public static Material Glyceryl = new Material(714, MaterialIconSet.FLUID, 		1.0F, 0, 1, 16, 0, 150, 150, 0, "Glyceryl", "Glyceryl Trinitrate", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeCyan, 1)
    public static Material GreenSapphire = new Material(504, MaterialIconSet.GEM_HORIZONTAL, 7.0F, 256, 2, 1|4|8 |64, 100, 200, 130, 127, "GreenSapphire", "Green Sapphire", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeCyan, 1)
    public static Material Grossular = new Material(831, MaterialIconSet.ROUGH, 		1.0F, 0, 1, 1 |8 , 200, 100, 0, 0, "Grossular", "Grossular", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeOrange, 1)
    public static Material HolyWater = new Material(729, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 0, 0, 255, 0, "HolyWater", "Holy Water", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 0)
    public static Material Ice = new Material(702, MaterialIconSet.SHINY, 				1.0F, 0, 0, 1| 16, 200, 200, 255, 0, "Ice", "Ice", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 0)
    public static Material Ilmenite = new Material(918, MaterialIconSet.METALLIC, 		1.0F, 0, 3, 1 |8 , 70, 55, 50, 0, "Ilmenite", "Ilmenite", 0, 0, -1, 0, false, false, 1, 2, 1, Dyes.dyePurple, 0)
    public static Material Rutile = new Material(375, MaterialIconSet.GEM_HORIZONTAL, 	1.0F, 0, 2, 1, 212, 13, 92, 0, "Rutile", "Rutile", 0, 0, -1, 0, false, false, 1, 2, 1, Dyes.dyeRed, 0)
    public static Material Bauxite = new Material(822, MaterialIconSet.DULL, 			1.0F, 0, 1, 1 |8 , 200, 100, 0, 0, "Bauxite", "Bauxite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBrown, 1)
    public static Material Titaniumtetrachloride =new Material(376, MaterialIconSet.FLUID,1.0F, 0, 2, 16 , 212, 13, 92, 0, "Titaniumtetrachloride", "Titaniumtetrachloride", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed, 0)
    public static Material Magnesiumchloride = new Material(377, MaterialIconSet.DULL, 1.0F, 0, 2, 1|16 , 212, 13, 92, 0, "Magnesiumchloride", "Magnesiumchloride", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed, 0)
    public static Material Invar = new Material(302, MaterialIconSet.METALLIC, 		6.0F, 256, 2, 1|2|64|128, 180, 180, 120, 0, "Invar", "Invar", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, 2)
    public static Material IronCompressed = new Material(-1, MaterialIconSet.METALLIC, 7.0F, 96, 1, 1|2|64|128, 128, 128, 128, 0, "IronCompressed", "Compressed Iron", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 2)
    public static Material Kanthal = new Material(312, MaterialIconSet.METALLIC, 		6.0F, 64, 2, 1|2|64, 194, 210, 223, 0, "Kanthal", "Kanthal", 0, 0, 1800, 1800, true, false, 1, 1, 1, Dyes.dyeYellow, 2)
    public static Material Lazurite = new Material(524, MaterialIconSet.LAPIS, 		1.0F, 0, 1, 1|4|8 , 100, 120, 255, 0, "Lazurite", "Lazurite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeCyan, 1)
    public static Material Magnalium = new Material(313, MaterialIconSet.DULL, 		6.0F, 256, 2, 1|2|64|128, 200, 190, 255, 0, "Magnalium", "Magnalium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightBlue, 2)
    public static Material Magnesite = new Material(908, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1 |8 , 250, 250, 180, 0, "Magnesite", "Magnesite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink, 1)
    public static Material Magnetite = new Material(870, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1 |8 , 30, 30, 30, 0, "Magnetite", "Magnetite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 1)
    public static Material Molybdenite = new Material(942, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1 |8 , 25, 25, 25, 0, "Molybdenite", "Molybdenite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 1)
    public static Material Nichrome = new Material(311, MaterialIconSet.METALLIC, 		6.0F, 64, 2, 1|2|64, 205, 206, 246, 0, "Nichrome", "Nichrome", 0, 0, 2700, 2700, true, false, 1, 1, 1, Dyes.dyeRed, 2)
    public static Material NiobiumNitride = new Material(359, MaterialIconSet.DULL, 	1.0F, 0, 2, 1|2, 29, 41, 29, 0, "NiobiumNitride", "Niobium Nitride", 0, 0, 2573, 2573, true, false, 1, 1, 1, Dyes.dyeBlack, 1)
    public static Material NiobiumTitanium = new Material(360, MaterialIconSet.DULL, 	1.0F, 0, 2, 1|2, 29, 29, 41, 0, "NiobiumTitanium", "Niobium-Titanium", 0, 0, 4500, 4500, true, false, 1, 1, 1, Dyes.dyeBlack, 2)
    public static Material NitroCarbon = new Material(716, MaterialIconSet.FLUID, 		1.0F, 0, 1, 16, 0, 75, 100, 0, "NitroCarbon", "Nitro-Carbon", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeCyan, 1)
    public static Material NitrogenDioxide = new Material(717, MaterialIconSet.FLUID, 	1.0F, 0, 1, 16, 100, 175, 255, 0, "NitrogenDioxide", "Nitrogen Dioxide", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeCyan, 1)
    public static Material Obsidian = new Material(804, MaterialIconSet.DULL, 			1.0F, 0, 3, 1, 80, 50, 100, 0, "Obsidian", "Obsidian", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 1)
    public static Material Phosphate = new Material(833, MaterialIconSet.DULL, 		1.0F, 0, 1, 1 |8|16, 255, 255, 0, 0, "Phosphate", "Phosphate", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeYellow, 1)
    public static Material PigIron = new Material(307, MaterialIconSet.METALLIC, 		6.0F, 384, 2, 1|2|64, 200, 180, 180, 0, "PigIron", "Pig Iron", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyePink, 2)
    public static Material Plastic = new Material(874, MaterialIconSet.DULL, 			3.0F, 32, 1, 1|2|64|128, 200, 200, 200, 0, "Plastic", "Polyethylene", 0, 0, 400, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0)
    public static Material Epoxid = new Material(470, MaterialIconSet.DULL, 			3.0F, 32, 1, 1|2|64|128, 200, 140, 20, 0, "Epoxid", "Epoxid", 0, 0, 400, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0)
    public static Material Silicone = new Material(471, MaterialIconSet.DULL, 			3.0F, 128, 1, 1|2|64|128, 220, 220, 220, 0, "Silicone", "Polysiloxane", 0, 0, 900, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0)
    public static Material Polycaprolactam = new Material(472, MaterialIconSet.DULL, 	3.0F, 32, 1, 1|2|64|128, 50, 50, 50, 0, "Polycaprolactam", "Polycaprolactam", 0, 0, 500, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0)
    public static Material Polytetrafluoroethylene  = new Material(473, MaterialIconSet.DULL, 3.0F, 32, 1, 1|2|64|128, 100, 100, 100, 0, "Polytetrafluoroethylene", "Polytetrafluoroethylene", 0, 0, 1400, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0)
    public static Material Powellite = new Material(883, MaterialIconSet.DULL, 		1.0F, 0, 2, 1 |8 , 255, 255, 0, 0, "Powellite", "Powellite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, 2)
    public static Material Pumice = new Material(926, MaterialIconSet.DULL, 			1.0F, 0, 2, 1, 230, 185, 185, 0, "Pumice", "Pumice", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 2)
    public static Material Pyrite = new Material(834, MaterialIconSet.ROUGH, 			1.0F, 0, 1, 1 |8 , 150, 120, 40, 0, "Pyrite", "Pyrite", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeOrange, 1)
    public static Material Pyrolusite = new Material(943, MaterialIconSet.DULL, 		1.0F, 0, 2, 1 |8 , 150, 150, 170, 0, "Pyrolusite", "Pyrolusite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, 1)
    public static Material Pyrope = new Material(835, MaterialIconSet.METALLIC, 		1.0F, 0, 2, 1 |8 , 120, 50, 100, 0, "Pyrope", "Pyrope", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyePurple, 1)
    public static Material RockSalt = new Material(944, MaterialIconSet.FINE, 			1.0F, 0, 1, 1 |8 , 240, 200, 200, 0, "RockSalt", "Rock Salt", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeWhite, 1)
    public static Material Rubber = new Material(880, MaterialIconSet.SHINY, 			1.5F, 16, 0, 1|2|64|128, 0, 0, 0, 0, "Rubber", "Rubber", 0, 0, 400, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 0)
    public static Material RawRubber = new Material(896, MaterialIconSet.DULL, 		1.0F, 0, 0, 1, 204, 199, 137, 0, "RawRubber", "Raw Rubber", 0, 0, 400, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0)
    public static Material Ruby = new Material(502, MaterialIconSet.RUBY, 				7.0F, 256, 2, 1|4|8 |64, 255, 100, 100, 127, "Ruby", "Ruby", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeRed, 1)
    public static Material Salt = new Material(817, MaterialIconSet.FINE, 				1.0F, 0, 1, 1 |8 , 250, 250, 250, 0, "Salt", "Salt", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeWhite, 1)
    public static Material Saltpeter = new Material(836, MaterialIconSet.FINE, 		1.0F, 0, 1, 1 |8 , 230, 230, 230, 0, "Saltpeter", "Saltpeter", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite, 1)
    public static Material SaltWater = new Material(-1, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 0, 0, 255, 0, "SaltWater", "Salt Water", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 0)
    public static Material Sapphire = new Material(503, MaterialIconSet.GEM_VERTICAL, 	7.0F, 256, 2, 1|4|8 |64, 100, 100, 200, 127, "Sapphire", "Sapphire", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeBlue, 1)
    public static Material Scheelite = new Material(910, MaterialIconSet.DULL, 		1.0F, 0, 3, 1 |8 , 200, 140, 20, 0, "Scheelite", "Scheelite", 0, 0, 2500, 2500, false, false, 4, 1, 1, Dyes.dyeBlack, 0)
    public static Material SiliconDioxide = new Material(837, MaterialIconSet.QUARTZ, 	1.0F, 0, 1, 1 |16, 200, 200, 200, 0, "SiliconDioxide", "Silicon Dioxide", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, 1)
    public static Material Snow = new Material(728, MaterialIconSet.FINE, 				1.0F, 0, 0, 1| 16, 250, 250, 250, 0, "Snow", "Snow", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0)
    public static Material Sodalite = new Material(525, MaterialIconSet.LAPIS, 		1.0F, 0, 1, 1|4|8 , 20, 20, 255, 0, "Sodalite", "Sodalite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlue, 1)
    public static Material SodiumPersulfate = new Material(718, MaterialIconSet.FLUID, 1.0F, 0, 2, 16, 255, 255, 255, 0, "SodiumPersulfate", "Sodium Persulfate", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 1)
    public static Material SodiumSulfide = new Material(719, MaterialIconSet.FLUID, 	1.0F, 0, 2, 16, 255, 255, 255, 0, "SodiumSulfide", "Sodium Sulfide", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 1)
    public static Material HydricSulfide = new Material(460, MaterialIconSet.FLUID, 	1.0F, 0, 2, 16, 255, 255, 255, 0, "HydricSulfide", "Hydrogen Sulfide", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 0)
    public static Material OilHeavy = new Material(730, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 10, 10, 10, 0, "OilHeavy", "Heavy Oil", 3, 32, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Material OilMedium = new Material(731, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 10, 10, 10, 0, "OilMedium", "Raw Oil", 3, 24, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Material OilLight = new Material(732, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 10, 10, 10, 0, "OilLight", "Light Oil", 3, 16, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Material NatruralGas = new Material(733, MaterialIconSet.FLUID, 		1.0F, 0, 1, 16, 255, 255, 255, 0, "NatruralGas", "Natural Gas", 1, 15, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite);
    public static Material SulfuricGas = new Material(734, MaterialIconSet.FLUID, 		1.0F, 0, 1, 16, 255, 255, 255, 0, "SulfuricGas", "Sulfuric Gas", 1, 20, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite);
    public static Material Gas = new Material(735, MaterialIconSet.FLUID, 				1.0F, 0, 1, 16, 255, 255, 255, 0, "Gas", "Refinery Gas", 1, 128, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite);
    public static Material SulfuricNaphtha = new Material(736, MaterialIconSet.FLUID, 	1.0F, 0, 0, 16, 255, 255, 0, 0, "SulfuricNaphtha", "Sulfuric Naphtha", 1, 32, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material SulfuricLightFuel = new Material(737, MaterialIconSet.FLUID,1.0F, 0, 0, 16, 255, 255, 0, 0, "SulfuricLightFuel", "Sulfuric Light Fuel", 0, 32, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material SulfuricHeavyFuel = new Material(738, MaterialIconSet.FLUID,1.0F, 0, 0, 16, 255, 255, 0, 0, "SulfuricHeavyFuel", "Sulfuric Heavy Fuel", 3, 32, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Material Naphtha = new Material(739, MaterialIconSet.FLUID, 			1.0F, 0, 0, 16, 255, 255, 0, 0, "Naphtha", "Naphtha", 1, 256, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material LightFuel = new Material(740, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 255, 255, 0, 0, "LightFuel", "Light Fuel", 0, 256, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material HeavyFuel = new Material(741, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 255, 255, 0, 0, "HeavyFuel", "Heavy Fuel", 3, 192, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Material LPG = new Material(742, MaterialIconSet.FLUID, 				1.0F, 0, 0, 16, 255, 255, 0, 0, "LPG", "LPG", 1, 256, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material CrackedLightFuel = new Material(743, MaterialIconSet.FLUID, 1.0F, 0, 0, 16, 255, 255, 0, 0, "CrackedLightFuel", "Cracked Light Fuel", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material CrackedHeavyFuel = new Material(744, MaterialIconSet.FLUID, 1.0F, 0, 0, 16, 255, 255, 0, 0, "CrackedHeavyFuel", "Cracked Heavy Fuel", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Material SolderingAlloy = new Material(314, MaterialIconSet.DULL, 	1.0F, 0, 1, 1|2, 220, 220, 230, 0, "SolderingAlloy", "Soldering Alloy", 0, 0, 400, 400, false, false, 1, 1, 1, Dyes.dyeWhite, 2)
    public static Material Spessartine = new Material(838, MaterialIconSet.DULL, 		1.0F, 0, 2, 1 |8 , 255, 100, 100, 0, "Spessartine", "Spessartine", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeRed, 1)
    public static Material Sphalerite = new Material(839, MaterialIconSet.DULL, 		1.0F, 0, 1, 1 |8 , 255, 255, 255, 0, "Sphalerite", "Sphalerite", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeYellow, 1)
    public static Material StainlessSteel = new Material(306, MaterialIconSet.SHINY, 	7.0F, 480, 2, 1|2|64|128, 200, 200, 220, 0, "StainlessSteel", "Stainless Steel", 0, 0, -1, 1700, true, false, 1, 1, 1, Dyes.dyeWhite, 1)
    public static Material Steel = new Material(305, MaterialIconSet.METALLIC, 		6.0F, 512, 2, 1|2|64|128, 128, 128, 128, 0, "Steel", "Steel", 0, 0, 1811, 1000, true, false, 4, 51, 50, Dyes.dyeGray, 1)
    public static Material Stibnite = new Material(945, MaterialIconSet.METALLIC, 		1.0F, 0, 2, 1 |8 , 70, 70, 70, 0, "Stibnite", "Stibnite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2)
    public static Material SulfuricAcid = new Material(720, MaterialIconSet.FLUID, 	1.0F, 0, 2, 16, 255, 128, 0, 0, "SulfuricAcid", "Sulfuric Acid", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 1)
    public static Material Tanzanite = new Material(508, MaterialIconSet.GEM_VERTICAL, 7.0F, 256, 2, 1|4|8 |64, 64, 0, 200, 127, "Tanzanite", "Tanzanite", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyePurple, 1)
    public static Material Tetrahedrite = new Material(840, MaterialIconSet.DULL, 		1.0F, 0, 2, 1 |8 , 200, 32, 0, 0, "Tetrahedrite", "Tetrahedrite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeRed, 2)
    public static Material TinAlloy = new Material(363, MaterialIconSet.METALLIC, 		6.5F, 96, 2, 1|2|64|128, 200, 200, 200, 0, "TinAlloy", "Tin Alloy", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2)
    public static Material Topaz = new Material(507, MaterialIconSet.GEM_HORIZONTAL, 	7.0F, 256, 3, 1|4|8 |64, 255, 128, 0, 127, "Topaz", "Topaz", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeOrange, 1)
    public static Material Tungstate = new Material(841, MaterialIconSet.DULL, 		1.0F, 0, 3, 1 |8 , 55, 50, 35, 0, "Tungstate", "Tungstate", 0, 0, 2500, 2500, true, false, 4, 1, 1, Dyes.dyeBlack, 0)
    public static Material Ultimet = new Material(344, MaterialIconSet.SHINY, 			9.0F, 2048, 4, 1|2|64|128, 180, 180, 230, 0, "Ultimet", "Ultimet", 0, 0, 2700, 2700, true, false, 1, 1, 1, Dyes.dyeLightBlue, 1)
    public static Material Uraninite = new Material(922, MaterialIconSet.METALLIC, 	1.0F, 0, 3, 1 |8 , 35, 35, 35, 0, "Uraninite", "Uraninite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLime, 2)
    public static Material Uvarovite = new Material(842, MaterialIconSet.DIAMOND, 		1.0F, 0, 2, 1, 180, 255, 180, 0, "Uvarovite", "Uvarovite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGreen, 1)
    public static Material VanadiumGallium = new Material(357, MaterialIconSet.SHINY, 	1.0F, 0, 2, 1|2, 128, 128, 140, 0, "VanadiumGallium", "Vanadium-Gallium", 0, 0, 4500, 4500, true, false, 1, 1, 1, Dyes.dyeGray, 2)
    public static Material Wood = new Material(809, MaterialIconSet.WOOD, 				2.0F, 16, 0, 1|2|64|128, 100, 50, 0, 0, "Wood", "Wood", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, 0)
    public static Material WroughtIron = new Material(304, MaterialIconSet.METALLIC, 	6.0F, 384, 2, 1|2|64|128, 200, 180, 180, 0, "WroughtIron", "Wrought Iron", 0, 0, 1811, 0, false, false, 3, 1, 1, Dyes.dyeLightGray, 2)
    public static Material Wulfenite = new Material(882, MaterialIconSet.DULL, 		1.0F, 0, 3, 1 |8 , 255, 128, 0, 0, "Wulfenite", "Wulfenite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 2)
    public static Material YellowLimonite = new Material(931, MaterialIconSet.METALLIC,1.0F, 0, 2, 1 |8 , 200, 200, 0, 0, "YellowLimonite", "Yellow Limonite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, 2)
    public static Material YttriumBariumCuprate  = new Material(358, MaterialIconSet.METALLIC, 1.0F, 0, 2, 1|2, 80, 64, 70, 0, "YttriumBariumCuprate", "Yttrium Barium Cuprate", 0, 0, 4500, 4500, true, false, 1, 1, 1, Dyes.dyeGray, 0)
    public static Material NetherQuartz = new Material(522, MaterialIconSet.QUARTZ,1.0F, 32, 1, 1|4|8 |64, 230, 210, 210, 0, "NetherQuartz", "Nether Quartz", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeWhite)
    public static Material CertusQuartz = new Material(516, MaterialIconSet.QUARTZ,5.0F, 32, 1, 1|4|8 |64, 210, 210, 230, 0, "CertusQuartz", "Certus Quartz", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeLightGray)
    public static Material Quartzite = new Material(523, MaterialIconSet.QUARTZ, 	1.0F, 0, 1, 1|4|8 , 210, 230, 210, 0, "Quartzite", "Quartzite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite);
    public static Material Graphite = new Material(865, MaterialIconSet.DULL, 		5.0F, 32, 2, 1 |8|16|64, 128, 128, 128, 0, "Graphite", "Graphite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGray)
    public static Material Graphene = new Material(819, MaterialIconSet.DULL, 		6.0F, 32, 1, 1|64, 128, 128, 128, 0, "Graphene", "Graphene", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGray)
    public static Material Jasper = new Material(511, MaterialIconSet.EMERALD, 	1.0F, 0, 2, 1|4|8, 200, 80, 80, 100, "Jasper", "Jasper", 0, 0, -1, 0, false, true, 3, 1, 1, Dyes.dyeRed)
    public static Material AluminiumBrass = new Material(-1, MaterialIconSet.METALLIC, 6.0F, 64, 2, 1|2|64, 255, 255, 255, 0, "AluminiumBrass", "Aluminium Brass", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material Osmiridium = new Material(317, MaterialIconSet.METALLIC, 	7.0F, 1600, 3, 1|2|64|128, 100, 100, 255, 0, "Osmiridium", "Osmiridium", 0, 0, 3333, 2500, true, false, 1, 1, 1, Dyes.dyeLightBlue, 1)

    /**
     * Second Degree Compounds
     */
    public static Material WoodSealed = new Material( 889, MaterialIconSet.WOOD, 		3.0F, 24, 0, 1|2|64|128, 80, 40, 0, 0, "WoodSealed", "Sealed Wood", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, 0)
    public static Material Glass = new Material( 890, MaterialIconSet.GLASS, 			1.0F, 4, 0, 1|4, 250, 250, 250, 220, "Glass", "Glass", 0, 0, 1500, 0, false, true, 1, 1, 1, Dyes.dyeWhite, 2)
    public static Material Perlite = new Material( 925, MaterialIconSet.DULL, 			1.0F, 0, 1, 1, 30, 20, 30, 0, "Perlite", "Perlite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 2)
    public static Material Borax = new Material( 941, MaterialIconSet.FINE, 			1.0F, 0, 1, 1, 250, 250, 250, 0, "Borax", "Borax", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 1)
    public static Material Lignite = new Material( 538, MaterialIconSet.LIGNITE, 		1.0F, 0, 0, 1|4|8 , 100, 70, 70, 0, "Lignite", "Lignite Coal", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 1)
    public static Material Olivine = new Material( 505, MaterialIconSet.RUBY, 			7.0F, 256, 2, 1|4|8 |64, 150, 255, 150, 127, "Olivine", "Olivine", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeLime, 1)
    public static Material Opal = new Material( 510, MaterialIconSet.OPAL, 			7.0F, 256, 2, 1|4|8 |64, 0, 0, 255, 0, "Opal", "Opal", 0, 0, -1, 0, false, true, 3, 1, 1, Dyes.dyeBlue, 1)
    public static Material Amethyst = new Material( 509, MaterialIconSet.FLINT, 		7.0F, 256, 3, 1|4|8 |64, 210, 50, 210, 127, "Amethyst", "Amethyst", 0, 0, -1, 0, false, true, 3, 1, 1, Dyes.dyePink, 1)
    public static Material Redstone = new Material( 810, MaterialIconSet.ROUGH, 		1.0F, 0, 2, 1 |8 , 200, 0, 0, 0, "Redstone", "Redstone", 0, 0, 500, 0, false, false, 3, 1, 1, Dyes.dyeRed, 2)
    public static Material Lapis = new Material( 526, MaterialIconSet.LAPIS, 			1.0F, 0, 1, 1|4|8 , 70, 70, 220, 0, "Lapis", "Lapis", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlue, 2)
    public static Material Blaze = new Material( 801, MaterialIconSet.POWDER, 			2.0F, 16, 1, 1|64, 255, 200, 0, 0, "Blaze", "Blaze", 0, 0, 6400, 0, false, false, 2, 3, 2, Dyes.dyeYellow, 2)
    public static Material EnderPearl = new Material( 532, MaterialIconSet.SHINY, 		1.0F, 16, 1, 1|4, 108, 220, 200, 0, "EnderPearl", "Enderpearl", 0, 0, -1, 0, false, false, 1, 16, 10, Dyes.dyeGreen, 1)
    public static Material EnderEye = new Material( 533, MaterialIconSet.SHINY, 		1.0F, 16, 1, 1|4, 160, 250, 230, 0, "EnderEye", "Endereye", 5, 10, -1, 0, false, false, 1, 2, 1, Dyes.dyeGreen, 2)
    public static Material Flint = new Material( 802, MaterialIconSet.FLINT, 			2.5F, 64, 1, 1|64, 0, 32, 64, 0, "Flint", "Flint", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 2)
    public static Material Diatomite = new Material( 948, MaterialIconSet.DULL, 		1.0F, 0, 1, 1 |8 , 225, 225, 225, 0, "Diatomite", "Diatomite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 2)
    public static Material VolcanicAsh = new Material( 940, MaterialIconSet.FLINT, 	1.0F, 0, 0, 1, 60, 50, 50, 0, "VolcanicAsh", "Volcanic Ashes", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 2)
    public static Material Niter = new Material( 531, MaterialIconSet.FLINT, 			1.0F, 0, 1, 1|4, 255, 200, 200, 0, "Niter", "Niter", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink, 2)
    public static Material Pyrotheum = new Material( 843, MaterialIconSet.FIERY, 		1.0F, 0, 1, 1, 255, 128, 0, 0, "Pyrotheum", "Pyrotheum", 2, 62, -1, 0, false, false, 2, 3, 1, Dyes.dyeYellow, 2)
    public static Material HydratedCoal = new Material( 818, MaterialIconSet.ROUGH, 	1.0F, 0, 1, 1, 70, 70, 100, 0, "HydratedCoal", "Hydrated Coal", 0, 0, -1, 0, false, false, 1, 9, 8, Dyes.dyeBlack, 2)
    public static Material Apatite = new Material(530, MaterialIconSet.DIAMOND, 		1.0F, 0, 1, 1|4|8, 200, 200, 255, 0, "Apatite", "Apatite", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeCyan, 1)
    public static Material Alumite = new Material(-1, MaterialIconSet.METALLIC, 		1.5F, 64, 0, 1|2|64, 255, 255, 255, 0, "Alumite", "Alumite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink, 2)
    public static Material Manyullyn = new Material(-1, MaterialIconSet.METALLIC, 		1.5F, 64, 0, 1|2|64, 255, 255, 255, 0, "Manyullyn", "Manyullyn", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePurple, 2)
    public static Material SterlingSilver = new Material( 350, MaterialIconSet.SHINY, 13.0F, 128, 2, 1|2|64|128, 250, 220, 225, 0, "SterlingSilver", "Sterling Silver", 0, 0, -1, 1700, true, false, 4, 1, 1, Dyes.dyeWhite, 2)
    public static Material RoseGold = new Material( 351, MaterialIconSet.SHINY, 	   14.0F, 128, 2, 1|2|64|128, 255, 230, 30, 0, "RoseGold", "Rose Gold", 0, 0, -1, 1600, true, false, 4, 1, 1, Dyes.dyeOrange, 2)
    public static Material BlackBronze = new Material( 352, MaterialIconSet.DULL, 	   12.0F, 256, 2, 1|2|64|128, 100, 50, 125, 0, "BlackBronze", "Black Bronze", 0, 0, -1, 2000, true, false, 4, 1, 1, Dyes.dyePurple, 2)
    public static Material BismuthBronze = new Material( 353, MaterialIconSet.DULL, 	8.0F, 256, 2, 1|2|64|128, 100, 125, 125, 0, "BismuthBronze", "Bismuth Bronze", 0, 0, -1, 1100, true, false, 4, 1, 1, Dyes.dyeCyan, 2)
    public static Material BlackSteel = new Material( 334, MaterialIconSet.METALLIC, 	6.5F, 768, 2, 1|2|64, 100, 100, 100, 0, "BlackSteel", "Black Steel", 0, 0, -1, 1200, true, false, 4, 1, 1, Dyes.dyeBlack, 2)
    public static Material RedSteel = new Material( 348, MaterialIconSet.METALLIC, 	7.0F, 896, 2, 1|2|64, 140, 100, 100, 0, "RedSteel", "Red Steel", 0, 0, -1, 1300, true, false, 4, 1, 1, Dyes.dyeRed, 2)
    public static Material BlueSteel = new Material( 349, MaterialIconSet.METALLIC, 	7.5F, 1024, 2, 1|2|64, 100, 100, 140, 0, "BlueSteel", "Blue Steel", 0, 0, -1, 1400, true, false, 4, 1, 1, Dyes.dyeBlue, 2)
    public static Material DamascusSteel = new Material( 335, MaterialIconSet.METALLIC,8.0F, 1280, 2, 1|2|64, 110, 110, 110, 0, "DamascusSteel", "Damascus Steel", 0, 0, 2000, 1500, true, false, 4, 1, 1, Dyes.dyeGray, 2)
    public static Material TungstenSteel = new Material( 316, MaterialIconSet.METALLIC,8.0F, 2560, 4, 1|2|64|128, 100, 100, 160, 0, "TungstenSteel", "Tungstensteel", 0, 0, -1, 3000, true, false, 4, 1, 1, Dyes.dyeBlue, 2)
    public static Material NitroFuel = new Material( 709, MaterialIconSet.FLUID, 		1.0F, 0, 2, 16, 200, 255, 0, 0, "NitroFuel", "Nitro-Diesel", 0, 512, -1, 0, false, false, 1, 1, 1, Dyes.dyeLime, 0)
    public static Material RedAlloy = new Material( 308, MaterialIconSet.DULL, 		1.0F, 0, 0, 1|2, 200, 0, 0, 0, "RedAlloy", "Red Alloy", 0, 0, -1, 0, false, false, 3, 5, 1, Dyes.dyeRed, 2)
    public static Material CobaltBrass = new Material( 343, MaterialIconSet.METALLIC, 	8.0F, 256, 2, 1|2|64|128, 180, 180, 160, 0, "CobaltBrass", "Cobalt Brass", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeOrange, 2)
    public static Material Phosphorus = new Material( 534, MaterialIconSet.FLINT, 		1.0F, 0, 2, 1|4|8|16, 255, 255, 0, 0, "Phosphorus", "Phosphorus", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow, 2)
    public static Material Basalt = new Material( 844, MaterialIconSet.ROUGH, 			1.0F, 0, 1, 1, 30, 20, 20, 0, "Basalt", "Basalt", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeBlack, 2)
    public static Material Andesite = new Material( 298, MaterialIconSet.ROUGH,   4.0F,     32,  1, 1|64|128, 190, 190, 190,   0,	"Andesite", "Andesite"	, 0, 0, -1,    0, false, false,   0,   1,   1, Dyes.dyeLightGray	, 1)
    public static Material Diorite = new Material( 297, MaterialIconSet.ROUGH,   4.0F,     32,  1, 1|64|128, 255, 255, 255,   0,	"Diorite", "Diorite", 0, 0, -1,    0, false, false,   0,   1,   1, Dyes.dyeWhite   	, 1)
    public static Material GarnetRed = new Material( 527, MaterialIconSet.RUBY, 		7.0F, 128, 2, 1|4|8 |64, 200, 80, 80, 127, "GarnetRed", "Red Garnet", 0, 0, -1, 0, false, true, 4, 1, 1, Dyes.dyeRed, 2)
    public static Material GarnetYellow = new Material( 528, MaterialIconSet.RUBY, 	7.0F, 128, 2, 1|4|8 |64, 200, 200, 80, 127, "GarnetYellow", "Yellow Garnet", 0, 0, -1, 0, false, true, 4, 1, 1, Dyes.dyeYellow, 2)
    public static Material Marble = new Material( 845, MaterialIconSet.FINE, 			1.0F, 0, 1, 1, 200, 200, 200, 0, "Marble", "Marble", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2)
    public static Material Sugar = new Material( 803, MaterialIconSet.FINE, 			1.0F, 0, 1, 1, 250, 250, 250, 0, "Sugar", "Sugar", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 1)
    public static Material Vinteum = new Material(529, MaterialIconSet.EMERALD, 	   10.0F, 128, 3, 1|4|8|64, 100, 200, 255, 0, "Vinteum", "Vinteum", 5, 32, -1, 0, false, false, 4, 1, 1, Dyes.dyeLightBlue, 2)
    public static Material Redrock = new Material( 846, MaterialIconSet.ROUGH, 		1.0F, 0, 1, 1, 255, 80, 50, 0, "Redrock", "Redrock", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed, 2)
    public static Material PotassiumFeldspar = new Material( 847, MaterialIconSet.FINE,1.0F, 0, 1, 1, 120, 40, 40, 0, "PotassiumFeldspar", "Potassium Feldspar", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink, 1)
    public static Material Biotite = new Material( 848, MaterialIconSet.METALLIC, 		1.0F, 0, 1, 1, 20, 30, 20, 0, "Biotite", "Biotite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 1)
    public static Material GraniteBlack = new Material( 849, MaterialIconSet.ROUGH, 	4.0F, 64, 3, 1|64|128, 10, 10, 10, 0, "GraniteBlack", "Black Granite", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeBlack, 2)
    public static Material GraniteRed = new Material( 850, MaterialIconSet.ROUGH, 		4.0F, 64, 3, 1|64|128, 255, 0, 128, 0, "GraniteRed", "Red Granite", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeMagenta, 1)
    public static Material Chrysotile = new Material( 912, MaterialIconSet.DULL, 		1.0F, 0, 2, 1, 110, 140, 110, 0, "Chrysotile", "Chrysotile", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2)
    public static Material Realgar = new Material( 913, MaterialIconSet.DULL, 			1.0F, 0, 2, 1, 140, 100, 100, 0, "Realgar", "Realgar", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2)
    public static Material VanadiumMagnetite = new Material( 923, MaterialIconSet.METALLIC, 1.0F, 0, 2, 1 |8 , 35, 35, 60, 0, "VanadiumMagnetite", "Vanadium Magnetite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 2)
    public static Material BasalticMineralSand = new Material( 935, MaterialIconSet.SAND, 1.0F, 0, 1, 1, 40, 50, 40, 0, "BasalticMineralSand", "Basaltic Mineral Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 2)
    public static Material GraniticMineralSand = new Material( 936, MaterialIconSet.SAND, 1.0F, 0, 1, 1, 40, 60, 60, 0, "GraniticMineralSand", "Granitic Mineral Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 2)
    public static Material GarnetSand = new Material( 938, MaterialIconSet.SAND, 		1.0F, 0, 1, 1, 200, 100, 0, 0, "GarnetSand", "Garnet Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 2)
    public static Material QuartzSand = new Material( 939, MaterialIconSet.SAND, 		1.0F, 0, 1, 1, 200, 200, 200, 0, "QuartzSand", "Quartz Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2)
    public static Material Bastnasite = new Material( 905, MaterialIconSet.FINE, 		1.0F, 0, 2, 1 |8 , 200, 110, 45, 0, "Bastnasite", "Bastnasite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Pentlandite = new Material( 909, MaterialIconSet.DULL, 		1.0F, 0, 2, 1 |8 , 165, 150, 5, 0, "Pentlandite", "Pentlandite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Spodumene = new Material( 920, MaterialIconSet.DULL, 		1.0F, 0, 2, 1 |8 , 190, 170, 170, 0, "Spodumene", "Spodumene", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Pollucite = new Material( 919, MaterialIconSet.DULL, 		1.0F, 0, 2, 1, 240, 210, 210, 0, "Pollucite", "Pollucite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Tantalite = new Material( 921, MaterialIconSet.METALLIC, 	1.0F, 0, 3, 1 |8 , 145, 80, 40, 0, "Tantalite", "Tantalite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Lepidolite = new Material( 907, MaterialIconSet.FINE, 		1.0F, 0, 2, 1 |8 , 240, 50, 140, 0, "Lepidolite", "Lepidolite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Glauconite = new Material( 933, MaterialIconSet.DULL, 		1.0F, 0, 2, 1 |8 , 130, 180, 60, 0, "Glauconite", "Glauconite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material GlauconiteSand = new Material( 949, MaterialIconSet.DULL, 	1.0F, 0, 2, 1, 130, 180, 60, 0, "GlauconiteSand", "Glauconite Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Vermiculite = new Material( 932, MaterialIconSet.METALLIC, 	1.0F, 0, 2, 1, 200, 180, 15, 0, "Vermiculite", "Vermiculite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Bentonite = new Material( 927, MaterialIconSet.ROUGH, 		1.0F, 0, 2, 1 |8 , 245, 215, 210, 0, "Bentonite", "Bentonite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material FullersEarth = new Material( 928, MaterialIconSet.FINE, 	1.0F, 0, 2, 1, 160, 160, 120, 0, "FullersEarth", "Fullers Earth", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Pitchblende = new Material( 873, MaterialIconSet.DULL, 		1.0F, 0, 3, 1 |8 , 200, 210, 0, 0, "Pitchblende", "Pitchblende", 0, 0, -1, 0, false, false, 5, 1, 1, Dyes.dyeYellow, 2)
    public static Material Monazite = new Material( 520, MaterialIconSet.DIAMOND, 		1.0F, 0, 1, 1|4|8 , 50, 70, 50, 0, "Monazite", "Monazite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGreen, 1)
    public static Material Malachite = new Material( 871, MaterialIconSet.DULL, 		1.0F, 0, 2, 1 |8 , 5, 95, 5, 0, "Malachite", "Malachite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGreen, 1)
    public static Material Mirabilite = new Material( 900, MaterialIconSet.DULL, 		1.0F, 0, 2, 1, 240, 250, 210, 0, "Mirabilite", "Mirabilite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Mica = new Material( 901, MaterialIconSet.FINE, 			1.0F, 0, 1, 1, 195, 195, 205, 0, "Mica", "Mica", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Trona = new Material( 903, MaterialIconSet.METALLIC, 		1.0F, 0, 1, 1, 135, 135, 95, 0, "Trona", "Trona", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Barite = new Material( 904, MaterialIconSet.DULL, 			1.0F, 0, 2, 1 |8 , 230, 235, 255, 0, "Barite", "Barite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Gypsum = new Material( 934, MaterialIconSet.DULL, 			1.0F, 0, 1, 1, 230, 230, 250, 0, "Gypsum", "Gypsum", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Alunite = new Material( 911, MaterialIconSet.METALLIC,		1.0F, 0, 2, 1, 225, 180, 65, 0, "Alunite", "Alunite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Dolomite = new Material( 914, MaterialIconSet.FLINT, 		1.0F, 0, 1, 1, 225, 205, 205, 0, "Dolomite", "Dolomite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Wollastonite = new Material( 915, MaterialIconSet.DULL, 	1.0F, 0, 2, 1, 240, 240, 240, 0, "Wollastonite", "Wollastonite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Zeolite = new Material( 916, MaterialIconSet.DULL, 			1.0F, 0, 2, 1, 240, 230, 230, 0, "Zeolite", "Zeolite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Kyanite = new Material( 924, MaterialIconSet.FLINT, 		1.0F, 0, 2, 1, 110, 110, 250, 0, "Kyanite", "Kyanite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Kaolinite = new Material( 929, MaterialIconSet.DULL, 		1.0F, 0, 2, 1, 245, 235, 235, 0, "Kaolinite", "Kaolinite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Talc = new Material( 902, MaterialIconSet.DULL, 			1.0F, 0, 2, 1 |8, 90, 180, 90, 0, "Talc", "Talc", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Soapstone = new Material( 877, MaterialIconSet.DULL, 		1.0F, 0, 1, 1 |8 , 95, 145, 95, 0, "Soapstone", "Soapstone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1)
    public static Material Concrete = new Material( 947, MaterialIconSet.ROUGH, 		1.0F, 0, 1, 1, 100, 100, 100, 0, "Concrete", "Concrete", 0, 0, 300, 0, false, false, 0, 1, 1, Dyes.dyeGray, 0)
    public static Material IronMagnetic = new Material( 354, MaterialIconSet.MAGNETIC, 6.0F, 256, 2, 1|2|64|128, 200, 200, 200, 0, "IronMagnetic", "Magnetic Iron", 0, 0, -1, 0, false, false, 4, 51, 50, Dyes.dyeGray, 1)
    public static Material SteelMagnetic = new Material( 355, MaterialIconSet.MAGNETIC,6.0F, 512, 2, 1|2|64|128, 128, 128, 128, 0, "SteelMagnetic", "Magnetic Steel", 0, 0, 1000, 1000, true, false, 4, 51, 50, Dyes.dyeGray, 1)
    public static Material NeodymiumMagnetic = new Material(356, MaterialIconSet.MAGNETIC,7.0F, 512, 2, 1|2|64|128, 100, 100, 100, 0, "NeodymiumMagnetic", "Magnetic Neodymium", 0, 0, 1297, 1297, true, false, 4, 51, 50, Dyes.dyeGray, 1)
    public static Material TungstenCarbide =new Material(370, MaterialIconSet.METALLIC,14.0F, 1280, 4, 1|2|64|128, 51, 0, 102, 0, "TungstenCarbide", "Tungstencarbide", 0, 0, 2460, 2460, true, false, 4, 1, 1, Dyes.dyeBlack, 2)
    public static Material VanadiumSteel = new Material( 371, MaterialIconSet.METALLIC,3.0F, 1920, 3, 1|2|64|128, 192, 192, 192, 0, "VanadiumSteel", "Vanadiumsteel", 0, 0, 1453, 1453, true, false, 4, 1, 1, Dyes.dyeWhite, 2)
    public static Material HSSG = new Material( 372, MaterialIconSet.METALLIC, 	   10.0F, 4000, 3, 1|2|64|128, 153, 153, 0, 0, "HSSG", "HSS-G", 0, 0, 4500, 4500, true, false, 4, 1, 1, Dyes.dyeYellow, 2)
    public static Material HSSE = new Material( 373, MaterialIconSet.METALLIC, 	   10.0F, 5120, 4, 1|2|64|128, 51, 102, 0, 0, "HSSE", "HSS-E", 0, 0, 5400, 5400, true, false, 4, 1, 1, Dyes.dyeBlue, 2)
    public static Material HSSS = new Material( 374, MaterialIconSet.METALLIC, 	   14.0F, 3000, 4, 1|2|64|128, 102, 0, 51, 0, "HSSS", "HSS-S", 0, 0, 5400, 5400, true, false, 4, 1, 1, Dyes.dyeRed, 2)

    /**
     * The "Random Material" ones.
     */
    public static Material AnyCopper = new Material(-1, MaterialIconSet.DULL, 1.0F, 0, 3, false, "AnyCopper", "AnyCopper");
    public static Material AnyBronze = new Material(-1, MaterialIconSet.DULL, 1.0F, 0, 3, false, "AnyBronze", "AnyBronze");
    public static Material AnyIron = new Material(-1, MaterialIconSet.DULL, 	1.0F, 0, 3, false, "AnyIron", "AnyIron");

    /**
     * Clear matter materials
     */
    public static Material Antimatter = new Material(-1, MaterialIconSet.NONE, 	1.0F, 0, 0, 0, 255, 255, 255, 0, "Antimatter", "Antimatter", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink)
    public static Material UUAmplifier = new Material(721, MaterialIconSet.FLUID, 	1.0F, 0, 1, 16, 96, 0, 128, 0, "UUAmplifier", "UU-Amplifier", 0, 0, -1, 0, false, false, 10, 1, 1, Dyes.dyePink);
    public static Material UUMatter = new Material(703, MaterialIconSet.FLUID, 	1.0F, 0, 1, 16, 128, 0, 196, 0, "UUMatter", "UU-Matter", 0, 0, -1, 0, false, false, 10, 1, 1, Dyes.dyePink);

    /**
     * Not possible to determine exact Components
     */
    public static Material Sand = new Material(-1, MaterialIconSet.NONE, 			1.0F, 0, 1, 0, 255, 255, 255, 0, "Sand", "Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material ConstructionFoam = new Material(854, MaterialIconSet.DULL, 1.0F, 0, 2, 1 |16, 128, 128, 128, 0, "ConstructionFoam", "Construction Foam", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray);
    public static Material BioFuel = new Material(705, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 255, 128, 0, 0, "BioFuel", "Biofuel", 0, 6, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange);
    public static Material Biomass = new Material(704, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 0, 255, 0, 0, "Biomass", "Biomass", 3, 8, -1, 0, false, false, 1, 1, 1, Dyes.dyeGreen);
    public static Material Creosote = new Material(712, MaterialIconSet.FLUID, 	1.0F, 0, 0, 16, 128, 64, 0, 0, "Creosote", "Creosote", 3, 8, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown);
    public static Material Ethanol = new Material(706, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 255, 128, 0, 0, "Ethanol", "Ethanol", 0, 128, -1, 0, false, false, 1, 1, 1, Dyes.dyePurple);
    public static Material Fuel = new Material(708, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 255, 255, 0, 0, "Fuel", "Diesel", 0, 128, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material Glue = new Material(726, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 200, 196, 0, 0, "Glue", "Glue", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange)
    public static Material Gunpowder = new Material(800, MaterialIconSet.DULL, 	1.0F, 0, 0, 1, 128, 128, 128, 0, "Gunpowder", "Gunpowder", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray)
    public static Material Leather = new Material(-1, MaterialIconSet.ROUGH, 		1.0F, 0, 0, 1, 150, 150, 80, 127, "Leather", "Leather", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange);
    public static Material Lubricant = new Material(724, MaterialIconSet.FLUID, 	1.0F, 0, 0, 16, 255, 196, 0, 0, "Lubricant", "Lubricant", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange)
    public static Material McGuffium239 = new Material(999, MaterialIconSet.FLUID, 1.0F, 0, 0, 16, 200, 50, 150, 0, "McGuffium239", "Mc Guffium 239", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink)
    public static Material Oil = new Material(707, MaterialIconSet.FLUID, 			1.0F, 0, 0, 16, 10, 10, 10, 0, "Oil", "Oil", 3, 16, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Material Oilsands = new Material(878, MaterialIconSet.NONE, 		1.0F, 0, 1, 1|8 , 10, 10, 10, 0, "Oilsands", "Oilsands", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Material Paper = new Material(879, MaterialIconSet.PAPER, 		1.0F, 0, 0, 1, 250, 250, 250, 0, "Paper", "Paper", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite)
    public static Material RareEarth = new Material(891, MaterialIconSet.FINE, 	1.0F, 0, 0, 1, 128, 128, 100, 0, "RareEarth", "Rare Earth", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray)
    public static Material SeedOil = new Material(713, MaterialIconSet.FLUID,		1.0F, 0, 0, 16, 196, 255, 0, 0, "SeedOil", "Seed Oil", 3, 2, -1, 0, false, false, 1, 1, 1, Dyes.dyeLime)
    public static Material Stone = new Material(299, MaterialIconSet.ROUGH, 		4.0F, 32, 1, 1|64|128, 205, 205, 205, 0, "Stone", "Stone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray)
    public static Material Lava = new Material(700, MaterialIconSet.FLUID, 		1.0F, 0, 1, 16, 255, 64, 0, 0, "Lava", "Lava", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange);
    public static Material Glowstone = new Material(811, MaterialIconSet.SHINY, 	1.0F, 0, 1, 1 |16, 255, 255, 0, 0, "Glowstone", "Glowstone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow)
    public static Material NetherStar = new Material(506, MaterialIconSet.NETHERSTAR,1.0F, 5120, 4, 1|4|64, 255, 255, 255, 0, "NetherStar", "Nether Star", 5, 50000, -1, 0, false, false, 15, 1, 1, Dyes.dyeWhite);
    public static Material Endstone = new Material(808, MaterialIconSet.DULL, 			1.0F, 0, 1, 1, 255, 255, 255, 0, "Endstone", "Endstone", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeYellow);
    public static Material Netherrack = new Material(807, MaterialIconSet.DULL, 		1.0F, 0, 0, 1, 200, 0, 0, 0, "Netherrack", "Netherrack", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeRed);
    public static Material SoulSand = new Material(-1, MaterialIconSet.DULL, 			1.0F, 0, 0, 1, 255, 255, 255, 0, "Soulsand", "Soulsand", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeBrown);

    /**
     * Startrek materials
     */
    public static Material Naquadah = new Material(324, MaterialIconSet.METALLIC, 	6.0F, 1280, 4, 1|2|8|16|64, 50, 50, 50, 0, "Naquadah", "Naquadah", 0, 0, 5400, 5400, true, false, 10, 1, 1, Dyes.dyeBlack)
    public static Material NaquadahAlloy = new Material(325, MaterialIconSet.METALLIC,8.0F, 5120, 5, 1|2|64|128, 40, 40, 40, 0, "NaquadahAlloy", "Naquadah Alloy", 0, 0, 7200, 7200, true, false, 10, 1, 1, Dyes.dyeBlack)
    public static Material NaquadahEnriched = new Material(326, MaterialIconSet.METALLIC,6.0F, 1280, 4, 1|2|8|16|64, 50, 50, 50, 0, "NaquadahEnriched", "Enriched Naquadah", 0, 0, 4500, 4500, true, false, 15, 1, 1, Dyes.dyeBlack)
    public static Material Naquadria = new Material(327, MaterialIconSet.SHINY, 	1.0F, 512, 4, 1|2|8|16|64, 30, 30, 30, 0, "Naquadria", "Naquadria", 0, 0, 9000, 9000, true, false, 20, 1, 1, Dyes.dyeBlack)
    public static Material Tritanium = new Material(329, MaterialIconSet.METALLIC,20.0F, 10240, 6, 1|2|64, 255, 255, 255, 0, "Tritanium", "Tritanium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite)
    public static Material Duranium = new Material(328, MaterialIconSet.METALLIC, 16.0F, 5120, 5, 1|2|64, 255, 255, 255, 0, "Duranium", "Duranium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray);

    /**
     * Actual food
     */
    public static Material Cheese = new Material(894, MaterialIconSet.FINE, 		1.0F, 0, 0, 1, 255, 255, 0, 0, "Cheese", "Cheese", 0, 0, 320, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material Chili = new Material(895, MaterialIconSet.FINE, 		1.0F, 0, 0, 1, 200, 0, 0, 0, "Chili", "Chili", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed);
    public static Material Chocolate = new Material(886, MaterialIconSet.FINE, 	1.0F, 0, 0, 1, 190, 95, 0, 0, "Chocolate", "Chocolate", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown);
    public static Material MeatRaw = new Material(892, MaterialIconSet.FINE, 		1.0F, 0, 0, 1, 255, 100, 100, 0, "MeatRaw", "Raw Meat", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink);
    public static Material MeatCooked = new Material(893, MaterialIconSet.FINE, 	1.0F, 0, 0, 1, 150, 60, 20, 0, "MeatCooked", "Cooked Meat", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink);
    public static Material Milk = new Material(885, MaterialIconSet.FINE, 			1.0F, 0, 0, 1 |16, 254, 254, 254, 0, "Milk", "Milk", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite)
    public static Material FryingOilHot = new Material(727, MaterialIconSet.FLUID, 1.0F, 0, 0, 16, 200, 196, 0, 0, "FryingOilHot", "Hot Frying Oil", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange)
    public static Material Honey = new Material(725, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 210, 200, 0, 0, "Honey", "Honey", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Material FishOil = new Material(711, MaterialIconSet.FLUID, 		1.0F, 0, 0, 16, 255, 196, 0, 0, "FishOil", "Fish Oil", 3, 2, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow)
    public static Material Cocoa = new Material(887, MaterialIconSet.FINE, 		1.0F, 0, 0, 1, 190, 95, 0, 0, "Cocoa", "Cocoa", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown);
    public static Material Coffee = new Material(888, MaterialIconSet.FINE, 		1.0F, 0, 0, 1, 150, 75, 0, 0, "Coffee", "Coffee", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown);


    /**
     * Circuitry, Batteries and other Technical things
     * To be exact, it's their tags. OrePrefix is type of thing, Material is tier of it
     */
    public static Material Primitive = new Material(-1, MaterialIconSet.NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Primitive", "Primitive", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray)
    public static Material Basic = new Material(-1, MaterialIconSet.NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "Basic", "Basic", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray)
    public static Material Good = new Material(-1, MaterialIconSet.NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "Good", "Good", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray)
    public static Material Advanced = new Material(-1, MaterialIconSet.NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Advanced", "Advanced", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray)
    public static Material Data = new Material(-1, MaterialIconSet.NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "Data", "Data", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray)
    public static Material Elite = new Material(-1, MaterialIconSet.NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "Elite", "Elite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray)
    public static Material Master = new Material(-1, MaterialIconSet.NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Master", "Master", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray)
    public static Material Ultimate = new Material(-1, MaterialIconSet.NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Ultimate", "Ultimate", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray)
    public static Material Superconductor = new Material(-1, MaterialIconSet.NONE, 1.0F, 0, 0, 0, 255, 255, 255, 0, "Superconductor", "Superconductor", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray)
    public static Material Infinite = new Material(-1, MaterialIconSet.NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Infinite", "Infinite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray);

    static {
        initSubTags();
        Iron					.mOreReRegistrations.add(AnyIron	);
        PigIron					.mOreReRegistrations.add(AnyIron	);
        WroughtIron				.mOreReRegistrations.add(AnyIron	);

        Copper					.mOreReRegistrations.add(AnyCopper	);
        AnnealedCopper			.mOreReRegistrations.add(AnyCopper	);

        Bronze					.mOreReRegistrations.add(AnyBronze	);

        WoodSealed				.setMaceratingInto(Wood				);

        NeodymiumMagnetic		.setSmeltingInto(Neodymium			).setMaceratingInto(Neodymium		).setArcSmeltingInto(Neodymium			);
        SteelMagnetic			.setSmeltingInto(Steel				).setMaceratingInto(Steel			).setArcSmeltingInto(Steel				);
        Iron					.setSmeltingInto(Iron				).setMaceratingInto(Iron			).setArcSmeltingInto(WroughtIron		);
        AnyIron					.setSmeltingInto(Iron				).setMaceratingInto(Iron			).setArcSmeltingInto(WroughtIron		);
        PigIron					.setSmeltingInto(Iron				).setMaceratingInto(Iron			).setArcSmeltingInto(WroughtIron		);
        WroughtIron				.setSmeltingInto(Iron				).setMaceratingInto(Iron			).setArcSmeltingInto(WroughtIron		);
        IronMagnetic			.setSmeltingInto(Iron				).setMaceratingInto(Iron			).setArcSmeltingInto(WroughtIron		);
        Copper					.setSmeltingInto(Copper				).setMaceratingInto(Copper			).setArcSmeltingInto(AnnealedCopper		);
        AnyCopper				.setSmeltingInto(Copper				).setMaceratingInto(Copper			).setArcSmeltingInto(AnnealedCopper		);
        AnnealedCopper			.setSmeltingInto(Copper				).setMaceratingInto(Copper			).setArcSmeltingInto(AnnealedCopper		);
        MeatRaw					.setSmeltingInto(MeatCooked			);
        Sand					.setSmeltingInto(Glass				);
        Ice						.setSmeltingInto(Water				);
        Snow					.setSmeltingInto(Water				);

        Mercury					.add(SubTag.SMELTING_TO_GEM);
        Cinnabar				.setDirectSmelting(Mercury		).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT).add(SubTag.SMELTING_TO_GEM);
        Tetrahedrite			.setDirectSmelting(Copper		).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        Chalcopyrite			.setDirectSmelting(Copper		).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        Malachite				.setDirectSmelting(Copper		).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        Pentlandite				.setDirectSmelting(Nickel		).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        Sphalerite				.setDirectSmelting(Zinc			).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        Pyrite					.setDirectSmelting(Iron			).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        BasalticMineralSand		.setDirectSmelting(Iron			).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        GraniticMineralSand		.setDirectSmelting(Iron			).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        YellowLimonite			.setDirectSmelting(Iron			).add(SubTag.INDUCTIONSMELTING_LOW_OUTPUT);
        BrownLimonite			.setDirectSmelting(Iron			);
        BandedIron				.setDirectSmelting(Iron			);
        Cassiterite				.setDirectSmelting(Tin			);
        CassiteriteSand			.setDirectSmelting(Tin			);
        Chromite				.setDirectSmelting(Chrome		);
        Garnierite				.setDirectSmelting(Nickel		);
        Cobaltite				.setDirectSmelting(Cobalt		);
        Stibnite				.setDirectSmelting(Antimony		);
        Cooperite				.setDirectSmelting(Platinum		);
        Pyrolusite				.setDirectSmelting(Manganese	);
        Magnesite				.setDirectSmelting(Magnesium	);
        Molybdenite				.setDirectSmelting(Molybdenum	);

        Salt					.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        RockSalt				.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        Scheelite				.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        Tungstate				.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        Cassiterite				.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        CassiteriteSand			.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        NetherQuartz			.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        CertusQuartz			.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        Phosphorus				.setOreMultiplier( 3).setSmeltingMultiplier( 3);
        Saltpeter				.setOreMultiplier( 4).setSmeltingMultiplier( 4);
        Apatite					.setOreMultiplier( 4).setSmeltingMultiplier( 4).setByProductMultiplier(2);
        Redstone				.setOreMultiplier( 5).setSmeltingMultiplier( 5);
        Glowstone				.setOreMultiplier( 5).setSmeltingMultiplier( 5);
        Lapis					.setOreMultiplier( 6).setSmeltingMultiplier( 6).setByProductMultiplier(4);
        Sodalite				.setOreMultiplier( 6).setSmeltingMultiplier( 6).setByProductMultiplier(4);
        Lazurite				.setOreMultiplier( 6).setSmeltingMultiplier( 6).setByProductMultiplier(4);
        Monazite				.setOreMultiplier( 8).setSmeltingMultiplier( 8).setByProductMultiplier(2);

        Plastic					.setEnchantmentForTools(Enchantments.KNOCKBACK, 1);
        Rubber					.setEnchantmentForTools(Enchantments.KNOCKBACK, 2);

        Vinteum					.setEnchantmentForTools(Enchantments.FORTUNE, 1);

        Flint					.setEnchantmentForTools(Enchantments.FIRE_ASPECT, 1);
        Pyrotheum				.setEnchantmentForTools(Enchantments.FIRE_ASPECT, 3);
        Blaze					.setEnchantmentForTools(Enchantments.FIRE_ASPECT, 3);

        EnderPearl				.setEnchantmentForTools(Enchantments.SILK_TOUCH, 1);
        NetherStar				.setEnchantmentForTools(Enchantments.SILK_TOUCH, 1);

        BlackBronze				.setEnchantmentForTools(Enchantments.SMITE, 2);
        Gold					.setEnchantmentForTools(Enchantments.SMITE, 3);
        RoseGold				.setEnchantmentForTools(Enchantments.SMITE, 4);
        Platinum				.setEnchantmentForTools(Enchantments.SMITE, 5);

        Lead					.setEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 2);
        Nickel					.setEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 2);
        Invar					.setEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 3);
        Antimony				.setEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 3);
        BatteryAlloy			.setEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 4);
        Bismuth					.setEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 4);
        BismuthBronze			.setEnchantmentForTools(Enchantments.BANE_OF_ARTHROPODS, 5);

        Iron					.setEnchantmentForTools(Enchantments.SHARPNESS, 1);
        Bronze					.setEnchantmentForTools(Enchantments.SHARPNESS, 1);
        Brass					.setEnchantmentForTools(Enchantments.SHARPNESS, 2);
        Steel					.setEnchantmentForTools(Enchantments.SHARPNESS, 2);
        WroughtIron				.setEnchantmentForTools(Enchantments.SHARPNESS, 2);
        StainlessSteel			.setEnchantmentForTools(Enchantments.SHARPNESS, 3);
        BlackSteel				.setEnchantmentForTools(Enchantments.SHARPNESS, 4);
        RedSteel				.setEnchantmentForTools(Enchantments.SHARPNESS, 4);
        BlueSteel				.setEnchantmentForTools(Enchantments.SHARPNESS, 5);
        DamascusSteel			.setEnchantmentForTools(Enchantments.SHARPNESS, 5);
        TungstenCarbide			.setEnchantmentForTools(Enchantments.SHARPNESS, 5);
        HSSE					.setEnchantmentForTools(Enchantments.SHARPNESS, 5);
        HSSG					.setEnchantmentForTools(Enchantments.SHARPNESS, 4);
        HSSS					.setEnchantmentForTools(Enchantments.SHARPNESS, 5);

        FryingOilHot			.setHeatDamage(1.0F);
        Lava					.setHeatDamage(3.0F);
        Pyrotheum				.setHeatDamage(5.0F);

        Chalcopyrite			.addOreByProducts(Pyrite				, Cobalt				, Cadmium				, Gold			);
        Sphalerite				.addOreByProducts(GarnetYellow			, Cadmium				, Gallium				, Zinc			);
        GlauconiteSand			.addOreByProducts(Sodium				, Aluminium				, Iron					);
        Glauconite				.addOreByProducts(Sodium				, Aluminium				, Iron					);
        Vermiculite				.addOreByProducts(Iron					, Aluminium				, Magnesium				);
        FullersEarth			.addOreByProducts(Aluminium				, Silicon				, Magnesium				);
        Bentonite				.addOreByProducts(Aluminium				, Calcium				, Magnesium				);
        Uraninite				.addOreByProducts(Uranium				, Thorium				, Uranium235			);
        Pitchblende				.addOreByProducts(Thorium				, Uranium				, Lead					);
        Galena					.addOreByProducts(Sulfur				, Silver				, Lead					);
        Lapis					.addOreByProducts(Lazurite				, Sodalite				, Pyrite				);
        Pyrite					.addOreByProducts(Sulfur				, Phosphorus			, Iron					);
        Copper					.addOreByProducts(Cobalt				, Gold					, Nickel				);
        Nickel					.addOreByProducts(Cobalt				, Platinum				, Iron					);
        GarnetRed				.addOreByProducts(Spessartine			, Pyrope				, Almandine				);
        GarnetYellow			.addOreByProducts(Andradite				, Grossular				, Uvarovite				);
        Cooperite				.addOreByProducts(Palladium				, Nickel				, Iridium				);
        Cinnabar				.addOreByProducts(Redstone				, Sulfur				, Glowstone				);
        Tantalite				.addOreByProducts(Manganese				, Niobium				, Tantalum				);
        Pollucite				.addOreByProducts(Caesium				, Aluminium				, Rubidium				);
        Chrysotile				.addOreByProducts(Asbestos				, Silicon				, Magnesium				);
        Asbestos				.addOreByProducts(Asbestos				, Silicon				, Magnesium				);
        Pentlandite				.addOreByProducts(Iron					, Sulfur				, Cobalt				);
        Uranium					.addOreByProducts(Lead					, Uranium235			, Thorium				);
        Scheelite				.addOreByProducts(Manganese				, Molybdenum			, Calcium				);
        Tungstate				.addOreByProducts(Manganese				, Silver				, Lithium				);
        Bauxite					.addOreByProducts(Grossular				, Rutile				, Gallium				);
        QuartzSand				.addOreByProducts(CertusQuartz			, Quartzite				, Barite				);
        Quartzite				.addOreByProducts(CertusQuartz			, Barite				);
        CertusQuartz			.addOreByProducts(Quartzite				, Barite				);
        Redstone				.addOreByProducts(Cinnabar				, RareEarth				, Glowstone				);
        Monazite				.addOreByProducts(Thorium				, Neodymium				, RareEarth				);
        Malachite				.addOreByProducts(Copper				, BrownLimonite			, Calcite				);
        YellowLimonite			.addOreByProducts(Nickel				, BrownLimonite			, Cobalt				);
        BrownLimonite			.addOreByProducts(Malachite				, YellowLimonite		);
        Neodymium				.addOreByProducts(Monazite				, RareEarth				);
        Bastnasite				.addOreByProducts(Neodymium				, RareEarth				);
        Glowstone				.addOreByProducts(Redstone				, Gold					);
        Zinc					.addOreByProducts(Tin					, Gallium				);
        Tungsten				.addOreByProducts(Manganese				, Molybdenum			);
        Diatomite				.addOreByProducts(BandedIron			, Sapphire				);
        Iron					.addOreByProducts(Nickel				, Tin					);
        Lepidolite				.addOreByProducts(Lithium				, Caesium				);
        Gold					.addOreByProducts(Copper				, Nickel				);
        Tin						.addOreByProducts(Iron					, Zinc					);
        Antimony				.addOreByProducts(Zinc					, Iron					);
        Silver					.addOreByProducts(Lead					, Sulfur				);
        Lead					.addOreByProducts(Silver				, Sulfur				);
        Thorium					.addOreByProducts(Uranium				, Lead					);
        Plutonium				.addOreByProducts(Uranium				, Lead					);
        Electrum				.addOreByProducts(Gold					, Silver				);
        Bronze					.addOreByProducts(Copper				, Tin					);
        Brass					.addOreByProducts(Copper				, Zinc					);
        Coal					.addOreByProducts(Lignite				, Thorium				);
        Ilmenite				.addOreByProducts(Iron					, Rutile				);
        Manganese				.addOreByProducts(Chrome				, Iron					);
        Sapphire				.addOreByProducts(Aluminium				, GreenSapphire			);
        GreenSapphire			.addOreByProducts(Aluminium				, Sapphire				);
        Platinum				.addOreByProducts(Nickel				, Iridium				);
        Emerald					.addOreByProducts(Beryllium				, Aluminium				);
        Olivine					.addOreByProducts(Pyrope				, Magnesium				);
        Chrome					.addOreByProducts(Iron					, Magnesium				);
        Chromite				.addOreByProducts(Iron					, Magnesium				);
        Tetrahedrite			.addOreByProducts(Antimony				, Zinc					);
        GarnetSand				.addOreByProducts(GarnetRed				, GarnetYellow			);
        Magnetite				.addOreByProducts(Iron					, Gold					);
        GraniticMineralSand		.addOreByProducts(GraniteBlack			, Magnetite				);
        BasalticMineralSand		.addOreByProducts(Basalt				, Magnetite				);
        Basalt					.addOreByProducts(Olivine				, DarkAsh				);
        VanadiumMagnetite		.addOreByProducts(Magnetite				, Vanadium				);
        Lazurite				.addOreByProducts(Sodalite				, Lapis					);
        Sodalite				.addOreByProducts(Lazurite				, Lapis					);
        Spodumene				.addOreByProducts(Aluminium				, Lithium				);
        Ruby					.addOreByProducts(Chrome				, GarnetRed				);
        Phosphorus				.addOreByProducts(Apatite				, Phosphate				);
        Iridium					.addOreByProducts(Platinum				, Osmium				);
        Pyrope					.addOreByProducts(GarnetRed				, Magnesium				);
        Almandine				.addOreByProducts(GarnetRed				, Aluminium				);
        Spessartine				.addOreByProducts(GarnetRed				, Manganese				);
        Andradite				.addOreByProducts(GarnetYellow			, Iron					);
        Grossular				.addOreByProducts(GarnetYellow			, Calcium				);
        Uvarovite				.addOreByProducts(GarnetYellow			, Chrome				);
        Calcite					.addOreByProducts(Andradite				, Malachite				);
        NaquadahEnriched		.addOreByProducts(Naquadah				, Naquadria				);
        Naquadah				.addOreByProducts(NaquadahEnriched		);
        Pyrolusite				.addOreByProducts(Manganese				);
        Molybdenite				.addOreByProducts(Molybdenum			);
        Stibnite				.addOreByProducts(Antimony				);
        Garnierite				.addOreByProducts(Nickel				);
        Lignite					.addOreByProducts(Coal					);
        Diamond					.addOreByProducts(Graphite				);
        Beryllium				.addOreByProducts(Emerald				);
        Apatite					.addOreByProducts(Phosphorus			);
        Magnesite				.addOreByProducts(Magnesium				);
        NetherQuartz			.addOreByProducts(Netherrack			);
        PigIron					.addOreByProducts(Iron					);
        Steel					.addOreByProducts(Iron					);
        Graphite				.addOreByProducts(Carbon				);
        Netherrack				.addOreByProducts(Sulfur				);
        Flint					.addOreByProducts(Obsidian				);
        Cobaltite				.addOreByProducts(Cobalt				);
        Cobalt					.addOreByProducts(Cobaltite				);
        Sulfur					.addOreByProducts(Sulfur				);
        Saltpeter				.addOreByProducts(Saltpeter				);
        Endstone				.addOreByProducts(Helium_3				);
        Osmium					.addOreByProducts(Iridium				);
        Magnesium				.addOreByProducts(Olivine				);
        Aluminium				.addOreByProducts(Bauxite				);
        Titanium				.addOreByProducts(Almandine				);
        Obsidian				.addOreByProducts(Olivine				);
        Ash						.addOreByProducts(Carbon				);
        DarkAsh					.addOreByProducts(Carbon				);
        Redrock					.addOreByProducts(Clay					);
        Marble					.addOreByProducts(Calcite				);
        Clay					.addOreByProducts(Clay					);
        Cassiterite				.addOreByProducts(Tin					);
        CassiteriteSand			.addOreByProducts(Tin					);
        GraniteBlack			.addOreByProducts(Biotite				);
        GraniteRed				.addOreByProducts(PotassiumFeldspar		);
        Phosphate				.addOreByProducts(Phosphor				);
        Phosphor				.addOreByProducts(Phosphate				);
        Tanzanite				.addOreByProducts(Opal					);
        Opal					.addOreByProducts(Tanzanite				);
        Amethyst				.addOreByProducts(Amethyst				);
        Topaz					.addOreByProducts(BlueTopaz				);
        BlueTopaz				.addOreByProducts(Topaz					);
        Niter					.addOreByProducts(Saltpeter				);
        Vinteum					.addOreByProducts(Vinteum				);
        Neutronium				.addOreByProducts(Neutronium			);
        Lithium					.addOreByProducts(Lithium				);
        Silicon					.addOreByProducts(SiliconDioxide		);
        Salt					.addOreByProducts(RockSalt				);
        RockSalt				.addOreByProducts(Salt					);
        Andesite                .addOreByProducts(Basalt                );
        Diorite                 .addOreByProducts(NetherQuartz          );

        Glue.mChemicalFormula = "No Horses were harmed for the Production";
        UUAmplifier.mChemicalFormula = "Accelerates the Mass Fabricator";
        WoodSealed.mChemicalFormula = "";
        Wood.mChemicalFormula = "";
        NaquadahEnriched.mChemicalFormula = "Nq+";
        Naquadah.mChemicalFormula = "Nq";
        Naquadria.mChemicalFormula = "NqX";
    }

    private static void initSubTags() {
        SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM.addTo(Bastnasite, Monazite);
        SubTag.ELECTROMAGNETIC_SEPERATION_GOLD.addTo(Magnetite, VanadiumMagnetite, BasalticMineralSand, GraniticMineralSand);
        SubTag.ELECTROMAGNETIC_SEPERATION_IRON.addTo(YellowLimonite, BrownLimonite, Pyrite, BandedIron, Nickel, Vermiculite, Glauconite, GlauconiteSand, Pentlandite, Tin, Antimony, Ilmenite, Manganese, Chrome, Chromite, Andradite);
        SubTag.BLASTFURNACE_CALCITE_DOUBLE.addTo(Pyrite, YellowLimonite, BasalticMineralSand, GraniticMineralSand);
        SubTag.BLASTFURNACE_CALCITE_TRIPLE.addTo(Iron, PigIron, WroughtIron, BrownLimonite);
        SubTag.WASHING_MERCURY.addTo(Gold, Silver, Osmium, Platinum, Cooperite);
        SubTag.WASHING_SODIUMPERSULFATE.addTo(Zinc, Nickel, Copper, Cobalt, Cobaltite, Tetrahedrite);
        SubTag.METAL.addTo(AnyIron, AnyCopper, AnyBronze, Aluminium, Americium, Antimony, Beryllium, Bismuth, Caesium, Cerium, Chrome, Cobalt, Copper, Dysprosium, Erbium, Europium, Gadolinium, Gallium, Gold,
                Holmium, Indium, Iridium, Iron, Lanthanum, Lead, Lutetium, Magnesium, Manganese, Mercury, Niobium, Molybdenum, Neodymium, Neutronium, Nickel, Osmium, Palladium, Platinum, Plutonium, Plutonium241,
                Praseodymium, Promethium, Rubidium, Samarium, Scandium, Silicon, Silver, Tantalum, Tellurium, Terbium, Thorium, Thulium, Tin, Titanium, Tungsten, Uranium, Uranium235, Vanadium, Ytterbium, Yttrium,
                Zinc, TinAlloy, Duranium, Naquadah, NaquadahAlloy, NaquadahEnriched, Naquadria, Tritanium, AluminiumBrass, Osmiridium, AnnealedCopper, BatteryAlloy, Brass, Bronze, ChromiumDioxide, Cupronickel,
                Electrum, Invar, IronCompressed, Kanthal, Magnalium, Nichrome, NiobiumNitride, NiobiumTitanium, PigIron, SolderingAlloy, StainlessSteel, Steel, Ultimet, VanadiumGallium, WroughtIron,
                YttriumBariumCuprate, Alumite, Manyullyn, SterlingSilver, RoseGold, BlackBronze, BismuthBronze, BlackSteel, RedSteel, BlueSteel, DamascusSteel,
                TungstenSteel, BlueAlloy, RedAlloy, CobaltBrass, IronMagnetic, SteelMagnetic, NeodymiumMagnetic, HSSG, HSSE, HSSS, TungstenCarbide,
                VanadiumSteel);

        SubTag.FOOD.addTo(MeatRaw, MeatCooked, Ice, Water, Salt, Chili, Cocoa, Cheese, Coffee, Chocolate, Milk, Honey, FryingOilHot, FishOil, SeedOil, Sugar, FreshWater);

        Wood.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        WoodSealed.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.NO_WORKING);

        MeatRaw.add(SubTag.NO_SMASHING);
        MeatCooked.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Snow.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        Ice.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        Water.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        Sulfur.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        Saltpeter.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        Graphite.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.NO_SMELTING);

        Paper.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE, SubTag.PAPER);
        Coal.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        Charcoal.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        Lignite.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);

        Rubber.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY, SubTag.STRETCHY);
        Plastic.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY);
        Silicone.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY, SubTag.STRETCHY);

        Gunpowder.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        Glyceryl.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        NitroFuel.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        NitroCarbon.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);

        Lead.add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL, SubTag.SOLDERING_MATERIAL_BAD);
        Tin.add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL);
        SolderingAlloy.add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL, SubTag.SOLDERING_MATERIAL_GOOD);

        Cheese.add(SubTag.SMELTING_TO_FLUID);
        Sugar.add(SubTag.SMELTING_TO_FLUID);

        Concrete.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.SMELTING_TO_FLUID);
        ConstructionFoam.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.EXPLOSIVE, SubTag.NO_SMELTING);
        Redstone.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.SMELTING_TO_FLUID, SubTag.PULVERIZING_CINNABAR);
        Glowstone.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.SMELTING_TO_FLUID);
        Netherrack.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.FLAMMABLE);
        Stone.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.NO_RECYCLING);
        Endstone.add(SubTag.STONE, SubTag.NO_SMASHING);
        Marble.add(SubTag.STONE, SubTag.NO_SMASHING);
        Basalt.add(SubTag.STONE, SubTag.NO_SMASHING);
        Redrock.add(SubTag.STONE, SubTag.NO_SMASHING);
        Obsidian.add(SubTag.STONE, SubTag.NO_SMASHING);
        Flint.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        GraniteRed.add(SubTag.STONE, SubTag.NO_SMASHING);
        GraniteBlack.add(SubTag.STONE, SubTag.NO_SMASHING);
        Salt.add(SubTag.STONE, SubTag.NO_SMASHING);
        RockSalt.add(SubTag.STONE, SubTag.NO_SMASHING);
        Andesite.add(SubTag.STONE, SubTag.NO_SMASHING);
        Diorite.add(SubTag.STONE, SubTag.NO_SMASHING);

        Sand.add(SubTag.NO_RECYCLING);

        Gold.add(SubTag.MORTAR_GRINDABLE);
        Silver.add(SubTag.MORTAR_GRINDABLE);
        Iron.add(SubTag.MORTAR_GRINDABLE);
        IronMagnetic.add(SubTag.MORTAR_GRINDABLE);
        Steel.add(SubTag.MORTAR_GRINDABLE);
        SteelMagnetic.add(SubTag.MORTAR_GRINDABLE);
        Zinc.add(SubTag.MORTAR_GRINDABLE);
        Antimony.add(SubTag.MORTAR_GRINDABLE);
        Copper.add(SubTag.MORTAR_GRINDABLE);
        AnnealedCopper.add(SubTag.MORTAR_GRINDABLE);
        Bronze.add(SubTag.MORTAR_GRINDABLE);
        Nickel.add(SubTag.MORTAR_GRINDABLE);
        Invar.add(SubTag.MORTAR_GRINDABLE);
        Brass.add(SubTag.MORTAR_GRINDABLE);
        WroughtIron.add(SubTag.MORTAR_GRINDABLE);
        Electrum.add(SubTag.MORTAR_GRINDABLE);
        Clay.add(SubTag.MORTAR_GRINDABLE);

        Glass.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_RECYCLING, SubTag.SMELTING_TO_FLUID);
        Diamond.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        Emerald.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Amethyst.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Tanzanite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Topaz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        BlueTopaz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        GreenSapphire.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Sapphire.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Ruby.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Opal.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Olivine.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Jasper.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        GarnetRed.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        GarnetYellow.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Niter.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Apatite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Lapis.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Sodalite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Lazurite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Monazite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Quartzite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        SiliconDioxide.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        NetherQuartz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        CertusQuartz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        Phosphorus.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.EXPLOSIVE);
        Phosphate.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.EXPLOSIVE);
        Vinteum.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        NetherStar.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        EnderPearl.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.PEARL);
        EnderEye.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.PEARL);
        Magic.add(SubTag.CRYSTAL, SubTag.MAGICAL, SubTag.UNBURNABLE);
        Blaze.add(SubTag.MAGICAL, SubTag.NO_SMELTING, SubTag.SMELTING_TO_FLUID, SubTag.MORTAR_GRINDABLE, SubTag.UNBURNABLE, SubTag.BURNING);

        Primitive.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Basic.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Good.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Advanced.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Data.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Elite.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Master.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Ultimate.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Superconductor.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Infinite.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
    }

}
