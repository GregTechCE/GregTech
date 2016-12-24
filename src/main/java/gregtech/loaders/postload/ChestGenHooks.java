package gregtech.loaders.postload;

import com.google.common.collect.Lists;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ChestGenHooks {

    private static final HashMap<ResourceLocation, ArrayList<LootEntryItem>> lootEntryItems = new HashMap<>();
    private static final HashMap<ResourceLocation, RandomValueRange> rollVals = new HashMap<>();


    private static final LootCondition[] NO_CONDITIONS = new LootCondition[0];

    public ChestGenHooks() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onWorldLoad(LootTableLoadEvent event) {
        LootPool mainPool = event.getTable().getPool("main");
        if(mainPool != null && lootEntryItems.containsKey(event.getName())) {
            ArrayList<LootEntryItem> entryItems = lootEntryItems.get(event.getName());
            for(LootEntryItem entry : entryItems) {
                mainPool.addEntry(entry);
            }
        }
        if(mainPool != null && rollVals.containsKey(event.getName())) {
            RandomValueRange rangeAdd = rollVals.get(event.getName());
            RandomValueRange range = mainPool.getRolls();
            mainPool.setRolls(new RandomValueRange(range.getMin() + rangeAdd.getMin(), range.getMax() + rangeAdd.getMax()));
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
        }, NO_CONDITIONS, "#loot_" + GT_Utility.stackToIntHash(item));
        if(lootEntryItems.containsKey(loottable)) {
            lootEntryItems.get(loottable).add(itemEntry);
        } else {
            lootEntryItems.put(loottable, Lists.newArrayList(itemEntry));
        }
    }

    public static void addRolls(ResourceLocation tableLocation, int minAdd, int maxAdd) {
        rollVals.put(tableLocation, new RandomValueRange(minAdd, maxAdd));
    }






}
