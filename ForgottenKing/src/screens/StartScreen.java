package screens;

import audio.Audio;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartScreen extends Screen {
	private int select = 0;
	private Stage stage;
	private Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 32);
	private Font fontS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 20);
	private String s;
	public Audio audio() { return Audio.INTRO; }
	
	public StartScreen() {
		switch((int)(Math.random()*3)) {
		case 1: s = "Beware of Lizardfolk!"; break;
		case 2: s = "Welcome!"; break;
		default: s = "Enter the Dungeon!"; break;
		}
	}
	
	public void displayOutput(Stage stage) {
		this.stage = stage;
		Group root = new Group();
		scene = new Scene(root,1280,800);
		draw(root, Loader.startScreenFull, 0, 0);
		
		writeCentered(root, s, 640, 354, fontS, Color.ANTIQUEWHITE);
		
		int y = 474;
		for (int i = 0; i < 2; i++) {
			if (i == select)
				draw(root, Loader.startScreenMenuBoxGold, 340, y + i * 84);
			else
				draw(root, Loader.startScreenMenuBox, 340, y + i * 84);
		}
		writeCentered(root, "[?] in game for help", 640, 700, fontS, Color.WHITE);
		
		Color c = Color.WHITE;
		if (select != 0)
			c = Color.ANTIQUEWHITE;
		writeCentered(root, "Start a New Game", 640, 474+56, font, c);
		
		c = Color.WHITE;
		if (select != 1)
			c = Color.ANTIQUEWHITE;
		writeCentered(root, "Quit Game", 640, 474+84+56, font, c);
		
		stage.setScene(scene);
	    stage.show();
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER)) {
			if (select == 0)
				return new CharacterSelectionScreen();
			else if (select == 1) {
				stage.close();
				return null;
			}
		}
		if (key.getCode().equals(KeyCode.DOWN))
			select = Math.min(select+1, 1);
		if (key.getCode().equals(KeyCode.UP))
			select = Math.min(select-1,  0);
    	return this;
    }
}
