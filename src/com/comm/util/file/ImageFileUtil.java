package com.comm.util.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class ImageFileUtil {

	public static BufferedImage getImageInfo(File srcFile) {

		BufferedImage sourceImg = null;

		try {
			sourceImg = ImageIO.read(new FileInputStream(srcFile));
			System.out
					.println(String.format("%.1f", srcFile.length() / 1024.0));
			System.out.println(sourceImg.getWidth());
			System.out.println(sourceImg.getHeight());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return sourceImg;

	}

}
