package gregtech.common;

import java.util.function.Supplier;

import gregtech.api.GTValues;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.enchants.EnchantmentEnderDamage;
import gregtech.api.enchants.EnchantmentRadioactivity;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.util.GTLog;
import gregtech.common.blocks.CompressedItemBlock;
import gregtech.common.blocks.OreItemBlock;
import gregtech.common.blocks.StoneItemBlock;
import gregtech.common.blocks.VariantItemBlock;
import gregtech.common.items.MetaItems;
import gregtech.loaders.postload.WorldgenLoader;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static gregtech.common.blocks.MetaBlocks.*;

@Mod.EventBusSubscriber(modid = GTValues.MODID)
public class CommonProxy {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        GTLog.logger.info("Registering Blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();

        for(BlockMachine<?> blockMachine : BlockMachine.MACHINES.keySet())
            registry.register(blockMachine);
        registry.register(CABLE);

        registry.register(BOILER_CASING);
        registry.register(METAL_CASING);
        registry.register(TURBINE_CASING);
        registry.register(MACHINE_CASING);
        registry.register(MUTLIBLOCK_CASING);
        registry.register(WIRE_COIL);
        registry.register(WARNING_SIGN);
        registry.register(GRANITE);
        registry.register(MINERAL);
        registry.register(CONCRETE);

        COMPRESSED.values().stream().distinct().forEach(registry::register);
        ORES.stream().distinct().forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        GTLog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();

        for (MetaItem<?> item : MetaItems.ITEMS) {
            registry.register(item);
            item.registerSubItems();
        }

        for(ItemBlock machineItemBlock : BlockMachine.MACHINES.values())
            registry.register(machineItemBlock);
        registry.register(createItemBlock(CABLE, () -> new ItemBlock(CABLE)));

        registry.register(createItemBlock(BOILER_CASING, () -> new VariantItemBlock<>(BOILER_CASING)));
        registry.register(createItemBlock(METAL_CASING, () -> new VariantItemBlock<>(METAL_CASING)));
        registry.register(createItemBlock(TURBINE_CASING, () -> new VariantItemBlock<>(TURBINE_CASING)));
        registry.register(createItemBlock(MACHINE_CASING, () -> new VariantItemBlock<>(MACHINE_CASING)));
        registry.register(createItemBlock(MUTLIBLOCK_CASING, () -> new VariantItemBlock<>(MUTLIBLOCK_CASING)));
        registry.register(createItemBlock(WIRE_COIL, () -> new VariantItemBlock<>(WIRE_COIL)));
        registry.register(createItemBlock(WARNING_SIGN, () -> new VariantItemBlock<>(WARNING_SIGN)));
        registry.register(createItemBlock(GRANITE, () -> new StoneItemBlock<>(GRANITE)));
        registry.register(createItemBlock(MINERAL, () -> new StoneItemBlock<>(MINERAL)));
        registry.register(createItemBlock(CONCRETE, () -> new StoneItemBlock<>(CONCRETE)));

        COMPRESSED.values()
            .stream()
            .distinct()
            .map(block -> createItemBlock(block, () -> new CompressedItemBlock(block)))
            .forEach(registry::register);
        ORES.stream()
            .distinct()
            .map(block -> createItemBlock(block, () -> new OreItemBlock(block)))
            .forEach(registry::register);
    }

    private static ItemBlock createItemBlock(Block block, Supplier<ItemBlock> producer) {
        ItemBlock itemBlock = producer.get();
        itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
    }

    @SubscribeEvent
    public void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        EnchantmentEnderDamage.INSTANCE.register(event);
        EnchantmentRadioactivity.INSTANCE.register(event);
    }   

    public void onPreLoad() {

    }

    public void onLoad() {

    }

    public void onPostLoad() {
        WorldgenLoader.init();
    }
}