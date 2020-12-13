package screens;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import world.World;


/**
 * A short class that displays the entire dungeon level on the screen at one as a variety of pretty colours
 */
public class DevMapScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
	private World world;
	private int z;
	
	public DevMapScreen(World world, int z) {
		this.world = world;
		this.z = z;
	}
	
	@Override
	public void displayOutput(Stage stage) {
		root = new Group();
		int size = 14;
		for (int y = 0; y < world.height(); y++) {
			for (int x = 0; x < world.width(); x++) {
				Rectangle r = new Rectangle(size*x,size*y,size,size);
				if (world.tile(x, y, z).isGround())
					r.setFill(Color.BEIGE);
				else if (world.tile(x, y, z).isPit())
					r.setFill(Color.DARKVIOLET);
				else if (world.tile(x, y, z).isWater())
					r.setFill(Color.DARKBLUE);
				else
					r.setFill(Color.MIDNIGHTBLUE);
				
				if (world.feature(x, y, z) != null && world.feature(x, y, z).name().equals("Door"))
					r.setFill(Color.YELLOW);
				
				root.getChildren().add(r);
			}
		}
	}
	
	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		if (code.equals(KeyCode.ESCAPE))
			return null;
		return this;
	}

}
