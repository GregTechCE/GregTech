package gregtech.api.world;

import com.google.common.base.Predicate;
import gregtech.common.blocks.GT_Block_Granites;
import gregtech.common.blocks.GT_Block_Stones;
import gregtech.common.blocks.GT_Block_Stones_Abstract;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IBlockStatePalette;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Random;

public class GT_Worldgen_Constants {


    public static Predicate<IBlockState> STONES = input ->
            input.getBlock() == Blocks.STONE ||
                    input.getBlock() instanceof GT_Block_Granites ||
                    input.getBlock() instanceof GT_Block_Stones;

    public static Predicate<IBlockState> NETHERRACK = input ->
            input.getBlock() == Blocks.NETHERRACK;

    public static Predicate<IBlockState> ENDSTONE = input ->
            input.getBlock() == Blocks.END_STONE;

    public static Predicate<IBlockState> GRAVEL = input ->
            input.getBlock() == Blocks.GRAVEL;

    public static Predicate<IBlockState> SAND = input ->
           input.getBlock() == Blocks.SANDSTONE;


    public static Predicate<IBlockState> ANY = input ->
            STONES.apply(input) || NETHERRACK.apply(input) || ENDSTONE.apply(input) || GRAVEL.apply(input) || SAND.apply(input);

}