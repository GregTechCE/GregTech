package gregtech.api.cover;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.*;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.function.Consumer;

public interface ICoverable {

    Transformation REVERSE_HORIZONTAL_ROTATION = new Rotation(Math.PI, new Vector3(0.0, 1.0, 0.0)).at(Vector3.center);
    Transformation REVERSE_VERTICAL_ROTATION = new Rotation(Math.PI, new Vector3(1.0, 0.0, 0.0)).at(Vector3.center);

    World getWorld();

    BlockPos getPos();

    long getTimer();

    void markDirty();

    boolean isValid();

    <T> T getCapability(Capability<T> capability, EnumFacing side);

    boolean placeCoverOnSide(EnumFacing side, ItemStack itemStack, CoverDefinition definition);

    boolean removeCover(EnumFacing side);

    boolean canPlaceCoverOnSide(EnumFacing side);

    CoverBehavior getCoverAtSide(EnumFacing side);

    void writeCoverData(CoverBehavior behavior, int id, Consumer<PacketBuffer> writer);

    int getInputRedstoneSignal(EnumFacing side, boolean ignoreCover);

    ItemStack getStackForm();

    double getCoverPlateThickness();

    int getPaintingColor();

    boolean shouldRenderBackSide();

    void notifyBlockUpdate();

    void scheduleRenderUpdate();

    @SideOnly(Side.CLIENT)
    default void renderCovers(CCRenderState renderState, Matrix4 translation) {
        renderState.lightMatrix.locate(getWorld(), getPos());
        double coverPlateThickness = getCoverPlateThickness();
        IVertexOperation[] platePipeline = new IVertexOperation[] {new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColor()))};
        IVertexOperation[] coverPipeline = new IVertexOperation[] {renderState.lightMatrix};

        for (EnumFacing sideFacing : EnumFacing.values()) {
            CoverBehavior coverBehavior = getCoverAtSide(sideFacing);
            if (coverBehavior == null) continue;
            Cuboid6 plateBox = getCoverPlateBox(sideFacing, coverPlateThickness, false);
            double coverOffset = getCoverOffset(sideFacing);

            if (coverPlateThickness > 0) {
                renderState.preRenderWorld(getWorld(), getPos());
                //render cover plate for cover
                //to prevent Z-fighting between cover plates
                plateBox.expand(coverOffset);
                TextureAtlasSprite casingSide = coverBehavior.getPlateSprite();
                for (EnumFacing coverPlateSide : EnumFacing.VALUES) {
                    Textures.renderFace(renderState, translation, platePipeline, coverPlateSide, plateBox, casingSide);
                }
                plateBox.expand(-coverOffset);
            }

            plateBox.expand(coverOffset * 10.0);
            coverBehavior.renderCover(renderState, translation.copy(), coverPipeline, plateBox);
            if (coverPlateThickness == 0.0 && shouldRenderBackSide()) {
                //machine is full block, but still not opaque - render cover on the back side too
                plateBox.expand(-coverOffset * -20.0);
                Matrix4 backTranslation = translation.copy();
                if (sideFacing.getAxis().isVertical()) {
                    REVERSE_VERTICAL_ROTATION.apply(backTranslation);
                } else {
                    REVERSE_HORIZONTAL_ROTATION.apply(backTranslation);
                }
                backTranslation.translate(-sideFacing.getFrontOffsetX(), -sideFacing.getFrontOffsetY(), -sideFacing.getFrontOffsetZ());
                coverBehavior.renderCover(renderState, backTranslation, coverPipeline, plateBox);
            }
        }
    }

    default void addCoverCollisionBoundingBox(List<? super IndexedCuboid6> collisionList, boolean offsetSide) {
        double plateThickness = getCoverPlateThickness();
        if (plateThickness > 0.0) {
            for (EnumFacing side : EnumFacing.VALUES) {
                double coverOffset = getCoverOffset(side);
                if (getCoverAtSide(side) != null) {
                    Cuboid6 coverBox = getCoverPlateBox(side, plateThickness, offsetSide);
                    coverBox.expand(coverOffset);
                    collisionList.add(new IndexedCuboid6(side, coverBox));
                }
            }
        }
    }

    static boolean checkCoverCollision(EnumFacing side, List<IndexedCuboid6> collisionBox, double plateThickness) {
        if (plateThickness > 0.0) {
            Cuboid6 coverPlateBox = getCoverPlateBox(side, plateThickness, false);
            for (Cuboid6 collisionCuboid : collisionBox) {
                if (collisionCuboid.intersects(coverPlateBox)) {
                    //collision box intersects with machine bounding box -
                    //cover cannot be placed on this side
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    static EnumFacing rayTraceCoverableSide(ICoverable coverable, EntityPlayer player) {
        RayTraceResult result = RayTracer.retraceBlock(coverable.getWorld(), player, coverable.getPos());
        if (result == null || result.typeOfHit != Type.BLOCK) {
            return null;
        }
        return traceCoverSide(result);
    }

    static EnumFacing traceCoverSide(RayTraceResult result) {
        if (result instanceof CuboidRayTraceResult) {
            CuboidRayTraceResult rayTraceResult = (CuboidRayTraceResult) result;
            if (rayTraceResult.cuboid6.data == null) {
                return determineGridSideHit(result);
            } else if (rayTraceResult.cuboid6.data instanceof EnumFacing) {
                return (EnumFacing) rayTraceResult.cuboid6.data;
            } else return null; //unknown hit type, return null
        }
        //normal collision ray trace, return side hit
        return determineGridSideHit(result);
    }

    static EnumFacing determineGridSideHit(RayTraceResult result) {
        return GTUtility.determineWrenchingSide(result.sideHit,
            (float) (result.hitVec.x - result.getBlockPos().getX()),
            (float) (result.hitVec.y - result.getBlockPos().getY()),
            (float) (result.hitVec.z - result.getBlockPos().getZ()));
    }

    static Cuboid6 getCoverPlateBox(EnumFacing side, double plateThickness, boolean offsetSide) {
        double offset = offsetSide ? 2.5 / 16.0 : 0.0;
        Cuboid6 coverPlateBox = new Cuboid6(offset, 0.0, offset, 1.0 - offset, plateThickness, 1.0 - offset);
        coverPlateBox.apply(Rotation.sideOrientation(side.getIndex(), 0).at(Vector3.center));
        return coverPlateBox;
    }

    static double getCoverOffset(EnumFacing side) {
        EnumFacing.Axis axis = side.getAxis();
        double sideMultiplier = axis == Axis.Y ? 3 :
            (axis == Axis.X ? 2 : 1);
        return sideMultiplier * 1e-4;
    }
}
