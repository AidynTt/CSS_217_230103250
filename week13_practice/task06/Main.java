
package week13_practice.task06;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Strategy interface
interface FilterStrategy {
    void apply(BufferedImage image);
}

// Concrete strategies
class GrayscaleFilter implements FilterStrategy {
    public void apply(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));

                int gray = (color.getRed()
                        + color.getGreen()
                        + color.getBlue()) / 3;

                Color newColor = new Color(gray, gray, gray);
                image.setRGB(x, y, newColor.getRGB());
            }
        }
    }
}

class SepiaToneFilter implements FilterStrategy {
    public void apply(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));

                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                int red = Math.min(255,
                        (int)(0.393 * r + 0.769 * g + 0.189 * b));

                int green = Math.min(255,
                        (int)(0.349 * r + 0.686 * g + 0.168 * b));

                int blue = Math.min(255,
                        (int)(0.272 * r + 0.534 * g + 0.131 * b));

                image.setRGB(x, y,
                        new Color(red, green, blue).getRGB());
            }
        }
    }
}

class HighContrastFilter implements FilterStrategy {
    public void apply(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));

                int gray = (color.getRed()
                        + color.getGreen()
                        + color.getBlue()) / 3;

                int value = gray >= 128 ? 255 : 0;

                image.setRGB(x, y,
                        new Color(value, value, value).getRGB());
            }
        }
    }
}

// Subsystems
class ImageDecoder {
    public BufferedImage decode(String inputPath)
            throws IOException {

        BufferedImage image = ImageIO.read(new File(inputPath));

        if (image == null) {
            throw new IOException("Unsupported image format");
        }

        return image;
    }
}

class ThumbnailResizer {
    public BufferedImage resize(BufferedImage image,
                                int width, int height) {

        BufferedImage resized = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = resized.createGraphics();

        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();

        return resized;
    }
}

class ImageEncoder {
    public void save(BufferedImage image, String outputPath)
            throws IOException {

        ImageIO.write(image, "png", new File(outputPath));
        System.out.println("Image saved: " + outputPath);
    }
}

// Facade
class ImagePipelineFacade {
    private FilterStrategy strategy;

    private final ImageDecoder decoder = new ImageDecoder();
    private final ThumbnailResizer resizer = new ThumbnailResizer();
    private final ImageEncoder encoder = new ImageEncoder();

    public ImagePipelineFacade(FilterStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(FilterStrategy strategy) {
        this.strategy = strategy;
    }

    public void processImage(String inputPath,
                             int width, int height) {
        try {
            BufferedImage image = decoder.decode(inputPath);

            BufferedImage resized =
                    resizer.resize(image, width, height);

            strategy.apply(resized);

            String outputPath =
                    strategy.getClass().getSimpleName() + ".png";

            encoder.save(resized, outputPath);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

// Client
public class Main {
    public static void main(String[] args) {

        ImagePipelineFacade facade =
                new ImagePipelineFacade(new GrayscaleFilter());

        facade.processImage("input.jpg", 300, 200);

        facade.setStrategy(new SepiaToneFilter());
        facade.processImage("input.jpg", 300, 200);

        facade.setStrategy(new HighContrastFilter());
        facade.processImage("input.jpg", 300, 200);
    }
}
