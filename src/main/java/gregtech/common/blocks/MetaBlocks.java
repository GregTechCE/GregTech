package gregtech.common.blocks;

import java.util.*;
import java.util.Map.Entry;

import gregtech.api.GregTechAPI;
import gregtech.api.cable.BlockCable;
import gregtech.api.cable.Insulation;
import gregtech.api.render.CableRenderer;
import gregtech.api.render.MetaTileEntityRenderer;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import gregtech.api.unification.ore.StoneTypes;
import gregtech.common.blocks.BlockGranite.GraniteVariant;
import gregtech.common.blocks.BlockMineral.MineralVariant;
import gregtech.common.blocks.StoneBlock.ChiselingVariant;
import gregtech.api.block.machines.BlockMachine;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static gregtech.common.ClientProxy.*;

public class MetaBlocks {

    private MetaBlocks() {}

    public static BlockMachine MACHINE;

    public static BlockBoilerCasing BOILER_CASING;
    public static BlockMetalCasing METAL_CASING;
    public static BlockTurbineCasing TURBINE_CASING;
    public static BlockMachineCasing MACHINE_CASING;
    public static BlockMultiblockCasing MUTLIBLOCK_CASING;
    public static BlockWireCoil WIRE_COIL;
    public static BlockWarningSign WARNING_SIGN;

    public static BlockGranite GRANITE;
    public static BlockMineral MINERAL;
    public static BlockConcrete CONCRETE;

    public static Map<MetalMaterial, BlockCable> CABLES = new HashMap<>();
    public static HashMap<DustMaterial, BlockCompressed> COMPRESSED = new HashMap<>();
    public static Collection<BlockOre> ORES = new HashSet<>();

    public static StoneType BLACK_GRANITE;
    public static StoneType RED_GRANITE;
    public static StoneType MARBLE;
    public static StoneType BASALT;

    public static void init() {
        MACHINE = new BlockMachine();
        MACHINE.setRegistryName("machine");
        BOILER_CASING = new BlockBoilerCasing();
        BOILER_CASING.setRegistryName("boiler_casing");
        METAL_CASING = new BlockMetalCasing();
        METAL_CASING.setRegistryName("metal_casing");
        TURBINE_CASING = new BlockTurbineCasing();
        TURBINE_CASING.setRegistryName("turbine_casing");
        MACHINE_CASING = new BlockMachineCasing();
        MACHINE_CASING.setRegistryName("machine_casing");
        MUTLIBLOCK_CASING = new BlockMultiblockCasing();
        MUTLIBLOCK_CASING.setRegistryName("multiblock_casing");
        WIRE_COIL = new BlockWireCoil();
        WIRE_COIL.setRegistryName("wire_coil");
        WARNING_SIGN = new BlockWarningSign();
        WARNING_SIGN.setRegistryName("warning_sign");
        GRANITE = new BlockGranite();
        GRANITE.setRegistryName("granite");
        BLACK_GRANITE = new StoneType(12, "black_granite", OrePrefix.oreBlackgranite, Materials.GraniteBlack, "gregtech:blocks/stones/granite/granite_black_stone", () -> GRANITE.withVariant(GraniteVariant.BLACK_GRANITE, ChiselingVariant.NORMAL), state -> state.getBlock() instanceof BlockGranite && ((BlockGranite) state.getBlock()).getVariant(state) == GraniteVariant.BLACK_GRANITE);
        RED_GRANITE = new StoneType(13, "red_granite", OrePrefix.oreRedgranite, Materials.GraniteRed, "gregtech:blocks/stones/granite/granite_red_stone", () -> GRANITE.withVariant(GraniteVariant.RED_GRANITE, ChiselingVariant.NORMAL), state -> state.getBlock() instanceof BlockGranite && ((BlockGranite) state.getBlock()).getVariant(state) == GraniteVariant.RED_GRANITE);
        MINERAL = new BlockMineral();
        MINERAL.setRegistryName("mineral");
        MARBLE = new StoneType(14, "marble", OrePrefix.oreMarble, Materials.Marble, "gregtech:blocks/stones/marble/marble_stone", () -> MINERAL.withVariant(MineralVariant.MARBLE, ChiselingVariant.NORMAL), state -> state.getBlock() instanceof BlockMineral && ((BlockMineral) state.getBlock()).getVariant(state) == BlockMineral.MineralVariant.MARBLE);
        BASALT = new StoneType(15, "basalt", OrePrefix.oreBasalt, Materials.Basalt, "gregtech:blocks/stones/basalt/basalt_stone", () -> MINERAL.withVariant(MineralVariant.BASALT, ChiselingVariant.NORMAL), state -> state.getBlock() instanceof BlockMineral && ((BlockMineral) state.getBlock()).getVariant(state) == BlockMineral.MineralVariant.BASALT);
        CONCRETE = new BlockConcrete();
        CONCRETE.setRegistryName("concrete");

        StoneType.init();
        Material[] materialBuffer = new Material[16];
        Arrays.fill(materialBuffer, Materials._NULL);
        int generationIndex = 0;
        for (Material material : Material.MATERIAL_REGISTRY.getObjectsWithIds()) {
            if (material instanceof DustMaterial) {
                if(material instanceof MetalMaterial) {
                    MetalMaterial metalMaterial = (MetalMaterial) material;
                    if(metalMaterial.cableProperties != null) {
                        createCableBlock(metalMaterial);
                    }
                }
                if (material.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
                    createOreBlock((DustMaterial) material);
                }
                int id = Material.MATERIAL_REGISTRY.getIDForObject(material);
                int index = id / 16;
                if (index > generationIndex) {
                    createCompressedBlock(materialBuffer, generationIndex);
                    Arrays.fill(materialBuffer, Materials._NULL);
                }
                if (!OrePrefix.block.isIgnored(material)) {
                    materialBuffer[id % 16] = material;
                    generationIndex = index;
                }
            }
        }
        createCompressedBlock(materialBuffer, generationIndex);
    }

