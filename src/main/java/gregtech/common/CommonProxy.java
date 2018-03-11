package gregtech.common;

import java.util.function.Supplier;

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

@Mod.EventBusSubscriber
public class CommonProxy {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        GTLog.logger.info("Registering Blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();
        registry.register(MACHINE);
        registry.register(STEAM_MACHINE);

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

        registry.register(createItemBlock(MACHINE, () -> new MachineItemBlock(MACHINE)));
        registry.register(createItemBlock(STEAM_MACHINE, () -> new MachineItemBlock(STEAM_MACHINE)));

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

//    public int getBurnTime(ItemStack fuel) {
//        if (fuel == null || (fuel.getItem() == null)) {
//            return 0;
//        }
//        int fuelValue = 0;
//        if (OreDictUnifier.isItemStackInstanceOf(fuel, "gemSodium")) {
//            fuelValue = Math.max(fuelValue, 4000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "crushedSodium")) {
//            fuelValue = Math.max(fuelValue, 4000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustImpureSodium")) {
//            fuelValue = Math.max(fuelValue, 4000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustSodium")) {
//            fuelValue = Math.max(fuelValue, 4000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustSmallSodium")) {
//            fuelValue = Math.max(fuelValue, 1000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustTinySodium")) {
//            fuelValue = Math.max(fuelValue, 444);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "gemLithium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "crushedLithium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustImpureLithium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustLithium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustSmallLithium")) {
//            fuelValue = Math.max(fuelValue, 2000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustTinyLithium")) {
//            fuelValue = Math.max(fuelValue, 888);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "gemCaesium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "crushedCaesium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustImpureCaesium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustCaesium")) {
//            fuelValue = Math.max(fuelValue, 6000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustSmallCaesium")) {
//            fuelValue = Math.max(fuelValue, 2000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustTinyCaesium")) {
//            fuelValue = Math.max(fuelValue, 888);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "gemLignite")) {
//            fuelValue = Math.max(fuelValue, 1200);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "crushedLignite")) {
//            fuelValue = Math.max(fuelValue, 1200);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustImpureLignite")) {
//            fuelValue = Math.max(fuelValue, 1200);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustLignite")) {
//            fuelValue = Math.max(fuelValue, 1200);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustSmallLignite")) {
//            fuelValue = Math.max(fuelValue, 375);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustTinyLignite")) {
//            fuelValue = Math.max(fuelValue, 166);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "gemCoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "crushedCoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustImpureCoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustCoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustSmallCoal")) {
//            fuelValue = Math.max(fuelValue, 400);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustTinyCoal")) {
//            fuelValue = Math.max(fuelValue, 177);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "gemCharcoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "crushedCharcoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustImpureCharcoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustCharcoal")) {
//            fuelValue = Math.max(fuelValue, 1600);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustSmallCharcoal")) {
//            fuelValue = Math.max(fuelValue, 400);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustTinyCharcoal")) {
//            fuelValue = Math.max(fuelValue, 177);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustWood")) {
//            fuelValue = Math.max(fuelValue, 100);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustSmallWood")) {
//            fuelValue = Math.max(fuelValue, 25);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "dustTinyWood")) {
//            fuelValue = Math.max(fuelValue, 11);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "plateWood")) {
//            fuelValue = Math.min(fuelValue, 300);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "blockLignite")) {
//            fuelValue = Math.max(fuelValue, 12000);
//        } else if (OreDictUnifier.isItemStackInstanceOf(fuel, "blockCharcoal")) {
//            fuelValue = Math.max(fuelValue, 16000);
//        } else if (GTUtility.areStacksEqual(fuel, new ItemStack(Blocks.WOODEN_BUTTON, 1))) {
//            fuelValue = Math.max(fuelValue, 150);
//        } else if (GTUtility.areStacksEqual(fuel, new ItemStack(Blocks.LADDER, 1))) {
//            fuelValue = Math.max(fuelValue, 100);
//        } else if (GTUtility.areStacksEqual(fuel, new ItemStack(Items.SIGN, 1))) {
//            fuelValue = Math.max(fuelValue, 600);
//        } else if (GTUtility.areStacksEqual(fuel, new ItemStack(Items.OAK_DOOR, 1))) {
//            fuelValue = Math.max(fuelValue, 600);
//        } else if (GTUtility.areStacksEqual(fuel, ItemList.Block_MSSFUEL.get(1))) {
//            fuelValue = Math.max(fuelValue, 150000);
//        } else if (GTUtility.areStacksEqual(fuel, ItemList.Block_SSFUEL.get(1))) {
//            fuelValue = Math.max(fuelValue, 100000);
//        }
//        return fuelValue;
//    }

    public boolean isServerSide() {
        return true;
    }

    public boolean isClientSide() {
        return false;
    }
}