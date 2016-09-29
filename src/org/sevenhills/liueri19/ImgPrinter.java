package org.sevenhills.liueri19;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgPrinter {
	BufferedImage image;
	
	public ImgPrinter() {
	}
	
	public ImgPrinter(int width, int height, int imageType) {
		initImage(width, height, imageType);
	}
	
	public void initImage(int width, int height, int imageType) {
		image = new BufferedImage(width, height, imageType);
	}
	
	public void setRGB(int x, int y, int rgbValue) {
		if (image == null)
			throw new NullPointerException("Image un-initialized, call initImage() to initialize");
		image.setRGB(x, y, rgbValue);
	}
	
	public void setRGB(int x, int y, Color color) {
		setRGB(x, y, color.getRGB());
	}
	
	public void setRGB(int x, int y, int r, int g, int b) {
		setRGB(x, y, new Color(r, g, b).getRGB());
	}
	
	public void print(String formatName, File file) throws IOException {
		if (image == null)
			throw new NullPointerException("Image un-initialized, call initImage() to initialize");
		ImageIO.write(image, formatName, file);
	}
	
	public void print(String formatName, String pathname) throws IOException {
		print(formatName, new File(pathname));
	}
	
	public void clear() {
		image = null;
	}
}
