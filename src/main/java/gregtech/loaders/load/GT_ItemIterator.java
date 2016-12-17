package gregtech.loaders.load;

import buildcraft.api.tools.IToolWrench;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.items.GT_Generic_Item;
import gregtech.api.util.GT_Log;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import mods.railcraft.api.core.items.IToolCrowbar;
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
        GT_Log.out.println("GT_Mod: Scanning for certain kinds of compatible Machineblocks.");
        ItemStack tStack2;
        ItemStack tStack;
        if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[]{tStack2 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 1L), tStack2, tStack2, tStack2, null, tStack2, tStack2, tStack2, tStack2}))) {
            GT_ModHandler.addPulverisationRecipe(tStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 8L), null, 0, false);
            GT_ModHandler.addSmeltingRecipe(tStack, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 8L));
        }
        if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[]{tStack2 = GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Bronze, 1L), tStack2, tStack2, tStack2, null, tStack2, tStack2, tStack2, tStack2}))) {
            GT_OreDictUnificator.registerOre(OreDictNames.craftingRawMachineTier00, tStack);
            GT_ModHandler.addPulverisationRecipe(tStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 8L), null, 0, false);
            GT_ModHandler.addSmeltingRecipe(tStack, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, 8L));
        }
        ItemStack tStack3;
        if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[]{tStack2 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iron, 1L), tStack3 = new ItemStack(Blocks.glass, 1, 0), tStack2, tStack3, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L), tStack3, tStack2, tStack3, tStack2}))) {
            GT_ModHandler.addPulverisationRecipe(tStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Iron, 4L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gold, 1L), 0, false);
        }
        if (null != (tStack = GT_ModHandler.getRecipeOutput(new ItemStack[]{tStack2 = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Steel, 1L), tStack3 = new ItemStack(Blocks.glass, 1, 0), tStack2, tStack3, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Gold, 1L), tStack3, tStack2, tStack3, tStack2}))) {
            GT_ModHandler.addPulverisationRecipe(tStack, GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 4L), GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Gold, 1L), 0, false);
        }
        GT_Log.out.println("GT_Mod: Registering various Tools to be usable on GregTech Machines");
        GregTech_API.registerScrewdriver(GT_ModHandler.getRecipeOutput(new ItemStack[]{null, new ItemStack(Items.iron_ingot, 1), null, new ItemStack(Items.stick, 1)}));
        GregTech_API.registerScrewdriver(GT_ModHandler.getRecipeOutput(new ItemStack[]{new ItemStack(Items.iron_ingot, 1), null, null, null, new ItemStack(Items.stick, 1)}));

        GT_Log.out.println("GT_Mod: Adding Food Recipes to the Automatic Canning Machine. (also during the following Item Iteration)");
        GT_Values.RA.addCannerRecipe(new ItemStack(Items.rotten_flesh, 2, 32767), ItemList.IC2_Food_Can_Empty.get(1L, new Object[0]), ItemList.IC2_Food_Can_Spoiled.get(1L, new Object[0]), null, 200, 1);
        GT_Values.RA.addCannerRecipe(new ItemStack(Items.spider_eye, 2, 32767), ItemList.IC2_Food_Can_Empty.get(1L, new Object[0]), ItemList.IC2_Food_Can_Spoiled.get(1L, new Object[0]), null, 100, 1);
        GT_Values.RA.addCannerRecipe(ItemList.Food_Poisonous_Potato.get(2L, new Object[0]), ItemList.IC2_Food_Can_Empty.get(1L, new Object[0]), ItemList.IC2_Food_Can_Spoiled.get(1L, new Object[0]), null, 100, 1);
        GT_Values.RA.addCannerRecipe(new ItemStack(Items.cake, 1, 32767), ItemList.IC2_Food_Can_Empty.get(12L, new Object[0]), ItemList.IC2_Food_Can_Filled.get(12L, new Object[0]), null, 600, 1);
        GT_Values.RA.addCannerRecipe(new ItemStack(Items.mushroom_stew, 1, 32767), ItemList.IC2_Food_Can_Empty.get(6L, new Object[0]), ItemList.IC2_Food_Can_Filled.get(6L, new Object[0]), new ItemStack(Items.bowl, 1), 300, 1);

        GT_Log.out.println("GT_Mod: Scanning ItemList.");

        try {
            Iterator tIterator = Item.itemRegistry.iterator();
            while (tIterator.hasNext()) {
                Object tObject;
                if (((tObject = tIterator.next()) instanceof Item) && (!(tObject instanceof GT_Generic_Item))) {
                    Item tItem = (Item) tObject;
                    String tName;
                    if ((tName = tItem.getUnlocalizedName()) != null) {
                        if ((tItem instanceof IToolCrowbar)) {
                            if ((!tItem.isDamageable()) && (!GT_ModHandler.isElectricItem(new ItemStack(tItem, 1, 0)))) {
                                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "infiniteDurabilityRCCrowbars", false)) &&
                                        (GT_ModHandler.removeRecipeByOutput(new ItemStack(tItem, 1, 32767)))) {
                                    GT_Log.out.println("GT_Mod: Removed infinite RC Crowbar: " + tName);
                                }
                            } else if (GregTech_API.registerCrowbar(new ItemStack(tItem, 1, 32767))) {
                                GT_Log.out.println("GT_Mod: Registered valid RC Crowbar: " + tName);
                            }
                        }
                        if ((tItem instanceof IToolWrench)) {
                            if ((!tItem.isDamageable()) && (!GT_ModHandler.isElectricItem(new ItemStack(tItem, 1, 0)))) {
                                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "infiniteDurabilityBCWrenches", false)) &&
                                        (GT_ModHandler.removeRecipeByOutput(new ItemStack(tItem, 1, 32767)))) {
                                    GT_Log.out.println("GT_Mod: Removed infinite BC Wrench: " + tName);
                                }
                            } else if (GregTech_API.registerWrench(new ItemStack(tItem, 1, 32767))) {
                                GT_Log.out.println("GT_Mod: Registered valid BC Wrench: " + tName);
                            }
                        }
                        Block tBlock = GT_Utility.getBlockFromStack(new ItemStack(tItem, 1, 0));
                        if (tBlock != null) {
                            if (tName.endsWith("beehives")) {
                                tBlock.setHarvestLevel("scoop", 0);
                                gregtech.common.tools.GT_Tool_Scoop.sBeeHiveMaterial = tBlock.getMaterial();
                            }
                            if (OrePrefixes.stone.mDefaultStackSize < tItem.getItemStackLimit(new ItemStack(tItem, 1, 0))) {
                                if ((tBlock.isReplaceableOreGen(GT_Values.DW, 0, 0, 0, Blocks.stone)) || (tBlock.isReplaceableOreGen(GT_Values.DW, 0, 0, 0, Blocks.netherrack)) || (tBlock.isReplaceableOreGen(GT_Values.DW, 0, 0, 0, Blocks.end_stone))) {
                                    tItem.setMaxStackSize(OrePrefixes.stone.mDefaultStackSize);
                                }
                            }
                        }
                        if (((tItem instanceof ItemFood)) && (tItem != ItemList.IC2_Food_Can_Filled.getItem()) && (tItem != ItemList.IC2_Food_Can_Spoiled.getItem())) {
                            int tFoodValue = ((ItemFood) tItem).func_150905_g(new ItemStack(tItem, 1, 0));
                            if (tFoodValue > 0) {
                                GT_Values.RA.addCannerRecipe(new ItemStack(tItem, 1, 32767), ItemList.IC2_Food_Can_Empty.get(tFoodValue, new Object[0]), ItemList.IC2_Food_Can_Filled.get(tFoodValue, new Object[0]), GT_Utility.getContainerItem(new ItemStack(tItem, 1, 0), true), tFoodValue * 100, 1);
                            }
                        }
                        if ((tItem instanceof IFluidContainerItem)) {
                            GT_OreDictUnificator.addToBlacklist(new ItemStack(tItem, 1, 32767));
                        }
                        if ((tName.equals("item.ItemSensorLocationCard")) || (tName.equals("item.ItemEnergySensorLocationCard")) || (tName.equals("item.ItemEnergyArrayLocationCard")) || (tName.equals("item.ItemTextCard"))) {
                            GT_Values.RA.addAssemblerRecipe(new ItemStack(tItem, 1, 32767), null, ItemList.Circuit_Basic.get(2L, new Object[0]), 200, 32);
                        }
                        if (tName.equals("item.ItemTimeCard")) {
                            GT_Values.RA.addAssemblerRecipe(new ItemStack(tItem, 1, 32767), null, ItemList.Circuit_Basic.get(1L, new Object[0]), 100, 32);
                        }
                        if (tName.equals("tile.ArsMagica:ore_vinteum")) {
                            GT_OreDictUnificator.set(OrePrefixes.ore, Materials.Vinteum, new ItemStack(tItem, 1, 0));
                        }
                        if (tName.equals("item.ArsMagica:purified_vinteum")) {
                            GT_Values.RA.addFuel(new ItemStack(tItem, 1, 0), null, 256, 5);
                        }
                        if ((tName.equals("item.fieryBlood")) || (tName.equals("item.fieryTears"))) {
                            GT_Values.RA.addFuel(new ItemStack(tItem, 1, 0), null, 2048, 5);
                        }
                        if (tName.equals("tile.TFRoots")) {
                            GT_ModHandler.addPulverisationRecipe(new ItemStack(tItem, 1, 0), new ItemStack(Items.stick, 2), new ItemStack(Items.stick, 1), 30);
                            GT_ModHandler.addSawmillRecipe(new ItemStack(tItem, 1, 0), new ItemStack(Items.stick, 4), new ItemStack(Items.stick, 2));
                            GT_Values.RA.addFuel(new ItemStack(tItem, 1, 1), new ItemStack(Items.stick, 4), 32, 5);
                        }
                        if (tName.equals("item.tconstruct.manual")) {
                            GT_OreDictUnificator.registerOre("bookTinkersManual", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.ArsMagica:spell_parchment")) {
                            GT_OreDictUnificator.registerOre("paperArsSpellParchment", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.ArsMagica:spell_recipe")) {
                            GT_OreDictUnificator.registerOre("paperArsSpellRecipe", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.ArsMagica:spell_book")) {
                            GT_OreDictUnificator.registerOre("bookArsSpells", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.myst.page")) {
                            GT_OreDictUnificator.registerOre("paperMystcraft", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.myst.agebook")) {
                            GT_OreDictUnificator.registerOre("bookMystcraftAge", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.myst.linkbook")) {
                            GT_OreDictUnificator.registerOre("bookMystcraftLink", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.myst.notebook")) {
                            GT_OreDictUnificator.registerOre("bookNotes", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.itemManuelBook")) {
                            GT_OreDictUnificator.registerOre("bookWritten", new ItemStack(tItem, 1, 0));
                        }
                        if (tName.equals("item.blueprintItem")) {
                            GT_OreDictUnificator.registerOre("paperBlueprint", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.ccprintout")) {
                            GT_OreDictUnificator.registerOre("paperWritten", new ItemStack(tItem, 1, 0));
                            GT_OreDictUnificator.registerOre("paperWritten", new ItemStack(tItem, 1, 1));
                            GT_OreDictUnificator.registerOre("bookWritten", new ItemStack(tItem, 1, 2));
                        }
                        if (tName.equals("item.blueprintItem")) {
                            GT_OreDictUnificator.registerOre("paperBlueprint", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.wirelessmap")) {
                            GT_OreDictUnificator.registerOre("paperMap", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.ItemResearchNotes")) {
                            GT_OreDictUnificator.registerOre("paperResearch", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.ItemThaumonomicon")) {
                            GT_OreDictUnificator.registerOre("bookThaumonomicon", new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("item.ligniteCoal")) {
                            GT_OreDictUnificator.set(OrePrefixes.gem, Materials.Lignite, new ItemStack(tItem, 1, 0));
                        }
                        if ((tName.equals("tile.extrabiomes.redrock")) || (tName.equals("tile.bop.redRocks"))) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Redrock, new ItemStack(tItem, 1, 0));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Redrock, new ItemStack(tItem, 1, 1));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Redrock, new ItemStack(tItem, 1, 2));
                        }
                        if (tName.equals("tile.rpstone")) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(tItem, 1, 0));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 1));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(tItem, 1, 2));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 3));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 4));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 5));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 6));
                        }
                        if (/**(tName.equals("tile.sedimentaryStone")) ||**/ ((tName.equals("tile.igneousStone")) || (tName.equals("tile.igneousStoneBrick")) || (tName.equals("tile.igneousCobblestone")))) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(tItem, 1, 0));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(tItem, 1, 1));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Rhyolite, new ItemStack(tItem, 1, 2));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Andesite, new ItemStack(tItem, 1, 3));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Gabbro, new ItemStack(tItem, 1, 4));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 5));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Komatiite, new ItemStack(tItem, 1, 6));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Dacite, new ItemStack(tItem, 1, 7));

                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteRed, new ItemStack(tItem, 1, 8));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.GraniteBlack, new ItemStack(tItem, 1, 9));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Rhyolite, new ItemStack(tItem, 1, 10));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Andesite, new ItemStack(tItem, 1, 11));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Gabbro, new ItemStack(tItem, 1, 12));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Basalt, new ItemStack(tItem, 1, 13));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Komatiite, new ItemStack(tItem, 1, 14));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Dacite, new ItemStack(tItem, 1, 15));
                        }
                        if ((tName.equals("tile.metamorphicStone")) || (tName.equals("tile.metamorphicStoneBrick")) || (tName.equals("tile.metamorphicCobblestone"))) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Gneiss, new ItemStack(tItem, 1, 0));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Eclogite, new ItemStack(tItem, 1, 1));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(tItem, 1, 2));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Quartzite, new ItemStack(tItem, 1, 3));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Blueschist, new ItemStack(tItem, 1, 4));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Greenschist, new ItemStack(tItem, 1, 5));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Soapstone, new ItemStack(tItem, 1, 6));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Migmatite, new ItemStack(tItem, 1, 7));

                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Gneiss, new ItemStack(tItem, 1, 8));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Eclogite, new ItemStack(tItem, 1, 9));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Marble, new ItemStack(tItem, 1, 10));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Quartzite, new ItemStack(tItem, 1, 11));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Blueschist, new ItemStack(tItem, 1, 12));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Greenschist, new ItemStack(tItem, 1, 13));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Soapstone, new ItemStack(tItem, 1, 14));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Migmatite, new ItemStack(tItem, 1, 15));
                        }
                        if (tName.equals("tile.blockCosmeticSolid")) {
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Obsidian, new ItemStack(tItem, 1, 0));
                            GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Obsidian, new ItemStack(tItem, 1, 1));
                            GT_OreDictUnificator.registerOre(OrePrefixes.block, Materials.Thaumium, new ItemStack(tItem, 1, 4));
                        }
                        if (tName.equals("tile.enderchest")) {
                            GT_OreDictUnificator.registerOre(OreDictNames.enderChest, new ItemStack(tItem, 1, 32767));
                        }
                        if (tName.equals("tile.autoWorkbenchBlock")) {
                            GT_OreDictUnificator.registerOre(OreDictNames.craftingWorkBench, new ItemStack(tItem, 1, 0));
                        }
                        if (tName.equals("tile.pumpBlock")) {
                            GT_OreDictUnificator.registerOre(OreDictNames.craftingPump, new ItemStack(tItem, 1, 0));
                            if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "BCPump", false)) {
                                GT_ModHandler.removeRecipeByOutput(new ItemStack(tItem, 1, 0));
                            }
                        }
                        if (tName.equals("tile.tankBlock")) {
                            GT_OreDictUnificator.registerOre(OreDictNames.craftingTank, new ItemStack(tItem, 1, 0));
                        }
                        if (tName.equals("item.drawplateDiamond")) {
                            GT_OreDictUnificator.registerOre(ToolDictNames.craftingToolDrawplate, new ItemStack(tItem, 1, 32767));
                        }
                    }
                }
            }
        } catch (Throwable e) {/**/}
    }
}
