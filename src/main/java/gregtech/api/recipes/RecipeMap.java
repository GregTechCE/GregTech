package gregtech.api.recipes;

import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.metatileentity.GregtechTileEntity;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.EnumValidationResult;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ValidationResult;
import gregtech.common.items.MetaItems;
import ic2.api.recipe.Recipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static gregtech.api.GTValues.L;
import static gregtech.api.GTValues.W;

//todo update examples after materials/oredictunificator/itemlist update
public class RecipeMap<T extends Recipe, R extends RecipeBuilder<T, R>> {

	/**
	 * Contains all Recipe Maps
	 */
	public static final Collection<RecipeMap<?, ?>> RECIPE_MAPS = new ArrayList<>();

	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> ORE_WASHER_RECIPES = new RecipeMap<>(new HashSet<>(0), "orewasher", "basicmachines/OreWasher", 1, 1, 3, 3, 0, 1, 0, 0, true, 1, 1, false, new RecipeBuilder.DefaultRecipeBuilder().duration(400).EUt(16));
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> THERMAL_CENTRIFUGE_RECIPES = new RecipeMap<>(new HashSet<>(0), "thermalcentrifuge", "basicmachines/ThermalCentrifuge", 1, 1, 1, 3, 0, 0, 0, 0, true, 2, 1, false, new RecipeBuilder.DefaultRecipeBuilder().duration(400).EUt(48));

