package rogue;


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
    private WorldBuilder create_rooms(int nbrRooms) {
    	int nbr = 0;
    	for(int i=0; i<width; i++) {
    		for(int j=0; j<height; j++) {
    			tiles[i][j] = Tile.WALL;
    		}
    	}
        while(nbr < nbrRooms) {
        	int x = (int) (Math.random() * (width-15));
        	int y = (int) (Math.random() * (height-15));
        	int w = (int) (Math.random() * 5) + 10;
        	int h = (int) (Math.random() * 5) + 10;
        	for(int i=x; i<x+w; i++) {
        		for(int j=y; j<y+h; j++) {
        			tiles[i][j] = Tile.FLOOR;
        		}
        	}
        	nbr++;
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
        return create_rooms(100);
    }
}
