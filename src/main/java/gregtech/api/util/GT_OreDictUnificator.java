package gregtech.api.util;

import gregtech.api.GregTech_API;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.objects.GT_HashSet;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import static gregtech.api.enums.GT_Values.*;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * This is the Core of my OreDict Unification Code
 * <p/>
 * If you just want to use this to unificate your Items, then use the Function in the GregTech_API File
 * <p/>
 * P.S. It is intended to be named "Unificator" and not "Unifier", because that sounds more awesome.
 */
public class GT_OreDictUnificator {
    private static final HashMap<String, ItemStack> sName2StackMap = new HashMap<String, ItemStack>();
    private static final HashMap<GT_ItemStack, ItemData> sItemStack2DataMap = new HashMap<GT_ItemStack, ItemData>();
    private static final GT_HashSet<GT_ItemStack> sNoUnificationList = new GT_HashSet<GT_ItemStack>();
    public static volatile int VERSION = 509;
    private static int isRegisteringOre = 0, isAddingOre = 0;
    private static boolean mRunThroughTheList = true;

    static {
        GregTech_API.sItemStackMappings.add(sItemStack2DataMap);
    }

    /**
     * The Blacklist just prevents the Item from being unificated into something else.
     * Useful if you have things like the Industrial Diamond, which is better than regular Diamond, but also usable in absolutely all Diamond Recipes.
     */
    public static void addToBlacklist(ItemStack aStack) {
        if (GT_Utility.isStackValid(aStack) && !GT_Utility.isStackInList(aStack, sNoUnificationList))
            sNoUnificationList.add(aStack);
    }

    public static boolean isBlacklisted(ItemStack aStack) {
        return GT_Utility.isStackInList(aStack, sNoUnificationList);
    }

    public static void add(OrePrefixes aPrefix, Materials aMaterial, ItemStack aStack) {
        set(aPrefix, aMaterial, aStack, false, false);
    }

    public static void set(OrePrefixes aPrefix, Materials aMaterial, ItemStack aStack) {
        set(aPrefix, aMaterial, aStack, true, false);
    }

    public static void set(OrePrefixes aPrefix, Materials aMaterial, ItemStack aStack, boolean aOverwrite, boolean aAlreadyRegistered) {
        if (aMaterial == null || aPrefix == null || GT_Utility.isStackInvalid(aStack) || Items.feather.getDamage(aStack) == W)
            return;
        isAddingOre++;
        aStack = GT_Utility.copyAmount(1, aStack);
        if (!aAlreadyRegistered) registerOre(aPrefix.get(aMaterial), aStack);
        addAssociation(aPrefix, aMaterial, aStack, isBlacklisted(aStack));
        if (aOverwrite || GT_Utility.isStackInvalid(sName2StackMap.get(aPrefix.get(aMaterial).toString())))
            sName2StackMap.put(aPrefix.get(aMaterial).toString(), aStack);
        isAddingOre--;
    }

    public static ItemStack getFirstOre(Object aName, long aAmount) {
        if (GT_Utility.isStringInvalid(aName)) return null;
        ItemStack tStack = sName2StackMap.get(aName.toString());
        if (GT_Utility.isStackValid(tStack)) return GT_Utility.copyAmount(aAmount, tStack);
        return GT_Utility.copyAmount(aAmount, getOres(aName).toArray());
    }

    public static ItemStack get(Object aName, long aAmount) {
        return get(aName, null, aAmount, true, true);
    }

    public static ItemStack get(Object aName, ItemStack aReplacement, long aAmount) {
        return get(aName, aReplacement, aAmount, true, true);
    }

    public static ItemStack get(OrePrefixes aPrefix, Object aMaterial, long aAmount) {
        return get(aPrefix, aMaterial, null, aAmount);
    }

    public static ItemStack get(OrePrefixes aPrefix, Object aMaterial, ItemStack aReplacement, long aAmount) {
        return get(aPrefix.get(aMaterial), aReplacement, aAmount, false, true);
    }

    public static ItemStack get(Object aName, ItemStack aReplacement, long aAmount, boolean aMentionPossibleTypos, boolean aNoInvalidAmounts) {
        if (aNoInvalidAmounts && aAmount < 1) return null;
        if (!sName2StackMap.containsKey(aName.toString()) && aMentionPossibleTypos)
            GT_Log.err.println("Unknown Key for Unification, Typo? " + aName);
        return GT_Utility.copyAmount(aAmount, sName2StackMap.get(aName.toString()), getFirstOre(aName, aAmount), aReplacement);
    }

