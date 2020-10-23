package screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoseScreen extends Screen {
	private Group baseRoot;

	public LoseScreen(Group root) {
		this.baseRoot = new Group(root);
	}
	
	public void displayOutput(Stage stage) {
		root = new Group(baseRoot);
		scene = new Scene(root, 1280, 800, Color.BLACK);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER))
			return new StartScreen();
    	return this;
    }

}
