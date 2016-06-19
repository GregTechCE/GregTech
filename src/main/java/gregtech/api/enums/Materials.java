package gregtech.api.enums;

import cpw.mods.fml.common.Loader;
import gregtech.api.GregTech_API;
import gregtech.api.enums.TC_Aspects.TC_AspectStack;
import gregtech.api.interfaces.IColorModulationContainer;
import gregtech.api.interfaces.ISubTagContainer;
import gregtech.api.objects.GT_FluidStack;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_Config;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

import static gregtech.api.enums.GT_Values.M;
import static gregtech.api.enums.GT_Values.MOD_ID_TC;

/**
 * This List contains every Material I know about, and is used to determine Recipes for the
 */
public enum Materials implements IColorModulationContainer, ISubTagContainer {
	/**
	 * This is the Default Material returned in case no Material has been found or a NullPointer has been inserted at a location where it shouldn't happen.
	 * 
	 * Mainly for preventing NullPointer Exceptions and providing Default Values.
	 */
	_NULL				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"NULL"							,    0,       0,          0,    0, false, false,   1,   1,   1, Dyes._NULL			, Element._NULL		, Arrays.asList(new TC_AspectStack(TC_Aspects.VACUOS, 1))),
	
	/**
	 * Direct Elements
	 */
	Aluminium			(  19, TextureSet.SET_DULL				,  10.0F,    128,  2, 1|2  |8   |32|64|128                  , 128, 200, 240,   0,	"Aluminium"						,    0,       0,        933, 1700, true, false,   3,   1,   1, Dyes.dyeLightBlue	, Element.Al		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VOLATUS, 1))),
	Americium			( 103, TextureSet.SET_METALLIC			,   1.0F,      0,  3, 1|2  |8   |32                         , 200, 200, 200,   0,	"Americium"						,    0,       0,       1449,    0, false, false,   3,   1,   1, Dyes.dyeLightGray	, Element.Am		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Antimony			(  58, TextureSet.SET_SHINY				,   1.0F,      0,  2, 1|2  |8   |32                         , 220, 220, 240,   0,	"Antimony"						,    0,       0,        903,    0, false, false,   2,   1,   1, Dyes.dyeLightGray	, Element.Sb		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.AQUA, 1))),
	Argon				(  24, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         ,   0, 255,   0, 240,	"Argon"							,    0,       0,         83,    0, false, true,   5,   1,   1, Dyes.dyeGreen		, Element.Ar		, Arrays.asList(new TC_AspectStack(TC_Aspects.AER, 2))),
	Arsenic				(  39, TextureSet.SET_DULL				,   1.0F,      0,  2, 1|2  |8|16|32                         , 255, 255, 255,   0,	"Arsenic"						,    0,       0,       1090,    0, false, false,   3,   1,   1, Dyes.dyeOrange		, Element.As		, Arrays.asList(new TC_AspectStack(TC_Aspects.VENENUM, 3))),
	Barium				(  63, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8   |32                         , 255, 255, 255,   0,	"Barium"						,    0,       0,       1000,    0, false, false,   1,   1,   1, Dyes._NULL			, Element.Ba		, Arrays.asList(new TC_AspectStack(TC_Aspects.VINCULUM, 3))),
	Beryllium			(   8, TextureSet.SET_METALLIC			,  14.0F,     64,  2, 1|2  |8   |32|64                      , 100, 180, 100,   0,	"Beryllium"						,    0,       0,       1560,    0, false, false,   6,   1,   1, Dyes.dyeGreen		, Element.Be		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 1))),
	Bismuth				(  90, TextureSet.SET_METALLIC			,   6.0F,     64,  1, 1|2  |8   |32|64|128                  , 100, 160, 160,   0,	"Bismuth"						,    0,       0,        544,    0, false, false,   2,   1,   1, Dyes.dyeCyan		, Element.Bi		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1))),
	Boron				(   9, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8   |32                         , 250, 250, 250,   0,	"Boron"							,    0,       0,       2349,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, Element.B			, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 3))),
	Caesium				(  62, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Caesium"						,    0,       0,        301,    0, false, false,   4,   1,   1, Dyes._NULL			, Element.Cs		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Calcium				(  26, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1         |32                         , 255, 245, 245,   0,	"Calcium"						,    0,       0,       1115,    0, false, false,   4,   1,   1, Dyes.dyePink		, Element.Ca		, Arrays.asList(new TC_AspectStack(TC_Aspects.SANO, 1), new TC_AspectStack(TC_Aspects.TUTAMEN, 1))),
	Carbon				(  10, TextureSet.SET_DULL				,   1.0F,     64,  2, 1|2       |32|64|128                  ,  20,  20,  20,   0,	"Carbon"						,    0,       0,       3800,    0, false, false,   2,   1,   1, Dyes.dyeBlack		, Element.C			, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.IGNIS, 1))),
	Cadmium				(  55, TextureSet.SET_SHINY				,   1.0F,      0,  2, 1    |8   |32                         ,  50,  50,  60,   0,	"Cadmium"						,    0,       0,        594,    0, false, false,   3,   1,   1, Dyes.dyeGray		, Element.Cd		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VENENUM, 1))),
	Cerium				(  65, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Cerium"						,    0,       0,       1068, 1068, true, false,   4,   1,   1, Dyes._NULL			, Element.Ce		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Chlorine			(  23, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         , 255, 255, 255,   0,	"Chlorine"						,    0,       0,        171,    0, false, false,   2,   1,   1, Dyes.dyeCyan		, Element.Cl		, Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.PANNUS, 1))),
	Chrome				(  30, TextureSet.SET_SHINY				,  11.0F,    256,  3, 1|2  |8   |32|64|128                  , 255, 230, 230,   0,	"Chrome"						,    0,       0,       2180, 1700, true, false,   5,   1,   1, Dyes.dyePink		, Element.Cr		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1))),
	Cobalt				(  33, TextureSet.SET_METALLIC			,   8.0F,    512,  3, 1|2  |8   |32|64                      ,  80,  80, 250,   0,	"Cobalt"						,    0,       0,       1768,    0, false, false,   3,   1,   1, Dyes.dyeBlue		, Element.Co		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1))),
	Copper				(  35, TextureSet.SET_SHINY				,   1.0F,      0,  1, 1|2  |8   |32   |128                  , 255, 100,   0,   0,	"Copper"						,    0,       0,       1357,    0, false, false,   3,   1,   1, Dyes.dyeOrange		, Element.Cu		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PERMUTATIO, 1))),
	Deuterium			(   2, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         , 255, 255,   0, 240,	"Deuterium"						,    0,       0,         14,    0, false, true,  10,   1,   1, Dyes.dyeYellow		, Element.D			, Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 3))),
	Dysprosium			(  73, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Dysprosium"					,    0,       0,       1680, 1680, true, false,   4,   1,   1, Dyes._NULL			, Element.Dy		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 3))),
	Empty				(   0, TextureSet.SET_NONE				,   1.0F,      0,  2, 256/*Only for Prefixes which need it*/, 255, 255, 255, 255,	"Empty"							,    0,       0,         -1,    0, false, true,   1,   1,   1, Dyes._NULL			, Element._NULL		, Arrays.asList(new TC_AspectStack(TC_Aspects.VACUOS, 2))),
	Erbium				(  75, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Erbium"						,    0,       0,       1802, 1802, true, false,   4,   1,   1, Dyes._NULL			, Element.Er		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Europium			(  70, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Europium"						,    0,       0,       1099, 1099, true, false,   4,   1,   1, Dyes._NULL			, Element.Eu		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Fluorine			(  14, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         , 255, 255, 255, 127,	"Fluorine"						,    0,       0,         53,    0, false, true,   2,   1,   1, Dyes.dyeGreen		, Element.F			, Arrays.asList(new TC_AspectStack(TC_Aspects.PERDITIO, 2))),
	Gadolinium			(  71, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Gadolinium"					,    0,       0,       1585, 1585, true, false,   4,   1,   1, Dyes._NULL			, Element.Gd		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Gallium				(  37, TextureSet.SET_SHINY				,   1.0F,     64,  2, 1|2  |8   |32                         , 220, 220, 255,   0,	"Gallium"						,    0,       0,        302,    0, false, false,   5,   1,   1, Dyes.dyeLightGray	, Element.Ga		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1))),
	Gold				(  86, TextureSet.SET_SHINY				,  12.0F,     64,  2, 1|2  |8   |32|64|128                  , 255, 255,  30,   0,	"Gold"							,    0,       0,       1337,    0, false, false,   4,   1,   1, Dyes.dyeYellow		, Element.Au		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 2))),
	Holmium				(  74, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Holmium"						,    0,       0,       1734, 1734, true, false,   4,   1,   1, Dyes._NULL			, Element.Ho		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Hydrogen			(   1, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         ,   0,   0, 255, 240,	"Hydrogen"						,    1,      15,         14,    0, false, true,   2,   1,   1, Dyes.dyeBlue		, Element.H			, Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 1))),
	Helium				(   4, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         , 255, 255,   0, 240,	"Helium"						,    0,       0,          1,    0, false, true,   5,   1,   1, Dyes.dyeYellow		, Element.He		, Arrays.asList(new TC_AspectStack(TC_Aspects.AER, 2))),
	Helium_3			(   5, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         , 255, 255,   0, 240,	"Helium-3"						,    0,       0,          1,    0, false, true,  10,   1,   1, Dyes.dyeYellow		, Element.He_3		, Arrays.asList(new TC_AspectStack(TC_Aspects.AER, 3))),
	Indium				(  56, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         ,  64,   0, 128,   0,	"Indium"						,    0,       0,        429,    0, false, false,   4,   1,   1, Dyes.dyeGray		, Element.In		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Iridium				(  84, TextureSet.SET_DULL				,   6.0F,   2560,  3, 1|2  |8   |32|64|128                  , 240, 240, 245,   0,	"Iridium"						,    0,       0,       2719, 2719, true, false,  10,   1,   1, Dyes.dyeWhite		, Element.Ir		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1))),
	Iron				(  32, TextureSet.SET_METALLIC			,   6.0F,    256,  2, 1|2  |8   |32|64|128                  , 200, 200, 200,   0,	"Iron"							,    0,       0,       1811,    0, false, false,   3,   1,   1, Dyes.dyeLightGray	, Element.Fe		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 3))),
	Lanthanum			(  64, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Lanthanum"						,    0,       0,       1193, 1193, true, false,   4,   1,   1, Dyes._NULL			, Element.La		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Lead				(  89, TextureSet.SET_DULL				,   8.0F,     64,  1, 1|2  |8   |32|64|128                  , 140, 100, 140,   0,	"Lead"							,    0,       0,        600,    0, false, false,   3,   1,   1, Dyes.dyePurple		, Element.Pb		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 1))),
	Lithium				(   6, TextureSet.SET_DULL				,   1.0F,      0,  2, 1|2  |8   |32                         , 225, 220, 255,   0,	"Lithium"						,    0,       0,        454,    0, false, false,   4,   1,   1, Dyes.dyeLightBlue	, Element.Li		, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 2))),
	Lutetium			(  78, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Lutetium"						,    0,       0,       1925, 1925, true, false,   4,   1,   1, Dyes._NULL			, Element.Lu		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Magic				(-128, TextureSet.SET_SHINY				,   8.0F,   5120,  5, 1|2|4|8|16|32|64|128                  , 100,   0, 200,   0,	"Magic"							,    5,      32,       5000,    0, false, false,   7,   1,   1, Dyes.dyePurple		, Element.Ma		, Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 4))),
	Magnesium			(  18, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 200, 200,   0,	"Magnesium"						,    0,       0,        923,    0, false, false,   3,   1,   1, Dyes.dyePink		, Element.Mg		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.SANO, 1))),
	Manganese			(  31, TextureSet.SET_DULL				,   7.0F,    512,  2, 1|2  |8   |32|64                      , 250, 250, 250,   0,	"Manganese"						,    0,       0,       1519,    0, false, false,   3,   1,   1, Dyes.dyeWhite		, Element.Mn		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 3))),
	Mercury				(  87, TextureSet.SET_SHINY				,   1.0F,      0,  0,         16|32                         , 255, 220, 220,   0,	"Mercury"						,    5,      32,        234,    0, false, false,   3,   1,   1, Dyes.dyeLightGray	, Element.Hg		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.VENENUM, 1))),
	Molybdenum			(  48, TextureSet.SET_SHINY				,   7.0F,    512,  2, 1|2  |8   |32|64                      , 180, 180, 220,   0,	"Molybdenum"					,    0,       0,       2896,    0, false, false,   1,   1,   1, Dyes.dyeBlue		, Element.Mo		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1))),
	Neodymium			(  67, TextureSet.SET_METALLIC			,   7.0F,    512,  2, 1|2  |8   |32|64|128                  , 100, 100, 100,   0,	"Neodymium"						,    0,       0,       1297, 1297, true, false,   4,   1,   1, Dyes._NULL			, Element.Nd		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 2))),
	Neutronium			( 129, TextureSet.SET_DULL				,  24.0F, 655360,  6, 1|2  |8   |32|64|128                  , 250, 250, 250,   0,	"Neutronium"					,    0,       0,      10000,    0, false, false,  20,   1,   1, Dyes.dyeWhite		, Element.Nt		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.ALIENIS, 2))),
	Nickel				(  34, TextureSet.SET_METALLIC			,   6.0F,     64,  2, 1|2  |8   |32|64|128                  , 200, 200, 250,   0,	"Nickel"						,    0,       0,       1728,    0, false, false,   4,   1,   1, Dyes.dyeLightBlue	, Element.Ni		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1))),
	Niobium				(  47, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 190, 180, 200,   0,	"Niobium"						,    0,       0,       2750, 2750, true, false,   5,   1,   1, Dyes._NULL			, Element.Nb		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1))),
	Nitrogen			(  12, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         ,   0, 150, 200, 240,	"Nitrogen"						,    0,       0,         63,    0, false, true,   2,   1,   1, Dyes.dyeCyan		, Element.N			, Arrays.asList(new TC_AspectStack(TC_Aspects.AER, 2))),
	Osmium				(  83, TextureSet.SET_METALLIC			,  16.0F,   1280,  4, 1|2  |8   |32|64|128                  ,  50,  50, 255,   0,	"Osmium"						,    0,       0,       3306, 3306, true, false,  10,   1,   1, Dyes.dyeBlue		, Element.Os		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1), new TC_AspectStack(TC_Aspects.NEBRISUM, 1))),
	Oxygen				(  13, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         ,   0, 100, 200, 240,	"Oxygen"						,    0,       0,         54,    0, false, true,   1,   1,   1, Dyes.dyeWhite		, Element.O			, Arrays.asList(new TC_AspectStack(TC_Aspects.AER, 1))),
	Palladium			(  52, TextureSet.SET_SHINY				,   8.0F,    512,  2, 1|2  |8   |32|64|128                  , 128, 128, 128,   0,	"Palladium"						,    0,       0,       1828, 1828, true, false,   4,   1,   1, Dyes.dyeGray		, Element.Pd		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 3))),
	Phosphor			(  21, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8   |32                         , 255, 255,   0,   0,	"Phosphor"						,    0,       0,        317,    0, false, false,   2,   1,   1, Dyes.dyeYellow		, Element.P			, Arrays.asList(new TC_AspectStack(TC_Aspects.IGNIS, 2), new TC_AspectStack(TC_Aspects.POTENTIA, 1))),
	Platinum			(  85, TextureSet.SET_SHINY				,  12.0F,     64,  2, 1|2  |8   |32|64|128                  , 255, 255, 200,   0,	"Platinum"						,    0,       0,       2041,    0, false, false,   6,   1,   1, Dyes.dyeOrange		, Element.Pt		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.NEBRISUM, 1))),
	Plutonium			( 100, TextureSet.SET_METALLIC			,   6.0F,    512,  3, 1|2  |8   |32|64                      , 240,  50,  50,   0,	"Plutonium 244"					,    0,       0,        912,    0, false, false,   6,   1,   1, Dyes.dyeLime		, Element.Pu		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 2))),
	Plutonium241		( 101, TextureSet.SET_SHINY				,   6.0F,    512,  3, 1|2  |8   |32|64                      , 250,  70,  70,   0,	"Plutonium 241"					,    0,       0,        912,    0, false, false,   6,   1,   1, Dyes.dyeLime		, Element.Pu_241	, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 3))),
	Potassium			(  25, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1|2       |32                         , 250, 250, 250,   0,	"Potassium"						,    0,       0,        336,    0, false, false,   2,   1,   1, Dyes.dyeWhite		, Element.K			, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 1))),
	Praseodymium		(  66, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Praseodymium"					,    0,       0,       1208, 1208, true, false,   4,   1,   1, Dyes._NULL			, Element.Pr		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Promethium			(  68, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Promethium"					,    0,       0,       1315, 1315, true, false,   4,   1,   1, Dyes._NULL			, Element.Pm		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Radon				(  93, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         , 255,   0, 255, 240,	"Radon"							,    0,       0,        202,    0, false, true,   5,   1,   1, Dyes.dyePurple		, Element.Rn		, Arrays.asList(new TC_AspectStack(TC_Aspects.AER, 1), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Rubidium			(  43, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 240,  30,  30,   0,	"Rubidium"						,    0,       0,        312,    0, false, false,   4,   1,   1, Dyes.dyeRed			, Element.Rb		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1))),
	Samarium			(  69, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Samarium"						,    0,       0,       1345, 1345, true, false,   4,   1,   1, Dyes._NULL			, Element.Sm		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Scandium			(  27, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Scandium"						,    0,       0,       1814, 1814, true, false,   2,   1,   1, Dyes.dyeYellow		, Element.Sc		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Silicon				(  20, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         ,  60,  60,  80,   0,	"Silicon"						,    0,       0,       1687, 1687, true, false,   1,   1,   1, Dyes.dyeBlack		, Element.Si		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.TENEBRAE, 1))),
	Silver				(  54, TextureSet.SET_SHINY				,  10.0F,     64,  2, 1|2  |8   |32|64|128                  , 220, 220, 255,   0,	"Silver"						,    0,       0,       1234,    0, false, false,   3,   1,   1, Dyes.dyeLightGray	, Element.Ag		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.LUCRUM, 1))),
	Sodium				(  17, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1         |32                         ,   0,   0, 150,   0,	"Sodium"						,    0,       0,        370,    0, false, false,   1,   1,   1, Dyes.dyeBlue		, Element.Na		, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.LUX, 1))),
	Strontium			(  44, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8   |32                         , 200, 200, 200,   0,	"Strontium"						,    0,       0,       1050,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Element.Sr		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.STRONTIO, 1))),
	Sulfur				(  22, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8   |32                         , 200, 200,   0,   0,	"Sulfur"						,    0,       0,        388,    0, false, false,   2,   1,   1, Dyes.dyeYellow		, Element.S			, Arrays.asList(new TC_AspectStack(TC_Aspects.IGNIS, 1))),
	Tantalum			(  80, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Tantalum"						,    0,       0,       3290,    0, false, false,   4,   1,   1, Dyes._NULL			, Element.Ta		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VINCULUM, 1))),
	Tellurium			(  59, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Tellurium"						,    0,       0,        722,    0, false, false,   4,   1,   1, Dyes.dyeGray		, Element.Te		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Terbium				(  72, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Terbium"						,    0,       0,       1629, 1629, true, false,   4,   1,   1, Dyes._NULL			, Element.Tb		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Thorium				(  96, TextureSet.SET_SHINY				,   6.0F,    512,  2, 1|2  |8   |32|64                      ,   0,  30,   0,   0,	"Thorium"						,    0,       0,       2115,    0, false, false,   4,   1,   1, Dyes.dyeBlack		, Element.Th		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Thulium				(  76, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Thulium"						,    0,       0,       1818, 1818, true, false,   4,   1,   1, Dyes._NULL			, Element.Tm		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Tin					(  57, TextureSet.SET_DULL				,   1.0F,      0,  1, 1|2  |8   |32   |128                  , 220, 220, 220,   0,	"Tin"							,    0,       0,        505,  505, false, false,   3,   1,   1, Dyes.dyeWhite		, Element.Sn		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1))),
	Titanium			(  28, TextureSet.SET_METALLIC			,   7.0F,   1600,  3, 1|2  |8   |32|64|128                  , 220, 160, 240,   0,	"Titanium"						,    0,       0,       1941, 1500, true, false,   5,   1,   1, Dyes.dyePurple		, Element.Ti		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.TUTAMEN, 1))),
	Tritium				(   3, TextureSet.SET_METALLIC			,   1.0F,      0,  2,         16|32                         , 255,   0,   0, 240,	"Tritium"						,    0,       0,         14,    0, false, true,  10,   1,   1, Dyes.dyeRed			, Element.T			, Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 4))),
	Tungsten			(  81, TextureSet.SET_METALLIC			,   7.0F,   2560,  3, 1|2  |8   |32|64|128                  ,  50,  50,  50,   0,	"Tungsten"						,    0,       0,       3695, 2500, true, false,   4,   1,   1, Dyes.dyeBlack		, Element.W			, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.TUTAMEN, 1))),
	Uranium				(  98, TextureSet.SET_METALLIC			,   6.0F,    512,  3, 1|2  |8   |32|64                      ,  50, 240,  50,   0,	"Uranium 238"					,    0,       0,       1405,    0, false, false,   4,   1,   1, Dyes.dyeGreen		, Element.U			, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Uranium235			(  97, TextureSet.SET_SHINY				,   6.0F,    512,  3, 1|2  |8   |32|64                      ,  70, 250,  70,   0,	"Uranium 235"					,    0,       0,       1405,    0, false, false,   4,   1,   1, Dyes.dyeGreen		, Element.U_235		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 2))),
	Vanadium			(  29, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         ,  50,  50,  50,   0,	"Vanadium"						,    0,       0,       2183, 2183, true, false,   2,   1,   1, Dyes.dyeBlack		, Element.V			, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Ytterbium			(  77, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 255, 255, 255,   0,	"Ytterbium"						,    0,       0,       1097, 1097, true, false,   4,   1,   1, Dyes._NULL			, Element.Yb		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Yttrium				(  45, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2  |8   |32                         , 220, 250, 220,   0,	"Yttrium"						,    0,       0,       1799, 1799, true, false,   4,   1,   1, Dyes._NULL			, Element.Y			, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.RADIO, 1))),
	Zinc				(  36, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1|2  |8   |32                         , 250, 240, 240,   0,	"Zinc"							,    0,       0,        692,    0, false, false,   2,   1,   1, Dyes.dyeWhite		, Element.Zn		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.SANO, 1))),
	
	/**
	 * The "Random Material" ones.
	 */
	Organic				(  -1, TextureSet.SET_LEAF				,   1.0F,      0,  1, false),
	AnyCopper			(  -1, TextureSet.SET_SHINY				,   1.0F,      0,  3, false),
	AnyBronze			(  -1, TextureSet.SET_SHINY				,   1.0F,      0,  3, false),
	AnyIron				(  -1, TextureSet.SET_SHINY				,   1.0F,      0,  3, false),
	Crystal				(  -1, TextureSet.SET_SHINY				,   1.0F,      0,  3, false),
	Quartz				(  -1, TextureSet.SET_QUARTZ			,   1.0F,      0,  2, false),
	Metal				(  -1, TextureSet.SET_METALLIC			,   1.0F,      0,  2, false),
	Unknown				(  -1, TextureSet.SET_DULL				,   1.0F,      0,  2, false),
	Cobblestone			(  -1, TextureSet.SET_DULL				,   1.0F,      0,  1, false),
	Brick				(  -1, TextureSet.SET_DULL				,   1.0F,      0,  1, false),
	BrickNether			(  -1, TextureSet.SET_DULL				,   1.0F,      0,  1, false),
	
	/**
	 * The "I don't care" Section, everything I don't want to do anything with right now, is right here. Just to make the Material Finder shut up about them.
	 * But I do see potential uses in some of these Materials.
	 */
	TarPitch			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Tar Pitch"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Serpentine			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2  |8                               , 255, 255, 255,   0,	"Serpentine"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Flux				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Flux"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	RedstoneAlloy		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Redstone Alloy"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	OsmiumTetroxide		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Osmium Tetroxide"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	NitricAcid			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 0                                     , 255, 255, 255,   0,	"Nitric Acid"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	RubberTreeSap		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 0                                     , 255, 255, 255,   0,	"Rubber Tree Sap"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	AquaRegia			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 0                                     , 255, 255, 255,   0,	"Aqua Regia"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	SolutionBlueVitriol	(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 0                                     , 255, 255, 255,   0,	"Blue Vitriol Solution"			,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	SolutionNickelSulfate( -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 0                                     , 255, 255, 255,   0,	"Nickel Sulfate Solution"		,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Signalum			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Signalum"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Lumium				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Lumium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	PhasedIron			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Phased Iron"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	PhasedGold			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Phased Gold"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Soularium			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Soularium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Endium				( 770, TextureSet.SET_DULL				,   1.0F,      0,  2, 1|2  |8                               , 165, 220, 250,   0,	"Endium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeYellow		),
 	Prismarine			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1  |4                                 , 255, 255, 255,   0,	"Prismarine"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	GraveyardDirt		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Graveyard Dirt"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	DarkSteel			( 364, TextureSet.SET_DULL				,   8.0F,    512,  3, 1|2  |8      |64                      ,  80,  70,  80,   0,	"Dark Steel"					,    0,       0,       1811,    0, false, false,   5,   1,   1, Dyes.dyePurple		),
	Terrasteel			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Terrasteel"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	ConductiveIron		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Conductive Iron"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	ElectricalSteel		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Electrical Steel"				,    0,       0,       1811, 1000, true, false,   3,   1,   1, Dyes._NULL			),
	EnergeticAlloy		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Energetic Alloy"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	VibrantAlloy		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Vibrant Alloy"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	PulsatingIron		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Pulsating Iron"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Teslatite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     ,  60, 180, 200,   0,	"Teslatite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Fluix				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1  |4                                 , 255, 255, 255,   0,	"Fluix"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Manasteel			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Manasteel"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Tennantite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Tennantite"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	DarkThaumium		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Dark Thaumium"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Alfium				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Alfium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Ryu					(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Ryu"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Mutation			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Mutation"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Aquamarine			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1  |4                                 , 255, 255, 255,   0,	"Aquamarine"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Ender				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Ender"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	ElvenElementium		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Elven Elementium"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	EnrichedCopper		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Enriched Copper"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	DiamondCopper		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Diamond Copper"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	SodiumPeroxide		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Sodium Peroxide"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	IridiumSodiumOxide	(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Iridium Sodium Oxide"			,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	PlatinumGroupSludge	( 241, TextureSet.SET_POWDER			,   1.0F,      0,  2, 1                                     ,   0,  30,   0,   0,	"Platinum Group Sludge"			,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Fairy				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Fairy"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Ludicrite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Ludicrite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Pokefennium			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Pokefennium"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Draconium			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Draconium"					    ,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	DraconiumAwakened	(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Awakened Draconium"			,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	PurpleAlloy			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 0	                                    , 100, 180, 255,   0,	"Purple Alloy"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL	),
	InfusedTeslatite	(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 0	                                    , 100, 180, 255,   0,	"Infused Teslatite"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL	),
	
	/**
	 * Unknown Material Components. Dead End Section.
	 */
	Adamantium			( 319, TextureSet.SET_SHINY				,  10.0F,   5120,  5, 1|2  |8      |64|128                  , 255, 255, 255,   0,	"Adamantium"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	),
	Adamite				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  3, 1    |8                               , 255, 255, 255,   0,	"Adamite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	),
	Adluorite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1    |8                               , 255, 255, 255,   0,	"Adluorite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Agate				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Agate"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Alduorite			( 485, TextureSet.SET_SHINY  			,   1.0F,      0,  2, 1    |8|16                            , 159, 180, 180,   0,	"Alduorite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Amber				( 514, TextureSet.SET_RUBY				,   4.0F,    128,  2, 1  |4|8      |64                      , 255, 128,   0, 127,	"Amber"							,    5,       3,         -1,    0, false, true,   1,   1,   1, Dyes.dyeOrange		, Arrays.asList(new TC_AspectStack(TC_Aspects.VINCULUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 1))),
	Ammonium			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Ammonium"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Amordrine			(  -1, TextureSet.SET_NONE				,   6.0F,     64,  2, 1|2  |8|16      |64                   , 255, 255, 255,   0,	"Amordrine"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Andesite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1    |8                               , 255, 255, 255,   0,	"Andesite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Angmallen			( 958, TextureSet.SET_METALLIC			,  10.0F,    128,  2, 1|2  |8|16   |64                      , 215, 225, 138,   0,	"Angmallen"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Ardite				(  -1, TextureSet.SET_NONE				,   6.0F,     64,  2, 1|2  |8      |64                      , 255,   0,   0,   0,	"Ardite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	Aredrite			(  -1, TextureSet.SET_NONE				,   6.0F,     64,  2, 1|2  |8      |64                      , 255,   0,   0,   0,	"Aredrite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	Atlarus				( 965, TextureSet.SET_METALLIC			,   6.0F,     64,  2, 1|2  |8      |64                      , 255, 255, 255,   0,	"Atlarus"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Bitumen				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1    |8                               , 255, 255, 255,   0,	"Bitumen"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Black				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 0                                     ,   0,   0,   0,   0,	"Black"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeBlack		),
	Blizz				( 851, TextureSet.SET_SHINY				,   1.0F,      0,  2, 1                                     , 220, 233, 255,   0,	"Blizz"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Blueschist			( 852, TextureSet.SET_DULL				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Blueschist"					,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes.dyeLightBlue	),
	Bluestone			( 813, TextureSet.SET_DULL				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Bluestone"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlue		),
	Bloodstone			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Bloodstone"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeRed			),
	Blutonium			(  -1, TextureSet.SET_SHINY				,   1.0F,      0,  2, 1|2  |8                               ,   0,   0, 255,   0,	"Blutonium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeBlue		),
	Carmot				( 962, TextureSet.SET_METALLIC			,  16.0F,    128,  1, 1|2  |8      |64                      , 217, 205, 140,   0,	"Carmot"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Celenegil			( 964, TextureSet.SET_METALLIC			,  10.0F,   4096,  2, 1|2  |8|16      |64                   , 148, 204,  72,   0,	"Celenegil"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	CertusQuartz		( 516, TextureSet.SET_QUARTZ			,   5.0F,     32,  1, 1  |4|8      |64                      , 210, 210, 230,   0,	"Certus Quartz"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VITREUS, 1))),
	Ceruclase			( 952, TextureSet.SET_METALLIC			,   6.0F,   1280,  2, 1|2  |8                               , 140, 189, 208,   0,	"Ceruclase"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Citrine				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Citrine"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	CobaltHexahydrate	( 853, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1      |16                            ,  80,  80, 250,   0,	"Cobalt Hexahydrate"			,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlue		),
	ConstructionFoam	( 854, TextureSet.SET_DULL				,   1.0F,      0,  2, 1      |16                            , 128, 128, 128,   0,	"Construction Foam"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		),
	Chert				( 857, TextureSet.SET_DULL				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Chert"							,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes._NULL			),
	Chimerite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Chimerite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Coral				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1                                     , 255, 128, 255,   0,	"Coral"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	CrudeOil			( 858, TextureSet.SET_DULL				,   1.0F,      0,  2, 1                                     ,  10,  10,  10,   0,	"Crude Oil"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	Chrysocolla			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Chrysocolla"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	CrystalFlux			(  -1, TextureSet.SET_QUARTZ			,   1.0F,      0,  3, 1  |4                                 , 100,  50, 100,   0,	"Flux Crystal"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Cyanite				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Cyanite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeCyan		),
	Dacite				( 859, TextureSet.SET_DULL				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Dacite"						,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes.dyeLightGray	),
	DarkIron			( 342, TextureSet.SET_DULL				,   7.0F,    384,  3, 1|2  |8      |64                      ,  55,  40,  60,   0,	"Dark Iron"						,    0,       0,         -1,    0, false, false,   5,   1,   1, Dyes.dyePurple		),
	DarkStone			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Dark Stone"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeBlack		),
	Demonite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Demonite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeRed			),
	Desh				( 884, TextureSet.SET_DULL				,   1.0F,   1280,  3, 1|2  |8      |64|128                  ,  40,  40,  40,   0,	"Desh"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	Desichalkos			(  -1, TextureSet.SET_NONE				,   6.0F,   1280,  3, 1|2  |8|16      |64                   , 255, 255, 255,   0,	"Desichalkos"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Dilithium			( 515, TextureSet.SET_DIAMOND			,   1.0F,      0,  1, 1  |4|8|16                            , 255, 250, 250, 127,	"Dilithium"						,    0,       0,         -1,    0, false, true,   1,   1,   1, Dyes.dyeWhite		),
	Draconic			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Draconic"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeRed			),
	Drulloy			    (  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|16                                  , 255, 255, 255,   0,	"Drulloy"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeRed			),
	Duranium			( 328, TextureSet.SET_METALLIC			,   8.0F,   1280,  4, 1|2          |64                      , 255, 255, 255,   0,	"Duranium"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	),
	Eclogite			( 860, TextureSet.SET_DULL				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Eclogite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	ElectrumFlux		( 320, TextureSet.SET_SHINY				,  16.0F,    512,  3, 1|2          |64                      , 255, 255, 120,   0,	"Fluxed Electrum"				,    0,       0,       3000, 3000, true, false,   1,   1,   1, Dyes.dyeYellow		),
	Emery				( 861, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 255, 255, 255,   0,	"Emery"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Enderium			( 321, TextureSet.SET_DULL				,   8.0F,    256,  3, 1|2          |64                      ,  89, 145, 135,   0,	"Enderium"						,    0,       0,       3000, 3000, true, false,   1,   1,   1, Dyes.dyeGreen		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ALIENIS, 1))),
	EnderiumBase		(  -1, TextureSet.SET_DULL				,   8.0F,    256,  3, 1|2          |64                      ,  89, 145, 135,   0,	"Enderium Base"					,    0,       0,       3000, 3000, true, false,   1,   1,   1, Dyes.dyeGreen		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ALIENIS, 1))),
	Energized			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 0                                     , 255, 255, 255,   0,	"Energized"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Epidote				( 862, TextureSet.SET_DULL				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Epidote"						,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes._NULL			),
	Eximite				( 959, TextureSet.SET_METALLIC			,   5.0F,   2560,  3, 1|2  |8      |64                      , 124,  90, 150,   0,	"Eximite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	FierySteel			( 346, TextureSet.SET_FIERY				,   8.0F,    256,  3, 1|2    |16   |64|128                  ,  64,   0,   0,   0,	"Fiery Steel"					,    5,    2048,       1811, 1000, true, false,   1,   1,   1, Dyes.dyeRed			, Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 3), new TC_AspectStack(TC_Aspects.CORPUS, 3))),
	Firestone			( 347, TextureSet.SET_QUARTZ			,   6.0F,   1280,  3, 1  |4|8      |64                      , 200,  20,   0,   0,	"Firestone"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeRed			),
	Fluorite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1    |8                               , 255, 255, 255,   0,	"Fluorite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeGreen		),
	FoolsRuby			( 512, TextureSet.SET_RUBY				,   1.0F,      0,  2, 1  |4|8                               , 255, 100, 100, 127,	"Ruby"							,    0,       0,         -1,    0, false, true,   3,   1,   1, Dyes.dyeRed			, Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 2), new TC_AspectStack(TC_Aspects.VITREUS, 2))),
	Force				( 521, TextureSet.SET_DIAMOND			,  10.0F,    128,  3, 1|2|4|8      |64|128                  , 255, 255,   0,   0,	"Force"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeYellow		, Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 5))),
	Forcicium			( 518, TextureSet.SET_DIAMOND			,   1.0F,      0,  1, 1  |4|8|16                            ,  50,  50,  70,   0,	"Forcicium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeGreen		, Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 2))),
	Forcillium			( 519, TextureSet.SET_DIAMOND			,   1.0F,      0,  1, 1  |4|8|16                            ,  50,  50,  70,   0,	"Forcillium"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeGreen		, Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 2))),
	Gabbro				( 863, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Gabbro"						,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes._NULL			),
	Glowstone			( 811, TextureSet.SET_SHINY				,   1.0F,      0,  1, 1      |16                            , 255, 255,   0,   0,	"Glowstone"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		, Arrays.asList(new TC_AspectStack(TC_Aspects.LUX, 2), new TC_AspectStack(TC_Aspects.SENSUS, 1))),
	Gneiss				( 864, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Gneiss"						,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes._NULL			),
	Graphite			( 865, TextureSet.SET_DULL				,   5.0F,     32,  2, 1    |8|16   |64                      , 128, 128, 128,   0,	"Graphite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeGray		, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1))),
	Graphene			( 819, TextureSet.SET_DULL				,   6.0F,     32,  1, 1            |64                      , 128, 128, 128,   0,	"Graphene"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeGray		, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.ELECTRUM, 1))),
	Greenschist			( 866, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Green Schist"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGreen		),
	Greenstone			( 867, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Greenstone"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGreen		),
	Greywacke			( 868, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Greywacke"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		),
	Haderoth			( 963, TextureSet.SET_METALLIC			,  10.0F,   3200,  3, 1|2  |8|16      |64                   , 119,  52,  30,   0,	"Haderoth"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Hematite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2  |8                               , 255, 255, 255,   0,	"Hematite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
 	Hepatizon			( 957, TextureSet.SET_METALLIC			,  12.0F,    128,  2, 1|2  |8|16      |64                   , 117,  94, 117,   0,	"Hepatizon"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	HSLA				( 322, TextureSet.SET_METALLIC			,   6.0F,    500,  2, 1|2          |64|128                  , 128, 128, 128,   0,	"HSLA Steel"					,    0,       0,       1811, 1000, true, false,   3,   1,   1, Dyes._NULL			, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.ORDO, 1))),
 	Ignatius			( 950, TextureSet.SET_METALLIC			,  12.0F,    512,  2, 1|2    |16                            , 255, 169,  83,   0,	"Ignatius"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Infernal			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 0                                     , 255, 255, 255,   0,	"Infernal"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Infuscolium			( 490, TextureSet.SET_METALLIC			,   6.0F,     64,  2, 1|2  |8|16   |64                      , 146,  33,  86,   0,	"Infuscolium"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
 	InfusedGold			( 323, TextureSet.SET_SHINY				,  12.0F,     64,  3, 1|2  |8      |64|128                  , 255, 200,  60,   0,	"Infused Gold"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeYellow		),
	InfusedAir			( 540, TextureSet.SET_SHARDS			,   8.0F,     64,  3, 1  |4|8      |64|128                  , 255, 255,   0,   0,	"Aer"							,    5,     160,         -1,    0, false, true,   3,   1,   1, Dyes.dyeYellow		, Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AER, 2))),
	InfusedFire			( 541, TextureSet.SET_SHARDS			,   8.0F,     64,  3, 1  |4|8      |64|128                  , 255,   0,   0,   0,	"Ignis"							,    5,     320,         -1,    0, false, true,   3,   1,   1, Dyes.dyeRed			, Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.IGNIS, 2))),
	InfusedEarth		( 542, TextureSet.SET_SHARDS			,   8.0F,    256,  3, 1  |4|8      |64|128                  ,   0, 255,   0,   0,	"Terra"							,    5,     160,         -1,    0, false, true,   3,   1,   1, Dyes.dyeGreen		, Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.TERRA, 2))),
	InfusedWater		( 543, TextureSet.SET_SHARDS			,   8.0F,     64,  3, 1  |4|8      |64|128                  ,   0,   0, 255,   0,	"Aqua"							,    5,     160,         -1,    0, false, true,   3,   1,   1, Dyes.dyeBlue		, Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AQUA, 2))),
	InfusedEntropy		( 544, TextureSet.SET_SHARDS			,  32.0F,     64,  4, 1  |4|8      |64|128                  ,  62,  62,  62,   0,	"Perditio"						,    5,     320,         -1,    0, false, true,   3,   1,   1, Dyes.dyeBlack		, Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.PERDITIO, 2))),
	InfusedOrder		( 545, TextureSet.SET_SHARDS			,   8.0F,     64,  3, 1  |4|8      |64|128                  , 252, 252, 252,   0,	"Ordo"							,    5,     240,         -1,    0, false, true,   3,   1,   1, Dyes.dyeWhite		, Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.ORDO, 2))),
	InfusedVis			(  -1, TextureSet.SET_SHARDS			,   8.0F,     64,  3, 1  |4|8      |64|128                  , 255,   0, 255,   0,	"Auram"							,    5,     240,         -1,    0, false, true,   3,   1,   1, Dyes.dyePurple		, Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.AURAM, 2))),
	InfusedDull			(  -1, TextureSet.SET_SHARDS			,  32.0F,     64,  3, 1  |4|8      |64|128                  , 100, 100, 100,   0,	"Vacuus"						,    5,     160,         -1,    0, false, true,   3,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1), new TC_AspectStack(TC_Aspects.VACUOS, 2))),
	Inolashite			( 954, TextureSet.SET_NONE				,   8.0F,   2304,  3, 1|2  |8|16      |64                   , 148, 216, 187,   0,	"Inolashite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Invisium			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1                                     , 255, 255, 255,   0,	"Invisium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Jade				( 537, TextureSet.SET_SHINY				,   1.0F,      0,  2, 1    |8                               ,   0, 100,   0,   0,	"Jade"							,    0,       0,         -1,    0, false, false,   5,   1,   1, Dyes.dyeGreen		, Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 3))),
	Jasper				( 511, TextureSet.SET_EMERALD			,   1.0F,      0,  2, 1  |4|8                               , 200,  80,  80, 100,	"Jasper"						,    0,       0,         -1,    0, false, true,   3,   1,   1, Dyes.dyeRed			, Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 2))),
	Kalendrite			( 953, TextureSet.SET_METALLIC			,   5.0F,   2560,  3, 1|2    |16                            , 170,  91, 189,   0,	"Kalendrite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Komatiite			( 869, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Komatiite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	Lava				( 700, TextureSet.SET_FLUID				,   1.0F,      0,  1,         16                            , 255,  64,   0,   0,	"Lava"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		),
	Lemurite			( 486, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |16                              , 219, 219, 219,   0,	"Lemurite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Limestone			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Limestone"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Lodestone			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1    |8                               , 255, 255, 255,   0,	"Lodestone"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Luminite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1    |8                               , 250, 250, 250,   0,	"Luminite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeWhite		),
	Magma				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255,  64,   0,   0,	"Magma"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		),
	Mawsitsit			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Mawsitsit"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Mercassium			(  -1, TextureSet.SET_NONE				,   6.0F,     64,  1, 1|2  |8      |64                      , 255, 255, 255,   0,	"Mercassium"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	MeteoricIron		( 340, TextureSet.SET_METALLIC			,   6.0F,    384,  2, 1|2  |8      |64                      , 100,  50,  80,   0,	"Meteoric Iron"					,    0,       0,       1811,    0, false, false,   1,   1,   1, Dyes.dyeGray		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1))),
	MeteoricSteel		( 341, TextureSet.SET_METALLIC			,   6.0F,    768,  2, 1|2          |64                      ,  50,  25,  40,   0,	"Meteoric Steel"				,    0,       0,       1811, 1000, true, false,   1,   1,   1, Dyes.dyeGray		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1), new TC_AspectStack(TC_Aspects.ORDO, 1))),
	Meteorite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1    |8                               ,  80,  35,  60,   0,	"Meteorite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePurple		),
	Meutoite			( 487, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1    |8|16                            ,  95,  82, 105,   0,	"Meutoite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Migmatite			( 872, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Migmatite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Mimichite			(  -1, TextureSet.SET_GEM_VERTICAL		,   1.0F,      0,  1, 1  |4|8                               , 255, 255, 255,   0,	"Mimichite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Moonstone			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1    |8                               , 255, 255, 255,   0,	"Moonstone"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeWhite		, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.ALIENIS, 1))),
	Naquadah			( 324, TextureSet.SET_METALLIC			,   6.0F,   1280,  4, 1|2  |8|16   |64                      ,  50,  50,  50,   0,	"Naquadah"						,    0,       0,       3000, 3000, true, false,  10,   1,   1, Dyes.dyeBlack		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.RADIO, 1), new TC_AspectStack(TC_Aspects.NEBRISUM, 1))),
	NaquadahAlloy		( 325, TextureSet.SET_METALLIC			,   8.0F,   5120,  5, 1|2          |64|128                  ,  40,  40,  40,   0,	"Naquadah Alloy"				,    0,       0,       3000, 3000, true, false,  10,   1,   1, Dyes.dyeBlack		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.NEBRISUM, 1))),
	NaquadahEnriched	( 326, TextureSet.SET_METALLIC			,   6.0F,   1280,  4, 1|2  |8|16   |64                      ,  50,  50,  50,   0,	"Enriched Naquadah"				,    0,       0,       3000, 3000, true, false,  15,   1,   1, Dyes.dyeBlack		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 3), new TC_AspectStack(TC_Aspects.RADIO, 2), new TC_AspectStack(TC_Aspects.NEBRISUM, 2))),
	Naquadria			( 327, TextureSet.SET_SHINY				,   1.0F,    512,  4, 1|2  |8      |64                      ,  30,  30,  30,   0,	"Naquadria"						,    0,       0,       3000, 3000, true, false,  20,   1,   1, Dyes.dyeBlack		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 4), new TC_AspectStack(TC_Aspects.RADIO, 3), new TC_AspectStack(TC_Aspects.NEBRISUM, 3))),
	Nether				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255, 255, 255,   0,	"Nether"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	NetherBrick			( 814, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 100,   0,   0,   0,	"Nether Brick"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeRed			, Arrays.asList(new TC_AspectStack(TC_Aspects.IGNIS, 1))),
	NetherQuartz		( 522, TextureSet.SET_QUARTZ			,   1.0F,     32,  1, 1  |4|8      |64                      , 230, 210, 210,   0,	"Nether Quartz"					,    0,       0,         -1,    0, false, false,   2,   1,   1, Dyes.dyeWhite		, Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 1), new TC_AspectStack(TC_Aspects.VITREUS, 1))),
	NetherStar			( 506, TextureSet.SET_NETHERSTAR		,   1.0F,   5120,  4, 1  |4        |64                      , 255, 255, 255,   0,	"Nether Star"					,    5,   50000,         -1,    0, false, false,  15,   1,   1, Dyes.dyeWhite		),
	Nikolite			( 812, TextureSet.SET_SHINY				,   1.0F,      0,  1, 1    |8                               ,  60, 180, 200,   0,	"Nikolite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeCyan		, Arrays.asList(new TC_AspectStack(TC_Aspects.ELECTRUM, 2))),
	ObsidianFlux		(  -1, TextureSet.SET_DULL				,   1.0F,      0,  1, 1|2                                   ,  80,  50, 100,   0,	"Fluxed Obsidian"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePurple		),
	Oilsands			( 878, TextureSet.SET_NONE				,   1.0F,      0,  1, 1    |8                               ,  10,  10,  10,   0,	"Oilsands"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Onyx				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Onyx"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Orichalcum			( 966, TextureSet.SET_METALLIC			,   4.5F,   3456,  3, 1|2  |8      |64                      ,  84, 122,  56,   0,	"Orichalcum"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Osmonium			(  -1, TextureSet.SET_NONE				,   6.0F,     64,  1, 1|2  |8      |64                      , 255, 255, 255,   0,	"Osmonium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeBlue		),
 	Oureclase			( 961, TextureSet.SET_METALLIC			,   6.0F,   1920,  3, 1|2  |8      |64                      , 183,  98,  21,   0,	"Oureclase"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Painite				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255, 255, 255,   0,	"Painite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Peanutwood			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255, 255, 255,   0,	"Peanut Wood"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Petroleum			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1    |8                               , 255, 255, 255,   0,	"Petroleum"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Pewter				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255, 255, 255,   0,	"Pewter"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Phoenixite			(  -1, TextureSet.SET_NONE				,   6.0F,     64,  1, 1|2  |8      |64                      , 255, 255, 255,   0,	"Phoenixite"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Potash				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255, 255, 255,   0,	"Potash"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Prometheum			( 960, TextureSet.SET_METALLIC			,   8.0F,    512,  1, 1|2  |8      |64                      ,  90, 129,  86,   0,	"Prometheum"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Quartzite			( 523, TextureSet.SET_QUARTZ			,   1.0F,      0,  1, 1  |4|8                               , 210, 230, 210,   0,	"Quartzite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeWhite		),
	Quicklime			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Quicklime"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Randomite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1    |8                               , 255, 255, 255,   0,	"Randomite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
//	RefinedGlowstone	(-326, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1|2                                   , 255, 255,   0,   0,	"Refined Glowstone"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
//	RefinedObsidian		(-327, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1|2                                   ,  80,  50, 100,   0,	"Refined Obsidian"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePurple		),
	Rhyolite			( 875, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Rhyolite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Rubracium			( 488, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1    |8|16                            , 151,  45,  45,   0,	"Rubracium"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
// 	RyuDragonRyder		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255, 255, 255,   0,	"Ryu Dragon Ryder"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Sand				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255, 255, 255,   0,	"Sand"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	Sanguinite			( 955, TextureSet.SET_METALLIC			,   3.0F,   4480,  4, 1|2  |8                               , 185,   0,   0,   0,	"Sanguinite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Siltstone			( 876, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Siltstone"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
	Spinel				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255, 255, 255,   0,	"Spinel"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Starconium			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1|2  |8                               , 255, 255, 255,   0,	"Starconium"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Sugilite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Sugilite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Sunstone			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1    |8                               , 255, 255, 255,   0,	"Sunstone"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeYellow		, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.ALIENIS, 1))),
	Tar					(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     ,  10,  10,  10,   0,	"Tar"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	Tartarite			( 956, TextureSet.SET_METALLIC			,  20.0F,   7680,  5, 1|2  |8|16                            , 255, 118,  60,   0,	"Tartarite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Tapazite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Tapazite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeGreen		),
	Thyrium				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1|2  |8                               , 255, 255, 255,   0,	"Thyrium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Tourmaline			(  -1, TextureSet.SET_RUBY				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Tourmaline"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	Tritanium			( 329, TextureSet.SET_METALLIC			,   6.0F,   2560,  4, 1|2          |64                      , 255, 255, 255,   0,	"Tritanium"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 2))),
	Turquoise			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Turquoise"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes._NULL			),
	UUAmplifier			( 721, TextureSet.SET_FLUID				,   1.0F,      0,  1,         16                            ,  96,   0, 128,   0,	"UU-Amplifier"					,    0,       0,         -1,    0, false, false,  10,   1,   1, Dyes.dyePink		),
	UUMatter			( 703, TextureSet.SET_FLUID				,   1.0F,      0,  1,         16                            , 128,   0, 196,   0,	"UU-Matter"						,    0,       0,         -1,    0, false, false,  10,   1,   1, Dyes.dyePink		),
	Void				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255, 255, 255, 200,	"Void"							,    0,       0,         -1,    0, false, true,   1,   1,   1, Dyes._NULL			, Arrays.asList(new TC_AspectStack(TC_Aspects.VACUOS, 1))),
	Voidstone			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  1, 0                                     , 255, 255, 255, 200,	"Voidstone"						,    0,       0,         -1,    0, false, true,   1,   1,   1, Dyes._NULL			, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.VACUOS, 1))),
	Vulcanite			( 489, TextureSet.SET_METALLIC			,   6.0F,     64,  2, 1|2  |8|16   |64                      , 255, 132,  72,   0,	"Vulcanite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Vyroxeres			( 951, TextureSet.SET_METALLIC			,   9.0F,    768,  3, 1|2  |8      |64                      ,  85, 224,   1,   0,	"Vyroxeres"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			),
 	Wimalite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2,       8                               , 255, 255, 255,   0,	"Wimalite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeYellow		),
	Yellorite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2,       8                               , 255, 255, 255,   0,	"Yellorite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeYellow		),
	Yellorium			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2                                   , 255, 255, 255,   0,	"Yellorium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeYellow		),
	Zectium				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  2, 1|2  |8                               , 255, 255, 255,   0,	"Zectium"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeBlack		),
	
	/**
	 * Circuitry, Batteries and other Technical things
	 */
	Primitive			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Primitive"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 1))),
	Basic				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Basic"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 2))),
	Good				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Good"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 3))),
	Advanced			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Advanced"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 4))),
	Data				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Data"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 5))),
	Elite				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Elite"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 6))),
	Master				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Master"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 7))),
	Ultimate			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Ultimate"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 8))),
	Superconductor		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Superconductor"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.ELECTRUM, 8))),
	Infinite			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Infinite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	),
	
	/**
	 * Not possible to determine exact Components
	 */
	Antimatter			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Antimatter"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePink		, Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 9), new TC_AspectStack(TC_Aspects.PERFODIO, 8))),
	BioFuel				( 705, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 128,   0,   0,	"Biofuel"						,    0,       6,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		),
	Biomass				( 704, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            ,   0, 255,   0,   0,	"Biomass"						,    3,       8,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGreen		),
	Cheese				( 894, TextureSet.SET_FINE				,   1.0F,      0,  0, 1    |8                               , 255, 255,   0,   0,	"Cheese"						,    0,       0,        320,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	Chili				( 895, TextureSet.SET_FINE				,   1.0F,      0,  0, 1                                     , 200,   0,   0,   0,	"Chili"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeRed			),
	Chocolate			( 886, TextureSet.SET_FINE				,   1.0F,      0,  0, 1                                     , 190,  95,   0,   0,	"Chocolate"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		),
	Cluster				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255, 127,	"Cluster"						,    0,       0,         -1,    0, false, true,   1,   1,   1, Dyes.dyeWhite		),
	CoalFuel			( 710, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            ,  50,  50,  70,   0,	"Coalfuel"						,    0,      16,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	Cocoa				( 887, TextureSet.SET_FINE				,   1.0F,      0,  0, 1                                     , 190,  95,   0,   0,	"Cocoa"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		),
	Coffee				( 888, TextureSet.SET_FINE				,   1.0F,      0,  0, 1                                     , 150,  75,   0,   0,	"Coffee"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		),
	Creosote			( 712, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 128,  64,   0,   0,	"Creosote"						,    3,       8,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		),
	Ethanol				( 706, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 128,   0,   0,	"Ethanol"						,    0,     128,         -1,    0, false, false,   1,   1,   1, Dyes.dyePurple		),
	FishOil				( 711, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 196,   0,   0,	"Fish Oil"						,    3,       2,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		, Arrays.asList(new TC_AspectStack(TC_Aspects.CORPUS, 2))),
	Fuel				( 708, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 255,   0,   0,	"Diesel"						,    0,     128,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	Glue				( 726, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 200, 196,   0,   0,	"Glue"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, Arrays.asList(new TC_AspectStack(TC_Aspects.LIMUS, 2))),
	Gunpowder			( 800, TextureSet.SET_DULL				,   1.0F,      0,  0, 1                                     , 128, 128, 128,   0,	"Gunpowder"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		, Arrays.asList(new TC_AspectStack(TC_Aspects.PERDITIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 4))),
	FryingOilHot		( 727, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 200, 196,   0,   0,	"Hot Frying Oil"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.IGNIS, 1))),
	Honey				( 725, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 210, 200,   0,   0,	"Honey"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	Leather				(  -1, TextureSet.SET_ROUGH				,   1.0F,      0,  0, 1                                     , 150, 150,  80, 127,	"Leather"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		),
	LimePure			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Pure Lime"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLime		),
	Lubricant			( 724, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 196,   0,   0,	"Lubricant"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1))),
	McGuffium239		( 999, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 200,  50, 150,   0,	"Mc Guffium 239"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePink		, Arrays.asList(new TC_AspectStack(TC_Aspects.ALIENIS, 8), new TC_AspectStack(TC_Aspects.PERMUTATIO, 8), new TC_AspectStack(TC_Aspects.SPIRITUS, 8), new TC_AspectStack(TC_Aspects.AURAM, 8), new TC_AspectStack(TC_Aspects.VITIUM, 8), new TC_AspectStack(TC_Aspects.RADIO, 8), new TC_AspectStack(TC_Aspects.MAGNETO, 8), new TC_AspectStack(TC_Aspects.ELECTRUM, 8), new TC_AspectStack(TC_Aspects.NEBRISUM, 8), new TC_AspectStack(TC_Aspects.STRONTIO, 8))),
	MeatRaw				( 892, TextureSet.SET_FINE				,   1.0F,      0,  0, 1                                     , 255, 100, 100,   0,	"Raw Meat"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePink		),
	MeatCooked			( 893, TextureSet.SET_FINE				,   1.0F,      0,  0, 1                                     , 150,  60,  20,   0,	"Cooked Meat"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePink		),
	Milk				( 885, TextureSet.SET_FINE				,   1.0F,      0,  0, 1      |16                            , 254, 254, 254,   0,	"Milk"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, Arrays.asList(new TC_AspectStack(TC_Aspects.SANO, 2))),
	Mud					(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Mud"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		),
	Oil					( 707, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            ,  10,  10,  10,   0,	"Oil"							,    3,      16,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	Paper				( 879, TextureSet.SET_PAPER				,   1.0F,      0,  0, 1                                     , 250, 250, 250,   0,	"Paper"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, Arrays.asList(new TC_AspectStack(TC_Aspects.COGNITIO, 1))),
	Peat				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Peat"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		, Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2))),
	Quantum				(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Quantum"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		),
	RareEarth			( 891, TextureSet.SET_FINE				,   1.0F,      0,  0, 1                                     , 128, 128, 100,   0,	"Rare Earth"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		, Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 1), new TC_AspectStack(TC_Aspects.LUCRUM, 1))),
	Red					(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255,   0,   0,   0,	"Red"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeRed			),
	Reinforced			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"Reinforced"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		),
	SeedOil				( 713, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 196, 255,   0,   0,	"Seed Oil"						,    3,       2,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLime		, Arrays.asList(new TC_AspectStack(TC_Aspects.GRANUM, 2))),
	SeedOilHemp			( 722, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 196, 255,   0,   0,	"Hemp Seed Oil"					,    3,       2,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLime		, Arrays.asList(new TC_AspectStack(TC_Aspects.GRANUM, 2))),
	SeedOilLin			( 723, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 196, 255,   0,   0,	"Lin Seed Oil"					,    3,       2,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLime		, Arrays.asList(new TC_AspectStack(TC_Aspects.GRANUM, 2))),
	Stone				( 299, TextureSet.SET_ROUGH				,   4.0F,     32,  1, 1            |64|128                  , 205, 205, 205,   0,	"Stone"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, Arrays.asList(new TC_AspectStack(TC_Aspects.TERRA, 1))),
	TNT					(  -1, TextureSet.SET_NONE				,   1.0F,      0,  0, 0                                     , 255, 255, 255,   0,	"TNT"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeRed			, Arrays.asList(new TC_AspectStack(TC_Aspects.PERDITIO, 7), new TC_AspectStack(TC_Aspects.IGNIS, 4))),
	Unstable			(  -1, TextureSet.SET_NONE				,   1.0F,      0,  4, 0                                     , 255, 255, 255, 127,	"Unstable"						,    0,       0,         -1,    0, false, true,   1,   1,   1, Dyes.dyeWhite		, Arrays.asList(new TC_AspectStack(TC_Aspects.PERDITIO, 4))),
	Unstableingot		(  -1, TextureSet.SET_NONE				,   1.0F,      0,  4, 0                                     , 255, 255, 255, 127,	"Unstable"						,    0,       0,         -1,    0, false, true,   1,   1,   1, Dyes.dyeWhite		, Arrays.asList(new TC_AspectStack(TC_Aspects.PERDITIO, 4))),
	Wheat				( 881, TextureSet.SET_POWDER			,   1.0F,      0,  0, 1                                     , 255, 255, 196,   0,	"Wheat"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		, Arrays.asList(new TC_AspectStack(TC_Aspects.MESSIS, 2))),
	
	/**
	 * TODO: This
	 */
	AluminiumBrass		(  -1, TextureSet.SET_METALLIC			,   6.0F,     64,  2, 1|2          |64                      , 255, 255, 255,   0,	"Aluminium Brass"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	Osmiridium			( 317, TextureSet.SET_METALLIC			,   7.0F,   1600,  3, 1|2          |64|128                  , 100, 100, 255,   0,	"Osmiridium"					,    0,       0,       3333, 2500, true,  false,   1,   1,   1, Dyes.dyeLightBlue	, 1, Arrays.asList(new MaterialStack(Iridium, 3), new MaterialStack(Osmium, 1))),
	Sunnarium			( 318, TextureSet.SET_SHINY				,   1.0F,      0,  1, 1|2                                   , 255, 255,   0,   0,	"Sunnarium"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	Endstone			( 808, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 255, 255, 255,   0,	"Endstone"						,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes.dyeYellow		),
	Netherrack			( 807, TextureSet.SET_DULL				,   1.0F,      0,  0, 1                                     , 200,   0,   0,   0,	"Netherrack"					,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes.dyeRed			),
	SoulSand			(  -1, TextureSet.SET_DULL				,   1.0F,      0,  0, 1                                     , 255, 255, 255,   0,	"Soulsand"						,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes.dyeBrown		),
	
	/**
	 * First Degree Compounds
	 */
	Methane				( 715, TextureSet.SET_FLUID				,   1.0F,      0,  1,         16                            , 255, 255, 255,   0,	"Methane"						,    1,      45,         -1,    0, false, false,   3,   1,   1, Dyes.dyeMagenta		, 1, Arrays.asList(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 4))),
	CarbonDioxide		( 497, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         , 169, 208, 245, 240,	"Carbon Dioxide"				,    0,       0,         25,    1, false, true,   1,   1,   1, Dyes.dyeLightBlue	, 1, Arrays.asList(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 2))),
	NobleGases			( 496, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         , 169, 208, 245, 240,	"Noble Gases"					,    0,       0,          4,    0, false, true,   1,   1,   1, Dyes.dyeLightBlue	, 2, Arrays.asList(new MaterialStack(CarbonDioxide,21),new MaterialStack(Helium, 9), new MaterialStack(Methane, 3), new MaterialStack(Deuterium, 1))),
	Air					(  -1, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         , 169, 208, 245, 240,	"Air"							,    0,       0,         -1,    0, false, true,   1,   1,   1, Dyes.dyeLightBlue	, 0, Arrays.asList(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1),new MaterialStack(NobleGases,1))),
	LiquidAir			( 495, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16|32                         , 169, 208, 245, 240,	"Liquid Air"					,    0,       0,          4,    0, false, true,   1,   1,   1, Dyes.dyeLightBlue	, 2, Arrays.asList(new MaterialStack(Nitrogen, 40), new MaterialStack(Oxygen, 11), new MaterialStack(Argon, 1),new MaterialStack(NobleGases,1))),
	Almandine			( 820, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1    |8                               , 255,   0,   0,   0,	"Almandine"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeRed			, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Iron, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12))),
	Andradite			( 821, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1    |8                               , 150, 120,   0,   0,	"Andradite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeYellow		, 1, Arrays.asList(new MaterialStack(Calcium, 3), new MaterialStack(Iron, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12))),
	AnnealedCopper		( 345, TextureSet.SET_SHINY				,   1.0F,      0,  2, 1|2             |128                  , 255, 120,  20,   0,	"Annealed Copper"				,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeOrange		, 2, Arrays.asList(new MaterialStack(Copper, 1))),
	Asbestos			( 946, TextureSet.SET_DULL				,   1.0F,      0,  1, 1    |8                               , 230, 230, 230,   0,	"Asbestos"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9))), // Mg3Si2O5(OH)4
	Ash					( 815, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 150, 150, 150,   0,	"Ashes"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, 2, Arrays.asList(new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.PERDITIO, 1))),
	BandedIron			( 917, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 145,  90,  90,   0,	"Banded Iron"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		, 1, Arrays.asList(new MaterialStack(Iron, 2), new MaterialStack(Oxygen, 3))),
	BatteryAlloy		( 315, TextureSet.SET_DULL				,   1.0F,      0,  1, 1|2                                   , 156, 124, 160,   0,	"Battery Alloy"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePurple		, 2, Arrays.asList(new MaterialStack(Lead, 4), new MaterialStack(Antimony, 1))),
	BlueTopaz			( 513, TextureSet.SET_GEM_HORIZONTAL	,   7.0F,    256,  3, 1  |4|8      |64                      ,   0,   0, 255, 127,	"Blue Topaz"					,    0,       0,         -1,    0, false, true,   3,   1,   1, Dyes.dyeBlue		, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4))),
	Bone				( 806, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     , 250, 250, 250,   0,	"Bone"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 0, Arrays.asList(new MaterialStack(Calcium, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.MORTUUS, 2), new TC_AspectStack(TC_Aspects.CORPUS, 1))),
	Brass				( 301, TextureSet.SET_METALLIC			,   7.0F,     96,  1, 1|2          |64|128                  , 255, 180,   0,   0,	"Brass"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1))),
	Bronze				( 300, TextureSet.SET_METALLIC			,   6.0F,    192,  2, 1|2          |64|128                  , 255, 128,   0,   0,	"Bronze"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, 2, Arrays.asList(new MaterialStack(Tin, 1), new MaterialStack(Copper, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1))),
	BrownLimonite		( 930, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1    |8                               , 200, 100,   0,   0,	"Brown Limonite"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		, 2, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 2))), // FeO(OH)
	Calcite				( 823, TextureSet.SET_DULL				,   1.0F,      0,  1, 1    |8                               , 250, 230, 220,   0,	"Calcite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, 1, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3))),
	Cassiterite			( 824, TextureSet.SET_METALLIC			,   1.0F,      0,  1,       8                               , 220, 220, 220,   0,	"Cassiterite"					,    0,       0,         -1,    0, false, false,   4,   3,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2))),
	CassiteriteSand		( 937, TextureSet.SET_SAND				,   1.0F,      0,  1,       8                               , 220, 220, 220,   0,	"Cassiterite Sand"				,    0,       0,         -1,    0, false, false,   4,   3,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Tin, 1), new MaterialStack(Oxygen, 2))),
	Chalcopyrite		( 855, TextureSet.SET_DULL				,   1.0F,      0,  1, 1    |8                               , 160, 120,  40,   0,	"Chalcopyrite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		, 1, Arrays.asList(new MaterialStack(Copper, 1), new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2))),
	//Chalk				( 856, TextureSet.SET_FINE				,   1.0F,      0,  2, 1                                     , 250, 250, 250,   0,	"Chalk"							,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3))),
	Charcoal			( 536, TextureSet.SET_FINE				,   1.0F,      0,  1, 1  |4                                 , 100,  70,  70,   0,	"Charcoal"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		, 1, Arrays.asList(new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2))),
	Chromite			( 825, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1    |8                               ,  35,  20,  15,   0,	"Chromite"						,    0,       0,       1700, 1700, true, false,   6,   1,   1, Dyes.dyePink		, 1, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Chrome, 2), new MaterialStack(Oxygen, 4))),
	ChromiumDioxide 	( 361, TextureSet.SET_DULL				,  11.0F,    256,  3, 1|2                                   , 230, 200, 200,   0,	"Chromium Dioxide"   			,    0,       0,        650,  650, false, false,   5,   3,   1, Dyes.dyePink 		, 1, Arrays.asList(new MaterialStack(Chrome, 1), new MaterialStack(Oxygen, 2)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MACHINA, 1))),
	Cinnabar			( 826, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1    |8                               , 150,   0,   0,   0,	"Cinnabar"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeBrown		, 2, Arrays.asList(new MaterialStack(Mercury, 1), new MaterialStack(Sulfur, 1))),
	Water				( 701, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            ,   0,   0, 255,   0,	"Water"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlue		, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 2))),
	Clay				( 805, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1                                     , 200, 200, 220,   0,	"Clay"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeLightBlue	, 1, Arrays.asList(new MaterialStack(Sodium, 2), new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2),new MaterialStack(Water,6))),
	Coal				( 535, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1  |4|8	                            ,  70,  70,  70,   0,	"Coal"							,    0,       0,         -1,    0, false, false,   2,   2,   1, Dyes.dyeBlack		, 1, Arrays.asList(new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.POTENTIA, 2), new TC_AspectStack(TC_Aspects.IGNIS, 2))),
	Cobaltite			( 827, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1    |8                               ,  80,  80, 250,   0,	"Cobaltite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeBlue		, 1, Arrays.asList(new MaterialStack(Cobalt, 1), new MaterialStack(Arsenic, 1), new MaterialStack(Sulfur, 1))),
	Cooperite			( 828, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1    |8                               , 255, 255, 200,   0,	"Sheldonite"					,    0,       0,         -1,    0, false, false,   5,   1,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(Platinum, 3), new MaterialStack(Nickel, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Palladium, 1))),
	Cupronickel			( 310, TextureSet.SET_METALLIC			,   6.0F,     64,  1, 1|2          |64                      , 227, 150, 128,   0,	"Cupronickel"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, 2, Arrays.asList(new MaterialStack(Copper, 1), new MaterialStack(Nickel, 1))),
	DarkAsh				( 816, TextureSet.SET_DULL				,   1.0F,      0,  1, 1                                     ,  50,  50,  50,   0,	"Dark Ashes"					,    0,       0,         -1,    0, false, false,   1,   2,   1, Dyes.dyeGray		, 1, Arrays.asList(new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.IGNIS, 1), new TC_AspectStack(TC_Aspects.PERDITIO, 1))),
	DeepIron			( 829, TextureSet.SET_METALLIC			,   6.0F,    384,  2, 1|2  |8      |64                      , 150, 140, 140,   0,	"Deep Iron"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyePink		, 2, Arrays.asList(new MaterialStack(Iron, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1))),
	Diamond				( 500, TextureSet.SET_DIAMOND			,   8.0F,   1280,  3, 1  |4|8      |64|128                  , 200, 255, 255, 127,	"Diamond"						,    0,       0,         -1,    0, false, true,   5,  64,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.LUCRUM, 4))),
	Electrum			( 303, TextureSet.SET_SHINY				,  12.0F,     64,  2, 1|2  |8      |64|128                  , 255, 255, 100,   0,	"Electrum"						,    0,       0,         -1,    0, false, false,   4,   1,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(Silver, 1), new MaterialStack(Gold, 1))),
	Emerald				( 501, TextureSet.SET_EMERALD			,   7.0F,    256,  2, 1  |4|8      |64                      ,  80, 255,  80, 127,	"Emerald"						,    0,       0,         -1,    0, false, true,   5,   1,   1, Dyes.dyeGreen		, 1, Arrays.asList(new MaterialStack(Beryllium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 6), new MaterialStack(Oxygen, 18)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 3), new TC_AspectStack(TC_Aspects.LUCRUM, 5))),
	FreshWater			(  -1, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            ,   0,   0, 255,   0,	"Fresh Water"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlue		, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 2))),
	Galena				( 830, TextureSet.SET_DULL				,   1.0F,      0,  3, 1    |8                               , 100,  60, 100,   0,	"Galena"						,    0,       0,         -1,    0, false, false,   4,   1,   1, Dyes.dyePurple		, 1, Arrays.asList(new MaterialStack(Lead, 3), new MaterialStack(Silver, 3), new MaterialStack(Sulfur, 2))),
	Garnierite			( 906, TextureSet.SET_METALLIC			,   1.0F,      0,  3, 1    |8                               ,  50, 200,  70,   0,	"Garnierite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightBlue	, 1, Arrays.asList(new MaterialStack(Nickel, 1), new MaterialStack(Oxygen, 1))), 
	Glyceryl			( 714, TextureSet.SET_FLUID				,   1.0F,      0,  1,         16                            ,   0, 150, 150,   0,	"Glyceryl Trinitrate"			,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeCyan		, 1, Arrays.asList(new MaterialStack(Carbon, 3), new MaterialStack(Hydrogen, 5), new MaterialStack(Nitrogen, 3), new MaterialStack(Oxygen, 9))),
	GreenSapphire		( 504, TextureSet.SET_GEM_HORIZONTAL	,   7.0F,    256,  2, 1  |4|8      |64                      , 100, 200, 130, 127,	"Green Sapphire"				,    0,       0,         -1,    0, false, true,   5,   1,   1, Dyes.dyeCyan		, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3))),
	Grossular			( 831, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1    |8                               , 200, 100,   0,   0,	"Grossular"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeOrange		, 1, Arrays.asList(new MaterialStack(Calcium, 3), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12))),
	HolyWater			( 729, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            ,   0,   0, 255,   0,	"Holy Water"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlue		, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 2), new TC_AspectStack(TC_Aspects.AURAM, 1))),
	Ice					( 702, TextureSet.SET_SHINY				,   1.0F,      0,  0, 1|      16                            , 200, 200, 255,   0,	"Ice"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlue		, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.GELUM, 2))),
	Ilmenite			( 918, TextureSet.SET_METALLIC			,   1.0F,      0,  3, 1    |8                               ,  70,  55,  50,   0,	"Ilmenite"						,    0,       0,         -1,    0, false, false,   1,   2,   1, Dyes.dyePurple		, 0, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 3))),
	Rutile				( 375, TextureSet.SET_GEM_HORIZONTAL	,   1.0F,      0,  2, 1    |8                               , 212,  13,  92,   0,	"Rutile"						,    0,       0,         -1,    0, false, false,   1,   2,   1, Dyes.dyeRed			, 0, Arrays.asList(new MaterialStack(Titanium, 1), new MaterialStack(Oxygen, 2))),
	Bauxite				( 822, TextureSet.SET_DULL				,   1.0F,      0,  1, 1    |8                               , 200, 100,   0,   0,	"Bauxite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeBrown		, 1, Arrays.asList(new MaterialStack(Rutile, 2), new MaterialStack(Aluminium, 16), new MaterialStack(Hydrogen, 10), new MaterialStack(Oxygen, 11))),
	Titaniumtetrachloride( 376, TextureSet.SET_FLUID			,   1.0F,      0,  2, 16                                    , 212,  13,  92,   0,	"Titaniumtetrachloride"			,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeRed			, 0, Arrays.asList(new MaterialStack(Titanium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Chlorine, 2))),
	Magnesiumchloride	( 377, TextureSet.SET_DULL				,   1.0F,      0,  2, 1|16                                    , 212,  13,  92,   0,	"Magnesiumchloride"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeRed			, 0, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Chlorine, 2))),
	Invar				( 302, TextureSet.SET_METALLIC			,   6.0F,    256,  2, 1|2          |64|128                  , 180, 180, 120,   0,	"Invar"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		, 2, Arrays.asList(new MaterialStack(Iron, 2), new MaterialStack(Nickel, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.GELUM, 1))),
	IronCompressed		(  -1, TextureSet.SET_METALLIC			,   7.0F,     96,  1, 1|2          |64|128                  , 128, 128, 128,   0,	"Compressed Iron"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		, 2, Arrays.asList(new MaterialStack(Iron, 1))),
	Kanthal				( 312, TextureSet.SET_METALLIC			,   6.0F,     64,  2, 1|2          |64                      , 194, 210, 223,   0,	"Kanthal"						,    0,       0,       1800, 1800, true, false,   1,   1,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Chrome, 1))),
	Lazurite			( 524, TextureSet.SET_LAPIS				,   1.0F,      0,  1, 1  |4|8                               , 100, 120, 255,   0,	"Lazurite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeCyan		, 1, Arrays.asList(new MaterialStack(Aluminium, 6), new MaterialStack(Silicon, 6), new MaterialStack(Calcium, 8), new MaterialStack(Sodium, 8))),
	Magnalium			( 313, TextureSet.SET_DULL				,   6.0F,    256,  2, 1|2          |64|128                  , 200, 190, 255,   0,	"Magnalium"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightBlue	, 2, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Aluminium, 2))),
	Magnesite			( 908, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8                               , 250, 250, 180,   0,	"Magnesite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePink		, 1, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 3))),
	Magnetite			( 870, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8                               ,  30,  30,  30,   0,	"Magnetite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		, 1, Arrays.asList(new MaterialStack(Iron, 3), new MaterialStack(Oxygen, 4)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1))),
	Molybdenite			( 942, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8                               ,  25,  25,  25,   0,	"Molybdenite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlue		, 1, Arrays.asList(new MaterialStack(Molybdenum, 1), new MaterialStack(Sulfur, 2))), // MoS2 (also source of Re)
	Nichrome			( 311, TextureSet.SET_METALLIC			,   6.0F,     64,  2, 1|2          |64                      , 205, 206, 246,   0,	"Nichrome"						,    0,       0,       2700, 2700, true, false,   1,   1,   1, Dyes.dyeRed			, 2, Arrays.asList(new MaterialStack(Nickel, 4), new MaterialStack(Chrome, 1))),
	NiobiumNitride		( 359, TextureSet.SET_DULL				,   1.0F,      0,  2, 1|2                                   ,  29,  41,  29,   0,	"Niobium Nitride"				,    0,       0,       2573, 2573, true, false,   1,   1,   1, Dyes.dyeBlack		, 1, Arrays.asList(new MaterialStack(Niobium, 1), new MaterialStack(Nitrogen, 1))), // Anti-Reflective Material
	NiobiumTitanium		( 360, TextureSet.SET_DULL				,   1.0F,      0,  2, 1|2                                   ,  29,  29,  41,   0,	"Niobium-Titanium"				,    0,       0,       2800, 2800, true, false,   1,   1,   1, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Niobium, 1), new MaterialStack(Titanium, 1))),
	NitroCarbon			( 716, TextureSet.SET_FLUID				,   1.0F,      0,  1,         16                            ,   0,  75, 100,   0,	"Nitro-Carbon"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeCyan		, 1, Arrays.asList(new MaterialStack(Nitrogen, 1), new MaterialStack(Carbon, 1))),
	NitrogenDioxide		( 717, TextureSet.SET_FLUID				,   1.0F,      0,  1,         16                            , 100, 175, 255,   0,	"Nitrogen Dioxide"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeCyan		, 1, Arrays.asList(new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 2))),
	Obsidian			( 804, TextureSet.SET_DULL				,   1.0F,      0,  3, 1                                     ,  80,  50, 100,   0,	"Obsidian"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		, 1, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Iron, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 8))),
	Phosphate			( 833, TextureSet.SET_DULL				,   1.0F,      0,  1, 1    |8|16                            , 255, 255,   0,   0,	"Phosphate"						,    0,       0,         -1,    0, false, false,   2,   1,   1, Dyes.dyeYellow		, 1, Arrays.asList(new MaterialStack(Phosphor, 1), new MaterialStack(Oxygen, 4))),
	PigIron				( 307, TextureSet.SET_METALLIC			,   6.0F,    384,  2, 1|2  |8      |64                      , 200, 180, 180,   0,	"Pig Iron"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyePink		, 2, Arrays.asList(new MaterialStack(Iron, 1))),
	Plastic				( 874, TextureSet.SET_DULL				,   3.0F,     32,  1, 1|2          |64|128                  , 200, 200, 200,   0,	"Polyethylene"					,    0,       0,        400,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 0, Arrays.asList(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2))),
	Epoxid				( 470, TextureSet.SET_DULL				,   3.0F,     32,  1, 1|2          |64|128                  , 200, 140,  20,   0,	"Epoxid"						,    0,       0,        400,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 0, Arrays.asList(new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2))),
	Silicone			( 471, TextureSet.SET_DULL				,   3.0F,    128,  1, 1|2          |64|128                  , 220, 220, 220,   0,	"Polysiloxane"						,    0,       0,        900,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 0, Arrays.asList(new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2))),
	Polycaprolactam		( 472, TextureSet.SET_DULL				,   3.0F,     32,  1, 1|2          |64|128                  ,  50,  50,  50,   0,	"Polycaprolactam"				,    0,       0,        500,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 0, Arrays.asList(new MaterialStack(Carbon, 6), new MaterialStack(Hydrogen, 11), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2))),
	Polytetrafluoroethylene( 473, TextureSet.SET_DULL			,   3.0F,     32,  1, 1|2          |64|128                  , 100, 100, 100,   0,	"Polytetrafluoroethylene"		,    0,       0,       1400,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 0, Arrays.asList(new MaterialStack(Carbon, 2), new MaterialStack(Fluorine, 4)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2))),
	Powellite			( 883, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 255, 255,   0,   0,	"Powellite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4))),
	Pumice				( 926, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 230, 185, 185,   0,	"Pumice"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		, 2, Arrays.asList(new MaterialStack(Stone, 1))),
	Pyrite				( 834, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1    |8                               , 150, 120,  40,   0,	"Pyrite"						,    0,       0,         -1,    0, false, false,   2,   1,   1, Dyes.dyeOrange		, 1, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Sulfur, 2))),
	Pyrolusite			( 943, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 150, 150, 170,   0,	"Pyrolusite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, 1, Arrays.asList(new MaterialStack(Manganese, 1), new MaterialStack(Oxygen, 2))),
	Pyrope				( 835, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8                               , 120,  50, 100,   0,	"Pyrope"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyePurple		, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12))),
	RockSalt			( 944, TextureSet.SET_FINE				,   1.0F,      0,  1, 1    |8                               , 240, 200, 200,   0,	"Rock Salt"						,    0,       0,         -1,    0, false, false,   2,   1,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Chlorine, 1))),
	Rubber				( 880, TextureSet.SET_SHINY				,   1.5F,     16,  0, 1|2          |64|128                  ,   0,   0,   0,   0,	"Rubber"						,    0,       0,        400,    0, false, false,   1,   1,   1, Dyes.dyeBlack		, 0, Arrays.asList(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2))),
	RawRubber			( 896, TextureSet.SET_DULL				,   1.0F,      0,  0, 1                                     , 204, 199, 137,   0,	"Raw Rubber"					,    0,       0,        400,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 0, Arrays.asList(new MaterialStack(Carbon, 5), new MaterialStack(Hydrogen, 8)), Arrays.asList(new TC_AspectStack(TC_Aspects.MOTUS, 2))),
	Ruby				( 502, TextureSet.SET_RUBY				,   7.0F,    256,  2, 1  |4|8      |64                      , 255, 100, 100, 127,	"Ruby"							,    0,       0,         -1,    0, false, true,   5,   1,   1, Dyes.dyeRed			, 1, Arrays.asList(new MaterialStack(Chrome, 1), new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4))),
	Salt				( 817, TextureSet.SET_FINE				,   1.0F,      0,  1, 1    |8                               , 250, 250, 250,   0,	"Salt"							,    0,       0,         -1,    0, false, false,   2,   1,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Sodium, 1), new MaterialStack(Chlorine, 1))),
	Saltpeter			( 836, TextureSet.SET_FINE				,   1.0F,      0,  1, 1    |8                               , 230, 230, 230,   0,	"Saltpeter"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Nitrogen, 1), new MaterialStack(Oxygen, 3))),
	SaltWater			(  -1, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            ,   0,   0, 255,   0,	"Salt Water"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlue		, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.AQUA, 2))),
	Sapphire			( 503, TextureSet.SET_GEM_VERTICAL		,   7.0F,    256,  2, 1  |4|8      |64                      , 100, 100, 200, 127,	"Sapphire"						,    0,       0,         -1,    0, false, true,   5,   1,   1, Dyes.dyeBlue		, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Oxygen, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3))),
	Scheelite			( 910, TextureSet.SET_DULL				,   1.0F,      0,  3, 1    |8                               , 200, 140,  20,   0,	"Scheelite"						,    0,       0,       2500, 2500, false, false,   4,   1,   1, Dyes.dyeBlack		, 0, Arrays.asList(new MaterialStack(Tungsten, 1), new MaterialStack(Calcium, 2), new MaterialStack(Oxygen, 4))),
	SiliconDioxide		( 837, TextureSet.SET_QUARTZ			,   1.0F,      0,  1, 1      |16                            , 200, 200, 200,   0,	"Silicon Dioxide"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLightGray	, 1, Arrays.asList(new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 2))),
	Snow				( 728, TextureSet.SET_FINE				,   1.0F,      0,  0, 1|      16                            , 250, 250, 250,   0,	"Snow"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.GELUM, 1))),
	Sodalite			( 525, TextureSet.SET_LAPIS				,   1.0F,      0,  1, 1  |4|8                               ,  20,  20, 255,   0,	"Sodalite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeBlue		, 1, Arrays.asList(new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Sodium, 4), new MaterialStack(Chlorine, 1))),
	SodiumPersulfate	( 718, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16                            , 255, 255, 255,   0,	"Sodium Persulfate"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, 1, Arrays.asList(new MaterialStack(Sodium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4))),
	SodiumSulfide		( 719, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16                            , 255, 255, 255,   0,	"Sodium Sulfide"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, 1, Arrays.asList(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1))),
	HydricSulfide		( 460, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16                            , 255, 255, 255,   0,	"Hydrogen Sulfide"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, 0, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1))),
	
	OilHeavy			( 730, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            ,  10,  10,  10,   0,	"Heavy Oil"						,    3,      32,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	OilMedium			( 731, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            ,  10,  10,  10,   0,	"Raw Oil"						,    3,      24,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	OilLight			( 732, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            ,  10,  10,  10,   0,	"Light Oil"						,    3,      16,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	
	NatruralGas			( 733, TextureSet.SET_FLUID				,   1.0F,      0,  1,         16                            , 255, 255, 255,   0,	"Natural Gas"					,    1,      15,         -1,    0, false, false,   3,   1,   1, Dyes.dyeWhite		),
	SulfuricGas			( 734, TextureSet.SET_FLUID				,   1.0F,      0,  1,         16                            , 255, 255, 255,   0,	"Sulfuric Gas"					,    1,      20,         -1,    0, false, false,   3,   1,   1, Dyes.dyeWhite		),
	Gas					( 735, TextureSet.SET_FLUID				,   1.0F,      0,  1,         16                            , 255, 255, 255,   0,	"Refinery Gas"					,    1,     128,         -1,    0, false, false,   3,   1,   1, Dyes.dyeWhite		),
	SulfuricNaphtha		( 736, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 255,   0,   0,	"Sulfuric Naphtha"				,    1,      32,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	SulfuricLightFuel	( 737, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 255,   0,   0,	"Sulfuric Light Fuel"			,    0,      32,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	SulfuricHeavyFuel	( 738, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 255,   0,   0,	"Sulfuric Heavy Fuel"			,    3,      32,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	Naphtha				( 739, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 255,   0,   0,	"Naphtha"						,    1,     256,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	LightFuel			( 740, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 255,   0,   0,	"Light Fuel"					,    0,     256,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	HeavyFuel			( 741, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 255,   0,   0,	"Heavy Fuel"					,    3,     192,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	LPG					( 742, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 255,   0,   0,	"LPG"							,    1,     256,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	CrackedLightFuel	( 743, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 255,   0,   0,	"Cracked Light Fuel"			,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		),
	CrackedHeavyFuel	( 744, TextureSet.SET_FLUID				,   1.0F,      0,  0,         16                            , 255, 255,   0,   0,	"Cracked Heavy Fuel"			,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		),
	
	SolderingAlloy		( 314, TextureSet.SET_DULL				,   1.0F,      0,  1, 1|2                                   , 220, 220, 230,   0,	"Soldering Alloy"				,    0,       0,        400,  400, false, false,   1,   1,   1, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(Tin, 9), new MaterialStack(Antimony, 1))),
	Spessartine			( 838, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 255, 100, 100,   0,	"Spessartine"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeRed			, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Manganese, 3), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12))),
	Sphalerite			( 839, TextureSet.SET_DULL				,   1.0F,      0,  1, 1    |8                               , 255, 255, 255,   0,	"Sphalerite"					,    0,       0,         -1,    0, false, false,   2,   1,   1, Dyes.dyeYellow		, 1, Arrays.asList(new MaterialStack(Zinc, 1), new MaterialStack(Sulfur, 1))),
	StainlessSteel		( 306, TextureSet.SET_SHINY				,   7.0F,    480,  2, 1|2          |64|128                  , 200, 200, 220,   0,	"Stainless Steel"				,    0,       0,         -1, 1700, true, false,   1,   1,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Iron, 6), new MaterialStack(Chrome, 1), new MaterialStack(Manganese, 1), new MaterialStack(Nickel, 1))),
	Steel				( 305, TextureSet.SET_METALLIC			,   6.0F,    512,  2, 1|2          |64|128                  , 128, 128, 128,   0,	"Steel"							,    0,       0,       1811, 1000, true, false,   4,  51,  50, Dyes.dyeGray		, 1, Arrays.asList(new MaterialStack(Iron, 50), new MaterialStack(Carbon, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.ORDO, 1))),
	Stibnite			( 945, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8                               ,  70,  70,  70,   0,	"Stibnite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(Antimony, 2), new MaterialStack(Sulfur, 3))),
	SulfuricAcid		( 720, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16                            , 255, 128,   0,   0,	"Sulfuric Acid"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, 1, Arrays.asList(new MaterialStack(Hydrogen, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4))),
	Tanzanite			( 508, TextureSet.SET_GEM_VERTICAL		,   7.0F,    256,  2, 1  |4|8      |64                      ,  64,   0, 200, 127,	"Tanzanite"						,    0,       0,         -1,    0, false, true,   5,   1,   1, Dyes.dyePurple		, 1, Arrays.asList(new MaterialStack(Calcium, 2), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 13)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3))),
	Tetrahedrite		( 840, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 200,  32,   0,   0,	"Tetrahedrite"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeRed			, 2, Arrays.asList(new MaterialStack(Copper, 3), new MaterialStack(Antimony, 1), new MaterialStack(Sulfur, 3), new MaterialStack(Iron, 1))), //Cu3SbS3 + x(Fe,Zn)6Sb2S9
	TinAlloy			( 363, TextureSet.SET_METALLIC			,   6.5F,     96,  2, 1|2          |64|128                  , 200, 200, 200,   0,	"Tin Alloy"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(Tin, 1), new MaterialStack(Iron, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1))),
	Topaz				( 507, TextureSet.SET_GEM_HORIZONTAL	,   7.0F,    256,  3, 1  |4|8      |64                      , 255, 128,   0, 127,	"Topaz"							,    0,       0,         -1,    0, false, true,   5,   1,   1, Dyes.dyeOrange		, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Fluorine, 2), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 6)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4))),
	Tungstate			( 841, TextureSet.SET_DULL				,   1.0F,      0,  3, 1    |8                               ,  55,  50,  35,   0,	"Tungstate"						,    0,       0,       2500, 2500, true, false,   4,   1,   1, Dyes.dyeBlack		, 0, Arrays.asList(new MaterialStack(Tungsten, 1), new MaterialStack(Lithium, 2), new MaterialStack(Oxygen, 4))),
	Ultimet				( 344, TextureSet.SET_SHINY				,   9.0F,   2048,  4, 1|2          |64|128                  , 180, 180, 230,   0,	"Ultimet"						,    0,       0,       2700, 2700, true, false,   1,   1,   1, Dyes.dyeLightBlue	, 1, Arrays.asList(new MaterialStack(Cobalt, 5), new MaterialStack(Chrome, 2), new MaterialStack(Nickel, 1), new MaterialStack(Molybdenum, 1))), // 54% Cobalt, 26% Chromium, 9% Nickel, 5% Molybdenum, 3% Iron, 2% Tungsten, 0.8% Manganese, 0.3% Silicon, 0.08% Nitrogen and 0.06% Carbon
	Uraninite			( 922, TextureSet.SET_METALLIC			,   1.0F,      0,  3, 1    |8                               ,  35,  35,  35,   0,	"Uraninite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLime		, 2, Arrays.asList(new MaterialStack(Uranium, 1), new MaterialStack(Oxygen, 2))),
	Uvarovite			( 842, TextureSet.SET_DIAMOND			,   1.0F,      0,  2, 1    |8                               , 180, 255, 180,   0,	"Uvarovite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeGreen		, 1, Arrays.asList(new MaterialStack(Calcium, 3), new MaterialStack(Chrome, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 12))),
	VanadiumGallium		( 357, TextureSet.SET_SHINY				,   1.0F,      0,  2, 1|2                                   , 128, 128, 140,   0,	"Vanadium-Gallium"				,    0,       0,       3000, 3000, true, false,   1,   1,   1, Dyes.dyeGray		, 2, Arrays.asList(new MaterialStack(Vanadium, 3), new MaterialStack(Gallium, 1))),
	Wood				( 809, TextureSet.SET_WOOD				,   2.0F,     16,  0, 1|2          |64|128                  , 100,  50,   0,   0,	"Wood"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		, 0, Arrays.asList(new MaterialStack(Carbon, 1), new MaterialStack(Oxygen, 1), new MaterialStack(Hydrogen, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.ARBOR, 2))),
	WroughtIron			( 304, TextureSet.SET_METALLIC			,   6.0F,    384,  2, 1|2          |64|128                  , 200, 180, 180,   0,	"Wrought Iron"					,    0,       0,       1811,    0, false, false,   3,   1,   1, Dyes.dyeLightGray	, 2, Arrays.asList(new MaterialStack(Iron, 1))),
	Wulfenite			( 882, TextureSet.SET_DULL				,   1.0F,      0,  3, 1    |8                               , 255, 128,   0,   0,	"Wulfenite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, 2, Arrays.asList(new MaterialStack(Lead, 1), new MaterialStack(Molybdenum, 1), new MaterialStack(Oxygen, 4))),
	YellowLimonite		( 931, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8                               , 200, 200,   0,   0,	"Yellow Limonite"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Hydrogen, 1), new MaterialStack(Oxygen, 2))), // FeO(OH) + a bit Ni and Co
	YttriumBariumCuprate( 358, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1|2                                   ,  80,  64,  70,   0,	"Yttrium Barium Cuprate"		,    0,       0,       1200, 1200, true, false,   1,   1,   1, Dyes.dyeGray		, 0, Arrays.asList(new MaterialStack(Yttrium, 1), new MaterialStack(Barium, 2), new MaterialStack(Copper, 3), new MaterialStack(Oxygen, 7))),
	
	/**
	 * Second Degree Compounds
	 */
	WoodSealed			( 889, TextureSet.SET_WOOD				,   3.0F,     24,  0, 1|2          |64|128                  ,  80,  40,   0,   0,	"Sealed Wood"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBrown		, 0, Arrays.asList(new MaterialStack(Wood, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.ARBOR, 2), new TC_AspectStack(TC_Aspects.FABRICO, 1))),
	LiveRoot			( 832, TextureSet.SET_WOOD				,   1.0F,      0,  1, 1                                     , 220, 200,   0,   0,	"Liveroot"						,    5,      16,         -1,    0, false, false,   2,   4,   3, Dyes.dyeBrown		, 2, Arrays.asList(new MaterialStack(Wood, 3), new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.ARBOR, 2), new TC_AspectStack(TC_Aspects.VICTUS, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1))),
	IronWood			( 338, TextureSet.SET_WOOD				,   6.0F,    384,  2, 1|2          |64|128                  , 150, 140, 110,   0,	"Ironwood"						,    5,       8,         -1,    0, false, false,   2,  19,  18, Dyes.dyeBrown		, 2, Arrays.asList(new MaterialStack(Iron, 9), new MaterialStack(LiveRoot, 9), new MaterialStack(Gold, 1))),
	Glass				( 890, TextureSet.SET_GLASS				,   1.0F,      4,  0, 1  |4                                 , 250, 250, 250, 220,	"Glass"							,    0,       0,       1500,    0, false, true,   1,   1,   1, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(SiliconDioxide, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 2))),
	Perlite				( 925, TextureSet.SET_DULL				,   1.0F,      0,  1, 1    |8                               ,  30,  20,  30,   0,	"Perlite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Obsidian, 2), new MaterialStack(Water, 1))),
	Borax				( 941, TextureSet.SET_FINE				,   1.0F,      0,  1, 1    |8                               , 250, 250, 250,   0,	"Borax"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Sodium, 2), new MaterialStack(Boron, 4), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 7))),
	Lignite				( 538, TextureSet.SET_LIGNITE			,   1.0F,      0,  0, 1  |4|8                               , 100,  70,  70,   0,	"Lignite Coal"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		, 1, Arrays.asList(new MaterialStack(Carbon, 2), new MaterialStack(Water, 4), new MaterialStack(DarkAsh, 1))),
	Olivine				( 505, TextureSet.SET_RUBY				,   7.0F,    256,  2, 1  |4|8      |64                      , 150, 255, 150, 127,	"Olivine"						,    0,       0,         -1,    0, false, true,   5,   1,   1, Dyes.dyeLime		, 1, Arrays.asList(new MaterialStack(Magnesium, 2), new MaterialStack(Iron, 1), new MaterialStack(SiliconDioxide, 2)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 4), new TC_AspectStack(TC_Aspects.VITREUS, 2))),
	Opal				( 510, TextureSet.SET_OPAL				,   7.0F,    256,  2, 1  |4|8      |64                      ,   0,   0, 255,   0,	"Opal"							,    0,       0,         -1,    0, false, true,   3,   1,   1, Dyes.dyeBlue		, 1, Arrays.asList(new MaterialStack(SiliconDioxide, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 5), new TC_AspectStack(TC_Aspects.VITREUS, 3))),
	Amethyst			( 509, TextureSet.SET_FLINT				,   7.0F,    256,  3, 1  |4|8      |64                      , 210,  50, 210, 127,	"Amethyst"						,    0,       0,         -1,    0, false, true,   3,   1,   1, Dyes.dyePink		, 1, Arrays.asList(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Iron, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 6), new TC_AspectStack(TC_Aspects.VITREUS, 4))),
	Redstone			( 810, TextureSet.SET_ROUGH				,   1.0F,      0,  2, 1    |8                               , 200,   0,   0,   0,	"Redstone"						,    0,       0,        500,    0, false, false,   3,   1,   1, Dyes.dyeRed			, 2, Arrays.asList(new MaterialStack(Silicon, 1), new MaterialStack(Pyrite, 5), new MaterialStack(Ruby, 1), new MaterialStack(Mercury, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 1), new TC_AspectStack(TC_Aspects.POTENTIA, 2))),
	Lapis				( 526, TextureSet.SET_LAPIS				,   1.0F,      0,  1, 1  |4|8                               ,  70,  70, 220,   0,	"Lapis"							,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeBlue		, 2, Arrays.asList(new MaterialStack(Lazurite, 12), new MaterialStack(Sodalite, 2), new MaterialStack(Pyrite, 1), new MaterialStack(Calcite, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.SENSUS, 1))),
	Blaze				( 801, TextureSet.SET_POWDER			,   2.0F,     16,  1, 1            |64                      , 255, 200,   0,   0,	"Blaze"							,    0,       0,       6400,    0, false, false,   2,   3,   2, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(DarkAsh, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2), new TC_AspectStack(TC_Aspects.IGNIS, 4))),
	EnderPearl			( 532, TextureSet.SET_SHINY				,   1.0F,     16,  1, 1  |4                                 , 108, 220, 200,   0,	"Enderpearl"					,    0,       0,         -1,    0, false, false,   1,  16,  10, Dyes.dyeGreen		, 1, Arrays.asList(new MaterialStack(Beryllium, 1), new MaterialStack(Potassium, 4), new MaterialStack(Nitrogen, 5), new MaterialStack(Magic, 6)), Arrays.asList(new TC_AspectStack(TC_Aspects.ALIENIS, 4), new TC_AspectStack(TC_Aspects.ITER, 4), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2))),
	EnderEye			( 533, TextureSet.SET_SHINY				,   1.0F,     16,  1, 1  |4                                 , 160, 250, 230,   0,	"Endereye"						,    5,      10,         -1,    0, false, false,   1,   2,   1, Dyes.dyeGreen		, 2, Arrays.asList(new MaterialStack(EnderPearl, 1), new MaterialStack(Blaze, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.SENSUS, 4), new TC_AspectStack(TC_Aspects.ALIENIS, 4), new TC_AspectStack(TC_Aspects.ITER, 4), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 3), new TC_AspectStack(TC_Aspects.IGNIS, 2))),
	Flint				( 802, TextureSet.SET_FLINT				,   2.5F,     64,  1, 1            |64                      ,   0,  32,  64,   0,	"Flint"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		, 2, Arrays.asList(new MaterialStack(SiliconDioxide, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.TERRA, 1), new TC_AspectStack(TC_Aspects.INSTRUMENTUM, 1))),
	Diatomite			( 948, TextureSet.SET_DULL				,   1.0F,      0,  1, 1    |8                               , 225, 225, 225,   0,	"Diatomite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		, 2, Arrays.asList(new MaterialStack(Flint, 8), new MaterialStack(BandedIron, 1), new MaterialStack(Sapphire, 1))),
	VolcanicAsh			( 940, TextureSet.SET_FLINT				,   1.0F,      0,  0, 1                                     ,  60,  50,  50,   0,	"Volcanic Ashes"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Flint, 6), new MaterialStack(Iron, 1), new MaterialStack(Magnesium, 1))),
	Niter				( 531, TextureSet.SET_FLINT				,   1.0F,      0,  1, 1  |4|8                               , 255, 200, 200,   0,	"Niter"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePink		, 2, Arrays.asList(new MaterialStack(Saltpeter, 1))),
	Pyrotheum			( 843, TextureSet.SET_FIERY				,   1.0F,      0,  1, 1                                     , 255, 128,   0,   0,	"Pyrotheum"						,    2,      62,         -1,    0, false, false,   2,   3,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(Coal, 1), new MaterialStack(Redstone, 1), new MaterialStack(Blaze, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.PRAECANTATIO, 2), new TC_AspectStack(TC_Aspects.IGNIS, 1))),
	HydratedCoal		( 818, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1                                     ,  70,  70, 100,   0,	"Hydrated Coal"					,    0,       0,         -1,    0, false, false,   1,   9,   8, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Coal, 8), new MaterialStack(Water, 1))),
	Apatite				( 530, TextureSet.SET_DIAMOND			,   1.0F,      0,  1, 1  |4|8                               , 200, 200, 255,   0,	"Apatite"						,    0,       0,         -1,    0, false, false,   2,   1,   1, Dyes.dyeCyan		, 1, Arrays.asList(new MaterialStack(Calcium, 5), new MaterialStack(Phosphate, 3), new MaterialStack(Chlorine, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.MESSIS, 2))),
	Alumite				(  -1, TextureSet.SET_METALLIC			,   1.5F,     64,  0, 1|2          |64                      , 255, 255, 255,   0,	"Alumite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePink		, 2, Arrays.asList(new MaterialStack(Aluminium, 5), new MaterialStack(Iron, 2), new MaterialStack(Obsidian, 2)), Arrays.asList(new TC_AspectStack(TC_Aspects.STRONTIO, 2))),
	Manyullyn			(  -1, TextureSet.SET_METALLIC			,   1.5F,     64,  0, 1|2          |64                      , 255, 255, 255,   0,	"Manyullyn"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePurple		, 2, Arrays.asList(new MaterialStack(Cobalt, 1), new MaterialStack(Aredrite, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.STRONTIO, 2))),
	ShadowIron			( 336, TextureSet.SET_METALLIC			,   6.0F,    384,  2, 1|2  |8      |64                      , 120, 120, 120,   0,	"Shadowiron"					,    0,       0,         -1,    0, false, false,   3,   4,   3, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Iron, 3), new MaterialStack(Magic, 1))),
	ShadowSteel			( 337, TextureSet.SET_METALLIC			,   6.0F,    768,  2, 1|2          |64                      ,  90,  90,  90,   0,	"Shadowsteel"					,    0,       0,         -1, 1700, true, false,   4,   4,   3, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Steel, 3), new MaterialStack(Magic, 1))),
	Steeleaf			( 339, TextureSet.SET_LEAF				,   8.0F,    768,  3, 1|2          |64|128                  ,  50, 127,  50,   0,	"Steeleaf"						,    5,      24,         -1,    0, false, false,   4,   1,   1, Dyes.dyeGreen		, 2, Arrays.asList(new MaterialStack(Steel, 1), new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.HERBA, 2), new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1))),
	Knightmetal			( 362, TextureSet.SET_METALLIC			,   8.0F,   1024,  3, 1|2          |64|128                  , 210, 240, 200,   0,	"Knightmetal"					,    5,      24,         -1,    0, false, false,   4,   1,   1, Dyes.dyeLime		, 2, Arrays.asList(new MaterialStack(Steel, 2), new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.LUCRUM, 1), new TC_AspectStack(TC_Aspects.METALLUM, 2))),
	SterlingSilver		( 350, TextureSet.SET_SHINY				,  13.0F,    128,  2, 1|2          |64|128                  , 250, 220, 225,   0,	"Sterling Silver"				,    0,       0,         -1, 1700, true, false,   4,   1,   1, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(Copper, 1), new MaterialStack(Silver, 4))),
	RoseGold			( 351, TextureSet.SET_SHINY				,  14.0F,    128,  2, 1|2          |64|128                  , 255, 230,  30,   0,	"Rose Gold"						,    0,       0,         -1, 1600, true, false,   4,   1,   1, Dyes.dyeOrange		, 2, Arrays.asList(new MaterialStack(Copper, 1), new MaterialStack(Gold, 4))),
	BlackBronze			( 352, TextureSet.SET_DULL				,  12.0F,    256,  2, 1|2          |64|128                  , 100,  50, 125,   0,	"Black Bronze"					,    0,       0,         -1, 2000, true, false,   4,   1,   1, Dyes.dyePurple		, 2, Arrays.asList(new MaterialStack(Gold, 1), new MaterialStack(Silver, 1), new MaterialStack(Copper, 3))),
	BismuthBronze		( 353, TextureSet.SET_DULL				,   8.0F,    256,  2, 1|2          |64|128                  , 100, 125, 125,   0,	"Bismuth Bronze"				,    0,       0,         -1, 1100, true, false,   4,   1,   1, Dyes.dyeCyan		, 2, Arrays.asList(new MaterialStack(Bismuth, 1), new MaterialStack(Zinc, 1), new MaterialStack(Copper, 3))),
	BlackSteel			( 334, TextureSet.SET_METALLIC			,   6.5F,    768,  2, 1|2          |64                      , 100, 100, 100,   0,	"Black Steel"					,    0,       0,         -1, 1200, true, false,   4,   1,   1, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Nickel, 1), new MaterialStack(BlackBronze, 1), new MaterialStack(Steel, 3))),
	RedSteel			( 348, TextureSet.SET_METALLIC			,   7.0F,    896,  2, 1|2          |64                      , 140, 100, 100,   0,	"Red Steel"						,    0,       0,         -1, 1300, true, false,   4,   1,   1, Dyes.dyeRed			, 2, Arrays.asList(new MaterialStack(SterlingSilver, 1), new MaterialStack(BismuthBronze, 1), new MaterialStack(Steel, 2), new MaterialStack(BlackSteel, 4))),
	BlueSteel			( 349, TextureSet.SET_METALLIC			,   7.5F,   1024,  2, 1|2          |64                      , 100, 100, 140,   0,	"Blue Steel"					,    0,       0,         -1, 1400, true, false,   4,   1,   1, Dyes.dyeBlue		, 2, Arrays.asList(new MaterialStack(RoseGold, 1), new MaterialStack(Brass, 1), new MaterialStack(Steel, 2), new MaterialStack(BlackSteel, 4))),
	DamascusSteel		( 335, TextureSet.SET_METALLIC			,   8.0F,   1280,  2, 1|2          |64                      , 110, 110, 110,   0,	"Damascus Steel"				,    0,       0,       2000, 1500, true, false,   4,   1,   1, Dyes.dyeGray		, 2, Arrays.asList(new MaterialStack(Steel, 1))),
	TungstenSteel		( 316, TextureSet.SET_METALLIC			,   8.0F,   2560,  4, 1|2          |64|128                  , 100, 100, 160,   0,	"Tungstensteel"					,    0,       0,         -1, 3000, true, false,   4,   1,   1, Dyes.dyeBlue		, 2, Arrays.asList(new MaterialStack(Steel, 1), new MaterialStack(Tungsten, 1))),
	NitroCoalFuel		(  -1, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16                            ,  50,  70,  50,   0,	"Nitro-Coalfuel"				,    0,      48,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		, 0, Arrays.asList(new MaterialStack(Glyceryl, 1), new MaterialStack(CoalFuel, 4))),
	NitroFuel			( 709, TextureSet.SET_FLUID				,   1.0F,      0,  2,         16                            , 200, 255,   0,   0,	"Nitro-Diesel"					,    0,     512,         -1,    0, false, false,   1,   1,   1, Dyes.dyeLime		, 0, Arrays.asList(new MaterialStack(Glyceryl, 1), new MaterialStack(Fuel, 4))),
	AstralSilver		( 333, TextureSet.SET_SHINY				,  10.0F,     64,  2, 1|2  |8      |64                      , 230, 230, 255,   0,	"Astral Silver"					,    0,       0,         -1,    0, false, false,   4,   3,   2, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(Silver, 2), new MaterialStack(Magic, 1))),
	Midasium			( 332, TextureSet.SET_SHINY				,  12.0F,     64,  2, 1|2  |8      |64                      , 255, 200,  40,   0,	"Midasium"						,    0,       0,         -1,    0, false, false,   4,   3,   2, Dyes.dyeOrange		, 2, Arrays.asList(new MaterialStack(Gold, 2), new MaterialStack(Magic, 1))),
	Mithril				( 331, TextureSet.SET_SHINY				,  14.0F,     64,  3, 1|2  |8      |64                      , 255, 255, 210,   0,	"Mithril"						,    0,       0,         -1,    0, false, false,   4,   3,   2, Dyes.dyeLightBlue	, 2, Arrays.asList(new MaterialStack(Platinum, 2), new MaterialStack(Magic, 1))),
	BlueAlloy			( 309, TextureSet.SET_DULL				,   1.0F,      0,  0, 1|2                                   , 100, 180, 255,   0,	"Blue Alloy"					,    0,       0,         -1,    0, false, false,   3,   5,   1, Dyes.dyeLightBlue	, 2, Arrays.asList(new MaterialStack(Silver, 1), new MaterialStack(Nikolite, 4)), Arrays.asList(new TC_AspectStack(TC_Aspects.ELECTRUM, 3))),
	RedAlloy			( 308, TextureSet.SET_DULL				,   1.0F,      0,  0, 1|2                                   , 200,   0,   0,   0,	"Red Alloy"						,    0,       0,         -1,    0, false, false,   3,   5,   1, Dyes.dyeRed			, 2, Arrays.asList(new MaterialStack(Metal, 1), new MaterialStack(Redstone, 4)), Arrays.asList(new TC_AspectStack(TC_Aspects.MACHINA, 3))),
	CobaltBrass			( 343, TextureSet.SET_METALLIC			,   8.0F,    256,  2, 1|2          |64|128                  , 180, 180, 160,   0,	"Cobalt Brass"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeOrange		, 2, Arrays.asList(new MaterialStack(Brass, 7), new MaterialStack(Aluminium, 1), new MaterialStack(Cobalt, 1))),
	Phosphorus			( 534, TextureSet.SET_FLINT				,   1.0F,      0,  2, 1  |4|8|16                            , 255, 255,   0,   0,	"Phosphorus"					,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(Calcium, 3), new MaterialStack(Phosphate, 2))),
	Basalt				( 844, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1                                     ,  30,  20,  20,   0,	"Basalt"						,    0,       0,         -1,    0, false, false,   2,   1,   1, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Olivine, 1), new MaterialStack(Calcite, 3), new MaterialStack(Flint, 8), new MaterialStack(DarkAsh, 4)), Arrays.asList(new TC_AspectStack(TC_Aspects.TENEBRAE, 1))),
	GarnetRed			( 527, TextureSet.SET_RUBY				,   7.0F,    128,  2, 1  |4|8      |64                      , 200,  80,  80, 127,	"Red Garnet"					,    0,       0,         -1,    0, false, true,   4,   1,   1, Dyes.dyeRed			, 2, Arrays.asList(new MaterialStack(Pyrope, 3), new MaterialStack(Almandine, 5), new MaterialStack(Spessartine, 8)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 3))),
	GarnetYellow		( 528, TextureSet.SET_RUBY				,   7.0F,    128,  2, 1  |4|8      |64                      , 200, 200,  80, 127,	"Yellow Garnet"					,    0,       0,         -1,    0, false, true,   4,   1,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(Andradite, 5), new MaterialStack(Grossular, 8), new MaterialStack(Uvarovite, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 3))),
	Marble				( 845, TextureSet.SET_FINE				,   1.0F,      0,  1, 1                                     , 200, 200, 200,   0,	"Marble"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Calcite, 7)), Arrays.asList(new TC_AspectStack(TC_Aspects.PERFODIO, 1))),
	Sugar				( 803, TextureSet.SET_FINE				,   1.0F,      0,  1, 1                                     , 250, 250, 250,   0,	"Sugar"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 1, Arrays.asList(new MaterialStack(Carbon, 2), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 25)), Arrays.asList(new TC_AspectStack(TC_Aspects.HERBA, 1), new TC_AspectStack(TC_Aspects.AQUA, 1), new TC_AspectStack(TC_Aspects.AER, 1))),
	Thaumium			( 330, TextureSet.SET_METALLIC			,  12.0F,    256,  3, 1|2          |64|128                  , 150, 100, 200,   0,	"Thaumium"						,    0,       0,         -1,    0, false, false,   5,   2,   1, Dyes.dyePurple		, 0, Arrays.asList(new MaterialStack(Iron, 1), new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1))),
	Vinteum				( 529, TextureSet.SET_EMERALD			,  10.0F,    128,  3, 1  |4|8      |64                      , 100, 200, 255,   0,	"Vinteum"						,    5,      32,         -1,    0, false, false,   4,   1,   1, Dyes.dyeLightBlue	, 2, Arrays.asList(new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.VITREUS, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1))),
	Vis					(  -1, TextureSet.SET_SHINY				,   1.0F,      0,  3, 0                                     , 128,   0, 255,   0,	"Vis"							,    5,      32,         -1,    0, false, false,   1,   1,   1, Dyes.dyePurple		, 2, Arrays.asList(new MaterialStack(Magic, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.AURAM, 2), new TC_AspectStack(TC_Aspects.PRAECANTATIO, 1))),
	Redrock				( 846, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1                                     , 255,  80,  50,   0,	"Redrock"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeRed			, 2, Arrays.asList(new MaterialStack(Calcite, 2), new MaterialStack(Flint, 1), new MaterialStack(Clay, 1))),
	PotassiumFeldspar	( 847, TextureSet.SET_FINE				,   1.0F,      0,  1, 1                                     , 120,  40,  40,   0,	"Potassium Feldspar"			,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyePink		, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 8))),
	Biotite				( 848, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1                                     ,  20,  30,  20,   0,	"Biotite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeGray		, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 3), new MaterialStack(Aluminium, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Silicon, 3), new MaterialStack(Oxygen, 10))),
	GraniteBlack		( 849, TextureSet.SET_ROUGH				,   4.0F,     64,  3, 1            |64|128                  ,  10,  10,  10,   0,	"Black Granite"					,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(SiliconDioxide, 4), new MaterialStack(Biotite, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.TUTAMEN, 1))),
	GraniteRed			( 850, TextureSet.SET_ROUGH				,   4.0F,     64,  3, 1            |64|128                  , 255,   0, 128,   0,	"Red Granite"					,    0,       0,         -1,    0, false, false,   0,   1,   1, Dyes.dyeMagenta		, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(PotassiumFeldspar, 1), new MaterialStack(Oxygen, 3)), Arrays.asList(new TC_AspectStack(TC_Aspects.TUTAMEN, 1))),
	Chrysotile			( 912, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 110, 140, 110,   0,	"Chrysotile"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(Asbestos, 1))),
	Realgar				( 913, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 140, 100, 100,   0,	"Realgar"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(Arsenic, 4), new MaterialStack(Sulfur,4))),
	VanadiumMagnetite	( 923, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8                               ,  35,  35,  60,   0,	"Vanadium Magnetite"			,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Magnetite, 1), new MaterialStack(Vanadium, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1))), // Mixture of Fe3O4 and V2O5
	BasalticMineralSand	( 935, TextureSet.SET_SAND				,   1.0F,      0,  1, 1    |8                               ,  40,  50,  40,   0,	"Basaltic Mineral Sand"			,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Magnetite, 1), new MaterialStack(Basalt, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1))),
	GraniticMineralSand	( 936, TextureSet.SET_SAND				,   1.0F,      0,  1, 1    |8                               ,  40,  60,  60,   0,	"Granitic Mineral Sand"			,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Magnetite, 1), new MaterialStack(GraniteBlack, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1))),
	GarnetSand			( 938, TextureSet.SET_SAND				,   1.0F,      0,  1, 1    |8                               , 200, 100,   0,   0,	"Garnet Sand"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeOrange		, 2, Arrays.asList(new MaterialStack(GarnetRed, 1), new MaterialStack(GarnetYellow, 1))),
	QuartzSand			( 939, TextureSet.SET_SAND				,   1.0F,      0,  1, 1    |8                               , 200, 200, 200,   0,	"Quartz Sand"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(CertusQuartz, 1), new MaterialStack(Quartzite, 1))),
	Bastnasite			( 905, TextureSet.SET_FINE				,   1.0F,      0,  2, 1    |8                               , 200, 110,  45,   0,	"Bastnasite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Cerium, 1), new MaterialStack(Carbon, 1), new MaterialStack(Fluorine, 1), new MaterialStack(Oxygen, 3))), // (Ce, La, Y)CO3F
	Pentlandite			( 909, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 165, 150,   5,   0,	"Pentlandite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Nickel, 9), new MaterialStack(Sulfur, 8))), // (Fe,Ni)9S8
	Spodumene			( 920, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 190, 170, 170,   0,	"Spodumene"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Lithium, 1), new MaterialStack(Aluminium, 1), new MaterialStack(Silicon, 2), new MaterialStack(Oxygen, 6))), // LiAl(SiO3)2
	Pollucite			( 919, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 240, 210, 210,   0,	"Pollucite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Caesium, 2), new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 4), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 12))), // (Cs,Na)2Al2Si4O12 2H2O (also a source of Rb)
	Tantalite			( 921, TextureSet.SET_METALLIC			,   1.0F,      0,  3, 1    |8                               , 145,  80,  40,   0,	"Tantalite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Manganese, 1), new MaterialStack(Tantalum, 2), new MaterialStack(Oxygen, 6))), // (Fe, Mn)Ta2O6 (also source of Nb)
	Lepidolite			( 907, TextureSet.SET_FINE				,   1.0F,      0,  2, 1    |8                               , 240,  50, 140,   0,	"Lepidolite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Lithium, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10))), // K(Li,Al,Rb)3(Al,Si)4O10(F,OH)2
	Glauconite			( 933, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 130, 180,  60,   0,	"Glauconite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 2), new MaterialStack(Aluminium, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12))), // (K,Na)(Fe3+,Al,Mg)2(Si,Al)4O10(OH)2
	GlauconiteSand		( 949, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 130, 180,  60,   0,	"Glauconite Sand"				,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Magnesium, 2), new MaterialStack(Aluminium, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12))), // (K,Na)(Fe3+,Al,Mg)2(Si,Al)4O10(OH)2
	Vermiculite			( 932, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8                               , 200, 180,  15,   0,	"Vermiculite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Iron, 3), new MaterialStack(Aluminium, 4), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Water, 4), new MaterialStack(Oxygen, 12))), // (Mg+2, Fe+2, Fe+3)3 [(AlSi)4O10] (OH)2 4H2O)
	Bentonite			( 927, TextureSet.SET_ROUGH				,   1.0F,      0,  2, 1    |8                               , 245, 215, 210,   0,	"Bentonite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Sodium, 1), new MaterialStack(Magnesium, 6), new MaterialStack(Silicon, 12), new MaterialStack(Hydrogen, 6), new MaterialStack(Water, 5), new MaterialStack(Oxygen, 36))), // (Na,Ca)0.33(Al,Mg)2(Si4O10)(OH)2 nH2O
	FullersEarth		( 928, TextureSet.SET_FINE				,   1.0F,      0,  2, 1    |8                               , 160, 160, 120,   0,	"Fullers Earth"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Magnesium, 1), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 4), new MaterialStack(Oxygen, 11))), // (Mg,Al)2Si4O10(OH) 4(H2O)
	Pitchblende			( 873, TextureSet.SET_DULL				,   1.0F,      0,  3, 1    |8                               , 200, 210,   0,   0,	"Pitchblende"					,    0,       0,         -1,    0, false, false,   5,   1,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(Uraninite, 3), new MaterialStack(Thorium, 1), new MaterialStack(Lead, 1))),
	Monazite			( 520, TextureSet.SET_DIAMOND			,   1.0F,      0,  1, 1  |4|8                               ,  50,  70,  50,   0,	"Monazite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeGreen		, 1, Arrays.asList(new MaterialStack(RareEarth, 1), new MaterialStack(Phosphate, 1))), // Wikipedia: (Ce, La, Nd, Th, Sm, Gd)PO4 Monazite also smelt-extract to Helium, it is brown like the rare earth Item Monazite sand deposits are inevitably of the monazite-(Ce) composition. Typically, the lanthanides in such monazites contain about 45ִ8% cerium, about 24% lanthanum, about 17% neodymium, about 5% praseodymium, and minor quantities of samarium, gadolinium, and yttrium. Europium concentrations tend to be low, about 0.05% Thorium content of monazite is variable and sometimes can be up to 20ֳ0%
	Malachite			( 871, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               ,   5,  95,   5,   0,	"Malachite"						,    0,       0,         -1,    0, false, false,   3,   1,   1, Dyes.dyeGreen		, 1, Arrays.asList(new MaterialStack(Copper, 2), new MaterialStack(Carbon, 1), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 5))), // Cu2CO3(OH)2
	Mirabilite			( 900, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 240, 250, 210,   0,	"Mirabilite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Sodium, 2), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 10), new MaterialStack(Oxygen, 4))), // Na2SO4 10H2O
	Mica				( 901, TextureSet.SET_FINE				,   1.0F,      0,  1, 1    |8                               , 195, 195, 205,   0,	"Mica"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 3), new MaterialStack(Fluorine, 2), new MaterialStack(Oxygen, 10))), // KAl2(AlSi3O10)(F,OH)2
	Trona				( 903, TextureSet.SET_METALLIC			,   1.0F,      0,  1, 1    |8                               , 135, 135,  95,   0,	"Trona"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Sodium, 3), new MaterialStack(Carbon, 2), new MaterialStack(Hydrogen, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 6))), // Na3(CO3)(HCO3) 2H2O
	Barite				( 904, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 230, 235, 255,   0,	"Barite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Barium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Oxygen, 4))),
	Gypsum				( 934, TextureSet.SET_DULL				,   1.0F,      0,  1, 1    |8                               , 230, 230, 250,   0,	"Gypsum"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Sulfur, 1), new MaterialStack(Water, 2), new MaterialStack(Oxygen, 4))), // CaSO4 2H2O
	Alunite				( 911, TextureSet.SET_METALLIC			,   1.0F,      0,  2, 1    |8                               , 225, 180,  65,   0,	"Alunite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Potassium, 1), new MaterialStack(Aluminium, 3), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 6), new MaterialStack(Oxygen, 14))), // KAl3(SO4)2(OH)6
	Dolomite			( 914, TextureSet.SET_FLINT				,   1.0F,      0,  1, 1    |8                               , 225, 205, 205,   0,	"Dolomite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Magnesium, 1), new MaterialStack(Carbon, 2), new MaterialStack(Oxygen, 6))), // CaMg(CO3)2
	Wollastonite		( 915, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 240, 240, 240,   0,	"Wollastonite"					,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Calcium, 1), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 3))), // CaSiO3
	Zeolite				( 916, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 240, 230, 230,   0,	"Zeolite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Sodium, 1), new MaterialStack(Calcium, 4), new MaterialStack(Silicon, 27), new MaterialStack(Aluminium, 9), new MaterialStack(Water, 28), new MaterialStack(Oxygen, 72))), // NaCa4(Si27Al9)O72 28(H2O)
	Kyanite				( 924, TextureSet.SET_FLINT				,   1.0F,      0,  2, 1    |8                               , 110, 110, 250,   0,	"Kyanite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 1), new MaterialStack(Oxygen, 5))), // Al2SiO5
	Kaolinite			( 929, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               , 245, 235, 235,   0,	"Kaolinite"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Aluminium, 2), new MaterialStack(Silicon, 2), new MaterialStack(Hydrogen, 4), new MaterialStack(Oxygen, 9))), // Al2Si2O5(OH)4
	Talc				( 902, TextureSet.SET_DULL				,   1.0F,      0,  2, 1    |8                               ,  90, 180,  90,   0,	"Talc"							,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12))), // H2Mg3(SiO3)4 
	Soapstone			( 877, TextureSet.SET_DULL				,   1.0F,      0,  1, 1    |8                               ,  95, 145,  95,   0,	"Soapstone"						,    0,       0,         -1,    0, false, false,   1,   1,   1, Dyes._NULL			, 1, Arrays.asList(new MaterialStack(Magnesium, 3), new MaterialStack(Silicon, 4), new MaterialStack(Hydrogen, 2), new MaterialStack(Oxygen, 12))), // H2Mg3(SiO3)4 
	Concrete			( 947, TextureSet.SET_ROUGH				,   1.0F,      0,  1, 1                                     , 100, 100, 100,   0,	"Concrete"						,    0,       0,        300,    0, false, false,   0,   1,   1, Dyes.dyeGray		, 0, Arrays.asList(new MaterialStack(Stone, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.TERRA, 1))),
	IronMagnetic		( 354, TextureSet.SET_MAGNETIC			,   6.0F,    256,  2, 1|2          |64|128                  , 200, 200, 200,   0,	"Magnetic Iron"					,    0,       0,         -1,    0, false, false,   4,  51,  50, Dyes.dyeGray		, 1, Arrays.asList(new MaterialStack(Iron, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 2), new TC_AspectStack(TC_Aspects.MAGNETO, 1))),
	SteelMagnetic		( 355, TextureSet.SET_MAGNETIC			,   6.0F,    512,  2, 1|2          |64|128                  , 128, 128, 128,   0,	"Magnetic Steel"				,    0,       0,       1000, 1000, true, false,   4,  51,  50, Dyes.dyeGray		, 1, Arrays.asList(new MaterialStack(Steel, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.ORDO, 1), new TC_AspectStack(TC_Aspects.MAGNETO, 1))),
	NeodymiumMagnetic	( 356, TextureSet.SET_MAGNETIC			,   7.0F,    512,  2, 1|2          |64|128                  , 100, 100, 100,   0,	"Magnetic Neodymium"			,    0,       0,       1297, 1297, true, false,   4,  51,  50, Dyes.dyeGray		, 1, Arrays.asList(new MaterialStack(Neodymium, 1)), Arrays.asList(new TC_AspectStack(TC_Aspects.METALLUM, 1), new TC_AspectStack(TC_Aspects.MAGNETO, 3))),
	TungstenCarbide		( 370, TextureSet.SET_METALLIC			,  14.0F,   1280,  4, 1|2          |64|128                  ,  51,   0, 102,   0,	"Tungstencarbide"				,    0,       0,       2460, 2460, true, false,   4,   1,   1, Dyes.dyeBlack		, 2, Arrays.asList(new MaterialStack(Tungsten, 1), new MaterialStack(Carbon, 1))),
	VanadiumSteel		( 371, TextureSet.SET_METALLIC			,   3.0F,   1920,  3, 1|2          |64|128                  , 192, 192, 192,   0,	"Vanadiumsteel"					,    0,       0,       1453, 1453, true, false,   4,   1,   1, Dyes.dyeWhite		, 2, Arrays.asList(new MaterialStack(Vanadium, 1), new MaterialStack(Chrome, 1), new MaterialStack(Steel, 7))),
	HSSG				( 372, TextureSet.SET_METALLIC			,  10.0F,   4000,  3, 1|2          |64|128                  , 153, 153,   0,   0,	"HSS-G"							,    0,       0,       3123, 3123, true, false,   4,   1,   1, Dyes.dyeYellow		, 2, Arrays.asList(new MaterialStack(TungstenSteel, 5), new MaterialStack(Chrome, 1), new MaterialStack(Molybdenum, 2), new MaterialStack(Vanadium, 1))),
	HSSE				( 373, TextureSet.SET_METALLIC			,  10.0F,   5120,  4, 1|2          |64|128                  ,  51, 102,   0,   0,	"HSS-E"							,    0,       0,       3383, 3383, true, false,   4,   1,   1, Dyes.dyeBlue		, 2, Arrays.asList(new MaterialStack(HSSG, 6), new MaterialStack(Cobalt, 1),new MaterialStack(Manganese, 1), new MaterialStack(Silicon, 1))),
	HSSS				( 374, TextureSet.SET_METALLIC			,  14.0F,   3000,  4, 1|2          |64|128                  , 102,   0,  51,   0,	"HSS-S"							,    0,       0,       3597, 3597, true, false,   4,   1,   1, Dyes.dyeRed			, 2, Arrays.asList(new MaterialStack(HSSG, 6), new MaterialStack(Iridium, 2), new MaterialStack(Osmium, 1))),
	
	/**
	 * Materials which are renamed automatically
	 */
	@Deprecated IridiumAndSodiumOxide(IridiumSodiumOxide, false),
	@Deprecated Palygorskite		(FullersEarth, false),
	@Deprecated Adamantine			(Adamantium, true),
	@Deprecated Ashes				(Ash, false),
	@Deprecated DarkAshes			(DarkAsh, false),
	@Deprecated Abyssal				(Basalt, false),
	@Deprecated Adamant				(Adamantium, true),
	@Deprecated AluminumBrass		(AluminiumBrass, false),
	@Deprecated Aluminum			(Aluminium, false),
	@Deprecated NaturalAluminum		(Aluminium, false),
	@Deprecated NaturalAluminium	(Aluminium, false),
	@Deprecated Americum			(Americium, false),
	@Deprecated Beryl				(Emerald, false), // 30,200,200
	@Deprecated BlackGranite		(GraniteBlack, false),
	@Deprecated CalciumCarbonate	(Calcite, false),
	@Deprecated CreosoteOil			(Creosote, false),
	@Deprecated Chromium			(Chrome, false),
	@Deprecated Diesel				(Fuel, false),
	@Deprecated Enderpearl			(EnderPearl, false),
	@Deprecated Endereye			(EnderEye, false),
	@Deprecated EyeOfEnder			(EnderEye, false),
	@Deprecated Eyeofender			(EnderEye, false),
	@Deprecated Flour				(Wheat, false),
	@Deprecated Meat				(MeatRaw, false),
	@Deprecated Garnet				(GarnetRed, true),
	@Deprecated Granite				(GraniteBlack, false),
	@Deprecated Goethite			(BrownLimonite, false),
	@Deprecated Kalium				(Potassium, false),
	@Deprecated Lapislazuli			(Lapis, false),
	@Deprecated LapisLazuli			(Lapis, false),
	@Deprecated Monazit				(Monazite, false),
	@Deprecated Natrium				(Sodium, false),
	@Deprecated Mythril				(Mithril, false),
	@Deprecated NitroDiesel			(NitroFuel, false),
	@Deprecated Naquadriah			(Naquadria, false),
	@Deprecated Obby				(Obsidian, false),
	@Deprecated Peridot				(Olivine, true),
	@Deprecated Phosphorite			(Phosphorus, true),
	@Deprecated Quarried			(Marble, false),
	@Deprecated Quicksilver			(Mercury, true),
	@Deprecated QuickSilver			(Mercury, false),
	@Deprecated RedRock				(Redrock, false),
	@Deprecated RefinedIron			(Iron, false),
	@Deprecated RedGranite			(GraniteRed, false),
	@Deprecated Sheldonite			(Cooperite, false),
	@Deprecated Soulsand			(SoulSand, false),
	@Deprecated Titan				(Titanium, false),
	@Deprecated Uran				(Uranium, false),
	@Deprecated Wolframite			(Tungstate, false),
	@Deprecated Wolframium			(Tungsten, false),
	@Deprecated Wolfram				(Tungsten, false),
	;
	
	/** List of all Materials. */
	public static final Collection<Materials> VALUES = new HashSet<Materials>(Arrays.asList(values())); 
	
	static {
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
				TungstenSteel, AstralSilver, Midasium, Mithril, BlueAlloy, RedAlloy, CobaltBrass, Thaumium, IronMagnetic, SteelMagnetic, NeodymiumMagnetic, Knightmetal);
		
		SubTag.FOOD.addTo(MeatRaw, MeatCooked, Ice, Water, Salt, Chili, Cocoa, Cheese, Coffee, Chocolate, Milk, Honey, FryingOilHot, FishOil, SeedOil, SeedOilLin, SeedOilHemp, Wheat, Sugar, FreshWater);
		
		Wood					.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
		WoodSealed				.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.NO_WORKING);
		Peanutwood				.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
		LiveRoot				.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE);
		IronWood				.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE);
		Steeleaf				.add(SubTag.WOOD, SubTag.FLAMMABLE, SubTag.MAGICAL, SubTag.MORTAR_GRINDABLE, SubTag.NO_SMELTING);
		
		MeatRaw					.add(SubTag.NO_SMASHING);
		MeatCooked				.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Snow					.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
		Ice						.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
		Water					.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.NO_RECYCLING);
		Sulfur					.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
		Saltpeter				.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
		Graphite				.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.NO_SMELTING);
		
		Wheat					.add(SubTag.FLAMMABLE, SubTag.MORTAR_GRINDABLE);
		Paper					.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE, SubTag.PAPER);
		Coal					.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
		Charcoal				.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
		Lignite					.add(SubTag.FLAMMABLE, SubTag.NO_SMELTING, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
		
		Rubber					.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY, SubTag.STRETCHY);
		Plastic					.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY);
		Silicone				.add(SubTag.FLAMMABLE, SubTag.NO_SMASHING, SubTag.BOUNCY, SubTag.STRETCHY);
		
		TNT						.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
		Gunpowder				.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
		Glyceryl				.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
		NitroCoalFuel			.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
		NitroFuel				.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
		NitroCarbon				.add(SubTag.FLAMMABLE, SubTag.EXPLOSIVE, SubTag.NO_SMELTING, SubTag.NO_SMASHING);
		
		Lead					.add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL, SubTag.SOLDERING_MATERIAL_BAD);
		Tin						.add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL);
		SolderingAlloy			.add(SubTag.MORTAR_GRINDABLE, SubTag.SOLDERING_MATERIAL, SubTag.SOLDERING_MATERIAL_GOOD);
		
		Cheese					.add(SubTag.SMELTING_TO_FLUID);
		Sugar					.add(SubTag.SMELTING_TO_FLUID);
		
		Concrete				.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.SMELTING_TO_FLUID);
		ConstructionFoam		.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.EXPLOSIVE, SubTag.NO_SMELTING);
		Redstone				.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.SMELTING_TO_FLUID, SubTag.PULVERIZING_CINNABAR);
		Glowstone				.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.SMELTING_TO_FLUID);
		Nikolite				.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.SMELTING_TO_FLUID);
		Teslatite				.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.SMELTING_TO_FLUID);
		Netherrack				.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.UNBURNABLE, SubTag.FLAMMABLE);
		Stone					.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.NO_RECYCLING);
		Brick					.add(SubTag.STONE, SubTag.NO_SMASHING);
		NetherBrick				.add(SubTag.STONE, SubTag.NO_SMASHING);
		Endstone				.add(SubTag.STONE, SubTag.NO_SMASHING);
		Marble					.add(SubTag.STONE, SubTag.NO_SMASHING);
		Basalt					.add(SubTag.STONE, SubTag.NO_SMASHING);
		Redrock					.add(SubTag.STONE, SubTag.NO_SMASHING);
		Obsidian				.add(SubTag.STONE, SubTag.NO_SMASHING);
		Flint					.add(SubTag.STONE, SubTag.NO_SMASHING, SubTag.MORTAR_GRINDABLE);
		GraniteRed				.add(SubTag.STONE, SubTag.NO_SMASHING);
		GraniteBlack			.add(SubTag.STONE, SubTag.NO_SMASHING);
		Salt					.add(SubTag.STONE, SubTag.NO_SMASHING);
		RockSalt				.add(SubTag.STONE, SubTag.NO_SMASHING);
		
		Sand					.add(SubTag.NO_RECYCLING);
		
		Gold					.add(SubTag.MORTAR_GRINDABLE);
		Silver					.add(SubTag.MORTAR_GRINDABLE);
		Iron					.add(SubTag.MORTAR_GRINDABLE);
		IronMagnetic			.add(SubTag.MORTAR_GRINDABLE);
		HSLA					.add(SubTag.MORTAR_GRINDABLE);
		Steel					.add(SubTag.MORTAR_GRINDABLE);
		SteelMagnetic			.add(SubTag.MORTAR_GRINDABLE);
		Zinc					.add(SubTag.MORTAR_GRINDABLE);
		Antimony				.add(SubTag.MORTAR_GRINDABLE);
		Copper					.add(SubTag.MORTAR_GRINDABLE);
		AnnealedCopper			.add(SubTag.MORTAR_GRINDABLE);
		Bronze					.add(SubTag.MORTAR_GRINDABLE);
		Nickel					.add(SubTag.MORTAR_GRINDABLE);
		Invar					.add(SubTag.MORTAR_GRINDABLE);
		Brass					.add(SubTag.MORTAR_GRINDABLE);
		WroughtIron				.add(SubTag.MORTAR_GRINDABLE);
		Electrum				.add(SubTag.MORTAR_GRINDABLE);
		Clay					.add(SubTag.MORTAR_GRINDABLE);
		
		Glass					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_RECYCLING, SubTag.SMELTING_TO_FLUID);
		Diamond					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE);
		Emerald					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Amethyst				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Tanzanite				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Topaz					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		BlueTopaz				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Amber					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		GreenSapphire			.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Sapphire				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Ruby					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		FoolsRuby				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Opal					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Olivine					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Jasper					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		GarnetRed				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		GarnetYellow			.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Mimichite				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		CrystalFlux				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Crystal					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Niter					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Apatite					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
		Lapis					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
		Sodalite				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
		Lazurite				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
		Monazite				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE);
		Quartzite				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
		Quartz					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
		SiliconDioxide			.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
		Dilithium				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
		NetherQuartz			.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
		CertusQuartz			.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
		Fluix					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.QUARTZ);
		Phosphorus				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.EXPLOSIVE);
		Phosphate				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.FLAMMABLE, SubTag.EXPLOSIVE);
		InfusedAir				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
		InfusedFire				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
		InfusedEarth			.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
		InfusedWater			.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
		InfusedEntropy			.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
		InfusedOrder			.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
		InfusedVis				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
		InfusedDull				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
		Vinteum					.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
		NetherStar				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.UNBURNABLE);
		EnderPearl				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.PEARL);
		EnderEye				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.MAGICAL, SubTag.PEARL);
		Firestone				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL, SubTag.QUARTZ, SubTag.UNBURNABLE, SubTag.BURNING);
		Forcicium				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL);
		Forcillium				.add(SubTag.CRYSTAL, SubTag.NO_SMASHING, SubTag.NO_SMELTING, SubTag.CRYSTALLISABLE, SubTag.MAGICAL);
		Force					.add(SubTag.CRYSTAL, SubTag.MAGICAL, SubTag.UNBURNABLE);
		Magic					.add(SubTag.CRYSTAL, SubTag.MAGICAL, SubTag.UNBURNABLE);
		
		Primitive				.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Basic					.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Good					.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Advanced				.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Data					.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Elite					.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Master					.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Ultimate				.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Superconductor			.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);
		Infinite				.add(SubTag.NO_SMASHING, SubTag.NO_SMELTING);

		Blaze					.add(SubTag.MAGICAL, SubTag.NO_SMELTING, SubTag.SMELTING_TO_FLUID, SubTag.MORTAR_GRINDABLE, SubTag.UNBURNABLE, SubTag.BURNING);
		FierySteel				.add(SubTag.MAGICAL, SubTag.UNBURNABLE, SubTag.BURNING);
		ElvenElementium			.add(SubTag.MAGICAL);
		DarkThaumium			.add(SubTag.MAGICAL);
		Thaumium				.add(SubTag.MAGICAL);
		Enderium				.add(SubTag.MAGICAL);
		AstralSilver			.add(SubTag.MAGICAL);
		Midasium				.add(SubTag.MAGICAL);
		Mithril					.add(SubTag.MAGICAL);
		
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
		Midasium				.setEnchantmentForTools(Enchantment.fortune, 2);
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

    /**
     * This Array can be changed dynamically by a Tick Handler in order to get a glowing Effect on all GT Meta Items out of this Material.
     */
    public final short[] mRGBa = new short[]{255, 255, 255, 0}, mMoltenRGBa = new short[]{255, 255, 255, 0};
    public final TextureSet mIconSet;
    public final int mMetaItemSubID;
    public final boolean mUnificatable;
    public final Materials mMaterialInto;
    public final List<MaterialStack> mMaterialList = new ArrayList<MaterialStack>();
    public final List<Materials> mOreByProducts = new ArrayList<Materials>(), mOreReRegistrations = new ArrayList<Materials>();
    public final List<TC_AspectStack> mAspects = new ArrayList<TC_AspectStack>();
    private final ArrayList<ItemStack> mMaterialItems = new ArrayList<ItemStack>();
    private final Collection<SubTag> mSubTags = new HashSet<SubTag>();
    public Enchantment mEnchantmentTools = null, mEnchantmentArmors = null;
    public byte mEnchantmentToolsLevel = 0, mEnchantmentArmorsLevel = 0;
    public boolean mBlastFurnaceRequired = false;
    public float mToolSpeed = 1.0F, mHeatDamage = 0.0F;
    public String mChemicalFormula = "?", mDefaultLocalName = "null";
    public Dyes mColor = Dyes._NULL;
    public short mMeltingPoint = 0, mBlastFurnaceTemp = 0;
    public int mTypes = 0, mDurability = 16, mFuelPower = 0, mFuelType = 0, mExtraData = 0, mOreValue = 0, mOreMultiplier = 1, mByProductMultiplier = 1, mSmeltingMultiplier = 1;
    public long mDensity = M;
    public Element mElement = null;
    public Materials mDirectSmelting = this, mOreReplacement = this, mMacerateInto = this, mSmeltInto = this, mArcSmeltInto = this, mHandleMaterial = this;
    public byte mToolQuality = 0;
    public Fluid mSolid = null, mFluid = null, mGas = null, mPlasma = null;
    /**
     * This Fluid is used as standard Unit for Molten Materials. 1296 is a Molten Block, what means 144 is one Material Unit worth
     */
    public Fluid mStandardMoltenFluid = null;

    private Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aToolDurability, int aToolQuality, boolean aUnificatable) {
        mUnificatable = aUnificatable;
        mMaterialInto = this;
        mMetaItemSubID = aMetaItemSubID;
        mToolQuality = (byte) aToolQuality;
        mDurability = aToolDurability;
        mToolSpeed = aToolSpeed;
        mIconSet = aIconSet;
        if (aMetaItemSubID >= 0) {
            if (GregTech_API.sGeneratedMaterials[aMetaItemSubID] == null) {
                GregTech_API.sGeneratedMaterials[aMetaItemSubID] = this;
            } else {
                throw new IllegalArgumentException("The Index " + aMetaItemSubID + " is already used!");
            }
        }
    }

    private Materials(Materials aMaterialInto, boolean aReRegisterIntoThis) {
        mUnificatable = false;
        mDefaultLocalName = aMaterialInto.mDefaultLocalName;
        mMaterialInto = aMaterialInto.mMaterialInto;
        if (aReRegisterIntoThis) mMaterialInto.mOreReRegistrations.add(this);
        mChemicalFormula = aMaterialInto.mChemicalFormula;
        mMetaItemSubID = -1;
        mIconSet = TextureSet.SET_NONE;
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
     * @param aLocalName            The Name used as Default for localization.
     * @param aFuelType             Type of Generator to get Energy from this Material.
     * @param aFuelPower            EU generated. Will be multiplied by 1000, also additionally multiplied by 2 for Gems.
     * @param aAmplificationValue   Amount of UUM amplifier gotten from this.
     * @param aUUMEnergy            Amount of EU needed to shape the UUM into this Material.
     * @param aMeltingPoint         Used to determine the smelting Costs in Furnii.
     * @param aBlastFurnaceTemp     Used to determine the needed Heat capactiy Costs in Blast Furnii.
     * @param aBlastFurnaceRequired If this requires a Blast Furnace.
     * @param aColor                Vanilla MC Wool Color which comes the closest to this.
     */
    private Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aToolDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aToolDurability, aToolQuality, true);
        mDefaultLocalName = aLocalName;
        mMeltingPoint = (short) aMeltingPoint;
        mBlastFurnaceTemp = (short) aBlastFurnaceTemp;
        mBlastFurnaceRequired = aBlastFurnaceRequired;
        if (aTransparent) add(SubTag.TRANSPARENT);
        mFuelPower = aFuelPower;
        mFuelType = aFuelType;
        mOreValue = aOreValue;
        mDensity = (M * aDensityMultiplier) / aDensityDivider;
        mColor = aColor == null ? Dyes._NULL : aColor;
        if (mColor != null) add(SubTag.HAS_COLOR);
        mRGBa[0] = mMoltenRGBa[0] = (short) aR;
        mRGBa[1] = mMoltenRGBa[1] = (short) aG;
        mRGBa[2] = mMoltenRGBa[2] = (short) aB;
        mRGBa[3] = mMoltenRGBa[3] = (short) aA;
        mTypes = aTypes;
        if ((mTypes & 2) != 0) add(SubTag.SMELTING_TO_FLUID);
    }

    private Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aToolDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, List<TC_AspectStack> aAspects) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aToolDurability, aToolQuality, aTypes, aR, aG, aB, aA, aLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor);
        mAspects.addAll(aAspects);
    }

    /**
     * @param aElement The Element Enum represented by this Material
     */
    private Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aToolDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, Element aElement, List<TC_AspectStack> aAspects) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aToolDurability, aToolQuality, aTypes, aR, aG, aB, aA, aLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor);
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

    private Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aToolDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, int aExtraData, List<MaterialStack> aMaterialList) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aToolDurability, aToolQuality, aTypes, aR, aG, aB, aA, aLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor, aExtraData, aMaterialList, null);
    }

    private Materials(int aMetaItemSubID, TextureSet aIconSet, float aToolSpeed, int aToolDurability, int aToolQuality, int aTypes, int aR, int aG, int aB, int aA, String aLocalName, int aFuelType, int aFuelPower, int aMeltingPoint, int aBlastFurnaceTemp, boolean aBlastFurnaceRequired, boolean aTransparent, int aOreValue, int aDensityMultiplier, int aDensityDivider, Dyes aColor, int aExtraData, List<MaterialStack> aMaterialList, List<TC_AspectStack> aAspects) {
        this(aMetaItemSubID, aIconSet, aToolSpeed, aToolDurability, aToolQuality, aTypes, aR, aG, aB, aA, aLocalName, aFuelType, aFuelPower, aMeltingPoint, aBlastFurnaceTemp, aBlastFurnaceRequired, aTransparent, aOreValue, aDensityMultiplier, aDensityDivider, aColor);
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
                for (TC_AspectStack tAspect : tMaterial.mMaterial.mAspects) tAspect.addToAspectList(mAspects);
        }

        if (mMeltingPoint < 0) mMeltingPoint = (short) (tMeltingPoint / tAmountOfComponents);

        tAmountOfComponents *= aDensityMultiplier;
        tAmountOfComponents /= aDensityDivider;
        if (aAspects == null) for (TC_AspectStack tAspect : mAspects)
            tAspect.mAmount = Math.max(1, tAspect.mAmount / Math.max(1, tAmountOfComponents));
        else mAspects.addAll(aAspects);
    }

    public static Materials get(String aMaterialName) {
        Object tObject = GT_Utility.getFieldContent(Materials.class, aMaterialName, false, false);
        if (tObject != null && tObject instanceof Materials) return (Materials) tObject;
        return _NULL;
    }

    public static Materials getRealMaterial(String aMaterialName) {
        return get(aMaterialName).mMaterialInto;
    }

    /**
     * Called in preInit with the Config to set Values.
     *
     * @param aConfiguration
     */
    public static void init(GT_Config aConfiguration) {
        for (Materials tMaterial : VALUES) {
            String tString = tMaterial.toString().toLowerCase();
            tMaterial.mHeatDamage = (float) aConfiguration.get(ConfigCategories.Materials.heatdamage, tString, tMaterial.mHeatDamage);
            if (tMaterial.mBlastFurnaceRequired)
                tMaterial.mBlastFurnaceRequired = aConfiguration.get(ConfigCategories.Materials.blastfurnacerequirements, tString, true);
            if (tMaterial.mBlastFurnaceRequired && aConfiguration.get(ConfigCategories.Materials.blastinductionsmelter, tString, tMaterial.mBlastFurnaceTemp < 1500))
                GT_ModHandler.ThermalExpansion.addSmelterBlastOre(tMaterial);
            tMaterial.mHandleMaterial = (tMaterial == Desh ? tMaterial.mHandleMaterial : tMaterial == Diamond || tMaterial == Thaumium ? Wood : tMaterial.contains(SubTag.BURNING) ? Blaze : tMaterial.contains(SubTag.MAGICAL) && tMaterial.contains(SubTag.CRYSTAL) && Loader.isModLoaded(MOD_ID_TC) ? Thaumium : tMaterial.getMass() > Element.Tc.getMass() * 2 ? TungstenSteel : tMaterial.getMass() > Element.Tc.getMass() ? Steel : Wood);
        }
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
        for (int i = 0; i < mMaterialItems.size(); i++)
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
    
    public static volatile int VERSION = 509;
}