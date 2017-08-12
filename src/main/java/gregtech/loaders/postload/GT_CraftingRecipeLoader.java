package gregtech.loaders.postload;

import gregtech.GT_Mod;
import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.items.OreDictNames;
import gregtech.api.items.ToolDictNames;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.items.ItemList;
import gregtech.api.util.GTLog;
import gregtech.api.unification.GT_OreDictUnificator;
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
                ModHandler.addCraftingRecipe(new ItemStack(Items.BUCKET, 1), ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"XhX", " X ", 'X', OrePrefixes.plate.get(Materials.AnyIron)});
                if (!GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Bucket", true)) {
                        ModHandler.addCraftingRecipe(new ItemStack(Items.BUCKET, 1), ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED, new Object[]{aTextIron1, " X ", 'X', OrePrefixes.ingot.get(Materials.AnyIron)});
                }
                ItemStack tMat = new ItemStack(Items.IRON_INGOT);
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.PressurePlate", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, null, null, null, null, null, null, null}))) {
                                ModHandler.addCraftingRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XXh", 'X', OrePrefixes.plate.get(Materials.AnyIron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.AnyIron)});
                        }
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Door", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, null, tMat, tMat, null, tMat, tMat, null}))) {
                                ModHandler.addCraftingRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XX ", "XXh", "XX ", 'X', OrePrefixes.plate.get(Materials.AnyIron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.AnyIron)});
                        }
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Cauldron", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, null, tMat, tMat, null, tMat, tMat, tMat, tMat}))) {
                                ModHandler.addCraftingRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{aTextIron1, "XhX", aTextIron2, 'X', OrePrefixes.plate.get(Materials.AnyIron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.AnyIron)});
                        }
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Hopper", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, null, tMat, tMat, new ItemStack(Blocks.CHEST, 1, 0), tMat, null, tMat, null}))) {
                                ModHandler.addCraftingRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XwX", "XCX", " X ", 'X', OrePrefixes.plate.get(Materials.AnyIron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.AnyIron), 'C', "craftingChest"});
                        }
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Bars", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, tMat, tMat, tMat, tMat, null, null, null}))) {
                                tStack.stackSize /= 2;
                                ModHandler.addCraftingRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{" w ", aTextIron2, aTextIron2, 'X', OrePrefixes.stick.get(Materials.AnyIron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.AnyIron)});
                        }
                }
                ModHandler.addCraftingRecipe(ModHandler.getIC2Item(BlockName.fence, BlockIC2Fence.IC2FenceType.iron, 6), ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextIron2, aTextIron2, " w ", 'X', OrePrefixes.stick.get(Materials.AnyIron), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.AnyIron)});

                tMat = new ItemStack(Items.GOLD_INGOT);
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Gold.PressurePlate", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, null, null, null, null, null, null, null}))) {
                                ModHandler.addCraftingRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XXh", 'X', OrePrefixes.plate.get(Materials.Gold), 'S', OrePrefixes.stick.get(Materials.Wood), 'I', OrePrefixes.ingot.get(Materials.Gold)});
                        }
                }
                tMat = GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Rubber, 1);
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Rubber.Sheet", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, tMat, tMat, tMat, tMat, null, null, null}))) {
                                ModHandler.addCraftingRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{aTextIron2, aTextIron2, 'X', OrePrefixes.plate.get(Materials.Rubber)});
                        }
                }
                ModHandler.removeRecipeByOutput(ItemList.Bottle_Empty.get(1));
                ModHandler.removeRecipeByOutput(ItemList.IC2_Spray_WeedEx.get(1));
                ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.re_battery, 1));
                ModHandler.removeRecipeByOutput(new ItemStack(Blocks.TNT));
                ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.dynamite, 1));
                ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(BlockName.te, TeBlock.itnt, 1));

                ItemStack tStack = ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.PLANKS, 1, 0), null, null, new ItemStack(Blocks.PLANKS, 1, 0)});
                if (tStack != null) {
                        ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, tStack), ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED, new Object[]{"s", "P", "P", 'P', OrePrefixes.plank.get(Materials.Wood)});
                        ModHandler.addCraftingRecipe(GT_Utility.copyAmount(GT_Mod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize / 2 : tStack.stackSize, tStack), ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED, new Object[]{"P", "P", 'P', OrePrefixes.plank.get(Materials.Wood)});
                }
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PP", 'P', OrePrefixes.plank.get(Materials.Wood)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.STONE_BUTTON, 2, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"S", "S", 'S', OrePrefixes.stone});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"SS", 'S', OrePrefixes.stone});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.STONE_BUTTON, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.stone});

                GTLog.out.println("GT_Mod: Adding Vanilla Convenience Recipes.");

                ModHandler.addCraftingRecipe(new ItemStack(Blocks.STONEBRICK, 1, 3), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"f", "X", 'X', new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.GRAVEL, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(Blocks.COBBLESTONE, 1, 0)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.COBBLESTONE, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(Blocks.STONE, 1, 0)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.STONEBRICK, 1, 2), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(Blocks.STONEBRICK, 1, 0)});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 0)});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 0)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.COBBLESTONE, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 3)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.BRICK_BLOCK, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 4)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 5)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.NETHER_BRICK, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 6)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 7)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 8)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 0)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 1)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 2), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 2)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 3), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 3)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 4), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 4)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 5), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 5)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 6), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 6)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PLANKS, 1, 7), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 7)});

                ModHandler.addCraftingRecipe(new ItemStack(Items.STICK, 2, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"s", "X", 'X', new ItemStack(Blocks.DEADBUSH, 1, 32767)});
                ModHandler.addCraftingRecipe(new ItemStack(Items.STICK, 2, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"s", "X", 'X', new ItemStack(Blocks.TALLGRASS, 1, 0)});
                ModHandler.addCraftingRecipe(new ItemStack(Items.STICK, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"s", "X", 'X', OrePrefixes.treeSapling});

                ModHandler.addCraftingRecipe(new ItemStack(Items.COMPARATOR, 1, 0), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" T ", "TQT", "SSS", 'Q', OreDictNames.craftingQuartz, 'S', OrePrefixes.stoneSmooth, 'T', OreDictNames.craftingRedstoneTorch});

                GTLog.out.println("GT_Mod: Adding Tool Recipes.");
                ModHandler.addCraftingRecipe(new ItemStack(Items.MINECART, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{" h ", "PwP", "WPW", 'P', OrePrefixes.plate.get(Materials.AnyIron), 'W', ItemList.Component_Minecart_Wheels_Iron});
                ModHandler.addCraftingRecipe(new ItemStack(Items.MINECART, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" h ", "PwP", "WPW", 'P', OrePrefixes.plate.get(Materials.Steel), 'W', ItemList.Component_Minecart_Wheels_Steel});

                ModHandler.addCraftingRecipe(new ItemStack(Items.CHEST_MINECART, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', OreDictNames.craftingChest});
                ModHandler.addCraftingRecipe(new ItemStack(Items.FURNACE_MINECART, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', OreDictNames.craftingFurnace});
                ModHandler.addCraftingRecipe(new ItemStack(Items.HOPPER_MINECART, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', new ItemStack(Blocks.HOPPER, 1, 32767)});
                ModHandler.addCraftingRecipe(new ItemStack(Items.TNT_MINECART, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', new ItemStack(Blocks.TNT, 1, 32767)});

                ModHandler.addCraftingRecipe(new ItemStack(Items.CHAINMAIL_HELMET, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"RRR", "RhR", 'R', OrePrefixes.ring.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(new ItemStack(Items.CHAINMAIL_CHESTPLATE, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"RhR", "RRR", "RRR", 'R', OrePrefixes.ring.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(new ItemStack(Items.CHAINMAIL_LEGGINGS, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"RRR", "RhR", "R R", 'R', OrePrefixes.ring.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(new ItemStack(Items.CHAINMAIL_BOOTS, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"R R", "RhR", 'R', OrePrefixes.ring.get(Materials.Steel)});

                GTLog.out.println("GT_Mod: Adding Wool and Color releated Recipes.");
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeOrange});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 2), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeMagenta});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 3), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeLightBlue});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 4), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeYellow});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 5), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeLime});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 6), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyePink});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 7), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeGray});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 8), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeLightGray});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 9), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeCyan});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 10), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyePurple});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 11), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeBlue});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 12), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeBrown});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 13), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeGreen});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 14), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeRed});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Blocks.WOOL, 1, 15), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeBlack});

                ModHandler.addCraftingRecipe(new ItemStack(Blocks.STAINED_GLASS, 8, 0), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GGG", "GDG", "GGG", 'G', new ItemStack(Blocks.GLASS, 1), 'D', Dyes.dyeWhite});

                GTLog.out.println("GT_Mod: Putting a Potato on a Stick.");
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Packaged_PotatoChips.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.foil.get(Materials.Aluminium), ItemList.Food_PotatoChips});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Packaged_ChiliChips.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.foil.get(Materials.Aluminium), ItemList.Food_ChiliChips});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Packaged_Fries.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.plateDouble.get(Materials.Paper), ItemList.Food_Fries});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Chum_On_Stick.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.stick.get(Materials.Wood), ItemList.Food_Chum});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Potato_On_Stick.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.stick.get(Materials.Wood), ItemList.Food_Raw_Potato});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Potato_On_Stick_Roasted.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.stick.get(Materials.Wood), ItemList.Food_Baked_Potato});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.bucket.get(Materials.Water), OrePrefixes.dust.get(Materials.Wheat)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough_Sugar.get(2), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", OrePrefixes.dust.get(Materials.Sugar)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough_Chocolate.get(2), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", OrePrefixes.dust.get(Materials.Cocoa)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Dough_Chocolate.get(2), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", OrePrefixes.dust.get(Materials.Chocolate)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Flat_Dough.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", ToolDictNames.craftingToolRollingPin});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Bun.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough"});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Bread.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", "foodDough"});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Baguette.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"foodDough", "foodDough", "foodDough"});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Cake.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Dough_Sugar, ItemList.Food_Dough_Sugar, ItemList.Food_Dough_Sugar, ItemList.Food_Dough_Sugar});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_ChiliChips.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_PotatoChips, OrePrefixes.dust.get(Materials.Chili)});

                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Buns.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Breads.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Baguettes.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Bun.get(2, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Bread.get(2, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sliced_Baguette.get(2, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes});

                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Veggie.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Cheese.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Meat.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, OrePrefixes.dust.get(Materials.MeatCooked)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Chum.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Buns, ItemList.Food_Chum});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Veggie.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Cheese.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Meat.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, OrePrefixes.dust.get(Materials.MeatCooked)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Burger_Chum.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bun, ItemList.Food_Sliced_Bun, ItemList.Food_Chum});

                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Veggie.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Cheese.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Bacon.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, new ItemStack(Items.COOKED_PORKCHOP, 1)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Steak.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Breads, new ItemStack(Items.COOKED_BEEF, 1)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Veggie.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Cheese.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Bacon.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, new ItemStack(Items.COOKED_PORKCHOP, 1)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Sandwich_Steak.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Bread, ItemList.Food_Sliced_Bread, new ItemStack(Items.COOKED_BEEF, 1)});

                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Veggie.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Cheese.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Bacon.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, new ItemStack(Items.COOKED_PORKCHOP, 1), new ItemStack(Items.COOKED_PORKCHOP, 1)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Steak.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguettes, new ItemStack(Items.COOKED_BEEF, 1), new ItemStack(Items.COOKED_BEEF, 1)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Veggie.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Cheese.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Bacon.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, new ItemStack(Items.COOKED_PORKCHOP, 1), new ItemStack(Items.COOKED_PORKCHOP, 1)});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Large_Sandwich_Steak.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Sliced_Baguette, ItemList.Food_Sliced_Baguette, new ItemStack(Items.COOKED_BEEF, 1), new ItemStack(Items.COOKED_BEEF, 1)});

                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Pizza_Veggie.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Flat_Dough, ItemList.Food_Sliced_Cucumber, ItemList.Food_Sliced_Tomato, ItemList.Food_Sliced_Onion});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Pizza_Cheese.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Flat_Dough, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese, ItemList.Food_Sliced_Cheese});
                ModHandler.addShapelessCraftingRecipe(ItemList.Food_Raw_Pizza_Meat.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Food_Flat_Dough, OrePrefixes.dust.get(Materials.MeatCooked)});

                ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Cheese.get(4, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "foodCheese"});
                ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Lemon.get(4, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropLemon"});
                ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Tomato.get(4, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropTomato"});
                ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Onion.get(4, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropOnion"});
                ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Cucumber.get(4, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropCucumber"});
                ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Bun.get(2, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Baked_Bun});
                ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Bread.get(2, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Baked_Bread});
                ModHandler.addCraftingRecipe(ItemList.Food_Sliced_Baguette.get(2, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Baked_Baguette});
                ModHandler.addCraftingRecipe(ItemList.Food_Raw_PotatoChips.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', "cropPotato"});
                ModHandler.addCraftingRecipe(ItemList.Food_Raw_Cookie.get(4, ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"kX", 'X', ItemList.Food_Dough_Chocolate});

                ModHandler.addCraftingRecipe(ItemList.Food_Raw_Fries.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"k", "X", 'X', "cropPotato"});
                ModHandler.addCraftingRecipe(new ItemStack(Items.BOWL, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"k", "X", 'X', OrePrefixes.plank.get(Materials.Wood)});
                ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"k", "X", 'X', OrePrefixes.plate.get(Materials.Rubber)});
                ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.toolHeadArrow, Materials.Flint, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"f", "X", 'X', new ItemStack(Items.FLINT, 1, 32767)});

                ModHandler.addCraftingRecipe(new ItemStack(Items.ARROW, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES, new Object[]{"  H", " S ", "F  ", 'H', new ItemStack(Items.FLINT, 1, 32767), 'S', OrePrefixes.stick.get(Materials.Wood), 'F', OreDictNames.craftingFeather});

                ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.PLANKS), null, new ItemStack(Blocks.PLANKS), null, new ItemStack(Blocks.PLANKS)});
                ModHandler.removeRecipeByOutput(ItemList.Food_Baked_Bread.get(1));
                ModHandler.removeRecipeByOutput(new ItemStack(Items.COOKIE, 1));
                ModHandler.removeRecipe(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1)});
                if (null != GT_Utility.setStack(ModHandler.getRecipeOutput(true, new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1), null, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Copper, 1), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Tin, 1)}), GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Bronze, GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "bronzeingotcrafting", true) ? 1 : 2)) {
                        GTLog.out.println("GT_Mod: Changed Forestrys Bronze Recipe");
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "enchantmenttable", false)) {
                        GTLog.out.println("GT_Mod: Removing the Recipe of the Enchantment Table, to have more Fun at enchanting with the Anvil and Books from Dungeons.");
                        ModHandler.removeRecipeByOutput(new ItemStack(Blocks.ENCHANTING_TABLE, 1));
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "enderchest", false)) {
                        ModHandler.removeRecipeByOutput(new ItemStack(Blocks.ENDER_CHEST, 1));
                }
                tStack = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ash, 1);
                ModHandler.addCraftingRecipe(ModHandler.getRecipeOutput(new ItemStack[]{null, new ItemStack(Blocks.SAND, 1, 0), null, null, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Apatite, 1), null, null, new ItemStack(Blocks.SAND, 1, 0), null}), new Object[]{"S", "A", "S", 'A', OrePrefixes.dust.get(Materials.Apatite), 'S', new ItemStack(Blocks.SAND, 1, 32767)});
                ModHandler.addCraftingRecipe(ModHandler.getRecipeOutput(new ItemStack[]{tStack, tStack, tStack, tStack, GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Apatite, 1), tStack, tStack, tStack, tStack}), new Object[]{"SSS", "SAS", "SSS", 'A', OrePrefixes.dust.get(Materials.Apatite), 'S', OrePrefixes.dust.get(Materials.Ash)});

                GTLog.out.println("GT_Mod: Adding Mixed Metal Ingot Recipes.");
                ModHandler.removeRecipeByOutput(ItemList.IC2_Mixed_Metal_Ingot.get(1));

                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.AnyIron), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.AnyIron), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.AnyIron), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.AnyIron), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.AnyIron), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.AnyIron), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Nickel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Invar), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Steel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.StainlessSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Titanium), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(4, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.Tungsten), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(6, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Bronze), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Zinc)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Mixed_Metal_Ingot.get(6, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"X", "Y", "Z", 'X', OrePrefixes.plate.get(Materials.TungstenSteel), 'Y', OrePrefixes.plate.get(Materials.Brass), 'Z', OrePrefixes.plate.get(Materials.Aluminium)});

                GTLog.out.println("GT_Mod: Adding Rolling Machine Recipes.");
                ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Standard.get(4, new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Aluminium).toString()});
                ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Standard.get(32, new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Titanium).toString()});
                ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Standard.get(32, new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Tungsten).toString()});

                ModHandler.addRollingMachineRecipe(ItemList.RC_Rail_Reinforced.get(32, new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.TungstenSteel).toString()});

                ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(2, new Object[]{"  X", " X ", "X  ", 'X', OrePrefixes.ingot.get(Materials.Aluminium).toString()});
                ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(16, new Object[]{"  X", " X ", "X  ", 'X', OrePrefixes.ingot.get(Materials.Titanium).toString()});
                ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(16, new Object[]{"  X", " X ", "X  ", 'X', OrePrefixes.ingot.get(Materials.Tungsten).toString()});
                ModHandler.addRollingMachineRecipe(ItemList.RC_Rebar.get(48, new Object[]{"  X", " X ", "X  ", 'X', OrePrefixes.ingot.get(Materials.TungstenSteel).toString()});

                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.light.blue", 8, new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefixes.ingot.get(Materials.Aluminium).toString()});
                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.purple", 64, new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefixes.ingot.get(Materials.Titanium).toString()});
                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.black", 64, new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefixes.ingot.get(Materials.Tungsten).toString()});

                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.light.blue", 8, new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Aluminium).toString()});
                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.purple", 64, new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Titanium).toString()});
                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.black", 64, new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefixes.ingot.get(Materials.Tungsten).toString()});

                GTLog.out.println("GT_Mod: Replacing Railcraft Recipes with slightly more OreDicted Variants");

                long tBitMask = ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES_IF_SAME_NBT | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES | ModHandler.RecipeBits.DELETE_ALL_OTHER_NATIVE_RECIPES | ModHandler.RecipeBits.ONLY_ADD_IF_THERE_IS_ANOTHER_RECIPE_FOR_IT;
                char tHammer = ' ';
                char tFile = ' ';
                char tWrench = ' ';
                OrePrefixes tIngot = OrePrefixes.ingot;
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "railcraft_stuff_use_tools", true)) {
                        tHammer = 'h';
                        tFile = 'f';
                        tWrench = 'w';
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "railcraft_stuff_use_plates", true)) {
                        tIngot = OrePrefixes.plate;
                }
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "part.gear", 2, 3), tBitMask | ModHandler.RecipeBits.MIRRORED, new Object[]{tHammer + "" + tFile, "XX", "XX", 'X', tIngot.get(Materials.Tin)});

                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 0), tBitMask, new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', OrePrefixes.nugget.get(Materials.Gold), 'G', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 3)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 1), tBitMask, new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', tIngot.get(Materials.AnyIron), 'G', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 3)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2), tBitMask, new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', tIngot.get(Materials.Steel), 'G', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 3)});

                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8, 0), tBitMask, new Object[]{tWrench + "PP", tHammer + "PP", 'P', OrePrefixes.plate.get(Materials.AnyIron)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8, 1), tBitMask, new Object[]{"GPG", "PGP", "GPG", 'P', OrePrefixes.plate.get(Materials.AnyIron), 'G', new ItemStack(Blocks.GLASS_PANE, 1, 32767)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8, 2), tBitMask, new Object[]{"BPB", "PLP", "BPB", 'P', OrePrefixes.plate.get(Materials.AnyIron), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'L', new ItemStack(Blocks.LEVER, 1, 32767)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 3), tBitMask, new Object[]{tWrench + "P", tHammer + "P", 'P', OrePrefixes.plate.get(Materials.AnyIron)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), tBitMask, new Object[]{tWrench + "P", tHammer + "P", 'P', OrePrefixes.plate.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 5), tBitMask, new Object[]{"BBB", "BFB", "BOB", 'B', OrePrefixes.ingot.get(Materials.Brick), 'F', new ItemStack(Items.FIRE_CHARGE, 1, 32767), 'O', OreDictNames.craftingFurnace});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 6), tBitMask, new Object[]{"PUP", "BFB", "POP", 'P', OrePrefixes.plate.get(Materials.Steel), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'F', new ItemStack(Items.FIRE_CHARGE, 1, 32767), 'U', OrePrefixes.bucket.get(Materials.Empty), 'O', OreDictNames.craftingFurnace});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 7), tBitMask | ModHandler.RecipeBits.MIRRORED, new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefixes.nugget.get(Materials.Gold), 'O', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 0), 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'T', OreDictNames.craftingPiston});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 8), tBitMask | ModHandler.RecipeBits.MIRRORED, new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefixes.plate.get(Materials.AnyIron), 'O', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 1), 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'T', OreDictNames.craftingPiston});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 9), tBitMask | ModHandler.RecipeBits.MIRRORED, new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefixes.plate.get(Materials.Steel), 'O', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2), 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'T', OreDictNames.craftingPiston});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 10), tBitMask, new Object[]{" E ", " O ", "OIO", 'I', tIngot.get(Materials.Gold), 'E', OrePrefixes.gem.get(Materials.EnderPearl), 'O', OrePrefixes.stone.get(Materials.Obsidian)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 11), tBitMask, new Object[]{"OOO", "OEO", "OOO", 'E', OrePrefixes.gem.get(Materials.EnderPearl), 'O', OrePrefixes.stone.get(Materials.Obsidian)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 12), tBitMask, new Object[]{"GPG", "PAP", "GPG", 'P', OreDictNames.craftingPiston, 'A', OreDictNames.craftingAnvil, 'G', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8, 13), tBitMask, new Object[]{tWrench + "PP", tHammer + "PP", 'P', OrePrefixes.plate.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8, 14), tBitMask, new Object[]{"GPG", "PGP", "GPG", 'P', OrePrefixes.plate.get(Materials.Steel), 'G', new ItemStack(Blocks.GLASS_PANE, 1, 32767)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8, 15), tBitMask, new Object[]{"BPB", "PLP", "BPB", 'P', OrePrefixes.plate.get(Materials.Steel), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'L', new ItemStack(Blocks.LEVER, 1, 32767)});

                ModHandler.addCraftingRecipe(ItemList.RC_ShuntingWireFrame.get(6, tBitMask, new Object[]{"PPP", "R" + tWrench + "R", "RRR", 'P', OrePrefixes.plate.get(Materials.AnyIron), 'R', ItemList.RC_Rebar.get(1)});

                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 0), tBitMask, new Object[]{"IOI", "GEG", "IOI", 'I', tIngot.get(Materials.Gold), 'G', OrePrefixes.gem.get(Materials.Diamond), 'E', OrePrefixes.gem.get(Materials.EnderPearl), 'O', OrePrefixes.stone.get(Materials.Obsidian)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 3, 1), tBitMask, new Object[]{"BPB", "P" + tWrench + "P", "BPB", 'P', OrePrefixes.plate.get(Materials.Steel), 'B', OrePrefixes.block.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 2), tBitMask, new Object[]{"IOI", "GEG", "IOI", 'I', tIngot.get(Materials.Gold), 'G', OrePrefixes.gem.get(Materials.Emerald), 'E', OrePrefixes.gem.get(Materials.EnderPearl), 'O', OrePrefixes.stone.get(Materials.Obsidian)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4, 3), tBitMask, new Object[]{"PPP", "PFP", "PPP", 'P', OrePrefixes.plate.get(Materials.Steel), 'F', OreDictNames.craftingFurnace});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 5), tBitMask, new Object[]{" N ", "RCR", 'R', OrePrefixes.dust.get(Materials.Redstone), 'N', OrePrefixes.stone.get(Materials.Netherrack), 'C', new ItemStack(Items.CAULDRON, 1, 0)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 6), tBitMask, new Object[]{"SGS", "EDE", "SGS", 'E', OrePrefixes.gem.get(Materials.Emerald), 'S', OrePrefixes.plate.get(Materials.Steel), 'G', new ItemStack(Blocks.GLASS_PANE, 1, 32767), 'D', new ItemStack(Blocks.DISPENSER, 1, 32767)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 8), tBitMask, new Object[]{"IPI", "PCP", "IPI", 'P', OreDictNames.craftingPiston, 'I', tIngot.get(Materials.AnyIron), 'C', new ItemStack(Blocks.CRAFTING_TABLE, 1, 32767)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 9), tBitMask, new Object[]{" I ", " T ", " D ", 'I', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'T', ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), 'D', new ItemStack(Blocks.DISPENSER, 1, 32767)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 10), tBitMask, new Object[]{" I ", "RTR", " D ", 'I', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'T', ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), 'D', new ItemStack(Blocks.DISPENSER, 1, 32767), 'R', OrePrefixes.dust.get(Materials.Redstone)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 10), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RTR", 'T', ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 9), 'R', OrePrefixes.dust.get(Materials.Redstone)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 11), tBitMask, new Object[]{"PCP", "CSC", "PCP", 'P', OrePrefixes.plank.get(Materials.Wood), 'S', OrePrefixes.plate.get(Materials.Steel), 'C', new ItemStack(Items.GOLDEN_CARROT, 1, 0)});
                if (GregTech_API.sMachineFile.get(ConfigCategories.machineconfig, "DisableRCBlastFurnace", false)) {
                        ModHandler.removeRecipeByOutput(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4, 12));
                }
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 13), tBitMask, new Object[]{"TSB", "SCS", "PSP", 'P', OreDictNames.craftingPiston, 'S', OrePrefixes.plate.get(Materials.Steel), 'B', OreDictNames.craftingBook, 'C', new ItemStack(Blocks.CRAFTING_TABLE, 1, 32767), 'T', new ItemStack(Items.DIAMOND_PICKAXE, 1, 0)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 6, 14), tBitMask, new Object[]{"PPP", "ISI", "PPP", 'P', OrePrefixes.plank.get(Materials.Wood), 'I', tIngot.get(Materials.AnyIron), 'S', "slimeball"});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4, 15), tBitMask, new Object[]{"PDP", "DBD", "PDP", 'P', OreDictNames.craftingPiston, 'B', OrePrefixes.block.get(Materials.Steel), 'D', OrePrefixes.gem.get(Materials.Diamond)});

                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "machine.epsilon", 1, 0), tBitMask, new Object[]{"PWP", "WWW", "PWP", 'P', OrePrefixes.plate.get(Materials.AnyIron), 'W', OrePrefixes.wireGt02.get(Materials.Copper)});

                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "tool.crowbar", 1, 0), tBitMask, new Object[]{tHammer + "DS", "DSD", "SD" + tFile, 'S', OrePrefixes.ingot.get(Materials.Iron), 'D', Dyes.dyeRed});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "tool.crowbar.reinforced", 1, 0), tBitMask, new Object[]{tHammer + "DS", "DSD", "SD" + tFile, 'S', OrePrefixes.ingot.get(Materials.Steel), 'D', Dyes.dyeRed});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "tool.whistle.tuner", 1, 0), tBitMask | ModHandler.RecipeBits.MIRRORED, new Object[]{"S" + tHammer + "S", "SSS", " S" + tFile, 'S', OrePrefixes.nugget.get(Materials.Iron)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "part.turbine.blade", 1, 0), tBitMask, new Object[]{"S" + tFile, "S ", "S" + tHammer, 'S', tIngot.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "part.turbine.disk", 1, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefixes.block.get(Materials.Steel), 'S', ModHandler.getModItem(aTextRailcraft, "part.turbine.blade", 1, 0)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "part.turbine.rotor", 1, 0), tBitMask, new Object[]{"SSS", " " + tWrench + " ", 'S', ModHandler.getModItem(aTextRailcraft, "part.turbine.disk", 1, 0)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "borehead.iron", 1, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefixes.block.get(Materials.Iron), 'S', tIngot.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "borehead.steel", 1, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefixes.block.get(Materials.Steel), 'S', tIngot.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "borehead.diamond", 1, 0), tBitMask, new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefixes.block.get(Materials.Diamond), 'S', tIngot.get(Materials.Steel)});

                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "cart.loco.steam.solid", 1, 0), tBitMask, new Object[]{"TTF", "TTF", "BCC", 'C', new ItemStack(Items.MINECART, 1), 'T', ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), 'F', ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 5), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "cart.loco.electric", 1, 0), tBitMask, new Object[]{"LP" + tWrench, "PEP", "GCG", 'C', new ItemStack(Items.MINECART, 1), 'E', ModHandler.getModItem(aTextRailcraft, "machine.epsilon", 1, 0), 'G', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2), 'L', new ItemStack(Blocks.REDSTONE_LAMP, 1, 32767), 'P', OrePrefixes.plate.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(ModHandler.getModItem(aTextRailcraft, "cart.bore", 1, 0), tBitMask, new Object[]{"BCB", "FCF", tHammer + "A" + tWrench, 'C', new ItemStack(Items.MINECART, 1), 'A', new ItemStack(Items.CHEST_MINECART, 1), 'F', OreDictNames.craftingFurnace, 'B', OrePrefixes.block.get(Materials.Steel)});

                GTLog.out.println("GT_Mod: Beginning to add regular Crafting Recipes.");
                ModHandler.addCraftingRecipe(ModHandler.getIC2Item(BlockName.scaffold, BlockScaffold.ScaffoldType.wood, 6), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", " S ", "S S", 'W', OrePrefixes.plank.get(Materials.Wood), 'S', OrePrefixes.stick.get(Materials.Wood)});

                ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "HPT", 'H', OrePrefixes.cell.get(Materials.Helium), 'N', OrePrefixes.cell.get(Materials.Nitrogen), 'T', OrePrefixes.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefixes.wireGt01.get(Materials.NiobiumTitanium)});
                ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "HPT", 'H', OrePrefixes.cell.get(Materials.Helium), 'N', OrePrefixes.cell.get(Materials.Nitrogen), 'T', OrePrefixes.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefixes.wireGt01.get(Materials.VanadiumGallium)});
                ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "NPT", 'N', OrePrefixes.cell.get(Materials.Nitrogen), 'T', OrePrefixes.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefixes.wireGt01.get(Materials.YttriumBariumCuprate)});
                ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"NPT", "CCC", "NPT", 'N', OrePrefixes.cell.get(Materials.Nitrogen), 'T', OrePrefixes.pipeTiny.get(Materials.TungstenSteel), 'P', ItemList.Electric_Pump_LV, 'C', OrePrefixes.wireGt01.get(Materials.HSSG)});

                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.stick.get(Materials.AnyIron), OrePrefixes.dust.get(Materials.Redstone), OrePrefixes.dust.get(Materials.Redstone), OrePrefixes.dust.get(Materials.Redstone), OrePrefixes.dust.get(Materials.Redstone)});

                ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Gold.get(1), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Gold)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Iron.get(1), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.AnyIron)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Bronze.get(1), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Bronze)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Copper.get(1), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.AnyCopper)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Tin.get(1), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Tin)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Lead.get(1), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Lead)});
                ModHandler.addCraftingRecipe(ItemList.IC2_Item_Casing_Steel.get(1), new Object[]{"h P", 'P', OrePrefixes.plate.get(Materials.Steel)});

                ModHandler.addCraftingRecipe(new ItemStack(Blocks.TORCH, 2), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"C", "S", 'C', OrePrefixes.dust.get(Materials.Sulfur), 'S', OrePrefixes.stick.get(Materials.Wood)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.TORCH, 6), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"C", "S", 'C', OrePrefixes.dust.get(Materials.Phosphorus), 'S', OrePrefixes.stick.get(Materials.Wood)});

                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PISTON, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.stoneCobble, 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.ingot.get(Materials.AnyIron)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PISTON, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.stoneCobble, 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.ingot.get(Materials.AnyBronze)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PISTON, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.stoneCobble, 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.ingot.get(Materials.Aluminium)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PISTON, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.stoneCobble, 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.ingot.get(Materials.Steel)});
                ModHandler.addCraftingRecipe(new ItemStack(Blocks.PISTON, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefixes.plank.get(Materials.Wood), 'C', OrePrefixes.stoneCobble, 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.ingot.get(Materials.Titanium)});

                ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.reactor_heat_vent, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"AIA", "I I", "AIA", 'I', new ItemStack(Blocks.IRON_BARS, 1), 'A', OrePrefixes.plate.get(Materials.Aluminium)});
                ModHandler.addShapelessCraftingRecipe(ModHandler.getIC2Item(ItemName.containment_plating, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ModHandler.getIC2Item(ItemName.plating, 1), OrePrefixes.plate.get(Materials.Lead)});
                if (!Materials.Steel.mBlastFurnaceRequired) {
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Steel, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Coal), OrePrefixes.dust.get(Materials.Coal)});
                }
                if (GT_Mod.gregtechproxy.mNerfDustCrafting) {
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Electrum, 6, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Gold)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Brass, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.Zinc)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Brass, 9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Zinc)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.Tin)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Bronze, 9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tin)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Invar, 9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Nickel)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Cupronickel, 6, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.AnyCopper)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustSmall, Materials.Nichrome, 15, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Chrome)});
                } else {
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Electrum, 2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Gold)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Brass, 4, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.Zinc)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Brass, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Zinc)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 4, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.Tin)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Bronze, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tetrahedrite), OrePrefixes.dust.get(Materials.Tin)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Invar, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Nickel)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Cupronickel, 2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.AnyCopper)});
                        ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Nichrome, 5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Chrome)});
                }
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.RoseGold, 5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Gold), OrePrefixes.dust.get(Materials.Gold), OrePrefixes.dust.get(Materials.Gold), OrePrefixes.dust.get(Materials.Gold), OrePrefixes.dust.get(Materials.AnyCopper)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.SterlingSilver, 5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.AnyCopper)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.BlackBronze, 5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Gold), OrePrefixes.dust.get(Materials.Silver), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.BismuthBronze, 5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Bismuth), OrePrefixes.dust.get(Materials.Zinc), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.BlackSteel, 5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.BlackBronze), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.RedSteel, 8, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.SterlingSilver), OrePrefixes.dust.get(Materials.BismuthBronze), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.BlueSteel, 8, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.RoseGold), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel), OrePrefixes.dust.get(Materials.BlackSteel)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Osmiridium, 4, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iridium), OrePrefixes.dust.get(Materials.Iridium), OrePrefixes.dust.get(Materials.Iridium), OrePrefixes.dust.get(Materials.Osmium)});

                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ultimet, 9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Chrome), OrePrefixes.dust.get(Materials.Chrome), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Molybdenum)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CobaltBrass, 9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Brass), OrePrefixes.dust.get(Materials.Aluminium), OrePrefixes.dust.get(Materials.Cobalt)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.StainlessSteel, 9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Nickel), OrePrefixes.dust.get(Materials.Manganese), OrePrefixes.dust.get(Materials.Chrome)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.YttriumBariumCuprate, 6, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Yttrium), OrePrefixes.dust.get(Materials.Barium), OrePrefixes.dust.get(Materials.Barium), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper), OrePrefixes.dust.get(Materials.AnyCopper)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Kanthal, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.Aluminium), OrePrefixes.dust.get(Materials.Chrome)});

                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Ultimet, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dustTiny.get(Materials.Cobalt), OrePrefixes.dustTiny.get(Materials.Cobalt), OrePrefixes.dustTiny.get(Materials.Cobalt), OrePrefixes.dustTiny.get(Materials.Cobalt), OrePrefixes.dustTiny.get(Materials.Cobalt), OrePrefixes.dustTiny.get(Materials.Chrome), OrePrefixes.dustTiny.get(Materials.Chrome), OrePrefixes.dustTiny.get(Materials.Nickel), OrePrefixes.dustTiny.get(Materials.Molybdenum)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CobaltBrass, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Brass), OrePrefixes.dustTiny.get(Materials.Aluminium), OrePrefixes.dustTiny.get(Materials.Cobalt)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.StainlessSteel, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Nickel), OrePrefixes.dustTiny.get(Materials.Manganese), OrePrefixes.dustTiny.get(Materials.Chrome)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.YttriumBariumCuprate, 6, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dustTiny.get(Materials.Yttrium), OrePrefixes.dustTiny.get(Materials.Barium), OrePrefixes.dustTiny.get(Materials.Barium), OrePrefixes.dustTiny.get(Materials.AnyCopper), OrePrefixes.dustTiny.get(Materials.AnyCopper), OrePrefixes.dustTiny.get(Materials.AnyCopper)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dustTiny, Materials.Kanthal, 3, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dustTiny.get(Materials.Iron), OrePrefixes.dustTiny.get(Materials.Aluminium), OrePrefixes.dustTiny.get(Materials.Chrome)});

                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.VanadiumSteel, 9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Steel), OrePrefixes.dust.get(Materials.Vanadium), OrePrefixes.dust.get(Materials.Chrome)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.HSSG, 9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.TungstenSteel), OrePrefixes.dust.get(Materials.TungstenSteel), OrePrefixes.dust.get(Materials.TungstenSteel), OrePrefixes.dust.get(Materials.TungstenSteel), OrePrefixes.dust.get(Materials.TungstenSteel), OrePrefixes.dust.get(Materials.Chrome), OrePrefixes.dust.get(Materials.Molybdenum), OrePrefixes.dust.get(Materials.Molybdenum), OrePrefixes.dust.get(Materials.Vanadium)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.HSSE, 9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.Cobalt), OrePrefixes.dust.get(Materials.Manganese), OrePrefixes.dust.get(Materials.Silicon)});
                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.HSSS, 9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.HSSG), OrePrefixes.dust.get(Materials.Iridium), OrePrefixes.dust.get(Materials.Iridium), OrePrefixes.dust.get(Materials.Osmium)});


                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.IronWood, 2, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Iron), OrePrefixes.dust.get(Materials.LiveRoot), OrePrefixes.dustTiny.get(Materials.Gold)});

                ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.GUNPOWDER, 3), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Coal), OrePrefixes.dust.get(Materials.Sulfur), OrePrefixes.dust.get(Materials.Saltpeter), OrePrefixes.dust.get(Materials.Saltpeter)});
                ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.GUNPOWDER, 2), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Charcoal), OrePrefixes.dust.get(Materials.Sulfur), OrePrefixes.dust.get(Materials.Saltpeter), OrePrefixes.dust.get(Materials.Saltpeter)});

                ModHandler.addShapelessCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Saltpeter, 5, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.dust.get(Materials.Potassium), OrePrefixes.cell.get(Materials.Nitrogen), OrePrefixes.cell.get(Materials.Oxygen), OrePrefixes.cell.get(Materials.Oxygen), OrePrefixes.cell.get(Materials.Oxygen)});
                ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_fibre, 1));
                if (GT_Mod.gregtechproxy.mDisableIC2Cables) {
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.copper, 0));
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.copper, 1));
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.gold, 0));
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.gold, 1));
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.iron, 0));
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.iron, 1));
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.glass, 0));
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.tin, 0));
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.tin, 1));
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.detector, 0));
                        ModHandler.removeRecipeByOutput(ItemCable.getCable(CableType.splitter, 0));
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.electrolyzer, 1));

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.batbox, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.batbox, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "BBB", "PPP", 'C', OrePrefixes.cableGt01.get(Materials.Tin), 'P', OrePrefixes.plank.get(Materials.Wood), 'B', OrePrefixes.battery.get(Materials.Basic)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.mfe, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.mfe, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CEC", "EME", "CEC", 'C', OrePrefixes.cableGt01.get(Materials.Gold), 'E', OrePrefixes.battery.get(Materials.Elite), 'M', ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.lv_transformer, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.lv_transformer, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "POP", "PCP", 'C', OrePrefixes.cableGt01.get(Materials.Tin), 'O', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coil, 1), 'P', OrePrefixes.plank.get(Materials.Wood)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CMC", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'M', ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.hv_transformer, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.hv_transformer, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" C ", "IMB", " C ", 'C', OrePrefixes.cableGt01.get(Materials.Gold), 'M', ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1), 'I', OrePrefixes.circuit.get(Materials.Basic), 'B', OrePrefixes.battery.get(Materials.Advanced)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.ev_transformer, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.ev_transformer, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" C ", "IMB", " C ", 'C', OrePrefixes.cableGt01.get(Materials.Aluminium), 'M', ModHandler.getIC2TEItem(TeBlock.hv_transformer, 1), 'I', OrePrefixes.circuit.get(Materials.Advanced), 'B', OrePrefixes.battery.get(Materials.Master)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.cesu, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.cesu, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "BBB", "PPP", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'P', OrePrefixes.plate.get(Materials.Bronze), 'B', OrePrefixes.battery.get(Materials.Advanced)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.luminator_flat, 1));
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.teleporter, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.teleporter, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GFG", "CMC", "GDG", 'C', OrePrefixes.cableGt01.get(Materials.Platinum), 'G', OrePrefixes.circuit.get(Materials.Advanced), 'D', OrePrefixes.gem.get(Materials.Diamond), 'M', ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1), 'F', ModHandler.getIC2Item(ItemName.frequency_transmitter, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.energy_o_mat, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.energy_o_mat, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RBR", "CMC", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'R', OrePrefixes.dust.get(Materials.Redstone), 'B', OrePrefixes.battery.get(Materials.Basic), 'M', ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.advanced_re_battery, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.advanced_re_battery, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CTC", "TST", "TLT", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'S', OrePrefixes.dust.get(Materials.Sulfur), 'L', OrePrefixes.dust.get(Materials.Lead), 'T', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.bronze, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.boat, ItemIC2Boat.BoatType.electric, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.boat, ItemIC2Boat.BoatType.electric, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CCC", "XWX", aTextIron2, 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'X', OrePrefixes.plate.get(Materials.Iron), 'W', ModHandler.getIC2TEItem(TeBlock.water_generator, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.cropnalyzer, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.cropnalyzer, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CC ", "RGR", "RIR", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'R', OrePrefixes.dust.get(Materials.Redstone), 'G', OrePrefixes.block.get(Materials.Glass), 'I', OrePrefixes.circuit.get(Materials.Basic)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coil, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coil, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CCC", "CXC", "CCC", 'C', OrePrefixes.wireGt01.get(Materials.Copper), 'X', OrePrefixes.ingot.get(Materials.AnyIron)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.power_unit, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.power_unit, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"BCA", "BIM", "BCA", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'B', OrePrefixes.battery.get(Materials.Basic), 'A', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.iron, 1), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.electric_motor, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.small_power_unit, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.small_power_unit, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" CA", "BIM", " CA", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'B', OrePrefixes.battery.get(Materials.Basic), 'A', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.iron, 1), 'I', OrePrefixes.circuit.get(Materials.Basic), 'M', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.electric_motor, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.remote, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.remote, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" C ", "TLT", " F ", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'L', OrePrefixes.dust.get(Materials.Lapis), 'T', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.tin, 1), 'F', ModHandler.getIC2Item(ItemName.frequency_transmitter, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.scanner, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.scanner, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PGP", "CBC", "WWW", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'G', OrePrefixes.dust.get(Materials.Glowstone), 'B', OrePrefixes.battery.get(Materials.Advanced), 'C', OrePrefixes.circuit.get(Materials.Advanced), 'P', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.gold, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.advanced_scanner, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.advanced_scanner, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PDP", "GCG", "WSW", 'W', OrePrefixes.cableGt01.get(Materials.Gold), 'G', OrePrefixes.dust.get(Materials.Glowstone), 'D', OrePrefixes.battery.get(Materials.Elite), 'C', OrePrefixes.circuit.get(Materials.Advanced), 'P', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.gold, 1), 'S', ModHandler.getIC2Item(ItemName.scanner, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.solar_helmet, 1));
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.static_boots, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.static_boots, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"I I", "IWI", "CCC", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'I', OrePrefixes.ingot.get(Materials.Iron), 'W', new ItemStack(Blocks.WOO});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.meter, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.meter, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" G ", "CIC", "C C", 'C', OrePrefixes.cableGt01.get(Materials.Copper), 'G', OrePrefixes.dust.get(Materials.Glowstone), 'I', OrePrefixes.circuit.get(Materials.Basic)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.obscurator, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.obscurator, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RER", "CAC", "RRR", 'C', OrePrefixes.cableGt01.get(Materials.Gold), 'R', OrePrefixes.dust.get(Materials.Redstone), 'E', OrePrefixes.battery.get(Materials.Advanced), 'A', OrePrefixes.circuit.get(Materials.Advanced)});
                        //ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.overclocker, 1));
                        //ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.overclocker, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CCC", "WEW", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'C', ModHandler.getIC2Item(ItemName.heat_vent, 1, 1), 'E', OrePrefixes.circuit.get(Materials.Basic)});
                        //ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.transformer, 1));
                        //ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.transformer, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GGG", "WTW", "GEG", 'W', OrePrefixes.cableGt01.get(Materials.Gold), 'T', ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1), 'E', OrePrefixes.circuit.get(Materials.Basic), 'G', OrePrefixes.block.get(Materials.Glass)});
                        //ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.energy_storage, 1));
                        //ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.energy_storage, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PPP", "WBW", "PEP", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'E', OrePrefixes.circuit.get(Materials.Basic), 'P', OrePrefixes.plank.get(Materials.Wood), 'B', OrePrefixes.battery.get(Materials.Basic)});
                        //ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.ejector, 1));
                        //ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.ejector, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PHP", "WEW", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'E', OrePrefixes.circuit.get(Materials.Basic), 'P', new ItemStack(Blocks.PISTON), 'H', new ItemStack(Blocks.HOPPER)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.single_use_battery, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.single_use_battery, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"W", "C", "R", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'C', OrePrefixes.dust.get(Materials.HydratedCoal), 'R', OrePrefixes.dust.get(Materials.Redstone)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.frequency_transmitter, 1));
                        //ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.pulling, 1));
                        //ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.pulling, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PHP", "WEW", 'W', OrePrefixes.cableGt01.get(Materials.Copper), 'P', new ItemStack(Blocks.STICKY_PISTON), 'R', new ItemStack(Blocks.HOPPER), 'E', OrePrefixes.circuit.get(Materials.Basic)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.cutter, 1));

                        if(Loader.isModLoaded("GraviSuite")){
                                ModHandler.removeRecipeByOutput(ModHandler.getModItem("GraviSuite", "advJetpack", 1));
                                ModHandler.addCraftingRecipe(ModHandler.getModItem("GraviSuite", "advJetpack", 1), new Object[]{"PJP","BLB","WCW",'P',OrePrefixes.plateAlloy.get(Materials.Carbon),'J',ModHandler.getIC2Item(ItemName.jetpack_electric, 1),'B',ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1, 6),'L',ModHandler.getModItem("GraviSuite", "advLappack", 1),'W',OrePrefixes.wireGt04.get(Materials.Platinum),'C',OrePrefixes.circuit.get(Materials.Advanced)});
                                ModHandler.removeRecipeByOutput(ModHandler.getModItem("GraviSuite", "itemSimpleItem", 3, 1));
                                ModHandler.addCraftingRecipe(ModHandler.getModItem("GraviSuite", "itemSimpleItem", 3, 1), new Object[]{"CCC","WWW","CCC",'C',ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1),'W',OrePrefixes.wireGt01.get(Materials.Superconductor)});
                        }
                } else {
                        ModHandler.addCraftingRecipe(ItemCable.getCable(CableType.glass, 0), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"GGG", "EDE", "GGG", 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'D', OrePrefixes.dust.get(Materials.Silver), 'E', ItemList.IC2_Energium_Dust.get(1)});
                }

                if(Loader.isModLoaded("ImmersiveEngineering")){
                        ModHandler.removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 4));
                        ModHandler.removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 4));
                        ModHandler.removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 4));
                }

                ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.uranium_fuel_rod, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"UUU", "NNN", "UUU", 'U', OrePrefixes.ingot.get(Materials.Uranium), 'N', OrePrefixes.nugget.get(Materials.Uranium235)});
                ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.mox_fuel_rod, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"UUU", "NNN", "UUU", 'U', OrePrefixes.ingot.get(Materials.Uranium), 'N', OrePrefixes.ingot.get(Materials.Plutonium)});

                if (!GregTech_API.mIC2Classic) {
                        ModHandler.addCraftingRecipe(ItemList.Uraniumcell_2.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "   ", "   ", 'R', ItemList.Uraniumcell_1, 'P', OrePrefixes.plate.get(Materials.Iron)});
                        ModHandler.addCraftingRecipe(ItemList.Uraniumcell_4.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "CPC", "RPR", 'R', ItemList.Uraniumcell_1, 'P', OrePrefixes.plate.get(Materials.Iron), 'C', OrePrefixes.plate.get(Materials.Copper)});
                        ModHandler.addCraftingRecipe(ItemList.Moxcell_2.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "   ", "   ", 'R', ItemList.Moxcell_1, 'P', OrePrefixes.plate.get(Materials.Iron)});
                        ModHandler.addCraftingRecipe(ItemList.Moxcell_4.get(1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RPR", "CPC", "RPR", 'R', ItemList.Moxcell_1, 'P', OrePrefixes.plate.get(Materials.Iron), 'C', OrePrefixes.plate.get(Materials.Copper)});

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.mining_laser, 1).copy());
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P',OrePrefixes.plate.get(Materials.Titanium),'G',OrePrefixes.gemExquisite.get(Materials.Diamond),'E',ItemList.Emitter_HV,'C',OrePrefixes.circuit.get(Materials.Master),'S',OrePrefixes.screw.get(Materials.Titanium),'B',new ItemStack(ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1, GT_Values.W)});
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P',OrePrefixes.plate.get(Materials.Titanium),'G',OrePrefixes.gemExquisite.get(Materials.Ruby),'E',ItemList.Emitter_HV,'C',OrePrefixes.circuit.get(Materials.Master),'S',OrePrefixes.screw.get(Materials.Titanium),'B',new ItemStack(ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1,GT_Values.W)});
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P',OrePrefixes.plate.get(Materials.Titanium),'G',OrePrefixes.gemExquisite.get(Materials.Jasper),'E',ItemList.Emitter_HV,'C',OrePrefixes.circuit.get(Materials.Master),'S',OrePrefixes.screw.get(Materials.Titanium),'B',new ItemStack(ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1,GT_Values.W)});
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P',OrePrefixes.plate.get(Materials.Titanium),'G',OrePrefixes.gemExquisite.get(Materials.GarnetRed),'E',ItemList.Emitter_HV,'C',OrePrefixes.circuit.get(Materials.Master),'S',OrePrefixes.screw.get(Materials.Titanium),'B',new ItemStack(ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1,GT_Values.W)});
                }

                ModHandler.removeRecipeByOutput(ItemList.IC2_Energium_Dust.get(1));
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.gregtechrecipes, "energycrystalruby", true)) {
                        ModHandler.addCraftingRecipe(ItemList.IC2_Energium_Dust.get(9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RDR", "DRD", "RDR", 'R', OrePrefixes.dust.get(Materials.Redstone), 'D', OrePrefixes.dust.get(Materials.Ruby)});
                } else {
                        ModHandler.addCraftingRecipe(ItemList.IC2_Energium_Dust.get(9, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RDR", "DRD", "RDR", 'R', OrePrefixes.dust.get(Materials.Redstone), 'D', OrePrefixes.dust.get(Materials.Diamond)});
                }
                ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.lapotron_crystal, 1));
                for(Materials tCMat : new Materials[]{Materials.Lapis, Materials.Lazurite, Materials.Sodalite}){
                        ModHandler.addShapelessCraftingRecipe(ModHandler.getIC2Item(ItemName.lapotron_crystal, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.gemExquisite.get(Materials.Sapphire), OrePrefixes.stick.get(tCMat),ItemList.Circuit_Parts_Wiring_Elite});
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.lapotron_crystal, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"LCL", "RSR", "LCL", 'C', OrePrefixes.circuit.get(Materials.Data), 'S', ModHandler.getIC2Item(ItemName.energy_crystal, 1, 32767), 'L', OrePrefixes.plate.get(tCMat), 'R', OrePrefixes.stick.get(tCMat)});
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.lapotron_crystal, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"LCL", "RSR", "LCL", 'C', OrePrefixes.circuit.get(Materials.Advanced), 'S', OrePrefixes.gemFlawless.get(Materials.Sapphire), 'L', OrePrefixes.plate.get(tCMat), 'R', OrePrefixes.stick.get(tCMat)});
                }
                ModHandler.removeRecipe(ModHandler.getIC2Item(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe, 8));
                ModHandler.addCraftingRecipe(ModHandler.getIC2Item(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe, 1), new Object[]{"hPf",'P',OrePrefixes.pipeSmall.get(Materials.Steel)});
                GT_Values.RA.addWiremillRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeTiny, Materials.Steel, 1), ModHandler.getIC2Item(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe, 1), 200, 16);

                ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.luminator_flat, 16), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RTR", "GHG", "GGG", 'H', OrePrefixes.cell.get(Materials.Helium), 'T', OrePrefixes.ingot.get(Materials.Tin), 'R', OrePrefixes.ingot.get(Materials.AnyIron), 'G', new ItemStack(Blocks.GLASS, 1)});
                ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.luminator_flat, 16), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RTR", "GHG", "GGG", 'H', OrePrefixes.cell.get(Materials.Mercury), 'T', OrePrefixes.ingot.get(Materials.Tin), 'R', OrePrefixes.ingot.get(Materials.AnyIron), 'G', new ItemStack(Blocks.GLASS, 1)});

                ModHandler.removeRecipe(new ItemStack[]{tStack = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1), tStack, tStack, tStack, new ItemStack(Items.COAL, 1, 0), tStack, tStack, tStack, tStack});
                ModHandler.removeRecipe(new ItemStack[]{tStack = GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sulfur, 1), tStack, tStack, tStack, new ItemStack(Items.COAL, 1, 1), tStack, tStack, tStack, tStack});
                ModHandler.removeRecipe(new ItemStack[]{null, tStack = new ItemStack(Items.COAL, 1), null, tStack, GT_OreDictUnificator.get(OrePrefixes.ingot, Materials.Iron, 1), tStack, null, tStack, null});

                ModHandler.removeFurnaceSmelting(new ItemStack(Blocks.HOPPER));

                GTLog.out.println("GT_Mod: Applying harder Recipes for several Blocks.");
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "blockbreaker", false)) {
                        ModHandler.addCraftingRecipe(ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Items.IRON_PICKAXE, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.PISTON, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Items.REDSTONE, 1), new ItemStack(Blocks.COBBLESTONE, 1)}), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"RGR", "RPR", "RCR", 'G', OreDictNames.craftingGrinder, 'C', OrePrefixes.circuit.get(Materials.Advanced), 'R', OrePrefixes.plate.get(Materials.Steel), 'P', OreDictNames.craftingPiston});
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "beryliumreflector", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1, 1)))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" N ", "NBN", " N ", 'B', OrePrefixes.plateDouble.get(Materials.Beryllium), 'N', ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1)});
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" B ", "NCN", " B ", 'B', OrePrefixes.plate.get(Materials.Beryllium), 'N', ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1), 'C', OrePrefixes.plate.get(Materials.TungstenCarbide)});
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "reflector", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1)))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"TGT", "GSG", "TGT", 'T', OrePrefixes.plate.get(Materials.Tin), 'G', OrePrefixes.dust.get(Materials.Graphite), 'S', OrePrefixes.plateDouble.get(Materials.Steel)});
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"TTT", "GSG", "TTT", 'T', OrePrefixes.plate.get(Materials.TinAlloy), 'G', OrePrefixes.dust.get(Materials.Graphite), 'S', OrePrefixes.plate.get(Materials.Beryllium)});
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "cropharvester", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.crop_harvester, 1)))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.crop_harvester, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"ACA", "PMS", "WOW", 'M', ItemList.Hull_HV, 'C', OrePrefixes.circuit.get(Materials.Master), 'A', ItemList.Robot_Arm_HV, 'P', ItemList.Electric_Piston_HV, 'S', ItemList.Sensor_HV, 'W', OrePrefixes.toolHeadSense.get(Materials.StainlessSteel), 'O', ItemList.Conveyor_Module_HV});
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "nuclearReactor", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.nuclear_reactor, 1)))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2TEItem(TeBlock.nuclear_reactor, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PCP", "PMP", "PAP", 'P', OrePrefixes.plateDense.get(Materials.Lead), 'C', OrePrefixes.circuit.get(Materials.Master), 'M', ModHandler.getIC2TEItem(TeBlock.reactor_chamber, 1), 'A', ItemList.Robot_Arm_EV});

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.reactor_chamber, 1));
                        GT_Values.RA.addAssemblerRecipe(ItemList.Hull_EV.get(1), GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Lead, 4), ModHandler.getIC2TEItem(TeBlock.reactor_chamber, 1), 200, 256);

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1));
                        GT_Values.RA.addChemicalBathRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Lead, 1), Materials.Concrete.getMolten(144), ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), GT_Values.NI, GT_Values.NI, null, 400, 80);

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.reactor_access_hatch, 1));
                        GT_Values.RA.addAssemblerRecipe(ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), ItemList.Conveyor_Module_EV.get(1), ModHandler.getIC2TEItem(TeBlock.reactor_access_hatch, 1), 200, 80);

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.reactor_fluid_port, 1));
                        GT_Values.RA.addAssemblerRecipe(ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), ItemList.Electric_Pump_EV.get(1), ModHandler.getIC2TEItem(TeBlock.reactor_fluid_port, 1), 200, 80);

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.reactor_redstone_port, 1));
                        GT_Values.RA.addAssemblerRecipe(ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 1), ModHandler.getIC2TEItem(TeBlock.reactor_redstone_port, 1), 200, 80);
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "rtg", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(BlockName.te, TeBlock.rt_generator, 1)))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(BlockName.te, TeBlock.rt_generator, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"III", "IMI", "ICI", 'I', ItemList.IC2_Item_Casing_Steel, 'C', OrePrefixes.circuit.get(Materials.Master), 'M', ItemList.Hull_IV});

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(BlockName.te, TeBlock.rt_heat_generator, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(BlockName.te, TeBlock.rt_heat_generator, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"III", "IMB", "ICI", 'I', ItemList.IC2_Item_Casing_Steel, 'C', OrePrefixes.circuit.get(Materials.Master), 'M', ItemList.Hull_IV, 'B', GT_OreDictUnificator.get(OrePrefixes.block, Materials.Copper, 1)});
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "windRotor", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.rotor_carbon, 1)))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.rotor_carbon, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_rotor_blade, 1), 'S', OrePrefixes.screw.get(Materials.Iridium), 'T', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.steel_shaft, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.rotor_steel, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.rotor_steel, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.steel_rotor_blade, 1), 'S', OrePrefixes.screw.get(Materials.StainlessSteel), 'T', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.iron_shaft, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.rotor_iron, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.rotor_iron, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.iron_rotor_blade, 1), 'S', OrePrefixes.screw.get(Materials.WroughtIron), 'T', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.iron_shaft, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.rotor_wood, 1));
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.rotor_wood, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"dBS", "BTB", "SBw", 'B', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.wood_rotor_blade, 1), 'S', OrePrefixes.screw.get(Materials.WroughtIron), 'T', OrePrefixes.stickLong.get(Materials.WroughtIron)});
                }
                if (GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Diamond, 1) != null) {
                        tStack = ModHandler.getRecipeOutput(new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Iron, 1), new ItemStack(Items.REDSTONE, 1), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Iron, 1), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Gold, 1), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Iron, 1), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Gold, 1), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Diamond, 1), new ItemStack(Items.DIAMOND_PICKAXE, 1), GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Diamond, 1)});
                        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "quarry", true)) {
                                ModHandler.removeRecipeByOutput(tStack);
                                ModHandler.addCraftingRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"ICI", "GIG", "DPD", 'C', OrePrefixes.circuit.get(Materials.Advanced), 'D', OrePrefixes.gear.get(Materials.Diamond), 'G', OrePrefixes.gear.get(Materials.Gold), 'I', OrePrefixes.gear.get(Materials.Steel), 'P', ModHandler.getIC2Item(ItemName.diamond_drill, 1, true)});
                        }
                        if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "quarry", false)) {
                                ModHandler.removeRecipeByOutput(tStack);
                        }
                }
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "sugarpaper", true))) {
                        ModHandler.removeRecipeByOutput(new ItemStack(Items.PAPER));
                        ModHandler.removeRecipeByOutput(new ItemStack(Items.SUGAR));
                        ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Paper, 2), new Object[]{"SSS", " m ", 'S', new ItemStack(Items.REEDS)});
                        ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Sugar, 1), new Object[]{"Sm ", 'S', new ItemStack(Items.REEDS)});
                        //ItemStack brick = new ItemStack(new ItemStack(Blocks.stone_slab).getItem().setContainerItem(new ItemStack(Blocks.stone_slab).getItem()));
                        //ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.paper, Materials.Empty, 2), new Object[]{" C ", "SSS", " C ", 'S', GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Paper, 1), 'C', brick});
                        ModHandler.addCraftingRecipe(GT_OreDictUnificator.get(OrePrefixes.paper, Materials.Empty, 2), new Object[]{" C ", "SSS", " C ", 'S', GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Paper, 1), 'C', new ItemStack(Blocks.STONE_SLAB)});
                        //GameRegistry.addRecipe(GT_OreDictUnificator.get(OrePrefixes.paper, Materials.Empty, 2), " C ", "SSS", " C ", 'S', GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Paper, 1), 'C', brick);
                }

                GTLog.out.println("GT_Mod: Applying Recipes for Tools");
                if ((GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "nanosaber", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.nano_saber, 1)))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.nano_saber, 1), ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"PI ", "PI ", "CLC", 'L', OrePrefixes.battery.get(Materials.Master), 'I', OrePrefixes.plateAlloy.get("Iridium"), 'P', OrePrefixes.plate.get(Materials.Platinum), 'C', OrePrefixes.circuit.get(Materials.Master)});
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "namefix", true)) {
                        ModHandler.addCraftingRecipe(ModHandler.removeRecipeByOutput(new ItemStack(Items.FLINT_AND_STEEL, 1)) ? new ItemStack(Items.FLINT_AND_STEEL, 1) : null, ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"S ", " F", 'F', new ItemStack(Items.FLINT, 1), 'S', "nuggetSteel"});
                }
                if (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.diamond_drill, 1))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.diamond_drill, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" D ", "DMD", "TAT", 'M', ModHandler.getIC2Item(ItemName.diamond_drill, 1, true), 'D', OreDictNames.craftingIndustrialDiamond, 'T', OrePrefixes.plate.get(Materials.Titanium), 'A', OrePrefixes.circuit.get(Materials.Advanced)});
                }
                if (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.drill, 1))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.drill, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" S ", "SCS", "SBS", 'C', OrePrefixes.circuit.get(Materials.Basic), 'B', OrePrefixes.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefixes.plate.get(Materials.StainlessSteel) : OrePrefixes.plate.get(Materials.Iron)});
                }
                if (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.chainsaw, 1))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.chainsaw, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"BS ", "SCS", " SS", 'C', OrePrefixes.circuit.get(Materials.Basic), 'B', OrePrefixes.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefixes.plate.get(Materials.StainlessSteel) : OrePrefixes.plate.get(Materials.Iron)});
                }
                if (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.electric_hoe, 1))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.electric_hoe, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"SS ", " C ", " B ", 'C', OrePrefixes.circuit.get(Materials.Basic), 'B', OrePrefixes.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefixes.plate.get(Materials.StainlessSteel) : OrePrefixes.plate.get(Materials.Iron)});
                }
                if (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.electric_treetap, 1))) {
                        ModHandler.addCraftingRecipe(ModHandler.getIC2Item(ItemName.electric_treetap, 1), ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" B ", "SCS", "S  ", 'C', OrePrefixes.circuit.get(Materials.Basic), 'B', OrePrefixes.battery.get(Materials.Basic), 'S', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefixes.plate.get(Materials.StainlessSteel) : OrePrefixes.plate.get(Materials.Iron)});
                }
                GTLog.out.println("GT_Mod: Removing Q-Armor Recipes if configured.");
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QHelmet", false)) {
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.quantum_helmet, 1));
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QPlate", false)) {
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.quantum_chestplate, 1));
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QLegs", false)) {
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.quantum_leggings, 1));
                }
                if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QBoots", false)) {
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.quantum_boots, 1));
                }

                if(Loader.isModLoaded("GraviSuite")){
                        ModHandler.removeRecipeByOutput(ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GT_Values.W));
                        ModHandler.addCraftingRecipe(ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GT_Values.W), new Object[]{"CJC","CNC","WPW",'C',OrePrefixes.plateAlloy.get(Materials.Carbon),'J',ModHandler.getModItem("GraviSuite", "advJetpack", 1, GT_Values.W),'N',ModHandler.getIC2Item(ItemName.nano_chestplate, 1),'W',OrePrefixes.wireGt04.get(Materials.Platinum),'P',OrePrefixes.circuit.get(Materials.Elite)});
                }

                long bits =  ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE;
                ModHandler.addCraftingRecipe(ItemList.ModularBasicHelmet.		getWildcard(1),bits, new Object[] { "AAA", "B B", 'A', 			new ItemStack(Items.LEATHER, 1, 32767), 'B', OrePrefixes.ring.get(Materials.AnyIron)} );
                ModHandler.addCraftingRecipe(ItemList.ModularBasicChestplate.	getWildcard(1),bits, new Object[] { "A A", "BAB", "AAA", 'A', 	new ItemStack(Items.LEATHER, 1, 32767), 'B', OrePrefixes.ring.get(Materials.AnyIron)} );
                ModHandler.addCraftingRecipe(ItemList.ModularBasicLeggings.		getWildcard(1),bits, new Object[] { "BAB", "A A", "A A", 'A', 	new ItemStack(Items.LEATHER, 1, 32767), 'B', OrePrefixes.ring.get(Materials.AnyIron)} );
                ModHandler.addCraftingRecipe(ItemList.ModularBasicBoots.			getWildcard(1),bits, new Object[] { "A A", "B B", "A A", 'A', 	new ItemStack(Items.LEATHER, 1, 32767), 'B', OrePrefixes.ring.get(Materials.AnyIron)} );
                ModHandler.addCraftingRecipe(ItemList.ModularElectric1Helmet.	getWildcard(1),bits, new Object[] { "ACA", "B B", 'A', 			OrePrefixes.stick.get(Materials.Aluminium), 'B', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.battery.get(Materials.Advanced)});
                ModHandler.addCraftingRecipe(ItemList.ModularElectric1Chestplate.getWildcard(1),bits, new Object[] { "A A", "BCB", "AAA", 'A', 	OrePrefixes.stick.get(Materials.Aluminium), 'B', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.battery.get(Materials.Advanced)});
                ModHandler.addCraftingRecipe(ItemList.ModularElectric1Leggings.	getWildcard(1),bits, new Object[] { "BCB", "A A", "A A", 'A', 	OrePrefixes.stick.get(Materials.Aluminium), 'B', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.battery.get(Materials.Advanced)});
                ModHandler.addCraftingRecipe(ItemList.ModularElectric1Boots.		getWildcard(1),bits, new Object[] { "A A", "BCB", "A A", 'A', 	OrePrefixes.stick.get(Materials.Aluminium), 'B', OrePrefixes.plate.get(Materials.Steel), 'C', OrePrefixes.battery.get(Materials.Advanced)});
                ModHandler.addCraftingRecipe(ItemList.ModularElectric2Helmet.	getWildcard(1),bits, new Object[] { "ACA", "B B", 'A', 			OrePrefixes.stick.get(Materials.TungstenSteel), 'B', OrePrefixes.plateAlloy.get(Materials.Carbon),'C',OrePrefixes.battery.get(Materials.Master)});
                ModHandler.addCraftingRecipe(ItemList.ModularElectric2Chestplate.getWildcard(1),bits, new Object[] { "A A", "BCB", "AAA", 'A', 	OrePrefixes.stick.get(Materials.TungstenSteel), 'B', OrePrefixes.plateAlloy.get(Materials.Carbon),'C',OrePrefixes.battery.get(Materials.Master)});
                ModHandler.addCraftingRecipe(ItemList.ModularElectric2Leggings.	getWildcard(1),bits, new Object[] { "BCB", "A A", "A A", 'A', 	OrePrefixes.stick.get(Materials.TungstenSteel), 'B', OrePrefixes.plateAlloy.get(Materials.Carbon),'C',OrePrefixes.battery.get(Materials.Master)});
                ModHandler.addCraftingRecipe(ItemList.ModularElectric2Boots.		getWildcard(1),bits, new Object[] { "A A", "BCB", "A A", 'A', 	OrePrefixes.stick.get(Materials.TungstenSteel), 'B', OrePrefixes.plateAlloy.get(Materials.Carbon),'C',OrePrefixes.battery.get(Materials.Master)});
        }
}