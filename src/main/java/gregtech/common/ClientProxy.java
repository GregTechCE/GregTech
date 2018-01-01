// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientProxy.java

package gregtech.common;

import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

//    private static void drawGrid(DrawBlockHighlightEvent aEvent) {
//        GL11.glPushMatrix();
//        EntityPlayer player = aEvent.getPlayer();
//        float partialTicks = aEvent.getPartialTicks();
//        double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
//        double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
//        double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
//        BlockPos pos = aEvent.getTarget().getBlockPos();
//        GL11.glTranslated(-d0, -d1, -d2);
//        GL11.glTranslated(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
////        Rotation.sideRotations[aEvent.getTarget().sideHit.getIndex()].glApply();
//        GL11.glTranslated(0.0D, -0.501D, 0.0D);
//        GL11.glLineWidth(2.0F);
//        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5F);
//        GL11.glBegin(1);
//        GL11.glVertex3d(0.5D, 0.0D, -0.25D);
//        GL11.glVertex3d(-0.5D, 0.0D, -0.25D);
//        GL11.glVertex3d(0.5D, 0.0D, 0.25D);
//        GL11.glVertex3d(-0.5D, 0.0D, 0.25D);
//        GL11.glVertex3d(0.25D, 0.0D, -0.5D);
//        GL11.glVertex3d(0.25D, 0.0D, 0.5D);
//        GL11.glVertex3d(-0.25D, 0.0D, -0.5D);
//        GL11.glVertex3d(-0.25D, 0.0D, 0.5D);
//        GL11.glEnd();
//        GL11.glPopMatrix();
//    }

    public boolean isServerSide() {
        return true;
    }

    public boolean isClientSide() {
        return true;
    }

    public void onPreLoad() {
        super.onPreLoad();
//        ModelLoaderRegistry.registerLoader(MTEModelLoader.INSTANCE);
//        MTEModelLoader.INSTANCE.addMTE2ModelMapping(new GTResourceLocation("block/basic_mte"), new MetaTileEntityModel(MetaTileEntityBakedModel::new));
    }

    @Override
    public void onLoad() {
        super.onLoad();
        registerColors();
    }

    @Override
    public void onPostLoad() {
        super.onPostLoad();
    }

    public void registerColors() {
        MetaBlocks.registerColors();
        MetaItems.registerColors();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        MetaBlocks.registerStateMappers();
        MetaBlocks.registerItemModels();
        MetaItems.registerModels();
    }

//    @SubscribeEvent
//    public void onTextureStitchPre(TextureStitchEvent.Pre event) {
//        TextureMap map = event.getMap();
//        map.registerSprite(new GTResourceLocation("textures/gui/slot_bronze_furnace_background.png"));
//    }

//    @SubscribeEvent
//    public void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
//        if(event.getPlayer() != null && event.getTarget().typeOfHit == RayTraceResult.Type.BLOCK && event.getTarget().getBlockPos() != null) {
//            EntityPlayer player = event.getPlayer();
//            ItemStack currentItem = player.getHeldItemMainhand();
//            BlockPos pos = event.getTarget().getBlockPos();
//            Block block = player.worldObj.getBlockState(pos).getBlock();
//            if (GTUtility.isStackValid(currentItem)) {
//                TileEntity aTileEntity = player.worldObj.getTileEntity(pos);
//                if (((aTileEntity instanceof BaseMetaPipeEntity)) && (((ICoverable) aTileEntity).getCoverIDAtSide((byte) event.getTarget().sideHit.getIndex()) == 0) && ((GTUtility.isStackInList(currentItem, GregTechAPI.sCoverItems.keySet())) || (GTUtility.isStackInList(currentItem, GregTechAPI.sCrowbarList)) || (GTUtility.isStackInList(currentItem, GregTechAPI.sScrewdriverList)))) {
//                    drawGrid(event);
//                }
//                else if ((aTileEntity instanceof ITurnable || aTileEntity instanceof IWrenchable || ROTATABLE_VANILLA_BLOCKS.contains(block)) && GTUtility.isStackInList(currentItem, GregTechAPI.sWrenchList)) {
//                    drawGrid(event);
//                }
//            }
//        }
//    }
}