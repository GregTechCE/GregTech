package gregtech.common;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class GT_MinableOreGenerator
        extends WorldGenerator {
    private Block minableBlockId;
    private Block mBlock;
    private int minableBlockMeta = 0;
    private int numberOfBlocks;
    private boolean allowVoid = false;

    public GT_MinableOreGenerator(Block par1, int par2) {
        this.minableBlockId = par1;
        this.numberOfBlocks = par2;
    }

    public GT_MinableOreGenerator(Block id, int meta, int number, boolean aAllowVoid, Block aBlock) {
        this(id, number);
        this.minableBlockMeta = meta;
        this.allowVoid = aAllowVoid;
        this.mBlock = aBlock;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        float var6 = par2Random.nextFloat() * 3.141593F;
        double var7 = par3 + 8 + MathHelper.sin(var6) * this.numberOfBlocks / 8.0F;
        double var9 = par3 + 8 - MathHelper.sin(var6) * this.numberOfBlocks / 8.0F;
        double var11 = par5 + 8 + MathHelper.cos(var6) * this.numberOfBlocks / 8.0F;
        double var13 = par5 + 8 - MathHelper.cos(var6) * this.numberOfBlocks / 8.0F;
        double var15 = par4 + par2Random.nextInt(3) - 2;
        double var17 = par4 + par2Random.nextInt(3) - 2;
        for (int var19 = 0; var19 <= this.numberOfBlocks; var19++) {
            double var20 = var7 + (var9 - var7) * var19 / this.numberOfBlocks;
            double var22 = var15 + (var17 - var15) * var19 / this.numberOfBlocks;
            double var24 = var11 + (var13 - var11) * var19 / this.numberOfBlocks;
            double var26 = par2Random.nextDouble() * this.numberOfBlocks / 16.0D;
            double var28 = (MathHelper.sin(var19 * 3.141593F / this.numberOfBlocks) + 1.0F) * var26 + 1.0D;
            double var30 = (MathHelper.sin(var19 * 3.141593F / this.numberOfBlocks) + 1.0F) * var26 + 1.0D;
            int var32 = MathHelper.floor_double(var20 - var28 / 2.0D);
            int var33 = MathHelper.floor_double(var22 - var30 / 2.0D);
            int var34 = MathHelper.floor_double(var24 - var28 / 2.0D);
            int var35 = MathHelper.floor_double(var20 + var28 / 2.0D);
            int var36 = MathHelper.floor_double(var22 + var30 / 2.0D);
            int var37 = MathHelper.floor_double(var24 + var28 / 2.0D);
            for (int var38 = var32; var38 <= var35; var38++) {
                double var39 = (var38 + 0.5D - var20) / (var28 / 2.0D);
                if (var39 * var39 < 1.0D) {
                    for (int var41 = var33; var41 <= var36; var41++) {
                        double var42 = (var41 + 0.5D - var22) / (var30 / 2.0D);
                        if (var39 * var39 + var42 * var42 < 1.0D) {
                            for (int var44 = var34; var44 <= var37; var44++) {
                                double var45 = (var44 + 0.5D - var24) / (var28 / 2.0D);
                                Block block = par1World.getBlock(var38, var41, var44);
                                if ((var39 * var39 + var42 * var42 + var45 * var45 < 1.0D) && (((this.allowVoid) && (par1World.getBlock(var38, var41, var44) == Blocks.air)) || ((block != null) && (block.isReplaceableOreGen(par1World, var38, var41, var44, this.mBlock))))) {
                                    par1World.setBlock(var38, var41, var44, this.minableBlockId, this.minableBlockMeta, 0);
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