    private static void createCableBlock(MetalMaterial material) {
        BlockCable blockCable = new BlockCable(material.cableProperties);
        blockCable.setRegistryName("cable_" + material.toString());
        CABLES.put(material, blockCable);
    }

    private static void createCompressedBlock(Material[] materials, int index) {
        BlockCompressed block = new BlockCompressed(materials);
        block.setRegistryName("compressed_" + index);
        for (Material material : materials) {
            if (material instanceof DustMaterial) {
                COMPRESSED.put((DustMaterial) material, block);
            }
        }
    }

    private static void createOreBlock(DustMaterial material) {
        StoneType[] stoneTypeBuffer = new StoneType[8];
        Arrays.fill(stoneTypeBuffer, StoneTypes._NULL);
        int generationIndex = 0;
        for (StoneType stoneType : StoneType.STONE_TYPE_REGISTRY) {
            int id = StoneType.STONE_TYPE_REGISTRY.getIDForObject(stoneType), index = id / 8;
            if (index > generationIndex) {
                createOreBlock(material, stoneTypeBuffer, generationIndex);
                Arrays.fill(stoneTypeBuffer, StoneTypes._NULL);
            }
            stoneTypeBuffer[id % 8] = stoneType;
            generationIndex = index;
        }
        createOreBlock(material, stoneTypeBuffer, generationIndex);
    }

