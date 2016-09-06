package gregtech.loaders.postload;

import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ChestGenHooks {

    private static final HashMap<ResourceLocation, ArrayList<LootEntryItem>> lootEntryItems = new HashMap<>();
    private static final LootCondition[] NO_CONDITIONS = new LootCondition[0];

    public ChestGenHooks() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load worldEvent) {
        LootTableManager tableManager = worldEvent.getWorld().getLootTableManager();
        LoadingCache<ResourceLocation, LootTable> lootTablesCache = ObfuscationReflectionHelper.getPrivateValue(LootTableManager.class, tableManager, 2);
        for(ResourceLocation tableLocation : lootTablesCache.asMap().keySet()) {
            if(lootEntryItems.containsKey(tableLocation)) {
                ArrayList<LootEntryItem> items = lootEntryItems.get(tableLocation);
                LootTable lootTable = lootTablesCache.getUnchecked(tableLocation);
                List<LootPool> pools = ObfuscationReflectionHelper.getPrivateValue(LootTable.class, lootTable, 2);
                pools.add(new LootPool(
                                items.toArray(new LootEntryItem[items.size()]), NO_CONDITIONS,
                                new RandomValueRange(items.size()),
                                new RandomValueRange(0.2F), "Gregtech Loot"));
            }
        }
    }

    public static void addItem(ResourceLocation loottable, ItemStack item, int minChance, int maxChance, int weight) {
        LootEntryItem itemEntry = new LootEntryItem(item.getItem(), weight, 1, new LootFunction[] {
                new LootFunction(NO_CONDITIONS) {
                    @Override
                    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
                        stack.setItemDamage(item.getItemDamage());
                        stack.stackSize = minChance + rand.nextInt(maxChance);
                        return stack;
                    }
                }
        }, NO_CONDITIONS, "Gregtech loot");
        if(lootEntryItems.containsKey(loottable)) {
            lootEntryItems.get(loottable).add(itemEntry);
        } else {
            lootEntryItems.put(loottable, Lists.newArrayList(itemEntry));
        }
    }






}
