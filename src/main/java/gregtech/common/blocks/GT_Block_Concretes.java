package gregtech.common.blocks;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.common.blocks.itemblocks.GT_Item_Concretes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class GT_Block_Concretes extends GT_Block_Stones_Abstract {

    public static final AxisAlignedBB CONCRETE_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public GT_Block_Concretes() {
        super("blockconcretes", GT_Item_Concretes.class);
        setResistance(20.0F);
        this.slipperiness = 0.9F;
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Dark Concrete");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Dark Concrete Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Mossy Dark Concrete Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Dark Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Cracked Dark Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Mossy Dark Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Chiseled Dark Concrete");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Smooth Dark Concrete");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".8.name", "Light Concrete");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".9.name", "Light Concrete Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".10.name", "Mossy Light Concrete Cobblestone");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".11.name", "Light Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".12.name", "Cracked Light Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".13.name", "Mossy Light Concrete Bricks");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".14.name", "Chiseled Light Concrete");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".15.name", "Smooth Light Concrete");
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, GT_Values.W));
    }

    @Override
    public Materials[] getMaterials() {
        if (mMaterials == null) {
            mMaterials = new Materials[]{Materials.Concrete};
        }
        return mMaterials;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity aEntity) {
        if (aEntity.onGround && !aEntity.isInWater()) {
            if (aEntity.isSneaking()) {
                aEntity.motionX *= 0.8999999761581421D;
                aEntity.motionZ *= 0.8999999761581421D;
            } else {
                if (aEntity.motionX < 6.0 && aEntity.motionZ < 6.0) {
                    aEntity.motionX *= 1.100000023841858D;
                    aEntity.motionZ *= 1.100000023841858D;
                }
            }
        }
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World aWorld, BlockPos pos) {
        Block tBlock = aWorld.getBlockState(pos.up()).getBlock();
        if (tBlock instanceof IFluidBlock || tBlock instanceof BlockLiquid) {
            return Block.FULL_BLOCK_AABB;
        }
        return CONCRETE_BLOCK_AABB;
    }
}
