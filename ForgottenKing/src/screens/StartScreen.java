package screens;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class StartScreen extends Screen {
	public StartScreen() {
		Group root = new Group();
		Screen.draw(root, new Image(this.getClass().getResourceAsStream("resources/SplashScreen" + (int)(Math.random()*4) + ".png")), 0, 0);
		write(root, "[?] in game for help", 460, 600, 20, Color.WHITE);
		write(root, "[enter] to start", 470, 632, 22, Color.WHITE);
		scene = new Scene(root,1280,800);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER))
			return new CharacterSelectionScreen();
    	return this;
    }
}
