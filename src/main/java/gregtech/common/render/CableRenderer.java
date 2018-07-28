package gregtech.common.render;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.uv.IconTransformation;
import gregtech.api.GTValues;
import gregtech.api.pipelike.BlockPipeLike;
import gregtech.api.pipelike.PipeFactory;
import gregtech.api.render.PipeLikeRenderer;
import gregtech.api.unification.material.MaterialIconSet;
import gregtech.api.unification.material.MaterialIconType;
import gregtech.api.unification.material.type.Material;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;
import gregtech.common.pipelike.cables.CableFactory;
import gregtech.common.pipelike.cables.Insulation;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static gregtech.api.pipelike.PipeFactory.MASK_FORMAL_CONNECTION;
import static gregtech.api.pipelike.PipeFactory.MASK_RENDER_SIDE;

@SideOnly(Side.CLIENT)
public class CableRenderer extends PipeLikeRenderer<Insulation> {

    public static CableRenderer INSTANCE = new CableRenderer();

    private TextureAtlasSprite[] insulationTextures = new TextureAtlasSprite[6];
    private Map<MaterialIconSet, TextureAtlasSprite> wireTextures = new HashMap<>();

    private CableRenderer() {
        super(CableFactory.INSTANCE);
    }

    @Override
    public void registerIcons(TextureMap map) {
        GTLog.logger.info("Registering cable textures.");
        for(int i = 0; i < insulationTextures.length; i++) {
            ResourceLocation location = new ResourceLocation(GTValues.MODID, "blocks/insulation/insulation_" + i);
            this.insulationTextures[i] = map.registerSprite(location);
        }
        for(MaterialIconSet iconSet : generatedSets) {
            ResourceLocation location = MaterialIconType.wire.getBlockPath(iconSet);
            this.wireTextures.put(iconSet, map.registerSprite(location));
        }
    }

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        GTLog.logger.info("Injected cable render model");
        event.getModelRegistry().putObject(MODEL_LOCATION, this);
    }

    @Override
    public Set<TextureAtlasSprite> getDestroyEffects(IBlockState state, IBlockAccess world, BlockPos pos) {
        BlockPipeLike<Insulation, ?, ?> block = getBlock(state);
        return Collections.singleton(state.getValue(block.getBaseProperty()).isColorable() ? insulationTextures[5] : wireTextures.get(block.material.materialIconSet));
    }

    @Override
    protected int getDestoryEffectColor(IBlockState state, World world, BlockPos pos) {
        BlockPipeLike<Insulation, ?, ?> block = getBlock(state);
        return state.getValue(block.getBaseProperty()).isColorable() ? 0x999999 : block.material.materialRGB;
    }

    @Override
    public void renderBlock(Material material, Insulation baseProperty, int tileColor, CCRenderState state, IVertexOperation[] pipeline, int renderMask) {
        MaterialIconSet iconSet = material.materialIconSet;
        int materialColor = GTUtility.convertRGBtoOpaqueRGBA_CL(material.materialRGB);
        float thickness = baseProperty.getThickness();

        IVertexOperation[][] pipelines = new IVertexOperation[3][];
        pipelines[0] = ArrayUtils.addAll(pipeline, new IconTransformation(wireTextures.get(iconSet)), new ColourMultiplier(materialColor));
        pipelines[1] = pipelines[0];
        pipelines[2] = pipelines[0];

        if(baseProperty.isColorable()) {
            int insulationColor = GTUtility.convertRGBtoOpaqueRGBA_CL(tileColor);
            ColourMultiplier multiplier = new ColourMultiplier(insulationColor);
            pipelines[2] = ArrayUtils.addAll(pipeline, new IconTransformation(insulationTextures[5]), multiplier);
            pipelines[1] = ArrayUtils.addAll(pipeline, new IconTransformation(insulationTextures[baseProperty.insulationLevel]), multiplier);
        }

        Cuboid6 cuboid6 = PipeFactory.getSideBox(null, thickness);
        int mask = renderMask & 0b111111;
        for(EnumFacing renderedSide : EnumFacing.VALUES) {
            if((mask & MASK_FORMAL_CONNECTION << renderedSide.getIndex()) == 0) {
                int oppositeIndex = renderedSide.getOpposite().getIndex();
                if((mask & MASK_FORMAL_CONNECTION << oppositeIndex) != 0 && (mask & ~(MASK_FORMAL_CONNECTION << oppositeIndex)) == 0) {
                    //if there is something only on opposite side, render overlay + base
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
