package gregtech.common.items.potions;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

class BlockPotionFluid extends BlockFluidFinite {

    private final PotionType potionType;

    public BlockPotionFluid(Fluid fluid, PotionType potionType) {
        super(fluid, Material.WATER);
        this.potionType = potionType;
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!(entityIn instanceof EntityLivingBase) ||
            worldIn.getTotalWorldTime() % 20 != 0) return;
        EntityLivingBase entity = (EntityLivingBase) entityIn;
        for (PotionEffect potionEffect : potionType.getEffects()) {
            if (!potionEffect.getPotion().isInstant()) {
                PotionEffect instantEffect = new PotionEffect(potionEffect.getPotion(), 60, potionEffect.getAmplifier(), true, true);
                entity.addPotionEffect(instantEffect);
            } else {
                potionEffect.getPotion().affectEntity(null, null, entity, potionEffect.getAmplifier(), 1.0);
            }
        }
    }

}