	/**
	 * Example:
	 * <pre>
	 *		RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
	 *			.inputs(OreDictUnifier.get(OrePrefix.ingot, Materials.Steel, 9))
	 *			.outputs(OreDictUnifier.get(OrePrefix.block, Materials.Steel))
	 *			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> COMPRESSOR_RECIPES = new RecipeMap<>(new HashSet<>(0), "compressor", "basicmachines/Compressor", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, false, new RecipeBuilder.DefaultRecipeBuilder().duration(400).EUt(2));

	/**
	 * Example:
	 * <pre>
	 *	    RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
	 * 			.inputs(new ItemStack(Blocks.RED_FLOWER, 1, 3))
	 * 			.outputs(new ItemStack(Items.DYE, 2, 7))
	 * 			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> EXTRACTOR_RECIPES = new RecipeMap<>(new HashSet<>(0), "extractor", "basicmachines/Extractor", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, false, new RecipeBuilder.DefaultRecipeBuilder().duration(400).EUt(2));

	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> RECYCLER_RECIPES = new RecipeMapRecycler(new HashSet<>(0), "recycler", "basicmachines/Recycler", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, false, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> FURNACE_RECIPES = new RecipeMapFurnace(new HashSet<>(0), "furnace", "basicmachines/E_Furnace", 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, true, false, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> MICROWAVE_RECIPES = new RecipeMapMicrowave(new HashSet<>(0), "microwave", "basicmachines/E_Furnace", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, false, new RecipeBuilder.DefaultRecipeBuilder());

	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> SCANNER_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(300), "scanner", "basicmachines/Scanner", 1, 0, 0, 0, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> ROCK_BREAKER_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(3), "rockbreaker", "basicmachines/RockBreaker", 0, 0, 0, 0, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> BY_PRODUCT_LIST = new FakeRecipeMap<>(new HashSet<>(1000), "byproductlist", "basicmachines/Default", 1, 0, 0, 0, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> REPICATOR_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(100), "replicator", "basicmachines/Replicator", 0, 1, 0, 0, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());//TODO min max amounts
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> ASSEMBLYLINE_FAKE_RECIPES = new FakeRecipeMap<>(new HashSet<>(30), "assemblyline", "basicmachines/Default", 1, 0, 0, 0, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());//TODO min max amounts

	/**
	 * Examples:
	 * <pre>
	 * 		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
	 *			.inputs(OreDictUnifier.get(OrePrefix.stick, Materials.Wood, 1L), new ItemStack(Items.coal, 1, GTValues.W))
	 *			.outputs(new ItemStack(Blocks.torch, 4))
	 *			.duration(400)
	 *			.EUt(1)
	 *			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> ASSEMBLER_RECIPES = new RecipeMap<>(new HashSet<>(300), "assembler", "basicmachines/Assembler", 1, 2, 1, 1, 0, 1, 0, 0, true, 1, 1, true, new RecipeBuilder.IntCircuitRecipeBuilder() {

        @Override
        public void buildAndRegister() {
            if(fluidInputs.size() == 1 && fluidInputs.get(0).getFluid() == Materials.SolderingAlloy.getMaterialFluid()) {
                int amount = fluidInputs.get(0).amount;
                fluidInputs.clear();
                recipeMap.addRecipe(this.copy().fluidInputs(Materials.SolderingAlloy.getFluid(amount)).build());
                recipeMap.addRecipe(this.copy().fluidInputs(Materials.Tin.getFluid((int) (amount * 1.5))).build());
                recipeMap.addRecipe(this.copy().fluidInputs(Materials.Lead.getFluid(amount * 2)).build());
            } else {
                recipeMap.addRecipe(build());
            }
        }

    });

	/**
	 * Example:
	 * <pre>
	 * 	    RecipeMap.PRINTER_RECIPES.recipeBuilder()
	 *				.inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 3L))
	 *				.fluidInputs(FluidRegistry.getFluidStack("squidink", 144))
	 *				.specialItem(ItemList.Tool_DataStick.getWithName(0, "With Scanned Book Data"))
	 *				.outputs(ItemList.Paper_Printed_Pages.get(1))
	 *				.duration(400)
	 *				.EUt(2)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> PRINTER_RECIPES = new RecipeMapPrinter(new HashSet<>(100), "printer", "basicmachines/Printer", 1, 1, 1, 1, 1, 1, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Examples:
	 * <pre>
	 * 		RecipeMap.PRESS_RECIPES.recipeBuilder()
	 * 				.inputs(ItemList.Empty_Board_Basic.get(1), ItemList.Circuit_Parts_Wiring_Basic.get(4))
	 * 				.outputs(ItemList.Circuit_Board_Basic.get(1))
	 * 				.duration(32)
	 * 				.EUt(16)
	 * 				.buildAndRegister();
	 * 		RecipeMap.PRESS_RECIPES.recipeBuilder()
	 * 				.inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1))
	 * 				.notConsumable(ItemList.Shape_Mold_Credit)
	 * 				.outputs(ItemList.Credit_Iron.get(4))
	 * 				.duration(100)
	 * 				.EUt(16)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> PRESS_RECIPES = new RecipeMapFormingPress(new HashSet<>(100), "press", "basicmachines/Press", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *	   RecipeMap.MACERATOR_RECIPES.recipeBuilder()
	 *	   			.inputs(OreDictUnifier.get(OrePrefix.block, Materials.Marble, 1))
	 *	   			.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Marble, 1))
	 *	   			.duration(160)
	 *	   			.EUt(4)
	 *	   			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> MACERATOR_RECIPES = new RecipeMap<>(new HashSet<>(10000), "macerator", "basicmachines/Macerator4", 1, 1, 1, 4, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder().duration(400).EUt(2));

	/**
	 * Input full box, output item and empty box.
	 * Example:
	 * <pre>
	 *		RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
	 *				.inputs(ItemList.Tool_MatchBox_Full.get(1))
	 *				.outputs(ItemList.Tool_Matches.get(16))
	 *				.duration(32)
	 *				.EUt(16)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> UNBOXINATOR_RECIPES = new RecipeMapUnboxinator(new HashSet<>(2500), "unpackager", "basicmachines/Unpackager", 1, 1, 1, 2, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
	 * 				.inputs(ItemList.Battery_Hull_LV.get(1))
	 * 				.outputs(ItemList.IC2_ReBattery.get(1))
	 * 				.fluidInputs(Materials.Redstone.getFluid(288))
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> FLUID_CANNER_RECIPES = new RecipeMapFluidCanner(new HashSet<>(100), "fluidcanner", "basicmachines/FluidCannerJEI", 1, 1, 1, 1, 0, 1, 0, 1, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		protected EnumValidationResult finalizeAndValidate(boolean checkForCollisions) {
			duration(fluidOutputs.isEmpty() ? fluidInputs.get(0).amount / 62 : fluidOutputs.get(0).amount / 62);
			return super.finalizeAndValidate(checkForCollisions);
		}
	}.EUt(1));

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.PLASMA_ARC_FURNACE_RECIPES.recipeBuilder()
	 * 				.inputs(ItemList.Block_TungstenSteelReinforced.get(1))
	 * 				.outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.TungstenSteel,2), OreDictUnifier.get(OrePrefix.dust,Materials.Concrete,1))
	 * 				.fluidInputs(Materials.Argon.getPlasma(16))
	 * 				.fluidOutputs(Materials.Argon.getGas(16))
	 * 				.duration(160)
	 * 				.EUt(96)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> PLASMA_ARC_FURNACE_RECIPES = new RecipeMap<>(new HashSet<>(10000), "plasmaarcfurnace", "basicmachines/PlasmaArcFurnace", 1, 1, 1, 4, 1, 1, 0, 1, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * <pre>
	 * Example:
	 * 		RecipeMap.ARC_FURNACE_RECIPES.recipeBuilder()
	 * 				.inputs(ItemList.Block_TungstenSteelReinforced.get(1))
	 * 				.outputs(OreDictUnifier.get(OrePrefix.ingot,Materials.TungstenSteel,2), OreDictUnifier.get(OrePrefix.dust,Materials.Concrete,1))
	 * 				.duration(160)
	 * 				.EUt(96)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.ArcFurnaceRecipeBuilder> ARC_FURNACE_RECIPES = new RecipeMap<>(new HashSet<>(10000), "arcfurnace", "basicmachines/ArcFurnace", 1, 1, 1, 4, 1, 1, 0, 0, true, 3, 1, true, new RecipeBuilder.ArcFurnaceRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *     RecipeMap.SIFTER_RECIPES.recipeBuilder()
	 *     			.inputs(new ItemStack(Blocks.SAND))
	 *     			.chancedOutput(OreDictUnifier.get(OrePrefix.gemExquisite, Materials.Ruby, 1L), 300)
	 *     			.chancedOutput(OreDictUnifier.get(OrePrefix.gemFlawless, Materials.Ruby, 1L), 1200)
	 *     			.chancedOutput(OreDictUnifier.get(OrePrefix.gemFlawed, Materials.Ruby, 1L), 4500)
	 *     			.chancedOutput(OreDictUnifier.get(OrePrefix.gemChipped, Materials.Ruby, 1L), 1400)
	 *     			.chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Ruby, 1L), 2800)
	 *     			.duration(800)
	 *     			.EUt(16)
	 *     			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> SIFTER_RECIPES = new RecipeMap<>(new HashSet<>(100), "sifter", "basicmachines/Sifter", 1, 1, 0, 6, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.LASER_ENGRAVER_RECIPES.recipeBuilder()
	 * 				.inputs(ItemList.IC2_LapotronCrystal.getWildcard(1))
	 * 				.notConsumable(Items.APPLE)
	 * 				.outputs(ItemList.Circuit_Parts_Crystal_Chip_Master.get(3))
	 * 				.duration(256)
	 * 				.EUt(480)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> LASER_ENGRAVER_RECIPES = new RecipeMap<>(new HashSet<>(100), "laserengraver", "basicmachines/LaserEngraver", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.MIXER_RECIPES.recipeBuilder()
	 * 				.inputs(new ItemStack(Blocks.SAND, 1, GTValues.W), new ItemStack(Blocks.DIRT, 1, GTValues.W))
	 * 				.fluidInputs(Materials.Water.getFluid(250))
	 * 				.outputs(GT_ModHandler.getModItem("Forestry", "soil", 2, 1))
	 * 				.duration(16)
	 * 				.EUt(16)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> MIXER_RECIPES = new RecipeMap<>(new HashSet<>(100), "mixer", "basicmachines/Mixer", 1, 4, 0, 0, 0, 1, 0, 1, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		protected EnumValidationResult validate(boolean checkForCollisions) {
			if (!((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()))){
				GTLog.logger.error("Recipe should have at least one input and one output", new IllegalArgumentException());
				recipeStatus = EnumValidationResult.INVALID;
			}
			return super.validate(checkForCollisions);
		}
	});

	/**
	 * Example:
	 * <pre>
	 *	 	RecipeMap.AUTOCLAVE_RECIPES.recipeBuilder()
	 * 				.inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 16))
	 * 				.fluidInputs(Materials.Lutetium.getFluid(4))
	 * 				.chancedOutput(GT_ModHandler.getIC2Item("carbonFiber", 8L), 3333)
	 * 				.duration(600)
	 * 				.EUt(5)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> AUTOCLAVE_RECIPES = new RecipeMap<>(new HashSet<>(200), "autoclave", "basicmachines/Autoclave", 1, 1, 1, 1, 1, 1, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 	    RecipeMap.ELECTROMAGNETIC_SEPARATOR_RECIPES.recipeBuilder()
	 * 				.inputs(OreDictUnifier.get(OrePrefix.dustPure, Materials.Iron, 1L))
	 * 				.chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Iron, 1L), 10000)
	 * 				.chancedOutput(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Iron, 1L), 4000)
	 * 				.chancedOutput(OreDictUnifier.get(OrePrefix.nugget, Materials.Iron, 1L), 2000)
	 * 				.duration(400)
	 * 				.EUt(24)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> ELECTROMAGNETIC_SEPARATOR_RECIPES = new RecipeMap<>(new HashSet<>(50), "electromagneticseparator", "basicmachines/ElectromagneticSeparator", 1, 1, 1, 3, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.POLARIZER_RECIPES.recipeBuilder()
	 * 				.inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1L))
	 * 				.outputs(OreDictUnifier.get(aPrefix, Materials.IronMagnetic, 1L))
	 * 				.duration(100)
	 * 				.EUt(16)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> POLARIZER_RECIPES = new RecipeMap<>(new HashSet<>(100), "polarizer", "basicmachines/Polarizer", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.CHEMICAL_BATH_RECIPES.recipeBuilder()
	 * 				.inputs(new ItemStack(Items.reeds, 1, GTValues.W))
	 * 				.fluidInputs(Materials.Water.getFluid(100))
	 * 				.outputs(new ItemStack(Items.paper, 1, 0))
	 * 				.duration(100)
	 * 				.EUt(8)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CHEMICAL_BATH_RECIPES = new RecipeMap<>(new HashSet<>(200), "chemicalbath", "basicmachines/ChemicalBath", 1, 1, 1, 3, 1, 1, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.BREWING_RECIPES.recipeBuilder()
	 *         		.inputs(ItemList.IC2_Hops.get(1))
	 *         		.fluidInput(FluidRegistry.WATER)
	 *         		.fluidOutput(FluidRegistry.getFluid("potion.hopsjuice"))
	 *         		.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.BrewingRecipeBuilder> BREWING_RECIPES = new RecipeMap<>(new HashSet<>(100), "brewer", "basicmachines/PotionBrewer", 1, 1, 0, 0, 1, 1, 1, 1, true, 1, 1, true, new RecipeBuilder.BrewingRecipeBuilder().notOptimized().duration(128).EUt(4));

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.FLUID_HEATER_RECIPES.recipeBuilder()
	 * 				.circuitMeta(1)
	 * 				.fluidInputs(Materials.Water.getFluid(6))
	 * 				.fluidOutputs(Materials.Water.getGas(960))
	 * 				.duration(30)
	 * 				.EUt(32)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> FLUID_HEATER_RECIPES = new RecipeMap<>(new HashSet<>(100), "fluidheater", "basicmachines/FluidHeater", 1, 1, 0, 0, 1, 1, 1, 1, true, 1, 1, true, new RecipeBuilder.IntCircuitRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *	 	RecipeMap.DISTILLERY_RECIPES.recipeBuilder()
	 *	 			.circuitMeta(4)
	 *	 			.fluidInputs(Materials.Creosote.getFluid(3))
	 *	 			.fluidOutputs(Materials.Lubricant.getFluid(1))
	 *	 			.duration(16)
	 *	 			.EUt(24)
	 *	 			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> DISTILLERY_RECIPES = new RecipeMap<>(new HashSet<>(100), "distillery", "basicmachines/Distillery", 1, 1, 0, 0, 1, 1, 1, 1, true, 1, 1, true, new RecipeBuilder.IntCircuitRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.FERMENTING_RECIPES.recipeBuilder()
	 *				.fluidInputs(FluidRegistry.getFluidStack("potion.lemonjuice", 50))
	 *				.fluidOutputs(FluidRegistry.getFluidStack("potion.limoncello", 25))
	 *				.duration(1024)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> FERMENTING_RECIPES = new RecipeMap<>(new HashSet<>(100), "fermenter", "basicmachines/Fermenter", 0, 0, 0, 0, 1, 1, 1, 1, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder().notOptimized().EUt(2));

	/**
	 * Example:
	 * <pre>
	 *  	RecipeMap.FLUID_SOLIDFICATION_RECIPES.recipeBuilder()
	 *				.notConsumable(ItemList.Shape_Mold_Casing)
	 *				.fluidInputs(Materials.Steel.getFluid(72))
	 *				.outputs(ItemList.IC2_Item_Casing_Steel.get(1))
	 *				.duration(16)
	 *				.EUt(8)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> FLUID_SOLIDFICATION_RECIPES = new RecipeMap<>(new HashSet<>(100), "fluidsolidifier", "basicmachines/FluidSolidifier", 1, 1, 1, 1, 1, 1, 0, 0, true, 1, 1, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Examples:
	 * <pre>
	 * 		RecipeMap.FLUID_EXTRACTION_RECIPES.recipeBuilder()
	 * 				.inputs(new ItemStack(Blocks.SNOW))
	 * 				.fluidOutputs(Materials.Water.getFluid(1000))
	 * 				.duration(128)
	 * 				.EUt(4)
	 * 				.buildAndRegister();
	 *
	 * 		RecipeMap.FLUID_EXTRACTION_RECIPES.recipeBuilder()
	 * 				.inputs(GT_ModHandler.getModItem("Forestry", "phosphor", 1))
	 * 				.chancedOutput(OreDictUnifier.get(OrePrefix.dust, Materials.Phosphorus, 1), 1000)
	 * 				.fluidOutputs(Materials.Lava.getFluid(800))
	 * 				.duration(256)
	 * 				.EUt(128)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> FLUID_EXTRACTION_RECIPES = new RecipeMap<>(new HashSet<>(100), "fluidextractor", "basicmachines/FluidExtractor", 1, 1, 0, 1, 0, 0, 1, 1, true, 1, 1, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Input item and empty box, output full box
	 * Example:
	 * <pre>
	 *   	RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
	 *				.inputs(ItemList.Tool_Matches.get(16), OreDictUnifier.get(OrePrefix.plateDouble, Materials.Paper, 1L))
	 *				.outputs(ItemList.Tool_MatchBox_Full.get(1))
	 *				.duration(64)
	 *				.EUt(16)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> BOXINATOR_RECIPES = new RecipeMap<>(new HashSet<>(2500), "packager", "basicmachines/Packager", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.FUSION_RECIPES.recipeBuilder()
	 *				.fluidInputs(Materials.Lithium.getFluid(16), Materials.Tungsten.getFluid(16))
	 *				.fluidOutputs(Materials.Iridium.getFluid(16))
	 *				.duration(32)
	 *				.EUt(32768)
	 *				.EUToStart(300000000)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe.FusionRecipe, RecipeBuilder.FusionRecipeBuilder> FUSION_RECIPES = new RecipeMap<>(new HashSet<>(50), "fusionreactor", "basicmachines/Default", 0, 0, 0, 0, 2, 2, 1, 1, true, 1, 1, true, new RecipeBuilder.FusionRecipeBuilder());

	/**
	 * Examples:
	 * <pre>
	 *		RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
	 *				.inputs(new ItemStack(Blocks.SANDSTONE))
	 *				.cellAmount(1)
	 *				.outputs(OreDictUnifier.get(OrePrefix.cell, Materials.Oil, 1L), new ItemStack(Blocks.SAND, 1, 0))
	 *				.duration(1000)
	 *				.buildAndRegister();
	 *
	 *		RecipeMap.CENTRIFUGE_RECIPES.recipeBuilder()
	 *				.inputs(new ItemStack(Blocks.PUMPKIN, 16))
	 *				.fluidOutputs(Materials.Methane.getGas(1152))
	 *				.duration(4608)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CENTRIFUGE_RECIPES = new RecipeMap<>(new HashSet<>(1000), "centrifuge", "basicmachines/Centrifuge", 0, 2, 0, 6, 0, 1, 0, 1, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		protected EnumValidationResult validate(boolean checkForCollisions) {
			if (!((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()))){
				GTLog.logger.error("Recipe should have at least one input and one output", new IllegalArgumentException());
				recipeStatus = EnumValidationResult.INVALID;
			}
			return super.validate(checkForCollisions);
		}
	}.EUt(5));

	/**
	 * Examples:
	 * <pre>
	 *	   	RecipeMap.ELECTROLYZER_RECIPES.recipeBuilder()
	 *	   			.cellAmount(1)
	 *	   			.fluidInputs(new FluidStack(ItemList.sBlueVitriol,9000))
	 *	   			.fluidOutputs(Materials.SulfuricAcid.getFluid(8000))
	 *	   			.outputs(OreDictUnifier.get(OrePrefix.dust,Materials.Copper,1), OreDictUnifier.get(OrePrefix.cell,Materials.Oxygen,1))
	 *	   			.duration(900)
	 *	   			.EUt(30)
	 *	   			.buildAndRegister();
	 *
	 *		RecipeMap.ELECTROLYZER_RECIPES.recipeBuilder()
	 *				.inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Tungstate, 7L))
	 *				.fluidInputs(Materials.Hydrogen.getGas(7000))
	 *				.fluidOutputs(Materials.Oxygen.getGas(4000))
	 *				.outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Tungsten, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Lithium, 2L))
	 *				.duration(120)
	 *				.EUt(1920)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> ELECTROLYZER_RECIPES = new RecipeMap<>(new HashSet<>(200), "electrolyzer", "basicmachines/Electrolyzer", 0, 2, 0, 6, 0, 1, 0, 1, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		protected EnumValidationResult validate(boolean checkForCollisions) {
			if (!((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()))){
				GTLog.logger.error("Recipe should have at least one input and one output", new IllegalArgumentException());
				recipeStatus = EnumValidationResult.INVALID;
			}
			return super.validate(checkForCollisions);
		}
	});

	/**
	 * Example:
	 * <pre>
	 *	    RecipeMap.BLAST_RECIPES.recipeBuilder()
	 *				.inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Glass, 1), OreDictUnifier.get(OrePrefix.dust, Materials.Carbon, 1))
	 *				.fluidInputs(Materials.Electrum.getFluid(16))
	 *				.outputs(ItemList.Circuit_Board_Fiberglass.get(16))
	 *				.duration(80)
	 *				.EUt(480)
	 *				.blastFurnaceTemp(2600)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe.BlastRecipe, RecipeBuilder.BlastRecipeBuilder> BLAST_RECIPES = new RecipeMap<>(new HashSet<>(500), "blastfurnace", "basicmachines/Default", 1, 2, 1, 2, 0, 1, 0, 1, false, 1, 1, true, new RecipeBuilder.BlastRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.IMPLOSION_RECIPES.recipeBuilder()
	 *         		.inputs(ItemList.Ingot_IridiumAlloy.get(1))
	 *         		.explosivesAmount(8)
	 *         		.outputs(OreDictUnifier.get(OrePrefix.plateAlloy, Materials.Iridium, 1), OreDictUnifier.get(OrePrefix.dustTiny, Materials.DarkAsh, 4L))
	 *         		.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.ImplosionRecipeBuilder> IMPLOSION_RECIPES = new RecipeMap<>(new HashSet<>(50), "implosioncompressor", "basicmachines/Default", 2, 2, 2, 2, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.ImplosionRecipeBuilder().duration(20).EUt(30));

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.VACUUM_RECIPES.recipeBuilder()
	 *				.inputs(OreDictUnifier.get(OrePrefix.cell, Materials.Water, 1L))
	 *				.outputs(OreDictUnifier.get(OrePrefix.cell, Materials.Ice, 1L))
	 *				.duration(50)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> VACUUM_RECIPES = new RecipeMap<>(new HashSet<>(100), "vacuumfreezer", "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder().EUt(120));

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.CHEMICAL_RECIPES.recipeBuilder()
	 *				.inputs(OreDictUnifier.get(OrePrefix.cell, Materials.NitrogenDioxide, 4), OreDictUnifier.get(OrePrefix.cell, Materials.Oxygen, 1))
	 *				.fluidInputs(Materials.Water.getFluid(2000))
	 *				.fluidOutputs( new FluidStack(ItemList.sNitricAcid,4000))
	 *				.outputs(ItemList.Cell_Empty.get(5))
	 *				.duration(950)
	 *				.EUt(30)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CHEMICAL_RECIPES = new RecipeMap<>(new HashSet<>(100), "chemicalreactor", "basicmachines/ChemicalReactor", 0, 2, 0, 1, 0, 1, 0, 1, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		protected EnumValidationResult validate(boolean checkForCollisions) {
			if (!((inputs.isEmpty() && fluidInputs.isEmpty()) || (outputs.isEmpty() && fluidOutputs.isEmpty()))){
				GTLog.logger.error("Recipe should have at least one input and one output", new IllegalArgumentException());
				recipeStatus = EnumValidationResult.INVALID;
			}
			return super.validate(checkForCollisions);
		}
	}.duration(30));

	/**
	 * If universal every Fluid also gets separate distillation recipes
	 * Examples:
	 * <pre>
	 *	    RecipeMap.DISTILLATION_RECIPES.recipeBuilder()
	 *	        	.fluidInputs(new FluidStack(FluidName.biomass.getInstance(), 250))
	 *	        	.fluidOutputs(new FluidStack(FluidRegistry.getFluid("ic2biogas"), 8000), Materials.Water.getFluid(125))
	 *	        	.outputs(ItemList.IC2_Fertilizer.get(1))
	 *	        	.duration(250)
	 *	        	.EUt(480)
	 *	        	.buildAndRegister();
	 *
	 *		RecipeMap.DISTILLATION_RECIPES.recipeBuilder()
	 *				.universal()
	 *				.fluidInputs(Materials.CrackedHeavyFuel.getFluid(100))
	 *				.fluidOutputs(Materials.Gas.getGas(80), Materials.Naphtha.getFluid(10), Materials.LightFuel.getFluid(40), new FluidStack(ItemList.sToluene,30), Materials.Lubricant.getFluid(5))
	 *				.outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.HydratedCoal, 1))
	 *				.duration(16)
	 *				.EUt(64)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.UniversalDistillationRecipeBuilder> DISTILLATION_RECIPES = new RecipeMap<>(new HashSet<>(50), "distillationtower", "basicmachines/Default", 0, 0, 0, 1, 1, 1, 1, 5, true, 1, 1, true, new RecipeBuilder.UniversalDistillationRecipeBuilder().notOptimized());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.CRACKING_RECIPES.recipeBuilder()
	 *         		.fluidInputs(Materials.HeavyFuel.getFluid(128))
	 *         		.fluidOutputs(Materials.CrackedHeavyFuel.getFluid(192))
	 *         		.duration(16)
	 *         		.EUt(320)
	 *         		.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CRACKING_RECIPES = new RecipeMap<>(new HashSet<>(50), "craker", "basicmachines/Default", 0, 0, 0, 0, 1, 2, 1, 2, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		public void buildAndRegister() {
			super.buildAndRegister();
			FluidStack fluidInput = fluidInputs.get(0);
			FluidStack fluidOutput = fluidInputs.get(0);
			recipeMap.addRecipe(this.copy().fluidInputs(fluidInput, ModHandler.getSteam(fluidInput.amount)).fluidOutputs(fluidOutput, Materials.Hydrogen.getFluid(fluidInput.amount)).build());
			recipeMap.addRecipe(this.copy().fluidInputs(fluidInput, Materials.Hydrogen.getFluid(fluidInput.amount)).fluidOutputs(new FluidStack(fluidOutput.getFluid(), (int) (fluidOutput.amount * 1.3))).build());
		}
	}.notOptimized());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.PYROLYSE_RECIPES.recipeBuilder()
	 *     			.inputs(new ItemStack(Blocks.LOG, 16))
	 *     			.circuitMeta(2)
	 *     			.fluidInputs(Materials.Nitrogen.getGas(1000))
	 *     			.outputs(new ItemStack(Items.COAL, 20, 1))
	 *     			.fluidOutputs(Materials.Creosote.getFluid(4000))
	 *     			.duration(320)
	 *     			.EUt(96)
	 *     			.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> PYROLYSE_RECIPES = new RecipeMap<>(new HashSet<>(50), "pyro", "basicmachines/Default", 2, 2, 0, 1, 0, 1, 1, 1, true, 1, 1, true, new RecipeBuilder.IntCircuitRecipeBuilder().notOptimized());

	/**
	 * Example:
	 * <pre>
	 * 		RecipeMap.WIREMILL_RECIPES.recipeBuilder()
	 *				.inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Iron, 1L))
	 *				.outputs(GT_ModHandler.getIC2Item("ironCableItem", 6L))
	 *				.duration(200)
	 *				.EUt(2)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> WIREMILL_RECIPES = new RecipeMap<>(new HashSet<>(50), "wiremill", "basicmachines/Wiremill", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *		RecipeMap.BENDER_RECIPES.recipeBuilder()
	 *				.inputs(OreDictUnifier.get(OrePrefix.plate, Materials.Tin, 12L))
	 *				.outputs(ItemList.Cell_Empty.get(6))
	 *				.duration(1200)
	 *				.EUt(8)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.IntCircuitRecipeBuilder> BENDER_RECIPES = new RecipeMap<>(new HashSet<>(400), "metalbender", "basicmachines/Bender", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.IntCircuitRecipeBuilder() {
		@Override
		protected EnumValidationResult finalizeAndValidate(boolean checkForCollisions) {
			this.circuitMeta(this.inputs.get(0).getCount());
			return super.finalizeAndValidate(checkForCollisions);
		}
	});

	/**
	 * Example:
	 * <pre>
	 *	 	RecipeMap.ALLOY_SMELTER_RECIPES.recipeBuilder()
	 *				.inputs(OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1L), OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 1L))
	 *				.outputs(OreDictUnifier.get(OrePrefix.ingot, Materials.ConductiveIron, 1L))
	 *				.duration(400)
	 *				.EUt(24)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> ALLOY_SMELTER_RECIPES = new RecipeMap<>(new HashSet<>(3000), "alloysmelter", "basicmachines/AlloySmelter", 1, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.NotConsumableInputRecipeBuilder() {
		@Override
		protected EnumValidationResult validate(boolean checkForCollisions) {
			ItemStack input = inputs.get(0);
			if (!(inputs.size() == 1
					&& (OreDictUnifier.getPrefix(input) == OrePrefix.ingot
					|| OreDictUnifier.getPrefix(input) == OrePrefix.dust
					|| OreDictUnifier.getPrefix(input) == OrePrefix.gem))){
				GTLog.logger.error("Recipe should have ingot, dust or gem as input", new IllegalArgumentException());
				recipeStatus = EnumValidationResult.INVALID;
			}
			return super.validate(checkForCollisions);
		}

		@Override
		public void buildAndRegister() {
//			if (GregTechMod.gregtechproxy.mTEMachineRecipes) {
//				ModHandler.ThermalExpansion.addInductionSmelterRecipe(getInputs().get(0), getInputs().get(1), getOutputs().get(0), null, duration * EUt * 2, 0);
//			}
			super.buildAndRegister();
		}
	});

	/**
	 * Example:
	 * <pre>
	 *       RecipeMap.CANNER_RECIPES.recipeBuilder()
	 * 				.inputs(new ItemStack(Items.cake, 1, GTValues.W), ItemList.IC2_Food_Can_Empty.get(12))
	 * 				.outputs(ItemList.IC2_Food_Can_Filled.get(12))
	 * 				.duration(600)
	 * 				.EUt(1)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CANNER_RECIPES = new RecipeMap<>(new HashSet<>(300), "canner", "basicmachines/Canner", 1, 2, 1, 2, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *	     RecipeMap.LATHE_RECIPES.recipeBuilder()
	 * 				.inputs(OreDictUnifier.get(OrePrefix.gemExquisite, Materials.Ruby, 1L))
	 * 				.outputs(OreDictUnifier.get(OrePrefix.lens, Materials.Ruby, 1L), OreDictUnifier.get(OrePrefix.dust, Materials.Ruby, 2L))
	 * 				.duration(Materials.Ruby.getMass())
	 * 				.EUt(24)
	 * 				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> LATHE_RECIPES = new RecipeMap<>(new HashSet<>(400), "lathe", "basicmachines/Lathe", 1, 1, 1, 2, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.CUTTER_RECIPES.recipeBuilder()
	 *				.inputs(new ItemStack(Blocks.LOG))
	 *				.fluidInputs(Materials.Lubricant.getFluid(1))
	 *				.outputs(new ItemStack(Blocks.PLANKS), OreDictUnifier.get(OrePrefix.dust, Materials.Wood, 1L))
	 *				.duration(200)
	 *				.EUt(8)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> CUTTER_RECIPES = new RecipeMap<>(new HashSet<>(200), "cuttingsaw", "basicmachines/Cutter", 1, 1, 0, 0, 1, 1, 1, 2, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		public void buildAndRegister() {
			if (fluidInputs.isEmpty()) {
				recipeMap.addRecipe(this.copy().fluidInputs(Materials.Water.getFluid(Math.max(4, Math.min(1000, duration * EUt / 320)))).duration(duration * 2).build());
				recipeMap.addRecipe(this.copy().fluidInputs(ModHandler.getDistilledWater(Math.max(3, Math.min(750, duration * EUt / 426)))).duration(duration * 2).build());
				recipeMap.addRecipe(this.copy().fluidInputs(Materials.Lubricant.getFluid(Math.max(1, Math.min(250, duration * EUt / 1280)))).duration(duration * 2).build());
			} else {
				recipeMap.addRecipe(build());
			}
		}
	});

	/**
	 * <pre>
	 * Example:
	 *	    RecipeMap.SLICER_RECIPES.recipeBuilder()
	 *				.inputs(ItemList.Food_Baked_Bread.get(1))
	 *				.notConsumable(ItemList.Shape_Slicer_Flat)
	 *				.outputs(ItemList.Food_Sliced_Bread.get(2))
	 *				.duration(128)
	 *				.EUt(4)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> SLICER_RECIPES = new RecipeMap<>(new HashSet<>(200), "slicer", "basicmachines/Slicer", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.EXTRUDER_RECIPES.recipeBuilder()
	 *				.inputs(new ItemStack(Items.IRON_INGOT))
	 *				.notConsumable(ItemList.Shape_Extruder_Rod)
	 *				.outputs(OreDictUnifier.get(OrePrefix.stick, Materials.Iron.smeltInto, 2))
	 *				.duration(64)
	 *				.EUt(8)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> EXTRUDER_RECIPES = new RecipeMap<>(new HashSet<>(1000), "extruder", "basicmachines/Extruder", 2, 2, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.NotConsumableInputRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.HAMMER_RECIPES.recipeBuilder()
	 *				.inputs(new ItemStack(Blocks.STONE, 1, 0))
	 *				.outputs(new ItemStack(Blocks.COBBLESTONE, 1, 0))
	 *				.duration(16)
	 *				.EUt(10)
	 *				.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> HAMMER_RECIPES = new RecipeMap<>(new HashSet<>(200), "hammer", "basicmachines/Hammer", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 *      RecipeMap.AMPLIFIERS.recipeBuilder()
	 *      		.inputs(ItemList.IC2_Scrap.get(9))
	 *      		.duration(180)
	 *      		.amplifierAmountOutputted(1)
	 *      		.buildAndRegister();
	 * </pre>
	 */
	public static final RecipeMap<Recipe.AmplifierRecipe, RecipeBuilder.AmplifierRecipeBuilder> AMPLIFIERS = new RecipeMap<>(new HashSet<>(10), "uuamplifier", "basicmachines/Amplifabricator", 1, 1, 0, 0, 0, 0, 1, 1, true, 1, 1, true, new RecipeBuilder.AmplifierRecipeBuilder().EUt(32));

	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> DIESEL_FUELS = new RecipeMap<>(new HashSet<>(10), "dieselgeneratorfuel", "basicmachines/Default", 1, 1, 0, 4, 0, 0, 0, 0, true, 1, 1000, true, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> TURBINE_FUELS = new RecipeMap<>(new HashSet<>(10), "gasturbinefuel", "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, true, 1, 1000, true, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> HOT_FUELS = new RecipeMap<>(new HashSet<>(10), "thermalgeneratorfuel", "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, true, 1, 1000, false, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> DENSE_LIQUID_FUELS = new RecipeMap<>(new HashSet<>(10), "semifluidboilerfuels", "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, true, 1, 1000, true, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> PLASMA_FUELS = new RecipeMap<>(new HashSet<>(10), "plasmageneratorfuels", "basicmachines/Default", 0, 1, 0, 4, 0, 0, 0, 0, true, 1, 1000, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Use {@link RecipeBuilder#EUt(int)} to set EU/t produced
	 */
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> MAGIC_FUELS = new RecipeMap<>(new HashSet<>(10), "magicfuels", "basicmachines/Default", 1, 1, 0, 1, 0, 0, 0, 0, true, 1, 1000, true, new RecipeBuilder.DefaultRecipeBuilder() {
		@Override
		protected EnumValidationResult finalizeAndValidate(boolean checkForCollisions) {
			this.EUt = -Math.abs(EUt);
			return super.finalizeAndValidate(checkForCollisions);
		}
	});

	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> SMALL_NAQUADAH_REACTOR_FUELS = new RecipeMap<>(new HashSet<>(10), "smallnaquadahreactor", "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1000, true, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> LARGE_NAQUADAH_REACTOR_FUELS = new RecipeMap<>(new HashSet<>(10), "largenaquadahreactor", "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1000, true, new RecipeBuilder.DefaultRecipeBuilder());
	public static final RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> FLUID_NAQUADAH_REACTOR_FUELS = new RecipeMap<>(new HashSet<>(10), "fluidnaquadahreactor", "basicmachines/Default", 1, 1, 1, 1, 0, 0, 0, 0, true, 1, 1000, true, new RecipeBuilder.DefaultRecipeBuilder());

	/**
	 * Example:
	 * <pre>
	 * 			RecipeBuilder.AssemblyLineRecipeBuilder.start()
	 *					.researchItem(ItemList.Sensor_ZPM.get(1))
	 *					.researchTime(288000)
	 *					.inputs(OreDictUnifier.get(OrePrefix.frameGt, Materials.Neutronium, 1L),
	 *								ItemList.Sensor_ZPM.get(1),
	 *								ItemList.Sensor_LuV.get(2),
	 *								ItemList.Sensor_IV.get(4),
	 *								OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 7L),
	 *								OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64L),
	 *								OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64L),
	 *								OreDictUnifier.get(OrePrefix.foil, Materials.Osmiridium, 64L),
	 *								OreDictUnifier.get(OrePrefix.cableGt04, Materials.NiobiumTitanium, 7L))
	 *					.fluidInputs(Materials.SolderingAlloy.getMolten(576))
	 *					.output(ItemList.Sensor_UV.get(1))
	 *					.duration(600)
	 *					.EUt(100000)
	 *					.buildAndRegister();
	 * </pre>
	 */
	public static final ArrayList<Recipe.AssemblyLineRecipe> ASSEMBLYLINE_RECIPES = new ArrayList<>();

	/**
	 * HashMap of Recipes based on their Item inputs
	 */
	protected final Map<SimpleItemStack, Collection<T>> recipeItemMap = new HashMap<>();
	/**
	 * HashMap of Recipes based on their Fluid inputs
	 */
	protected final Map<Fluid, Collection<T>> recipeFluidMap = new HashMap<>();
	/**
	 * The List of all Recipes
	 */
	protected final Collection<T> recipeList;
	/**
	 * String used as an unlocalised Name.
	 */
	protected final String unlocalizedName;

	protected final R defaultRecipe;

	// Stuff that is used to validate recipes
	protected final int minInputs, maxInputs;
	protected final int minOutputs, maxOutputs;
	protected final int minFluidInputs, maxFluidInputs;
	protected final int minFluidOutputs, maxFluidOutputs;

	protected final int amperage;

	/**
	 * GUI used for JEI Display. Usually the GUI of the Machine itself
	 */
	protected final String JEIGUIPath;

	protected final int JEISpecialValueMultiplier;

	protected final boolean JEIAllowed;
	protected final boolean showVoltageAmperageInJEI;

	/**
	 * Initialises a new type of Recipe Handler.
	 * @param recipeList                a List you specify as Recipe List. Usually just an ArrayList with a pre-initialised Size.
	 * @param unlocalizedName           the unlocalised Name of this Recipe Handler, used mainly for JEI.
	 * @param JEIGUIPath                the displayed GUI Texture, usually just a Machine GUI. Auto-Attaches ".png" if forgotten.
	 * @param JEISpecialValueMultiplier the Value the Special Value is getting Multiplied with before displaying
	 * @param JEIAllowed                if JEI is allowed to display this Recipe Handler in general.
	 */
	public RecipeMap(Collection<T> recipeList, String unlocalizedName, String JEIGUIPath,
					 int minInputs, int maxInputs, int minOutputs, int maxOutputs,
					 int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs,
					 boolean showVoltageAmperageInJEI, int amperage, int JEISpecialValueMultiplier,
					 boolean JEIAllowed, R defaultRecipe) {
		RECIPE_MAPS.add(this);
		this.JEIAllowed = JEIAllowed;
		this.showVoltageAmperageInJEI = showVoltageAmperageInJEI;
		this.recipeList = recipeList;
		this.JEIGUIPath = GTValues.MODID + ":textures/gui/" + (JEIGUIPath.endsWith(".png") ? JEIGUIPath : JEIGUIPath + ".png");
		this.JEISpecialValueMultiplier = JEISpecialValueMultiplier;
		this.amperage = amperage;

		defaultRecipe.setRecipeMap(this);
		this.defaultRecipe = defaultRecipe;

		this.minInputs = minInputs;
		this.minFluidInputs = minFluidInputs;
		this.minOutputs = minOutputs;
		this.minFluidOutputs = minFluidOutputs;

		this.maxInputs = maxInputs;
		this.maxFluidInputs = maxFluidInputs;
		this.maxOutputs = maxOutputs;
		this.maxFluidOutputs = maxFluidOutputs;

		this.unlocalizedName = unlocalizedName;
	}

	public static boolean foundInvalidRecipe = false;

	protected void addRecipe(ValidationResult<T> validationResult) {

		switch (validationResult.getType()) {
			case SKIP:
				return;
			case INVALID:
				foundInvalidRecipe = true;
				return;
		}

		T recipe = validationResult.getResult();
		recipeList.add(recipe);

		for (ItemStack stack : recipe.getInputs()) {
			recipeItemMap.computeIfAbsent(new SimpleItemStack(stack), k -> new HashSet<>(1)).add(recipe);
		}

		for (FluidStack fluid : recipe.getFluidInputs()) {
			recipeFluidMap.computeIfAbsent(fluid.getFluid(), k -> new HashSet<>(1)).add(recipe);
		}
	}

	/**
	 * @return if this Item is a valid Input for any for the Recipes
	 */
	public boolean containsInput(ItemStack stack) {
		return stack != null && (recipeItemMap.containsKey(new SimpleItemStack(stack)) || recipeItemMap.containsKey(new SimpleItemStack(stack.getItem(), W)));
	}

	/**
	 * @return if this Fluid is a valid Input for any for the Recipes
	 */
	public boolean containsInput(FluidStack fluid) {
		return fluid != null && containsInput(fluid.getFluid());
	}

	/**
	 * @return if this Fluid is a valid Input for any for the Recipes
	 */
	public boolean containsInput(Fluid fluid) {
		return fluid != null && recipeFluidMap.containsKey(fluid);
	}

    @Nullable
    public T findRecipe(@Nullable TileEntity tileEntity, @Nullable T inputRecipe, boolean notUnificated, long voltage, IItemHandlerModifiable inputs, IMultipleTankHandler fluidInputs) {
        List<FluidStack> fluidStacks = new ArrayList<>(fluidInputs.getTanks());
        for (int i = 0; i < fluidInputs.getTanks(); i++) {
            fluidStacks.add(fluidInputs.getFluidInTank(i));
        }
        NonNullList<ItemStack> stacks = NonNullList.create();
        for (int i = 0; i < inputs.getSlots(); i++) {
            stacks.add(inputs.getStackInSlot(i));
        }
        return this.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, stacks, fluidStacks);
    }

	@Nullable
	public T findRecipe(TileEntity tileEntity, boolean notUnificated, long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluids) {
		return findRecipe(tileEntity, null, notUnificated, voltage, inputs, fluids);
	}

	/**
	 * Finds a Recipe matching the Fluid and ItemStack Inputs.
	 *
	 * @param tileEntity    an Object representing the current coordinates of the executing Block/Entity/Whatever. This may be null, especially during Startup.
	 * @param inputRecipe        in case this is != null it will try to use this Recipe first when looking things up.
	 * @param notUnificated if this is true the Recipe searcher will unificate the ItemStack Inputs
	 * @param voltage       Voltage of the Machine or Long.MAX_VALUE if it has no Voltage
	 * @param inputs        the Item Inputs
	 * @param fluidInputs   the Fluid Inputs
	 * @return the Recipe it has found or null for no matching Recipe
	 */
	@Nullable
	public T findRecipe(@Nullable TileEntity tileEntity, @Nullable T inputRecipe, boolean notUnificated, long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
		// No Recipes? Well, nothing to be found then.
		if (recipeList.isEmpty()) return null;

		// Some Recipe Classes require a certain amount of Inputs of certain kinds. Like "at least 1 Fluid + 1 Stack" or "at least 2 Stacks" before they start searching for Recipes.
		// This improves Performance massively, especially if people leave things like Circuits, Molds or Shapes in their Machines to select Sub Recipes.
//		if (GregTechAPI.sPostloadFinished) {
			if (minFluidInputs > 0) {
				if (fluidInputs == null) {
					return null;
				}
				int amount = 0;
				for (FluidStack fluid : fluidInputs) {
					if (fluid != null) {
						amount++;
					}
				}
				if (amount < minFluidInputs) {
					return null;
				}
			}

			if (minInputs > 0) {
				if (inputs == null) {
					return null;
				}
				int amount = 0;
				for (ItemStack stack : inputs) {
					if (stack != null) {
						amount++;
					}
				}
				if (amount < minInputs) {
					return null;
				}
			}
//		}

		// Check the Recipe which has been used last time in order to not have to search for it again, if possible.
		if (inputRecipe != null) {
			if (inputRecipe.canBeBuffered() && inputRecipe.isRecipeInputEqual(false, true, inputs, fluidInputs)) {
				return voltage * amperage >= inputRecipe.getEUt() ? inputRecipe : null;
			}
		}

		// Now look for the Recipes inside the Item HashMaps, but only when the Recipes usually have Items.
		if (maxInputs > 0) {
			T recipe = findByItemInput(inputs, fluidInputs);
			if (recipe != null) {
				return voltage * amperage >= recipe.getEUt() ? inputRecipe : null;
			}
		}

		// Unification happens here in case the Input isn't already unificated.
		if (notUnificated) {
			for (int i = 0; i < inputs.size(); i++) {
				if (!inputs.get(i).isEmpty()) {
					inputs.set(i, OreDictUnifier.getUnificated(inputs.get(i)));
				}
			}

			// Same as above but with unificated inputs
			if (inputRecipe != null) {
				if (inputRecipe.canBeBuffered() && inputRecipe.isRecipeInputEqual(false, true, inputs, fluidInputs)) {
					return voltage * amperage >= inputRecipe.getEUt() ? inputRecipe : null;
				}
			}

			if (maxInputs > 0) {
				T recipe = findByItemInput(inputs, fluidInputs);
				if (recipe != null) {
					return voltage * amperage >= recipe.getEUt() ? recipe : null;
				}
			}
		}

		// If the minimal Amount of Items for the Recipe is 0, then it could be a Fluid-Only Recipe, so check that Map too.
		if (maxInputs == 0 && fluidInputs != null) {
			for (FluidStack fluid : fluidInputs) {
				if (fluid != null) {
					Collection<T> recipes = recipeFluidMap.get(fluid.getFluid());
					if (recipes != null) {
						for (T tmpRecipe : recipes) {
							if (tmpRecipe.isRecipeInputEqual(false, true, inputs, fluidInputs)) {
								return voltage * amperage >= tmpRecipe.getEUt() ? tmpRecipe : null;
							}
						}
					}
				}
			}
		}

		// And nothing has been found.
		return null;
	}

	@Nullable
	private T findByItemInput(NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
		for (ItemStack stack : inputs) {
			if (stack != null) {
				Collection<T> recipes = recipeItemMap.get(new SimpleItemStack(stack));
				if (recipes != null) {
					for (T tmpRecipe : recipes) {
						if (tmpRecipe.isRecipeInputEqual(false, true, inputs, fluidInputs)) {
							return tmpRecipe;
						}
					}
				}
				recipes = recipeItemMap.get(new SimpleItemStack(stack.getItem(), W));
				if (recipes != null) {
					for (T tmpRecipe : recipes) {
						if (tmpRecipe.isRecipeInputEqual(false, true, inputs, fluidInputs)) {
							return tmpRecipe;
						}
					}
				}
			}
		}
		return null;
	}

	public R recipeBuilder() {
		return defaultRecipe.copy();
	}

	///////////////////
	//    Getters    //
	///////////////////

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public int getMinInputs() {
		return minInputs;
	}

	public int getMaxInputs() {
		return maxInputs;
	}

	public int getMinOutputs() {
		return minOutputs;
	}

	public int getMaxOutputs() {
		return maxOutputs;
	}

	public int getMinFluidInputs() {
		return minFluidInputs;
	}

	public int getMaxFluidInputs() {
		return maxFluidInputs;
	}

	public int getMinFluidOutputs() {
		return minFluidOutputs;
	}

	public int getMaxFluidOutputs() {
		return maxFluidOutputs;
	}

	public int getAmperage() {
		return amperage;
	}

	public String getJEIGUIPath() {
		return JEIGUIPath;
	}

	public int getJEISpecialValueMultiplier() {
		return JEISpecialValueMultiplier;
	}

	public boolean isJEIAllowed() {
		return JEIAllowed;
	}

	public boolean doShowVoltageAmperageInJEI() {
		return showVoltageAmperageInJEI;
	}

	public static class FakeRecipeMap<T extends Recipe, R extends RecipeBuilder<T, R>> extends RecipeMap<T, R> {

		public FakeRecipeMap(Collection<T> recipeList, String unlocalizedName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, boolean showVoltageAmperageInJEI, int amperage, int JEISpecialValueMultiplier, boolean JEIAllowed, R defaultRecipe) {
			super(recipeList, unlocalizedName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, showVoltageAmperageInJEI, amperage, JEISpecialValueMultiplier, JEIAllowed, defaultRecipe);
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			throw new UnsupportedOperationException("This should not get called on fake recipe map");
		}

		@Override
		public boolean containsInput(FluidStack fluid) {
			throw new UnsupportedOperationException("This should not get called on fake recipe map");
		}

		@Override
		public boolean containsInput(Fluid fluid) {
			throw new UnsupportedOperationException("This should not get called on fake recipe map");
		}

		@Override
		public T findRecipe(TileEntity tileEntity, T inputRecipe, boolean notUnificated, long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
			throw new UnsupportedOperationException("This should not get called on fake recipe map");
		}
	}

	/**
	 * Abstract Class for general Recipe Handling of non GT Recipes
	 */
	public static abstract class RecipeMapNonGTRecipes<T extends Recipe, R extends RecipeBuilder<T, R>> extends RecipeMap<T, R> {

		public RecipeMapNonGTRecipes(Collection<T> recipeList, String unlocalizedName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, boolean showVoltageAmperageInJEI, int amperage, int JEISpecialValueMultiplier, boolean JEIAllowed, R defaultRecipe) {
			super(recipeList, unlocalizedName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, showVoltageAmperageInJEI, amperage, JEISpecialValueMultiplier, JEIAllowed, defaultRecipe);
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return false;
		}

		@Override
		public boolean containsInput(FluidStack fluid) {
			return false;
		}

		@Override
		public boolean containsInput(Fluid fluid) {
			return false;
		}

		@Override
		protected void addRecipe(ValidationResult<T> validationResult) {
		}
	}

	/**
	 * Special Class for Furnace Recipe handling.
	 */
	public static class RecipeMapFurnace extends RecipeMapNonGTRecipes<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapFurnace(Collection<Recipe> recipeList, String unlocalizedName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, int amperage, int JEISpecialValueMultiplier, boolean showVoltageAmperageInJEI, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, showVoltageAmperageInJEI, amperage, JEISpecialValueMultiplier, JEIAllowed, defaultRecipe);
		}

		@Override
		@Nullable
		public Recipe findRecipe(TileEntity tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
			if (inputs == null || inputs.size() <= 0 || inputs.get(0).isEmpty()) return null;
//			if (inputRecipe != null && inputRecipe.isRecipeInputEqual(false, true, inputs, fluidInputs)) return inputRecipe; // TODO this is doubling output stack size
			ItemStack output = ModHandler.getSmeltingOutput(inputs.get(0));
			return output.isEmpty() ? null : this.recipeBuilder()
					.notOptimized()
					.inputs(GTUtility.copyAmount(1, inputs.get(0)))
					.outputs(output)
					.duration(128)
					.EUt(4)
					.build(false)
					.getResult();
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return !ModHandler.getSmeltingOutput(stack).isEmpty();
		}
	}

