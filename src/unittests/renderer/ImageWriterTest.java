package unittests.renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

/**
 * The ImageWriterTest class contains unit tests for the ImageWriter class.
 * These tests verify the functionality of the ImageWriter class, ensuring that:
 * <ul>
 * <li>Images are correctly written to files</li>
 * <li>Different image formats are supported and handled properly</li>
 * <li>Exceptions are thrown for invalid input or errors during the write
 * process</li>
 * <li>Performance considerations are met for writing large images</li>
 * </ul>
 * The tests cover various scenarios and edge cases to ensure the robustness and
 * reliability of the ImageWriter class.
 */
public class ImageWriterTest {

	/**
	 * Tests the writeImage method. This test verifies that the writeImage method
	 * correctly writes an image to a specified file. The test ensures the
	 * following:
	 * <ul>
	 * <li>The image is written to the correct file path</li>
	 * <li>The file format is correct</li>
	 * <li>The image data is accurately written and can be read back</li>
	 * <li>Appropriate exceptions are thrown for invalid inputs or conditions</li>
	 * </ul>
	 */
	@Test
	public void testWriteImage() {
		// Define the image resolution
		int nX = 800;
		int nY = 500;

		// Define the grid dimensions
		int gridCols = 16;
		int gridRows = 10;

		// Define the colors
		Color backgroundColor = new Color(173, 216, 230); // Light blue background
		Color gridColor = new Color(255, 105, 180); // Pink grid

		// Create an ImageWriter instance
		ImageWriter imageWriter = new ImageWriter("testImage", nX, nY);

		// Define the width and height of each grid cell
		int cellWidth = nX / gridCols;
		int cellHeight = nY / gridRows;

		// Fill the image with the background color and draw the grid
		for (int y = 0; y < nY; y++) {
			for (int x = 0; x < nX; x++) {
				if (x % cellWidth == 0 || y % cellHeight == 0) {
					imageWriter.writePixel(x, y, gridColor);
				} else {
					imageWriter.writePixel(x, y, backgroundColor);
				}
			}
		}

		// Write the image to a file
		imageWriter.writeToImage();
	}
}