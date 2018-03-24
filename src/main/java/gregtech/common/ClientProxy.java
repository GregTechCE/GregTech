// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientProxy.java

package gregtech.common;

import codechicken.lib.render.item.CCRenderItem;
import gregtech.api.block.machines.BlockMachine;
import gregtech.api.capability.ICustomHighlightBlock;
import gregtech.api.render.MetaTileEntityRenderer;
import gregtech.common.blocks.BlockCompressed;
import gregtech.common.blocks.BlockOre;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;
import java.util.Map.Entry;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public static final IBlockColor COMPRESSED_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) ->
        state.getValue(((BlockCompressed) state.getBlock()).variantProperty).materialRGB;

    public static final IItemColor COMPRESSED_ITEM_COLOR = (stack, tintIndex) -> {
        BlockCompressed block = (BlockCompressed) ((ItemBlock) stack.getItem()).getBlock();
        IBlockState state = block.getStateFromMeta(stack.getItemDamage());
        return state.getValue(block.variantProperty).materialRGB;
    };

    public static final IBlockColor ORE_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) ->
        tintIndex == 1 ? ((BlockOre) state.getBlock()).material.materialRGB : 0xFFFFFF;

    public static final IItemColor ORE_ITEM_COLOR = (stack, tintIndex) ->
        tintIndex == 1 ? ((BlockOre) ((ItemBlock) stack.getItem()).getBlock()).material.materialRGB : 0xFFFFFF;

    public boolean isServerSide() {
        return true;
    }

    public boolean isClientSide() {
        return true;
    }

    public void onPreLoad() {
        super.onPreLoad();
        MetaTileEntityRenderer.preInit();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        registerColors();
    }

    @Override
    public void onPostLoad() {
        super.onPostLoad();
        MetaTileEntityRenderer.postInit();
        for(Entry<BlockMachine<?>, ItemBlock> machineEntry : BlockMachine.MACHINES.entrySet()) {
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(machineEntry.getValue(),
                stack -> MetaTileEntityRenderer.MODEL_LOCATION);
            ModelLoader.setCustomStateMapper(machineEntry.getKey(), new DefaultStateMapper() {
                @Override
                protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                    return MetaTileEntityRenderer.MODEL_LOCATION;
                }
            });
        }
    }

    public void registerColors() {
        MetaBlocks.registerColors();
        MetaItems.registerColors();
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        MetaBlocks.registerStateMappers();
        MetaBlocks.registerItemModels();
        MetaItems.registerModels();
    }

    @SubscribeEvent
    public void onBlockHighlight(DrawBlockHighlightEvent event) {
        if(drawSelectionMultiBoundingBox(event.getPlayer(), event.getTarget(), event.getSubID(), event.getPartialTicks()))
            event.setCanceled(true);
    }

    public boolean drawSelectionMultiBoundingBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks) {
        if (execute == 0 && movingObjectPositionIn.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            IBlockState iblockstate = player.world.getBlockState(blockpos);
            if(!(iblockstate.getBlock() instanceof ICustomHighlightBlock))
                return false;
            ICustomHighlightBlock highlightBlock = (ICustomHighlightBlock) iblockstate.getBlock();
            Collection<AxisAlignedBB> bbCollection = highlightBlock.getSelectedBoundingBoxes(player.world, blockpos, iblockstate);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth(2.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);

            if (iblockstate.getMaterial() != Material.AIR && player.world.getWorldBorder().contains(blockpos)) {
                double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
                double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
                double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
                for(AxisAlignedBB axisAlignedBB : bbCollection) {
                    RenderGlobal.drawSelectionBoundingBox(axisAlignedBB .grow(0.0020000000949949026D)
                        .offset(-d3, -d4, -d5), 0.0F, 0.0F, 0.0F, 0.4F);
                }
            }
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            return true;
        }
        return false;
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
}