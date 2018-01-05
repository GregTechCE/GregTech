package gregtech.loaders.postload;

@SuppressWarnings("ALL")
public class GT_CraftingRecipeLoader implements Runnable {

    @Override
    public void run() {
    }

/*
        private final static String aTextIron1 = "X X" ;
        private final static String aTextIron2 = "XXX" ;
        private final static String aTextRailcraft = "Railcraft";
        
        private final static String aTextMachineBeta = "machine.beta" ;
        private final static String aTextMachineAlpha = "machine.alpha";
        public void run() {
                GTLog.out.println("GregTechMod: Adding nerfed Vanilla Recipes.");
                ModHandler.addShapedRecipe(new ItemStack(Items.BUCKET, 1), new Object[]{"XhX", " X ", 'X', OrePrefix.plate.get(Materials.AnyIron)});
                if (!GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Bucket", true)) {
                        ModHandler.addShapedRecipe(new ItemStack(Items.BUCKET, 1), new Object[]{aTextIron1, " X ", 'X', OrePrefix.ingot.get(Materials.AnyIron)});
                }
                ItemStack tMat = new ItemStack(Items.IRON_INGOT);
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.PressurePlate", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, null, null, null, null, null, null, null}))) {
                                ModHandler.addShapedRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XXh", 'X', OrePrefix.plate.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron)});
                        }
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Door", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, null, tMat, tMat, null, tMat, tMat, null}))) {
                                ModHandler.addShapedRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XX ", "XXh", "XX ", 'X', OrePrefix.plate.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron)});
                        }
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Cauldron", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, null, tMat, tMat, null, tMat, tMat, tMat, tMat}))) {
                                ModHandler.addShapedRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{aTextIron1, "XhX", aTextIron2, 'X', OrePrefix.plate.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron)});
                        }
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Hopper", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, null, tMat, tMat, new ItemStack(Blocks.CHEST, 1, 0), tMat, null, tMat, null}))) {
                                ModHandler.addShapedRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XwX", "XCX", " X ", 'X', OrePrefix.plate.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron), 'C', "craftingChest"});
                        }
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Iron.Bars", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, tMat, tMat, tMat, tMat, null, null, null}))) {
                                tStack.stackSize /= 2;
                                ModHandler.addShapedRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{" w ", aTextIron2, aTextIron2, 'X', OrePrefix.stick.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron)});
                        }
                }
                ModHandler.addShapedRecipe(ModHandler.getIC2Item(BlockName.fence, BlockIC2Fence.IC2FenceType.iron, 6), ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextIron2, aTextIron2, " w ", 'X', OrePrefix.stick.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.AnyIron)});

                tMat = new ItemStack(Items.GOLD_INGOT);
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Gold.PressurePlate", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, null, null, null, null, null, null, null}))) {
                                ModHandler.addShapedRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{"XXh", 'X', OrePrefix.plate.get(Materials.Gold), 'S', OrePrefix.stick.get(Materials.Wood), 'I', OrePrefix.ingot.get(Materials.Gold)});
                        }
                }
                tMat = OreDictUnifier.get(OrePrefix.ingot, Materials.Rubber, 1);
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, "Rubber.Sheet", true)) {
                        ItemStack tStack;
                        if (null != (tStack = ModHandler.removeRecipe(new ItemStack[]{tMat, tMat, tMat, tMat, tMat, tMat, null, null, null}))) {
                                ModHandler.addShapedRecipe(tStack, ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES, new Object[]{aTextIron2, aTextIron2, 'X', OrePrefix.plate.get(Materials.Rubber)});
                        }
                }
                ModHandler.removeRecipeByOutput(MetaItems.Bottle_Empty.get(1, new Object[0]));
                ModHandler.removeRecipeByOutput(MetaItems.IC2_Spray_WeedEx.get(1, new Object[0]));
                ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.re_battery, 1));
                ModHandler.removeRecipeByOutput(new ItemStack(Blocks.TNT));
                ModHandler.removeRecipeByOutput(ModHandler.IC2.getIC2Item(ItemName.dynamite, 1));
                ModHandler.removeRecipeByOutput(ModHandler.IC2.getIC2Item(BlockName.te, TeBlock.itnt, 1));

                ItemStack tStack = ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.PLANKS, 1, 0), null, null, new ItemStack(Blocks.PLANKS, 1, 0)});
                if (tStack != null) {
                        ModHandler.addShapedRecipe(GTUtility.copyAmount(GregTechMod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize : tStack.stackSize * 5 / 4, new Object[]{tStack}), new Object[]{"s", "P", "P", 'P', OrePrefix.plank.get(Materials.Wood)});
                        ModHandler.addShapedRecipe(GTUtility.copyAmount(GregTechMod.gregtechproxy.mNerfedWoodPlank ? tStack.stackSize / 2 : tStack.stackSize, new Object[]{tStack}), new Object[]{"P", "P", 'P', OrePrefix.plank.get(Materials.Wood)});
                }
                ModHandler.addShapedRecipe(new ItemStack(Blocks.WOODEN_PRESSURE_PLATE, 1, 0), new Object[]{"PP", 'P', OrePrefix.plank.get(Materials.Wood)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.STONE_BUTTON, 2, 0), new Object[]{"S", "S", 'S', OrePrefix.stone});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1, 0), new Object[]{"SS", 'S', OrePrefix.stone});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.STONE_BUTTON, 1, 0), new Object[]{OrePrefix.stone});

                GTLog.out.println("GregTechMod: Adding Vanilla Convenience Recipes.");

                ModHandler.addShapedRecipe(new ItemStack(Blocks.STONEBRICK, 1, 3), new Object[]{"f", "X", 'X', new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.GRAVEL, 1, 0), new Object[]{"h", "X", 'X', new ItemStack(Blocks.COBBLESTONE, 1, 0)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.COBBLESTONE, 1, 0), new Object[]{"h", "X", 'X', new ItemStack(Blocks.STONE, 1, 0)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.STONEBRICK, 1, 2), new Object[]{"h", "X", 'X', new ItemStack(Blocks.STONEBRICK, 1, 0)});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8), new Object[]{new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 0)});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 0), new Object[]{new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 0), new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 0)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.COBBLESTONE, 1, 0), new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 3)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.BRICK_BLOCK, 1, 0), new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 4)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0), new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 5)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.NETHER_BRICK, 1, 0), new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 6)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0), new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 7)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.DOUBLE_STONE_SLAB, 1, 8), new Object[]{"B", "B", 'B', new ItemStack(Blocks.STONE_SLAB, 1, 8)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PLANKS, 1, 0), new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 0)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PLANKS, 1, 1), new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 1)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PLANKS, 1, 2), new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 2)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PLANKS, 1, 3), new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 3)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PLANKS, 1, 4), new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 4)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PLANKS, 1, 5), new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 5)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PLANKS, 1, 6), new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 6)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PLANKS, 1, 7), new Object[]{"B", "B", 'B', new ItemStack(Blocks.WOODEN_SLAB, 1, 7)});

                ModHandler.addShapedRecipe(new ItemStack(Items.STICK, 2, 0), new Object[]{"s", "X", 'X', new ItemStack(Blocks.DEADBUSH, 1, 32767)});
                ModHandler.addShapedRecipe(new ItemStack(Items.STICK, 2, 0), new Object[]{"s", "X", 'X', new ItemStack(Blocks.TALLGRASS, 1, 0)});
                ModHandler.addShapedRecipe(new ItemStack(Items.STICK, 1, 0), new Object[]{"s", "X", 'X', OrePrefix.treeSapling});

                ModHandler.addShapedRecipe(new ItemStack(Items.COMPARATOR, 1, 0), new Object[]{" T ", "TQT", "SSS", 'Q', OreDictNames.craftingQuartz, 'S', OrePrefix.stoneSmooth, 'T', OreDictNames.craftingRedstoneTorch});

                GTLog.out.println("GregTechMod: Adding Tool Recipes.");
                ModHandler.addShapedRecipe(new ItemStack(Items.MINECART, 1), new Object[]{" h ", "PwP", "WPW", 'P', OrePrefix.plate.get(Materials.AnyIron), 'W', MetaItems.Component_Minecart_Wheels_Iron});
                ModHandler.addShapedRecipe(new ItemStack(Items.MINECART, 1), new Object[]{" h ", "PwP", "WPW", 'P', OrePrefix.plate.get(Materials.Steel), 'W', MetaItems.Component_Minecart_Wheels_Steel});

                ModHandler.addShapedRecipe(new ItemStack(Items.CHEST_MINECART, 1), new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', OreDictNames.craftingChest});
                ModHandler.addShapedRecipe(new ItemStack(Items.FURNACE_MINECART, 1), new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', OreDictNames.craftingFurnace});
                ModHandler.addShapedRecipe(new ItemStack(Items.HOPPER_MINECART, 1), new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', new ItemStack(Blocks.HOPPER, 1, 32767)});
                ModHandler.addShapedRecipe(new ItemStack(Items.TNT_MINECART, 1), new Object[]{"X", "C", 'C', new ItemStack(Items.MINECART, 1), 'X', new ItemStack(Blocks.TNT, 1, 32767)});

                ModHandler.addShapedRecipe(new ItemStack(Items.CHAINMAIL_HELMET, 1), new Object[]{"RRR", "RhR", 'R', OrePrefix.ring.get(Materials.Steel)});
                ModHandler.addShapedRecipe(new ItemStack(Items.CHAINMAIL_CHESTPLATE, 1), new Object[]{"RhR", "RRR", "RRR", 'R', OrePrefix.ring.get(Materials.Steel)});
                ModHandler.addShapedRecipe(new ItemStack(Items.CHAINMAIL_LEGGINGS, 1), new Object[]{"RRR", "RhR", "R R", 'R', OrePrefix.ring.get(Materials.Steel)});
                ModHandler.addShapedRecipe(new ItemStack(Items.CHAINMAIL_BOOTS, 1), new Object[]{"R R", "RhR", 'R', OrePrefix.ring.get(Materials.Steel)});

                GTLog.out.println("GregTechMod: Adding Wool and Color releated Recipes.");
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 1), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeOrange});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 2), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeMagenta});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 3), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeLightBlue});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 4), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeYellow});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 5), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeLime});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 6), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyePink});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 7), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeGray});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 8), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeLightGray});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 9), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeCyan});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 10), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyePurple});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 11), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeBlue});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 12), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeBrown});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 13), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeGreen});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 14), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeRed});
                ModHandler.addShapelessRecipe(new ItemStack(Blocks.WOOL, 1, 15), new Object[]{new ItemStack(Blocks.WOOL, 1, 0), Dyes.dyeBlack});

                ModHandler.addShapedRecipe(new ItemStack(Blocks.STAINED_GLASS, 8, 0), new Object[]{"GGG", "GDG", "GGG", 'G', new ItemStack(Blocks.GLASS, 1), 'D', Dyes.dyeWhite});

                GTLog.out.println("GregTechMod: Putting a Potato on a Stick.");
                ModHandler.addShapelessRecipe(MetaItems.Food_Packaged_PotatoChips.get(1, new Object[0]), new Object[]{OrePrefix.foil.get(Materials.Aluminium), MetaItems.Food_PotatoChips});
                ModHandler.addShapelessRecipe(MetaItems.Food_Packaged_ChiliChips.get(1, new Object[0]), new Object[]{OrePrefix.foil.get(Materials.Aluminium), MetaItems.Food_ChiliChips});
                ModHandler.addShapelessRecipe(MetaItems.Food_Packaged_Fries.get(1, new Object[0]), new Object[]{OrePrefix.plateDouble.get(Materials.Paper), MetaItems.Food_Fries});
                ModHandler.addShapelessRecipe(MetaItems.Food_Chum_On_Stick.get(1, new Object[0]), new Object[]{OrePrefix.stick.get(Materials.Wood), MetaItems.Food_Chum});
                ModHandler.addShapelessRecipe(MetaItems.Food_Potato_On_Stick.get(1, new Object[0]), new Object[]{OrePrefix.stick.get(Materials.Wood), MetaItems.Food_Raw_Potato});
                ModHandler.addShapelessRecipe(MetaItems.Food_Potato_On_Stick_Roasted.get(1, new Object[0]), new Object[]{OrePrefix.stick.get(Materials.Wood), MetaItems.Food_Baked_Potato});
                ModHandler.addShapelessRecipe(MetaItems.Food_Dough.get(1, new Object[0]), new Object[]{OrePrefix.bucket.get(Materials.Water), OrePrefix.dust.get(Materials.Wheat)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Dough_Sugar.get(2L, new Object[0]), new Object[]{"foodDough", OrePrefix.dust.get(Materials.Sugar)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Dough_Chocolate.get(2L, new Object[0]), new Object[]{"foodDough", OrePrefix.dust.get(Materials.Cocoa)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Dough_Chocolate.get(2L, new Object[0]), new Object[]{"foodDough", OrePrefix.dust.get(Materials.Chocolate)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Flat_Dough.get(1, new Object[0]), new Object[]{"foodDough", ToolDictNames.craftingToolRollingPin});
                ModHandler.addShapelessRecipe(MetaItems.Food_Raw_Bun.get(1, new Object[0]), new Object[]{"foodDough"});
                ModHandler.addShapelessRecipe(MetaItems.Food_Raw_Bread.get(1, new Object[0]), new Object[]{"foodDough", "foodDough"});
                ModHandler.addShapelessRecipe(MetaItems.Food_Raw_Baguette.get(1, new Object[0]), new Object[]{"foodDough", "foodDough", "foodDough"});
                ModHandler.addShapelessRecipe(MetaItems.Food_Raw_Cake.get(1, new Object[0]), new Object[]{MetaItems.Food_Dough_Sugar, MetaItems.Food_Dough_Sugar, MetaItems.Food_Dough_Sugar, MetaItems.Food_Dough_Sugar});
                ModHandler.addShapelessRecipe(MetaItems.Food_ChiliChips.get(1, new Object[0]), new Object[]{MetaItems.Food_PotatoChips, OrePrefix.dust.get(Materials.Chili)});

                ModHandler.addShapelessRecipe(MetaItems.Food_Sliced_Buns.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Bun, MetaItems.Food_Sliced_Bun});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sliced_Breads.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Bread, MetaItems.Food_Sliced_Bread});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sliced_Baguettes.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Baguette, MetaItems.Food_Sliced_Baguette});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sliced_Bun.get(2L, new Object[0]), new Object[]{MetaItems.Food_Sliced_Buns});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sliced_Bread.get(2L, new Object[0]), new Object[]{MetaItems.Food_Sliced_Breads});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sliced_Baguette.get(2L, new Object[0]), new Object[]{MetaItems.Food_Sliced_Baguettes});

                ModHandler.addShapelessRecipe(MetaItems.Food_Burger_Veggie.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Buns, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Onion});
                ModHandler.addShapelessRecipe(MetaItems.Food_Burger_Cheese.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Buns, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese});
                ModHandler.addShapelessRecipe(MetaItems.Food_Burger_Meat.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Buns, OrePrefix.dust.get(Materials.MeatCooked)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Burger_Chum.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Buns, MetaItems.Food_Chum});
                ModHandler.addShapelessRecipe(MetaItems.Food_Burger_Veggie.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Bun, MetaItems.Food_Sliced_Bun, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Onion});
                ModHandler.addShapelessRecipe(MetaItems.Food_Burger_Cheese.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Bun, MetaItems.Food_Sliced_Bun, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese});
                ModHandler.addShapelessRecipe(MetaItems.Food_Burger_Meat.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Bun, MetaItems.Food_Sliced_Bun, OrePrefix.dust.get(Materials.MeatCooked)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Burger_Chum.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Bun, MetaItems.Food_Sliced_Bun, MetaItems.Food_Chum});

                ModHandler.addShapelessRecipe(MetaItems.Food_Sandwich_Veggie.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Breads, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Onion});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sandwich_Cheese.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Breads, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sandwich_Bacon.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Breads, new ItemStack(Items.COOKED_PORKCHOP, 1)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sandwich_Steak.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Breads, new ItemStack(Items.COOKED_BEEF, 1)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sandwich_Veggie.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Bread, MetaItems.Food_Sliced_Bread, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Onion});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sandwich_Cheese.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Bread, MetaItems.Food_Sliced_Bread, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sandwich_Bacon.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Bread, MetaItems.Food_Sliced_Bread, new ItemStack(Items.COOKED_PORKCHOP, 1)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Sandwich_Steak.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Bread, MetaItems.Food_Sliced_Bread, new ItemStack(Items.COOKED_BEEF, 1)});

                ModHandler.addShapelessRecipe(MetaItems.Food_Large_Sandwich_Veggie.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Baguettes, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Onion});
                ModHandler.addShapelessRecipe(MetaItems.Food_Large_Sandwich_Cheese.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Baguettes, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese});
                ModHandler.addShapelessRecipe(MetaItems.Food_Large_Sandwich_Bacon.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Baguettes, new ItemStack(Items.COOKED_PORKCHOP, 1), new ItemStack(Items.COOKED_PORKCHOP, 1)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Large_Sandwich_Steak.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Baguettes, new ItemStack(Items.COOKED_BEEF, 1), new ItemStack(Items.COOKED_BEEF, 1)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Large_Sandwich_Veggie.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Baguette, MetaItems.Food_Sliced_Baguette, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Onion});
                ModHandler.addShapelessRecipe(MetaItems.Food_Large_Sandwich_Cheese.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Baguette, MetaItems.Food_Sliced_Baguette, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese});
                ModHandler.addShapelessRecipe(MetaItems.Food_Large_Sandwich_Bacon.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Baguette, MetaItems.Food_Sliced_Baguette, new ItemStack(Items.COOKED_PORKCHOP, 1), new ItemStack(Items.COOKED_PORKCHOP, 1)});
                ModHandler.addShapelessRecipe(MetaItems.Food_Large_Sandwich_Steak.get(1, new Object[0]), new Object[]{MetaItems.Food_Sliced_Baguette, MetaItems.Food_Sliced_Baguette, new ItemStack(Items.COOKED_BEEF, 1), new ItemStack(Items.COOKED_BEEF, 1)});

                ModHandler.addShapelessRecipe(MetaItems.Food_Raw_Pizza_Veggie.get(1, new Object[0]), new Object[]{MetaItems.Food_Flat_Dough, MetaItems.Food_Sliced_Cucumber, MetaItems.Food_Sliced_Tomato, MetaItems.Food_Sliced_Onion});
                ModHandler.addShapelessRecipe(MetaItems.Food_Raw_Pizza_Cheese.get(1, new Object[0]), new Object[]{MetaItems.Food_Flat_Dough, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese, MetaItems.Food_Sliced_Cheese});
                ModHandler.addShapelessRecipe(MetaItems.Food_Raw_Pizza_Meat.get(1, new Object[0]), new Object[]{MetaItems.Food_Flat_Dough, OrePrefix.dust.get(Materials.MeatCooked)});

                ModHandler.addShapedRecipe(MetaItems.Food_Sliced_Cheese.get(4L, new Object[0]), new Object[]{"kX", 'X', "foodCheese"});
                ModHandler.addShapedRecipe(MetaItems.Food_Sliced_Lemon.get(4L, new Object[0]), new Object[]{"kX", 'X', "cropLemon"});
                ModHandler.addShapedRecipe(MetaItems.Food_Sliced_Tomato.get(4L, new Object[0]), new Object[]{"kX", 'X', "cropTomato"});
                ModHandler.addShapedRecipe(MetaItems.Food_Sliced_Onion.get(4L, new Object[0]), new Object[]{"kX", 'X', "cropOnion"});
                ModHandler.addShapedRecipe(MetaItems.Food_Sliced_Cucumber.get(4L, new Object[0]), new Object[]{"kX", 'X', "cropCucumber"});
                ModHandler.addShapedRecipe(MetaItems.Food_Sliced_Bun.get(2L, new Object[0]), new Object[]{"kX", 'X', MetaItems.Food_Baked_Bun});
                ModHandler.addShapedRecipe(MetaItems.Food_Sliced_Bread.get(2L, new Object[0]), new Object[]{"kX", 'X', MetaItems.Food_Baked_Bread});
                ModHandler.addShapedRecipe(MetaItems.Food_Sliced_Baguette.get(2L, new Object[0]), new Object[]{"kX", 'X', MetaItems.Food_Baked_Baguette});
                ModHandler.addShapedRecipe(MetaItems.Food_Raw_PotatoChips.get(1, new Object[0]), new Object[]{"kX", 'X', "cropPotato"});
                ModHandler.addShapedRecipe(MetaItems.Food_Raw_Cookie.get(4L, new Object[0]), new Object[]{"kX", 'X', MetaItems.Food_Dough_Chocolate});

                ModHandler.addShapedRecipe(MetaItems.Food_Raw_Fries.get(1, new Object[0]), new Object[]{"k", "X", 'X', "cropPotato"});
                ModHandler.addShapedRecipe(new ItemStack(Items.BOWL, 1), new Object[]{"k", "X", 'X', OrePrefix.plank.get(Materials.Wood)});
                ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.ring, Materials.Rubber, 1), new Object[]{"k", "X", 'X', OrePrefix.plate.get(Materials.Rubber)});
                ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.toolHeadArrow, Materials.Flint, 1), new Object[]{"f", "X", 'X', new ItemStack(Items.FLINT, 1, 32767)});

                ModHandler.addShapedRecipe(new ItemStack(Items.ARROW, 1), new Object[]{"  H", " S ", "F  ", 'H', new ItemStack(Items.FLINT, 1, 32767), 'S', OrePrefix.stick.get(Materials.Wood), 'F', OreDictNames.craftingFeather});

                ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.PLANKS), null, new ItemStack(Blocks.PLANKS), null, new ItemStack(Blocks.PLANKS)});
                ModHandler.removeRecipeByOutput(MetaItems.Food_Baked_Bread.get(1, new Object[0]));
                ModHandler.removeRecipeByOutput(new ItemStack(Items.COOKIE, 1));
                ModHandler.removeRecipe(new ItemStack[]{OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 1), OreDictUnifier.get(OrePrefix.ingot, Materials.Tin, 1), OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 1)});
                if (null != GTUtility.setStack(ModHandler.getRecipeOutput(true, new ItemStack[]{OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 1), OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 1), null, OreDictUnifier.get(OrePrefix.ingot, Materials.Copper, 1), OreDictUnifier.get(OrePrefix.ingot, Materials.Tin, 1)}), OreDictUnifier.get(OrePrefix.ingot, Materials.Bronze, GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "bronzeingotcrafting", true) ? 1 : 2L))) {
                        GTLog.out.println("GregTechMod: Changed Forestrys Bronze Recipe");
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "enchantmenttable", false)) {
                        GTLog.out.println("GregTechMod: Removing the Recipe of the Enchantment Table, to have more Fun at enchanting with the Anvil and Books from Dungeons.");
                        ModHandler.removeRecipeByOutput(new ItemStack(Blocks.ENCHANTING_TABLE, 1));
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "enderchest", false)) {
                        ModHandler.removeRecipeByOutput(new ItemStack(Blocks.ENDER_CHEST, 1));
                }
                tStack = OreDictUnifier.get(OrePrefix.dust, Materials.Ash, 1);
                ModHandler.addShapedRecipe(ModHandler.getRecipeOutput(new ItemStack[]{null, new ItemStack(Blocks.SAND, 1, 0), null, null, OreDictUnifier.get(OrePrefix.gem, Materials.Apatite, 1), null, null, new ItemStack(Blocks.SAND, 1, 0), null}), new Object[]{"S", "A", "S", 'A', OrePrefix.dust.get(Materials.Apatite), 'S', new ItemStack(Blocks.SAND, 1, 32767)});
                ModHandler.addShapedRecipe(ModHandler.getRecipeOutput(new ItemStack[]{tStack, tStack, tStack, tStack, OreDictUnifier.get(OrePrefix.gem, Materials.Apatite, 1), tStack, tStack, tStack, tStack}), new Object[]{"SSS", "SAS", "SSS", 'A', OrePrefix.dust.get(Materials.Apatite), 'S', OrePrefix.dust.get(Materials.Ash)});

                GTLog.out.println("GregTechMod: Adding Mixed Metal Ingot Recipes.");
                ModHandler.removeRecipeByOutput(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]));

                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.AnyIron), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(1, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Nickel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Invar), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(2L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Steel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Titanium), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(3L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(4L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.Tungsten), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(5L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(5L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(6L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Bronze), 'Z', OrePrefix.plate.get(Materials.Aluminium)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(5L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(5L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Zinc)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Mixed_Metal_Ingot.get(6L, new Object[0]), new Object[]{"X", "Y", "Z", 'X', OrePrefix.plate.get(Materials.TungstenSteel), 'Y', OrePrefix.plate.get(Materials.Brass), 'Z', OrePrefix.plate.get(Materials.Aluminium)});

                GTLog.out.println("GregTechMod: Adding Rolling Machine Recipes.");
                ModHandler.addRollingMachineRecipe(MetaItems.RC_Rail_Standard.get(4L, new Object[0]), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Aluminium).toString()});
                ModHandler.addRollingMachineRecipe(MetaItems.RC_Rail_Standard.get(32L, new Object[0]), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Titanium).toString()});
                ModHandler.addRollingMachineRecipe(MetaItems.RC_Rail_Standard.get(32L, new Object[0]), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Tungsten).toString()});

                ModHandler.addRollingMachineRecipe(MetaItems.RC_Rail_Reinforced.get(32L, new Object[0]), new Object[]{aTextIron1, aTextIron1, aTextIron1, 'X', OrePrefix.ingot.get(Materials.TungstenSteel).toString()});

                ModHandler.addRollingMachineRecipe(MetaItems.RC_Rebar.get(2L, new Object[0]), new Object[]{"  X", " X ", "X  ", 'X', OrePrefix.ingot.get(Materials.Aluminium).toString()});
                ModHandler.addRollingMachineRecipe(MetaItems.RC_Rebar.get(16L, new Object[0]), new Object[]{"  X", " X ", "X  ", 'X', OrePrefix.ingot.get(Materials.Titanium).toString()});
                ModHandler.addRollingMachineRecipe(MetaItems.RC_Rebar.get(16L, new Object[0]), new Object[]{"  X", " X ", "X  ", 'X', OrePrefix.ingot.get(Materials.Tungsten).toString()});
                ModHandler.addRollingMachineRecipe(MetaItems.RC_Rebar.get(48L, new Object[0]), new Object[]{"  X", " X ", "X  ", 'X', OrePrefix.ingot.get(Materials.TungstenSteel).toString()});

                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.light.blue", 8L), new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefix.ingot.get(Materials.Aluminium).toString()});
                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.purple", 64L), new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefix.ingot.get(Materials.Titanium).toString()});
                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.black", 64L), new Object[]{aTextIron2, " X ", aTextIron2, 'X', OrePrefix.ingot.get(Materials.Tungsten).toString()});

                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.light.blue", 8L), new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Aluminium).toString()});
                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.purple", 64L), new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Titanium).toString()});
                ModHandler.addRollingMachineRecipe(ModHandler.getModItem(aTextRailcraft, "post.metal.black", 64L), new Object[]{aTextIron1, aTextIron2, aTextIron1, 'X', OrePrefix.ingot.get(Materials.Tungsten).toString()});

                GTLog.out.println("GregTechMod: Replacing Railcraft Recipes with slightly more OreDicted Variants");

                long tBitMask = ModHandler.RecipeBits.BUFFERED | ModHandler.RecipeBits.NOT_REMOVABLE | ModHandler.RecipeBits.REVERSIBLE | ModHandler.RecipeBits.DELETE_ALL_OTHER_RECIPES_IF_SAME_NBT | ModHandler.RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES | ModHandler.RecipeBits.DELETE_ALL_OTHER_NATIVE_RECIPES | ModHandler.RecipeBits.ONLY_ADD_IF_THERE_IS_ANOTHER_RECIPE_FOR_IT;
                char tHammer = ' ';
                char tFile = ' ';
                char tWrench = ' ';
                OrePrefix tIngot = OrePrefix.ingot;
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "railcraft_stuff_use_tools", true)) {
                        tHammer = 'h';
                        tFile = 'f';
                        tWrench = 'w';
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "railcraft_stuff_use_plates", true)) {
                        tIngot = OrePrefix.plate;
                }
                ModHandler.addMirroredShapedRecipe(ModHandler.getModItem(aTextRailcraft, "part.gear", 2L, 3), new Object[]{tHammer + "" + tFile, "XX", "XX", 'X', tIngot.get(Materials.Tin)});

                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 0), new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', OrePrefix.nugget.get(Materials.Gold), 'G', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 3)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 1), new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', tIngot.get(Materials.AnyIron), 'G', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 3)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2), new Object[]{tHammer + "X ", "XGX", " X" + tFile, 'X', tIngot.get(Materials.Steel), 'G', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 3)});

                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 0), new Object[]{tWrench + "PP", tHammer + "PP", 'P', OrePrefix.plate.get(Materials.AnyIron)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 1), new Object[]{"GPG", "PGP", "GPG", 'P', OrePrefix.plate.get(Materials.AnyIron), 'G', new ItemStack(Blocks.GLASS_PANE, 1, 32767)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 2), new Object[]{"BPB", "PLP", "BPB", 'P', OrePrefix.plate.get(Materials.AnyIron), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'L', new ItemStack(Blocks.LEVER, 1, 32767)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 3), new Object[]{tWrench + "P", tHammer + "P", 'P', OrePrefix.plate.get(Materials.AnyIron)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), new Object[]{tWrench + "P", tHammer + "P", 'P', OrePrefix.plate.get(Materials.Steel)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 5), new Object[]{"BBB", "BFB", "BOB", 'B', OrePrefix.ingot.get(Materials.Brick), 'F', new ItemStack(Items.FIRE_CHARGE, 1, 32767), 'O', OreDictNames.craftingFurnace});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 6), new Object[]{"PUP", "BFB", "POP", 'P', OrePrefix.plate.get(Materials.Steel), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'F', new ItemStack(Items.FIRE_CHARGE, 1, 32767), 'U', OrePrefix.bucket.get(Materials.Empty), 'O', OreDictNames.craftingFurnace});
                ModHandler.addMirroredShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 7), new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefix.nugget.get(Materials.Gold), 'O', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 0), 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'T', OreDictNames.craftingPiston});
                ModHandler.addMirroredShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 8), new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefix.plate.get(Materials.AnyIron), 'O', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 1), 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'T', OreDictNames.craftingPiston});
                ModHandler.addMirroredShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 9), new Object[]{"PPP", tHammer + "G" + tWrench, "OTO", 'P', OrePrefix.plate.get(Materials.Steel), 'O', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2), 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'T', OreDictNames.craftingPiston});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 10), new Object[]{" E ", " O ", "OIO", 'I', tIngot.get(Materials.Gold), 'E', OrePrefix.gem.get(Materials.EnderPearl), 'O', OrePrefix.stone.get(Materials.Obsidian)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 11), new Object[]{"OOO", "OEO", "OOO", 'E', OrePrefix.gem.get(Materials.EnderPearl), 'O', OrePrefix.stone.get(Materials.Obsidian)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 12), new Object[]{"GPG", "PAP", "GPG", 'P', OreDictNames.craftingPiston, 'A', OreDictNames.craftingAnvil, 'G', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 13), new Object[]{tWrench + "PP", tHammer + "PP", 'P', OrePrefix.plate.get(Materials.Steel)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 14), new Object[]{"GPG", "PGP", "GPG", 'P', OrePrefix.plate.get(Materials.Steel), 'G', new ItemStack(Blocks.GLASS_PANE, 1, 32767)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 8L, 15), new Object[]{"BPB", "PLP", "BPB", 'P', OrePrefix.plate.get(Materials.Steel), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'L', new ItemStack(Blocks.LEVER, 1, 32767)});

                ModHandler.addShapedRecipe(MetaItems.RC_ShuntingWireFrame.get(6L, new Object[0]), new Object[]{"PPP", "R" + tWrench + "R", "RRR", 'P', OrePrefix.plate.get(Materials.AnyIron), 'R', MetaItems.RC_Rebar.get(1, new Object[0])});

                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 0), new Object[]{"IOI", "GEG", "IOI", 'I', tIngot.get(Materials.Gold), 'G', OrePrefix.gem.get(Materials.Diamond), 'E', OrePrefix.gem.get(Materials.EnderPearl), 'O', OrePrefix.stone.get(Materials.Obsidian)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 3L, 1), new Object[]{"BPB", "P" + tWrench + "P", "BPB", 'P', OrePrefix.plate.get(Materials.Steel), 'B', OrePrefix.block.get(Materials.Steel)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 2), new Object[]{"IOI", "GEG", "IOI", 'I', tIngot.get(Materials.Gold), 'G', OrePrefix.gem.get(Materials.Emerald), 'E', OrePrefix.gem.get(Materials.EnderPearl), 'O', OrePrefix.stone.get(Materials.Obsidian)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4L, 3), new Object[]{"PPP", "PFP", "PPP", 'P', OrePrefix.plate.get(Materials.Steel), 'F', OreDictNames.craftingFurnace});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 5), new Object[]{" N ", "RCR", 'R', OrePrefix.dust.get(Materials.Redstone), 'N', OrePrefix.stone.get(Materials.Netherrack), 'C', new ItemStack(Items.CAULDRON, 1, 0)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 6), new Object[]{"SGS", "EDE", "SGS", 'E', OrePrefix.gem.get(Materials.Emerald), 'S', OrePrefix.plate.get(Materials.Steel), 'G', new ItemStack(Blocks.GLASS_PANE, 1, 32767), 'D', new ItemStack(Blocks.DISPENSER, 1, 32767)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 8), new Object[]{"IPI", "PCP", "IPI", 'P', OreDictNames.craftingPiston, 'I', tIngot.get(Materials.AnyIron), 'C', new ItemStack(Blocks.CRAFTING_TABLE, 1, 32767)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 9), new Object[]{" I ", " T ", " D ", 'I', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'T', ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), 'D', new ItemStack(Blocks.DISPENSER, 1, 32767)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 10), new Object[]{" I ", "RTR", " D ", 'I', new ItemStack(Blocks.IRON_BARS, 1, 32767), 'T', ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), 'D', new ItemStack(Blocks.DISPENSER, 1, 32767), 'R', OrePrefix.dust.get(Materials.Redstone)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 10), new Object[]{"RTR", 'T', ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 9), 'R', OrePrefix.dust.get(Materials.Redstone)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 11), new Object[]{"PCP", "CSC", "PCP", 'P', OrePrefix.plank.get(Materials.Wood), 'S', OrePrefix.plate.get(Materials.Steel), 'C', new ItemStack(Items.GOLDEN_CARROT, 1, 0)});
                if (GregTechAPI.sMachineFile.get(ConfigCategories.machineconfig, "DisableRCBlastFurnace", false)) {
                        ModHandler.removeRecipeByOutput(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4, 12));
                }
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 1, 13), new Object[]{"TSB", "SCS", "PSP", 'P', OreDictNames.craftingPiston, 'S', OrePrefix.plate.get(Materials.Steel), 'B', OreDictNames.craftingBook, 'C', new ItemStack(Blocks.CRAFTING_TABLE, 1, 32767), 'T', new ItemStack(Items.DIAMOND_PICKAXE, 1, 0)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 6L, 14), new Object[]{"PPP", "ISI", "PPP", 'P', OrePrefix.plank.get(Materials.Wood), 'I', tIngot.get(Materials.AnyIron), 'S', "slimeball"});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, aTextMachineAlpha, 4L, 15), new Object[]{"PDP", "DBD", "PDP", 'P', OreDictNames.craftingPiston, 'B', OrePrefix.block.get(Materials.Steel), 'D', OrePrefix.gem.get(Materials.Diamond)});

                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "machine.epsilon", 1, 0), new Object[]{"PWP", "WWW", "PWP", 'P', OrePrefix.plate.get(Materials.AnyIron), 'W', OrePrefix.wireGt02.get(Materials.Copper)});

                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "tool.crowbar", 1, 0), new Object[]{tHammer + "DS", "DSD", "SD" + tFile, 'S', OrePrefix.ingot.get(Materials.Iron), 'D', Dyes.dyeRed});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "tool.crowbar.reinforced", 1, 0), new Object[]{tHammer + "DS", "DSD", "SD" + tFile, 'S', OrePrefix.ingot.get(Materials.Steel), 'D', Dyes.dyeRed});
                ModHandler.addMirroredShapedRecipe(ModHandler.getModItem(aTextRailcraft, "tool.whistle.tuner", 1, 0), new Object[]{"S" + tHammer + "S", "SSS", " S" + tFile, 'S', OrePrefix.nugget.get(Materials.Iron)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "part.turbine.blade", 1, 0), new Object[]{"S" + tFile, "S ", "S" + tHammer, 'S', tIngot.get(Materials.Steel)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "part.turbine.disk", 1, 0), new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefix.block.get(Materials.Steel), 'S', ModHandler.getModItem(aTextRailcraft, "part.turbine.blade", 1, 0)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "part.turbine.rotor", 1, 0), new Object[]{"SSS", " " + tWrench + " ", 'S', ModHandler.getModItem(aTextRailcraft, "part.turbine.disk", 1, 0)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "borehead.iron", 1, 0), new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefix.block.get(Materials.Iron), 'S', tIngot.get(Materials.Steel)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "borehead.steel", 1, 0), new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefix.block.get(Materials.Steel), 'S', tIngot.get(Materials.Steel)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "borehead.diamond", 1, 0), new Object[]{"SSS", "SBS", "SSS", 'B', OrePrefix.block.get(Materials.Diamond), 'S', tIngot.get(Materials.Steel)});

                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "cart.loco.steam.solid", 1, 0), new Object[]{"TTF", "TTF", "BCC", 'C', new ItemStack(Items.MINECART, 1), 'T', ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 4), 'F', ModHandler.getModItem(aTextRailcraft, aTextMachineBeta, 1, 5), 'B', new ItemStack(Blocks.IRON_BARS, 1, 32767)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "cart.loco.electric", 1, 0), new Object[]{"LP" + tWrench, "PEP", "GCG", 'C', new ItemStack(Items.MINECART, 1), 'E', ModHandler.getModItem(aTextRailcraft, "machine.epsilon", 1, 0), 'G', ModHandler.getModItem(aTextRailcraft, "part.gear", 1, 2), 'L', new ItemStack(Blocks.REDSTONE_LAMP, 1, 32767), 'P', OrePrefix.plate.get(Materials.Steel)});
                ModHandler.addShapedRecipe(ModHandler.getModItem(aTextRailcraft, "cart.bore", 1, 0), new Object[]{"BCB", "FCF", tHammer + "A" + tWrench, 'C', new ItemStack(Items.MINECART, 1), 'A', new ItemStack(Items.CHEST_MINECART, 1), 'F', OreDictNames.craftingFurnace, 'B', OrePrefix.block.get(Materials.Steel)});

                GTLog.out.println("GregTechMod: Beginning to add regular Crafting Recipes.");
                ModHandler.addShapedRecipe(ModHandler.getIC2Item(BlockName.scaffold, BlockScaffold.ScaffoldType.wood, 6), new Object[]{"WWW", " S ", "S S", 'W', OrePrefix.plank.get(Materials.Wood), 'S', OrePrefix.stick.get(Materials.Wood)});

                ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 3L), new Object[]{"NPT", "CCC", "HPT", 'H', OrePrefix.cell.get(Materials.Helium), 'N', OrePrefix.cell.get(Materials.Nitrogen), 'T', OrePrefix.pipeTiny.get(Materials.TungstenSteel), 'P', MetaItems.Electric_Pump_LV, 'C', OrePrefix.wireGt01.get(Materials.NiobiumTitanium)});
                ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 3L), new Object[]{"NPT", "CCC", "HPT", 'H', OrePrefix.cell.get(Materials.Helium), 'N', OrePrefix.cell.get(Materials.Nitrogen), 'T', OrePrefix.pipeTiny.get(Materials.TungstenSteel), 'P', MetaItems.Electric_Pump_LV, 'C', OrePrefix.wireGt01.get(Materials.VanadiumGallium)});
                ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 3L), new Object[]{"NPT", "CCC", "NPT", 'N', OrePrefix.cell.get(Materials.Nitrogen), 'T', OrePrefix.pipeTiny.get(Materials.TungstenSteel), 'P', MetaItems.Electric_Pump_LV, 'C', OrePrefix.wireGt01.get(Materials.YttriumBariumCuprate)});
                ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.wireGt01, Materials.Superconductor, 3L), new Object[]{"NPT", "CCC", "NPT", 'N', OrePrefix.cell.get(Materials.Nitrogen), 'T', OrePrefix.pipeTiny.get(Materials.TungstenSteel), 'P', MetaItems.Electric_Pump_LV, 'C', OrePrefix.wireGt01.get(Materials.HSSG)});

                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.stick, Materials.IronMagnetic, 1), new Object[]{OrePrefix.stick.get(Materials.AnyIron), OrePrefix.dust.get(Materials.Redstone), OrePrefix.dust.get(Materials.Redstone), OrePrefix.dust.get(Materials.Redstone), OrePrefix.dust.get(Materials.Redstone)});

                ModHandler.addShapedRecipe(MetaItems.IC2_Item_Casing_Gold.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.Gold)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Item_Casing_Iron.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.AnyIron)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Item_Casing_Bronze.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.Bronze)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Item_Casing_Copper.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.AnyCopper)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Item_Casing_Tin.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.Tin)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Item_Casing_Lead.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.Lead)});
                ModHandler.addShapedRecipe(MetaItems.IC2_Item_Casing_Steel.get(1, new Object[0]), new Object[]{"h P", 'P', OrePrefix.plate.get(Materials.Steel)});

                ModHandler.addShapedRecipe(new ItemStack(Blocks.TORCH, 2), new Object[]{"C", "S", 'C', OrePrefix.dust.get(Materials.Sulfur), 'S', OrePrefix.stick.get(Materials.Wood)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.TORCH, 6), new Object[]{"C", "S", 'C', OrePrefix.dust.get(Materials.Phosphor), 'S', OrePrefix.stick.get(Materials.Wood)});

                ModHandler.addShapedRecipe(new ItemStack(Blocks.PISTON, 1), new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefix.plank.get(Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.ingot.get(Materials.AnyIron)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PISTON, 1), new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefix.plank.get(Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.ingot.get(Materials.AnyBronze)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PISTON, 1), new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefix.plank.get(Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.ingot.get(Materials.Aluminium)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PISTON, 1), new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefix.plank.get(Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.ingot.get(Materials.Steel)});
                ModHandler.addShapedRecipe(new ItemStack(Blocks.PISTON, 1), new Object[]{"WWW", "CBC", "CRC", 'W', OrePrefix.plank.get(Materials.Wood), 'C', OrePrefix.stoneCobble, 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.ingot.get(Materials.Titanium)});

                ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.reactor_heat_vent, 1), new Object[]{"AIA", "I I", "AIA", 'I', new ItemStack(Blocks.IRON_BARS, 1), 'A', OrePrefix.plate.get(Materials.Aluminium)});
                ModHandler.addShapelessRecipe(ModHandler.getIC2Item(ItemName.containment_plating, 1), new Object[]{ModHandler.getIC2Item(ItemName.plating, 1), OrePrefix.plate.get(Materials.Lead)});
                if (!Materials.Steel.mBlastFurnaceRequired) {
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Steel, 1), new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Coal), OrePrefix.dust.get(Materials.Coal)});
                }
                if (GregTechMod.gregtechproxy.mNerfDustCrafting) {
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Electrum, 6L), new Object[]{OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.Gold)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Brass, 3L), new Object[]{OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.Zinc)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Brass, 9L), new Object[]{OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Zinc)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Bronze, 3L), new Object[]{OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.Tin)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Bronze, 9L), new Object[]{OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tin)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Invar, 9L), new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Nickel)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Cupronickel, 6L), new Object[]{OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.AnyCopper)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dustSmall, Materials.Nichrome, 15L), new Object[]{OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Chrome)});
                } else {
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Electrum, 2L), new Object[]{OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.Gold)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Brass, 4L), new Object[]{OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.Zinc)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Brass, 3L), new Object[]{OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Zinc)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Bronze, 4L), new Object[]{OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.Tin)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Bronze, 3L), new Object[]{OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tetrahedrite), OrePrefix.dust.get(Materials.Tin)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Invar, 3L), new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Nickel)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Cupronickel, 2L), new Object[]{OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.AnyCopper)});
                        ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Nichrome, 5L), new Object[]{OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Chrome)});
                }
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.RoseGold, 5L), new Object[]{OrePrefix.dust.get(Materials.Gold), OrePrefix.dust.get(Materials.Gold), OrePrefix.dust.get(Materials.Gold), OrePrefix.dust.get(Materials.Gold), OrePrefix.dust.get(Materials.AnyCopper)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.SterlingSilver, 5L), new Object[]{OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.AnyCopper)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.BlackBronze, 5L), new Object[]{OrePrefix.dust.get(Materials.Gold), OrePrefix.dust.get(Materials.Silver), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.BismuthBronze, 5L), new Object[]{OrePrefix.dust.get(Materials.Bismuth), OrePrefix.dust.get(Materials.Zinc), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.BlackSteel, 5L), new Object[]{OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.BlackBronze), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.RedSteel, 8L), new Object[]{OrePrefix.dust.get(Materials.SterlingSilver), OrePrefix.dust.get(Materials.BismuthBronze), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.BlueSteel, 8L), new Object[]{OrePrefix.dust.get(Materials.RoseGold), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel), OrePrefix.dust.get(Materials.BlackSteel)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Osmiridium, 4L), new Object[]{OrePrefix.dust.get(Materials.Iridium), OrePrefix.dust.get(Materials.Iridium), OrePrefix.dust.get(Materials.Iridium), OrePrefix.dust.get(Materials.Osmium)});

                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Ultimet, 9L), new Object[]{OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Chrome), OrePrefix.dust.get(Materials.Chrome), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Molybdenum)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.CobaltBrass, 9L), new Object[]{OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Brass), OrePrefix.dust.get(Materials.Aluminium), OrePrefix.dust.get(Materials.Cobalt)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.StainlessSteel, 9L), new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Nickel), OrePrefix.dust.get(Materials.Manganese), OrePrefix.dust.get(Materials.Chrome)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.YttriumBariumCuprate, 6L), new Object[]{OrePrefix.dust.get(Materials.Yttrium), OrePrefix.dust.get(Materials.Barium), OrePrefix.dust.get(Materials.Barium), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper), OrePrefix.dust.get(Materials.AnyCopper)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Kanthal, 3L), new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.Aluminium), OrePrefix.dust.get(Materials.Chrome)});

                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Ultimet, 1), new Object[]{OrePrefix.dustTiny.get(Materials.Cobalt), OrePrefix.dustTiny.get(Materials.Cobalt), OrePrefix.dustTiny.get(Materials.Cobalt), OrePrefix.dustTiny.get(Materials.Cobalt), OrePrefix.dustTiny.get(Materials.Cobalt), OrePrefix.dustTiny.get(Materials.Chrome), OrePrefix.dustTiny.get(Materials.Chrome), OrePrefix.dustTiny.get(Materials.Nickel), OrePrefix.dustTiny.get(Materials.Molybdenum)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.CobaltBrass, 1), new Object[]{OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Brass), OrePrefix.dustTiny.get(Materials.Aluminium), OrePrefix.dustTiny.get(Materials.Cobalt)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.StainlessSteel, 1), new Object[]{OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Nickel), OrePrefix.dustTiny.get(Materials.Manganese), OrePrefix.dustTiny.get(Materials.Chrome)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dustTiny, Materials.YttriumBariumCuprate, 6), new Object[]{OrePrefix.dustTiny.get(Materials.Yttrium), OrePrefix.dustTiny.get(Materials.Barium), OrePrefix.dustTiny.get(Materials.Barium), OrePrefix.dustTiny.get(Materials.AnyCopper), OrePrefix.dustTiny.get(Materials.AnyCopper), OrePrefix.dustTiny.get(Materials.AnyCopper)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dustTiny, Materials.Kanthal, 3), new Object[]{OrePrefix.dustTiny.get(Materials.Iron), OrePrefix.dustTiny.get(Materials.Aluminium), OrePrefix.dustTiny.get(Materials.Chrome)});

                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.VanadiumSteel, 9), new Object[]{OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Steel), OrePrefix.dust.get(Materials.Vanadium), OrePrefix.dust.get(Materials.Chrome)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.HSSG, 9), new Object[]{OrePrefix.dust.get(Materials.TungstenSteel), OrePrefix.dust.get(Materials.TungstenSteel), OrePrefix.dust.get(Materials.TungstenSteel), OrePrefix.dust.get(Materials.TungstenSteel), OrePrefix.dust.get(Materials.TungstenSteel), OrePrefix.dust.get(Materials.Chrome), OrePrefix.dust.get(Materials.Molybdenum), OrePrefix.dust.get(Materials.Molybdenum), OrePrefix.dust.get(Materials.Vanadium)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.HSSE, 9), new Object[]{OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.Cobalt), OrePrefix.dust.get(Materials.Manganese), OrePrefix.dust.get(Materials.Silicon)});
                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.HSSS, 9), new Object[]{OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.HSSG), OrePrefix.dust.get(Materials.Iridium), OrePrefix.dust.get(Materials.Iridium), OrePrefix.dust.get(Materials.Osmium)});


                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.IronWood, 2L), new Object[]{OrePrefix.dust.get(Materials.Iron), OrePrefix.dust.get(Materials.LiveRoot), OrePrefix.dustTiny.get(Materials.Gold)});

                ModHandler.addShapelessRecipe(new ItemStack(Items.GUNPOWDER, 3), new Object[]{OrePrefix.dust.get(Materials.Coal), OrePrefix.dust.get(Materials.Sulfur), OrePrefix.dust.get(Materials.Saltpeter), OrePrefix.dust.get(Materials.Saltpeter)});
                ModHandler.addShapelessRecipe(new ItemStack(Items.GUNPOWDER, 2), new Object[]{OrePrefix.dust.get(Materials.Charcoal), OrePrefix.dust.get(Materials.Sulfur), OrePrefix.dust.get(Materials.Saltpeter), OrePrefix.dust.get(Materials.Saltpeter)});

                ModHandler.addShapelessRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Saltpeter, 5L), new Object[]{OrePrefix.dust.get(Materials.Potassium), OrePrefix.cell.get(Materials.Nitrogen), OrePrefix.cell.get(Materials.Oxygen), OrePrefix.cell.get(Materials.Oxygen), OrePrefix.cell.get(Materials.Oxygen)});
                ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_fibre, 1));
                if (GregTechMod.gregtechproxy.mDisableIC2Cables) {
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
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.batbox, 1), new Object[]{"PCP", "BBB", "PPP", 'C', OrePrefix.cableGt01.get(Materials.Tin), 'P', OrePrefix.plank.get(Materials.Wood), 'B', OrePrefix.battery.get(Materials.Basic)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.mfe, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.mfe, 1), new Object[]{"CEC", "EME", "CEC", 'C', OrePrefix.cableGt01.get(Materials.Gold), 'E', OrePrefix.battery.get(Materials.Elite), 'M', ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.lv_transformer, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.lv_transformer, 1), new Object[]{"PCP", "POP", "PCP", 'C', OrePrefix.cableGt01.get(Materials.Tin), 'O', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coil, 1), 'P', OrePrefix.plank.get(Materials.Wood)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1), new Object[]{"CMC", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'M', ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.hv_transformer, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.hv_transformer, 1), new Object[]{" C ", "IMB", " C ", 'C', OrePrefix.cableGt01.get(Materials.Gold), 'M', ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1), 'I', OrePrefix.circuit.get(Materials.Basic), 'B', OrePrefix.battery.get(Materials.Advanced)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.ev_transformer, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.ev_transformer, 1), new Object[]{" C ", "IMB", " C ", 'C', OrePrefix.cableGt01.get(Materials.Aluminium), 'M', ModHandler.getIC2TEItem(TeBlock.hv_transformer, 1), 'I', OrePrefix.circuit.get(Materials.Advanced), 'B', OrePrefix.battery.get(Materials.Master)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.cesu, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.cesu, 1), new Object[]{"PCP", "BBB", "PPP", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'P', OrePrefix.plate.get(Materials.Bronze), 'B', OrePrefix.battery.get(Materials.Advanced)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.luminator_flat, 1));
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.teleporter, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.teleporter, 1), new Object[]{"GFG", "CMC", "GDG", 'C', OrePrefix.cableGt01.get(Materials.Platinum), 'G', OrePrefix.circuit.get(Materials.Advanced), 'D', OrePrefix.gem.get(Materials.Diamond), 'M', ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1), 'F', ModHandler.getIC2Item(ItemName.frequency_transmitter, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.energy_o_mat, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.energy_o_mat, 1), new Object[]{"RBR", "CMC", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'R', OrePrefix.dust.get(Materials.Redstone), 'B', OrePrefix.battery.get(Materials.Basic), 'M', ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.advanced_re_battery, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.advanced_re_battery, 1), new Object[]{"CTC", "TST", "TLT", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'S', OrePrefix.dust.get(Materials.Sulfur), 'L', OrePrefix.dust.get(Materials.Lead), 'T', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.bronze, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.boat, ItemIC2Boat.BoatType.electric, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.boat, ItemIC2Boat.BoatType.electric, 1), new Object[]{"CCC", "XWX", aTextIron2, 'C', OrePrefix.cableGt01.get(Materials.Copper), 'X', OrePrefix.plate.get(Materials.Iron), 'W', ModHandler.getIC2TEItem(TeBlock.water_generator, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.cropnalyzer, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.cropnalyzer, 1), new Object[]{"CC ", "RGR", "RIR", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'R', OrePrefix.dust.get(Materials.Redstone), 'G', OrePrefix.block.get(Materials.Glass), 'I', OrePrefix.circuit.get(Materials.Basic)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coil, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.coil, 1), new Object[]{"CCC", "CXC", "CCC", 'C', OrePrefix.wireGt01.get(Materials.Copper), 'X', OrePrefix.ingot.get(Materials.AnyIron)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.power_unit, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.power_unit, 1), new Object[]{"BCA", "BIM", "BCA", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'B', OrePrefix.battery.get(Materials.Basic), 'A', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.iron, 1), 'I', OrePrefix.circuit.get(Materials.Basic), 'M', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.electric_motor, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.small_power_unit, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.small_power_unit, 1), new Object[]{" CA", "BIM", " CA", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'B', OrePrefix.battery.get(Materials.Basic), 'A', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.iron, 1), 'I', OrePrefix.circuit.get(Materials.Basic), 'M', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.electric_motor, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.remote, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.remote, 1), new Object[]{" C ", "TLT", " F ", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'L', OrePrefix.dust.get(Materials.Lapis), 'T', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.tin, 1), 'F', ModHandler.getIC2Item(ItemName.frequency_transmitter, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.scanner, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.scanner, 1), new Object[]{"PGP", "CBC", "WWW", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'G', OrePrefix.dust.get(Materials.Glowstone), 'B', OrePrefix.battery.get(Materials.Advanced), 'C', OrePrefix.circuit.get(Materials.Advanced), 'P', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.gold, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.advanced_scanner, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.advanced_scanner, 1), new Object[]{"PDP", "GCG", "WSW", 'W', OrePrefix.cableGt01.get(Materials.Gold), 'G', OrePrefix.dust.get(Materials.Glowstone), 'D', OrePrefix.battery.get(Materials.Elite), 'C', OrePrefix.circuit.get(Materials.Advanced), 'P', ModHandler.getIC2Item(ItemName.casing, CasingResourceType.gold, 1), 'S', ModHandler.getIC2Item(ItemName.scanner, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.solar_helmet, 1));
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.static_boots, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.static_boots, 1), new Object[]{"I I", "IWI", "CCC", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'I', OrePrefix.ingot.get(Materials.Iron), 'W', new ItemStack(Blocks.WOOL)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.meter, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.meter, 1), new Object[]{" G ", "CIC", "C C", 'C', OrePrefix.cableGt01.get(Materials.Copper), 'G', OrePrefix.dust.get(Materials.Glowstone), 'I', OrePrefix.circuit.get(Materials.Basic)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.obscurator, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.obscurator, 1), new Object[]{"RER", "CAC", "RRR", 'C', OrePrefix.cableGt01.get(Materials.Gold), 'R', OrePrefix.dust.get(Materials.Redstone), 'E', OrePrefix.battery.get(Materials.Advanced), 'A', OrePrefix.circuit.get(Materials.Advanced)});
                        //ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.overclocker, 1));
                        //ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.overclocker, 1), new Object[]{"CCC", "WEW", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'C', ModHandler.getIC2Item(ItemName.heat_vent, 1, 1), 'E', OrePrefix.circuit.get(Materials.Basic)});
                        //ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.transformer, 1));
                        //ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.transformer, 1), new Object[]{"GGG", "WTW", "GEG", 'W', OrePrefix.cableGt01.get(Materials.Gold), 'T', ModHandler.getIC2TEItem(TeBlock.mv_transformer, 1), 'E', OrePrefix.circuit.get(Materials.Basic), 'G', OrePrefix.block.get(Materials.Glass)});
                        //ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.energy_storage, 1));
                        //ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.energy_storage, 1), new Object[]{"PPP", "WBW", "PEP", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'E', OrePrefix.circuit.get(Materials.Basic), 'P', OrePrefix.plank.get(Materials.Wood), 'B', OrePrefix.battery.get(Materials.Basic)});
                        //ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.ejector, 1));
                        //ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.ejector, 1), new Object[]{"PHP", "WEW", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'E', OrePrefix.circuit.get(Materials.Basic), 'P', new ItemStack(Blocks.PISTON), 'H', new ItemStack(Blocks.HOPPER)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.single_use_battery, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.single_use_battery, 1), new Object[]{"W", "C", "R", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'C', OrePrefix.dust.get(Materials.HydratedCoal), 'R', OrePrefix.dust.get(Materials.Redstone)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.frequency_transmitter, 1));
                        //ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.pulling, 1));
                        //ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.upgrade, ItemUpgradeModule.UpgradeType.pulling, 1), new Object[]{"PHP", "WEW", 'W', OrePrefix.cableGt01.get(Materials.Copper), 'P', new ItemStack(Blocks.STICKY_PISTON), 'R', new ItemStack(Blocks.HOPPER), 'E', OrePrefix.circuit.get(Materials.Basic)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.cutter, 1));

                        if(Loader.isModLoaded("GraviSuite")){
                                ModHandler.removeRecipeByOutput(ModHandler.getModItem("GraviSuite", "advJetpack", 1));
                                ModHandler.addShapedRecipe(ModHandler.getModItem("GraviSuite", "advJetpack", 1), new Object[]{"PJP","BLB","WCW",'P', OrePrefix.plateAlloy.get(Materials.Carbon),'J',ModHandler.getIC2Item(ItemName.jetpack_electric, 1),'B',ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1, 6),'L',ModHandler.getModItem("GraviSuite", "advLappack", 1),'W', OrePrefix.wireGt04.get(Materials.Platinum),'C', OrePrefix.circuit.get(Materials.Advanced)});
                                ModHandler.removeRecipeByOutput(ModHandler.getModItem("GraviSuite", "itemSimpleItem", 3, 1));
                                ModHandler.addShapedRecipe(ModHandler.getModItem("GraviSuite", "itemSimpleItem", 3, 1), new Object[]{"CCC","WWW","CCC",'C',ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1),'W', OrePrefix.wireGt01.get(Materials.Superconductor)});
                        }
                } else {
                        ModHandler.addShapedRecipe(ItemCable.getCable(CableType.glass, 0), new Object[]{"GGG", "EDE", "GGG", 'G', new ItemStack(Blocks.GLASS, 1, 32767), 'D', OrePrefix.dust.get(Materials.Silver), 'E', MetaItems.IC2_Energium_Dust.get(1, new Object[0])});
                }

                if(Loader.isModLoaded("ImmersiveEngineering")){
                        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.stick, Materials.Iron, 4));
                        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.stick, Materials.Steel, 4));
                        ModHandler.removeRecipeByOutput(OreDictUnifier.get(OrePrefix.stick, Materials.Aluminium, 4));
                }

                ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.uranium_fuel_rod, 1), new Object[]{"UUU", "NNN", "UUU", 'U', OrePrefix.ingot.get(Materials.Uranium), 'N', OrePrefix.nugget.get(Materials.Uranium235)});
                ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.mox_fuel_rod, 1), new Object[]{"UUU", "NNN", "UUU", 'U', OrePrefix.ingot.get(Materials.Uranium), 'N', OrePrefix.ingot.get(Materials.Plutonium)});

                if (!GregTechAPI.mIC2Classic) {
                        ModHandler.addShapedRecipe(MetaItems.Uraniumcell_2.get(1, new Object[0]), new Object[]{"RPR", "   ", "   ", 'R', MetaItems.Uraniumcell_1, 'P', OrePrefix.plate.get(Materials.Iron)});
                        ModHandler.addShapedRecipe(MetaItems.Uraniumcell_4.get(1, new Object[0]), new Object[]{"RPR", "CPC", "RPR", 'R', MetaItems.Uraniumcell_1, 'P', OrePrefix.plate.get(Materials.Iron), 'C', OrePrefix.plate.get(Materials.Copper)});
                        ModHandler.addShapedRecipe(MetaItems.Moxcell_2.get(1, new Object[0]), new Object[]{"RPR", "   ", "   ", 'R', MetaItems.Moxcell_1, 'P', OrePrefix.plate.get(Materials.Iron)});
                        ModHandler.addShapedRecipe(MetaItems.Moxcell_4.get(1, new Object[0]), new Object[]{"RPR", "CPC", "RPR", 'R', MetaItems.Moxcell_1, 'P', OrePrefix.plate.get(Materials.Iron), 'C', OrePrefix.plate.get(Materials.Copper)});

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.mining_laser, 1).copy());
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P', OrePrefix.plate.get(Materials.Titanium),'G', OrePrefix.gemExquisite.get(Materials.Diamond),'E',MetaItems.Emitter_HV,'C', OrePrefix.circuit.get(Materials.Master),'S', OrePrefix.screw.get(Materials.Titanium),'B',new ItemStack(ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1, GTValues.W)});
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P', OrePrefix.plate.get(Materials.Titanium),'G', OrePrefix.gemExquisite.get(Materials.Ruby),'E',MetaItems.Emitter_HV,'C', OrePrefix.circuit.get(Materials.Master),'S', OrePrefix.screw.get(Materials.Titanium),'B',new ItemStack(ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1, GTValues.W)});
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P', OrePrefix.plate.get(Materials.Titanium),'G', OrePrefix.gemExquisite.get(Materials.Jasper),'E',MetaItems.Emitter_HV,'C', OrePrefix.circuit.get(Materials.Master),'S', OrePrefix.screw.get(Materials.Titanium),'B',new ItemStack(ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1, GTValues.W)});
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.mining_laser, 1).copy(), new Object[]{"PPP","GEC","SBd",'P', OrePrefix.plate.get(Materials.Titanium),'G', OrePrefix.gemExquisite.get(Materials.GarnetRed),'E',MetaItems.Emitter_HV,'C', OrePrefix.circuit.get(Materials.Master),'S', OrePrefix.screw.get(Materials.Titanium),'B',new ItemStack(ModHandler.getIC2Item(ItemName.charging_lapotron_crystal, 1).copy().getItem(),1, GTValues.W)});
                }

                ModHandler.removeRecipeByOutput(MetaItems.IC2_Energium_Dust.get(1, new Object[0]));
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.gregtechrecipes, "energycrystalruby", true)) {
                        ModHandler.addShapedRecipe(MetaItems.IC2_Energium_Dust.get(9L, new Object[0]), new Object[]{"RDR", "DRD", "RDR", 'R', OrePrefix.dust.get(Materials.Redstone), 'D', OrePrefix.dust.get(Materials.Ruby)});
                } else {
                        ModHandler.addShapedRecipe(MetaItems.IC2_Energium_Dust.get(9L, new Object[0]), new Object[]{"RDR", "DRD", "RDR", 'R', OrePrefix.dust.get(Materials.Redstone), 'D', OrePrefix.dust.get(Materials.Diamond)});
                }
                ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.lapotron_crystal, 1));
                for(Materials tCMat : new Materials[]{Materials.Lapis, Materials.Lazurite, Materials.Sodalite}){
                        ModHandler.addShapelessRecipe(ModHandler.getIC2Item(ItemName.lapotron_crystal, 1), new Object[]{OrePrefix.gemExquisite.get(Materials.Sapphire), OrePrefix.stick.get(tCMat),MetaItems.Circuit_Parts_Wiring_Elite});
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.lapotron_crystal, 1), new Object[]{"LCL", "RSR", "LCL", 'C', OrePrefix.circuit.get(Materials.Data), 'S', ModHandler.getIC2Item(ItemName.energy_crystal, 1, 32767), 'L', OrePrefix.plate.get(tCMat), 'R', OrePrefix.stick.get(tCMat)});
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.lapotron_crystal, 1), new Object[]{"LCL", "RSR", "LCL", 'C', OrePrefix.circuit.get(Materials.Advanced), 'S', OrePrefix.gemFlawless.get(Materials.Sapphire), 'L', OrePrefix.plate.get(tCMat), 'R', OrePrefix.stick.get(tCMat)});
                }
                ModHandler.removeRecipe(ModHandler.getIC2Item(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe, 8));
                ModHandler.addShapedRecipe(ModHandler.getIC2Item(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe, 1), new Object[]{"hPf",'P', OrePrefix.pipeSmall.get(Materials.Steel)});
                GTValues.RA.addWiremillRecipe(OreDictUnifier.get(OrePrefix.pipeTiny, Materials.Steel, 1), ModHandler.getIC2Item(BlockName.mining_pipe, BlockMiningPipe.MiningPipeType.pipe, 1), 200, 16);

                ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.luminator_flat, 16), new Object[]{"RTR", "GHG", "GGG", 'H', OrePrefix.cell.get(Materials.Helium), 'T', OrePrefix.ingot.get(Materials.Tin), 'R', OrePrefix.ingot.get(Materials.AnyIron), 'G', new ItemStack(Blocks.GLASS, 1)});
                ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.luminator_flat, 16), new Object[]{"RTR", "GHG", "GGG", 'H', OrePrefix.cell.get(Materials.Mercury), 'T', OrePrefix.ingot.get(Materials.Tin), 'R', OrePrefix.ingot.get(Materials.AnyIron), 'G', new ItemStack(Blocks.GLASS, 1)});

                ModHandler.removeRecipe(new ItemStack[]{tStack = OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), tStack, tStack, tStack, new ItemStack(Items.COAL, 1, 0), tStack, tStack, tStack, tStack});
                ModHandler.removeRecipe(new ItemStack[]{tStack = OreDictUnifier.get(OrePrefix.dust, Materials.Sulfur, 1), tStack, tStack, tStack, new ItemStack(Items.COAL, 1, 1), tStack, tStack, tStack, tStack});
                ModHandler.removeRecipe(new ItemStack[]{null, tStack = new ItemStack(Items.COAL, 1), null, tStack, OreDictUnifier.get(OrePrefix.ingot, Materials.Iron, 1), tStack, null, tStack, null});

                ModHandler.removeFurnaceSmelting(new ItemStack(Blocks.HOPPER));

                GTLog.out.println("GregTechMod: Applying harder Recipes for several Blocks.");
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "blockbreaker", false)) {
                        ModHandler.addShapedRecipe(ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Items.IRON_PICKAXE, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.PISTON, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Blocks.COBBLESTONE, 1), new ItemStack(Items.REDSTONE, 1), new ItemStack(Blocks.COBBLESTONE, 1)}), new Object[]{"RGR", "RPR", "RCR", 'G', OreDictNames.craftingGrinder, 'C', OrePrefix.circuit.get(Materials.Advanced), 'R', OrePrefix.plate.get(Materials.Steel), 'P', OreDictNames.craftingPiston});
                }
                if ((GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "beryliumreflector", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1, 1)))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1, 1), new Object[]{" N ", "NBN", " N ", 'B', OrePrefix.plateDouble.get(Materials.Beryllium), 'N', ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1)});
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.thick_neutron_reflector, 1, 1), new Object[]{" B ", "NCN", " B ", 'B', OrePrefix.plate.get(Materials.Beryllium), 'N', ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1), 'C', OrePrefix.plate.get(Materials.TungstenCarbide)});
                }
                if ((GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "reflector", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1)))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1), new Object[]{"TGT", "GSG", "TGT", 'T', OrePrefix.plate.get(Materials.Tin), 'G', OrePrefix.dust.get(Materials.Graphite), 'S', OrePrefix.plateDouble.get(Materials.Steel)});
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.neutron_reflector, 1, 1), new Object[]{"TTT", "GSG", "TTT", 'T', OrePrefix.plate.get(Materials.TinAlloy), 'G', OrePrefix.dust.get(Materials.Graphite), 'S', OrePrefix.plate.get(Materials.Beryllium)});
                }
                if ((GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "cropharvester", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.crop_harvester, 1)))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.crop_harvester, 1), new Object[]{"ACA", "PMS", "WOW", 'M', MetaItems.Hull_HV, 'C', OrePrefix.circuit.get(Materials.Master), 'A', MetaItems.Robot_Arm_HV, 'P', MetaItems.Electric_Piston_HV, 'S', MetaItems.Sensor_HV, 'W', OrePrefix.toolHeadSense.get(Materials.StainlessSteel), 'O', MetaItems.Conveyor_Module_HV});
                }
                if ((GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "nuclearReactor", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.nuclear_reactor, 1)))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2TEItem(TeBlock.nuclear_reactor, 1), new Object[]{"PCP", "PMP", "PAP", 'P', OrePrefix.plateDense.get(Materials.Lead), 'C', OrePrefix.circuit.get(Materials.Master), 'M', ModHandler.getIC2TEItem(TeBlock.reactor_chamber, 1), 'A', MetaItems.Robot_Arm_EV});

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.reactor_chamber, 1));
                        GTValues.RA.addAssemblerRecipe(MetaItems.Hull_EV.get(1, new Object[0]), OreDictUnifier.get(OrePrefix.plate, Materials.Lead, 4), ModHandler.getIC2TEItem(TeBlock.reactor_chamber, 1), 200, 256);

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1));
                        GTValues.RA.addChemicalBathRecipe(OreDictUnifier.get(OrePrefix.frameGt, Materials.Lead, 1), Materials.Concrete.getMolten(144), ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), GTValues.NI, GTValues.NI, null, 400, 80);

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.reactor_access_hatch, 1));
                        GTValues.RA.addAssemblerRecipe(ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), MetaItems.Conveyor_Module_EV.get(1, new Object[0]), ModHandler.getIC2TEItem(TeBlock.reactor_access_hatch, 1), 200, 80);

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.reactor_fluid_port, 1));
                        GTValues.RA.addAssemblerRecipe(ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), MetaItems.Electric_Pump_EV.get(1, new Object[0]), ModHandler.getIC2TEItem(TeBlock.reactor_fluid_port, 1), 200, 80);

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2TEItem(TeBlock.reactor_redstone_port, 1));
                        GTValues.RA.addAssemblerRecipe(ModHandler.getIC2Item(BlockName.resource, ResourceBlock.reactor_vessel, 1), OreDictUnifier.get(OrePrefix.circuit, Materials.Master, 1), ModHandler.getIC2TEItem(TeBlock.reactor_redstone_port, 1), 200, 80);
                }
                if ((GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "rtg", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(BlockName.te, TeBlock.rt_generator, 1)))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(BlockName.te, TeBlock.rt_generator, 1), new Object[]{"III", "IMI", "ICI", 'I', MetaItems.IC2_Item_Casing_Steel, 'C', OrePrefix.circuit.get(Materials.Master), 'M', MetaItems.Hull_IV});

                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(BlockName.te, TeBlock.rt_heat_generator, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(BlockName.te, TeBlock.rt_heat_generator, 1), new Object[]{"III", "IMB", "ICI", 'I', MetaItems.IC2_Item_Casing_Steel, 'C', OrePrefix.circuit.get(Materials.Master), 'M', MetaItems.Hull_IV, 'B', OreDictUnifier.get(OrePrefix.block, Materials.Copper, 1)});
                }
                if ((GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "windRotor", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.rotor_carbon, 1)))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.rotor_carbon, 1), new Object[]{"dBS", "BTB", "SBw", 'B', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_rotor_blade, 1), 'S', OrePrefix.screw.get(Materials.Iridium), 'T', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.steel_shaft, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.rotor_steel, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.rotor_steel, 1), new Object[]{"dBS", "BTB", "SBw", 'B', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.steel_rotor_blade, 1), 'S', OrePrefix.screw.get(Materials.StainlessSteel), 'T', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.iron_shaft, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.rotor_iron, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.rotor_iron, 1), new Object[]{"dBS", "BTB", "SBw", 'B', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.iron_rotor_blade, 1), 'S', OrePrefix.screw.get(Materials.WroughtIron), 'T', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.iron_shaft, 1)});
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.rotor_wood, 1));
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.rotor_wood, 1), new Object[]{"dBS", "BTB", "SBw", 'B', ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.wood_rotor_blade, 1), 'S', OrePrefix.screw.get(Materials.WroughtIron), 'T', OrePrefix.stickLong.get(Materials.WroughtIron)});
                }
                if (OreDictUnifier.get(OrePrefix.gear, Materials.Diamond, 1) != null) {
                        tStack = ModHandler.getRecipeOutput(new ItemStack[]{OreDictUnifier.get(OrePrefix.gear, Materials.Iron, 1), new ItemStack(Items.REDSTONE, 1), OreDictUnifier.get(OrePrefix.gear, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.gear, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.gear, Materials.Iron, 1), OreDictUnifier.get(OrePrefix.gear, Materials.Gold, 1), OreDictUnifier.get(OrePrefix.gear, Materials.Diamond, 1), new ItemStack(Items.DIAMOND_PICKAXE, 1), OreDictUnifier.get(OrePrefix.gear, Materials.Diamond, 1)});
                        if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "quarry", true)) {
                                ModHandler.removeRecipeByOutput(tStack);
                                ModHandler.addShapedRecipe(tStack, new Object[]{"ICI", "GIG", "DPD", 'C', OrePrefix.circuit.get(Materials.Advanced), 'D', OrePrefix.gear.get(Materials.Diamond), 'G', OrePrefix.gear.get(Materials.Gold), 'I', OrePrefix.gear.get(Materials.Steel), 'P', ModHandler.getIC2Item(ItemName.diamond_drill, 1, true)});
                        }
                        if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "quarry", false)) {
                                ModHandler.removeRecipeByOutput(tStack);
                        }
                }
                if ((GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "sugarpaper", true))) {
                        ModHandler.removeRecipeByOutput(new ItemStack(Items.PAPER));
                        ModHandler.removeRecipeByOutput(new ItemStack(Items.SUGAR));
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Paper, 2), new Object[]{"SSS", " m ", 'S', new ItemStack(Items.REEDS)});
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.dust, Materials.Sugar, 1), new Object[]{"Sm ", 'S', new ItemStack(Items.REEDS)});
                        //ItemStack brick = new ItemStack(new ItemStack(Blocks.stone_slab).getItem().setContainerItem(new ItemStack(Blocks.stone_slab).getItem()));
                        //ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.paper, Materials.Empty, 2), new Object[]{" C ", "SSS", " C ", 'S', OreDictUnifier.get(OrePrefix.dust, Materials.Paper, 1), 'C', brick});
                        ModHandler.addShapedRecipe(OreDictUnifier.get(OrePrefix.paper, Materials.Empty, 2), new Object[]{" C ", "SSS", " C ", 'S', OreDictUnifier.get(OrePrefix.dust, Materials.Paper, 1), 'C', new ItemStack(Blocks.STONE_SLAB)});
                        //GameRegistry.addRecipe(OreDictUnifier.get(OrePrefix.paper, Materials.Empty, 2), " C ", "SSS", " C ", 'S', OreDictUnifier.get(OrePrefix.dust, Materials.Paper, 1), 'C', brick);
                }

                GTLog.out.println("GregTechMod: Applying Recipes for Tools");
                if ((GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "nanosaber", true)) &&
                        (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.nano_saber, 1)))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.nano_saber, 1), new Object[]{"PI ", "PI ", "CLC", 'L', OrePrefix.battery.get(Materials.Master), 'I', OrePrefix.plateAlloy.get("Iridium"), 'P', OrePrefix.plate.get(Materials.Platinum), 'C', OrePrefix.circuit.get(Materials.Master)});
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "namefix", true)) {
                        ModHandler.addShapedRecipe(ModHandler.removeRecipeByOutput(new ItemStack(Items.FLINT_AND_STEEL, 1)) ? new ItemStack(Items.FLINT_AND_STEEL, 1) : null, new Object[]{"S ", " F", 'F', new ItemStack(Items.FLINT, 1), 'S', "nuggetSteel"});
                }
                if (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.diamond_drill, 1))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.diamond_drill, 1), new Object[]{" D ", "DMD", "TAT", 'M', ModHandler.getIC2Item(ItemName.diamond_drill, 1, true), 'D', OreDictNames.craftingIndustrialDiamond, 'T', OrePrefix.plate.get(Materials.Titanium), 'A', OrePrefix.circuit.get(Materials.Advanced)});
                }
                if (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.drill, 1))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.drill, 1), new Object[]{" S ", "SCS", "SBS", 'C', OrePrefix.circuit.get(Materials.Basic), 'B', OrePrefix.battery.get(Materials.Basic), 'S', GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefix.plate.get(Materials.StainlessSteel) : OrePrefix.plate.get(Materials.Iron)});
                }
                if (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.chainsaw, 1))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.chainsaw, 1), new Object[]{"BS ", "SCS", " SS", 'C', OrePrefix.circuit.get(Materials.Basic), 'B', OrePrefix.battery.get(Materials.Basic), 'S', GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefix.plate.get(Materials.StainlessSteel) : OrePrefix.plate.get(Materials.Iron)});
                }
                if (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.electric_hoe, 1))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.electric_hoe, 1), new Object[]{"SS ", " C ", " B ", 'C', OrePrefix.circuit.get(Materials.Basic), 'B', OrePrefix.battery.get(Materials.Basic), 'S', GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefix.plate.get(Materials.StainlessSteel) : OrePrefix.plate.get(Materials.Iron)});
                }
                if (ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.electric_treetap, 1))) {
                        ModHandler.addShapedRecipe(ModHandler.getIC2Item(ItemName.electric_treetap, 1), new Object[]{" B ", "SCS", "S  ", 'C', OrePrefix.circuit.get(Materials.Basic), 'B', OrePrefix.battery.get(Materials.Basic), 'S', GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "electricsteeltools", true) ? OrePrefix.plate.get(Materials.StainlessSteel) : OrePrefix.plate.get(Materials.Iron)});
                }
                GTLog.out.println("GregTechMod: Removing Q-Armor Recipes if configured.");
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QHelmet", false)) {
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.quantum_helmet, 1));
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QPlate", false)) {
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.quantum_chestplate, 1));
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QLegs", false)) {
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.quantum_leggings, 1));
                }
                if (GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.disabledrecipes, "QBoots", false)) {
                        ModHandler.removeRecipeByOutput(ModHandler.getIC2Item(ItemName.quantum_boots, 1));
                }

                if(Loader.isModLoaded("GraviSuite")){
                        ModHandler.removeRecipeByOutput(ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GTValues.W));
                        ModHandler.addShapedRecipe(ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GTValues.W), new Object[]{"CJC","CNC","WPW",'C', OrePrefix.plateAlloy.get(Materials.Carbon),'J',ModHandler.getModItem("GraviSuite", "advJetpack", 1, GTValues.W),'N',ModHandler.getIC2Item(ItemName.nano_chestplate, 1),'W', OrePrefix.wireGt04.get(Materials.Platinum),'P', OrePrefix.circuit.get(Materials.Elite)});
                }
        }
        */
}