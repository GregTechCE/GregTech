package gregtech.api.util;


import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import gregtech.api.GregTech_API;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static gregtech.api.enums.GT_Values.E;

public class GT_LanguageManager {

    public static final LanguageMap TRANSLATION = ObfuscationReflectionHelper.getPrivateValue(LanguageMap.class, null, 2);

    public static final HashMap<String, String> TEMPMAP = new HashMap<String, String>(), BUFFERMAP = new HashMap<String, String>();
    public static Configuration sEnglishFile;

    public static String addStringLocalization(String aKey, String aEnglish) {
        return addStringLocalization(aKey, aEnglish, true);
    }

    public static String addStringLocalization(String aKey, String aEnglish, boolean aWriteIntoLangFile) {
        if (aKey == null) return E;
        if (aWriteIntoLangFile) aEnglish = writeToLangFile(aKey, aEnglish);
        TEMPMAP.put(aKey.trim(), aEnglish);
        Map<String, String> translation = ObfuscationReflectionHelper.getPrivateValue(LanguageMap.class, TRANSLATION, 3);
        translation.put(aKey.trim(), aEnglish);
        return aEnglish;
    }

    private static synchronized String writeToLangFile(String aKey, String aEnglish) {
        if (aKey == null) return E;
        if (sEnglishFile == null) {
            BUFFERMAP.put(aKey.trim(), aEnglish);
        } else {
            if (!BUFFERMAP.isEmpty()) {
                for (Entry<String, String> tEntry : BUFFERMAP.entrySet()) {
                    Property tProperty = sEnglishFile.get("LanguageFile", tEntry.getKey(), tEntry.getValue());
                    if (!tProperty.wasRead() && GregTech_API.sPostloadFinished) sEnglishFile.save();
                }
                BUFFERMAP.clear();
            }
            Property tProperty = sEnglishFile.get("LanguageFile", aKey.trim(), aEnglish);
            if (!tProperty.wasRead() && GregTech_API.sPostloadFinished) sEnglishFile.save();
            if (sEnglishFile.get("EnableLangFile", "UseThisFileAsLanguageFile", false).getBoolean(false))
                aEnglish = tProperty.getString();
        }
        return aEnglish;
    }

    public static String getTranslation(String aKey) {
        if (aKey == null) return E;
        aKey = aKey.trim();
        String result = TRANSLATION.translateKey(aKey);
        if(result.equals(aKey) && !aKey.endsWith(".name")) {
            result = TRANSLATION.translateKey(aKey + ".name");
        }
        return result;
    }

    public static String getTranslation(String aKey, String aSeperator) {
        if (aKey == null) return E;
        String rTranslation = E;
        for (String tString : aKey.split(aSeperator)) {
            rTranslation += getTranslation(tString);
        }
        return rTranslation;
    }

    public static String getTranslateableItemStackName(ItemStack aStack) {
        if (GT_Utility.isStackInvalid(aStack)) return "null";
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT != null && tNBT.hasKey("display")) {
            String tName = tNBT.getCompoundTag("display").getString("Name");
            if (GT_Utility.isStringValid(tName)) {
                return tName;
            }
        }
        return aStack.getUnlocalizedName() + ".name";
    }

}