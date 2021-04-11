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

    public static CCModel[] generateHalfVariants(CCModel halfModel) {
        Transformation translation = new Translation(0, 0.25, 0);
        CCModel centeredModel = halfModel.copy().apply(translation);
        return generateFullBlockVariants(centeredModel);
    }

    public static CCModel[] generateRotatedVariants(CCModel originalModel, double translate) {
        CCModel[] result = new CCModel[6];
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

    /**
     * Method left for compatibility, as InvPipeRenderer is using this method.
     * When Inventory Pipes are fully implemented, they should either switch to this new
     * model generation system, or create their own models. Then this method can be removed.
     */
    public static CCModel[] generateRotatedVariants(CCModel originalModel) {
        return generateRotatedVariants(originalModel, 1.0 - originalModel.verts[2].vec.y);
    }

    public static CCModel[] generateCornerVariants(CCModel[] halfModels, CCModel curvedModel) {
        CCModel[] result = generateFancyCornerVariants(curvedModel);
        List<CCModel> parts;

        for (int i = 1; i < result.length; i++) {
            if (result[i] == null) { // Check here to not overwrite models handled separately
                parts = new ArrayList<>();
                for (int j = 0; j < 6; j++) {
                    if ((i >> j & 1) == 1) {
                        parts.add(halfModels[j].copy());
                    }
                }
                result[i] = CCModel.combine(parts);
            }
        }
        return result;
    }

    private static CCModel[] generateFancyCornerVariants(CCModel turnModel) {
        CCModel[] result = new CCModel[64];

        List<Transformation> sRotations = Arrays.asList(
            Rotation.sideRotations[0].at(Vector3.center),
            Rotation.sideRotations[5].at(Vector3.center),
            Rotation.sideRotations[4].at(Vector3.center));

        List<Transformation> qRotations = Arrays.asList(
            Rotation.quarterRotations[0].at(Vector3.center),
            Rotation.quarterRotations[2].at(Vector3.center),
            Rotation.quarterRotations[3].at(Vector3.center),
            Rotation.quarterRotations[1].at(Vector3.center));

        for (int i = 0; i < 3; i++) {
            CCModel originalModel = turnModel.copy().apply(sRotations.get(i));
            for (int j = 0; j < 4; j++) {
                int index = i == 0
                        ? j < 2
                            ? ((j + 1) * 5) << 2
                            : (j * 3) << 2
                        : (4 << j) + i;
                result[index] = originalModel.copy().apply(qRotations.get(j));
            }
        }

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

    public static CCModel generateModel(int angles, double height, double radius, double segmentHeight) {
        int amountOfSegments = (int) Math.ceil(height / segmentHeight);
        CCModel initialModel = CCModel.quadModel(angles * 4 * amountOfSegments);
        double radiansPerAngle = (Math.PI * 2) / (angles * 1.0);
        for (int i = 0; i < angles; i++) {
            Vector3 first = generatePoint(radiansPerAngle, i, radius);
            Vector3 second = generatePoint(radiansPerAngle, i + 1, radius);
            Vector3 firstTop = first.copy().add(0.0, segmentHeight, 0.0);
            Vector3 secondTop = second.copy().add(0.0, segmentHeight, 0.0);
            double heightLeft = height;
            for (int j = 0; j < amountOfSegments; j++) {
                double actualHeight = firstTop.y - first.y;
                int offset = i * amountOfSegments * 4 + j * 4;
                initialModel.verts[offset] = new Vertex5(first.copy(), 1.0, 0.0);
                initialModel.verts[offset + 1] = new Vertex5(firstTop.copy(), 1.0, 1.0);
                initialModel.verts[offset + 2] = new Vertex5(secondTop.copy(), 0.0, 1.0);
                initialModel.verts[offset + 3] = new Vertex5(second.copy(), 0.0, 0.0);

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

    private static Matrix4 createRotationMatrix(Vector3 forward, Vector3 up) {
        Vector3 right = forward.copy().crossProduct(up).multiply(-1.0f); //i*j = -k

        return new Matrix4(
            forward.x, up.x, right.x, 0.0f,
            forward.y, up.y, right.y, 0.0f,
            forward.z, up.z, right.z, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f);
    }

    private static Vector3[] generatePointsForAngle(double anglePerNumber, int number, int numberOfAnglesInner, double radiusInner, int numberOfTurns) {
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
            if (number == 0) // TODO There is probably a smarter way to do this, maybe in "center"
                originalPoint.z = 0.0;
            else if (number == numberOfTurns)
                originalPoint.x = 0.0;
            result[numberInner] = originalPoint;
        }
        return result;
    }

    public static CCModel generateTurnModel(int numberOfTurns, int turnPointsPerTexel, int numberOfAnglesInner, double radiusInner) {
        CCModel initialModel = CCModel.quadModel(numberOfAnglesInner * 4 * numberOfTurns);
        int currentIndex = 0;

        Vector3[][] allGeneratedPoints = new Vector3[numberOfTurns + 1][];
        double anglePerNumber = (Math.PI / 2) / numberOfTurns;
        for (int i = 0; i <= numberOfTurns; i++) {
            allGeneratedPoints[i] = generatePointsForAngle(anglePerNumber, i, numberOfAnglesInner, radiusInner, numberOfTurns);
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
