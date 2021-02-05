package gregtech.common.render;

import codechicken.lib.render.CCModel;
import codechicken.lib.vec.*;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
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

    // Old, should be removed, but InvPipeRenderer depends on it (despite never being used)
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

    public static CCModel[] generateHalfModels(CCModel originalModel) {
        CCModel[] result = new CCModel[6];
        double translate = 0.5;
        for (int i = 0; i < 3; i++) {
            EnumFacing side = EnumFacing.VALUES[i * 2 + 1];
            Transformation rotation = Rotation.sideRotations[i * 2].at(Vector3.center);
            Transformation translation = new Translation(side.getFrontOffsetX() * translate, side.getFrontOffsetY() * translate, side.getFrontOffsetZ() * translate);
            CCModel negativeModel = originalModel.copy().apply(rotation);
            CCModel positiveModel = negativeModel.copy().apply(translation);
            result[i * 2] = negativeModel;
            result[i * 2 + 1] = positiveModel;
        }
        return result;
    }

    public static CCModel[] generateSingleModels(CCModel halfModel) {
        Transformation translation = new Translation(0, 0.25, 0);
        CCModel centeredModel = halfModel.copy().apply(translation);
        return generateFullBlockVariants(centeredModel);
    }

    // TODO Finished
    public static CCModel[] generateCornerVariantsTakeTwo(CCModel[] straightModels, CCModel[] halfModels, CCModel curvedModel) {
        CCModel[] result = generateFancyCornerVariants(curvedModel);

        for (int i=1; i < 64; i++) {
            if (result[i] == null) { // Check here to not overwrite models handled separately
                List<CCModel> parts = new ArrayList<>();

                if ((i & 1) == 1 && (i & 2) == 2) {
                    parts.add(straightModels[0].copy());
                } else if ((i & 1) == 1) {
                    parts.add(halfModels[0].copy());
                } else if ((i & 2) == 2) {
                    parts.add(halfModels[1].copy());
                }

                if ((i & 4) == 4 && (i & 8) == 8) {
                    parts.add(straightModels[1].copy());
                } else if ((i & 4) == 4) {
                    parts.add(halfModels[2].copy());
                } else if ((i & 8) == 8) {
                    parts.add(halfModels[3].copy());
                }

                if ((i & 16) == 16 && (i & 32) == 32) {
                    parts.add(straightModels[2].copy());
                } else if ((i & 16) == 16) {
                    parts.add(halfModels[4].copy());
                } else if ((i & 32) == 32) {
                    parts.add(halfModels[5].copy());
                }

                result[i] = CCModel.combine(parts);
            }
        }
        return result;
    }

    /*
     * Indices:
     * Down: 0
     * Up: 1
     * North: 2
     * South: 3
     * West: 4
     * East: 5
     */
    private static CCModel[] generateFancyCornerVariants(CCModel turnModel) { // turnModel is NW
        CCModel[] result = new CCModel[64];

        List<Transformation> rotations = Arrays.asList(       // Rotation chart:
            Rotation.sideRotations[0].at(Vector3.center),     // - No rotation, is current state
            Rotation.sideRotations[1].at(Vector3.center),     // - y=-y, z=-z
            Rotation.sideRotations[2].at(Vector3.center),     // - y=-z, z=y
            Rotation.sideRotations[3].at(Vector3.center),     // - y=z, z=-y
            Rotation.sideRotations[4].at(Vector3.center),     // - x=y, y=-x
            Rotation.sideRotations[5].at(Vector3.center),     // - x=-y, y=x
            Rotation.quarterRotations[1].at(Vector3.center),  // - x=-z, z=x      // 6
            Rotation.quarterRotations[2].at(Vector3.center),  // - x=-x, z=-z     // 7
            Rotation.quarterRotations[3].at(Vector3.center),  // - x=z, z=-x      // 8
            AxisCycle.cycles[1].at(Vector3.center),           // - x=z, y=x, z=y  // 9
            AxisCycle.cycles[2].at(Vector3.center));          // - x=y, y=z, z=x  // 10

        CCModel originalModel = turnModel.copy();
        result[0b010100] = originalModel.copy();
        result[0b100100] = originalModel.copy().apply(rotations.get(6));
        result[0b101000] = originalModel.copy().apply(rotations.get(7));
        result[0b011000] = originalModel.copy().apply(rotations.get(8));

        originalModel = turnModel.copy().apply(rotations.get(4));
        result[0b000110] = originalModel.copy();
        result[0b100010] = originalModel.copy().apply(rotations.get(6));
        result[0b001010] = originalModel.copy().apply(rotations.get(7));
        result[0b010010] = originalModel.copy().apply(rotations.get(8));

        originalModel = turnModel.copy().apply(rotations.get(5));
        result[0b000101] = originalModel.copy();
        result[0b100001] = originalModel.copy().apply(rotations.get(6));
        result[0b001001] = originalModel.copy().apply(rotations.get(7));
        result[0b010001] = originalModel.copy().apply(rotations.get(8));

        // Generate corner+1 pipes
        for (int i = 0; i < result.length; i++) {
            CCModel corner1 = result[i];
            if (corner1 != null) {
                for (int j = i + 1; j < result.length; j++) {
                    CCModel corner2 = result[j];
                    if (corner2 != null) {
                        CCModel corner3 = result[(i ^ j)];
                        if (corner3 != null) {
                            result[i | j] = CCModel.combine(Arrays.asList(corner1.copy(), corner2.copy(), corner3.copy()));
                        }
                    }
                }
            }
        }
        return result;
    }

    public static CCModel[] generateCornerVariants(CCModel originalModel, CCModel halfModel) {
        CCModel[] result = new CCModel[64];

        List<Transformation> rotations = Arrays.asList(       // Rotation chart:
            Rotation.sideRotations[0].at(Vector3.center),     // - No rotation, is current state
            Rotation.sideRotations[1].at(Vector3.center),     // - y=-y, z=-z
            Rotation.sideRotations[2].at(Vector3.center),     // - y=-z, z=y
            Rotation.sideRotations[3].at(Vector3.center),     // - y=z, z=-y
            Rotation.sideRotations[4].at(Vector3.center),     // - x=y, y=-x
            Rotation.sideRotations[5].at(Vector3.center),     // - x=-y, y=x
            Rotation.quarterRotations[1].at(Vector3.center),  // - x=-z, z=x      // 6
            Rotation.quarterRotations[2].at(Vector3.center),  // - x=-x, z=-z     // 7
            Rotation.quarterRotations[3].at(Vector3.center),  // - x=z, z=-x      // 8
            AxisCycle.cycles[1].at(Vector3.center),           // - x=z, y=x, z=y  // 9
            AxisCycle.cycles[2].at(Vector3.center));          // - x=y, y=z, z=x  // 10

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
        CCModel[] halfModels = generateHalfModels(halfModel);

        // No connections "joint"

        // Elbow joint (save for later)
        // CCModel elbowJoint = generateTurnModel(<calculate values>);


        // T joint
        CCModel tJoint = CCModel.combine(Arrays.asList(straightModels[0].copy(), halfModels[4].copy()));
        result[0b010011] = tJoint.copy();                         // DUW
        result[0b000111] = tJoint.copy().apply(rotations.get(6)); // NUD
        result[0b100011] = tJoint.copy().apply(rotations.get(7)); // EUD
        result[0b001011] = tJoint.copy().apply(rotations.get(8)); // SUD

        result[0b110010] = tJoint.copy().apply(rotations.get(4)); // UWE good
        result[0b110100] = tJoint.copy().apply(rotations.get(10)); // NWE good
        result[0b110001] = tJoint.copy().apply(rotations.get(5)); // DWE good
        result[0b111000] = tJoint.copy().apply(rotations.get(2)).apply(rotations.get(8)); // SWE

        result[0b101100] = tJoint.copy().apply(rotations.get(1)); // ENS
        result[0b001110] = tJoint.copy().apply(rotations.get(10)); // UNS
        result[0b011100] = tJoint.copy().apply(rotations.get(3)); // WNS good
        result[0b001101] = tJoint.copy().apply(rotations.get(9)); // DNS good



        // 3-way joint (save for later)


        // Plus joint TODO DONE
        CCModel plusJoint = CCModel.combine(Arrays.asList(straightModels[0].copy(), straightModels[1].copy()));
        result[0b001111] = plusJoint; // SNUD
        result[0b110011] = plusJoint.copy().apply(rotations.get(8)); // EWUD
        result[0b111100] = plusJoint.copy().apply(rotations.get(4)); // EWSN

        // T+1 joint
        CCModel tPlusJoint = CCModel.combine(Arrays.asList(tJoint.copy(), halfModels[2].copy())); // DUWN

        // 5-way joint
        CCModel fiveJoint = CCModel.combine(Arrays.asList(plusJoint.copy(), halfModels[4].copy())); // SNUDW

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


    // Thank you to Arch for these complex generations
    private static Matrix4 createRotationMatrix(Vector3 forward, Vector3 up) {
        Vector3 right = forward.copy().crossProduct(up).multiply(-1.0f); //i*j = -k

        return new Matrix4(
            forward.x, up.x, right.x, 0.0f,
            forward.y, up.y, right.y, 0.0f,
            forward.z, up.z, right.z, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f);
    }

    private static Vector3[] generatePointsForAngle(double anglePerNumber, int number, int numberOfAnglesInner, double radiusInner) {
        double angle = anglePerNumber * number;
        double nextAngle = anglePerNumber * (number + 1);

        Vector3 center = new Vector3(Math.cos(angle) * 0.5, 0.5f, Math.sin(angle) * 0.5);
        Vector3 nextCenter = new Vector3(Math.cos(nextAngle) * 0.5, 0.5, Math.sin(nextAngle) * 0.5);
        Vector3 forward = nextCenter.copy().subtract(center).normalize();
        Vector3 up = new Vector3(0.0f, 1.0f, 0.0f);
        Matrix4 rotationMatrix = createRotationMatrix(forward, up);

        Vector3[] result = new Vector3[numberOfAnglesInner];
        double anglePerNumberInner = (Math.PI * 2) / (numberOfAnglesInner * 1.0);
        for (int numberInner = 0; numberInner < numberOfAnglesInner; numberInner++) {
            double angleInner = anglePerNumberInner * numberInner;
            Vector3 originalPoint = new Vector3(0.0f, Math.cos(angleInner) * radiusInner, Math.sin(angleInner) * radiusInner);
            rotationMatrix.apply(originalPoint);
            originalPoint.add(center);
            result[numberInner] = originalPoint;
        }
        return result;
    }

    // Example was with (20, 5, 6, 0.5)
    public static CCModel generateTurnModel(int numberOfTurns, int turnPointsPerTexel, int numberOfAnglesInner, double radiusInner) {
        CCModel initialModel = CCModel.quadModel(numberOfAnglesInner * 4 * numberOfTurns);
        int currentIndex = 0;

        Vector3[][] allGeneratedPoints = new Vector3[numberOfTurns + 1][];
        double anglePerNumber = (Math.PI / 2) / numberOfTurns;
        for (int i = 0; i <= numberOfTurns; i++) {
            allGeneratedPoints[i] = generatePointsForAngle(anglePerNumber, i, numberOfAnglesInner, radiusInner);
        }

        double texelSizePerTurn = 1.0 / (turnPointsPerTexel * 1.0);

        for (int i = 0; i < numberOfTurns; i++) {
            Vector3[] currentTurn = allGeneratedPoints[i];
            Vector3[] nextTurn = allGeneratedPoints[i + 1];

            for (int j = 0; j < numberOfAnglesInner; j++) {
                Vector3 currentTurnFirstPoint = currentTurn[j];
                Vector3 currentTurnSecondPoint = currentTurn[(j + 1) % numberOfAnglesInner];

                Vector3 nextTurnFirstPoint = nextTurn[j];
                Vector3 nextTurnSecondPoint = nextTurn[(j + 1) % numberOfAnglesInner];

                double startU = texelSizePerTurn * (i % turnPointsPerTexel);
                double endU = startU + texelSizePerTurn;

                double startV = 0.0;
                double endV = 1.0;

                initialModel.verts[currentIndex++] = new Vertex5(nextTurnFirstPoint.copy(), endU, startV);
                initialModel.verts[currentIndex++] = new Vertex5(nextTurnSecondPoint.copy(), endU, endV);
                initialModel.verts[currentIndex++] = new Vertex5(currentTurnSecondPoint.copy(), startU, endV);
                initialModel.verts[currentIndex++] = new Vertex5(currentTurnFirstPoint.copy(), startU, startV);
            }
        }
        return initialModel.computeNormals();
    }
}
