package gregtech.common.items;

import com.google.common.collect.ImmutableMap;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import forestry.api.recipes.RecipeManagers;
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

import static gregtech.api.enums.GT_Values.MOD_ID;

public class ItemComb extends Item {
	@SideOnly(Side.CLIENT)
	private IIcon secondIcon;

	public ItemComb() {
		super();
		this.setCreativeTab(Tabs.tabApiculture);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("gt.comb");
		GameRegistry.registerItem(this, "gt.comb", MOD_ID);
	}

	public ItemStack getStackForType(CombType type) {
		return new ItemStack(this, 1, type.ordinal());
	}

	public ItemStack getStackForType(CombType type, int count) {
		return new ItemStack(this, count, type.ordinal());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (CombType type : CombType.values()) {
			if (type.showInList) {
				list.add(this.getStackForType(type));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int meta) {
		return 2;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("forestry:beeCombs.0");
		this.secondIcon = par1IconRegister.registerIcon("forestry:beeCombs.1");
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return (pass == 0) ? itemIcon : secondIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		int meta = Math.max(0, Math.min(CombType.values().length - 1, stack.getItemDamage()));
		int colour = CombType.values()[meta].getColours()[0];

		if (pass >= 1) {
			colour = CombType.values()[meta].getColours()[1];
		}

		return colour;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return CombType.values()[stack.getItemDamage()].getName();
	}

	public void initCombsRecipes() {
		for (CombType type : CombType.values()) {
			ItemStack tComb = getStackForType(type);
			if (type.material == Materials._NULL || type == type.COAL || type == type.LIGNIE) {
				if (type == type.STICKY) {
					GT_Values.RA.addCentrifugeRecipe(tComb, GT_Values.NI, GT_Values.NF, GT_Values.NF, ItemList.IC2_Resin.get(1, new Object[0]), ItemList.FR_Wax.get(1, new Object[0]),
							ItemList.IC2_Plantball.get(1, new Object[0]), GT_Values.NI, GT_Values.NI, GT_Values.NI, new int[] { 5000, 10000, 1500 }, 128, 5);
					RecipeManagers.centrifugeManager.addRecipe(40, tComb,
							ImmutableMap.of(ItemList.IC2_Resin.get(1, new Object[0]), 0.5f, ItemList.FR_Wax.get(1, new Object[0]), 1.0f, ItemList.IC2_Plantball.get(1, new Object[0]), 0.15f));
				} else if (type == type.OIL) {
					GT_Values.RA.addCentrifugeRecipe(tComb, GT_Values.NI, GT_Values.NF, GT_Values.NF, ItemList.Crop_Drop_OilBerry.get(1, new Object[0]), ItemList.FR_Wax.get(1, new Object[0]),
							GT_Values.NI, GT_Values.NI, GT_Values.NI, GT_Values.NI, new int[] { 10000, 10000 }, 128, 5);
					RecipeManagers.centrifugeManager.addRecipe(40, tComb, ImmutableMap.of(ItemList.Crop_Drop_OilBerry.get(1, new Object[0]), 1.0f, ItemList.FR_Wax.get(1, new Object[0]), 1.0f));
				} else if (type == type.STONE || type == type.SLAG) {
					GT_Values.RA.addCentrifugeRecipe(tComb, GT_Values.NI, GT_Values.NF, GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1),
							ItemList.FR_Wax.get(1, new Object[0]), GT_Values.NI, GT_Values.NI, GT_Values.NI, GT_Values.NI, new int[] { 7000, 3000 }, 128, 5);
					RecipeManagers.centrifugeManager.addRecipe(40, tComb,
							ImmutableMap.of(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Stone, 1), 0.7f, ItemList.FR_Wax.get(1, new Object[0]), 0.3f));
				} else {
					GT_Values.RA.addCentrifugeRecipe(tComb, GT_Values.NI, GT_Values.NF, GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.dustTiny, type.material, 1),
							ItemList.FR_Wax.get(1, new Object[0]), GT_Values.NI, GT_Values.NI, GT_Values.NI, GT_Values.NI, new int[] { type.chance * 100, 3000 }, 128, 5);
					RecipeManagers.centrifugeManager.addRecipe(40, tComb,
							ImmutableMap.of(GT_OreDictUnificator.get(OrePrefixes.dustTiny, type.material, 1), type.chance * 0.01f, ItemList.FR_Wax.get(1, new Object[0]), 0.3f));
				}
			} else {
				if (GT_Mod.gregtechproxy.mNervedCombs) {
					GT_Values.RA.addChemicalRecipe(GT_Utility.copyAmount(9, tComb), GT_OreDictUnificator.get(OrePrefixes.crushed, type.material, 1), Materials.Water.getFluid(1000),
							type.material.mOreByProducts.isEmpty() ? null : type.material.mOreByProducts.get(0).getMolten(144),
							GT_OreDictUnificator.get(OrePrefixes.crushedPurified, type.material, 4), 96, 300);

					GT_Values.RA.addAutoclaveRecipe(GT_Utility.copyAmount(16, tComb), Materials.UUMatter.getFluid(1), GT_OreDictUnificator.get(OrePrefixes.crushedPurified, type.material, 1),(type==CombType.URANIUM||type==CombType.PLUTONIUM||type==CombType.NAQUADAH)? 300: 1000, 1000, 24);
				} else {
					if (type == type.IRIDIUM) {
						GT_Values.RA.addCentrifugeRecipe(tComb, GT_Values.NI, GT_Values.NF, GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Iridium, 1),
								GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Osmium, 1), ItemList.FR_Wax.get(1, new Object[0]), GT_Values.NI, GT_Values.NI, GT_Values.NI, new int[] { 2000,
										800, 3000 }, 128, 5);
						RecipeManagers.centrifugeManager.addRecipe(40, tComb, ImmutableMap.of(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Iridium, 1), 0.2f,
								GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Osmium, 1), 0.08f, ItemList.FR_Wax.get(1, new Object[0]), 0.3f));
					} else {
						GT_Values.RA.addCentrifugeRecipe(tComb, GT_Values.NI, GT_Values.NF, GT_Values.NF, GT_OreDictUnificator.get(OrePrefixes.dustTiny, type.material, 1),
								ItemList.FR_Wax.get(1, new Object[0]), GT_Values.NI, GT_Values.NI, GT_Values.NI, GT_Values.NI, new int[] { type.chance * 100, 3000 }, 128, 5);
						RecipeManagers.centrifugeManager.addRecipe(40, tComb,
								ImmutableMap.of(GT_OreDictUnificator.get(OrePrefixes.dustTiny, type.material, 1), type.chance * 0.01f, ItemList.FR_Wax.get(1, new Object[0]), 0.3f));
					}
				}
			}
		}
	}
}