package rogue;

import java.awt.Rectangle;

public class Room {
	Rectangle rect;
	public Rectangle getRect() { return rect; }
	public Room(Rectangle rect) {
		this.rect = rect;
	}
	
	public int getRoomCenterX() {
		return (int) rect.getCenterX();
	}
	
	public int getRoomCenterY() {
		return (int) rect.getCenterY();
	}
	
	public Tile[][] connectRoom(Room r, Tile[][] tiles) {
		int x0 = getRoomCenterX(), y0 = getRoomCenterY();
		int x1 = r.getRoomCenterX(), y1 = r.getRoomCenterY();
		int cmpt = 0;
		while(x0!=x1 && y0!=y1) {
			tiles[x0][y0] = Tile.FLOOR;
			double a = -1*Math.toDegrees(Math.atan2((y1-y0), (x1-x0)));
			System.out.println(a);
			if(a>-18.43 && a<18.43) {
				x0++;
			} else if(a >= 18.43 && a <= 90-18.43) {
				if(a>45) {
					tiles[x0][y0==0?0:y0-1] = Tile.FLOOR;
				} else {
					tiles[x0+1][y0] = Tile.FLOOR;
				}
				x0++; y0--;
			} else if(a > 90-18.43 && a<90+18.43) {
				x0--;
			} else if(a >=90+18.43 && a <=180-18.43) {
				if(a>135) {
					tiles[x0==0?0:x0-1][y0] = Tile.FLOOR;
				} else {
					tiles[x0][y0==0?0:y0-1] = Tile.FLOOR;
				}
				x0--; y0--;
			} else if(a > 180-18.43) {
				x0--;
			} else if(a<=-18.43 && a>=-90+18.43) {
				if(a<-45) {
					tiles[x0][y0+1] = Tile.FLOOR;
				} else {
					tiles[x0+1][y0] = Tile.FLOOR;
				}
				x0++; y0++;
			} else if(a<-90+18.43 && a>-90-18.43) {
				y0++;
			} else if(a<-90-18.43 && a>-180+18.43) {
				if(a<-135) {
					tiles[x0-1][y0] = Tile.FLOOR;
				} else {
					tiles[x0][y0+1] = Tile.FLOOR;
				}
				x0--; y0++;
			} else if(a<-180+18.43) {
				x0--;
			}
			if(cmpt++>3000) {
				x0 = x1;
				y0 = y1;
			}
		}
		return tiles;
	}
}
