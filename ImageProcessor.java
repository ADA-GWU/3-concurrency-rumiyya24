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
            System.out.println("How to use: java ImageProcessor <filename> <square size> <S/M>");
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

        // determine the number of threads based on the processing mode
        int numThreads = processingMode == 'M' ? Runtime.getRuntime().availableProcessors() : 1;
        // create a thread pool with the determined number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        BufferedImage processedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

          // iterate over the image by squares of the given size
        for (int y = 0; y < image.getHeight(); y += squareSize) {
            for (int x = 0; x < image.getWidth(); x += squareSize) {
                final int threadX = x;
                final int threadY = y;
                executorService.execute(() -> processSquare(image, processedImage, threadX, threadY, squareSize));
            }
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // wait for all tasks to finish
        }

        try {
            ImageIO.write(processedImage, "jpg", new File("result.jpg"));
        } catch (IOException e) {
            System.out.println("Error writing the output file: " + e.getMessage());
        }
    }

    private static void processSquare(BufferedImage originalImage, BufferedImage processedImage, int x, int y, int squareSize) {
        int[] rgbArray = new int[squareSize * squareSize];
        int pixelCount = 0;

        for (int j = y; j < y + squareSize && j < originalImage.getHeight(); j++) {
            for (int i = x; i < x + squareSize && i < originalImage.getWidth(); i++) {
                rgbArray[pixelCount++] = originalImage.getRGB(i, j);
            }
        }
         // set the average color to each pixel in the square
        Color averageColor = calculateAverageColor(rgbArray, pixelCount);
        for (int j = y; j < y + squareSize && j < originalImage.getHeight(); j++) {
            for (int i = x; i < x + squareSize && i < originalImage.getWidth(); i++) {
                processedImage.setRGB(i, j, averageColor.getRGB());
            }
        }
    }

    private static Color calculateAverageColor(int[] rgbArray, int pixelCount) {
        long sumRed = 0;
        long sumGreen = 0;
        long sumBlue = 0;
        for (int i = 0; i < pixelCount; i++) {
            Color pixel = new Color(rgbArray[i]);
            sumRed += pixel.getRed();
            sumGreen += pixel.getGreen();
            sumBlue += pixel.getBlue();
        }
        return new Color((int) (sumRed / pixelCount), (int) (sumGreen / pixelCount), (int) (sumBlue / pixelCount));
    }
}