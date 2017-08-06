package gregtech.common.items;

import gregtech.api.items.ItemList;
import gregtech.api.material.Materials;
import gregtech.api.material.OrePrefixes;
import gregtech.api.items.GenericItem;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class GT_IntegratedCircuit_Item extends GenericItem {
    public GT_IntegratedCircuit_Item() {
        super("integrated_circuit", "Integrated Circuit", "");
        setHasSubtypes(true);
        setMaxDamage(0);

        ItemList.Circuit_Integrated.set(this);


        GT_ModHandler.addShapelessCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 0L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{OrePrefixes.circuit.get(Materials.Basic)});

        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"d  ", " P ", "   ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 2L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" d ", " P ", "   ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 3L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  d", " P ", "   ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 4L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"   ", " Pd", "   ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 5L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"   ", " P ", "  d", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 6L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"   ", " P ", " d ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 7L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"   ", " P ", "d  ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 8L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"   ", "dP ", "   ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});

        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 9L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"P d", "   ", "   ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 10L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"P  ", "  d", "   ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 11L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"P  ", "   ", "  d", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 12L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"P  ", "   ", " d ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 13L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  P", "   ", "  d", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 14L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  P", "   ", " d ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 15L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  P", "   ", "d  ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 16L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  P", "d  ", "   ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 17L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"   ", "   ", "d P", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 18L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"   ", "d  ", "  P", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 19L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"d  ", "   ", "  P", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 20L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" d ", "   ", "  P", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 21L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"d  ", "   ", "P  ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 22L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" d ", "   ", "P  ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 23L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  d", "   ", "P  ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
        GT_ModHandler.addCraftingRecipe(ItemList.Circuit_Integrated.getWithDamage(1L, 24L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"   ", "  d", "P  ", 'P', ItemList.Circuit_Integrated.getWildcard(1L)});
    }

    private static String getModeString(int aMetaData) {
        switch ((byte) (aMetaData >>> 8)) {
            case 0:
                return "==";
            case 1:
                return "<=";
            case 2:
                return ">=";
            case 3:
                return "<";
            case 4:
                return ">";
        }
        return "";
    }

    private static String getConfigurationString(int aMetaData) {
        return getModeString(aMetaData) + " " + (byte) (aMetaData & 0xFF);
    }

    @Override
    public void addAdditionalToolTips(List<String> aList, ItemStack aStack, EntityPlayer aPlayer) {
        super.addAdditionalToolTips(aList, aStack, aPlayer);
        aList.add(GT_LanguageManager.addStringLocalization(new StringBuilder().append(getUnlocalizedName()).append(".configuration").toString(), "Configuration: ") + getConfigurationString(getDamage(aStack)));
    }

    @Override
    public String getUnlocalizedName(ItemStack aStack) {
        return getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public final void getSubItems(Item var1, CreativeTabs aCreativeTab, List<ItemStack> aList) {
        aList.add(new ItemStack(this, 1, 0));
    }

}