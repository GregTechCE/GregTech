package gregtech.common;

import gregtech.api.GregTechAPI;
import gregtech.api.enchants.EnchantmentEnderDamage;
import gregtech.api.enchants.EnchantmentRadioactivity;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.util.GTLog;
import gregtech.common.blocks.CompressedItemBlock;
import gregtech.common.blocks.MachineItemBlock;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.OreItemBlock;
import gregtech.common.blocks.StoneItemBlock;
import gregtech.common.blocks.VariantItemBlock;
import gregtech.common.items.MetaItems;
import ic2.core.ref.ItemName;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static gregtech.common.blocks.MetaBlocks.*;

@Mod.EventBusSubscriber
public class CommonProxy {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        GTLog.logger.info("Registering Blocks...");
        IForgeRegistry<Block> registry = event.getRegistry();
        registry.register(MetaBlocks.MACHINE);
        registry.register(MetaBlocks.BOILER_CASING);
        registry.register(MetaBlocks.METAL_CASING);
        registry.register(MetaBlocks.TURBINE_CASING);
        registry.register(MetaBlocks.MACHINE_CASING);
        registry.register(MetaBlocks.MUTLIBLOCK_CASING);
        registry.register(MetaBlocks.WIRE_COIL);
        registry.register(MetaBlocks.WARNING_SIGN);
        registry.register(MetaBlocks.GRANITE);
        registry.register(MetaBlocks.MINERAL);
        registry.register(MetaBlocks.CONCRETE);

        MetaBlocks.COMPRESSED.values().stream().distinct().forEach(registry::register);
        MetaBlocks.ORES.values().stream().distinct().forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        GTLog.logger.info("Registering Items...");
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(MetaItems.META_ITEM_FIRST);
        MetaItems.META_ITEM_FIRST.registerSubItems();
        registry.register(MetaItems.META_ITEM_SECOND);
        MetaItems.META_ITEM_SECOND.registerSubItems();
        registry.register(MetaItems.META_TOOL);
        MetaItems.META_TOOL.registerSubItems();

        registry.register(createItemBlock(MACHINE, () -> new MachineItemBlock(MACHINE)));
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

        MetaBlocks.COMPRESSED.values()
            .stream()
            .distinct()
            .map(block -> createItemBlock(block, () -> new CompressedItemBlock(block)))
            .forEach(registry::register);
        MetaBlocks.ORES.values()
            .stream()
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

        OreDictUnifier.registerOre(new ItemStack(Items.IRON_DOOR, 1), new ItemMaterialInfo(new MaterialStack(Materials.Iron, 21772800L)));
        OreDictUnifier.registerOre(new ItemStack(Items.ACACIA_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));
        OreDictUnifier.registerOre(new ItemStack(Items.BIRCH_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));
        OreDictUnifier.registerOre(new ItemStack(Items.JUNGLE_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));
        OreDictUnifier.registerOre(new ItemStack(Items.OAK_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));
        OreDictUnifier.registerOre(new ItemStack(Items.SPRUCE_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));
        OreDictUnifier.registerOre(new ItemStack(Items.DARK_OAK_DOOR, 1, 32767), new ItemMaterialInfo(new MaterialStack(Materials.Wood, 21772800L)));

