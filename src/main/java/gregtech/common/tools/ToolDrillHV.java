package gregtech.common.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ToolDrillHV extends ToolDrillLV {

    @Override
    public int getToolDamagePerBlockBreak(ItemStack stack) {
        return 800;
    }

    @Override
    public int getToolDamagePerDropConversion(ItemStack stack) {
        return 1600;
    }

    @Override
    public int getToolDamagePerContainerCraft(ItemStack stack) {
        return 12800;
    }

    @Override
    public int getToolDamagePerEntityAttack(ItemStack stack) {
        return 3200;
    }

    @Override
    public int getBaseQuality(ItemStack stack) {
        return 1;
    }

    @Override
    public float getBaseDamage(ItemStack stack) {
        return 3.0F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack stack) {
        return 9.0F;
    }

    @Override
    public float getMaxDurabilityMultiplier(ItemStack stack) {
        return 4.0F;
    }

    @Override
    public void onToolCrafted(ItemStack stack, EntityPlayer player) {
        super.onToolCrafted(stack, player);
//        GregTechMod.achievements.issueAchievement(player, "highpowerdrill"); // TODO ACHIEVEMENTS/ADVANCEMENTS
//        GregTechMod.achievements.issueAchievement(player, "buildDDrill");
    }
}
