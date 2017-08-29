package gregtech.loaders.postload;

import gregtech.api.items.ItemList;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import ic2.api.recipe.Recipes;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ScrapboxRecipeLoader implements Runnable {

    public void run() {
        addScrapboxDrop(9.5F, new ItemStack(Items.WOODEN_HOE));
        addScrapboxDrop(2.0F, new ItemStack(Items.SIGN));
        addScrapboxDrop(9.5F, new ItemStack(Items.STICK));
        addScrapboxDrop(5.0F, new ItemStack(Blocks.DIRT));
        addScrapboxDrop(3.0F, new ItemStack(Blocks.GRASS));
        addScrapboxDrop(3.0F, new ItemStack(Blocks.GRAVEL));
        addScrapboxDrop(0.5F, new ItemStack(Blocks.PUMPKIN));
        addScrapboxDrop(1.0F, new ItemStack(Blocks.SOUL_SAND));
        addScrapboxDrop(2.0F, new ItemStack(Blocks.NETHERRACK));
        addScrapboxDrop(1.0F, new ItemStack(Items.BONE));
        addScrapboxDrop(9.0F, new ItemStack(Items.ROTTEN_FLESH));
        addScrapboxDrop(0.4F, new ItemStack(Items.COOKED_PORKCHOP));
        addScrapboxDrop(0.4F, new ItemStack(Items.COOKED_BEEF));
        addScrapboxDrop(0.4F, new ItemStack(Items.COOKED_CHICKEN));
        addScrapboxDrop(0.5F, new ItemStack(Items.APPLE));
        addScrapboxDrop(0.5F, new ItemStack(Items.BREAD));
        addScrapboxDrop(0.1F, new ItemStack(Items.CAKE));
        addScrapboxDrop(1.0F, ItemList.IC2_Food_Can_Filled.get(1));
        addScrapboxDrop(2.0F, ItemList.IC2_Food_Can_Spoiled.get(1));
        addScrapboxDrop(0.2F, OreDictUnifier.get(OrePrefix.dust, Materials.Silicon));
        addScrapboxDrop(1.0F, OreDictUnifier.get(OrePrefix.cell, Materials.Water));
        addScrapboxDrop(2.0F, ItemList.Cell_Empty.get(1));
        addScrapboxDrop(5.0F, OreDictUnifier.get(OrePrefix.plate, Materials.Paper));
        addScrapboxDrop(1.0F, new ItemStack(Items.LEATHER));
        addScrapboxDrop(1.0F, new ItemStack(Items.FEATHER));
        addScrapboxDrop(0.7F, ItemName.crafting.getItemStack(CraftingItemType.plant_ball));
        addScrapboxDrop(3.8F, OreDictUnifier.get(OrePrefix.dust, Materials.Wood));
        addScrapboxDrop(0.6F, new ItemStack(Items.SLIME_BALL));
        addScrapboxDrop(0.8F, OreDictUnifier.get(OrePrefix.dust, Materials.Rubber));
        addScrapboxDrop(2.7F, ItemName.single_use_battery.getItemStack());
        addScrapboxDrop(3.6F, ItemList.Circuit_Primitive.get(1));
        addScrapboxDrop(0.8F, ItemList.Circuit_Parts_Advanced.get(1));
        addScrapboxDrop(1.8F, ItemList.Circuit_Board_Basic.get(1));
        addScrapboxDrop(0.4F, ItemList.Circuit_Board_Advanced.get(1));
        addScrapboxDrop(0.2F, ItemList.Circuit_Board_Elite.get(1));
        addScrapboxDrop(0.9F, OreDictUnifier.get(OrePrefix.dust, Materials.Redstone));
        addScrapboxDrop(0.8F, OreDictUnifier.get(OrePrefix.dust, Materials.Glowstone));
        addScrapboxDrop(0.8F, OreDictUnifier.get(OrePrefix.dust, Materials.Coal));
        addScrapboxDrop(2.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Charcoal));
        addScrapboxDrop(1.0F, OreDictUnifier.get(OrePrefix.dust, Materials.Iron));
        addScrapboxDrop(1.0F, OreDictUnifier.get(OrePrefix.dust, Materials.Gold));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Silver));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Electrum));
        addScrapboxDrop(1.2F, OreDictUnifier.get(OrePrefix.dust, Materials.Tin));
        addScrapboxDrop(1.2F, OreDictUnifier.get(OrePrefix.dust, Materials.Copper));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Bauxite));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Aluminium));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Lead));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Nickel));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Zinc));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Brass));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Steel));
        addScrapboxDrop(1.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Obsidian));
        addScrapboxDrop(1.5F, OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur));
        addScrapboxDrop(2.0F, OreDictUnifier.get(OrePrefix.dust, Materials.Saltpeter));
        addScrapboxDrop(2.0F, OreDictUnifier.get(OrePrefix.dust, Materials.Lazurite));
        addScrapboxDrop(2.0F, OreDictUnifier.get(OrePrefix.dust, Materials.Pyrite));
        addScrapboxDrop(2.0F, OreDictUnifier.get(OrePrefix.dust, Materials.Calcite));
        addScrapboxDrop(2.0F, OreDictUnifier.get(OrePrefix.dust, Materials.Sodalite));
        addScrapboxDrop(4.0F, OreDictUnifier.get(OrePrefix.dust, Materials.Netherrack));
        addScrapboxDrop(4.0F, OreDictUnifier.get(OrePrefix.dust, Materials.Flint));
        addScrapboxDrop(0.03F, OreDictUnifier.get(OrePrefix.dust, Materials.Platinum));
        addScrapboxDrop(0.03F, OreDictUnifier.get(OrePrefix.dust, Materials.Tungsten));
        addScrapboxDrop(0.03F, OreDictUnifier.get(OrePrefix.dust, Materials.Chrome));
        addScrapboxDrop(0.03F, OreDictUnifier.get(OrePrefix.dust, Materials.Titanium));
        addScrapboxDrop(0.03F, OreDictUnifier.get(OrePrefix.dust, Materials.Magnesium));
        addScrapboxDrop(0.03F, OreDictUnifier.get(OrePrefix.dust, Materials.Endstone));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.GarnetRed));
        addScrapboxDrop(0.5F, OreDictUnifier.get(OrePrefix.dust, Materials.GarnetYellow));
        addScrapboxDrop(0.05F, OreDictUnifier.get(OrePrefix.gem, Materials.Olivine));
        addScrapboxDrop(0.05F, OreDictUnifier.get(OrePrefix.gem, Materials.Ruby));
        addScrapboxDrop(0.05F, OreDictUnifier.get(OrePrefix.gem, Materials.Sapphire));
        addScrapboxDrop(0.05F, OreDictUnifier.get(OrePrefix.gem, Materials.GreenSapphire));
        addScrapboxDrop(0.05F, OreDictUnifier.get(OrePrefix.gem, Materials.Emerald));
        addScrapboxDrop(0.05F, OreDictUnifier.get(OrePrefix.gem, Materials.Diamond));
    }
    
    private static void addScrapboxDrop(float chance, ItemStack itemStack) {
        Recipes.scrapboxDrops.addDrop(itemStack, chance);
    }
    
}
