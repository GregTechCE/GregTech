package gregtech.common.gui.widget;

import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ScrollableListWidget;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ItemStackKey;
import gregtech.common.inventory.IItemInfo;
import gregtech.common.inventory.IItemList;
import gregtech.common.inventory.SimpleItemInfo;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

public class ItemListGridWidget extends ScrollableListWidget {

    @Nullable
    private final IItemList itemList;
    private final int slotAmountX;
    private final int slotAmountY;
    private int slotRowsAmount = 0;
    private Map<ItemStackKey, SimpleItemInfo> cachedItemList = new HashMap<>();
    private List<SimpleItemInfo> itemsChanged = new ArrayList<>();
    private List<ItemStackKey> itemsRemoved = new ArrayList<>();

    private Comparator<IItemInfo> comparator = Comparator.comparing(it -> it.getItemStackKey().getItemStackRaw(), GTUtility.createItemStackComparator());
    private List<SimpleItemInfo> displayItemList = new ArrayList<>();

    public ItemListGridWidget(int x, int y, int slotsX, int slotsY, @Nullable IItemList itemList) {
        super(x, y, slotsX * 18 + 10, slotsY * 18);
        this.itemList = itemList;
        this.slotAmountX = slotsX;
        this.slotAmountY = slotsY;
    }

    @Nullable
    public IItemInfo getItemInfoAt(int index) {
        return displayItemList.size() > index ? displayItemList.get(index) : null;
    }

    private void modifySlotRows(int delta) {
        for (int i = 0; i < delta * 9; i++) {
            if (delta > 0) {
                int widgetAmount = widgets.size();
                int yOffset = (widgetAmount / 9) * 18;
                Widget slotWidget = new ItemListSlotWidget(i * 18, yOffset, this, widgetAmount);
                addWidget(slotWidget);
            } else {
                Widget slotWidget = widgets.remove(widgets.size() - 1);
                removeWidget(slotWidget);
            }
        }
    }

    @Override
    public void addWidget(Widget widget) {
        super.addWidget(widget);
        if (!(widget instanceof ItemListSlotWidget)) {
            throw new UnsupportedOperationException();
        }
    }

    private void checkItemListForChanges() {
        Iterator<ItemStackKey> iterator = cachedItemList.keySet().iterator();
        while (iterator.hasNext()) {
            ItemStackKey itemStack = iterator.next();
            if (!itemList.hasItemStored(itemStack)) {
                iterator.remove();
                itemsRemoved.add(itemStack);
            }
        }
        for (ItemStackKey itemStack : itemList.getStoredItems()) {
            IItemInfo itemInfo = itemList.getItemInfo(itemStack);
            if (!cachedItemList.containsKey(itemStack)) {
                SimpleItemInfo lookupInfo = new SimpleItemInfo(itemStack);
                lookupInfo.setTotalItemAmount(itemInfo.getTotalItemAmount());
                cachedItemList.put(itemStack, lookupInfo);
                itemsChanged.add(lookupInfo);
            } else {
                SimpleItemInfo cachedItemInfo = cachedItemList.get(itemStack);
                if (cachedItemInfo.getTotalItemAmount() != itemInfo.getTotalItemAmount()) {
                    cachedItemInfo.setTotalItemAmount(itemInfo.getTotalItemAmount());
                    itemsChanged.add(cachedItemInfo);
                }
            }
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (itemList == null) return;
        int amountOfItemTypes = itemList.getStoredItems().size();
        int slotRowsRequired = Math.max(slotAmountY, (int) Math.ceil(amountOfItemTypes / (slotAmountX * 1.0)) * slotAmountX);
        if (slotRowsAmount != slotRowsRequired) {
            this.slotRowsAmount = slotRowsRequired;
            int slotsToAdd = slotRowsRequired - slotRowsAmount;
            writeUpdateInfo(1, buf -> buf.writeVarInt(slotsToAdd));
            modifySlotRows(slotsToAdd);
        }

        this.itemsChanged.clear();
        this.itemsRemoved.clear();
        checkItemListForChanges();
        if (!itemsChanged.isEmpty() || !itemsRemoved.isEmpty()) {
            writeUpdateInfo(2, buf -> {
                buf.writeVarInt(itemsRemoved.size());
                for (ItemStackKey itemStackKey : itemsRemoved) {
                    buf.writeItemStack(itemStackKey.getItemStackRaw());
                }
                buf.writeVarInt(itemsChanged.size());
                for (SimpleItemInfo itemInfo : itemsChanged) {
                    buf.writeItemStack(itemInfo.getItemStackKey().getItemStackRaw());
                    buf.writeVarInt(itemInfo.getTotalItemAmount());
                }
            });
        }
    }

    @Override
    public void readUpdateInfo(int id, PacketBuffer buffer) {
        super.readUpdateInfo(id, buffer);
        if (id == 1) {
            int slotsToAdd = buffer.readVarInt();
            modifySlotRows(slotsToAdd);
        }
        if (id == 2) {
            try {
                int itemsRemoved = buffer.readVarInt();
                for (int i = 0; i < itemsRemoved; i++) {
                    ItemStackKey itemStack = new ItemStackKey(buffer.readItemStack());
                    this.displayItemList.removeIf(it -> it.getItemStackKey().equals(itemStack));
                }
                int itemsChanged = buffer.readVarInt();
                for (int i = 0; i < itemsChanged; i++) {
                    ItemStackKey itemStack = new ItemStackKey(buffer.readItemStack());
                    int newTotalAmount = buffer.readVarInt();
                    SimpleItemInfo itemInfo = displayItemList.stream().filter(it -> it.getItemStackKey().equals(itemStack)).findAny().orElse(null);
                    if (itemInfo == null) {
                        itemInfo = new SimpleItemInfo(itemStack);
                        this.displayItemList.add(itemInfo);
                    }
                    itemInfo.setTotalItemAmount(newTotalAmount);
                }
                this.displayItemList.sort(comparator);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
