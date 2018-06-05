package gregtech.common.blocks;

import com.google.common.collect.ImmutableMap;
import gregtech.api.GTValues;
import gregtech.api.GregTechAPI;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.render.MetaTileEntityRenderer;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.MarkerMaterials;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.DustMaterial.MatFlags;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.StoneType;
import gregtech.api.unification.ore.StoneTypes;
import gregtech.common.blocks.wood.BlockLeavesGT;
import gregtech.common.blocks.wood.BlockLogGT;
import gregtech.common.blocks.wood.BlockSaplingGT;
import gregtech.common.cable.BlockCable;
import gregtech.common.cable.Insulation;
import gregtech.common.cable.WireProperties;
import gregtech.common.render.CableRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public static BlockLogGT LOG;
    public static BlockLeavesGT LEAVES;
    public static BlockSaplingGT SAPLING;

    public static Map<Material, BlockCable> CABLES = new HashMap<>();
    public static HashMap<DustMaterial, BlockCompressed> COMPRESSED = new HashMap<>();
    public static HashMap<MetalMaterial, BlockSurfaceRock> SURFACE_ROCKS = new HashMap<>();
    public static HashMap<SolidMaterial, BlockFrame> FRAMES = new HashMap<>();
    public static Collection<BlockOre> ORES = new HashSet<>();
    public static Collection<BlockFluidBase> FLUID_BLOCKS = new HashSet<>();

    public static void init() {
        GregTechAPI.MACHINE = MACHINE = new BlockMachine();
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
        MINERAL = new BlockMineral();
        MINERAL.setRegistryName("mineral");

        CONCRETE = new BlockConcrete();
        CONCRETE.setRegistryName("concrete");

        LOG = new BlockLogGT();
        LOG.setRegistryName("log");
        LEAVES = new BlockLeavesGT();
        LEAVES.setRegistryName("leaves");
        SAPLING = new BlockSaplingGT();
        SAPLING.setRegistryName("sapling");

        StoneType.init();

        createGeneratedBlock(material -> material instanceof DustMaterial &&
            !OrePrefix.block.isIgnored(material), MetaBlocks::createCompressedBlock);
        createGeneratedBlock(material -> material instanceof MetalMaterial &&
            !OrePrefix.frameGt.isIgnored(material), MetaBlocks::createFrameBlock);
        createGeneratedBlock(material -> material instanceof MetalMaterial &&
            material.hasFlag(MatFlags.GENERATE_ORE), MetaBlocks::createSurfaceRockBlock);

        for (Material material : Material.MATERIAL_REGISTRY.getObjectsWithIds()) {
            if (material instanceof DustMaterial &&
                material.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
                createOreBlock((DustMaterial) material);
            }
            if(material instanceof MetalMaterial) {
                MetalMaterial metalMaterial = (MetalMaterial) material;
                if(metalMaterial.cableProperties != null) {
                    createCableBlock(metalMaterial);
                }
            }
        }
        createCableBlock(MarkerMaterials.Tier.Superconductor, new WireProperties(Integer.MAX_VALUE, 4, 1));
    }

    private static void createGeneratedBlock(Predicate<Material> materialPredicate, BiConsumer<Material[], Integer> blockGenerator) {
        Material[] materialBuffer = new Material[16];
        Arrays.fill(materialBuffer, Materials._NULL);
        int currentGenerationIndex = 0;
        for(Material material : Material.MATERIAL_REGISTRY.getObjectsWithIds()) {
            if(materialPredicate.test(material)) {
                if(currentGenerationIndex > 0 && currentGenerationIndex % 16 == 0) {
                    blockGenerator.accept(materialBuffer, currentGenerationIndex / 16 - 1);
                    Arrays.fill(materialBuffer, Materials._NULL);
                }
                materialBuffer[currentGenerationIndex % 16] = material;
                currentGenerationIndex++;
            }
        }
        if(materialBuffer[0] != Materials._NULL) {
            blockGenerator.accept(materialBuffer, currentGenerationIndex / 16);
        }
    }

    private static void createCableBlock(MetalMaterial material) {
        createCableBlock(material, material.cableProperties);
    }

    private static void createCableBlock(Material material, WireProperties wireProperties) {
        BlockCable blockCable = new BlockCable(material, wireProperties);
        blockCable.setRegistryName("cable_" + material.toString());
        CABLES.put(material, blockCable);
    }

    private static void createSurfaceRockBlock(Material[] materials, int index) {
        BlockSurfaceRock block = new BlockSurfaceRock(materials);
        block.setRegistryName("surface_rock_" + index);
        for (Material material : materials) {
            if (material instanceof DustMaterial) {
                SURFACE_ROCKS.put((MetalMaterial) material, block);
            }
        }
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

    private static void createFrameBlock(Material[] materials, int index) {
        BlockFrame block = new BlockFrame(materials);
        block.setRegistryName("frame_" + index);
        for (Material material : materials) {
            if (material instanceof SolidMaterial) {
                FRAMES.put((SolidMaterial) material, block);
            }
        }
    }

    private static void createOreBlock(DustMaterial material) {
        StoneType[] stoneTypeBuffer = new StoneType[16];
        Arrays.fill(stoneTypeBuffer, StoneTypes._NULL);
        int generationIndex = 0;
        for (StoneType stoneType : StoneType.STONE_TYPE_REGISTRY) {
            int id = StoneType.STONE_TYPE_REGISTRY.getIDForObject(stoneType), index = id / 16;
            if (index > generationIndex) {
                createOreBlock(material, stoneTypeBuffer, generationIndex);
                Arrays.fill(stoneTypeBuffer, StoneTypes._NULL);
            }
            stoneTypeBuffer[id % 16] = stoneType;
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
        registerItemModel(LOG, ImmutableMap.of(BlockLogGT.LOG_AXIS, EnumAxis.Y));
        registerItemModel(LEAVES);
        registerItemModel(SAPLING);

        ItemMeshDefinition cableMeshDefinition = stack -> CableRenderer.MODEL_LOCATION;
        CABLES.values().forEach(cable -> ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(cable), cableMeshDefinition));
        COMPRESSED.values().stream().distinct().forEach(MetaBlocks::registerItemModel);
        FRAMES.values().stream().distinct().forEach(MetaBlocks::registerItemModel);
        ORES.stream().distinct().forEach(MetaBlocks::registerItemModel);
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block) {
        for (IBlockState state : block.getBlockState().getValidStates()) {
            //noinspection ConstantConditions
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                block.getMetaFromState(state),
                new ModelResourceLocation(block.getRegistryName(),
                    statePropertiesToString(state.getProperties())));
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemModel(Block block, Map<IProperty<?>, Comparable<?>> stateOverrides) {
        for (IBlockState state : block.getBlockState().getValidStates()) {
            HashMap<IProperty<?>, Comparable<?>> stringProperties = new HashMap<>(state.getProperties());
            stringProperties.putAll(stateOverrides);
            //noinspection ConstantConditions
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
                block.getMetaFromState(state),
                new ModelResourceLocation(block.getRegistryName(),
                    statePropertiesToString(stringProperties)));
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

        MetaBlocks.FRAMES.values().stream().distinct().forEach(block -> {
            Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(FRAME_BLOCK_COLOR, block);
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(FRAME_ITEM_COLOR, block);
        });

        MetaBlocks.ORES.stream().distinct().forEach(block -> {
            Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(ORE_BLOCK_COLOR, block);
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ORE_ITEM_COLOR, block);
        });

        MetaBlocks.SURFACE_ROCKS.values().stream().distinct().forEach(block ->
            Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(SURFACE_ROCK_COLOR, block));
    }

    public static void registerOreDict() {
        OreDictUnifier.registerOre(new ItemStack(LOG, 1, GTValues.W), OrePrefix.log, Materials.Wood);
        OreDictUnifier.registerOre(new ItemStack(LEAVES, 1, GTValues.W), OrePrefix.treeLeaves, null);
        OreDictUnifier.registerOre(new ItemStack(SAPLING, 1, GTValues.W), OrePrefix.treeSapling, null);

        for(Entry<DustMaterial, BlockCompressed> entry : COMPRESSED.entrySet()) {
            DustMaterial material = entry.getKey();
            BlockCompressed block = entry.getValue();
            ItemStack itemStack = block.getItem(material);
            OreDictUnifier.registerOre(itemStack, OrePrefix.block, material);
        }

        for(Entry<SolidMaterial, BlockFrame> entry : FRAMES.entrySet()) {
            SolidMaterial material = entry.getKey();
            BlockFrame block = entry.getValue();
            ItemStack itemStack = block.getItem(material);
            OreDictUnifier.registerOre(itemStack, OrePrefix.frameGt, material);
        }

        for(BlockOre blockOre : ORES) {
            DustMaterial material = blockOre.material;
            for(StoneType stoneType : blockOre.STONE_TYPE.getAllowedValues()) {
                ItemStack normalStack = blockOre.getItem(blockOre.getDefaultState()
                    .withProperty(blockOre.STONE_TYPE, stoneType));
                OreDictUnifier.registerOre(normalStack, stoneType.processingPrefix, material);
            }
        }
        for(BlockCable blockCable : CABLES.values()) {
            for(Insulation insulation : Insulation.values()) {
                ItemStack itemStack = blockCable.getItem(insulation);
                OreDictUnifier.registerOre(itemStack, insulation.orePrefix, blockCable.material);
            }
        }
    }

    private static String statePropertiesToString(Map<IProperty<?>, Comparable<?>> properties) {
        StringBuilder stringbuilder = new StringBuilder();

        List<Entry<IProperty<?>, Comparable<?>>> entries = properties.entrySet().stream()
            .sorted(Comparator.comparing(c -> c.getKey().getName()))
            .collect(Collectors.toList());

        for (Map.Entry<IProperty<?>, Comparable<?>> entry : entries) {
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
