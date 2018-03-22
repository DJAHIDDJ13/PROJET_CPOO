package rogue;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class SavedGame {
	World world;
	int centerX;
	int centerY;
	int width;
	int height;
	public SavedGame(World world, int centerX, int centerY, int width, int height) {
		this.world = world;
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
	}
	public static String RGBtoInt(int R, int G, int B) {
		return Long.toString((long)Math.pow(2, 32)*(long)R + (long)Math.pow(2, 16)*(long)G + B);
	}
	public void SaveGame(String name) throws FileNotFoundException, UnsupportedEncodingException{
		String path = name;
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.print(width+" "+height+"\n");
		writer.print(centerX+" "+centerY+"\n");
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				Color c = world.color(i, j);
				int R = c.getRed(), G = c.getGreen(), B = c.getBlue();
				writer.print(world.glyph(i, j)+RGBtoInt(R, G, B)+" ");
			}
			writer.print("\n");
		}
		writer.close();
	}
	public void update(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}
}
