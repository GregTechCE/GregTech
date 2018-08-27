// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientProxy.java

package gregtech.common;

import codechicken.lib.texture.TextureUtils;
import gregtech.api.render.MetaTileEntityRenderer;
import gregtech.common.blocks.*;
import gregtech.common.items.MetaItems;
import gregtech.common.pipelike.fluidpipe.FluidPipeType;
import gregtech.common.render.CableRenderer;
import gregtech.common.render.FluidPipeRenderer;
import gregtech.common.render.StoneRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    public static final IBlockColor FRAME_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) ->
        state.getValue(((BlockFrame) state.getBlock()).variantProperty).materialRGB;

    public static final IItemColor FRAME_ITEM_COLOR = (stack, tintIndex) -> {
        BlockFrame block = (BlockFrame) ((ItemBlock) stack.getItem()).getBlock();
        IBlockState state = block.getStateFromMeta(stack.getItemDamage());
        return state.getValue(block.variantProperty).materialRGB;
    };

    public static final IBlockColor ORE_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) ->
        tintIndex == 1 ? ((BlockOre) state.getBlock()).material.materialRGB : 0xFFFFFF;

    public static final IItemColor ORE_ITEM_COLOR = (stack, tintIndex) ->
        tintIndex == 1 ? ((BlockOre) ((ItemBlock) stack.getItem()).getBlock()).material.materialRGB : 0xFFFFFF;

    public static final IBlockColor SURFACE_ROCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) ->
        state.getValue(((BlockSurfaceRock) state.getBlock()).materialProperty).materialRGB;

    public void onPreLoad() {
        super.onPreLoad();
        MetaTileEntityRenderer.preInit();
        CableRenderer.preInit();
        FluidPipeRenderer.preInit();
        StoneRenderer.preInit();
        MetaEntities.initRenderers();
        TextureUtils.addIconRegister(MetaFluids::registerSprites);
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

}