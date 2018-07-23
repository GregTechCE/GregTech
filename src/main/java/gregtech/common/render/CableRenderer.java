package gregtech.common.render;

import codechicken.lib.render.BlockRenderer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.block.BlockRenderingRegistry;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.texture.TextureUtils;
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
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.pipelike.CableFactory;
import gregtech.common.pipelike.Insulation;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public class CableRenderer extends PipeLikeRenderer<Insulation> {

    public static ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(new ResourceLocation(GTValues.MODID, "cable"), "normal");
    public static CableRenderer INSTANCE = new CableRenderer();
    public static EnumBlockRenderType BLOCK_RENDER_TYPE;

    private TextureAtlasSprite[] insulationTextures = new TextureAtlasSprite[6];
    private Set<MaterialIconSet> generatedSets = new HashSet<>();
    private Map<MaterialIconSet, TextureAtlasSprite> wireTextures = new HashMap<>();

    private CableRenderer() {
        super(CableFactory.INSTANCE);
    }

    public static void preInit() {
        BLOCK_RENDER_TYPE = BlockRenderingRegistry.createRenderType("gt_cable");
        BlockRenderingRegistry.registerRenderer(BLOCK_RENDER_TYPE, INSTANCE);
        MinecraftForge.EVENT_BUS.register(INSTANCE);
        TextureUtils.addIconRegister(INSTANCE::registerIcons);
        for(Material material : MetaBlocks.CABLES.keySet()) {
            MaterialIconSet iconSet = material.materialIconSet;
            INSTANCE.generatedSets.add(iconSet);
        }
    }

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
    public EnumBlockRenderType getRenderType() {
        return BLOCK_RENDER_TYPE;
    }

    @Override
    protected IVertexOperation[][] getVertexOperations(Insulation baseProperty, IVertexOperation[] pipeline, MaterialIconSet iconSet, int tileColor, int materialColor) {
        IVertexOperation[][] vo = new IVertexOperation[3][];
        vo[0] = ArrayUtils.addAll(pipeline, new IconTransformation(wireTextures.get(iconSet)), new ColourMultiplier(materialColor));
        vo[1] = vo[0];
        vo[2] = vo[0];

        if(baseProperty.isColorable()) {
            int insulationColor = GTUtility.convertRGBtoOpaqueRGBA_CL(tileColor);
            ColourMultiplier multiplier = new ColourMultiplier(insulationColor);
            vo[2] = ArrayUtils.addAll(pipeline, new IconTransformation(insulationTextures[5]), multiplier);
            vo[1] = ArrayUtils.addAll(pipeline, new IconTransformation(insulationTextures[baseProperty.insulationLevel]), multiplier);
        }
        return vo;
    }

    @Override
    public Set<TextureAtlasSprite> getDestroyEffects(IBlockState state, IBlockAccess world, BlockPos pos) {
        BlockPipeLike<Insulation, ?, ?> block = (BlockPipeLike<Insulation, ?, ?>) state.getBlock();
        return Collections.singleton(state.getValue(block.getBaseProperty()).isColorable() ? insulationTextures[5] : wireTextures.get(block.material.materialIconSet));
    }
}
