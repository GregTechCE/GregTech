package gregtech.common.covers;

import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.util.GT_CoverBehavior;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.network.play.server.S2DPacketOpenWindow;

public class GT_Cover_Crafting
        extends GT_CoverBehavior {
    public boolean onCoverRightclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        if ((aPlayer instanceof EntityPlayerMP)) {
            ((EntityPlayerMP) aPlayer).getNextWindowId();
            ((EntityPlayerMP) aPlayer).playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(((EntityPlayerMP) aPlayer).currentWindowId, 1, "Crafting", 9, true));
            ((EntityPlayerMP) aPlayer).openContainer = new ContainerWorkbench(((EntityPlayerMP) aPlayer).inventory, ((EntityPlayerMP) aPlayer).worldObj, aTileEntity.getXCoord(), aTileEntity.getYCoord(), aTileEntity.getZCoord()) {
                public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
                    return true;
                }
            };
            ((EntityPlayerMP) aPlayer).openContainer.windowId = ((EntityPlayerMP) aPlayer).currentWindowId;
            ((EntityPlayerMP) aPlayer).openContainer.addCraftingToCrafters((EntityPlayerMP) aPlayer);
        }
        return true;
    }
}
