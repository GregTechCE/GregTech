package gregtech.api.unification.ore;

import gregtech.api.unification.material.Materials;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;

public class StoneTypes {

    public static StoneType _NULL = new StoneType(-1, "_null", OrePrefix.ore, Materials.Stone, "", "missingno", () -> Blocks.AIR.getDefaultState());
    
    public static StoneType STONE = new StoneType(0, "stone", OrePrefix.ore, Materials.Stone, "blocks/stone", () -> Blocks.STONE.getDefaultState(), state -> state.getBlock() instanceof BlockStone && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.STONE);
    public static StoneType GRANITE = new StoneType(1, "granite", OrePrefix.ore, Materials.Stone, "blocks/stone_granite", () -> Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), state -> state.getBlock() instanceof BlockStone && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.GRANITE);
    public static StoneType DIORITE = new StoneType(2, "diorite", OrePrefix.ore, Materials.Stone, "blocks/stone_diorite", () -> Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), state -> state.getBlock() instanceof BlockStone && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.DIORITE);
    public static StoneType ANDESITE = new StoneType(3, "andesite", OrePrefix.ore, Materials.Stone, "blocks/stone_andesite", () -> Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), state -> state.getBlock() instanceof BlockStone && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.ANDESITE);
    public static StoneType GRAVEL = new StoneType(4, "gravel", OrePrefix.oreGravel, Materials.Flint, "shovel", 0x4, "blocks/gravel", new String[]{"blocks/gravel"}, () -> Blocks.GRAVEL.getDefaultState(), state -> state.getBlock() == Blocks.GRAVEL);
    public static StoneType BEDROCK = new StoneType(5, "bedrock", OrePrefix.ore, Materials.Stone, 0x2, "blocks/bedrock", new String[]{"blocks/bedrock"},() -> Blocks.BEDROCK.getDefaultState(), state -> state.getBlock() == Blocks.BEDROCK);
    public static StoneType NETHERRACK = new StoneType(6, "netherrack", OrePrefix.oreNetherrack, Materials.Netherrack, "blocks/netherrack", () -> Blocks.NETHERRACK.getDefaultState(), state -> state.getBlock() == Blocks.NETHERRACK);
    public static StoneType ENDSTONE = new StoneType(7, "endstone", OrePrefix.oreEndstone, Materials.Endstone, "blocks/end_stone", () -> Blocks.END_STONE.getDefaultState(), state -> state.getBlock() == Blocks.END_STONE);
    public static StoneType SAND = new StoneType(8, "sand", OrePrefix.oreSand, Materials.SiliconDioxide, "shovel", 0x4, "blocks/sand", new String[]{"blocks/sand"}, () -> Blocks.SAND.getDefaultState(), state -> state.getBlock() instanceof BlockSand && state.getValue(BlockSand.VARIANT) == BlockSand.EnumType.SAND);
    public static StoneType SANDSTONE = new StoneType(9, "sandstone", OrePrefix.oreSand, Materials.SiliconDioxide, 0x1, "blocks/sandstone_normal", new String[]{"blocks/sandstone_bottom", "blocks/sandstone_top", "blocks/sandstone_normal", "blocks/sandstone_normal", "blocks/sandstone_normal", "blocks/sandstone_normal"}, () -> Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.DEFAULT), state -> state.getBlock() instanceof BlockSandStone && state.getValue(BlockSandStone.TYPE) == BlockSandStone.EnumType.DEFAULT);
    public static StoneType REDSAND = new StoneType(10, "red_sand", OrePrefix.oreSand, Materials.SiliconDioxide, "shovel", 0x4, "blocks/red_sand", new String[]{"blocks/red_sand"}, () -> Blocks.SAND.getDefaultState().withProperty(BlockSand.VARIANT, BlockSand.EnumType.RED_SAND), state -> state.getBlock() instanceof BlockSand && state.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND);
    public static StoneType REDSANDSTONE = new StoneType(11, "red_sandstone", OrePrefix.oreSand, Materials.SiliconDioxide, 0x1, "blocks/red_sandstone_normal", new String[]{"blocks/red_sandstone_bottom", "blocks/red_sandstone_top", "blocks/red_sandstone_normal", "blocks/red_sandstone_normal", "blocks/red_sandstone_normal", "blocks/red_sandstone_normal"}, () -> Blocks.RED_SANDSTONE.getDefaultState(), state -> state.getBlock() instanceof BlockRedSandstone && state.getValue(BlockRedSandstone.TYPE) == BlockRedSandstone.EnumType.DEFAULT);
}
