/* 
 * loads in buffered images
 */


package com.game.src.main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;

public class bufferedImageLoader {
	
	private BufferedImage image;
	
	public BufferedImage loadImage(String path) throws IOException {
		image = ImageIO.read(getClass().getResource(path));
		System.out.println(getClass().getResource(path));
		return image;
	}
	

}
