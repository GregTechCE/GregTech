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

        ItemList.ThermosCan_Dark_Coffee.set(addItem(0, "thermoscan.dark.coffee").addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SPEED, 400, 1, 70), new RandomPotionEffect(MobEffects.HASTE, 400, 1, 70))));
        ItemList.ThermosCan_Dark_Cafe_au_lait.set(addItem(1, "thermoscan.dark.cafe.au.lait").setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SPEED, 400, 2, 90), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 90))));
        ItemList.ThermosCan_Coffee.set(addItem(2, "thermoscan.coffee").addStats(new FoodStats(3, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SPEED, 400, 0, 50), new RandomPotionEffect(MobEffects.HASTE, 400, 0, 50))));
        ItemList.ThermosCan_Cafe_au_lait.set(addItem(3, "thermoscan.cafe.au.lait").addStats(new FoodStats(3, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SPEED, 400, 1, 70), new RandomPotionEffect(MobEffects.HASTE, 400, 1, 70))));
        ItemList.ThermosCan_Lait_au_cafe.set(addItem(4, "thermoscan.lait.au.cafe").setInvisible().addStats(new FoodStats(3, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SPEED, 400, 2, 90), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 90))));
        ItemList.ThermosCan_Dark_Chocolate_Milk.set(addItem(5, "thermoscan.dark.chocolate.milk").addStats(new FoodStats(3, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.REGENERATION, 50, 1, 60))));
        ItemList.ThermosCan_Chocolate_Milk.set(addItem(6, "thermoscan.chocolate.milk").addStats(new FoodStats(3, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.REGENERATION, 50, 1, 90))));
        ItemList.ThermosCan_Tea.set(addItem(7, "thermoscan.tea").addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SLOWNESS, 300, 0, 50))));
        ItemList.ThermosCan_Sweet_Tea.set(addItem(8, "thermoscan.sweet.tea").setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1))));
        ItemList.ThermosCan_Ice_Tea.set(addItem(9, "thermoscan.ice.tea").addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.ThermosCan_Empty.get(1), new RandomPotionEffect(MobEffects.SLOWNESS, 300, 0, 50))));

        ItemList.GelledToluene.set(addItem(10, "gelledtoluene"));

        ItemList.Bottle_Purple_Drink.set(addItem(100, "bottle.purple.drink").addStats(new FoodStats(8, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.SLOWNESS, 400, 1, 90))));
        ItemList.Bottle_Grape_Juice.set(addItem(101, "bottle.grape.juice").addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HUNGER, 400, 1, 60))));
        ItemList.Bottle_Wine.set(addItem(102, "bottle.wine").addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 1, 60), new RandomPotionEffect(MobEffects.INSTANT_HEALTH, 0, 0, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5))));
        ItemList.Bottle_Vinegar.set(addItem(103, "bottle.vinegar").setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 1, 90), new RandomPotionEffect(MobEffects.INSTANT_HEALTH, 0, 1, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5))));
        ItemList.Bottle_Potato_Juice.set(addItem(104, "bottle.potato.juice").setInvisible().addStats(new FoodStats(3, 0.3F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1))));
        ItemList.Bottle_Vodka.set(addItem(105, "bottle.vodka").setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 500, 0, 60), new RandomPotionEffect(MobEffects.STRENGTH, 500, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5))));
        ItemList.Bottle_Leninade.set(addItem(106, "bottle.leninade").setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 500, 1, 90), new RandomPotionEffect(MobEffects.STRENGTH, 500, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5))));
        ItemList.Bottle_Mineral_Water.set(addItem(107, "bottle.mineral.water").addStats(new FoodStats(1, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.REGENERATION, 100, 1, 10))));
        ItemList.Bottle_Salty_Water.set(addItem(108, "bottle.salty.water").setInvisible().addStats(new FoodStats(1, 0.0F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HUNGER, 400, 2, 95))));
        ItemList.Bottle_Reed_Water.set(addItem(109, "bottle.reed.water").addStats(new FoodStats(1, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1))));
        ItemList.Bottle_Rum.set(addItem(110, "bottle.rum").setInvisible().addStats(new FoodStats(4, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 300, 0, 60), new RandomPotionEffect(MobEffects.STRENGTH, 300, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)))); //  "", ""
        ItemList.Bottle_Pirate_Brew.set(addItem(111, "bottle.pirate.brew").setInvisible().addStats(new FoodStats(4, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 90), new RandomPotionEffect(MobEffects.STRENGTH, 300, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5))));
        ItemList.Bottle_Hops_Juice.set(addItem(112, "bottle.hops.juice").addStats(new FoodStats(1, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1))));
        ItemList.Bottle_Dark_Beer.set(addItem(113, "bottle.dark.beer").addStats(new FoodStats(4, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 60), new RandomPotionEffect(MobEffects.STRENGTH, 300, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5))));
        ItemList.Bottle_Dragon_Blood.set(addItem(114, "bottle.dragon.blood").setInvisible().addStats(new FoodStats(4, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 300, 2, 90), new RandomPotionEffect(MobEffects.STRENGTH, 300, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5))));
        ItemList.Bottle_Wheaty_Juice.set(addItem(115, "bottle.wheaty.juice").addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1))));
        ItemList.Bottle_Scotch.set(addItem(116, "bottle.scotch").setInvisible().addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 0, 60), new RandomPotionEffect(MobEffects.RESISTANCE, 400, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5))));
        ItemList.Bottle_Glen_McKenner.set(addItem(117, "bottle.glen.mckenner").setInvisible().addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 1, 90), new RandomPotionEffect(MobEffects.RESISTANCE, 400, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5))));
        ItemList.Bottle_Wheaty_Hops_Juice.set(addItem(118, "bottle.wheaty.hops.juice").addStats(new FoodStats(1, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1))));
        ItemList.Bottle_Beer.set(addItem(119, "bottle.beer").addStats(new FoodStats(6, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 0, 60), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 60), new RandomPotionEffect(MobEffects.POISON, 100, 0, 5))));
        ItemList.Bottle_Chilly_Sauce.set(addItem(120, "bottle.chilly.sauce").addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 1000, 0, 10), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 1000, 0, 60))));
        ItemList.Bottle_Hot_Sauce.set(addItem(121, "bottle.hot.sauce").addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 2000, 0, 30), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 2000, 0, 70))));
        ItemList.Bottle_Diabolo_Sauce.set(addItem(122, "bottle.diabolo.sauce").setInvisible().addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 3000, 1, 50), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 3000, 0, 80))));
        ItemList.Bottle_Diablo_Sauce.set(addItem(123, "bottle.diablo.sauce").setInvisible().addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 4000, 1, 70), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 4000, 0, 90))));
        ItemList.Bottle_Snitches_Glitch_Sauce.set(addItem(124, "bottle.snitches.glitch.sauce").setInvisible().addStats(new FoodStats(2, 0.1F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 9999, 2, 999), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 9999, 9, 999))));
        ItemList.Bottle_Apple_Juice.set(addItem(125, "bottle.apple.juice").addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HUNGER, 400, 0, 20))));
        ItemList.Bottle_Cider.set(addItem(126, "bottle.cider").addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.NAUSEA, 400, 0, 60), new RandomPotionEffect(MobEffects.RESISTANCE, 400, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5))));
        ItemList.Bottle_Golden_Apple_Juice.set(addItem(127, "bottle.golden.apple.juice").setInvisible().addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HUNGER, 400, 0, 20), new RandomPotionEffect(MobEffects.ABSORPTION, 2400, 0, 100), new RandomPotionEffect(MobEffects.REGENERATION, 100, 1, 100))));
        ItemList.Bottle_Golden_Cider.set(addItem(128, "bottle.golden.cider").setInvisible().addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HUNGER, 400, 0, 60), new RandomPotionEffect(MobEffects.ABSORPTION, 2400, 1, 95), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5))));
        ItemList.Bottle_Iduns_Apple_Juice.set(addItem(129, "bottle.iduns.apple.juice").setInvisible().addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.REGENERATION, 600, 4, 100), new RandomPotionEffect(MobEffects.ABSORPTION, 2400, 0, 100), new RandomPotionEffect(MobEffects.RESISTANCE, 6000, 0, 100), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0, 100))));
        ItemList.Bottle_Notches_Brew.set(addItem(130, "bottle.notches.brew").setInvisible().addStats(new FoodStats(4, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.REGENERATION, 700, 4, 95), new RandomPotionEffect(MobEffects.ABSORPTION, 3000, 1, 95), new RandomPotionEffect(MobEffects.RESISTANCE, 7000, 1, 95), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 7000, 0, 95), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 20))));
        ItemList.Bottle_Lemon_Juice.set(addItem(131, "bottle.lemon.juice").addStats(new FoodStats(2, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HASTE, 1200, 0, 60))));
        ItemList.Bottle_Limoncello.set(addItem(132, "bottle.limoncello").setInvisible().addStats(new FoodStats(2, 0.4F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HASTE, 1200, 0, 90), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5))));
        ItemList.Bottle_Lemonade.set(addItem(133, "bottle.lemonade").addStats(new FoodStats(4, 0.3F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HASTE, 900, 1, 90))));
        ItemList.Bottle_Alcopops.set(addItem(134, "bottle.alcopops").setInvisible().addStats(new FoodStats(2, 0.2F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.HASTE, 900, 1, 90), new RandomPotionEffect(MobEffects.POISON, 300, 2, 20))));
        ItemList.Bottle_Cave_Johnsons_Grenade_Juice.set(addItem(135, "bottle.cave.johnsons.grenade.juice").setInvisible().addStats(new FoodStats(0, 0.0F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1))));
        ItemList.Bottle_Milk.set(addItem(136, "bottle.milk").setUnificationData(OrePrefix.bottle, Materials.Milk).addStats(new FoodStats(0, 0.0F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1))));
        ItemList.Bottle_Holy_Water.set(addItem(137, "bottle.holy.water").setUnificationData(OrePrefix.bottle, Materials.HolyWater).addStats(new FoodStats(0, 0.0F, true, GregTech_API.sDrinksAlwaysDrinkable, ItemList.Bottle_Empty.get(1), new RandomPotionEffect(MobEffects.POISON, 100, 1, 100))));

        ItemList.Food_Potato_On_Stick.set(addItem(200, "food.potato.on.stick").addStats(new FoodStats(1, 0.3F, false, false, new ItemStack(Items.STICK, 1))));
        ItemList.Food_Potato_On_Stick_Roasted.set(addItem(201, "food.potato.on.stick.roasted").addStats(new FoodStats(6, 0.6F, false, false, new ItemStack(Items.STICK, 1))));
        ItemList.Food_Raw_Fries.set(addItem(202, "food.raw.fries").setMaxStackSize(16).addStats(new FoodStats(1, 0.3F)));
        ItemList.Food_Fries.set(addItem(203, "food.fries").setMaxStackSize(16).addStats(new FoodStats(7, 0.5F)));
        ItemList.Food_Packaged_Fries.set(addItem(204, "food.packaged.fries").addStats(new FoodStats(7, 0.5F, false, false, OreDictionaryUnifier.get(OrePrefix.plate, Materials.Paper, 1))));
        ItemList.Food_Raw_PotatoChips.set(addItem(205, "food.raw.potatochips").setMaxStackSize(16).addStats(new FoodStats(1, 0.3F)));
        ItemList.Food_PotatoChips.set(addItem(206, "food.potatochips").setMaxStackSize(16).addStats(new FoodStats(7, 0.5F)));
        ItemList.Food_ChiliChips.set(addItem(207, "food.chilichips").setMaxStackSize(16).addStats(new FoodStats(7, 0.6F)));
        ItemList.Food_Packaged_PotatoChips.set(addItem(208, "food.packaged.potatochips").addStats(new FoodStats(7, 0.5F, false, false, OreDictionaryUnifier.get(OrePrefix.foil, Materials.Aluminium, 1))));
        ItemList.Food_Packaged_ChiliChips.set(addItem(209, "food.packaged.chilichips").addStats(new FoodStats(7, 0.6F, false, false, OreDictionaryUnifier.get(OrePrefix.foil, Materials.Aluminium, 1))));
        ItemList.Food_Chum.set(addItem(210, "food.chum").addStats(new FoodStats(5, 0.2F, false, true, null, new RandomPotionEffect(MobEffects.HUNGER, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80))));
        ItemList.Food_Chum_On_Stick.set(addItem(211, "food.chum.on.stick").addStats(new FoodStats(5, 0.2F, false, true, new ItemStack(Items.STICK, 1), new RandomPotionEffect(MobEffects.HUNGER, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80))));
        ItemList.Food_Dough_Sugar.set(addItem(212, "food.dough.sugar").addStats(new FoodStats(1, 0.1F)));
        ItemList.Food_Dough_Chocolate.set(addItem(213, "food.dough.chocolate").addStats(new FoodStats(1, 0.1F)));
        ItemList.Food_Raw_Cookie.set(addItem(214, "food.raw.cookie").addStats(new FoodStats(1, 0.1F)));

        ItemList.Food_Sliced_Buns.set(addItem(220, "food.sliced.buns").addStats(new FoodStats(3, 0.5F)));
        ItemList.Food_Burger_Veggie.set(addItem(221, "food.burger.veggie").addStats(new FoodStats(3, 0.5F)));
        ItemList.Food_Burger_Cheese.set(addItem(222, "food.burger.cheese").addStats(new FoodStats(3, 0.5F)).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Cheese, 907200))));
        ItemList.Food_Burger_Meat.set(addItem(223, "food.burger.meat").addStats(new FoodStats(3, 0.5F)));
        ItemList.Food_Burger_Chum.set(addItem(224, "food.burger.chum").addStats(new FoodStats(5, 0.2F, false, true, null, new RandomPotionEffect(MobEffects.SLOWNESS, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80))));

        ItemList.Food_Sliced_Breads.set(addItem(230, "food.sliced.breads").addStats(new FoodStats(5, 0.6F)));
        ItemList.Food_Sandwich_Veggie.set(addItem(231, "food.sandwich.veggie").setMaxStackSize(32).addStats(new FoodStats(7, 0.6F)));
        ItemList.Food_Sandwich_Cheese.set(addItem(232, "food.sandwich.cheese").setMaxStackSize(32).addStats(new FoodStats(7, 0.6F)));
        ItemList.Food_Sandwich_Bacon.set(addItem(233, "food.sandwich.bacon").setMaxStackSize(32).addStats(new FoodStats(10, 0.8F)));
        ItemList.Food_Sandwich_Steak.set(addItem(234, "food.sandwich.steak").setMaxStackSize(32).addStats(new FoodStats(10, 0.8F)));

        ItemList.Food_Sliced_Baguettes.set(addItem(240, "food.sliced.baguettes").addStats(new FoodStats(8, 0.5F)));
        ItemList.Food_Large_Sandwich_Veggie.set(addItem(241, "food.large.sandwich.veggie").setMaxStackSize(16).addStats(new FoodStats(15, 0.8F)));
        ItemList.Food_Large_Sandwich_Cheese.set(addItem(242, "food.large.sandwich.cheese").setMaxStackSize(16).addStats(new FoodStats(15, 0.8F)));
        ItemList.Food_Large_Sandwich_Bacon.set(addItem(243, "food.large.sandwich.bacon").setMaxStackSize(16).addStats(new FoodStats(20, 1.0F)));
        ItemList.Food_Large_Sandwich_Steak.set(addItem(244, "food.large.sandwich.steak").setMaxStackSize(16).addStats(new FoodStats(20, 1.0F)));

        ItemList.Food_Raw_Pizza_Veggie.set(addItem(250, "food.raw.pizza.veggie").addStats(new FoodStats(1, 0.2F)));
        ItemList.Food_Raw_Pizza_Cheese.set(addItem(251, "food.raw.pizza.cheese").addStats(new FoodStats(2, 0.2F)));
        ItemList.Food_Raw_Pizza_Meat.set(addItem(252, "food.raw.pizza.meat").addStats(new FoodStats(2, 0.2F)));

        ItemList.Food_Baked_Pizza_Veggie.set(addItem(260, "food.baked.pizza.veggie").addStats(new FoodStats(3, 0.3F)));
        ItemList.Food_Baked_Pizza_Cheese.set(addItem(261, "food.baked.pizza.cheese").addStats(new FoodStats(4, 0.4F)));
        ItemList.Food_Baked_Pizza_Meat.set(addItem(262, "food.baked.pizza.meat").addStats(new FoodStats(5, 0.5F)));

        ItemList.Dye_Indigo.set(addItem(410, "dye.indigo").addOreDict("dyeBlue"));
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            ItemList.DYE_ONLY_ITEMS[i].set(addItem(414 + i, "dye." + EnumDyeColor.byMetadata(i).getUnlocalizedName()).addOreDict("dye" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, EnumDyeColor.byMetadata(i).getName())));
        }
        ItemList.Plank_Oak.set(addItem(470, "plank.oak").setBurnValue(75));
        ItemList.Plank_Spruce.set(addItem(471, "plank.spruce").setBurnValue(75));
        ItemList.Plank_Birch.set(addItem(472, "plank.birch").setBurnValue(75));
        ItemList.Plank_Jungle.set(addItem(473, "plank.jungle").setBurnValue(75));
        ItemList.Plank_Acacia.set(addItem(474, "plank.acacia").setBurnValue(75));
        ItemList.Plank_DarkOak.set(addItem(475, "plank.darkoak").setBurnValue(75));

        ItemList.SFMixture.set(addItem(270, "sfmixture"));
        ItemList.MSFMixture.set(addItem(271, "msfmixture"));

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
        

        ItemList.Crop_Drop_Plumbilia.set(addItem(500, "crop.drop.plumbilia"));
        ItemList.Crop_Drop_Argentia.set(addItem(501, "crop.drop.argentia"));
        ItemList.Crop_Drop_Indigo.set(addItem(502, "crop.drop.indigo"));
        ItemList.Crop_Drop_Ferru.set(addItem(503, "crop.drop.ferru"));
        ItemList.Crop_Drop_Aurelia.set(addItem(504, "crop.drop.aurelia"));
        ItemList.Crop_Drop_TeaLeaf.set(addItem(505, "crop.drop.tealeaf").addOreDict("cropTea"));

        ItemList.Crop_Drop_OilBerry.set(addItem(510, "crop.drop.oilberry"));
        ItemList.Crop_Drop_BobsYerUncleRanks.set(addItem(511, "crop.drop.bobsyeruncleranks"));
        ItemList.Crop_Drop_UUMBerry.set(addItem(512, "crop.drop.uumberry"));
        ItemList.Crop_Drop_UUABerry.set(addItem(513, "crop.drop.uuaberry"));

        ItemList.Crop_Drop_MilkWart.set(addItem(520, "crop.drop.milkwart"));

        ItemList.Crop_Drop_Coppon.set(addItem(530, "crop.drop.coppon"));

        ItemList.Crop_Drop_Tine.set(addItem(540, "crop.drop.tine").setBurnValue(100));

        ItemList.Crop_Drop_Bauxite.set(addItem(521, "crop.drop.bauxite"));
        ItemList.Crop_Drop_Ilmenite.set(addItem(522, "crop.drop.ilmenite"));
        ItemList.Crop_Drop_Pitchblende.set(addItem(523, "crop.drop.pitchblende"));
        ItemList.Crop_Drop_Uraninite.set(addItem(524, "crop.drop.uraninite"));
        ItemList.Crop_Drop_Thorium.set(addItem(526, "crop.drop.thorium"));
        ItemList.Crop_Drop_Nickel.set(addItem(527, "crop.drop.nickel"));
        ItemList.Crop_Drop_Zinc.set(addItem(528, "crop.drop.zinc"));
        ItemList.Crop_Drop_Manganese.set(addItem(529, "crop.drop.manganese"));
        ItemList.Crop_Drop_Scheelite.set(addItem(531, "crop.drop.scheelite"));
        ItemList.Crop_Drop_Platinum.set(addItem(532, "crop.drop.platinum"));
        ItemList.Crop_Drop_Iridium.set(addItem(533, "crop.drop.iridium"));
        ItemList.Crop_Drop_Osmium.set(addItem(534, "crop.drop.osmium"));
        ItemList.Crop_Drop_Naquadah.set(addItem(535, "crop.drop.naquadah"));

        ItemList.Crop_Drop_Chilly.set(addItem(550, "crop.drop.chilly").addOreDict("cropChilipepper").addStats(new FoodStats(1, 0.3F, false, false, null, new RandomPotionEffect(MobEffects.NAUSEA, 200, 1, 40))));
        ItemList.Crop_Drop_Lemon.set(addItem(551, "crop.drop.lemon").addOreDict("cropLemon").addStats(new FoodStats(1, 0.3F)));
        ItemList.Crop_Drop_Tomato.set(addItem(552, "crop.drop.tomato").addOreDict("cropTomato").addStats(new FoodStats(1, 0.2F)));
        ItemList.Crop_Drop_MTomato.set(addItem(553, "crop.drop.mtomato").addOreDict("cropTomato").addStats(new FoodStats(9, 1.0F, false, false, null, new RandomPotionEffect(MobEffects.REGENERATION, 100, 100, 100))));
        ItemList.Crop_Drop_Grapes.set(addItem(554, "crop.drop.grapes").addOreDict("cropGrape").addStats(new FoodStats(2, 0.3F)));
        ItemList.Crop_Drop_Onion.set(addItem(555, "crop.drop.onion").addOreDict("cropOnion").addStats(new FoodStats(2, 0.2F)));
        ItemList.Crop_Drop_Cucumber.set(addItem(556, "crop.drop.cucumber").addOreDict("cropCucumber").addStats(new FoodStats(1, 0.2F)));

        ItemList.Food_Cheese.set(addItem(558, "food.cheese").addOreDict("foodCheese").addStats(new FoodStats(3, 0.6F)));
        ItemList.Food_Dough.set(addItem(559, "food.dough").addOreDict("foodDough").addStats(new FoodStats(1, 0.1F)));
        ItemList.Food_Flat_Dough.set(addItem(560, "food.flat.dough").addStats(new FoodStats(1, 0.1F)));
        ItemList.Food_Raw_Bread.set(addItem(561, "food.raw.bread").addStats(new FoodStats(1, 0.2F)));
        ItemList.Food_Raw_Bun.set(addItem(562, "food.raw.bun").addStats(new FoodStats(1, 0.1F)));
        ItemList.Food_Raw_Baguette.set(addItem(563, "food.raw.baguette").addStats(new FoodStats(1, 0.3F)));
        ItemList.Food_Baked_Bun.set(addItem(564, "food.baked.bun").addStats(new FoodStats(3, 0.5F)));
        ItemList.Food_Baked_Baguette.set(addItem(565, "food.baked.baguette").addStats(new FoodStats(8, 0.5F)));
        ItemList.Food_Sliced_Bread.set(addItem(566, "food.sliced.bread").addStats(new FoodStats(2, 0.3F)));
        ItemList.Food_Sliced_Bun.set(addItem(567, "food.sliced.bun").addStats(new FoodStats(1, 0.3F)));
        ItemList.Food_Sliced_Baguette.set(addItem(568, "food.sliced.baguette").addStats(new FoodStats(4, 0.3F)));
        ItemList.Food_Raw_Cake.set(addItem(569, "food.raw.cake").addStats(new FoodStats(2, 0.2F)));
        ItemList.Food_Baked_Cake.set(addItem(570, "food.baked.cake").addStats(new FoodStats(3, 0.3F)));
        ItemList.Food_Sliced_Lemon.set(addItem(571, "food.sliced.lemon").addStats(new FoodStats(1, 0.075F)));
        ItemList.Food_Sliced_Tomato.set(addItem(572, "food.sliced.tomato").addStats(new FoodStats(1, 0.05F)));
        ItemList.Food_Sliced_Onion.set(addItem(573, "food.sliced.onion").addStats(new FoodStats(1, 0.05F)));
        ItemList.Food_Sliced_Cucumber.set(addItem(574, "food.sliced.cucumber").addStats(new FoodStats(1, 0.05F)));

        ItemList.Food_Sliced_Cheese.set(addItem(576, "food.sliced.cheese").addStats(new FoodStats(1, 0.1F)));

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