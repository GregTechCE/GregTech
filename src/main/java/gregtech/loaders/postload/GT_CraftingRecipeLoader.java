package gregtech.loaders.postload;

import gregtech.GT_Mod;
import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.items.OreDictNames;
import gregtech.api.items.ToolDictNames;
import gregtech.api.unification.Dyes;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.ItemList;
import gregtech.api.util.GTLog;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import ic2.core.block.BlockIC2Fence;
import ic2.core.block.BlockScaffold;
import ic2.core.block.machine.BlockMiningPipe;
import ic2.core.block.type.ResourceBlock;
import ic2.core.block.wiring.CableType;
import ic2.core.item.ItemIC2Boat;
import ic2.core.item.block.ItemCable;
import ic2.core.item.type.CasingResourceType;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

@SuppressWarnings("ALL")
public class GT_CraftingRecipeLoader implements Runnable {
        private final static String aTextIron1 = "X X" ;private final static String aTextIron2 = "XXX" ;private final static String aTextRailcraft = "Railcraft";
        private final static String aTextMachineBeta = "machine.beta" ;private final static String aTextMachineAlpha = "machine.alpha";
        public void run() {
                GTLog.out.println("GT_Mod: Adding nerfed Vanilla Recipes.");
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.BUCKET, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"XhX", " X ", 'X', OrePrefix.plate.get(Materials.AnyIron)});
                if (!GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Bucket", true)) {
                        GT_ModHandler.addCraftingRecipe(new ItemStack(Items.BUCKET, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{aTextIron1, " X ", 'X', OrePrefix.ingot.get(Materials.AnyIron)});
                }
                ItemStack tMat = new ItemStack(Items.IRON_INGOT);
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.PressurePlate", true)) {
                        ItemStack tStack;
                        if (null != (tStack = GT_ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, null, null, null, null, null, null, null}))) {
                                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XXh", 'X', OrePrefix.plate.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron)});
                        }
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Door", true)) {
                        ItemStack tStack;
                        if (null != (tStack = GT_ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, null, tMat, tMat, null, tMat, tMat, null}))) {
                                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XX ", "XXh", "XX ", 'X', OrePrefix.plate.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron)});
                        }
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Cauldron", true)) {
                        ItemStack tStack;
                        if (null != (tStack = GT_ModHandler.removeRecipe(new ItemStack[]{tMat, null, tMat, tMat, null, tMat, tMat, tMat, tMat}))) {
                                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{aTextIron1, "XhX", aTextIron2, 'X', OrePrefix.plate.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron)});
                        }
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Hopper", true)) {
                        ItemStack tStack;
                        if (null != (tStack = GT_ModHandler.removeRecipe(new ItemStack[]{tMat, null, tMat, tMat, new ItemStack(Blocks.CHEST, 1, 0), tMat, null, tMat, null}))) {
                                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XwX", "XCX", " X ", 'X', OrePrefix.plate.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron), 'C', "craftingChest"});
                        }
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Bars", true)) {
                        ItemStack tStack;
                        if (null != (tStack = GT_ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, tMat, tMat, tMat, tMat, null, null, null}))) {
                                tStack.stackSize /= 2;
                                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{" w ", aTextIron2, aTextIron2, 'X', OrePrefix.stick.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron)});
                        }
                }
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(BlockName.fence, BlockIC2Fence.IC2FenceType.iron, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextIron2, aTextIron2, " w ", 'X', OrePrefix.stick.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron)});

                tMat = new ItemStack(Items.GOLD_INGOT);
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Gold.PressurePlate", true)) {
                        ItemStack tStack;
                        if (null != (tStack = GT_ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, null, null, null, null, null, null, null}))) {
                                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XXh", 'X', OrePrefix.plate.get(Materials.Gold), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.Gold)});
                        }
                }
                tMat = OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Rubber, 1);
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Rubber.Sheet", true)) {
                        ItemStack tStack;
                        if (null != (tStack = GT_ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, tMat, tMat, tMat, tMat, null, null, null}))) {
                                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{aTextIron2, aTextIron2, 'X', OrePrefix.plate.get(Materials.Rubber)});
                        }
                }
                GT_ModHandler.removeRecipeByOutput(ItemList.Bottle_Empty.get(1, new Object[0]));
                GT_ModHandler.removeRecipeByOutput(ItemList.IC2_Spray_WeedEx.get(1, new Object[0]));
                GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.re_battery, 1));
                GT_ModHandler.removeRecipeByOutput(new ItemStack(Blocks.TNT));
                GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.dynamite, 1));
                GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(BlockName.te, TeBlock.itnt, 1));

                ItemStack tStack = GT_ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.PLANKS, 1, 0), null, null, new ItemStack(Blocks.PLANKS, 1, 0)});
                if (tStack != null) {
                        GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[]{tStack}), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"s", "P", "P", 'P', OrePrefix.plank.get(Materials.Wood)});
                        GT_ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize / 2 : tStack.stackSize, new Object[]{tStack}), GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.BUFFERED, new Object[]{"P", "P", 'P', OrePrefix.plank.get(Materials.Wood)});
                }
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PP", 'P', OrePrefix.plank.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.STONE_BUTTON, 2, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"S", "S", 'S', OrePrefix.stone});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"SS", 'S', OrePrefix.stone});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.STONE_BUTTON, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.stone});

                GTLog.out.println("GT_Mod: Adding Vanilla Convenience Recipes.");

                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.STONEBRICK, 1, 3), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"f", "X", 'X', new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.GRAVEL, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(Blocks.COBBLESTONE, 1, 0)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.COBBLESTONE, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(Blocks.STONE, 1, 0)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.STONEBRICK, 1, 2), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(Blocks.STONEBRICK, 1, 0)});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 0)});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 0)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.COBBLESTONE, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 3)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.BRICK_BLOCK, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 4)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 5)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.NETHER_BRICK, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 6)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 7)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 8)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 0)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 1)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 2), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 2)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 3), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 3)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 4), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 4)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 5), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 5)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 6), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 6)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 7), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 7)});

                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.STICK, 2, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"s", "X", 'X', new ItemStack(Blocks.DEADBUSH, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.STICK, 2, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"s", "X", 'X', new ItemStack(Blocks.TALLGRASS, 1, 0)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.STICK, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"s", "X", 'X', OrePrefix.treeSapling});

                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.COMPARATOR, 1, 0), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" T ", "TQT", "SSS", 'Q', OreDictNames.craftingQuartz, 'S', OrePrefix.stoneSmooth, 'T', OreDictNames.craftingRedstoneTorch});

                GTLog.out.println("GT_Mod: Adding Tool Recipes.");
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.MINECART, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{" h ", "PwP", "WPW", 'P', OrePrefix.plate.get(Materials.AnyIron), 'W', ItemList.Component_Minecart_Wheels_Iron});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.MINECART, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" h ", "PwP", "WPW", 'P', OrePrefix.plate.get(Materials.Steel), 'W', ItemList.Component_Minecart_Wheels_Steel});

                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.CHEST_MINECART, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', OreDictNames.craftingChest});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.FURNACE_MINECART, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', OreDictNames.craftingFurnace});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.HOPPER_MINECART, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', new ItemStack(Blocks.HOPPER, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.TNT_MINECART, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', new ItemStack(Blocks.TNT, 1, 32767)});

                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.CHAINMAIL_HELMET, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"RRR", "RhR", 'R', OrePrefix.ring.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.CHAINMAIL_CHESTPLATE, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"RhR", "RRR", "RRR", 'R', OrePrefix.ring.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.CHAINMAIL_LEGGINGS, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"RRR", "RhR", "R R", 'R', OrePrefix.ring.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.CHAINMAIL_BOOTS, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"R R", "RhR", 'R', OrePrefix.ring.get(Materials.Steel)});

                GTLog.out.println("GT_Mod: Adding Wool and Color releated Recipes.");
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeOrange});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 2), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeMagenta});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 3), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeLightBlue});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 4), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeYellow});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 5), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeLime});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyePink});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 7), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeGray});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 8), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeLightGray});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 9), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeCyan});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 10), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyePurple});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 11), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeBlue});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 12), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeBrown});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 13), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeGreen});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 14), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeRed});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 15), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeBlack});

                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.STAINED_GLASS, 8, 0), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GGG", "GDG", "GGG", 'G', new ItemStack(Blocks.GLASS, 1), 'D', Dyes.dyeWhite});

                GTLog.out.println("GT_Mod: Putting a Potato on a Stick.");
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Packaged_PotatoChips.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.foil.get(Materials.Aluminium), ItemList.Food_PotatoChips});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Packaged_ChiliChips.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.foil.get(Materials.Aluminium), ItemList.Food_ChiliChips});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Packaged_Fries.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.plateDouble.get(Materials.Paper), ItemList.Food_Fries});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Chum_On_Stick.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.stick.get(Materials.Wood), ItemList.Food_Chum});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Potato_On_Stick.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.stick.get(Materials.Wood), ItemList.Food_Raw_Potato});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Potato_On_Stick_Roasted.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.stick.get(Materials.Wood), ItemList.Food_Baked_Potato});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.bucket.get(Materials.Water), OrePrefix.dust.get(Materials.Wheat)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough_Sugar.get(2L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", OrePrefix.dust.get(Materials.Sugar)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough_Chocolate.get(2L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", OrePrefix.dust.get(Materials.Cocoa)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough_Chocolate.get(2L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", OrePrefix.dust.get(Materials.Chocolate)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Flat_Dough.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", ToolDictNames.craftingToolRollingPin});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Bun.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough"});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Bread.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", "foodDough"});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Baguette.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", "foodDough", "foodDough"});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Cake.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Dough_Sugar, ItemList.Food_Dough_Sugar, ItemList.Food_Dough_Sugar, ItemList.Food_Dough_Sugar});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_ChiliChips.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_PotatoChips, OrePrefix.dust.get(Materials.Chili)});

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Buns.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Breads.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Baguettes.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Bun.get(2L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Bread.get(2L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Baguette.get(2L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes});

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Veggie.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Cheese.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Meat.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, OrePrefix.dust.get(Materials.MeatCooked)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Chum.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, ItemList.Food_Chum});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Veggie.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Cheese.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Meat.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, OrePrefix.dust.get(Materials.MeatCooked)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Chum.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, ItemList.Food_Chum});

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Veggie.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Cheese.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Bacon.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, new ItemStack(Items.COOKED_PORKCHOP, 1)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Steak.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, new ItemStack(Items.COOKED_BEEF, 1)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Veggie.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Cheese.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Bacon.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, new ItemStack(Items.COOKED_PORKCHOP, 1)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Steak.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, new ItemStack(Items.COOKED_BEEF, 1)});

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Veggie.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Cheese.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Bacon.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, new ItemStack(Items.COOKED_PORKCHOP, 1), new ItemStack(Items.COOKED_PORKCHOP, 1)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Steak.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, new ItemStack(Items.COOKED_BEEF, 1), new ItemStack(Items.COOKED_BEEF, 1)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Veggie.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Cheese.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Bacon.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, new ItemStack(Items.COOKED_PORKCHOP, 1), new ItemStack(Items.COOKED_PORKCHOP, 1)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Steak.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, new ItemStack(Items.COOKED_BEEF, 1), new ItemStack(Items.COOKED_BEEF, 1)});

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Pizza_Veggie.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Flat_Dough, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Pizza_Cheese.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Flat_Dough, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Pizza_Meat.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Flat_Dough, OrePrefix.dust.get(Materials.MeatCooked)});

                GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Cheese.get(4L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "foodCheese"});
                GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Lemon.get(4L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropLemon"});
                GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Tomato.get(4L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropTomato"});
                GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Onion.get(4L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropOnion"});
                GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Cucumber.get(4L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropCucumber"});
                GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Bun.get(2L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Baked_Bun});
                GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Bread.get(2L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Baked_Bread});
                GT_ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Baguette.get(2L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Baked_Baguette});
                GT_ModHandler.addCraftingRecipe(ItemList.Food_Raw_PotatoChips.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropPotato"});
                GT_ModHandler.addCraftingRecipe(ItemList.Food_Raw_Cookie.get(4L, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Dough_Chocolate});

                GT_ModHandler.addCraftingRecipe(ItemList.Food_Raw_Fries.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"k", "X", 'X', "cropPotato"});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.BOWL, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"k", "X", 'X', OrePrefix.plank.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ring, Materials.Rubber, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"k", "X", 'X', OrePrefix.plate.get(Materials.Rubber)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadArrow, Materials.Flint, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"f", "X", 'X', new ItemStack(Items.FLINT, 1, 32767)});

                GT_ModHandler.addCraftingRecipe(new ItemStack(Items.ARROW, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"  H", " S ", "F  ", 'H', new ItemStack(Items.FLINT, 1, 32767), 'S', OrePrefix.stick.get(Materials.Wood), 'F', OreDictNames.craftingFeather});

                GT_ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.PLANKS), null, new ItemStack(Blocks.PLANKS), null, new ItemStack(Blocks.PLANKS)});
                GT_ModHandler.removeRecipeByOutput(ItemList.Food_Baked_Bread.get(1, new Object[0]));
                GT_ModHandler.removeRecipeByOutput(new ItemStack(Items.COOKIE, 1));
                GT_ModHandler.removeRecipe(new ItemStack[]{OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Copper, 1), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Tin, 1), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Copper, 1)});
                if (null != GT_Utility.setStack(GT_ModHandler.getRecipeOutput(true, new ItemStack[]{OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Copper, 1), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Copper, 1), null, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Copper, 1), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Tin, 1)}), OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Bronze, GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "bronzeingotcrafting", true) ? 1 : 2L))) {
                        GTLog.out.println("GT_Mod: Changed Forestrys Bronze Recipe");
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "enchantmenttable", false)) {
                        GTLog.out.println("GT_Mod: Removing the Recipe of the Enchantment Table, to have more Fun at enchanting with the Anvil and Books from Dungeons.");
                        GT_ModHandler.removeRecipeByOutput(new ItemStack(Blocks.ENCHANTING_TABLE, 1));
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "enderchest", false)) {
                        GT_ModHandler.removeRecipeByOutput(new ItemStack(Blocks.ENDER_CHEST, 1));
                }
                tStack = OreDictionaryUnifier.get(OrePrefix.dust, Materials.Ash, 1);
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getRecipeOutput(new ItemStack[]{null, new ItemStack(Blocks.SAND, 1, 0), null, null, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Apatite, 1), null, null, new ItemStack(Blocks.SAND, 1, 0), null}), new Object[]{"S", "A", "S", 'A', OrePrefix.dust.get(Materials.Apatite), 'S', new ItemStack(Blocks.SAND, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getRecipeOutput(new ItemStack[]{tStack, tStack, tStack, tStack, OreDictionaryUnifier.get(OrePrefix.gem, Materials.Apatite, 1), tStack, tStack, tStack, tStack}), new Object[]{"SSS", "SAS", "SSS", 'A', OrePrefix.dust.get(Materials.Apatite), 'S', OrePrefix.dust.get(Materials.Ash)});

                GTLog.out.println("GT_Mod: Adding Mixed Metal Ingot Recipes.");
                GT_ModHandler.removeRecipeByOutput(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]));

                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(6L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(6L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                GTLog.out.println("GT_Mod: Adding Rolling Machine Recipes.");
                GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Standard.get(4L, new Object[0]), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Aluminium).toString()});
                GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Standard.get(32L, new Object[0]), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Titanium).toString()});
                GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Standard.get(32L, new Object[0]), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Tungsten).toString()});

                GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Reinforced.get(32L, new Object[0]), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefix.ingot.get(Materials.TungstenSteel).toString()});

                GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(2L, new Object[0]), new Object[]{"  X", " X ", "X  ", 'X', OrePrefix.ingot.get(Materials.Aluminium).toString()});
                GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(16L, new Object[0]), new Object[]{"  X", " X ", "X  ", 'X', OrePrefix.ingot.get(Materials.Titanium).toString()});
                GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(16L, new Object[0]), new Object[]{"  X", " X ", "X  ", 'X', OrePrefix.ingot.get(Materials.Tungsten).toString()});
                GT_ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(48L, new Object[0]), new Object[]{"  X", " X ", "X  ", 'X', OrePrefix.ingot.get(Materials.TungstenSteel).toString()});

                GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem(aTextRailcraft, "post.metal.light.blue", 8L), new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefix.ingot.get(Materials.Aluminium).toString()});
                GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem(aTextRailcraft, "post.metal.purple", 64L), new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefix.ingot.get(Materials.Titanium).toString()});
                GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem(aTextRailcraft, "post.metal.black", 64L), new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefix.ingot.get(Materials.Tungsten).toString()});

                GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem(aTextRailcraft, "post.metal.light.blue", 8L), new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Aluminium).toString()});
                GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem(aTextRailcraft, "post.metal.purple", 64L), new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Titanium).toString()});
                GT_ModHandler.addRollingMachineRecipe(GT_ModHandler.getModItem(aTextRailcraft, "post.metal.black", 64L), new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Tungsten).toString()});

                GTLog.out.println("GT_Mod: Replacing Railcraft Recipes with slightly more OreDicted Variants");

                long tBitMask = GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES_IF_SAME_NBT | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES | GT_ModHandler.RecipeBits.DELETE_ALL_OTHER_NATIVE_RECIPES | GT_ModHandler.RecipeBits.ONLY_ADD_IF_THERE_IS_ANOTHER_RECIPE_FOR_IT;
                char tHammer = ' ';
                char tFile = ' ';
                char tWrench = ' ';
                OrePrefix tIngot = OrePrefix.ingot;
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "railcraft_stuff_use_tools", true)) {
                        tHammer = 'h';
                        tFile = 'f';
                        tWrench = 'w';
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "railcraft_stuff_use_plates", true)) {
                        tIngot = OrePrefix.plate;
                }
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 2L, 3), tBitMask | GT_ModHandler.RecipeBits.MIRRORED, new Object[]{tHammer + "" + tFile, "XX", "XX", 'X', tIngot.get(Materials.Tin)});

                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 0), tBitMask, new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', OrePrefix.nugget.get(Materials.Gold), 'G', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 3)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 1), tBitMask, new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', tIngot.get(Materials.AnyIron), 'G', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 3)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2), tBitMask, new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', tIngot.get(Materials.Steel), 'G', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 3)});

                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 0), tBitMask, new Object[]{tWrench + "PP", tHammer + "PP", 'P', OrePrefix.plate.get(Materials.AnyIron)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 1), tBitMask, new Object[]{"GPG", "PGP", "GPG", 'P', OrePrefix.plate.get(Materials.AnyIron), 'G', new ItemStack(Blocks.GLASS_PANE, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 2), tBitMask, new Object[]{"BPB", "PLP", "BPB", 'P', OrePrefix.plate.get(Materials.AnyIron), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'L', new ItemStack(Blocks.LEVER, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 3), tBitMask, new Object[]{tWrench + "P", tHammer + "P", 'P', OrePrefix.plate.get(Materials.AnyIron)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), tBitMask, new Object[]{tWrench + "P", tHammer + "P", 'P', OrePrefix.plate.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 5), tBitMask, new Object[]{"BBB", "BFB", "BOB", 'B', OrePrefix.ingot.get(Materials.Brick), 'F', new ItemStack(Items.FIRE_CHARGE, 1, 32767), 'O', OreDictNames.craftingFurnace});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 6), tBitMask, new Object[]{"PUP", "BFB", "POP", 'P', OrePrefix.plate.get(Materials.Steel), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'F', new ItemStack(Items.FIRE_CHARGE, 1, 32767), 'U', OrePrefix.bucket.get(Materials.Empty), 'O', OreDictNames.craftingFurnace});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 7), tBitMask | GT_ModHandler.RecipeBits.MIRRORED, new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefix.nugget.get(Materials.Gold), 'O', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 0), 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'T', OreDictNames.craftingPiston});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 8), tBitMask | GT_ModHandler.RecipeBits.MIRRORED, new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefix.plate.get(Materials.AnyIron), 'O', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 1), 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'T', OreDictNames.craftingPiston});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 9), tBitMask | GT_ModHandler.RecipeBits.MIRRORED, new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefix.plate.get(Materials.Steel), 'O', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2), 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'T', OreDictNames.craftingPiston});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 10), tBitMask, new Object[]{" E ", " O ", "OIO", 'I', tIngot.get(Materials.Gold), 'E', OrePrefix.gem.get(Materials.EnderPearl), 'O', OrePrefix.stone.get(Materials.Obsidian)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 11), tBitMask, new Object[]{"OOO", "OEO", "OOO", 'E', OrePrefix.gem.get(Materials.EnderPearl), 'O', OrePrefix.stone.get(Materials.Obsidian)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 12), tBitMask, new Object[]{"GPG", "PAP", "GPG", 'P', OreDictNames.craftingPiston, 'A', OreDictNames.craftingAnvil, 'G', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 13), tBitMask, new Object[]{tWrench + "PP", tHammer + "PP", 'P', OrePrefix.plate.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 14), tBitMask, new Object[]{"GPG", "PGP", "GPG", 'P', OrePrefix.plate.get(Materials.Steel), 'G', new ItemStack(Blocks.GLASS_PANE, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 15), tBitMask, new Object[]{"BPB", "PLP", "BPB", 'P', OrePrefix.plate.get(Materials.Steel), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'L', new ItemStack(Blocks.LEVER, 1, 32767)});

                GT_ModHandler.addCraftingRecipe(ItemList.RC_ShuntingWireFrame.get(6L, new Object[0]), tBitMask, new Object[]{"PPP", "R" + tWrench + "R", "RRR", 'P', OrePrefix.plate.get(Materials.AnyIron), 'R', ItemList.RC_Rebar.get(1, new Object[0])});

                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 0), tBitMask, new Object[]{"IOI", "GEG", "IOI", 'I', tIngot.get(Materials.Gold), 'G', OrePrefix.gem.get(Materials.Diamond), 'E', OrePrefix.gem.get(Materials.EnderPearl), 'O', OrePrefix.stone.get(Materials.Obsidian)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 3L, 1), tBitMask, new Object[]{"BPB", "P" + tWrench + "P", "BPB", 'P', OrePrefix.plate.get(Materials.Steel), 'B', OrePrefix.block.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 2), tBitMask, new Object[]{"IOI", "GEG", "IOI", 'I', tIngot.get(Materials.Gold), 'G', OrePrefix.gem.get(Materials.Emerald), 'E', OrePrefix.gem.get(Materials.EnderPearl), 'O', OrePrefix.stone.get(Materials.Obsidian)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4L, 3), tBitMask, new Object[]{"PPP", "PFP", "PPP", 'P', OrePrefix.plate.get(Materials.Steel), 'F', OreDictNames.craftingFurnace});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 5), tBitMask, new Object[]{" N ", "RCR", 'R', OrePrefix.dust.get(Materials.Redstone), 'N', OrePrefix.stone.get(Materials.Netherrack), 'C', new ItemStack(Items.CAULDRON, 1, 0)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 6), tBitMask, new Object[]{"SGS", "EDE", "SGS", 'E', OrePrefix.gem.get(Materials.Emerald), 'S', OrePrefix.plate.get(Materials.Steel), 'G', new ItemStack(Blocks.GLASS_PANE, 1, 32767), 'D', new ItemStack(Blocks.DISPENSER, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 8), tBitMask, new Object[]{"IPI", "PCP", "IPI", 'P', OreDictNames.craftingPiston, 'I', tIngot.get(Materials.AnyIron), 'C', new ItemStack(Blocks.CRAFTING_TABLE, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 9), tBitMask, new Object[]{" I ", " T ", " D ", 'I', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'T', GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), 'D', new ItemStack(Blocks.DISPENSER, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 10), tBitMask, new Object[]{" I ", "RTR", " D ", 'I', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'T', GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), 'D', new ItemStack(Blocks.DISPENSER, 1, 32767), 'R', OrePrefix.dust.get(Materials.Redstone)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 10), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RTR", 'T', GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 9), 'R', OrePrefix.dust.get(Materials.Redstone)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 11), tBitMask, new Object[]{"PCP", "CSC", "PCP", 'P', OrePrefix.plank.get(Materials.Wood), 'S', OrePrefix.plate.get(Materials.Steel), 'C', new ItemStack(Items.GOLDEN_CARROT, 1, 0)});
                if (GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "DisableRCBlastFurnace", false)) {
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4L, 12));
                }
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 13), tBitMask, new Object[]{"TSB", "SCS", "PSP", 'P', OreDictNames.craftingPiston, 'S', OrePrefix.plate.get(Materials.Steel), 'B', OreDictNames.craftingBook, 'C', new ItemStack(Blocks.CRAFTING_TABLE, 1, 32767), 'T', new ItemStack(Items.DIAMOND_PICKAXE, 1, 0)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 6L, 14), tBitMask, new Object[]{"PPP", "ISI", "PPP", 'P', OrePrefix.plank.get(Materials.Wood), 'I', tIngot.get(Materials.AnyIron), 'S', "slimeball"});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4L, 15), tBitMask, new Object[]{"PDP", "DBD", "PDP", 'P', OreDictNames.craftingPiston, 'B', OrePrefix.block.get(Materials.Steel), 'D', OrePrefix.gem.get(Materials.Diamond)});

                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "machine.epsilon", 1, 0), tBitMask, new Object[]{"PWP", "WWW", "PWP", 'P', OrePrefix.plate.get(Materials.AnyIron), 'W', OrePrefix.wireGt02.get(Materials.Copper)});

                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "tool.crowbar", 1, 0), tBitMask, new Object[]{tHammer + "DS", "DSD", "SD" + tFile, 'S', OrePrefix.ingot.get(Materials.Iron), 'D', Dyes.dyeRed});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "tool.crowbar.reinforced", 1, 0), tBitMask, new Object[]{tHammer + "DS", "DSD", "SD" + tFile, 'S', OrePrefix.ingot.get(Materials.Steel), 'D', Dyes.dyeRed});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "tool.whistle.tuner", 1, 0), tBitMask | GT_ModHandler.RecipeBits.MIRRORED, new Object[]{"S" + tHammer + "S", "SSS", " S" + tFile, 'S', OrePrefix.nugget.get(Materials.Iron)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.blade", 1, 0), tBitMask, new Object[]{"S" + tFile, "S ", "S" + tHammer, 'S', tIngot.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.disk", 1, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefix.block.get(Materials.Steel), 'S', GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.blade", 1, 0)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.rotor", 1, 0), tBitMask, new Object[]{"SSS", " " + tWrench + " ", 'S', GT_ModHandler.getModItem(aTextRailcraft, "part.turbine.disk", 1, 0)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "borehead.iron", 1, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefix.block.get(Materials.Iron), 'S', tIngot.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "borehead.steel", 1, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefix.block.get(Materials.Steel), 'S', tIngot.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "borehead.diamond", 1, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefix.block.get(Materials.Diamond), 'S', tIngot.get(Materials.Steel)});

                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "cart.loco.steam.solid", 1, 0), tBitMask, new Object[]{"TTF", "TTF", "BCC", 'C', new ItemStack(Items.MINECART, 1), 'T', GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), 'F', GT_ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 5), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "cart.loco.electric", 1, 0), tBitMask, new Object[]{"LP" + tWrench, "PEP", "GCG", 'C', new ItemStack(Items.MINECART, 1), 'E', GT_ModHandler.getModItem(aTextRailcraft, "machine.epsilon", 1, 0), 'G', GT_ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2), 'L', new ItemStack(Blocks.REDSTONE_LAMP, 1, 32767), 'P', OrePrefix.plate.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem(aTextRailcraft, "cart.bore", 1, 0), tBitMask, new Object[]{"BCB", "FCF", tHammer + "A" + tWrench, 'C', new ItemStack(Items.MINECART, 1), 'A', new ItemStack(Items.CHEST_MINECART, 1), 'F', OreDictNames.craftingFurnace, 'B', OrePrefix.block.get(Materials.Steel)});

                GTLog.out.println("GT_Mod: Beginning to add regular Crafting Recipes.");
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(BlockName.scaffold, BlockScaffold.ScaffoldType.wood, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", " S ", "S S", 'W', OrePrefix.plank.get(Materials.Wood), 'S', OrePrefix.stick.get(Materials.Wood)});

                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "HPT", 'H', OrePrefix.cell.get(Materials.Helium), 'N', OrePrefix.cell.get(Materials.Nitrogen), 'T', OrePrefix.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefix.wireGt01.get(Materials.NiobiumTitanium)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "HPT", 'H', OrePrefix.cell.get(Materials.Helium), 'N', OrePrefix.cell.get(Materials.Nitrogen), 'T', OrePrefix.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefix.wireGt01.get(Materials.VanadiumGallium)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "NPT", 'N', OrePrefix.cell.get(Materials.Nitrogen), 'T', OrePrefix.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefix.wireGt01.get(Materials.YttriumBariumCuprate)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "NPT", 'N', OrePrefix.cell.get(Materials.Nitrogen), 'T', OrePrefix.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefix.wireGt01.get(Materials.HSSG)});

                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stick, Materials.IronMagnetic, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.stick.get(Materials.AnyIron), OrePrefix.dust.get(Materials.Redstone), OrePrefix.dust.get(Materials.Redstone), OrePrefix.dust.get(Materials.Redstone), OrePrefix.dust.get(Materials.Redstone)});

                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Gold.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.Gold)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Iron.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.AnyIron)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Bronze.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.Bronze)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Copper.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.AnyCopper)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Tin.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Lead.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.Lead)});
                GT_ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Steel.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.Steel)});

                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.TORCH, 2), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"C", "S", 'C', OrePrefix.dust.get(Materials.Sulfur), 'S', OrePrefix.stick.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.TORCH, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"C", "S", 'C', OrePrefix.dust.get(Materials.Phosphorus), 'S', OrePrefix.stick.get(Materials.Wood)});

                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PISTON, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefix.plank.get(Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.ingot.get(Materials.AnyIron)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PISTON, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefix.plank.get(Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.ingot.get(Materials.AnyBronze)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PISTON, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefix.plank.get(Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.ingot.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PISTON, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefix.plank.get(Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.ingot.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.PISTON, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefix.plank.get(Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.ingot.get(Materials.Titanium)});

                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.reactor_heat_vent, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"AIA", "I I", "AIA", 'I', new ItemStack(Blocks.IRON_BARS, 1), 'A', OrePrefix.plate.get(Materials.Aluminium)});
                GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.containment_plating, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{GT_ModHandler.getIC2Item(ItemName.plating, 1), OrePrefix.plate.get(Materials.Lead)});
                if (!Materials.Steel.mBlastFurnaceRequired) {
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Steel, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Coal), OrePrefix.dust.get(Materials.Coal)});
                }
                if (GT_Mod.gregtechproxy.mNerfDustCrafting) {
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Electrum, 6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.Gold)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Brass, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.Zinc)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Brass, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Zinc)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Bronze, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.Tin)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Bronze, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tin)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Invar, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Nickel)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Cupronickel, 6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.AnyCopper)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Nichrome, 15L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Chrome)});
                } else {
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Electrum, 2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.Gold)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Brass, 4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.Zinc)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Brass, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Zinc)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Bronze, 4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.Tin)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Bronze, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tin)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Invar, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Nickel)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Cupronickel, 2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.AnyCopper)});
                        GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Nichrome, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Chrome)});
                }
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.RoseGold, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Gold), OrePrefix.dust.get(Materials.Gold), OrePrefix.dust.get(Materials.Gold), OrePrefix.dust.get(Materials.Gold), OrePrefix.dust.get(Materials.AnyCopper)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.SterlingSilver, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.AnyCopper)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.BlackBronze, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Gold), OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.BismuthBronze, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Bismuth), OrePrefix.dust.get(Materials.Zinc), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.BlackSteel, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.BlackBronze), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.RedSteel, 8L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.SterlingSilver), OrePrefix.dust.get(Materials.BismuthBronze), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.BlueSteel, 8L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.RoseGold), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Osmiridium, 4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Iridium), OrePrefix.dust.get(Materials.Iridium), OrePrefix.dust.get(Materials.Iridium), OrePrefix.dust.get(Materials.Osmium)});

                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Ultimet, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Chrome), OrePrefix.dust.get(Materials.Chrome), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Molybdenum)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.CobaltBrass, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Aluminium), OrePrefix.dust.get(Materials.Cobalt)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.StainlessSteel, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Manganese), OrePrefix.dust.get(Materials.Chrome)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.YttriumBariumCuprate, 6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Yttrium), OrePrefix.dust.get(Materials.Barium), OrePrefix.dust.get(Materials.Barium), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Kanthal, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Aluminium), OrePrefix.dust.get(Materials.Chrome)});

                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Ultimet, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dustTiny.get(Materials.Cobalt), OrePrefix.dustTiny.get(Materials.Cobalt), OrePrefix.dustTiny.get(Materials.Cobalt), OrePrefix.dustTiny.get(Materials.Cobalt), OrePrefix.dustTiny.get(Materials.Cobalt), OrePrefix.dustTiny.get(Materials.Chrome), OrePrefix.dustTiny.get(Materials.Chrome), OrePrefix.dustTiny.get(Materials.Nickel), OrePrefix.dustTiny.get(Materials.Molybdenum)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.CobaltBrass, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Aluminium), OrePrefix.dustTiny.get(Materials.Cobalt)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.StainlessSteel, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Nickel), OrePrefix.dustTiny.get(Materials.Manganese), OrePrefix.dustTiny.get(Materials.Chrome)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.YttriumBariumCuprate, 6L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dustTiny.get(Materials.Yttrium), OrePrefix.dustTiny.get(Materials.Barium), OrePrefix.dustTiny.get(Materials.Barium), OrePrefix.dustTiny.get(Materials.AnyCopper), OrePrefix.dustTiny.get(Materials.AnyCopper), OrePrefix.dustTiny.get(Materials.AnyCopper)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustTiny, Materials.Kanthal, 3L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Aluminium), OrePrefix.dustTiny.get(Materials.Chrome)});

                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.VanadiumSteel, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Vanadium), OrePrefix.dust.get(Materials.Chrome)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.HSSG, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.TungstenSteel), OrePrefix.dust.get(Materials.TungstenSteel), OrePrefix.dust.get(Materials.TungstenSteel), OrePrefix.dust.get(Materials.TungstenSteel), OrePrefix.dust.get(Materials.TungstenSteel), OrePrefix.dust.get(Materials.Chrome), OrePrefix.dust.get(Materials.Molybdenum), OrePrefix.dust.get(Materials.Molybdenum), OrePrefix.dust.get(Materials.Vanadium)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.HSSE, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Manganese), OrePrefix.dust.get(Materials.Silicon)});
                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.HSSS, 9L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.Iridium), OrePrefix.dust.get(Materials.Iridium), OrePrefix.dust.get(Materials.Osmium)});


                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.IronWood, 2L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.LiveRoot), OrePrefix.dustTiny.get(Materials.Gold)});

                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.GUNPOWDER, 3), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Coal), OrePrefix.dust.get(Materials.Sulfur), OrePrefix.dust.get(Materials.Saltpeter), OrePrefix.dust.get(Materials.Saltpeter)});
                GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.GUNPOWDER, 2), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Charcoal), OrePrefix.dust.get(Materials.Sulfur), OrePrefix.dust.get(Materials.Saltpeter), OrePrefix.dust.get(Materials.Saltpeter)});

                GT_ModHandler.addShapelessCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Saltpeter, 5L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.dust.get(Materials.Potassium), OrePrefix.cell.get(Materials.Nitrogen), OrePrefix.cell.get(Materials.Oxygen), OrePrefix.cell.get(Materials.Oxygen), OrePrefix.cell.get(Materials.Oxygen)});
                GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_fibre, 1));
                if (GT_Mod.gregtechproxy.mDisableIC2Cables) {
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.copper, 0));
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.copper, 1));
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.gold, 0));
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.gold, 1));
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.iron, 0));
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.iron, 1));
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.glass, 0));
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.tin, 0));
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.tin, 1));
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.detector, 0));
                        GT_ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.splitter, 0));
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.electrolyzer, 1));

                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.batbox, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.batbox, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "BBB", "PPP", 'C', OrePrefix.cableGt01.get(Materials.Tin), 'P', OrePrefix.plank.get(Materials.Wood), 'B', OrePrefix.battery.get(Materials.Basic)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.mfe, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.mfe, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CEC", "EME", "CEC", 'C', OrePrefix.cableGt01.get(Materials.Gold), 'E', OrePrefix.battery.get(Materials.Elite), 'M', GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.lv_transformer, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.lv_transformer, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "POP", "PCP", 'C', OrePrefix.cableGt01.get(Materials.Tin), 'O', GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coil, 1), 'P', OrePrefix.plank.get(Materials.Wood)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CMC", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'M', GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.hv_transformer, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.hv_transformer, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" C ", "IMB", " C ", 'C', OrePrefix.cableGt01.get(Materials.Gold), 'M', GT_ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1), 'I', OrePrefix.circuit.get(Materials.Basic), 'B', OrePrefix.battery.get(Materials.Advanced)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.ev_transformer, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.ev_transformer, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" C ", "IMB", " C ", 'C', OrePrefix.cableGt01.get(Materials.Aluminium), 'M', GT_ModHandler.getIC2TEItem(TeBlock.hv_transformer, 1), 'I', OrePrefix.circuit.get(Materials.Advanced), 'B', OrePrefix.battery.get(Materials.Master)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.cesu, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.cesu, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "BBB", "PPP", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'P', OrePrefix.plate.get(Materials.Bronze), 'B', OrePrefix.battery.get(Materials.Advanced)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.luminator_flat, 1));
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.teleporter, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.teleporter, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GFG", "CMC", "GDG", 'C', OrePrefix.cableGt01.get(Materials.Platinum), 'G', OrePrefix.circuit.get(Materials.Advanced), 'D', OrePrefix.gem.get(Materials.Diamond), 'M', GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1), 'F', GT_ModHandler.getIC2Item(ItemName.frequency_transmitter, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.energy_o_mat, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.energy_o_mat, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RBR", "CMC", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.battery.get(Materials.Basic), 'M', GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.advanced_re_battery, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.advanced_re_battery, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CTC", "TST", "TLT", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'S', OrePrefix.dust.get(Materials.Sulfur), 'L', OrePrefix.dust.get(Materials.Lead), 'T', GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.bronze, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.boat, ItemIC2Boat.BoatType.electric, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.boat, ItemIC2Boat.BoatType.electric, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CCC", "XWX", aTextIron2, 'C', OrePrefix.cableGt01.get(Materials.Copper), 'X', OrePrefix.plate.get(Materials.Iron), 'W', GT_ModHandler.getIC2TEItem(TeBlock.water_generator, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.cropnalyzer, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.cropnalyzer, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CC ", "RGR", "RIR", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'R', OrePrefix.dust.get(Materials.Redstone), 'G', OrePrefix.block.get(Materials.Glass), 'I', OrePrefix.circuit.get(Materials.Basic)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coil, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coil, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CCC", "CXC", "CCC", 'C', OrePrefix.wireGt01.get(Materials.Copper), 'X', OrePrefix.ingot.get(Materials.AnyIron)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.power_unit, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.power_unit, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"BCA", "BIM", "BCA", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'B', OrePrefix.battery.get(Materials.Basic), 'A', GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.iron, 1), 'I', OrePrefix.circuit.get(Materials.Basic), 'M', GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.electric_motor, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.small_power_unit, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.small_power_unit, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" CA", "BIM", " CA", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'B', OrePrefix.battery.get(Materials.Basic), 'A', GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.iron, 1), 'I', OrePrefix.circuit.get(Materials.Basic), 'M', GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.electric_motor, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.remote, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.remote, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" C ", "TLT", " F ", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'L', OrePrefix.dust.get(Materials.Lapis), 'T', GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.tin, 1), 'F', GT_ModHandler.getIC2Item(ItemName.frequency_transmitter, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.scanner, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.scanner, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PGP", "CBC", "WWW", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'G', OrePrefix.dust.get(Materials.Glowstone), 'B', OrePrefix.battery.get(Materials.Advanced), 'C', OrePrefix.circuit.get(Materials.Advanced), 'P', GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.gold, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.advanced_scanner, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.advanced_scanner, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PDP", "GCG", "WSW", 'W', OrePrefix.cableGt01.get(Materials.Gold), 'G', OrePrefix.dust.get(Materials.Glowstone), 'D', OrePrefix.battery.get(Materials.Elite), 'C', OrePrefix.circuit.get(Materials.Advanced), 'P', GT_ModHandler.getIC2Item(ItemName.casing, CasingResourceType.gold, 1), 'S', GT_ModHandler.getIC2Item(ItemName.scanner, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.solar_helmet, 1));
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.static_boots, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.static_boots, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"I I", "IWI", "CCC", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'I', OrePrefix.ingot.get(Materials.Iron), 'W', new ItemStack(Blocks.WOOL)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.meter, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.meter, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" G ", "CIC", "C C", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'G', OrePrefix.dust.get(Materials.Glowstone), 'I', OrePrefix.circuit.get(Materials.Basic)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.obscurator, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.obscurator, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RER", "CAC", "RRR", 'C', OrePrefix.cableGt01.get(Materials.Gold), 'R', OrePrefix.dust.get(Materials.Redstone), 'E', OrePrefix.battery.get(Materials.Advanced), 'A', OrePrefix.circuit.get(Materials.Advanced)});
                        //GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.overclocker, 1));
                        //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.overclocker, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CCC", "WEW", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'C', GT_ModHandler.getIC2Item(ItemName.heat_vent, 1, 1), 'E', OrePrefix.circuit.get(Materials.Basic)});
                        //GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.transformer, 1));
                        //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.transformer, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GGG", "WTW", "GEG", 'W', OrePrefix.cableGt01.get(Materials.Gold), 'T', GT_ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1), 'E', OrePrefix.circuit.get(Materials.Basic), 'G', OrePrefix.block.get(Materials.Glass)});
                        //GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.energy_storage, 1));
                        //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.energy_storage, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PPP", "WBW", "PEP", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'E', OrePrefix.circuit.get(Materials.Basic), 'P', OrePrefix.plank.get(Materials.Wood), 'B', OrePrefix.battery.get(Materials.Basic)});
                        //GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.ejector, 1));
                        //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.ejector, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PHP", "WEW", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'E', OrePrefix.circuit.get(Materials.Basic), 'P', new ItemStack(Blocks.PISTON), 'H', new ItemStack(Blocks.HOPPER)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.single_use_battery, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.single_use_battery, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"W", "C", "R", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'C', OrePrefix.dust.get(Materials.HydratedCoal), 'R', OrePrefix.dust.get(Materials.Redstone)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.frequency_transmitter, 1));
                        //GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.pulling, 1));
                        //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.pulling, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PHP", "WEW", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'P', new ItemStack(Blocks.STICKY_PISTON), 'R', new ItemStack(Blocks.HOPPER), 'E', OrePrefix.circuit.get(Materials.Basic)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.cutter, 1));

                        if(Loader.isModLoaded("GraviSuite")){
                                GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1));
                                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1), new Object[]{"PJP","BLB","WCW",'P', OrePrefix.plateAlloy.get(Materials.Carbon),'J',GT_ModHandler.getIC2Item(ItemName.jetpack_electric, 1),'B',GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1, 6),'L',GT_ModHandler.getModItem("GraviSuite", "advLappack", 1),'W', OrePrefix.wireGt04.get(Materials.Platinum),'C', OrePrefix.circuit.get(Materials.Advanced)});
                                GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 3, 1));
                                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 3, 1), new Object[]{"CCC","WWW","CCC",'C',GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1),'W', OrePrefix.wireGt01.get(Materials.Superconductor)});
                        }
                } else {
                        GT_ModHandler.addCraftingRecipe(ItemCable.getCable(CableType.glass, 0), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GGG", "EDE", "GGG", 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'D', OrePrefix.dust.get(Materials.Silver), 'E', ItemList.IC2_Energium_Dust.get(1, new Object[0])});
                }

                if(Loader.isModLoaded("ImmersiveEngineering")){
                        GT_ModHandler.removeRecipeByOutput(OreDictionaryUnifier.get(OrePrefix.stick, Materials.Iron, 4));
                        GT_ModHandler.removeRecipeByOutput(OreDictionaryUnifier.get(OrePrefix.stick, Materials.Steel, 4));
                        GT_ModHandler.removeRecipeByOutput(OreDictionaryUnifier.get(OrePrefix.stick, Materials.Aluminium, 4));
                }

                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.uranium_fuel_rod, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"UUU", "NNN", "UUU", 'U', OrePrefix.ingot.get(Materials.Uranium), 'N', OrePrefix.nugget.get(Materials.Uranium235)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.mox_fuel_rod, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"UUU", "NNN", "UUU", 'U', OrePrefix.ingot.get(Materials.Uranium), 'N', OrePrefix.ingot.get(Materials.Plutonium)});

                if (!GregTech_API.mIC2Classic) {
                        GT_ModHandler.addCraftingRecipe(ItemList.Uraniumcell_2.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "   ", "   ", 'R', ItemList.Uraniumcell_1, 'P', OrePrefix.plate.get(Materials.Iron)});
                        GT_ModHandler.addCraftingRecipe(ItemList.Uraniumcell_4.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "CPC", "RPR", 'R', ItemList.Uraniumcell_1, 'P', OrePrefix.plate.get(Materials.Iron), 'C', OrePrefix.plate.get(Materials.Copper)});
                        GT_ModHandler.addCraftingRecipe(ItemList.Moxcell_2.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "   ", "   ", 'R', ItemList.Moxcell_1, 'P', OrePrefix.plate.get(Materials.Iron)});
                        GT_ModHandler.addCraftingRecipe(ItemList.Moxcell_4.get(1, new Object[0]), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "CPC", "RPR", 'R', ItemList.Moxcell_1, 'P', OrePrefix.plate.get(Materials.Iron), 'C', OrePrefix.plate.get(Materials.Copper)});

                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.mining_laser, 1).copy());
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P', OrePrefix.plate.get(Materials.Titanium),'G', OrePrefix.gemExquisite.get(Materials.Diamond),'E',ItemList.Emitter_HV,'C', OrePrefix.circuit.get(Materials.Master),'S', OrePrefix.screw.get(Materials.Titanium),'B',new ItemStack(GT_ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1, GT_Values.W)});
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P', OrePrefix.plate.get(Materials.Titanium),'G', OrePrefix.gemExquisite.get(Materials.Ruby),'E',ItemList.Emitter_HV,'C', OrePrefix.circuit.get(Materials.Master),'S', OrePrefix.screw.get(Materials.Titanium),'B',new ItemStack(GT_ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1,GT_Values.W)});
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P', OrePrefix.plate.get(Materials.Titanium),'G', OrePrefix.gemExquisite.get(Materials.Jasper),'E',ItemList.Emitter_HV,'C', OrePrefix.circuit.get(Materials.Master),'S', OrePrefix.screw.get(Materials.Titanium),'B',new ItemStack(GT_ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1,GT_Values.W)});
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P', OrePrefix.plate.get(Materials.Titanium),'G', OrePrefix.gemExquisite.get(Materials.GarnetRed),'E',ItemList.Emitter_HV,'C', OrePrefix.circuit.get(Materials.Master),'S', OrePrefix.screw.get(Materials.Titanium),'B',new ItemStack(GT_ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1,GT_Values.W)});
                }

                GT_ModHandler.removeRecipeByOutput(ItemList.IC2_Energium_Dust.get(1, new Object[0]));
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.gregtechrecipes, "energycrystalruby", true)) {
                        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Energium_Dust.get(9L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RDR", "DRD", "RDR", 'R', OrePrefix.dust.get(Materials.Redstone), 'D', OrePrefix.dust.get(Materials.Ruby)});
                } else {
                        GT_ModHandler.addCraftingRecipe(ItemList.IC2_Energium_Dust.get(9L, new Object[0]), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RDR", "DRD", "RDR", 'R', OrePrefix.dust.get(Materials.Redstone), 'D', OrePrefix.dust.get(Materials.Diamond)});
                }
                GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.lapotron_crystal, 1));
                for(Materials tCMat : new Materials[]{Materials.Lapis, Materials.Lazurite, Materials.Sodalite}){
                        GT_ModHandler.addShapelessCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.lapotron_crystal, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefix.gemExquisite.get(Materials.Sapphire), OrePrefix.stick.get(tCMat),ItemList.Circuit_Parts_Wiring_Elite});
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.lapotron_crystal, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"LCL", "RSR", "LCL", 'C', OrePrefix.circuit.get(Materials.Data), 'S', GT_ModHandler.getIC2Item(ItemName.energy_crystal, 1, 32767), 'L', OrePrefix.plate.get(tCMat), 'R', OrePrefix.stick.get(tCMat)});
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.lapotron_crystal, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"LCL", "RSR", "LCL", 'C', OrePrefix.circuit.get(Materials.Advanced), 'S', OrePrefix.gemFlawless.get(Materials.Sapphire), 'L', OrePrefix.plate.get(tCMat), 'R', OrePrefix.stick.get(tCMat)});
                }
                GT_ModHandler.removeRecipe(GT_ModHandler.getIC2Item(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe, 8));
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe, 1), new Object[]{"hPf",'P', OrePrefix.pipeSmall.get(Materials.Steel)});
                GT_Values.RA.addWiremillRecipe(OreDictionaryUnifier.get(OrePrefix.pipeTiny, Materials.Steel, 1), GT_ModHandler.getIC2Item(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe, 1), 200, 16);

                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.luminator_flat, 16), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RTR", "GHG", "GGG", 'H', OrePrefix.cell.get(Materials.Helium), 'T', OrePrefix.ingot.get(Materials.Tin), 'R', OrePrefix.ingot.get(Materials.AnyIron), 'G', new ItemStack(Blocks.GLASS, 1)});
                GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.luminator_flat, 16), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RTR", "GHG", "GGG", 'H', OrePrefix.cell.get(Materials.Mercury), 'T', OrePrefix.ingot.get(Materials.Tin), 'R', OrePrefix.ingot.get(Materials.AnyIron), 'G', new ItemStack(Blocks.GLASS, 1)});

                GT_ModHandler.removeRecipe(new ItemStack[]{tStack = OreDictionaryUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), tStack, tStack, tStack, new ItemStack(Items.COAL, 1, 0), tStack, tStack, tStack, tStack});
                GT_ModHandler.removeRecipe(new ItemStack[]{tStack = OreDictionaryUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), tStack, tStack, tStack, new ItemStack(Items.COAL, 1, 1), tStack, tStack, tStack, tStack});
                GT_ModHandler.removeRecipe(new ItemStack[]{null, tStack = new ItemStack(Items.COAL, 1), null, tStack, OreDictionaryUnifier.get(OrePrefix.ingot, Materials.Iron, 1), tStack, null, tStack, null});

                GT_ModHandler.removeFurnaceSmelting(new ItemStack(Blocks.HOPPER));

                GTLog.out.println("GT_Mod: Applying harder Recipes for several Blocks.");
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "blockbreaker", false)) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Items.IRON_PICKAXE, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.PISTON, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Items.REDSTONE, 1), new ItemStack(Blocks.COBBLESTONE, 1)}), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RGR", "RPR", "RCR", 'G', OreDictNames.craftingGrinder, 'C', OrePrefix.circuit.get(Materials.Advanced), 'R', OrePrefix.plate.get(Materials.Steel), 'P', OreDictNames.craftingPiston});
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "beryliumreflector", true)) &&
                        (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1, 1)))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" N ", "NBN", " N ", 'B', OrePrefix.plateDouble.get(Materials.Beryllium), 'N', GT_ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1)});
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" B ", "NCN", " B ", 'B', OrePrefix.plate.get(Materials.Beryllium), 'N', GT_ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1), 'C', OrePrefix.plate.get(Materials.TungstenCarbide)});
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "reflector", true)) &&
                        (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1)))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"TGT", "GSG", "TGT", 'T', OrePrefix.plate.get(Materials.Tin), 'G', OrePrefix.dust.get(Materials.Graphite), 'S', OrePrefix.plateDouble.get(Materials.Steel)});
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"TTT", "GSG", "TTT", 'T', OrePrefix.plate.get(Materials.TinAlloy), 'G', OrePrefix.dust.get(Materials.Graphite), 'S', OrePrefix.plate.get(Materials.Beryllium)});
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "cropharvester", true)) &&
                        (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.crop_harvester, 1)))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.crop_harvester, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"ACA", "PMS", "WOW", 'M', ItemList.Hull_HV, 'C', OrePrefix.circuit.get(Materials.Master), 'A', ItemList.Robot_Arm_HV, 'P', ItemList.Electric_Piston_HV, 'S', ItemList.Sensor_HV, 'W', OrePrefix.toolHeadSense.get(Materials.StainlessSteel), 'O', ItemList.Conveyor_Module_HV});
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "nuclearReactor", true)) &&
                        (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.nuclear_reactor, 1)))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2TEItem(TeBlock.nuclear_reactor, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PMP", "PAP", 'P', OrePrefix.plateDense.get(Materials.Lead), 'C', OrePrefix.circuit.get(Materials.Master), 'M', GT_ModHandler.getIC2TEItem(TeBlock.reactor_chamber, 1), 'A', ItemList.Robot_Arm_EV});

                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.reactor_chamber, 1));
                        GT_Values.RA.addAssemblerRecipe(ItemList.Hull_EV.get(1, new Object[0]), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Lead, 4), GT_ModHandler.getIC2TEItem(TeBlock.reactor_chamber, 1), 200, 256);

                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1));
                        GT_Values.RA.addChemicalBathRecipe(OreDictionaryUnifier.get(OrePrefix.frameGt, Materials.Lead, 1), Materials.Concrete.getMolten(144), GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), GT_Values.NI, GT_Values.NI, null, 400, 80);

                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.reactor_access_hatch, 1));
                        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), ItemList.Conveyor_Module_EV.get(1, new Object[0]), GT_ModHandler.getIC2TEItem(TeBlock.reactor_access_hatch, 1), 200, 80);

                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.reactor_fluid_port, 1));
                        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), ItemList.Electric_Pump_EV.get(1, new Object[0]), GT_ModHandler.getIC2TEItem(TeBlock.reactor_fluid_port, 1), 200, 80);

                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2TEItem(TeBlock.reactor_redstone_port, 1));
                        GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Master, 1), GT_ModHandler.getIC2TEItem(TeBlock.reactor_redstone_port, 1), 200, 80);
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "rtg", true)) &&
                        (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(BlockName.te, TeBlock.rt_generator, 1)))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(BlockName.te, TeBlock.rt_generator, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"III", "IMI", "ICI", 'I', ItemList.IC2_Item_Casing_Steel, 'C', OrePrefix.circuit.get(Materials.Master), 'M', ItemList.Hull_IV});

                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(BlockName.te, TeBlock.rt_heat_generator, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(BlockName.te, TeBlock.rt_heat_generator, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"III", "IMB", "ICI", 'I', ItemList.IC2_Item_Casing_Steel, 'C', OrePrefix.circuit.get(Materials.Master), 'M', ItemList.Hull_IV, 'B', OreDictionaryUnifier.get(OrePrefix.block, Materials.Copper, 1)});
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "windRotor", true)) &&
                        (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.rotor_carbon, 1)))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.rotor_carbon, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_rotor_blade, 1), 'S', OrePrefix.screw.get(Materials.Iridium), 'T', GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.steel_shaft, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.rotor_steel, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.rotor_steel, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.steel_rotor_blade, 1), 'S', OrePrefix.screw.get(Materials.StainlessSteel), 'T', GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.iron_shaft, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.rotor_iron, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.rotor_iron, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.iron_rotor_blade, 1), 'S', OrePrefix.screw.get(Materials.WroughtIron), 'T', GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.iron_shaft, 1)});
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.rotor_wood, 1));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.rotor_wood, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.wood_rotor_blade, 1), 'S', OrePrefix.screw.get(Materials.WroughtIron), 'T', OrePrefix.stickLong.get(Materials.WroughtIron)});
                }
                if (OreDictionaryUnifier.get(OrePrefix.gear, Materials.Diamond, 1) != null) {
                        tStack = GT_ModHandler.getRecipeOutput(new ItemStack[]{OreDictionaryUnifier.get(OrePrefix.gear, Materials.Iron, 1), new ItemStack(Items.REDSTONE, 1), OreDictionaryUnifier.get(OrePrefix.gear, Materials.Iron, 1), OreDictionaryUnifier.get(OrePrefix.gear, Materials.Gold, 1), OreDictionaryUnifier.get(OrePrefix.gear, Materials.Iron, 1), OreDictionaryUnifier.get(OrePrefix.gear, Materials.Gold, 1), OreDictionaryUnifier.get(OrePrefix.gear, Materials.Diamond, 1), new ItemStack(Items.DIAMOND_PICKAXE, 1), OreDictionaryUnifier.get(OrePrefix.gear, Materials.Diamond, 1)});
                        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "quarry", true)) {
                                GT_ModHandler.removeRecipeByOutput(tStack);
                                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"ICI", "GIG", "DPD", 'C', OrePrefix.circuit.get(Materials.Advanced), 'D', OrePrefix.gear.get(Materials.Diamond), 'G', OrePrefix.gear.get(Materials.Gold), 'I', OrePrefix.gear.get(Materials.Steel), 'P', GT_ModHandler.getIC2Item(ItemName.diamond_drill, 1, true)});
                        }
                        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "quarry", false)) {
                                GT_ModHandler.removeRecipeByOutput(tStack);
                        }
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "sugarpaper", true))) {
                        GT_ModHandler.removeRecipeByOutput(new ItemStack(Items.PAPER));
                        GT_ModHandler.removeRecipeByOutput(new ItemStack(Items.SUGAR));
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Paper, 2), new Object[]{"SSS", " m ", 'S', new ItemStack(Items.REEDS)});
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Sugar, 1), new Object[]{"Sm ", 'S', new ItemStack(Items.REEDS)});
                        //ItemStack brick = new ItemStack(new ItemStack(Blocks.stone_slab).getItem().setContainerItem(new ItemStack(Blocks.stone_slab).getItem()));
