package gregtech.api.util;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.interfaces.IDamagableItem;
import gregtech.api.interfaces.IItemContainer;
import gregtech.api.interfaces.internal.IGT_CraftingRecipe;
import gregtech.api.objects.GT_HashSet;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.objects.ItemData;
import ic2.api.item.IBoxable;
import ic2.api.item.IC2Items;
import ic2.api.item.IElectricItem;
import ic2.api.reactor.IReactorComponent;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeOutput;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

import static gregtech.api.enums.GT_Values.*;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the Interface I use for interacting with other Mods.
 * <p/>
 * Due to the many imports, this File can cause compile Problems if not all the APIs are installed
 */
public class GT_ModHandler {
    public static final List<IRecipe> sSingleNonBlockDamagableRecipeList = new ArrayList<IRecipe>(1000);
    private static final Map<String, ItemStack> sIC2ItemMap = new HashMap<String, ItemStack>();
    private static final List<IRecipe> sAllRecipeList = /*Collections.synchronizedList(*/new ArrayList<IRecipe>(5000)/*)*/, sBufferRecipeList = new ArrayList<IRecipe>(1000);
    public static volatile int VERSION = 509;
    public static Collection<String> sNativeRecipeClasses = new HashSet<String>(), sSpecialRecipeClasses = new HashSet<String>();
    public static GT_HashSet<GT_ItemStack> sNonReplaceableItems = new GT_HashSet<GT_ItemStack>();
    public static Object sBoxableWrapper = GT_Utility.callConstructor("gregtechmod.api.util.GT_IBoxableWrapper", 0, null, false);
    private static Map<IRecipeInput, RecipeOutput> sExtractorRecipes = new /*Concurrent*/HashMap<IRecipeInput, RecipeOutput>();
    private static Map<IRecipeInput, RecipeOutput> sMaceratorRecipes = new /*Concurrent*/HashMap<IRecipeInput, RecipeOutput>();
    private static Map<IRecipeInput, RecipeOutput> sCompressorRecipes = new /*Concurrent*/HashMap<IRecipeInput, RecipeOutput>();
    private static Map<IRecipeInput, RecipeOutput> sOreWashingRecipes = new /*Concurrent*/HashMap<IRecipeInput, RecipeOutput>();
    private static Map<IRecipeInput, RecipeOutput> sThermalCentrifugeRecipes = new /*Concurrent*/HashMap<IRecipeInput, RecipeOutput>();
    private static Map<IRecipeInput, RecipeOutput> sMassfabRecipes = new /*Concurrent*/HashMap<IRecipeInput, RecipeOutput>();
    private static boolean sBufferCraftingRecipes = true;
    public static List<Integer> sSingleNonBlockDamagableRecipeList_list = new ArrayList<Integer>(100);
    private static boolean sSingleNonBlockDamagableRecipeList_create = true;
    private static final ItemStack sMt1 = new ItemStack(Blocks.dirt, 1, 0), sMt2 = new ItemStack(Blocks.dirt, 1, 0);
    private static final String s_H = "h", s_F = "f", s_I = "I", s_P = "P", s_R = "R";
    private static final ItemStack[][]
            sShapes1 = new ItemStack[][]{
            {sMt1, null, sMt1, sMt1, sMt1, sMt1, null, sMt1, null},
            {sMt1, null, sMt1, sMt1, null, sMt1, sMt1, sMt1, sMt1},
            {null, sMt1, null, sMt1, sMt1, sMt1, sMt1, null, sMt1},
            {sMt1, sMt1, sMt1, sMt1, null, sMt1, null, null, null},
            {sMt1, null, sMt1, sMt1, sMt1, sMt1, sMt1, sMt1, sMt1},
            {sMt1, sMt1, sMt1, sMt1, null, sMt1, sMt1, null, sMt1},
            {null, null, null, sMt1, null, sMt1, sMt1, null, sMt1},
            {null, sMt1, null, null, sMt1, null, null, sMt2, null},
            {sMt1, sMt1, sMt1, null, sMt2, null, null, sMt2, null},
            {null, sMt1, null, null, sMt2, null, null, sMt2, null},
            {sMt1, sMt1, null, sMt1, sMt2, null, null, sMt2, null},
            {null, sMt1, sMt1, null, sMt2, sMt1, null, sMt2, null},
            {sMt1, sMt1, null, null, sMt2, null, null, sMt2, null},
            {null, sMt1, sMt1, null, sMt2, null, null, sMt2, null},
            {null, sMt1, null, sMt1, null, null, null, sMt1, sMt2},
            {null, sMt1, null, null, null, sMt1, sMt2, sMt1, null},
            {null, sMt1, null, sMt1, null, sMt1, null, null, sMt2},
            {null, sMt1, null, sMt1, null, sMt1, sMt2, null, null},
            {null, sMt2, null, null, sMt1, null, null, sMt1, null},
            {null, sMt2, null, null, sMt2, null, sMt1, sMt1, sMt1},
            {null, sMt2, null, null, sMt2, null, null, sMt1, null},
            {null, sMt2, null, sMt1, sMt2, null, sMt1, sMt1, null},
            {null, sMt2, null, null, sMt2, sMt1, null, sMt1, sMt1},
            {null, sMt2, null, null, sMt2, null, sMt1, sMt1, null},
            {sMt1, null, null, null, sMt2, null, null, null, sMt2},
            {null, null, sMt1, null, sMt2, null, sMt2, null, null},
            {sMt1, null, null, null, sMt2, null, null, null, null},
            {null, null, sMt1, null, sMt2, null, null, null, null},
            {sMt1, sMt2, null, null, null, null, null, null, null},
            {sMt2, sMt1, null, null, null, null, null, null, null},
            {sMt1, null, null, sMt2, null, null, null, null, null},
            {sMt2, null, null, sMt1, null, null, null, null, null},
            {sMt1, sMt1, sMt1, sMt1, sMt1, sMt1, null, sMt2, null},
            {sMt1, sMt1, null, sMt1, sMt1, sMt2, sMt1, sMt1, null},
            {null, sMt1, sMt1, sMt2, sMt1, sMt1, null, sMt1, sMt1},
            {null, sMt2, null, sMt1, sMt1, sMt1, sMt1, sMt1, sMt1},
            {sMt1, sMt1, sMt1, sMt1, sMt2, sMt1, null, sMt2, null},
            {sMt1, sMt1, null, sMt1, sMt2, sMt2, sMt1, sMt1, null},
            {null, sMt1, sMt1, sMt2, sMt2, sMt1, null, sMt1, sMt1},
            {null, sMt2, null, sMt1, sMt2, sMt1, sMt1, sMt1, sMt1},
            {sMt1, null, null, null, sMt1, null, null, null, null},
            {null, sMt1, null, sMt1, null, null, null, null, null},
            {sMt1, sMt1, null, sMt2, null, sMt1, sMt2, null, null},
            {null, sMt1, sMt1, sMt1, null, sMt2, null, null, sMt2}
    };
    public static List<Integer> sSingleNonBlockDamagableRecipeList_validsShapes1 = new ArrayList<Integer>(44);
    public static boolean sSingleNonBlockDamagableRecipeList_validsShapes1_update = false;
    public static List<Integer> sSingleNonBlockDamagableRecipeList_warntOutput = new ArrayList<Integer>(50);
    public static List<Integer> sVanillaRecipeList_warntOutput = new ArrayList<Integer>(50);
    public static final List<IRecipe> sSingleNonBlockDamagableRecipeList_verified = new ArrayList<IRecipe>(1000);

    static {
        sNativeRecipeClasses.add(ShapedRecipes.class.getName());
        sNativeRecipeClasses.add(ShapedOreRecipe.class.getName());
        sNativeRecipeClasses.add(GT_Shaped_Recipe.class.getName());
        sNativeRecipeClasses.add(ShapelessRecipes.class.getName());
        sNativeRecipeClasses.add(ShapelessOreRecipe.class.getName());
        sNativeRecipeClasses.add(GT_Shapeless_Recipe.class.getName());
        sNativeRecipeClasses.add(ic2.core.AdvRecipe.class.getName());
        sNativeRecipeClasses.add(ic2.core.AdvShapelessRecipe.class.getName());
        sNativeRecipeClasses.add("appeng.recipes.game.ShapedRecipe");
        sNativeRecipeClasses.add("appeng.recipes.game.ShapelessRecipe");
        sNativeRecipeClasses.add("forestry.core.utils.ShapedRecipeCustom");

        // Recipe Classes, which should never be removed.
        sSpecialRecipeClasses.add(net.minecraft.item.crafting.RecipeFireworks.class.getName());
        sSpecialRecipeClasses.add(net.minecraft.item.crafting.RecipesArmorDyes.class.getName());
        sSpecialRecipeClasses.add(net.minecraft.item.crafting.RecipeBookCloning.class.getName());
        sSpecialRecipeClasses.add(net.minecraft.item.crafting.RecipesMapCloning.class.getName());
        sSpecialRecipeClasses.add(net.minecraft.item.crafting.RecipesMapExtending.class.getName());
        sSpecialRecipeClasses.add("jds.bibliocraft.BiblioSpecialRecipes");
        sSpecialRecipeClasses.add("dan200.qcraft.shared.EntangledQBlockRecipe");
        sSpecialRecipeClasses.add("dan200.qcraft.shared.EntangledQuantumComputerRecipe");
        sSpecialRecipeClasses.add("dan200.qcraft.shared.QBlockRecipe");
        sSpecialRecipeClasses.add("appeng.recipes.game.FacadeRecipe");
        sSpecialRecipeClasses.add("appeng.recipes.game.DisassembleRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.carts.LocomotivePaintingRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.util.crafting.RotorRepairRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.util.crafting.RoutingTableCopyRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.util.crafting.RoutingTicketCopyRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.util.crafting.TankCartFilterRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.emblems.LocomotiveEmblemRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.emblems.EmblemPostColorRecipe");
        sSpecialRecipeClasses.add("mods.railcraft.common.emblems.EmblemPostEmblemRecipe");
        sSpecialRecipeClasses.add("mods.immibis.redlogic.interaction.RecipeDyeLumarButton");
        sSpecialRecipeClasses.add("thaumcraft.common.items.armor.RecipesRobeArmorDyes");
        sSpecialRecipeClasses.add("thaumcraft.common.items.armor.RecipesVoidRobeArmorDyes");
        sSpecialRecipeClasses.add("thaumcraft.common.lib.crafting.ShapelessNBTOreRecipe");
        sSpecialRecipeClasses.add("twilightforest.item.TFMapCloningRecipe");
        sSpecialRecipeClasses.add("forestry.lepidopterology.MatingRecipe");
        sSpecialRecipeClasses.add("micdoodle8.mods.galacticraft.planets.asteroids.recipe.CanisterRecipes");
        sSpecialRecipeClasses.add("shedar.mods.ic2.nuclearcontrol.StorageArrayRecipe");
    }

    /**
     * Returns if that Liquid is Water or Distilled Water
     */
    public static boolean isWater(FluidStack aFluid) {
        if (aFluid == null) return false;
        return aFluid.isFluidEqual(getWater(1)) || aFluid.isFluidEqual(getDistilledWater(1));
    }

    /**
     * Returns a Liquid Stack with given amount of Water.
     */
    public static FluidStack getWater(long aAmount) {
        return FluidRegistry.getFluidStack("water", (int) aAmount);
    }

    /**
     * Returns a Liquid Stack with given amount of distilled Water.
     */
    public static FluidStack getDistilledWater(long aAmount) {
        return FluidRegistry.getFluidStack("ic2distilledwater", (int) aAmount);
    }

    /**
     * Returns if that Liquid is Lava
     */
    public static boolean isLava(FluidStack aFluid) {
        if (aFluid == null) return false;
        return aFluid.isFluidEqual(getLava(1));
    }

    /**
     * Returns a Liquid Stack with given amount of Lava.
     */
    public static FluidStack getLava(long aAmount) {
        return FluidRegistry.getFluidStack("lava", (int) aAmount);
    }

