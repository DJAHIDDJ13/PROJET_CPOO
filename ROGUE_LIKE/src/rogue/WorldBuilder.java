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
		int maxWidth = 10;
		int minWidth = 5;
		double rectangularity = 0.3;
		int nbrRooms = 500;
		int nbr = 0;
		Rectangle[] rooms = new Rectangle[nbrRooms];
		// initializing the world with walls
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				tiles[i][j] = Tile.WALL;
			}
		}
		// creating the rectangles
		while_1:do {
			Rectangle n;
			int x = (int) (Math.random()*(width - maxWidth));
			int y = (int) (Math.random()*(height - maxWidth));
			int w = (int) (Math.random()*(maxWidth-minWidth))+minWidth;
			int h = (int) (w*(Math.random()*2*rectangularity)+1-rectangularity);
			h = h<minWidth?minWidth:h;
			n = new Rectangle(x, y, w, h);
			for(int i=0; i<nbr; i++) {
				if(n.intersects(rooms[i]))
					continue while_1;
			}
			rooms[nbr] = n;
			nbr++;
		} while(nbr<nbrRooms);
		// drawing the rectangles as floors
		for(int i=0; i<nbrRooms; i++) {
			for(int j=(int) rooms[i].getX(); j<rooms[i].getX()+rooms[i].getWidth(); j++) {
				for(int k=(int) rooms[i].getY(); k<rooms[i].getY()+rooms[i].getHeight(); k++) {
					tiles[j][k] = Tile.FLOOR;
				}
			}
		}
		
		return this;
	}
	
	public WorldBuilder makeCaves() {
		return makeRooms();
	}
}