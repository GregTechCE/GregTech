package gregtech.common.blocks;

import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.Material;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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

    public static HashMap<DustMaterial, BlockCompressed> COMPRESSED;
    public static HashMap<DustMaterial, BlockOre> ORES;

    public static void init() {
//        MACHINE = new BlockMachine(); // TODO by @AntiquiAvium
//        MACHINE.registerBlock("machine");
        BOILER_CASING = new BlockBoilerCasing();
        BOILER_CASING.registerBlock("boiler_casing");
        METAL_CASING = new BlockMetalCasing();
        METAL_CASING.registerBlock("metal_casing");
        TURBINE_CASING = new BlockTurbineCasing();
        TURBINE_CASING.registerBlock("turbine_casing");
        MACHINE_CASING = new BlockMachineCasing();
        MACHINE_CASING.registerBlock("machine_casing");
        MUTLIBLOCK_CASING = new BlockMultiblockCasing();
        MUTLIBLOCK_CASING.registerBlock("multiblock_casing");
        WIRE_COIL = new BlockWireCoil();
        WIRE_COIL.registerBlock("wire_coil");
        WARNING_SIGN = new BlockWarningSign();
        WARNING_SIGN.registerBlock("warning_sign");
        GRANITE = new BlockGranite();
        GRANITE.registerBlock("granite");
        MINERAL = new BlockMineral();
        MINERAL.registerBlock("mineral");
        CONCRETE = new BlockConcrete();
        CONCRETE.registerBlock("concrete");

        COMPRESSED = new HashMap<>();
        ORES = new HashMap<>();
        ArrayList<DustMaterial> materialBuffer = new ArrayList<>();
        int generationIndex = 0;
        for(Material material : Material.MATERIAL_REGISTRY.getObjectsWithIds()) {
            if(material instanceof DustMaterial) {
                materialBuffer.add((DustMaterial) material);
                if(materialBuffer.size() == 16) {
                    createCompressedBlock(materialBuffer, generationIndex);
                    materialBuffer.clear();
                    generationIndex++;
                }
                if(material.hasFlag(DustMaterial.MatFlags.GENERATE_ORE)) {
                    createOreBlock((DustMaterial) material);
                }
            }
        }
        if(!materialBuffer.isEmpty()) {
            createCompressedBlock(materialBuffer, generationIndex);
        }

    }

    private static void createCompressedBlock(Collection<DustMaterial> materials, int index) {
        BlockCompressed block = new BlockCompressed(materials);
        block.registerBlock("compressed_" + index);
        materials.forEach(material -> COMPRESSED.put(material, block));
    }

    private static void createOreBlock(DustMaterial material) {
        BlockOre block = new BlockOre(material);
        block.registerBlock("ore_" + material.toCamelCaseString());
        ORES.put(material, block);
    }

}
