package rogue;

import java.awt.Rectangle;

import org.jgrapht.alg.PrimMinimumSpanningTree;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

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
		double rectangularity = 0.3;
		int nbrRooms = 300;
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
		SimpleWeightedGraph<Room, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(int i=0; i<nbrRooms; i++) {
			graph.addVertex(rooms[i]);
		}
		for(int i=0; i<nbrRooms; i++) {
				graph.addVertex(rooms[i]);
			for(int j=i+1; j<nbrRooms; j++) {
				DefaultWeightedEdge e = graph.addEdge(rooms[i], rooms[j]);
				graph.setEdgeWeight(e, rooms[i].dist(rooms[j]));
			}
		}
		PrimMinimumSpanningTree<Room, DefaultWeightedEdge> MST = new PrimMinimumSpanningTree<>(graph);
		for(DefaultWeightedEdge e:MST.getMinimumSpanningTreeEdgeSet()) {
			graph.getEdgeSource(e).connectRoom(graph.getEdgeTarget(e), tiles);
		}
		return this;
	}
	public WorldBuilder makeCaves() {
		return makeRooms();
	}
}