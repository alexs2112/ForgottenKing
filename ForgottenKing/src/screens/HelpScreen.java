package screens;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelpScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
	private Font font = Font.loadFont(Screen.class.getResourceAsStream("resources/SDS_8x8.ttf"), 20);
	private int currentScreen;
	private int top;
	private int height = 23;
	private List<String> strings;
	public HelpScreen() {
		initControls();
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		if (currentScreen == 0) {
			//Main
			draw(root, new Image(Screen.class.getResourceAsStream("resources/full-screens/help-main.png")), 0, 0);
		} else if (currentScreen == 1) {
			//Controls
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
		} else if (currentScreen == 2) {
			draw(root, new Image(Screen.class.getResourceAsStream("resources/full-screens/help-stats.png")), 0, 0);
		} else if (currentScreen == 3) {
			draw(root, new Image(Screen.class.getResourceAsStream("resources/full-screens/help-combat.png")), 0, 0);
		} else if (currentScreen == 4) {
			draw(root, new Image(Screen.class.getResourceAsStream("resources/full-screens/help-magic.png")), 0, 0);
		} else if (currentScreen == 5) {
			draw(root, new Image(Screen.class.getResourceAsStream("resources/full-screens/help-perks.png")), 0, 0);
		}
	}
	
	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		if (c == '1')
			currentScreen = 1;
		else if (c == '2')
			currentScreen = 2;
		else if (c == '3')
			currentScreen = 3;
		else if (c == '4')
			currentScreen = 4;
		else if (c == '5')
			currentScreen = 5;
		else if (code.equals(KeyCode.ESCAPE)) {
			if (currentScreen == 0)
				return null;
			else
				currentScreen = 0;
		}
		else if (code.equals(KeyCode.DOWN) && currentScreen == 1)
			top= Math.min(top + 1, strings.size()-height);
		else if (code.equals(KeyCode.UP) && currentScreen == 1)
			top= Math.max(top - 1, 0);
        return this;
	}
	
	private void initControls() {
		strings = new ArrayList<String>();
		strings.add("Controls:");
		strings.add("");
		strings.add("Menu Controls:");
		strings.add("Arrow keys to scroll if the menu is scrollable");
		strings.add("Character keys to select options");
		strings.add("[esc] to exit");
		strings.add("");
		strings.add("Movement Controls:");
		strings.add("Arrow Keys, NumPad or vi (hjklyubn) keys to move");
		strings.add("Bump into enemies to attack them");
		strings.add("[.] to wait a turn to rest");
		strings.add("[R] to Rest and recuperate for a time");
		strings.add("[<] or [>] to move up or down a staircase");
		strings.add("[C] to Close an open door next to you");
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
		strings.add("[m] to Meditate on spell points");
		strings.add("");
		strings.add("Misc Controls:");
		strings.add("[space] to pick up items or use staircases");
		strings.add("[s] to view your (very basic) character Stats");
		strings.add("[x] to eXamine your surroundings");
		strings.add("[p] to open the Perk menu");
	}

}