    /**
     * Returns if that Liquid is Steam
     */
    public static boolean isSteam(FluidStack aFluid) {
        if (aFluid == null) return false;
        return aFluid.isFluidEqual(getSteam(1));
    }

    /**
     * Returns a Liquid Stack with given amount of Steam.
     */
    public static FluidStack getSteam(long aAmount) {
        return FluidRegistry.getFluidStack("steam", (int) aAmount);
    }

    /**
     * Returns if that Liquid is Milk
     */
    public static boolean isMilk(FluidStack aFluid) {
        if (aFluid == null) return false;
        return aFluid.isFluidEqual(getMilk(1));
    }

    /**
     * Returns a Liquid Stack with given amount of Milk.
     */
    public static FluidStack getMilk(long aAmount) {
        return FluidRegistry.getFluidStack("milk", (int) aAmount);
    }

    public static ItemStack getEmptyFuelCan(long aAmount) {
        return ItemList.IC2_Fuel_Can_Empty.get(aAmount);
    }

    public static ItemStack getEmptyCell(long aAmount) {
        return ItemList.Cell_Empty.get(aAmount);
    }

    public static ItemStack getAirCell(long aAmount) {
        return ItemList.Cell_Air.get(aAmount);
    }

    public static ItemStack getWaterCell(long aAmount) {
        return ItemList.Cell_Water.get(aAmount);
    }

    public static ItemStack getLavaCell(long aAmount) {
        return ItemList.Cell_Lava.get(aAmount);
    }

    /**
     * @param aValue the Value of this Stack, when burning inside a Furnace (200 = 1 Burn Process = 500 EU, max = 32767 (that is 81917.5 EU)), limited to Short because the vanilla Furnace otherwise can't handle it properly, stupid Mojang...
     */
    public static ItemStack setFuelValue(ItemStack aStack, short aValue) {
        aStack.setTagCompound(GT_Utility.getNBTContainingShort(aStack.getTagCompound(), "GT.ItemFuelValue", aValue));
        return aStack;
    }

    /**
     * @return the Value of this Stack, when burning inside a Furnace (200 = 1 Burn Process = 500 EU, max = 32767 (that is 81917.5 EU)), limited to Short because the vanilla Furnace otherwise can't handle it properly, stupid Mojang...
     */
    public static int getFuelValue(ItemStack aStack) {
        return TileEntityFurnace.getItemBurnTime(aStack);
    }

    /**
     * @param aValue Fuel value in EU
     */
    public static ItemStack getFuelCan(int aValue) {
        if (aValue < 5) return ItemList.IC2_Fuel_Can_Empty.get(1);
        ItemStack rFuelCanStack = ItemList.IC2_Fuel_Can_Filled.get(1);
        if (rFuelCanStack == null) return null;
        NBTTagCompound tNBT = new NBTTagCompound();
        tNBT.setInteger("value", aValue / 5);
        rFuelCanStack.setTagCompound(tNBT);
        return rFuelCanStack;
    }

    /**
     * @param aFuelCan the Item you want to check
     * @return the exact Value in EU the Fuel Can is worth if its even a Fuel Can.
     */
    public static int getFuelCanValue(ItemStack aFuelCan) {
        if (GT_Utility.isStackInvalid(aFuelCan) || !ItemList.IC2_Fuel_Can_Filled.isStackEqual(aFuelCan, false, true))
            return 0;
        NBTTagCompound tNBT = aFuelCan.getTagCompound();
        return tNBT == null ? 0 : tNBT.getInteger("value") * 5;
    }

    /**
     * Gets an Item from IndustrialCraft, and returns a Replacement Item if not possible
     */
    public static ItemStack getIC2Item(String aItem, long aAmount, ItemStack aReplacement) {
        if (GT_Utility.isStringInvalid(aItem) || !GregTech_API.sPreloadStarted) return null;
        //if (D1) GT_Log.out.println("Requested the Item '" + aItem + "' from the IC2-API");
        if (!sIC2ItemMap.containsKey(aItem)) try {
            ItemStack tStack = IC2Items.getItem(aItem);
            sIC2ItemMap.put(aItem, tStack);
            if (tStack == null && D1) GT_Log.err.println(aItem + " is not found in the IC2 Items!");
        } catch (Throwable e) {/*Do nothing*/}
        return GT_Utility.copyAmount(aAmount, sIC2ItemMap.get(aItem), aReplacement);
    }

    /**
     * Gets an Item from IndustrialCraft, but the Damage Value can be specified, and returns a Replacement Item with the same Damage if not possible
     */
    public static ItemStack getIC2Item(String aItem, long aAmount, int aMeta, ItemStack aReplacement) {
        ItemStack rStack = getIC2Item(aItem, aAmount, aReplacement);
        if (rStack == null) return null;
        Items.feather.setDamage(rStack, aMeta);
        return rStack;
    }

    /**
     * Gets an Item from IndustrialCraft, but the Damage Value can be specified
     */
    public static ItemStack getIC2Item(String aItem, long aAmount, int aMeta) {
        return getIC2Item(aItem, aAmount, aMeta, null);
    }

    /**
     * Gets an Item from IndustrialCraft
     */
    public static ItemStack getIC2Item(String aItem, long aAmount) {
        return getIC2Item(aItem, aAmount, null);
    }

    /**
     * Gets an Item from RailCraft
     */
    public static ItemStack getModItem(String aModID, String aItem, long aAmount) {
        return getModItem(aModID, aItem, aAmount, null);
    }

    /**
     * Gets an Item from RailCraft, and returns a Replacement Item if not possible
     */
    public static ItemStack getModItem(String aModID, String aItem, long aAmount, ItemStack aReplacement) {
        if (GT_Utility.isStringInvalid(aItem) || !GregTech_API.sPreloadStarted) return null;
        return GT_Utility.copyAmount(aAmount, GameRegistry.findItemStack(aModID, aItem, (int) aAmount), aReplacement);
    }

    /**
     * Gets an Item from RailCraft, but the Damage Value can be specified
     */
    public static ItemStack getModItem(String aModID, String aItem, long aAmount, int aMeta) {
        ItemStack rStack = getModItem(aModID, aItem, aAmount);
        if (rStack == null) return null;
        Items.feather.setDamage(rStack, aMeta);
        return rStack;
    }

    /**
     * Gets an Item from RailCraft, but the Damage Value can be specified, and returns a Replacement Item with the same Damage if not possible
     */
    public static ItemStack getModItem(String aModID, String aItem, long aAmount, int aMeta, ItemStack aReplacement) {
        ItemStack rStack = getModItem(aModID, aItem, aAmount, aReplacement);
        if (rStack == null) return null;
        Items.feather.setDamage(rStack, aMeta);
        return rStack;
    }

    /**
     * OUT OF ORDER
     */
    public static boolean getModeKeyDown(EntityPlayer aPlayer) {
        return false;
    }

    /**
     * OUT OF ORDER
     */
    public static boolean getBoostKeyDown(EntityPlayer aPlayer) {
        return false;
    }

    /**
     * OUT OF ORDER
     */
    public static boolean getJumpKeyDown(EntityPlayer aPlayer) {
        return false;
    }

    /**
     * Adds a Valuable Ore to the Miner
     */
    public static boolean addValuableOre(Block aBlock, int aMeta, int aValue) {
        if (aValue <= 0) return false;
        try {
            Class.forName("ic2.core.IC2").getMethod("addValuableOre", IRecipeInput.class, int.class).invoke(null, new RecipeInputItemStack(new ItemStack(aBlock, 1, aMeta)), aValue);
        } catch (Throwable e) {/*Do nothing*/}
        return true;
    }

    /**
     * Adds a Scrapbox Drop. Fails at April first for the "suddenly Hoes"-Feature of IC2
     */
    public static boolean addScrapboxDrop(float aChance, ItemStack aOutput) {
        aOutput = GT_OreDictUnificator.get(true, aOutput);
        if (aOutput == null || aChance <= 0) return false;
        aOutput.stackSize = 1;
        if (GT_Config.troll && !GT_Utility.areStacksEqual(aOutput, new ItemStack(Items.wooden_hoe, 1, 0))) return false;
        aChance = (float) GregTech_API.sRecipeFile.get(ConfigCategories.Machines.scrapboxdrops, aOutput, aChance);
        if (aChance <= 0) return false;
        try {
            GT_Utility.callMethod(GT_Utility.getFieldContent("ic2.api.recipe.Recipes", "scrapboxDrops", true, true), "addDrop", true, false, true, GT_Utility.copy(aOutput), aChance);
            GT_Utility.callMethod(GT_Utility.getFieldContent("ic2.api.recipe.Recipes", "scrapboxDrops", true, true), "addRecipe", true, true, false, GT_Utility.copy(aOutput), aChance);
        } catch (Throwable e) {/*Do nothing*/}
        return true;
    }

    /**
     * Adds an Item to the Recycler Blacklist
     */
    public static boolean addToRecyclerBlackList(ItemStack aRecycledStack) {
        if (aRecycledStack == null) return false;
        try {
            ic2.api.recipe.Recipes.recyclerBlacklist.add(new RecipeInputItemStack(aRecycledStack));
        } catch (Throwable e) {/*Do nothing*/}
        return true;
    }

