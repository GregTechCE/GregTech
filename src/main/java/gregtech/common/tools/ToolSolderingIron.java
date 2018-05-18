package gregtech.common.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ToolSolderingIron extends ToolBase {

    @Override
    public float getNormalDamageBonus(EntityLivingBase entity, ItemStack stack, EntityLivingBase attacker) {
        String name = entity.getClass().getName();
        name = name.substring(name.lastIndexOf('.') + 1);
        return name.toLowerCase().contains("spider") ? 2.0F : 1.0F;
    }

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 10;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 5;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 10;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 5;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 1.5F;
    }

    @Override
    public boolean isMinableBlock(IBlockState block, ItemStack stack) {
        return block.getMaterial() == Material.CIRCUITS;
    }

}