<<<<<<< Updated upstream
                        //GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.paper, Materials.Empty, 2), new Object[]{" C ", "SSS", " C ", 'S', OreDictionaryUnifier.get(OrePrefix.dust, Materials.Paper, 1), 'C', brick});
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.paper, Materials.Empty, 2), new Object[]{" C ", "SSS", " C ", 'S', OreDictionaryUnifier.get(OrePrefix.dust, Materials.Paper, 1), 'C', new ItemStack(Blocks.STONE_SLAB)});
                        //GameRegistry.addRecipe(OreDictionaryUnifier.get(OrePrefix.paper, Materials.Empty, 2), " C ", "SSS", " C ", 'S', OreDictionaryUnifier.get(OrePrefix.dust, Materials.Paper, 1), 'C', brick);
=======
                        //GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefixes.paper, Materials.Empty, 2), new Object[]{" C ", "SSS", " C ", 'S', OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Paper, 1), 'C', brick});
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.paper, Materials.Empty, 2), new Object[]{" C ", "SSS", " C ", 'S', OreDictionaryUnifier.get(OrePrefix.dust, Materials.Paper, 1), 'C', new ItemStack(Blocks.STONE_SLAB)});
                        //GameRegistry.addRecipe(OreDictionaryUnifier.get(OrePrefixes.paper, Materials.Empty, 2), " C ", "SSS", " C ", 'S', OreDictionaryUnifier.get(OrePrefixes.dust, Materials.Paper, 1), 'C', brick);
