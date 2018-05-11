package rogue;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class World {
    private Tile[][] tiles;
    private int width;
    public int width() { return width; }
	private Item[][] items;
	private int height;
    public int height() { return height; }

    public World(Tile[][] tiles){
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
		this.items = new Item[width][height];
	}
    public Tile tile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[x][y];
    }

    public char glyph(int x, int y){
    	if(getCreature(x,y) != null)
    		return getCreature(x,y).glyph();
    	if(item(x, y) != null)
    		return item(x,y).glyph();
        return tile(x, y).glyph();
    }

	public Color color(int x, int y){
    	if(getCreature(x,y) != null)
    		return getCreature(x,y).color();
    	if(item(x, y) != null)
    		return item(x,y).color();
        return tile(x, y).color();
    }

	public void addAtEmptyLocation(Creature creature){
	    int x, y;
	    do {
	        x = (int)(Math.random() * width);
	        y = (int)(Math.random() * height);
	    } while (!tile(x,y).isGround() ||
	    		getCreature(x, y) != null);
	    creature.x = x;
	    creature.y = y;
	    creatures.add(creature);
	}

	public void addAtLocation(Creature player, int centerX, int centerY) {
		player.x = centerX;
		player.y = centerY;
		creatures.add(player);
	}

	List<Creature> creatures = new ArrayList<Creature>();
	public List<Creature> getCreature(){
		return creatures;
	}
	public void setCreatures(List<Creature> c) {
		creatures = c;
	}
	public Creature getCreature(int x, int y) {
		if(creatures == null)
			return null;
		for(Creature c : creatures) {
			if(c.x == x && c.y == y)
				return c;
		}
		return null;
	}
	public void updateCreatures() {
		List<Creature> toUpdate = new ArrayList<Creature>(creatures);
		for(Creature c: toUpdate)
			c.update();
	}
	public void remove(Creature c) {
		creatures.remove(c);
	}


	public Item item(int x, int y){
		return items[x][y];
	}
	
	public int[] addAtEmptyLocation(Item item) {
		int x;
		int y;
		
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		} 
		while (!tile(x,y).isGround() || item(x,y) != null);
		int[] res = {x,y};
		items[x][y] = item;
		return res;
	}

	public void remove(int x, int y) {
		items[x][y] = null;
	}

	public boolean addAtEmptySpace(Item item, int x, int y){
		if (item == null)
			return true;
		
		List<Point> points = new ArrayList<Point>();
		List<Point> checked = new ArrayList<Point>();
		
		points.add(new Point(x, y));
		
		while (!points.isEmpty()){
			Point p = points.remove(0);
			checked.add(p);
			
			if (!tiles[p.x][p.y].isGround())
				continue;
				
			if (items[p.x][p.y] == null) {
				items[p.x][p.y] = item;
				Creature c = this.getCreature(p.x, p.y);
				if (c != null)
					c.notify("A %s lands between your feet.", item.name());
				return true;
			} else {
				List<Point> neighbors = p.neighbors8();
				neighbors.removeAll(checked);
				points.addAll(neighbors);
			}
		}
		return false;
	}
}