package gregtech;

import gregtech.api.GTValues;
import gregtech.api.capability.SimpleCapabilityManager;
import gregtech.api.metatileentity.MetaTileEntityUIFactory;
import gregtech.api.model.ResourcePackHook;
import gregtech.api.net.NetworkHandler;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTLog;
import gregtech.common.CommonProxy;
import gregtech.common.MetaFluids;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.modelfactories.BlockCompressedFactory;
import gregtech.common.blocks.modelfactories.BlockOreFactory;
import gregtech.common.items.MetaItems;
import gregtech.loaders.preload.MaterialInfoLoader;
import gregtech.loaders.preload.OreDictionaryLoader;
import net.minecraft.init.Items;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = GTValues.MODID,
     name = "GregTech",
     version = "5.10.90",
     acceptedMinecraftVersions = "[1.12,1.13)")
public class GregTechMod {

    static {
        FluidRegistry.enableUniversalBucket();
        if(FMLCommonHandler.instance().getSide().isClient()) {
            ResourcePackHook.init();
            BlockOreFactory.init();
            BlockCompressedFactory.init();
        }
    }

    @Mod.Instance(GTValues.MODID)
    public static GregTechMod instance;

    @SidedProxy(modId = GTValues.MODID, clientSide = "gregtech.common.ClientProxy", serverSide = "gregtech.common.CommonProxy")
    public static CommonProxy gregtechproxy;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        GTLog.init(event.getModLog(), event.getModConfigurationDirectory().getParentFile());

        GTLog.logger.info("PreInit-Phase started!");

        NetworkHandler.init();
        MetaTileEntityUIFactory.INSTANCE.init();
        SimpleCapabilityManager.init();

        OreDictUnifier.init();
//        new OreProcessingLoader().run();
        new MaterialInfoLoader().run();

        MetaBlocks.init();
        MetaItems.init();
        MetaFluids.init();

//        new MTELoader().run();

        gregtechproxy.onPreLoad();

        GTLog.logger.info("PreInit-Phase finished!");
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        GTLog.logger.info("Init-Phase started!");

//        new BeeLoader();

        new OreDictionaryLoader().run();
        MetaItems.registerOreDict();
        MetaItems.registerRecipes();

        gregtechproxy.onLoad();

        if (RecipeMap.foundInvalidRecipe) {
            throw new LoaderException("Found at least one invalid recipe. Please read the log above for more details.");
        }

//        new FuelLoader().run();
        Material.init();
        GTLog.logger.info("Init-Phase finished!");
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        GTLog.logger.info("PostInit-Phase started!");

