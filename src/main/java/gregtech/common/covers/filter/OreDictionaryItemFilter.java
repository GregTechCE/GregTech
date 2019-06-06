package gregtech.common.covers.filter;

import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.LabelWidget;
import gregtech.api.gui.widgets.TextFieldWidget;
import gregtech.api.unification.OreDictUnifier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public class OreDictionaryItemFilter extends AbstractItemFilter {

    private static final Pattern ORE_DICTIONARY_FILTER = Pattern.compile("\\*?[a-zA-Z0-9_]*\\*?");

    protected String oreDictionaryFilter = "";

    protected void setOreDictionaryFilter(String oreDictionaryFilter) {
        this.oreDictionaryFilter = oreDictionaryFilter;
        markDirty();
    }

    public String getOreDictionaryFilter() {
        return oreDictionaryFilter;
    }

    @Override
    public int getTotalOccupiedHeight() {
        return 37;
    }

    @Override
    public void initUI(int y, Consumer<Widget> widgetGroup) {
        widgetGroup.accept(new LabelWidget(10, y, "cover.ore_dictionary_filter.title1"));
        widgetGroup.accept(new LabelWidget(10, y + 10, "cover.ore_dictionary_filter.title2"));
        widgetGroup.accept(new TextFieldWidget(10, y + 25, 100, 12, true,
            () -> oreDictionaryFilter, this::setOreDictionaryFilter)
            .setMaxStringLength(64)
            .setValidator(str -> ORE_DICTIONARY_FILTER.matcher(str).matches()));
    }

    @Override
    public boolean testItemStack(ItemStack itemStack) {
        return matchesOreDictionaryFilter(getOreDictionaryFilter(), itemStack);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setString("OreDictionaryFilter", oreDictionaryFilter);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        this.oreDictionaryFilter = tagCompound.getString("OreDictionaryFilter");
    }

    public static boolean matchesOreDictionaryFilter(String oreDictionaryFilter, ItemStack itemStack) {
        if (oreDictionaryFilter.isEmpty()) {
            return false;
        }
        boolean startWildcard = oreDictionaryFilter.charAt(0) == '*';
        boolean endWildcard = oreDictionaryFilter.length() > 1 && oreDictionaryFilter.charAt(oreDictionaryFilter.length() - 1) == '*';
        if (startWildcard) {
            oreDictionaryFilter = oreDictionaryFilter.substring(1);
        }
        if (endWildcard) {
            oreDictionaryFilter = oreDictionaryFilter.substring(0, oreDictionaryFilter.length() - 1);
        }
        for (String stackOreName : OreDictUnifier.getOreDictionaryNames(itemStack)) {
            if (areOreDictNamesEqual(startWildcard, endWildcard, oreDictionaryFilter, stackOreName)) {
                return true;
            }
        }
        return false;
    }

    private static boolean areOreDictNamesEqual(boolean startWildcard, boolean endWildcard, String oreDictName, String stackOreName) {
        if (startWildcard && endWildcard) {
            return stackOreName.contains(oreDictName);
        } else if (startWildcard) {
            return stackOreName.endsWith(oreDictName);
        } else if (endWildcard) {
            return stackOreName.startsWith(oreDictName);
        } else {
            return stackOreName.equals(oreDictName);
        }
    }
}
