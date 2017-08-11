package gregtech.loaders.load;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.GT_Values;
import gregtech.api.items.ItemList;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.util.GTLog;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.unification.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class GT_FuelLoader
        implements Runnable {
    public void run() {
        GTLog.out.println("GT_Mod: Initializing various Fuels.");
        ItemList.sNitricAcid = GT_Mod.gregtechproxy.addFluid("nitricacid", "Nitric acid ", null, 1, 295);
        ItemList.sBlueVitriol = GT_Mod.gregtechproxy.addFluid("solution.bluevitriol", "Blue Vitriol water solution", null, 1, 295);
        ItemList.sNickelSulfate = GT_Mod.gregtechproxy.addFluid("solution.nickelsulfate", "Nickel sulfate water solution", null, 1, 295);
        ItemList.sRocketFuel = GT_Mod.gregtechproxy.addFluid("rocket_fuel", "Rocket Fuel", null, 1, 295);
        new GT_Recipe(new ItemStack(Items.LAVA_BUCKET), new ItemStack(Blocks.OBSIDIAN), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1L), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Electrum, 1L), 30, 2);

        GT_Recipe.GT_Recipe_Map.sSmallNaquadahReactorFuels.addRecipe(true, new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.NaquadahEnriched, 1L)}, new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Naquadah, 1L)}, null, null, null, 0, 0, 25000);
        GT_Recipe.GT_Recipe_Map.sLargeNaquadahReactorFuels.addRecipe(true, new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.NaquadahEnriched, 1L)}, new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Naquadah, 1L)}, null, null, null, 0, 0, 200000);
        GT_Recipe.GT_Recipe_Map.sFluidNaquadahReactorFuels.addRecipe(true, new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahEnriched, 1L)}, new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.cell,  Materials.Naquadah, 1L)}, null, null, null, 0, 0, 1400000);

        GT_Values.RA.addFuel(GT_ModHandler.getModItem("Thaumcraft", "ItemResource", 1, 4), null, 4, 5);
        GT_Values.RA.addFuel(new ItemStack(Items.EXPERIENCE_BOTTLE, 1), null, 10, 5);
        GT_Values.RA.addFuel(new ItemStack(Items.GHAST_TEAR, 1), null, 50, 5);
        GT_Values.RA.addFuel(new ItemStack(Blocks.BEACON, 1), null, Materials.NetherStar.mFuelPower * 2, Materials.NetherStar.mFuelType);
        GT_Values.RA.addFuel(GT_ModHandler.getModItem("EnderIO", "bucketRocket_fuel", 1), null, 250, 1);
        if(GregTech_API.mMagneticraft){
            GT_Values.RA.addFuel(GT_ModHandler.getModItem("Magneticraft", "item.bucket_light_oil", 1), null, 256, 0);
            GT_Values.RA.addFuel(GT_ModHandler.getModItem("Magneticraft", "item.bucket_heavy_oil", 1), null, 192, 3);
        }
        if(GregTech_API.mImmersiveEngineering){
            GT_Values.RA.addFuel(GT_ModHandler.getModItem("ImmersiveEngineering", "fluidContainers", 1, 7), null, 128, 0);
        }
        if(Loader.isModLoaded("PneumaticCraft")){
            GT_Values.RA.addFuel(GT_ModHandler.getModItem("PneumaticCraft", "pgBucket", 1), null, 512, 1);
            GT_Values.RA.addFuel(GT_ModHandler.getModItem("PneumaticCraft", "fuelBucket", 1), null, 400, 0);
            GT_Values.RA.addFuel(GT_ModHandler.getModItem("PneumaticCraft", "fuelBucket", 1, 1), null, 400, 0);
            GT_Values.RA.addFuel(GT_ModHandler.getModItem("PneumaticCraft", "keroseneBucket", 1), null, 256, 0);
            GT_Values.RA.addFuel(GT_ModHandler.getModItem("PneumaticCraft", "dieselBucket", 1), null, 200, 0);
        }
    }
}