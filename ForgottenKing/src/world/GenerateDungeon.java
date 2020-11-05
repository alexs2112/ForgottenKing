package world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import features.Door;
import features.Feature;
import tools.Point;

public class GenerateDungeon {
	private int[][] map;
	private int[][] regions;
	private int[][] rooms;
	private Feature[][] features;
	public Feature feat(int x, int y) { return features[x][y]; }
	private int nextRegion;
	private int height;
	private int width;
	private ArrayList<Point> set;
	
	public GenerateDungeon(int[][] map, int[][] currentRooms) {
		this.map = map;
		this.width = map.length;
		this.height = map[0].length;
		this.nextRegion = 1;
		this.set = new ArrayList<Point>();
		this.features = new Feature[width][height];
		this.rooms = currentRooms;	//1s mean a room is placed here, so that deleting dead ends doesnt delete them
	}
	
	public int[][] generateDungeon() {
		generateMaze();
		for (int x = 1; x < width-1; x++) {
			for (int y = 1; y < height-1; y++) {
				if (adjacentToFloor(x,y) == 0) {
					generateMaze(x,y);
				}
			}
		}
		
		createRegions();
		findConnectors();
		resolveConnections();

		for (int x = 0; x < width-1; x++) {
			for (int y = 0; y < height-1; y++) {
				if (isDeadEnd(x, y)) {
					map[x][y] = 1;
					regions[x][y] = 0;
					features[x][y] = null;
					x = 1;
					y = 1;
				}
			}
		}
		return map;
	}
	
	private void generateMaze(int sx, int sy) {
		set.add(new Point(sx,sy,0));
		
		while (set.size() > 0) {
			Point c = set.get(0);
			set.remove(c);
			if (adjacentToFloor(c.x,c.y) > 1 || map[c.x][c.y]==0 )
				continue;
			map[c.x][c.y] = 0; 
			addNeighbors(c);
		}
	}
	private void generateMaze() {
		int sx;
		int sy;
		do {
			sx = (int)(Math.random()*width);
			sy = (int)(Math.random()*height);
		} while (adjacentToFloor(sx,sy) > 0);
		
		generateMaze(sx,sy);
	}
	
	private int adjacentToFloor(int x, int y) {
		Point s = new Point(x,y,0);
		Point parent = null;
		List<Point> n = s.neighbors4();
		for (Point p : n) {
			if (p.x > 0 && p.x < width & p.y > 0 && p.y < height)
				if (map[p.x][p.y] != 1)
					parent = p;
		}
		n = s.neighbors8();
		int adjacency = 0;
		for (Point p : n) {
			if (p.x < 0 || p.x >= width || p.y < 0 || p.y >= height) 
				adjacency++;
			else if (map[p.x][p.y] != 1 && !isNeighbors(parent,p))
				adjacency++;
		}
		return adjacency;
	}
	private void addNeighbors(Point p) {
		List<Point> n = p.neighbors4();
		for (Point c : n) {
			if (adjacentToFloor(c.x,c.y) <= 1)
				set.add(c);
		}
	}
	private boolean isNeighbors(Point a, Point b) {
		if (a == null || b == null)
			return false;
		List<Point> points = a.neighbors4();
		for (Point p : points)
			if (p.equals(b))
				return true;
		return false;
	}
	private boolean isDeadEnd(int x, int y) {
		int n = 0;
		if (map[x][y] == 1 || rooms[x][y] == 1)
			return false;
		if (map[x-1][y] != 1)
			n++;
		if (map[x+1][y] != 1)
			n++;
		if (map[x][y-1] != 1)
			n++;
		if (map[x][y+1] != 1)
			n++;
		return n == 1;
	}
	
	private void createRegions() {
		regions = new int[width][height];
	    
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				if (map[x][y] == 0 && regions[x][y] == 0){
					fillRegion(nextRegion++, x, y);
				}
			}
        }
	}
	private void fillRegion(int region, int x, int y) {
        ArrayList<Point> open = new ArrayList<Point>();
        open.add(new Point(x,y, 0));
        regions[x][y] = region;
    
        while (!open.isEmpty()){
            Point p = open.remove(0);

            for (Point neighbor : p.neighbors8()){
                if (regions[neighbor.x][neighbor.y] > 0
                  || map[neighbor.x][neighbor.y] != 0)
                    continue;

                regions[neighbor.x][neighbor.y] = region;
                open.add(neighbor);
            }
        }
    }
	
	int[][] connectors;
	ArrayList<Point> connections;
	private void findConnectors() {
		connectors = new int[width][height];
		connections = new ArrayList<Point>();
		for (int x = 1; x < width-1; x++) {
			for (int y = 1; y < height-1; y++) {
				if (map[x][y] == 1 && regions[x][y] == 0) {
					if (regions[x-1][y] != regions[x+1][y] && regions[x-1][y] != 0 && regions[x+1][y] != 0) {
						connectors[x][y] = 1;
						connections.add(new Point(x,y,0));
					} if (regions[x][y-1] != regions[x][y+1] && regions[x][y-1] != 0 && regions[x][y+1] != 0) {
						connectors[x][y] = 1;
						connections.add(new Point(x,y,0));
					}
				}
			}
		}
	}
	private void changeRegion(int region, int change) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (regions[x][y] == region)
					regions[x][y] = change;
			}
		}
	}
	private void resolveConnections() {
		Collections.shuffle(connections);
		for (int i = 0; i < connections.size(); i++) {
			Point p = connections.get(i);
			if (regions[p.x-1][p.y] != regions[p.x+1][p.y] && regions[p.x-1][p.y]!= 0 && regions[p.x+1][p.y]!=0) {
				int n = regions[p.x-1][p.y];
				changeRegion(regions[p.x+1][p.y], n);
				regions[p.x][p.y] = n;
				map[p.x][p.y] = 0; 
				features[p.x][p.y] = new Door(1); 
			} else if (regions[p.x][p.y-1] != regions[p.x][p.y+1] && regions[p.x][p.y-1]!= 0 && regions[p.x][p.y+1]!=0) {
				int n = regions[p.x][p.y-1];
				changeRegion(regions[p.x][p.y+1], n);
				regions[p.x][p.y] = n;
				map[p.x][p.y] = 0; 
				features[p.x][p.y] = new Door(0);
			}

		}
	}
	@SuppressWarnings("unused")
	private void printMap() {
		for (int y=0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				System.out.print(map[x][y]);
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

}