	/**
	 * Special Class for Microwave Recipe handling.
	 */
	public static class RecipeMapMicrowave extends RecipeMapNonGTRecipes<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapMicrowave(Collection<Recipe> recipeList, String unlocalizedName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, boolean showVoltageAmperageInJEI, int amperage, int JEISpecialValueMultiplier, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, showVoltageAmperageInJEI, amperage, JEISpecialValueMultiplier, JEIAllowed, defaultRecipe);
		}

		@Override
		@Nullable
		public Recipe findRecipe(TileEntity tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
			if (inputs == null || inputs.size() <= 0 || inputs.get(0).isEmpty()) return null;
			if (inputRecipe != null && inputRecipe.isRecipeInputEqual(false, true, inputs, fluidInputs)) return inputRecipe;
			ItemStack output = ModHandler.getSmeltingOutput(inputs.get(0));

//			if (GTUtility.areStacksEqual(inputs[0], new ItemStack(Items.BOOK, 1, W))) {
//				return this.recipeBuilder()
//						.notOptimized()
//						.inputs(GTUtility.copyAmount(1, inputs[0]))
//						.outputs(GTUtility.getWrittenBook("Manual_Microwave", ItemList.Book_Written_03.get(1)))
//						.duration(32)
//						.EUt(4)
//						.build();
//			}

			for (ItemStack stack : new ItemStack[]{inputs.get(0), output}) {
				if (!output.isEmpty()) {
					if (stack.getItem() == Item.getItemFromBlock(Blocks.NETHERRACK)
							|| stack.getItem() == Item.getItemFromBlock(Blocks.TNT)
							|| stack.getItem() == Items.EGG
							|| stack.getItem() == Items.FIREWORK_CHARGE
							|| stack.getItem() == Items.FIRE_CHARGE
							|| stack.getItem() == Items.FIREWORKS
							) {
						if (tileEntity instanceof GregtechTileEntity)
							((GregtechTileEntity) tileEntity).getMetaTileEntity().doExplosion(voltage * 4);
						return null;
					}
					MaterialStack materialStack = OreDictUnifier.getMaterial(stack);

					if (materialStack != null) {
						if (materialStack.material != null && materialStack.material != null) {
							if (materialStack.material instanceof MetalMaterial
									|| materialStack.material.hasFlag(Material.MatFlags.EXPLOSIVE)) {
								if (tileEntity instanceof GregtechTileEntity)
									((GregtechTileEntity) tileEntity).getMetaTileEntity().doExplosion(voltage * 4);
								return null;
							}
							if (materialStack.material.hasFlag(Material.MatFlags.FLAMMABLE)) {
								GTUtility.setCoordsOnFire(tileEntity.getWorld(), tileEntity.getPos());
								return null;
							}
						}
						for (MaterialStack material : OreDictUnifier.getByProducts(stack))
							if (material != null) {
								if (material.material instanceof MetalMaterial || material.material.hasFlag(Material.MatFlags.EXPLOSIVE)) {
									if (tileEntity instanceof GregtechTileEntity)
										((GregtechTileEntity) tileEntity).getMetaTileEntity().doExplosion(voltage * 4);
									return null;
								}
								if (material.material.hasFlag(Material.MatFlags.FLAMMABLE)) {
									GTUtility.setCoordsOnFire(tileEntity.getWorld(), tileEntity.getPos());
									return null;
								}
							}
					}
					if (TileEntityFurnace.getItemBurnTime(stack) > 0) {
						GTUtility.setCoordsOnFire(tileEntity.getWorld(), tileEntity.getPos());
						return null;
					}

				}
			}

			return output.isEmpty() ? null : new RecipeBuilder.DefaultRecipeBuilder(this.defaultRecipe)
					.notOptimized()
					.inputs(GTUtility.copyAmount(1, inputs.get(0)))
					.outputs(output)
					.duration(32)
					.EUt(4)
					.build()
					.getResult();
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return !ModHandler.getSmeltingOutput(stack).isEmpty();
		}
	}

	/**
	 * Special Class for Unboxinator handling.
	 */
	public static class RecipeMapUnboxinator extends RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapUnboxinator(Collection<Recipe> recipeList, String unlocalizedName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, boolean showVoltageAmperageInJEI, int amperage, int JEISpecialValueMultiplier, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, showVoltageAmperageInJEI, amperage, JEISpecialValueMultiplier, JEIAllowed, defaultRecipe);
		}

		@Override
		@Nullable
		public Recipe findRecipe(TileEntity tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
			if (inputs == null || inputs.size() <= 0 || !ModHandler.IC2.getScrapBox(1).isItemEqual(inputs.get(0)))
				return super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, inputs, fluidInputs);
			ItemStack output = Recipes.scrapboxDrops.getDrop(ModHandler.IC2.getScrapBox(1), false);
			if (output.isEmpty()) {
				return super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, inputs, fluidInputs);
			}
			return this.recipeBuilder()
					.notOptimized()
					.cannotBeBuffered() // It is not allowed to be buffered due to the random Output
					.needsEmptyOutput() // Due to its randomness it is not good if there are Items in the Output Slot, because those Items could manipulate the outcome.
					.inputs(ModHandler.IC2.getScrapBox(1))
					.outputs(output)
					.duration(16)
					.EUt(1)
					.build()
					.getResult();
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return ModHandler.IC2.getScrapBox(1).isItemEqual(stack) || super.containsInput(stack);
		}
	}

	/**
	 * Special Class for Fluid Canner handling.
	 */
	public static class RecipeMapFluidCanner extends RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapFluidCanner(Collection<Recipe> recipeList, String unlocalizedName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, boolean showVoltageAmperageInJEI, int amperage, int JEISpecialValueMultiplier, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, showVoltageAmperageInJEI, amperage, JEISpecialValueMultiplier, JEIAllowed, defaultRecipe);
		}

		@Override
		@Nullable
		public Recipe findRecipe(TileEntity tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
			Recipe recipe = super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, inputs, fluidInputs);
			if (inputs == null || inputs.size() <= 0 || inputs.get(0).isEmpty() || recipe != null /*|| !GregTechAPI.sPostloadFinished*/)
				return recipe;
			if (fluidInputs != null && fluidInputs.size() > 0 && fluidInputs.get(0) != null) {
//				ItemStack output = GTUtility.fillFluidContainer(fluidInputs[0], inputs[0], false, true);
//				FluidStack fluid = GTUtility.getFluidForFilledItem(output, true);
//				if (fluid != null) {
//					recipe = this.recipeBuilder()
//							.cannotBeBuffered()
//							.notOptimized()
//							.inputs(GTUtility.copyAmount(1, inputs[0]))
//							.outputs(output)
//							.fluidInputs(fluidInputs)
//							.duration(Math.max(fluid.amount / 64, 16))
//							.EUt(1)
//							.build();
//				}
			}
