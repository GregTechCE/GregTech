package gregtech.api.util;

import gregtech.common.blocks.MetaBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashSet;
import java.util.Set;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber
public class DebugRenderer {

    public static Set<BlockPos> blockPosSet = new HashSet<>();

    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        RenderGlobal context = event.getContext();
        float partialTicks = event.getPartialTicks();
        EntityPlayer player = Minecraft.getMinecraft().player;
        WorldClient world = Minecraft.getMinecraft().world;

        if (blockPosSet.size() == 0) {
            return;
        }

        double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate(-playerX, -playerY, -playerZ);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(2.5F);

        {
            blockPosSet.forEach(blockPos -> {
                double distance = MathHelper.sqrt(player.getDistanceSqToCenter(blockPos));
                if (distance > 64F) {
                    return;
                }

                AxisAlignedBB box = new AxisAlignedBB(blockPos.getX() + 0.25,
                    blockPos.getY() + 0.25,
                    blockPos.getZ() + 0.25,
                    blockPos.getX() + 0.75,
                    blockPos.getY() + 0.75,
                    blockPos.getZ() + 0.75);
                RenderGlobal.drawSelectionBoundingBox(box, 0.0F, 1F, 0.0F, 0.5F);

                TileEntity tileEntity = world.getTileEntity(blockPos);

                if (tileEntity != null) {
                    AxisAlignedBB axisAlignedBB = new AxisAlignedBB(blockPos.getX() + 0.25,
                        blockPos.getY() + 0.25,
                        blockPos.getZ() + 0.25,
                        blockPos.getX() + 0.75,
                        blockPos.getY() + 0.75,
                        blockPos.getZ() + 0.75);
                    RenderGlobal.drawSelectionBoundingBox(axisAlignedBB, 0.0F, 0.0F, 1F, 0.5F);

//                    ((TileEntityCableEmitter) tileEntity).outgoingConnections.forEach(connectionInfo -> {
//                        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(connectionInfo.receiverContainer.getPos()), 0.0F, 1F, 1F, 0.5F);
//
//                    });
                }
            });
        }

        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.PlaceEvent event) {
        if (event.getPlacedBlock().getBlock() == MetaBlocks.CABLE) {
//            blockPosSet.add(event.getPos());
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
//        blockPosSet.remove(event.getPos());
    }
}

