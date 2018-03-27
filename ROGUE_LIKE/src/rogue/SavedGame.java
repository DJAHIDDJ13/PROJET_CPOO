package rogue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public void SaveGame(String name) throws FileNotFoundException, UnsupportedEncodingException{
		String path = System.getProperty("user.home") + File.separator + ".savedRlGames" + File.separator + name;
		System.out.println(path);
		File f = new File(path);
		if(!f.getParentFile().isDirectory())
			f.getParentFile().mkdirs();
		if(f.exists()) {
			throw new FileNotFoundException();
		}
		PrintWriter writer = new PrintWriter(path, "UTF-8");
		writer.print(width+" "+height+"\n");
		writer.print(centerX+" "+centerY+"\n");
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				writer.print(world.glyph(i, j)+" ");
			}
			writer.print("\n");
		}
		writer.close();
	}
	public void update(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}

	public SavedGame loadGame(String path) throws FileNotFoundException {
		Tile[][] tiles = null;
		File file = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(file));
		int width = 0, height = 0, cX = 0, cY = 0;
		String line;
		try {
			if((line = br.readLine())!=null) {
				String[] s = line.split(" ");
				System.out.println(s[0] + " "+ s[1]);
				width = Integer.parseInt(s[0]);
				height = Integer.parseInt(s[1]);
			}
			tiles = new Tile[width][height];
			if((line = br.readLine())!=null) {
				String[] s = line.split(" ");
				System.out.println(s[0] + " "+ s[1]);
				cX = Integer.parseInt(s[0]);
				cY = Integer.parseInt(s[1]);
			}
			for(int i=0; i<height; i++) {
				if((line = br.readLine())!=null) {
					String[] s = line.split(" ");
					for(int j=0; j<width; j++) {
						System.out.println(s[j]);
						char glyph = s[j].charAt(0);
						switch(glyph) {
						case '.': tiles[i][j] = Tile.FLOOR;
						case '@': tiles[i][j] = Tile.WALL;
						default:  tiles[i][j] = Tile.BOUNDS;
						}
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new SavedGame(new World(tiles), cX,cY, width, height);
	}
}