//			if (recipe == null) {
//				FluidStack fluid = GTUtility.getFluidForFilledItem(inputs[0], true);
//				if (fluid != null) {
//					recipe = this.recipeBuilder()
//							.cannotBeBuffered()
//							.notOptimized()
//							.inputs(GTUtility.copyAmount(1, inputs[0]))
//							.outputs(GTUtility.getContainerItem(inputs[0], true))
//							.fluidOutputs(fluidInputs)
//							.duration(Math.max(fluid.amount / 64, 16))
//							.EUt(1)
//							.build();
//				}
//			}
			return recipe;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return stack != null &&
                (super.containsInput(stack)
                    || (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
                        && stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null).drain(Integer.MAX_VALUE, true).amount > 0));
		}

		@Override
		public boolean containsInput(FluidStack fluid) {
			return true;
		}

		@Override
		public boolean containsInput(Fluid fluid) {
			return true;
		}
	}

	/**
	 * Special Class for Recycler Recipe handling.
	 */
	public static class RecipeMapRecycler extends RecipeMapNonGTRecipes<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapRecycler(Collection<Recipe> recipeList, String unlocalizedName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, boolean showVoltageAmperageInJEI, int amperage, int JEISpecialValueMultiplier, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, showVoltageAmperageInJEI, amperage, JEISpecialValueMultiplier, JEIAllowed, defaultRecipe);
		}

		@Override
		@Nullable
		public Recipe findRecipe(TileEntity tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
			if (inputs == null || inputs.size() <= 0 || inputs.get(0).isEmpty()) return null;
			if (inputRecipe != null && inputRecipe.isRecipeInputEqual(false, true, inputs, fluidInputs)) return inputRecipe;

			RecipeBuilder<Recipe, RecipeBuilder.DefaultRecipeBuilder> builder = this.recipeBuilder()
					.notOptimized()
					.inputs(GTUtility.copyAmount(1, inputs.get(0)))
					.duration(45)
					.EUt(1);

			if (!ModHandler.getRecyclerOutput(GTUtility.copyAmount(64, inputs.get(0)), 0).isEmpty()) {
				builder.chancedOutput(ModHandler.IC2.getScrap(1), 1250);
			}

			return builder.build().getResult();
		}
		@Override
		public boolean containsInput(ItemStack stack) {
			return !ModHandler.getRecyclerOutput(GTUtility.copyAmount(64, stack), 0).isEmpty();
		}
	}

	/**
	 * Special Class for Forming Press handling.
	 */
	public static class RecipeMapFormingPress extends RecipeMap<Recipe, RecipeBuilder.NotConsumableInputRecipeBuilder> {

		public RecipeMapFormingPress(Collection<Recipe> recipeList, String unlocalizedName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, boolean showVoltageAmperageInJEI, int amperage, int JEISpecialValueMultiplier, boolean JEIAllowed, RecipeBuilder.NotConsumableInputRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, showVoltageAmperageInJEI, amperage, JEISpecialValueMultiplier, JEIAllowed, defaultRecipe);
		}

		@Override
		@Nullable
		public Recipe findRecipe(TileEntity tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
			Recipe recipe = super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, inputs, fluidInputs);
			if (inputs == null || inputs.size() < 2 || inputs.get(0).isEmpty() || inputs.get(1).isEmpty() /*|| !GregTechAPI.sPostloadFinished*/)
				return recipe;
			if (recipe == null) {
				if (MetaItems.SHAPE_MOLD_NAME.getStackForm().isItemEqual(inputs.get(0))) {
					ItemStack output = GTUtility.copyAmount(1, inputs.get(1));
					output.setStackDisplayName(inputs.get(0).getDisplayName());

					return this.recipeBuilder()
							.cannotBeBuffered()
							.notOptimized()
							.notConsumable(MetaItems.SHAPE_MOLD_NAME)
							.inputs(GTUtility.copyAmount(1, inputs.get(1)))
							.outputs(output)
							.duration(128)
							.EUt(8)
							.build()
							.getResult();
				}
				if (MetaItems.SHAPE_MOLD_NAME.getStackForm().isItemEqual(inputs.get(1))) {
					ItemStack output = GTUtility.copyAmount(1, inputs.get(0));
					output.setStackDisplayName(inputs.get(1).getDisplayName());

					return this.recipeBuilder()
							.cannotBeBuffered()
							.notOptimized()
							.notConsumable(MetaItems.SHAPE_MOLD_NAME)
							.inputs(GTUtility.copyAmount(1, inputs.get(0)))
							.outputs(output)
							.duration(128)
							.EUt(8)
							.build()
							.getResult();
				}
				return null;
			}
			for (ItemStack mold : inputs) {
				if (MetaItems.SCHEMATIC_CRAFTING.getStackForm().isItemEqual(mold)) {
					NBTTagCompound tag = mold.getTagCompound();
					if (tag == null) tag = new NBTTagCompound();
					if (!tag.hasKey("credit_security_id")) tag.setLong("credit_security_id", System.nanoTime());
					mold.setTagCompound(tag);

					RecipeBuilder<?,?> builder = this.recipeBuilder()
							.fromRecipe(recipe)
							.cannotBeBuffered();

					List<ItemStack> outputs = builder.getOutputs();
					ItemStack stack = outputs.get(0);
					stack.setTagCompound(tag);
					outputs.set(0, stack);

					return builder.build().getResult();
				}
			}
			return recipe;
		}
	}

	/**
	 * Special Class for Printer handling.
	 */
	public static class RecipeMapPrinter extends RecipeMap<Recipe, RecipeBuilder.DefaultRecipeBuilder> {

		public RecipeMapPrinter(Collection<Recipe> recipeList, String unlocalizedName, String JEIGUIPath, int minInputs, int maxInputs, int minOutputs, int maxOutputs, int minFluidInputs, int maxFluidInputs, int minFluidOutputs, int maxFluidOutputs, boolean showVoltageAmperageInJEI, int amperage, int JEISpecialValueMultiplier, boolean JEIAllowed, RecipeBuilder.DefaultRecipeBuilder defaultRecipe) {
			super(recipeList, unlocalizedName, JEIGUIPath, minInputs, maxInputs, minOutputs, maxOutputs, minFluidInputs, maxFluidInputs, minFluidOutputs, maxFluidOutputs, showVoltageAmperageInJEI, amperage, JEISpecialValueMultiplier, JEIAllowed, defaultRecipe);
		}

		@Override
		@Nullable
		public Recipe findRecipe(TileEntity tileEntity, Recipe inputRecipe, boolean notUnificated, long voltage, NonNullList<ItemStack> inputs, List<FluidStack> fluidInputs) {
			Recipe recipe = super.findRecipe(tileEntity, inputRecipe, notUnificated, voltage, inputs, fluidInputs);
			if (inputs == null || inputs.size() <= 0 || inputs.get(0).isEmpty()
                    || fluidInputs == null || fluidInputs.size() <= 0 || fluidInputs.get(0) == null/*|| !GregTechAPI.sPostloadFinished*/)
				return recipe;

			EnumDyeColor color = GregTechAPI.LIQUID_DYE_MAP.inverse().get(fluidInputs.get(0).getFluid());
			if (color != null) return recipe;

			if (recipe == null) {
				ItemStack output = ModHandler.getRecipeOutput(tileEntity == null ? null : tileEntity.getWorld(),
                    inputs.get(0), inputs.get(0), inputs.get(0),
                    inputs.get(0), MetaItems.DYE_ONLY_ITEMS[color.getMetadata()].getStackForm(), inputs.get(0),
                    inputs.get(0), inputs.get(0), inputs.get(0));
				if (!output.isEmpty()) {
					ValidationResult<Recipe> outputRecipe = this.recipeBuilder()
							.inputs(GTUtility.copyAmount(8, inputs.get(0)))
							.outputs(output)
							.fluidInputs(new FluidStack(fluidInputs.get(0).getFluid(), L))
							.duration(256)
							.EUt(2)
							.build();
					this.addRecipe(outputRecipe);

					return outputRecipe.getResult();
				}

				output = ModHandler.getRecipeOutput(tileEntity == null ? null : tileEntity.getWorld(), inputs.get(0), MetaItems.DYE_ONLY_ITEMS[color.getMetadata()].getStackForm());
				if (!output.isEmpty()) {
					ValidationResult<Recipe> outputRecipe = this.recipeBuilder()
							.inputs(GTUtility.copyAmount(1, inputs.get(0)))
							.outputs(output)
							.fluidInputs(new FluidStack(fluidInputs.get(0).getFluid(), L))
							.duration(32)
							.EUt(2)
							.build();

					this.addRecipe(outputRecipe);
					return outputRecipe.getResult();

				}
			} else {
				if (inputs.get(0).getItem() == Items.WRITTEN_BOOK) {
					if (!MetaItems.TOOL_DATASTICK.getStackForm().isItemEqual(inputs.get(0))) return null;
					NBTTagCompound tag = inputs.get(0).getTagCompound();
					if (tag == null || !GTUtility.isStringValid(tag.getString("title")) || !GTUtility.isStringValid(tag.getString("author")))
						return null;

					RecipeBuilder<?,?> builder = this.recipeBuilder()
							.fromRecipe(recipe)
							.cannotBeBuffered();

					List<ItemStack> outputs = builder.getOutputs();
					ItemStack stack = outputs.get(0);
					stack.setTagCompound(tag);
					outputs.set(0, stack);

					return builder.build().getResult();

				}
				if (inputs.get(0).getItem() == Items.FILLED_MAP) {
					if (!MetaItems.TOOL_DATASTICK.getStackForm().isItemEqual(inputs.get(0))) return null;
					NBTTagCompound tag = inputs.get(0).getTagCompound();
					if (tag == null || !tag.hasKey("map_id")) return null;

					RecipeBuilder<?,?> builder = this.recipeBuilder()
							.fromRecipe(recipe)
							.cannotBeBuffered();

					List<ItemStack> outputs = builder.getOutputs();
					ItemStack stack = outputs.get(0);
					stack.setItemDamage(tag.getShort("map_id"));
					outputs.set(0, stack);

					return builder.build().getResult();
				}
				if (MetaItems.PAPER_PUNCH_CARD_EMPTY.getStackForm().isItemEqual(inputs.get(0))) {
					if (!MetaItems.TOOL_DATASTICK.getStackForm().isItemEqual(inputs.get(0))) return null;
					NBTTagCompound tag = inputs.get(0).getTagCompound();
					if (tag == null || !tag.hasKey("GT.PunchCardData")) return null;

					RecipeBuilder<?,?> builder = this.recipeBuilder()
							.fromRecipe(recipe)
							.cannotBeBuffered();

					List<ItemStack> outputs = builder.getOutputs();
					ItemStack stack = outputs.get(0);

					NBTTagCompound tagCompound = new NBTTagCompound();
					tagCompound.setString("GT.PunchCardData", tagCompound.getString("GT.PunchCardData"));
					stack.setTagCompound(tagCompound);

					outputs.set(0, stack);
					return builder.build().getResult();
				}
			}
			return recipe;
		}

		@Override
		public boolean containsInput(ItemStack stack) {
			return true;
		}

		@Override
		public boolean containsInput(FluidStack fluid) {
			return super.containsInput(fluid) || GregTechAPI.LIQUID_DYE_MAP.containsValue(fluid.getFluid());
		}

		@Override
		public boolean containsInput(Fluid fluid) {
			return super.containsInput(fluid) || GregTechAPI.LIQUID_DYE_MAP.containsValue(fluid);
		}
	}
}