        GTLog.logger.info("GregTechMod: Adding Configs specific for MetaTileEntities");

/*
        GTLog.out.println("GregTechMod: Adding Tool Usage Crafting Recipes for OreDict Items.");
        for (Materials aMaterial : Materials.values()) {
            if ((aMaterial.mUnificatable) && (aMaterial.mMaterialInto == aMaterial)) {
                if (!aMaterial.contains(SubTag.NO_SMASHING)) {
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammerplating, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, aMaterial, 1), tBits, new Object[]{"h", "X", "X",
                                Character.valueOf('X'), OrePrefix.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, aMaterial, 1), tBits,
                                new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gem.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, aMaterial, 1), tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefix.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(
                                OreDictUnifier.get(OrePrefix.plate, aMaterial, 1),
                                tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefix.gem.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, aMaterial, 1), tBits,
                                new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.ingotDouble.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, aMaterial, 2L), tBits,
                                new Object[]{"H", "X", Character.valueOf('H'), ToolDictNames.craftingToolForgeHammer, Character.valueOf('X'),
                                        OrePrefix.ingotDouble.get(aMaterial)});
                    }
                    if (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.hammermultiingot, aMaterial.toString(), true)) {
                        GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingotDouble, aMaterial, 1), tBits, new Object[]{"I", "I", "h",
                                Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingotTriple, aMaterial, 1), tBits, new Object[]{"I", "B", "h",
                                Character.valueOf('I'), OrePrefix.ingotDouble.get(aMaterial), Character.valueOf('B'), OrePrefix.ingot.get(aMaterial)});
                        GT_ModHandler
                                .addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingotQuadruple, aMaterial, 1), tBits,
                                        new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefix.ingotTriple.get(aMaterial), Character.valueOf('B'),
                                                OrePrefix.ingot.get(aMaterial)});
                        GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingotQuintuple, aMaterial, 1), tBits,
                                new Object[]{"I", "B", "h", Character.valueOf('I'), OrePrefix.ingotQuadruple.get(aMaterial), Character.valueOf('B'),
                                        OrePrefix.ingot.get(aMaterial)});
                    }
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, aMaterial, 1), tBits, new Object[]{"PIh", "P  ",
                            "f  ", Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadHammer, aMaterial, 1), tBits, new Object[]{"II ", "IIh",
                            "II ", Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, aMaterial, 1), tBits, new Object[]{"PIh", "f  ",
                            Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, aMaterial, 1), tBits, new Object[]{"PII", "f h",
                            Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadPlow, aMaterial, 1), tBits, new Object[]{"PP", "PP", "hf",
                            Character.valueOf('P'), OrePrefix.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSaw, aMaterial, 1), tBits, new Object[]{"PP ", "fh ",
                            Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSense, aMaterial, 1), tBits, new Object[]{"PPI", "hf ",
                            Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('I'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(
                            OreDictUnifier.get(OrePrefix.toolHeadShovel, aMaterial, 1),
                            tBits,
                            new Object[]{"fPh", 'P', OrePrefix.plate.get(aMaterial), 'I',
                                    OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, aMaterial, 1), tBits, new Object[]{" P ", "fPh",
                            'P', OrePrefix.plate.get(aMaterial), 'I', OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ring, aMaterial, 1), tBits,
                            new Object[]{"h ", " X", 'X', OrePrefix.stick.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.stickLong, aMaterial, 1), tBits,
                            new Object[]{"ShS", 'S', OrePrefix.stick.get(aMaterial)});
                }*/
                /*if (!aMaterial.contains(SubTag.NO_WORKING)) {
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.stick, aMaterial, 2L), tBits,
                            new Object[]{"s", "X", Character.valueOf('X'), OrePrefix.stickLong.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.stick, aMaterial, 1), tBits,
                            new Object[]{"f ", " X", Character.valueOf('X'), OrePrefix.ingot.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.bolt, aMaterial, 2L), tBits,
                            new Object[]{"s ", " X", Character.valueOf('X'), OrePrefix.stick.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.screw, aMaterial, 1), tBits,
                            new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefix.bolt.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.round, aMaterial, 1), tBits,
                            new Object[]{"fX", "X ", Character.valueOf('X'), OrePrefix.nugget.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.rotor, aMaterial, 1), tBits, new Object[]{"PhP", "SRf", "PdP",
                            Character.valueOf('P'), aMaterial == Materials.Wood ? OrePrefix.plank.get(aMaterial) : OrePrefix.plate.get(aMaterial),
                            Character.valueOf('R'), OrePrefix.ring.get(aMaterial), Character.valueOf('S'), OrePrefix.screw.get(aMaterial)});
                    GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, aMaterial, 4L), OreDictUnifier.get(OrePrefix.ring, aMaterial, 1), Materials.Tin.getMolten(32), OreDictUnifier.get(OrePrefix.rotor, aMaterial, 1), 240, 24);
                    GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, aMaterial, 4L), OreDictUnifier.get(OrePrefix.ring, aMaterial, 1), Materials.Lead.getMolten(48), OreDictUnifier.get(OrePrefix.rotor, aMaterial, 1), 240, 24);
                    GTValues.RA.addAssemblerRecipe(OreDictUnifier.get(OrePrefix.plate, aMaterial, 4L), OreDictUnifier.get(OrePrefix.ring, aMaterial, 1), Materials.SolderingAlloy.getMolten(16), OreDictUnifier.get(OrePrefix.rotor, aMaterial, 1), 240, 24);
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.stickLong, aMaterial, 1), tBits,
                            new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefix.gemFlawless.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.stickLong, aMaterial, 2L), tBits,
                            new Object[]{"sf", "G ", Character.valueOf('G'), OrePrefix.gemExquisite.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.wireGt01, aMaterial, 1), tBits,
                            new Object[]{"Xx", Character.valueOf('X'), OrePrefix.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.wireFine, aMaterial, 1), tBits,
                            new Object[]{"Xx", Character.valueOf('X'), OrePrefix.foil.get(aMaterial)});

                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.turbineBlade, aMaterial, 1), tBits, new Object[]{"fPd", "SPS", " P ",
                            Character.valueOf('P'), aMaterial == Materials.Wood ? OrePrefix.plank.get(aMaterial) : OrePrefix.plateDouble.get(aMaterial),
                            Character.valueOf('R'), OrePrefix.ring.get(aMaterial), Character.valueOf('S'), OrePrefix.screw.get(aMaterial)});


                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.arrowGtWood, aMaterial, 1), tBits, new Object[]{"  A", " S ",
                            "F  ", Character.valueOf('S'), OrePrefix.stick.get(Materials.Wood), Character.valueOf('F'), OreDictNames.craftingFeather,
                            Character.valueOf('A'), OrePrefix.toolHeadArrow.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.arrowGtPlastic, aMaterial, 1), tBits, new Object[]{"  A", " S ",
                            "F  ", Character.valueOf('S'), OrePrefix.stick.get(Materials.Plastic), Character.valueOf('F'), OreDictNames.craftingFeather,
                            Character.valueOf('A'), OrePrefix.toolHeadArrow.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadArrow, aMaterial, 1), tBits,
                            new Object[]{"Xf", Character.valueOf('X'), OrePrefix.gemChipped.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadArrow, aMaterial, 3L), tBits,
                            new Object[]{(aMaterial.contains(SubTag.WOOD) ? 115 : 'x') + "Pf", Character.valueOf('P'), OrePrefix.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadAxe, aMaterial, 1), tBits, new Object[]{"GG ", "G  ",
                            "f  ", Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadHoe, aMaterial, 1), tBits, new Object[]{"GG ", "f  ",
                            "   ", Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadPickaxe, aMaterial, 1), tBits, new Object[]{"GGG", "f  ",
                            Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadPlow, aMaterial, 1), tBits, new Object[]{"GG", "GG", " f",
                            Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSaw, aMaterial, 1), tBits,
                            new Object[]{"GGf", Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSense, aMaterial, 1), tBits, new Object[]{"GGG", " f ",
                            "   ", Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadShovel, aMaterial, 1), tBits,
                            new Object[]{"fG", Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadSword, aMaterial, 1), tBits, new Object[]{" G", "fG",
                            Character.valueOf('G'), OrePrefix.gem.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadUniversalSpade, aMaterial, 1), tBits, new Object[]{"fX",
                            Character.valueOf('X'), OrePrefix.toolHeadShovel.get(aMaterial)});

                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadBuzzSaw, aMaterial, 1), tBits, new Object[]{"wXh", "X X",
                            "fXx", Character.valueOf('X'), OrePrefix.plate.get(aMaterial)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadWrench, aMaterial, 1), tBits, new Object[]{"hXW", "XRX",
                            "WXd", Character.valueOf('X'), OrePrefix.plate.get(aMaterial), Character.valueOf('S'), OrePrefix.plate.get(Materials.Steel),
                            Character.valueOf('R'), OrePrefix.ring.get(Materials.Steel), Character.valueOf('W'), OrePrefix.screw.get(Materials.Steel)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadChainsaw, aMaterial, 1), tBits, new Object[]{"SRS", "XhX",
                            "SRS", Character.valueOf('X'), OrePrefix.plate.get(aMaterial), Character.valueOf('S'), OrePrefix.plate.get(Materials.Steel),
                            Character.valueOf('R'), OrePrefix.ring.get(Materials.Steel)});
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.toolHeadDrill, aMaterial, 1), tBits, new Object[]{"XSX", "XSX",
                            "ShS", Character.valueOf('X'), OrePrefix.plate.get(aMaterial), Character.valueOf('S'), OrePrefix.plate.get(Materials.Steel)});
                    switch (aMaterial.mName) {
                        case "Wood":
                            GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gearSmall, aMaterial, 1), tBits, new Object[]{"P ", " s",
                                    Character.valueOf('P'), OrePrefix.plank.get(aMaterial)});
                            break;
                        case "Stone":
                            GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gearSmall, aMaterial, 1), tBits, new Object[]{"P ", " f",
                                    Character.valueOf('P'), OrePrefix.stoneSmooth});
                            break;
                        default:
                            GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gearSmall, aMaterial, 1), tBits,
                                    new Object[]{"P ", aMaterial.contains(SubTag.WOOD) ? " s" : " h", Character.valueOf('P'), OrePrefix.plate.get(aMaterial)});
                    }
                    switch (aMaterial.mName) {
                        case "Wood":
                            GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gear, aMaterial, 1), tBits, new Object[]{"SPS", "PsP", "SPS",
                                    Character.valueOf('P'), OrePrefix.plank.get(aMaterial), Character.valueOf('S'), OrePrefix.stick.get(aMaterial)});
                            break;
                        case "Stone":
                            GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gear, aMaterial, 1), tBits, new Object[]{"SPS", "PfP", "SPS",
                                    Character.valueOf('P'), OrePrefix.stoneSmooth, Character.valueOf('S'), new ItemStack(Blocks.STONE_BUTTON, 1, 32767)});
                            break;
                        default:
                            GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gear, aMaterial, 1), tBits, new Object[]{"SPS", "PwP", "SPS",
                                    Character.valueOf('P'), OrePrefix.plate.get(aMaterial), Character.valueOf('S'), OrePrefix.stick.get(aMaterial)});
                    }
                }
                if (aMaterial.contains(SubTag.SMELTING_TO_GEM)) {
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gem, aMaterial, 1), tBits, new Object[]{"XXX", "XXX", "XXX",
                            Character.valueOf('X'), OrePrefix.nugget.get(aMaterial)});
                } else {
                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingot, aMaterial, 1), tBits, new Object[]{"XXX", "XXX", "XXX",
                            Character.valueOf('X'), OrePrefix.nugget.get(aMaterial)});
                }
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.crushedCentrifuged.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.crystalline.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.crystal.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustPure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.crushedPurified.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustPure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.cleanGravel.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustPure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.reduced.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.clump.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.shard.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.crushed.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustImpure, aMaterial.mMacerateInto, 1), tBits, new Object[]{"h", "X",
                        'X', OrePrefix.dirtyGravel.get(aMaterial)});

                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustSmall, aMaterial, 4L), tBits,
                        new Object[]{" X", "  ", 'X', OrePrefix.dust.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustTiny, aMaterial, 9L), tBits,
                        new Object[]{"X ", "  ", 'X', OrePrefix.dust.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial, 1), tBits,
                        new Object[]{"XX", "XX", 'X', OrePrefix.dustSmall.get(aMaterial)});
                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial, 1), tBits,
                        new Object[]{"XXX", "XXX", "XXX", 'X', OrePrefix.dustTiny.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial, 16L), tBits, new Object[]{"Xc", Character.valueOf('X'),
//                        OrePrefix.crateGtDust.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gem, aMaterial, 16L), tBits, new Object[]{"Xc", Character.valueOf('X'),
//                        OrePrefix.crateGtGem.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.ingot, aMaterial, 16L), tBits, new Object[]{"Xc",
//                        Character.valueOf('X'), OrePrefix.crateGtIngot.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.plate, aMaterial, 16L), tBits, new Object[]{"Xc",
//                        Character.valueOf('X'), OrePrefix.crateGtPlate.get(aMaterial)});
//
//                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gemChipped, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemFlawed.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gemFlawed, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gem.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gem, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemFlawless.get(aMaterial)});
//                GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.gemFlawless, aMaterial, 2L), tBits,
//                        new Object[]{"h", "X", Character.valueOf('X'), OrePrefix.gemExquisite.get(aMaterial)});
//                if ((aMaterial.contains(SubTag.MORTAR_GRINDABLE)) && (GregTechAPI.sRecipeFile.get(ConfigCategories.Tools.mortar, aMaterial.mName, true))) {
//                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustSmall, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemChipped.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dustSmall, aMaterial, 2L), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemFlawed.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gem.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial, 2L), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemFlawless.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial, 4L), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.gemExquisite.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.ingot.get(aMaterial)});
//                    GT_ModHandler.addCraftingRecipe(OreDictUnifier.get(OrePrefix.dust, aMaterial, 1), tBits,
//                            new Object[]{"X", "m", Character.valueOf('X'), OrePrefix.plate.get(aMaterial)});
//                }
            }
        }*/
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