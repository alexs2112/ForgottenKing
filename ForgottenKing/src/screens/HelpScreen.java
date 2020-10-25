package screens;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelpScreen extends Screen {
	private Font font;

	public HelpScreen() {
		font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 20);
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		draw(root, Loader.screenBorder, 0, 0);
		int x = 32;
		int y = 48;
		write(root, "Welcome!", x, y += 32, font, Color.WHITE);
		write(root, "Wander around a small cave, fighting random stuff and", x, y += 32, font, Color.WHITE);
		write(root, "gathering op items", x, y += 32, font, Color.WHITE);
		write(root, "Arrow Keys, NumPad or vi (hjklyubn) keys to move", x, y+=32, font, Color.WHITE);
		write(root, "Bump into enemies to attack them", x, y+=32, font, Color.WHITE);
		write(root, "[.] to wait a turn to rest", x, y+=32, font, Color.WHITE);
		write(root, "[g] to Grab items", x, y+=32, font, Color.WHITE);
		write(root, "[w] to Wear/Wield equipment", x, y+=32, font, Color.WHITE);
		write(root, "[s] to view your (very basic) character Stats", x, y+=32, font, Color.WHITE);
		write(root, "[d] to Drop an item", x, y+=32, font, Color.WHITE);
		write(root, "[i] to view your Inventory", x, y+=32, font, Color.WHITE);
		write(root, "[x] to eXamine your surroundings", x, y+=32, font, Color.WHITE);
		write(root, "[t] to Throw an item", x, y+=32, font, Color.WHITE);
		write(root, "[f] to Fire an equipped ranged weapon", x, y+=32, font, Color.WHITE);
		write(root, "[tab] to swap to your last used weapon", x, y+=32, font, Color.WHITE);
		write(root, "[q] to Quaff a potion", x, y+=32, font, Color.WHITE);
		write(root, "[r] to Read a book or scroll", x, y+=32, font, Color.WHITE);
		write(root, "[z] to Zap a memorized spell", x, y+=32, font, Color.WHITE);
		write(root, "[m] to allocate Magic points", x, y+=32, font, Color.WHITE);
		write(root, "[p] to open the Perk menu", x, y+=32, font, Color.WHITE);
		write(root, "[c] to Close an open door next to you", x, y+=32, font, Color.WHITE);
		write(root, "[5] to rest and recuperate for a time", x, y+=32, font, Color.WHITE);
		write(root, "[<] or [>] to move up or down a staircase", x, y+=32, font, Color.WHITE);
	}
	
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ESCAPE))
            return null;
        else
            return this;
	}

}