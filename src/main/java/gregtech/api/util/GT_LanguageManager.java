package gregtech.api.util;


import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.translation.*;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import gregtech.api.GregTech_API;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static gregtech.api.enums.GT_Values.E;

@SideOnly(Side.CLIENT)
public class GT_LanguageManager implements IResourceManagerReloadListener {

    public static GT_LanguageManager INSTANCE = new GT_LanguageManager();
    private GT_LanguageManager() {}

    public static final HashMap<String, String>
            LOCALIZATION = new HashMap<>(),
            BUFFERMAP = new HashMap<>();

    public static Configuration sEnglishFile;

    public static String addStringLocalization(String aKey, String aEnglish) {
        return addStringLocalization(aKey, aEnglish, true);
    }

    public static String addStringLocalization(String aKey, String aEnglish, boolean aWriteIntoLangFile) {
        if (aKey == null)
            return E;
        if (aWriteIntoLangFile)
            aEnglish = writeToLangFile(aKey, aEnglish);
        LOCALIZATION.put(aKey, aEnglish);
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
        if(LOCALIZATION.containsKey(aKey)) {
            return LOCALIZATION.get(aKey);
        }
        return I18n.format(aKey);
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

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        Locale i18nLocale = ObfuscationReflectionHelper.getPrivateValue(I18n.class, null, 0);
        Map<String, String> properties = ObfuscationReflectionHelper.getPrivateValue(Locale.class, i18nLocale, 2);

        LanguageMap languageMap = ObfuscationReflectionHelper.getPrivateValue(net.minecraft.util.text.translation.I18n.class, null, 0);
        Map<String, String> properties2 = ObfuscationReflectionHelper.getPrivateValue(LanguageMap.class, languageMap, 3);

        properties.putAll(LOCALIZATION);
        properties2.putAll(LOCALIZATION);
        GT_Log.out.println("Resource manager reloaded. Localization injected.");
    }

}