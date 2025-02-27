package tools;

import world.World;
import world.Tile;
import tools.Icon;

public final class GetTileDirection {
	public static Icon handleFloor(World world, Tile tile, int x, int y, int z) {
			if (getAllDirections(world, tile, x,y,z,0,1,1,1))
				return tile.N;
			if (getAllDirections(world, tile, x,y,z,1,0,1,1))
				return tile.E;
			if (getAllDirections(world, tile, x,y,z,1,1,0,1))
				return tile.S;
			if (getAllDirections(world, tile, x,y,z,1,1,1,0))
				return tile.W;
			if (getAllDirections(world, tile, x,y,z,0,0,1,1))
				return tile.NE;
			if (getAllDirections(world, tile, x,y,z,0,1,0,1))
				return tile.NS;
			if (getAllDirections(world, tile, x,y,z,0,1,1,0))
				return tile.NW;
			if (getAllDirections(world, tile, x,y,z,0,0,0,1))
				return tile.NES;
			if (getAllDirections(world, tile, x,y,z,0,0,1,0))
				return tile.NEW;
			if (getAllDirections(world, tile, x,y,z,0,1,0,0))
				return tile.NSW;
			if (getAllDirections(world, tile, x,y,z,0,0,0,0))
				return tile.NESW;
			if (getAllDirections(world, tile, x,y,z,1,0,0,1))
				return tile.ES;
			if (getAllDirections(world, tile, x,y,z,1,0,1,0))
				return tile.EW;
			if (getAllDirections(world, tile, x,y,z,1,0,0,0))
				return tile.ESW;
			if (getAllDirections(world, tile, x,y,z,1,1,0,0))
				return tile.SW;
			return tile.ALL;
	}
	
	public static Icon handleWall(World world, Tile tile, int x, int y, int z) {
		if (getAllDirections(world, tile, x,y,z,0,-1,1,-1,1,-1,0,-1) ||
			getAllDirections(world, tile, x,y,z,1,1,1,0,1,1,1,1))
    			return tile.ES;
		if (getAllDirections(world, tile, x,y,z,0,-1,1,-1,0,-1,1,-1) ||
			getAllDirections(world, tile, x,y,z,0,-1,1,-1,0,-1,0,-1) ||
			getAllDirections(world, tile, x,y,z,0,-1,0,-1,0,-1,1,-1) ||
			getAllDirections(world, tile, x,y,z,1,1,1,-1,0,-1,1,1) ||
			getAllDirections(world, tile, x,y,z,0,-1,1,1,1,1,1,-1) ||
			getAllDirections(world, tile, x,y,z,0,-1,0,-1,0,-1,0,-1))
				return tile.EW;
		if (getAllDirections(world, tile, x,y,z,0,-1,0,-1,1,-1,1,-1) ||
				getAllDirections(world, tile, x,y,z,1,1,1,1,1,0,1,1))
				return tile.SW;
		if (getAllDirections(world, tile, x,y,z,1,-1,0,-1,1,-1,0,-1) ||
			getAllDirections(world, tile, x,y,z,0,-1,0,-1,1,-1,0,-1) ||
			getAllDirections(world, tile, x,y,z,1,-1,0,-1,1,1,1,1) ||
			getAllDirections(world, tile, x,y,z,1,1,1,1,1,-1,0,-1))
				return tile.NS;
		if (getAllDirections(world, tile, x,y,z,1,-1,0,-1,0,-1,0,-1))
				return tile.N;
		if (getAllDirections(world, tile, x,y,z,1,-1,1,-1,0,-1,0,-1) ||
				getAllDirections(world, tile, x,y,z,1,0,1,1,1,1,1,1))
				return tile.NE;
		if (getAllDirections(world, tile, x,y,z,1,-1,0,-1,0,-1,1,-1) ||
			getAllDirections(world, tile, x,y,z,1,1,1,1,1,1,1,0))
				return tile.NW;
		if (getAllDirections(world, tile, x,y,z,0,-1,1,-1,1,0,1,-1) ||
			getAllDirections(world, tile, x,y,z,0,-1,1,0,1,-1,1,-1) ||
				getAllDirections(world, tile, x,y,z,1,1,1,0,1,0,1,1))
				return tile.ESW;
		if (getAllDirections(world, tile, x,y,z,1,0,1,-1,1,-1,0,-1) ||
				getAllDirections(world, tile, x,y,z,1,-1,1,0,1,-1,0,-1) ||
				getAllDirections(world, tile, x,y,z,1,0,1,0,1,1,1,1))
				return tile.NES;
		if (getAllDirections(world, tile, x,y,z,1,0,1,0,1,0,1,0) ||
				getAllDirections(world, tile, x,y,z,1,-1,1,0,1,-1,1,0) ||
				getAllDirections(world, tile, x,y,z,1,0,1,-1,1,0,1,-1))
				return tile.NESW;
		if (getAllDirections(world, tile, x,y,z,1,-1,0,-1,1,0,1,-1) ||
				getAllDirections(world, tile, x,y,z,1,-1,0,-1,1,-1,1,0) ||
				getAllDirections(world, tile, x,y,z,1,1,1,1,1,0,1,0))
				return tile.NSW;
		if (getAllDirections(world, tile, x,y,z,1,-1,1,-1,0,-1,1,0) ||
				getAllDirections(world, tile, x,y,z,1,0,1,-1,0,-1,1,-1) ||
				getAllDirections(world, tile, x,y,z,1,0,1,1,1,1,1,0))
				return tile.NEW;
		return tile.ALL;
	}
	
