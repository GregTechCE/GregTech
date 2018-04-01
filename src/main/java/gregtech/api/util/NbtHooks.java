package gregtech.api.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

public class NbtHooks {

    public static void setPunchCardData(ItemStack stack, String aPunchCardData) {
        NBTTagCompound compound = stack.getTagCompound();
        compound.setString("GT.PunchCardData", aPunchCardData);
        stack.setTagCompound(compound);
    }

    public static String getPunchCardData(ItemStack stack) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack);
        return compound.getString("GT.PunchCardData");
    }

    public static void setMapID(ItemStack stack, short aMapID) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack);
        compound.setShort("map_id", aMapID);
        stack.setTagCompound(compound);
    }

    public static short getMapID(ItemStack stack) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack);
        if (!compound.hasKey("map_id")) return -1;
        return compound.getShort("map_id");
    }

    public static void setBookTitle(ItemStack stack, String aTitle) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack);
        compound.setString("title", aTitle);
        stack.setTagCompound(compound);
    }

    public static String getBookTitle(ItemStack stack) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack);
        return compound.getString("title");
    }

    public static void setBookAuthor(ItemStack stack, String aAuthor) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack);
        compound.setString("author", aAuthor);
        stack.setTagCompound(compound);
    }

    public static String getBookAuthor(ItemStack stack) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack);
        return compound.getString("author");
    }

    public static void addEnchantment(ItemStack stack, Enchantment enchantment, int level) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack), tEnchantmentTag;
        if (!compound.hasKey("ench", 9)) compound.setTag("ench", new NBTTagList());
        NBTTagList tagList = compound.getTagList("ench", 10);

        boolean temp = true;

        for (int i = 0; i < tagList.tagCount(); i++) {
            tEnchantmentTag = tagList.getCompoundTagAt(i);
            if (tEnchantmentTag.getShort("id") == Enchantment.getEnchantmentID(enchantment)) {
                tEnchantmentTag.setShort("id", (short) Enchantment.getEnchantmentID(enchantment));
                tEnchantmentTag.setShort("lvl", (byte) level);
                temp = false;
                break;
            }
        }

        if (temp) {
            tEnchantmentTag = new NBTTagCompound();
            tEnchantmentTag.setShort("id", (short) Enchantment.getEnchantmentID(enchantment));
            tEnchantmentTag.setShort("lvl", (byte) level);
            tagList.appendTag(tEnchantmentTag);
        }
        stack.setTagCompound(compound);
    }
}
