package gregtech.common.blocks;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

public class GT_Block_Concretes
        extends GT_Block_Stones_Abstract {
    public GT_Block_Concretes() {
        super(GT_Item_Concretes.class, "gt.blockconcretes");
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
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 0));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 1));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 2));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 3));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 4));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 5));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 6));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 7));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 8));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 9));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 10));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 11));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 12));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 13));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 14));
        GT_OreDictUnificator.registerOre(OrePrefixes.stone, Materials.Concrete, new ItemStack(this, 1, 15));
    }

    public int getHarvestLevel(int aMeta) {
        return 1;
    }

    public float getBlockHardness(World aWorld, int aX, int aY, int aZ) {
        return this.blockHardness = Blocks.stone.getBlockHardness(aWorld, aX, aY, aZ);
    }

    public IIcon getIcon(int aSide, int aMeta) {
        if ((aMeta >= 0) && (aMeta < 16)) {
            return gregtech.api.enums.Textures.BlockIcons.CONCRETES[aMeta].getIcon();
        }
        return gregtech.api.enums.Textures.BlockIcons.CONCRETES[0].getIcon();
    }

    public void onEntityCollidedWithBlock(World aWorld, int aX, int aY, int aZ, Entity aEntity) {
        Block tBlock = aWorld.getBlock(aX, aY + 1, aZ);
        if (((aEntity instanceof EntityLivingBase)) && (!(tBlock instanceof IFluidBlock)) && (!(tBlock instanceof BlockLiquid)) && (aEntity.onGround) && (!aEntity.isInWater()) && (!aEntity.isWet())) {
            if (aEntity.isSneaking()) {
                aEntity.motionX *= 0.8999999761581421D;
                aEntity.motionZ *= 0.8999999761581421D;
            } else {
                aEntity.motionX *= 1.100000023841858D;
                aEntity.motionZ *= 1.100000023841858D;
            }
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ) {
        Block tBlock = aWorld.getBlock(aX, aY + 1, aZ);
        if (((tBlock instanceof IFluidBlock)) || ((tBlock instanceof BlockLiquid))) {
            return super.getCollisionBoundingBoxFromPool(aWorld, aX, aY, aZ);
        }
        return AxisAlignedBB.getBoundingBox(aX, aY, aZ, aX + 1, aY + 0.875D, aZ + 1);
    }
}
