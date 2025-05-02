package GUI;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ImageHelper {
	int width;
	int height;
	URL url;
	
	public ImageHelper(int width, int height, URL url) {
		this.width = width;
		this.height = height;
		this.url = url;
	}
	
	public ImageIcon getScaledImage() {
		// Load the original image
		ImageIcon originalIcon = new ImageIcon(url);

		// Resize the image (e.g., to 50x50 pixels)
		Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		// Create a new ImageIcon with the scaled image
		return new ImageIcon(scaledImage);
	}
}