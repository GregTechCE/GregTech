package gregtech.common.render;

import gregtech.api.GTValues;
import gregtech.common.items.MetaItems;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped.ArmPose;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid = GTValues.MODID, value = Side.CLIENT)
public class NanoSaberRenderHandler {

    @SubscribeEvent
    public static void onLivingRender(RenderLivingEvent.Pre<? extends EntityLivingBase> event) {
        if (event.getEntity() instanceof EntityPlayer) {
            RenderPlayer renderPlayer = (RenderPlayer) event.getRenderer();
            ItemStack itemInUse = event.getEntity().getActiveItemStack();
            if (!itemInUse.isEmpty() && MetaItems.NANO_SABER.isItemEqual(itemInUse)) {
                ModelPlayer model = renderPlayer.getMainModel();
                AbstractClientPlayer player = (AbstractClientPlayer) event.getEntity();
                ArmPose targetPose = ArmPose.BLOCK;
                if (player.getPrimaryHand() == EnumHandSide.RIGHT) {
                    model.rightArmPose = targetPose;
                } else {
                    model.leftArmPose = targetPose;
                }
            }
        }
    }
}