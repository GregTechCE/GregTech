package gregtech.loaders.postload;

import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_BaseCrop;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GT_CropLoader
        implements Runnable {
    public void run() {
        GT_Log.out.println("GT_Mod: Register Crops to IC2.");
        try {
        	ItemStack[] tI = new ItemStack[]{ItemList.Crop_Drop_Indigo.get(4L, new Object[0]),ItemList.Crop_Drop_MilkWart.get(4L, new Object[0]),new ItemStack(Blocks.brown_mushroom, 4),new ItemStack(Blocks.red_mushroom, 4)};
            new GT_BaseCrop(124, "Indigo", "Eloraam", 				tI[0], 2, 4,    0, 1, 4, 1, 1, 0, 4, 0, new String[]{"Flower", "Color", "Ingredient"}, 									ItemList.Crop_Drop_Indigo.get(1L, new Object[0]), null);
            new GT_BaseCrop(125, "Flax", "Eloraam", 				null,  2, 4,    0, 1, 4, 1, 1, 2, 0, 1, new String[]{"Silk", "Vine", "Addictive"}, 										new ItemStack(Items.string, 1), null);
            new GT_BaseCrop(126, "Oilberries", "Spacetoad", 		null,  9, 4,    0, 1, 4, 6, 1, 2, 1,12, new String[]{"Fire", "Dark", "Reed", "Rotten", "Coal", "Oil"}, 					ItemList.Crop_Drop_OilBerry.get(1L, new Object[0]), null);
            new GT_BaseCrop(127, "Bobsyeruncleranks", "GenerikB", 	null, 11, 4,    0, 1, 4, 4, 0, 8, 2, 9, new String[]{"Shiny", "Vine", "Emerald", "Berylium", "Crystal"}, Materials.Emerald, ItemList.Crop_Drop_BobsYerUncleRanks.get(1L, new Object[0]), new ItemStack[]{new ItemStack(Items.emerald, 1)});
            new GT_BaseCrop(128, "Diareed", "Direwolf20", 			null, 12, 4,    0, 1, 4, 5, 0,10, 2,10, new String[]{"Fire", "Shiny", "Reed", "Coal", "Diamond", "Crystal"}, Materials.Diamond, GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Diamond, 1L), new ItemStack[]{new ItemStack(Items.diamond, 1)});
            new GT_BaseCrop(129, "Withereed", "CovertJaguar", 		null,  8, 4,    0, 1, 4, 2, 0, 4, 1, 3, new String[]{"Fire", "Undead", "Reed", "Coal", "Rotten", "Wither"}, Materials.Coal, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Coal, 1L), new ItemStack[]{new ItemStack(Items.coal, 1), new ItemStack(Items.coal, 1)});
            new GT_BaseCrop(130, "Blazereed", "Mr. Brain", 			null,  6, 4,    0, 1, 4, 0, 4, 1, 0, 0, new String[]{"Fire", "Blaze", "Reed", "Sulfur"}, 								new ItemStack(Items.blaze_powder, 1), new ItemStack[]{new ItemStack(Items.blaze_rod, 1)});
            new GT_BaseCrop(131, "Eggplant", "Link", 				null,  6, 3,  900, 2, 3, 0, 4, 1, 0, 0, new String[]{"Chicken", "Egg", "Edible", "Feather", "Flower", "Addictive"},		new ItemStack(Items.egg, 1), new ItemStack[]{new ItemStack(Items.chicken, 1), new ItemStack(Items.feather, 1), new ItemStack(Items.feather, 1), new ItemStack(Items.feather, 1)});
            new GT_BaseCrop(132, "Corium", "Gregorius Techneticies",null,  6, 4,    0, 1, 4, 0, 2, 3, 1, 0, new String[]{"Cow", "Silk", "Vine"}, 											new ItemStack(Items.leather, 1), null);
            new GT_BaseCrop(133, "Corpseplant", "Mr. Kenny", 		null,  5, 4,    0, 1, 4, 0, 2, 1, 0, 3, new String[]{"Toxic", "Undead", "Vine", "Edible", "Rotten"}, 					new ItemStack(Items.rotten_flesh, 1), new ItemStack[]{ItemList.Dye_Bonemeal.get(1L, new Object[0]), ItemList.Dye_Bonemeal.get(1L, new Object[0]), new ItemStack(Items.bone, 1)});
            new GT_BaseCrop(134, "Creeperweed", "General Spaz", 	null,  7, 4,    0, 1, 4, 3, 0, 5, 1, 3, new String[]{"Creeper", "Vine", "Explosive", "Fire", "Sulfur", "Saltpeter", "Coal"}, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gunpowder, 1L), null);
            new GT_BaseCrop(135, "Enderbloom", "RichardG", 			null, 10, 4,    0, 1, 4, 5, 0, 2, 1, 6, new String[]{"Ender", "Flower", "Shiny"}, 										GT_OreDictUnificator.get(OrePrefixes.dust, Materials.EnderPearl, 1L), new ItemStack[]{new ItemStack(Items.ender_pearl, 1), new ItemStack(Items.ender_pearl, 1), new ItemStack(Items.ender_eye, 1)});
            new GT_BaseCrop(136, "Meatrose", "VintageBeef", 		null,  7, 4, 1500, 1, 4, 0, 4, 1, 3, 0, new String[]{"Edible", "Flower", "Cow", "Fish", "Chicken", "Pig"}, 				new ItemStack(Items.dye, 1, 9), new ItemStack[]{new ItemStack(Items.beef, 1), new ItemStack(Items.porkchop, 1), new ItemStack(Items.chicken, 1), new ItemStack(Items.fish, 1)});
            new GT_BaseCrop(137, "Milkwart", "Mr. Brain", 			tI[1], 6, 3,  900, 1, 3, 0, 3, 0, 1, 0, new String[]{"Edible", "Milk", "Cow"}, 											ItemList.Crop_Drop_MilkWart.get(1L, new Object[0]), null);
            new GT_BaseCrop(138, "Slimeplant", "Neowulf", 			null,  6, 4,    0, 3, 4, 3, 0, 0, 0, 2, new String[]{"Slime", "Bouncy", "Sticky", "Bush"}, 								new ItemStack(Items.slime_ball, 1), null);
            new GT_BaseCrop(139, "Spidernip", "Mr. Kenny", 			null,  4, 4,  600, 1, 4, 2, 1, 4, 1, 3, new String[]{"Toxic", "Silk", "Spider", "Flower", "Ingredient", "Addictive"}, 	new ItemStack(Items.string, 1), new ItemStack[]{new ItemStack(Items.spider_eye, 1), new ItemStack(Blocks.web, 1)});
            new GT_BaseCrop(140, "Tearstalks", "Neowulf", 			null,  8, 4,    0, 1, 4, 1, 2, 0, 0, 0, new String[]{"Healing", "Nether", "Ingredient", "Reed", "Ghast"}, 				new ItemStack(Items.ghast_tear, 1), null);
            new GT_BaseCrop(141, "Tine", "Gregorius Techneticies", 	null,  5, 3,    0, 2, 3, 2, 0, 3, 0, 0, new String[]{"Shiny", "Metal", "Pine", "Tin", "Bush"}, Materials.Tin, 			ItemList.Crop_Drop_Tine.get(1L, new Object[0]), null);
            new GT_BaseCrop(142, "Coppon", "Mr. Brain", 			null,  6, 3,    0, 2, 3, 2, 0, 1, 1, 1, new String[]{"Shiny", "Metal", "Cotton", "Copper", "Bush"}, Materials.Copper, 	ItemList.Crop_Drop_Coppon.get(1L, new Object[0]), null);
            new GT_BaseCrop(143, "Brown Mushrooms", "Mr. Brain", 	tI[2], 1, 3,    0, 1, 3, 0, 2, 0, 0, 2, new String[]{"Edible", "Mushroom", "Ingredient"}, 								new ItemStack(Blocks.brown_mushroom, 1), null);
            new GT_BaseCrop(144, "Red Mushrooms", "Mr. Kenny", 		tI[3], 1, 3,    0, 1, 3, 0, 1, 3, 0, 2, new String[]{"Toxic", "Mushroom", "Ingredient"}, 								new ItemStack(Blocks.red_mushroom, 1), null);
            new GT_BaseCrop(145, "Argentia", "Eloraam", 			null,  7, 4,    0, 3, 4, 2, 0, 1, 0, 0, new String[]{"Shiny", "Metal", "Silver", "Reed"}, Materials.Silver, 			ItemList.Crop_Drop_Argentia.get(1L, new Object[0]), null);
            new GT_BaseCrop(146, "Plumbilia", "KingLemming", 		null,  6, 4,    0, 3, 4, 2, 0, 3, 1, 1, new String[]{"Heavy", "Metal", "Lead", "Reed"}, Materials.Lead, 				ItemList.Crop_Drop_Plumbilia.get(1L, new Object[0]), null);
            new GT_BaseCrop(147, "Steeleafranks", "Benimatic", 		null, 10, 4,    0, 1, 4, 3, 0, 7, 2, 8, new String[]{"Metal", "Vine", "Iron"}, 											GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steeleaf, 1L), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steeleaf, 1L)});
            new GT_BaseCrop(148, "Liveroots", "Benimatic", 			null,  8, 4,    0, 1, 4, 2, 0, 5, 2, 6, new String[]{"Wood", "Vine"}, 													GT_OreDictUnificator.get(OrePrefixes.dust, Materials.LiveRoot, 1L), new ItemStack[]{ItemList.TF_LiveRoot.get(1L, new Object[0])});

            new GT_BaseCrop(149, "Trollplant", "unknown", 			null,  6, 5, 1000, 1, 4, 0, 0, 5, 2, 8, new String[]{"Troll", "Bad", "Scrap"}, 											GT_OreDictUnificator.get(OrePrefixes.gem, Materials.FoolsRuby, 1L), new ItemStack[]{ItemList.IC2_Plantball.get(1, new Object[0]), ItemList.IC2_Scrap.get(1, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Plutonium241, 1L)});
            new GT_BaseCrop(150, "Lazulia", "unknown", 				null,  7, 4,    0, 2, 4, 4, 2, 5, 7, 4, new String[]{"Shiny", "Bad", "Crystal", "Lapis"}, Materials.Lapis, 				GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Lapis, 1L), null);
            new GT_BaseCrop(151, "Glowheat", "unknown", 			null, 10, 7,    0, 5, 7, 3, 3, 3, 5, 4, new String[]{"Light", "Shiny", "Crystal"}, 										GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Glowstone, 1L), null);
            new GT_BaseCrop(153, "Fertilia", "unknown", 			null,  3, 4,    0, 1, 4, 2, 3, 5, 4, 8, new String[]{"Growth", "Healing", "Flower"}, 									GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Calcite, 1L), new ItemStack[]{ItemList.IC2_Fertilizer.get(1, new Object[0]), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Apatite, 1L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Phosphate, 1L)});
            new GT_BaseCrop(154, "Bauxia", "unknown", 				null,  6, 3,    0, 2, 3, 5, 0, 2, 3, 3, new String[]{"Metal", "Aluminium", "Reed", "Aluminium"}, Materials.Aluminium, 	ItemList.Crop_Drop_Bauxite.get(1, new Object[0]), null);
            new GT_BaseCrop(155, "Titania", "unknown", 				null,  9, 3,    0, 2, 3, 5, 0, 3, 3, 1, new String[]{"Metal", "Heavy", "Reed", "Titanium"}, Materials.Titanium, 		ItemList.Crop_Drop_Ilmenite.get(1, new Object[0]), null);
            new GT_BaseCrop(156, "Reactoria", "unknown", 			null, 12, 4,    0, 2, 4, 4, 0, 1, 2, 1, new String[]{"Radioactive", "Metal", "Danger", "Uranium"}, Materials.Uranium, 	ItemList.Crop_Drop_Pitchblende.get(1, new Object[0]), new ItemStack[]{ItemList.Crop_Drop_Uraninite.get(1, new Object[0])});
            new GT_BaseCrop(157, "God of Thunder", "unknown", 		null,  9, 4,    0, 2, 4, 3, 0, 5, 1, 2, new String[]{"Radioactive", "Metal", "Coal", "Thorium"}, Materials.Thorium, 	GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Coal, 1L), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Coal, 1L), ItemList.Crop_Drop_Thorium.get(1, new Object[0])});
            new GT_BaseCrop(158, "Transformium", "unknown", 		null, 12, 4, 2500, 1, 4, 6, 2, 1, 6, 1, new String[]{"Transform", "Coal", "Reed"}, 										ItemList.Crop_Drop_UUABerry.get(1L, new Object[0]), new ItemStack[]{ItemList.Crop_Drop_UUABerry.get(1L, new Object[0]), ItemList.Crop_Drop_UUABerry.get(1L, new Object[0]), ItemList.Crop_Drop_UUABerry.get(1L, new Object[0]), ItemList.Crop_Drop_UUABerry.get(1L, new Object[0]), ItemList.Crop_Drop_UUMBerry.get(1L, new Object[0])});
            new GT_BaseCrop(159, "Starwart", "unknown", 			null, 12, 4, 4000, 1, 4, 2, 0, 0, 1, 0, new String[]{"Wither", "Nether", "Undead", "Netherstar"}, Materials.NetherStar, GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Coal, 1L), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Coal, 1L), new ItemStack(Items.skull, 1), new ItemStack(Items.skull, 1, 1), new ItemStack(Items.skull, 1, 1), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.NetherStar, 1L)});
            new GT_BaseCrop(160, "Zomplant", "unknown", 			null,  3, 4,    0, 1, 4, 1, 3, 4, 2, 6, new String[]{"Zombie", "Rotten", "Undead"}, 									new ItemStack(Items.rotten_flesh), null);
            new GT_BaseCrop(161, "Nickelback", "unknown", 			null,  5, 3,    0, 2, 3, 3, 0, 1, 2, 2, new String[]{"Metal", "Fire", "Alloy"}, Materials.Nickel, 						ItemList.Crop_Drop_Nickel.get(1, new Object[0]), null);
            new GT_BaseCrop(162, "Galvania", "unknown", 			null,  6, 3,    0, 2, 3, 3, 0, 2, 2, 3, new String[]{"Metal", "Alloy", "Bush"}, Materials.Zinc, 						ItemList.Crop_Drop_Zinc.get(1, new Object[0]), null);
            new GT_BaseCrop(163, "Evil Ore", "unknown", 			null,  8, 4,    0, 3, 4, 4, 0, 2, 1, 3, new String[]{"Crystal", "Fire", "Nether"}, 										GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.NetherQuartz, 1L), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherQuartz, 1L), GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.CertusQuartz, 1L),});
            new GT_BaseCrop(164, "Olivia", "unknown", 				null,  2, 4,    0, 3, 4, 1, 0, 1, 4, 0, new String[]{"Crystal", "Shiny", "Processing", "Olivine"}, Materials.Olivine, 	GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Olivine, 1L), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Olivine, 1L),});
            new GT_BaseCrop(165, "Sapphirum", "unknown", 			null,  4, 4,    0, 3, 4, 1, 0, 1, 5, 0, new String[]{"Crystal", "Shiny", "Metal", "Sapphire"}, Materials.Sapphire, 		GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Sapphire, 1L), new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Sapphire, 1L),});
            new GT_BaseCrop(166, "Pyrolusium", "unknown", 			null, 12, 3,    0, 2, 3, 1, 0, 1, 1, 0, new String[]{"Metal", "Clean", "Bush", "Manganese"}, Materials.Manganese, 		ItemList.Crop_Drop_Manganese.get(1, new Object[0]), null);
            new GT_BaseCrop(167, "Scheelinium", "unknown", 			null, 12, 3,    0, 2, 3, 3, 0, 1, 1, 0, new String[]{"Metal", "Hard", "Bush", "Tungsten"}, Materials.Tungsten, 			ItemList.Crop_Drop_Scheelite.get(1, new Object[0]), null);
            new GT_BaseCrop(168, "Platina", "unknown", 				null, 11, 4,    0, 1, 4, 3, 0, 0, 3, 0, new String[]{"Metal", "Shiny", "Reed", "Platinum"}, Materials.Platinum, 		ItemList.Crop_Drop_Platinum.get(1, new Object[0]), null);
            new GT_BaseCrop(169, "Quantaria", "unknown", 			null, 12, 4, 1000, 1, 4, 4, 0, 0, 1, 0, new String[]{"Metal", "Iridium", "Reed"}, Materials.Iridium, 		ItemList.Crop_Drop_Iridium.get(1, new Object[0]), new ItemStack[]{ItemList.Crop_Drop_Osmium.get(1, new Object[0])});
            new GT_BaseCrop(170, "Stargatium", "unknown", 			null, 12, 4, 1500, 1, 4, 4, 0, 0, 2, 0, new String[]{"Metal", "Heavy", "Alien", "Naquadah"}, Materials.Naquadah, 		GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Endstone, 1L), new ItemStack[]{ItemList.Crop_Drop_Naquadah.get(1, new Object[0])});
        } catch (Throwable e) {
            GT_Log.err.println("GT_Mod: Failed to register Crops to IC2.");
            e.printStackTrace(GT_Log.err);
        }
    }
}
