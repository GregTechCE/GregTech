package gregtech.common.items;

import com.google.common.base.CaseFormat;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.FoodStats;
import gregtech.api.recipes.ModHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.RandomPotionEffect;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import static gregtech.api.GTValues.W;
import static gregtech.common.items.MetaItems.*;

public class MetaItem2 extends MaterialMetaItem {

    public MetaItem2() {
        super(OrePrefix.toolHeadSword, OrePrefix.toolHeadPickaxe, OrePrefix.toolHeadShovel, OrePrefix.toolHeadAxe,
                OrePrefix.toolHeadHoe, OrePrefix.toolHeadHammer, OrePrefix.toolHeadFile, OrePrefix.toolHeadSaw,
                OrePrefix.toolHeadDrill, OrePrefix.toolHeadChainsaw, OrePrefix.toolHeadWrench, OrePrefix.toolHeadUniversalSpade,
                OrePrefix.toolHeadSense, OrePrefix.toolHeadPlow,  OrePrefix.toolHeadBuzzSaw, OrePrefix.turbineBlade, 
                OrePrefix.wireFine, OrePrefix.gearSmall, OrePrefix.rotor, OrePrefix.stickLong, OrePrefix.springSmall, OrePrefix.spring,
                OrePrefix.gemChipped, OrePrefix.gemFlawed, OrePrefix.gemFlawless, OrePrefix.gemExquisite, OrePrefix.gear,
                null, null, null, null, null);
    }