	public static Icon handlePit(World world, Tile tile, int x, int y, int z) {
		if (getAllDirectionsPit(world, tile, x,y,z,0,1,1,1))
			return tile.N;
		if (getAllDirectionsPit(world, tile, x,y,z,1,0,1,1))
			return tile.E;
		if (getAllDirectionsPit(world, tile, x,y,z,1,1,0,1))
			return tile.S;
		if (getAllDirectionsPit(world, tile, x,y,z,1,1,1,0))
			return tile.W;
		if (getAllDirectionsPit(world, tile, x,y,z,0,0,1,1))
			return tile.NE;
		if (getAllDirectionsPit(world, tile, x,y,z,0,1,0,1))
			return tile.NS;
		if (getAllDirectionsPit(world, tile, x,y,z,0,1,1,0))
			return tile.NW;
		if (getAllDirectionsPit(world, tile, x,y,z,0,0,0,1))
			return tile.NES;
		if (getAllDirectionsPit(world, tile, x,y,z,0,0,1,0))
			return tile.NEW;
		if (getAllDirectionsPit(world, tile, x,y,z,0,1,0,0))
			return tile.NSW;
		if (getAllDirectionsPit(world, tile, x,y,z,0,0,0,0))
			return tile.NESW;
		if (getAllDirectionsPit(world, tile, x,y,z,1,0,0,1))
			return tile.ES;
		if (getAllDirectionsPit(world, tile, x,y,z,1,0,1,0))
			return tile.EW;
		if (getAllDirectionsPit(world, tile, x,y,z,1,0,0,0))
			return tile.ESW;
		if (getAllDirectionsPit(world, tile, x,y,z,1,1,0,0))
			return tile.SW;
		if (getAllDirectionsPit(world, tile, x,y,z,1,1,1,-1,-1,-1,-1,0))
			return tile.XNE;
		if (getAllDirectionsPit(world, tile, x,y,z,1,0,1,-1,-1,-1,-1,0))
			return tile.XNEW;
		if (getAllDirectionsPit(world, tile, x,y,z,1,0,1,-1,-1,-1,-1,1))
			return tile.XNW;
		return tile.ALL;
}

	/**
	 * A 1 in that direction means the tile in that direction is the same as the tile in question
	 * A 0 means the tile is a different tile
	 * A -1 means that tile is irrelevant
	 */
	private static boolean getAllDirections(World world, Tile tile, int x, int y, int z, int n, int ne, int e, int se, int s, int sw, int w, int nw) {
		boolean c1 = false;
		boolean c2 = false;
		boolean c3 = false;
		boolean c4 = false;
		boolean c5 = false;
		boolean c6 = false;
		boolean c7 = false;
		boolean c8 = false;
		if (x == 0 || w == -1)
			c1 = true;
		else {
			if (w == 1) {
				if (world.tile(x-1, y, z).isEqual(tile))
					c1 = true;
			} else
				if (!world.tile(x-1, y, z).isEqual(tile))
					c1 = true;
		}
		
		if (x == world.width()-1 || e == -1)
			c2 = true;
		else {
			if (e == 1) {
				if (world.tile(x+1, y, z).isEqual(tile))
					c2 = true;
			} else
				if (!world.tile(x+1, y, z).isEqual(tile))
					c2 = true;
		}
		
		if (y == 0 || n == -1)
			c3 = true;
		else {
			if (n == 1) {
				if (world.tile(x, y-1, z).isEqual(tile))
					c3 = true;
			} else
				if (!world.tile(x, y-1, z).isEqual(tile))
					c3 = true;
		}
		
		if (y == world.height() - 1 || s == -1)
			c4 = true;
		else {
			if (s == 1) {
				if (world.tile(x, y+1, z).isEqual(tile))
					c4 = true;
			} else
				if (!world.tile(x, y+1, z).isEqual(tile))
					c4 = true;
		}
		
		if (x == 0 || y == 0 || nw == -1)
			c5 = true;
		else {
			if (nw == 1) {
				if (world.tile(x-1, y-1, z).isEqual(tile))
					c5 = true;
			} else
				if (!world.tile(x-1, y-1, z).isEqual(tile))
					c5 = true;
		}
		
		if ((x == world.width() - 1 || y == 0) || ne == -1)
			c6 = true;
		else {
			if (ne == 1) {
				if (world.tile(x+1, y-1, z).isEqual(tile))
					c6 = true;
			} else
				if (!world.tile(x+1, y-1, z).isEqual(tile))
					c6 = true;
		}
		
		if ((x == 0 || y == world.height() - 1) || sw == -1)
			c7 = true;
		else {
			if (sw == 1) {
				if (world.tile(x-1, y+1, z).isEqual(tile))
					c7 = true;
			} else
				if (!world.tile(x-1, y+1, z).isEqual(tile))
					c7 = true;
		}
		
		if ((x == world.width()-1 || y == world.height()-1) || se == -1)
			c8 = true;
		else {
			if (se == 1) {
				if (world.tile(x+1,y+1, z).isEqual(tile))
					c8 = true;
			} else
				if (!world.tile(x+1,y+1, z).isEqual(tile))
					c8 = true;
		}
		return (c1 && c2 && c3 && c4 && c5 && c6 && c7 && c8);
	}
	private static boolean getAllDirections(World world, Tile tile, int x, int y, int z, int n, int e, int s, int w) {
		return getAllDirections(world, tile, x,y,z,n,-1,e,-1,s,-1,w,-1);
	}
	
	
	
