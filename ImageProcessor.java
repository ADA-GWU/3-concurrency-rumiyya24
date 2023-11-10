import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageProcessor {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java ImageProcessor <filename> <square size> <S/M>");
            System.exit(1);
        }

        String fileName = args[0];
        int squareSize = Integer.parseInt(args[1]);
        char processingMode = args[2].charAt(0);

        BufferedImage image;
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            System.out.println("Error reading image file: " + e.getMessage());
            return;
        }

        int numThreads = processingMode == 'M' ? Runtime.getRuntime().availableProcessors() : 1;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        BufferedImage processedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < image.getHeight(); y += squareSize) {
            for (int x = 0; x < image.getWidth(); x += squareSize) {
                final int threadX = x;
                final int threadY = y;
                executorService.execute(() -> processSquare(image, processedImage, threadX, threadY, squareSize));
            }
        }