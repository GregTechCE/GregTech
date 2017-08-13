package gregtech.common.items;

import gregtech.api.ConfigCategories;
import gregtech.api.GregTech_API;
import gregtech.api.items.ItemList;
import gregtech.api.items.OreDictNames;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.ElectricStats;
import gregtech.api.items.metaitem.FluidStats;
import gregtech.api.items.metaitem.FoodStats;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.behaviors.Behaviour_DataOrb;
import gregtech.common.items.behaviors.Behaviour_DataStick;
import gregtech.common.items.behaviors.Behaviour_Lighter;
import gregtech.common.items.behaviors.Behaviour_PrintedPages;
import gregtech.common.items.behaviors.Behaviour_Scanner;
import gregtech.common.items.behaviors.Behaviour_SensorKit;
import gregtech.common.items.behaviors.Behaviour_Spray_Color;
import gregtech.common.items.behaviors.Behaviour_WrittenBook;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class MetaItem1 extends MaterialMetaItem {

	private final static String textEmptyRow = "   ";
	private final static String textShape = " P ";

	public MetaItem1() {
		super("metaitem.01",
				OrePrefix.dustTiny, OrePrefix.dustSmall, OrePrefix.dust, OrePrefix.dustImpure, OrePrefix.dustPure,
				OrePrefix.crushed, OrePrefix.crushedPurified, OrePrefix.crushedCentrifuged,
				OrePrefix.gem, OrePrefix.nugget, null,
				OrePrefix.ingot, OrePrefix.ingotHot, OrePrefix.ingotDouble, OrePrefix.ingotTriple, OrePrefix.ingotQuadruple, OrePrefix.ingotQuintuple,
				OrePrefix.plate, OrePrefix.plateDouble, OrePrefix.plateTriple, OrePrefix.plateQuadruple, OrePrefix.plateQuintuple, OrePrefix.plateDense,
				OrePrefix.stick, OrePrefix.lens, OrePrefix.round, OrePrefix.bolt, OrePrefix.screw, OrePrefix.ring, OrePrefix.foil,
				OrePrefix.cell, OrePrefix.cellPlasma);

		//it's here because OrePrefix.cell is handled by gt.metaitem.01
		ItemList.Cell_Empty.set(OreDictionaryUnifier.get(OrePrefix.cell, null));
		ItemList.Cell_Air.set(OreDictionaryUnifier.get(OrePrefix.cell, Materials.Air));
		ItemList.Cell_Water.set(OreDictionaryUnifier.get(OrePrefix.cell, Materials.Water));
		ItemList.Cell_Lava.set(OreDictionaryUnifier.get(OrePrefix.cell, Materials.Lava));

		OrePrefix.cellPlasma.containerItem = ItemList.Cell_Empty.get(1);
		OrePrefix.cell.containerItem = ItemList.Cell_Empty.get(1);

		//17000 -> OrePrefix.plate
		int woodItemMeta = 17000 + Material.MATERIAL_REGISTRY.getIDForObject(Materials.Wood);
		addItem(woodItemMeta).setBurnValue(1600);
		ModHandler.addCompressionRecipe(OreDictionaryUnifier.getDust(Materials.Wood, 8), new ItemStack(this, 1, woodItemMeta));

		ItemStack stack = new ItemStack(this, 1, woodItemMeta);
		stack.setStackDisplayName("The holy Planks of Sengir");
		GT_Utility.ItemNBT.addEnchantment(stack, Enchantments.SMITE, 10);
		ModHandler.addCraftingRecipe(stack, ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{"XXX", "XDX", "XXX",
						'X', OreDictionaryUnifier.getGem(Materials.NetherStar, 1),
						'D', new ItemStack(Blocks.DRAGON_EGG, 1, OreDictionary.WILDCARD_VALUE)});

		ItemList.Credit_Greg_Copper.set(addItem(0)); //"Copper GT Credit", "0.125 Credits"
		ItemList.Credit_Greg_Cupronickel.set(addItem(1).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Cupronickel, 907200L)))); //"Cupronickel GT Credit", "1 Credit"
		ItemList.Credit_Greg_Silver.set(addItem(2)); //"Silver GT Credit", "8 Credits"
		ItemList.Credit_Greg_Gold.set(addItem(3)); //"Gold GT Credit", "64 Credits"
		ItemList.Credit_Greg_Platinum.set(addItem(4)); //"Platinum GT Credit", "512 Credits"
		ItemList.Credit_Greg_Osmium.set(addItem(5)); //"Osmium GT Credit", "4096 Credits"
		ItemList.Credit_Greg_Naquadah.set(addItem(6)); //"Naquadah GT Credit", "32768 Credits"
		ItemList.Credit_Greg_Neutronium.set(addItem(7)); //"Neutronium GT Credit", "262144 Credits"
		ItemList.Coin_Gold_Ancient.set(addItem(8).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Gold, 907200L)))); //"Ancient Gold Coin", "Found in ancient Ruins"
		ItemList.Coin_Doge.set(addItem(9).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Brass, 907200L)))); //"Doge Coin", "wow much coin how money so crypto plz mine v rich very currency wow",
		ItemList.Coin_Chocolate.set(addItem(10).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Gold, OrePrefix.foil.materialAmount))).addStats(new FoodStats(1, 0.1F, false, true, new PotionEffect(MobEffects.SPEED, 200, 1)/*, 10, OreDictionaryUnifier.get(OrePrefix.foil, Materials.Gold, 1)*/))); //"Chocolate Coin", "Wrapped in Gold",
		ItemList.Credit_Copper.set(addItem(11)); //"Industrial Copper Credit", "0.125 Credits"

		ItemList.Credit_Silver.set(addItem(13)); //"Industrial Silver Credit", "8 Credits"
		ItemList.Credit_Gold.set(addItem(14)); //"Industrial Gold Credit", "64 Credits"
		ItemList.Credit_Platinum.set(addItem(15)); //"Industrial Platinum Credit", "512 Credits"
		ItemList.Credit_Osmium.set(addItem(16)); //"Industrial Osmium Credit", "4096 Credits"

		ModHandler.addShapelessCraftingRecipe(ItemList.Coin_Chocolate.get(1),
				new Object[]{new UnificationEntry(OrePrefix.dust, Materials.Cocoa),
						new UnificationEntry(OrePrefix.dust, Materials.Milk),
						new UnificationEntry(OrePrefix.dust, Materials.Sugar),
						new UnificationEntry(OrePrefix.foil, Materials.Gold)});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Copper.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Iron});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Iron.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Silver});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Silver.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Gold});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Gold.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Platinum});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Platinum.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Osmium});


		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Iron.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Copper,
						ItemList.Credit_Copper,
						ItemList.Credit_Copper,
						ItemList.Credit_Copper,
						ItemList.Credit_Copper,
						ItemList.Credit_Copper,
						ItemList.Credit_Copper,
						ItemList.Credit_Copper});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Silver.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Iron,
						ItemList.Credit_Iron,
						ItemList.Credit_Iron,
						ItemList.Credit_Iron,
						ItemList.Credit_Iron,
						ItemList.Credit_Iron,
						ItemList.Credit_Iron,
						ItemList.Credit_Iron});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Gold.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Silver,
						ItemList.Credit_Silver,
						ItemList.Credit_Silver,
						ItemList.Credit_Silver,
						ItemList.Credit_Silver,
						ItemList.Credit_Silver,
						ItemList.Credit_Silver,
						ItemList.Credit_Silver});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Platinum.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Gold,
						ItemList.Credit_Gold,
						ItemList.Credit_Gold,
						ItemList.Credit_Gold,
						ItemList.Credit_Gold,
						ItemList.Credit_Gold,
						ItemList.Credit_Gold,
						ItemList.Credit_Gold});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Osmium.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Platinum,
						ItemList.Credit_Platinum,
						ItemList.Credit_Platinum,
						ItemList.Credit_Platinum,
						ItemList.Credit_Platinum,
						ItemList.Credit_Platinum,
						ItemList.Credit_Platinum,
						ItemList.Credit_Platinum});


		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Copper.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Cupronickel});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Cupronickel.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Silver});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Silver.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Gold});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Gold.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Platinum});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Platinum.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Osmium});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Osmium.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Naquadah});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Naquadah.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Neutronium});


		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Cupronickel.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Copper,
						ItemList.Credit_Greg_Copper,
						ItemList.Credit_Greg_Copper,
						ItemList.Credit_Greg_Copper,
						ItemList.Credit_Greg_Copper,
						ItemList.Credit_Greg_Copper,
						ItemList.Credit_Greg_Copper,
						ItemList.Credit_Greg_Copper});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Silver.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Cupronickel,
						ItemList.Credit_Greg_Cupronickel,
						ItemList.Credit_Greg_Cupronickel,
						ItemList.Credit_Greg_Cupronickel,
						ItemList.Credit_Greg_Cupronickel,
						ItemList.Credit_Greg_Cupronickel,
						ItemList.Credit_Greg_Cupronickel,
						ItemList.Credit_Greg_Cupronickel});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Gold.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Silver,
						ItemList.Credit_Greg_Silver,
						ItemList.Credit_Greg_Silver,
						ItemList.Credit_Greg_Silver,
						ItemList.Credit_Greg_Silver,
						ItemList.Credit_Greg_Silver,
						ItemList.Credit_Greg_Silver,
						ItemList.Credit_Greg_Silver});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Platinum.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Gold,
						ItemList.Credit_Greg_Gold,
						ItemList.Credit_Greg_Gold,
						ItemList.Credit_Greg_Gold,
						ItemList.Credit_Greg_Gold,
						ItemList.Credit_Greg_Gold,
						ItemList.Credit_Greg_Gold,
						ItemList.Credit_Greg_Gold});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Osmium.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Platinum,
						ItemList.Credit_Greg_Platinum,
						ItemList.Credit_Greg_Platinum,
						ItemList.Credit_Greg_Platinum,
						ItemList.Credit_Greg_Platinum,
						ItemList.Credit_Greg_Platinum,
						ItemList.Credit_Greg_Platinum,
						ItemList.Credit_Greg_Platinum});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Naquadah.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Osmium,
						ItemList.Credit_Greg_Osmium,
						ItemList.Credit_Greg_Osmium,
						ItemList.Credit_Greg_Osmium,
						ItemList.Credit_Greg_Osmium,
						ItemList.Credit_Greg_Osmium,
						ItemList.Credit_Greg_Osmium,
						ItemList.Credit_Greg_Osmium});

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Neutronium.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Credit_Greg_Naquadah,
						ItemList.Credit_Greg_Naquadah,
						ItemList.Credit_Greg_Naquadah,
						ItemList.Credit_Greg_Naquadah,
						ItemList.Credit_Greg_Naquadah,
						ItemList.Credit_Greg_Naquadah,
						ItemList.Credit_Greg_Naquadah,
						ItemList.Credit_Greg_Naquadah});


		ItemList.Component_Minecart_Wheels_Iron.set(addItem(100)); // "Iron Minecart Wheels", "To get things rolling"
		ItemList.Component_Minecart_Wheels_Steel.set(addItem(101)); //"Steel Minecart Wheels", "To get things rolling"

		ModHandler.addCraftingRecipe(ItemList.Component_Minecart_Wheels_Iron.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{" h ", "RSR", " w ",
						'R', new UnificationEntry(OrePrefix.ring, Materials.AnyIron),
						'S', new UnificationEntry(OrePrefix.stick, Materials.AnyIron)});

		ModHandler.addCraftingRecipe(ItemList.Component_Minecart_Wheels_Steel.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{" h ", "RSR", " w ",
						'R', new UnificationEntry(OrePrefix.ring, Materials.Steel),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Steel)});


		ItemList.Shape_Empty.set(addItem(300)); // "Empty Shape Plate", "Raw Plate to make Molds and Extruder Shapes"

		ModHandler.addCraftingRecipe(ItemList.Shape_Empty.get(1),
				ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"hf", "PP", "PP",
						'P', new UnificationEntry(OrePrefix.plate, Materials.Steel)});

		ItemList.Shape_Mold_Plate.set(addItem(301)); //"Mold (Plate)", "Mold for making Plates"
		ItemList.Shape_Mold_Casing.set(addItem(302)); //"Mold (Casing)", "Mold for making Item Casings"
		ItemList.Shape_Mold_Gear.set(addItem(303)); //"Mold (Gear)", "Mold for making Gears"
		ItemList.Shape_Mold_Credit.set(addItem(304)); //"Mold (Coinage)", "Secure Mold for making Coins (Don't lose it!)"
		ItemList.Shape_Mold_Bottle.set(addItem(305)); //"Mold (Bottle)", "Mold for making Bottles"
		ItemList.Shape_Mold_Ingot.set(addItem(306)); //"Mold (Ingot)", "Mold for making Ingots"
		ItemList.Shape_Mold_Ball.set(addItem(307)); //"Mold (Ball)", "Mold for making Balls"
		ItemList.Shape_Mold_Block.set(addItem(308)); //"Mold (Block)", "Mold for making Blocks"
		ItemList.Shape_Mold_Nugget.set(addItem(309)); //"Mold (Nuggets)", "Mold for making Nuggets"
		ItemList.Shape_Mold_Bun.set(addItem(310)); //"Mold (Buns)", "Mold for shaping Buns"
		ItemList.Shape_Mold_Bread.set(addItem(311)); //"Mold (Bread)", "Mold for shaping Breads"
		ItemList.Shape_Mold_Baguette.set(addItem(312)); //"Mold (Baguette)", "Mold for shaping Baguettes"
		ItemList.Shape_Mold_Cylinder.set(addItem(313)); //"Mold (Cylinder)", "Mold for shaping Cylinders"
		ItemList.Shape_Mold_Anvil.set(addItem(314)); //"Mold (Anvil)", "Mold for shaping Anvils"
		ItemList.Shape_Mold_Name.set(addItem(315)); //"Mold (Name)", "Mold for naming Items (rename Mold with Anvil)"
		ItemList.Shape_Mold_Gear_Small.set(addItem(317)); //"Mold (Small Gear)", "Mold for making small Gears"

		ModHandler.removeRecipe(new ItemStack(Blocks.GLASS), null, new ItemStack(Blocks.GLASS), null, new ItemStack(Blocks.GLASS));

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Credit.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"h  ", textShape, textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Plate.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{" h ", textShape, textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Casing.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"  h", textShape, textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Gear.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, " Ph", textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bottle.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, textShape, "  h",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Ingot.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, textShape, " h ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Ball.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, textShape, "h  ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Block.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, "hP ", textEmptyRow,
						'P', ItemList.Shape_Empty});


		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Nugget.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P h", textEmptyRow, textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bun.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P  ", "  h", textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bread.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P  ", textEmptyRow, "  h",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Baguette.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P  ", textEmptyRow, " h ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Cylinder.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"  P", textEmptyRow, "  h",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Anvil.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"  P", textEmptyRow, " h ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Name.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"  P", textEmptyRow, "h  ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Gear_Small.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, textEmptyRow, "h P",
						'P', ItemList.Shape_Empty});


		ItemList.Shape_Extruder_Plate.set(addItem(350)); //"Extruder Shape (Plate)", "Extruder Shape for making Plates"
		ItemList.Shape_Extruder_Rod.set(addItem(351)); //"Extruder Shape (Rod)", "Extruder Shape for making Rods"
		ItemList.Shape_Extruder_Bolt.set(addItem(352)); //"Extruder Shape (Bolt)", "Extruder Shape for making Bolts"
		ItemList.Shape_Extruder_Ring.set(addItem(353)); //"Extruder Shape (Ring)", "Extruder Shape for making Rings"
		ItemList.Shape_Extruder_Cell.set(addItem(354)); //"Extruder Shape (Cell)", "Extruder Shape for making Cells"
		ItemList.Shape_Extruder_Ingot.set(addItem(355)); //"Extruder Shape (Ingot)", "Extruder Shape for, wait, can't we just use a Furnace?"
		ItemList.Shape_Extruder_Wire.set(addItem(356)); //"Extruder Shape (Wire)", "Extruder Shape for making Wires"
		ItemList.Shape_Extruder_Casing.set(addItem(357)); //"Extruder Shape (Casing)", "Extruder Shape for making Item Casings"
		ItemList.Shape_Extruder_Pipe_Tiny.set(addItem(358)); //"Extruder Shape (Tiny Pipe)", "Extruder Shape for making tiny Pipes"
		ItemList.Shape_Extruder_Pipe_Small.set(addItem(359)); //"Extruder Shape (Small Pipe)", "Extruder Shape for making small Pipes"
		ItemList.Shape_Extruder_Pipe_Medium.set(addItem(360)); //"Extruder Shape (Normal Pipe)", "Extruder Shape for making Pipes"
		ItemList.Shape_Extruder_Pipe_Large.set(addItem(361)); //"Extruder Shape (Large Pipe)", "Extruder Shape for making large Pipes"
		ItemList.Shape_Extruder_Pipe_Huge.set(addItem(362)); //"Extruder Shape (Huge Pipe)", "Extruder Shape for making full Block Pipes"
		ItemList.Shape_Extruder_Block.set(addItem(363)); //"Extruder Shape (Block)", "Extruder Shape for making Blocks"
		ItemList.Shape_Extruder_Sword.set(addItem(364)); //"Extruder Shape (Sword Blade)", "Extruder Shape for making Swords"
		ItemList.Shape_Extruder_Pickaxe.set(addItem(365)); //"Extruder Shape (Pickaxe Head)", "Extruder Shape for making Pickaxes"
		ItemList.Shape_Extruder_Shovel.set(addItem(366)); //"Extruder Shape (Shovel Head)", "Extruder Shape for making Shovels"
		ItemList.Shape_Extruder_Axe.set(addItem(367)); //"Extruder Shape (Axe Head)", "Extruder Shape for making Axes"
		ItemList.Shape_Extruder_Hoe.set(addItem(368)); //"Extruder Shape (Hoe Head)", "Extruder Shape for making Hoes"
		ItemList.Shape_Extruder_Hammer.set(addItem(369)); //"Extruder Shape (Hammer Head)", "Extruder Shape for making Hammers"
		ItemList.Shape_Extruder_File.set(addItem(370)); //"Extruder Shape (File Head)", "Extruder Shape for making Files"
		ItemList.Shape_Extruder_Saw.set(addItem(371)); //"Extruder Shape (Saw Blade)", "Extruder Shape for making Saws"
		ItemList.Shape_Extruder_Gear.set(addItem(372)); //"Extruder Shape (Gear)", "Extruder Shape for making Gears"
		ItemList.Shape_Extruder_Bottle.set(addItem(373)); //"Extruder Shape (Bottle)", "Extruder Shape for making Bottles"

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Bolt.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"x  ", textShape, textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Cell.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{" x ", textShape, textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Ingot.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"  x", textShape, textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Ring.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, " Px", textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Rod.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, textShape, "  x",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Wire.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, textShape, " x ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Casing.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, textShape, "x  ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Plate.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, "xP ", textEmptyRow,
						'P', ItemList.Shape_Empty});


		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Block.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P x", textEmptyRow, textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Small.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P  ", "  x", textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Large.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P  ", textEmptyRow, "  x",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Medium.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P  ", textEmptyRow, " x ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Sword.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"  P", textEmptyRow, "  x",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pickaxe.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"  P", textEmptyRow, " x ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Shovel.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"  P", textEmptyRow, "x  ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Axe.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"  P", "x  ", textEmptyRow,
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Hoe.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, textEmptyRow, "x P",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Hammer.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, "x  ", "  P",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_File.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"x  ", textEmptyRow, "  P",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Saw.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{" x ", textEmptyRow, "  P",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Gear.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"x  ", textEmptyRow, "P  ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Tiny.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{" x ", textEmptyRow, "P  ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Huge.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"  x", textEmptyRow, "P  ",
						'P', ItemList.Shape_Empty});

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Bottle.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{textEmptyRow, "  x", "P  ",
						'P', ItemList.Shape_Empty});


		ItemList.Shape_Slicer_Flat.set(addItem(398)); //"Slicer Blade (Flat)", "Slicer Blade for cutting Flat"
		ItemList.Shape_Slicer_Stripes.set(addItem(399)); //"Slicer Blade (Stripes)", "Slicer Blade for cutting Stripes"

		ModHandler.addCraftingRecipe(ItemList.Shape_Slicer_Flat.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"hXS", textShape, "fXd",
						'P', ItemList.Shape_Extruder_Block,
						'X', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
						'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel)});

		ModHandler.addCraftingRecipe(ItemList.Shape_Slicer_Stripes.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"hXS", "XPX", "fXd",
						'P', ItemList.Shape_Extruder_Block,
						'X', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
						'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel)});


		ItemList.Fuel_Can_Plastic_Empty.set(addItem(400).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Plastic, OrePrefix.plate.materialAmount)))); // "Empty Plastic Fuel Can", "Used to store Fuels"
		ItemList.Fuel_Can_Plastic_Filled.set(addItem(401).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Plastic, OrePrefix.plate.materialAmount)))); // "Plastic Fuel Can", "Burns well in Diesel Generators"

		ModHandler.addCraftingRecipe(ItemList.Fuel_Can_Plastic_Empty.get(7),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{" PP", "P P", "PPP",
						'P', new UnificationEntry(OrePrefix.plate, Materials.Plastic)});

		ItemList.Spray_Empty.set(addItem(402).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Tin, OrePrefix.plate.materialAmount * 2L), new MaterialStack(Materials.Redstone, OrePrefix.dust.materialAmount)))); //"Empty Spray Can", "Used for making Sprays",

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Redstone, 1), OreDictionaryUnifier.get(OrePrefix.cell, null, 1))
				.outputs(ItemList.Spray_Empty.get(1))
				.duration(800)
				.EUt(1)
				.buildAndRegister();

		ItemList.Crate_Empty.set(addItem(403).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Wood, 3628800L), new MaterialStack(Materials.Iron, OrePrefix.screw.materialAmount)))); //"Empty Crate", "To Package lots of Material",

		ModHandler.addCraftingRecipe(ItemList.Crate_Empty.get(4),
				ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{"SWS", "WdW", "SWS",
						'W', new UnificationEntry(OrePrefix.plank, Materials.Wood),
						'S', new UnificationEntry(OrePrefix.screw, Materials.AnyIron)});

		ModHandler.addCraftingRecipe(ItemList.Crate_Empty.get(4),
				ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{"SWS", "WdW", "SWS",
						'W', new UnificationEntry(OrePrefix.plank, Materials.Wood),
						'S', new UnificationEntry(OrePrefix.screw, Materials.Steel)});


		ItemList.ThermosCan_Empty.set(addItem(404).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Aluminium, OrePrefix.plate.materialAmount + 2L * OrePrefix.ring.materialAmount)))); //"Empty Thermos Can", "Keeping hot things hot and cold things cold",

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.Aluminium, 1), OreDictionaryUnifier.get(OrePrefix.ring, Materials.Aluminium, 2))
				.outputs(ItemList.ThermosCan_Empty.get(1))
				.duration(800)
				.EUt(1)
				.buildAndRegister();

		ItemList.Large_Fluid_Cell_Steel.set(addItem(405).addStats(new FluidStats(16000, Integer.MAX_VALUE, Integer.MAX_VALUE)).setMaxStackSize(16).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount)))); //"Large Steel Fluid Cell", "",

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.Steel, 1), OreDictionaryUnifier.get(OrePrefix.ring, Materials.Steel, 2))
				.outputs(ItemList.Large_Fluid_Cell_Steel.get(1))
				.duration(100)
				.EUt(64)
				.buildAndRegister();

		ItemList.Large_Fluid_Cell_TungstenSteel.set(addItem(406).addStats(new FluidStats(64000, Integer.MAX_VALUE, Integer.MAX_VALUE)).setMaxStackSize(16).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.TungstenSteel, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount)))); //"Large Tungstensteel Fluid Cell", "",

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.TungstenSteel, 1), OreDictionaryUnifier.get(OrePrefix.ring, Materials.TungstenSteel, 2))
				.outputs(ItemList.Large_Fluid_Cell_TungstenSteel.get(1))
				.duration(200)
				.EUt(256)
				.buildAndRegister();

		for (byte i = 0; i < 16; i = (byte) (i + 1)) {
			IItemBehaviour behaviour = new Behaviour_Spray_Color(ItemList.Spray_Empty.get(1), ItemList.SPRAY_CAN_DYES_USED[i].get(1), ItemList.SPRAY_CAN_DYES[i].get(1), 512L, i);
			ItemList.SPRAY_CAN_DYES[i].set(addItem(430 + 2 * i).addStats(behaviour)); //, "Spray Can (" + Dyes.get(i).mName + ")", "Full"
			ItemList.SPRAY_CAN_DYES_USED[i].set(addItem(431 + 2 * i).addStats(behaviour)); //"Spray Can (" + Dyes.get(i).mName + ")", "Used"
		}
		ItemList.Tool_Matches.set(addItem(471)); // "Match", ""
		ItemList.Tool_MatchBox_Used.set(addItem(472)); // "Match Box", "This is not a Car"
		ItemList.Tool_MatchBox_Full.set(addItem(473)); //"Match Box (Full)", "This is not a Car"

		IItemBehaviour behaviour = new Behaviour_Lighter(null, ItemList.Tool_Matches.get(1), ItemList.Tool_Matches.get(1), 1L);
		addItem(32471).addStats(behaviour);
		behaviour = new Behaviour_Lighter(null, ItemList.Tool_MatchBox_Used.get(1), ItemList.Tool_MatchBox_Full.get(1), 16L);
		addItem(32472).addStats(behaviour);
		addItem(32473).addStats(behaviour);

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Phosphor, 1))
				.outputs(ItemList.Tool_Matches.get(1))
				.duration(16)
				.EUt(16)
				.buildAndRegister();

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Phosphorus, 1))
				.outputs(ItemList.Tool_Matches.get(1))
				.duration(16)
				.EUt(16)
				.buildAndRegister();

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 4), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Phosphor, 1))
				.outputs(ItemList.Tool_Matches.get(4))
				.duration(64)
				.EUt(16)
				.buildAndRegister();

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 4), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Phosphorus, 1))
				.outputs(ItemList.Tool_Matches.get(4))
				.duration(64)
				.EUt(16)
				.buildAndRegister();

		RecipeMap.BOXINATOR_RECIPES.recipeBuilder()
				.inputs(ItemList.Tool_Matches.get(16), OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.Paper, 1))
				.outputs(ItemList.Tool_MatchBox_Full.get(1))
				.duration(64)
				.EUt(16)
				.buildAndRegister();
		RecipeMap.UNBOXINATOR_RECIPES.recipeBuilder()
				.inputs(ItemList.Tool_MatchBox_Full.get(1))
				.outputs(ItemList.Tool_Matches.get(16))
				.duration(32)
				.EUt(16)
				.buildAndRegister();

		ItemList.Tool_Lighter_Invar_Empty.set(addItem(474).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L)))); //"Lighter (Empty)", "",
		ItemList.Tool_Lighter_Invar_Used.set(addItem(475).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L)))); //"Lighter", "",
		ItemList.Tool_Lighter_Invar_Full.set(addItem(476).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L)))); //"Lighter (Full)", ""

		behaviour = new Behaviour_Lighter(ItemList.Tool_Lighter_Invar_Empty.get(1), ItemList.Tool_Lighter_Invar_Used.get(1), ItemList.Tool_Lighter_Invar_Full.get(1), 100L);
		addItem(32475).addStats(behaviour);
		addItem(32476).addStats(behaviour);

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Invar, 2), new ItemStack(Items.FLINT, 1))
				.outputs(ItemList.Tool_Lighter_Invar_Empty.get(1))
				.duration(256)
				.EUt(16)
				.buildAndRegister();

		ItemList.Tool_Lighter_Platinum_Empty.set(addItem(477).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L)))); //"Platinum Lighter (Empty)", "A known Prank Master is engraved on it"
		ItemList.Tool_Lighter_Platinum_Used.set(addItem(478).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L)))); //"Platinum Lighter", "A known Prank Master is engraved on it",
		ItemList.Tool_Lighter_Platinum_Full.set(addItem(479).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L)))); //"Platinum Lighter (Full)", "A known Prank Master is engraved on it"

		behaviour = new Behaviour_Lighter(ItemList.Tool_Lighter_Platinum_Empty.get(1), ItemList.Tool_Lighter_Platinum_Used.get(1), ItemList.Tool_Lighter_Platinum_Full.get(1), 1000L);
		addItem(this.metaItemOffset + 478).addStats(behaviour);
		addItem(this.metaItemOffset + 479).addStats(behaviour);

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Platinum, 2), new ItemStack(Items.FLINT, 1))
				.outputs(ItemList.Tool_Lighter_Platinum_Empty.get(1))
				.duration(256)
				.EUt(256)
				.buildAndRegister();

		ItemList.Ingot_IridiumAlloy.set(addItem(480)); //"Iridium Alloy Ingot", "Used to make Iridium Plates"

		ModHandler.addRollingMachineRecipe(ItemList.Ingot_IridiumAlloy.get(1),
				new Object[]{"IAI", "ADA", "IAI",
						'D', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "iridiumplate", true) ? OreDictNames.craftingIndustrialDiamond : new UnificationEntry(OrePrefix.dust, Materials.Diamond),
						'A', OrePrefix.plateAlloy.name() + "Advanced",
						'I', new UnificationEntry(OrePrefix.plate, Materials.Iridium)});

		ModHandler.addCraftingRecipe(ItemList.Ingot_IridiumAlloy.get(1),
				ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{"IAI", "ADA", "IAI",
						'D', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "iridiumplate", true) ? OreDictNames.craftingIndustrialDiamond : new UnificationEntry(OrePrefix.dust, Materials.Diamond),
						'A', OrePrefix.plateAlloy.name() + "Advanced",
						'I', new UnificationEntry(OrePrefix.plate, Materials.Iridium)});

		ItemList.Paper_Printed_Pages.set(addItem(481).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L))).addStats(new Behaviour_PrintedPages())); // "Printed Pages", "Used to make written Books",
		ItemList.Paper_Magic_Empty.set(addItem(482).setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 3628800L)))); //"Magic Paper", "",
		ItemList.Paper_Magic_Page.set(addItem(483).setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 3628800L)))); //"Enchanted Page", "",
		ItemList.Paper_Magic_Pages.set(addItem(484).setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L)))); //"Enchanted Pages", "",
		ItemList.Paper_Punch_Card_Empty.set(addItem(485).setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 7257600L)))); //"Punch Card", "",
		ItemList.Paper_Punch_Card_Encoded.set(addItem(486).setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 7257600L)))); //"Punched Card", "",
		ItemList.Book_Written_01.set(addItem(487).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L))).addStats(new Behaviour_WrittenBook()).addOreDict("bookWritten").addOreDict(OreDictNames.craftingBook)); //"Book", "",
		ItemList.Book_Written_02.set(addItem(488).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L))).addStats(new Behaviour_WrittenBook()).addOreDict("bookWritten").addOreDict(OreDictNames.craftingBook)); //"Book", "",
		ItemList.Book_Written_03.set(addItem(489).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L))).addStats(new Behaviour_WrittenBook()).addOreDict("bookWritten").addOreDict(OreDictNames.craftingBook)); //"Book", "",

		ItemList.Schematic.set(addItem(490).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))); //"Schematic", "EMPTY"
		ItemList.Schematic_Crafting.set(addItem(491).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))); //"Schematic (Crafting)", "Crafts the Programmed Recipe",
		ItemList.Schematic_1by1.set(addItem(495).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))); //"Schematic (1x1)", "Crafts 1 Items as 1x1 (use in Packager)"
		ItemList.Schematic_2by2.set(addItem(496).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))); //"Schematic (2x2)", "Crafts 4 Items as 2x2 (use in Packager)"
		ItemList.Schematic_3by3.set(addItem(497).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))); //"Schematic (3x3)", "Crafts 9 Items as 3x3 (use in Packager)"
		ItemList.Schematic_Dust.set(addItem(498).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L)))); //"Schematic (Dusts)", "Combines Dusts (use in Packager)"

		ModHandler.addCraftingRecipe(ItemList.Schematic_1by1.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{"d  ", textShape, textEmptyRow,
						'P', ItemList.Schematic});

		ModHandler.addCraftingRecipe(ItemList.Schematic_2by2.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{" d ", textShape, textEmptyRow,
						'P', ItemList.Schematic});

		ModHandler.addCraftingRecipe(ItemList.Schematic_3by3.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{"  d", textShape, textEmptyRow,
						'P', ItemList.Schematic});

		ModHandler.addCraftingRecipe(ItemList.Schematic_Dust.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{textEmptyRow, textShape, "  d",
						'P', ItemList.Schematic});


		ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Schematic_Crafting});

		ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Schematic_1by1});

		ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Schematic_2by2});

		ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Schematic_3by3});

		ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{ItemList.Schematic_Dust});


		ItemList.Battery_Hull_LV.set(addItem(500).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount)))); //"Small Battery Hull", "An empty LV Battery Hull",
		ItemList.Battery_Hull_MV.set(addItem(501).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount * 3L)))); //"Medium Battery Hull", "An empty MV Battery Hull",
		ItemList.Battery_Hull_HV.set(addItem(502).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount * 9L)))); //"Large Battery Hull", "An empty HV Battery Hull",

		ModHandler.addCraftingRecipe(ItemList.Battery_Hull_LV.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"C", "P", "P", 'P', new UnificationEntry(OrePrefix.plate, Materials.BatteryAlloy), 'C', OreDictNames.craftingWireTin});
		ModHandler.addCraftingRecipe(ItemList.Battery_Hull_MV.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"C C", "PPP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.BatteryAlloy), 'C', OreDictNames.craftingWireCopper});

		ItemList.Battery_RE_ULV_Tantalum.set(addItem(499).addStats(new ElectricStats(1000, 0))); // "Tantalum Capacitor", "Reusable"

		ItemList.Battery_SU_LV_SulfuricAcid.set(addItem(510).addStats(new ElectricStats(18000, 1, false))); //"Small Acid Battery", "Single Use"
		ItemList.Battery_SU_LV_Mercury.set(addItem(511).addStats(new ElectricStats(32000, 1, false))); // "Small Mercury Battery", "Single Use"

		ItemList.Battery_RE_LV_Cadmium.set(addItem(517).addStats(new ElectricStats(75000, 1))); //"Small Cadmium Battery", "Reusable"
		ItemList.Battery_RE_LV_Lithium.set(addItem(518).addStats(new ElectricStats(100000, 1))); //"Small Lithium Battery", "Reusable"
		ItemList.Battery_RE_LV_Sodium.set(addItem(519).addStats(new ElectricStats(50000, 1))); //"Small Sodium Battery", "Reusable"

		ItemList.Battery_SU_MV_SulfuricAcid.set(addItem(520).addStats(new ElectricStats(72000, 2, false))); //"Medium Acid Battery", "Single Use"
		ItemList.Battery_SU_MV_Mercury.set(addItem(521).addStats(new ElectricStats(128000, 2, false))); //"Medium Mercury Battery", "Single Use"

		ItemList.Battery_RE_MV_Cadmium.set(addItem(527).addStats(new ElectricStats(300000, 2))); // "Medium Cadmium Battery", "Reusable",
		ItemList.Battery_RE_MV_Lithium.set(addItem(528).addStats(new ElectricStats(400000, 2))); //"Medium Lithium Battery", "Reusable",
		ItemList.Battery_RE_MV_Sodium.set(addItem(529).addStats(new ElectricStats(200000, 2))); //"Medium Sodium Battery", "Reusable",

		ItemList.Battery_SU_HV_SulfuricAcid.set(addItem(530).addStats(new ElectricStats(288000, 3, false))); //"Large Acid Battery", "Single Use",
		ItemList.Battery_SU_HV_Mercury.set(addItem(531).addStats(new ElectricStats(512000, 3, false))); //"Large Mercury Battery", "Single Use",

		ItemList.Battery_RE_HV_Cadmium.set(addItem(537).addStats(new ElectricStats(1200000, 3))); //"Large Cadmium Battery", "Reusable",
		ItemList.Battery_RE_HV_Lithium.set(addItem(538).addStats(new ElectricStats(1600000, 3))); //"Large Lithium Battery", "Reusable",
		ItemList.Battery_RE_HV_Sodium.set(addItem(539).addStats(new ElectricStats(800000, 3))); //"Large Sodium Battery", "Reusable",

		ModHandler.addExtractionRecipe(ItemList.Battery_SU_LV_SulfuricAcid.get(1), ItemList.Battery_Hull_LV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_SU_LV_Mercury.get(1), ItemList.Battery_Hull_LV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_SU_MV_SulfuricAcid.get(1), ItemList.Battery_Hull_MV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_SU_MV_Mercury.get(1), ItemList.Battery_Hull_MV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_SU_HV_SulfuricAcid.get(1), ItemList.Battery_Hull_HV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_SU_HV_Mercury.get(1), ItemList.Battery_Hull_HV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_RE_LV_Cadmium.get(1), ItemList.Battery_Hull_LV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_RE_LV_Lithium.get(1), ItemList.Battery_Hull_LV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_RE_LV_Sodium.get(1), ItemList.Battery_Hull_LV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_RE_MV_Cadmium.get(1), ItemList.Battery_Hull_MV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_RE_MV_Lithium.get(1), ItemList.Battery_Hull_MV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_RE_MV_Sodium.get(1), ItemList.Battery_Hull_MV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_RE_HV_Cadmium.get(1), ItemList.Battery_Hull_HV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_RE_HV_Lithium.get(1), ItemList.Battery_Hull_HV.get(1));
		ModHandler.addExtractionRecipe(ItemList.Battery_RE_HV_Sodium.get(1), ItemList.Battery_Hull_HV.get(1));

		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Cadmium, 2), ItemList.Battery_Hull_LV.get(1))
				.outputs(ItemList.Battery_RE_LV_Cadmium.get(1))
				.duration(100)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Lithium, 2), ItemList.Battery_Hull_LV.get(1))
				.outputs(ItemList.Battery_RE_LV_Lithium.get(1))
				.duration(100)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Sodium, 2), ItemList.Battery_Hull_LV.get(1))
				.outputs(ItemList.Battery_RE_LV_Sodium.get(1))
				.duration(100)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Cadmium, 8), ItemList.Battery_Hull_MV.get(1))
				.outputs(ItemList.Battery_RE_MV_Cadmium.get(1))
				.duration(400)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Lithium, 8), ItemList.Battery_Hull_MV.get(1))
				.outputs(ItemList.Battery_RE_MV_Lithium.get(1))
				.duration(400)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Sodium, 8), ItemList.Battery_Hull_MV.get(1))
				.outputs(ItemList.Battery_RE_MV_Sodium.get(1))
				.duration(400)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Cadmium, 32), ItemList.Battery_Hull_HV.get(1))
				.outputs(ItemList.Battery_RE_HV_Cadmium.get(1))
				.duration(1600)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Lithium, 32), ItemList.Battery_Hull_HV.get(1))
				.outputs(ItemList.Battery_RE_HV_Lithium.get(1))
				.duration(1600)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.FLUID_CANNER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Sodium, 32), ItemList.Battery_Hull_HV.get(1))
				.outputs(ItemList.Battery_RE_HV_Sodium.get(1))
				.duration(1600)
				.EUt(2)
				.buildAndRegister();

		ItemList.Energy_LapotronicOrb.set(addItem(597).addStats(new ElectricStats(100000000, 5)).setUnificationData(OrePrefix.battery, Materials.Ultimate)); //"Lapotronic Energy Orb", ""

		ItemList.ZPM.set(addItem(598).addStats(new ElectricStats(2000000000000L, 7, false))); // "Zero Point Module", ""

		ItemList.Energy_LapotronicOrb2.set(addItem(599).addStats(new ElectricStats(1000000000, 6)).setUnificationData(OrePrefix.battery, Materials.Ultimate)); //"Lapotronic Energy Orb Cluster", "",

		ItemList.ZPM2.set(addItem(605).addStats(new ElectricStats(Long.MAX_VALUE, 8))); //"Ultimate Battery", "Fill this to win minecraft"

		ItemList.Electric_Motor_LV.set(addItem(600)); // "Electric Motor (LV)", ""
		ItemList.Electric_Motor_MV.set(addItem(601)); // "Electric Motor (MV)", ""
		ItemList.Electric_Motor_HV.set(addItem(602)); // "Electric Motor (HV)", ""
		ItemList.Electric_Motor_EV.set(addItem(603)); // "Electric Motor (EV)", ""
		ItemList.Electric_Motor_IV.set(addItem(604)); // "Electric Motor (IV)", ""
		ItemList.Electric_Motor_LuV.set(addItem(606)); // "Electric Motor (LuV)", ""
		ItemList.Electric_Motor_ZPM.set(addItem(607)); // "Electric Motor (ZPM)", ""
		ItemList.Electric_Motor_UV.set(addItem(608)); // "Electric Motor (UV)", ""

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"CWR", "WIW", "RWC",
						'I', new UnificationEntry(OrePrefix.stick, Materials.IronMagnetic),
						'R', new UnificationEntry(OrePrefix.stick, Materials.AnyIron),
						'W', new UnificationEntry(OrePrefix.wireGt01, Materials.AnyCopper),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE,
				new Object[]{"CWR", "WIW", "RWC",
						'I', new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic),
						'R', new UnificationEntry(OrePrefix.stick, Materials.Steel),
						'W', new UnificationEntry(OrePrefix.wireGt01, Materials.AnyCopper),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"CWR", "WIW", "RWC",
						'I', new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic),
						'R', new UnificationEntry(OrePrefix.stick, Materials.Aluminium),
						'W', new UnificationEntry(OrePrefix.wireGt02, Materials.AnyCopper),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"CWR", "WIW", "RWC",
						'I', new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic),
						'R', new UnificationEntry(OrePrefix.stick, Materials.StainlessSteel),
						'W', new UnificationEntry(OrePrefix.wireGt04, Materials.AnyCopper),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"CWR", "WIW", "RWC",
						'I', new UnificationEntry(OrePrefix.stick, Materials.NeodymiumMagnetic),
						'R', new UnificationEntry(OrePrefix.stick, Materials.Titanium),
						'W', new UnificationEntry(OrePrefix.wireGt08, Materials.AnnealedCopper),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"CWR", "WIW", "RWC",
						'I', new UnificationEntry(OrePrefix.stick, Materials.NeodymiumMagnetic),
						'R', new UnificationEntry(OrePrefix.stick, Materials.TungstenSteel),
						'W', new UnificationEntry(OrePrefix.wireGt16, Materials.AnnealedCopper),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten)});


		ItemList.Electric_Pump_LV.set(addItem(610)); //"Electric Pump (LV)", "640 L/sec (as Cover)"
		ItemList.Electric_Pump_MV.set(addItem(611)); //"Electric Pump (MV)", "2560 L/sec (as Cover)"
		ItemList.Electric_Pump_HV.set(addItem(612)); //"Electric Pump (HV)", "10240 L/sec (as Cover)"
		ItemList.Electric_Pump_EV.set(addItem(613)); //"Electric Pump (EV)", "40.960 L/sec (as Cover)"
		ItemList.Electric_Pump_IV.set(addItem(614)); //"Electric Pump (IV)", "163.840 L/sec (as Cover)"
		ItemList.Electric_Pump_LuV.set(addItem(620)); // "Electric Pump (LuV)", "655.360 L/sec (as Cover)"
		ItemList.Electric_Pump_ZPM.set(addItem(621)); // "Electric Pump (ZPM)", "2.621.440 L/sec (as Cover)"
		ItemList.Electric_Pump_UV.set(addItem(622)); //"Electric Pump (UV)", "10.485.760 L/sec (as Cover)"

		ItemList.FluidRegulator_LV.set(addItem(615)); // "Fluid Regulator (LV)", "Configuable up to 640 L/sec (as Cover)"
		ItemList.FluidRegulator_MV.set(addItem(616)); // "Fluid Regulator (MV)", "Configuable up to 2560 L/sec (as Cover)"
		ItemList.FluidRegulator_HV.set(addItem(617)); // "Fluid Regulator (HV)", "Configuable up to 10240 L/sec (as Cover)"
		ItemList.FluidRegulator_EV.set(addItem(618)); // "Fluid Regulator (EV)", "Configuable up to 40960 L/sec (as Cover)"
		ItemList.FluidRegulator_IV.set(addItem(619)); // "Fluid Regulator (IV)", "Configuable up to 163840 L/sec (as Cover)"

		ItemList.FluidFilter.set(addItem(635)); // "Fluid Filter", "Set with Fluid Container to only accept one Fluid Type"

		ItemList.Rotor_LV.set(addItem(620).setUnificationData(OrePrefix.rotor, Materials.Tin)); //"Tin Rotor", ""
		ItemList.Rotor_MV.set(addItem(621).setUnificationData(OrePrefix.rotor, Materials.Bronze)); //"Bronze Rotor", ""
		ItemList.Rotor_HV.set(addItem(622).setUnificationData(OrePrefix.rotor, Materials.Steel)); //"Steel Rotor", ""
		ItemList.Rotor_EV.set(addItem(623).setUnificationData(OrePrefix.rotor, Materials.StainlessSteel)); //"Stainless Steel Rotor",
		ItemList.Rotor_IV.set(addItem(624).setUnificationData(OrePrefix.rotor, Materials.TungstenSteel)); //"Tungstensteel Rotor", ""
		ItemList.Rotor_LuV.set(ItemList.Rotor_IV.get(1));
		ItemList.Rotor_ZPM.set(ItemList.Rotor_LuV.get(1));
		ItemList.Rotor_UV.set(ItemList.Rotor_ZPM.get(1));

		ModHandler.addCraftingRecipe(ItemList.Electric_Pump_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"SXO", "dPw", "OMW",
						'M', ItemList.Electric_Motor_LV,
						'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
						'X', new UnificationEntry(OrePrefix.rotor, Materials.Tin),
						'S', new UnificationEntry(OrePrefix.screw, Materials.Tin),
						'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin),
						'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Bronze)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Pump_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"SXO", "dPw", "OMW",
						'M', ItemList.Electric_Motor_MV,
						'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
						'X', new UnificationEntry(OrePrefix.rotor, Materials.Bronze),
						'S', new UnificationEntry(OrePrefix.screw, Materials.Bronze),
						'W', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper),
						'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Steel)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Pump_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"SXO", "dPw", "OMW",
						'M', ItemList.Electric_Motor_HV,
						'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
						'X', new UnificationEntry(OrePrefix.rotor, Materials.Steel),
						'S', new UnificationEntry(OrePrefix.screw, Materials.Steel),
						'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold),
						'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.StainlessSteel)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Pump_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"SXO", "dPw", "OMW",
						'M', ItemList.Electric_Motor_EV,
						'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
						'X', new UnificationEntry(OrePrefix.rotor, Materials.StainlessSteel),
						'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
						'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium),
						'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Titanium)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Pump_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"SXO", "dPw", "OMW",
						'M', ItemList.Electric_Motor_IV,
						'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
						'X', new UnificationEntry(OrePrefix.rotor, Materials.TungstenSteel),
						'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
						'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten),
						'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.TungstenSteel)});


		ItemList.Conveyor_Module_LV.set(addItem(630)); //"Conveyor Module (LV)", "1 Stack every 20 secs (as Cover)"
		ItemList.Conveyor_Module_MV.set(addItem(631)); //"Conveyor Module (MV)", "1 Stack every 5 secs (as Cover)"
		ItemList.Conveyor_Module_HV.set(addItem(632)); //"Conveyor Module (HV)", "1 Stack every 1 sec (as Cover)"
		ItemList.Conveyor_Module_EV.set(addItem(633)); //"Conveyor Module (EV)", "1 Stack every 1/5 sec (as Cover)"
		ItemList.Conveyor_Module_IV.set(addItem(634)); //"Conveyor Module (IV)", "1 Stack every 1/20 sec (as Cover)"
		ItemList.Conveyor_Module_LuV.set(addItem(636)); //"Conveyor Module (LuV)", "1 Stack every 1/20 sec (as Cover)"
		ItemList.Conveyor_Module_ZPM.set(addItem(637)); //"Conveyor Module (ZPM)", "1 Stack every 1/20 sec (as Cover)"
		ItemList.Conveyor_Module_UV.set(addItem(638)); //"Conveyor Module (UV)", "1 Stack every 1/20 sec (as Cover)"

		ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"RRR", "MCM", "RRR",
						'M', ItemList.Electric_Motor_LV,
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin),
						'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber)});

		ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"RRR", "MCM", "RRR",
						'M', ItemList.Electric_Motor_MV,
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper),
						'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber)});

		ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"RRR", "MCM", "RRR",
						'M', ItemList.Electric_Motor_HV,
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold),
						'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber)});

		ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"RRR", "MCM", "RRR",
						'M', ItemList.Electric_Motor_EV,
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium),
						'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber)});

		ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"RRR", "MCM", "RRR",
						'M', ItemList.Electric_Motor_IV,
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten),
						'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber)});


		ItemList.Electric_Piston_LV.set(addItem(640)); //"Electric Piston (LV)", ""
		ItemList.Electric_Piston_MV.set(addItem(641)); //"Electric Piston (MV)", ""
		ItemList.Electric_Piston_HV.set(addItem(642)); //"Electric Piston (HV)", ""
		ItemList.Electric_Piston_EV.set(addItem(643)); //"Electric Piston (EV)", ""
		ItemList.Electric_Piston_IV.set(addItem(644)); //"Electric Piston (IV)", ""
		ItemList.Electric_Piston_LuV.set(addItem(645)); //"Electric Piston (LuV)", ""
		ItemList.Electric_Piston_ZPM.set(addItem(646)); //"Electric Piston (ZPM)", ""
		ItemList.Electric_Piston_UV.set(addItem(647)); //"Electric Piston (UV)", ""

		ModHandler.addCraftingRecipe(ItemList.Electric_Piston_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"PPP", "CSS", "CMG",
						'P', new UnificationEntry(OrePrefix.plate, Materials.Steel),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Steel),
						'G', new UnificationEntry(OrePrefix.gearGtSmall, Materials.Steel),
						'M', ItemList.Electric_Motor_LV,
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Piston_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"PPP", "CSS", "CMG",
						'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Aluminium),
						'G', new UnificationEntry(OrePrefix.gearGtSmall, Materials.Aluminium),
						'M', ItemList.Electric_Motor_MV,
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Piston_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"PPP", "CSS", "CMG",
						'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
						'S', new UnificationEntry(OrePrefix.stick, Materials.StainlessSteel),
						'G', new UnificationEntry(OrePrefix.gearGtSmall, Materials.StainlessSteel),
						'M', ItemList.Electric_Motor_HV,
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Piston_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"PPP", "CSS", "CMG",
						'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Titanium),
						'G', new UnificationEntry(OrePrefix.gearGtSmall, Materials.Titanium),
						'M', ItemList.Electric_Motor_EV,
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium)});

		ModHandler.addCraftingRecipe(ItemList.Electric_Piston_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"PPP", "CSS", "CMG",
						'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
						'S', new UnificationEntry(OrePrefix.stick, Materials.TungstenSteel),
						'G', new UnificationEntry(OrePrefix.gearGtSmall, Materials.TungstenSteel),
						'M', ItemList.Electric_Motor_IV,
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten)});


		ItemList.Robot_Arm_LV.set(addItem(650)); //"Robot Arm (LV)", "Inserts into specific Slots (as Cover)"
		ItemList.Robot_Arm_MV.set(addItem(651)); //"Robot Arm (MV)", "Inserts into specific Slots (as Cover)"
		ItemList.Robot_Arm_HV.set(addItem(652)); //"Robot Arm (HV)", "Inserts into specific Slots (as Cover)"
		ItemList.Robot_Arm_EV.set(addItem(653)); //"Robot Arm (EV)", "Inserts into specific Slots (as Cover)"
		ItemList.Robot_Arm_IV.set(addItem(654)); //"Robot Arm (IV)", "Inserts into specific Slots (as Cover)"
		ItemList.Robot_Arm_LuV.set(addItem(655)); //"Robot Arm (LuV)", "Inserts into specific Slots (as Cover)"
		ItemList.Robot_Arm_ZPM.set(addItem(656)); //"Robot Arm (ZPM)", "Inserts into specific Slots (as Cover)"
		ItemList.Robot_Arm_UV.set(addItem(657)); //"Robot Arm (UV)", "Inserts into specific Slots (as Cover)"

		ModHandler.addCraftingRecipe(ItemList.Robot_Arm_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"CCC", "MSM", "PES",
						'S', new UnificationEntry(OrePrefix.stick, Materials.Steel),
						'M', ItemList.Electric_Motor_LV, 'P', ItemList.Electric_Piston_LV,
						'E', new UnificationEntry(OrePrefix.circuit, Materials.Basic),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin)});

		ModHandler.addCraftingRecipe(ItemList.Robot_Arm_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"CCC", "MSM", "PES",
						'S', new UnificationEntry(OrePrefix.stick, Materials.Aluminium),
						'M', ItemList.Electric_Motor_MV, 'P', ItemList.Electric_Piston_MV,
						'E', new UnificationEntry(OrePrefix.circuit, Materials.Good),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper)});

		ModHandler.addCraftingRecipe(ItemList.Robot_Arm_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"CCC", "MSM", "PES",
						'S', new UnificationEntry(OrePrefix.stick, Materials.StainlessSteel),
						'M', ItemList.Electric_Motor_HV, 'P', ItemList.Electric_Piston_HV,
						'E', new UnificationEntry(OrePrefix.circuit, Materials.Advanced),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold)});

		ModHandler.addCraftingRecipe(ItemList.Robot_Arm_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"CCC", "MSM", "PES",
						'S', new UnificationEntry(OrePrefix.stick, Materials.Titanium),
						'M', ItemList.Electric_Motor_EV, 'P', ItemList.Electric_Piston_EV,
						'E', new UnificationEntry(OrePrefix.circuit, Materials.Elite),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium)});

		ModHandler.addCraftingRecipe(ItemList.Robot_Arm_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"CCC", "MSM", "PES",
						'S', new UnificationEntry(OrePrefix.stick, Materials.TungstenSteel),
						'M', ItemList.Electric_Motor_IV, 'P', ItemList.Electric_Piston_IV,
						'E', new UnificationEntry(OrePrefix.circuit, Materials.Master),
						'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten)});

		ItemList.Field_Generator_LV.set(addItem(670)); //"Field Generator (LV)", ""
		ItemList.Field_Generator_MV.set(addItem(671)); //"Field Generator (MV)", ""
		ItemList.Field_Generator_HV.set(addItem(672)); //"Field Generator (HV)", ""
		ItemList.Field_Generator_EV.set(addItem(673)); //"Field Generator (EV)", ""
		ItemList.Field_Generator_IV.set(addItem(674)); //"Field Generator (IV)", ""
		ItemList.Field_Generator_LuV.set(addItem(675)); //"Field Generator (LuV)", ""
		ItemList.Field_Generator_ZPM.set(addItem(676)); //"Field Generator (ZPM)", ""
		ItemList.Field_Generator_UV.set(addItem(677)); //"Field Generator (UV)", ""

		ModHandler.addCraftingRecipe(ItemList.Field_Generator_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"WCW", "CGC", "WCW",
						'G', new UnificationEntry(OrePrefix.gem, Materials.EnderPearl),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Basic),
						'W', new UnificationEntry(OrePrefix.wireGt01, Materials.Osmium)});

		ModHandler.addCraftingRecipe(ItemList.Field_Generator_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"WCW", "CGC", "WCW",
						'G', new UnificationEntry(OrePrefix.gem, Materials.EnderEye),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Good),
						'W', new UnificationEntry(OrePrefix.wireGt02, Materials.Osmium)});

		ItemList.Emitter_LV.set(addItem(680)); //"Emitter (LV)", ""
		ItemList.Emitter_MV.set(addItem(681)); //"Emitter (MV)", ""
		ItemList.Emitter_HV.set(addItem(682)); //"Emitter (HV)", ""
		ItemList.Emitter_EV.set(addItem(683)); //"Emitter (EV)", ""
		ItemList.Emitter_IV.set(addItem(684)); //"Emitter (IV)", ""
		ItemList.Emitter_LuV.set(addItem(685)); //"Emitter (LuV)", ""
		ItemList.Emitter_ZPM.set(addItem(686)); //"Emitter (ZPM)", ""
		ItemList.Emitter_UV.set(addItem(687)); //"Emitter (UV)", ""

		ModHandler.addCraftingRecipe(ItemList.Emitter_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"SSC", "WQS", "CWS",
						'Q', new UnificationEntry(OrePrefix.gem, Materials.Quartzite),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Brass),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Basic),
						'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin)});

		ModHandler.addCraftingRecipe(ItemList.Emitter_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"SSC", "WQS", "CWS",
						'Q', new UnificationEntry(OrePrefix.gem, Materials.NetherQuartz),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Electrum),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Good),
						'W', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper)});

		ModHandler.addCraftingRecipe(ItemList.Emitter_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"SSC", "WQS", "CWS",
						'Q', new UnificationEntry(OrePrefix.gem, Materials.Emerald),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Chrome),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Advanced),
						'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold)});

		ModHandler.addCraftingRecipe(ItemList.Emitter_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"SSC", "WQS", "CWS",
						'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderPearl),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Platinum),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Elite),
						'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium)});

		ModHandler.addCraftingRecipe(ItemList.Emitter_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"SSC", "WQS", "CWS",
						'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderEye),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Osmium),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Master),
						'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten)});


		ItemList.Sensor_LV.set(addItem(690)); //"Sensor (LV)", ""
		ItemList.Sensor_MV.set(addItem(691)); //"Sensor (MV)", ""
		ItemList.Sensor_HV.set(addItem(692)); //"Sensor (HV)", ""
		ItemList.Sensor_EV.set(addItem(693)); //"Sensor (EV)", ""
		ItemList.Sensor_IV.set(addItem(694)); //"Sensor (IV)", ""
		ItemList.Sensor_LuV.set(addItem(695)); //"Sensor (LuV)", ""
		ItemList.Sensor_ZPM.set(addItem(696)); //"Sensor (ZPM)", ""
		ItemList.Sensor_UV.set(addItem(697)); //"Sensor (UV)", ""

		ModHandler.addCraftingRecipe(ItemList.Sensor_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P Q", "PS ", "CPP",
						'Q', new UnificationEntry(OrePrefix.gem, Materials.Quartzite),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Brass),
						'P', new UnificationEntry(OrePrefix.plate, Materials.Steel),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Basic)});

		ModHandler.addCraftingRecipe(ItemList.Sensor_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P Q", "PS ", "CPP",
						'Q', new UnificationEntry(OrePrefix.gem, Materials.NetherQuartz),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Electrum),
						'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Good)});

		ModHandler.addCraftingRecipe(ItemList.Sensor_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P Q", "PS ", "CPP",
						'Q', new UnificationEntry(OrePrefix.gem, Materials.Emerald),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Chrome),
						'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Advanced)});

		ModHandler.addCraftingRecipe(ItemList.Sensor_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P Q", "PS ", "CPP",
						'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderPearl),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Platinum),
						'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Elite)});

		ModHandler.addCraftingRecipe(ItemList.Sensor_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"P Q", "PS ", "CPP",
						'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderEye),
						'S', new UnificationEntry(OrePrefix.stick, Materials.Osmium),
						'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Master)});


		ItemList.Circuit_Primitive.set(addItem(700).setUnificationData(OrePrefix.circuit, Materials.Primitive)); // "NAND Chip", "A very simple Circuit",
		ItemList.Circuit_Basic.set(addItem(701).setUnificationData(OrePrefix.circuit, Materials.Basic)); //"Basic Electronic Circuit", "A basic Circuit",
		ItemList.Circuit_Good.set(addItem(702).setUnificationData(OrePrefix.circuit, Materials.Good)); //"Good Electronic Circuit", "A good Circuit",
		ItemList.Circuit_Advanced.set(addItem(703).setUnificationData(OrePrefix.circuit, Materials.Advanced)); //"Advanced Circuit", "An advanced Circuit"
		ItemList.Circuit_Data.set(addItem(704).setUnificationData(OrePrefix.circuit, Materials.Data)); //"Data Storage Circuit", "A Data Storage Chip"
		ItemList.Circuit_Elite.set(addItem(705).setUnificationData(OrePrefix.circuit, Materials.Elite)); //"Data Control Circuit", "A Processor",
		ItemList.Circuit_Master.set(addItem(706).setUnificationData(OrePrefix.circuit, Materials.Master)); //"Energy Flow Circuit", "A High Voltage Processor"
		ItemList.Tool_DataOrb.set(addItem(707).setUnificationData(OrePrefix.circuit, Materials.Ultimate).addStats(new Behaviour_DataOrb())); //"Data Orb", "A High Capacity Data Storage"
		ItemList.Circuit_Ultimate.set(ItemList.Tool_DataOrb.get(1));
		ModHandler.addShapelessCraftingRecipe(ItemList.Tool_DataOrb.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Tool_DataOrb.get(1)});
		ItemList.Tool_DataStick.set(addItem(708).setUnificationData(OrePrefix.circuit, Materials.Data).addStats(new Behaviour_DataStick())); //"Data Stick", "A Low Capacity Data Storage",
		ModHandler.addShapelessCraftingRecipe(ItemList.Tool_DataStick.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Tool_DataStick.get(1)});


		ItemList.Circuit_Board_Basic.set(addItem(710)); //"Basic Circuit Board", "A basic Board"
		ItemList.Circuit_Board_Advanced.set(addItem(711)); //"Advanced Circuit Board", "An advanced Board"
		ItemList.Circuit_Board_Elite.set(addItem(712)); //"Processor Board", "A Processor Board"
		ItemList.Circuit_Parts_Crystal_Chip_Elite.set(addItem(713)); //"Engraved Crystal Chip", "Needed for Circuits"
		ItemList.Circuit_Parts_Crystal_Chip_Master.set(addItem(714)); //"Engraved Lapotron Chip", "Needed for Circuits"
		ItemList.Circuit_Parts_Advanced.set(addItem(715)); //"Advanced Circuit Parts", "Advanced Circuit Parts"
		ItemList.Circuit_Parts_Wiring_Basic.set(addItem(716)); //"Etched Medium Voltage Wiring", "Part of Circuit Boards"
		ItemList.Circuit_Parts_Wiring_Advanced.set(addItem(717)); //"Etched High Voltage Wiring", "Part of Circuit Boards"
		ItemList.Circuit_Parts_Wiring_Elite.set(addItem(718)); //"Etched Extreme Voltage Wiring", "Part of Circuit Boards"
		ItemList.Empty_Board_Basic.set(addItem(719)); //"Empty Circuit Board", "A Board Part"
		ItemList.Empty_Board_Elite.set(addItem(720)); //"Empty Processor Board", "A Processor Board Part"


		ItemList.Component_Sawblade_Diamond.set(addItem(721).addOreDict(OreDictNames.craftingDiamondBlade)); //"Diamond Sawblade", "",
		ItemList.Component_Grinder_Diamond.set(addItem(722).addOreDict(OreDictNames.craftingGrinder)); //"Diamond Grinding Head", ""
		ItemList.Component_Grinder_Tungsten.set(addItem(723).addOreDict(OreDictNames.craftingGrinder)); //"Tungsten Grinding Head", ""

		ItemList.QuantumEye.set(addItem(724)); //"Quantum Eye", "Improved Ender Eye"
		ItemList.QuantumStar.set(addItem(725)); //"Quantum Star", "Improved Nether Star"
		ItemList.Gravistar.set(addItem(726)); //"Gravi Star", "Ultimate Nether Star"

		ModHandler.addCraftingRecipe(ItemList.Field_Generator_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"WCW", "CGC", "WCW",
						'G', ItemList.QuantumEye.get(1),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Advanced),
						'W', new UnificationEntry(OrePrefix.wireGt04, Materials.Osmium)});

		ModHandler.addCraftingRecipe(ItemList.Field_Generator_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"WCW", "CGC", "WCW",
						'G', new UnificationEntry(OrePrefix.gem, Materials.NetherStar),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Elite),
						'W', new UnificationEntry(OrePrefix.wireGt08, Materials.Osmium)});

		ModHandler.addCraftingRecipe(ItemList.Field_Generator_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"WCW", "CGC", "WCW",
						'G', ItemList.QuantumStar.get(1),
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Master),
						'W', new UnificationEntry(OrePrefix.wireGt16, Materials.Osmium)});


		ModHandler.addCraftingRecipe(ItemList.Component_Sawblade_Diamond.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{" D ", "DGD", " D ",
						'D', new UnificationEntry(OrePrefix.dustSmall, Materials.Diamond),
						'G', new UnificationEntry(OrePrefix.gearGt, Materials.CobaltBrass)});

		ModHandler.addCraftingRecipe(ItemList.Component_Grinder_Diamond.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"DSD", "SIS", "DSD",
						'I', OreDictNames.craftingIndustrialDiamond,
						'D', new UnificationEntry(OrePrefix.dust, Materials.Diamond),
						'S', new UnificationEntry(OrePrefix.plate, Materials.Steel)});

		ModHandler.addCraftingRecipe(ItemList.Component_Grinder_Tungsten.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"TST", "SIS", "TST",
						'I', OreDictNames.craftingIndustrialDiamond,
						'T', new UnificationEntry(OrePrefix.plate, Materials.Tungsten),
						'S', new UnificationEntry(OrePrefix.plate, Materials.Steel)});

		ItemList.Upgrade_Muffler.set(addItem(727)); //"Muffler Upgrade", "Makes Machines silent"
		ItemList.Upgrade_Lock.set(addItem(728)); //"Lock Upgrade", "Protects your Machines"

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Aluminium, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Plastic, 2))
				.outputs(ItemList.Upgrade_Muffler.get(1))
				.duration(1600)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Aluminium, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 2))
				.outputs(ItemList.Upgrade_Muffler.get(1))
				.duration(1600)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iron, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Plastic, 2))
				.outputs(ItemList.Upgrade_Muffler.get(1))
				.duration(1600)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iron, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 2))
				.outputs(ItemList.Upgrade_Muffler.get(1))
				.duration(1600)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Plastic, 2))
				.outputs(ItemList.Upgrade_Muffler.get(1))
				.duration(1600)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 2))
				.outputs(ItemList.Upgrade_Muffler.get(1))
				.duration(1600)
				.EUt(2)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Aluminium, 1), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iridium, 1))
				.outputs(ItemList.Upgrade_Lock.get(1))
				.duration(6400)
				.EUt(16)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iron, 1), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iridium, 1))
				.outputs(ItemList.Upgrade_Lock.get(1))
				.duration(6400)
				.EUt(16)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iridium, 1))
				.outputs(ItemList.Upgrade_Lock.get(1))
				.duration(6400)
				.EUt(16)
				.buildAndRegister();

		ItemList.Component_Filter.set(addItem(729).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Zinc, OrePrefix.foil.materialAmount * 16L))).addOreDict(OreDictNames.craftingFilter)); // "Item Filter", "",

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_mesh, 4), OreDictionaryUnifier.get(OrePrefix.foil, Materials.Zinc, 16))
				.fluidInputs(Materials.Plastic.getFluid(144))
				.outputs(ItemList.Component_Filter.get(1))
				.duration(1600)
				.EUt(32)
				.buildAndRegister();

		ItemList.Tool_Cheat.set(addItem(761).addStats(new Behaviour_Scanner(), new ElectricStats(-2000000000, -1))); //"Debug Scanner", "Also an Infinite Energy Source"
		ItemList.Tool_Scanner.set(addItem(762).addStats(new Behaviour_Scanner(), new ElectricStats(400000, 2, true, false))); // "Portable Scanner", "Tricorder",
		ModHandler.addCraftingRecipe(ItemList.Tool_Scanner.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				new Object[]{"EPR", "CSC", "PBP",
						'C', new UnificationEntry(OrePrefix.circuit, Materials.Advanced),
						'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium),
						'E', ItemList.Emitter_MV,
						'R', ItemList.Sensor_MV,
						'S', Items.DIAMOND,
						'B', ItemList.Battery_RE_MV_Lithium});

		ItemList.NC_SensorKit.set(addItem(763).addStats(new Behaviour_SensorKit())); //"GregTech Sensor Kit", "",
		ItemList.Duct_Tape.set(addItem(764).addOreDict(OreDictNames.craftingDuctTape)); //"BrainTech Aerospace Advanced Reinforced Duct Tape FAL-84", "If you can't fix it with this, use more of it!",
		ItemList.McGuffium_239.set(addItem(765)); //"Mc Guffium 239", "42% better than Phlebotnium"

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Good, 4), OreDictionaryUnifier.get(OrePrefix.plate, Materials.StainlessSteel, 2))
				.outputs(ItemList.Schematic.get(1))
				.duration(3200)
				.EUt(4)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(ItemList.Sensor_LV.get(1), ItemList.Emitter_LV.get(1))
				.outputs(ItemList.NC_SensorKit.get(1))
				.duration(1600)
				.EUt(2)
				.buildAndRegister();

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(ItemList.Electric_Pump_LV.get(1), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Basic, 2))
				.outputs(ItemList.FluidRegulator_LV.get(1))
				.duration(800)
				.EUt(4)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(ItemList.Electric_Pump_MV.get(1), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Good, 2))
				.outputs(ItemList.FluidRegulator_MV.get(1))
				.duration(800)
				.EUt(8)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(ItemList.Electric_Pump_HV.get(1), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Advanced, 2))
				.outputs(ItemList.FluidRegulator_HV.get(1))
				.duration(800)
				.EUt(16)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(ItemList.Electric_Pump_EV.get(1), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Elite, 2))
				.outputs(ItemList.FluidRegulator_EV.get(1))
				.duration(800)
				.EUt(32)
				.buildAndRegister();
		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(ItemList.Electric_Pump_IV.get(1), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Master, 2))
				.outputs(ItemList.FluidRegulator_IV.get(1))
				.duration(800)
				.EUt(64)
				.buildAndRegister();
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem itemEntity) {
		int damage = itemEntity.getEntityItem().getItemDamage();
		if (damage < 32000 && damage >= 0 && !itemEntity.worldObj.isRemote) {
			Material material = Material.MATERIAL_REGISTRY.getObjectById(damage % 1000);
			if (material != null) {
				int posX = MathHelper.floor_double(itemEntity.posX);
				int posY = MathHelper.floor_double(itemEntity.posY);
				int posZ = MathHelper.floor_double(itemEntity.posZ);
				OrePrefix prefix = this.orePrefixes[(damage / 1000)];
				if (prefix == OrePrefix.dustImpure || prefix == OrePrefix.dustPure || prefix == OrePrefix.crushed || prefix == OrePrefix.dust) {
					IBlockState blockState = itemEntity.worldObj.getBlockState(new BlockPos(posX, posY, posZ));
					if (blockState.getBlock() == Blocks.CAULDRON) {
						int waterLevel = blockState.getValue(BlockCauldron.LEVEL);
						if (waterLevel > 0) {
							if (prefix == OrePrefix.crushed) {
								itemEntity.setEntityItemStack(OreDictionaryUnifier.get(OrePrefix.crushedPurified, material, itemEntity.getEntityItem().stackSize));
							} else if (prefix == OrePrefix.dust && material == Materials.Wheat) {
								itemEntity.setEntityItemStack(ItemList.Food_Dough.get(itemEntity.getEntityItem().stackSize));
							} else {
								itemEntity.setEntityItemStack(OreDictionaryUnifier.get(OrePrefix.dust, material, itemEntity.getEntityItem().stackSize));
							}
							itemEntity.worldObj.setBlockState(new BlockPos(posX, posY, posZ), blockState.withProperty(BlockCauldron.LEVEL, waterLevel - 1));
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List<String> lines, boolean showAdditionalInfo) {
		super.addInformation(itemStack, player, lines, showAdditionalInfo);
		int damage = itemStack.getItemDamage();
		if (damage < 32000 && damage >= 0) {
			Material material = Material.MATERIAL_REGISTRY.getObjectById(damage % 1000);
			if (material != null) {
				OrePrefix prefix = this.orePrefixes[(damage / 1000)];
				if (prefix == OrePrefix.dustImpure || prefix == OrePrefix.dustPure) {
					lines.add(I18n.format("metaitem.01.tooltip.purify"));
				}
			}
		}
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
		int damage = stack.getItemDamage();
		if (damage >= this.metaItemOffset + 430 && damage <= this.metaItemOffset + 461) {
			return ItemList.Spray_Empty.get(1);
		}
		if (damage == this.metaItemOffset + 479 || damage == this.metaItemOffset + 476) {
			return new ItemStack(this, 1, damage - 2);
		}
		if (damage == this.metaItemOffset + 401) {
			return new ItemStack(this, 1, damage - 1);
		}
		return super.getContainerItem(stack);
	}
}