    public static ItemStack[] setStackArray(boolean aUseBlackList, ItemStack... aStacks) {
        for (int i = 0; i < aStacks.length; i++) aStacks[i] = get(aUseBlackList, GT_Utility.copy(aStacks[i]));
        return aStacks;
    }

    public static ItemStack[] getStackArray(boolean aUseBlackList, Object... aStacks) {
        ItemStack[] rStacks = new ItemStack[aStacks.length];
        for (int i = 0; i < aStacks.length; i++) rStacks[i] = get(aUseBlackList, GT_Utility.copy(aStacks[i]));
        return rStacks;
    }

    public static ItemStack setStack(ItemStack aStack) {
        return setStack(true, aStack);
    }

    public static ItemStack setStack(boolean aUseBlackList, ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack)) return aStack;
        ItemStack tStack = get(aUseBlackList, aStack);
        if (GT_Utility.areStacksEqual(aStack, tStack)) return aStack;
        aStack.func_150996_a(tStack.getItem());
        Items.feather.setDamage(aStack, Items.feather.getDamage(tStack));
        return aStack;
    }

    public static ItemStack get(ItemStack aStack) {
        return get(true, aStack);
    }

    public static ItemStack get(boolean aUseBlackList, ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack)) return null;
        ItemData tPrefixMaterial = getAssociation(aStack);
        ItemStack rStack = null;
        if (tPrefixMaterial == null || !tPrefixMaterial.hasValidPrefixMaterialData() || (aUseBlackList && tPrefixMaterial.mBlackListed))
            return GT_Utility.copy(aStack);
        if (aUseBlackList && !GregTech_API.sUnificationEntriesRegistered && isBlacklisted(aStack)) {
            tPrefixMaterial.mBlackListed = true;
            return GT_Utility.copy(aStack);
        }
        if (tPrefixMaterial.mUnificationTarget == null)
            tPrefixMaterial.mUnificationTarget = sName2StackMap.get(tPrefixMaterial.toString());
        rStack = tPrefixMaterial.mUnificationTarget;
        if (GT_Utility.isStackInvalid(rStack)) return GT_Utility.copy(aStack);
        assert rStack != null;
        rStack.setTagCompound(aStack.getTagCompound());
        return GT_Utility.copyAmount(aStack.stackSize, rStack);
    }

    public static void addItemData(ItemStack aStack, ItemData aData) {
        if (GT_Utility.isStackValid(aStack) && getItemData(aStack) == null && aData != null) setItemData(aStack, aData);
    }

    public static void setItemData(ItemStack aStack, ItemData aData) {
        if (GT_Utility.isStackInvalid(aStack) || aData == null) return;
        ItemData tData = getItemData(aStack);
        if (tData == null || !tData.hasValidPrefixMaterialData()) {
            if (tData != null) for (Object tObject : tData.mExtraData)
                if (!aData.mExtraData.contains(tObject)) aData.mExtraData.add(tObject);
            if (aStack.stackSize > 1) {
                if (aData.mMaterial != null) aData.mMaterial.mAmount /= aStack.stackSize;
                for (MaterialStack tMaterial : aData.mByProducts) tMaterial.mAmount /= aStack.stackSize;
                aStack = GT_Utility.copyAmount(1, aStack);
            }
            sItemStack2DataMap.put(new GT_ItemStack(aStack), aData);
            if (aData.hasValidMaterialData()) {
                long tValidMaterialAmount = aData.mMaterial.mMaterial.contains(SubTag.NO_RECYCLING) ? 0 : aData.mMaterial.mAmount >= 0 ? aData.mMaterial.mAmount : M;
                for (MaterialStack tMaterial : aData.mByProducts)
                    tValidMaterialAmount += tMaterial.mMaterial.contains(SubTag.NO_RECYCLING) ? 0 : tMaterial.mAmount >= 0 ? tMaterial.mAmount : M;
                if (tValidMaterialAmount < M) GT_ModHandler.addToRecyclerBlackList(aStack);
            }
            if (mRunThroughTheList) {
                if (GregTech_API.sLoadStarted) {
                    mRunThroughTheList = false;
                    for (Entry<GT_ItemStack, ItemData> tEntry : sItemStack2DataMap.entrySet())
                        if (!tEntry.getValue().hasValidPrefixData() || tEntry.getValue().mPrefix.mAllowNormalRecycling)
                            GT_RecipeRegistrator.registerMaterialRecycling(tEntry.getKey().toStack(), tEntry.getValue());
                }
            } else {
                if (!aData.hasValidPrefixData() || aData.mPrefix.mAllowNormalRecycling)
                    GT_RecipeRegistrator.registerMaterialRecycling(aStack, aData);
            }
        } else {
            for (Object tObject : aData.mExtraData)
                if (!tData.mExtraData.contains(tObject)) tData.mExtraData.add(tObject);
        }
    }

    public static void addAssociation(OrePrefixes aPrefix, Materials aMaterial, ItemStack aStack, boolean aBlackListed) {
        if (aPrefix == null || aMaterial == null || GT_Utility.isStackInvalid(aStack)) return;
        if (Items.feather.getDamage(aStack) == W) for (byte i = 0; i < 16; i++)
            setItemData(GT_Utility.copyAmountAndMetaData(1, i, aStack), new ItemData(aPrefix, aMaterial, aBlackListed));
        setItemData(aStack, new ItemData(aPrefix, aMaterial, aBlackListed));
    }

    public static ItemData getItemData(ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack)) return null;
        ItemData rData = sItemStack2DataMap.get(new GT_ItemStack(aStack));
        if (rData == null) rData = sItemStack2DataMap.get(new GT_ItemStack(GT_Utility.copyMetaData(W, aStack)));
        return rData;
    }

    public static ItemData getAssociation(ItemStack aStack) {
        ItemData rData = getItemData(aStack);
        return rData != null && rData.hasValidPrefixMaterialData() ? rData : null;
    }

    public static boolean isItemStackInstanceOf(ItemStack aStack, Object aName) {
        if (GT_Utility.isStringInvalid(aName) || GT_Utility.isStackInvalid(aStack)) return false;
        for (ItemStack tOreStack : getOres(aName.toString()))
            if (GT_Utility.areStacksEqual(tOreStack, aStack, true)) return true;
        return false;
    }

    public static boolean isItemStackDye(ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack)) return false;
        for (Dyes tDye : Dyes.VALUES) if (isItemStackInstanceOf(aStack, tDye.toString())) return true;
        return false;
    }

    public static boolean registerOre(OrePrefixes aPrefix, Object aMaterial, ItemStack aStack) {
        return registerOre(aPrefix.get(aMaterial), aStack);
    }

    public static boolean registerOre(Object aName, ItemStack aStack) {
        if (aName == null || GT_Utility.isStackInvalid(aStack)) return false;
        String tName = aName.toString();
        if (GT_Utility.isStringInvalid(tName)) return false;
        ArrayList<ItemStack> tList = getOres(tName);
        for (int i = 0; i < tList.size(); i++) if (GT_Utility.areStacksEqual(tList.get(i), aStack, true)) return false;
        isRegisteringOre++;
        OreDictionary.registerOre(tName, GT_Utility.copyAmount(1, aStack));
        isRegisteringOre--;
        return true;
    }

    public static boolean isRegisteringOres() {
        return isRegisteringOre > 0;
    }

    public static boolean isAddingOres() {
        return isAddingOre > 0;
    }

    public static void resetUnificationEntries() {
        for (ItemData tPrefixMaterial : sItemStack2DataMap.values()) tPrefixMaterial.mUnificationTarget = null;
    }

    public static ItemStack getGem(MaterialStack aMaterial) {
        return aMaterial == null ? null : getGem(aMaterial.mMaterial, aMaterial.mAmount);
    }

    public static ItemStack getGem(Materials aMaterial, OrePrefixes aPrefix) {
        return aMaterial == null ? null : getGem(aMaterial, aPrefix.mMaterialAmount);
    }

    public static ItemStack getGem(Materials aMaterial, long aMaterialAmount) {
        ItemStack rStack = null;
        if (((aMaterialAmount >= M) || aMaterialAmount >= M * 32))
            rStack = get(OrePrefixes.gem, aMaterial, aMaterialAmount / M);
        if (rStack == null && (((aMaterialAmount * 2) % M == 0) || aMaterialAmount >= M * 16))
            rStack = get(OrePrefixes.gemFlawed, aMaterial, (aMaterialAmount * 2) / M);
        if (rStack == null && (((aMaterialAmount * 4) >= M)))
            rStack = get(OrePrefixes.gemChipped, aMaterial, (aMaterialAmount * 4) / M);
        return rStack;
    }

    public static ItemStack getDust(MaterialStack aMaterial) {
        return aMaterial == null ? null : getDust(aMaterial.mMaterial, aMaterial.mAmount);
    }

    public static ItemStack getDust(Materials aMaterial, OrePrefixes aPrefix) {
        return aMaterial == null ? null : getDust(aMaterial, aPrefix.mMaterialAmount);
    }

    public static ItemStack getDust(Materials aMaterial, long aMaterialAmount) {
        if (aMaterialAmount <= 0) return null;
        ItemStack rStack = null;
        if (((aMaterialAmount % M == 0) || aMaterialAmount >= M * 16))
            rStack = get(OrePrefixes.dust, aMaterial, aMaterialAmount / M);
        if (rStack == null && (((aMaterialAmount * 4) % M == 0) || aMaterialAmount >= M * 8))
            rStack = get(OrePrefixes.dustSmall, aMaterial, (aMaterialAmount * 4) / M);
        if (rStack == null && (((aMaterialAmount * 9) >= M)))
            rStack = get(OrePrefixes.dustTiny, aMaterial, (aMaterialAmount * 9) / M);
        return rStack;
    }

    public static ItemStack getIngot(MaterialStack aMaterial) {
        return aMaterial == null ? null : getIngot(aMaterial.mMaterial, aMaterial.mAmount);
    }

    public static ItemStack getIngot(Materials aMaterial, OrePrefixes aPrefix) {
        return aMaterial == null ? null : getIngot(aMaterial, aPrefix.mMaterialAmount);
    }

    public static ItemStack getIngot(Materials aMaterial, long aMaterialAmount) {
        if (aMaterialAmount <= 0) return null;
        ItemStack rStack = null;
        if (((aMaterialAmount % (M * 9) == 0 && aMaterialAmount / (M * 9) > 1) || aMaterialAmount >= M * 72))
            rStack = get(OrePrefixes.block, aMaterial, aMaterialAmount / (M * 9));
        if (rStack == null && ((aMaterialAmount % M == 0) || aMaterialAmount >= M * 8))
            rStack = get(OrePrefixes.ingot, aMaterial, aMaterialAmount / M);
        if (rStack == null && (((aMaterialAmount * 9) >= M)))
            rStack = get(OrePrefixes.nugget, aMaterial, (aMaterialAmount * 9) / M);
        return rStack;
    }

    public static ItemStack getIngotOrDust(Materials aMaterial, long aMaterialAmount) {
        if (aMaterialAmount <= 0) return null;
        ItemStack rStack = getIngot(aMaterial, aMaterialAmount);
        if (rStack == null) rStack = getDust(aMaterial, aMaterialAmount);
        return rStack;
    }

    public static ItemStack getIngotOrDust(MaterialStack aMaterial) {
        ItemStack rStack = getIngot(aMaterial);
        if(aMaterial!=null&&aMaterial.mMaterial!=null&&(aMaterial.mMaterial==Materials.Naquadah||aMaterial.mMaterial==Materials.NaquadahEnriched))rStack = getDust(aMaterial);
        if (rStack == null) rStack = getDust(aMaterial);
        return rStack;
    }

    public static ItemStack getDustOrIngot(Materials aMaterial, long aMaterialAmount) {
        if (aMaterialAmount <= 0) return null;
        ItemStack rStack = getDust(aMaterial, aMaterialAmount);
        if (rStack == null) rStack = getIngot(aMaterial, aMaterialAmount);
        return rStack;
    }

    public static ItemStack getDustOrIngot(MaterialStack aMaterial) {
        ItemStack rStack = getDust(aMaterial);
        if (rStack == null) rStack = getIngot(aMaterial);
        return rStack;
    }

    /**
     * @return a Copy of the OreDictionary.getOres() List
     */
    public static ArrayList<ItemStack> getOres(OrePrefixes aPrefix, Object aMaterial) {
        return getOres(aPrefix.get(aMaterial));
    }

    /**
     * @return a Copy of the OreDictionary.getOres() List
     */
    public static ArrayList<ItemStack> getOres(Object aOreName) {
        String aName = aOreName == null ? E : aOreName.toString();
        ArrayList<ItemStack> rList = new ArrayList<ItemStack>();
        if (GT_Utility.isStringValid(aName)) rList.addAll(OreDictionary.getOres(aName));
        return rList;
    }
}
