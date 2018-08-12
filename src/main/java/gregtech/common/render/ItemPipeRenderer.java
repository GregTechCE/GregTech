package gregtech.common.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.uv.IconTransformation;
import com.google.common.collect.Maps;
import gregtech.api.GTValues;
import gregtech.api.pipelike.PipeFactory;
import gregtech.api.render.PipeLikeRenderer;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.common.pipelike.itempipes.ItemPipeFactory;
import gregtech.common.pipelike.itempipes.TypeItemPipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static gregtech.api.pipelike.PipeFactory.MASK_FORMAL_CONNECTION;
import static gregtech.api.pipelike.PipeFactory.MASK_RENDER_SIDE;

@SideOnly(Side.CLIENT)
public class ItemPipeRenderer extends PipeLikeRenderer<TypeItemPipe> {

    public static ItemPipeRenderer INSTANCE = new ItemPipeRenderer();

    private TextureAtlasSprite[] pipeTextures = new TextureAtlasSprite[2];
    private Map<MaterialIconSet, TextureAtlasSprite[]> itemPipeTextures = Maps.newHashMap();

    private ItemPipeRenderer() {
        super(ItemPipeFactory.INSTANCE);
    }

    @Override
    protected void registerIcons(TextureMap map) {
        GTLog.logger.info("Registering item pipe textures.");
        pipeTextures[0] = map.registerSprite(new ResourceLocation(GTValues.MODID, "blocks/pipe/pipe_center"));
        pipeTextures[1] = map.registerSprite(new ResourceLocation(GTValues.MODID, "blocks/pipe/pipe_restrictor"));
        for(MaterialIconSet iconSet : generatedSets) {
            TextureAtlasSprite[] textures = new TextureAtlasSprite[6];
            textures[0] = map.registerSprite(MaterialIconType.pipeTiny.getBlockPath(iconSet));
            textures[1] = map.registerSprite(MaterialIconType.pipeSmall.getBlockPath(iconSet));
            textures[2] = map.registerSprite(MaterialIconType.pipeMedium.getBlockPath(iconSet));
            textures[3] = map.registerSprite(MaterialIconType.pipeLarge.getBlockPath(iconSet));
            textures[4] = map.registerSprite(MaterialIconType.pipeHuge.getBlockPath(iconSet));
            textures[5] = map.registerSprite(MaterialIconType.pipeSide.getBlockPath(iconSet));
            this.itemPipeTextures.put(iconSet, textures);
        }
    }

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        GTLog.logger.info("Injected item pipe render model");
        event.getModelRegistry().putObject(MODEL_LOCATION, this);
    }

    @Override
    public Set<TextureAtlasSprite> getDestroyEffects(IBlockState state, IBlockAccess world, BlockPos pos) {
        return Collections.singleton(itemPipeTextures.get(getBlock(state).material.materialIconSet)[5]);
    }

    @Override
    protected int getDestoryEffectColor(IBlockState state, World world, BlockPos pos) {
        return factory.getMaterialColorForRender(getBlock(state).material);
    }

    @Override
    public void renderBlock(Material material, TypeItemPipe baseProperty, int tileColor, CCRenderState state, IVertexOperation[] pipeline, int renderMask) {
        MaterialIconSet iconSet = material.materialIconSet;
        int materialColor = GTUtility.convertRGBtoOpaqueRGBA_CL(factory.getMaterialColorForRender(material));
        float thickness = baseProperty.getThickness();

        IVertexOperation[][] pipelines = new IVertexOperation[4][];
        ColourMultiplier multiplier = new ColourMultiplier(tileColor == factory.getDefaultColor() ? materialColor : GTUtility.convertRGBtoOpaqueRGBA_CL(tileColor));
        pipelines[0] = ArrayUtils.addAll(pipeline, new IconTransformation(pipeTextures[0]), new ColourMultiplier(materialColor));
        pipelines[1] = ArrayUtils.addAll(pipeline, new IconTransformation(itemPipeTextures.get(iconSet)[baseProperty.index]), multiplier);
        pipelines[2] = ArrayUtils.addAll(pipeline, new IconTransformation(itemPipeTextures.get(iconSet)[5]), multiplier);
        pipelines[3] = baseProperty.isRestrictive ? ArrayUtils.addAll(pipeline, new IconTransformation(pipeTextures[1])) : null;

        Cuboid6 cuboid6 = PipeFactory.getSideBox(null, thickness);
        int mask = renderMask & 0b111111;
        for(EnumFacing renderedSide : EnumFacing.VALUES) {
            if((mask & MASK_FORMAL_CONNECTION << renderedSide.getIndex()) == 0) {
                int oppositeIndex = renderedSide.getOpposite().getIndex();
                if(mask == 0 || ((mask & MASK_FORMAL_CONNECTION << oppositeIndex) != 0 && (mask & ~(MASK_FORMAL_CONNECTION << oppositeIndex)) == 0)) {
                    //if the pipe is isolated or there is something only on opposite side, render overlay + base
                    renderSide(state, pipelines[0], renderedSide, cuboid6);
                    renderSide(state, pipelines[1], renderedSide, cuboid6);
                } else {
                    renderSide(state, pipelines[2], renderedSide, cuboid6);
                    renderSide(state, pipelines[3], renderedSide, cuboid6);
                }
            }
        }

        for (EnumFacing side : EnumFacing.VALUES) renderSideBox(renderMask, state, pipelines, side, thickness);
    }

    private static void renderSideBox(int renderMask, CCRenderState renderState, IVertexOperation[][] pipelines, EnumFacing side, float thickness) {
        if((renderMask & MASK_FORMAL_CONNECTION << side.getIndex()) > 0) {
            boolean renderFrontSide = (renderMask & MASK_RENDER_SIDE << side.getIndex()) > 0;
            Cuboid6 cuboid6 = PipeFactory.getSideBox(side, thickness);
            for(EnumFacing renderedSide : EnumFacing.VALUES) {
                if(renderedSide == side) {
                    if(renderFrontSide) {
                        renderSide(renderState, pipelines[0], renderedSide, cuboid6);
                        renderSide(renderState, pipelines[1], renderedSide, cuboid6);
                    }
                } else if(renderedSide != side.getOpposite()) {
                    renderSide(renderState, pipelines[2], renderedSide, cuboid6);
                    renderSide(renderState, pipelines[3], renderedSide, cuboid6);
                }
            }
        }
    }
}
