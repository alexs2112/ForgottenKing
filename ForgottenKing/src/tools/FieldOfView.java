package tools;

import javafx.scene.image.Image;
import world.Tile;
import world.World;

public class FieldOfView implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
    private World world;
    private int depth;

    private boolean[][] visible;
    public boolean isVisible(int x, int y, int z){
        return z == depth && x >= 0 && y >= 0 && x < visible.length && y < visible[0].length && visible[x][y];
    }

    private Tile[][][] tiles;
    public Tile tile(int x, int y, int z){
        return tiles[x][y][z];
    }
    
    public boolean hasSeen(int x, int y, int z) {
    	return !(tiles[x][y][z]==Tile.UNKNOWN);
    }

    public FieldOfView(World world){
        this.world = world;
        this.visible = new boolean[world.width()][world.height()];
        this.tiles = new Tile[world.width()][world.height()][world.depth()];
    
        for (int x = 0; x < world.width(); x++){
            for (int y = 0; y < world.height(); y++){
                for (int z = 0; z < world.depth(); z++){
                    tiles[x][y][z] = Tile.UNKNOWN;
                }
            }
        }
    }
    
    public void update(int wx, int wy, int wz, int r){
        depth = wz;
        visible = new boolean[world.width()][world.height()];
    
        for (int x = -r; x <= r; x++){
            for (int y = -r; y <= r; y++){
                if (x*x + y*y > r*r)
                    continue;
         
                if (wx + x < 0 || wx + x >= world.width() 
                 || wy + y < 0 || wy + y >= world.height())
                    continue;
         
                for (Point p : new Line(wx, wy, wx + x, wy + y)){
                    Tile tile = world.tile(p.x, p.y, wz);
                    visible[p.x][p.y] = true;
                    tiles[p.x][p.y][wz] = tile;
             
                    if ((world.feature(p.x, p.y, wz) != null && world.feature(p.x, p.y, wz).blockLineOfSight()))
                        break;
                    if (!world.tile(p.x, p.y, wz).isWall())
                        continue;
                    
                    break;
                }
            }
        }
    }
    public Image tileImage(int x, int y, int z){
		return tile(x, y, z).getImage(world, x, y, z);
	}
}