    /**
     * Just simple Furnace smelting. Unbelievable how Minecraft fails at making a simple ItemStack->ItemStack mapping...
     */
    public static boolean addSmeltingRecipe(ItemStack aInput, ItemStack aOutput) {
        aOutput = GT_OreDictUnificator.get(true, aOutput);
        if (aInput == null || aOutput == null || GT_Utility.getContainerItem(aInput, false) != null) return false;
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.smelting, aInput, true)) return false;
        FurnaceRecipes.smelting().func_151394_a(aInput, GT_Utility.copy(aOutput), 0.0F);
        return true;
    }

    /**
     * Adds to Furnace AND Alloysmelter AND Induction Smelter
     */
    public static boolean addSmeltingAndAlloySmeltingRecipe(ItemStack aInput, ItemStack aOutput, boolean hidden) {
        if (aInput == null || aOutput == null) return false;
        boolean temp = false;
        if (aInput.stackSize == 1 && addSmeltingRecipe(aInput, aOutput)) temp = true;
        if (RA.addAlloySmelterRecipe(aInput, OrePrefixes.ingot.contains(aOutput) ? ItemList.Shape_Mold_Ingot.get(0) : OrePrefixes.block.contains(aOutput) ? ItemList.Shape_Mold_Block.get(0) : OrePrefixes.nugget.contains(aOutput) ? ItemList.Shape_Mold_Nugget.get(0) : null, aOutput, 130, 3,hidden))
            temp = true;
        if (addInductionSmelterRecipe(aInput, null, aOutput, null, aOutput.stackSize * 1600, 0)) temp = true;
        return temp;
    }

    /**
     * LiquidTransposer Recipe for both directions
     */
    public static boolean addLiquidTransposerRecipe(ItemStack aEmptyContainer, FluidStack aLiquid, ItemStack aFullContainer, int aMJ) {
        aFullContainer = GT_OreDictUnificator.get(true, aFullContainer);
        if (aEmptyContainer == null || aFullContainer == null || aLiquid == null) return false;
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.liquidtransposer, aFullContainer, true))
            return false;
        try {
            ThermalExpansion.addTransposerFill(aMJ * 10, aEmptyContainer, aFullContainer, aLiquid, true);
        } catch (Throwable e) {/*Do nothing*/}
        return true;
    }

    /**
     * LiquidTransposer Recipe for filling Containers
     */
    public static boolean addLiquidTransposerFillRecipe(ItemStack aEmptyContainer, FluidStack aLiquid, ItemStack aFullContainer, int aMJ) {
        aFullContainer = GT_OreDictUnificator.get(true, aFullContainer);
        if (aEmptyContainer == null || aFullContainer == null || aLiquid == null) return false;
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.liquidtransposerfilling, aFullContainer, true))
            return false;
        try {
            ThermalExpansion.addTransposerFill(aMJ * 10, aEmptyContainer, aFullContainer, aLiquid, false);
        } catch (Throwable e) {/*Do nothing*/}
        return true;
    }

    /**
     * LiquidTransposer Recipe for emptying Containers
     */
    public static boolean addLiquidTransposerEmptyRecipe(ItemStack aFullContainer, FluidStack aLiquid, ItemStack aEmptyContainer, int aMJ) {
        aEmptyContainer = GT_OreDictUnificator.get(true, aEmptyContainer);
        if (aFullContainer == null || aEmptyContainer == null || aLiquid == null) return false;
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.liquidtransposeremptying, aFullContainer, true))
            return false;
        try {
            ThermalExpansion.addTransposerExtract(aMJ * 10, aFullContainer, aEmptyContainer, aLiquid, 100, false);
        } catch (Throwable e) {/*Do nothing*/}
        return true;
    }

    /**
     * IC2-Extractor Recipe. Overloads old Recipes automatically
     */
    public static boolean addExtractionRecipe(ItemStack aInput, ItemStack aOutput) {
        aOutput = GT_OreDictUnificator.get(true, aOutput);
        if (aInput == null || aOutput == null) return false;
        GT_Utility.removeSimpleIC2MachineRecipe(aInput, getExtractorRecipeList(), null);
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.extractor, aInput, true)) return false;
        GT_Utility.addSimpleIC2MachineRecipe(aInput, getExtractorRecipeList(), null, aOutput);
        return true;
    }

    /**
     * RC-BlastFurnace Recipes
     */
    public static boolean addRCBlastFurnaceRecipe(ItemStack aInput, ItemStack aOutput, int aTime) {
        aOutput = GT_OreDictUnificator.get(true, aOutput);
        if (aInput == null || aOutput == null || aTime <= 0) return false;
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.rcblastfurnace, aInput, true)) return false;
        aInput = GT_Utility.copy(aInput);
        aOutput = GT_Utility.copy(aOutput);
        try {
            mods.railcraft.api.crafting.RailcraftCraftingManager.blastFurnace.addRecipe(aInput, true, false, aTime, aOutput);
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    public static boolean addPulverisationRecipe(ItemStack aInput, ItemStack aOutput1) {
        return addPulverisationRecipe(aInput, aOutput1, null, 0, false);
    }

    public static boolean addPulverisationRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2) {
        return addPulverisationRecipe(aInput, aOutput1, aOutput2, 100, false);
    }

    public static boolean addPulverisationRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, int aChance) {
        return addPulverisationRecipe(aInput, aOutput1, aOutput2, aChance, false);
    }

    public static boolean addPulverisationRecipe(ItemStack aInput, ItemStack aOutput1, boolean aOverwrite) {
        return addPulverisationRecipe(aInput, aOutput1, null, 0, aOverwrite);
    }

    public static boolean addPulverisationRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, boolean aOverwrite) {
        return addPulverisationRecipe(aInput, aOutput1, aOutput2, 100, aOverwrite);
    }

    public static boolean addPulverisationRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, int aChance, boolean aOverwrite) {
        return addPulverisationRecipe(aInput, aOutput1, aOutput2, aChance, null, 0, aOverwrite);
    }

    /**
     * Adds Several Pulverizer-Type Recipes.
     */
    public static boolean addPulverisationRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, int aChance2, ItemStack aOutput3, int aChance3, boolean aOverwrite) {
        aOutput1 = GT_OreDictUnificator.get(true, aOutput1);
        aOutput2 = GT_OreDictUnificator.get(true, aOutput2);
        if (GT_Utility.isStackInvalid(aInput) || GT_Utility.isStackInvalid(aOutput1)) return false;
        GT_Utility.removeSimpleIC2MachineRecipe(aInput, getMaceratorRecipeList(), null);

        if (GT_Utility.getContainerItem(aInput, false) == null) {
            if (GregTech_API.sRecipeFile.get(ConfigCategories.Machines.maceration, aInput, true)) {
                GT_Utility.addSimpleIC2MachineRecipe(aInput, getMaceratorRecipeList(), null, aOutput1);
            }
            addMagneticraftRecipe(aInput, aOutput1, aOutput2, aChance2, aOutput3, aChance3);
            RA.addPulveriserRecipe(aInput, new ItemStack[]{aOutput1, aOutput2, aOutput3}, new int[]{10000, aChance2 <= 0 ? 1000 : 100 * aChance2, aChance3 <= 0 ? 1000 : 100 * aChance3}, 400, 2);

            if (!OrePrefixes.log.contains(aInput)) {
                if (Materials.Wood.contains(aOutput1)) {
                    if (GregTech_API.sRecipeFile.get(ConfigCategories.Machines.pulverization, aInput, true)) {
                        if (aOutput2 == null)
                            ThermalExpansion.addSawmillRecipe(32000, GT_Utility.copy(aInput), GT_Utility.copy(aOutput1));
                        else
                            ThermalExpansion.addSawmillRecipe(32000, GT_Utility.copy(aInput), GT_Utility.copy(aOutput1), GT_Utility.copy(aOutput2), aChance2 <= 0 ? 10 : aChance2);
                    }
                } else {
                    if (GregTech_API.sRecipeFile.get(ConfigCategories.Machines.rockcrushing, aInput, true)) {
                        try {
                            if (GT_Utility.getBlockFromStack(aInput) != Blocks.obsidian && GT_Utility.getBlockFromStack(aInput) != Blocks.gravel) {
                                mods.railcraft.api.crafting.IRockCrusherRecipe tRecipe = mods.railcraft.api.crafting.RailcraftCraftingManager.rockCrusher.createNewRecipe(GT_Utility.copyAmount(1, aInput), aInput.getItemDamage() != W, false);
                                tRecipe.addOutput(GT_Utility.copy(aOutput1), 1.0F / aInput.stackSize);
                                if (aOutput2 != null)
                                    tRecipe.addOutput(GT_Utility.copy(aOutput2), (0.01F * (aChance2 <= 0 ? 10 : aChance2)) / aInput.stackSize);
                                if (aOutput3 != null)
                                    tRecipe.addOutput(GT_Utility.copy(aOutput3), (0.01F * (aChance3 <= 0 ? 10 : aChance3)) / aInput.stackSize);
                            }
                        } catch (Throwable e) {/*Do nothing*/}
                    }
                    if (GregTech_API.sRecipeFile.get(ConfigCategories.Machines.pulverization, aInput, true)) {
                        if (aOutput2 == null)
                            ThermalExpansion.addPulverizerRecipe(32000, GT_Utility.copy(aInput), GT_Utility.copy(aOutput1));
                        else
                            ThermalExpansion.addPulverizerRecipe(32000, GT_Utility.copy(aInput), GT_Utility.copy(aOutput1), GT_Utility.copy(aOutput2), aChance2 <= 0 ? 10 : aChance2);
                    }
                }
            }
        }
        return true;
    }

    static Class tClass;
    static Method tMethod1;
    static Method tMethod2;

    public static boolean addMagneticraftRecipe(ItemStack aInput, ItemStack aOutput1, ItemStack aOutput2, int aChance2, ItemStack aOutput3, int aChance3){
        if(GT_Mod.gregtechproxy.mMagneticraftRecipes && Loader.isModLoaded("Magneticraft")){
            try {
                if(tClass==null)tClass = Class.forName("com.cout970.magneticraft.api.access.MgRecipeRegister");
                if(tMethod1==null)tMethod1 = tClass.getMethod("registerCrusherRecipe", ItemStack.class, ItemStack.class,ItemStack.class, float.class, ItemStack.class, float.class);
                if(tMethod2==null)tMethod2 = tClass.getMethod("registerGrinderRecipe", ItemStack.class, ItemStack.class,ItemStack.class, float.class, ItemStack.class, float.class);

                ItemData  tData = GT_OreDictUnificator.getAssociation(aInput);
                if(tData!=null&&tData.mPrefix!=null){
                    if(tData.mPrefix==OrePrefixes.ore||tData.mPrefix==OrePrefixes.oreBlackgranite||tData.mPrefix==OrePrefixes.oreEndstone||tData.mPrefix==OrePrefixes.oreNetherrack||tData.mPrefix==OrePrefixes.oreRedgranite){
                        tMethod1.invoke(null, aInput, aOutput1, aOutput2,(float)((float)aChance2/GT_Mod.gregtechproxy.mMagneticraftBonusOutputPercent), aOutput3,(float)((float)aChance3/GT_Mod.gregtechproxy.mMagneticraftBonusOutputPercent));
                    }else if(tData.mPrefix==OrePrefixes.crushed||tData.mPrefix==OrePrefixes.crushedCentrifuged||tData.mPrefix==OrePrefixes.crushedPurified){
                        tMethod2.invoke(null, aInput, aOutput1, aOutput2,(float)((float)aChance2/GT_Mod.gregtechproxy.mMagneticraftBonusOutputPercent), aOutput3,(float)((float)aChance3/GT_Mod.gregtechproxy.mMagneticraftBonusOutputPercent));
                    }
                }

            } catch (Exception e) {e.printStackTrace();}
        }
        return true;
    }

    /**
     * Adds a Recipe to the Sawmills of GregTech and ThermalCraft
     */
    public static boolean addSawmillRecipe(ItemStack aInput1, ItemStack aOutput1, ItemStack aOutput2) {
        aOutput1 = GT_OreDictUnificator.get(true, aOutput1);
        aOutput2 = GT_OreDictUnificator.get(true, aOutput2);
        if (aInput1 == null || aOutput1 == null) return false;
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.sawmill, aInput1, true)) return false;
        try {
            ThermalExpansion.addSawmillRecipe(1600, aInput1, aOutput1, aOutput2, 100);
        } catch (Throwable e) {/*Do nothing*/}
        return true;
    }

    /**
     * Induction Smelter Recipes and Alloy Smelter Recipes
     */
    public static boolean addAlloySmelterRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, int aDuration, int aEUt, boolean aAllowSecondaryInputEmpty) {
        if (aInput1 == null || (aInput2 == null && !aAllowSecondaryInputEmpty) || aOutput1 == null) return false;
        aOutput1 = GT_OreDictUnificator.get(true, aOutput1);
        boolean temp = false;
        if (RA.addAlloySmelterRecipe(aInput1, aInput2, aOutput1, aDuration, aEUt)) temp = true;
        if (addInductionSmelterRecipe(aInput1, aInput2, aOutput1, null, aDuration * aEUt * 2, 0)) temp = true;
        return temp;
    }

    /**
     * Induction Smelter Recipes for TE
     */
    public static boolean addInductionSmelterRecipe(ItemStack aInput1, ItemStack aInput2, ItemStack aOutput1, ItemStack aOutput2, int aEnergy, int aChance) {
        aOutput1 = GT_OreDictUnificator.get(true, aOutput1);
        aOutput2 = GT_OreDictUnificator.get(true, aOutput2);
        if (aInput1 == null || aOutput1 == null || GT_Utility.getContainerItem(aInput1, false) != null) return false;
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.inductionsmelter, aInput2 == null ? aInput1 : aOutput1, true))
            return false;
        try {
            ThermalExpansion.addSmelterRecipe(aEnergy * 10, GT_Utility.copy(aInput1), aInput2 == null ? new ItemStack(Blocks.sand, 1, 0) : aInput2, aOutput1, aOutput2, aChance);
        } catch (Throwable e) {/*Do nothing*/}
        return true;
    }

    /**
     * Smelts Ores to Ingots
     */
    public static boolean addOreToIngotSmeltingRecipe(ItemStack aInput, ItemStack aOutput) {
        aOutput = GT_OreDictUnificator.get(true, aOutput);
        if (aInput == null || aOutput == null) return false;
        FurnaceRecipes.smelting().func_151394_a(aInput, GT_Utility.copy(aOutput), 0.0F);
        return true;
    }

    public static Map<IRecipeInput, RecipeOutput> getExtractorRecipeList() {
        try {
            return ic2.api.recipe.Recipes.extractor.getRecipes();
        } catch (Throwable e) {/*Do nothing*/}
        return sExtractorRecipes;
    }

    public static Map<IRecipeInput, RecipeOutput> getCompressorRecipeList() {
        try {
            return ic2.api.recipe.Recipes.compressor.getRecipes();
        } catch (Throwable e) {/*Do nothing*/}
        return sCompressorRecipes;
    }

    public static Map<IRecipeInput, RecipeOutput> getMaceratorRecipeList() {
        try {
            return ic2.api.recipe.Recipes.macerator.getRecipes();
        } catch (Throwable e) {/*Do nothing*/}
        return sMaceratorRecipes;
    }

    public static Map<IRecipeInput, RecipeOutput> getThermalCentrifugeRecipeList() {
        try {
            return ic2.api.recipe.Recipes.centrifuge.getRecipes();
        } catch (Throwable e) {/*Do nothing*/}
        return sThermalCentrifugeRecipes;
    }

    public static Map<IRecipeInput, RecipeOutput> getOreWashingRecipeList() {
        try {
            return ic2.api.recipe.Recipes.oreWashing.getRecipes();
        } catch (Throwable e) {/*Do nothing*/}
        return sOreWashingRecipes;
    }

    public static Map<IRecipeInput, RecipeOutput> getMassFabricatorList() {
        try {
            return ic2.api.recipe.Recipes.matterAmplifier.getRecipes();
        } catch (Throwable e) {/*Do nothing*/}
        return sMassfabRecipes;
    }

    /**
     * IC2-ThermalCentrifuge Recipe. Overloads old Recipes automatically
     */
    public static boolean addThermalCentrifugeRecipe(ItemStack aInput, int aHeat, Object... aOutput) {
        if (aInput == null || aOutput == null || aOutput.length <= 0 || aOutput[0] == null) return false;
        GT_Utility.removeSimpleIC2MachineRecipe(aInput, getThermalCentrifugeRecipeList(), null);
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.thermalcentrifuge, aInput, true)) return false;
        NBTTagCompound tNBT = new NBTTagCompound();
        tNBT.setInteger("minHeat", aHeat);
        GT_Utility.addSimpleIC2MachineRecipe(aInput, getThermalCentrifugeRecipeList(), tNBT, aOutput);
        return true;
    }

    /**
     * IC2-OreWasher Recipe. Overloads old Recipes automatically
     */
    public static boolean addOreWasherRecipe(ItemStack aInput, int aWaterAmount, Object... aOutput) {
        if (aInput == null || aOutput == null || aOutput.length <= 0 || aOutput[0] == null) return false;
        GT_Utility.removeSimpleIC2MachineRecipe(aInput, getOreWashingRecipeList(), null);
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.orewashing, aInput, true)) return false;
        NBTTagCompound tNBT = new NBTTagCompound();
        tNBT.setInteger("amount", aWaterAmount);
        GT_Utility.addSimpleIC2MachineRecipe(aInput, getOreWashingRecipeList(), tNBT, aOutput);
        return true;
    }

    /**
     * IC2-Compressor Recipe. Overloads old Recipes automatically
     */
    public static boolean addCompressionRecipe(ItemStack aInput, ItemStack aOutput) {
        aOutput = GT_OreDictUnificator.get(true, aOutput);
        if (aInput == null || aOutput == null) return false;
        GT_Utility.removeSimpleIC2MachineRecipe(aInput, getCompressorRecipeList(), null);
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.compression, aInput, true)) return false;
        GT_Utility.addSimpleIC2MachineRecipe(aInput, getCompressorRecipeList(), null, aOutput);
        return true;
    }

    /**
     * @param aValue Scrap = 5000, Scrapbox = 45000, Diamond Dust 125000
     */
    public static boolean addIC2MatterAmplifier(ItemStack aAmplifier, int aValue) {
        if (aAmplifier == null || aValue <= 0) return false;
        if (!GregTech_API.sRecipeFile.get(ConfigCategories.Machines.massfabamplifier, aAmplifier, true)) return false;
        try {
            NBTTagCompound tNBT = new NBTTagCompound();
            tNBT.setInteger("amplification", aValue);
            GT_Utility.callMethod(ic2.api.recipe.Recipes.matterAmplifier, "addRecipe", false, false, false, aAmplifier, tNBT);
        } catch (Throwable e) {/*Do nothing*/}
        return true;
    }

    /**
     * Rolling Machine Crafting Recipe
     */
    public static boolean addRollingMachineRecipe(ItemStack aResult, Object[] aRecipe) {
        aResult = GT_OreDictUnificator.get(true, aResult);
        if (aResult == null || aRecipe == null || aResult.stackSize <= 0) return false;
        try {
            mods.railcraft.api.crafting.RailcraftCraftingManager.rollingMachine.getRecipeList().add(new ShapedOreRecipe(GT_Utility.copy(aResult), aRecipe));
        } catch (Throwable e) {
            return addCraftingRecipe(GT_Utility.copy(aResult), aRecipe);
        }
        return true;
    }

    public static void stopBufferingCraftingRecipes() {
        sBufferCraftingRecipes = false;
        for (IRecipe tRecipe : sBufferRecipeList) {GameRegistry.addRecipe(tRecipe);}
        sBufferRecipeList.clear();
    }

    /**
     * Shapeless Crafting Recipes. Deletes conflicting Recipes too.
     */
    public static boolean addCraftingRecipe(ItemStack aResult, Enchantment[] aEnchantmentsAdded, int[] aEnchantmentLevelsAdded, Object[] aRecipe) {
        return addCraftingRecipe(aResult, aEnchantmentsAdded, aEnchantmentLevelsAdded, false, true, false, false, false, false, false, false, false, false, false, false, true, aRecipe);
    }

    /**
     * Regular Crafting Recipes. Deletes conflicting Recipes too.
     * <p/>
     * You can insert instances of IItemContainer into the Recipe Input Array directly without having to call "get(1)" on them.
     * <p/>
     * Enums are automatically getting their "name()"-Method called in order to deliver an OreDict String.
     * <p/>
     * Lowercase Letters are reserved for Tools. They are as follows:
     * <p/>
     * 'b' ToolDictNames.craftingToolBlade
     * 'c' ToolDictNames.craftingToolCrowbar,
     * 'd' ToolDictNames.craftingToolScrewdriver,
     * 'f' ToolDictNames.craftingToolFile,
     * 'h' ToolDictNames.craftingToolHardHammer,
     * 'i' ToolDictNames.craftingToolSolderingIron,
     * 'j' ToolDictNames.craftingToolSolderingMetal,
     * 'k' ToolDictNames.craftingToolKnive
     * 'm' ToolDictNames.craftingToolMortar,
     * 'p' ToolDictNames.craftingToolDrawplate,
     * 'r' ToolDictNames.craftingToolSoftHammer,
     * 's' ToolDictNames.craftingToolSaw,
     * 'w' ToolDictNames.craftingToolWrench,
     * 'x' ToolDictNames.craftingToolWireCutter,
     */
    public static boolean addCraftingRecipe(ItemStack aResult, Object[] aRecipe) {
        return addCraftingRecipe(aResult, 0, aRecipe);
    }

    /**
     * Regular Crafting Recipes. Deletes conflicting Recipes too.
     * <p/>
     * You can insert instances of IItemContainer into the Recipe Input Array directly without having to call "get(1)" on them.
     * <p/>
     * Enums are automatically getting their "name()"-Method called in order to deliver an OreDict String.
     * <p/>
     * Lowercase Letters are reserved for Tools. They are as follows:
     * <p/>
     * 'b' ToolDictNames.craftingToolBlade
     * 'c' ToolDictNames.craftingToolCrowbar,
     * 'd' ToolDictNames.craftingToolScrewdriver,
     * 'f' ToolDictNames.craftingToolFile,
     * 'h' ToolDictNames.craftingToolHardHammer,
     * 'i' ToolDictNames.craftingToolSolderingIron,
     * 'j' ToolDictNames.craftingToolSolderingMetal,
     * 'k' ToolDictNames.craftingToolKnive
     * 'm' ToolDictNames.craftingToolMortar,
     * 'p' ToolDictNames.craftingToolDrawplate,
     * 'r' ToolDictNames.craftingToolSoftHammer,
     * 's' ToolDictNames.craftingToolSaw,
     * 'w' ToolDictNames.craftingToolWrench,
     * 'x' ToolDictNames.craftingToolWireCutter,
     */
    public static boolean addCraftingRecipe(ItemStack aResult, long aBitMask, Object[] aRecipe) {
        return addCraftingRecipe(aResult, new Enchantment[0], new int[0], (aBitMask & RecipeBits.MIRRORED) != 0, (aBitMask & RecipeBits.BUFFERED) != 0, (aBitMask & RecipeBits.KEEPNBT) != 0, (aBitMask & RecipeBits.DISMANTLEABLE) != 0, (aBitMask & RecipeBits.NOT_REMOVABLE) == 0, (aBitMask & RecipeBits.REVERSIBLE) != 0, (aBitMask & RecipeBits.DELETE_ALL_OTHER_RECIPES) != 0, (aBitMask & RecipeBits.DELETE_ALL_OTHER_RECIPES_IF_SAME_NBT) != 0, (aBitMask & RecipeBits.DELETE_ALL_OTHER_SHAPED_RECIPES) != 0, (aBitMask & RecipeBits.DELETE_ALL_OTHER_NATIVE_RECIPES) != 0, (aBitMask & RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS) == 0, (aBitMask & RecipeBits.ONLY_ADD_IF_THERE_IS_ANOTHER_RECIPE_FOR_IT) != 0, (aBitMask & RecipeBits.ONLY_ADD_IF_RESULT_IS_NOT_NULL) != 0, aRecipe);
    }

    /**
     * Internal realisation of the Crafting Recipe adding Process.
     */
    private static boolean addCraftingRecipe(ItemStack aResult, Enchantment[] aEnchantmentsAdded, int[] aEnchantmentLevelsAdded, boolean aMirrored, boolean aBuffered, boolean aKeepNBT, boolean aDismantleable, boolean aRemovable, boolean aReversible, boolean aRemoveAllOthersWithSameOutput, boolean aRemoveAllOthersWithSameOutputIfTheyHaveSameNBT, boolean aRemoveAllOtherShapedsWithSameOutput, boolean aRemoveAllOtherNativeRecipes, boolean aCheckForCollisions, boolean aOnlyAddIfThereIsAnyRecipeOutputtingThis, boolean aOnlyAddIfResultIsNotNull, Object[] aRecipe) {
        aResult = GT_OreDictUnificator.get(true, aResult);
        if (aOnlyAddIfResultIsNotNull && aResult == null) return false;
        if (aResult != null && Items.feather.getDamage(aResult) == W) Items.feather.setDamage(aResult, 0);
        if (aRecipe == null || aRecipe.length <= 0) return false;

        boolean tThereWasARecipe = false;

        for (byte i = 0; i < aRecipe.length; i++) {
            if (aRecipe[i] instanceof IItemContainer)
                aRecipe[i] = ((IItemContainer) aRecipe[i]).get(1);
            else if (aRecipe[i] instanceof Enum)
                aRecipe[i] = ((Enum) aRecipe[i]).name();
            else if (!(aRecipe[i] == null || aRecipe[i] instanceof ItemStack || aRecipe[i] instanceof ItemData || aRecipe[i] instanceof String || aRecipe[i] instanceof Character))
                aRecipe[i] = aRecipe[i].toString();
        }

        try {
            String shape = E;
            int idx = 0;
            if (aRecipe[idx] instanceof Boolean) {
                throw new IllegalArgumentException();
            }

            ArrayList<Object> tRecipeList = new ArrayList<Object>(Arrays.asList(aRecipe));

            while (aRecipe[idx] instanceof String) {
                String s = (String) aRecipe[idx++];
                shape += s;
                while (s.length() < 3) s += " ";
                if (s.length() > 3) throw new IllegalArgumentException();

                for (char c : s.toCharArray()) {
                    switch (c) {
                        case 'b':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolBlade.name());
                            break;
                        case 'c':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolCrowbar.name());
                            break;
                        case 'd':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolScrewdriver.name());
                            break;
                        case 'f':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolFile.name());
                            break;
                        case 'h':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolHardHammer.name());
                            break;
                        case 'i':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolSolderingIron.name());
                            break;
                        case 'j':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolSolderingMetal.name());
                            break;
                        case 'k':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolKnife.name());
                            break;
                        case 'm':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolMortar.name());
                            break;
                        case 'p':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolDrawplate.name());
                            break;
                        case 'r':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolSoftHammer.name());
                            break;
                        case 's':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolSaw.name());
                            break;
                        case 'w':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolWrench.name());
                            break;
                        case 'x':
                            tRecipeList.add(c);
                            tRecipeList.add(ToolDictNames.craftingToolWireCutter.name());
                            break;
                    }
                }
            }

            aRecipe = tRecipeList.toArray();

            if (aRecipe[idx] instanceof Boolean) {
                idx++;
            }
            /*ConcurrentHash*/Map<Character, ItemStack> tItemStackMap = new /*ConcurrentHash*/HashMap<Character, ItemStack>();
            /*ConcurrentHash*/Map<Character, ItemData> tItemDataMap = new /*ConcurrentHash*/HashMap<Character, ItemData>();
            tItemStackMap.put(' ', null);

            boolean tRemoveRecipe = true;

            for (; idx < aRecipe.length; idx += 2) {
                if (aRecipe[idx] == null || aRecipe[idx + 1] == null) {
                    if (D1) {
                        GT_Log.err.println("WARNING: Missing Item for shaped Recipe: " + (aResult == null ? "null" : aResult.getDisplayName()));
                        for (Object tContent : aRecipe) GT_Log.err.println(tContent);
                    }
                    return false;
                }
                Character chr = (Character) aRecipe[idx];
                Object in = aRecipe[idx + 1];
                if (in instanceof ItemStack) {
                    tItemStackMap.put(chr, GT_Utility.copy((ItemStack) in));
                    tItemDataMap.put(chr, GT_OreDictUnificator.getItemData((ItemStack) in));
                } else if (in instanceof ItemData) {
                    String tString = in.toString();
                    if (tString.equals("plankWood")) {
                        tItemDataMap.put(chr, new ItemData(Materials.Wood, M));
                    } else if (tString.equals("stoneNetherrack")) {
                        tItemDataMap.put(chr, new ItemData(Materials.Netherrack, M));
                    } else if (tString.equals("stoneObsidian")) {
                        tItemDataMap.put(chr, new ItemData(Materials.Obsidian, M));
                    } else if (tString.equals("stoneEndstone")) {
                        tItemDataMap.put(chr, new ItemData(Materials.Endstone, M));
                    } else {
                        tItemDataMap.put(chr, (ItemData) in);
                    }
                    ItemStack tStack = GT_OreDictUnificator.getFirstOre(in, 1);
                    if (tStack == null) tRemoveRecipe = false;
                    else tItemStackMap.put(chr, tStack);
                    in = aRecipe[idx + 1] = in.toString();
                } else if (in instanceof String) {
                    if (in.equals(OreDictNames.craftingChest.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Wood, M * 8));
                    else if (in.equals(OreDictNames.craftingBook.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Paper, M * 3));
                    else if (in.equals(OreDictNames.craftingPiston.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Stone, M * 4, Materials.Wood, M * 3));
                    else if (in.equals(OreDictNames.craftingFurnace.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Stone, M * 8));
                    else if (in.equals(OreDictNames.craftingIndustrialDiamond.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Diamond, M));
                    else if (in.equals(OreDictNames.craftingAnvil.toString()))
                        tItemDataMap.put(chr, new ItemData(Materials.Iron, M * 10));
                    ItemStack tStack = GT_OreDictUnificator.getFirstOre(in, 1);
                    if (tStack == null) tRemoveRecipe = false;
                    else tItemStackMap.put(chr, tStack);
                } else {
                    throw new IllegalArgumentException();
                }
            }

            if (aReversible && aResult != null) {
                ItemData[] tData = new ItemData[9];
                int x = -1;
                for (char chr : shape.toCharArray()) tData[++x] = tItemDataMap.get(chr);
                if (GT_Utility.arrayContainsNonNull(tData))
                    GT_OreDictUnificator.addItemData(aResult, new ItemData(tData));
            }

            if (aCheckForCollisions && tRemoveRecipe) {
                ItemStack[] tRecipe = new ItemStack[9];
                int x = -1;
                for (char chr : shape.toCharArray()) {
                    tRecipe[++x] = tItemStackMap.get(chr);
                    if (tRecipe[x] != null && Items.feather.getDamage(tRecipe[x]) == W)
                        Items.feather.setDamage(tRecipe[x], 0);
                }
                tThereWasARecipe = removeRecipe(tRecipe) != null || tThereWasARecipe;
            }
        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
        }

        if (aResult == null || aResult.stackSize <= 0) return false;

        if (aRemoveAllOthersWithSameOutput || aRemoveAllOthersWithSameOutputIfTheyHaveSameNBT || aRemoveAllOtherShapedsWithSameOutput || aRemoveAllOtherNativeRecipes)
            tThereWasARecipe = removeRecipeByOutput(aResult, !aRemoveAllOthersWithSameOutputIfTheyHaveSameNBT, aRemoveAllOtherShapedsWithSameOutput, aRemoveAllOtherNativeRecipes) || tThereWasARecipe;

        if (aOnlyAddIfThereIsAnyRecipeOutputtingThis && !tThereWasARecipe) {
            ArrayList<IRecipe> tList = (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList();
            int tList_sS=tList.size();
            for (int i = 0; i < tList_sS && !tThereWasARecipe; i++) {
                IRecipe tRecipe = tList.get(i);
                if (sSpecialRecipeClasses.contains(tRecipe.getClass().getName())) continue;
                if (GT_Utility.areStacksEqual(GT_OreDictUnificator.get(tRecipe.getRecipeOutput()), aResult, true)) {
                    tList.remove(i--); tList_sS=tList.size();
                    tThereWasARecipe = true;
                }
            }
        }

        if (Items.feather.getDamage(aResult) == W || Items.feather.getDamage(aResult) < 0)
            Items.feather.setDamage(aResult, 0);

        GT_Utility.updateItemStack(aResult);

        if (tThereWasARecipe || !aOnlyAddIfThereIsAnyRecipeOutputtingThis) {
            if (sBufferCraftingRecipes && aBuffered)
                sBufferRecipeList.add(new GT_Shaped_Recipe(GT_Utility.copy(aResult), aDismantleable, aRemovable, aKeepNBT, aEnchantmentsAdded, aEnchantmentLevelsAdded, aRecipe).setMirrored(aMirrored));
            else
                GameRegistry.addRecipe(new GT_Shaped_Recipe(GT_Utility.copy(aResult), aDismantleable, aRemovable, aKeepNBT, aEnchantmentsAdded, aEnchantmentLevelsAdded, aRecipe).setMirrored(aMirrored));
        }
        return true;
    }

    /**
     * Shapeless Crafting Recipes. Deletes conflicting Recipes too.
     */
    public static boolean addShapelessEnchantingRecipe(ItemStack aResult, Enchantment[] aEnchantmentsAdded, int[] aEnchantmentLevelsAdded, Object[] aRecipe) {
        return addShapelessCraftingRecipe(aResult, aEnchantmentsAdded, aEnchantmentLevelsAdded, true, false, false, false, aRecipe);
    }

    /**
     * Shapeless Crafting Recipes. Deletes conflicting Recipes too.
     */
    public static boolean addShapelessCraftingRecipe(ItemStack aResult, Object[] aRecipe) {
        return addShapelessCraftingRecipe(aResult, RecipeBits.DO_NOT_CHECK_FOR_COLLISIONS | RecipeBits.BUFFERED, aRecipe);
    }

    /**
     * Shapeless Crafting Recipes. Deletes conflicting Recipes too.
     */
    public static boolean addShapelessCraftingRecipe(ItemStack aResult, long aBitMask, Object[] aRecipe) {
        return addShapelessCraftingRecipe(aResult, new Enchantment[0], new int[0], (aBitMask & RecipeBits.BUFFERED) != 0, (aBitMask & RecipeBits.KEEPNBT) != 0, (aBitMask & RecipeBits.DISMANTLEABLE) != 0, (aBitMask & RecipeBits.NOT_REMOVABLE) == 0, aRecipe);
    }

    /**
     * Shapeless Crafting Recipes. Deletes conflicting Recipes too.
     */
    private static boolean addShapelessCraftingRecipe(ItemStack aResult, Enchantment[] aEnchantmentsAdded, int[] aEnchantmentLevelsAdded, boolean aBuffered, boolean aKeepNBT, boolean aDismantleable, boolean aRemovable, Object[] aRecipe) {
        aResult = GT_OreDictUnificator.get(true, aResult);
        if (aRecipe == null || aRecipe.length <= 0) return false;
        for (byte i = 0; i < aRecipe.length; i++) {
            if (aRecipe[i] instanceof IItemContainer)
                aRecipe[i] = ((IItemContainer) aRecipe[i]).get(1);
            else if (aRecipe[i] instanceof Enum)
                aRecipe[i] = ((Enum) aRecipe[i]).name();
            else if (!(aRecipe[i] == null || aRecipe[i] instanceof ItemStack || aRecipe[i] instanceof String || aRecipe[i] instanceof Character))
                aRecipe[i] = aRecipe[i].toString();
        }
        try {
            ItemStack[] tRecipe = new ItemStack[9];
            int i = 0;
            for (Object tObject : aRecipe) {
                if (tObject == null) {
                    if (D1)
                        GT_Log.err.println("WARNING: Missing Item for shapeless Recipe: " + (aResult == null ? "null" : aResult.getDisplayName()));
                    for (Object tContent : aRecipe) GT_Log.err.println(tContent);
                    return false;
                }
                if (tObject instanceof ItemStack) {
                    tRecipe[i] = (ItemStack) tObject;
                } else if (tObject instanceof String) {
                    tRecipe[i] = GT_OreDictUnificator.getFirstOre(tObject, 1);
                    if (tRecipe[i] == null) break;
                }/* else if (tObject instanceof Boolean) {
                    //
                } else {
                    throw new IllegalArgumentException();
                }*/
                i++;
            }
            removeRecipe(tRecipe);
        } catch (Throwable e) {
            e.printStackTrace(GT_Log.err);
        }

        if (aResult == null || aResult.stackSize <= 0) return false;

        if (Items.feather.getDamage(aResult) == W || Items.feather.getDamage(aResult) < 0)
            Items.feather.setDamage(aResult, 0);

        GT_Utility.updateItemStack(aResult);

        if (sBufferCraftingRecipes && aBuffered)
            sBufferRecipeList.add(new GT_Shapeless_Recipe(GT_Utility.copy(aResult), aDismantleable, aRemovable, aKeepNBT, aEnchantmentsAdded, aEnchantmentLevelsAdded, aRecipe));
        else
            GameRegistry.addRecipe(new GT_Shapeless_Recipe(GT_Utility.copy(aResult), aDismantleable, aRemovable, aKeepNBT, aEnchantmentsAdded, aEnchantmentLevelsAdded, aRecipe));
        return true;
    }

    /**
     * Removes a Smelting Recipe
     */
    public static boolean removeFurnaceSmelting(ItemStack aInput) {
        if (aInput != null) {
            for (Object tInput : FurnaceRecipes.smelting().getSmeltingList().keySet()) {
                if (GT_Utility.isStackValid(tInput) && GT_Utility.areStacksEqual(aInput, (ItemStack) tInput, true)) {
                    FurnaceRecipes.smelting().getSmeltingList().remove(tInput);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes a Crafting Recipe and gives you the former output of it.
     *
     * @param aRecipe The content of the Crafting Grid as ItemStackArray with length 9
     * @return the output of the old Recipe or null if there was nothing.
     */
    public static ItemStack removeRecipe(ItemStack... aRecipe) {
        if (aRecipe == null) return null;
        boolean temp = false;
        for (byte i = 0; i < aRecipe.length; i++) {
            if (aRecipe[i] != null) {
                temp = true;
                break;
            }
        }
        if (!temp) return null;
        ItemStack rReturn = null;
        InventoryCrafting aCrafting = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer var1) {
                return false;
            }
        }, 3, 3);
        for (int i = 0; i < aRecipe.length && i < 9; i++) aCrafting.setInventorySlotContents(i, aRecipe[i]);
        ArrayList<IRecipe> tList = (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList();
        int tList_sS=tList.size();
        try {
            for (int i = 0; i < tList_sS; i++) {
                for (; i < tList_sS; i++) {
                    if ((!(tList.get(i) instanceof IGT_CraftingRecipe) || ((IGT_CraftingRecipe) tList.get(i)).isRemovable()) && tList.get(i).matches(aCrafting, DW)) {
                        rReturn = tList.get(i).getCraftingResult(aCrafting);
                        if (rReturn != null) tList.remove(i--); tList_sS=tList.size();
                    }
                }
            }} catch (Throwable e) {e.printStackTrace(GT_Log.err);}
        return rReturn;
    }

    public static boolean removeRecipeByOutput(ItemStack aOutput) {
        return removeRecipeByOutput(aOutput, true, false, false);
    }

    /**
     * Removes a Crafting Recipe.
     *
     * @param aOutput The output of the Recipe.
     * @return if it has removed at least one Recipe.
     */
    public static boolean removeRecipeByOutput(ItemStack aOutput, boolean aIgnoreNBT, boolean aNotRemoveShapelessRecipes, boolean aOnlyRemoveNativeHandlers) {
        if (aOutput == null) return false;
        boolean rReturn = false;
        ArrayList<IRecipe> tList = (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList();
        aOutput = GT_OreDictUnificator.get(aOutput);
        int tList_sS=tList.size();
        for (int i = 0; i < tList_sS; i++) {
            IRecipe tRecipe = tList.get(i);
            if (aNotRemoveShapelessRecipes && (tRecipe instanceof ShapelessRecipes || tRecipe instanceof ShapelessOreRecipe))
                continue;
            if (aOnlyRemoveNativeHandlers) {
                if (!sNativeRecipeClasses.contains(tRecipe.getClass().getName())) continue;
            } else {
                if (sSpecialRecipeClasses.contains(tRecipe.getClass().getName())) continue;
            }
            ItemStack tStack = tRecipe.getRecipeOutput();
            if ((!(tRecipe instanceof IGT_CraftingRecipe) || ((IGT_CraftingRecipe) tRecipe).isRemovable()) && GT_Utility.areStacksEqual(GT_OreDictUnificator.get(tStack), aOutput, aIgnoreNBT)) {
                tList.remove(i--); tList_sS=tList.size();
                rReturn = true;
            }
        }
        return rReturn;
    }

    /**
     * Checks all Crafting Handlers for Recipe Output
     * Used for the Autocrafting Table
     */
    public static ItemStack getAllRecipeOutput(World aWorld, ItemStack... aRecipe) {
        if (aRecipe == null || aRecipe.length == 0) return null;

        if (aWorld == null) aWorld = DW;

        boolean temp = false;
        for (byte i = 0; i < aRecipe.length; i++) {
            if (aRecipe[i] != null) {
                temp = true;
                break;
            }
        }
        if (!temp) return null;
        InventoryCrafting aCrafting = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer var1) {
                return false;
            }
        }, 3, 3);
        for (int i = 0; i < 9 && i < aRecipe.length; i++) aCrafting.setInventorySlotContents(i, aRecipe[i]);
        List<IRecipe> tList = CraftingManager.getInstance().getRecipeList();
        synchronized (sAllRecipeList) {
            if (sAllRecipeList.size() != tList.size()) {
                sAllRecipeList.clear();
                sAllRecipeList.addAll(tList);
            }
            for (int i = 0, j = sAllRecipeList.size(); i < j; i++) {
                IRecipe tRecipe = sAllRecipeList.get(i);
                if (tRecipe.matches(aCrafting, aWorld)) {
                    if (i > 10) {
                        sAllRecipeList.remove(i);
                        sAllRecipeList.add(i - 10, tRecipe);
                    }
                    return tRecipe.getCraftingResult(aCrafting);
                }
            }
        }

        int tIndex = 0;
        ItemStack tStack1 = null, tStack2 = null;
        for (int i = 0, j = aCrafting.getSizeInventory(); i < j; i++) {
            ItemStack tStack = aCrafting.getStackInSlot(i);
            if (tStack != null) {
                if (tIndex == 0) tStack1 = tStack;
                if (tIndex == 1) tStack2 = tStack;
                tIndex++;
            }
        }

        if (tIndex == 2) {
            assert tStack1 != null && tStack2 != null;
            if (tStack1.getItem() == tStack2.getItem() && tStack1.stackSize == 1 && tStack2.stackSize == 1 && tStack1.getItem().isRepairable()) {
                int tNewDamage = tStack1.getMaxDamage() + tStack1.getItemDamage() - tStack2.getItemDamage() + tStack1.getMaxDamage() / 20;
                return new ItemStack(tStack1.getItem(), 1, tNewDamage < 0 ? 0 : tNewDamage);
            }
        }

        return null;
    }

    /**
     * Gives you a copy of the Output from a Crafting Recipe
     * Used for Recipe Detection.
     */
    public static ItemStack getRecipeOutput(ItemStack... aRecipe) {
        return getRecipeOutput(false, aRecipe);
    }

    /**
     * Gives you a copy of the Output from a Crafting Recipe
     * Used for Recipe Detection.
     */
    public static ItemStack getRecipeOutput(boolean aUncopiedStack, ItemStack... aRecipe) {
        if (aRecipe == null) return null;
        boolean temp = false;
        for (byte i = 0; i < aRecipe.length; i++) {
            if (aRecipe[i] != null) {
                temp = true;
                break;
            }
        }
        if (!temp) return null;
        InventoryCrafting aCrafting = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer var1) {
                return false;
            }
        }, 3, 3);
        for (int i = 0; i < 9 && i < aRecipe.length; i++) aCrafting.setInventorySlotContents(i, aRecipe[i]);
        ArrayList<IRecipe> tList = (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList();
        for (int i = 0; i < tList.size(); i++) {
            temp = false;
            try {
                temp = tList.get(i).matches(aCrafting, DW);
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
            if (temp) {
                ItemStack tOutput = aUncopiedStack ? tList.get(i).getRecipeOutput() : tList.get(i).getCraftingResult(aCrafting);
                if (tOutput == null || tOutput.stackSize <= 0) {
                    // Seriously, who would ever do that shit?
                    if (!GregTech_API.sPostloadFinished)
                        throw new GT_ItsNotMyFaultException("Seems another Mod added a Crafting Recipe with null Output. Tell the Developer of said Mod to fix that.");
                } else {
                    if (aUncopiedStack) return tOutput;
                    return GT_Utility.copy(tOutput);
                }
            }
        }
        return null;
    }

    /**
     * Gives you a list of the Outputs from a Crafting Recipe
     * If you have multiple Mods, which add Bronze Armor for example
     * This also removes old Recipes from the List.
     */
    public static ArrayList<ItemStack> getVanillyToolRecipeOutputs(ItemStack... aRecipe) {
        if (!GregTech_API.sPostloadStarted || GregTech_API.sPostloadFinished)
            sSingleNonBlockDamagableRecipeList.clear();
        if (sSingleNonBlockDamagableRecipeList.isEmpty()) {
            for (IRecipe tRecipe : (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList()) {
                ItemStack tStack = tRecipe.getRecipeOutput();
                if (GT_Utility.isStackValid(tStack) && tStack.getMaxStackSize() == 1 && tStack.getMaxDamage() > 0 && !(tStack.getItem() instanceof ItemBlock) && !(tStack.getItem() instanceof IReactorComponent) && !isElectricItem(tStack) && !GT_Utility.isStackInList(tStack, sNonReplaceableItems)) {
                    if (!(tRecipe instanceof ShapelessRecipes || tRecipe instanceof ShapelessOreRecipe)) {
                        if (tRecipe instanceof ShapedOreRecipe) {
                            boolean temp = true;
                            for (Object tObject : ((ShapedOreRecipe) tRecipe).getInput())
                                if (tObject != null) {
                                    if (tObject instanceof ItemStack && (((ItemStack) tObject).getItem() == null || ((ItemStack) tObject).getMaxStackSize() < 2 || ((ItemStack) tObject).getMaxDamage() > 0 || ((ItemStack) tObject).getItem() instanceof ItemBlock)) {
                                        temp = false;
                                        break;
                                    }
                                    if (tObject instanceof List && ((List) tObject).isEmpty()) {
                                        temp = false;
                                        break;
                                    }
                                }
                            if (temp) sSingleNonBlockDamagableRecipeList.add(tRecipe);
                        } else if (tRecipe instanceof ShapedRecipes) {
                            boolean temp = true;
                            for (ItemStack tObject : ((ShapedRecipes) tRecipe).recipeItems) {
                                if (tObject != null && (tObject.getItem() == null || tObject.getMaxStackSize() < 2 || tObject.getMaxDamage() > 0 || tObject.getItem() instanceof ItemBlock)) {
                                    temp = false;
                                    break;
                                }
                            }
                            if (temp) sSingleNonBlockDamagableRecipeList.add(tRecipe);
                        } else {
                            sSingleNonBlockDamagableRecipeList.add(tRecipe);
                        }
                    }
                }
            }
            GT_Log.out.println("GT_Mod: Created a List of Tool Recipes containing " + sSingleNonBlockDamagableRecipeList.size() + " Recipes for recycling." + (sSingleNonBlockDamagableRecipeList.size() > 1024 ? " Scanning all these Recipes is the reason for the startup Lag you receive right now." : E));
        }
        ArrayList<ItemStack> rList = getRecipeOutputs(sSingleNonBlockDamagableRecipeList, true, aRecipe);
        if (!GregTech_API.sPostloadStarted || GregTech_API.sPostloadFinished)
            sSingleNonBlockDamagableRecipeList.clear();
        return rList;
    }

    /**
     * Gives you a list of the Outputs from a Crafting Recipe
     * If you have multiple Mods, which add Bronze Armor for example
     */
    public static ArrayList<ItemStack> getRecipeOutputs(ItemStack... aRecipe) {
        return getRecipeOutputs(CraftingManager.getInstance().getRecipeList(), false, aRecipe);
    }

    /**
     * Gives you a list of the Outputs from a Crafting Recipe
     * If you have multiple Mods, which add Bronze Armor for example
     */
    public static ArrayList<ItemStack> getRecipeOutputs(List<IRecipe> aList, boolean aDeleteFromList, ItemStack... aRecipe) {
        ArrayList<ItemStack> rList = new ArrayList<ItemStack>();
        if (aRecipe == null) return rList;
        boolean temp = false;
        for (byte i = 0; i < aRecipe.length; i++) {
            if (aRecipe[i] != null) {
                temp = true;
                break;
            }
        }
        if (!temp) return rList;
        InventoryCrafting aCrafting = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer var1) {
                return false;
            }
        }, 3, 3);
        for (int i = 0; i < 9 && i < aRecipe.length; i++) aCrafting.setInventorySlotContents(i, aRecipe[i]);
        for (int i = 0; i < aList.size(); i++) {
            temp = false;
            try {
                temp = aList.get(i).matches(aCrafting, DW);
            } catch (Throwable e) {
                e.printStackTrace(GT_Log.err);
            }
            if (temp) {
                ItemStack tOutput = aList.get(i).getCraftingResult(aCrafting);
                if (tOutput == null || tOutput.stackSize <= 0) {
                    // Seriously, who would ever do that shit?
                    if (!GregTech_API.sPostloadFinished)
                        throw new GT_ItsNotMyFaultException("Seems another Mod added a Crafting Recipe with null Output. Tell the Developer of said Mod to fix that.");
                } else {
                    rList.add(GT_Utility.copy(tOutput));
                    if (aDeleteFromList) aList.remove(i--);
                }
            }
        }
        return rList;
    }

    /**
     * Used in my own Macerator. Decreases StackSize of the Input if wanted.
     */
    public static ItemStack getMaceratorOutput(ItemStack aInput, boolean aRemoveInput, ItemStack aOutputSlot) {
        return GT_Utility.copy(getMachineOutput(aInput, getMaceratorRecipeList(), aRemoveInput, new NBTTagCompound(), aOutputSlot)[0]);
    }

    /**
     * Used in my own Extractor. Decreases StackSize of the Input if wanted.
     */
    public static ItemStack getExtractorOutput(ItemStack aInput, boolean aRemoveInput, ItemStack aOutputSlot) {
        return GT_Utility.copy(getMachineOutput(aInput, getExtractorRecipeList(), aRemoveInput, new NBTTagCompound(), aOutputSlot)[0]);
    }

    /**
     * Used in my own Compressor. Decreases StackSize of the Input if wanted.
     */
    public static ItemStack getCompressorOutput(ItemStack aInput, boolean aRemoveInput, ItemStack aOutputSlot) {
        return GT_Utility.copy(getMachineOutput(aInput, getCompressorRecipeList(), aRemoveInput, new NBTTagCompound(), aOutputSlot)[0]);
    }

    /**
     * Used in my own Furnace.
     */
    public static ItemStack getSmeltingOutput(ItemStack aInput, boolean aRemoveInput, ItemStack aOutputSlot) {
        if (aInput == null || aInput.stackSize < 1) return null;
        ItemStack rStack = GT_OreDictUnificator.get(FurnaceRecipes.smelting().getSmeltingResult(aInput));
        if (rStack != null && (aOutputSlot == null || (GT_Utility.areStacksEqual(rStack, aOutputSlot) && rStack.stackSize + aOutputSlot.stackSize <= aOutputSlot.getMaxStackSize()))) {
            if (aRemoveInput) aInput.stackSize--;
            return rStack;
        }
        return null;
    }

    /**
     * Used in my own Machines. Decreases StackSize of the Input if wanted.
     * <p/>
     * Checks also if there is enough Space in the Output Slots.
     */
    public static ItemStack[] getMachineOutput(ItemStack aInput, Map<IRecipeInput, RecipeOutput> aRecipeList, boolean aRemoveInput, NBTTagCompound rRecipeMetaData, ItemStack... aOutputSlots) {
        if (aOutputSlots == null || aOutputSlots.length <= 0) return new ItemStack[0];
        if (aInput == null) return new ItemStack[aOutputSlots.length];
        try {
            for (Entry<IRecipeInput, RecipeOutput> tEntry : aRecipeList.entrySet()) {
                if (tEntry.getKey().matches(aInput)) {
                    if (tEntry.getKey().getAmount() <= aInput.stackSize) {
                        ItemStack[] tList = (ItemStack[]) tEntry.getValue().items.toArray(new ItemStack[tEntry.getValue().items.size()]);
                        if (tList.length == 0) break;
                        ItemStack[] rList = new ItemStack[aOutputSlots.length];
                        rRecipeMetaData.setTag("return", tEntry.getValue().metadata);
                        for (byte i = 0; i < aOutputSlots.length && i < tList.length; i++) {
                            if (tList[i] != null) {
                                if (aOutputSlots[i] == null || (GT_Utility.areStacksEqual(tList[i], aOutputSlots[i]) && tList[i].stackSize + aOutputSlots[i].stackSize <= aOutputSlots[i].getMaxStackSize())) {
                                    rList[i] = GT_Utility.copy(tList[i]);
                                } else {
                                    return new ItemStack[aOutputSlots.length];
                                }
                            }
                        }

                        if (aRemoveInput) aInput.stackSize -= tEntry.getKey().getAmount();
                        return rList;
                    }
                    break;
                }
            }
        } catch (Throwable e) {
            if (D1) e.printStackTrace(GT_Log.err);
        }
        return new ItemStack[aOutputSlots.length];
    }

    /**
     * Used in my own Recycler.
     * <p/>
     * Only produces Scrap if aScrapChance == 0. aScrapChance is usually the random Number I give to the Function
     * If you directly insert 0 as aScrapChance then you can check if its Recycler-Blacklisted or similar
     */
    public static ItemStack getRecyclerOutput(ItemStack aInput, int aScrapChance) {
        if (aInput == null || aScrapChance != 0) return null;
        try {
            if (ic2.api.recipe.Recipes.recyclerWhitelist.isEmpty())
                return ic2.api.recipe.Recipes.recyclerBlacklist.contains(aInput) ? null : ItemList.IC2_Scrap.get(1);
            return ic2.api.recipe.Recipes.recyclerWhitelist.contains(aInput) ? ItemList.IC2_Scrap.get(1) : null;
        } catch (Throwable e) {/*Do nothing*/}
        try {
            return ic2.api.recipe.Recipes.recyclerBlacklist.contains(aInput) ? null : ItemList.IC2_Scrap.get(1);
        } catch (Throwable e) {/*Do nothing*/}
        return null;
    }

    /**
     * For the Scrapboxinator
     */
    public static ItemStack getRandomScrapboxDrop() {
        return ic2.api.recipe.Recipes.scrapboxDrops.getDrop(ItemList.IC2_Scrapbox.get(1), false);
    }

    /**
     * Charges an Electric Item. Only if it's a valid Electric Item of course.
     * This forces the Usage of proper Voltages (so not the transfer limits defined by the Items) unless you ignore the Transfer Limit.
     * If aTier is Integer.MAX_VALUE it will ignore Tier based Limitations.
     *
     * @return the actually used Energy.
     */
    public static int chargeElectricItem(ItemStack aStack, int aCharge, int aTier, boolean aIgnoreLimit, boolean aSimulate) {
        try {
            if (isElectricItem(aStack)) {
                int tTier = ((ic2.api.item.IElectricItem) aStack.getItem()).getTier(aStack);
                if (tTier < 0 || tTier == aTier || aTier == Integer.MAX_VALUE) {
                    if (!aIgnoreLimit && tTier >= 0)
                        aCharge = (int) Math.min(aCharge, V[Math.max(0, Math.min(V.length - 1, tTier))]);
                    if (aCharge > 0) {
                        int rCharge = (int) Math.max(0.0, ic2.api.item.ElectricItem.manager.charge(aStack, aCharge, tTier, true, aSimulate));
                        return rCharge + (rCharge * 4 > aTier ? aTier : 0);
                    }
                }
            }
        } catch (Throwable e) {/*Do nothing*/}
        return 0;
    }

    /**
     * Discharges an Electric Item. Only if it's a valid Electric Item for that of course.
     * This forces the Usage of proper Voltages (so not the transfer limits defined by the Items) unless you ignore the Transfer Limit.
     * If aTier is Integer.MAX_VALUE it will ignore Tier based Limitations.
     *
     * @return the Energy got from the Item.
     */
    public static int dischargeElectricItem(ItemStack aStack, int aCharge, int aTier, boolean aIgnoreLimit, boolean aSimulate, boolean aIgnoreDischargability) {
        try {
//			if (isElectricItem(aStack) &&  (aIgnoreDischargability || ((ic2.api.item.IElectricItem)aStack.getItem()).canProvideEnergy(aStack))) {
            if (isElectricItem(aStack)) {
                int tTier = ((ic2.api.item.IElectricItem) aStack.getItem()).getTier(aStack);
                if (tTier < 0 || tTier == aTier || aTier == Integer.MAX_VALUE) {
                    if (!aIgnoreLimit && tTier >= 0)
                        aCharge = (int) Math.min(aCharge, V[Math.max(0, Math.min(V.length - 1, tTier))]);
                    if (aCharge > 0) {
//						int rCharge = Math.max(0, ic2.api.item.ElectricItem.manager.discharge(aStack, aCharge + (aCharge * 4 > aTier ? aTier : 0), tTier, T, aSimulate));
                        int rCharge = (int) Math.max(0, ic2.api.item.ElectricItem.manager.discharge(aStack, aCharge + (aCharge * 4 > aTier ? aTier : 0), tTier, true, !aIgnoreDischargability, aSimulate));
                        return rCharge - (rCharge * 4 > aTier ? aTier : 0);
                    }
                }
            }
        } catch (Throwable e) {/*Do nothing*/}
        return 0;
    }

    /**
     * Uses an Electric Item. Only if it's a valid Electric Item for that of course.
     *
     * @return if the action was successful
     */
    public static boolean canUseElectricItem(ItemStack aStack, int aCharge) {
        try {
            if (isElectricItem(aStack)) {
                return ic2.api.item.ElectricItem.manager.canUse(aStack, aCharge);
            }
        } catch (Throwable e) {/*Do nothing*/}
        return false;
    }

    /**
     * Uses an Electric Item. Only if it's a valid Electric Item for that of course.
     *
     * @return if the action was successful
     */
    public static boolean useElectricItem(ItemStack aStack, int aCharge, EntityPlayer aPlayer) {
        try {
            if (isElectricItem(aStack)) {
                ic2.api.item.ElectricItem.manager.use(aStack, 0, aPlayer);
                if (ic2.api.item.ElectricItem.manager.canUse(aStack, aCharge)) {
                    return ic2.api.item.ElectricItem.manager.use(aStack, aCharge, aPlayer);
                }
            }
        } catch (Throwable e) {/*Do nothing*/}
        return false;
    }

    /**
     * Uses an Item. Tries to discharge in case of Electric Items
     */
    public static boolean damageOrDechargeItem(ItemStack aStack, int aDamage, int aDecharge, EntityLivingBase aPlayer) {
        if (GT_Utility.isStackInvalid(aStack) || (aStack.getMaxStackSize() <= 1 && aStack.stackSize > 1)) return false;
        if (aPlayer != null && aPlayer instanceof EntityPlayer && ((EntityPlayer) aPlayer).capabilities.isCreativeMode)
            return true;
        if (aStack.getItem() instanceof IDamagableItem) {
            return ((IDamagableItem) aStack.getItem()).doDamageToItem(aStack, aDamage);
        } else if (GT_ModHandler.isElectricItem(aStack)) {
            if (canUseElectricItem(aStack, aDecharge)) {
                if (aPlayer != null && aPlayer instanceof EntityPlayer) {
                    return GT_ModHandler.useElectricItem(aStack, aDecharge, (EntityPlayer) aPlayer);
                }
                return GT_ModHandler.dischargeElectricItem(aStack, aDecharge, Integer.MAX_VALUE, true, false, true) >= aDecharge;
            }
        } else if (aStack.getItem().isDamageable()) {
            if (aPlayer == null) {
                aStack.setItemDamage(aStack.getItemDamage() + aDamage);
            } else {
                aStack.damageItem(aDamage, aPlayer);
            }
            if (aStack.getItemDamage() >= aStack.getMaxDamage()) {
                aStack.setItemDamage(aStack.getMaxDamage() + 1);
                ItemStack tStack = GT_Utility.getContainerItem(aStack, true);
                if (tStack != null) {
                    aStack.func_150996_a(tStack.getItem());
                    aStack.setItemDamage(tStack.getItemDamage());
                    aStack.stackSize = tStack.stackSize;
                    aStack.setTagCompound(tStack.getTagCompound());
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Uses a Soldering Iron
     */
    public static boolean useSolderingIron(ItemStack aStack, EntityLivingBase aPlayer) {
        if (aPlayer == null || aStack == null) return false;
        if (GT_Utility.isStackInList(aStack, GregTech_API.sSolderingToolList)) {
            if (aPlayer instanceof EntityPlayer) {
                EntityPlayer tPlayer = (EntityPlayer) aPlayer;
                if (tPlayer.capabilities.isCreativeMode) return true;
                if (isElectricItem(aStack) && ic2.api.item.ElectricItem.manager.getCharge(aStack) > 1000.0d) {
                    for (int i = 0; i < tPlayer.inventory.mainInventory.length; i++) {
                        if (GT_Utility.isStackInList(tPlayer.inventory.mainInventory[i], GregTech_API.sSolderingMetalList)) {
                            if (tPlayer.inventory.mainInventory[i].stackSize < 1) return false;
                            if (tPlayer.inventory.mainInventory[i].stackSize == 1) {
                                tPlayer.inventory.mainInventory[i] = null;
                            } else {
                                tPlayer.inventory.mainInventory[i].stackSize--;
                            }
                            if (tPlayer.inventoryContainer != null) tPlayer.inventoryContainer.detectAndSendChanges();
                            if (canUseElectricItem(aStack, 10000)) {
                                return GT_ModHandler.useElectricItem(aStack, 10000, (EntityPlayer) aPlayer);
                            }
                            GT_ModHandler.useElectricItem(aStack, (int) ic2.api.item.ElectricItem.manager.getCharge(aStack), (EntityPlayer) aPlayer);
                            return false;
                        }
                    }
                }
            } else {
                damageOrDechargeItem(aStack, 1, 1000, aPlayer);
                return true;
            }
        }
        return false;
    }

    /**
     * Is this an electric Item, which can charge other Items?
     */
    public static boolean isChargerItem(ItemStack aStack) {
        try {
            if (isElectricItem(aStack)) {
                return ((ic2.api.item.IElectricItem) aStack.getItem()).canProvideEnergy(aStack);
            }
        } catch (Throwable e) {/*Do nothing*/}
        return false;
    }

    /**
     * Is this an electric Item?
     */
    public static boolean isElectricItem(ItemStack aStack) {
        try {
            return aStack != null && aStack.getItem() instanceof ic2.api.item.IElectricItem && ((IElectricItem) aStack.getItem()).getTier(aStack) < Integer.MAX_VALUE;
        } catch (Throwable e) {/*Do nothing*/}
        return false;
    }

    public static boolean isElectricItem(ItemStack aStack, byte aTier) {
        try {
            return aStack != null && aStack.getItem() instanceof ic2.api.item.IElectricItem && ((IElectricItem) aStack.getItem()).getTier(aStack) == aTier;
        } catch (Throwable e) {/*Do nothing*/}
        return false;
    }

    public static void registerBoxableItemToToolBox(ItemStack aStack) {
        if (aStack != null) registerBoxableItemToToolBox(aStack.getItem());
    }

    public static void registerBoxableItemToToolBox(Item aItem) {
        if (aItem != null && sBoxableWrapper != null) {
            try {
                ic2.api.item.ItemWrapper.registerBoxable(aItem, (IBoxable) sBoxableWrapper);
            } catch (Throwable e) {/*Do nothing*/}
        }
    }

    public static int getCapsuleCellContainerCountMultipliedWithStackSize(ItemStack... aStacks) {
        int rAmount = 0;
        for (ItemStack tStack : aStacks)
            if (tStack != null) rAmount += getCapsuleCellContainerCount(tStack) * tStack.stackSize;
        return rAmount;
    }

    public static int getCapsuleCellContainerCount(ItemStack aStack) {
        if (aStack == null) return 0;
        return GT_Utility.areStacksEqual(GT_Utility.getContainerForFilledItem(aStack, true), ItemList.Cell_Empty.get(1)) || OrePrefixes.cell.contains(aStack) || OrePrefixes.cellPlasma.contains(aStack) || GT_Utility.areStacksEqual(aStack, getIC2Item("waterCell", 1, W)) ? 1 : 0;
    }

    public static class RecipeBits {
        /**
         * Mirrors the Recipe
         */
        public static long MIRRORED = B[0];
        /**
         * Buffers the Recipe for later addition. This makes things more efficient.
         */
        public static long BUFFERED = B[1];
        /**
         * This is a special Tag I used for crafting Coins up and down.
         */
        public static long KEEPNBT = B[2];
        /**
         * Makes the Recipe Reverse Craftable in the Disassembler.
         */
        public static long DISMANTLEABLE = B[3];
        /**
         * Prevents the Recipe from accidentally getting removed by my own Handlers.
         */
        public static long NOT_REMOVABLE = B[4];
        /**
         * Reverses the Output of the Recipe for smelting and pulverising.
         */
        public static long REVERSIBLE = B[5];
        /**
         * Removes all Recipes with the same Output Item regardless of NBT, unless another Recipe Deletion Bit is added too.
         */
        public static long DELETE_ALL_OTHER_RECIPES = B[6];
        /**
         * Removes all Recipes with the same Output Item limited to the same NBT.
         */
        public static long DELETE_ALL_OTHER_RECIPES_IF_SAME_NBT = B[7];
        /**
         * Removes all Recipes with the same Output Item limited to Shaped Recipes.
         */
        public static long DELETE_ALL_OTHER_SHAPED_RECIPES = B[8];
        /**
         * Removes all Recipes with the same Output Item limited to native Recipe Handlers.
         */
        public static long DELETE_ALL_OTHER_NATIVE_RECIPES = B[9];
        /**
         * Disables the check for colliding Recipes.
         */
        public static long DO_NOT_CHECK_FOR_COLLISIONS = B[10];
        /**
         * Only adds the Recipe if there is another Recipe having that Output
         */
        public static long ONLY_ADD_IF_THERE_IS_ANOTHER_RECIPE_FOR_IT = B[11];
        /**
         * Only adds the Recipe if it has an Output
         */
        public static long ONLY_ADD_IF_RESULT_IS_NOT_NULL = B[12];
    }

    /**
     * Copy of the original Helper Class of Thermal Expansion, just to make sure it works even when other Mods include TE-APIs
     */
    public static class ThermalExpansion {
        public static void addFurnaceRecipe(int energy, ItemStack input, ItemStack output) {
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("output", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            output.writeToNBT(toSend.getCompoundTag("output"));
            FMLInterModComms.sendMessage("ThermalExpansion", "FurnaceRecipe", toSend);
        }

        public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput) {
            addPulverizerRecipe(energy, input, primaryOutput, null, 0);
        }

        public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput) {
            addPulverizerRecipe(energy, input, primaryOutput, secondaryOutput, 100);
        }

        public static void addPulverizerRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
            if (input == null || primaryOutput == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("primaryOutput", new NBTTagCompound());
            toSend.setTag("secondaryOutput", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            primaryOutput.writeToNBT(toSend.getCompoundTag("primaryOutput"));
            if (secondaryOutput != null) secondaryOutput.writeToNBT(toSend.getCompoundTag("secondaryOutput"));
            toSend.setInteger("secondaryChance", secondaryChance);
            FMLInterModComms.sendMessage("ThermalExpansion", "PulverizerRecipe", toSend);
        }

        public static void addSawmillRecipe(int energy, ItemStack input, ItemStack primaryOutput) {
            addSawmillRecipe(energy, input, primaryOutput, null, 0);
        }

        public static void addSawmillRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput) {
            addSawmillRecipe(energy, input, primaryOutput, secondaryOutput, 100);
        }

        public static void addSawmillRecipe(int energy, ItemStack input, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
            if (input == null || primaryOutput == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("primaryOutput", new NBTTagCompound());
            toSend.setTag("secondaryOutput", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            primaryOutput.writeToNBT(toSend.getCompoundTag("primaryOutput"));
            if (secondaryOutput != null) secondaryOutput.writeToNBT(toSend.getCompoundTag("secondaryOutput"));
            toSend.setInteger("secondaryChance", secondaryChance);
            FMLInterModComms.sendMessage("ThermalExpansion", "SawmillRecipe", toSend);
        }

        public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput) {
            addSmelterRecipe(energy, primaryInput, secondaryInput, primaryOutput, null, 0);
        }

        public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput) {
            addSmelterRecipe(energy, primaryInput, secondaryInput, primaryOutput, secondaryOutput, 100);
        }

        public static void addSmelterRecipe(int energy, ItemStack primaryInput, ItemStack secondaryInput, ItemStack primaryOutput, ItemStack secondaryOutput, int secondaryChance) {
            if (primaryInput == null || secondaryInput == null || primaryOutput == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("primaryInput", new NBTTagCompound());
            toSend.setTag("secondaryInput", new NBTTagCompound());
            toSend.setTag("primaryOutput", new NBTTagCompound());
            toSend.setTag("secondaryOutput", new NBTTagCompound());
            primaryInput.writeToNBT(toSend.getCompoundTag("primaryInput"));
            secondaryInput.writeToNBT(toSend.getCompoundTag("secondaryInput"));
            primaryOutput.writeToNBT(toSend.getCompoundTag("primaryOutput"));
            if (secondaryOutput != null) secondaryOutput.writeToNBT(toSend.getCompoundTag("secondaryOutput"));
            toSend.setInteger("secondaryChance", secondaryChance);
            FMLInterModComms.sendMessage("ThermalExpansion", "SmelterRecipe", toSend);
        }

        public static void addSmelterBlastOre(Materials aMaterial) {
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setString("oreType", aMaterial.toString());
            FMLInterModComms.sendMessage("ThermalExpansion", "SmelterBlastOreType", toSend);
        }

        public static void addCrucibleRecipe(int energy, ItemStack input, FluidStack output) {
            if (input == null || output == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("output", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            output.writeToNBT(toSend.getCompoundTag("output"));
            FMLInterModComms.sendMessage("ThermalExpansion", "CrucibleRecipe", toSend);
        }

        public static void addTransposerFill(int energy, ItemStack input, ItemStack output, FluidStack fluid, boolean reversible) {
            if (input == null || output == null || fluid == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("output", new NBTTagCompound());
            toSend.setTag("fluid", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            output.writeToNBT(toSend.getCompoundTag("output"));
            toSend.setBoolean("reversible", reversible);
            fluid.writeToNBT(toSend.getCompoundTag("fluid"));
            FMLInterModComms.sendMessage("ThermalExpansion", "TransposerFillRecipe", toSend);
        }

        public static void addTransposerExtract(int energy, ItemStack input, ItemStack output, FluidStack fluid, int chance, boolean reversible) {
            if (input == null || output == null || fluid == null) return;
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setInteger("energy", energy);
            toSend.setTag("input", new NBTTagCompound());
            toSend.setTag("output", new NBTTagCompound());
            toSend.setTag("fluid", new NBTTagCompound());
            input.writeToNBT(toSend.getCompoundTag("input"));
            output.writeToNBT(toSend.getCompoundTag("output"));
            toSend.setBoolean("reversible", reversible);
            toSend.setInteger("chance", chance);
            fluid.writeToNBT(toSend.getCompoundTag("fluid"));
            FMLInterModComms.sendMessage("ThermalExpansion", "TransposerExtractRecipe", toSend);
        }

        public static void addMagmaticFuel(String fluidName, int energy) {
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setString("fluidName", fluidName);
            toSend.setInteger("energy", energy);
            FMLInterModComms.sendMessage("ThermalExpansion", "MagmaticFuel", toSend);
        }

        public static void addCompressionFuel(String fluidName, int energy) {
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setString("fluidName", fluidName);
            toSend.setInteger("energy", energy);
            FMLInterModComms.sendMessage("ThermalExpansion", "CompressionFuel", toSend);
        }

        public static void addCoolant(String fluidName, int energy) {
            NBTTagCompound toSend = new NBTTagCompound();
            toSend.setString("fluidName", fluidName);
            toSend.setInteger("energy", energy);
            FMLInterModComms.sendMessage("ThermalExpansion", "Coolant", toSend);
        }
    }
}