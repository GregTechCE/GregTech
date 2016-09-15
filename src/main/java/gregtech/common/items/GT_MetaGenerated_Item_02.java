package gregtech.common.items;

import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.items.GT_MetaGenerated_Item_X32;
import gregtech.api.objects.GT_CopiedBlockTexture;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.*;
import gregtech.common.items.behaviors.Behaviour_Arrow;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class GT_MetaGenerated_Item_02 extends GT_MetaGenerated_Item_X32 {
    public static GT_MetaGenerated_Item_02 INSTANCE;
    private final static String aTextCover = "Usable as Cover"; private final static String aTextForestry = "Forestry";

    public GT_MetaGenerated_Item_02() {
        super("metaitem.02", new OrePrefixes[]{OrePrefixes.toolHeadSword, OrePrefixes.toolHeadPickaxe, OrePrefixes.toolHeadShovel, OrePrefixes.toolHeadAxe, OrePrefixes.toolHeadHoe, OrePrefixes.toolHeadHammer, OrePrefixes.toolHeadFile, OrePrefixes.toolHeadSaw, OrePrefixes.toolHeadDrill, OrePrefixes.toolHeadChainsaw, OrePrefixes.toolHeadWrench, OrePrefixes.toolHeadUniversalSpade, OrePrefixes.toolHeadSense, OrePrefixes.toolHeadPlow, OrePrefixes.toolHeadArrow, OrePrefixes.toolHeadBuzzSaw, OrePrefixes.turbineBlade, null, null, OrePrefixes.wireFine, OrePrefixes.gearGtSmall, OrePrefixes.rotor, OrePrefixes.stickLong, OrePrefixes.springSmall, OrePrefixes.spring, OrePrefixes.arrowGtWood, OrePrefixes.arrowGtPlastic, OrePrefixes.gemChipped, OrePrefixes.gemFlawed, OrePrefixes.gemFlawless, OrePrefixes.gemExquisite, OrePrefixes.gearGt});
        INSTANCE = this;

        int tLastID = 0;

        ItemList.ThermosCan_Dark_Coffee.set(addItem(tLastID = 0, "Dark Coffee", "Coffee, dark, without anything else", new Object[]{new GT_FoodStat(2, 0.2F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.moveSpeed.id, 400, 1, 70, Potion.digSpeed.id, 400, 1, 70}), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L)}));
        ItemList.ThermosCan_Dark_Cafe_au_lait.set(addItem(tLastID = 1, "Dark Coffee au lait", "Keeping you awake the whole night", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.2F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.moveSpeed.id, 400, 2, 90, Potion.digSpeed.id, 400, 2, 90}), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 3L)}));
        ItemList.ThermosCan_Coffee.set(addItem(tLastID = 2, "Coffee", "Just the regular morning Coffee", new Object[]{new GT_FoodStat(3, 0.4F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.moveSpeed.id, 400, 0, 50, Potion.digSpeed.id, 400, 0, 50}), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L)}));
        ItemList.ThermosCan_Cafe_au_lait.set(addItem(tLastID = 3, "Cafe au lait", "Sweet Coffee", new Object[]{new GT_FoodStat(3, 0.4F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.moveSpeed.id, 400, 1, 70, Potion.digSpeed.id, 400, 1, 70}), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L)}));
        ItemList.ThermosCan_Lait_au_cafe.set(addItem(tLastID = 4, "Lait au cafe", "You want Coffee to your Sugar?", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(3, 0.4F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.moveSpeed.id, 400, 2, 90, Potion.digSpeed.id, 400, 2, 90}), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 3L)}));
        ItemList.ThermosCan_Dark_Chocolate_Milk.set(addItem(tLastID = 5, "Dark Chocolate Milk", "A bit bitter, better add a bit Sugar", new Object[]{new GT_FoodStat(3, 0.4F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.regeneration.id, 50, 1, 60}), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)}));
        ItemList.ThermosCan_Chocolate_Milk.set(addItem(tLastID = 6, "Chocolate Milk", "Sweet Goodness", new Object[]{new GT_FoodStat(3, 0.4F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.regeneration.id, 50, 1, 90}), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 2L)}));
        ItemList.ThermosCan_Tea.set(addItem(tLastID = 7, "Tea", "Keep calm and carry on", new Object[]{new GT_FoodStat(2, 0.2F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.moveSlowdown.id, 300, 0, 50}), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2L)}));
        ItemList.ThermosCan_Sweet_Tea.set(addItem(tLastID = 8, "Sweet Tea", "How about a Tea Party? In Boston?", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.2F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2L)}));
        ItemList.ThermosCan_Ice_Tea.set(addItem(tLastID = 9, "Ice Tea", "Better than this purple Junk Drink from failed Potions", new Object[]{new GT_FoodStat(2, 0.2F, EnumAction.drink, ItemList.ThermosCan_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.moveSlowdown.id, 300, 0, 50}), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2L)}));

        ItemList.GelledToluene.set(addItem(tLastID = 10, "Gelled Toluene", "Raw Explosive", new Object[]{}));
        
        ItemList.Bottle_Purple_Drink.set(addItem(tLastID = 100, "Purple Drink", "How about Lemonade. Or some Ice Tea? I got Purple Drink!", new Object[]{new GT_FoodStat(8, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.moveSlowdown.id, 400, 1, 90}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VINCULUM, 1L)}));
        ItemList.Bottle_Grape_Juice.set(addItem(tLastID = 101, "Grape Juice", "This has a cleaning effect on your internals.", new Object[]{new GT_FoodStat(4, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.hunger.id, 400, 1, 60}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)}));
        ItemList.Bottle_Wine.set(addItem(tLastID = 102, "Wine", "Ordinary", new Object[]{new GT_FoodStat(2, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 400, 1, 60, Potion.heal.id, 0, 0, 60, Potion.poison.id, 200, 1, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)}));
        ItemList.Bottle_Vinegar.set(addItem(tLastID = 103, "Vinegar", "Exquisite", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 400, 1, 90, Potion.heal.id, 0, 1, 90, Potion.poison.id, 200, 2, 10, Potion.harm.id, 0, 2, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)}));
        ItemList.Bottle_Potato_Juice.set(addItem(tLastID = 104, "Potato Juice", "Ever seen Potato Juice in stores? No? That has a reason.", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(3, 0.3F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L)}));
        ItemList.Bottle_Vodka.set(addItem(tLastID = 105, "Vodka", "Not to confuse with Water", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 500, 0, 60, Potion.damageBoost.id, 500, 1, 60, Potion.poison.id, 200, 1, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L)}));
        ItemList.Bottle_Leninade.set(addItem(tLastID = 106, "Leninade", "Let the Communism flow through you!", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 500, 1, 90, Potion.damageBoost.id, 500, 2, 90, Potion.poison.id, 200, 2, 10, Potion.harm.id, 0, 2, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 2L)}));
        ItemList.Bottle_Mineral_Water.set(addItem(tLastID = 107, "Mineral Water", "The best Stuff you can drink to stay healthy", new Object[]{new GT_FoodStat(1, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.regeneration.id, 100, 1, 10}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)}));
        ItemList.Bottle_Salty_Water.set(addItem(tLastID = 108, "Salty Water", "Like Sea Water but less dirty", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(1, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.hunger.id, 400, 2, 95}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.TEMPESTAS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Bottle_Reed_Water.set(addItem(tLastID = 109, "Reed Water", "I guess this tastes better when fermented", new Object[]{new GT_FoodStat(1, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)}));
        ItemList.Bottle_Rum.set(addItem(tLastID = 110, "Rum", "A buddle o' rum", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(4, 0.4F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 300, 0, 60, Potion.damageBoost.id, 300, 1, 60, Potion.poison.id, 200, 1, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)}));
        ItemList.Bottle_Pirate_Brew.set(addItem(tLastID = 111, "Pirate Brew", "Set the Sails, we are going to Torrentuga!", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(4, 0.4F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 300, 1, 90, Potion.damageBoost.id, 300, 2, 90, Potion.poison.id, 200, 2, 10, Potion.harm.id, 0, 2, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 2L)}));
        ItemList.Bottle_Hops_Juice.set(addItem(tLastID = 112, "Hops Juice", "Every Beer has a start", new Object[]{new GT_FoodStat(1, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L)}));
        ItemList.Bottle_Dark_Beer.set(addItem(tLastID = 113, "Dark Beer", "Dark Beer, for the real Men", new Object[]{new GT_FoodStat(4, 0.4F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 300, 1, 60, Potion.damageBoost.id, 300, 1, 60, Potion.poison.id, 200, 1, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L)}));
        ItemList.Bottle_Dragon_Blood.set(addItem(tLastID = 114, "Dragon Blood", "FUS RO DAH!", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(4, 0.4F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 300, 2, 90, Potion.damageBoost.id, 300, 2, 90, Potion.poison.id, 200, 2, 10, Potion.harm.id, 0, 2, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L)}));
        ItemList.Bottle_Wheaty_Juice.set(addItem(tLastID = 115, "Wheaty Juice", "Is this liquefied Bread or what?", new Object[]{new GT_FoodStat(2, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L)}));
        ItemList.Bottle_Scotch.set(addItem(tLastID = 116, "Scotch", "Technically this is just a Whisky", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 400, 0, 60, Potion.resistance.id, 400, 1, 60, Potion.poison.id, 200, 1, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 1L)}));
        ItemList.Bottle_Glen_McKenner.set(addItem(tLastID = 117, "Glen McKenner", "Don't hand to easily surprised people, they will shatter it.", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 400, 1, 90, Potion.resistance.id, 400, 2, 90, Potion.poison.id, 200, 2, 10, Potion.harm.id, 0, 2, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 2L)}));
        ItemList.Bottle_Wheaty_Hops_Juice.set(addItem(tLastID = 118, "Wheaty Hops Juice", "Also known as 'Duff-Lite'", new Object[]{new GT_FoodStat(1, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 2L)}));
        ItemList.Bottle_Beer.set(addItem(tLastID = 119, "Beer", "Good old Beer", new Object[]{new GT_FoodStat(6, 0.4F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 400, 0, 60, Potion.digSpeed.id, 400, 2, 60, Potion.poison.id, 100, 0, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 1L)}));
        ItemList.Bottle_Chilly_Sauce.set(addItem(tLastID = 120, "Chilly Sauce", "Spicy", new Object[]{new GT_FoodStat(2, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 1000, 0, 10, Potion.fireResistance.id, 1000, 0, 60}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Bottle_Hot_Sauce.set(addItem(tLastID = 121, "Hot Sauce", "Very Spicy, I guess?", new Object[]{new GT_FoodStat(2, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 2000, 0, 30, Potion.fireResistance.id, 2000, 0, 70}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 2L)}));
        ItemList.Bottle_Diabolo_Sauce.set(addItem(tLastID = 122, "Diabolo Sauce", "As if the Devil made this Sauce", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 3000, 1, 50, Potion.fireResistance.id, 3000, 0, 80}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 3L)}));
        ItemList.Bottle_Diablo_Sauce.set(addItem(tLastID = 123, "Diablo Sauce", "Diablo always comes back!", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 4000, 1, 70, Potion.fireResistance.id, 4000, 0, 90}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 4L)}));
        ItemList.Bottle_Snitches_Glitch_Sauce.set(addItem(tLastID = 124, "Old Man Snitches glitched Diablo Sauce", "[Missing No]", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.1F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 9999, 2, 999, Potion.fireResistance.id, 9999, 9, 999}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 5L)}));
        ItemList.Bottle_Apple_Juice.set(addItem(tLastID = 125, "Apple Juice", "Made of the Apples from our best Oak Farms", new Object[]{new GT_FoodStat(4, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.hunger.id, 400, 0, 20}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)}));
        ItemList.Bottle_Cider.set(addItem(tLastID = 126, "Cider", "If you have nothing better to do with your Apples", new Object[]{new GT_FoodStat(4, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 400, 0, 60, Potion.resistance.id, 400, 1, 60, Potion.poison.id, 200, 1, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 1L)}));
        ItemList.Bottle_Golden_Apple_Juice.set(addItem(tLastID = 127, "Golden Apple Juice", "A golden Apple in liquid form", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(4, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.hunger.id, 400, 0, 20, Potion.field_76444_x.id, 2400, 0, 100, Potion.regeneration.id, 100, 1, 100}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)}));
        ItemList.Bottle_Golden_Cider.set(addItem(tLastID = 128, "Golden Cider", "More Resistance, less Regeneration", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(4, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.confusion.id, 400, 0, 60, Potion.field_76444_x.id, 2400, 1, 95, Potion.poison.id, 200, 1, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 1L)}));
        ItemList.Bottle_Iduns_Apple_Juice.set(addItem(tLastID = 129, "Idun's Apple Juice", "So you got the Idea of using Notch Apples for a drink?", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(4, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.regeneration.id, 600, 4, 100, Potion.field_76444_x.id, 2400, 0, 100, Potion.resistance.id, 6000, 0, 100, Potion.fireResistance.id, 6000, 0, 100}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 9L)}));
        ItemList.Bottle_Notches_Brew.set(addItem(tLastID = 130, "Notches Brew", "This is just overpowered", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(4, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.regeneration.id, 700, 4, 95, Potion.field_76444_x.id, 3000, 1, 95, Potion.resistance.id, 7000, 1, 95, Potion.fireResistance.id, 7000, 0, 95, Potion.harm.id, 0, 2, 20}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 9L)}));
        ItemList.Bottle_Lemon_Juice.set(addItem(tLastID = 131, "Lemon Juice", "Maybe adding Sugar will make it less sour", new Object[]{new GT_FoodStat(2, 0.4F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.digSpeed.id, 1200, 0, 60}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 1L)}));
        ItemList.Bottle_Limoncello.set(addItem(tLastID = 132, "Limoncello", "An alcoholic Drink which tastes like Lemons", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.4F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.digSpeed.id, 1200, 0, 90, Potion.poison.id, 200, 1, 5}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 1L)}));
        ItemList.Bottle_Lemonade.set(addItem(tLastID = 133, "Lemonade", "Cold and refreshing Lemonade", new Object[]{new GT_FoodStat(4, 0.3F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.digSpeed.id, 900, 1, 90}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERFODIO, 1L)}));
        ItemList.Bottle_Alcopops.set(addItem(tLastID = 134, "Alcopops", "Don't let your Children drink this junk!", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(2, 0.2F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.digSpeed.id, 900, 1, 90, Potion.poison.id, 300, 2, 20}), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VINCULUM, 1L)}));
        ItemList.Bottle_Cave_Johnsons_Grenade_Juice.set(addItem(tLastID = 135, "Cave Johnson's Grenade Juice", "When life gives you Lemons, make Life take them Lemons back!", new Object[]{SubTag.INVISIBLE, new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[0]).setExplosive(), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MORTUUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 1L)}));
        ItemList.Bottle_Milk.set(addItem(tLastID = 136, "Milk", "Got Milk?", new Object[]{OrePrefixes.bottle.get(Materials.Milk), new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[0]).setMilk(), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)}));
        ItemList.Bottle_Holy_Water.set(addItem(tLastID = 137, "Holy Water", "May the holy Planks be with you", new Object[]{OrePrefixes.bottle.get(Materials.HolyWater), new GT_FoodStat(0, 0.0F, EnumAction.drink, ItemList.Bottle_Empty.get(1L, new Object[0]), GregTech_API.sDrinksAlwaysDrinkable, false, false, new int[]{Potion.poison.id, 100, 1, 100}).setMilk(), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AURAM, 1L)}));

        
        
        ItemList.Food_Potato_On_Stick.set(addItem(tLastID = 200, "Potato on a Stick", "Totally looks like a Crab Claw", new Object[]{new GT_FoodStat(1, 0.3F, EnumAction.eat, new ItemStack(Items.stick, 1), false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        ItemList.Food_Potato_On_Stick_Roasted.set(addItem(tLastID = 201, "Roasted Potato on a Stick", "Still looks like a Crab Claw", new Object[]{new GT_FoodStat(6, 0.6F, EnumAction.eat, new ItemStack(Items.stick, 1), false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Raw_Fries.set(addItem(tLastID = 202, "Potato Strips", "It's Potato in Stripe Form", new Object[]{new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Fries.set(addItem(tLastID = 203, "Fries", "Not to confuse with Fry the Delivery Boy", new Object[]{new GT_FoodStat(7, 0.5F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Packaged_Fries.set(addItem(tLastID = 204, "Fries", "Ketchup not included", new Object[]{new GT_FoodStat(7, 0.5F, EnumAction.eat, GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Paper, 1L), false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Raw_PotatoChips.set(addItem(tLastID = 205, "Potato Chips (Raw)", "Just like a Potato", new Object[]{new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_PotatoChips.set(addItem(tLastID = 206, "Potato Chips", "Crunchy", new Object[]{new GT_FoodStat(7, 0.5F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_ChiliChips.set(addItem(tLastID = 207, "Chili Chips", "Spicy", new Object[]{new GT_FoodStat(7, 0.6F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Packaged_PotatoChips.set(addItem(tLastID = 208, "Bag of Potato Chips", "Full of delicious Air", new Object[]{new GT_FoodStat(7, 0.5F, EnumAction.eat, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 1L), false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Packaged_ChiliChips.set(addItem(tLastID = 209, "Bag of Chili Chips", "Stop making noises Baj!", new Object[]{new GT_FoodStat(7, 0.6F, EnumAction.eat, GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Aluminium, 1L), false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Chum.set(addItem(tLastID = 210, "Chum", "Chum is Fum!", new Object[]{new GT_FoodStat(5, 0.2F, EnumAction.eat, null, true, false, true, new int[]{Potion.hunger.id, 1000, 4, 100, Potion.confusion.id, 300, 1, 80}), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Chum_On_Stick.set(addItem(tLastID = 211, "Chum on a Stick", "Don't forget to try our Chum-balaya", new Object[]{new GT_FoodStat(5, 0.2F, EnumAction.eat, new ItemStack(Items.stick, 1), true, false, true, new int[]{Potion.hunger.id, 1000, 4, 100, Potion.confusion.id, 300, 1, 80}), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Dough_Sugar.set(addItem(tLastID = 212, "Sugary Dough", "Don't eat the Dough before it is baken", new Object[]{new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Food_Dough_Chocolate.set(addItem(tLastID = 213, "Chocolate Dough", "I said don't eat the Dough!", new Object[]{new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Food_Raw_Cookie.set(addItem(tLastID = 214, "Cookie shaped Dough", "For baking Cookies", new Object[]{new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));

        ItemList.Food_Sliced_Buns.set(addItem(tLastID = 220, "Buns", "Pre Sliced", new Object[]{new GT_FoodStat(3, 0.5F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Burger_Veggie.set(addItem(tLastID = 221, "Veggieburger", "No matter how you call this, this is NOT a Burger!", new Object[]{new GT_FoodStat(3, 0.5F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Burger_Cheese.set(addItem(tLastID = 222, "Cheeseburger", "Cheesy!", new Object[]{new GT_FoodStat(3, 0.5F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new ItemData(Materials.Cheese, 907200L, new MaterialStack[0])}));
        ItemList.Food_Burger_Meat.set(addItem(tLastID = 223, "Hamburger", "The Mc Burger Queen Burger", new Object[]{new GT_FoodStat(3, 0.5F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Burger_Chum.set(addItem(tLastID = 224, "Chumburger", "Fum is Chum!", new Object[]{new GT_FoodStat(5, 0.2F, EnumAction.eat, null, true, false, true, new int[]{Potion.hunger.id, 1000, 4, 100, Potion.confusion.id, 300, 1, 80}), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));

        ItemList.Food_Sliced_Breads.set(addItem(tLastID = 230, "Breads", "Pre Sliced", new Object[]{new GT_FoodStat(5, 0.6F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Sandwich_Veggie.set(addItem(tLastID = 231, "Veggie Sandwich", "Meatless", new Object[]{new GT_FoodStat(7, 0.6F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 32L);
        ItemList.Food_Sandwich_Cheese.set(addItem(tLastID = 232, "Cheese Sandwich", "Say Cheese!", new Object[]{new GT_FoodStat(7, 0.6F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 32L);
        ItemList.Food_Sandwich_Bacon.set(addItem(tLastID = 233, "Bacon Sandwich", "The best Sandwich ever!", new Object[]{new GT_FoodStat(10, 0.8F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 32L);
        ItemList.Food_Sandwich_Steak.set(addItem(tLastID = 234, "Steak Sandwich", "Not a 'Steam Sandwich'", new Object[]{new GT_FoodStat(10, 0.8F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 32L);

        ItemList.Food_Sliced_Baguettes.set(addItem(tLastID = 240, "Baguettes", "Pre Sliced", new Object[]{new GT_FoodStat(8, 0.5F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Large_Sandwich_Veggie.set(addItem(tLastID = 241, "Large Veggie Sandwich", "Just not worth it", new Object[]{new GT_FoodStat(15, 0.8F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Large_Sandwich_Cheese.set(addItem(tLastID = 242, "Large Cheese Sandwich", "I need another cheesy tooltip for this", new Object[]{new GT_FoodStat(15, 0.8F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Large_Sandwich_Bacon.set(addItem(tLastID = 243, "Large Bacon Sandwich", "For Men! (and manly Women)", new Object[]{new GT_FoodStat(20, 1.0F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);
        ItemList.Food_Large_Sandwich_Steak.set(addItem(tLastID = 244, "Large Steak Sandwich", "Yes, I once accidentially called it 'Steam Sandwich'", new Object[]{new GT_FoodStat(20, 1.0F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        setFluidContainerStats(32000 + tLastID, 0L, 16L);

        ItemList.Food_Raw_Pizza_Veggie.set(addItem(tLastID = 250, "Raw Veggie Pizza", "Into the Oven with it!", new Object[]{new GT_FoodStat(1, 0.2F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Raw_Pizza_Cheese.set(addItem(tLastID = 251, "Raw Cheese Pizza", "Into the Oven with it!", new Object[]{new GT_FoodStat(2, 0.2F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Raw_Pizza_Meat.set(addItem(tLastID = 252, "Raw Mince Meat Pizza", "Into the Oven with it!", new Object[]{new GT_FoodStat(2, 0.2F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));

        ItemList.Food_Baked_Pizza_Veggie.set(addItem(tLastID = 260, "Veggie Pizza", "The next they want is Gluten Free Pizzas...", new Object[]{new GT_FoodStat(3, 0.3F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Baked_Pizza_Cheese.set(addItem(tLastID = 261, "Cheese Pizza", "Pizza Magarita", new Object[]{new GT_FoodStat(4, 0.4F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Baked_Pizza_Meat.set(addItem(tLastID = 262, "Mince Meat Pizza", "Emo Pizza, it cuts itself!", new Object[]{new GT_FoodStat(5, 0.5F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.CORPUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));


        ItemList.Dye_Indigo.set(addItem(tLastID = 410, "Indigo Dye", "Blue Dye", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 1L), Dyes.dyeBlue}));
        for (byte i = 0; i < 16; i = (byte) (i + 1)) {
            ItemList.DYE_ONLY_ITEMS[i].set(addItem(tLastID = 414 + i, Dyes.get(i).mName + " Dye", "", new Object[]{Dyes.get(i).name(), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 1L)}));
        }
        ItemList.Plank_Oak.set(addItem(tLastID = 470, "Oak Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Spruce.set(addItem(tLastID = 471, "Spruce Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Birch.set(addItem(tLastID = 472, "Birch Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Jungle.set(addItem(tLastID = 473, "Jungle Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Acacia.set(addItem(tLastID = 474, "Acacia Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_DarkOak.set(addItem(tLastID = 475, "Dark Oak Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Larch.set(addItem(tLastID = 476, "Larch Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Teak.set(addItem(tLastID = 477, "Teak Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Acacia_Green.set(addItem(tLastID = 478, "Green Acacia Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Lime.set(addItem(tLastID = 479, "Lime Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Chestnut.set(addItem(tLastID = 480, "Chestnut Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Wenge.set(addItem(tLastID = 481, "Wenge Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Baobab.set(addItem(tLastID = 482, "Baobab Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Sequoia.set(addItem(tLastID = 483, "Sequoia Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Kapok.set(addItem(tLastID = 484, "Kapok Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Ebony.set(addItem(tLastID = 485, "Ebony Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Mahagony.set(addItem(tLastID = 486, "Mahagony Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Balsa.set(addItem(tLastID = 487, "Balsa Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Willow.set(addItem(tLastID = 488, "Willow Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Walnut.set(addItem(tLastID = 489, "Walnut Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Greenheart.set(addItem(tLastID = 490, "Greenheart Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Cherry.set(addItem(tLastID = 491, "Cherry Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Mahoe.set(addItem(tLastID = 492, "Mahoe Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Poplar.set(addItem(tLastID = 493, "Poplar Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Palm.set(addItem(tLastID = 494, "Palm Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Papaya.set(addItem(tLastID = 495, "Papaya Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Pine.set(addItem(tLastID = 496, "Pine Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Plum.set(addItem(tLastID = 497, "Plum Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Maple.set(addItem(tLastID = 498, "Maple Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);
        ItemList.Plank_Citrus.set(addItem(tLastID = 499, "Citrus Plank", aTextCover, new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 200);

        ItemList.SFMixture.set(addItem(tLastID = 270, "Super Fuel Binder", "Raw Material", new Object[]{}));
        ItemList.MSFMixture.set(addItem(tLastID = 271, "Magic Super Fuel Binder", "Raw Material", new Object[]{}));
        
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Oak.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), new ItemStack(Blocks.wooden_slab, 1, 0)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Spruce.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), new ItemStack(Blocks.wooden_slab, 1, 1)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Birch.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), new ItemStack(Blocks.wooden_slab, 1, 2)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Jungle.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), new ItemStack(Blocks.wooden_slab, 1, 3)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Acacia.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), new ItemStack(Blocks.wooden_slab, 1, 4)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_DarkOak.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), new ItemStack(Blocks.wooden_slab, 1, 5)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Larch.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs1", 1L, 0)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Teak.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs1", 1L, 1)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Acacia_Green.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs1", 1L, 2)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Lime.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs1", 1L, 3)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Chestnut.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs1", 1L, 4)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Wenge.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs1", 1L, 5)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Baobab.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs1", 1L, 6)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Sequoia.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs1", 1L, 7)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Kapok.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs2", 1L, 0)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Ebony.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs2", 1L, 1)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Mahagony.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs2", 1L, 2)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Balsa.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs2", 1L, 3)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Willow.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs2", 1L, 4)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Walnut.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs2", 1L, 5)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Greenheart.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs2", 1L, 6)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Cherry.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs2", 1L, 7)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Mahoe.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs3", 1L, 0)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Poplar.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs3", 1L, 1)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Palm.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs3", 1L, 2)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Papaya.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs3", 1L, 3)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Pine.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs3", 1L, 4)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Plum.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs3", 1L, 5)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Maple.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs3", 1L, 6)});
        GT_ModHandler.addCraftingRecipe(ItemList.Plank_Citrus.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"s ", " P", Character.valueOf('P'), GT_ModHandler.getModItem(aTextForestry, "slabs3", 1L, 7)});

        GregTech_API.registerCover(ItemList.Plank_Oak.get(1L, new Object[0]), new GT_CopiedBlockTexture(Blocks.planks, 0, 0), null);
        GregTech_API.registerCover(ItemList.Plank_Spruce.get(1L, new Object[0]), new GT_CopiedBlockTexture(Blocks.planks, 0, 1), null);
        GregTech_API.registerCover(ItemList.Plank_Birch.get(1L, new Object[0]), new GT_CopiedBlockTexture(Blocks.planks, 0, 2), null);
        GregTech_API.registerCover(ItemList.Plank_Jungle.get(1L, new Object[0]), new GT_CopiedBlockTexture(Blocks.planks, 0, 3), null);
        GregTech_API.registerCover(ItemList.Plank_Acacia.get(1L, new Object[0]), new GT_CopiedBlockTexture(Blocks.planks, 0, 4), null);
        GregTech_API.registerCover(ItemList.Plank_DarkOak.get(1L, new Object[0]), new GT_CopiedBlockTexture(Blocks.planks, 0, 5), null);
        GregTech_API.registerCover(ItemList.Plank_Larch.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 0, new ItemStack(Blocks.planks, 1, 0))), 0, 0), null);
        GregTech_API.registerCover(ItemList.Plank_Teak.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 1, new ItemStack(Blocks.planks, 1, 0))), 0, 1), null);
        GregTech_API.registerCover(ItemList.Plank_Acacia_Green.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 2, new ItemStack(Blocks.planks, 1, 0))), 0, 2), null);
        GregTech_API.registerCover(ItemList.Plank_Lime.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 3, new ItemStack(Blocks.planks, 1, 0))), 0, 3), null);
        GregTech_API.registerCover(ItemList.Plank_Chestnut.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 4, new ItemStack(Blocks.planks, 1, 0))), 0, 4), null);
        GregTech_API.registerCover(ItemList.Plank_Wenge.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 5, new ItemStack(Blocks.planks, 1, 0))), 0, 5), null);
        GregTech_API.registerCover(ItemList.Plank_Baobab.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 6, new ItemStack(Blocks.planks, 1, 0))), 0, 6), null);
        GregTech_API.registerCover(ItemList.Plank_Sequoia.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 7, new ItemStack(Blocks.planks, 1, 0))), 0, 7), null);
        GregTech_API.registerCover(ItemList.Plank_Kapok.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 8, new ItemStack(Blocks.planks, 1, 0))), 0, 8), null);
        GregTech_API.registerCover(ItemList.Plank_Ebony.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 9, new ItemStack(Blocks.planks, 1, 0))), 0, 9), null);
        GregTech_API.registerCover(ItemList.Plank_Mahagony.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 10, new ItemStack(Blocks.planks, 1, 0))), 0, 10), null);
        GregTech_API.registerCover(ItemList.Plank_Balsa.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 11, new ItemStack(Blocks.planks, 1, 0))), 0, 11), null);
        GregTech_API.registerCover(ItemList.Plank_Willow.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 12, new ItemStack(Blocks.planks, 1, 0))), 0, 12), null);
        GregTech_API.registerCover(ItemList.Plank_Walnut.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 13, new ItemStack(Blocks.planks, 1, 0))), 0, 13), null);
        GregTech_API.registerCover(ItemList.Plank_Greenheart.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 14, new ItemStack(Blocks.planks, 1, 0))), 0, 14), null);
        GregTech_API.registerCover(ItemList.Plank_Cherry.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks", 1L, 15, new ItemStack(Blocks.planks, 1, 0))), 0, 15), null);
        GregTech_API.registerCover(ItemList.Plank_Mahoe.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks2", 1L, 0, new ItemStack(Blocks.planks, 1, 0))), 0, 0), null);
        GregTech_API.registerCover(ItemList.Plank_Poplar.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks2", 1L, 1, new ItemStack(Blocks.planks, 1, 0))), 0, 1), null);
        GregTech_API.registerCover(ItemList.Plank_Palm.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks2", 1L, 2, new ItemStack(Blocks.planks, 1, 0))), 0, 2), null);
        GregTech_API.registerCover(ItemList.Plank_Papaya.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks2", 1L, 3, new ItemStack(Blocks.planks, 1, 0))), 0, 3), null);
        GregTech_API.registerCover(ItemList.Plank_Pine.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks2", 1L, 4, new ItemStack(Blocks.planks, 1, 0))), 0, 4), null);
        GregTech_API.registerCover(ItemList.Plank_Plum.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks2", 1L, 5, new ItemStack(Blocks.planks, 1, 0))), 0, 5), null);
        GregTech_API.registerCover(ItemList.Plank_Maple.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks2", 1L, 6, new ItemStack(Blocks.planks, 1, 0))), 0, 6), null);
        GregTech_API.registerCover(ItemList.Plank_Citrus.get(1L, new Object[0]), new GT_CopiedBlockTexture(GT_Utility.getBlockFromStack(GT_ModHandler.getModItem(aTextForestry, "planks2", 1L, 7, new ItemStack(Blocks.planks, 1, 0))), 0, 7), null);

        ItemList.Crop_Drop_Plumbilia.set(addItem(tLastID = 500, "Plumbilia Leaf", "Source of Lead", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 1L)}));
        ItemList.Crop_Drop_Argentia.set(addItem(tLastID = 501, "Argentia Leaf", "Source of Silver", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)}));
        ItemList.Crop_Drop_Indigo.set(addItem(tLastID = 502, "Indigo Blossom", "Used for making Blue Dye", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 1L)}));
        ItemList.Crop_Drop_Ferru.set(addItem(tLastID = 503, "Ferru Leaf", "Source of Iron", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L)}));
        ItemList.Crop_Drop_Aurelia.set(addItem(tLastID = 504, "Aurelia Leaf", "Source of Gold", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)}));
        ItemList.Crop_Drop_TeaLeaf.set(addItem(tLastID = 505, "Tea Leaf", "Source of Tea", new Object[]{"cropTea", new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)}));

        ItemList.Crop_Drop_OilBerry.set(addItem(tLastID = 510, "Oil Berry", "Oil in Berry form", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L)}));
        ItemList.Crop_Drop_BobsYerUncleRanks.set(addItem(tLastID = 511, "Bobs-Yer-Uncle-Berry", "Source of Emeralds", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)}));
        ItemList.Crop_Drop_UUMBerry.set(addItem(tLastID = 512, "UUM Berry", "UUM in Berry form", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L)}));
        ItemList.Crop_Drop_UUABerry.set(addItem(tLastID = 513, "UUA Berry", "UUA in Berry form", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L)}));

        ItemList.Crop_Drop_MilkWart.set(addItem(tLastID = 520, "Milk Wart", "Source of Milk", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 1L)}));

        ItemList.Crop_Drop_Coppon.set(addItem(tLastID = 530, "Coppon Fiber", "ORANGE WOOOOOOOL!!!", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 1L)}));

        ItemList.Crop_Drop_Tine.set(addItem(tLastID = 540, "Tine Twig", "Source of Tin", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ARBOR, 1L)}));
        setBurnValue(32000 + tLastID, 100);

        ItemList.Crop_Drop_Bauxite.set(addItem(tLastID = 521, "Bauxia Leaf", "Source of Aluminium", new Object[]{}));
        ItemList.Crop_Drop_Ilmenite.set(addItem(tLastID = 522, "Titania Leaf", "Source of Titanium", new Object[]{}));
        ItemList.Crop_Drop_Pitchblende.set(addItem(tLastID = 523, "Reactoria Leaf", "Source of Uranium", new Object[]{}));
        ItemList.Crop_Drop_Uraninite.set(addItem(tLastID = 524, "Uranium Leaf", "Source of Uranite", new Object[]{}));
        ItemList.Crop_Drop_Thorium.set(addItem(tLastID = 526, "Thunder Leaf", "Source of Thorium", new Object[]{}));
        ItemList.Crop_Drop_Nickel.set(addItem(tLastID = 527, "Nickelback Leaf", "Source of Nickel", new Object[]{}));
        ItemList.Crop_Drop_Zinc.set(addItem(tLastID = 528, "Galvania Leaf", "Source of Zinc", new Object[]{}));
        ItemList.Crop_Drop_Manganese.set(addItem(tLastID = 529, "Pyrolusium Leaf", "Source of Manganese", new Object[]{}));
        ItemList.Crop_Drop_Scheelite.set(addItem(tLastID = 531, "Scheelinium Leaf", "Source of Tungsten", new Object[]{}));
        ItemList.Crop_Drop_Platinum.set(addItem(tLastID = 532, "Platina Leaf", "Source of Platinum", new Object[]{}));
        ItemList.Crop_Drop_Iridium.set(addItem(tLastID = 533, "Quantaria Leaf", "Source of Iridium", new Object[]{}));
        ItemList.Crop_Drop_Osmium.set(addItem(tLastID = 534, "Quantaria Leaf", "Source of Osmium", new Object[]{}));
        ItemList.Crop_Drop_Naquadah.set(addItem(tLastID = 535, "Stargatium Leaf", "Source of Naquadah", new Object[]{}));
        
        ItemList.Crop_Drop_Chilly.set(addItem(tLastID = 550, "Chilly Pepper", "It is red and hot", new Object[]{"cropChilipepper", new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false, new int[]{Potion.confusion.id, 200, 1, 40}), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Crop_Drop_Lemon.set(addItem(tLastID = 551, "Lemon", "Don't make Lemonade", new Object[]{"cropLemon", new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Crop_Drop_Tomato.set(addItem(tLastID = 552, "Tomato", "Solid Ketchup", new Object[]{"cropTomato", new GT_FoodStat(1, 0.2F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Crop_Drop_MTomato.set(addItem(tLastID = 553, "Max Tomato", "Full Health in one Tomato", new Object[]{"cropTomato", new GT_FoodStat(9, 1.0F, EnumAction.eat, null, false, true, false, new int[]{Potion.regeneration.id, 100, 100, 100}), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SANO, 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Crop_Drop_Grapes.set(addItem(tLastID = 554, "Grapes", "Source of Wine", new Object[]{"cropGrape", new GT_FoodStat(2, 0.3F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Crop_Drop_Onion.set(addItem(tLastID = 555, "Onion", "Taking over the whole Taste", new Object[]{"cropOnion", new GT_FoodStat(2, 0.2F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Crop_Drop_Cucumber.set(addItem(tLastID = 556, "Cucumber", "Not a Sea Cucumber!", new Object[]{"cropCucumber", new GT_FoodStat(1, 0.2F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.MESSIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));

        ItemList.Food_Cheese.set(addItem(tLastID = 558, "Cheese", "Click the Cheese", new Object[]{"foodCheese", new GT_FoodStat(3, 0.6F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 2L)}));
        ItemList.Food_Dough.set(addItem(tLastID = 559, "Dough", "For making Breads", new Object[]{"foodDough", new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Food_Flat_Dough.set(addItem(tLastID = 560, "Flattened Dough", "For making Pizza", new Object[]{new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Food_Raw_Bread.set(addItem(tLastID = 561, "Dough", "In Bread Shape", new Object[]{new GT_FoodStat(1, 0.2F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Food_Raw_Bun.set(addItem(tLastID = 562, "Dough", "In Bun Shape", new Object[]{new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Food_Raw_Baguette.set(addItem(tLastID = 563, "Dough", "In Baguette Shape", new Object[]{new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Food_Baked_Bun.set(addItem(tLastID = 564, "Bun", "Do not teleport Bread!", new Object[]{new GT_FoodStat(3, 0.5F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Baked_Baguette.set(addItem(tLastID = 565, "Baguette", "I teleported nothing BUT Bread!!!", new Object[]{new GT_FoodStat(8, 0.5F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Sliced_Bread.set(addItem(tLastID = 566, "Sliced Bread", "Just half a Bread", new Object[]{new GT_FoodStat(2, 0.3F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Sliced_Bun.set(addItem(tLastID = 567, "Sliced Bun", "Just half a Bun", new Object[]{new GT_FoodStat(1, 0.3F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Sliced_Baguette.set(addItem(tLastID = 568, "Sliced Baguette", "Just half a Baguette", new Object[]{new GT_FoodStat(4, 0.3F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L)}));
        ItemList.Food_Raw_Cake.set(addItem(tLastID = 569, "Cake Bottom", "For making Cake", new Object[]{new GT_FoodStat(2, 0.2F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Food_Baked_Cake.set(addItem(tLastID = 570, "Baked Cake Bottom", "I know I promised you an actual Cake, but well...", new Object[]{new GT_FoodStat(3, 0.3F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));
        ItemList.Food_Sliced_Lemon.set(addItem(tLastID = 571, "Lemon Slice", "Ideal to put on your Drink", new Object[]{new GT_FoodStat(1, 0.075F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)}));
        ItemList.Food_Sliced_Tomato.set(addItem(tLastID = 572, "Tomato Slice", "Solid Ketchup", new Object[]{new GT_FoodStat(1, 0.05F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)}));
        ItemList.Food_Sliced_Onion.set(addItem(tLastID = 573, "Onion Slice", "ONIONS, UNITE!", new Object[]{new GT_FoodStat(1, 0.05F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)}));
        ItemList.Food_Sliced_Cucumber.set(addItem(tLastID = 574, "Cucumber Slice", "QUEWWW-CUMMM-BERRR!!!", new Object[]{new GT_FoodStat(1, 0.05F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.HERBA, 1L)}));

        ItemList.Food_Sliced_Cheese.set(addItem(tLastID = 576, "Cheese Slice", "ALIEN ATTACK!!!, throw the CHEEEEESE!!!", new Object[]{new GT_FoodStat(1, 0.1F, EnumAction.eat, null, false, true, false, new int[0]), new TC_Aspects.TC_AspectStack(TC_Aspects.FAMES, 1L)}));

        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 0), new ItemStack(Items.dye, 2, 1));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 1), new ItemStack(Items.dye, 2, 12));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 2), new ItemStack(Items.dye, 2, 13));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 3), new ItemStack(Items.dye, 2, 7));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 4), new ItemStack(Items.dye, 2, 1));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 5), new ItemStack(Items.dye, 2, 14));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 6), new ItemStack(Items.dye, 2, 7));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 7), new ItemStack(Items.dye, 2, 9));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.red_flower, 1, 8), new ItemStack(Items.dye, 2, 7));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.yellow_flower, 1, 0), new ItemStack(Items.dye, 2, 11));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.double_plant, 1, 0), new ItemStack(Items.dye, 3, 11));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.double_plant, 1, 1), new ItemStack(Items.dye, 3, 13));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.double_plant, 1, 4), new ItemStack(Items.dye, 3, 1));
        GT_ModHandler.addExtractionRecipe(new ItemStack(Blocks.double_plant, 1, 5), new ItemStack(Items.dye, 3, 9));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Plumbilia.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Lead, 1L));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Argentia.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Silver, 1L));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Indigo.get(1L, new Object[0]), ItemList.Dye_Indigo.get(1L, new Object[0]));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_MilkWart.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Milk, 1L));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Coppon.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Copper, 1L));
        GT_ModHandler.addExtractionRecipe(ItemList.Crop_Drop_Tine.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Tin, 1L));

        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Coppon.get(4L, new Object[0]), new ItemStack(Blocks.wool, 1, 1));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Plumbilia.get(8L, new Object[0]), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Argentia.get(8L, new Object[0]), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Indigo.get(8L, new Object[0]), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Ferru.get(8L, new Object[0]), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Aurelia.get(8L, new Object[0]), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_OilBerry.get(8L, new Object[0]), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_BobsYerUncleRanks.get(8L, new Object[0]), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
        GT_ModHandler.addCompressionRecipe(ItemList.Crop_Drop_Tine.get(4L, new Object[0]), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
        GT_ModHandler.addCompressionRecipe(new ItemStack(Blocks.red_flower, 8, 32767), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));
        GT_ModHandler.addCompressionRecipe(new ItemStack(Blocks.yellow_flower, 8, 32767), ItemList.IC2_PlantballCompressed.get(1L, new Object[0]));

        GT_ModHandler.addPulverisationRecipe(ItemList.Food_Sliced_Cheese.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Cheese, 1L));
        GT_ModHandler.addPulverisationRecipe(ItemList.Dye_Cocoa.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cocoa, 1L));
        GT_ModHandler.addPulverisationRecipe(ItemList.Crop_Drop_Tine.get(1L, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Wood, 2L));
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.reeds, 1), new ItemStack(Items.sugar, 1), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.melon_block, 1, 0), new ItemStack(Items.melon, 8, 0), new ItemStack(Items.melon_seeds, 1), 80, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.pumpkin, 1, 0), new ItemStack(Items.pumpkin_seeds, 4, 0), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.melon, 1, 0), new ItemStack(Items.melon_seeds, 1, 0), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.wheat, 1, 0), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wheat, 1L), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(GT_ModHandler.getIC2Item("crop", 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Wood, 1L), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Items.stick, 1), GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Wood, 2L), null, 0, false);
        GT_ModHandler.addPulverisationRecipe(new ItemStack(Blocks.wool, 1, 32767), new ItemStack(Items.string, 2), new ItemStack(Items.string, 1), 50, false);
        try {
            Object tCrop;
            GT_Utility.getField(tCrop = ic2.api.crops.Crops.instance.getCropList()[13], "mDrop").set(tCrop, ItemList.Crop_Drop_Ferru.get(1L, new Object[0]));
            GT_Utility.getField(tCrop = ic2.api.crops.Crops.instance.getCropList()[14], "mDrop").set(tCrop, ItemList.Crop_Drop_Aurelia.get(1L, new Object[0]));
        } catch (Throwable e) {
            if (GT_Values.D1) {
                e.printStackTrace(GT_Log.err);
            }
        }
        ItemList.Display_ITS_FREE.set(addItem(tLastID = 766, "ITS FREE", "(or at least almost free)", new Object[]{SubTag.INVISIBLE, new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)}));
    }

    public boolean onLeftClickEntity(ItemStack aStack, EntityPlayer aPlayer, Entity aEntity) {
        super.onLeftClickEntity(aStack, aPlayer, aEntity);
        int aDamage = aStack.getItemDamage();
        if ((aDamage >= 25000) && (aDamage < 27000)) {
            if (aDamage >= 26000) {
                return Behaviour_Arrow.DEFAULT_PLASTIC.onLeftClickEntity(this, aStack, aPlayer, aEntity);
            }
            return Behaviour_Arrow.DEFAULT_WOODEN.onLeftClickEntity(this, aStack, aPlayer, aEntity);
        }
        return false;
    }

    public boolean hasProjectile(SubTag aProjectileType, ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        return ((aDamage >= 25000) && (aDamage < 27000)) || (super.hasProjectile(aProjectileType, aStack));
    }

    public EntityArrow getProjectile(SubTag aProjectileType, ItemStack aStack, World aWorld, double aX, double aY, double aZ) {
        int aDamage = aStack.getItemDamage();
        if ((aDamage >= 25000) && (aDamage < 27000)) {
            if (aDamage >= 26000) {
                return Behaviour_Arrow.DEFAULT_PLASTIC.getProjectile(this, aProjectileType, aStack, aWorld, aX, aY, aZ);
            }
            return Behaviour_Arrow.DEFAULT_WOODEN.getProjectile(this, aProjectileType, aStack, aWorld, aX, aY, aZ);
        }
        return super.getProjectile(aProjectileType, aStack, aWorld, aX, aY, aZ);
    }

    public EntityArrow getProjectile(SubTag aProjectileType, ItemStack aStack, World aWorld, EntityLivingBase aEntity, float aSpeed) {
        int aDamage = aStack.getItemDamage();
        if ((aDamage >= 25000) && (aDamage < 27000)) {
            if (aDamage >= 26000) {
                return Behaviour_Arrow.DEFAULT_PLASTIC.getProjectile(this, aProjectileType, aStack, aWorld, aEntity, aSpeed);
            }
            return Behaviour_Arrow.DEFAULT_WOODEN.getProjectile(this, aProjectileType, aStack, aWorld, aEntity, aSpeed);
        }
        return super.getProjectile(aProjectileType, aStack, aWorld, aEntity, aSpeed);
    }

    public boolean isItemStackUsable(ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        Materials aMaterial = GregTech_API.sGeneratedMaterials[(aDamage % 1000)];
        if ((aDamage >= 25000) && (aDamage < 27000) && (aMaterial != null) && (aMaterial.mEnchantmentTools != null)) {
            Enchantment tEnchant = aMaterial.mEnchantmentTools == Enchantment.fortune ? Enchantment.looting : aMaterial.mEnchantmentTools;
            if (tEnchant.type == EnumEnchantmentType.weapon) {
                NBTTagCompound tNBT = GT_Utility.ItemNBT.getNBT(aStack);
                if (!tNBT.getBoolean("GT.HasBeenUpdated")) {
                    tNBT.setBoolean("GT.HasBeenUpdated", true);
                    GT_Utility.ItemNBT.setNBT(aStack, tNBT);
                    GT_Utility.ItemNBT.addEnchantment(aStack, tEnchant, aMaterial.mEnchantmentToolsLevel);
                }
            }
        }
        return super.isItemStackUsable(aStack);
    }

    public boolean doesShowInCreative(OrePrefixes aPrefix, Materials aMaterial, boolean aDoShowAllItems) {
        return (aDoShowAllItems) || (!aPrefix.name().startsWith("toolHead"));
    }

    public ItemStack onDispense(IBlockSource aSource, ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        if ((aDamage >= 25000) && (aDamage < 27000)) {
            if (aDamage >= 26000) {
                return Behaviour_Arrow.DEFAULT_PLASTIC.onDispense(this, aSource, aStack);
            }
            return Behaviour_Arrow.DEFAULT_WOODEN.onDispense(this, aSource, aStack);
        }
        return super.onDispense(aSource, aStack);
    }

    public final ItemStack getContainerItem(ItemStack aStack) {
        int aDamage = aStack.getItemDamage();
        if (aDamage < 32000) {
            return null;
        }
        if (aDamage < 32100) {
            return ItemList.ThermosCan_Empty.get(1L, new Object[0]);
        }
        if (aDamage < 32200) {
            return ItemList.Bottle_Empty.get(1L, new Object[0]);
        }
        return null;
    }
}
