package gregtech.common.items.behaviors;

import gregtech.api.util.GTUtility;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class Behaviour_WrittenBook
        extends Behaviour_None {

    @SideOnly(Side.CLIENT)
    @Override
    public boolean onItemUse(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos blockPos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if ((GTUtility.isStringValid(GTUtility.ItemNBT.getBookTitle(aStack))) && ((aPlayer instanceof EntityPlayerSP))) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiScreenBook(aPlayer, aStack, false));
        }
        return true;
    }

    @Override
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        String tTitle = GTUtility.ItemNBT.getBookTitle(aStack);
        if (GTUtility.isStringValid(tTitle)) {
            aList.add(tTitle);
            aList.add("by " + GTUtility.ItemNBT.getBookAuthor(aStack));
        }
        return aList;
    }

}
