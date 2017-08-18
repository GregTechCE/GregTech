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
import gregtech.api.util.RandomPotionEffect;
import gregtech.common.items.behaviors.*;
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
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
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
				OrePrefix.crushed, OrePrefix.crushedPurified, OrePrefix.crushedCentrifuged, OrePrefix.gem, OrePrefix.nugget,
				OrePrefix.ingot, OrePrefix.ingotHot, OrePrefix.ingotDouble, OrePrefix.ingotTriple, OrePrefix.ingotQuadruple, OrePrefix.ingotQuintuple,
				OrePrefix.plate, OrePrefix.plateDouble, OrePrefix.plateTriple, OrePrefix.plateQuadruple, OrePrefix.plateQuintuple, OrePrefix.plateDense,
				OrePrefix.stick, OrePrefix.lens, OrePrefix.round, OrePrefix.bolt, OrePrefix.screw, OrePrefix.ring, OrePrefix.foil,
				OrePrefix.cell, OrePrefix.cellPlasma, null);

		//it's here because OrePrefix.cell is handled by gt.metaitem.01
		ItemList.Cell_Empty.set(OreDictionaryUnifier.get(OrePrefix.cell, null));
		ItemList.Cell_Air.set(OreDictionaryUnifier.get(OrePrefix.cell, Materials.Air));
		ItemList.Cell_Water.set(OreDictionaryUnifier.get(OrePrefix.cell, Materials.Water));
		ItemList.Cell_Lava.set(OreDictionaryUnifier.get(OrePrefix.cell, Materials.Lava));

		OrePrefix.cellPlasma.containerItem = ItemList.Cell_Empty.get(1);
		OrePrefix.cell.containerItem = ItemList.Cell_Empty.get(1);

		//17000 -> OrePrefix.plate
		int woodItemMeta = 17000 + Material.MATERIAL_REGISTRY.getIDForObject(Materials.Wood);
