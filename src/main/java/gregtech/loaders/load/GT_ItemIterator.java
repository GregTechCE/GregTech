package gregtech.loaders.load;

import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.items.OreDictNames;
import gregtech.api.items.ToolDictNames;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.items.GenericItem;
import gregtech.api.items.ItemList;
import gregtech.api.util.GTLog;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import java.util.Iterator;

public class GT_ItemIterator
        implements Runnable {
    public void run() {
        GTLog.out.println("GT_Mod: Scanning for certain kinds of compatible Machineblocks.");
        ItemStack tStack2;
        ItemStack tStack;
        if (null != (tStack = GT_ModHandler.getRecipeOutput(tStack2 = OreDictionaryUnifier.get(OrePrefixes.ingot, Materials.Bronze, 1L), tStack2, tStack2, tStack2, null, tStack2, tStack2, tStack2, tStack2))) {
            GT_ModHandler.addPulverisationRecipe(tStack, OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Bronze, 8L), null, 0, false);
            GT_ModHandler.addSmeltingRecipe(tStack, OreDictionaryUnifier.get(OrePrefixes.ingot, Materials.Bronze, 8L));
        }
        if (null != (tStack = GT_ModHandler.getRecipeOutput(tStack2 = OreDictionaryUnifier.get(OrePrefixes.plate, Materials.Bronze, 1L), tStack2, tStack2, tStack2, null, tStack2, tStack2, tStack2, tStack2))) {
            OreDictionaryUnifier.registerOre(OreDictNames.craftingRawMachineTier00, tStack);
            GT_ModHandler.addPulverisationRecipe(tStack, OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Bronze, 8L), null, 0, false);
            GT_ModHandler.addSmeltingRecipe(tStack, OreDictionaryUnifier.get(OrePrefixes.ingot, Materials.Bronze, 8L));
        }
        ItemStack tStack3;
        if (null != (tStack = GT_ModHandler.getRecipeOutput(tStack2 = OreDictionaryUnifier.get(OrePrefixes.ingot, Materials.Iron, 1L), tStack3 = new ItemStack(Blocks.GLASS, 1, 0), tStack2, tStack3, OreDictionaryUnifier.get(OrePrefixes.ingot, Materials.Gold, 1L), tStack3, tStack2, tStack3, tStack2))) {
            GT_ModHandler.addPulverisationRecipe(tStack, OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Iron, 4L), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Gold, 1L), 0, false);
        }
        if (null != (tStack = GT_ModHandler.getRecipeOutput(tStack2 = OreDictionaryUnifier.get(OrePrefixes.ingot, Materials.Steel, 1L), tStack3 = new ItemStack(Blocks.GLASS, 1, 0), tStack2, tStack3, OreDictionaryUnifier.get(OrePrefixes.ingot, Materials.Gold, 1L), tStack3, tStack2, tStack3, tStack2))) {
            GT_ModHandler.addPulverisationRecipe(tStack, OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Steel, 4L), OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Gold, 1L), 0, false);
        }
        GTLog.out.println("GT_Mod: Registering various Tools to be usable on GregTech Machines");
        GregTech_API.registerScrewdriver(GT_ModHandler.getRecipeOutput(null, new ItemStack(Items.IRON_INGOT, 1), null, new ItemStack(Items.STICK, 1)));
        GregTech_API.registerScrewdriver(GT_ModHandler.getRecipeOutput(new ItemStack(Items.IRON_INGOT, 1), null, null, null, new ItemStack(Items.STICK, 1)));

        GTLog.out.println("GT_Mod: Adding Food Recipes to the Automatic Canning Machine. (also during the following Item Iteration)");
        GT_Values.RA.addCannerRecipe(new ItemStack(Items.ROTTEN_FLESH, 2, 32767), ItemList.IC2_Food_Can_Empty.get(1L, new Object[0]), ItemList.IC2_Food_Can_Spoiled.get(1L, new Object[0]), null, 200, 1);
        GT_Values.RA.addCannerRecipe(new ItemStack(Items.SPIDER_EYE, 2, 32767), ItemList.IC2_Food_Can_Empty.get(1L, new Object[0]), ItemList.IC2_Food_Can_Spoiled.get(1L, new Object[0]), null, 100, 1);
        GT_Values.RA.addCannerRecipe(ItemList.Food_Poisonous_Potato.get(2L, new Object[0]), ItemList.IC2_Food_Can_Empty.get(1L, new Object[0]), ItemList.IC2_Food_Can_Spoiled.get(1L, new Object[0]), null, 100, 1);
        GT_Values.RA.addCannerRecipe(new ItemStack(Items.CAKE, 1, 32767), ItemList.IC2_Food_Can_Empty.get(12L), ItemList.IC2_Food_Can_Filled.get(12L), null, 600, 1);
        GT_Values.RA.addCannerRecipe(new ItemStack(Items.MUSHROOM_STEW, 1, 32767), ItemList.IC2_Food_Can_Empty.get(6L), ItemList.IC2_Food_Can_Filled.get(6L), new ItemStack(Items.BOWL, 1), 300, 1);

        GTLog.out.println("GT_Mod: Scanning ItemList.");

        Iterator<Item> tIterator = Item.REGISTRY.iterator();
        while (tIterator.hasNext()) {
            Object tObject;
            if (((tObject = tIterator.next()) instanceof Item) && (!(tObject instanceof GenericItem))) {
                Item tItem = (Item) tObject;
                String tName;
                if ((tName = tItem.getUnlocalizedName()) != null) {
                    /*try {
                        if ((tItem instanceof IToolCrowbar)) {
                            if ((!tItem.isDamageable()) && (!GT_ModHandler.isElectricItem(new ItemStack(tItem, 1, 0)))) {
                                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "infiniteDurabilityRCCrowbars", false)) &&
                                        (GT_ModHandler.removeRecipeByOutput(new ItemStack(tItem, 1, 32767)))) {
                                    GTLog.out.println("GT_Mod: Removed infinite RC Crowbar: " + tName);
                                }
                            } else if (GregTech_API.registerCrowbar(new ItemStack(tItem, 1, 32767))) {
                                GTLog.out.println("GT_Mod: Registered valid RC Crowbar: " + tName);
                            }
                        }
                    } catch (Throwable e) {
                    }
                    try {
                        if ((tItem instanceof IToolWrench)) {
                            if ((!tItem.isDamageable()) && (!GT_ModHandler.isElectricItem(new ItemStack(tItem, 1, 0)))) {
                                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "infiniteDurabilityBCWrenches", false)) &&
                                        (GT_ModHandler.removeRecipeByOutput(new ItemStack(tItem, 1, 32767)))) {
                                    GTLog.out.println("GT_Mod: Removed infinite BC Wrench: " + tName);
                                }
                            } else if (GregTech_API.registerWrench(new ItemStack(tItem, 1, 32767))) {
                                GTLog.out.println("GT_Mod: Registered valid BC Wrench: " + tName);
                            }
                        }
                    } catch (Throwable e) {
                    }*/
                    Block tBlock = GT_Utility.getBlockFromStack(new ItemStack(tItem, 1, 0));
                    if (tBlock != null) {
                        if (tName.endsWith("beehives")) {
                            tBlock.setHarvestLevel("scoop", 0);
                            gregtech.common.tools.GT_Tool_Scoop.sBeeHiveMaterial = tBlock.getMaterial(tBlock.getDefaultState());
                        }
                    }
                    if (((tItem instanceof ItemFood)) && (tItem != ItemList.IC2_Food_Can_Filled.getItem()) && (tItem != ItemList.IC2_Food_Can_Spoiled.getItem())) {
                        int tFoodValue = ((ItemFood) tItem).getHealAmount(new ItemStack(tItem, 1, 0));
                        if (tFoodValue > 0) {
                            GT_Values.RA.addCannerRecipe(new ItemStack(tItem, 1, 32767), ItemList.IC2_Food_Can_Empty.get(tFoodValue, new Object[0]), ItemList.IC2_Food_Can_Filled.get(tFoodValue, new Object[0]), GT_Utility.getContainerItem(new ItemStack(tItem, 1, 0), true), tFoodValue * 100, 1);
                        }
                    }
                    if (tItem instanceof IFluidContainerItem) {
                        OreDictionaryUnifier.addToBlacklist(new ItemStack(tItem, 1, 32767));
                    }
                    if ((tName.equals("item.ItemSensorLocationCard")) || (tName.equals("item.ItemEnergySensorLocationCard")) || (tName.equals("item.ItemEnergyArrayLocationCard")) || (tName.equals("item.ItemTextCard"))) {
                        GT_Values.RA.addAssemblerRecipe(new ItemStack(tItem, 1, 32767), null, ItemList.Circuit_Basic.get(2L, new Object[0]), 200, 32);
                    }
                    if (tName.equals("item.ItemTimeCard")) {
                        GT_Values.RA.addAssemblerRecipe(new ItemStack(tItem, 1, 32767), null, ItemList.Circuit_Basic.get(1L, new Object[0]), 100, 32);
                    }
                    if (tName.equals("tile.ArsMagica:ore_vinteum")) {
                        OreDictionaryUnifier.set(OrePrefixes.ore, Materials.Vinteum, new ItemStack(tItem, 1, 0));
                    }
                    if (tName.equals("item.ArsMagica:purified_vinteum")) {
                        GT_Values.RA.addFuel(new ItemStack(tItem, 1, 0), null, 256, 5);
                    }
                    if ((tName.equals("item.fieryBlood")) || (tName.equals("item.fieryTears"))) {
                        GT_Values.RA.addFuel(new ItemStack(tItem, 1, 0), null, 2048, 5);
                    }
                    if (tName.equals("tile.TFRoots")) {
                        GT_ModHandler.addPulverisationRecipe(new ItemStack(tItem, 1, 0), new ItemStack(Items.STICK, 2), new ItemStack(Items.STICK, 1), 30);
                        GT_ModHandler.addSawmillRecipe(new ItemStack(tItem, 1, 0), new ItemStack(Items.STICK, 4), new ItemStack(Items.STICK, 2));
                        GT_Values.RA.addFuel(new ItemStack(tItem, 1, 1), new ItemStack(Items.STICK, 4), 32, 5);
                    }
                    if (tName.equals("item.tconstruct.manual")) {
                        OreDictionaryUnifier.registerOre("bookTinkersManual", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.ArsMagica:spell_parchment")) {
                        OreDictionaryUnifier.registerOre("paperArsSpellParchment", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.ArsMagica:spell_recipe")) {
                        OreDictionaryUnifier.registerOre("paperArsSpellRecipe", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.ArsMagica:spell_book")) {
                        OreDictionaryUnifier.registerOre("bookArsSpells", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.myst.page")) {
                        OreDictionaryUnifier.registerOre("paperMystcraft", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.myst.agebook")) {
                        OreDictionaryUnifier.registerOre("bookMystcraftAge", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.myst.linkbook")) {
                        OreDictionaryUnifier.registerOre("bookMystcraftLink", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.myst.notebook")) {
                        OreDictionaryUnifier.registerOre("bookNotes", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.itemManuelBook")) {
                        OreDictionaryUnifier.registerOre("bookWritten", new ItemStack(tItem, 1, 0));
                    }
                    if (tName.equals("item.blueprintItem")) {
                        OreDictionaryUnifier.registerOre("paperBlueprint", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.ccprintout")) {
                        OreDictionaryUnifier.registerOre("paperWritten", new ItemStack(tItem, 1, 0));
                        OreDictionaryUnifier.registerOre("paperWritten", new ItemStack(tItem, 1, 1));
                        OreDictionaryUnifier.registerOre("bookWritten", new ItemStack(tItem, 1, 2));
                    }
                    if (tName.equals("item.blueprintItem")) {
                        OreDictionaryUnifier.registerOre("paperBlueprint", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.wirelessmap")) {
                        OreDictionaryUnifier.registerOre("paperMap", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.ItemResearchNotes")) {
                        OreDictionaryUnifier.registerOre("paperResearch", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.ItemThaumonomicon")) {
                        OreDictionaryUnifier.registerOre("bookThaumonomicon", new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("item.ligniteCoal")) {
                        OreDictionaryUnifier.set(OrePrefixes.gem, Materials.Lignite, new ItemStack(tItem, 1, 0));
                    }
                    if ((tName.equals("tile.extrabiomes.redrock")) || (tName.equals("tile.bop.redRocks"))) {
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Redrock, new ItemStack(tItem, 1, 0));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Redrock, new ItemStack(tItem, 1, 1));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Redrock, new ItemStack(tItem, 1, 2));
                    }
                    if (tName.equals("tile.rpstone")) {
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(tItem, 1, 0));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 1));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(tItem, 1, 2));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 3));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 4));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 5));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 6));
                    }
                    if (/**(tName.equals("tile.sedimentaryStone")) ||**/ ((tName.equals("tile.igneousStone")) || (tName.equals("tile.igneousStoneBrick")) || (tName.equals("tile.igneousCobblestone")))) {
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(tItem, 1, 0));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(tItem, 1, 1));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Rhyolite, new ItemStack(tItem, 1, 2));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Andesite, new ItemStack(tItem, 1, 3));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Gabbro, new ItemStack(tItem, 1, 4));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 5));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Komatiite, new ItemStack(tItem, 1, 6));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Dacite, new ItemStack(tItem, 1, 7));

                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(tItem, 1, 8));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(tItem, 1, 9));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Rhyolite, new ItemStack(tItem, 1, 10));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Andesite, new ItemStack(tItem, 1, 11));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Gabbro, new ItemStack(tItem, 1, 12));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 13));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Komatiite, new ItemStack(tItem, 1, 14));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Dacite, new ItemStack(tItem, 1, 15));
                    }
                    if ((tName.equals("tile.metamorphicStone")) || (tName.equals("tile.metamorphicStoneBrick")) || (tName.equals("tile.metamorphicCobblestone"))) {
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Gneiss, new ItemStack(tItem, 1, 0));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Eclogite, new ItemStack(tItem, 1, 1));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(tItem, 1, 2));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Quartzite, new ItemStack(tItem, 1, 3));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Blueschist, new ItemStack(tItem, 1, 4));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Greenschist, new ItemStack(tItem, 1, 5));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Soapstone, new ItemStack(tItem, 1, 6));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Migmatite, new ItemStack(tItem, 1, 7));

                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Gneiss, new ItemStack(tItem, 1, 8));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Eclogite, new ItemStack(tItem, 1, 9));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(tItem, 1, 10));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Quartzite, new ItemStack(tItem, 1, 11));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Blueschist, new ItemStack(tItem, 1, 12));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Greenschist, new ItemStack(tItem, 1, 13));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Soapstone, new ItemStack(tItem, 1, 14));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Migmatite, new ItemStack(tItem, 1, 15));
                    }
                    if (tName.equals("tile.blockCosmeticSolid")) {
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Obsidian, new ItemStack(tItem, 1, 0));
                        OreDictionaryUnifier.registerOre(OrePrefixes.stone, Materials.Obsidian, new ItemStack(tItem, 1, 1));
                        OreDictionaryUnifier.registerOre(OrePrefixes.block, Materials.Thaumium, new ItemStack(tItem, 1, 4));
                    }
                    if (tName.equals("tile.enderchest")) {
                        OreDictionaryUnifier.registerOre(OreDictNames.enderChest, new ItemStack(tItem, 1, 32767));
                    }
                    if (tName.equals("tile.autoWorkbenchBlock")) {
                        OreDictionaryUnifier.registerOre(OreDictNames.craftingWorkBench, new ItemStack(tItem, 1, 0));
                    }
                    if (tName.equals("tile.pumpBlock")) {
                        OreDictionaryUnifier.registerOre(OreDictNames.craftingPump, new ItemStack(tItem, 1, 0));
                        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "BCPump", false)) {
                            GT_ModHandler.removeRecipeByOutput(new ItemStack(tItem, 1, 0));
                        }
                    }
                    if (tName.equals("tile.tankBlock")) {
                        OreDictionaryUnifier.registerOre(OreDictNames.craftingTank, new ItemStack(tItem, 1, 0));
                    }
                    if (tName.equals("item.drawplateDiamond")) {
                        OreDictionaryUnifier.registerOre(ToolDictNames.craftingToolDrawplate, new ItemStack(tItem, 1, 32767));
                    }
                }
            }
        }
    }
}