    public void registerSubItems() {
        boolean drinksAlwaysDrinkable = false;

        THERMOS_CAN_DARK_COFFEE = addItem(0, "thermos_can.dark.coffee").addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SPEED, 400, 1, 70), new RandomPotionEffect(MobEffects.HASTE, 400, 1, 70)));
        THERMOS_CAN_DARK_CAFE_AU_LAIT = addItem(1, "thermos_can.dark.cafe.au.lait").setInvisible().addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SPEED, 400, 2, 90), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 90)));
        THERMOS_CAN_COFFEE = addItem(2, "thermos_can.coffee").addStats(new FoodStats(3, 0.4F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SPEED, 400, 0, 50), new RandomPotionEffect(MobEffects.HASTE, 400, 0, 50)));
        THERMOS_CAN_CAFE_AU_LAIT = addItem(3, "thermos_can.cafe.au.lait").addStats(new FoodStats(3, 0.4F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SPEED, 400, 1, 70), new RandomPotionEffect(MobEffects.HASTE, 400, 1, 70)));
        THERMOS_CAN_LAIT_AU_CAFE = addItem(4, "thermos_can.lait.au.cafe").setInvisible().addStats(new FoodStats(3, 0.4F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SPEED, 400, 2, 90), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 90)));
        THERMOS_CAN_DARK_CHOCOLATE_MILK = addItem(5, "thermos_can.dark.chocolate.milk").addStats(new FoodStats(3, 0.4F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.REGENERATION, 50, 1, 60)));
        THERMOS_CAN_CHOCOLATE_MILK = addItem(6, "thermos_can.chocolate.milk").addStats(new FoodStats(3, 0.4F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.REGENERATION, 50, 1, 90)));
        THERMOS_CAN_TEA = addItem(7, "thermos_can.tea").addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SLOWNESS, 300, 0, 50)));
        THERMOS_CAN_SWEET_TEA = addItem(8, "thermos_can.sweet.tea").setInvisible().addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm()));
        THERMOS_CAN_ICE_TEA = addItem(9, "thermos_can.ice.tea").addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, THERMOS_CAN_EMPTY.getStackForm(), new RandomPotionEffect(MobEffects.SLOWNESS, 300, 0, 50)));

        GELLED_TOLUENE = addItem(10, "gelled_toluene");

        ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);

        BOTTLE_PURPLE_DRINK = addItem(100, "bottle.purple.drink").addStats(new FoodStats(8, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.SLOWNESS, 400, 1, 90)));
        BOTTLE_GRAPE_JUICE = addItem(101, "bottle.grape.juice").addStats(new FoodStats(4, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.HUNGER, 400, 1, 60)));
        BOTTLE_WINE = addItem(102, "bottle.wine").addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 400, 1, 60), new RandomPotionEffect(MobEffects.INSTANT_HEALTH, 0, 0, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)));
        BOTTLE_VINEGAR = addItem(103, "bottle.vinegar").setInvisible().addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 400, 1, 90), new RandomPotionEffect(MobEffects.INSTANT_HEALTH, 0, 1, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5)));
        BOTTLE_POTATO_JUICE = addItem(104, "bottle.potato.juice").setInvisible().addStats(new FoodStats(3, 0.3F, true, drinksAlwaysDrinkable, emptyBottle));
        BOTTLE_VODKA = addItem(105, "bottle.vodka").setInvisible().addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 500, 0, 60), new RandomPotionEffect(MobEffects.STRENGTH, 500, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)));
        BOTTLE_LENINADE = addItem(106, "bottle.leninade").setInvisible().addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 500, 1, 90), new RandomPotionEffect(MobEffects.STRENGTH, 500, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5)));
        BOTTLE_MINERAL_WATER = addItem(107, "bottle.mineral.water").addStats(new FoodStats(1, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.REGENERATION, 100, 1, 10)));
        BOTTLE_SALTY_WATER = addItem(108, "bottle.salty.water").setInvisible().addStats(new FoodStats(1, 0.0F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.HUNGER, 400, 2, 95)));
        BOTTLE_REED_WATER = addItem(109, "bottle.reed.water").addStats(new FoodStats(1, 0.1F, true, drinksAlwaysDrinkable, emptyBottle));
        BOTTLE_RUM = addItem(110, "bottle.rum").setInvisible().addStats(new FoodStats(4, 0.4F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 300, 0, 60), new RandomPotionEffect(MobEffects.STRENGTH, 300, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)));
        BOTTLE_PIRATE_BREW = addItem(111, "bottle.pirate.brew").setInvisible().addStats(new FoodStats(4, 0.4F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 90), new RandomPotionEffect(MobEffects.STRENGTH, 300, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5)));
        BOTTLE_HOPS_JUICE = addItem(112, "bottle.hops.juice").addStats(new FoodStats(1, 0.1F, true, drinksAlwaysDrinkable, emptyBottle));
        BOTTLE_DARK_BEER = addItem(113, "bottle.dark.beer").addStats(new FoodStats(4, 0.4F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 60), new RandomPotionEffect(MobEffects.STRENGTH, 300, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)));
        BOTTLE_DRAGON_BLOOD = addItem(114, "bottle.dragon.blood").setInvisible().addStats(new FoodStats(4, 0.4F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 300, 2, 90), new RandomPotionEffect(MobEffects.STRENGTH, 300, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5)));
        BOTTLE_WHEATY_JUICE = addItem(115, "bottle.wheaty.juice").addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle));
        BOTTLE_SCOTCH = addItem(116, "bottle.scotch").setInvisible().addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 400, 0, 60), new RandomPotionEffect(MobEffects.RESISTANCE, 400, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)));
        BOTTLE_GLEN_MCKENNER = addItem(117, "bottle.glen.mckenner").setInvisible().addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 400, 1, 90), new RandomPotionEffect(MobEffects.RESISTANCE, 400, 2, 90), new RandomPotionEffect(MobEffects.POISON, 200, 2, 10), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 5)));
        BOTTLE_WHEATY_HOPS_JUICE = addItem(118, "bottle.wheaty.hops.juice").addStats(new FoodStats(1, 0.1F, true, drinksAlwaysDrinkable, emptyBottle));
        BOTTLE_BEER = addItem(119, "bottle.beer").addStats(new FoodStats(6, 0.4F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 400, 0, 60), new RandomPotionEffect(MobEffects.HASTE, 400, 2, 60), new RandomPotionEffect(MobEffects.POISON, 100, 0, 5)));
        BOTTLE_CHILLY_SAUCE = addItem(120, "bottle.chilly.sauce").addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 1000, 0, 10), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 1000, 0, 60)));
        BOTTLE_HOT_SAUCE = addItem(121, "bottle.hot.sauce").addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 2000, 0, 30), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 2000, 0, 70)));
        BOTTLE_DIABOLO_SAUCE = addItem(122, "bottle.diabolo.sauce").setInvisible().addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 3000, 1, 50), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 3000, 0, 80)));
        BOTTLE_DIABLO_SAUCE = addItem(123, "bottle.diablo.sauce").setInvisible().addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 4000, 1, 70), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 4000, 0, 90)));
        BOTTLE_SNITCHES_GLITCH_SAUCE = addItem(124, "bottle.snitches.glitch.sauce").setInvisible().addStats(new FoodStats(2, 0.1F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 9999, 2, 999), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 9999, 9, 999)));
        BOTTLE_APPLE_JUICE = addItem(125, "bottle.apple.juice").addStats(new FoodStats(4, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.HUNGER, 400, 0, 20)));
        BOTTLE_CIDER = addItem(126, "bottle.cider").addStats(new FoodStats(4, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.NAUSEA, 400, 0, 60), new RandomPotionEffect(MobEffects.RESISTANCE, 400, 1, 60), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)));
        BOTTLE_GOLDEN_APPLE_JUICE = addItem(127, "bottle.golden.apple.juice").setInvisible().addStats(new FoodStats(4, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.HUNGER, 400, 0, 20), new RandomPotionEffect(MobEffects.ABSORPTION, 2400, 0, 100), new RandomPotionEffect(MobEffects.REGENERATION, 100, 1, 100)));
        BOTTLE_GOLDEN_CIDER = addItem(128, "bottle.golden.cider").setInvisible().addStats(new FoodStats(4, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.HUNGER, 400, 0, 60), new RandomPotionEffect(MobEffects.ABSORPTION, 2400, 1, 95), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)));
        BOTTLE_IDUNS_APPLE_JUICE = addItem(129, "bottle.iduns.apple.juice").setInvisible().addStats(new FoodStats(4, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.REGENERATION, 600, 4, 100), new RandomPotionEffect(MobEffects.ABSORPTION, 2400, 0, 100), new RandomPotionEffect(MobEffects.RESISTANCE, 6000, 0, 100), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0, 100)));
        BOTTLE_NOTCHES_BREW = addItem(130, "bottle.notches.brew").setInvisible().addStats(new FoodStats(4, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.REGENERATION, 700, 4, 95), new RandomPotionEffect(MobEffects.ABSORPTION, 3000, 1, 95), new RandomPotionEffect(MobEffects.RESISTANCE, 7000, 1, 95), new RandomPotionEffect(MobEffects.FIRE_RESISTANCE, 7000, 0, 95), new RandomPotionEffect(MobEffects.INSTANT_DAMAGE, 0, 2, 20)));
        BOTTLE_LEMON_JUICE = addItem(131, "bottle.lemon.juice").addStats(new FoodStats(2, 0.4F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.HASTE, 1200, 0, 60)));
        BOTTLE_LIMONCELLO = addItem(132, "bottle.limoncello").setInvisible().addStats(new FoodStats(2, 0.4F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.HASTE, 1200, 0, 90), new RandomPotionEffect(MobEffects.POISON, 200, 1, 5)));
        BOTTLE_LEMONADE = addItem(133, "bottle.lemonade").addStats(new FoodStats(4, 0.3F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.HASTE, 900, 1, 90)));
        BOTTLE_ALCOPOPS = addItem(134, "bottle.alcopops").setInvisible().addStats(new FoodStats(2, 0.2F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.HASTE, 900, 1, 90), new RandomPotionEffect(MobEffects.POISON, 300, 2, 20)));
        BOTTLE_CAVE_JOHNSONS_GRENADE_JUICE = addItem(135, "bottle.cave.johnsons.grenade.juice").setInvisible().addStats(new FoodStats(0, 0.0F, true, drinksAlwaysDrinkable, emptyBottle));
        BOTTLE_MILK = addItem(136, "bottle.milk").setUnificationData(OrePrefix.bottle, Materials.Milk).addStats(new FoodStats(0, 0.0F, true, drinksAlwaysDrinkable, emptyBottle));
        BOTTLE_HOLY_WATER = addItem(137, "bottle.holy.water").setUnificationData(OrePrefix.bottle, Materials.HolyWater).addStats(new FoodStats(0, 0.0F, true, drinksAlwaysDrinkable, emptyBottle, new RandomPotionEffect(MobEffects.POISON, 100, 1, 100)));

        FOOD_POTATO_ON_STICK = addItem(200, "food.potato.on.stick").addStats(new FoodStats(1, 0.3F, false, false, new ItemStack(Items.STICK, 1)));
        FOOD_POTATO_ON_STICK_ROASTED = addItem(201, "food.potato.on.stick.roasted").addStats(new FoodStats(6, 0.6F, false, false, new ItemStack(Items.STICK, 1)));
        FOOD_RAW_FRIES = addItem(202, "food.raw.fries").setMaxStackSize(16).addStats(new FoodStats(1, 0.3F));
        FOOD_FRIES = addItem(203, "food.fries").setMaxStackSize(16).addStats(new FoodStats(7, 0.5F));
        FOOD_PACKAGED_FRIES = addItem(204, "food.packaged.fries").addStats(new FoodStats(7, 0.5F, false, false, OreDictUnifier.get(OrePrefix.plate, Materials.Paper, 1)));
        FOOD_RAW_POTATOCHIPS = addItem(205, "food.raw.potatochips").setMaxStackSize(16).addStats(new FoodStats(1, 0.3F));
        FOOD_POTATOCHIPS = addItem(206, "food.potatochips").setMaxStackSize(16).addStats(new FoodStats(7, 0.5F));
        FOOD_CHILICHIPS = addItem(207, "food.chilichips").setMaxStackSize(16).addStats(new FoodStats(7, 0.6F));
        FOOD_PACKAGED_POTATOCHIPS = addItem(208, "food.packaged.potatochips").addStats(new FoodStats(7, 0.5F, false, false, OreDictUnifier.get(OrePrefix.foil, Materials.Aluminium, 1)));
        FOOD_PACKAGED_CHILICHIPS = addItem(209, "food.packaged.chilichips").addStats(new FoodStats(7, 0.6F, false, false, OreDictUnifier.get(OrePrefix.foil, Materials.Aluminium, 1)));
        FOOD_CHUM = addItem(210, "food.chum").addStats(new FoodStats(5, 0.2F, false, true, null, new RandomPotionEffect(MobEffects.HUNGER, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80)));
        FOOD_CHUM_ON_STICK = addItem(211, "food.chum.on.stick").addStats(new FoodStats(5, 0.2F, false, true, new ItemStack(Items.STICK, 1), new RandomPotionEffect(MobEffects.HUNGER, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80)));
        FOOD_DOUGH_SUGAR = addItem(212, "food.dough.sugar").addStats(new FoodStats(1, 0.1F));
        FOOD_DOUGH_CHOCOLATE = addItem(213, "food.dough.chocolate").addStats(new FoodStats(1, 0.1F));
        FOOD_RAW_COOKIE = addItem(214, "food.raw.cookie").addStats(new FoodStats(1, 0.1F));

        FOOD_SLICED_BUNS = addItem(220, "food.sliced.buns").addStats(new FoodStats(3, 0.5F));
        FOOD_BURGER_VEGGIE = addItem(221, "food.burger.veggie").addStats(new FoodStats(3, 0.5F));
        FOOD_BURGER_CHEESE = addItem(222, "food.burger.cheese").addStats(new FoodStats(3, 0.5F)).setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Cheese, 907200)));
        FOOD_BURGER_MEAT = addItem(223, "food.burger.meat").addStats(new FoodStats(3, 0.5F));
        FOOD_BURGER_CHUM = addItem(224, "food.burger.chum").addStats(new FoodStats(5, 0.2F, false, true, null, new RandomPotionEffect(MobEffects.SLOWNESS, 1000, 4, 100), new RandomPotionEffect(MobEffects.NAUSEA, 300, 1, 80)));

        FOOD_SLICED_BREADS = addItem(230, "food.sliced.breads").addStats(new FoodStats(5, 0.6F));
        FOOD_SANDWICH_VEGGIE = addItem(231, "food.sandwich.veggie").setMaxStackSize(32).addStats(new FoodStats(7, 0.6F));
        FOOD_SANDWICH_CHEESE = addItem(232, "food.sandwich.cheese").setMaxStackSize(32).addStats(new FoodStats(7, 0.6F));
        FOOD_SANDWICH_BACON = addItem(233, "food.sandwich.bacon").setMaxStackSize(32).addStats(new FoodStats(10, 0.8F));
        FOOD_SANDWICH_STEAK = addItem(234, "food.sandwich.steak").setMaxStackSize(32).addStats(new FoodStats(10, 0.8F));

        FOOD_SLICED_BAGUETTES = addItem(240, "food.sliced.baguettes").addStats(new FoodStats(8, 0.5F));
        FOOD_LARGE_SANDWICH_VEGGIE = addItem(241, "food.large.sandwich.veggie").setMaxStackSize(16).addStats(new FoodStats(15, 0.8F));
        FOOD_LARGE_SANDWICH_CHEESE = addItem(242, "food.large.sandwich.cheese").setMaxStackSize(16).addStats(new FoodStats(15, 0.8F));
        FOOD_LARGE_SANDWICH_BACON = addItem(243, "food.large.sandwich.bacon").setMaxStackSize(16).addStats(new FoodStats(20, 1.0F));
        FOOD_LARGE_SANDWICH_STEAK = addItem(244, "food.large.sandwich.steak").setMaxStackSize(16).addStats(new FoodStats(20, 1.0F));

        FOOD_RAW_PIZZA_VEGGIE = addItem(250, "food.raw.pizza.veggie").addStats(new FoodStats(1, 0.2F));
        FOOD_RAW_PIZZA_CHEESE = addItem(251, "food.raw.pizza.cheese").addStats(new FoodStats(2, 0.2F));
        FOOD_RAW_PIZZA_MEAT = addItem(252, "food.raw.pizza.meat").addStats(new FoodStats(2, 0.2F));

        FOOD_BAKED_PIZZA_VEGGIE = addItem(260, "food.baked.pizza.veggie").addStats(new FoodStats(3, 0.3F));
        FOOD_BAKED_PIZZA_CHEESE = addItem(261, "food.baked.pizza.cheese").addStats(new FoodStats(4, 0.4F));
        FOOD_BAKED_PIZZA_MEAT = addItem(262, "food.baked.pizza.meat").addStats(new FoodStats(5, 0.5F));

        DYE_INDIGO = addItem(410, "dye.indigo").addOreDict("dyeBlue");
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            DYE_ONLY_ITEMS[i] = addItem(414 + i, "dye." + EnumDyeColor.byMetadata(i).getUnlocalizedName()).addOreDict("dye" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, EnumDyeColor.byMetadata(i).getName()));
        }

        PLANK_OAK = addItem(470, "plank.oak").setBurnValue(75);
        PLANK_SPRUCE = addItem(471, "plank.spruce").setBurnValue(75);
        PLANK_BIRCH = addItem(472, "plank.birch").setBurnValue(75);
        PLANK_JUNGLE = addItem(473, "plank.jungle").setBurnValue(75);
        PLANK_ACACIA = addItem(474, "plank.acacia").setBurnValue(75);
        PLANK_DARKOAK = addItem(475, "plank.darkoak").setBurnValue(75);

        SFMIXTURE = addItem(270, "sfmixture");
        MSFMIXTURE = addItem(271, "msfmixture");

        CROP_DROP_PLUMBILIA = addItem(500, "crop.drop.plumbilia");
        CROP_DROP_ARGENTIA = addItem(501, "crop.drop.argentia");
        CROP_DROP_INDIGO = addItem(502, "crop.drop.indigo");
        CROP_DROP_FERRU = addItem(503, "crop.drop.ferru");
        CROP_DROP_AURELIA = addItem(504, "crop.drop.aurelia");
        CROP_DROP_TEALEAF = addItem(505, "crop.drop.tealeaf").addOreDict("cropTea");

        CROP_DROP_OIL_BERRY = addItem(510, "crop.drop.oil_berry");
        CROP_DROP_BOBS_YER_UNCLE_RANKS = addItem(511, "crop.drop.bobs_yer_uncle_ranks");
        CROP_DROP_UUM_BERRY = addItem(512, "crop.drop.uum_berry");
        CROP_DROP_UUA_BERRY = addItem(513, "crop.drop.uua_berry");

        CROP_DROP_MILK_WART = addItem(520, "crop.drop.milk_wart");

        CROP_DROP_COPPON = addItem(530, "crop.drop.coppon");
        CROP_DROP_TINE = addItem(540, "crop.drop.tine").setBurnValue(100);
        CROP_DROP_BAUXITE = addItem(521, "crop.drop.bauxite");
        CROP_DROP_ILMENITE = addItem(522, "crop.drop.ilmenite");
        CROP_DROP_PITCHBLENDE = addItem(523, "crop.drop.pitchblende");
        CROP_DROP_URANINITE = addItem(524, "crop.drop.uraninite");
        CROP_DROP_THORIUM = addItem(526, "crop.drop.thorium");
        CROP_DROP_NICKEL = addItem(527, "crop.drop.nickel");
        CROP_DROP_ZINC = addItem(528, "crop.drop.zinc");
        CROP_DROP_MANGANESE = addItem(529, "crop.drop.manganese");
        CROP_DROP_SCHEELITE = addItem(531, "crop.drop.scheelite");
        CROP_DROP_PLATINUM = addItem(532, "crop.drop.platinum");
        CROP_DROP_IRIDIUM = addItem(533, "crop.drop.iridium");
        CROP_DROP_OSMIUM = addItem(534, "crop.drop.osmium");
        CROP_DROP_NAQUADAH = addItem(535, "crop.drop.naquadah");

        CROP_DROP_CHILLY = addItem(550, "crop.drop.chilly").addOreDict("cropChilipepper").addStats(new FoodStats(1, 0.3F, false, false, null, new RandomPotionEffect(MobEffects.NAUSEA, 200, 1, 40)));
        CROP_DROP_LEMON = addItem(551, "crop.drop.lemon").addOreDict("cropLemon").addStats(new FoodStats(1, 0.3F));
        CROP_DROP_TOMATO = addItem(552, "crop.drop.tomato").addOreDict("cropTomato").addStats(new FoodStats(1, 0.2F));
        CROP_DROP_MTOMATO = addItem(553, "crop.drop.mtomato").addOreDict("cropTomato").addStats(new FoodStats(9, 1.0F, false, false, null, new RandomPotionEffect(MobEffects.REGENERATION, 100, 100, 100)));
        CROP_DROP_GRAPES = addItem(554, "crop.drop.grapes").addOreDict("cropGrape").addStats(new FoodStats(2, 0.3F));
        CROP_DROP_ONION = addItem(555, "crop.drop.onion").addOreDict("cropOnion").addStats(new FoodStats(2, 0.2F));
        CROP_DROP_CUCUMBER = addItem(556, "crop.drop.cucumber").addOreDict("cropCucumber").addStats(new FoodStats(1, 0.2F));

        FOOD_CHEESE = addItem(558, "food.cheese").addOreDict("foodCheese").addStats(new FoodStats(3, 0.6F));
        FOOD_DOUGH = addItem(559, "food.dough").addOreDict("foodDough").addStats(new FoodStats(1, 0.1F));
        FOOD_FLAT_DOUGH = addItem(560, "food.flat.dough").addStats(new FoodStats(1, 0.1F));
        FOOD_RAW_BREAD = addItem(561, "food.raw.bread").addStats(new FoodStats(1, 0.2F));
        FOOD_RAW_BUN = addItem(562, "food.raw.bun").addStats(new FoodStats(1, 0.1F));
        FOOD_RAW_BAGUETTE = addItem(563, "food.raw.baguette").addStats(new FoodStats(1, 0.3F));
        FOOD_BAKED_BUN = addItem(564, "food.baked.bun").addStats(new FoodStats(3, 0.5F));
        FOOD_BAKED_BAGUETTE = addItem(565, "food.baked.baguette").addStats(new FoodStats(8, 0.5F));
        FOOD_SLICED_BREAD = addItem(566, "food.sliced.bread").addStats(new FoodStats(2, 0.3F));
        FOOD_SLICED_BUN = addItem(567, "food.sliced.bun").addStats(new FoodStats(1, 0.3F));
        FOOD_SLICED_BAGUETTE = addItem(568, "food.sliced.baguette").addStats(new FoodStats(4, 0.3F));
        FOOD_RAW_CAKE = addItem(569, "food.raw.cake").addStats(new FoodStats(2, 0.2F));
        FOOD_BAKED_CAKE = addItem(570, "food.baked.cake").addStats(new FoodStats(3, 0.3F));
        FOOD_SLICED_LEMON = addItem(571, "food.sliced.lemon").addStats(new FoodStats(1, 0.075F));
        FOOD_SLICED_TOMATO = addItem(572, "food.sliced.tomato").addStats(new FoodStats(1, 0.05F));
        FOOD_SLICED_ONION = addItem(573, "food.sliced.onion").addStats(new FoodStats(1, 0.05F));
        FOOD_SLICED_CUCUMBER = addItem(574, "food.sliced.cucumber").addStats(new FoodStats(1, 0.05F));

        FOOD_SLICED_CHEESE = addItem(576, "food.sliced.cheese").addStats(new FoodStats(1, 0.1F));
    }

    public void registerRecipes() {

        // Plank recipes
        ModHandler.addShapedRecipe("plank_oak", PLANK_OAK.getStackForm(2),
            "s ",
            " P",
            'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 0));

        ModHandler.addShapedRecipe("plank_spruce", PLANK_SPRUCE.getStackForm(2),
            "s ",
            " P",
            'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 1));

        ModHandler.addShapedRecipe("plank_birch", PLANK_BIRCH.getStackForm(2),
            "s ",
            " P",
            'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 2));

        ModHandler.addShapedRecipe("plank_jungle", PLANK_JUNGLE.getStackForm(2),
            "s ",
            " P",
            'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 3));

        ModHandler.addShapedRecipe("plank_acacia", PLANK_ACACIA.getStackForm(2),
            "s ",
            " P",
            'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 4));

        ModHandler.addShapedRecipe("plank_darkoak", PLANK_DARKOAK.getStackForm(2),
            "s ",
            " P",
            'P', new ItemStack(Blocks.WOODEN_SLAB, 1, 5));

        // Dyes recipes
        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 0))
            .outputs(new ItemStack(Items.DYE, 2, 1))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 1))
            .outputs(new ItemStack(Items.DYE, 2, 12))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 2))
            .outputs(new ItemStack(Items.DYE, 2, 13))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 3))
            .outputs(new ItemStack(Items.DYE, 2, 7))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 4))
            .outputs(new ItemStack(Items.DYE, 2, 1))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 5))
            .outputs(new ItemStack(Items.DYE, 2, 14))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 6))
            .outputs(new ItemStack(Items.DYE, 2, 7))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 7))
            .outputs(new ItemStack(Items.DYE, 2, 9))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 1, 8))
            .outputs(new ItemStack(Items.DYE, 2, 7))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.YELLOW_FLOWER, 1, 0))
            .outputs(new ItemStack(Items.DYE, 2, 11))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0))
            .outputs(new ItemStack(Items.DYE, 3, 11))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1))
            .outputs(new ItemStack(Items.DYE, 3, 13))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4))
            .outputs(new ItemStack(Items.DYE, 3, 1))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5))
            .outputs(new ItemStack(Items.DYE, 3, 9))
            .buildAndRegister();

        // Crops recipes
        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_PLUMBILIA.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Lead, 1))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_ARGENTIA.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Silver, 1))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_INDIGO.getStackForm())
            .outputs(DYE_INDIGO.getStackForm())
            .buildAndRegister();