>>>>>>> Stashed changes
                }

                GTLog.out.println("GT_Mod: Applying Recipes for Tools");
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "nanosaber", true)) &&
                        (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.nano_saber, 1)))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.nano_saber, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PI ", "PI ", "CLC", 'L', OrePrefix.battery.get(Materials.Master), 'I', OrePrefix.plateAlloy.get("Iridium"), 'P', OrePrefix.plate.get(Materials.Platinum), 'C', OrePrefix.circuit.get(Materials.Master)});
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "namefix", true)) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.removeRecipeByOutput(new ItemStack(Items.FLINT_AND_STEEL, 1)) ? new ItemStack(Items.FLINT_AND_STEEL, 1) : null, GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"S ", " F", 'F', new ItemStack(Items.FLINT, 1), 'S', "nuggetSteel"});
                }
                if (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.diamond_drill, 1))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.diamond_drill, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" D ", "DMD", "TAT", 'M', GT_ModHandler.getIC2Item(ItemName.diamond_drill, 1, true), 'D', OreDictNames.craftingIndustrialDiamond, 'T', OrePrefix.plate.get(Materials.Titanium), 'A', OrePrefix.circuit.get(Materials.Advanced)});
                }
                if (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.drill, 1))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.drill, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" S ", "SCS", "SBS", 'C', OrePrefix.circuit.get(Materials.Basic), 'B', OrePrefix.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefix.plate.get(Materials.StainlessSteel) : OrePrefix.plate.get(Materials.Iron)});
                }
                if (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.chainsaw, 1))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.chainsaw, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"BS ", "SCS", " SS", 'C', OrePrefix.circuit.get(Materials.Basic), 'B', OrePrefix.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefix.plate.get(Materials.StainlessSteel) : OrePrefix.plate.get(Materials.Iron)});
                }
                if (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.electric_hoe, 1))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.electric_hoe, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"SS ", " C ", " B ", 'C', OrePrefix.circuit.get(Materials.Basic), 'B', OrePrefix.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefix.plate.get(Materials.StainlessSteel) : OrePrefix.plate.get(Materials.Iron)});
                }
                if (GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.electric_treetap, 1))) {
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(ItemName.electric_treetap, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" B ", "SCS", "S  ", 'C', OrePrefix.circuit.get(Materials.Basic), 'B', OrePrefix.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefix.plate.get(Materials.StainlessSteel) : OrePrefix.plate.get(Materials.Iron)});
                }
                GTLog.out.println("GT_Mod: Removing Q-Armor Recipes if configured.");
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QHelmet", false)) {
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.quantum_helmet, 1));
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QPlate", false)) {
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.quantum_chestplate, 1));
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QLegs", false)) {
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.quantum_leggings, 1));
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QBoots", false)) {
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getIC2Item(ItemName.quantum_boots, 1));
                }

                if(Loader.isModLoaded("GraviSuite")){
                        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GT_Values.W));
                        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GT_Values.W), new Object[]{"CJC","CNC","WPW",'C', OrePrefix.plateAlloy.get(Materials.Carbon),'J',GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1, GT_Values.W),'N',GT_ModHandler.getIC2Item(ItemName.nano_chestplate, 1),'W', OrePrefix.wireGt04.get(Materials.Platinum),'P', OrePrefix.circuit.get(Materials.Elite)});
                }

                long bits =  GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE;
                GT_ModHandler.addCraftingRecipe(ItemList.ModularBasicHelmet.		getWildcard(1, new Object[0]),bits, new Object[] { "AAA", "B B", 'A', 			new ItemStack(Items.LEATHER, 1, 32767), 'B', OrePrefix.ring.get(Materials.AnyIron)} );
                GT_ModHandler.addCraftingRecipe(ItemList.ModularBasicChestplate.	getWildcard(1, new Object[0]),bits, new Object[] { "A A", "BAB", "AAA", 'A', 	new ItemStack(Items.LEATHER, 1, 32767), 'B', OrePrefix.ring.get(Materials.AnyIron)} );
                GT_ModHandler.addCraftingRecipe(ItemList.ModularBasicLeggings.		getWildcard(1, new Object[0]),bits, new Object[] { "BAB", "A A", "A A", 'A', 	new ItemStack(Items.LEATHER, 1, 32767), 'B', OrePrefix.ring.get(Materials.AnyIron)} );
                GT_ModHandler.addCraftingRecipe(ItemList.ModularBasicBoots.			getWildcard(1, new Object[0]),bits, new Object[] { "A A", "B B", "A A", 'A', 	new ItemStack(Items.LEATHER, 1, 32767), 'B', OrePrefix.ring.get(Materials.AnyIron)} );
                GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric1Helmet.	getWildcard(1, new Object[0]),bits, new Object[] { "ACA", "B B", 'A', 			OrePrefix.stick.get(Materials.Aluminium), 'B', OrePrefix.plate.get(Materials.Steel), 'C', OrePrefix.battery.get(Materials.Advanced)});
                GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric1Chestplate.getWildcard(1, new Object[0]),bits, new Object[] { "A A", "BCB", "AAA", 'A', 	OrePrefix.stick.get(Materials.Aluminium), 'B', OrePrefix.plate.get(Materials.Steel), 'C', OrePrefix.battery.get(Materials.Advanced)});
                GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric1Leggings.	getWildcard(1, new Object[0]),bits, new Object[] { "BCB", "A A", "A A", 'A', 	OrePrefix.stick.get(Materials.Aluminium), 'B', OrePrefix.plate.get(Materials.Steel), 'C', OrePrefix.battery.get(Materials.Advanced)});
                GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric1Boots.		getWildcard(1, new Object[0]),bits, new Object[] { "A A", "BCB", "A A", 'A', 	OrePrefix.stick.get(Materials.Aluminium), 'B', OrePrefix.plate.get(Materials.Steel), 'C', OrePrefix.battery.get(Materials.Advanced)});
                GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric2Helmet.	getWildcard(1, new Object[0]),bits, new Object[] { "ACA", "B B", 'A', 			OrePrefix.stick.get(Materials.TungstenSteel), 'B', OrePrefix.plateAlloy.get(Materials.Carbon),'C', OrePrefix.battery.get(Materials.Master)});
                GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric2Chestplate.getWildcard(1, new Object[0]),bits, new Object[] { "A A", "BCB", "AAA", 'A', 	OrePrefix.stick.get(Materials.TungstenSteel), 'B', OrePrefix.plateAlloy.get(Materials.Carbon),'C', OrePrefix.battery.get(Materials.Master)});
                GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric2Leggings.	getWildcard(1, new Object[0]),bits, new Object[] { "BCB", "A A", "A A", 'A', 	OrePrefix.stick.get(Materials.TungstenSteel), 'B', OrePrefix.plateAlloy.get(Materials.Carbon),'C', OrePrefix.battery.get(Materials.Master)});
                GT_ModHandler.addCraftingRecipe(ItemList.ModularElectric2Boots.		getWildcard(1, new Object[0]),bits, new Object[] { "A A", "BCB", "A A", 'A', 	OrePrefix.stick.get(Materials.TungstenSteel), 'B', OrePrefix.plateAlloy.get(Materials.Carbon),'C', OrePrefix.battery.get(Materials.Master)});
        }
}