        gregtechproxy.onPostLoad();

//        new DungeonLootLoader().run();
//        new BlockResistanceLoader().run();
//        new MachineRecipeLoader().run();
//        new ScrapboxRecipeLoader().run();
//        new CropLoader().run();
//        new WorldgenLoader().run();

//        GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Blocks.PLANKS, 1), null, false);
//        GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Blocks.COBBLESTONE, 1), null, false);
//        GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Blocks.STONE, 1), null, false);
//        GT_RecipeRegistrator.registerUsagesForMaterials(new ItemStack(Items.LEATHER, 1), null, false);

//        OreDictUnifier.addItemData(GT_ModHandler.getRecipeOutput(new ItemStack[]{null, OreDictUnifier.get(OrePrefix.ingot, Materials.Tin, 1L), null, OreDictUnifier.get(OrePrefix.ingot, Materials.Tin, 1L), null, OreDictUnifier.get(OrePrefix.ingot, Materials.Tin, 1L), null, null, null}), new ItemMaterialInfo(Materials.Tin, 10886400L, new MaterialStack[0]));
//        if (!GregTechAPI.sRecipeFile.get(ConfigCategories.Recipes.storageblockcrafting, "tile.glowstone", false)) {
//            GT_ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Items.GLOWSTONE_DUST, 1), new ItemStack(Items.GLOWSTONE_DUST, 1), null, new ItemStack(Items.GLOWSTONE_DUST, 1), new ItemStack(Items.GLOWSTONE_DUST, 1)});
//        }
//        GT_ModHandler.removeRecipe(new ItemStack[]{new ItemStack(Blocks.WOODEN_SLAB, 1, 0), new ItemStack(Blocks.WOODEN_SLAB, 1, 1), new ItemStack(Blocks.WOODEN_SLAB, 1, 2)});
//        GT_ModHandler.addCraftingRecipe(new ItemStack(Blocks.WOODEN_SLAB, 6, 0), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"WWW", 'W', new ItemStack(Blocks.PLANKS, 1, 0)});
//
//        GTLog.out.println("GregTechMod: List of Lists of Tool Recipes: "+GT_ModHandler.sSingleNonBlockDamagableRecipeList_list.toString());
//        GTLog.out.println("GregTechMod: Vanilla Recipe List -> Outputs null or stackSize <=0: " + GT_ModHandler.sVanillaRecipeList_warntOutput.toString());
//        GTLog.out.println("GregTechMod: Single Non Block Damagable Recipe List -> Outputs null or stackSize <=0: " + GT_ModHandler.sSingleNonBlockDamagableRecipeList_warntOutput.toString());
//
//        new CraftingRecipeLoader().run();
//        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getIC2Item(BlockName.resource, ResourceBlock.machine, 1), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "RwR", "RRR", Character.valueOf('R'), OrePrefix.plate.get(Materials.Iron)});
//        ItemStack ISdata0 = new ItemStack(Items.POTIONITEM, 1, 0);
//        ItemStack ILdata0 = ItemList.Bottle_Empty.get(1L, new Object[0]);
//        for (FluidContainerRegistry.FluidContainerData tData : FluidContainerRegistry.getRegisteredFluidContainerData()) {
//            if ((tData.filledContainer.getItem() == Items.POTIONITEM) && (tData.filledContainer.getItemDamage() == 0)) {
//                GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[]{ILdata0}, new ItemStack[]{ISdata0}, null, new FluidStack[]{Materials.Water.getFluid(250L)}, null, 4, 1, 0);
//                GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[]{ISdata0}, new ItemStack[]{ILdata0}, null, null, null, 4, 1, 0);
//            } else {
//                GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[]{tData.emptyContainer}, new ItemStack[]{tData.filledContainer}, null, new FluidStack[]{tData.fluid}, null, tData.fluid.amount / 62, 1, 0);
//                GT_Recipe.GT_Recipe_Map.sFluidCannerRecipes.addRecipe(true, new ItemStack[]{tData.filledContainer}, new ItemStack[]{GTUtility.getContainerItem(tData.filledContainer, true)}, null, null, new FluidStack[]{tData.fluid}, tData.fluid.amount / 62, 1, 0);
//            }
//        }
//        try {
//            for (ICentrifugeRecipe tRecipe : RecipeManagers.centrifugeManager.recipes()) {
//                Map<ItemStack, Float> outputs = tRecipe.getAllProducts();
//                ItemStack[] tOutputs = new ItemStack[outputs.size()];
//                int[] tChances = new int[outputs.size()];
//                int i = 0;
//                for (Map.Entry<ItemStack, Float> entry : outputs.entrySet()) {
//                    tChances[i] = (int) (entry.getValue() * 10000);
//                    tOutputs[i] = entry.getKey().copy();
//                    i++;
//                }
//                GT_Recipe.GT_Recipe_Map.sCentrifugeRecipes.addRecipe(true, new ItemStack[]{tRecipe.getInput()}, tOutputs, null, tChances, null, null, 128, 5, 0);
//            }
//        } catch (Throwable e) {
//            if (GTValues.D1) {
//                e.printStackTrace(GTLog.err);
//            }
//        }
//        try {
//            for (ISqueezerRecipe tRecipe : RecipeManagers.squeezerManager.recipes()) {
//                if ((tRecipe.getResources().length == 1) && (tRecipe.getFluidOutput() != null)) {
//                    GT_Recipe.GT_Recipe_Map.sFluidExtractionRecipes.addRecipe(true, new ItemStack[]{tRecipe.getResources()[0]}, new ItemStack[]{tRecipe.getRemnants()}, null, new int[]{(int) (tRecipe.getRemnantsChance() * 10000)}, null, new FluidStack[]{tRecipe.getFluidOutput()}, 400, 2, 0);
//                }
//            }
//        } catch (Throwable e) {
//            if (GTValues.D1) {
//                e.printStackTrace(GTLog.err);
//            }
//        }
//
//        if (gregtechproxy.mNerfedVanillaTools) {
            GTLog.logger.info("GregTechMod: Nerfing Vanilla Tool Durability");
            Items.WOODEN_SWORD.setMaxDamage(12);
            Items.WOODEN_PICKAXE.setMaxDamage(12);
            Items.WOODEN_SHOVEL.setMaxDamage(12);
            Items.WOODEN_AXE.setMaxDamage(12);
            Items.WOODEN_HOE.setMaxDamage(12);

