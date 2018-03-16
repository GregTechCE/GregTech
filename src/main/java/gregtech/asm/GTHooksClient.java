package gregtech.asm;

import gregtech.api.block.machines.BlockMachine;
import gregtech.api.render.MetaTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GTHooksClient {

    public static boolean renderTileEntityItem(ItemStack itemStack, float partialTicks) {
        Item item = itemStack.getItem();
        if(item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof BlockMachine) {
            MetaTileEntityRenderer.INSTANCE.setRenderingForItemStack(itemStack);
            TileEntityRendererDispatcher.instance.render(MetaTileEntityRenderer.DUMMY_TILE_ENTITY, partialTicks, -1);
            MetaTileEntityRenderer.INSTANCE.setRenderingForItemStack(null);
            return true;
        }
        return false;
    }

}