	/**
	 * Need slightly different handling for pits
	 * In order to make bridges, pits only change if it is a different tile of the same group
	 */
	private static boolean getAllDirectionsPit(World world, Tile tile, int x, int y, int z, int n, int e, int s, int w) {
		return getAllDirectionsPit(world, tile, x,y,z,n,-1,e,-1,s,-1,w,-1);
	}
	private static boolean getAllDirectionsPit(World world, Tile tile, int x, int y, int z, int n, int ne, int e, int se, int s, int sw, int w, int nw) {
		boolean c1 = false;
		boolean c2 = false;
		boolean c3 = false;
		boolean c4 = false;
		boolean c5 = false;
		boolean c6 = false;
		boolean c7 = false;
		boolean c8 = false;
		if (x == 0 || w == -1)
			c1 = true;
		else {
			Tile t = world.tile(x-1, y, z);
			if (w == 1) {
				if (t == tile || t.group() != tile.group())
					c1 = true;
			} else
				if (t != tile && t.group() == tile.group())
					c1 = true;
		}
		
		if (x == world.width()-1 || e == -1)
			c2 = true;
		else {
			Tile t = world.tile(x+1, y, z);
			if (e == 1) {
				if (t == tile || t.group() != tile.group())
					c2 = true;
			} else
				if (t != tile && t.group() == tile.group())
					c2 = true;
		}
		
		if (y == 0 || n == -1)
			c3 = true;
		else {
			Tile t = world.tile(x, y-1, z);
			if (n == 1) {
				if (t == tile || t.group() != tile.group())
					c3 = true;
			} else
				if (t != tile && t.group() == tile.group())
					c3 = true;
		}
		
		if (y == world.height() - 1 || s == -1)
			c4 = true;
		else {
			Tile t = world.tile(x, y+1, z);
			if (s == 1) {
				if (t == tile || t.group() != tile.group())
					c4 = true;
			} else
				if (t != tile && t.group() == tile.group())
					c4 = true;
		}
		
		if (x == 0 || y == 0 || nw == -1)
			c5 = true;
		else {
			Tile t = world.tile(x-1, y-1, z);
			if (nw == 1) {
				if (t == tile || t.group() != tile.group())
					c5 = true;
			} else
				if (t != tile && t.group() == tile.group())
					c5 = true;
		}
		
		if ((x == world.width() - 1 || y == 0) || ne == -1)
			c6 = true;
		else {
			Tile t = world.tile(x+1, y-1, z);
			if (ne == 1) {
				if (t == tile || t.group() != tile.group())
					c6 = true;
			} else
				if (t != tile && t.group() == tile.group())
					c6 = true;
		}
		
		if ((x == 0 || y == world.height() - 1) || sw == -1)
			c7 = true;
		else {
			Tile t = world.tile(x-1, y+1, z);
			if (sw == 1) {
				if (t == tile || t.group() != tile.group())
					c7 = true;
			} else
				if (t != tile && t.group() == tile.group())
					c7 = true;
		}
		
		if ((x == world.width()-1 || y == world.height()-1) || se == -1)
			c8 = true;
		else {
			Tile t = world.tile(x+1, y+1, z);
			if (se == 1) {
				if (t == tile || t.group() != tile.group())
					c8 = true;
			} else
				if (t != tile && t.group() == tile.group())
					c8 = true;
		}
		return (c1 && c2 && c3 && c4 && c5 && c6 && c7 && c8);
	}
}
