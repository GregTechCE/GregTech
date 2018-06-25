package gregtech.common;

import gregtech.api.GTValues;
import gregtech.api.block.machines.MachineItemBlock;
import gregtech.api.enchants.EnchantmentEnderDamage;
import gregtech.api.enchants.EnchantmentRadioactivity;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.util.GTLog;
import gregtech.common.blocks.*;
import gregtech.common.blocks.wood.BlockLeavesGT;
import gregtech.common.blocks.wood.BlockLogGT;
import gregtech.common.blocks.wood.BlockSaplingGT;
import gregtech.common.cable.ItemBlockCable;
import gregtech.common.items.MetaItems;
import gregtech.common.items.PotionFluids;
import gregtech.loaders.load.FuelLoader;
import gregtech.loaders.load.MetaTileEntityLoader;
import gregtech.loaders.load.OreDictionaryLoader;
import gregtech.loaders.oreprocessing.OreProcessingHandler;
import gregtech.loaders.postload.CraftingRecipeLoader;
import gregtech.loaders.postload.MachineRecipeLoader;
import gregtech.loaders.preload.MaterialInfoLoader;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Function;

import static gregtech.common.blocks.MetaBlocks.*;

@Mod.EventBusSubscriber(modid = GTValues.MODID)
public class CommonProxy {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        GTLog.logger.info("Registering Blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();

        //last chance for mods to register their potion types is here
        PotionFluids.initPotionFluids();

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
        registry.register(LOG);
        registry.register(LEAVES);
        registry.register(SAPLING);
        registry.register(CRUSHER_BLADE);

        CABLES.values().forEach(registry::register);
        COMPRESSED.values().stream().distinct().forEach(registry::register);
        SURFACE_ROCKS.values().stream().distinct().forEach(registry::register);
        FRAMES.values().stream().distinct().forEach(registry::register);
        ORES.forEach(registry::register);
        FLUID_BLOCKS.forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        GTLog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();

        for (MetaItem<?> item : MetaItems.ITEMS) {
            registry.register(item);
            item.registerSubItems();
        }
        OreProcessingHandler.initializeMetaItems();

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
        registry.register(createMultiTexItemBlock(LOG, state -> state.getValue(BlockLogGT.VARIANT).getName()));
        registry.register(createMultiTexItemBlock(LEAVES, state -> state.getValue(BlockLeavesGT.VARIANT).getName()));
        registry.register(createMultiTexItemBlock(SAPLING, state -> state.getValue(BlockSaplingGT.VARIANT).getName()));
        registry.register(createItemBlock(CRUSHER_BLADE, ItemBlock::new));

        CABLES.values().stream()
            .map(block -> createItemBlock(block, ItemBlockCable::new))
            .forEach(registry::register);
        COMPRESSED.values()
            .stream().distinct()
            .map(block -> createItemBlock(block, CompressedItemBlock::new))
            .forEach(registry::register);
        FRAMES.values()
            .stream().distinct()
            .map(block -> createItemBlock(block, FrameItemBlock::new))
            .forEach(registry::register);
        ORES.stream()
            .map(block -> createItemBlock(block, OreItemBlock::new))
            .forEach(registry::register);
    }

    //this is called with normal priority, so most mods working with
    //ore dictionary and recipes will get recipes accessible in time
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        GTLog.logger.info("Registering ore dictionary...");

        MetaItems.registerOreDict();
        MetaBlocks.registerOreDict();
        OreDictionaryLoader.init();
        MaterialInfoLoader.init();

        GTLog.logger.info("Registering recipes...");

        MetaItems.registerRecipes();
        MachineRecipeLoader.init();
        FuelLoader.registerFuels();
        CraftingRecipeLoader.init();
        MetaTileEntityLoader.init();
        OreProcessingHandler.registerProcessing();
    }

    //this is called almost last, to make sure all mods registered their ore dictionary
    //items and blocks for running first phase of material handlers
    //it will also clear generated materials
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void runEarlyMaterialHandlers(RegistryEvent.Register<IRecipe> event) {
        GTLog.logger.info("Running early material handlers...");
        OrePrefix.runMaterialHandlers();
    }

    //this is called last, so all mods finished registering their stuff, as example, CraftTweaker
    //if it registered some kind of ore dictionary entry, late processing will hook it and generate recipes
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerRecipesLowest(RegistryEvent.Register<IRecipe> event) {
        GTLog.logger.info("Running late material handlers...");
        OrePrefix.runMaterialHandlers();
    }

    private static <T extends Block> ItemBlock createMultiTexItemBlock(T block, Function<IBlockState, String> nameProducer) {
        ItemBlock itemBlock = new ItemMultiTexture(block, block, stack -> {
            IBlockState blockState = block.getStateFromMeta(stack.getMetadata());
            return nameProducer.apply(blockState);
        });
        itemBlock.setRegistryName(block.getRegistryName());
        return itemBlock;
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
    }


}