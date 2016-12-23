package gregtech.api.enums;

import cpw.mods.fml.common.Loader;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.TC_Aspects.TC_AspectStack;
import gregtech.api.interfaces.IColorModulationContainer;
import gregtech.api.interfaces.IMaterialHandler;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.objects.GT_FluidStack;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.loaders.materialprocessing.ProcessingConfig;
import gregtech.loaders.materialprocessing.ProcessingModSupport;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

import static gregtech.api.enums.GT_Values.M;

public class Materials implements IColorModulationContainer, ISubTagContainer {
    private static Materials[] MATERIALS_ARRAY = new Materials[]{};
    private static final Map<String, Materials> MATERIALS_MAP = new LinkedHashMap<String, Materials>();
    public static final List<IMaterialHandler> mMaterialHandlers = new ArrayList<IMaterialHandler>();

    /**
     * This is for keeping compatibility with addons mods (Such as TinkersGregworks etc) that looped over the old materials enum
     */
    @Deprecated
    public static Collection<Materials> VALUES = new LinkedHashSet<Materials>();

    /**
     * This is the Default Material returned in case no Material has been found or a NullPointer has been inserted at a location where it shouldn't happen.
     */
    public static Materials _NULL = new Materials(-1, TextureSet.SET_NONE, 1.0F, 0, 0, 0, 255, 255, 255, 0,	"NULL", "NULL", 0, 0, 0, 0, false, false, 1, 1, 1, Dyes._NULL, Element._NULL, Arrays.asList(new TC_AspectStack(TC_Aspects.VACUOS, 1)));

