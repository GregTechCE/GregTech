package gregtech.common;

import gregtech.api.GregTechAPI;
import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.IMetaTileEntity;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GTLog;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public abstract class GT_Proxy implements IFuelHandler, IGuiHandler {

    private static List<Block> ROTATABLE_VANILLA_BLOCKS;

    static {
        ROTATABLE_VANILLA_BLOCKS = Arrays.asList(Blocks.PISTON, Blocks.STICKY_PISTON, Blocks.FURNACE, Blocks.LIT_FURNACE,
                Blocks.DROPPER, Blocks.DISPENSER, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.HOPPER,
                Blocks.PUMPKIN, Blocks.LIT_PUMPKIN);
    }

    public final HashSet<ItemStack> mRegisteredOres = new HashSet<ItemStack>(10000);

//    public boolean mHardcoreCables = false;
//    public boolean mDisableVanillaOres = true;
//    public boolean mDisableModdedOres = true;
//    public boolean mNerfDustCrafting = true;
//    public boolean mSortToTheEnd = true;
//    public boolean mCraftingUnification = true;
//    public boolean mInventoryUnification = true;
//    public boolean mIncreaseDungeonLoot = true;
//    public boolean mAxeWhenAdventure = true;
//    public boolean mSurvivalIntoAdventure = false;
//    public boolean mNerfedWoodPlank = true;
//    public boolean mNerfedVanillaTools = true;
//    public boolean mHardRock = false;
//    public boolean mHungerEffect = true;
//    public boolean mOnline = true;
//    public boolean mIgnoreTcon = true;
//    public boolean mDisableIC2Cables = false;
//    public boolean mAchievements = true;
//    public boolean mAE2Integration = true;
//    public boolean mArcSmeltIntoAnnealed = true;
//    public boolean mMagneticraftRecipes = true;
//    public boolean mImmersiveEngineeringRecipes = true;
//    private boolean isFirstServerWorldTick = true;
//    private boolean mOreDictActivated = false;
//    public boolean mChangeHarvestLevels=false;
//    public boolean mNerfedCombs = true;
//    public boolean mNerfedCrops = true;
//    public boolean mGTBees = true;
//    public boolean mHideUnusedOres = true;
//    public boolean mHideRecyclingRecipes = true;
//    public boolean mPollution = true;
//    public boolean mExplosionItemDrop = false;
//    public int mSkeletonsShootGTArrows = 16;
//    public int mMaxEqualEntitiesAtOneSpot = 3;
//    public int mFlintChance = 30;
//    public int mItemDespawnTime = 6000;
//    public int mUpgradeCount = 4;
//    public int[] mHarvestLevel= new int[1000];
//    public int mGraniteHavestLevel=3;
//    public int mMaxHarvestLevel=7;
//    public int mWireHeatingTicks = 4;
//    public int mPollutionSmogLimit = 500000;
//    public int mPollutionPoisonLimit = 750000;
//    public int mPollutionVegetationLimit = 1000000;
//    public int mPollutionSourRainLimit = 2000000;
//    public int mTicksUntilNextCraftSound = 0;
//    public double mMagneticraftBonusOutputPercent = 100.0d;
//    private final String aTextThermalExpansion = "ThermalExpansion";
//    private final String aTextRailcraft = "Railcraft";
//    private final String aTextTwilightForest = "TwilightForest";
//    private final String aTextForestry = "Forestry";
//    private final String aTextArsmagica2 = "arsmagica2";
//    public boolean mTEMachineRecipes = false;
//    public boolean mEnableAllMaterials = false;
//    public boolean mEnableAllComponents = false;

    public void onPreLoad() {
        GTLog.logger.info("GT_Mod: Preload-Phase started!");

//        GregTechAPI.sPreloadStarted = true;

        GameRegistry.registerFuelHandler(this);
        MinecraftForge.EVENT_BUS.register(this);
//        MinecraftForge.ORE_GEN_BUS.register(this);

//        GTLog.out.println("GT_Mod: Getting required Items of other Mods.");

//        GregTechAPI.sFrostHazmatList.add(ItemName.hazmat_helmet.getItemStack());
//        GregTechAPI.sFrostHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
//        GregTechAPI.sFrostHazmatList.add(ItemName.hazmat_leggings.getItemStack());
//        GregTechAPI.sFrostHazmatList.add(ItemName.rubber_boots.getItemStack());
//
//        GregTechAPI.sHeatHazmatList.add(ItemName.hazmat_helmet.getItemStack());
//        GregTechAPI.sHeatHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
//        GregTechAPI.sHeatHazmatList.add(ItemName.hazmat_leggings.getItemStack());
//        GregTechAPI.sHeatHazmatList.add(ItemName.rubber_boots.getItemStack());
//
//        GregTechAPI.sBioHazmatList.add(ItemName.hazmat_helmet.getItemStack());
//        GregTechAPI.sBioHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
//        GregTechAPI.sBioHazmatList.add(ItemName.hazmat_leggings.getItemStack());
//        GregTechAPI.sBioHazmatList.add(ItemName.rubber_boots.getItemStack());
//
//        GregTechAPI.sGasHazmatList.add(ItemName.hazmat_helmet.getItemStack());
//        GregTechAPI.sGasHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
//        GregTechAPI.sGasHazmatList.add(ItemName.hazmat_leggings.getItemStack());
//        GregTechAPI.sGasHazmatList.add(ItemName.rubber_boots.getItemStack());
//
//        GregTechAPI.sRadioHazmatList.add(ItemName.hazmat_helmet.getItemStack());
//        GregTechAPI.sRadioHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
//        GregTechAPI.sRadioHazmatList.add(ItemName.hazmat_leggings.getItemStack());
//        GregTechAPI.sRadioHazmatList.add(ItemName.rubber_boots.getItemStack());
//
//        GregTechAPI.sElectroHazmatList.add(ItemName.hazmat_helmet.getItemStack());
//        GregTechAPI.sElectroHazmatList.add(ItemName.hazmat_chestplate.getItemStack());
//        GregTechAPI.sElectroHazmatList.add(ItemName.hazmat_leggings.getItemStack());
//        GregTechAPI.sElectroHazmatList.add(ItemName.rubber_boots.getItemStack());
//        GregTechAPI.sElectroHazmatList.add(new ItemStack(Items.CHAINMAIL_HELMET, 1, 32767));
//        GregTechAPI.sElectroHazmatList.add(new ItemStack(Items.CHAINMAIL_CHESTPLATE, 1, 32767));
//        GregTechAPI.sElectroHazmatList.add(new ItemStack(Items.CHAINMAIL_LEGGINGS, 1, 32767));
//        GregTechAPI.sElectroHazmatList.add(new ItemStack(Items.CHAINMAIL_BOOTS, 1, 32767));
    }

    public void onLoad() {
        GTLog.logger.info("GT_Mod: Beginning Load-Phase.");


    }

    public void onPostLoad() {
        GTLog.logger.info("GT_Mod: Beginning PostLoad-Phase.");

//        GregTechAPI.sPostloadStarted = true;
        OreDictionaryUnifier.registerOre(new ItemStack(Items.IRON_DOOR, 1), new ItemMaterialInfo(new MaterialStack(Materials.Iron, 21772800L)));
        OreDictionaryUnifier.registerOre(new ItemStack(Items.ACACIA_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));
        OreDictionaryUnifier.registerOre(new ItemStack(Items.BIRCH_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));
        OreDictionaryUnifier.registerOre(new ItemStack(Items.JUNGLE_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));
        OreDictionaryUnifier.registerOre(new ItemStack(Items.OAK_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));
        OreDictionaryUnifier.registerOre(new ItemStack(Items.SPRUCE_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));
        OreDictionaryUnifier.registerOre(new ItemStack(Items.DARK_OAK_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));

        GTLog.logger.info("GT_Mod: Adding Configs specific for MetaTileEntities");


//        try {
//            for (int i = 1; i < GregTechAPI.METATILEENTITIES.length; i++) {
//                for (; i < GregTechAPI.METATILEENTITIES.length; i++) {
//                    if (GregTechAPI.METATILEENTITIES[i] != null) {
//                        GregTechAPI.METATILEENTITIES[i].onConfigLoad(GregTechAPI.sMachineFile);
//                    }
//                }
//            }
//        } catch (Throwable e) {
//            e.printStackTrace(GTLog.err);
//        }

/*
        GTLog.out.println("GT_Mod: Adding Tool Usage Crafting Recipes for OreDict Items.");
        for (Materials aMaterial : Materials.values()) {
            if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial)) {
                if (!aMaterial.contains(SubTag.NO_SMASHING)) {
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammerplating, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 1), tBits, new Object[]{"h", "X", "X",
                                Character.valueOf('X'), OrePrefix.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 1), tBits,
                                new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gem.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 1), tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefix.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(
                                OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 1),
                                tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefix.gem.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 1), tBits,
                                new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.ingotDouble.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 2L), tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefix.ingotDouble.get(aMaterial)});
                    }
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotDouble, aMaterial, 1), tBits, new Object[]{"I", "I", "h",
                                Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotTriple, aMaterial, 1), tBits, new Object[]{"I", "B", "h",
                                Character.valueOf('I'), OrePrefix.ingotDouble.get(aMaterial), Character.valueOf('B'), OrePrefix.ingot.get(aMaterial)});
                        GT_ModHandler
                                .addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotQuadruple, aMaterial, 1), tBits,
                                        new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefix.ingotTriple.get(aMaterial), Character.valueOf('B'),
                                                OrePrefix.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingotQuintuple, aMaterial, 1), tBits,
                                new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefix.ingotQuadruple.get(aMaterial), Character.valueOf('B'),
                                        OrePrefix.ingot.get(aMaterial)});
                    }
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadAxe, aMaterial, 1), tBits, new Object[]{"PIh", "P  ",
                            "f  ", Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadHammer, aMaterial, 1), tBits, new Object[]{"II ", "IIh",
                            "II ", Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadHoe, aMaterial, 1), tBits, new Object[]{"PIh", "f  ",
                            Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadPickaxe, aMaterial, 1), tBits, new Object[]{"PII", "f h",
                            Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadPlow, aMaterial, 1), tBits, new Object[]{"PP", "PP", "hf",
                            Character.valueOf('P'), OrePrefix.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadSaw, aMaterial, 1), tBits, new Object[]{"PP ", "fh ",
                            Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadSense, aMaterial, 1), tBits, new Object[]{"PPI", "hf ",
                            Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(
                            OreDictionaryUnifier.get(OrePrefix.toolHeadShovel, aMaterial, 1),
                            tBits,
                            new Object[]{"fPh", 'P', OrePrefix.plate.get(aMaterial), 'I',
                                    OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadSword, aMaterial, 1), tBits, new Object[]{" P ", "fPh",
                            'P', OrePrefix.plate.get(aMaterial), 'I', OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ring, aMaterial, 1), tBits,
                            new Object[]{"h ", " X", 'X', OrePrefix.stick.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stickLong, aMaterial, 1), tBits,
                            new Object[]{"ShS", 'S', OrePrefix.stick.get(aMaterial)});
                }*/
                /*if (!aMaterial.contains(SubTag.NO_WORKING)) {
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stick, aMaterial, 2L), tBits,
                            new Object[]{"s", "X", Character.valueOf('X'), OrePrefix.stickLong.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stick, aMaterial, 1), tBits,
                            new Object[]{"f ", " X", Character.valueOf('X'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.bolt, aMaterial, 2L), tBits,
                            new Object[]{"s ", " X", Character.valueOf('X'), OrePrefix.stick.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.screw, aMaterial, 1), tBits,
                            new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefix.bolt.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.round, aMaterial, 1), tBits,
                            new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefix.nugget.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.rotor, aMaterial, 1), tBits, new Object[]{"PhP", "SRf", "PdP",
                            Character.valueOf('P'), aMaterial == Materials.Wood ? OrePrefix.plank.get(aMaterial) : OrePrefix.plate.get(aMaterial),
                            Character.valueOf('R'), OrePrefix.ring.get(aMaterial), Character.valueOf('S'), OrePrefix.screw.get(aMaterial)});
                    GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 4L), OreDictionaryUnifier.get(OrePrefix.ring, aMaterial, 1), Materials.Tin.getMolten(32), OreDictionaryUnifier.get(OrePrefix.rotor, aMaterial, 1), 240, 24);
                    GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 4L), OreDictionaryUnifier.get(OrePrefix.ring, aMaterial, 1), Materials.Lead.getMolten(48), OreDictionaryUnifier.get(OrePrefix.rotor, aMaterial, 1), 240, 24);
                    GTValues.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 4L), OreDictionaryUnifier.get(OrePrefix.ring, aMaterial, 1), Materials.SolderingAlloy.getMolten(16), OreDictionaryUnifier.get(OrePrefix.rotor, aMaterial, 1), 240, 24);
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stickLong, aMaterial, 1), tBits,
                            new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefix.gemFlawless.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.stickLong, aMaterial, 2L), tBits,
                            new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefix.gemExquisite.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireGt01, aMaterial, 1), tBits,
                            new Object[]{"Xx", Character.valueOf('X'), OrePrefix.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.wireFine, aMaterial, 1), tBits,
                            new Object[]{"Xx", Character.valueOf('X'), OrePrefix.foil.get(aMaterial)});

                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.turbineBlade, aMaterial, 1), tBits, new Object[]{"fPd", "SPS", " P ",
                            Character.valueOf('P'), aMaterial == Materials.Wood ? OrePrefix.plank.get(aMaterial) : OrePrefix.plateDouble.get(aMaterial),
                            Character.valueOf('R'), OrePrefix.ring.get(aMaterial), Character.valueOf('S'), OrePrefix.screw.get(aMaterial)});


                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.arrowGtWood, aMaterial, 1), tBits, new Object[]{"  A", " S ",
                            "F  ", Character.valueOf('S'), OrePrefix.stick.get(Materials.Wood), Character.valueOf('F'), OreDictNames.craftingFeather,
                            Character.valueOf('A'), OrePrefix.toolHeadArrow.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.arrowGtPlastic, aMaterial, 1), tBits, new Object[]{"  A", " S ",
                            "F  ", Character.valueOf('S'), OrePrefix.stick.get(Materials.Plastic), Character.valueOf('F'), OreDictNames.craftingFeather,
                            Character.valueOf('A'), OrePrefix.toolHeadArrow.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadArrow, aMaterial, 1), tBits,
                            new Object[]{"Xf", Character.valueOf('X'), OrePrefix.gemChipped.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadArrow, aMaterial, 3L), tBits,
                            new Object[]{(aMaterial.contains(SubTag.WOOD) ? 115 : 'x') + "Pf", Character.valueOf('P'), OrePrefix.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadAxe, aMaterial, 1), tBits, new Object[]{"GG ", "G  ",
                            "f  ", Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadHoe, aMaterial, 1), tBits, new Object[]{"GG ", "f  ",
                            "   ", Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadPickaxe, aMaterial, 1), tBits, new Object[]{"GGG", "f  ",
                            Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadPlow, aMaterial, 1), tBits, new Object[]{"GG", "GG", " f",
                            Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadSaw, aMaterial, 1), tBits,
                            new Object[]{"GGf", Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadSense, aMaterial, 1), tBits, new Object[]{"GGG", " f ",
                            "   ", Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadShovel, aMaterial, 1), tBits,
                            new Object[]{"fG", Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadSword, aMaterial, 1), tBits, new Object[]{" G", "fG",
                            Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadUniversalSpade, aMaterial, 1), tBits, new Object[]{"fX",
                            Character.valueOf('X'), OrePrefix.toolHeadShovel.get(aMaterial)});

                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadBuzzSaw, aMaterial, 1), tBits, new Object[]{"wXh", "X X",
                            "fXx", Character.valueOf('X'), OrePrefix.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadWrench, aMaterial, 1), tBits, new Object[]{"hXW", "XRX",
                            "WXd", Character.valueOf('X'), OrePrefix.plate.get(aMaterial), Character.valueOf('S'), OrePrefix.plate.get(Materials.Steel),
                            Character.valueOf('R'), OrePrefix.ring.get(Materials.Steel), Character.valueOf('W'), OrePrefix.screw.get(Materials.Steel)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadChainsaw, aMaterial, 1), tBits, new Object[]{"SRS", "XhX",
                            "SRS", Character.valueOf('X'), OrePrefix.plate.get(aMaterial), Character.valueOf('S'), OrePrefix.plate.get(Materials.Steel),
                            Character.valueOf('R'), OrePrefix.ring.get(Materials.Steel)});
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.toolHeadDrill, aMaterial, 1), tBits, new Object[]{"XSX", "XSX",
                            "ShS", Character.valueOf('X'), OrePrefix.plate.get(aMaterial), Character.valueOf('S'), OrePrefix.plate.get(Materials.Steel)});
                    switch (aMaterial.mName) {
                        case "Wood":
                            GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, aMaterial, 1), tBits, new Object[]{"P ", " s",
                                    Character.valueOf('P'), OrePrefix.plank.get(aMaterial)});
                            break;
                        case "Stone":
                            GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, aMaterial, 1), tBits, new Object[]{"P ", " f",
                                    Character.valueOf('P'), OrePrefix.stoneSmooth});
                            break;
                        default:
                            GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGtSmall, aMaterial, 1), tBits,
                                    new Object[]{"P ", aMaterial.contains(SubTag.WOOD) ? " s" : " h", Character.valueOf('P'), OrePrefix.plate.get(aMaterial)});
                    }
                    switch (aMaterial.mName) {
                        case "Wood":
                            GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, aMaterial, 1), tBits, new Object[]{"SPS", "PsP", "SPS",
                                    Character.valueOf('P'), OrePrefix.plank.get(aMaterial), Character.valueOf('S'), OrePrefix.stick.get(aMaterial)});
                            break;
                        case "Stone":
                            GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, aMaterial, 1), tBits, new Object[]{"SPS", "PfP", "SPS",
                                    Character.valueOf('P'), OrePrefix.stoneSmooth, Character.valueOf('S'), new ItemStack(Blocks.STONE_BUTTON, 1, 32767)});
                            break;
                        default:
                            GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gearGt, aMaterial, 1), tBits, new Object[]{"SPS", "PwP", "SPS",
                                    Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('S'), OrePrefix.stick.get(aMaterial)});
                    }
                }
                if (aMaterial.contains(SubTag.SMELTING_TO_GEM)) {
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 1), tBits, new Object[]{"XXX", "XXX", "XXX",
                            Character.valueOf('X'), OrePrefix.nugget.get(aMaterial)});
                } else {
                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 1), tBits, new Object[]{"XXX", "XXX", "XXX",
                            Character.valueOf('X'), OrePrefix.nugget.get(aMaterial)});
                }
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.crushedCentrifuged.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.crystalline.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.crystal.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustPure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.crushedPurified.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustPure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.cleanGravel.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustPure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.reduced.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.clump.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.shard.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.crushed.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.dirtyGravel.get(aMaterial)});

                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, aMaterial, 4L), tBits,
                        new Object[]{" X", "  ", 'X', OrePrefix.dust.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustTiny, aMaterial, 9L), tBits,
                        new Object[]{"X ", "  ", 'X', OrePrefix.dust.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 1), tBits,
                        new Object[]{"XX", "XX", 'X', OrePrefix.dustSmall.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 1), tBits,
                        new Object[]{"XXX", "XXX", "XXX", 'X', OrePrefix.dustTiny.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 16L), tBits, new Object[]{"Xc", Character.valueOf('X'),
//                        OrePrefix.crateGtDust.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 16L), tBits, new Object[]{"Xc", Character.valueOf('X'),
//                        OrePrefix.crateGtGem.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.ingot, aMaterial, 16L), tBits, new Object[]{"Xc",
//                        Character.valueOf('X'), OrePrefix.crateGtIngot.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.plate, aMaterial, 16L), tBits, new Object[]{"Xc",
//                        Character.valueOf('X'), OrePrefix.crateGtPlate.get(aMaterial)});
//
//                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gemChipped, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemFlawed.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gemFlawed, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gem.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gem, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemFlawless.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.gemFlawless, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemExquisite.get(aMaterial)});
//                if ((aMaterial.contains(SubTag.MORTAR_GRINDABLE)) && (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, aMaterial.mName, true))) {
//                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemChipped.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dustSmall, aMaterial, 2L), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemFlawed.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gem.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 2L), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemFlawless.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 4L), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemExquisite.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.ingot.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.plate.get(aMaterial)});
//                }
            }
        }*/
    }

    public void onServerStarting() {
        GTLog.logger.info("GT_Mod: ServerStarting-Phase started!");

//        try {
//            for (int i = 1; i < GregTechAPI.METATILEENTITIES.length; i++) {
//                for (; i < GregTechAPI.METATILEENTITIES.length; i++) {
//                    if (GregTechAPI.METATILEENTITIES[i] != null) {
//                        GregTechAPI.METATILEENTITIES[i].onServerStart();
//                    }
//                }
//            }
//        } catch (Throwable e) {
//            e.printStackTrace(GTLog.err);
//        }
    }

    public void onServerStarted() {

    }

    public void onServerStopping() {
//        File tSaveDirectory = getSaveDirectory();
//        if (tSaveDirectory != null) {
//            try {
//                for (int i = 1; i < GregTechAPI.METATILEENTITIES.length; i++) {
//                    for (; i < GregTechAPI.METATILEENTITIES.length; i++) {
//                        if (GregTechAPI.METATILEENTITIES[i] != null) {
//                            GregTechAPI.METATILEENTITIES[i].onWorldSave(tSaveDirectory);
//                        }
//                    }
//                }
//            } catch (Throwable e) {
//                e.printStackTrace(GTLog.err);
//            }
//        }
    }

    @Override
    public Object getServerGuiElement(int aID, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ) {
//        TileEntity tTileEntity = aWorld.getTileEntity(new BlockPos(aX, aY, aZ));
//        if ((tTileEntity instanceof IGregTechTileEntity)) {
//            IMetaTileEntity tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
//            if (tMetaTileEntity != null) {
//                return tMetaTileEntity.getServerGUI(aID, aPlayer.inventory, (IGregTechTileEntity) tTileEntity);
//            }
//        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int aID, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ) {
//        TileEntity tTileEntity = aWorld.getTileEntity(new BlockPos(aX, aY, aZ));
//        if ((tTileEntity instanceof IGregTechTileEntity)) {
//            IMetaTileEntity tMetaTileEntity = ((IGregTechTileEntity) tTileEntity).getMetaTileEntity();
//            if (tMetaTileEntity != null) {
//                return tMetaTileEntity.getClientGUI(aID, aPlayer.inventory, (IGregTechTileEntity) tTileEntity);
//            }
//        }
        return null;
    }

    public int getBurnTime(ItemStack fuel) {
        if (fuel == null || (fuel.getItem() == null)) {
            return 0;
        }
        int fuelValue = 0;
        if (fuel.getItem() instanceof MetaItem) {
            fuelValue = Math.max(fuelValue, ((MetaItem<?>) fuel.getItem()).getBurnTime(fuel));
        }

//        if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "gemSodium")) {
//            fuelValue = Math.max(fuelValue, 4000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "crushedSodium")) {
//            fuelValue = Math.max(fuelValue, 4000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustImpureSodium")) {
//            fuelValue = Math.max(fuelValue, 4000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustSodium")) {
//            fuelValue = Math.max(fuelValue, 4000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustSmallSodium")) {
//            fuelValue = Math.max(fuelValue, 1000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustTinySodium")) {
//            fuelValue = Math.max(fuelValue, 444);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "gemLithium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "crushedLithium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustImpureLithium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustLithium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustSmallLithium")) {
//            fuelValue = Math.max(fuelValue, 2000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustTinyLithium")) {
//            fuelValue = Math.max(fuelValue, 888);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "gemCaesium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "crushedCaesium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustImpureCaesium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustCaesium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustSmallCaesium")) {
//            fuelValue = Math.max(fuelValue, 2000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustTinyCaesium")) {
//            fuelValue = Math.max(fuelValue, 888);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "gemLignite")) {
//            fuelValue = Math.max(fuelValue, 1200);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "crushedLignite")) {
//            fuelValue = Math.max(fuelValue, 1200);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustImpureLignite")) {
//            fuelValue = Math.max(fuelValue, 1200);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustLignite")) {
//            fuelValue = Math.max(fuelValue, 1200);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustSmallLignite")) {
//            fuelValue = Math.max(fuelValue, 375);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustTinyLignite")) {
//            fuelValue = Math.max(fuelValue, 166);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "gemCoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "crushedCoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustImpureCoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustCoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustSmallCoal")) {
//            fuelValue = Math.max(fuelValue, 400);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustTinyCoal")) {
//            fuelValue = Math.max(fuelValue, 177);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "gemCharcoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "crushedCharcoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustImpureCharcoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustCharcoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustSmallCharcoal")) {
//            fuelValue = Math.max(fuelValue, 400);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustTinyCharcoal")) {
//            fuelValue = Math.max(fuelValue, 177);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustWood")) {
//            fuelValue = Math.max(fuelValue, 100);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustSmallWood")) {
//            fuelValue = Math.max(fuelValue, 25);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "dustTinyWood")) {
//            fuelValue = Math.max(fuelValue, 11);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "plateWood")) {
//            fuelValue = Math.min(fuelValue, 300);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "blockLignite")) {
//            fuelValue = Math.max(fuelValue, 12000);
//        } else if (OreDictionaryUnifier.isItemStackInstanceOf(fuel, "blockCharcoal")) {
//            fuelValue = Math.max(fuelValue, 16000);
//        } else if (GT_Utility.areStacksEqual(fuel, new ItemStack(Blocks.WOODEN_BUTTON, 1))) {
//            fuelValue = Math.max(fuelValue, 150);
//        } else if (GT_Utility.areStacksEqual(fuel, new ItemStack(Blocks.LADDER, 1))) {
//            fuelValue = Math.max(fuelValue, 100);
//        } else if (GT_Utility.areStacksEqual(fuel, new ItemStack(Items.SIGN, 1))) {
//            fuelValue = Math.max(fuelValue, 600);
//        } else if (GT_Utility.areStacksEqual(fuel, new ItemStack(Items.OAK_DOOR, 1))) {
//            fuelValue = Math.max(fuelValue, 600);
//        } else if (GT_Utility.areStacksEqual(fuel, ItemList.Block_MSSFUEL.get(1))) {
//            fuelValue = Math.max(fuelValue, 150000);
//        } else if (GT_Utility.areStacksEqual(fuel, ItemList.Block_SSFUEL.get(1))) {
//            fuelValue = Math.max(fuelValue, 100000);
//        }
        return fuelValue;
    }

//    public Fluid addAutogeneratedMoltenFluid(Materials aMaterial) {
//        return addFluid("molten." + aMaterial.mName.toLowerCase(Locale.ENGLISH), "molten.autogenerated", "Molten " + aMaterial.mDefaultLocalName, aMaterial,
//                aMaterial.mMoltenRGBa, 4, aMaterial.mMeltingPoint <= 0 ? 1000 : aMaterial.mMeltingPoint, null, null, 0);
//    }
//
//    public Fluid addAutogeneratedPlasmaFluid(Materials aMaterial) {
//        return addFluid("plasma." + aMaterial.mName.toLowerCase(Locale.ENGLISH), "plasma.autogenerated", aMaterial.mDefaultLocalName + " Plasma", aMaterial,
//                aMaterial.mMoltenRGBa, 3, 10000, OreDictionaryUnifier.get(OrePrefix.cellPlasma, aMaterial, 1), ItemList.Cell_Empty.get(1),
//                1000);
//    }
//
//    public Fluid addFluid(String aName, String aLocalized, Materials aMaterial, int aState, int aTemperatureK) {
//        return addFluid(aName, aLocalized, aMaterial, aState, aTemperatureK, null, null, 0);
//    }
//
//    public Fluid addFluid(String aName, String aLocalized, Materials aMaterial, int aState, int aTemperatureK, ItemStack aFullContainer,
//                          ItemStack aEmptyContainer, int aFluidAmount) {
//        return addFluid(aName, aName.toLowerCase(Locale.ENGLISH), aLocalized, aMaterial, null, aState, aTemperatureK, aFullContainer, aEmptyContainer, aFluidAmount);
//    }
//
//    public Fluid addFluid(String aName, String aTexture, String aLocalized, Materials aMaterial, short[] aRGBa, int aState, int aTemperatureK,
//                          ItemStack aFullContainer, ItemStack aEmptyContainer, int aFluidAmount) {
//        aName = aName.toLowerCase(Locale.ENGLISH);
//        Fluid rFluid = new GT_Fluid(aName, aTexture, aRGBa != null ? aRGBa : Dyes._NULL.getRGBA());
//        GT_LanguageManager.addStringLocalization(rFluid.getUnlocalizedName(), aLocalized == null ? aName : aLocalized);
//        if (FluidRegistry.registerFluid(rFluid)) {
//            switch (aState) {
//                case 0:
//                    rFluid.setGaseous(false);
//                    rFluid.setViscosity(10000);
//                    break;
//                case 1:
//                case 4:
//                    rFluid.setGaseous(false);
//                    rFluid.setViscosity(1000);
//                    break;
//                case 2:
//                    rFluid.setGaseous(true);
//                    rFluid.setDensity(-100);
//                    rFluid.setViscosity(200);
//                    break;
//                case 3:
//                    rFluid.setGaseous(true);
//                    rFluid.setDensity(55536);
//                    rFluid.setViscosity(10);
//                    rFluid.setLuminosity(15);
//            }
//        } else {
//            rFluid = FluidRegistry.getFluid(aName);
//        }
//        if (aMaterial != null) {
//            switch (aState) {
//                case 0:
//                    aMaterial.mSolid = rFluid;
//                    break;
//                case 1:
//                    aMaterial.mFluid = rFluid;
//                    break;
//                case 2:
//                    aMaterial.mGas = rFluid;
//                    break;
//                case 3:
//                    aMaterial.mPlasma = rFluid;
//                    break;
//                case 4:
//                    aMaterial.mStandardMoltenFluid = rFluid;
//            }
//        }
//        registerFluidContainer(aFullContainer, aEmptyContainer, rFluid, aFluidAmount);
//        return rFluid;
//    }
//
//    public void registerFluidContainer(ItemStack aFullContainer, ItemStack aEmptyContainer, Fluid rFluid, int aFluidAmount) {
//        if ((aFullContainer != null) && (aEmptyContainer != null)
//                && (!FluidContainerRegistry.registerFluidContainer(new FluidStack(rFluid, aFluidAmount), aFullContainer, aEmptyContainer))) {
//            GTValues.RA.addFluidCannerRecipe(aFullContainer, GT_Utility.getContainerItem(aFullContainer, false), null, new FluidStack(rFluid, aFluidAmount));
//        }
//    }

    public boolean isServerSide() {
        return true;
    }

    public boolean isClientSide() {
        return false;
    }
}