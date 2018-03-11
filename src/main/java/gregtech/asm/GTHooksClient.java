package gregtech.asm;

import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class GTHooksClient {

    private static Map<Item, MetaTileEntity> renderingMap = new HashMap<>();

    public static <T extends MetaTileEntity> void registerMetaTileEntityItemRendering(ItemBlock itemBlock, Class<T> tileEntityClass) {
        try {
            T metaTileEntity = tileEntityClass.newInstance();
            ResourceLocation itemModelLocation = metaTileEntity.setupForItemRendering();
            ModelBakery.registerItemVariants(itemBlock, itemModelLocation);
            renderingMap.put(itemBlock, metaTileEntity);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException("Failed to initialize MTE for rendering", exception);
        }
    }

    public static boolean renderTileEntityItem(ItemStack itemStack, float partialTicks) {
        Item item = itemStack.getItem();
        if(!renderingMap.containsKey(item))
            return false;
        MetaTileEntity metaTileEntity = renderingMap.get(item);
        TileEntityRendererDispatcher.instance.render(metaTileEntity, partialTicks, -1);
        return true;
    }

}
