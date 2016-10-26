package gregtech.api.util;

import cpw.mods.fml.common.registry.LanguageRegistry;
import gregtech.api.GregTech_API;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.HashMap;
import java.util.Map.Entry;

import static gregtech.api.enums.GT_Values.E;

public class GT_LanguageManager {
    public static final HashMap<String, String> TEMPMAP = new HashMap<String, String>(), BUFFERMAP = new HashMap<String, String>(), LANGMAP = new HashMap<String, String>();
    public static Configuration sEnglishFile;
    public static boolean sUseEnglishFile = false;

    public static String addStringLocalization(String aKey, String aEnglish) {
        return addStringLocalization(aKey, aEnglish, true);
    }

    public static String addStringLocalization(String aKey, String aEnglish, boolean aWriteIntoLangFile) {
        if (aKey == null) return E;
        if (aWriteIntoLangFile){ aEnglish = writeToLangFile(aKey, aEnglish);
        if(!LANGMAP.containsKey(aKey)){
        	LANGMAP.put(aKey, aEnglish);
        	}
        }
        TEMPMAP.put(aKey.trim(), aEnglish);
        LanguageRegistry.instance().injectLanguage("en_US", TEMPMAP);
        TEMPMAP.clear();
        if(sUseEnglishFile && !aWriteIntoLangFile && LANGMAP.containsKey(aKey)){
        	aEnglish = LANGMAP.get(aKey);
        }
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
            if (sEnglishFile.get("EnableLangFile", "UseThisFileAsLanguageFile", false).getBoolean(false)){
                aEnglish = tProperty.getString();
                sUseEnglishFile = true;
            }
        }
        return aEnglish;
    }

    public static String getTranslation(String aKey) {
        if (aKey == null) return E;
        String tTrimmedKey = aKey.trim(), rTranslation = LanguageRegistry.instance().getStringLocalization(tTrimmedKey);
        if (GT_Utility.isStringInvalid(rTranslation)) {
            rTranslation = StatCollector.translateToLocal(tTrimmedKey);
            if (GT_Utility.isStringInvalid(rTranslation) || tTrimmedKey.equals(rTranslation)) {
                if (aKey.endsWith(".name")) {
                    rTranslation = StatCollector.translateToLocal(tTrimmedKey.substring(0, tTrimmedKey.length() - 5));
                    if (GT_Utility.isStringInvalid(rTranslation) || tTrimmedKey.substring(0, tTrimmedKey.length() - 5).equals(rTranslation)) {
                        return aKey;
                    }
                } else {
                    rTranslation = StatCollector.translateToLocal(tTrimmedKey + ".name");
                    if (GT_Utility.isStringInvalid(rTranslation) || (tTrimmedKey + ".name").equals(rTranslation)) {
                        return aKey;
                    }
                }
            }
        }
        return rTranslation;
    }

    public static String getTranslation(String aKey, String aSeperator) {
        if (aKey == null) return E;
        String rTranslation = E;
        StringBuilder rTranslationSB = new StringBuilder(rTranslation);
        for (String tString : aKey.split(aSeperator)) {
            rTranslationSB.append(getTranslation(tString));
        }
        rTranslation = String.valueOf(rTranslationSB);
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