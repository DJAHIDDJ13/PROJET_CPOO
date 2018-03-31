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
		int maxWidth = 50;
		int minWidth = 20;
		double rectangularity = 0.3;
		int nbrRooms = 2;
		int nbr = 0;
		Room[] rooms = new Room[nbrRooms];
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
				if(n.intersects(rooms[i].getRect())) 
					continue while_1;
			}
			System.out.println(n.getX()+ "x room y" + n.getY()+";"+w +"w room h"+h);
			rooms[nbr] = new Room(n);
			nbr++;
		} while(nbr<nbrRooms);
		// drawing the rectangles as floors
		for(int i=0; i<nbrRooms; i++) {
			for(int j=(int) rooms[i].getRect().getX(); j<rooms[i].getRect().getX()+rooms[i].getRect().getWidth(); j++) {
				for(int k=(int) rooms[i].getRect().getY(); k<rooms[i].getRect().getY()+rooms[i].getRect().getHeight(); k++) {
					tiles[j][k] = Tile.FLOOR;
				}
			}
		}
		tiles = rooms[0].connectRoom(rooms[1], tiles);
		return this;
	}
	public WorldBuilder makeCaves() {
		return makeRooms();
	}
}