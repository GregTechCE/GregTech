package gregtech.api.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.ore.StoneType;
import gregtech.api.unification.ore.StoneTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public abstract class GTWorldGen implements Comparable<GTWorldGen> {

    public boolean enabled;
    public final String name;
    public final int sortingWeight;
    private final String[] dimWhiteList, biomeWhiteList;
    /**
     * @param name              Name of this world generator
     * @param enabled           Set true to enable this generator
     * @param sortingWeight     For sorting the generators if registered into {@linkplain gregtech.api.GregTechAPI#worldgenList GregTechAPI.worldgenList}}
     * @param registryList      The generator will be appended into this list
     * @param dimWhiteList      Dimensions allowed to execute this generator.
     *                          Use:Integer to match the dimension's id;
     *                              "@" + Integer to match the dimension's type;
     *                              String to match the dimension's name;
     *                              "?" + String to match the dimensions whose name contains such String;
     *                              "@" + String to match the dimensions whose provider class' name contains such String.
     * @param biomeWhiteList    Biomes allowed to execute this generator.
     *                          Use "all" for all the biomes; String to match the biome's name; "?" + String to match the biomes whose name contains such String.
     */
    public GTWorldGen(String name, boolean enabled, int sortingWeight, List registryList, String[] dimWhiteList, String[] biomeWhiteList) {
        this.enabled = enabled;
        this.name = name;
        this.sortingWeight = sortingWeight;
        this.dimWhiteList = Arrays.stream(dimWhiteList).filter(GTUtility::isStringValid).toArray(String[]::new);
        this.biomeWhiteList = Arrays.stream(biomeWhiteList).filter(GTUtility::isStringValid).toArray(String[]::new);
        if (this.enabled) registryList.add(this);
    }

    public boolean isGenerationAllowed(World world) {
        String dimType = '@' + Integer.toString(world.provider.getDimensionType().getId());
        String dimID = Integer.toString(world.provider.getDimension());
        String dimName = world.provider.getDimensionType().getName().toLowerCase();
        String dimClassName = world.provider.getClass().getName();
        dimClassName = dimClassName.substring(dimClassName.lastIndexOf('.') + 1).toLowerCase();
        for (String s : dimWhiteList) {
            if (s.equals(dimID) || s.equals(dimType) || s.equals(dimName)) return true;
            if (s.startsWith("?") && dimName.contains(s.substring(1))) return true;
            if (s.startsWith("@") && dimClassName.contains(s.substring(1))) return true;
        }
        return false;
    }

    public boolean isGenerationAllowed(Biome biome) {
        String biomeName = ObfuscationReflectionHelper.getPrivateValue(Biome.class, biome, 17); //biome.biomeName
        biomeName = biomeName.toLowerCase();
        for (String s : biomeWhiteList) {
            if (s.equals("all") || biomeName.equals(s)) return true;
            if (s.startsWith("?") && biomeName.contains(s.substring(1))) return true;
        }
        return false;
    }

    public boolean isGenerationAllowed(World world, Biome biome) {
        return isGenerationAllowed(world) && isGenerationAllowed(biome);
    }

    public abstract void generate(Random random, int chunkX, int chunkZ, World world, Biome biome, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider);

    @Override
    public int compareTo(GTWorldGen o) {
        return this.sortingWeight - o.sortingWeight;
    }

    public static void generateOreBlock(World world, BlockPos pos, DustMaterial material, boolean small, boolean force) {
        IBlockState target = world.getBlockState(pos);
        if (target.getBlock() == Blocks.AIR && !force) return;
        StoneType stoneType = StoneType.computeStoneType(target);
        if (stoneType == StoneTypes._NULL) {
            if (force) stoneType = StoneTypes.STONE;
            else return;
        }
        getOreBlock(material, stoneType, small).ifPresent(ore -> world.setBlockState(pos, ore, 18));
    }

    public static Optional<IBlockState> getOreBlock(DustMaterial material, StoneType stoneType, boolean small) {
        return Optional.ofNullable(GregTechAPI.oreBlockTable.get(material))
                .map(oreBlocks -> oreBlocks.get(stoneType))
                .map(oreBlock -> oreBlock.getOreBlock(stoneType, small));
    }
}
