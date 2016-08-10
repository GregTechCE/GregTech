package gregtech.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class GT_Entity_Arrow_Potion extends GT_Entity_Arrow {

    private int[] mPotions = new int[0];

    public GT_Entity_Arrow_Potion(World worldIn) {
        super(worldIn);
    }

    public GT_Entity_Arrow_Potion(World worldIn, double x, double y, double z, ItemStack arrowStack, int[] mPotions) {
        super(worldIn, x, y, z, arrowStack);
        this.mPotions = mPotions;
    }

    public GT_Entity_Arrow_Potion(World worldIn, EntityLivingBase shooter, ItemStack arrowStack, int[] mPotions) {
        super(worldIn, shooter, arrowStack);
        this.mPotions = mPotions;
    }

    public void writeEntityToNBT(NBTTagCompound aNBT) {
        super.writeEntityToNBT(aNBT);
        aNBT.setIntArray("mPotions", this.mPotions);
    }

    public void readEntityFromNBT(NBTTagCompound aNBT) {
        super.readEntityFromNBT(aNBT);
        setPotions(aNBT.getIntArray("mPotions"));
    }

    public int[] getPotions() {
        return this.mPotions;
    }

    public void setPotions(int... aPotions) {
        if (aPotions != null) {
            this.mPotions = aPotions;
        }
    }

    @Override
    protected void arrowHit(EntityLivingBase living) {
        for (int i = 3; i < this.mPotions.length; i += 4) {
            if (worldObj.rand.nextInt(100) < this.mPotions[i]) {
                living.addPotionEffect(new PotionEffect(
                        Potion.getPotionById(this.mPotions[(i - 3)]),
                        this.mPotions[(i - 2)],
                        this.mPotions[(i - 1)],
                        false, false));
            }
        }
    }

}
