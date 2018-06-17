package gregtech.common.blocks.modelfactories;

import codechicken.lib.texture.TextureUtils;
import gregtech.common.blocks.MetaBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelFluid;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FluidModelHandler {

    @SubscribeEvent
    public void onModelsBake(ModelBakeEvent event) {
        for(BlockFluidBase fluidBlock : MetaBlocks.FLUID_BLOCKS) {
            Fluid fluid = ObfuscationReflectionHelper.getPrivateValue(BlockFluidBase.class, fluidBlock, "definedFluid");
            ModelFluid modelFluid = new ModelFluid(fluid);
            IBakedModel bakedModel = modelFluid.bake(modelFluid.getDefaultState(), DefaultVertexFormats.ITEM, TextureUtils::getTexture);
            ModelResourceLocation resourceLocation = new ModelResourceLocation(Block.REGISTRY.getNameForObject(fluidBlock), "");
            event.getModelRegistry().putObject(resourceLocation, bakedModel);
        }
    }

}

