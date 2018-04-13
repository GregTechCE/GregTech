package gregtech.common;

import java.util.function.Function;

import gregtech.api.GTValues;
import gregtech.api.block.machines.MachineItemBlock;
import gregtech.common.cable.ItemBlockCable;
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

        registry.register(MACHINE);

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

        CABLES.values().forEach(registry::register);
        COMPRESSED.values().stream().distinct().forEach(registry::register);
        ORES.forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        GTLog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();

        for (MetaItem<?> item : MetaItems.ITEMS) {
            registry.register(item);
            item.registerSubItems();
        }

        registry.register(createItemBlock(MACHINE, MachineItemBlock::new));
        registry.register(createItemBlock(BOILER_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(METAL_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(TURBINE_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(MACHINE_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(MUTLIBLOCK_CASING, VariantItemBlock::new));
        registry.register(createItemBlock(WIRE_COIL, VariantItemBlock::new));
        registry.register(createItemBlock(WARNING_SIGN, VariantItemBlock::new));
        registry.register(createItemBlock(GRANITE, StoneItemBlock::new));
        registry.register(createItemBlock(MINERAL, StoneItemBlock::new));
        registry.register(createItemBlock(CONCRETE, StoneItemBlock::new));

        CABLES.values().stream()
            .map(block -> createItemBlock(block, ItemBlockCable::new))
            .forEach(registry::register);
        COMPRESSED.values()
            .stream().distinct()
            .map(block -> createItemBlock(block, CompressedItemBlock::new))
            .forEach(registry::register);
        ORES.stream()
            .map(block -> createItemBlock(block, OreItemBlock::new))
            .forEach(registry::register);
    }

    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        ItemBlock itemBlock = producer.apply(block);
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