    /**
     * Direct Elements
     */
    public static Materials Aluminium = new Materials(19, TextureSet.SET_DULL, 	   10.0F, 128, 2, 1|2|8|32|64|128, 128, 200, 240, 0, "Aluminium", "Aluminium", 0, 0, 933, 1700, true, false, 3, 1, 1, Dyes.dyeLightBlue, Element.Al, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VOLATUS, 1)));
    public static Materials Americium = new Materials(103, TextureSet.SET_METALLIC, 1.0F, 0, 3, 1|2|32, 200, 200, 200, 0, "Americium", "Americium", 0, 0, 1449, 0, false, false, 3, 1, 1, Dyes.dyeLightGray, Element.Am, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Antimony = new Materials(58, TextureSet.SET_SHINY, 		1.0F, 0, 2, 1|2|32, 220, 220, 240, 0, "Antimony", "Antimony", 0, 0, 903, 0, false, false, 2, 1, 1, Dyes.dyeLightGray, Element.Sb, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.AQUA, 1)));
    public static Materials Argon = new Materials(24, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 0, 255, 0, 240, "Argon", "Argon", 0, 0, 83, 0, false, true, 5, 1, 1, Dyes.dyeGreen, Element.Ar, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AER, 2)));
    public static Materials Arsenic = new Materials(39, TextureSet.SET_DULL, 		1.0F, 0, 2, 1|2|16|32, 255, 255, 255, 0, "Arsenic", "Arsenic", 0, 0, 1090, 0, false, false, 3, 1, 1, Dyes.dyeOrange, Element.As, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 3)));
    public static Materials Barium = new Materials(63, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|32, 255, 255, 255, 0, "Barium", "Barium", 0, 0, 1000, 0, false, false, 1, 1, 1, Dyes._NULL, Element.Ba, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VINCULUM, 3)));
    public static Materials Beryllium = new Materials(8, TextureSet.SET_METALLIC,  14.0F, 64, 2, 1|2|8|32|64, 100, 180, 100, 0, "Beryllium", "Beryllium", 0, 0, 1560, 0, false, false, 6, 1, 1, Dyes.dyeGreen, Element.Be, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 1)));
    public static Materials Bismuth = new Materials(90, TextureSet.SET_METALLIC, 	6.0F, 64, 1, 1|2|8|32|64|128, 100, 160, 160, 0, "Bismuth", "Bismuth", 0, 0, 544, 0, false, false, 2, 1, 1, Dyes.dyeCyan, Element.Bi, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)));
    public static Materials Boron = new Materials(9, TextureSet.SET_DULL, 			1.0F, 0, 2, 1|32, 250, 250, 250, 0, "Boron", "Boron", 0, 0, 2349, 0, false, false, 1, 1, 1, Dyes.dyeWhite, Element.B, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 3)));
    public static Materials Caesium = new Materials(62, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Caesium", "Caesium", 0, 0, 301, 0, false, false, 4, 1, 1, Dyes._NULL, Element.Cs, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Calcium = new Materials(26, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1 |32, 255, 245, 245, 0, "Calcium", "Calcium", 0, 0, 1115, 0, false, false, 4, 1, 1, Dyes.dyePink, Element.Ca, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1), new TC_AspectStack(TC_Aspects.TUTAMEN, 1)));
    public static Materials Carbon = new Materials(10, TextureSet.SET_DULL, 		1.0F, 64, 2, 1|2|32|64|128, 20, 20, 20, 0, "Carbon", "Carbon", 0, 0, 3800, 0, false, false, 2, 1, 1, Dyes.dyeBlack, Element.C, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.IGNIS, 1)));
    public static Materials Cadmium = new Materials(55, TextureSet.SET_SHINY, 		1.0F, 0, 2, 1|32, 50, 50, 60, 0, "Cadmium", "Cadmium", 0, 0, 594, 0, false, false, 3, 1, 1, Dyes.dyeGray, Element.Cd, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VENENUM, 1)));
    public static Materials Cerium = new Materials(65, TextureSet.SET_METALLIC,		1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Cerium", "Cerium", 0, 0, 1068, 1068, true, false, 4, 1, 1, Dyes._NULL, Element.Ce, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Chlorine = new Materials(23, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 255, 255, 255, 0, "Chlorine", "Chlorine", 0, 0, 171, 0, false, false, 2, 1, 1, Dyes.dyeCyan, Element.Cl, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.PANNUS, 1)));
    public static Materials Chrome = new Materials(30, TextureSet.SET_SHINY, 	   11.0F, 256, 3, 1|2|8|32|64|128, 255, 230, 230, 0, "Chrome", "Chrome", 0, 0, 2180, 1700, true, false, 5, 1, 1, Dyes.dyePink, Element.Cr, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)));
    public static Materials Cobalt = new Materials(33, TextureSet.SET_METALLIC, 	8.0F, 512, 3, 1|2|32|64, 80, 80, 250, 0, "Cobalt", "Cobalt", 0, 0, 1768, 0, false, false, 3, 1, 1, Dyes.dyeBlue, Element.Co, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)));
    public static Materials Copper = new Materials(35, TextureSet.SET_SHINY, 		1.0F, 0, 1, 1|2|8|32|128, 255, 100, 0, 0, "Copper", "Copper", 0, 0, 1357, 0, false, false, 3, 1, 1, Dyes.dyeOrange, Element.Cu, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PERMUTATIO, 1)));
    public static Materials Deuterium = new Materials(2, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 255, 255, 0, 240, "Deuterium", "Deuterium", 0, 0, 14, 0, false, true, 10, 1, 1, Dyes.dyeYellow, Element.D, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 3)));
    public static Materials Dysprosium = new Materials(73, TextureSet.SET_METALLIC, 1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Dysprosium", "Dysprosium", 0, 0, 1680, 1680, true, false, 4, 1, 1, Dyes._NULL, Element.Dy, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 3)));
    public static Materials Empty = new Materials(0, TextureSet.SET_NONE, 			1.0F, 0, 2, 256/*Only for Prefixes which need it*/, 255, 255, 255, 255, "Empty", "Empty", 0, 0, -1, 0, false, true, 1, 1, 1, Dyes._NULL, Element._NULL, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 2)));
    public static Materials Erbium = new Materials(75, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Erbium", "Erbium", 0, 0, 1802, 1802, true, false, 4, 1, 1, Dyes._NULL, Element.Er, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Europium = new Materials(70, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Europium", "Europium", 0, 0, 1099, 1099, true, false, 4, 1, 1, Dyes._NULL, Element.Eu, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Fluorine = new Materials(14, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 255, 255, 255, 127, "Fluorine", "Fluorine", 0, 0, 53, 0, false, true, 2, 1, 1, Dyes.dyeGreen, Element.F, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2)));
    public static Materials Gadolinium = new Materials(71, TextureSet.SET_METALLIC, 1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Gadolinium", "Gadolinium", 0, 0, 1585, 1585, true, false, 4, 1, 1, Dyes._NULL, Element.Gd, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Gallium = new Materials(37, TextureSet.SET_SHINY, 		1.0F, 64, 2, 1|2|32, 220, 220, 255, 0, "Gallium", "Gallium", 0, 0, 302, 0, false, false, 5, 1, 1, Dyes.dyeLightGray, Element.Ga, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1)));
    public static Materials Gold = new Materials(86, TextureSet.SET_SHINY, 		   12.0F, 64, 2, 1|2|8|32|64|128, 255, 255, 30, 0, "Gold", "Gold", 0, 0, 1337, 0, false, false, 4, 1, 1, Dyes.dyeYellow, Element.Au, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 2)));
    public static Materials Holmium = new Materials(74, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Holmium", "Holmium", 0, 0, 1734, 1734, true, false, 4, 1, 1, Dyes._NULL, Element.Ho, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Hydrogen = new Materials(1, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 0, 0, 255, 240, "Hydrogen", "Hydrogen", 1, 15, 14, 0, false, true, 2, 1, 1, Dyes.dyeBlue, Element.H, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1)));
    public static Materials Helium = new Materials(4, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 255, 255, 0, 240, "Helium", "Helium", 0, 0, 1, 0, false, true, 5, 1, 1, Dyes.dyeYellow, Element.He, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AER, 2)));
    public static Materials Helium_3 = new Materials(5, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 255, 255, 0, 240, "Helium_3", "Helium-3", 0, 0, 1, 0, false, true, 10, 1, 1, Dyes.dyeYellow, Element.He_3, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AER, 3)));
    public static Materials Indium = new Materials(56, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 64, 0, 128, 0, "Indium", "Indium", 0, 0, 429, 0, false, false, 4, 1, 1, Dyes.dyeGray, Element.In, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Iridium = new Materials(84, TextureSet.SET_DULL, 		6.0F, 2560, 3, 1|2|8|32|64|128, 240, 240, 245, 0, "Iridium", "Iridium", 0, 0, 2719, 2719, true, false, 10, 1, 1, Dyes.dyeWhite, Element.Ir, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)));
    public static Materials Iron = new Materials(32, TextureSet.SET_METALLIC, 		6.0F, 256, 2, 1|2|8|32|64|128, 200, 200, 200, 0, "Iron", "Iron", 0, 0, 1811, 0, false, false, 3, 1, 1, Dyes.dyeLightGray, Element.Fe, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 3)));
    public static Materials Lanthanum = new Materials(64, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Lanthanum", "Lanthanum", 0, 0, 1193, 1193, true, false, 4, 1, 1, Dyes._NULL, Element.La, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Lead = new Materials(89, TextureSet.SET_DULL, 			8.0F, 64, 1, 1|2|8|32|64|128, 140, 100, 140, 0, "Lead", "Lead", 0, 0, 600, 0, false, false, 3, 1, 1, Dyes.dyePurple, Element.Pb, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 1)));
    public static Materials Lithium = new Materials(6, TextureSet.SET_DULL, 		1.0F, 0, 2, 1|2|8|32, 225, 220, 255, 0, "Lithium", "Lithium", 0, 0, 454, 0, false, false, 4, 1, 1, Dyes.dyeLightBlue, Element.Li, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 2)));
    public static Materials Lutetium = new Materials(78, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Lutetium", "Lutetium", 0, 0, 1925, 1925, true, false, 4, 1, 1, Dyes._NULL, Element.Lu, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Magic = new Materials(-128, TextureSet.SET_SHINY, 		8.0F, 5120, 5, 1|2|4|16|32|64|128, 100, 0, 200, 0, "Magic", "Magic", 5, 32, 5000, 0, false, false, 7, 1, 1, Dyes.dyePurple, Element.Ma, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 4)));
    public static Materials Magnesium = new Materials(18, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 200, 200, 0, "Magnesium", "Magnesium", 0, 0, 923, 0, false, false, 3, 1, 1, Dyes.dyePink, Element.Mg, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.SANO, 1)));
    public static Materials Manganese = new Materials(31, TextureSet.SET_DULL, 		7.0F, 512, 2, 1|2|8|32|64, 250, 250, 250, 0, "Manganese", "Manganese", 0, 0, 1519, 0, false, false, 3, 1, 1, Dyes.dyeWhite, Element.Mn, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 3)));
    public static Materials Mercury = new Materials(87, TextureSet.SET_SHINY, 		1.0F, 0, 0, 16|32, 255, 220, 220, 0, "Mercury", "Mercury", 5, 32, 234, 0, false, false, 3, 1, 1, Dyes.dyeLightGray, Element.Hg, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.VENENUM, 1)));
    public static Materials Molybdenum = new Materials(48, TextureSet.SET_SHINY, 	7.0F, 512, 2, 1|2|8|32|64, 180, 180, 220, 0, "Molybdenum", "Molybdenum", 0, 0, 2896, 0, false, false, 1, 1, 1, Dyes.dyeBlue, Element.Mo, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)));
    public static Materials Neodymium = new Materials(67, TextureSet.SET_METALLIC, 	7.0F, 512, 2, 1|2|8|32|64|128, 100, 100, 100, 0, "Neodymium", "Neodymium", 0, 0, 1297, 1297, true, false, 4, 1, 1, Dyes._NULL, Element.Nd, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 2)));
    public static Materials Neutronium = new Materials(129, TextureSet.SET_DULL,   24.0F, 655360, 6, 1|2|32|64|128, 250, 250, 250, 0, "Neutronium", "Neutronium", 0, 0, 10000, 0, false, false, 20, 1, 1, Dyes.dyeWhite, Element.Nt, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.ALIENIS, 2)));
    public static Materials Nickel = new Materials(34, TextureSet.SET_METALLIC, 	6.0F, 64, 2, 1|2|8|32|64|128, 200, 200, 250, 0, "Nickel", "Nickel", 0, 0, 1728, 0, false, false, 4, 1, 1, Dyes.dyeLightBlue, Element.Ni, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1)));
    public static Materials Niobium = new Materials(47, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 190, 180, 200, 0, "Niobium", "Niobium", 0, 0, 2750, 2750, true, false, 5, 1, 1, Dyes._NULL, Element.Nb, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1)));
    public static Materials Nitrogen = new Materials(12, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 0, 150, 200, 240, "Nitrogen", "Nitrogen", 0, 0, 63, 0, false, true, 2, 1, 1, Dyes.dyeCyan, Element.N, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AER, 2)));
    public static Materials Osmium = new Materials(83, TextureSet.SET_METALLIC, 	16.0F, 1280, 4, 1|2|8|32|64|128, 50, 50, 255, 0, "Osmium", "Osmium", 0, 0, 3306, 3306, true, false, 10, 1, 1, Dyes.dyeBlue, Element.Os, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)));
    public static Materials Oxygen = new Materials(13, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 0, 100, 200, 240, "Oxygen", "Oxygen", 0, 0, 54, 0, false, true, 1, 1, 1, Dyes.dyeWhite, Element.O, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AER, 1)));
    public static Materials Palladium = new Materials(52, TextureSet.SET_SHINY, 	8.0F, 512, 2, 1|2|8|32|64|128, 128, 128, 128, 0, "Palladium", "Palladium", 0, 0, 1828, 1828, true, false, 4, 1, 1, Dyes.dyeGray, Element.Pd, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 3)));
    public static Materials Phosphor = new Materials(21, TextureSet.SET_DULL, 		1.0F, 0, 2, 1|32, 255, 255, 0, 0, "Phosphor", "Phosphor", 0, 0, 317, 0, false, false, 2, 1, 1, Dyes.dyeYellow, Element.P, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 2), new TC_AspectStack(TC_Aspects.POTENTIA, 1)));
    public static Materials Platinum = new Materials(85, TextureSet.SET_SHINY, 		12.0F, 64, 2, 1|2|8|32|64|128, 255, 255, 200, 0, "Platinum", "Platinum", 0, 0, 2041, 0, false, false, 6, 1, 1, Dyes.dyeOrange, Element.Pt, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)));
    public static Materials Plutonium = new Materials(100, TextureSet.SET_METALLIC, 6.0F, 512, 3, 1|2|8|32|64, 240, 50, 50, 0, "Plutonium", "Plutonium 244", 0, 0, 912, 0, false, false, 6, 1, 1, Dyes.dyeLime, Element.Pu, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 2)));
    public static Materials Plutonium241 = new Materials(101, TextureSet.SET_SHINY, 6.0F, 512, 3, 1|2|32|64, 250, 70, 70, 0, "Plutonium241", "Plutonium 241", 0, 0, 912, 0, false, false, 6, 1, 1, Dyes.dyeLime, Element.Pu_241, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 3)));
    public static Materials Potassium = new Materials(25, TextureSet.SET_METALLIC, 	1.0F, 0, 1, 1|2|32, 250, 250, 250, 0, "Potassium", "Potassium", 0, 0, 336, 0, false, false, 2, 1, 1, Dyes.dyeWhite, Element.K, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 1)));
    public static Materials Praseodymium =new Materials(66, TextureSet.SET_METALLIC,1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Praseodymium", "Praseodymium", 0, 0, 1208, 1208, true, false, 4, 1, 1, Dyes._NULL, Element.Pr, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Promethium = new Materials(68, TextureSet.SET_METALLIC, 1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Promethium", "Promethium", 0, 0, 1315, 1315, true, false, 4, 1, 1, Dyes._NULL, Element.Pm, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Radon = new Materials(93, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 255, 0, 255, 240, "Radon", "Radon", 0, 0, 202, 0, false, true, 5, 1, 1, Dyes.dyePurple, Element.Rn, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AER, 1), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Rubidium = new Materials(43, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 240, 30, 30, 0, "Rubidium", "Rubidium", 0, 0, 312, 0, false, false, 4, 1, 1, Dyes.dyeRed, Element.Rb, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1)));
    public static Materials Samarium = new Materials(69, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Samarium", "Samarium", 0, 0, 1345, 1345, true, false, 4, 1, 1, Dyes._NULL, Element.Sm, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Scandium = new Materials(27, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Scandium", "Scandium", 0, 0, 1814, 1814, true, false, 2, 1, 1, Dyes.dyeYellow, Element.Sc, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Silicon = new Materials(20, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 60, 60, 80, 0, "Silicon", "Silicon", 0, 0, 1687, 1687, true, false, 1, 1, 1, Dyes.dyeBlack, Element.Si, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.TENEBRAE, 1)));
    public static Materials Silver = new Materials(54, TextureSet.SET_SHINY, 		10.0F, 64, 2, 1|2|8|32|64|128, 220, 220, 255, 0, "Silver", "Silver", 0, 0, 1234, 0, false, false, 3, 1, 1, Dyes.dyeLightGray, Element.Ag, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 1)));
    public static Materials Sodium = new Materials(17, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1 |32, 0, 0, 150, 0, "Sodium", "Sodium", 0, 0, 370, 0, false, false, 1, 1, 1, Dyes.dyeBlue, Element.Na, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.LUX, 1)));
    public static Materials Strontium = new Materials(44, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|32, 200, 200, 200, 0, "Strontium", "Strontium", 0, 0, 1050, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Element.Sr, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.STRONTIO, 1)));
    public static Materials Sulfur = new Materials(22, TextureSet.SET_DULL, 		1.0F, 0, 2, 1 |8|32, 200, 200, 0, 0, "Sulfur", "Sulfur", 0, 0, 388, 0, false, false, 2, 1, 1, Dyes.dyeYellow, Element.S, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1)));
    public static Materials Tantalum = new Materials(80, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Tantalum", "Tantalum", 0, 0, 3290, 0, false, false, 4, 1, 1, Dyes._NULL, Element.Ta, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VINCULUM, 1)));
    public static Materials Tellurium = new Materials(59, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Tellurium", "Tellurium", 0, 0, 722, 0, false, false, 4, 1, 1, Dyes.dyeGray, Element.Te, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Terbium = new Materials(72, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Terbium", "Terbium", 0, 0, 1629, 1629, true, false, 4, 1, 1, Dyes._NULL, Element.Tb, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Thorium = new Materials(96, TextureSet.SET_SHINY, 		6.0F, 512, 2, 1|2|8|32|64, 0, 30, 0, 0, "Thorium", "Thorium", 0, 0, 2115, 0, false, false, 4, 1, 1, Dyes.dyeBlack, Element.Th, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Thulium = new Materials(76, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Thulium", "Thulium", 0, 0, 1818, 1818, true, false, 4, 1, 1, Dyes._NULL, Element.Tm, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Tin = new Materials(57, TextureSet.SET_DULL, 			1.0F, 0, 1, 1|2|8|32|128, 220, 220, 220, 0, "Tin", "Tin", 0, 0, 505, 505, false, false, 3, 1, 1, Dyes.dyeWhite, Element.Sn, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1)));
    public static Materials Titanium = new Materials(28, TextureSet.SET_METALLIC, 	7.0F, 1600, 3, 1|2|8|32|64|128, 220, 160, 240, 0, "Titanium", "Titanium", 0, 0, 1941, 1940, true, false, 5, 1, 1, Dyes.dyePurple, Element.Ti, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.TUTAMEN, 1)));
    public static Materials Tritium = new Materials(3, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 16|32, 255, 0, 0, 240, "Tritium", "Tritium", 0, 0, 14, 0, false, true, 10, 1, 1, Dyes.dyeRed, Element.T, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 4)));
    public static Materials Tungsten = new Materials(81, TextureSet.SET_METALLIC, 	7.0F, 2560, 3, 1|2|32|64|128, 50, 50, 50, 0, "Tungsten", "Tungsten", 0, 0, 3695, 3000, true, false, 4, 1, 1, Dyes.dyeBlack, Element.W, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.TUTAMEN, 1)));
    public static Materials Uranium = new Materials(98, TextureSet.SET_METALLIC, 	6.0F, 512, 3, 1|2|8|32|64, 50, 240, 50, 0, "Uranium", "Uranium 238", 0, 0, 1405, 0, false, false, 4, 1, 1, Dyes.dyeGreen, Element.U, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Uranium235 = new Materials(97, TextureSet.SET_SHINY, 	6.0F, 512, 3, 1|2|8|32|64, 70, 250, 70, 0, "Uranium235", "Uranium 235", 0, 0, 1405, 0, false, false, 4, 1, 1, Dyes.dyeGreen, Element.U_235, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 2)));
    public static Materials Vanadium = new Materials(29, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 50, 50, 50, 0, "Vanadium", "Vanadium", 0, 0, 2183, 2183, true, false, 2, 1, 1, Dyes.dyeBlack, Element.V, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Ytterbium = new Materials(77, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 255, 255, 255, 0, "Ytterbium", "Ytterbium", 0, 0, 1097, 1097, true, false, 4, 1, 1, Dyes._NULL, Element.Yb, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Yttrium = new Materials(45, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1|2|32, 220, 250, 220, 0, "Yttrium", "Yttrium", 0, 0, 1799, 1799, true, false, 4, 1, 1, Dyes._NULL, Element.Y, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1)));
    public static Materials Zinc = new Materials(36, TextureSet.SET_METALLIC, 		1.0F, 0, 1, 1|2|8|32, 250, 240, 240, 0, "Zinc", "Zinc", 0, 0, 692, 0, false, false, 2, 1, 1, Dyes.dyeWhite, Element.Zn, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.SANO, 1)));

    /**
     * The "Random Material" ones.
     */
    public static Materials Organic = new Materials(-1, TextureSet.SET_LEAF, 	1.0F, 0, 1, false, "Organic", "Organic");
    public static Materials AnyCopper = new Materials(-1, TextureSet.SET_SHINY, 1.0F, 0, 3, false, "AnyCopper", "AnyCopper");
    public static Materials AnyBronze = new Materials(-1, TextureSet.SET_SHINY, 1.0F, 0, 3, false, "AnyBronze", "AnyBronze");
    public static Materials AnyIron = new Materials(-1, TextureSet.SET_SHINY, 	1.0F, 0, 3, false, "AnyIron", "AnyIron");
    public static Materials Crystal = new Materials(-1, TextureSet.SET_SHINY, 	1.0F, 0, 3, false, "Crystal", "Crystal");
    public static Materials Quartz = new Materials(-1, TextureSet.SET_QUARTZ, 	1.0F, 0, 2, false, "Quartz", "Quartz");
    public static Materials Metal = new Materials(-1, TextureSet.SET_METALLIC, 	1.0F, 0, 2, false, "Metal", "Metal");
    public static Materials Unknown = new Materials(-1, TextureSet.SET_DULL, 	1.0F, 0, 2, false, "Unknown", "Unknown");
    public static Materials Cobblestone = new Materials(-1, TextureSet.SET_DULL,1.0F, 0, 1, false, "Cobblestone", "Cobblestone");
    public static Materials Brick = new Materials(-1, TextureSet.SET_DULL, 		1.0F, 0, 1, false, "Brick", "Brick");
    public static Materials BrickNether = new Materials(-1, TextureSet.SET_DULL,1.0F, 0, 1, false, "BrickNether", "BrickNether");

    /**
     * The "I don't care" Section, everything I don't want to do anything with right now, is right here. Just to make the Material Finder shut up about them.
     * But I do see potential uses in some of these Materials.
     */
    public static Materials TarPitch = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1|2, 255, 255, 255, 0, "TarPitch", "Tar Pitch", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Serpentine = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Serpentine", "Serpentine", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Flux = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 2, 1, 255, 255, 255, 0, "Flux", "Flux", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials RedstoneAlloy = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1, 255, 255, 255, 0, "RedstoneAlloy", "Redstone Alloy", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials OsmiumTetroxide = new Materials(-1, TextureSet.SET_NONE,1.0F, 0, 2, 1, 255, 255, 255, 0, "OsmiumTetroxide", "Osmium Tetroxide", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials NitricAcid = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 0, 255, 255, 255, 0, "NitricAcid", "Nitric Acid", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials RubberTreeSap = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 0, 255, 255, 255, 0, "RubberTreeSap", "Rubber Tree Sap", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials AquaRegia = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 0, 255, 255, 255, 0, "AquaRegia", "Aqua Regia", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials SolutionBlueVitriol  = new Materials(-1, TextureSet.SET_NONE, 1.0F, 0, 2, 0, 255, 255, 255, 0, "SolutionBlueVitriol", "Blue Vitriol Solution", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials SolutionNickelSulfate  = new Materials(-1, TextureSet.SET_NONE, 1.0F, 0, 2, 0, 255, 255, 255, 0, "SolutionNickelSulfate", "Nickel Sulfate Solution", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Signalum = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Signalum", "Signalum", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Lumium = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Lumium", "Lumium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials PhasedIron = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|2, 255, 255, 255, 0, "PhasedIron", "Phased Iron", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials PhasedGold = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|2, 255, 255, 255, 0, "PhasedGold", "Phased Gold", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Soularium = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Soularium", "Soularium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Endium = new Materials(770, TextureSet.SET_DULL, 		1.0F, 0, 2, 1|2, 165, 220, 250, 0, "Endium", "Endium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow);
    public static Materials Prismarine = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|4, 255, 255, 255, 0, "Prismarine", "Prismarine", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials GraveyardDirt = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1, 255, 255, 255, 0, "GraveyardDirt", "Graveyard Dirt", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials DarkSteel = new Materials(364, TextureSet.SET_DULL, 	8.0F, 512, 3, 1|2|8 |64, 80, 70, 80, 0, "DarkSteel", "Dark Steel", 0, 0, 1811, 0, false, false, 5, 1, 1, Dyes.dyePurple);
    public static Materials Terrasteel = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Terrasteel", "Terrasteel", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials ConductiveIron = new Materials(-1, TextureSet.SET_NONE, 1.0F, 0, 2, 1|2, 255, 255, 255, 0, "ConductiveIron", "Conductive Iron", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials ElectricalSteel = new Materials(-1, TextureSet.SET_NONE,1.0F, 0, 2, 1|2, 255, 255, 255, 0, "ElectricalSteel", "Electrical Steel", 0, 0, 1811, 1000, true, false, 3, 1, 1, Dyes._NULL);
    public static Materials EnergeticAlloy = new Materials(-1, TextureSet.SET_NONE, 1.0F, 0, 2, 1|2, 255, 255, 255, 0, "EnergeticAlloy", "Energetic Alloy", 0, 0, 1950, 1950, true, false, 3, 1, 1, Dyes._NULL);
    public static Materials VibrantAlloy = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|2, 255, 255, 255, 0, "VibrantAlloy", "Vibrant Alloy", 0, 0, 3300, 3300, true, false, 3, 1, 1, Dyes._NULL);
    public static Materials PulsatingIron = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|2, 255, 255, 255, 0, "PulsatingIron", "Pulsating Iron", 0, 0, 1600, 1600, true, false, 3, 1, 1, Dyes._NULL);
    public static Materials Teslatite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 60, 180, 200, 0, "Teslatite", "Teslatite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Fluix = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 2, 1|4, 255, 255, 255, 0, "Fluix", "Fluix", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Manasteel = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Manasteel", "Manasteel", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Tennantite = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1, 255, 255, 255, 0, "Tennantite", "Tennantite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials DarkThaumium = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|2, 255, 255, 255, 0, "DarkThaumium", "Dark Thaumium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Alfium = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Alfium", "Alfium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Ryu = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 2, 1, 255, 255, 255, 0, "Ryu", "Ryu", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Mutation = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Mutation", "Mutation", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Aquamarine = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|4, 255, 255, 255, 0, "Aquamarine", "Aquamarine", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Ender = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 2, 1, 255, 255, 255, 0, "Ender", "Ender", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials ElvenElementium = new Materials(-1, TextureSet.SET_NONE,1.0F, 0, 2, 1|2, 255, 255, 255, 0, "ElvenElementium", "Elven Elementium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials EnrichedCopper = new Materials(-1, TextureSet.SET_NONE, 1.0F, 0, 2, 1|2, 255, 255, 255, 0, "EnrichedCopper", "Enriched Copper", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials DiamondCopper = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|2, 255, 255, 255, 0, "DiamondCopper", "Diamond Copper", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials SodiumPeroxide = new Materials(-1, TextureSet.SET_NONE, 1.0F, 0, 2, 1, 255, 255, 255, 0, "SodiumPeroxide", "Sodium Peroxide", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials IridiumSodiumOxide = new Materials(-1, TextureSet.SET_NONE,1.0F, 0, 2, 1, 255, 255, 255, 0, "IridiumSodiumOxide", "Iridium Sodium Oxide", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials PlatinumGroupSludge = new Materials(241, TextureSet.SET_POWDER, 1.0F, 0, 2, 1, 0, 30, 0, 0, "PlatinumGroupSludge", "Platinum Group Sludge", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Fairy = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Fairy", "Fairy", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Ludicrite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Ludicrite", "Ludicrite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Pokefennium = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Pokefennium", "Pokefennium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Draconium = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Draconium", "Draconium" , 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials DraconiumAwakened = new Materials(-1, TextureSet.SET_NONE,1.0F, 0, 2, 1|2, 255, 255, 255, 0, "DraconiumAwakened", "Awakened Draconium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials PurpleAlloy = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 0 , 100, 180, 255, 0, "PurpleAlloy", "Purple Alloy", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials InfusedTeslatite = new Materials(-1, TextureSet.SET_NONE,1.0F, 0, 2, 0 , 100, 180, 255, 0, "InfusedTeslatite", "Infused Teslatite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);

    /**
     * Unknown Material Components. Dead End Section.
     */
    public static Materials Adamantium = new Materials(319, TextureSet.SET_SHINY, 	10.0F, 5120, 5, 1|2|64|128, 255, 255, 255, 0, "Adamantium", "Adamantium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray);
    public static Materials Adamite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 3, 1, 255, 255, 255, 0, "Adamite", "Adamite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray);
    public static Materials Adluorite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1 | 8, 255, 255, 255, 0, "Adluorite", "Adluorite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Agate = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 2, 1, 255, 255, 255, 0, "Agate", "Agate", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Alduorite = new Materials(485, TextureSet.SET_SHINY, 	1.0F, 0, 2, 1|16, 159, 180, 180, 0, "Alduorite", "Alduorite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Amber = new Materials(514, TextureSet.SET_RUBY, 		4.0F, 128, 2, 1|4|8 |64, 255, 128, 0, 127, "Amber", "Amber", 5, 3, -1, 0, false, true, 1, 1, 1, Dyes.dyeOrange, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VINCULUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1)));
    public static Materials Ammonium = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Ammonium", "Ammonium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Amordrine = new Materials(-1, TextureSet.SET_NONE, 		6.0F, 64, 2, 1 | 2 | 8 | 16 | 64, 255, 255, 255, 0, "Amordrine", "Amordrine", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Andesite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Andesite", "Andesite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Angmallen = new Materials(958, TextureSet.SET_METALLIC, 10.0F, 128, 2, 1 | 2 | 8 | 16 | 64, 215, 225, 138, 0, "Angmallen", "Angmallen", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Ardite = new Materials(-1, TextureSet.SET_NONE, 		6.0F, 64, 2, 1|2|8 |64, 255, 0, 0, 0, "Ardite", "Ardite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials Aredrite = new Materials(-1, TextureSet.SET_NONE, 		6.0F, 64, 2, 1|2|64, 255, 0, 0, 0, "Aredrite", "Aredrite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials Atlarus = new Materials(965, TextureSet.SET_METALLIC, 	6.0F, 64, 2, 1 | 2 | 8 | 64, 255, 255, 255, 0, "Atlarus", "Atlarus", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Bitumen = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1 |8 , 255, 255, 255, 0, "Bitumen", "Bitumen", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Black = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 2, 0, 0, 0, 0, 0, "Black", "Black", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlack);
    public static Materials Blizz = new Materials(851, TextureSet.SET_SHINY, 		1.0F, 0, 2, 1, 220, 233, 255, 0, "Blizz", "Blizz", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Blueschist = new Materials(852, TextureSet.SET_DULL, 	1.0F, 0, 2, 1, 255, 255, 255, 0, "Blueschist", "Blueschist", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeLightBlue);
    public static Materials Bluestone = new Materials(813, TextureSet.SET_DULL, 	1.0F, 0, 2, 1, 255, 255, 255, 0, "Bluestone", "Bluestone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue);
    public static Materials Bloodstone = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1, 255, 255, 255, 0, "Bloodstone", "Bloodstone", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeRed);
    public static Materials Blutonium = new Materials(-1, TextureSet.SET_SHINY, 	1.0F, 0, 2, 1|2|8, 0, 0, 255, 0, "Blutonium", "Blutonium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlue);
    public static Materials Carmot = new Materials(962, TextureSet.SET_METALLIC, 	16.0F, 128, 1, 1 | 2 | 8 | 64, 217, 205, 140, 0, "Carmot", "Carmot", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Celenegil = new Materials(964, TextureSet.SET_METALLIC, 10.0F, 4096, 2, 1 | 2 | 8 | 16 | 64, 148, 204, 72, 0, "Celenegil", "Celenegil", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials CertusQuartz = new Materials(516, TextureSet.SET_QUARTZ,5.0F, 32, 1, 1|4|8 |64, 210, 210, 230, 0, "CertusQuartz", "Certus Quartz", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VITREUS, 1)));
    public static Materials Ceruclase = new Materials(952, TextureSet.SET_METALLIC, 6.0F, 1280, 2, 1 | 2 | 8, 140, 189, 208, 0, "Ceruclase", "Ceruclase", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Citrine = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Citrine", "Citrine", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials CobaltHexahydrate = new Materials(853, TextureSet.SET_METALLIC, 1.0F, 0, 2, 1 |16, 80, 80, 250, 0, "CobaltHexahydrate", "Cobalt Hexahydrate", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue);
    public static Materials ConstructionFoam = new Materials(854, TextureSet.SET_DULL, 1.0F, 0, 2, 1 |16, 128, 128, 128, 0, "ConstructionFoam", "Construction Foam", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray);
    public static Materials Chert = new Materials(857, TextureSet.SET_DULL, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Chert", "Chert", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes._NULL);
    public static Materials Chimerite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Chimerite", "Chimerite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Coral = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 1, 1, 255, 128, 255, 0, "Coral", "Coral", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials CrudeOil = new Materials(858, TextureSet.SET_DULL, 		1.0F, 0, 2, 1, 10, 10, 10, 0, "CrudeOil", "Crude Oil", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Materials Chrysocolla = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 2, 1, 255, 255, 255, 0, "Chrysocolla", "Chrysocolla", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials CrystalFlux = new Materials(-1, TextureSet.SET_QUARTZ, 	1.0F, 0, 3, 1|4, 100, 50, 100, 0, "CrystalFlux", "Flux Crystal", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Cyanite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Cyanite", "Cyanite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeCyan);
    public static Materials Dacite = new Materials(859, TextureSet.SET_DULL, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Dacite", "Dacite", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeLightGray);
    public static Materials DarkIron = new Materials(342, TextureSet.SET_DULL, 		7.0F, 384, 3, 1|2|64, 55, 40, 60, 0, "DarkIron", "Dark Iron", 0, 0, -1, 0, false, false, 5, 1, 1, Dyes.dyePurple);
    public static Materials DarkStone = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "DarkStone", "Dark Stone", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlack);
    public static Materials Demonite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Demonite", "Demonite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeRed);
    public static Materials Desh = new Materials(884, TextureSet.SET_DULL, 			1.0F, 1280, 3, 1|2|8 |64|128, 40, 40, 40, 0, "Desh", "Desh", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Materials Desichalkos = new Materials(-1, TextureSet.SET_NONE, 	6.0F, 1280, 3, 1 | 2 | 8 | 16 | 64, 255, 255, 255, 0, "Desichalkos", "Desichalkos", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Dilithium = new Materials(515, TextureSet.SET_DIAMOND, 	1.0F, 0, 1, 1|4|16, 255, 250, 250, 127, "Dilithium", "Dilithium", 0, 0, -1, 0, false, true, 1, 1, 1, Dyes.dyeWhite);
    public static Materials Draconic = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Draconic", "Draconic", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed);
    public static Materials Drulloy = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1|16 , 255, 255, 255, 0, "Drulloy", "Drulloy", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed);
    public static Materials Duranium = new Materials(328, TextureSet.SET_METALLIC, 16.0F, 5120, 5, 1|2|64, 255, 255, 255, 0, "Duranium", "Duranium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray);
    public static Materials Eclogite = new Materials(860, TextureSet.SET_DULL, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Eclogite", "Eclogite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials ElectrumFlux = new Materials(320, TextureSet.SET_SHINY,16.0F, 512, 3, 1|2|64, 255, 255, 120, 0, "ElectrumFlux", "Fluxed Electrum", 0, 0, 3000, 3000, true, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials Emery = new Materials(861, TextureSet.SET_DULL, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Emery", "Emery", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Enderium = new Materials(321, TextureSet.SET_DULL, 		8.0F, 256, 3, 1|2|64, 89, 145, 135, 0, "Enderium", "Enderium", 0, 0, 3000, 3000, true, false, 1, 1, 1, Dyes.dyeGreen, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ALIENIS, 1)));
    public static Materials EnderiumBase = new Materials(-1, TextureSet.SET_DULL, 	8.0F, 256, 3, 1|2|64, 89, 145, 135, 0, "EnderiumBase", "Enderium Base", 0, 0, 3000, 3000, true, false, 1, 1, 1, Dyes.dyeGreen, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ALIENIS, 1)));
    public static Materials Energized = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 0, 255, 255, 255, 0, "Energized", "Energized", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Epidote = new Materials(862, TextureSet.SET_DULL, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Epidote", "Epidote", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes._NULL);
    public static Materials Eximite = new Materials(959, TextureSet.SET_METALLIC, 	5.0F, 2560, 3, 1 | 2 | 8 | 64, 124, 90, 150, 0, "Eximite", "Eximite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials FierySteel = new Materials(346, TextureSet.SET_FIERY, 	8.0F, 256, 3, 1|2 |16|64|128, 64, 0, 0, 0, "FierySteel", "Fiery Steel", 5, 2048, 1811, 1000, true, false, 1, 1, 1, Dyes.dyeRed, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 3), new TC_AspectStack(TC_Aspects.CORPUS, 3)));
    public static Materials Firestone = new Materials(347, TextureSet.SET_QUARTZ, 	6.0F, 1280, 3, 1|4|8 |64, 200, 20, 0, 0, "Firestone", "Firestone", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeRed);
    public static Materials Fluorite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Fluorite", "Fluorite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGreen);
    public static Materials FoolsRuby = new Materials(512, TextureSet.SET_RUBY, 	1.0F, 0, 2, 1|4|8, 255, 100, 100, 127, "FoolsRuby", "Ruby", 0, 0, -1, 0, false, true, 3, 1, 1, Dyes.dyeRed, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 2)));
    public static Materials Force = new Materials(521, TextureSet.SET_DIAMOND, 	   10.0F, 128, 3, 1|2|4|8 |64|128, 255, 255, 0, 0, "Force", "Force", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 5)));
    public static Materials Forcicium = new Materials(518, TextureSet.SET_DIAMOND, 	1.0F, 0, 1, 1|4|16, 50, 50, 70, 0, "Forcicium", "Forcicium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGreen, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2)));
    public static Materials Forcillium = new Materials(519, TextureSet.SET_DIAMOND, 1.0F, 0, 1, 1|4|16, 50, 50, 70, 0, "Forcillium", "Forcillium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGreen, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2)));
    public static Materials Gabbro = new Materials(863, TextureSet.SET_DULL, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Gabbro", "Gabbro", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes._NULL);
    public static Materials Glowstone = new Materials(811, TextureSet.SET_SHINY, 	1.0F, 0, 1, 1 |16, 255, 255, 0, 0, "Glowstone", "Glowstone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 2), new TC_AspectStack(TC_Aspects.SENSUS, 1)));
    public static Materials Gneiss = new Materials(864, TextureSet.SET_DULL, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Gneiss", "Gneiss", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes._NULL);
    public static Materials Graphite = new Materials(865, TextureSet.SET_DULL, 		5.0F, 32, 2, 1 |8|16|64, 128, 128, 128, 0, "Graphite", "Graphite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1)));
    public static Materials Graphene = new Materials(819, TextureSet.SET_DULL, 		6.0F, 32, 1, 1|64, 128, 128, 128, 0, "Graphene", "Graphene", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1)));
    public static Materials Greenschist = new Materials(866, TextureSet.SET_DULL, 	1.0F, 0, 1, 1, 255, 255, 255, 0, "Greenschist", "Green Schist", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGreen);
    public static Materials Greenstone = new Materials(867, TextureSet.SET_DULL, 	1.0F, 0, 1, 1, 255, 255, 255, 0, "Greenstone", "Greenstone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGreen);
    public static Materials Greywacke = new Materials(868, TextureSet.SET_DULL, 	1.0F, 0, 1, 1, 255, 255, 255, 0, "Greywacke", "Greywacke", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray);
    public static Materials Haderoth = new Materials(963, TextureSet.SET_METALLIC, 10.0F, 3200, 3, 1 | 2 | 8 | 16 | 64, 119, 52, 30, 0, "Haderoth", "Haderoth", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Hematite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Hematite", "Hematite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Hepatizon = new Materials(957, TextureSet.SET_METALLIC,12.0F, 128, 2, 1 | 2 | 8 | 16 | 64, 117, 94, 117, 0, "Hepatizon", "Hepatizon", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials HSLA = new Materials(322, TextureSet.SET_METALLIC, 		6.0F, 500, 2, 1|2|64|128, 128, 128, 128, 0, "HSLA", "HSLA Steel", 0, 0, 1811, 1000, true, false, 3, 1, 1, Dyes._NULL, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.ORDO, 1)));
    public static Materials Ignatius = new Materials(950, TextureSet.SET_METALLIC, 12.0F, 512, 2, 1 | 2 | 16, 255, 169, 83, 0, "Ignatius", "Ignatius", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Infernal = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 0, 255, 255, 255, 0, "Infernal", "Infernal", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Infuscolium =new Materials(490, TextureSet.SET_METALLIC,6.0F, 64, 2, 1 | 2 | 8 | 16 | 64, 146, 33, 86, 0, "Infuscolium", "Infuscolium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials InfusedGold = new Materials(323, TextureSet.SET_SHINY, 12.0F, 64, 3, 1|2|8 |64|128, 255, 200, 60, 0, "InfusedGold", "Infused Gold", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow);
    public static Materials InfusedAir = new Materials(540, TextureSet.SET_SHARDS, 	8.0F, 64, 3, 1|4|8 |64|128, 255, 255, 0, 0, "InfusedAir", "Aer", 5, 160, -1, 0, false, true, 3, 1, 1, Dyes.dyeYellow, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AER, 2)));
    public static Materials InfusedFire = new Materials(541, TextureSet.SET_SHARDS, 8.0F, 64, 3, 1|4|8 |64|128, 255, 0, 0, 0, "InfusedFire", "Ignis", 5, 320, -1, 0, false, true, 3, 1, 1, Dyes.dyeRed, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.IGNIS, 2)));
    public static Materials InfusedEarth = new Materials(542, TextureSet.SET_SHARDS,8.0F, 256, 3, 1|4|8 |64|128, 0, 255, 0, 0, "InfusedEarth", "Terra", 5, 160, -1, 0, false, true, 3, 1, 1, Dyes.dyeGreen, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.TERRA, 2)));
    public static Materials InfusedWater = new Materials(543, TextureSet.SET_SHARDS,8.0F, 64, 3, 1|4|8 |64|128, 0, 0, 255, 0, "InfusedWater", "Aqua", 5, 160, -1, 0, false, true, 3, 1, 1, Dyes.dyeBlue, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AQUA, 2)));
    public static Materials InfusedEntropy=new Materials(544,TextureSet.SET_SHARDS,32.0F, 64, 4, 1|4|8 |64|128, 62, 62, 62, 0, "InfusedEntropy", "Perditio", 5, 320, -1, 0, false, true, 3, 1, 1, Dyes.dyeBlack, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.PERDITIO, 2)));
    public static Materials InfusedOrder = new Materials(545, TextureSet.SET_SHARDS,8.0F, 64, 3, 1|4|8 |64|128, 252, 252, 252, 0, "InfusedOrder", "Ordo", 5, 240, -1, 0, false, true, 3, 1, 1, Dyes.dyeWhite, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.ORDO, 2)));
    public static Materials InfusedVis = new Materials(-1, TextureSet.SET_SHARDS, 	8.0F, 64, 3, 1|4|8 |64|128, 255, 0, 255, 0, "InfusedVis", "Auram", 5, 240, -1, 0, false, true, 3, 1, 1, Dyes.dyePurple, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AURAM, 2)));
    public static Materials InfusedDull = new Materials(-1, TextureSet.SET_SHARDS, 32.0F, 64, 3, 1|4|8 |64|128, 100, 100, 100, 0, "InfusedDull", "Vacuus", 5, 160, -1, 0, false, true, 3, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.VACUOS, 2)));
    public static Materials Inolashite = new Materials(954, TextureSet.SET_NONE, 	8.0F, 2304, 3, 1 | 2 | 8 | 16 | 64, 148, 216, 187, 0, "Inolashite", "Inolashite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Invisium = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1, 255, 255, 255, 0, "Invisium", "Invisium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Jade = new Materials(537, TextureSet.SET_SHINY, 		1.0F, 0, 2, 1, 0, 100, 0, 0, "Jade", "Jade", 0, 0, -1, 0, false, false, 5, 1, 1, Dyes.dyeGreen, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 3)));
    public static Materials Jasper = new Materials(511, TextureSet.SET_EMERALD, 	1.0F, 0, 2, 1|4, 200, 80, 80, 100, "Jasper", "Jasper", 0, 0, -1, 0, false, true, 3, 1, 1, Dyes.dyeRed, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 2)));
    public static Materials Kalendrite = new Materials(953, TextureSet.SET_METALLIC,5.0F, 2560, 3, 1 | 2 | 16, 170, 91, 189, 0, "Kalendrite", "Kalendrite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Komatiite = new Materials(869, TextureSet.SET_DULL, 	1.0F, 0, 1, 1, 255, 255, 255, 0, "Komatiite", "Komatiite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials Lava = new Materials(700, TextureSet.SET_FLUID, 		1.0F, 0, 1, 16, 255, 64, 0, 0, "Lava", "Lava", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange);
    public static Materials Lemurite = new Materials(486, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1 | 16, 219, 219, 219, 0, "Lemurite", "Lemurite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Limestone = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Limestone", "Limestone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Lodestone = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Lodestone", "Lodestone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Luminite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 250, 250, 250, 0, "Luminite", "Luminite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite);
    public static Materials Magma = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 1, 0, 255, 64, 0, 0, "Magma", "Magma", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange);
    public static Materials Mawsitsit = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Mawsitsit", "Mawsitsit", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Mercassium = new Materials(-1, TextureSet.SET_NONE, 	6.0F, 64, 1, 1|2|64, 255, 255, 255, 0, "Mercassium", "Mercassium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials MeteoricIron =new Materials(340,TextureSet.SET_METALLIC,6.0F, 384, 2, 1|2|8 |64, 100, 50, 80, 0, "MeteoricIron", "Meteoric Iron", 0, 0, 1811, 0, false, false, 1, 1, 1, Dyes.dyeGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)));
    public static Materials MeteoricSteel=new Materials(341,TextureSet.SET_METALLIC,6.0F, 768, 2, 1|2|64, 50, 25, 40, 0, "MeteoricSteel", "Meteoric Steel", 0, 0, 1811, 1000, true, false, 1, 1, 1, Dyes.dyeGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1), new TC_AspectStack(TC_Aspects.ORDO, 1)));
    public static Materials Meteorite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 80, 35, 60, 0, "Meteorite", "Meteorite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePurple);
    public static Materials Meutoite = new Materials(487, TextureSet.SET_METALLIC, 	1.0F, 0, 1, 1 | 8 | 16, 95, 82, 105, 0, "Meutoite", "Meutoite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Migmatite = new Materials(872, TextureSet.SET_DULL, 	1.0F, 0, 1, 1, 255, 255, 255, 0, "Migmatite", "Migmatite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Mimichite =new Materials(-1,TextureSet.SET_GEM_VERTICAL,1.0F, 0, 1, 1|4, 255, 255, 255, 0, "Mimichite", "Mimichite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Moonstone = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1 |8 , 255, 255, 255, 0, "Moonstone", "Moonstone", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.ALIENIS, 1)));
    public static Materials Naquadah = new Materials(324, TextureSet.SET_METALLIC, 	6.0F, 1280, 4, 1|2|8|16|64, 50, 50, 50, 0, "Naquadah", "Naquadah", 0, 0, 5400, 5400, true, false, 10, 1, 1, Dyes.dyeBlack, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.RADIO, 1), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)));
    public static Materials NaquadahAlloy=new Materials(325,TextureSet.SET_METALLIC,8.0F, 5120, 5, 1|2|64|128, 40, 40, 40, 0, "NaquadahAlloy", "Naquadah Alloy", 0, 0, 7200, 7200, true, false, 10, 1, 1, Dyes.dyeBlack, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.NEBRISUM, 1)));
    public static Materials NaquadahEnriched=new Materials(326,TextureSet.SET_METALLIC,6.0F, 1280, 4, 1|2|8|16|64, 50, 50, 50, 0, "NaquadahEnriched", "Enriched Naquadah", 0, 0, 4500, 4500, true, false, 15, 1, 1, Dyes.dyeBlack, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.RADIO, 2), new TC_AspectStack(TC_Aspects.NEBRISUM, 2)));
    public static Materials Naquadria = new Materials(327, TextureSet.SET_SHINY, 	1.0F, 512, 4, 1|2|8|16|64, 30, 30, 30, 0, "Naquadria", "Naquadria", 0, 0, 9000, 9000, true, false, 20, 1, 1, Dyes.dyeBlack, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.RADIO, 3), new TC_AspectStack(TC_Aspects.NEBRISUM, 3)));
    public static Materials Nether = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 0, 255, 255, 255, 0, "Nether", "Nether", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials NetherBrick = new Materials(814, TextureSet.SET_DULL, 	1.0F, 0, 1, 1, 100, 0, 0, 0, "NetherBrick", "Nether Brick", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1)));
    public static Materials NetherQuartz = new Materials(522, TextureSet.SET_QUARTZ,1.0F, 32, 1, 1|4|8 |64, 230, 210, 210, 0, "NetherQuartz", "Nether Quartz", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeWhite, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VITREUS, 1)));
    public static Materials NetherStar =new Materials(506,TextureSet.SET_NETHERSTAR,1.0F, 5120, 4, 1|4|64, 255, 255, 255, 0, "NetherStar", "Nether Star", 5, 50000, -1, 0, false, false, 15, 1, 1, Dyes.dyeWhite);
    public static Materials Nikolite = new Materials(812, TextureSet.SET_SHINY, 	1.0F, 0, 1, 1, 60, 180, 200, 0, "Nikolite", "Nikolite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeCyan, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2)));
    public static Materials ObsidianFlux = new Materials(-1, TextureSet.SET_DULL, 	1.0F, 0, 1, 1|2, 80, 50, 100, 0, "ObsidianFlux", "Fluxed Obsidian", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePurple);
    public static Materials Oilsands = new Materials(878, TextureSet.SET_NONE, 		1.0F, 0, 1, 1|8 , 10, 10, 10, 0, "Oilsands", "Oilsands", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Onyx = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 1, 1, 255, 255, 255, 0, "Onyx", "Onyx", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Orichalcum = new Materials(966, TextureSet.SET_METALLIC,4.5F, 3456, 3, 1 | 2 | 8 | 64, 84, 122, 56, 0, "Orichalcum", "Orichalcum", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Osmonium = new Materials(-1, TextureSet.SET_NONE, 		6.0F, 64, 1, 1|2|64, 255, 255, 255, 0, "Osmonium", "Osmonium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlue);
    public static Materials Oureclase = new Materials(961, TextureSet.SET_METALLIC, 6.0F, 1920, 3, 1 | 2 | 8 | 64, 183, 98, 21, 0, "Oureclase", "Oureclase", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Painite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 0, 255, 255, 255, 0, "Painite", "Painite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Peanutwood = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 1, 0, 255, 255, 255, 0, "Peanutwood", "Peanut Wood", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Petroleum = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Petroleum", "Petroleum", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Pewter = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 0, 255, 255, 255, 0, "Pewter", "Pewter", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Phoenixite = new Materials(-1, TextureSet.SET_NONE, 	6.0F, 64, 1, 1|2|64, 255, 255, 255, 0, "Phoenixite", "Phoenixite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Potash = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 0, 255, 255, 255, 0, "Potash", "Potash", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Prometheum = new Materials(960, TextureSet.SET_METALLIC,8.0F, 512, 1, 1 | 2 | 8 | 64, 90, 129, 86, 0, "Prometheum", "Prometheum", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Quartzite = new Materials(523, TextureSet.SET_QUARTZ, 	1.0F, 0, 1, 1|4|8 , 210, 230, 210, 0, "Quartzite", "Quartzite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite);
    public static Materials Quicklime = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Quicklime", "Quicklime", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Randomite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Randomite", "Randomite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Rhyolite = new Materials(875, TextureSet.SET_DULL, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Rhyolite", "Rhyolite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Rubracium = new Materials(488, TextureSet.SET_METALLIC, 1.0F, 0, 1, 1 | 8 | 16, 151, 45, 45, 0, "Rubracium", "Rubracium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Sand = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 1, 0, 255, 255, 255, 0, "Sand", "Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials Sanguinite = new Materials(955, TextureSet.SET_METALLIC,3.0F, 4480, 4, 1 | 2 | 8, 185, 0, 0, 0, "Sanguinite", "Sanguinite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Siltstone = new Materials(876, TextureSet.SET_DULL, 	1.0F, 0, 1, 1, 255, 255, 255, 0, "Siltstone", "Siltstone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Spinel = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 0, 255, 255, 255, 0, "Spinel", "Spinel", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Starconium = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 1, 1|2, 255, 255, 255, 0, "Starconium", "Starconium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Sugilite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Sugilite", "Sugilite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Sunstone = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1 |8 , 255, 255, 255, 0, "Sunstone", "Sunstone", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.ALIENIS, 1)));
    public static Materials Tar = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 1, 0, 10, 10, 10, 0, "Tar", "Tar", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Materials Tartarite = new Materials(956, TextureSet.SET_METALLIC,20.0F, 7680, 5, 1 | 2 | 8 | 16, 255, 118, 60, 0, "Tartarite", "Tartarite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Tapazite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Tapazite", "Tapazite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGreen);
    public static Materials Thyrium = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1|2, 255, 255, 255, 0, "Thyrium", "Thyrium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Tourmaline = new Materials(-1, TextureSet.SET_RUBY, 	1.0F, 0, 1, 1, 255, 255, 255, 0, "Tourmaline", "Tourmaline", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials Tritanium = new Materials(329, TextureSet.SET_METALLIC,20.0F, 10240, 6, 1|2|64, 255, 255, 255, 0, "Tritanium", "Tritanium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 2)));
    public static Materials Turquoise = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 1, 255, 255, 255, 0, "Turquoise", "Turquoise", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL);
    public static Materials UUAmplifier = new Materials(721, TextureSet.SET_FLUID, 	1.0F, 0, 1, 16, 96, 0, 128, 0, "UUAmplifier", "UU-Amplifier", 0, 0, -1, 0, false, false, 10, 1, 1, Dyes.dyePink);
    public static Materials UUMatter = new Materials(703, TextureSet.SET_FLUID, 	1.0F, 0, 1, 16, 128, 0, 196, 0, "UUMatter", "UU-Matter", 0, 0, -1, 0, false, false, 10, 1, 1, Dyes.dyePink);
    public static Materials Void = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 1, 0, 255, 255, 255, 200, "Void", "Void", 0, 0, -1, 0, false, true, 1, 1, 1, Dyes._NULL, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1)));
    public static Materials Voidstone = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 1, 0, 255, 255, 255, 200, "Voidstone", "Voidstone", 0, 0, -1, 0, false, true, 1, 1, 1, Dyes._NULL, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.VACUOS, 1)));
    public static Materials Vulcanite = new Materials(489, TextureSet.SET_METALLIC, 6.0F, 64, 2, 1 | 2 | 8 | 16 | 64, 255, 132, 72, 0, "Vulcanite", "Vulcanite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Vyroxeres = new Materials(951, TextureSet.SET_METALLIC, 9.0F, 768, 3, 1 | 2 | 8 | 64, 85, 224, 1, 0, "Vyroxeres", "Vyroxeres", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL);
    public static Materials Wimalite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 8 , 255, 255, 255, 0, "Wimalite", "Wimalite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow);
    public static Materials Yellorite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 8 , 255, 255, 255, 0, "Yellorite", "Yellorite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow);
    public static Materials Yellorium = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Yellorium", "Yellorium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow);
    public static Materials Zectium = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 2, 1|2, 255, 255, 255, 0, "Zectium", "Zectium", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlack);

    /**
     * Circuitry, Batteries and other Technical things
     */
    public static Materials Primitive = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Primitive", "Primitive", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1)));
    public static Materials Basic = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "Basic", "Basic", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2)));
    public static Materials Good = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "Good", "Good", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 3)));
    public static Materials Advanced = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Advanced", "Advanced", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4)));
    public static Materials Data = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "Data", "Data", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 5)));
    public static Materials Elite = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "Elite", "Elite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 6)));
    public static Materials Master = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Master", "Master", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 7)));
    public static Materials Ultimate = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Ultimate", "Ultimate", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8)));
    public static Materials Superconductor = new Materials(-1, TextureSet.SET_NONE, 1.0F, 0, 0, 0, 255, 255, 255, 0, "Superconductor", "Superconductor", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8)));
    public static Materials Infinite = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Infinite", "Infinite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray);

    /**
     * Not possible to determine exact Components
     */
    public static Materials Antimatter = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 0, 0, 255, 255, 255, 0, "Antimatter", "Antimatter", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 9), new TC_AspectStack(TC_Aspects.PERFODIO, 8)));
    public static Materials BioFuel = new Materials(705, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 255, 128, 0, 0, "BioFuel", "Biofuel", 0, 6, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange);
    public static Materials Biomass = new Materials(704, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 0, 255, 0, 0, "Biomass", "Biomass", 3, 8, -1, 0, false, false, 1, 1, 1, Dyes.dyeGreen);
    public static Materials Cheese = new Materials(894, TextureSet.SET_FINE, 		1.0F, 0, 0, 1, 255, 255, 0, 0, "Cheese", "Cheese", 0, 0, 320, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials Chili = new Materials(895, TextureSet.SET_FINE, 		1.0F, 0, 0, 1, 200, 0, 0, 0, "Chili", "Chili", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed);
    public static Materials Chocolate = new Materials(886, TextureSet.SET_FINE, 	1.0F, 0, 0, 1, 190, 95, 0, 0, "Chocolate", "Chocolate", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown);
    public static Materials Cluster = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 127, "Cluster", "Cluster", 0, 0, -1, 0, false, true, 1, 1, 1, Dyes.dyeWhite);
    public static Materials CoalFuel = new Materials(710, TextureSet.SET_FLUID, 	1.0F, 0, 0, 16, 50, 50, 70, 0, "CoalFuel", "Coalfuel", 0, 16, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Materials Cocoa = new Materials(887, TextureSet.SET_FINE, 		1.0F, 0, 0, 1, 190, 95, 0, 0, "Cocoa", "Cocoa", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown);
    public static Materials Coffee = new Materials(888, TextureSet.SET_FINE, 		1.0F, 0, 0, 1, 150, 75, 0, 0, "Coffee", "Coffee", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown);
    public static Materials Creosote = new Materials(712, TextureSet.SET_FLUID, 	1.0F, 0, 0, 16, 128, 64, 0, 0, "Creosote", "Creosote", 3, 8, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown);
    public static Materials Ethanol = new Materials(706, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 255, 128, 0, 0, "Ethanol", "Ethanol", 0, 128, -1, 0, false, false, 1, 1, 1, Dyes.dyePurple);
    public static Materials FishOil = new Materials(711, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 255, 196, 0, 0, "FishOil", "Fish Oil", 3, 2, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 2)));
    public static Materials Fuel = new Materials(708, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 255, 255, 0, 0, "Fuel", "Diesel", 0, 128, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials Glue = new Materials(726, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 200, 196, 0, 0, "Glue", "Glue", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.LIMUS, 2)));
    public static Materials Gunpowder = new Materials(800, TextureSet.SET_DULL, 	1.0F, 0, 0, 1, 128, 128, 128, 0, "Gunpowder", "Gunpowder", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 4)));
    public static Materials FryingOilHot = new Materials(727, TextureSet.SET_FLUID, 1.0F, 0, 0, 16, 200, 196, 0, 0, "FryingOilHot", "Hot Frying Oil", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.IGNIS, 1)));
    public static Materials Honey = new Materials(725, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 210, 200, 0, 0, "Honey", "Honey", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials Leather = new Materials(-1, TextureSet.SET_ROUGH, 		1.0F, 0, 0, 1, 150, 150, 80, 127, "Leather", "Leather", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange);
    public static Materials LimePure = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "LimePure", "Pure Lime", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLime);
    public static Materials Lubricant = new Materials(724, TextureSet.SET_FLUID, 	1.0F, 0, 0, 16, 255, 196, 0, 0, "Lubricant", "Lubricant", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)));
    public static Materials McGuffium239 = new Materials(999, TextureSet.SET_FLUID, 1.0F, 0, 0, 16, 200, 50, 150, 0, "McGuffium239", "Mc Guffium 239", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.ALIENIS, 8), new TC_AspectStack(TC_Aspects.PERMUTATIO, 8), new TC_AspectStack(TC_Aspects.SPIRITUS, 8), new TC_AspectStack(TC_Aspects.AURAM, 8), new TC_AspectStack(TC_Aspects.VITIUM, 8), new TC_AspectStack(TC_Aspects.RADIO, 8), new TC_AspectStack(TC_Aspects.MAGNETO, 8), new TC_AspectStack(TC_Aspects.ELECTRUM, 8), new TC_AspectStack(TC_Aspects.NEBRISUM, 8), new TC_AspectStack(TC_Aspects.STRONTIO, 8)));
    public static Materials MeatRaw = new Materials(892, TextureSet.SET_FINE, 		1.0F, 0, 0, 1, 255, 100, 100, 0, "MeatRaw", "Raw Meat", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink);
    public static Materials MeatCooked = new Materials(893, TextureSet.SET_FINE, 	1.0F, 0, 0, 1, 150, 60, 20, 0, "MeatCooked", "Cooked Meat", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink);
    public static Materials Milk = new Materials(885, TextureSet.SET_FINE, 			1.0F, 0, 0, 1 |16, 254, 254, 254, 0, "Milk", "Milk", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 2)));
    public static Materials Mud = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "Mud", "Mud", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown);
    public static Materials Oil = new Materials(707, TextureSet.SET_FLUID, 			1.0F, 0, 0, 16, 10, 10, 10, 0, "Oil", "Oil", 3, 16, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Materials Paper = new Materials(879, TextureSet.SET_PAPER, 		1.0F, 0, 0, 1, 250, 250, 250, 0, "Paper", "Paper", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1)));
    public static Materials Peat = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "Peat", "Peat", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2)));
    public static Materials Quantum = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 0, 0, 255, 255, 255, 0, "Quantum", "Quantum", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite);
    public static Materials RareEarth = new Materials(891, TextureSet.SET_FINE, 	1.0F, 0, 0, 1, 128, 128, 100, 0, "RareEarth", "Rare Earth", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.LUCRUM, 1)));
    public static Materials Red = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 0, 0, 255, 0, 0, 0, "Red", "Red", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed);
    public static Materials Reinforced = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 0, 0, 255, 255, 255, 0, "Reinforced", "Reinforced", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray);
    public static Materials SeedOil = new Materials(713, TextureSet.SET_FLUID,		1.0F, 0, 0, 16, 196, 255, 0, 0, "SeedOil", "Seed Oil", 3, 2, -1, 0, false, false, 1, 1, 1, Dyes.dyeLime, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.GRANUM, 2)));
    public static Materials SeedOilHemp = new Materials(722, TextureSet.SET_FLUID, 	1.0F, 0, 0, 16, 196, 255, 0, 0, "SeedOilHemp", "Hemp Seed Oil", 3, 2, -1, 0, false, false, 1, 1, 1, Dyes.dyeLime, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.GRANUM, 2)));
    public static Materials SeedOilLin = new Materials(723, TextureSet.SET_FLUID, 	1.0F, 0, 0, 16, 196, 255, 0, 0, "SeedOilLin", "Lin Seed Oil", 3, 2, -1, 0, false, false, 1, 1, 1, Dyes.dyeLime, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.GRANUM, 2)));
    public static Materials Stone = new Materials(299, TextureSet.SET_ROUGH, 		4.0F, 32, 1, 1|64|128, 205, 205, 205, 0, "Stone", "Stone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.TERRA, 1)));
    public static Materials TNT = new Materials(-1, TextureSet.SET_NONE, 			1.0F, 0, 0, 0, 255, 255, 255, 0, "TNT", "TNT", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 7), new TC_AspectStack(TC_Aspects.IGNIS, 4)));
    public static Materials Unstable = new Materials(-1, TextureSet.SET_NONE, 		1.0F, 0, 4, 0, 255, 255, 255, 127, "Unstable", "Unstable", 0, 0, -1, 0, false, true, 1, 1, 1, Dyes.dyeWhite, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 4)));
    public static Materials Unstableingot = new Materials(-1, TextureSet.SET_NONE, 	1.0F, 0, 4, 0, 255, 255, 255, 127, "Unstableingot", "Unstable", 0, 0, -1, 0, false, true, 1, 1, 1, Dyes.dyeWhite, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 4)));
    public static Materials Wheat = new Materials(881, TextureSet.SET_POWDER, 		1.0F, 0, 0, 1, 255, 255, 196, 0, "Wheat", "Wheat", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 2)));

    /**
     * TODO: This
     */
    public static Materials AluminiumBrass = new Materials(-1, TextureSet.SET_METALLIC, 6.0F, 64, 2, 1|2|64, 255, 255, 255, 0, "AluminiumBrass", "Aluminium Brass", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials Osmiridium = new Materials(317, TextureSet.SET_METALLIC, 	7.0F, 1600, 3, 1|2|64|128, 100, 100, 255, 0, "Osmiridium", "Osmiridium", 0, 0, 3333, 2500, true, false, 1, 1, 1, Dyes.dyeLightBlue, 1, Arrays.asList(new MaterialStack(Iridium, 3), new MaterialStack(Osmium, 1)));
    public static Materials Sunnarium = new Materials(318, TextureSet.SET_SHINY, 		1.0F, 0, 1, 1|2, 255, 255, 0, 0, "Sunnarium", "Sunnarium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials Endstone = new Materials(808, TextureSet.SET_DULL, 			1.0F, 0, 1, 1, 255, 255, 255, 0, "Endstone", "Endstone", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeYellow);
    public static Materials Netherrack = new Materials(807, TextureSet.SET_DULL, 		1.0F, 0, 0, 1, 200, 0, 0, 0, "Netherrack", "Netherrack", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeRed);
    public static Materials SoulSand = new Materials(-1, TextureSet.SET_DULL, 			1.0F, 0, 0, 1, 255, 255, 255, 0, "Soulsand", "Soulsand", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeBrown);

    /**
     * First Degree Compounds
     */
    public static Materials Methane = new Materials(715, TextureSet.SET_FLUID, 			1.0F, 0, 1, 16, 255, 255, 255, 0, "Methane", "Methane", 1, 45, -1, 0, false, false, 3, 1, 1, Dyes.dyeMagenta, 1, Arrays.asList(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 4)));
    public static Materials CarbonDioxide = new Materials(497, TextureSet.SET_FLUID, 	1.0F, 0, 2, 16|32, 169, 208, 245, 240, "CarbonDioxide", "Carbon Dioxide", 0, 0, 25, 1, false, true, 1, 1, 1, Dyes.dyeLightBlue, 1, Arrays.asList(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 2)));
    public static Materials NobleGases = new Materials(496, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 169, 208, 245, 240, "NobleGases", "Noble Gases", 0, 0, 4, 0, false, true, 1, 1, 1, Dyes.dyeLightBlue, 2, Arrays.asList(new MaterialStack(CarbonDioxide, 21), new MaterialStack(Helium, 9), new MaterialStack(Methane, 3), new MaterialStack(Deuterium, 1)));
    public static Materials Air = new Materials(-1, TextureSet.SET_FLUID, 				1.0F, 0, 2, 16|32, 169, 208, 245, 240, "Air", "Air", 0, 0, -1, 0, false, true, 1, 1, 1, Dyes.dyeLightBlue, 0, Arrays.asList(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1), new MaterialStack(NobleGases, 1)));
    public static Materials LiquidAir = new Materials(495, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16|32, 169, 208, 245, 240, "LiquidAir", "Liquid Air", 0, 0, 4, 0, false, true, 1, 1, 1, Dyes.dyeLightBlue, 2, Arrays.asList(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1), new MaterialStack(NobleGases, 1)));
    public static Materials Almandine = new Materials(820, TextureSet.SET_ROUGH, 		1.0F, 0, 1, 1 |8 , 255, 0, 0, 0, "Almandine", "Almandine", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeRed, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Iron, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)));
    public static Materials Andradite = new Materials(821, TextureSet.SET_ROUGH, 		1.0F, 0, 1, 1, 150, 120, 0, 0, "Andradite", "Andradite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow, 1, Arrays.asList(new MaterialStack(Calcium, 3), new MaterialStack(Iron, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)));
    public static Materials AnnealedCopper = new Materials(345, TextureSet.SET_SHINY, 	1.0F, 0, 2, 1|2|128, 255, 120, 20, 0, "AnnealedCopper", "Annealed Copper", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeOrange, 2, Arrays.asList(new MaterialStack(Copper, 1)));
    public static Materials Asbestos = new Materials(946, TextureSet.SET_DULL, 			1.0F, 0, 1, 1, 230, 230, 230, 0, "Asbestos", "Asbestos", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9))); // Mg3Si2O5(OH)4
    public static Materials Ash = new Materials(815, TextureSet.SET_DULL, 				1.0F, 0, 1, 1, 150, 150, 150, 0, "Ash", "Ashes", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, 2, Arrays.asList(new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.PERDITIO, 1)));
    public static Materials BandedIron = new Materials(917, TextureSet.SET_DULL, 		1.0F, 0, 2, 1 |8 , 145, 90, 90, 0, "BandedIron", "Banded Iron", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, 1, Arrays.asList(new MaterialStack(Iron, 2), new MaterialStack(Oxygen, 3)));
    public static Materials BatteryAlloy = new Materials(315, TextureSet.SET_DULL, 		1.0F, 0, 1, 1|2, 156, 124, 160, 0, "BatteryAlloy", "Battery Alloy", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePurple, 2, Arrays.asList(new MaterialStack(Lead, 4), new MaterialStack(Antimony, 1)));
    public static Materials BlueTopaz = new Materials(513,TextureSet.SET_GEM_HORIZONTAL,7.0F, 256, 3, 1|4|8 |64, 0, 0, 255, 127, "BlueTopaz", "Blue Topaz", 0, 0, -1, 0, false, true, 3, 1, 1, Dyes.dyeBlue, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)));
    public static Materials Bone = new Materials(806, TextureSet.SET_DULL, 				1.0F, 0, 1, 1, 250, 250, 250, 0, "Bone", "Bone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0, Arrays.asList(new MaterialStack(Calcium, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.MORTUUS, 2), new TC_AspectStack(TC_Aspects.CORPUS, 1)));
    public static Materials Brass = new Materials(301, TextureSet.SET_METALLIC, 		7.0F, 96, 1, 1|2|64|128, 255, 180, 0, 0, "Brass", "Brass", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)));
    public static Materials Bronze = new Materials(300, TextureSet.SET_METALLIC, 		6.0F, 192, 2, 1|2|64|128, 255, 128, 0, 0, "Bronze", "Bronze", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 2, Arrays.asList(new MaterialStack(Tin, 1), new MaterialStack(Copper, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)));
    public static Materials BrownLimonite = new Materials(930, TextureSet.SET_METALLIC, 1.0F, 0, 1, 1 |8 , 200, 100, 0, 0, "BrownLimonite", "Brown Limonite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, 2, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 2))); // FeO(OH)
    public static Materials Calcite = new Materials(823, TextureSet.SET_DULL, 			1.0F, 0, 1, 1 |8 , 250, 230, 220, 0, "Calcite", "Calcite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 1, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)));
    public static Materials Cassiterite = new Materials(824, TextureSet.SET_METALLIC, 	1.0F, 0, 1, 8 , 220, 220, 220, 0, "Cassiterite", "Cassiterite", 0, 0, -1, 0, false, false, 4, 3, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2)));
    public static Materials CassiteriteSand = new Materials(937, TextureSet.SET_SAND, 	1.0F, 0, 1, 8 , 220, 220, 220, 0, "CassiteriteSand", "Cassiterite Sand", 0, 0, -1, 0, false, false, 4, 3, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2)));
    public static Materials Chalcopyrite = new Materials(855, TextureSet.SET_DULL, 		1.0F, 0, 1, 1 |8 , 160, 120, 40, 0, "Chalcopyrite", "Chalcopyrite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, 1, Arrays.asList(new MaterialStack(Copper, 1), new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2)));
    //public static Materials Chalk = new Materials(856, TextureSet.SET_FINE, 			  1.0F, 0, 2, 1, 250, 250, 250, 0, "Chalk", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)));
    public static Materials Charcoal = new Materials(536, TextureSet.SET_FINE, 			1.0F, 0, 1, 1|4, 100, 70, 70, 0, "Charcoal", "Charcoal", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 1, Arrays.asList(new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2)));
    public static Materials Chromite = new Materials(825, TextureSet.SET_METALLIC, 		1.0F, 0, 1, 1|8, 35, 20, 15, 0, "Chromite", "Chromite", 0, 0, 1700, 1700, true, false, 6, 1, 1, Dyes.dyePink, 1, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Chrome, 2), new MaterialStack(Oxygen, 4)));
    public static Materials ChromiumDioxide  = new Materials(361, TextureSet.SET_DULL, 11.0F, 256, 3, 1|2, 230, 200, 200, 0, "ChromiumDioxide", "Chromium Dioxide", 0, 0, 650, 650, false, false, 5, 3, 1, Dyes.dyePink , 1, Arrays.asList(new MaterialStack(Chrome, 1), new MaterialStack(Oxygen, 2)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1)));
    public static Materials Cinnabar = new Materials(826, TextureSet.SET_ROUGH, 		1.0F, 0, 1, 1 |8 , 150, 0, 0, 0, "Cinnabar", "Cinnabar", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBrown, 2, Arrays.asList(new MaterialStack(Mercury, 1), new MaterialStack(Sulfur, 1)));
    public static Materials Water = new Materials(701, TextureSet.SET_FLUID, 			1.0F, 0, 0, 16, 0, 0, 255, 0, "Water", "Water", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 2)));
    public static Materials Clay = new Materials(805, TextureSet.SET_ROUGH, 			1.0F, 0, 1, 1, 200, 200, 220, 0, "Clay", "Clay", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeLightBlue, 1, Arrays.asList(new MaterialStack(Sodium, 2), new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Water, 6)));
    public static Materials Coal = new Materials(535, TextureSet.SET_ROUGH, 			1.0F, 0, 1, 1|4|8, 70, 70, 70, 0, "Coal", "Coal", 0, 0, -1, 0, false, false, 2, 2, 1, Dyes.dyeBlack, 1, Arrays.asList(new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2)));
    public static Materials Cobaltite = new Materials(827, TextureSet.SET_METALLIC, 	1.0F, 0, 1, 1 |8 , 80, 80, 250, 0, "Cobaltite", "Cobaltite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlue, 1, Arrays.asList(new MaterialStack(Cobalt, 1), new MaterialStack(Arsenic, 1), new MaterialStack(Sulfur, 1)));
    public static Materials Cooperite = new Materials(828, TextureSet.SET_METALLIC, 	1.0F, 0, 1, 1 |8 , 255, 255, 200, 0, "Cooperite", "Sheldonite", 0, 0, -1, 0, false, false, 5, 1, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(Platinum, 3), new MaterialStack(Nickel, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Palladium, 1)));
    public static Materials Cupronickel = new Materials(310, TextureSet.SET_METALLIC, 	6.0F, 64, 1, 1|2|64, 227, 150, 128, 0, "Cupronickel", "Cupronickel", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 2, Arrays.asList(new MaterialStack(Copper, 1), new MaterialStack(Nickel, 1)));
    public static Materials DarkAsh = new Materials(816, TextureSet.SET_DULL, 			1.0F, 0, 1, 1, 50, 50, 50, 0, "DarkAsh", "Dark Ashes", 0, 0, -1, 0, false, false, 1, 2, 1, Dyes.dyeGray, 1, Arrays.asList(new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.IGNIS, 1), new TC_AspectStack(TC_Aspects.PERDITIO, 1)));
    public static Materials DeepIron = new Materials(829, TextureSet.SET_METALLIC, 		6.0F, 384, 2, 1 | 2 | 8 | 64, 150, 140, 140, 0, "DeepIron", "Deep Iron", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyePink, 2, Arrays.asList(new MaterialStack(Materials.Iron, 1)), Arrays.asList(new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_Aspects.TC_AspectStack(TC_Aspects.MAGNETO, 1)));
    public static Materials Diamond = new Materials(500, TextureSet.SET_DIAMOND, 		8.0F, 1280, 3, 1|4|8 |64|128, 200, 255, 255, 127, "Diamond", "Diamond", 0, 0, -1, 0, false, true, 5, 64, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.LUCRUM, 4)));
    public static Materials Electrum = new Materials(303, TextureSet.SET_SHINY, 	   12.0F, 64, 2, 1|2|64|128, 255, 255, 100, 0, "Electrum", "Electrum", 0, 0, -1, 0, false, false, 4, 1, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(Silver, 1), new MaterialStack(Gold, 1)));
    public static Materials Emerald = new Materials(501, TextureSet.SET_EMERALD, 		7.0F, 256, 2, 1|4|8 |64, 80, 255, 80, 127, "Emerald", "Emerald", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeGreen, 1, Arrays.asList(new MaterialStack(Beryllium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 6), new MaterialStack(Oxygen, 18)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.LUCRUM, 5)));
    public static Materials FreshWater = new Materials(-1, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 0, 0, 255, 0, "FreshWater", "Fresh Water", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 2)));
    public static Materials Galena = new Materials(830, TextureSet.SET_DULL, 			1.0F, 0, 3, 1 |8 , 100, 60, 100, 0, "Galena", "Galena", 0, 0, -1, 0, false, false, 4, 1, 1, Dyes.dyePurple, 1, Arrays.asList(new MaterialStack(Lead, 3), new MaterialStack(Silver, 3), new MaterialStack(Sulfur, 2)));
    public static Materials Garnierite = new Materials(906, TextureSet.SET_METALLIC, 	1.0F, 0, 3, 1 |8 , 50, 200, 70, 0, "Garnierite", "Garnierite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightBlue, 1, Arrays.asList(new MaterialStack(Nickel, 1), new MaterialStack(Oxygen, 1)));
    public static Materials Glyceryl = new Materials(714, TextureSet.SET_FLUID, 		1.0F, 0, 1, 16, 0, 150, 150, 0, "Glyceryl", "Glyceryl Trinitrate", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeCyan, 1, Arrays.asList(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 5), new MaterialStack(Nitrogen, 3), new MaterialStack(Oxygen, 9)));
    public static Materials GreenSapphire = new Materials(504, TextureSet.SET_GEM_HORIZONTAL, 7.0F, 256, 2, 1|4|8 |64, 100, 200, 130, 127, "GreenSapphire", "Green Sapphire", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeCyan, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)));
    public static Materials Grossular = new Materials(831, TextureSet.SET_ROUGH, 		1.0F, 0, 1, 1 |8 , 200, 100, 0, 0, "Grossular", "Grossular", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeOrange, 1, Arrays.asList(new MaterialStack(Calcium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)));
    public static Materials HolyWater = new Materials(729, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 0, 0, 255, 0, "HolyWater", "Holy Water", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.AURAM, 1)));
    public static Materials Ice = new Materials(702, TextureSet.SET_SHINY, 				1.0F, 0, 0, 1| 16, 200, 200, 255, 0, "Ice", "Ice", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.GELUM, 2)));
    public static Materials Ilmenite = new Materials(918, TextureSet.SET_METALLIC, 		1.0F, 0, 3, 1 |8 , 70, 55, 50, 0, "Ilmenite", "Ilmenite", 0, 0, -1, 0, false, false, 1, 2, 1, Dyes.dyePurple, 0, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 3)));
    public static Materials Rutile = new Materials(375, TextureSet.SET_GEM_HORIZONTAL, 	1.0F, 0, 2, 1, 212, 13, 92, 0, "Rutile", "Rutile", 0, 0, -1, 0, false, false, 1, 2, 1, Dyes.dyeRed, 0, Arrays.asList(new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 2)));
    public static Materials Bauxite = new Materials(822, TextureSet.SET_DULL, 			1.0F, 0, 1, 1 |8 , 200, 100, 0, 0, "Bauxite", "Bauxite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBrown, 1, Arrays.asList(new MaterialStack(Rutile, 2), new MaterialStack(Aluminium, 16), new MaterialStack(Hydrogen, 10), new MaterialStack(Oxygen, 11)));
    public static Materials Titaniumtetrachloride =new Materials(376,TextureSet.SET_FLUID,1.0F, 0, 2, 16 , 212, 13, 92, 0, "Titaniumtetrachloride", "Titaniumtetrachloride", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed, 0, Arrays.asList(new MaterialStack(Titanium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Chlorine, 2)));
    public static Materials Magnesiumchloride = new Materials(377, TextureSet.SET_DULL, 1.0F, 0, 2, 1|16 , 212, 13, 92, 0, "Magnesiumchloride", "Magnesiumchloride", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed, 0, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Chlorine, 2)));
    public static Materials Invar = new Materials(302, TextureSet.SET_METALLIC, 		6.0F, 256, 2, 1|2|64|128, 180, 180, 120, 0, "Invar", "Invar", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, 2, Arrays.asList(new MaterialStack(Iron, 2), new MaterialStack(Nickel, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.GELUM, 1)));
    public static Materials IronCompressed = new Materials(-1, TextureSet.SET_METALLIC, 7.0F, 96, 1, 1|2|64|128, 128, 128, 128, 0, "IronCompressed", "Compressed Iron", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 2, Arrays.asList(new MaterialStack(Iron, 1)));
    public static Materials Kanthal = new Materials(312, TextureSet.SET_METALLIC, 		6.0F, 64, 2, 1|2|64, 194, 210, 223, 0, "Kanthal", "Kanthal", 0, 0, 1800, 1800, true, false, 1, 1, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Chrome, 1)));
    public static Materials Lazurite = new Materials(524, TextureSet.SET_LAPIS, 		1.0F, 0, 1, 1|4|8 , 100, 120, 255, 0, "Lazurite", "Lazurite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeCyan, 1, Arrays.asList(new MaterialStack(Aluminium, 6), new MaterialStack(Silicon, 6), new MaterialStack(Calcium, 8), new MaterialStack(Sodium, 8)));
    public static Materials Magnalium = new Materials(313, TextureSet.SET_DULL, 		6.0F, 256, 2, 1|2|64|128, 200, 190, 255, 0, "Magnalium", "Magnalium", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightBlue, 2, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Aluminium, 2)));
    public static Materials Magnesite = new Materials(908, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1 |8 , 250, 250, 180, 0, "Magnesite", "Magnesite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink, 1, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3)));
    public static Materials Magnetite = new Materials(870, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1 |8 , 30, 30, 30, 0, "Magnetite", "Magnetite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 1, Arrays.asList(new MaterialStack(Iron, 3), new MaterialStack(Oxygen, 4)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)));
    public static Materials Molybdenite = new Materials(942, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1 |8 , 25, 25, 25, 0, "Molybdenite", "Molybdenite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 1, Arrays.asList(new MaterialStack(Molybdenum, 1), new MaterialStack(Sulfur, 2))); // MoS2 (also source of Re)
    public static Materials Nichrome = new Materials(311, TextureSet.SET_METALLIC, 		6.0F, 64, 2, 1|2|64, 205, 206, 246, 0, "Nichrome", "Nichrome", 0, 0, 2700, 2700, true, false, 1, 1, 1, Dyes.dyeRed, 2, Arrays.asList(new MaterialStack(Nickel, 4), new MaterialStack(Chrome, 1)));
    public static Materials NiobiumNitride = new Materials(359, TextureSet.SET_DULL, 	1.0F, 0, 2, 1|2, 29, 41, 29, 0, "NiobiumNitride", "Niobium Nitride", 0, 0, 2573, 2573, true, false, 1, 1, 1, Dyes.dyeBlack, 1, Arrays.asList(new MaterialStack(Niobium, 1), new MaterialStack(Nitrogen, 1))); // Anti-Reflective Material
    public static Materials NiobiumTitanium = new Materials(360, TextureSet.SET_DULL, 	1.0F, 0, 2, 1|2, 29, 29, 41, 0, "NiobiumTitanium", "Niobium-Titanium", 0, 0, 4500, 4500, true, false, 1, 1, 1, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Niobium, 1), new MaterialStack(Titanium, 1)));
    public static Materials NitroCarbon = new Materials(716, TextureSet.SET_FLUID, 		1.0F, 0, 1, 16, 0, 75, 100, 0, "NitroCarbon", "Nitro-Carbon", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeCyan, 1, Arrays.asList(new MaterialStack(Nitrogen, 1), new MaterialStack(Carbon, 1)));
    public static Materials NitrogenDioxide = new Materials(717, TextureSet.SET_FLUID, 	1.0F, 0, 1, 16, 100, 175, 255, 0, "NitrogenDioxide", "Nitrogen Dioxide", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeCyan, 1, Arrays.asList(new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 2)));
    public static Materials Obsidian = new Materials(804, TextureSet.SET_DULL, 			1.0F, 0, 3, 1, 80, 50, 100, 0, "Obsidian", "Obsidian", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 1, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Iron, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 8)));
    public static Materials Phosphate = new Materials(833, TextureSet.SET_DULL, 		1.0F, 0, 1, 1 |8|16, 255, 255, 0, 0, "Phosphate", "Phosphate", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeYellow, 1, Arrays.asList(new MaterialStack(Phosphor, 1), new MaterialStack(Oxygen, 4)));
    public static Materials PigIron = new Materials(307, TextureSet.SET_METALLIC, 		6.0F, 384, 2, 1|2|64, 200, 180, 180, 0, "PigIron", "Pig Iron", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyePink, 2, Arrays.asList(new MaterialStack(Iron, 1)));
    public static Materials Plastic = new Materials(874, TextureSet.SET_DULL, 			3.0F, 32, 1, 1|2|64|128, 200, 200, 200, 0, "Plastic", "Polyethylene", 0, 0, 400, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0, Arrays.asList(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2)));
    public static Materials Epoxid = new Materials(470, TextureSet.SET_DULL, 			3.0F, 32, 1, 1|2|64|128, 200, 140, 20, 0, "Epoxid", "Epoxid", 0, 0, 400, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0, Arrays.asList(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2)));
    public static Materials Silicone = new Materials(471, TextureSet.SET_DULL, 			3.0F, 128, 1, 1|2|64|128, 220, 220, 220, 0, "Silicone", "Polysiloxane", 0, 0, 900, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0, Arrays.asList(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2)));
    public static Materials Polycaprolactam = new Materials(472, TextureSet.SET_DULL, 	3.0F, 32, 1, 1|2|64|128, 50, 50, 50, 0, "Polycaprolactam", "Polycaprolactam", 0, 0, 500, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0, Arrays.asList(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 11), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2)));
    public static Materials Polytetrafluoroethylene  = new Materials(473, TextureSet.SET_DULL, 3.0F, 32, 1, 1|2|64|128, 100, 100, 100, 0, "Polytetrafluoroethylene", "Polytetrafluoroethylene", 0, 0, 1400, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0, Arrays.asList(new MaterialStack(Carbon, 2), new MaterialStack(Fluorine, 4)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2)));
    public static Materials Powellite = new Materials(883, TextureSet.SET_DULL, 		1.0F, 0, 2, 1 |8 , 255, 255, 0, 0, "Powellite", "Powellite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4)));
    public static Materials Pumice = new Materials(926, TextureSet.SET_DULL, 			1.0F, 0, 2, 1, 230, 185, 185, 0, "Pumice", "Pumice", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 2, Arrays.asList(new MaterialStack(Stone, 1)));
    public static Materials Pyrite = new Materials(834, TextureSet.SET_ROUGH, 			1.0F, 0, 1, 1 |8 , 150, 120, 40, 0, "Pyrite", "Pyrite", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeOrange, 1, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2)));
    public static Materials Pyrolusite = new Materials(943, TextureSet.SET_DULL, 		1.0F, 0, 2, 1 |8 , 150, 150, 170, 0, "Pyrolusite", "Pyrolusite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, 1, Arrays.asList(new MaterialStack(Manganese, 1), new MaterialStack(Oxygen, 2)));
    public static Materials Pyrope = new Materials(835, TextureSet.SET_METALLIC, 		1.0F, 0, 2, 1 |8 , 120, 50, 100, 0, "Pyrope", "Pyrope", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyePurple, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)));
    public static Materials RockSalt = new Materials(944, TextureSet.SET_FINE, 			1.0F, 0, 1, 1 |8 , 240, 200, 200, 0, "RockSalt", "Rock Salt", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Chlorine, 1)));
    public static Materials Rubber = new Materials(880, TextureSet.SET_SHINY, 			1.5F, 16, 0, 1|2|64|128, 0, 0, 0, 0, "Rubber", "Rubber", 0, 0, 400, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 0, Arrays.asList(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2)));
    public static Materials RawRubber = new Materials(896, TextureSet.SET_DULL, 		1.0F, 0, 0, 1, 204, 199, 137, 0, "RawRubber", "Raw Rubber", 0, 0, 400, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0, Arrays.asList(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2)));
    public static Materials Ruby = new Materials(502, TextureSet.SET_RUBY, 				7.0F, 256, 2, 1|4|8 |64, 255, 100, 100, 127, "Ruby", "Ruby", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeRed, 1, Arrays.asList(new MaterialStack(Chrome, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)));
    public static Materials Salt = new Materials(817, TextureSet.SET_FINE, 				1.0F, 0, 1, 1 |8 , 250, 250, 250, 0, "Salt", "Salt", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Sodium, 1), new MaterialStack(Chlorine, 1)));
    public static Materials Saltpeter = new Materials(836, TextureSet.SET_FINE, 		1.0F, 0, 1, 1 |8 , 230, 230, 230, 0, "Saltpeter", "Saltpeter", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 3)));
    public static Materials SaltWater = new Materials(-1, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 0, 0, 255, 0, "SaltWater", "Salt Water", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlue, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 2)));
    public static Materials Sapphire = new Materials(503, TextureSet.SET_GEM_VERTICAL, 	7.0F, 256, 2, 1|4|8 |64, 100, 100, 200, 127, "Sapphire", "Sapphire", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeBlue, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)));
    public static Materials Scheelite = new Materials(910, TextureSet.SET_DULL, 		1.0F, 0, 3, 1 |8 , 200, 140, 20, 0, "Scheelite", "Scheelite", 0, 0, 2500, 2500, false, false, 4, 1, 1, Dyes.dyeBlack, 0, Arrays.asList(new MaterialStack(Tungsten, 1), new MaterialStack(Calcium, 2), new MaterialStack(Oxygen, 4)));
    public static Materials SiliconDioxide = new Materials(837, TextureSet.SET_QUARTZ, 	1.0F, 0, 1, 1 |16, 200, 200, 200, 0, "SiliconDioxide", "Silicon Dioxide", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLightGray, 1, Arrays.asList(new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 2)));
    public static Materials Snow = new Materials(728, TextureSet.SET_FINE, 				1.0F, 0, 0, 1| 16, 250, 250, 250, 0, "Snow", "Snow", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.GELUM, 1)));
    public static Materials Sodalite = new Materials(525, TextureSet.SET_LAPIS, 		1.0F, 0, 1, 1|4|8 , 20, 20, 255, 0, "Sodalite", "Sodalite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlue, 1, Arrays.asList(new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Sodium, 4), new MaterialStack(Chlorine, 1)));
    public static Materials SodiumPersulfate = new Materials(718, TextureSet.SET_FLUID, 1.0F, 0, 2, 16, 255, 255, 255, 0, "SodiumPersulfate", "Sodium Persulfate", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 1, Arrays.asList(new MaterialStack(Sodium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)));
    public static Materials SodiumSulfide = new Materials(719, TextureSet.SET_FLUID, 	1.0F, 0, 2, 16, 255, 255, 255, 0, "SodiumSulfide", "Sodium Sulfide", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 1, Arrays.asList(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1)));
    public static Materials HydricSulfide = new Materials(460, TextureSet.SET_FLUID, 	1.0F, 0, 2, 16, 255, 255, 255, 0, "HydricSulfide", "Hydrogen Sulfide", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1)));

    public static Materials OilHeavy = new Materials(730, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 10, 10, 10, 0, "OilHeavy", "Heavy Oil", 3, 32, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Materials OilMedium = new Materials(731, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 10, 10, 10, 0, "OilMedium", "Raw Oil", 3, 24, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Materials OilLight = new Materials(732, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 10, 10, 10, 0, "OilLight", "Light Oil", 3, 16, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);

    public static Materials NatruralGas = new Materials(733, TextureSet.SET_FLUID, 		1.0F, 0, 1, 16, 255, 255, 255, 0, "NatruralGas", "Natural Gas", 1, 15, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite);
    public static Materials SulfuricGas = new Materials(734, TextureSet.SET_FLUID, 		1.0F, 0, 1, 16, 255, 255, 255, 0, "SulfuricGas", "Sulfuric Gas", 1, 20, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite);
    public static Materials Gas = new Materials(735, TextureSet.SET_FLUID, 				1.0F, 0, 1, 16, 255, 255, 255, 0, "Gas", "Refinery Gas", 1, 128, -1, 0, false, false, 3, 1, 1, Dyes.dyeWhite);
    public static Materials SulfuricNaphtha = new Materials(736, TextureSet.SET_FLUID, 	1.0F, 0, 0, 16, 255, 255, 0, 0, "SulfuricNaphtha", "Sulfuric Naphtha", 1, 32, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials SulfuricLightFuel = new Materials(737, TextureSet.SET_FLUID,1.0F, 0, 0, 16, 255, 255, 0, 0, "SulfuricLightFuel", "Sulfuric Light Fuel", 0, 32, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials SulfuricHeavyFuel = new Materials(738, TextureSet.SET_FLUID,1.0F, 0, 0, 16, 255, 255, 0, 0, "SulfuricHeavyFuel", "Sulfuric Heavy Fuel", 3, 32, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Materials Naphtha = new Materials(739, TextureSet.SET_FLUID, 			1.0F, 0, 0, 16, 255, 255, 0, 0, "Naphtha", "Naphtha", 1, 256, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials LightFuel = new Materials(740, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 255, 255, 0, 0, "LightFuel", "Light Fuel", 0, 256, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials HeavyFuel = new Materials(741, TextureSet.SET_FLUID, 		1.0F, 0, 0, 16, 255, 255, 0, 0, "HeavyFuel", "Heavy Fuel", 3, 192, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);
    public static Materials LPG = new Materials(742, TextureSet.SET_FLUID, 				1.0F, 0, 0, 16, 255, 255, 0, 0, "LPG", "LPG", 1, 256, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials CrackedLightFuel = new Materials(743, TextureSet.SET_FLUID, 1.0F, 0, 0, 16, 255, 255, 0, 0, "CrackedLightFuel", "Cracked Light Fuel", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow);
    public static Materials CrackedHeavyFuel = new Materials(744, TextureSet.SET_FLUID, 1.0F, 0, 0, 16, 255, 255, 0, 0, "CrackedHeavyFuel", "Cracked Heavy Fuel", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack);

    public static Materials SolderingAlloy = new Materials(314, TextureSet.SET_DULL, 	1.0F, 0, 1, 1|2, 220, 220, 230, 0, "SolderingAlloy", "Soldering Alloy", 0, 0, 400, 400, false, false, 1, 1, 1, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(Tin, 9), new MaterialStack(Antimony, 1)));
    public static Materials Spessartine = new Materials(838, TextureSet.SET_DULL, 		1.0F, 0, 2, 1 |8 , 255, 100, 100, 0, "Spessartine", "Spessartine", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeRed, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Manganese, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)));
    public static Materials Sphalerite = new Materials(839, TextureSet.SET_DULL, 		1.0F, 0, 1, 1 |8 , 255, 255, 255, 0, "Sphalerite", "Sphalerite", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeYellow, 1, Arrays.asList(new MaterialStack(Zinc, 1), new MaterialStack(Sulfur, 1)));
    public static Materials StainlessSteel = new Materials(306, TextureSet.SET_SHINY, 	7.0F, 480, 2, 1|2|64|128, 200, 200, 220, 0, "StainlessSteel", "Stainless Steel", 0, 0, -1, 1700, true, false, 1, 1, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Iron, 6), new MaterialStack(Chrome, 1), new MaterialStack(Manganese, 1), new MaterialStack(Nickel, 1)));
    public static Materials Steel = new Materials(305, TextureSet.SET_METALLIC, 		6.0F, 512, 2, 1|2|64|128, 128, 128, 128, 0, "Steel", "Steel", 0, 0, 1811, 1000, true, false, 4, 51, 50, Dyes.dyeGray, 1, Arrays.asList(new MaterialStack(Iron, 50), new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 1)));
    public static Materials Stibnite = new Materials(945, TextureSet.SET_METALLIC, 		1.0F, 0, 2, 1 |8 , 70, 70, 70, 0, "Stibnite", "Stibnite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(Antimony, 2), new MaterialStack(Sulfur, 3)));
    public static Materials SulfuricAcid = new Materials(720, TextureSet.SET_FLUID, 	1.0F, 0, 2, 16, 255, 128, 0, 0, "SulfuricAcid", "Sulfuric Acid", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 1, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)));
    public static Materials Tanzanite = new Materials(508, TextureSet.SET_GEM_VERTICAL, 7.0F, 256, 2, 1|4|8 |64, 64, 0, 200, 127, "Tanzanite", "Tanzanite", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyePurple, 1, Arrays.asList(new MaterialStack(Calcium, 2), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 13)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)));
    public static Materials Tetrahedrite = new Materials(840, TextureSet.SET_DULL, 		1.0F, 0, 2, 1 |8 , 200, 32, 0, 0, "Tetrahedrite", "Tetrahedrite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeRed, 2, Arrays.asList(new MaterialStack(Copper, 3), new MaterialStack(Antimony, 1), new MaterialStack(Sulfur, 3), new MaterialStack(Iron, 1))); //Cu3SbS3 + x(Fe, Zn)6Sb2S9
    public static Materials TinAlloy = new Materials(363, TextureSet.SET_METALLIC, 		6.5F, 96, 2, 1|2|64|128, 200, 200, 200, 0, "TinAlloy", "Tin Alloy", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(Tin, 1), new MaterialStack(Iron, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)));
    public static Materials Topaz = new Materials(507, TextureSet.SET_GEM_HORIZONTAL, 	7.0F, 256, 3, 1|4|8 |64, 255, 128, 0, 127, "Topaz", "Topaz", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeOrange, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)));
    public static Materials Tungstate = new Materials(841, TextureSet.SET_DULL, 		1.0F, 0, 3, 1 |8 , 55, 50, 35, 0, "Tungstate", "Tungstate", 0, 0, 2500, 2500, true, false, 4, 1, 1, Dyes.dyeBlack, 0, Arrays.asList(new MaterialStack(Tungsten, 1), new MaterialStack(Lithium, 2), new MaterialStack(Oxygen, 4)));
    public static Materials Ultimet = new Materials(344, TextureSet.SET_SHINY, 			9.0F, 2048, 4, 1|2|64|128, 180, 180, 230, 0, "Ultimet", "Ultimet", 0, 0, 2700, 2700, true, false, 1, 1, 1, Dyes.dyeLightBlue, 1, Arrays.asList(new MaterialStack(Cobalt, 5), new MaterialStack(Chrome, 2), new MaterialStack(Nickel, 1), new MaterialStack(Molybdenum, 1))); // 54% Cobalt, 26% Chromium, 9% Nickel, 5% Molybdenum, 3% Iron, 2% Tungsten, 0.8% Manganese, 0.3% Silicon, 0.08% Nitrogen and 0.06% Carbon
    public static Materials Uraninite = new Materials(922, TextureSet.SET_METALLIC, 	1.0F, 0, 3, 1 |8 , 35, 35, 35, 0, "Uraninite", "Uraninite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeLime, 2, Arrays.asList(new MaterialStack(Uranium, 1), new MaterialStack(Oxygen, 2)));
    public static Materials Uvarovite = new Materials(842, TextureSet.SET_DIAMOND, 		1.0F, 0, 2, 1, 180, 255, 180, 0, "Uvarovite", "Uvarovite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGreen, 1, Arrays.asList(new MaterialStack(Calcium, 3), new MaterialStack(Chrome, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12)));
    public static Materials VanadiumGallium = new Materials(357, TextureSet.SET_SHINY, 	1.0F, 0, 2, 1|2, 128, 128, 140, 0, "VanadiumGallium", "Vanadium-Gallium", 0, 0, 4500, 4500, true, false, 1, 1, 1, Dyes.dyeGray, 2, Arrays.asList(new MaterialStack(Vanadium, 3), new MaterialStack(Gallium, 1)));
    public static Materials Wood = new Materials(809, TextureSet.SET_WOOD, 				2.0F, 16, 0, 1|2|64|128, 100, 50, 0, 0, "Wood", "Wood", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, 0, Arrays.asList(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 1), new MaterialStack(Hydrogen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.ARBOR, 2)));
    public static Materials WroughtIron = new Materials(304, TextureSet.SET_METALLIC, 	6.0F, 384, 2, 1|2|64|128, 200, 180, 180, 0, "WroughtIron", "Wrought Iron", 0, 0, 1811, 0, false, false, 3, 1, 1, Dyes.dyeLightGray, 2, Arrays.asList(new MaterialStack(Iron, 1)));
    public static Materials Wulfenite = new Materials(882, TextureSet.SET_DULL, 		1.0F, 0, 3, 1 |8 , 255, 128, 0, 0, "Wulfenite", "Wulfenite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 2, Arrays.asList(new MaterialStack(Lead, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4)));
    public static Materials YellowLimonite = new Materials(931, TextureSet.SET_METALLIC,1.0F, 0, 2, 1 |8 , 200, 200, 0, 0, "YellowLimonite", "Yellow Limonite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 2))); // FeO(OH) + a bit Ni and Co
    public static Materials YttriumBariumCuprate  = new Materials(358, TextureSet.SET_METALLIC, 1.0F, 0, 2, 1|2, 80, 64, 70, 0, "YttriumBariumCuprate", "Yttrium Barium Cuprate", 0, 0, 4500, 4500, true, false, 1, 1, 1, Dyes.dyeGray, 0, Arrays.asList(new MaterialStack(Yttrium, 1), new MaterialStack(Barium, 2), new MaterialStack(Copper, 3), new MaterialStack(Oxygen, 7)));

    /**
     * Second Degree Compounds
     */
    public static Materials WoodSealed = new Materials( 889, TextureSet.SET_WOOD, 		3.0F, 24, 0, 1|2|64|128, 80, 40, 0, 0, "WoodSealed", "Sealed Wood", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBrown, 0, Arrays.asList(new MaterialStack(Wood, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.ARBOR, 2), new TC_AspectStack(TC_Aspects.FABRICO, 1)));
    public static Materials LiveRoot = new Materials(832, TextureSet.SET_WOOD, 			1.0F, 0, 1, 1, 220, 200, 0, 0, "LiveRoot", "Liveroot", 5, 16, -1, 0, false, false, 2, 4, 3, Dyes.dyeBrown, 2, Arrays.asList(new MaterialStack(Wood, 3), new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.ARBOR, 2), new TC_AspectStack(TC_Aspects.VICTUS, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)));
    public static Materials IronWood = new Materials(338, TextureSet.SET_WOOD, 			6.0F, 384, 2, 1|2|64|128, 150, 140, 110, 0, "IronWood", "Ironwood", 5, 8, -1, 0, false, false, 2, 19, 18, Dyes.dyeBrown, 2, Arrays.asList(new MaterialStack(Iron, 9), new MaterialStack(LiveRoot, 9), new MaterialStack(Gold, 1)));
    public static Materials Glass = new Materials( 890, TextureSet.SET_GLASS, 			1.0F, 4, 0, 1|4, 250, 250, 250, 220, "Glass", "Glass", 0, 0, 1500, 0, false, true, 1, 1, 1, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(SiliconDioxide, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 2)));
    public static Materials Perlite = new Materials( 925, TextureSet.SET_DULL, 			1.0F, 0, 1, 1, 30, 20, 30, 0, "Perlite", "Perlite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Obsidian, 2), new MaterialStack(Water, 1)));
    public static Materials Borax = new Materials( 941, TextureSet.SET_FINE, 			1.0F, 0, 1, 1, 250, 250, 250, 0, "Borax", "Borax", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Sodium, 2), new MaterialStack(Boron, 4), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 7)));
    public static Materials Lignite = new Materials( 538, TextureSet.SET_LIGNITE, 		1.0F, 0, 0, 1|4|8 , 100, 70, 70, 0, "Lignite", "Lignite Coal", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 1, Arrays.asList(new MaterialStack(Carbon, 2), new MaterialStack(Water, 4), new MaterialStack(DarkAsh, 1)));
    public static Materials Olivine = new Materials( 505, TextureSet.SET_RUBY, 			7.0F, 256, 2, 1|4|8 |64, 150, 255, 150, 127, "Olivine", "Olivine", 0, 0, -1, 0, false, true, 5, 1, 1, Dyes.dyeLime, 1, Arrays.asList(new MaterialStack(Magnesium, 2), new MaterialStack(Iron, 1), new MaterialStack(SiliconDioxide, 2)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 2)));
    public static Materials Opal = new Materials( 510, TextureSet.SET_OPAL, 			7.0F, 256, 2, 1|4|8 |64, 0, 0, 255, 0, "Opal", "Opal", 0, 0, -1, 0, false, true, 3, 1, 1, Dyes.dyeBlue, 1, Arrays.asList(new MaterialStack(SiliconDioxide, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3)));
    public static Materials Amethyst = new Materials( 509, TextureSet.SET_FLINT, 		7.0F, 256, 3, 1|4|8 |64, 210, 50, 210, 127, "Amethyst", "Amethyst", 0, 0, -1, 0, false, true, 3, 1, 1, Dyes.dyePink, 1, Arrays.asList(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Iron, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4)));
    public static Materials Redstone = new Materials( 810, TextureSet.SET_ROUGH, 		1.0F, 0, 2, 1 |8 , 200, 0, 0, 0, "Redstone", "Redstone", 0, 0, 500, 0, false, false, 3, 1, 1, Dyes.dyeRed, 2, Arrays.asList(new MaterialStack(Silicon, 1), new MaterialStack(Pyrite, 5), new MaterialStack(Ruby, 1), new MaterialStack(Mercury, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 2)));
    public static Materials Lapis = new Materials( 526, TextureSet.SET_LAPIS, 			1.0F, 0, 1, 1|4|8 , 70, 70, 220, 0, "Lapis", "Lapis", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeBlue, 2, Arrays.asList(new MaterialStack(Lazurite, 12), new MaterialStack(Sodalite, 2), new MaterialStack(Pyrite, 1), new MaterialStack(Calcite, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.SENSUS, 1)));
    public static Materials Blaze = new Materials( 801, TextureSet.SET_POWDER, 			2.0F, 16, 1, 1|64, 255, 200, 0, 0, "Blaze", "Blaze", 0, 0, 6400, 0, false, false, 2, 3, 2, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(DarkAsh, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2), new TC_AspectStack(TC_Aspects.IGNIS, 4)));
    public static Materials EnderPearl = new Materials( 532, TextureSet.SET_SHINY, 		1.0F, 16, 1, 1|4, 108, 220, 200, 0, "EnderPearl", "Enderpearl", 0, 0, -1, 0, false, false, 1, 16, 10, Dyes.dyeGreen, 1, Arrays.asList(new MaterialStack(Beryllium, 1), new MaterialStack(Potassium, 4), new MaterialStack(Nitrogen, 5), new MaterialStack(Magic, 6)), Arrays.asList(new TC_AspectStack(TC_Aspects.ALIENIS, 4), new TC_AspectStack(TC_Aspects.ITER, 4), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2)));
    public static Materials EnderEye = new Materials( 533, TextureSet.SET_SHINY, 		1.0F, 16, 1, 1|4, 160, 250, 230, 0, "EnderEye", "Endereye", 5, 10, -1, 0, false, false, 1, 2, 1, Dyes.dyeGreen, 2, Arrays.asList(new MaterialStack(EnderPearl, 1), new MaterialStack(Blaze, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.SENSUS, 4), new TC_AspectStack(TC_Aspects.ALIENIS, 4), new TC_AspectStack(TC_Aspects.ITER, 4), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 2)));
    public static Materials Flint = new Materials( 802, TextureSet.SET_FLINT, 			2.5F, 64, 1, 1|64, 0, 32, 64, 0, "Flint", "Flint", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 2, Arrays.asList(new MaterialStack(SiliconDioxide, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.TERRA, 1), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1)));
    public static Materials Diatomite = new Materials( 948, TextureSet.SET_DULL, 		1.0F, 0, 1, 1 |8 , 225, 225, 225, 0, "Diatomite", "Diatomite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 2, Arrays.asList(new MaterialStack(Flint, 8), new MaterialStack(BandedIron, 1), new MaterialStack(Sapphire, 1)));
    public static Materials VolcanicAsh = new Materials( 940, TextureSet.SET_FLINT, 	1.0F, 0, 0, 1, 60, 50, 50, 0, "VolcanicAsh", "Volcanic Ashes", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Flint, 6), new MaterialStack(Iron, 1), new MaterialStack(Magnesium, 1)));
    public static Materials Niter = new Materials( 531, TextureSet.SET_FLINT, 			1.0F, 0, 1, 1|4, 255, 200, 200, 0, "Niter", "Niter", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink, 2, Arrays.asList(new MaterialStack(Saltpeter, 1)));
    public static Materials Pyrotheum = new Materials( 843, TextureSet.SET_FIERY, 		1.0F, 0, 1, 1, 255, 128, 0, 0, "Pyrotheum", "Pyrotheum", 2, 62, -1, 0, false, false, 2, 3, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(Coal, 1), new MaterialStack(Redstone, 1), new MaterialStack(Blaze, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1)));
    public static Materials HydratedCoal = new Materials( 818, TextureSet.SET_ROUGH, 	1.0F, 0, 1, 1, 70, 70, 100, 0, "HydratedCoal", "Hydrated Coal", 0, 0, -1, 0, false, false, 1, 9, 8, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Coal, 8), new MaterialStack(Water, 1)));
    public static Materials Apatite = new Materials(530, TextureSet.SET_DIAMOND, 		1.0F, 0, 1, 1|4|8, 200, 200, 255, 0, "Apatite", "Apatite", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeCyan, 1, Arrays.asList(new MaterialStack(Calcium, 5), new MaterialStack(Phosphate, 3), new MaterialStack(Chlorine, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.MESSIS, 2)));
    public static Materials Alumite = new Materials(-1, TextureSet.SET_METALLIC, 		1.5F, 64, 0, 1|2|64, 255, 255, 255, 0, "Alumite", "Alumite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink, 2, Arrays.asList(new MaterialStack(Aluminium, 5), new MaterialStack(Iron, 2), new MaterialStack(Obsidian, 2)), Arrays.asList(new TC_AspectStack(TC_Aspects.STRONTIO, 2)));
    public static Materials Manyullyn = new Materials(-1, TextureSet.SET_METALLIC, 		1.5F, 64, 0, 1|2|64, 255, 255, 255, 0, "Manyullyn", "Manyullyn", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePurple, 2, Arrays.asList(new MaterialStack(Cobalt, 1), new MaterialStack(Aredrite, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.STRONTIO, 2)));
    public static Materials ShadowIron = new Materials(336, TextureSet.SET_METALLIC, 	6.0F, 384, 2, 1 | 2 | 8 | 64, 120, 120, 120, 0, "ShadowIron", "Shadowiron", 0, 0, -1, 0, false, false, 3, 4, 3, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Materials.Iron, 3), new MaterialStack(Materials.Magic, 1)));
    public static Materials ShadowSteel = new Materials(337, TextureSet.SET_METALLIC, 	6.0F, 768, 2, 1 | 2 | 64, 90, 90, 90, 0, "ShadowSteel", "Shadowsteel", 0, 0, -1, 1700, true, false, 4, 4, 3, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Materials.Steel, 3), new MaterialStack(Materials.Magic, 1)));
    public static Materials Steeleaf = new Materials(339, TextureSet.SET_LEAF, 			8.0F, 768, 3, 1|2|64|128, 50, 127, 50, 0, "Steeleaf", "Steeleaf", 5, 24, -1, 0, false, false, 4, 1, 1, Dyes.dyeGreen, 2, Arrays.asList(new MaterialStack(Steel, 1), new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.HERBA, 2), new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)));
    public static Materials Knightmetal = new Materials(362, TextureSet.SET_METALLIC, 	8.0F, 1024, 3, 1|2|64|128, 210, 240, 200, 0, "Knightmetal", "Knightmetal", 5, 24, -1, 0, false, false, 4, 1, 1, Dyes.dyeLime, 2, Arrays.asList(new MaterialStack(Steel, 2), new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 1), new TC_AspectStack(TC_Aspects.METALLUM, 2)));
    public static Materials SterlingSilver = new Materials( 350, TextureSet.SET_SHINY, 13.0F, 128, 2, 1|2|64|128, 250, 220, 225, 0, "SterlingSilver", "Sterling Silver", 0, 0, -1, 1700, true, false, 4, 1, 1, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(Copper, 1), new MaterialStack(Silver, 4)));
    public static Materials RoseGold = new Materials( 351, TextureSet.SET_SHINY, 	   14.0F, 128, 2, 1|2|64|128, 255, 230, 30, 0, "RoseGold", "Rose Gold", 0, 0, -1, 1600, true, false, 4, 1, 1, Dyes.dyeOrange, 2, Arrays.asList(new MaterialStack(Copper, 1), new MaterialStack(Gold, 4)));
    public static Materials BlackBronze = new Materials( 352, TextureSet.SET_DULL, 	   12.0F, 256, 2, 1|2|64|128, 100, 50, 125, 0, "BlackBronze", "Black Bronze", 0, 0, -1, 2000, true, false, 4, 1, 1, Dyes.dyePurple, 2, Arrays.asList(new MaterialStack(Gold, 1), new MaterialStack(Silver, 1), new MaterialStack(Copper, 3)));
    public static Materials BismuthBronze = new Materials( 353, TextureSet.SET_DULL, 	8.0F, 256, 2, 1|2|64|128, 100, 125, 125, 0, "BismuthBronze", "Bismuth Bronze", 0, 0, -1, 1100, true, false, 4, 1, 1, Dyes.dyeCyan, 2, Arrays.asList(new MaterialStack(Bismuth, 1), new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)));
    public static Materials BlackSteel = new Materials( 334, TextureSet.SET_METALLIC, 	6.5F, 768, 2, 1|2|64, 100, 100, 100, 0, "BlackSteel", "Black Steel", 0, 0, -1, 1200, true, false, 4, 1, 1, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Nickel, 1), new MaterialStack(BlackBronze, 1), new MaterialStack(Steel, 3)));
    public static Materials RedSteel = new Materials( 348, TextureSet.SET_METALLIC, 	7.0F, 896, 2, 1|2|64, 140, 100, 100, 0, "RedSteel", "Red Steel", 0, 0, -1, 1300, true, false, 4, 1, 1, Dyes.dyeRed, 2, Arrays.asList(new MaterialStack(SterlingSilver, 1), new MaterialStack(BismuthBronze, 1), new MaterialStack(Steel, 2), new MaterialStack(BlackSteel, 4)));
    public static Materials BlueSteel = new Materials( 349, TextureSet.SET_METALLIC, 	7.5F, 1024, 2, 1|2|64, 100, 100, 140, 0, "BlueSteel", "Blue Steel", 0, 0, -1, 1400, true, false, 4, 1, 1, Dyes.dyeBlue, 2, Arrays.asList(new MaterialStack(RoseGold, 1), new MaterialStack(Brass, 1), new MaterialStack(Steel, 2), new MaterialStack(BlackSteel, 4)));
    public static Materials DamascusSteel = new Materials( 335, TextureSet.SET_METALLIC,8.0F, 1280, 2, 1|2|64, 110, 110, 110, 0, "DamascusSteel", "Damascus Steel", 0, 0, 2000, 1500, true, false, 4, 1, 1, Dyes.dyeGray, 2, Arrays.asList(new MaterialStack(Steel, 1)));
    public static Materials TungstenSteel = new Materials( 316, TextureSet.SET_METALLIC,8.0F, 2560, 4, 1|2|64|128, 100, 100, 160, 0, "TungstenSteel", "Tungstensteel", 0, 0, -1, 3000, true, false, 4, 1, 1, Dyes.dyeBlue, 2, Arrays.asList(new MaterialStack(Steel, 1), new MaterialStack(Tungsten, 1)));
    public static Materials NitroCoalFuel = new Materials(-1, TextureSet.SET_FLUID, 	1.0F, 0, 2, 16, 50, 70, 50, 0, "NitroCoalFuel", "Nitro-Coalfuel", 0, 48, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 0, Arrays.asList(new MaterialStack(Glyceryl, 1), new MaterialStack(CoalFuel, 4)));
    public static Materials NitroFuel = new Materials( 709, TextureSet.SET_FLUID, 		1.0F, 0, 2, 16, 200, 255, 0, 0, "NitroFuel", "Nitro-Diesel", 0, 512, -1, 0, false, false, 1, 1, 1, Dyes.dyeLime, 0, Arrays.asList(new MaterialStack(Glyceryl, 1), new MaterialStack(Fuel, 4)));
    public static Materials AstralSilver = new Materials(333, TextureSet.SET_SHINY,    10.0F, 64, 2, 1 | 2 | 8 | 64, 230, 230, 255, 0, "AstralSilver", "Astral Silver", 0, 0, -1, 0, false, false, 4, 3, 2, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(Materials.Silver, 2), new MaterialStack(Materials.Magic, 1)));
    public static Materials Midasium = new Materials(332, TextureSet.SET_SHINY, 	   12.0F, 64, 2, 1 | 2 | 8 | 64, 255, 200, 40, 0, "Midasium", "Midasium", 0, 0, -1, 0, false, false, 4, 3, 2, Dyes.dyeOrange, 2, Arrays.asList(new MaterialStack(Materials.Gold, 2), new MaterialStack(Materials.Magic, 1)));
    public static Materials Mithril = new Materials(331, TextureSet.SET_SHINY, 		   14.0F, 64, 3, 1|2|64, 255, 255, 210, 0, "Mithril", "Mithril", 0, 0, -1, 0, false, false, 4, 3, 2, Dyes.dyeLightBlue, 2, Arrays.asList(new MaterialStack(Materials.Platinum, 2), new MaterialStack(Materials.Magic, 1)));
    public static Materials BlueAlloy = new Materials(309, TextureSet.SET_DULL, 		1.0F, 0, 0, 1|2, 100, 180, 255, 0, "BlueAlloy", "Blue Alloy", 0, 0, -1, 0, false, false, 3, 5, 1, Dyes.dyeLightBlue, 2, Arrays.asList(new MaterialStack(Silver, 1), new MaterialStack(Nikolite, 4)), Arrays.asList(new TC_AspectStack(TC_Aspects.ELECTRUM, 3)));
    public static Materials RedAlloy = new Materials( 308, TextureSet.SET_DULL, 		1.0F, 0, 0, 1|2, 200, 0, 0, 0, "RedAlloy", "Red Alloy", 0, 0, -1, 0, false, false, 3, 5, 1, Dyes.dyeRed, 2, Arrays.asList(new MaterialStack(Metal, 1), new MaterialStack(Redstone, 4)), Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 3)));
    public static Materials CobaltBrass = new Materials( 343, TextureSet.SET_METALLIC, 	8.0F, 256, 2, 1|2|64|128, 180, 180, 160, 0, "CobaltBrass", "Cobalt Brass", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeOrange, 2, Arrays.asList(new MaterialStack(Brass, 7), new MaterialStack(Aluminium, 1), new MaterialStack(Cobalt, 1)));
    public static Materials Phosphorus = new Materials( 534, TextureSet.SET_FLINT, 		1.0F, 0, 2, 1|4|8|16, 255, 255, 0, 0, "Phosphorus", "Phosphorus", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(Calcium, 3), new MaterialStack(Phosphate, 2)));
    public static Materials Basalt = new Materials( 844, TextureSet.SET_ROUGH, 			1.0F, 0, 1, 1, 30, 20, 20, 0, "Basalt", "Basalt", 0, 0, -1, 0, false, false, 2, 1, 1, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Olivine, 1), new MaterialStack(Calcite, 3), new MaterialStack(Flint, 8), new MaterialStack(DarkAsh, 4)), Arrays.asList(new TC_AspectStack(TC_Aspects.TENEBRAE, 1)));
    public static Materials GarnetRed = new Materials( 527, TextureSet.SET_RUBY, 		7.0F, 128, 2, 1|4|8 |64, 200, 80, 80, 127, "GarnetRed", "Red Garnet", 0, 0, -1, 0, false, true, 4, 1, 1, Dyes.dyeRed, 2, Arrays.asList(new MaterialStack(Pyrope, 3), new MaterialStack(Almandine, 5), new MaterialStack(Spessartine, 8)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 3)));
    public static Materials GarnetYellow = new Materials( 528, TextureSet.SET_RUBY, 	7.0F, 128, 2, 1|4|8 |64, 200, 200, 80, 127, "GarnetYellow", "Yellow Garnet", 0, 0, -1, 0, false, true, 4, 1, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(Andradite, 5), new MaterialStack(Grossular, 8), new MaterialStack(Uvarovite, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 3)));
    public static Materials Marble = new Materials( 845, TextureSet.SET_FINE, 			1.0F, 0, 1, 1, 200, 200, 200, 0, "Marble", "Marble", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Calcite, 7)), Arrays.asList(new TC_AspectStack(TC_Aspects.PERFODIO, 1)));
    public static Materials Sugar = new Materials( 803, TextureSet.SET_FINE, 			1.0F, 0, 1, 1, 250, 250, 250, 0, "Sugar", "Sugar", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 1, Arrays.asList(new MaterialStack(Carbon, 2), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 25)), Arrays.asList(new TC_AspectStack(TC_Aspects.HERBA, 1), new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.AER, 1)));
    public static Materials Thaumium = new Materials(330, TextureSet.SET_METALLIC, 	   12.0F, 256, 3, 1|2|64|128, 150, 100, 200, 0, "Thaumium", "Thaumium", 0, 0, -1, 0, false, false, 5, 2, 1, Dyes.dyePurple, 0, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)));
    public static Materials Vinteum = new Materials(529, TextureSet.SET_EMERALD, 	   10.0F, 128, 3, 1|4|8|64, 100, 200, 255, 0, "Vinteum", "Vinteum", 5, 32, -1, 0, false, false, 4, 1, 1, Dyes.dyeLightBlue, 2, Arrays.asList(new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)));
    public static Materials Vis = new Materials(-1, TextureSet.SET_SHINY, 				1.0F, 0, 3, 0, 128, 0, 255, 0, "Vis", "Vis", 5, 32, -1, 0, false, false, 1, 1, 1, Dyes.dyePurple, 2, Arrays.asList(new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.AURAM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1)));
    public static Materials Redrock = new Materials( 846, TextureSet.SET_ROUGH, 		1.0F, 0, 1, 1, 255, 80, 50, 0, "Redrock", "Redrock", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeRed, 2, Arrays.asList(new MaterialStack(Calcite, 2), new MaterialStack(Flint, 1), new MaterialStack(Clay, 1)));
    public static Materials PotassiumFeldspar = new Materials( 847, TextureSet.SET_FINE,1.0F, 0, 1, 1, 120, 40, 40, 0, "PotassiumFeldspar", "Potassium Feldspar", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyePink, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 8)));
    public static Materials Biotite = new Materials( 848, TextureSet.SET_METALLIC, 		1.0F, 0, 1, 1, 20, 30, 20, 0, "Biotite", "Biotite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeGray, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 3), new MaterialStack(Aluminium, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 10)));
    public static Materials GraniteBlack = new Materials( 849, TextureSet.SET_ROUGH, 	4.0F, 64, 3, 1|64|128, 10, 10, 10, 0, "GraniteBlack", "Black Granite", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Biotite, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.TUTAMEN, 1)));
    public static Materials GraniteRed = new Materials( 850, TextureSet.SET_ROUGH, 		4.0F, 64, 3, 1|64|128, 255, 0, 128, 0, "GraniteRed", "Red Granite", 0, 0, -1, 0, false, false, 0, 1, 1, Dyes.dyeMagenta, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(PotassiumFeldspar, 1), new MaterialStack(Oxygen, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.TUTAMEN, 1)));
    public static Materials Chrysotile = new Materials( 912, TextureSet.SET_DULL, 		1.0F, 0, 2, 1, 110, 140, 110, 0, "Chrysotile", "Chrysotile", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(Asbestos, 1)));
    public static Materials Realgar = new Materials( 913, TextureSet.SET_DULL, 			1.0F, 0, 2, 1, 140, 100, 100, 0, "Realgar", "Realgar", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(Arsenic, 4), new MaterialStack(Sulfur, 4)));
    public static Materials VanadiumMagnetite = new Materials( 923, TextureSet.SET_METALLIC, 1.0F, 0, 2, 1 |8 , 35, 35, 60, 0, "VanadiumMagnetite", "Vanadium Magnetite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Magnetite, 1), new MaterialStack(Vanadium, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1))); // Mixture of Fe3O4 and V2O5
    public static Materials BasalticMineralSand = new Materials( 935, TextureSet.SET_SAND, 1.0F, 0, 1, 1, 40, 50, 40, 0, "BasalticMineralSand", "Basaltic Mineral Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Magnetite, 1), new MaterialStack(Basalt, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)));
    public static Materials GraniticMineralSand = new Materials( 936, TextureSet.SET_SAND, 1.0F, 0, 1, 1, 40, 60, 60, 0, "GraniticMineralSand", "Granitic Mineral Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Magnetite, 1), new MaterialStack(GraniteBlack, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)));
    public static Materials GarnetSand = new Materials( 938, TextureSet.SET_SAND, 		1.0F, 0, 1, 1, 200, 100, 0, 0, "GarnetSand", "Garnet Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeOrange, 2, Arrays.asList(new MaterialStack(GarnetRed, 1), new MaterialStack(GarnetYellow, 1)));
    public static Materials QuartzSand = new Materials( 939, TextureSet.SET_SAND, 		1.0F, 0, 1, 1, 200, 200, 200, 0, "QuartzSand", "Quartz Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(CertusQuartz, 1), new MaterialStack(Quartzite, 1)));
    public static Materials Bastnasite = new Materials( 905, TextureSet.SET_FINE, 		1.0F, 0, 2, 1 |8 , 200, 110, 45, 0, "Bastnasite", "Bastnasite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Cerium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Fluorine, 1), new MaterialStack(Oxygen, 3))); // (Ce, La, Y)CO3F
    public static Materials Pentlandite = new Materials( 909, TextureSet.SET_DULL, 		1.0F, 0, 2, 1 |8 , 165, 150, 5, 0, "Pentlandite", "Pentlandite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Nickel, 9), new MaterialStack(Sulfur, 8))); // (Fe, Ni)9S8
    public static Materials Spodumene = new Materials( 920, TextureSet.SET_DULL, 		1.0F, 0, 2, 1 |8 , 190, 170, 170, 0, "Spodumene", "Spodumene", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 6))); // LiAl(SiO3)2
    public static Materials Pollucite = new Materials( 919, TextureSet.SET_DULL, 		1.0F, 0, 2, 1, 240, 210, 210, 0, "Pollucite", "Pollucite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Caesium, 2), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 4), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 12))); // (Cs, Na)2Al2Si4O12 2H2O (also a source of Rb)
    public static Materials Tantalite = new Materials( 921, TextureSet.SET_METALLIC, 	1.0F, 0, 3, 1 |8 , 145, 80, 40, 0, "Tantalite", "Tantalite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Manganese, 1), new MaterialStack(Tantalum, 2), new MaterialStack(Oxygen, 6))); // (Fe, Mn)Ta2O6 (also source of Nb)
    public static Materials Lepidolite = new Materials( 907, TextureSet.SET_FINE, 		1.0F, 0, 2, 1 |8 , 240, 50, 140, 0, "Lepidolite", "Lepidolite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Lithium, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10))); // K(Li, Al, Rb)3(Al, Si)4O10(F, OH)2
    public static Materials Glauconite = new Materials( 933, TextureSet.SET_DULL, 		1.0F, 0, 2, 1 |8 , 130, 180, 60, 0, "Glauconite", "Glauconite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 2), new MaterialStack(Aluminium, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12))); // (K, Na)(Fe3+, Al, Mg)2(Si, Al)4O10(OH)2
    public static Materials GlauconiteSand = new Materials( 949, TextureSet.SET_DULL, 	1.0F, 0, 2, 1, 130, 180, 60, 0, "GlauconiteSand", "Glauconite Sand", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 2), new MaterialStack(Aluminium, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12))); // (K, Na)(Fe3+, Al, Mg)2(Si, Al)4O10(OH)2
    public static Materials Vermiculite = new Materials( 932, TextureSet.SET_METALLIC, 	1.0F, 0, 2, 1, 200, 180, 15, 0, "Vermiculite", "Vermiculite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Iron, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Water, 4), new MaterialStack(Oxygen, 12))); // (Mg+2, Fe+2, Fe+3)3 [(AlSi)4O10] (OH)2 4H2O)
    public static Materials Bentonite = new Materials( 927, TextureSet.SET_ROUGH, 		1.0F, 0, 2, 1 |8 , 245, 215, 210, 0, "Bentonite", "Bentonite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Sodium, 1), new MaterialStack(Magnesium, 6), new MaterialStack(Silicon, 12), new MaterialStack(Hydrogen, 6), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 36))); // (Na, Ca)0.33(Al, Mg)2(Si4O10)(OH)2 nH2O
    public static Materials FullersEarth = new Materials( 928, TextureSet.SET_FINE, 	1.0F, 0, 2, 1, 160, 160, 120, 0, "FullersEarth", "Fullers Earth", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 4), new MaterialStack(Oxygen, 11))); // (Mg, Al)2Si4O10(OH) 4(H2O)
    public static Materials Pitchblende = new Materials( 873, TextureSet.SET_DULL, 		1.0F, 0, 3, 1 |8 , 200, 210, 0, 0, "Pitchblende", "Pitchblende", 0, 0, -1, 0, false, false, 5, 1, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(Uraninite, 3), new MaterialStack(Thorium, 1), new MaterialStack(Lead, 1)));
    public static Materials Monazite = new Materials( 520, TextureSet.SET_DIAMOND, 		1.0F, 0, 1, 1|4|8 , 50, 70, 50, 0, "Monazite", "Monazite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGreen, 1, Arrays.asList(new MaterialStack(RareEarth, 1), new MaterialStack(Phosphate, 1))); // Wikipedia: (Ce, La, Nd, Th, Sm, Gd)PO4 Monazite also smelt-extract to Helium, it is brown like the rare earth Item Monazite sand deposits are inevitably of the monazite-(Ce) composition. Typically, the lanthanides in such monazites contain about 45ִ8% cerium, about 24% lanthanum, about 17% neodymium, about 5% praseodymium, and minor quantities of samarium, gadolinium, and yttrium. Europium concentrations tend to be low, about 0.05% Thorium content of monazite is variable and sometimes can be up to 20ֳ0%
    public static Materials Malachite = new Materials( 871, TextureSet.SET_DULL, 		1.0F, 0, 2, 1 |8 , 5, 95, 5, 0, "Malachite", "Malachite", 0, 0, -1, 0, false, false, 3, 1, 1, Dyes.dyeGreen, 1, Arrays.asList(new MaterialStack(Copper, 2), new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 5))); // Cu2CO3(OH)2
    public static Materials Mirabilite = new Materials( 900, TextureSet.SET_DULL, 		1.0F, 0, 2, 1, 240, 250, 210, 0, "Mirabilite", "Mirabilite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 4))); // Na2SO4 10H2O
    public static Materials Mica = new Materials( 901, TextureSet.SET_FINE, 			1.0F, 0, 1, 1, 195, 195, 205, 0, "Mica", "Mica", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10))); // KAl2(AlSi3O10)(F, OH)2
    public static Materials Trona = new Materials( 903, TextureSet.SET_METALLIC, 		1.0F, 0, 1, 1, 135, 135, 95, 0, "Trona", "Trona", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Sodium, 3), new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 6))); // Na3(CO3)(HCO3) 2H2O
    public static Materials Barite = new Materials( 904, TextureSet.SET_DULL, 			1.0F, 0, 2, 1 |8 , 230, 235, 255, 0, "Barite", "Barite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Barium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4)));
    public static Materials Gypsum = new Materials( 934, TextureSet.SET_DULL, 			1.0F, 0, 1, 1, 230, 230, 250, 0, "Gypsum", "Gypsum", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 4))); // CaSO4 2H2O
    public static Materials Alunite = new Materials( 911, TextureSet.SET_METALLIC,		1.0F, 0, 2, 1, 225, 180, 65, 0, "Alunite", "Alunite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 14))); // KAl3(SO4)2(OH)6
    public static Materials Dolomite = new Materials( 914, TextureSet.SET_FLINT, 		1.0F, 0, 1, 1, 225, 205, 205, 0, "Dolomite", "Dolomite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Oxygen, 6))); // CaMg(CO3)2
    public static Materials Wollastonite = new Materials( 915, TextureSet.SET_DULL, 	1.0F, 0, 2, 1, 240, 240, 240, 0, "Wollastonite", "Wollastonite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 3))); // CaSiO3
    public static Materials Zeolite = new Materials( 916, TextureSet.SET_DULL, 			1.0F, 0, 2, 1, 240, 230, 230, 0, "Zeolite", "Zeolite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Sodium, 1), new MaterialStack(Calcium, 4), new MaterialStack(Silicon, 27), new MaterialStack(Aluminium, 9), new MaterialStack(Water, 28), new MaterialStack(Oxygen, 72))); // NaCa4(Si27Al9)O72 28(H2O)
    public static Materials Kyanite = new Materials( 924, TextureSet.SET_FLINT, 		1.0F, 0, 2, 1, 110, 110, 250, 0, "Kyanite", "Kyanite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 5))); // Al2SiO5
    public static Materials Kaolinite = new Materials( 929, TextureSet.SET_DULL, 		1.0F, 0, 2, 1, 245, 235, 235, 0, "Kaolinite", "Kaolinite", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9))); // Al2Si2O5(OH)4
    public static Materials Talc = new Materials( 902, TextureSet.SET_DULL, 			1.0F, 0, 2, 1 |8, 90, 180, 90, 0, "Talc", "Talc", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12))); // H2Mg3(SiO3)4
    public static Materials Soapstone = new Materials( 877, TextureSet.SET_DULL, 		1.0F, 0, 1, 1 |8 , 95, 145, 95, 0, "Soapstone", "Soapstone", 0, 0, -1, 0, false, false, 1, 1, 1, Dyes._NULL, 1, Arrays.asList(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12))); // H2Mg3(SiO3)4
    public static Materials Concrete = new Materials( 947, TextureSet.SET_ROUGH, 		1.0F, 0, 1, 1, 100, 100, 100, 0, "Concrete", "Concrete", 0, 0, 300, 0, false, false, 0, 1, 1, Dyes.dyeGray, 0, Arrays.asList(new MaterialStack(Stone, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.TERRA, 1)));
    public static Materials IronMagnetic = new Materials( 354, TextureSet.SET_MAGNETIC, 6.0F, 256, 2, 1|2|64|128, 200, 200, 200, 0, "IronMagnetic", "Magnetic Iron", 0, 0, -1, 0, false, false, 4, 51, 50, Dyes.dyeGray, 1, Arrays.asList(new MaterialStack(Iron, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1)));
    public static Materials SteelMagnetic = new Materials( 355, TextureSet.SET_MAGNETIC,6.0F, 512, 2, 1|2|64|128, 128, 128, 128, 0, "SteelMagnetic", "Magnetic Steel", 0, 0, 1000, 1000, true, false, 4, 51, 50, Dyes.dyeGray, 1, Arrays.asList(new MaterialStack(Steel, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.ORDO, 1), new TC_AspectStack(TC_Aspects.MAGNETO, 1)));
    public static Materials NeodymiumMagnetic=new Materials(356,TextureSet.SET_MAGNETIC,7.0F, 512, 2, 1|2|64|128, 100, 100, 100, 0, "NeodymiumMagnetic", "Magnetic Neodymium", 0, 0, 1297, 1297, true, false, 4, 51, 50, Dyes.dyeGray, 1, Arrays.asList(new MaterialStack(Neodymium, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.MAGNETO, 3)));
    public static Materials TungstenCarbide =new Materials(370,TextureSet.SET_METALLIC,14.0F, 1280, 4, 1|2|64|128, 51, 0, 102, 0, "TungstenCarbide", "Tungstencarbide", 0, 0, 2460, 2460, true, false, 4, 1, 1, Dyes.dyeBlack, 2, Arrays.asList(new MaterialStack(Tungsten, 1), new MaterialStack(Carbon, 1)));
    public static Materials VanadiumSteel = new Materials( 371, TextureSet.SET_METALLIC,3.0F, 1920, 3, 1|2|64|128, 192, 192, 192, 0, "VanadiumSteel", "Vanadiumsteel", 0, 0, 1453, 1453, true, false, 4, 1, 1, Dyes.dyeWhite, 2, Arrays.asList(new MaterialStack(Vanadium, 1), new MaterialStack(Chrome, 1), new MaterialStack(Steel, 7)));
    public static Materials HSSG = new Materials( 372, TextureSet.SET_METALLIC, 	   10.0F, 4000, 3, 1|2|64|128, 153, 153, 0, 0, "HSSG", "HSS-G", 0, 0, 4500, 4500, true, false, 4, 1, 1, Dyes.dyeYellow, 2, Arrays.asList(new MaterialStack(TungstenSteel, 5), new MaterialStack(Chrome, 1), new MaterialStack(Molybdenum, 2), new MaterialStack(Vanadium, 1)));
    public static Materials HSSE = new Materials( 373, TextureSet.SET_METALLIC, 	   10.0F, 5120, 4, 1|2|64|128, 51, 102, 0, 0, "HSSE", "HSS-E", 0, 0, 5400, 5400, true, false, 4, 1, 1, Dyes.dyeBlue, 2, Arrays.asList(new MaterialStack(HSSG, 6), new MaterialStack(Cobalt, 1), new MaterialStack(Manganese, 1), new MaterialStack(Silicon, 1)));
    public static Materials HSSS = new Materials( 374, TextureSet.SET_METALLIC, 	   14.0F, 3000, 4, 1|2|64|128, 102, 0, 51, 0, "HSSS", "HSS-S", 0, 0, 5400, 5400, true, false, 4, 1, 1, Dyes.dyeRed, 2, Arrays.asList(new MaterialStack(HSSG, 6), new MaterialStack(Iridium, 2), new MaterialStack(Osmium, 1)));

    /**
     * Materials which are renamed automatically
     */
    @Deprecated public static Materials IridiumAndSodiumOxide = new Materials(IridiumSodiumOxide, false);
    @Deprecated public static Materials Palygorskite = new Materials(FullersEarth, false);
    @Deprecated public static Materials Adamantine = new Materials(Adamantium, true);
    @Deprecated public static Materials Ashes = new Materials(Ash, false);
    @Deprecated public static Materials DarkAshes = new Materials(DarkAsh, false);
    @Deprecated public static Materials Abyssal = new Materials(Basalt, false);
    @Deprecated public static Materials Adamant = new Materials(Adamantium, true);
    @Deprecated public static Materials AluminumBrass = new Materials(AluminiumBrass, false);
    @Deprecated public static Materials Aluminum = new Materials(Aluminium, false);
    @Deprecated public static Materials NaturalAluminum = new Materials(Aluminium, false);
    @Deprecated public static Materials NaturalAluminium = new Materials(Aluminium, false);
    @Deprecated public static Materials Americum = new Materials(Americium, false);
    @Deprecated public static Materials Beryl = new Materials(Emerald, false);
    @Deprecated public static Materials BlackGranite = new Materials(GraniteBlack, false);
    @Deprecated public static Materials CalciumCarbonate = new Materials(Calcite, false);
    @Deprecated public static Materials CreosoteOil = new Materials(Creosote, false);
    @Deprecated public static Materials Chromium = new Materials(Chrome, false);
    @Deprecated public static Materials Diesel = new Materials(Fuel, false);
    @Deprecated public static Materials Enderpearl = new Materials(EnderPearl, false);
    @Deprecated public static Materials Endereye = new Materials(EnderEye, false);
    @Deprecated public static Materials EyeOfEnder = new Materials(EnderEye, false);
    @Deprecated public static Materials Eyeofender = new Materials(EnderEye, false);
    @Deprecated public static Materials Flour = new Materials(Wheat, false);
    @Deprecated public static Materials Meat = new Materials(MeatRaw, false);
    @Deprecated public static Materials Garnet = new Materials(GarnetRed, true);
    @Deprecated public static Materials Granite = new Materials(GraniteBlack, false);
    @Deprecated public static Materials Goethite = new Materials(BrownLimonite, false);
    @Deprecated public static Materials Kalium = new Materials(Potassium, false);
    @Deprecated public static Materials Lapislazuli = new Materials(Lapis, false);
    @Deprecated public static Materials LapisLazuli = new Materials(Lapis, false);
    @Deprecated public static Materials Monazit = new Materials(Monazite, false);
    @Deprecated public static Materials Natrium = new Materials(Sodium, false);
    @Deprecated public static Materials Mythril = new Materials(Mithril, false);
    @Deprecated public static Materials NitroDiesel = new Materials(NitroFuel, false);
    @Deprecated public static Materials Naquadriah = new Materials(Naquadria, false);
    @Deprecated public static Materials Obby = new Materials(Obsidian, false);
    @Deprecated public static Materials Peridot = new Materials(Olivine, true);
    @Deprecated public static Materials Phosphorite = new Materials(Phosphorus, true);
    @Deprecated public static Materials Quarried = new Materials(Marble, false);
    @Deprecated public static Materials Quicksilver = new Materials(Mercury, true);
    @Deprecated public static Materials QuickSilver = new Materials(Mercury, false);
    @Deprecated public static Materials RedRock = new Materials(Redrock, false);
    @Deprecated public static Materials RefinedIron = new Materials(Iron, false);
    @Deprecated public static Materials RedGranite = new Materials(GraniteRed, false);
    @Deprecated public static Materials Sheldonite = new Materials(Cooperite, false);
    @Deprecated public static Materials Soulsand = new Materials(SoulSand, false);
    @Deprecated public static Materials Titan = new Materials(Titanium, false);
    @Deprecated public static Materials Uran = new Materials(Uranium, false);
    @Deprecated public static Materials Wolframite = new Materials(Tungstate, false);
    @Deprecated public static Materials Wolframium = new Materials(Tungsten, false);
    @Deprecated public static Materials Wolfram = new Materials(Tungsten, false);

    public final short[] mRGBa = new short[]{255, 255, 255, 0}, mMoltenRGBa = new short[]{255, 255, 255, 0};
    public TextureSet mIconSet;
    public int mMetaItemSubID;
    public boolean mUnificatable;
    public Materials mMaterialInto;
    public List<MaterialStack> mMaterialList = new ArrayList<MaterialStack>();
    public List<Materials> mOreByProducts = new ArrayList<Materials>(), mOreReRegistrations = new ArrayList<Materials>();
    public List<TC_Aspects.TC_AspectStack> mAspects = new ArrayList<TC_Aspects.TC_AspectStack>();
    public ArrayList<ItemStack> mMaterialItems = new ArrayList<ItemStack>();
    public Collection<SubTag> mSubTags = new LinkedHashSet<SubTag>();
    public Enchantment mEnchantmentTools = null, mEnchantmentArmors = null;
    public byte mEnchantmentToolsLevel = 0, mEnchantmentArmorsLevel = 0;
    public boolean mBlastFurnaceRequired = false, mTransparent = false;
    public float mToolSpeed = 1.0F, mHeatDamage = 0.0F;
    public String mChemicalFormula = "?", mName = "null", mDefaultLocalName = "null", mCustomID = "null", mConfigSection = "null";
    public Dyes mColor = Dyes._NULL;
    public short mMeltingPoint = 0, mBlastFurnaceTemp = 0, mGasTemp = 0;
    public int mTypes = 0;
    public int mDurability = 16, mFuelPower = 0, mFuelType = 0, mExtraData = 0, mOreValue = 0, mOreMultiplier = 1, mByProductMultiplier = 1, mSmeltingMultiplier = 1;
    public int mDensityMultiplier = 1, mDensityDivider = 1;
    public long mDensity = M;
    public Element mElement = null;
    public Materials mDirectSmelting = this, mOreReplacement = this, mMacerateInto = this, mSmeltInto = this, mArcSmeltInto = this, mHandleMaterial = this;
    public byte mToolQuality = 0;
    public boolean mHasParentMod = true, mHasPlasma = false, mHasGas = false, mCustomOre = false;
    public Fluid mSolid = null, mFluid = null, mGas = null, mPlasma = null;

    /**
     * This Fluid is used as standard Unit for Molten Materials. 1296 is a Molten Block, that means 144 is one Material Unit worth of fluid.
     */
    public Fluid mStandardMoltenFluid = null;

    static {
        initSubTags();
        Iron					.mOreReRegistrations.add(AnyIron	);
        PigIron					.mOreReRegistrations.add(AnyIron	);
        WroughtIron				.mOreReRegistrations.add(AnyIron	);

        Copper					.mOreReRegistrations.add(AnyCopper	);
        AnnealedCopper			.mOreReRegistrations.add(AnyCopper	);

        Bronze					.mOreReRegistrations.add(AnyBronze	);

        Peanutwood				.setMaceratingInto(Wood				);
        WoodSealed				.setMaceratingInto(Wood				);
        NetherBrick				.setMaceratingInto(Netherrack		);

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
        Netherrack				.setSmeltingInto(NetherBrick		);
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

        Amber					.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        InfusedAir				.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        InfusedFire				.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        InfusedEarth			.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        InfusedWater			.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        InfusedEntropy			.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        InfusedOrder			.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        InfusedVis				.setOreMultiplier( 2).setSmeltingMultiplier( 2);
        InfusedDull				.setOreMultiplier( 2).setSmeltingMultiplier( 2);
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
        Nikolite				.setOreMultiplier( 5).setSmeltingMultiplier( 5);
        Teslatite				.setOreMultiplier( 5).setSmeltingMultiplier( 5);
        Redstone				.setOreMultiplier( 5).setSmeltingMultiplier( 5);
        Glowstone				.setOreMultiplier( 5).setSmeltingMultiplier( 5);
        Lapis					.setOreMultiplier( 6).setSmeltingMultiplier( 6).setByProductMultiplier(4);
        Sodalite				.setOreMultiplier( 6).setSmeltingMultiplier( 6).setByProductMultiplier(4);
        Lazurite				.setOreMultiplier( 6).setSmeltingMultiplier( 6).setByProductMultiplier(4);
        Monazite				.setOreMultiplier( 8).setSmeltingMultiplier( 8).setByProductMultiplier(2);

        Plastic					.setEnchantmentForTools(Enchantment.knockback, 1);
        Rubber					.setEnchantmentForTools(Enchantment.knockback, 2);
        InfusedAir				.setEnchantmentForTools(Enchantment.knockback, 2);

        IronWood				.setEnchantmentForTools(Enchantment.fortune, 1);
        Steeleaf				.setEnchantmentForTools(Enchantment.fortune, 2);
        Midasium                .setEnchantmentForTools(Enchantment.fortune, 2);
        Mithril					.setEnchantmentForTools(Enchantment.fortune, 3);
        Vinteum					.setEnchantmentForTools(Enchantment.fortune, 1);
        Thaumium				.setEnchantmentForTools(Enchantment.fortune, 2);
        InfusedWater			.setEnchantmentForTools(Enchantment.fortune, 3);

        Flint					.setEnchantmentForTools(Enchantment.fireAspect, 1);
        DarkIron				.setEnchantmentForTools(Enchantment.fireAspect, 2);
        Firestone				.setEnchantmentForTools(Enchantment.fireAspect, 3);
        FierySteel				.setEnchantmentForTools(Enchantment.fireAspect, 3);
        Pyrotheum				.setEnchantmentForTools(Enchantment.fireAspect, 3);
        Blaze					.setEnchantmentForTools(Enchantment.fireAspect, 3);
        InfusedFire				.setEnchantmentForTools(Enchantment.fireAspect, 3);

        Force					.setEnchantmentForTools(Enchantment.silkTouch, 1);
        Amber					.setEnchantmentForTools(Enchantment.silkTouch, 1);
        EnderPearl				.setEnchantmentForTools(Enchantment.silkTouch, 1);
        Enderium				.setEnchantmentForTools(Enchantment.silkTouch, 1);
        NetherStar				.setEnchantmentForTools(Enchantment.silkTouch, 1);
        InfusedOrder			.setEnchantmentForTools(Enchantment.silkTouch, 1);

        BlackBronze				.setEnchantmentForTools(Enchantment.smite, 2);
        Gold					.setEnchantmentForTools(Enchantment.smite, 3);
        RoseGold				.setEnchantmentForTools(Enchantment.smite, 4);
        Platinum				.setEnchantmentForTools(Enchantment.smite, 5);
        InfusedVis				.setEnchantmentForTools(Enchantment.smite, 5);

        Lead					.setEnchantmentForTools(Enchantment.baneOfArthropods, 2);
        Nickel					.setEnchantmentForTools(Enchantment.baneOfArthropods, 2);
        Invar					.setEnchantmentForTools(Enchantment.baneOfArthropods, 3);
        Antimony				.setEnchantmentForTools(Enchantment.baneOfArthropods, 3);
        BatteryAlloy			.setEnchantmentForTools(Enchantment.baneOfArthropods, 4);
        Bismuth					.setEnchantmentForTools(Enchantment.baneOfArthropods, 4);
        BismuthBronze			.setEnchantmentForTools(Enchantment.baneOfArthropods, 5);
        InfusedEarth			.setEnchantmentForTools(Enchantment.baneOfArthropods, 5);

        Iron					.setEnchantmentForTools(Enchantment.sharpness, 1);
        Bronze					.setEnchantmentForTools(Enchantment.sharpness, 1);
        Brass					.setEnchantmentForTools(Enchantment.sharpness, 2);
        HSLA					.setEnchantmentForTools(Enchantment.sharpness, 2);
        Steel					.setEnchantmentForTools(Enchantment.sharpness, 2);
        WroughtIron				.setEnchantmentForTools(Enchantment.sharpness, 2);
        StainlessSteel			.setEnchantmentForTools(Enchantment.sharpness, 3);
        Knightmetal				.setEnchantmentForTools(Enchantment.sharpness, 3);
        ShadowIron				.setEnchantmentForTools(Enchantment.sharpness, 3);
        ShadowSteel				.setEnchantmentForTools(Enchantment.sharpness, 4);
        BlackSteel				.setEnchantmentForTools(Enchantment.sharpness, 4);
        RedSteel				.setEnchantmentForTools(Enchantment.sharpness, 4);
        BlueSteel				.setEnchantmentForTools(Enchantment.sharpness, 5);
        DamascusSteel			.setEnchantmentForTools(Enchantment.sharpness, 5);
        InfusedEntropy			.setEnchantmentForTools(Enchantment.sharpness, 5);
        TungstenCarbide			.setEnchantmentForTools(Enchantment.sharpness, 5);
        HSSE					.setEnchantmentForTools(Enchantment.sharpness, 5);
        HSSG					.setEnchantmentForTools(Enchantment.sharpness, 4);
        HSSS					.setEnchantmentForTools(Enchantment.sharpness, 5);

        InfusedAir				.setEnchantmentForArmors(Enchantment.respiration, 3);

        InfusedFire				.setEnchantmentForArmors(Enchantment.featherFalling, 4);

        Steeleaf				.setEnchantmentForArmors(Enchantment.protection, 2);
        Knightmetal				.setEnchantmentForArmors(Enchantment.protection, 1);
        InfusedEarth			.setEnchantmentForArmors(Enchantment.protection, 4);

        InfusedEntropy			.setEnchantmentForArmors(Enchantment.thorns, 3);

        InfusedWater			.setEnchantmentForArmors(Enchantment.aquaAffinity, 1);
        IronWood				.setEnchantmentForArmors(Enchantment.aquaAffinity, 1);

        InfusedOrder			.setEnchantmentForArmors(Enchantment.projectileProtection, 4);

        InfusedDull				.setEnchantmentForArmors(Enchantment.blastProtection, 4);

        InfusedVis				.setEnchantmentForArmors(Enchantment.protection, 4);

        FryingOilHot			.setHeatDamage(1.0F);
        Lava					.setHeatDamage(3.0F);
        Firestone				.setHeatDamage(5.0F);
        Pyrotheum				.setHeatDamage(5.0F);

        Chalcopyrite			.addOreByProducts(Pyrite				, Cobalt				, Cadmium				, Gold			);
        Sphalerite				.addOreByProducts(GarnetYellow			, Cadmium				, Gallium				, Zinc			);
        MeteoricIron			.addOreByProducts(Iron					, Nickel				, Iridium				, Platinum		);
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
        Forcicium				.addOreByProducts(Thorium				, Neodymium				, RareEarth				);
        Forcillium				.addOreByProducts(Thorium				, Neodymium				, RareEarth				);
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
        Nikolite				.addOreByProducts(Diamond				);
        Teslatite				.addOreByProducts(Diamond				);
        Magnesite				.addOreByProducts(Magnesium				);
        NetherQuartz			.addOreByProducts(Netherrack			);
        PigIron					.addOreByProducts(Iron					);
        DeepIron				.addOreByProducts(Iron					);
        ShadowIron				.addOreByProducts(Iron					);
        DarkIron				.addOreByProducts(Iron					);
        MeteoricIron			.addOreByProducts(Iron					);
        Steel					.addOreByProducts(Iron					);
        HSLA					.addOreByProducts(Iron					);
        Mithril					.addOreByProducts(Platinum				);
        Midasium				.addOreByProducts(Gold					);
        AstralSilver			.addOreByProducts(Silver				);
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
        FoolsRuby				.addOreByProducts(Jasper				);
        Amber					.addOreByProducts(Amber					);
        Topaz					.addOreByProducts(BlueTopaz				);
        BlueTopaz				.addOreByProducts(Topaz					);
        Niter					.addOreByProducts(Saltpeter				);
        Vinteum					.addOreByProducts(Vinteum				);
        Force					.addOreByProducts(Force					);
        Dilithium				.addOreByProducts(Dilithium				);
        Neutronium				.addOreByProducts(Neutronium			);
        Lithium					.addOreByProducts(Lithium				);
        Silicon					.addOreByProducts(SiliconDioxide		);
        Salt					.addOreByProducts(RockSalt				);
        RockSalt				.addOreByProducts(Salt					);

        Glue.mChemicalFormula = "No Horses were harmed for the Production";
        UUAmplifier.mChemicalFormula = "Accelerates the Mass Fabricator";
        LiveRoot.mChemicalFormula = "";
        WoodSealed.mChemicalFormula = "";
        Wood.mChemicalFormula = "";
        FoolsRuby.mChemicalFormula = Ruby.mChemicalFormula;

        Naquadah.mMoltenRGBa[0] = 0;
        Naquadah.mMoltenRGBa[1] = 255;
        Naquadah.mMoltenRGBa[2] = 0;
        Naquadah.mMoltenRGBa[3] = 0;
        NaquadahEnriched.mMoltenRGBa[0] = 64;
        NaquadahEnriched.mMoltenRGBa[1] = 255;
        NaquadahEnriched.mMoltenRGBa[2] = 64;
        NaquadahEnriched.mMoltenRGBa[3] = 0;
        Naquadria.mMoltenRGBa[0] = 128;
        Naquadria.mMoltenRGBa[1] = 255;
        Naquadria.mMoltenRGBa[2] = 128;
        Naquadria.mMoltenRGBa[3] = 0;

        NaquadahEnriched.mChemicalFormula = "Nq+";
        Naquadah.mChemicalFormula = "Nq";
        Naquadria.mChemicalFormula = "NqX";
    }

    private static void initSubTags() {
        SubTag.ELECTROMAGNETIC_SEPERATION_NEODYMIUM.addTo(Bastnasite, Monazite, Forcicium, Forcillium);
        SubTag.ELECTROMAGNETIC_SEPERATION_GOLD.addTo(Magnetite, VanadiumMagnetite, BasalticMineralSand, GraniticMineralSand);
        SubTag.ELECTROMAGNETIC_SEPERATION_IRON.addTo(YellowLimonite, BrownLimonite, Pyrite, BandedIron, Nickel, Vermiculite, Glauconite, GlauconiteSand, Pentlandite, Tin, Antimony, Ilmenite, Manganese, Chrome, Chromite, Andradite);
        SubTag.BLASTFURNACE_CALCITE_DOUBLE.addTo(Pyrite, YellowLimonite, BasalticMineralSand, GraniticMineralSand);
        SubTag.BLASTFURNACE_CALCITE_TRIPLE.addTo(Iron, PigIron, DeepIron, ShadowIron, WroughtIron, MeteoricIron, BrownLimonite);
        SubTag.WASHING_MERCURY.addTo(Gold, Silver, Osmium, Mithril, Platinum, Midasium, Cooperite, AstralSilver);
        SubTag.WASHING_SODIUMPERSULFATE.addTo(Zinc, Nickel, Copper, Cobalt, Cobaltite, Tetrahedrite);
        SubTag.METAL.addTo(AnyIron, AnyCopper, AnyBronze, Metal, Aluminium, Americium, Antimony, Beryllium, Bismuth, Caesium, Cerium, Chrome, Cobalt, Copper, Dysprosium, Erbium, Europium, Gadolinium, Gallium, Gold,
                Holmium, Indium, Iridium, Iron, Lanthanum, Lead, Lutetium, Magnesium, Manganese, Mercury, Niobium, Molybdenum, Neodymium, Neutronium, Nickel, Osmium, Palladium, Platinum, Plutonium, Plutonium241,
                Praseodymium, Promethium, Rubidium, Samarium, Scandium, Silicon, Silver, Tantalum, Tellurium, Terbium, Thorium, Thulium, Tin, Titanium, Tungsten, Uranium, Uranium235, Vanadium, Ytterbium, Yttrium,
                Zinc, /**Satinspar, Selenite, Microcline, Sylvite, RefinedGlowstone, RefinedObsidian,**/ Serpentine, Signalum, Lumium, PhasedIron, PhasedGold, DarkSteel, Terrasteel, TinAlloy, ConductiveIron, ElectricalSteel, EnergeticAlloy, VibrantAlloy,
                PulsatingIron, Manasteel, DarkThaumium, ElvenElementium, EnrichedCopper, DiamondCopper, Adamantium, Amordrine, Angmallen, Ardite, Aredrite, Atlarus, Blutonium, Carmot, Celenegil, Ceruclase, DarkIron,
                Desh, Desichalkos, Duranium, ElectrumFlux, Enderium, EnderiumBase, Eximite, FierySteel, Force, Haderoth, Hematite, Hepatizon, HSLA, Infuscolium, InfusedGold, Inolashite, Mercassium, MeteoricIron,
                MeteoricSteel, Naquadah, NaquadahAlloy, NaquadahEnriched, Naquadria, ObsidianFlux, Orichalcum, Osmonium, Oureclase, Phoenixite, Prometheum, Sanguinite, Starconium,
                Tartarite, Thyrium, Tritanium, Vulcanite, Vyroxeres, Yellorium, Zectium, AluminiumBrass, Osmiridium, Sunnarium, AnnealedCopper, BatteryAlloy, Brass, Bronze, ChromiumDioxide, Cupronickel, DeepIron,
                Electrum, Invar, IronCompressed, Kanthal, Magnalium, Nichrome, NiobiumNitride, NiobiumTitanium, PigIron, SolderingAlloy, StainlessSteel, Steel, Ultimet, VanadiumGallium, WroughtIron,
                YttriumBariumCuprate, IronWood, Alumite, Manyullyn, ShadowIron, ShadowSteel, Steeleaf, SterlingSilver, RoseGold, BlackBronze, BismuthBronze, BlackSteel, RedSteel, BlueSteel, DamascusSteel,
                TungstenSteel, AstralSilver, Midasium, Mithril, BlueAlloy, RedAlloy, CobaltBrass, Thaumium, IronMagnetic, SteelMagnetic, NeodymiumMagnetic, Knightmetal, HSSG, HSSE, HSSS, TungstenCarbide, Endium,
                VanadiumSteel, Kalendrite, Ignatius);

        SubTag.FOOD.addTo(MeatRaw, MeatCooked, Ice, Water, Salt, Chili, Cocoa, Cheese, Coffee, Chocolate, Milk, Honey, FryingOilHot, FishOil, SeedOil, SeedOilLin, SeedOilHemp, Wheat, Sugar, FreshWater);

        Wood.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        WoodSealed.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.NO_WORKING);
        Peanutwood.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        LiveRoot.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE);
        IronWood.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE);
        Steeleaf.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE, SubTag.NO_SMELTING);

        MeatRaw.add(SubTag.NO_SMASHING);
        MeatCooked.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Snow.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        Ice.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        Water.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
        Sulfur.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        Saltpeter.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
        Graphite.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.NO_SMELTING);

        Wheat.add(SubTag.FLAMMABLE, SubTag.MORTAR_GRINDABLE);
        Paper.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE, SubTag.PAPER);
        Coal.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        Charcoal.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
        Lignite.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);

        Rubber.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY, SubTag.STRETCHY);
        Plastic.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY);
        Silicone.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY, SubTag.STRETCHY);

        TNT.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        Gunpowder.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        Glyceryl.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
        NitroCoalFuel.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
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
        Nikolite.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.SMELTING_TO_FLUID);
        Teslatite.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.SMELTING_TO_FLUID);
        Netherrack.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.FLAMMABLE);
        Stone.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.NO_RECYCLING);
        Brick.add(SubTag.STONE, SubTag.NO_SMASHING);
        NetherBrick.add(SubTag.STONE, SubTag.NO_SMASHING);
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

        Sand.add(SubTag.NO_RECYCLING);

        Gold.add(SubTag.MORTAR_GRINDABLE);
        Silver.add(SubTag.MORTAR_GRINDABLE);
        Iron.add(SubTag.MORTAR_GRINDABLE);
        IronMagnetic.add(SubTag.MORTAR_GRINDABLE);
        HSLA.add(SubTag.MORTAR_GRINDABLE);
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
        Amber.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        GreenSapphire.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Sapphire.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Ruby.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        FoolsRuby.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Opal.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Olivine.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Jasper.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        GarnetRed.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        GarnetYellow.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Mimichite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        CrystalFlux.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Crystal.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Niter.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
        Apatite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Lapis.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Sodalite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Lazurite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Monazite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
        Quartzite.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        Quartz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        SiliconDioxide.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        Dilithium.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        NetherQuartz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        CertusQuartz.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        Fluix.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
        Phosphorus.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.EXPLOSIVE);
        Phosphate.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.EXPLOSIVE);
        InfusedAir.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedFire.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedEarth.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedWater.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedEntropy.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedOrder.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedVis.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        InfusedDull.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        Vinteum.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        NetherStar.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
        EnderPearl.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.PEARL);
        EnderEye.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.PEARL);
        Firestone.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL, SubTag.QUARTZ, SubTag.UNBURNABLE, SubTag.BURNING);
        Forcicium.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL);
        Forcillium.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL);
        Force.add(SubTag.CRYSTAL, SubTag.MAGICAL, SubTag.UNBURNABLE);
        Magic.add(SubTag.CRYSTAL, SubTag.MAGICAL, SubTag.UNBURNABLE);

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

        Blaze.add(SubTag.MAGICAL, SubTag.NO_SMELTING, SubTag.SMELTING_TO_FLUID, SubTag.MORTAR_GRINDABLE, SubTag.UNBURNABLE, SubTag.BURNING);
        FierySteel.add(SubTag.MAGICAL, SubTag.UNBURNABLE, SubTag.BURNING);
        ElvenElementium.add(SubTag.MAGICAL);
        DarkThaumium.add(SubTag.MAGICAL);
        Thaumium.add(SubTag.MAGICAL);
        Enderium.add(SubTag.MAGICAL);
        AstralSilver.add(SubTag.MAGICAL);
        Midasium.add(SubTag.MAGICAL);
        Mithril.add(SubTag.MAGICAL);
    }

    public static void init() {
        new ProcessingConfig();
        if (!GT_Mod.gregtechproxy.mEnableAllMaterials) new ProcessingModSupport();
        for (IMaterialHandler aRegistrator : mMaterialHandlers) {
            aRegistrator.onMaterialsInit(); //This is where addon mods can add/manipulate materials
        }
        initMaterialProperties(); //No more material addition or manipulation should be done past this point!
        MATERIALS_ARRAY = MATERIALS_MAP.values().toArray(new Materials[MATERIALS_MAP.size()]); //Generate standard object array. This is a lot faster to loop over.
        VALUES = Arrays.asList(MATERIALS_ARRAY);
        if (!GT_Mod.gregtechproxy.mEnableAllComponents) OrePrefixes.initMaterialComponents();
        for (Materials aMaterial : MATERIALS_ARRAY) {
            if (aMaterial.mMetaItemSubID >= 0) {
                if (aMaterial.mMetaItemSubID < 1000) {
                    if (aMaterial.mHasParentMod) {
                        if (GregTech_API.sGeneratedMaterials[aMaterial.mMetaItemSubID] == null) {
                            GregTech_API.sGeneratedMaterials[aMaterial.mMetaItemSubID] = aMaterial;
                        } else throw new IllegalArgumentException("The Material Index " + aMaterial.mMetaItemSubID + " for " + aMaterial.mName + " is already used!");
                    }
                } else throw new IllegalArgumentException("The Material Index " + aMaterial.mMetaItemSubID + " for " + aMaterial.mName + " is/over the maximum of 1000");
            }
        }
        // Fills empty spaces with materials, causes horrible load times.
        /*for (int i = 0; i < GregTech_API.sGeneratedMaterials.length; i++) {
            if (GregTech_API.sGeneratedMaterials[i] == null) {
                GregTech_API.sGeneratedMaterials[i] = new Materials(i, TextureSet.SET_NONE, 1.0F, 0, 2, 1|2|4|8|16|32|64|128, 92, 0, 168, 0, "TestMat" + i, 0, 0, -1, 0, false, false, 3, 1, 1, Dyes._NULL, "testmat");
            }
        }*/
    }

    public static void initMaterialProperties() {
        GT_Mod.gregtechproxy.mChangeHarvestLevels = GregTech_API.sMaterialProperties.get("harvestlevel", "ActivateHarvestLevelChange", false);
        GT_Mod.gregtechproxy.mMaxHarvestLevel = Math.min(15, GregTech_API.sMaterialProperties.get("harvestlevel", "MaxHarvestLevel",7));
        GT_Mod.gregtechproxy.mGraniteHavestLevel = GregTech_API.sMaterialProperties.get("harvestlevel", "GraniteHarvestLevel", 3);
        StringBuilder aConfigPathSB = new StringBuilder();
        for (Materials aMaterial : MATERIALS_MAP.values()) { /** The only place where MATERIALS_MAP should be used to loop over all materials. **/
            if (aMaterial != null && aMaterial != Materials._NULL && aMaterial != Materials.Empty) {
                aConfigPathSB.append("materials.").append(aMaterial.mConfigSection).append(".").append(aMaterial.mCustomOre ? aMaterial.mCustomID : aMaterial.mName);
                String aConfigPath = aConfigPathSB.toString();
                aMaterial.mMetaItemSubID = GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialID", aMaterial.mCustomOre ? -1 : aMaterial.mMetaItemSubID);
                aMaterial.mDefaultLocalName = GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialName", aMaterial.mCustomOre ? "CustomOre" + aMaterial.mCustomID : aMaterial.mDefaultLocalName);
                aMaterial.mMeltingPoint = (short) GregTech_API.sMaterialProperties.get(aConfigPath, "MeltingPoint", aMaterial.mMeltingPoint);
                aMaterial.mBlastFurnaceRequired = GregTech_API.sMaterialProperties.get(aConfigPath, "BlastFurnaceRequired", aMaterial.mBlastFurnaceRequired);
                aMaterial.mBlastFurnaceTemp = (short) GregTech_API.sMaterialProperties.get(aConfigPath, "BlastFurnaceTemp", aMaterial.mBlastFurnaceTemp);
                if (GT_Mod.gregtechproxy.mTEMachineRecipes && aMaterial.mBlastFurnaceRequired && aMaterial.mBlastFurnaceTemp < 1500) GT_ModHandler.ThermalExpansion.addSmelterBlastOre(aMaterial);
                aMaterial.mFuelPower = GregTech_API.sMaterialProperties.get(aConfigPath, "FuelPower", aMaterial.mFuelPower);
                aMaterial.mFuelType = GregTech_API.sMaterialProperties.get(aConfigPath, "FuelType", aMaterial.mFuelType);
                aMaterial.mOreValue = GregTech_API.sMaterialProperties.get(aConfigPath, "OreValue", aMaterial.mOreValue);
                aMaterial.mDensityMultiplier = GregTech_API.sMaterialProperties.get(aConfigPath, "DensityMultiplier", aMaterial.mDensityMultiplier);
                aMaterial.mDensityDivider = GregTech_API.sMaterialProperties.get(aConfigPath, "DensityDivider", aMaterial.mDensityDivider);
                aMaterial.mDensity = (long) GregTech_API.sMaterialProperties.get(aConfigPath, "Density", (M * aMaterial.mDensityMultiplier) / aMaterial.mDensityDivider);
                aMaterial.mDurability = GregTech_API.sMaterialProperties.get(aConfigPath, "ToolDurability", aMaterial.mDurability);
                aMaterial.mToolSpeed = (float) GregTech_API.sMaterialProperties.get(aConfigPath, "ToolSpeed", aMaterial.mToolSpeed);
                aMaterial.mToolQuality = (byte) GregTech_API.sMaterialProperties.get(aConfigPath, "ToolQuality", aMaterial.mToolQuality);
                //aMaterial.mIconSet = TextureSet.valueOf(GregTech_API.sMaterialProperties.get(aConfigPath.toString(), "IconSet", aMaterial.mIconSet.mSetName));
                aMaterial.mTransparent = GregTech_API.sMaterialProperties.get(aConfigPath, "Transparent", aMaterial.mTransparent);
                String aColor = GregTech_API.sMaterialProperties.get(aConfigPath, "DyeColor", aMaterial.mColor == Dyes._NULL ? "None" : aMaterial.mColor.toString());
                aMaterial.mColor = aColor.equals("None") ? Dyes._NULL : Dyes.get(aColor);
                String[] aRGBA = GregTech_API.sMaterialProperties.get(aConfigPath, "MatRGBA", String.valueOf(aMaterial.mRGBa[0] + "," + aMaterial.mRGBa[1] + "," + aMaterial.mRGBa[2] + "," + aMaterial.mRGBa[3] + ",")).split(",");
                aMaterial.mRGBa[0] = Short.parseShort(aRGBA[0]);
                aMaterial.mRGBa[1] = Short.parseShort(aRGBA[1]);
                aMaterial.mRGBa[2] = Short.parseShort(aRGBA[2]);
                aMaterial.mRGBa[3] = Short.parseShort(aRGBA[3]);
                aMaterial.mTypes = GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialTypes", aMaterial.mCustomOre ? 1|2|4|8|16|32|64|128 : aMaterial.mTypes);
                aMaterial.mUnificatable = GregTech_API.sMaterialProperties.get(aConfigPath, "Unificatable", aMaterial.mUnificatable);
                aMaterial.mChemicalFormula = GregTech_API.sMaterialProperties.get(aConfigPath, "ChemicalFormula", aMaterial.mChemicalFormula);
                aMaterial.mGasTemp = (short) GregTech_API.sMaterialProperties.get(aConfigPath, "GasTemp", aMaterial.mGasTemp);
                aMaterial.setOreMultiplier(GregTech_API.sMaterialProperties.get(aConfigPath, "OreMultiplier", aMaterial.mOreMultiplier));
                aMaterial.setSmeltingMultiplier(GregTech_API.sMaterialProperties.get(aConfigPath, "OreSmeltingMultiplier", aMaterial.mSmeltingMultiplier));
                aMaterial.setByProductMultiplier(GregTech_API.sMaterialProperties.get(aConfigPath, "OreByProductMultiplier", aMaterial.mByProductMultiplier));
                aMaterial.setHeatDamage((float) GregTech_API.sMaterialProperties.get(aConfigPath, "HeatDamage", aMaterial.mHeatDamage));
                aMaterial.mSmeltInto = MATERIALS_MAP.get(GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialSmeltInto", aMaterial.mSmeltInto.mName));
                aMaterial.mMacerateInto = MATERIALS_MAP.get(GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialMacerateInto", aMaterial.mMacerateInto.mName));
                aMaterial.mArcSmeltInto = MATERIALS_MAP.get(GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialArcSmeltInto", aMaterial.mArcSmeltInto.mName));
                aMaterial.mDirectSmelting = MATERIALS_MAP.get(GregTech_API.sMaterialProperties.get(aConfigPath, "MaterialDirectSmeltInto", aMaterial.mDirectSmelting.mName));
                aMaterial.mHasParentMod = GregTech_API.sMaterialProperties.get(aConfigPath, "HasParentMod", aMaterial.mHasParentMod);
                if (aMaterial.mHasPlasma = GregTech_API.sMaterialProperties.get(aConfigPath, "AddPlasma", aMaterial.mHasPlasma)) GT_Mod.gregtechproxy.addAutogeneratedPlasmaFluid(aMaterial);
                if (aMaterial.mHasGas = GregTech_API.sMaterialProperties.get(aConfigPath, "AddGas", aMaterial.mHasGas)) GT_Mod.gregtechproxy.addFluid(aMaterial.mName.toLowerCase(), aMaterial.mDefaultLocalName, aMaterial, 2, aMaterial.mGasTemp);
                aMaterial.mEnchantmentToolsLevel = (byte) GregTech_API.sMaterialProperties.get(aConfigPath, "EnchantmentLevel", aMaterial.mEnchantmentToolsLevel);
                String aEnchantmentName = GregTech_API.sMaterialProperties.get(aConfigPath, "Enchantment", aMaterial.mEnchantmentTools != null ? aMaterial.mEnchantmentTools.getName() : "");
                if (aMaterial.mEnchantmentTools != null && !aEnchantmentName.equals(aMaterial.mEnchantmentTools.getName())) {
                    for (int i = 0; i < Enchantment.enchantmentsList.length; i++) {
                        if (aEnchantmentName.equals(Enchantment.enchantmentsList[i].getName())) aMaterial.mEnchantmentTools = Enchantment.enchantmentsList[i];
                    }
                }
                /**
                 * Converts the pre-defined list of SubTags from a material into a list of SubTag names for setting/getting to/from the config.
                 * It is then converted to a String[] and finally to a singular String for insertion into the config
                 * If the config string is different from the default, we then want to clear the Materials SubTags and insert new ones from the config string.
                 */
                List<String> aSubTags = new ArrayList<>();
                for (SubTag aTag : aMaterial.mSubTags) aSubTags.add(aTag.mName);
                String aDefaultTagString = "," + aSubTags.toString().replace(" ", "").replace("[", "").replace("]", "");
                String aConfigTagString = GregTech_API.sMaterialProperties.get(aConfigPath, "ListSubTags", aDefaultTagString);
                if (!aConfigTagString.equals(aDefaultTagString)) {
                    aMaterial.mSubTags.clear();
                    if (aConfigTagString.length() > 0) {
                        aSubTags = new ArrayList<>(Arrays.asList(aConfigTagString.split(",")));
                        for (String aTagString : aSubTags) {
                            SubTag aTag = SubTag.sSubTags.get(aTagString);
                            if (aTag != null) aMaterial.mSubTags.add(aTag);
                        }
                    }
                }
                /** Same principal as SubTags **/
                List<String> aOreByProducts = new ArrayList<>();
                for (Materials aMat : aMaterial.mOreByProducts) aOreByProducts.add(aMat.mName);
                String aDefaultMatByProString = "," + aOreByProducts.toString().replace(" ", "").replace("[", "").replace("]", "");
                String aConfigMatByProString = GregTech_API.sMaterialProperties.get(aConfigPath, "ListMaterialByProducts", aDefaultMatByProString);
                if (!aConfigMatByProString.equals(aDefaultMatByProString)) {
                    aMaterial.mOreByProducts.clear();
                    if (aConfigMatByProString.length() > 0) {
                        aOreByProducts = new ArrayList<>(Arrays.asList(aConfigMatByProString.split(",")));
                        for (String aMaterialString : aOreByProducts) {
                            Materials aMat = MATERIALS_MAP.get(aMaterialString);
                            if (aMat != null) aMaterial.mOreByProducts.add(aMat);
                        }
                    }
                }
                /** Same principal as SubTags **/
                List<String> aOreReRegistrations = new ArrayList<>();
                for (Materials aMat : aMaterial.mOreReRegistrations) aOreReRegistrations.add(aMat.mName);
                String aDefaultMatReRegString = "," + aOreReRegistrations.toString().replace(" ", "").replace("[", "").replace("]", "");
                String aConfigMatMatReRegString = GregTech_API.sMaterialProperties.get(aConfigPath, "ListMaterialReRegistrations", aDefaultMatReRegString);
                if (!aConfigMatMatReRegString.equals(aDefaultMatReRegString)) {
                    aMaterial.mOreReRegistrations.clear();
                    if (aConfigMatMatReRegString.length() > 0) {
                        aOreReRegistrations = new ArrayList<>(Arrays.asList(aConfigMatMatReRegString.split(",")));
                        for (String aMaterialString : aOreReRegistrations) {
                            Materials aMat = MATERIALS_MAP.get(aMaterialString);
                            if (aMat != null) aMaterial.mOreReRegistrations.add(aMat);
                        }
                    }
                }
                /** Same principal as SubTags but with two values **/
                List<String> aAspects = new ArrayList<>();
                ArrayList<String> aAspectAmounts = new ArrayList<>();
                for (TC_Aspects.TC_AspectStack aAspectStack : aMaterial.mAspects) {
                    aAspects.add(aAspectStack.mAspect.toString());
                    aAspectAmounts.add(String.valueOf(aAspectStack.mAmount));
                }
                String aDefaultAspectString = "," + aAspects.toString().replace(" ", "").replace("[", "").replace("]", "");
                String aDefaultAspectAmountString = "," + aAspectAmounts.toString().replace(" ", "").replace("[", "").replace("]", "");
                String aConfigAspectString = GregTech_API.sMaterialProperties.get(aConfigPath, "ListTCAspects", aDefaultAspectString);
                String aConfigAspectAmountString = GregTech_API.sMaterialProperties.get(aConfigPath, "ListTCAspectAmounts", aDefaultAspectAmountString);
                if (!aConfigAspectString.equals(aDefaultAspectString) || !aConfigAspectAmountString.equals(aDefaultAspectAmountString)) {
                    aMaterial.mAspects.clear();
                    if (aConfigAspectString.length() > 0) {
                        aAspects = new ArrayList<>(Arrays.asList(aConfigAspectString.split(",")));
                        for (int i = 0; i < aAspects.size(); i++) {
                            String aAspectString = aAspects.get(i);
                            long aAspectAmount = Long.parseLong(aAspectAmounts.get(i));
                            TC_Aspects.TC_AspectStack aAspectStack = new TC_Aspects.TC_AspectStack(TC_Aspects.valueOf(aAspectString), aAspectAmount);
                            if (aAspectStack != null) aMaterial.mAspects.add(aAspectStack);
                        }
                    }
                }
                /** Moved the harvest level changes from GT_Mod to have less things iterating over MATERIALS_ARRAY **/
                if (GT_Mod.gregtechproxy.mChangeHarvestLevels && aMaterial.mToolQuality > 0 && aMaterial.mMetaItemSubID < GT_Mod.gregtechproxy.mHarvestLevel.length && aMaterial.mMetaItemSubID >= 0) {
                    GT_Mod.gregtechproxy.mHarvestLevel[aMaterial.mMetaItemSubID] = GregTech_API.sMaterialProperties.get(aConfigPath, "HarvestLevel", aMaterial.mToolQuality);
                }
                /** Moved from GT_Proxy? (Not sure)**/
                aMaterial.mHandleMaterial = (aMaterial == Desh ? aMaterial.mHandleMaterial : aMaterial == Diamond || aMaterial == Thaumium ? Wood : aMaterial.contains(SubTag.BURNING) ? Blaze : aMaterial.contains(SubTag.MAGICAL) && aMaterial.contains(SubTag.CRYSTAL) && Loader.isModLoaded(GT_Values.MOD_ID_TC) ? Thaumium : aMaterial.getMass() > Element.Tc.getMass() * 2 ? TungstenSteel : aMaterial.getMass() > Element.Tc.getMass() ? Steel : Wood);
            }
            aConfigPathSB.setLength(0);
        }
    }

    public Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aDurability, int aToolQuality, boolean aUnificatable, String aName, String aDefaultLocalName) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aUnificatable, aName, aDefaultLocalName, "ore", false, "null");
    }

    public Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aDurability, int aToolQuality, boolean aUnificatable, String aName, String aDefaultLocalName, String aConfigSection, boolean aCustomOre, String aCustomID) {
        mMetaItemSubID = aMetaItemSubID;
        mDefaultLocalName = aDefaultLocalName;
        mName = aName;
        MATERIALS_MAP.put(mName, this);
        mCustomOre = aCustomOre;
        mCustomID = aCustomID;
        mConfigSection = aConfigSection;
        mUnificatable = aUnificatable;
        mDurability = aDurability;
        mToolSpeed = aToolSpeed;
        mToolQuality = (byte) aToolQuality;
        mMaterialInto = this;
        mIconSet = aIconSet;
    }

    public Materials(Materials aMaterialInto, boolean aReRegisterIntoThis) {
        mUnificatable = false;
        mDefaultLocalName = aMaterialInto.mDefaultLocalName;
        mName = aMaterialInto.mName;
        mMaterialInto = aMaterialInto.mMaterialInto;
        if (aReRegisterIntoThis) mMaterialInto.mOreReRegistrations.add(this);
        mChemicalFormula = aMaterialInto.mChemicalFormula;
        mMetaItemSubID = -1;
        mIconSet = TextureSet.SET_NONE;
    }

    public Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor, "ore", false, "null");
    }

    public Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, String aConfigSection) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor, aConfigSection, false, "null");
    }

    /**
     * @param aMetaItemSubID        the Sub-ID used in my own MetaItems. Range 0-1000. -1 for no Material
     * @param aTypes                which kind of Items should be generated. Bitmask as follows:
     *                              1 = Dusts of all kinds.
     *                              2 = Dusts, Ingots, Plates, Rods/Sticks, Machine Components and other Metal specific things.
     *                              4 = Dusts, Gems, Plates, Lenses (if transparent).
     *                              8 = Dusts, Impure Dusts, crushed Ores, purified Ores, centrifuged Ores etc.
     *                              16 = Cells
     *                              32 = Plasma Cells
     *                              64 = Tool Heads
     *                              128 = Gears
     * @param aR,                   aG, aB Color of the Material 0-255 each.
     * @param aA                    transparency of the Material Texture. 0 = fully visible, 255 = Invisible.
     * @param aName                 The Name used as Default for localization.
     * @param aFuelType             Type of Generator to get Energy from this Material.
     * @param aFuelPower            EU generated. Will be multiplied by 1000, also additionally multiplied by 2 for Gems.
     * @param aMeltingPoint         Used to determine the smelting Costs in Furnii.
     * @param aBlastFurnaceTemp     Used to determine the needed Heat capactiy Costs in Blast Furnii.
     * @param aBlastFurnaceRequired If this requires a Blast Furnace.
     * @param aColor                Vanilla MC Wool Color which comes the closest to this.
     */
    public Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, String aConfigSection, boolean aCustomOre, String aCustomID) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, true, aName, aDefaultLocalName, aConfigSection, aCustomOre, aCustomID);
        mMeltingPoint = (short) aMeltingPoint;
        mBlastFurnaceRequired = aBlastFurnaceRequired;
        mBlastFurnaceTemp = (short) aBlastFurnaceTemp;
        mTransparent = aTransparent;
        mFuelPower = aFuelPower;
        mFuelType = aFuelType;
        mOreValue = aOreValue;
        mDensityMultiplier = aDensityMultiplier;
        mDensityDivider = aDensityDivider;
        mDensity = (M * aDensityMultiplier) / aDensityDivider;
        mColor = aColor;
        mRGBa[0] = mMoltenRGBa[0] = (short) aR;
        mRGBa[1] = mMoltenRGBa[1] = (short) aG;
        mRGBa[2] = mMoltenRGBa[2] = (short) aB;
        mRGBa[3] = mMoltenRGBa[3] = (short) aA;
        mTypes = aTypes;
        if (mColor != null) add(SubTag.HAS_COLOR);
        if (mTransparent) add(SubTag.TRANSPARENT);
        if ((mTypes & 2) != 0) add(SubTag.SMELTING_TO_FLUID);
    }

    public Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, List<TC_Aspects.TC_AspectStack> aAspects) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor);
        mAspects.addAll(aAspects);
    }

    public Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, Element aElement, List<TC_Aspects.TC_AspectStack> aAspects) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor);
        mElement = aElement;
        mElement.mLinkedMaterials.add(this);
        if (aElement == Element._NULL) {
            mChemicalFormula = "Empty";
        } else {
            mChemicalFormula = aElement.toString();
            mChemicalFormula = mChemicalFormula.replaceAll("_", "-");
        }
        mAspects.addAll(aAspects);
    }

    public Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, int aExtraData, List<MaterialStack> aMaterialList) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor, aExtraData, aMaterialList, null);
    }

    public Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aName, String aDefaultLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, int aExtraData, List<MaterialStack> aMaterialList, List<TC_Aspects.TC_AspectStack> aAspects) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aDurability, aToolQuality, aTypes, aR, aG, aB, aA, aName, aDefaultLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor);
        mExtraData = aExtraData;
        mMaterialList.addAll(aMaterialList);
        mChemicalFormula = "";
        for (MaterialStack tMaterial : mMaterialList) mChemicalFormula += tMaterial.toString();
        mChemicalFormula = mChemicalFormula.replaceAll("_", "-");

        int tAmountOfComponents = 0, tMeltingPoint = 0;
        for (MaterialStack tMaterial : mMaterialList) {
            tAmountOfComponents += tMaterial.mAmount;
            if (tMaterial.mMaterial.mMeltingPoint > 0)
                tMeltingPoint += tMaterial.mMaterial.mMeltingPoint * tMaterial.mAmount;
            if (aAspects == null)
                for (TC_Aspects.TC_AspectStack tAspect : tMaterial.mMaterial.mAspects) tAspect.addToAspectList(mAspects);
        }

        if (mMeltingPoint < 0) mMeltingPoint = (short) (tMeltingPoint / tAmountOfComponents);

        tAmountOfComponents *= aDensityMultiplier;
        tAmountOfComponents /= aDensityDivider;
        if (aAspects == null) for (TC_Aspects.TC_AspectStack tAspect : mAspects)
            tAspect.mAmount = Math.max(1, tAspect.mAmount / Math.max(1, tAmountOfComponents));
        else mAspects.addAll(aAspects);
    }

    /**
     * This is for keeping compatibility with addons mods (Such as TinkersGregworks etc) that looped over the old materials enum
     */
    @Deprecated
    public String name() {
        return mName;
    }

    /**
     * This is for keeping compatibility with addons mods (Such as TinkersGregworks etc) that looped over the old materials enum
     */
    @Deprecated
    public static Materials valueOf(String aMaterialName) {
        return getMaterialsMap().get(aMaterialName);
    }

    /**
     * This is for keeping compatibility with addons mods (Such as TinkersGregworks etc) that looped over the old materials enum
     */
    public static Materials[] values() {
        return MATERIALS_ARRAY;
    }

    /**
     * This should only be used for getting a Material by its name as a String. Do not loop over this map, use values().
     */
    public static Map<String, Materials> getMaterialsMap() {
        return MATERIALS_MAP;
    }

    public static Materials get(String aMaterialName) {
        Materials aMaterial = getMaterialsMap().get(aMaterialName);
        if (aMaterial != null) return aMaterial;
        return Materials._NULL;
    }

    public static Materials getRealMaterial(String aMaterialName) {
        return get(aMaterialName).mMaterialInto;
    }

    public boolean isRadioactive() {
        if (mElement != null) return mElement.mHalfLifeSeconds >= 0;
        for (MaterialStack tMaterial : mMaterialList) if (tMaterial.mMaterial.isRadioactive()) return true;
        return false;
    }

    public long getProtons() {
        if (mElement != null) return mElement.getProtons();
        if (mMaterialList.size() <= 0) return Element.Tc.getProtons();
        long rAmount = 0, tAmount = 0;
        for (MaterialStack tMaterial : mMaterialList) {
            tAmount += tMaterial.mAmount;
            rAmount += tMaterial.mAmount * tMaterial.mMaterial.getProtons();
        }
        return (getDensity() * rAmount) / (tAmount * M);
    }

    public long getNeutrons() {
        if (mElement != null) return mElement.getNeutrons();
        if (mMaterialList.size() <= 0) return Element.Tc.getNeutrons();
        long rAmount = 0, tAmount = 0;
        for (MaterialStack tMaterial : mMaterialList) {
            tAmount += tMaterial.mAmount;
            rAmount += tMaterial.mAmount * tMaterial.mMaterial.getNeutrons();
        }
        return (getDensity() * rAmount) / (tAmount * M);
    }

    public long getMass() {
        if (mElement != null) return mElement.getMass();
        if (mMaterialList.size() <= 0) return Element.Tc.getMass();
        long rAmount = 0, tAmount = 0;
        for (MaterialStack tMaterial : mMaterialList) {
            tAmount += tMaterial.mAmount;
            rAmount += tMaterial.mAmount * tMaterial.mMaterial.getMass();
        }
        return (getDensity() * rAmount) / (tAmount * M);
    }

    public long getDensity() {
        return mDensity;
    }

    public String getToolTip() {
        return getToolTip(1, false);
    }

    public String getToolTip(boolean aShowQuestionMarks) {
        return getToolTip(1, aShowQuestionMarks);
    }

    public String getToolTip(long aMultiplier) {
        return getToolTip(aMultiplier, false);
    }

    public String getToolTip(long aMultiplier, boolean aShowQuestionMarks) {
        if (!aShowQuestionMarks && mChemicalFormula.equals("?")) return "";
        if (aMultiplier >= M * 2 && !mMaterialList.isEmpty()) {
            return ((mElement != null || (mMaterialList.size() < 2 && mMaterialList.get(0).mAmount == 1)) ? mChemicalFormula : "(" + mChemicalFormula + ")") + aMultiplier;
        }
        return mChemicalFormula;
    }

    /**
     * Adds a Class implementing IMaterialRegistrator to the master list
     */
    public static boolean add(IMaterialHandler aRegistrator) {
        if (aRegistrator == null) return false;
        return mMaterialHandlers.add(aRegistrator);
    }

    /**
     * Adds an ItemStack to this Material.
     */
    public Materials add(ItemStack aStack) {
        if (aStack != null && !contains(aStack)) mMaterialItems.add(aStack);
        return this;
    }

    /**
     * This is used to determine if any of the ItemStacks belongs to this Material.
     */
    public boolean contains(ItemStack... aStacks) {
        if (aStacks == null || aStacks.length <= 0) return false;
        for (ItemStack tStack : mMaterialItems)
            for (ItemStack aStack : aStacks)
                if (GT_Utility.areStacksEqual(aStack, tStack, !tStack.hasTagCompound())) return true;
        return false;
    }

    /**
     * This is used to determine if an ItemStack belongs to this Material.
     */
    public boolean remove(ItemStack aStack) {
        if (aStack == null) return false;
        boolean temp = false;
        int mMaterialItems_sS=mMaterialItems.size();
        for (int i = 0; i < mMaterialItems_sS; i++)
            if (GT_Utility.areStacksEqual(aStack, mMaterialItems.get(i))) {
                mMaterialItems.remove(i--);
                temp = true;
            }
        return temp;
    }

    /**
     * Adds a SubTag to this Material
     */
    @Override
    public ISubTagContainer add(SubTag... aTags) {
        if (aTags != null) for (SubTag aTag : aTags)
            if (aTag != null && !contains(aTag)) {
                aTag.addContainerToList(this);
                mSubTags.add(aTag);
            }
        return this;
    }

    /**
     * If this Material has this exact SubTag
     */
    @Override
    public boolean contains(SubTag aTag) {
        return mSubTags.contains(aTag);
    }

    /**
     * Removes a SubTag from this Material
     */
    @Override
    public boolean remove(SubTag aTag) {
        return mSubTags.remove(aTag);
    }

    /**
     * Sets the Heat Damage for this Material (negative = frost)
     */
    public Materials setHeatDamage(float aHeatDamage) {
        mHeatDamage = aHeatDamage;
        return this;
    }

    /**
     * Adds a Material to the List of Byproducts when grinding this Ore.
     * Is used for more precise Ore grinding, so that it is possible to choose between certain kinds of Materials.
     */
    public Materials addOreByProduct(Materials aMaterial) {
        if (!mOreByProducts.contains(aMaterial.mMaterialInto)) mOreByProducts.add(aMaterial.mMaterialInto);
        return this;
    }

    /**
     * Adds multiple Materials to the List of Byproducts when grinding this Ore.
     * Is used for more precise Ore grinding, so that it is possible to choose between certain kinds of Materials.
     */
    public Materials addOreByProducts(Materials... aMaterials) {
        for (Materials tMaterial : aMaterials) if (tMaterial != null) addOreByProduct(tMaterial);
        return this;
    }

    /**
     * If this Ore gives multiple drops of its Main Material.
     * Lapis Ore for example gives about 6 drops.
     */
    public Materials setOreMultiplier(int aOreMultiplier) {
        if (aOreMultiplier > 0) mOreMultiplier = aOreMultiplier;
        return this;
    }

    /**
     * If this Ore gives multiple drops of its Byproduct Material.
     */
    public Materials setByProductMultiplier(int aByProductMultiplier) {
        if (aByProductMultiplier > 0) mByProductMultiplier = aByProductMultiplier;
        return this;
    }

    /**
     * If this Ore gives multiple drops of its Main Material.
     * Lapis Ore for example gives about 6 drops.
     */
    public Materials setSmeltingMultiplier(int aSmeltingMultiplier) {
        if (aSmeltingMultiplier > 0) mSmeltingMultiplier = aSmeltingMultiplier;
        return this;
    }

    /**
     * This Ore should be smolten directly into an Ingot of this Material instead of an Ingot of itself.
     */
    public Materials setDirectSmelting(Materials aMaterial) {
        if (aMaterial != null) mDirectSmelting = aMaterial.mMaterialInto.mDirectSmelting;
        return this;
    }

    /**
     * This Material should be the Main Material this Ore gets ground into.
     * Example, Chromite giving Chrome or Tungstate giving Tungsten.
     */
    public Materials setOreReplacement(Materials aMaterial) {
        if (aMaterial != null) mOreReplacement = aMaterial.mMaterialInto.mOreReplacement;
        return this;
    }

    /**
     * This Material smelts always into an instance of aMaterial. Used for Magnets.
     */
    public Materials setSmeltingInto(Materials aMaterial) {
        if (aMaterial != null) mSmeltInto = aMaterial.mMaterialInto.mSmeltInto;
        return this;
    }

    /**
     * This Material arc smelts always into an instance of aMaterial. Used for Wrought Iron.
     */
    public Materials setArcSmeltingInto(Materials aMaterial) {
        if (aMaterial != null) mArcSmeltInto = aMaterial.mMaterialInto.mArcSmeltInto;
        return this;
    }

    /**
     * This Material macerates always into an instance of aMaterial.
     */
    public Materials setMaceratingInto(Materials aMaterial) {
        if (aMaterial != null) mMacerateInto = aMaterial.mMaterialInto.mMacerateInto;
        return this;
    }

    public Materials setEnchantmentForTools(Enchantment aEnchantment, int aEnchantmentLevel) {
        mEnchantmentTools = aEnchantment;
        mEnchantmentToolsLevel = (byte) aEnchantmentLevel;
        return this;
    }

    public Materials setEnchantmentForArmors(Enchantment aEnchantment, int aEnchantmentLevel) {
        mEnchantmentArmors = aEnchantment;
        mEnchantmentArmorsLevel = (byte) aEnchantmentLevel;
        return this;
    }

    public FluidStack getSolid(long aAmount) {
        if (mSolid == null) return null;
        return new GT_FluidStack(mSolid, (int) aAmount);
    }

    public FluidStack getFluid(long aAmount) {
        if (mFluid == null) return null;
        return new GT_FluidStack(mFluid, (int) aAmount);
    }

    public FluidStack getGas(long aAmount) {
        if (mGas == null) return null;
        return new GT_FluidStack(mGas, (int) aAmount);
    }

    public FluidStack getPlasma(long aAmount) {
        if (mPlasma == null) return null;
        return new GT_FluidStack(mPlasma, (int) aAmount);
    }

    public FluidStack getMolten(long aAmount) {
        if (mStandardMoltenFluid == null) return null;
        return new GT_FluidStack(mStandardMoltenFluid, (int) aAmount);
    }

    @Override
    public short[] getRGBA() {
        return mRGBa;
    }

    @Override
    public String toString() {
        return this.mName;
    }

    public static volatile int VERSION = 509;
}