//		addItem(woodItemMeta, "").setBurnValue(1600);
		ModHandler.addCompressionRecipe(OreDictionaryUnifier.getDust(Materials.Wood, 8), new ItemStack(this, 1, woodItemMeta));

		ItemStack stack = new ItemStack(this, 1, woodItemMeta);
		stack.setStackDisplayName("The holy Planks of Sengir");
		GT_Utility.ItemNBT.addEnchantment(stack, Enchantments.SMITE, 10);
		ModHandler.addCraftingRecipe(stack, ModHandler.RecipeBits.NOT_REMOVABLE,
				"XXX", "XDX", "XXX",
				'X', OreDictionaryUnifier.getGem(Materials.NetherStar, 1),
				'D', new ItemStack(Blocks.DRAGON_EGG, 1, OreDictionary.WILDCARD_VALUE));

		ItemList.Credit_Greg_Copper.set(addItem(0, "credit.copper"));
		ItemList.Credit_Greg_Cupronickel.set(addItem(1, "credit.cupronickel").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Cupronickel, 907200L))));
		ItemList.Credit_Greg_Silver.set(addItem(2, "credit.silver"));
		ItemList.Credit_Greg_Gold.set(addItem(3, "credit.gold"));
		ItemList.Credit_Greg_Platinum.set(addItem(4, "credit.platinum"));
		ItemList.Credit_Greg_Osmium.set(addItem(5, "credit.osmium"));
		ItemList.Credit_Greg_Naquadah.set(addItem(6, "credit.naquadah"));
		ItemList.Credit_Greg_Neutronium.set(addItem(7, "credit.neutronium"));

		ItemList.Coin_Gold_Ancient.set(addItem(8, "coin.gold.ancient").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Gold, 907200L))));
		ItemList.Coin_Doge.set(addItem(9, "coin.doge").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Brass, 907200L))));
		ItemList.Coin_Chocolate.set(addItem(10, "coin.chocolate").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Gold, OrePrefix.foil.materialAmount))).addStats(new FoodStats(1, 0.1F, false, true, OreDictionaryUnifier.get(OrePrefix.foil, Materials.Gold, 1), new RandomPotionEffect(MobEffects.SPEED, 200, 1, 10))));

		ModHandler.addShapelessCraftingRecipe(ItemList.Coin_Chocolate.get(1),
				new UnificationEntry(OrePrefix.dust, Materials.Cocoa),
				new UnificationEntry(OrePrefix.dust, Materials.Milk),
				new UnificationEntry(OrePrefix.dust, Materials.Sugar),
				new UnificationEntry(OrePrefix.foil, Materials.Gold));

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Copper.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Iron);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Iron.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Silver);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Silver.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Gold);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Gold.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Platinum);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Platinum.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Osmium);


		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Iron.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Copper,
				ItemList.Credit_Copper,
				ItemList.Credit_Copper,
				ItemList.Credit_Copper,
				ItemList.Credit_Copper,
				ItemList.Credit_Copper,
				ItemList.Credit_Copper,
				ItemList.Credit_Copper);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Silver.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Iron,
				ItemList.Credit_Iron,
				ItemList.Credit_Iron,
				ItemList.Credit_Iron,
				ItemList.Credit_Iron,
				ItemList.Credit_Iron,
				ItemList.Credit_Iron,
				ItemList.Credit_Iron);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Gold.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Silver,
				ItemList.Credit_Silver,
				ItemList.Credit_Silver,
				ItemList.Credit_Silver,
				ItemList.Credit_Silver,
				ItemList.Credit_Silver,
				ItemList.Credit_Silver,
				ItemList.Credit_Silver);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Platinum.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Gold,
				ItemList.Credit_Gold,
				ItemList.Credit_Gold,
				ItemList.Credit_Gold,
				ItemList.Credit_Gold,
				ItemList.Credit_Gold,
				ItemList.Credit_Gold,
				ItemList.Credit_Gold);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Osmium.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Platinum,
				ItemList.Credit_Platinum,
				ItemList.Credit_Platinum,
				ItemList.Credit_Platinum,
				ItemList.Credit_Platinum,
				ItemList.Credit_Platinum,
				ItemList.Credit_Platinum,
				ItemList.Credit_Platinum);


		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Copper.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Cupronickel);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Cupronickel.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Silver);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Silver.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Gold);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Gold.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Platinum);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Platinum.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Osmium);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Osmium.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Naquadah);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Naquadah.get(8),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Neutronium);


		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Cupronickel.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Copper,
				ItemList.Credit_Greg_Copper,
				ItemList.Credit_Greg_Copper,
				ItemList.Credit_Greg_Copper,
				ItemList.Credit_Greg_Copper,
				ItemList.Credit_Greg_Copper,
				ItemList.Credit_Greg_Copper,
				ItemList.Credit_Greg_Copper);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Silver.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Cupronickel,
				ItemList.Credit_Greg_Cupronickel,
				ItemList.Credit_Greg_Cupronickel,
				ItemList.Credit_Greg_Cupronickel,
				ItemList.Credit_Greg_Cupronickel,
				ItemList.Credit_Greg_Cupronickel,
				ItemList.Credit_Greg_Cupronickel,
				ItemList.Credit_Greg_Cupronickel);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Gold.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Silver,
				ItemList.Credit_Greg_Silver,
				ItemList.Credit_Greg_Silver,
				ItemList.Credit_Greg_Silver,
				ItemList.Credit_Greg_Silver,
				ItemList.Credit_Greg_Silver,
				ItemList.Credit_Greg_Silver,
				ItemList.Credit_Greg_Silver);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Platinum.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Gold,
				ItemList.Credit_Greg_Gold,
				ItemList.Credit_Greg_Gold,
				ItemList.Credit_Greg_Gold,
				ItemList.Credit_Greg_Gold,
				ItemList.Credit_Greg_Gold,
				ItemList.Credit_Greg_Gold,
				ItemList.Credit_Greg_Gold);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Osmium.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Platinum,
				ItemList.Credit_Greg_Platinum,
				ItemList.Credit_Greg_Platinum,
				ItemList.Credit_Greg_Platinum,
				ItemList.Credit_Greg_Platinum,
				ItemList.Credit_Greg_Platinum,
				ItemList.Credit_Greg_Platinum,
				ItemList.Credit_Greg_Platinum);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Naquadah.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Osmium,
				ItemList.Credit_Greg_Osmium,
				ItemList.Credit_Greg_Osmium,
				ItemList.Credit_Greg_Osmium,
				ItemList.Credit_Greg_Osmium,
				ItemList.Credit_Greg_Osmium,
				ItemList.Credit_Greg_Osmium,
				ItemList.Credit_Greg_Osmium);

		ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Neutronium.get(1),
				ModHandler.RecipeBits.KEEPNBT | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Credit_Greg_Naquadah,
				ItemList.Credit_Greg_Naquadah,
				ItemList.Credit_Greg_Naquadah,
				ItemList.Credit_Greg_Naquadah,
				ItemList.Credit_Greg_Naquadah,
				ItemList.Credit_Greg_Naquadah,
				ItemList.Credit_Greg_Naquadah,
				ItemList.Credit_Greg_Naquadah);


		ItemList.Component_Minecart_Wheels_Iron.set(addItem(100, "component.minecart.wheels.iron"));
		ItemList.Component_Minecart_Wheels_Steel.set(addItem(101, "component.minecart.wheels.steel"));

		ModHandler.addCraftingRecipe(ItemList.Component_Minecart_Wheels_Iron.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				" h ", "RSR", " w ",
				'R', new UnificationEntry(OrePrefix.ring, Materials.AnyIron),
				'S', new UnificationEntry(OrePrefix.stick, Materials.AnyIron));

		ModHandler.addCraftingRecipe(ItemList.Component_Minecart_Wheels_Steel.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				" h ", "RSR", " w ",
				'R', new UnificationEntry(OrePrefix.ring, Materials.Steel),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Steel));


		ItemList.Shape_Empty.set(addItem(300, "shape.empty"));

		ModHandler.addCraftingRecipe(ItemList.Shape_Empty.get(1),
				ModHandler.RecipeBits.MIRRORED | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"hf", "PP", "PP",
				'P', new UnificationEntry(OrePrefix.plate, Materials.Steel));

		ItemList.Shape_Mold_Plate.set(addItem(301, "shape.mold.plate"));
		ItemList.Shape_Mold_Casing.set(addItem(302, "shape.mold.casing"));
		ItemList.Shape_Mold_Gear.set(addItem(303, "shape.mold.gear"));
		ItemList.Shape_Mold_Credit.set(addItem(304, "shape.mold.credit"));
		ItemList.Shape_Mold_Bottle.set(addItem(305, "shape.mold.bottle"));
		ItemList.Shape_Mold_Ingot.set(addItem(306, "shape.mold.ingot"));
		ItemList.Shape_Mold_Ball.set(addItem(307, "shape.mold.ball"));
		ItemList.Shape_Mold_Block.set(addItem(308, "shape.mold.block"));
		ItemList.Shape_Mold_Nugget.set(addItem(309, "shape.mold.nugget"));
		ItemList.Shape_Mold_Bun.set(addItem(310, "shape.mold.bun"));
		ItemList.Shape_Mold_Bread.set(addItem(311, "shape.mold.bread"));
		ItemList.Shape_Mold_Baguette.set(addItem(312, "shape.mold.baguette"));
		ItemList.Shape_Mold_Cylinder.set(addItem(313, "shape.mold.cylinder"));
		ItemList.Shape_Mold_Anvil.set(addItem(314, "shape.mold.anvil"));
		ItemList.Shape_Mold_Name.set(addItem(315, "shape.mold.name"));
		ItemList.Shape_Mold_Gear_Small.set(addItem(317, "shape.mold.gear.small"));

		ModHandler.removeRecipe(new ItemStack(Blocks.GLASS), null, new ItemStack(Blocks.GLASS), null, new ItemStack(Blocks.GLASS));

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Credit.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"h  ", textShape, textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Plate.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				" h ", textShape, textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Casing.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"  h", textShape, textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Gear.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, " Ph", textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bottle.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, textShape, "  h",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Ingot.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, textShape, " h ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Ball.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, textShape, "h  ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Block.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, "hP ", textEmptyRow,
				'P', ItemList.Shape_Empty);


		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Nugget.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P h", textEmptyRow, textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bun.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P  ", "  h", textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bread.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P  ", textEmptyRow, "  h",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Baguette.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P  ", textEmptyRow, " h ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Cylinder.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"  P", textEmptyRow, "  h",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Anvil.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"  P", textEmptyRow, " h ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Name.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"  P", textEmptyRow, "h  ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Gear_Small.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, textEmptyRow, "h P",
				'P', ItemList.Shape_Empty);


		ItemList.Shape_Extruder_Plate.set(addItem(350, "shape.extruder.plate"));
		ItemList.Shape_Extruder_Rod.set(addItem(351, "shape.extruder.rod"));
		ItemList.Shape_Extruder_Bolt.set(addItem(352, "shape.extruder.bolt"));
		ItemList.Shape_Extruder_Ring.set(addItem(353, "shape.extruder.ring"));
		ItemList.Shape_Extruder_Cell.set(addItem(354, "shape.extruder.cell"));
		ItemList.Shape_Extruder_Ingot.set(addItem(355, "shape.extruder.ingot"));
		ItemList.Shape_Extruder_Wire.set(addItem(356, "shape.extruder.wire"));
		ItemList.Shape_Extruder_Casing.set(addItem(357, "shape.extruder.casing"));
		ItemList.Shape_Extruder_Pipe_Tiny.set(addItem(358, "shape.extruder.pipe.tiny"));
		ItemList.Shape_Extruder_Pipe_Small.set(addItem(359, "shape.extruder.pipe.small"));
		ItemList.Shape_Extruder_Pipe_Medium.set(addItem(360, "shape.extruder.pipe.medium"));
		ItemList.Shape_Extruder_Pipe_Large.set(addItem(361, "shape.extruder.pipe.large"));
		ItemList.Shape_Extruder_Pipe_Huge.set(addItem(362, "shape.extruder.pipe.huge"));
		ItemList.Shape_Extruder_Block.set(addItem(363, "shape.extruder.block"));
		ItemList.Shape_Extruder_Sword.set(addItem(364, "shape.extruder.sword"));
		ItemList.Shape_Extruder_Pickaxe.set(addItem(365, "shape.extruder.pickaxe"));
		ItemList.Shape_Extruder_Shovel.set(addItem(366, "shape.extruder.shovel"));
		ItemList.Shape_Extruder_Axe.set(addItem(367, "shape.extruder.axe"));
		ItemList.Shape_Extruder_Hoe.set(addItem(368, "shape.extruder.hoe"));
		ItemList.Shape_Extruder_Hammer.set(addItem(369, "shape.extruder.hammer"));
		ItemList.Shape_Extruder_File.set(addItem(370, "shape.extruder.file"));
		ItemList.Shape_Extruder_Saw.set(addItem(371, "shape.extruder.saw"));
		ItemList.Shape_Extruder_Gear.set(addItem(372, "shape.extruder.gear"));
		ItemList.Shape_Extruder_Bottle.set(addItem(373, "shape.extruder.bottle"));

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Bolt.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"x  ", textShape, textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Cell.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				" x ", textShape, textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Ingot.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"  x", textShape, textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Ring.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, " Px", textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Rod.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, textShape, "  x",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Wire.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, textShape, " x ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Casing.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, textShape, "x  ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Plate.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, "xP ", textEmptyRow,
				'P', ItemList.Shape_Empty);


		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Block.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P x", textEmptyRow, textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Small.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P  ", "  x", textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Large.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P  ", textEmptyRow, "  x",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Medium.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P  ", textEmptyRow, " x ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Sword.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"  P", textEmptyRow, "  x",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pickaxe.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"  P", textEmptyRow, " x ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Shovel.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"  P", textEmptyRow, "x  ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Axe.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"  P", "x  ", textEmptyRow,
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Hoe.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, textEmptyRow, "x P",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Hammer.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, "x  ", "  P",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_File.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"x  ", textEmptyRow, "  P",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Saw.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				" x ", textEmptyRow, "  P",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Gear.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"x  ", textEmptyRow, "P  ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Tiny.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				" x ", textEmptyRow, "P  ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Huge.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"  x", textEmptyRow, "P  ",
				'P', ItemList.Shape_Empty);

		ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Bottle.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				textEmptyRow, "  x", "P  ",
				'P', ItemList.Shape_Empty);


		ItemList.Shape_Slicer_Flat.set(addItem(398, "shape.slicer.flat"));
		ItemList.Shape_Slicer_Stripes.set(addItem(399, "shape.slicer.stripes"));

		ModHandler.addCraftingRecipe(ItemList.Shape_Slicer_Flat.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"hXS", textShape, "fXd",
				'P', ItemList.Shape_Extruder_Block,
				'X', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
				'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel));

		ModHandler.addCraftingRecipe(ItemList.Shape_Slicer_Stripes.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"hXS", "XPX", "fXd",
				'P', ItemList.Shape_Extruder_Block,
				'X', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
				'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel));


		ItemList.Fuel_Can_Plastic_Empty.set(addItem(400, "fuel.can.plastic.empty").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Plastic, OrePrefix.plate.materialAmount))));
		ItemList.Fuel_Can_Plastic_Filled.set(addItem(401, "fuel.can.plastic.filled").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Plastic, OrePrefix.plate.materialAmount))));

		ModHandler.addCraftingRecipe(ItemList.Fuel_Can_Plastic_Empty.get(7),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				" PP", "P P", "PPP",
				'P', new UnificationEntry(OrePrefix.plate, Materials.Plastic));

		ItemList.Spray_Empty.set(addItem(402, "spray.empty").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Tin, OrePrefix.plate.materialAmount * 2L), new MaterialStack(Materials.Redstone, OrePrefix.dust.materialAmount))));

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Redstone, 1), OreDictionaryUnifier.get(OrePrefix.cell, null, 1))
				.outputs(ItemList.Spray_Empty.get(1))
				.duration(800)
				.EUt(1)
				.buildAndRegister();

		ItemList.ThermosCan_Empty.set(addItem(404, "thermoscan.empty").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Aluminium, OrePrefix.plate.materialAmount + 2L * OrePrefix.ring.materialAmount))));

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.Aluminium, 1), OreDictionaryUnifier.get(OrePrefix.ring, Materials.Aluminium, 2))
				.outputs(ItemList.ThermosCan_Empty.get(1))
				.duration(800)
				.EUt(1)
				.buildAndRegister();

		ItemList.Large_Fluid_Cell_Steel.set(addItem(405, "large.fluid.cell.steel").addStats(new FluidStats(16000, Integer.MAX_VALUE, Integer.MAX_VALUE)).setMaxStackSize(16).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount))));

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.Steel, 1), OreDictionaryUnifier.get(OrePrefix.ring, Materials.Steel, 2))
				.outputs(ItemList.Large_Fluid_Cell_Steel.get(1))
				.duration(100)
				.EUt(64)
				.buildAndRegister();

		ItemList.Large_Fluid_Cell_TungstenSteel.set(addItem(406, "large.fluid.cell.tungstensteel").addStats(new FluidStats(64000, Integer.MAX_VALUE, Integer.MAX_VALUE)).setMaxStackSize(16).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.TungstenSteel, OrePrefix.plate.materialAmount * 2L + 2L * OrePrefix.ring.materialAmount))));

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.TungstenSteel, 1), OreDictionaryUnifier.get(OrePrefix.ring, Materials.TungstenSteel, 2))
				.outputs(ItemList.Large_Fluid_Cell_TungstenSteel.get(1))
				.duration(200)
				.EUt(256)
				.buildAndRegister();

		for (byte i = 0; i < 16; i = (byte) (i + 1)) {
			IItemBehaviour behaviour = new Behaviour_Spray_Color(ItemList.Spray_Empty.get(1), ItemList.SPRAY_CAN_DYES_USED[i].get(1), ItemList.SPRAY_CAN_DYES[i].get(1), 512L, i);
			ItemList.SPRAY_CAN_DYES[i].set(addItem(430 + 2 * i, "spray.can.dyes", EnumDyeColor.byDyeDamage(i).getUnlocalizedName()).addStats(behaviour));
			ItemList.SPRAY_CAN_DYES_USED[i].set(addItem(431 + 2 * i, "spray.can.dyes.used", EnumDyeColor.byDyeDamage(i).getUnlocalizedName()).addStats(behaviour));
		}

		IItemBehaviour behaviour = new Behaviour_Lighter(null, ItemList.Tool_Matches.get(1), ItemList.Tool_Matches.get(1), 1L);
		ItemList.Tool_Matches.set(addItem(471, "tool.matches").addStats(behaviour));

		behaviour = new Behaviour_Lighter(null, ItemList.Tool_MatchBox_Used.get(1), ItemList.Tool_MatchBox_Full.get(1), 16L);
		ItemList.Tool_MatchBox_Used.set(addItem(472, "tool.matchbox.used").addStats(behaviour));
		ItemList.Tool_MatchBox_Full.set(addItem(473, "tool.matchbox.full").addStats(behaviour));

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Phosphorus, 1))
				.outputs(ItemList.Tool_Matches.get(1))
				.duration(16)
				.EUt(16)
				.buildAndRegister();

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Phosphor, 1))
				.outputs(ItemList.Tool_Matches.get(1))
				.duration(16)
				.EUt(16)
				.buildAndRegister();

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 4), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Phosphorus, 1))
				.outputs(ItemList.Tool_Matches.get(4))
				.duration(64)
				.EUt(16)
				.buildAndRegister();

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 4), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Phosphor, 1))
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

		ItemList.Tool_Lighter_Invar_Empty.set(addItem(474, "tool.lighter.invar.empty").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L))));

		behaviour = new Behaviour_Lighter(ItemList.Tool_Lighter_Invar_Empty.get(1), ItemList.Tool_Lighter_Invar_Used.get(1), ItemList.Tool_Lighter_Invar_Full.get(1), 100L);
		ItemList.Tool_Lighter_Invar_Used.set(addItem(475, "tool.lighter.invar.used").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L))).addStats(behaviour));
		ItemList.Tool_Lighter_Invar_Full.set(addItem(476, "tool.lighter.invar.full").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Invar, OrePrefix.plate.materialAmount * 2L))).addStats(behaviour));

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Invar, 2), new ItemStack(Items.FLINT, 1))
				.outputs(ItemList.Tool_Lighter_Invar_Empty.get(1))
				.duration(256)
				.EUt(16)
				.buildAndRegister();

		ItemList.Tool_Lighter_Platinum_Empty.set(addItem(477, "tool.lighter.platinum.empty").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L))));

		behaviour = new Behaviour_Lighter(ItemList.Tool_Lighter_Platinum_Empty.get(1), ItemList.Tool_Lighter_Platinum_Used.get(1), ItemList.Tool_Lighter_Platinum_Full.get(1), 1000L);
		ItemList.Tool_Lighter_Platinum_Used.set(addItem(478, "tool.lighter.platinum.used").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L))).addStats(behaviour));
		ItemList.Tool_Lighter_Platinum_Full.set(addItem(479, "tool.lighter.platinum.full").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Platinum, OrePrefix.plate.materialAmount * 2L))).addStats(behaviour));

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Platinum, 2), new ItemStack(Items.FLINT, 1))
				.outputs(ItemList.Tool_Lighter_Platinum_Empty.get(1))
				.duration(256)
				.EUt(256)
				.buildAndRegister();

		ItemList.Ingot_IridiumAlloy.set(addItem(480, "ingot.iridiumalloy"));

		ModHandler.addRollingMachineRecipe(ItemList.Ingot_IridiumAlloy.get(1),
				new Object[]{"IAI", "ADA", "IAI",
						'D', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "iridiumplate", true) ? OreDictNames.craftingIndustrialDiamond : new UnificationEntry(OrePrefix.dust, Materials.Diamond),
						'A', OrePrefix.plateAlloy.name() + "Advanced",
						'I', new UnificationEntry(OrePrefix.plate, Materials.Iridium)});

		ModHandler.addCraftingRecipe(ItemList.Ingot_IridiumAlloy.get(1),
				ModHandler.RecipeBits.NOT_REMOVABLE,
				"IAI", "ADA", "IAI",
				'D', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "iridiumplate", true) ? OreDictNames.craftingIndustrialDiamond : new UnificationEntry(OrePrefix.dust, Materials.Diamond),
				'A', OrePrefix.plateAlloy.name() + "Advanced",
				'I', new UnificationEntry(OrePrefix.plate, Materials.Iridium));

		ItemList.Paper_Printed_Pages.set(addItem(481, "paper.printed.pages").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L))).addStats(new Behaviour_PrintedPages()));
		ItemList.Paper_Magic_Empty.set(addItem(482, "paper.magic.empty").setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 3628800L))));
		ItemList.Paper_Magic_Page.set(addItem(483, "paper.magic.page").setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 3628800L))));
		ItemList.Paper_Magic_Pages.set(addItem(484, "paper.magic.pages").setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 10886400L))));
		ItemList.Paper_Punch_Card_Empty.set(addItem(485, "paper.punch.card.empty").setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 7257600L))));
		ItemList.Paper_Punch_Card_Encoded.set(addItem(486, "paper.punch.card.encoded").setInvisible().setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Paper, 7257600L))));

		ItemList.Schematic.set(addItem(490, "schematic").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L))));
		ItemList.Schematic_Crafting.set(addItem(491, "schematic.crafting").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L))));
		ItemList.Schematic_1by1.set(addItem(495, "schematic.1by1").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L))));
		ItemList.Schematic_2by2.set(addItem(496, "schematic.2by2").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L))));
		ItemList.Schematic_3by3.set(addItem(497, "schematic.3by3").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L))));
		ItemList.Schematic_Dust.set(addItem(498, "schematic.dust").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.StainlessSteel, 7257600L))));

		ModHandler.addCraftingRecipe(ItemList.Schematic_1by1.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				"d  ", textShape, textEmptyRow,
				'P', ItemList.Schematic);

		ModHandler.addCraftingRecipe(ItemList.Schematic_2by2.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				" d ", textShape, textEmptyRow,
				'P', ItemList.Schematic);

		ModHandler.addCraftingRecipe(ItemList.Schematic_3by3.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				"  d", textShape, textEmptyRow,
				'P', ItemList.Schematic);

		ModHandler.addCraftingRecipe(ItemList.Schematic_Dust.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				textEmptyRow, textShape, "  d",
				'P', ItemList.Schematic);


		ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Schematic_Crafting);

		ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Schematic_1by1);

		ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Schematic_2by2);

		ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Schematic_3by3);

		ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1),
				ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE,
				ItemList.Schematic_Dust);


		ItemList.Battery_Hull_LV.set(addItem(500, "battery.hull.lv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount))));
		ItemList.Battery_Hull_MV.set(addItem(501, "battery.hull.hv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount * 3L))));
		ItemList.Battery_Hull_HV.set(addItem(502, "battery.hull.mv").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.BatteryAlloy, OrePrefix.plate.materialAmount * 9L))));

		ModHandler.addCraftingRecipe(ItemList.Battery_Hull_LV.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, "C", "P", "P", 'P', new UnificationEntry(OrePrefix.plate, Materials.BatteryAlloy), 'C', OreDictNames.craftingWireTin);
		ModHandler.addCraftingRecipe(ItemList.Battery_Hull_MV.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, "C C", "PPP", "PPP", 'P', new UnificationEntry(OrePrefix.plate, Materials.BatteryAlloy), 'C', OreDictNames.craftingWireCopper);

		ItemList.Battery_RE_ULV_Tantalum.set(addItem(499, "battery.re.ulv.tantalum").addStats(new ElectricStats(1000, 0)));

		ItemList.Battery_SU_LV_SulfuricAcid.set(addItem(510, "battery.su.lv.sulfuricacid").addStats(new ElectricStats(18000, 1, false)));
		ItemList.Battery_SU_LV_Mercury.set(addItem(511, "battery.su.lv.mercury").addStats(new ElectricStats(32000, 1, false)));

		ItemList.Battery_RE_LV_Cadmium.set(addItem(517, "battery.re.lv.cadmium").addStats(new ElectricStats(75000, 1)));
		ItemList.Battery_RE_LV_Lithium.set(addItem(518, "battery.re.lv.lithium").addStats(new ElectricStats(100000, 1)));
		ItemList.Battery_RE_LV_Sodium.set(addItem(519, "battery.re.lv.sodium").addStats(new ElectricStats(50000, 1)));

		ItemList.Battery_SU_MV_SulfuricAcid.set(addItem(520, "battery.su.mv.sulfuricacid").addStats(new ElectricStats(72000, 2, false)));
		ItemList.Battery_SU_MV_Mercury.set(addItem(521, "battery.su.mv.mercury").addStats(new ElectricStats(128000, 2, false)));

		ItemList.Battery_RE_MV_Cadmium.set(addItem(527, "battery.re.mv.cadmium").addStats(new ElectricStats(300000, 2)));
		ItemList.Battery_RE_MV_Lithium.set(addItem(528, "battery.re.mv.lithium").addStats(new ElectricStats(400000, 2)));
		ItemList.Battery_RE_MV_Sodium.set(addItem(529, "battery.re.mv.sodium").addStats(new ElectricStats(200000, 2)));

		ItemList.Battery_SU_HV_SulfuricAcid.set(addItem(530, "battery.su.hv.sulfuricacid").addStats(new ElectricStats(288000, 3, false)));
		ItemList.Battery_SU_HV_Mercury.set(addItem(531, "battery.su.hv.mercury").addStats(new ElectricStats(512000, 3, false)));

		ItemList.Battery_RE_HV_Cadmium.set(addItem(537, "battery.re.hv.cadmium").addStats(new ElectricStats(1200000, 3)));
		ItemList.Battery_RE_HV_Lithium.set(addItem(538, "battery.re.hv.lithium").addStats(new ElectricStats(1600000, 3)));
		ItemList.Battery_RE_HV_Sodium.set(addItem(539, "battery.re.hv.sodium").addStats(new ElectricStats(800000, 3)));

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

		ItemList.Energy_LapotronicOrb.set(addItem(597, "energy.lapotronicorb").addStats(new ElectricStats(100000000, 5)).setUnificationData(OrePrefix.battery, Materials.Ultimate));
		ItemList.Energy_LapotronicOrb2.set(addItem(598, "energy.lapotronicorb2").addStats(new ElectricStats(1000000000, 6)).setUnificationData(OrePrefix.battery, Materials.Ultimate));

		ItemList.ZPM.set(addItem(599, "zpm").addStats(new ElectricStats(2000000000000L, 7, false)));
		ItemList.ZPM2.set(addItem(605, "zpm2").addStats(new ElectricStats(Long.MAX_VALUE, 8)));

		ItemList.Electric_Motor_LV.set(addItem(600, "electric.motor.lv"));
		ItemList.Electric_Motor_MV.set(addItem(601, "electric.motor.mv"));
		ItemList.Electric_Motor_HV.set(addItem(602, "electric.motor.hv"));
		ItemList.Electric_Motor_EV.set(addItem(603, "electric.motor.ev"));
		ItemList.Electric_Motor_IV.set(addItem(604, "electric.motor.iv"));
		ItemList.Electric_Motor_LuV.set(addItem(606, "electric.motor.luv"));
		ItemList.Electric_Motor_ZPM.set(addItem(607, "electric.motor.zpm"));
		ItemList.Electric_Motor_UV.set(addItem(608, "electric.motor.uv"));

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"CWR", "WIW", "RWC",
				'I', new UnificationEntry(OrePrefix.stick, Materials.IronMagnetic),
				'R', new UnificationEntry(OrePrefix.stick, Materials.AnyIron),
				'W', new UnificationEntry(OrePrefix.wireGt01, Materials.AnyCopper),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin));

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE,
				"CWR", "WIW", "RWC",
				'I', new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic),
				'R', new UnificationEntry(OrePrefix.stick, Materials.Steel),
				'W', new UnificationEntry(OrePrefix.wireGt01, Materials.AnyCopper),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin));

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"CWR", "WIW", "RWC",
				'I', new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic),
				'R', new UnificationEntry(OrePrefix.stick, Materials.Aluminium),
				'W', new UnificationEntry(OrePrefix.wireGt02, Materials.AnyCopper),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper));

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"CWR", "WIW", "RWC",
				'I', new UnificationEntry(OrePrefix.stick, Materials.SteelMagnetic),
				'R', new UnificationEntry(OrePrefix.stick, Materials.StainlessSteel),
				'W', new UnificationEntry(OrePrefix.wireGt04, Materials.AnyCopper),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold));

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"CWR", "WIW", "RWC",
				'I', new UnificationEntry(OrePrefix.stick, Materials.NeodymiumMagnetic),
				'R', new UnificationEntry(OrePrefix.stick, Materials.Titanium),
				'W', new UnificationEntry(OrePrefix.wireGt08, Materials.AnnealedCopper),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium));

		ModHandler.addCraftingRecipe(ItemList.Electric_Motor_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"CWR", "WIW", "RWC",
				'I', new UnificationEntry(OrePrefix.stick, Materials.NeodymiumMagnetic),
				'R', new UnificationEntry(OrePrefix.stick, Materials.TungstenSteel),
				'W', new UnificationEntry(OrePrefix.wireGt16, Materials.AnnealedCopper),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten));


		ItemList.Electric_Pump_LV.set(addItem(610, "electric.pump.lv"));
		ItemList.Electric_Pump_MV.set(addItem(611, "electric.pump.mv"));
		ItemList.Electric_Pump_HV.set(addItem(612, "electric.pump.hv"));
		ItemList.Electric_Pump_EV.set(addItem(613, "electric.pump.ev"));
		ItemList.Electric_Pump_IV.set(addItem(614, "electric.pump.iv"));
		ItemList.Electric_Pump_LuV.set(addItem(620, "electric.pump.luv"));
		ItemList.Electric_Pump_ZPM.set(addItem(621, "electric.pump.zpm"));
		ItemList.Electric_Pump_UV.set(addItem(622, "electric.pump.uv"));

		ItemList.FluidRegulator_LV.set(addItem(615, "fluidregulator.lv"));
		ItemList.FluidRegulator_MV.set(addItem(616, "fluidregulator.mv"));
		ItemList.FluidRegulator_HV.set(addItem(617, "fluidregulator.hv"));
		ItemList.FluidRegulator_EV.set(addItem(618, "fluidregulator.ev"));
		ItemList.FluidRegulator_IV.set(addItem(619, "fluidregulator.iv"));

		ItemList.FluidFilter.set(addItem(635, "fluidfilter"));

		ItemList.Rotor_LV.set(addItem(620, "rotor.lv").setUnificationData(OrePrefix.rotor, Materials.Tin));
		ItemList.Rotor_MV.set(addItem(621, "rotor.mv").setUnificationData(OrePrefix.rotor, Materials.Bronze));
		ItemList.Rotor_HV.set(addItem(622, "rotor.hv").setUnificationData(OrePrefix.rotor, Materials.Steel));
		ItemList.Rotor_EV.set(addItem(623, "rotor.ev").setUnificationData(OrePrefix.rotor, Materials.StainlessSteel));
		ItemList.Rotor_IV.set(addItem(624, "rotor.iv").setUnificationData(OrePrefix.rotor, Materials.TungstenSteel));

		ModHandler.addCraftingRecipe(ItemList.Electric_Pump_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"SXO", "dPw", "OMW",
				'M', ItemList.Electric_Motor_LV,
				'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
				'X', new UnificationEntry(OrePrefix.rotor, Materials.Tin),
				'S', new UnificationEntry(OrePrefix.screw, Materials.Tin),
				'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin),
				'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Bronze));

		ModHandler.addCraftingRecipe(ItemList.Electric_Pump_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"SXO", "dPw", "OMW",
				'M', ItemList.Electric_Motor_MV,
				'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
				'X', new UnificationEntry(OrePrefix.rotor, Materials.Bronze),
				'S', new UnificationEntry(OrePrefix.screw, Materials.Bronze),
				'W', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper),
				'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Steel));

		ModHandler.addCraftingRecipe(ItemList.Electric_Pump_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"SXO", "dPw", "OMW",
				'M', ItemList.Electric_Motor_HV,
				'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
				'X', new UnificationEntry(OrePrefix.rotor, Materials.Steel),
				'S', new UnificationEntry(OrePrefix.screw, Materials.Steel),
				'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold),
				'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.StainlessSteel));

		ModHandler.addCraftingRecipe(ItemList.Electric_Pump_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"SXO", "dPw", "OMW",
				'M', ItemList.Electric_Motor_EV,
				'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
				'X', new UnificationEntry(OrePrefix.rotor, Materials.StainlessSteel),
				'S', new UnificationEntry(OrePrefix.screw, Materials.StainlessSteel),
				'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium),
				'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.Titanium));

		ModHandler.addCraftingRecipe(ItemList.Electric_Pump_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"SXO", "dPw", "OMW",
				'M', ItemList.Electric_Motor_IV,
				'O', new UnificationEntry(OrePrefix.ring, Materials.Rubber),
				'X', new UnificationEntry(OrePrefix.rotor, Materials.TungstenSteel),
				'S', new UnificationEntry(OrePrefix.screw, Materials.TungstenSteel),
				'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten),
				'P', new UnificationEntry(OrePrefix.pipeMedium, Materials.TungstenSteel));

		ItemList.Conveyor_Module_LV.set(addItem(630, "conveyor.module.lv"));
		ItemList.Conveyor_Module_MV.set(addItem(631, "conveyor.module.mv"));
		ItemList.Conveyor_Module_HV.set(addItem(632, "conveyor.module.hv"));
		ItemList.Conveyor_Module_EV.set(addItem(633, "conveyor.module.ev"));
		ItemList.Conveyor_Module_IV.set(addItem(634, "conveyor.module.iv"));

		ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"RRR", "MCM", "RRR",
				'M', ItemList.Electric_Motor_LV,
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin),
				'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

		ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"RRR", "MCM", "RRR",
				'M', ItemList.Electric_Motor_MV,
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper),
				'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

		ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"RRR", "MCM", "RRR",
				'M', ItemList.Electric_Motor_HV,
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold),
				'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

		ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"RRR", "MCM", "RRR",
				'M', ItemList.Electric_Motor_EV,
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium),
				'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));

		ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"RRR", "MCM", "RRR",
				'M', ItemList.Electric_Motor_IV,
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten),
				'R', new UnificationEntry(OrePrefix.plate, Materials.Rubber));


		ItemList.Electric_Piston_LV.set(addItem(640, "electric.piston.lv"));
		ItemList.Electric_Piston_MV.set(addItem(641, "electric.piston.mv"));
		ItemList.Electric_Piston_HV.set(addItem(642, "electric.piston.hv"));
		ItemList.Electric_Piston_EV.set(addItem(643, "electric.piston.ev"));
		ItemList.Electric_Piston_IV.set(addItem(644, "electric.piston.iv"));
		ItemList.Electric_Piston_LuV.set(addItem(645, "electric.piston.luv"));
		ItemList.Electric_Piston_ZPM.set(addItem(646, "electric.piston.zpm"));
		ItemList.Electric_Piston_UV.set(addItem(647, "electric.piston.uv"));

		ModHandler.addCraftingRecipe(ItemList.Electric_Piston_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"PPP", "CSS", "CMG",
				'P', new UnificationEntry(OrePrefix.plate, Materials.Steel),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Steel),
				'G', new UnificationEntry(OrePrefix.gearGtSmall, Materials.Steel),
				'M', ItemList.Electric_Motor_LV,
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin));

		ModHandler.addCraftingRecipe(ItemList.Electric_Piston_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"PPP", "CSS", "CMG",
				'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Aluminium),
				'G', new UnificationEntry(OrePrefix.gearGtSmall, Materials.Aluminium),
				'M', ItemList.Electric_Motor_MV,
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper));

		ModHandler.addCraftingRecipe(ItemList.Electric_Piston_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"PPP", "CSS", "CMG",
				'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
				'S', new UnificationEntry(OrePrefix.stick, Materials.StainlessSteel),
				'G', new UnificationEntry(OrePrefix.gearGtSmall, Materials.StainlessSteel),
				'M', ItemList.Electric_Motor_HV,
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold));

		ModHandler.addCraftingRecipe(ItemList.Electric_Piston_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"PPP", "CSS", "CMG",
				'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Titanium),
				'G', new UnificationEntry(OrePrefix.gearGtSmall, Materials.Titanium),
				'M', ItemList.Electric_Motor_EV,
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium));

		ModHandler.addCraftingRecipe(ItemList.Electric_Piston_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"PPP", "CSS", "CMG",
				'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
				'S', new UnificationEntry(OrePrefix.stick, Materials.TungstenSteel),
				'G', new UnificationEntry(OrePrefix.gearGtSmall, Materials.TungstenSteel),
				'M', ItemList.Electric_Motor_IV,
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten));


		ItemList.Robot_Arm_LV.set(addItem(650, "robot.arm.lv"));
		ItemList.Robot_Arm_MV.set(addItem(651, "robot.arm.mv"));
		ItemList.Robot_Arm_HV.set(addItem(652, "robot.arm.hv"));
		ItemList.Robot_Arm_EV.set(addItem(653, "robot.arm.ev"));
		ItemList.Robot_Arm_IV.set(addItem(654, "robot.arm.iv"));
		ItemList.Robot_Arm_LuV.set(addItem(655, "robot.arm.luv"));
		ItemList.Robot_Arm_ZPM.set(addItem(656, "robot.arm.zpm"));
		ItemList.Robot_Arm_UV.set(addItem(657, "robot.arm.uv"));

		ModHandler.addCraftingRecipe(ItemList.Robot_Arm_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"CCC", "MSM", "PES",
				'S', new UnificationEntry(OrePrefix.stick, Materials.Steel),
				'M', ItemList.Electric_Motor_LV, 'P', ItemList.Electric_Piston_LV,
				'E', new UnificationEntry(OrePrefix.circuit, Materials.Basic),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin));

		ModHandler.addCraftingRecipe(ItemList.Robot_Arm_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"CCC", "MSM", "PES",
				'S', new UnificationEntry(OrePrefix.stick, Materials.Aluminium),
				'M', ItemList.Electric_Motor_MV, 'P', ItemList.Electric_Piston_MV,
				'E', new UnificationEntry(OrePrefix.circuit, Materials.Good),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper));

		ModHandler.addCraftingRecipe(ItemList.Robot_Arm_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"CCC", "MSM", "PES",
				'S', new UnificationEntry(OrePrefix.stick, Materials.StainlessSteel),
				'M', ItemList.Electric_Motor_HV, 'P', ItemList.Electric_Piston_HV,
				'E', new UnificationEntry(OrePrefix.circuit, Materials.Advanced),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold));

		ModHandler.addCraftingRecipe(ItemList.Robot_Arm_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"CCC", "MSM", "PES",
				'S', new UnificationEntry(OrePrefix.stick, Materials.Titanium),
				'M', ItemList.Electric_Motor_EV, 'P', ItemList.Electric_Piston_EV,
				'E', new UnificationEntry(OrePrefix.circuit, Materials.Elite),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium));

		ModHandler.addCraftingRecipe(ItemList.Robot_Arm_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"CCC", "MSM", "PES",
				'S', new UnificationEntry(OrePrefix.stick, Materials.TungstenSteel),
				'M', ItemList.Electric_Motor_IV, 'P', ItemList.Electric_Piston_IV,
				'E', new UnificationEntry(OrePrefix.circuit, Materials.Master),
				'C', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten));

		ItemList.Field_Generator_LV.set(addItem(670, "field.generator.lv"));
		ItemList.Field_Generator_MV.set(addItem(671, "field.generator.mv"));
		ItemList.Field_Generator_HV.set(addItem(672, "field.generator.hv"));
		ItemList.Field_Generator_EV.set(addItem(673, "field.generator.ev"));
		ItemList.Field_Generator_IV.set(addItem(674, "field.generator.iv"));
		ItemList.Field_Generator_LuV.set(addItem(675, "field.generator.luv"));
		ItemList.Field_Generator_ZPM.set(addItem(676, "field.generator.zpm"));
		ItemList.Field_Generator_UV.set(addItem(677, "field.generator.uv"));

		ModHandler.addCraftingRecipe(ItemList.Field_Generator_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"WCW", "CGC", "WCW",
				'G', new UnificationEntry(OrePrefix.gem, Materials.EnderPearl),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Basic),
				'W', new UnificationEntry(OrePrefix.wireGt01, Materials.Osmium));

		ModHandler.addCraftingRecipe(ItemList.Field_Generator_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"WCW", "CGC", "WCW",
				'G', new UnificationEntry(OrePrefix.gem, Materials.EnderEye),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Good),
				'W', new UnificationEntry(OrePrefix.wireGt02, Materials.Osmium));

		ItemList.Emitter_LV.set(addItem(680, "emitter.lv"));
		ItemList.Emitter_MV.set(addItem(681, "emitter.mv"));
		ItemList.Emitter_HV.set(addItem(682, "emitter.hv"));
		ItemList.Emitter_EV.set(addItem(683, "emitter.ev"));
		ItemList.Emitter_IV.set(addItem(684, "emitter.iv"));
		ItemList.Emitter_LuV.set(addItem(685, "emitter.luv"));
		ItemList.Emitter_ZPM.set(addItem(686, "emitter.zpm"));
		ItemList.Emitter_UV.set(addItem(687, "emitter.uv"));

		ModHandler.addCraftingRecipe(ItemList.Emitter_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"SSC", "WQS", "CWS",
				'Q', new UnificationEntry(OrePrefix.gem, Materials.Quartzite),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Brass),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Basic),
				'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tin));

		ModHandler.addCraftingRecipe(ItemList.Emitter_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"SSC", "WQS", "CWS",
				'Q', new UnificationEntry(OrePrefix.gem, Materials.NetherQuartz),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Electrum),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Good),
				'W', new UnificationEntry(OrePrefix.cableGt01, Materials.AnyCopper));

		ModHandler.addCraftingRecipe(ItemList.Emitter_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"SSC", "WQS", "CWS",
				'Q', new UnificationEntry(OrePrefix.gem, Materials.Emerald),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Chrome),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Advanced),
				'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Gold));

		ModHandler.addCraftingRecipe(ItemList.Emitter_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"SSC", "WQS", "CWS",
				'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderPearl),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Platinum),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Elite),
				'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Aluminium));

		ModHandler.addCraftingRecipe(ItemList.Emitter_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"SSC", "WQS", "CWS",
				'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderEye),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Osmium),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Master),
				'W', new UnificationEntry(OrePrefix.cableGt01, Materials.Tungsten));


		ItemList.Sensor_LV.set(addItem(690, "sensor.lv"));
		ItemList.Sensor_MV.set(addItem(691, "sensor.mv"));
		ItemList.Sensor_HV.set(addItem(692, "sensor.hv"));
		ItemList.Sensor_EV.set(addItem(693, "sensor.ev"));
		ItemList.Sensor_IV.set(addItem(694, "sensor.iv"));
		ItemList.Sensor_LuV.set(addItem(695, "sensor.luv"));
		ItemList.Sensor_ZPM.set(addItem(696, "sensor.zpm"));
		ItemList.Sensor_UV.set(addItem(697, "sensor.uv"));

		ModHandler.addCraftingRecipe(ItemList.Sensor_LV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P Q", "PS ", "CPP",
				'Q', new UnificationEntry(OrePrefix.gem, Materials.Quartzite),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Brass),
				'P', new UnificationEntry(OrePrefix.plate, Materials.Steel),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Basic));

		ModHandler.addCraftingRecipe(ItemList.Sensor_MV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P Q", "PS ", "CPP",
				'Q', new UnificationEntry(OrePrefix.gem, Materials.NetherQuartz),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Electrum),
				'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Good));

		ModHandler.addCraftingRecipe(ItemList.Sensor_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P Q", "PS ", "CPP",
				'Q', new UnificationEntry(OrePrefix.gem, Materials.Emerald),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Chrome),
				'P', new UnificationEntry(OrePrefix.plate, Materials.StainlessSteel),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Advanced));

		ModHandler.addCraftingRecipe(ItemList.Sensor_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P Q", "PS ", "CPP",
				'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderPearl),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Platinum),
				'P', new UnificationEntry(OrePrefix.plate, Materials.Titanium),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Elite));

		ModHandler.addCraftingRecipe(ItemList.Sensor_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"P Q", "PS ", "CPP",
				'Q', new UnificationEntry(OrePrefix.gem, Materials.EnderEye),
				'S', new UnificationEntry(OrePrefix.stick, Materials.Osmium),
				'P', new UnificationEntry(OrePrefix.plate, Materials.TungstenSteel),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Master));


		ItemList.Tool_DataStick.set(addItem(708, "tool.datastick").setUnificationData(OrePrefix.circuit, Materials.Data).addStats(new Behaviour_DataStick()));
		ItemList.Tool_DataOrb.set(addItem(707, "tool.dataorb").setUnificationData(OrePrefix.circuit, Materials.Ultimate).addStats(new Behaviour_DataOrb()));

		ItemList.Circuit_Primitive.set(addItem(700, "circuit.primitive").setUnificationData(OrePrefix.circuit, Materials.Primitive));
		ItemList.Circuit_Basic.set(addItem(701, "circuit.basic").setUnificationData(OrePrefix.circuit, Materials.Basic));
		ItemList.Circuit_Good.set(addItem(702, "circuit.good").setUnificationData(OrePrefix.circuit, Materials.Good));
		ItemList.Circuit_Advanced.set(addItem(703, "circuit.advanced").setUnificationData(OrePrefix.circuit, Materials.Advanced));
		ItemList.Circuit_Data.set(addItem(704, "circuit.data").setUnificationData(OrePrefix.circuit, Materials.Data));
		ItemList.Circuit_Elite.set(addItem(705, "circuit.elite").setUnificationData(OrePrefix.circuit, Materials.Elite));
		ItemList.Circuit_Master.set(addItem(706, "circuit.master").setUnificationData(OrePrefix.circuit, Materials.Master));
		ItemList.Circuit_Ultimate.set(ItemList.Tool_DataOrb.get(1));

		ModHandler.addShapelessCraftingRecipe(ItemList.Tool_DataOrb.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, ItemList.Tool_DataOrb.get(1));
		ModHandler.addShapelessCraftingRecipe(ItemList.Tool_DataStick.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, ItemList.Tool_DataStick.get(1));


		ItemList.Circuit_Board_Basic.set(addItem(710, "circuit.board.basic"));
		ItemList.Circuit_Board_Advanced.set(addItem(711, "circuit.board.advanced"));
		ItemList.Circuit_Board_Elite.set(addItem(712, "circuit.board.elite"));
		ItemList.Circuit_Parts_Crystal_Chip_Elite.set(addItem(713, "circuit.parts.crystal.chip.elite"));
		ItemList.Circuit_Parts_Crystal_Chip_Master.set(addItem(714, "circuit.parts.crystal.chip.master"));
		ItemList.Circuit_Parts_Advanced.set(addItem(715, "circuit.parts.advanced"));
		ItemList.Circuit_Parts_Wiring_Basic.set(addItem(716, "circuit.parts.wiring.basic"));
		ItemList.Circuit_Parts_Wiring_Advanced.set(addItem(717, "circuit.parts.wiring.advanced"));
		ItemList.Circuit_Parts_Wiring_Elite.set(addItem(718, "circuit.parts.wiring.elite"));
		ItemList.Empty_Board_Basic.set(addItem(719, "empty.board.basic"));
		ItemList.Empty_Board_Elite.set(addItem(720, "empty.board.elite"));


		ItemList.Component_Sawblade_Diamond.set(addItem(721, "component.sawblade.diamond").addOreDict(OreDictNames.craftingDiamondBlade));
		ItemList.Component_Grinder_Diamond.set(addItem(722, "component.grinder.diamond").addOreDict(OreDictNames.craftingGrinder));
		ItemList.Component_Grinder_Tungsten.set(addItem(723, "component.grinder.tungsten").addOreDict(OreDictNames.craftingGrinder));

		ItemList.QuantumEye.set(addItem(724, "quantumeye"));
		ItemList.QuantumStar.set(addItem(725, "quantumstar"));
		ItemList.Gravistar.set(addItem(726, "gravistar"));

		ModHandler.addCraftingRecipe(ItemList.Field_Generator_HV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"WCW", "CGC", "WCW",
				'G', ItemList.QuantumEye.get(1),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Advanced),
				'W', new UnificationEntry(OrePrefix.wireGt04, Materials.Osmium));

		ModHandler.addCraftingRecipe(ItemList.Field_Generator_EV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"WCW", "CGC", "WCW",
				'G', new UnificationEntry(OrePrefix.gem, Materials.NetherStar),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Elite),
				'W', new UnificationEntry(OrePrefix.wireGt08, Materials.Osmium));

		ModHandler.addCraftingRecipe(ItemList.Field_Generator_IV.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"WCW", "CGC", "WCW",
				'G', ItemList.QuantumStar.get(1),
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Master),
				'W', new UnificationEntry(OrePrefix.wireGt16, Materials.Osmium));


		ModHandler.addCraftingRecipe(ItemList.Component_Sawblade_Diamond.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				" D ", "DGD", " D ",
				'D', new UnificationEntry(OrePrefix.dustSmall, Materials.Diamond),
				'G', new UnificationEntry(OrePrefix.gearGt, Materials.CobaltBrass));

		ModHandler.addCraftingRecipe(ItemList.Component_Grinder_Diamond.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"DSD", "SIS", "DSD",
				'I', OreDictNames.craftingIndustrialDiamond,
				'D', new UnificationEntry(OrePrefix.dust, Materials.Diamond),
				'S', new UnificationEntry(OrePrefix.plate, Materials.Steel));

		ModHandler.addCraftingRecipe(ItemList.Component_Grinder_Tungsten.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"TST", "SIS", "TST",
				'I', OreDictNames.craftingIndustrialDiamond,
				'T', new UnificationEntry(OrePrefix.plate, Materials.Tungsten),
				'S', new UnificationEntry(OrePrefix.plate, Materials.Steel));

		ItemList.Upgrade_Muffler.set(addItem(727, "upgrade.muffler"));
		ItemList.Upgrade_Lock.set(addItem(728, "upgrade.lock"));

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

		ItemList.Component_Filter.set(addItem(729, "component.filter").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Zinc, OrePrefix.foil.materialAmount * 16L))).addOreDict(OreDictNames.craftingFilter));

		RecipeMap.ASSEMBLER_RECIPES.recipeBuilder()
				.inputs(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_mesh, 4), OreDictionaryUnifier.get(OrePrefix.foil, Materials.Zinc, 16))
				.fluidInputs(Materials.Plastic.getFluid(144))
				.outputs(ItemList.Component_Filter.get(1))
				.duration(1600)
				.EUt(32)
				.buildAndRegister();

		ItemList.Tool_Cheat.set(addItem(761, "tool.cheat").addStats(new Behaviour_Scanner(), new ElectricStats(-2000000000, -1)));
		ItemList.Tool_Scanner.set(addItem(762, "tool.scanner").addStats(new Behaviour_Scanner(), new ElectricStats(400000, 2, true, false)));
		ModHandler.addCraftingRecipe(ItemList.Tool_Scanner.get(1),
				ModHandler.RecipeBits.DISMANTLEABLE | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
				"EPR", "CSC", "PBP",
				'C', new UnificationEntry(OrePrefix.circuit, Materials.Advanced),
				'P', new UnificationEntry(OrePrefix.plate, Materials.Aluminium),
				'E', ItemList.Emitter_MV,
				'R', ItemList.Sensor_MV,
				'S', Items.DIAMOND,
				'B', ItemList.Battery_RE_MV_Lithium);

		ItemList.NC_SensorKit.set(addItem(763, "nc.sensorkit").addStats(new Behaviour_SensorKit()));
		ItemList.Duct_Tape.set(addItem(764, "duct.tape").addOreDict(OreDictNames.craftingDuctTape));
		ItemList.McGuffium_239.set(addItem(765, "mcguffium.239"));

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