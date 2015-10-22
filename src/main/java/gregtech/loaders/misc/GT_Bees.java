package gregtech.loaders.misc;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import forestry.api.apiculture.BeeManager;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.common.GT_Proxy;
import gregtech.common.items.CombType;
import gregtech.common.items.ItemComb;

public class GT_Bees {

	public static ItemComb combs;
	
	public GT_Bees(){
		if(Loader.isModLoaded("Forestry")&&GT_Mod.gregtechproxy.mGTBees){
		combs = new ItemComb();
	    combs.initCombsRecipes();		
	    GT_BeeDefinition.initBees();}
	}
}
