package gregtech.common.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class GT_Entity_Arrow extends EntityArrow {

    private ItemStack arrowStack;

    public GT_Entity_Arrow(World worldIn) {
        super(worldIn);
    }

    public GT_Entity_Arrow(World worldIn, double x, double y, double z, ItemStack arrowStack) {
        super(worldIn, x, y, z);
        this.arrowStack = arrowStack;
    }

    public GT_Entity_Arrow(World worldIn, EntityLivingBase shooter, ItemStack arrowStack) {
        super(worldIn, shooter);
        this.arrowStack = arrowStack;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        if(arrowStack != null) {
            compound.setTag("Item", arrowStack.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("Item")) {
            arrowStack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Item"));
        }
    }

    public void setArrowStack(ItemStack arrowStack) {
        this.arrowStack = arrowStack;
    }

    @Override
    protected ItemStack getArrowStack() {
        return arrowStack;
    }

}
