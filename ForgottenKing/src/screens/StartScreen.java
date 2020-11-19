package screens;

import audio.Audio;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartScreen extends Screen {
	private int select = 0;
	//private Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 32);
	private Font fontS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 20);
	public Audio audio() { return Audio.INTRO; }
	public void displayOutput(Stage stage) {
		Group root = new Group();
		scene = new Scene(root,1280,800);
		draw(root, Loader.startScreenFull, 0, 0);
		int y = 514;
		Image start = Loader.startScreenNewGame;
		Image quit = Loader.startScreenQuitGame;
		if (select == 0)
			start = Loader.startScreenNewGameGold;
		if (select == 1)
			quit = Loader.startScreenQuitGameGold;
		
		draw(root, start, 340, y, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				refreshScreen = new CharacterSelectionScreen();
			}
		}, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				if (select != 0) {
					select = 0;
					refreshScreen = returnThis();
				}
			}
		}, null);

		draw(root, quit, 340, y + 84, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				stage.close();
			}
		}, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				if (select != 1) {
					select = 1;
					refreshScreen = returnThis();
				}
			}
		}, null);
		
		writeCentered(root, "[?] in game for help", 640, 740, fontS, Color.WHITE);
		stage.setScene(scene);
	    stage.show();
	}

	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		if (code.equals(KeyCode.ENTER)) {
			if (select == 0)
				return new CharacterSelectionScreen();
			else if (select == 1) {
				Platform.exit();
				return null;
			}
		}
		if (code.equals(KeyCode.DOWN))
			select = Math.min(select+1, 1);
		if (code.equals(KeyCode.UP))
			select = Math.max(select-1,  0);
    	return this;
    }
}
