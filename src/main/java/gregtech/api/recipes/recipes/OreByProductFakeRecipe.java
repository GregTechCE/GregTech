package gregtech.api.recipes.recipes;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class OreByProductFakeRecipe {

	public final DustMaterial material;
	public final List<ItemStack> oreIngredients;
	public final List<ItemStack> byProductIngredients;
	public final List<ItemStack> oreProcessingSteps;
	public final static EnumSet<OrePrefix> ores = EnumSet.of(
			OrePrefix.ore, 
			OrePrefix.oreBasalt, 
			OrePrefix.oreBlackgranite, 
			OrePrefix.oreEndstone, 
			OrePrefix.oreGravel, 
			OrePrefix.oreMarble,
			OrePrefix.oreNetherrack,
			OrePrefix.oreRedgranite,
			OrePrefix.oreSand);
	
	public OreByProductFakeRecipe(DustMaterial material) {
		this.material = material;
		this.oreIngredients = new ArrayList<ItemStack>();
		for(OrePrefix ore : ores)
		this.oreIngredients.add(OreDictUnifier.get(ore, material));
		
		this.byProductIngredients =  new ArrayList<ItemStack>();
		
		for(Material mat : material.oreByProducts)
			this.byProductIngredients.add(OreDictUnifier.get(OrePrefix.dust, mat));
		
		this.oreProcessingSteps = new ArrayList<ItemStack>();
		this.oreProcessingSteps.add(OreDictUnifier.get(OrePrefix.crushed, material));
		this.oreProcessingSteps.add(OreDictUnifier.get(OrePrefix.crushedPurified, material));
		this.oreProcessingSteps.add(OreDictUnifier.get(OrePrefix.crushedCentrifuged, material));
		this.oreProcessingSteps.add(OreDictUnifier.get(OrePrefix.dustImpure, material));
		this.oreProcessingSteps.add(OreDictUnifier.get(OrePrefix.dustPure, material));
		this.oreProcessingSteps.add(OreDictUnifier.get(OrePrefix.dust, material));
		
		//Sifter gem
		//Electrolyzer/Centrifuge decomposion
	}
	
	
}
