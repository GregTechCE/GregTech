package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class GT_Cover_Crafting extends GT_CoverBehavior {

    public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if ((aPlayer instanceof EntityPlayerMP)) {
            EntityPlayerMP playerMP = (EntityPlayerMP) aPlayer;
            playerMP.getNextWindowId();

            playerMP.connection.sendPacket(new SPacketOpenWindow(playerMP.currentWindowId, "minecraft:crafting_table", new TextComponentString("Crafting")));
            playerMP.openContainer = new ContainerWorkbench(playerMP.inventory, playerMP.worldObj, new BlockPos(playerMP)) {
                @Override
                public boolean canInteractWith(EntityPlayer playerIn) {
                    return true;
                }
            };
            playerMP.openContainer.windowId = playerMP.currentWindowId;
            playerMP.openContainer.addListener(playerMP);
        }
        return true;
    }
}
