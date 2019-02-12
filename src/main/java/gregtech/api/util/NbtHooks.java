package gregtech.api.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;

public class NbtHooks {

    public static void setLore(ItemStack stack, String... description) {
        NBTTagCompound compound = GTUtility.getOrCreateNbtCompound(stack);
        NBTTagCompound displayTag = compound.getCompoundTag("display");
        NBTTagList loreList = new NBTTagList();
        for(String descriptionLine : description) {
            loreList.appendTag(new NBTTagString(TextFormatting.WHITE + descriptionLine));
        }
        displayTag.setTag("Lore", loreList);
        compound.setTag("display", displayTag);
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
}