    private static void createOreBlock(DustMaterial material, StoneType[] stoneTypes, int index) {
        BlockOre block = new BlockOre(material, stoneTypes);
        block.setRegistryName("ore_" + material + "_" + index);
        for (StoneType stoneType : stoneTypes) {
            GregTechAPI.oreBlockTable.computeIfAbsent(material, m -> new HashMap<>()).put(stoneType, block);
        }
        ORES.add(block);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemModels() {
        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(MACHINE), stack -> MetaTileEntityRenderer.MODEL_LOCATION);
        registerItemModel(BOILER_CASING);
        registerItemModel(METAL_CASING);
        registerItemModel(TURBINE_CASING);
        registerItemModel(MACHINE_CASING);
        registerItemModel(MUTLIBLOCK_CASING);
        registerItemModel(WIRE_COIL);
        registerItemModel(WARNING_SIGN);
        registerItemModel(GRANITE);
        registerItemModel(MINERAL);
        registerItemModel(CONCRETE);

        ItemMeshDefinition cableMeshDefinition = stack -> CableRenderer.MODEL_LOCATION;
        CABLES.values().forEach(cable -> ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(cable), cableMeshDefinition));
        COMPRESSED.values().stream().distinct().forEach(MetaBlocks::registerItemModel);
        ORES.stream().distinct().forEach(MetaBlocks::registerItemModel);
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {
        for (IBlockState state : block.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                block.getMetaFromState(state),
                new ModelResourceLocation(block.getRegistryName(), statePropertiesToString(state.getProperties())));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerStateMappers() {
        ModelLoader.setCustomStateMapper(MACHINE, new DefaultStateMapper() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return MetaTileEntityRenderer.MODEL_LOCATION;
            }
        });
        DefaultStateMapper cableStateMapper = new DefaultStateMapper() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return CableRenderer.MODEL_LOCATION;
            }
        };
        CABLES.values().forEach(cable -> ModelLoader.setCustomStateMapper(cable, cableStateMapper));
    }

    @SideOnly(Side.CLIENT)
    public static void registerColors() {
        MetaBlocks.COMPRESSED.values().stream().distinct().forEach(block -> {
            Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(COMPRESSED_BLOCK_COLOR, block);
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(COMPRESSED_ITEM_COLOR, block);
        });

        MetaBlocks.ORES.stream().distinct().forEach(block -> {
            Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(ORE_BLOCK_COLOR, block);
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ORE_ITEM_COLOR, block);
        });
    }

    public static void registerOreDict() {
        for(Entry<DustMaterial, BlockCompressed> entry : COMPRESSED.entrySet()) {
            DustMaterial material = entry.getKey();
            BlockCompressed block = entry.getValue();
            ItemStack itemStack = block.getItem(block.getDefaultState()
                .withProperty(block.variantProperty, material));
            OreDictUnifier.registerOre(itemStack, OrePrefix.block, material);
        }
        for(BlockOre blockOre : ORES) {
            DustMaterial material = blockOre.material;
            for(StoneType stoneType : blockOre.STONE_TYPE.getAllowedValues()) {
                ItemStack normalStack = blockOre.getItem(blockOre.getDefaultState()
                    .withProperty(blockOre.STONE_TYPE, stoneType)
                    .withProperty(BlockOre.SMALL, false));
                ItemStack smallOreStack = blockOre.getItem(blockOre.getDefaultState()
                    .withProperty(blockOre.STONE_TYPE, stoneType)
                    .withProperty(BlockOre.SMALL, true));
                OreDictUnifier.registerOre(normalStack, stoneType.processingPrefix, material);
                //small ore variants are always registered as oreSmall, not taking stone type into account
                OreDictUnifier.registerOre(smallOreStack, OrePrefix.oreSmall, material);
            }
        }
        for(BlockCable blockCable : CABLES.values()) {
            for(Insulation insulation : Insulation.values()) {
                ItemStack itemStack = blockCable.getItem(insulation);
                MetalMaterial material = blockCable.baseProps.material;
                OreDictUnifier.registerOre(itemStack, insulation.orePrefix, material);
            }
        }
    }

    private static String statePropertiesToString(Map<IProperty<?>, Comparable<?>> properties) {
        StringBuilder stringbuilder = new StringBuilder();

        for (Map.Entry<IProperty<?>, Comparable<?>> entry : properties.entrySet()) {
            if (stringbuilder.length() != 0) {
                stringbuilder.append(",");
            }

            IProperty<?> property = entry.getKey();
            stringbuilder.append(property.getName());
            stringbuilder.append("=");
            stringbuilder.append(getPropertyName(property, entry.getValue()));
        }

        if (stringbuilder.length() == 0) {
            stringbuilder.append("normal");
        }

        return stringbuilder.toString();
    }

    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
        return property.getName((T) value);
    }
}
