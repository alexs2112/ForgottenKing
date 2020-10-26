package screens;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelpScreen extends Screen {
	private Font font;
	private int top;
	private int height;
	private List<String> strings;

	public HelpScreen() {
		font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 20);
		height = 23;
		init();
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		draw(root, Loader.screenBorder, 0, 0);
		int x = 64;
		int y = 64;
		for (int i = 0; i < height; i++) {
			write(root, strings.get(top+i), x, y + i*32, font, Color.WHITE);
		}
		if (top + height < strings.size())
			draw(root, Loader.arrowDown, 20, 704);
		if (top > 0)
			draw(root, Loader.arrowUp, 20, 64);
	}
	
	private void init() {
		strings = new ArrayList<String>();
		strings.add("Welcome!");
		strings.add("Wander around a small cave, fighting random stuff and");
		strings.add("gathering op items");
		strings.add("");
		strings.add("Movement Controls:");
		strings.add("Arrow Keys, NumPad or vi (hjklyubn) keys to move");
		strings.add("Bump into enemies to attack them");
		strings.add("[.] to wait a turn to rest");
		strings.add("[R] to Rest and recuperate for a time");
		strings.add("[<] or [>] to move up or down a staircase");
		strings.add("");
		strings.add("Inventory Controls:");
		strings.add("[i] to view your Inventory");
		strings.add("[g] to Grab items");
		strings.add("[w] to Wear/Wield equipment");
		strings.add("[d] to Drop an item");
		strings.add("[t] to Throw an item");
		strings.add("[f] to Fire an equipped ranged weapon");
		strings.add("[q] to Quaff a potion");
		strings.add("[r] to Read a book or scroll");
		strings.add("[tab] to swap to your last used weapon");
		strings.add("");
		strings.add("Magic Commands");
		strings.add("[c] to Cast a memorized spell");
		strings.add("[m] to allocate Magic points");
		strings.add("");
		strings.add("Misc Commands:");
		strings.add("[s] to view your (very basic) character Stats");
		strings.add("[x] to eXamine your surroundings");
		strings.add("[p] to open the Perk menu");
		strings.add("[C] to Close an open door next to you");
	}
	
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getCode().equals(KeyCode.DOWN))
			top= Math.min(top + 1, strings.size()-height);
		else if (key.getCode().equals(KeyCode.UP))
			top= Math.max(top - 1, 0);
		else if (key.getCode().equals(KeyCode.ESCAPE))
            return null;
        return this;
	}

}