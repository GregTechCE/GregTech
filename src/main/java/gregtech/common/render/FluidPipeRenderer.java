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
import gregtech.common.pipelike.fluidpipes.FluidPipeFactory;
import gregtech.common.pipelike.fluidpipes.TypeFluidPipe;
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
import static gregtech.common.pipelike.fluidpipes.FluidPipeFactory.MASK_RENDER_EXPOSED;

@SideOnly(Side.CLIENT)
public class FluidPipeRenderer extends PipeLikeRenderer<TypeFluidPipe> {

    public static FluidPipeRenderer INSTANCE = new FluidPipeRenderer();

    private TextureAtlasSprite pipeTextures;
    private Map<MaterialIconSet, TextureAtlasSprite[]> fluidPipeTextures = Maps.newHashMap();

    private FluidPipeRenderer() {
        super(FluidPipeFactory.INSTANCE);
    }

    @Override
    protected void registerIcons(TextureMap map) {
        GTLog.logger.info("Registering fluid pipe textures.");
        pipeTextures = map.registerSprite(new ResourceLocation(GTValues.MODID, "blocks/pipe/pipe_center"));
        for(MaterialIconSet iconSet : generatedSets) {
            TextureAtlasSprite[] textures = new TextureAtlasSprite[9];
            textures[0] = map.registerSprite(MaterialIconType.pipeTiny.getBlockPath(iconSet));
            textures[1] = map.registerSprite(MaterialIconType.pipeSmall.getBlockPath(iconSet));
            textures[2] = map.registerSprite(MaterialIconType.pipeMedium.getBlockPath(iconSet));
            textures[3] = map.registerSprite(MaterialIconType.pipeLarge.getBlockPath(iconSet));
            textures[4] = map.registerSprite(MaterialIconType.pipeHuge.getBlockPath(iconSet));
            textures[5] = map.registerSprite(MaterialIconType.pipeQuadruple.getBlockPath(iconSet));
            textures[6] = map.registerSprite(MaterialIconType.pipeNonuple.getBlockPath(iconSet));
            textures[7] = map.registerSprite(MaterialIconType.pipeSexdecuple.getBlockPath(iconSet));
            textures[8] = map.registerSprite(MaterialIconType.pipeSide.getBlockPath(iconSet));
            this.fluidPipeTextures.put(iconSet, textures);
        }
    }

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        GTLog.logger.info("Injected fluid pipe render model");
        event.getModelRegistry().putObject(MODEL_LOCATION, this);
    }

    @Override
    public Set<TextureAtlasSprite> getDestroyEffects(IBlockState state, IBlockAccess world, BlockPos pos) {
        return Collections.singleton(fluidPipeTextures.get(getBlock(state).material.materialIconSet)[8]);
    }

    @Override
    protected int getDestoryEffectColor(IBlockState state, World world, BlockPos pos) {
        return factory.getMaterialColorForRender(getBlock(state).material);
    }

    @Override
    public void renderBlock(Material material, TypeFluidPipe baseProperty, int tileColor, CCRenderState state, IVertexOperation[] pipeline, int renderMask) {
        MaterialIconSet iconSet = material.materialIconSet;
        int materialColor = GTUtility.convertRGBtoOpaqueRGBA_CL(factory.getMaterialColorForRender(material));
        float thickness = baseProperty.getThickness();

        IVertexOperation[][] pipelines = new IVertexOperation[3][];
        ColourMultiplier multiplier = new ColourMultiplier(tileColor == factory.getDefaultColor() ? materialColor : GTUtility.convertRGBtoOpaqueRGBA_CL(tileColor));
        pipelines[0] = ArrayUtils.addAll(pipeline, new IconTransformation(pipeTextures), new ColourMultiplier(materialColor));
        pipelines[1] = ArrayUtils.addAll(pipeline, new IconTransformation(fluidPipeTextures.get(iconSet)[baseProperty.index]), multiplier);
        pipelines[2] = ArrayUtils.addAll(pipeline, new IconTransformation(fluidPipeTextures.get(iconSet)[8]), multiplier);

        Cuboid6 cuboid6 = PipeFactory.getSideBox(null, thickness);
        int mask = renderMask & 0b111111;
        int exposed = renderMask & 0b111111 << 12;
        for(EnumFacing renderedSide : EnumFacing.VALUES) {
            int index = renderedSide.getIndex();
            if((mask & MASK_FORMAL_CONNECTION << index) == 0) {
                int oppositeIndex = renderedSide.getOpposite().getIndex();
                if(mask == 0
                    || (exposed != 0 ? ((exposed & MASK_RENDER_EXPOSED << index) != 0)
                        : ((mask & MASK_FORMAL_CONNECTION << oppositeIndex) != 0 && (mask & ~(MASK_FORMAL_CONNECTION << oppositeIndex)) == 0))) {
                    renderSide(state, pipelines[0], renderedSide, cuboid6);
                    renderSide(state, pipelines[1], renderedSide, cuboid6);
                } else {
                    renderSide(state, pipelines[2], renderedSide, cuboid6);
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
                }
            }
        }
    }
}
