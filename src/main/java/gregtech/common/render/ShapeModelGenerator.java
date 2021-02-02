package gregtech.common.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.vec.*;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;
import java.util.List;

public class ShapeModelGenerator {

    public static CCModel[] generateFullBlockVariants(CCModel originalModel) {
        CCModel[] result = new CCModel[3];
        for (int i = 0; i < 3; i++) {
            Transformation rotation = Rotation.sideRotations[i * 2].at(Vector3.center);
            CCModel rotatedModel = originalModel.copy().apply(rotation);
            result[i] = rotatedModel;
        }
        return result;
    }

    public static CCModel[] generateRotatedVariants(CCModel originalModel) {
        CCModel[] result = new CCModel[6];
        double modelHeight = originalModel.verts[2].vec.y;
        double translate = 1.0 - modelHeight;
        for (int i = 0; i < 3; i++) {
            EnumFacing side = EnumFacing.VALUES[i * 2 + 1];
            Transformation rotation = Rotation.sideRotations[i * 2].at(Vector3.center);
            Transformation translation = new Translation(side.getXOffset() * translate, side.getYOffset() * translate, side.getZOffset() * translate);
            CCModel negativeModel = originalModel.copy().apply(rotation);
            CCModel positiveModel = negativeModel.copy().apply(translation);
            result[i * 2] = negativeModel;
            result[i * 2 + 1] = positiveModel;
        }
        return result;
    }

    private static CCModel[] generateHalfModels(CCModel originalModel) {
        CCModel[] result = new CCModel[6];
        double modelHeight = originalModel.verts[2].vec.y;
        double translate = 1.0 - modelHeight;
        for (int i = 0; i < 3; i++) {
            EnumFacing side = EnumFacing.VALUES[i * 2 + 1];
            Transformation rotation = Rotation.sideRotations[i * 2].at(Vector3.center);
            Transformation translation = new Translation(side.getFrontOffsetX() * translate / 2.0f, side.getFrontOffsetY() * translate / 2.0f, side.getFrontOffsetZ() * translate / 2.0f);
            CCModel negativeModel = originalModel.copy().apply(rotation);
            CCModel positiveModel = negativeModel.copy().apply(translation);
            result[i * 2] = negativeModel;
            result[i * 2 + 1] = positiveModel;
        }
        return result;
    }


    /*
     * Bitmask info:
     * 0: DOWN
     * 1: UP
     * 2: NORTH
     * 3: SOUTH
     * 4: WEST
     * 5: EAST
     */
    public static CCModel[] generateCornerVariants(CCModel originalModel) {
        CCModel[] result = new CCModel[64]; // 64 different "corner" variants possible.
                                            // There are some extra models generated that are wasted, but it saved a TON of code to do it this way
        double modelHeight = originalModel.verts[2].vec.y;
        double translate = 1.0 - modelHeight;

        CCModel[] baseTypes = new CCModel[7]; // 7 basic types of pipe joints

        List<Transformation> rotations = Arrays.asList(       // Rotation chart:
            Rotation.sideRotations[0].at(Vector3.center),     // - No rotation, is current state
            Rotation.sideRotations[1].at(Vector3.center),     // - y=-y, z=-z
            Rotation.sideRotations[2].at(Vector3.center),     // - y=-z, z=y
            Rotation.sideRotations[3].at(Vector3.center),     // - y=z, z=-y
            Rotation.sideRotations[4].at(Vector3.center),     // - x=y, y=-x
            Rotation.sideRotations[5].at(Vector3.center),     // - x=-y, y=x
            Rotation.quarterRotations[1].at(Vector3.center),  // - x=-z, z=x    // 6
            Rotation.quarterRotations[2].at(Vector3.center),  // - x=-x, z=-z   // 7
            Rotation.quarterRotations[3].at(Vector3.center)); // - x=z, z=-x    // 8


        /* Indices:
         * Up/Down: 0
         * North/South: 1
         * West/East: 2
         */
        CCModel[] straightModels = generateFullBlockVariants(originalModel);

        /*
         * Indices:
         * Down: 0
         * Up: 1
         * North: 2
         * South: 3
         * West: 4
         * East: 5
         */
        CCModel[] halfModels = generateHalfModels(originalModel); // TODO Fix half models code

        // No connections "joint"


        // Elbow joint (save for later)


        // T joint
        CCModel tJoint = CCModel.combine(Arrays.asList(straightModels[0], halfModels[4]));
        result[0b010011] = tJoint;


        // 3-way joint (save for later)


        // Plus joint TODO DONE
        CCModel plusJoint = CCModel.combine(Arrays.asList(straightModels[0].copy(), straightModels[1].copy()));
        result[0b001111] = plusJoint; // SNUD
        result[0b110011] = plusJoint.copy().apply(rotations.get(8)); // EWUD
        result[0b111100] = plusJoint.copy().apply(rotations.get(4)); // EWSN

        // T+1 joint


        // 5-way joint


        // 6-way joint TODO DONE
        CCModel sixJoint = CCModel.combine(Arrays.asList(straightModels[0].copy(), straightModels[1].copy(), straightModels[2].copy()));
        result[0b111111] = sixJoint;



        return result;
    }

    public static CCModel generateModel(int angles, double height, double radius, double segmentHeight) {
        int amountOfSegments = (int) Math.ceil(height / segmentHeight);
        CCModel initialModel = CCModel.quadModel(angles * 4 * amountOfSegments);
        double radiansPerAngle = (Math.PI * 2) / (angles * 1.0);
        for (int i = 0; i < angles; i++) {
            Vector3 first = generatePoint(radiansPerAngle, i, radius);
            Vector3 second = generatePoint(radiansPerAngle, i + 1, radius);
            Vector3 firstTop = first.copy().add(0.0, segmentHeight, 0.0);
            Vector3 secondTop = second.copy().add(0.0, segmentHeight, 0.0);
            double width = first.copy().subtract(second).mag();
            double heightLeft = height;
            for (int j = 0; j < amountOfSegments; j++) {
                double actualHeight = firstTop.y - first.y;
                double textureHeight = 1.0 * (actualHeight / segmentHeight);
                double textureWidth = (textureHeight / actualHeight) * width;
                int offset = i * amountOfSegments * 4 + j * 4;
                initialModel.verts[offset] = new Vertex5(first.copy(), 0.0, 0.0);
                initialModel.verts[offset + 1] = new Vertex5(firstTop.copy(), 0.0, textureHeight);
                initialModel.verts[offset + 2] = new Vertex5(secondTop.copy(), textureWidth, textureHeight);
                initialModel.verts[offset + 3] = new Vertex5(second.copy(), textureWidth, 0.0);

                heightLeft -= actualHeight;
                double nextSegmentHeight = Math.min(segmentHeight, heightLeft);
                first.add(0.0, actualHeight, 0.0);
                second.add(0.0, actualHeight, 0.0);
                firstTop.y = first.y + nextSegmentHeight;
                secondTop.y = second.y + nextSegmentHeight;
            }
        }
        return initialModel.computeNormals();
    }

    private static Vector3 generatePoint(double anglePerNumber, int number, double radius) {
        double angle = anglePerNumber * number;
        double x = 0.5 + Math.cos(angle) * radius;
        double z = 0.5 + Math.sin(angle) * radius;
        return new Vector3(x, 0.0, z);
    }

}
