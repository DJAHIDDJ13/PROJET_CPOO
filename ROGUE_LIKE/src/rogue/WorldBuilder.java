package rogue;

import java.awt.Rectangle;

public class WorldBuilder {
	private int width;
	private int height;
	private Tile[][] tiles;

	public WorldBuilder(int width, int height) {
		this.width = width;
		this.height = height;
		this.tiles = new Tile[width][height];
	}

	public World build() {
		return new World(tiles);
	}
	private WorldBuilder makeRooms() {
		int maxWidth = 15;
		int minWidth = 5;
		int rectangularity = 4;
		int nbrRooms = 75;
		int nbr = 0;
		Rectangle[] rooms = new Rectangle[nbrRooms];
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				tiles[i][j] = Tile.WALL;
			}
		}
		while_1:do {
			Rectangle n;
			int x = (int) (Math.random()*(width - maxWidth));
			int y = (int) (Math.random()*(height - maxWidth));
			int w = (int) (Math.random()*(maxWidth-minWidth))+minWidth;
			int h = w > maxWidth-rectangularity?w-(int)(Math.random()*rectangularity):w+(int)(Math.random()*rectangularity);
			n = new Rectangle(x, y, w, h);
			for(int i=0; i<nbr; i++) {
				if(n.intersects(rooms[i]))
					continue while_1;
			}
			rooms[nbr] = n;
			nbr++;
		} while(nbr<nbrRooms);
		for(int i=0; i<nbrRooms; i++) {
			for(int j=(int) rooms[i].getX(); j<rooms[i].getX()+rooms[i].getWidth(); j++) {
				for(int k=(int) rooms[i].getY(); k<rooms[i].getY()+rooms[i].getHeight(); k++) {
					tiles[j][k] = Tile.FLOOR;
				}
			}
		}
		return this;
	}
	private WorldBuilder randomizeTiles() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
			}
		}
		return this;
	}

	private WorldBuilder smooth(int times) {
		Tile[][] tiles2 = new Tile[width][height];
		for (int time = 0; time < times; time++) {

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int floors = 0;
					int rocks = 0;

					for (int ox = -1; ox < 2; ox++) {
						for (int oy = -1; oy < 2; oy++) {
							if (x + ox < 0 || x + ox >= width || y + oy < 0
									|| y + oy >= height)
								continue;

							if (tiles[x + ox][y + oy] == Tile.FLOOR)
								floors++;
							else
								rocks++;
						}
					}
					tiles2[x][y] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
				}
			}
			tiles = tiles2;
		}
		return this;
	}

	public WorldBuilder makeCaves() {
		return makeRooms();
	}
}