// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClientProxy.java

package gregtech.common;

import codechicken.lib.texture.TextureUtils;
import com.mojang.realmsclient.gui.ChatFormatting;
import gregtech.api.render.MetaTileEntityRenderer;
import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.stack.UnificationEntry;
import gregtech.common.blocks.*;
import gregtech.common.blocks.surfacerock.BlockSurfaceRock;
import gregtech.common.blocks.surfacerock.BlockSurfaceRockFlooded;
import gregtech.common.items.MetaItems;
import gregtech.common.render.CableRenderer;
import gregtech.common.render.FluidPipeRenderer;
import gregtech.common.render.StoneRenderer;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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

    public static final IBlockColor FRAME_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) -> {
        Material material = ((BlockFrame) state.getBlock()).frameMaterial;
        EnumDyeColor dyeColor = state.getValue(BlockColored.COLOR);
        return dyeColor == EnumDyeColor.WHITE ? material.materialRGB : dyeColor.colorValue;
    };

    public static final IItemColor FRAME_ITEM_COLOR = (stack, tintIndex) -> {
        IBlockState frameState = ((FrameItemBlock) stack.getItem()).getBlockState(stack);
        BlockFrame block = (BlockFrame) frameState.getBlock();
        EnumDyeColor dyeColor = frameState.getValue(BlockColored.COLOR);
        return dyeColor == EnumDyeColor.WHITE ? block.frameMaterial.materialRGB : dyeColor.colorValue;
    };

    public static final IBlockColor ORE_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) ->
        tintIndex == 1 ? ((BlockOre) state.getBlock()).material.materialRGB : 0xFFFFFF;

    public static final IItemColor ORE_ITEM_COLOR = (stack, tintIndex) ->
        tintIndex == 1 ? ((BlockOre) ((ItemBlock) stack.getItem()).getBlock()).material.materialRGB : 0xFFFFFF;

    public static final IBlockColor FOAM_BLOCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) ->
        state.getValue(BlockColored.COLOR).colorValue;

    public static final IBlockColor SURFACE_ROCK_COLOR = (IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) -> {
        if(tintIndex == 1) {
            if(state.getBlock() instanceof BlockSurfaceRock) {
                BlockSurfaceRock surfaceRock = (BlockSurfaceRock) state.getBlock();
                return state.getValue(surfaceRock.materialProperty).materialRGB;
            } else if(state.getBlock() instanceof BlockSurfaceRockFlooded) {
                BlockSurfaceRockFlooded surfaceRock = (BlockSurfaceRockFlooded) state.getBlock();
                return state.getValue(surfaceRock.materialProperty).materialRGB;
            } else return 0xFFFFFF;
        } else {
            //flooded surface rock water variant
            return BiomeColorHelper.getWaterColorAtPos(worldIn, pos);
        }
    };

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

    @SubscribeEvent
    public static void addMaterialFormulaHandler(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if(!(itemStack.getItem() instanceof ItemBlock)) {
            UnificationEntry unificationEntry = OreDictUnifier.getUnificationEntry(itemStack);
            if(unificationEntry != null && unificationEntry.material != null) {
                String formula = unificationEntry.material.chemicalFormula;
                if(formula != null && !formula.isEmpty() && !formula.equals("?")) {
                    event.getToolTip().add(1, ChatFormatting.GRAY.toString() + unificationEntry.material.chemicalFormula);
                }
            }
        }
    }

}