//        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
//            .inputs(CROP_DROP_MILK_WART.getStackForm())
//            .outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Milk))
//            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_COPPON.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Copper))
            .buildAndRegister();

        RecipeMap.EXTRACTOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_TINE.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Tin))
            .buildAndRegister();

        ItemStack plantBall = ModHandler.IC2.getIC2Item(ItemName.crafting, CraftingItemType.plant_ball, 1);

        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_COPPON.getStackForm(4))
            .outputs(new ItemStack(Blocks.WOOL, 1, 1))
            .buildAndRegister();

        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_PLUMBILIA.getStackForm(8))
            .outputs(plantBall.copy())
            .buildAndRegister();

        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_ARGENTIA.getStackForm(8))
            .outputs(plantBall.copy())
            .buildAndRegister();

        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_INDIGO.getStackForm(8))
            .outputs(plantBall.copy())
            .buildAndRegister();
        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_FERRU.getStackForm(8))
            .outputs(plantBall.copy())
            .buildAndRegister();
        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_AURELIA.getStackForm(8))
            .outputs(plantBall.copy())
            .buildAndRegister();
        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_OIL_BERRY.getStackForm(8))
            .outputs(plantBall.copy())
            .buildAndRegister();
        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_BOBS_YER_UNCLE_RANKS.getStackForm(8))
            .outputs(plantBall.copy())
            .buildAndRegister();
        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_TINE.getStackForm(4))
            .outputs(plantBall.copy())
            .buildAndRegister();
        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 8, W))
            .outputs(plantBall.copy())
            .buildAndRegister();
        RecipeMap.COMPRESSOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.RED_FLOWER, 8, W))
            .outputs(plantBall.copy())
            .buildAndRegister();

        // Misc
        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(FOOD_SLICED_CHEESE.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Cheese, 1))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()))
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Cocoa, 1))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(CROP_DROP_TINE.getStackForm())
            .outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.REEDS, 1))
            .outputs(new ItemStack(Items.SUGAR, 1))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.MELON_BLOCK, 1, 0))
            .outputs(new ItemStack(Items.MELON, 8, 0))
            .chancedOutput(new ItemStack(Items.MELON_SEEDS, 1), 8000)
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.PUMPKIN, 1, 0))
            .outputs(new ItemStack(Items.PUMPKIN_SEEDS, 4, 0))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.MELON, 1, 0))
            .outputs(new ItemStack(Items.MELON_SEEDS, 1, 0))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.WHEAT, 1, 0))
            .outputs(OreDictUnifier.get(OrePrefix.dust, Materials.Wheat, 1))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Items.STICK, 1))
            .outputs(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Wood, 2))
            .duration(400)
            .EUt(2)
            .buildAndRegister();

        RecipeMap.MACERATOR_RECIPES.recipeBuilder()
            .inputs(new ItemStack(Blocks.WOOL, 1, W))
            .outputs(new ItemStack(Items.STRING, 2))
            .chancedOutput(new ItemStack(Items.STRING, 1), 5000)
            .duration(400)
            .EUt(2)
            .buildAndRegister();
    }

    @Override
    public final ItemStack getContainerItem(ItemStack stack) {
        int damage = stack.getItemDamage();
        if (damage < metaItemOffset) {
            return ItemStack.EMPTY;
        }
        if (damage < metaItemOffset + 100) {
            return THERMOS_CAN_EMPTY.getStackForm();
        }
        if (damage < metaItemOffset + 200) {
            return new ItemStack(Items.GLASS_BOTTLE);
        }
        return ItemStack.EMPTY;
    }
}