package gregtech.common.items;

import com.google.common.base.CaseFormat;
import gregtech.api.GregTech_API;
import gregtech.api.items.ItemList;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.FoodStats;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.RandomPotionEffect;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class MataItem2 extends MaterialMetaItem {

    public MataItem2() {
        super("metaitem.02",
                OrePrefix.toolHeadSword, OrePrefix.toolHeadPickaxe, OrePrefix.toolHeadShovel, OrePrefix.toolHeadAxe,
                OrePrefix.toolHeadHoe, OrePrefix.toolHeadHammer, OrePrefix.toolHeadFile, OrePrefix.toolHeadSaw,
                OrePrefix.toolHeadDrill, OrePrefix.toolHeadChainsaw, OrePrefix.toolHeadWrench, OrePrefix.toolHeadUniversalSpade,
                OrePrefix.toolHeadSense, OrePrefix.toolHeadPlow,  OrePrefix.toolHeadBuzzSaw, OrePrefix.turbineBlade, 
                OrePrefix.wireFine, OrePrefix.gearGtSmall, OrePrefix.rotor, OrePrefix.stickLong, OrePrefix.springSmall, OrePrefix.spring, 
                OrePrefix.gemChipped, OrePrefix.gemFlawed, OrePrefix.gemFlawless, OrePrefix.gemExquisite, OrePrefix.gearGt, 
                null, null, null, null, null);

        ItemList.ThermosCan_Dark_Coffee.set(addItem(0).addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SPEED, 400, 1, 70), new RandomPotionEffect(MobEffects.HASTE, 400, 1, 70)))); // "Dark Coffee", "Coffee, dark, without anything else"
        ItemList.ThermosCan_Dark_Cafe_au_lait.set(addItem(1).setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SPEED, 400, 2, 90), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 90)))); // "Dark Coffee au lait", "Keeping you awake the whole night",
        ItemList.ThermosCan_Coffee.set(addItem(2).addStats(new FoodStats(3, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SPEED, 400, 0, 50), new RandomPotionEffect(MobEffects.HASTE, 400, 0, 50)))); //"Coffee", "Just the regular morning Coffee",
        ItemList.ThermosCan_Cafe_au_lait.set(addItem(3).addStats(new FoodStats(3, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SPEED, 400, 1, 70), new RandomPotionEffect(MobEffects.HASTE, 400, 1, 70)))); // "Cafe au lait", "Sweet Coffee"
        ItemList.ThermosCan_Lait_au_cafe.set(addItem(4).setInvisible().addStats(new FoodStats(3, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SPEED, 400, 2, 90), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 90)))); //, "Lait au cafe", "You want Coffee to your Sugar?"
        ItemList.ThermosCan_Dark_Chocolate_Milk.set(addItem(5).addStats(new FoodStats(3, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.REGENERATION, 50, 1, 60)))); //, "Dark Chocolate Milk", "A bit bitter, better add a bit Sugar"
        ItemList.ThermosCan_Chocolate_Milk.set(addItem(6).addStats(new FoodStats(3, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.REGENERATION, 50, 1, 90)))); //"Chocolate Milk", "Sweet Goodness"
        ItemList.ThermosCan_Tea.set(addItem(7).addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SLOWNESS, 300, 0, 50)))); //"Tea", "Keep calm and carry on"
        ItemList.ThermosCan_Sweet_Tea.set(addItem(8).setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1)))); //"Sweet Tea", "How about a Tea Party? In Boston?"
        ItemList.ThermosCan_Ice_Tea.set(addItem(9).addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SLOWNESS, 300, 0, 50)))); //"Ice Tea", "Better than this purple Junk Drink from failed Potions"

        ItemList.GelledToluene.set(addItem(10)); //, "Gelled Toluene", "Raw Explosive"

        ItemList.Bottle_Purple_Drink.set(addItem(100).addStats(new FoodStats(8, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.SLOWNESS, 400, 1, 90)))); //, "Purple Drink", "How about Lemonade. Or some Ice Tea? I got Purple Drink!"
        ItemList.Bottle_Grape_Juice.set(addItem(101).addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HUNGER, 400, 1, 60)))); // "This has a cleaning effect on your internals.", "Grape Juice"
        ItemList.Bottle_Wine.set(addItem(102).addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 1, 60), new RandomPotionEffect(MobEffects.INSTANT_HEALTH, 0, 0, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)))); // "Wine", "Ordinary"
        ItemList.Bottle_Vinegar.set(addItem(103).setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 1, 90), new RandomPotionEffect(MobEffects.INSTANT_HEALTH, 0, 1, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5)))); //"Vinegar", "Exquisite"
        ItemList.Bottle_Potato_Juice.set(addItem(104).setInvisible().addStats(new FoodStats(3, 0.3F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1)))); // "Potato Juice", "Ever seen Potato Juice in stores? No? That has a reason."
        ItemList.Bottle_Vodka.set(addItem(105).setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 500, 0, 60), new RandomPotionEffect(MobEffects.STRENGTH, 500, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)))); // "Vodka", "Not to confuse with Water"
        ItemList.Bottle_Leninade.set(addItem(106).setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 500, 1, 90), new RandomPotionEffect(MobEffects.STRENGTH, 500, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5)))); // "Leninade", "Let the Communism flow through you!"
        ItemList.Bottle_Mineral_Water.set(addItem(107).addStats(new FoodStats(1, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.REGENERATION, 100, 1, 10)))); // "Mineral Water", "The best Stuff you can drink to stay healthy
        ItemList.Bottle_Salty_Water.set(addItem(108).setInvisible().addStats(new FoodStats(1, 0.0F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HUNGER, 400, 2, 95)))); // "Salty Water", "Like Sea Water but less dirty"
        ItemList.Bottle_Reed_Water.set(addItem(109).addStats(new FoodStats(1, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1)))); // "Reed Water", "I guess this tastes better when fermented"
        ItemList.Bottle_Rum.set(addItem(110).setInvisible().addStats(new FoodStats(4, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 300, 0, 60), new RandomPotionEffect(MobEffects.STRENGTH, 300, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)))); //  "Rum", "A buddle o' rum"
        ItemList.Bottle_Pirate_Brew.set(addItem(111).setInvisible().addStats(new FoodStats(4, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 90), new RandomPotionEffect(MobEffects.STRENGTH, 300, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5)))); // "Pirate Brew", "Set the Sails, we are going to Torrentuga!"
        ItemList.Bottle_Hops_Juice.set(addItem(112).addStats(new FoodStats(1, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1)))); // "Hops Juice", "Every Beer has a start"
        ItemList.Bottle_Dark_Beer.set(addItem(113).addStats(new FoodStats(4, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 60), new RandomPotionEffect(MobEffects.STRENGTH, 300, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)))); //"Dark Beer", "Dark Beer, for the real Men"
        ItemList.Bottle_Dragon_Blood.set(addItem(114).setInvisible().addStats(new FoodStats(4, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 300, 2, 90), new RandomPotionEffect(MobEffects.STRENGTH, 300, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5)))); // "Dragon Blood", "FUS RO DAH!"
        ItemList.Bottle_Wheaty_Juice.set(addItem(115).addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1)))); // "Wheaty Juice", "Is this liquefied Bread or what?"
        ItemList.Bottle_Scotch.set(addItem(116).setInvisible().addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 0, 60), new RandomPotionEffect(MobEffects.RESISTANCE, 400, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)))); // "Scotch", "Technically this is just a Whisky"
        ItemList.Bottle_Glen_McKenner.set(addItem(117).setInvisible().addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 1, 90), new RandomPotionEffect(MobEffects.RESISTANCE, 400, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5)))); // "Glen McKenner", "Don't hand to easily surprised people, they will shatter it."
        ItemList.Bottle_Wheaty_Hops_Juice.set(addItem(118).addStats(new FoodStats(1, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1)))); // "Wheaty Hops Juice", "Also known as 'Duff-Lite'"
        ItemList.Bottle_Beer.set(addItem(119).addStats(new FoodStats(6, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 0, 60), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 60), new RandomPotionEffect(MobEffects.POISON, 100, 0, 5)))); // "Beer", "Good old Beer"
        ItemList.Bottle_Chilly_Sauce.set(addItem(120).addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 1000, 0, 10), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 1000, 0, 60)))); // "Chilly Sauce", "Spicy"
        ItemList.Bottle_Hot_Sauce.set(addItem(121).addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 2000, 0, 30), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 2000, 0, 70)))); // "Hot Sauce", "Very Spicy, I guess?"
        ItemList.Bottle_Diabolo_Sauce.set(addItem(122).setInvisible().addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 3000, 1, 50), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 3000, 0, 80)))); // "Diabolo Sauce", "As if the Devil made this Sauce"
        ItemList.Bottle_Diablo_Sauce.set(addItem(123).setInvisible().addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 4000, 1, 70), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 4000, 0, 90)))); // "Diablo Sauce", "Diablo always comes back!"
        ItemList.Bottle_Snitches_Glitch_Sauce.set(addItem(124).setInvisible().addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 9999, 2, 999), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 9999, 9, 999)))); // "Old Man Snitches glitched Diablo Sauce", "[Missing No]"
        ItemList.Bottle_Apple_Juice.set(addItem(125).addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HUNGER, 400, 0, 20)))); // "Apple Juice", "Made of the Apples from our best Oak Farms"
        ItemList.Bottle_Cider.set(addItem(126).addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 0, 60), new RandomPotionEffect(MobEffects.RESISTANCE, 400, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)))); // "Cider", "If you have nothing better to do with your Apples"
        ItemList.Bottle_Golden_Apple_Juice.set(addItem(127).setInvisible().addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HUNGER, 400, 0, 20), new RandomPotionEffect(MobEffects.ABSORPTION, 2400, 0, 100), new RandomPotionEffect(MobEffects.REGENERATION, 100, 1, 100)))); // "Golden Apple Juice", "A golden Apple in liquid form"
        ItemList.Bottle_Golden_Cider.set(addItem(128).setInvisible().addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HUNGER, 400, 0, 60), new RandomPotionEffect(MobEffects.ABSORPTION, 2400, 1, 95), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)))); // "Golden Cider", "More Resistance, less Regeneration"
        ItemList.Bottle_Iduns_Apple_Juice.set(addItem(129).setInvisible().addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.REGENERATION, 600, 4, 100), new RandomPotionEffect(MobEffects.ABSORPTION, 2400, 0, 100), new RandomPotionEffect(MobEffects.RESISTANCE, 6000, 0, 100), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0, 100)))); // "Idun's Apple Juice", "So you got the Idea of using Notch Apples for a drink?"
        ItemList.Bottle_Notches_Brew.set(addItem(130).setInvisible().addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.REGENERATION, 700, 4, 95), new RandomPotionEffect(MobEffects.ABSORPTION, 3000, 1, 95), new RandomPotionEffect(MobEffects.RESISTANCE, 7000, 1, 95), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 7000, 0, 95), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 20)))); // "Notches Brew", "This is just overpowered"
        ItemList.Bottle_Lemon_Juice.set(addItem(131).addStats(new FoodStats(2, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HASTE, 1200, 0, 60)))); // "Lemon Juice", "Maybe adding Sugar will make it less sour"
        ItemList.Bottle_Limoncello.set(addItem(132).setInvisible().addStats(new FoodStats(2, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HASTE, 1200, 0, 90), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)))); // "Limoncello", "An alcoholic Drink which tastes like Lemons"
        ItemList.Bottle_Lemonade.set(addItem(133).addStats(new FoodStats(4, 0.3F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HASTE, 900, 1, 90)))); // "Lemonade", "Cold and refreshing Lemonade"
        ItemList.Bottle_Alcopops.set(addItem(134).setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HASTE, 900, 1, 90), new RandomPotionEffect(MobEffects.POISON, 300, 2, 20)))); // "Alcopops", "Don't let your Children drink this junk!"
        ItemList.Bottle_Cave_Johnsons_Grenade_Juice.set(addItem(135).setInvisible().addStats(new FoodStats(0, 0.0F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1)))); // "Cave Johnson's Grenade Juice", "When life gives you Lemons, make Life take them Lemons back!"
        ItemList.Bottle_Milk.set(addItem(136).setUnificationData(OrePrefix.bottle, Materials.Milk).addStats(new FoodStats(0, 0.0F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1)))); // "Milk", "Got Milk?"
        ItemList.Bottle_Holy_Water.set(addItem(137).setUnificationData(OrePrefix.bottle, Materials.HolyWater).addStats(new FoodStats(0, 0.0F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.POISON, 100, 1, 100)))); // "Holy Water", "May the holy Planks be with you"

        ItemList.Food_Potato_On_Stick.set(addItem(200).addStats(new FoodStats(1, 0.3F, false, false, new ItemStack(Items.STICK, 1)))); // "Potato on a Stick", "Totally looks like a Crab Claw"
        ItemList.Food_Potato_On_Stick_Roasted.set(addItem(201).addStats(new FoodStats(6, 0.6F, false, false, new ItemStack(Items.STICK, 1)))); // "Roasted Potato on a Stick", "Still looks like a Crab Claw"
        ItemList.Food_Raw_Fries.set(addItem(202).setMaxStackSize(16).addStats(new FoodStats(1, 0.3F))); // "Potato Strips", "It's Potato in Stripe Form"
        ItemList.Food_Fries.set(addItem(203).setMaxStackSize(16).addStats(new FoodStats(7, 0.5F))); // "Fries", "Not to confuse with Fry the Delivery Boy"
        ItemList.Food_Packaged_Fries.set(addItem(204).addStats(new FoodStats(7, 0.5F, false, false, OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 1)))); // "Fries", "Ketchup not included"
        ItemList.Food_Raw_PotatoChips.set(addItem(205).setMaxStackSize(16).addStats(new FoodStats(1, 0.3F))); // "Potato Chips (Raw)", "Just like a Potato"
        ItemList.Food_PotatoChips.set(addItem(206).setMaxStackSize(16).addStats(new FoodStats(7, 0.5F))); // "Potato Chips", "Crunchy"
        ItemList.Food_ChiliChips.set(addItem(207).setMaxStackSize(16).addStats(new FoodStats(7, 0.6F))); //  "Chili Chips", "Spicy"
        ItemList.Food_Packaged_PotatoChips.set(addItem(208).addStats(new FoodStats(7, 0.5F, false, false, OreDictionaryUnifier.get(OrePrefix.foil, Materials.Aluminium, 1)))); // "Bag of Potato Chips", "Full of delicious Air"
        ItemList.Food_Packaged_ChiliChips.set(addItem(209).addStats(new FoodStats(7, 0.6F, false, false, OreDictionaryUnifier.get(OrePrefix.foil, Materials.Aluminium, 1)))); // "Bag of Chili Chips", "Stop making noises Baj!"
        ItemList.Food_Chum.set(addItem(210).addStats(new FoodStats(5, 0.2F, false, true, null, new RandomPotionEffect(MobEffects.HUNGER, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80)))); // "Chum", "Chum is Fum!"
        ItemList.Food_Chum_On_Stick.set(addItem(211).addStats(new FoodStats(5, 0.2F, false, true, new ItemStack(Items.STICK, 1), new RandomPotionEffect(MobEffects.HUNGER, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80)))); // "Chum on a Stick", "Don't forget to try our Chum-balaya"
        ItemList.Food_Dough_Sugar.set(addItem(212).addStats(new FoodStats(1, 0.1F))); // "Sugary Dough", "Don't eat the Dough before it is baken"
        ItemList.Food_Dough_Chocolate.set(addItem(213).addStats(new FoodStats(1, 0.1F))); // "Chocolate Dough", "I said don't eat the Dough!"
        ItemList.Food_Raw_Cookie.set(addItem(214).addStats(new FoodStats(1, 0.1F))); // "Cookie shaped Dough", "For baking Cookies"

        ItemList.Food_Sliced_Buns.set(addItem(220).addStats(new FoodStats(3, 0.5F))); //"Buns", "Pre Sliced",
        ItemList.Food_Burger_Veggie.set(addItem(221).addStats(new FoodStats(3, 0.5F))); //"Veggieburger", "No matter how you call this, this is NOT a Burger!",
        ItemList.Food_Burger_Cheese.set(addItem(222).addStats(new FoodStats(3, 0.5F)).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Cheese, 907200)))); //  "Cheeseburger", "Cheesy!",
        ItemList.Food_Burger_Meat.set(addItem(223).addStats(new FoodStats(3, 0.5F))); // "Hamburger", "The Mc Burger Queen Burger",
        ItemList.Food_Burger_Chum.set(addItem(224).addStats(new FoodStats(5, 0.2F, false, true, null, new RandomPotionEffect(MobEffects.SLOWNESS, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80)))); // "Chumburger", "Fum is Chum!",

        ItemList.Food_Sliced_Breads.set(addItem(230).addStats(new FoodStats(5, 0.6F))); // "Breads", "Pre Sliced",
        ItemList.Food_Sandwich_Veggie.set(addItem(231).setMaxStackSize(32).addStats(new FoodStats(7, 0.6F))); // "Veggie Sandwich", "Meatless",
        ItemList.Food_Sandwich_Cheese.set(addItem(232).setMaxStackSize(32).addStats(new FoodStats(7, 0.6F))); //"Cheese Sandwich", "Say Cheese!",
        ItemList.Food_Sandwich_Bacon.set(addItem(233).setMaxStackSize(32).addStats(new FoodStats(10, 0.8F))); //"Bacon Sandwich", "The best Sandwich ever!",
        ItemList.Food_Sandwich_Steak.set(addItem(234).setMaxStackSize(32).addStats(new FoodStats(10, 0.8F))); //"Steak Sandwich", "Not a 'Steam Sandwich'",

        ItemList.Food_Sliced_Baguettes.set(addItem(240).addStats(new FoodStats(8, 0.5F))); //"Baguettes", "Pre Sliced",
        ItemList.Food_Large_Sandwich_Veggie.set(addItem(241).setMaxStackSize(16).addStats(new FoodStats(15, 0.8F))); //"Large Veggie Sandwich", "Just not worth it",
        ItemList.Food_Large_Sandwich_Cheese.set(addItem(242).setMaxStackSize(16).addStats(new FoodStats(15, 0.8F))); //"Large Cheese Sandwich", "I need another cheesy tooltip for this",
        ItemList.Food_Large_Sandwich_Bacon.set(addItem(243).setMaxStackSize(16).addStats(new FoodStats(20, 1.0F))); //"Large Bacon Sandwich", "For Men! (and manly Women)",
        ItemList.Food_Large_Sandwich_Steak.set(addItem(244).setMaxStackSize(16).addStats(new FoodStats(20, 1.0F))); //"Large Steak Sandwich", "Yes, I once accidentially called it 'Steam Sandwich'"

        ItemList.Food_Raw_Pizza_Veggie.set(addItem(250).addStats(new FoodStats(1, 0.2F))); // , "Raw Veggie Pizza", "Into the Oven with it!"
        ItemList.Food_Raw_Pizza_Cheese.set(addItem(251).addStats(new FoodStats(2, 0.2F))); // , "Raw Cheese Pizza", "Into the Oven with it!"
        ItemList.Food_Raw_Pizza_Meat.set(addItem(252).addStats(new FoodStats(2, 0.2F))); // "Raw Mince Meat Pizza", "Into the Oven with it!"

        ItemList.Food_Baked_Pizza_Veggie.set(addItem(260).addStats(new FoodStats(3, 0.3F))); // , "Veggie Pizza", "The next they want is Gluten Free Pizzas..."
        ItemList.Food_Baked_Pizza_Cheese.set(addItem(261).addStats(new FoodStats(4, 0.4F))); //  "Cheese Pizza", "Pizza Magarita"
        ItemList.Food_Baked_Pizza_Meat.set(addItem(262).addStats(new FoodStats(5, 0.5F))); // "Mince Meat Pizza", "Emo Pizza, it cuts itself!"

        ItemList.Dye_Indigo.set(addItem(410).addOreDict("dyeBlue")); //"Indigo Dye", "Blue Dye"
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            ItemList.DYE_ONLY_ITEMS[i].set(addItem(414 + i).addOreDict("dye" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, EnumDyeColor.byMetadata(i).getName()))); //Dyes.get(i).mName + " Dye", ""
        }
        ItemList.Plank_Oak.set(addItem(470).setBurnValue(75)); //"Oak Plank", "Usable as Cover"
        ItemList.Plank_Spruce.set(addItem(471).setBurnValue(75)); // "Spruce Plank", "Usable as Cover"
        ItemList.Plank_Birch.set(addItem(472).setBurnValue(75)); //"Birch Plank", "Usable as Cover"
        ItemList.Plank_Jungle.set(addItem(473).setBurnValue(75)); // "Jungle Plank", "Usable as Cover"
        ItemList.Plank_Acacia.set(addItem(474).setBurnValue(75)); //"Acacia Plank", "Usable as Cover"
        ItemList.Plank_DarkOak.set(addItem(475).setBurnValue(75)); //"Dark Oak Plank", "Usable as Cover"

        ItemList.SFMixture.set(addItem(270)); //"Super Fuel Binder", "Raw Material"
        ItemList.MSFMixture.set(addItem(271)); //"Magic Super Fuel Binder", "Raw Material"

        ModHandler.addCraftingRecipe(ItemList.Plank_Oak.get(2),
                ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
                "s ", " P",
                'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 0));
        
        ModHandler.addCraftingRecipe(ItemList.Plank_Spruce.get(2),
                ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
                "s ", " P",
                'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 1));
        
        ModHandler.addCraftingRecipe(ItemList.Plank_Birch.get(2),
                ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
                "s ", " P",
                'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 2));
        
        ModHandler.addCraftingRecipe(ItemList.Plank_Jungle.get(2),
                ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
                "s ", " P",
                'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 3));
        
        ModHandler.addCraftingRecipe(ItemList.Plank_Acacia.get(2),
                ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
                "s ", " P",
                'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 4));
        
        ModHandler.addCraftingRecipe(ItemList.Plank_DarkOak.get(2),
                ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE,
                "s ", " P",
                'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 5));
        

        ItemList.Crop_Drop_Plumbilia.set(addItem(500)); // "Plumbilia Leaf", "Source of Lead"
        ItemList.Crop_Drop_Argentia.set(addItem(501)); // "Argentia Leaf", "Source of Silver"
        ItemList.Crop_Drop_Indigo.set(addItem(502)); //"Indigo Blossom", "Used for making Blue Dye"
        ItemList.Crop_Drop_Ferru.set(addItem(503)); // "Ferru Leaf", "Source of Iron"
        ItemList.Crop_Drop_Aurelia.set(addItem(504)); //"Aurelia Leaf", "Source of Gold"
        ItemList.Crop_Drop_TeaLeaf.set(addItem(505).addOreDict("cropTea")); //"Tea Leaf", "Source of Tea"

        ItemList.Crop_Drop_OilBerry.set(addItem(510)); //"Oil Berry", "Oil in Berry form"
        ItemList.Crop_Drop_BobsYerUncleRanks.set(addItem(511)); //"Bobs-Yer-Uncle-Berry", "Source of Emeralds"
        ItemList.Crop_Drop_UUMBerry.set(addItem(512)); //"UUM Berry", "UUM in Berry form"
        ItemList.Crop_Drop_UUABerry.set(addItem(513)); //"UUA Berry", "UUA in Berry form"

        ItemList.Crop_Drop_MilkWart.set(addItem(520)); // "Milk Wart", "Source of Milk"

        ItemList.Crop_Drop_Coppon.set(addItem(530)); //"Coppon Fiber", "ORANGE WOOOOOOOL!!!"

        ItemList.Crop_Drop_Tine.set(addItem(540).setBurnValue(100)); //"Tine Twig", "Source of Tin"

        ItemList.Crop_Drop_Bauxite.set(addItem(521)); //"Bauxia Leaf", "Source of Aluminium"
        ItemList.Crop_Drop_Ilmenite.set(addItem(522)); //"Titania Leaf", "Source of Titanium"
        ItemList.Crop_Drop_Pitchblende.set(addItem(523)); //"Reactoria Leaf", "Source of Uranium"
        ItemList.Crop_Drop_Uraninite.set(addItem(524)); //"Uranium Leaf", "Source of Uranite"
        ItemList.Crop_Drop_Thorium.set(addItem(526)); //"Thunder Leaf", "Source of Thorium"
        ItemList.Crop_Drop_Nickel.set(addItem(527)); //"Nickelback Leaf", "Source of Nickel"
        ItemList.Crop_Drop_Zinc.set(addItem(528)); //"Galvania Leaf", "Source of Zinc"
        ItemList.Crop_Drop_Manganese.set(addItem(529)); //"Pyrolusium Leaf", "Source of Manganese"
        ItemList.Crop_Drop_Scheelite.set(addItem(531)); //"Scheelinium Leaf", "Source of Tungsten"
        ItemList.Crop_Drop_Platinum.set(addItem(532)); //"Platina Leaf", "Source of Platinum"
        ItemList.Crop_Drop_Iridium.set(addItem(533)); //"Quantaria Leaf", "Source of Iridium"
        ItemList.Crop_Drop_Osmium.set(addItem(534)); //"Quantaria Leaf", "Source of Osmium"
        ItemList.Crop_Drop_Naquadah.set(addItem(535)); //"Stargatium Leaf", "Source of Naquadah"

        ItemList.Crop_Drop_Chilly.set(addItem(550).addOreDict("cropChilipepper").addStats(new FoodStats(1, 0.3F, false, false, null, new RandomPotionEffect(MobEffects.NAUSEA, 200, 1, 40)))); //"Chilly Pepper", "It is red and hot"
        ItemList.Crop_Drop_Lemon.set(addItem(551).addOreDict("cropLemon").addStats(new FoodStats(1, 0.3F))); //"Lemon", "Don't make Lemonade"
        ItemList.Crop_Drop_Tomato.set(addItem(552).addOreDict("cropTomato").addStats(new FoodStats(1, 0.2F))); //"Tomato", "Solid Ketchup"
        ItemList.Crop_Drop_MTomato.set(addItem(553).addOreDict("cropTomato").addStats(new FoodStats(9, 1.0F, false, false, null, new RandomPotionEffect(MobEffects.REGENERATION, 100, 100, 100)))); //"Max Tomato", "Full Health in one Tomato"
        ItemList.Crop_Drop_Grapes.set(addItem(554).addOreDict("cropGrape").addStats(new FoodStats(2, 0.3F))); //"Grapes", "Source of Wine"
        ItemList.Crop_Drop_Onion.set(addItem(555).addOreDict("cropOnion").addStats(new FoodStats(2, 0.2F))); //"Onion", "Taking over the whole Taste"
        ItemList.Crop_Drop_Cucumber.set(addItem(556).addOreDict("cropCucumber").addStats(new FoodStats(1, 0.2F))); //"Cucumber", "Not a Sea Cucumber!"

        ItemList.Food_Cheese.set(addItem(558).addOreDict("foodCheese").addStats(new FoodStats(3, 0.6F))); //"Cheese", "Click the Cheese"
        ItemList.Food_Dough.set(addItem(559).addOreDict("foodDough").addStats(new FoodStats(1, 0.1F))); //"Dough", "For making Breads",
        ItemList.Food_Flat_Dough.set(addItem(560).addStats(new FoodStats(1, 0.1F))); //"Flattened Dough", "For making Pizza"
        ItemList.Food_Raw_Bread.set(addItem(561).addStats(new FoodStats(1, 0.2F))); //"Dough", "In Bread Shape"
        ItemList.Food_Raw_Bun.set(addItem(562).addStats(new FoodStats(1, 0.1F))); //"Dough", "In Bun Shape"
        ItemList.Food_Raw_Baguette.set(addItem(563).addStats(new FoodStats(1, 0.3F))); //"Dough", "In Baguette Shape"
        ItemList.Food_Baked_Bun.set(addItem(564).addStats(new FoodStats(3, 0.5F))); //"Bun", "Do not teleport Bread!"
        ItemList.Food_Baked_Baguette.set(addItem(565).addStats(new FoodStats(8, 0.5F))); //"Baguette", "I teleported nothing BUT Bread!!!"
        ItemList.Food_Sliced_Bread.set(addItem(566).addStats(new FoodStats(2, 0.3F))); //"Sliced Bread", "Just half a Bread"
        ItemList.Food_Sliced_Bun.set(addItem(567).addStats(new FoodStats(1, 0.3F))); //"Sliced Bun", "Just half a Bun"
        ItemList.Food_Sliced_Baguette.set(addItem(568).addStats(new FoodStats(4, 0.3F))); //"Sliced Baguette", "Just half a Baguette"
        ItemList.Food_Raw_Cake.set(addItem(569).addStats(new FoodStats(2, 0.2F))); //"Cake Bottom", "For making Cake"
        ItemList.Food_Baked_Cake.set(addItem(570).addStats(new FoodStats(3, 0.3F))); //"Baked Cake Bottom", "I know I promised you an actual Cake, but well..."
        ItemList.Food_Sliced_Lemon.set(addItem(571).addStats(new FoodStats(1, 0.075F))); //"Lemon Slice", "Ideal to put on your Drink"
        ItemList.Food_Sliced_Tomato.set(addItem(572).addStats(new FoodStats(1, 0.05F))); //"Tomato Slice", "Solid Ketchup"
        ItemList.Food_Sliced_Onion.set(addItem(573).addStats(new FoodStats(1, 0.05F))); //"Onion Slice", "ONIONS, UNITE!"
        ItemList.Food_Sliced_Cucumber.set(addItem(574).addStats(new FoodStats(1, 0.05F))); //"Cucumber Slice", "QUEWWW-CUMMM-BERRR!!!"

        ItemList.Food_Sliced_Cheese.set(addItem(576).addStats(new FoodStats(1, 0.1F))); //"Cheese Slice", "ALIEN ATTACK!!!, throw the CHEEEEESE!!!"

        ModHandler.addExtractionRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 0), new ItemStack(Items.DYE, 2, 1));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 1), new ItemStack(Items.DYE, 2, 12));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 2), new ItemStack(Items.DYE, 2, 13));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 3), new ItemStack(Items.DYE, 2, 7));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 4), new ItemStack(Items.DYE, 2, 1));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 5), new ItemStack(Items.DYE, 2, 14));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 6), new ItemStack(Items.DYE, 2, 7));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 7), new ItemStack(Items.DYE, 2, 9));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 8), new ItemStack(Items.DYE, 2, 7));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.YELLOW_FLOWER, 1, 0), new ItemStack(Items.DYE, 2, 11));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new ItemStack(Items.DYE, 3, 11));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), new ItemStack(Items.DYE, 3, 13));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), new ItemStack(Items.DYE, 3, 1));
        ModHandler.addExtractionRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), new ItemStack(Items.DYE, 3, 9));
        ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Plumbilia.get(1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Lead, 1));
        ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Argentia.get(1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Silver, 1));
        ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Indigo.get(1), ItemList.Dye_Indigo.get(1));
        ModHandler.addExtractionRecipe(ItemList.Crop_Drop_MilkWart.get(1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Milk, 1));
        ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Coppon.get(1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Copper, 1));
        ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Tine.get(1), OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Tin, 1));

        ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Coppon.get(4), new ItemStack(Blocks.WOOL, 1, 1));
        ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Plumbilia.get(8), ItemList.IC2_PlantballCompressed.get(1));
        ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Argentia.get(8), ItemList.IC2_PlantballCompressed.get(1));
        ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Indigo.get(8), ItemList.IC2_PlantballCompressed.get(1));
        ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Ferru.get(8), ItemList.IC2_PlantballCompressed.get(1));
        ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Aurelia.get(8), ItemList.IC2_PlantballCompressed.get(1));
        ModHandler.addCompressionRecipe(ItemList.Crop_Drop_OilBerry.get(8), ItemList.IC2_PlantballCompressed.get(1));
        ModHandler.addCompressionRecipe(ItemList.Crop_Drop_BobsYerUncleRanks.get(8), ItemList.IC2_PlantballCompressed.get(1));
        ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Tine.get(4), ItemList.IC2_PlantballCompressed.get(1));
        ModHandler.addCompressionRecipe(new ItemStack(Blocks.RED_FLOWER, 8, 32767), ItemList.IC2_PlantballCompressed.get(1));
        ModHandler.addCompressionRecipe(new ItemStack(Blocks.YELLOW_FLOWER, 8, 32767), ItemList.IC2_PlantballCompressed.get(1));

        ModHandler.addPulverisationRecipe(ItemList.Food_Sliced_Cheese.get(1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Cheese, 1));
        ModHandler.addPulverisationRecipe(ItemList.Dye_Cocoa.get(1), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Cocoa, 1));
        ModHandler.addPulverisationRecipe(ItemList.Crop_Drop_Tine.get(1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2));
        ModHandler.addPulverisationRecipe(new ItemStack(Items.REEDS, 1), new ItemStack(Items.SUGAR, 1), null, 0, false);
        ModHandler.addPulverisationRecipe(new ItemStack(Blocks.MELON_BLOCK, 1, 0), new ItemStack(Items.MELON, 8, 0), new ItemStack(Items.MELON_SEEDS, 1), 80, false);
        ModHandler.addPulverisationRecipe(new ItemStack(Blocks.PUMPKIN, 1, 0), new ItemStack(Items.PUMPKIN_SEEDS, 4, 0), null, 0, false);
        ModHandler.addPulverisationRecipe(new ItemStack(Items.MELON, 1, 0), new ItemStack(Items.MELON_SEEDS, 1, 0), null, 0, false);
        ModHandler.addPulverisationRecipe(new ItemStack(Items.WHEAT, 1, 0), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wheat, 1), null, 0, false);
        ModHandler.addPulverisationRecipe(new ItemStack(Items.STICK, 1), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2), null, 0, false);
        ModHandler.addPulverisationRecipe(new ItemStack(Blocks.WOOL, 1, 32767), new ItemStack(Items.STRING, 2), new ItemStack(Items.STRING, 1), 50, false);
    }

    @Override
    public final ItemStack getContainerItem(ItemStack stack) {
        int damage = stack.getItemDamage();
        if (damage < metaItemOffset) {
            return null;
        }
        if (damage < metaItemOffset + 100) {
            return ItemList.ThermosCan_Empty.get(1);
        }
        if (damage < metaItemOffset + 200) {
            return ItemList.Bottle_Empty.get(1);
        }
        return null;
    }
}