            Items.STONE_SWORD.setMaxDamage(48);
            Items.STONE_PICKAXE.setMaxDamage(48);
            Items.STONE_SHOVEL.setMaxDamage(48);
            Items.STONE_AXE.setMaxDamage(48);
            Items.STONE_HOE.setMaxDamage(48);

            Items.IRON_SWORD.setMaxDamage(256);
            Items.IRON_PICKAXE.setMaxDamage(256);
            Items.IRON_SHOVEL.setMaxDamage(256);
            Items.IRON_AXE.setMaxDamage(256);
            Items.IRON_HOE.setMaxDamage(256);

            Items.GOLDEN_SWORD.setMaxDamage(24);
            Items.GOLDEN_PICKAXE.setMaxDamage(24);
            Items.GOLDEN_SHOVEL.setMaxDamage(24);
            Items.GOLDEN_AXE.setMaxDamage(24);
            Items.GOLDEN_HOE.setMaxDamage(24);

            Items.DIAMOND_SWORD.setMaxDamage(768);
            Items.DIAMOND_PICKAXE.setMaxDamage(768);
            Items.DIAMOND_SHOVEL.setMaxDamage(768);
            Items.DIAMOND_AXE.setMaxDamage(768);
            Items.DIAMOND_HOE.setMaxDamage(768);
//        }

//        GTLog.out.println("GregTechMod: Adding Fake Recipes for NEI");
//        if (ItemList.FR_Bee_Drone.get(1) != null) {
//            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Bee_Drone.getWildcard(1)}, new ItemStack[]{ItemList.FR_Bee_Drone.getWithName(1, "Scanned Drone")}, null, new FluidStack[]{Materials.Honey.getFluid(100L)}, null, 500, 2, 0);
//        }
//        if (ItemList.FR_Bee_Princess.get(1) != null) {
//            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Bee_Princess.getWildcard(1)}, new ItemStack[]{ItemList.FR_Bee_Princess.getWithName(1, "Scanned Princess")}, null, new FluidStack[]{Materials.Honey.getFluid(100L)}, null, 500, 2, 0);
//        }
//        if (ItemList.FR_Bee_Queen.get(1) != null) {
//            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Bee_Queen.getWildcard(1)}, new ItemStack[]{ItemList.FR_Bee_Queen.getWithName(1, "Scanned Queen")}, null, new FluidStack[]{Materials.Honey.getFluid(100L)}, null, 500, 2, 0);
//        }
//        if (ItemList.FR_Tree_Sapling.get(1) != null) {
//            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Tree_Sapling.getWildcard(1)}, new ItemStack[]{ItemList.FR_Tree_Sapling.getWithName(1, "Scanned Sapling")}, null, new FluidStack[]{Materials.Honey.getFluid(100L)}, null, 500, 2, 0);
//        }
//        if (ItemList.FR_Butterfly.get(1) != null) {
//            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Butterfly.getWildcard(1)}, new ItemStack[]{ItemList.FR_Butterfly.getWithName(1, "Scanned Butterfly")}, null, new FluidStack[]{Materials.Honey.getFluid(100L)}, null, 500, 2, 0);
//        }
//        if (ItemList.FR_Larvae.get(1) != null) {
//            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Larvae.getWildcard(1)}, new ItemStack[]{ItemList.FR_Larvae.getWithName(1, "Scanned Larvae")}, null, new FluidStack[]{Materials.Honey.getFluid(100L)}, null, 500, 2, 0);
//        }
//        if (ItemList.FR_Serum.get(1) != null) {
//            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Serum.getWildcard(1)}, new ItemStack[]{ItemList.FR_Serum.getWithName(1, "Scanned Serum")}, null, new FluidStack[]{Materials.Honey.getFluid(100L)}, null, 500, 2, 0);
//        }
//        if (ItemList.FR_Caterpillar.get(1) != null) {
//            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_Caterpillar.getWildcard(1)}, new ItemStack[]{ItemList.FR_Caterpillar.getWithName(1, "Scanned Caterpillar")}, null, new FluidStack[]{Materials.Honey.getFluid(100L)}, null, 500, 2, 0);
//        }
//        if (ItemList.FR_PollenFertile.get(1) != null) {
//            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.FR_PollenFertile.getWildcard(1)}, new ItemStack[]{ItemList.FR_PollenFertile.getWithName(1, "Scanned Pollen")}, null, new FluidStack[]{Materials.Honey.getFluid(100L)}, null, 500, 2, 0);
//        }
//        if (ItemList.IC2_Crop_Seeds.get(1) != null) {
//            GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.IC2_Crop_Seeds.getWildcard(1)}, new ItemStack[]{ItemList.IC2_Crop_Seeds.getWithName(1, "Scanned Seeds")}, null, null, null, 160, 8, 0);
//        }
//        GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{new ItemStack(Items.WRITTEN_BOOK, 1, 32767)}, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1, "Scanned Book Data")}, ItemList.Tool_DataStick.getWithName(1, "Stick to save it to"), null, null, 128, 32, 0);
//        GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{new ItemStack(Items.FILLED_MAP, 1, 32767)}, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1, "Scanned Map Data")}, ItemList.Tool_DataStick.getWithName(1, "Stick to save it to"), null, null, 128, 32, 0);
//        GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.Tool_DataOrb.getWithName(1, "Orb to overwrite")}, new ItemStack[]{ItemList.Tool_DataOrb.getWithName(1, "Copy of the Orb")}, ItemList.Tool_DataOrb.getWithName(0L, "Orb to copy"), null, null, 512, 32, 0);
//        GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1, "Stick to overwrite")}, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1, "Copy of the Stick")}, ItemList.Tool_DataStick.getWithName(0L, "Stick to copy"), null, null, 128, 32, 0);
//        GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1, "Raw Prospection Data")}, new ItemStack[]{ItemList.Tool_DataStick.getWithName(1, "Analyzed Prospection Data")}, null, null, null, 1000, 32, 0);
//        for (Materials tMaterial : Materials.values()) {
//            if ((tMaterial.mElement != null) && (!tMaterial.mElement.mIsIsotope) && (tMaterial != Materials.Magic) && (tMaterial.getMass() > 0L)) {
//                ItemStack tOutput = ItemList.Tool_DataOrb.get(1);
//                Behaviour_DataOrb.setDataTitle(tOutput, "Elemental-Scan");
//                Behaviour_DataOrb.setDataName(tOutput, tMaterial.mElement.name());
//                ItemStack tInput = OreDictUnifier.get(OrePrefix.dust, tMaterial, 1);
//                ItemStack[] ISmat0 = new ItemStack[]{tInput};
//                ItemStack[] ISmat1 = new ItemStack[]{tOutput};
//                if (tInput != null) {
//                    GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, ISmat0, ISmat1, ItemList.Tool_DataOrb.get(1), null, null, (int) (tMaterial.getMass() * 8192L), 32, 0);
//                    GT_Recipe.GT_Recipe_Map.sRepicatorFakeRecipes.addFakeRecipe(false, null, ISmat0, ISmat1, new FluidStack[]{Materials.UUMatter.getFluid(tMaterial.getMass())}, null, (int) (tMaterial.getMass() * 512L), 32, 0);
//
//                }
//                tInput = OreDictUnifier.get(OrePrefix.cell, tMaterial, 1L);
//                ISmat0 = new ItemStack[]{tInput};
//                if (tInput != null) {
//                    GT_Recipe.GT_Recipe_Map.sScannerFakeRecipes.addFakeRecipe(false, ISmat0, ISmat1, ItemList.Tool_DataOrb.get(1), null, null, (int) (tMaterial.getMass() * 8192L), 32, 0);
//                    GT_Recipe.GT_Recipe_Map.sRepicatorFakeRecipes.addFakeRecipe(false, null, ISmat0, ISmat1, new FluidStack[]{Materials.UUMatter.getFluid(tMaterial.getMass())}, null, (int) (tMaterial.getMass() * 512L), 32, 0);
//                }
//            }
//        }
//        GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.Display_ITS_FREE.getWithName(0L, "Place Lava on Side")}, new ItemStack[]{new ItemStack(Blocks.COBBLESTONE, 1)}, null, null, null, 16, 32, 0);
//        GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes.addFakeRecipe(false, new ItemStack[]{ItemList.Display_ITS_FREE.getWithName(0L, "Place Lava on Top")}, new ItemStack[]{new ItemStack(Blocks.STONE, 1)}, null, null, null, 16, 32, 0);
//        GT_Recipe.GT_Recipe_Map.sRockBreakerFakeRecipes.addFakeRecipe(false, new ItemStack[]{OreDictUnifier.get(OrePrefix.dust, Materials.Redstone, 1)}, new ItemStack[]{new ItemStack(Blocks.OBSIDIAN, 1)}, null, null, null, 128, 32, 0);
//        for (IMachineRecipeManager.RecipeIoContainer recipeIoContainer : Recipes.macerator.getRecipes()) {
//            if (recipeIoContainer.output.items.size() > 0) {
//                for (ItemStack tStack : recipeIoContainer.input.getInputs()) {
//                    if (GTUtility.isStackValid(tStack)) {
//                        GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.addFakeRecipe(true,
//                                new ItemStack[]{GTUtility.copyAmount(recipeIoContainer.input.getAmount(), tStack)},
//                                new ItemStack[]{recipeIoContainer.output.items.get(0)}, null, null, null, null, 400, 2, 0);
//                    }
//                }
//            }
//        }
//
//        if(GregTechAPI.mOutputRF|| GregTechAPI.mInputRF){
//            GTUtility.checkAvailabilities();
//            if(!GTUtility.RF_CHECK){
//                GregTechAPI.mOutputRF = false;
//                GregTechAPI.mInputRF = false;
//            }
//        }
        GTLog.logger.info("PostInit-Phase finished!");
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent aEvent) {
//        try {
//            for (Runnable tRunnable : GregTechAPI.sBeforeGTServerstart) {
//                tRunnable.run();
//            }
//        } catch (Throwable e) {e.printStackTrace(GTLog.err);}
//        GTLog.out.println("GregTechMod: Unificating outputs of all known Recipe Types.");
//        ArrayList<ItemStack> tStacks = new ArrayList(10000);
//        GTLog.out.println("GregTechMod: IC2 Machines");
//        for (RecipeOutput tRecipe : getOutputs(Recipes.centrifuge)) {
//            ItemStack tStack;
//            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
//                tStack = (ItemStack) i$.next();
//            }
//        }
//        for (RecipeOutput tRecipe : getOutputs(Recipes.centrifuge)) {
//            ItemStack tStack;
//            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
//                tStack = (ItemStack) i$.next();
//            }
//        }
//        for (RecipeOutput tRecipe : getOutputs(Recipes.compressor)) {
//            ItemStack tStack;
//            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
//                tStack = (ItemStack) i$.next();
//            }
//        }
//        for (RecipeOutput tRecipe : getOutputs(Recipes.extractor)) {
//            ItemStack tStack;
//            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
//                tStack = (ItemStack) i$.next();
//            }
//        }
//        for (RecipeOutput tRecipe : getOutputs(Recipes.macerator)) {
//            ItemStack tStack;
//            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
//                tStack = (ItemStack) i$.next();
//            }
//        }
//        for (RecipeOutput tRecipe : getOutputs(Recipes.metalformerCutting)) {
//            ItemStack tStack;
//            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
//                tStack = (ItemStack) i$.next();
//            }
//        }
//        for (RecipeOutput tRecipe : getOutputs(Recipes.metalformerExtruding)) {
//            ItemStack tStack;
//            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
//                tStack = (ItemStack) i$.next();
//            }
//        }
//        for (RecipeOutput tRecipe : getOutputs(Recipes.metalformerRolling)) {
//            ItemStack tStack;
//            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
//                tStack = (ItemStack) i$.next();
//            }
//        }
//        for (RecipeOutput tRecipe : getOutputs(Recipes.matterAmplifier)) {
//            ItemStack tStack;
//            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
//                tStack = (ItemStack) i$.next();
//            }
//        }
//        for (RecipeOutput tRecipe : getOutputs(Recipes.oreWashing)) {
//            ItemStack tStack;
//            for (Iterator i$ = tRecipe.items.iterator(); i$.hasNext(); tStacks.add(tStack)) {
//                tStack = (ItemStack) i$.next();
//            }
//        }
//        GTLog.out.println("GregTechMod: Dungeon Loot");
        /*for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("dungeonChest").getItems(new XSTR())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("bonusChest").getItems(new XSTR())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("villageBlacksmith").getItems(new XSTR())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("strongholdCrossing").getItems(new XSTR())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("strongholdLibrary").getItems(new XSTR())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("strongholdCorridor").getItems(new XSTR())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("pyramidJungleDispenser").getItems(new XSTR())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("pyramidJungleChest").getItems(new XSTR())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("pyramidDesertyChest").getItems(new XSTR())) {
            tStacks.add(tContent.theItemId);
        }
        for (WeightedRandomChestContent tContent : ChestGenHooks.getInfo("mineshaftCorridor").getItems(new XSTR())) {
            tStacks.add(tContent.theItemId);
        }*/
//        GTLog.out.println("GregTechMod: Smelting");
//        Object tStack;
//        for (Iterator i$ = FurnaceRecipes.instance().getSmeltingList().values().iterator(); i$.hasNext(); tStacks.add((ItemStack) tStack)) {
//            tStack = i$.next();
//        }
//        if (gregtechproxy.mCraftingUnification) {
//            GTLog.out.println("GregTechMod: Crafting Recipes");
//            for (Object tRecipe : CraftingManager.getInstance().getRecipeList()) {
//                if ((tRecipe instanceof IRecipe)) {
//                    tStacks.add(((IRecipe) tRecipe).getRecipeOutput());
//                }
//            }
//        }
//        for (ItemStack tOutput : tStacks) {
//            if (gregtechproxy.mRegisteredOres.contains(tOutput)) {
//                FMLLog.severe("GT-ERR-01: @ " + tOutput.getUnlocalizedName() + "   " + tOutput.getDisplayName());
//                FMLLog.severe("A Recipe used an OreDict Item as Output directly, without copying it before!!! This is a typical CallByReference/CallByValue Error");
//                FMLLog.severe("Said Item will be renamed to make the invalid Recipe visible, so that you can report it properly.");
//                FMLLog.severe("Please check all Recipes outputting this Item, and report the Recipes to their Owner.");
//                FMLLog.severe("The Owner of the ==>RECIPE<==, NOT the Owner of the Item, which has been mentioned above!!!");
//                FMLLog.severe("And ONLY Recipes which are ==>OUTPUTTING<== the Item, sorry but I don't want failed Bug Reports.");
//                FMLLog.severe("GregTech just reports this Error to you, so you can report it to the Mod causing the Problem.");
//                FMLLog.severe("Even though I make that Bug visible, I can not and will not fix that for you, that's for the causing Mod to fix.");
//                FMLLog.severe("And speaking of failed Reports:");
//                FMLLog.severe("Both IC2 and GregTech CANNOT be the CAUSE of this Problem, so don't report it to either of them.");
//                FMLLog.severe("I REPEAT, BOTH, IC2 and GregTech CANNOT be the source of THIS BUG. NO MATTER WHAT.");
//                FMLLog.severe("Asking in the IC2 Forums, which Mod is causing that, won't help anyone, since it is not possible to determine, which Mod it is.");
//                FMLLog.severe("If it would be possible, then I would have had added the Mod which is causing it to the Message already. But it is not possible.");
//                FMLLog.severe("Sorry, but this Error is serious enough to justify this Wall-O-Text and the partially allcapsed Language.");
//                FMLLog.severe("Also it is a Ban Reason on the IC2-Forums to post this seriously.");
//                tOutput.setStackDisplayName("ERROR! PLEASE CHECK YOUR LOG FOR 'GT-ERR-01'!");
//            } else {
//                OreDictUnifier.setStack(tOutput);
//
//            }
//        }
//        GregTechAPI.mServerStarted = true;
//        GTLog.out.println("GregTechMod: ServerStarting-Phase finished!");
//        try {
//            for (Runnable tRunnable : GregTechAPI.sAfterGTServerstart) {
//                tRunnable.run();
//            }
//        } catch (Throwable e) {e.printStackTrace(GTLog.err);}
    }
//
//    public ArrayList<RecipeOutput> getOutputs(IMachineRecipeManager recipeManager) {
//        ArrayList<RecipeOutput> outputs = new ArrayList<>();
//        for(IMachineRecipeManager.RecipeIoContainer container : recipeManager.getRecipes())
//            outputs.add(container.output);
//        return outputs;
//    }
}