package gregtech.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class GT_Server
        extends GT_Proxy {
    public boolean isServerSide() {
        return true;
    }

    public boolean isClientSide() {
        return false;
    }

    public boolean isBukkitSide() {
        return false;
    }

    public void doSonictronSound(ItemStack aStack, World aWorld, double aX, double aY, double aZ) {
    }

    public int addArmor(String aPrefix) {
        return 0;
    }

    public EntityPlayer getThePlayer() {
        return null;
    }
}
