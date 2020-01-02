package gregtech.api.util;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiFunction;

public class CTImageGenerator {

    public static void main(String[] args) {
        Preconditions.checkArgument(args.length >= 2, "Missing argument(s): texture path, border size");
        Path rootTexturesPath = Paths.get("assets/gregtech/textures/blocks/");
        Path rawFilePath = Paths.get(args[0]);
        int borderSize = Integer.parseInt(args[1]);
        String baseFileName = rawFilePath.getFileName().toString();

        Path backgroundFilePath = rootTexturesPath.resolve(rawFilePath.getParent()).resolve(String.format("%s_background.png", baseFileName));
        Path borderFilePath = rootTexturesPath.resolve(rawFilePath.getParent()).resolve(String.format("%s_border.png", baseFileName));
        Path parentDirectoryPath = backgroundFilePath.getParent();

        Preconditions.checkArgument(Files.isRegularFile(backgroundFilePath), "Background texture file not found at %s", backgroundFilePath.toString());
        Preconditions.checkArgument(Files.isRegularFile(borderFilePath), "Border texture file not found at %s", borderFilePath.toString());

        BufferedImage baseImage = readImageFromPath(backgroundFilePath);
        BufferedImage borderImage = readImageFromPath(borderFilePath);

        TextureDirection[] directions = TextureDirection.values();
        BorderSideData[] borderDataByDirection = new BorderSideData[directions.length];
        Size borderImageSize = new Size(borderImage.getWidth(), borderImage.getHeight());
        for (TextureDirection direction : directions) {
            Region region = direction.regionMapper.apply(borderImageSize, borderSize);
            int[] pixelArray = new int[region.size.width * region.size.height];
            borderImage.getRGB(region.position.x, region.position.y, region.size.width, region.size.height, pixelArray, 0, region.size.width);
            borderDataByDirection[direction.ordinal()] = new BorderSideData(region, pixelArray);
        }

        for (int i = 0; i < 16; i++) {
            BufferedImage resultImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), baseImage.getType());
            baseImage.copyData(resultImage.getRaster());
            for (TextureDirection direction : directions) {
                int mask = 1 << direction.ordinal();
                if ((i & mask) <= 0) continue;
                BorderSideData sideData = borderDataByDirection[direction.ordinal()];
                resultImage.setRGB(sideData.region.position.x, sideData.region.position.y, sideData.region.size.width, sideData.region.size.height,
                    sideData.pixelData, 0, sideData.region.size.width);
            }
            Path resultPath = parentDirectoryPath.resolve(String.format("%s_%d.png", baseFileName, i));
            saveImageToPath(resultPath, resultImage);
            System.out.println("Saved " + resultPath + " with mask " + StringUtils.leftPad(Integer.toBinaryString(i), 4, '0'));
        }
    }

    private static void saveImageToPath(Path path, BufferedImage bufferedImage) {
        try (OutputStream outputStream = Files.newOutputStream(path)) {
            ImageIO.write(bufferedImage, "PNG", outputStream);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to save image to " + path, exception);
        }
    }

    private static BufferedImage readImageFromPath(Path path) {
        try(InputStream inputStream = Files.newInputStream(path)) {
            return ImageIO.read(inputStream);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to read image at " + path, exception);
        }
    }

    private static class BorderSideData {
        public final Region region;
        public final int[] pixelData;

        public BorderSideData(Region region, int[] pixelData) {
            this.region = region;
            this.pixelData = pixelData;
        }
    }

    public enum TextureDirection {
        TOP((image, size) -> new Region(0, 0, image.getWidth(), size)),
        BOTTOM((image, size) -> new Region(0, image.getHeight() - size, image.getWidth(), size)),
        LEFT((image, size) -> new Region(0, 0, size, image.getHeight())),
        RIGHT((image, size) -> new Region(image.getWidth() - size, 0, size, image.getHeight()));

        private BiFunction<Size, Integer, Region> regionMapper;

        TextureDirection(BiFunction<Size, Integer, Region> regionMapper) {
            this.regionMapper = regionMapper;
        }
    }

    private static class Region {
        public final Position position;
        public final Size size;

        public Region(int x, int y, int width, int height) {
            this(new Position(x, y), new Size(width, height));
        }

        public Region(Position position, Size size) {
            this.position = position;
            this.size = size;
        }
    }
}
