package tools;

import creatures.Player;
import features.Feature;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import world.Tile;

/**
 * A helper class to reduce bloat on PlayScreen
 */
public class DrawMinimap {
	
	public static void draw(Group root, Player p, int sx, int sy) {
		int sizeX = 3;
		int sizeY = 4;
		for (int y = 0; y < p.world().height(); y++) {
			for (int x = 0; x < p.world().width(); x++) {
				Rectangle r = new Rectangle(sx + sizeX*x,sy + sizeY*y,sizeX,sizeY);
				if (x == p.x && y == p.y)
					r.setFill(Color.ORANGERED);
				else {
					if (p.tile(x,y,p.z) == Tile.UNKNOWN)
						r.setFill(Color.BLACK);
					else if (p.tile(x,y,p.z).isGround()) {
						if (p.canSee(x, y, p.z))
							r.setFill(Color.KHAKI);
						else
							r.setFill(Color.DARKKHAKI);
					} else if (p.tile(x,y,p.z).isPit())
						r.setFill(Color.DARKSLATEBLUE);
					else if (p.tile(x,y,p.z).isWater())
						r.setFill(Color.DARKBLUE);
					else
						r.setFill(Color.MIDNIGHTBLUE);

					Feature f = p.feature(x, y, p.z);
					if (f != null) {
						if (f.name().equals("Door"))
							r.setFill(Color.YELLOW);
						else if (f.name().equals("Down Stair"))
							r.setFill(Color.DARKSALMON);
						else if (f.name().equals("Up Stair"))
							r.setFill(Color.DARKORANGE);
						else if (f.name().equals("Entrance"))
							r.setFill(Color.CYAN);
						else if (f instanceof features.Block)
							r.setFill(Color.MIDNIGHTBLUE);
					}
				}
				
				root.getChildren().add(r);
			}
		}
	}

}
