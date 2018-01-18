package gregtech.api.unification.ore;

import gregtech.api.unification.material.Materials;
import gregtech.common.blocks.BlockGranite;
import gregtech.common.blocks.BlockMineral;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;

public class StoneTypes {

    public static StoneType _NULL = new StoneType(-1, "_null", OrePrefix.ore, Materials.Stone, "missingno");
    //vanilla ones
    public static StoneType STONE = new StoneType(0, "stone", OrePrefix.ore, Materials.Stone, "blocks/stone", state -> state.getBlock() instanceof BlockStone && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.STONE);
    public static StoneType NETHERRACK = new StoneType(1, "netherrack", OrePrefix.oreNetherrack, Materials.Netherrack, "blocks/netherrack", state -> state.getBlock() == Blocks.NETHERRACK);
    public static StoneType ENDSTONE = new StoneType(2, "endstone", OrePrefix.oreEndstone, Materials.Endstone, "blocks/end_stone", state -> state.getBlock() == Blocks.END_STONE);
    public static StoneType SANDSTONE = new StoneType(3, "sandstone", OrePrefix.oreSand, Materials.SiliconDioxide, 0x1, "blocks/sandstone_normal", new String[]{"blocks/sandstone_bottom", "blocks/sandstone_top", "blocks/sandstone_normal", "blocks/sandstone_normal", "blocks/sandstone_normal", "blocks/sandstone_normal"}, state -> state.getBlock() instanceof BlockSandStone && state.getValue(BlockSandStone.TYPE) == BlockSandStone.EnumType.DEFAULT);

    //gt ones
    public static StoneType BLACK_GRANITE = new StoneType(4, "black_granite", OrePrefix.oreBlackgranite, Materials.GraniteBlack, "gregtech:blocks/iconsets/granite_black_stone", state -> state.getBlock() instanceof BlockGranite && ((BlockGranite) state.getBlock()).getVariant(state) == BlockGranite.GraniteVariant.BLACK_GRANITE);
    public static StoneType RED_GRANITE = new StoneType(5, "red_granite", OrePrefix.oreRedgranite, Materials.GraniteRed, "gregtech:blocks/iconsets/granite_red_stone", state -> state.getBlock() instanceof BlockGranite && ((BlockGranite) state.getBlock()).getVariant(state) == BlockGranite.GraniteVariant.RED_GRANITE);
    public static StoneType MARBLE = new StoneType(6, "marble", OrePrefix.oreMarble, Materials.Marble, "gregtech:blocks/iconsets/marble_stone", state -> state.getBlock() instanceof BlockMineral && ((BlockMineral) state.getBlock()).getVariant(state) == BlockMineral.MineralVariant.MARBLE);
    public static StoneType BASALT = new StoneType(7, "basalt", OrePrefix.oreBasalt, Materials.Basalt, "gregtech:blocks/iconsets/basalt_stone", state -> state.getBlock() instanceof BlockMineral && ((BlockMineral) state.getBlock()).getVariant(state) == BlockMineral.MineralVariant.BASALT);

    public static StoneType GRANITE = new StoneType(8, "granite", OrePrefix.ore, Materials.Stone, "blocks/stone_granite", state -> state.getBlock() instanceof BlockStone && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.GRANITE);
    public static StoneType DIORITE = new StoneType(9, "diorite", OrePrefix.ore, Materials.Stone, "blocks/stone_diorite", state -> state.getBlock() instanceof BlockStone && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.DIORITE);
    public static StoneType ANDESITE = new StoneType(10, "andesite", OrePrefix.ore, Materials.Stone, "blocks/stone_andesite", state -> state.getBlock() instanceof BlockStone && state.getValue(BlockStone.VARIANT) == BlockStone.EnumType.ANDESITE);
    public static StoneType SAND = new StoneType(11, "sand", OrePrefix.oreSand, Materials.SiliconDioxide, "shovel", 0x4, "blocks/sand", new String[]{"blocks/sand"}, state -> state.getBlock() instanceof BlockSand && state.getValue(BlockSand.VARIANT) == BlockSand.EnumType.SAND);
    public static StoneType REDSAND = new StoneType(12, "red_sand", OrePrefix.oreSand, Materials.SiliconDioxide, "shovel", 0x4, "blocks/red_sand", new String[]{"blocks/red_sand"}, state -> state.getBlock() instanceof BlockSand && state.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND);
    public static StoneType GRAVEL = new StoneType(13, "gravel", OrePrefix.oreGravel, Materials.Flint, "shovel", 0x4, "blocks/gravel", new String[]{"blocks/gravel"}, state -> state.getBlock() == Blocks.GRAVEL);
    public static StoneType REDSANDSTONE = new StoneType(14, "red_sandstone", OrePrefix.oreSand, Materials.SiliconDioxide, 0x1, "blocks/red_sandstone_normal", new String[]{"blocks/red_sandstone_bottom", "blocks/red_sandstone_top", "blocks/red_sandstone_normal", "blocks/red_sandstone_normal", "blocks/red_sandstone_normal", "blocks/red_sandstone_normal"}, state -> state.getBlock() instanceof BlockRedSandstone && state.getValue(BlockRedSandstone.TYPE) == BlockRedSandstone.EnumType.DEFAULT);
    public static StoneType BEDROCK = new StoneType(15, "bedrock", OrePrefix.ore, Materials.Stone, 0x2, "blocks/bedrock", new String[]{"blocks/bedrock"}, state -> state.getBlock() == Blocks.BEDROCK);
}
